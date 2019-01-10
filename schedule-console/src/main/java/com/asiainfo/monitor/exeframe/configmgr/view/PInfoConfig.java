package com.asiainfo.monitor.exeframe.configmgr.view;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.tapestry.IRequestCycle;

import com.ai.appframe2.complex.cache.CacheFactory;
import com.ai.appframe2.service.ServiceFactory;
import com.ailk.appengine.util.DataBusHelper;
import com.ailk.appengine.web.view.AppPage;
import com.ailk.common.data.IData;
import com.ailk.common.data.IDataBus;
import com.ailk.common.data.IDataContext;
import com.ailk.common.data.impl.DataContext;
import com.ailk.common.data.impl.TreeBean;
import com.ailk.common.data.impl.TreeHelper;
import com.ailk.common.data.impl.TreeItem;
import com.ailk.web.view.component.tree.TreeFactory;
import com.ailk.web.view.component.tree.TreeParam;
import com.asiainfo.common.operation.log.OperationLogUtils;
import com.asiainfo.common.utils.CommonConstants;
import com.asiainfo.common.utils.PagingUtil;
import com.asiainfo.common.utils.ProcessJsonUtil;
import com.asiainfo.monitor.busi.cache.MonCacheManager;
import com.asiainfo.monitor.busi.cache.impl.AIMonPInfoGroupCacheImpl;
import com.asiainfo.monitor.busi.cache.impl.AIMonStaticDataCacheImpl;
import com.asiainfo.monitor.busi.cache.impl.MonPInfoGroup;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPExecValue;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPInfoGroupValue;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPInfoValue;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPTimeValue;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonThresholdValue;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPExecSV;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPInfoGroupSV;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPInfoSV;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPThresholdSV;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPTimeSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonParamValuesSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonPhysicHostSV;
import com.asiainfo.monitor.common.utils.XssUtil;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonParamValuesValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonStaticDataValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonPhysicHostValue;
import com.asiainfo.monitor.exeframe.configmgr.util.ConfigPageUtil;
import com.asiainfo.resultConstant.ResultConstants;

/**
 * Created by SMT Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
public abstract class PInfoConfig extends AppPage
{
    private static final Logger LOGGER = Logger.getLogger(PInfoConfig.class);
    
    private static final String LEVEL_ONE = "1";
    
    private static final String LEVEL_TWO = "2";
    
    private static final String LEVEL_THREE = "3";
    
    /** 任务类型 */
    private Map<String, String> methodMap;
    
    /** 监控信息类型 */
    private Map<String, String> typeMap;
    
    /** 监控信息类型 */
    private Map<String, String> hostMap;
    
    /** 时间配置 */
    private Map<String, String> timeMap;
    
    /** 监控配置（进程监控配置表） */
    private Map<String, String> ttypeMap;
    
    /** 阀值 */
    private Map<String, String> thresholdMap;
    
    private Map<String, String> layerMap;
    
    private void initExtend(IRequestCycle cycle)
    {
        
    }
    
    private void initExtend()
    {
        
    }
    
    private void initMethodMap()
    {
        methodMap = new HashMap<String, String>();
        methodMap.put("F", "固定时间");
        methodMap.put("I", "立即执行");
        methodMap.put("C", "周期执行");
        methodMap.put("P", "面板执行");
    }
    
    private void initTypeMap()
    {
        typeMap = new HashMap<String, String>();
        typeMap.put("EXEC", "进程方式");
    }
    
    private void initLayerMap()
        throws Exception
    {
        layerMap = new HashMap<String, String>();
        HashMap all = CacheFactory.getAll(AIMonStaticDataCacheImpl.class);
        IBOAIMonStaticDataValue[] result = (IBOAIMonStaticDataValue[])all.get("_CT^LAYER");
        IDataContext context = new DataContext();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < result.length; i++)
        {
            String key = result[i].getCodeName();
            String val = result[i].getCodeValue();
            layerMap.put(key, val);
        }
    }
    
    /**
     * 获取主机配置
     * 
     * @return
     */
    private void initHostMap()
    {
        try
        {
            IAIMonPhysicHostSV hostSV = (IAIMonPhysicHostSV)ServiceFactory.getService(IAIMonPhysicHostSV.class);
            IBOAIMonPhysicHostValue[] values = hostSV.getAllPhostBean();
            hostMap = new HashMap<String, String>();
            if (values != null && values.length > 0)
            {
                for (IBOAIMonPhysicHostValue host : values)
                {
                    hostMap.put(String.valueOf(host.getHostId()), host.getHostName() + "(" + host.getHostIp() + ")");
                }
            }
        }
        catch (Exception e)
        {
            log.error(e);
        }
    }
    
    /**
     * 初始化监控配置信息
     */
    private void initTtypeMap()
    {
        ttypeMap = new HashMap<String, String>();
        IAIMonPExecSV sv = (IAIMonPExecSV)ServiceFactory.getService(IAIMonPExecSV.class);
        try
        {
            IBOAIMonPExecValue[] values = sv.getExecByName(null, -1, -1);
            for (IBOAIMonPExecValue value : values)
            {
                ttypeMap.put(value.getExecId() + "", value.getName());
            }
            
        }
        catch (RemoteException e)
        {
            log.error(e);
        }
        catch (Exception e)
        {
            log.error(e);
        }
    }
    
    /**
     * 初始化时间配置信息
     */
    private void initTimeMap()
    {
        timeMap = new HashMap<String, String>();
        IAIMonPTimeSV sv = (IAIMonPTimeSV)ServiceFactory.getService(IAIMonPTimeSV.class);
        try
        {
            IBOAIMonPTimeValue[] values = sv.getTimeInfoByExpr(null, -1, -1);
            for (IBOAIMonPTimeValue value : values)
            {
                // 若备注没有 则显示表达式,用以区别没有配置和配置了没有备注两种情况
                timeMap.put(value.getTimeId() + "", value.getRemarks() == null ? value.getExpr() : value.getRemarks());
            }
            
        }
        catch (RemoteException e)
        {
            log.error(e);
        }
        catch (Exception e)
        {
            log.error(e);
        }
    }
    
    /**
     * 初始化阀值配置信息
     */
    private void initThresholdMap()
    {
        thresholdMap = new HashMap<String, String>();
        IAIMonPThresholdSV sv = (IAIMonPThresholdSV)ServiceFactory.getService(IAIMonPThresholdSV.class);
        try
        {
            IBOAIMonThresholdValue[] values = sv.getThresholdByExprAndName(null, null, -1, -1);
            for (IBOAIMonThresholdValue value : values)
            {
                // 若备注没有 则显示表达式,用以区别没有配置和配置了没有备注两种情况
                thresholdMap.put(value.getThresholdId() + "", value.getThresholdName());
            }
            
        }
        catch (RemoteException e)
        {
            log.error(e);
        }
        catch (Exception e)
        {
            log.error(e);
        }
    }
    
    /**
     * 生成任务类型下拉框
     * 
     * @return
     */
    public IDataBus getMethodSelectList()
    {
        initMethodMap();
        IDataBus bus = ConfigPageUtil.getSelectList(methodMap);
        return bus;
    }
    
    /**
     * 转换任务类型
     * 
     * @param methodId
     * @return
     */
    public String methodTrans(String methodId)
    {
        initMethodMap();
        return methodMap.get(methodId);
    }
    
    /**
     * 生成监控信息类型下拉框
     * 
     * @return
     */
    public IDataBus getTypeSelectList()
    {
        initTypeMap();
        IDataBus bus = ConfigPageUtil.getSelectList(typeMap);
        return bus;
    }
    
    /**
     * 转换监控信息类型
     * 
     * @param typeId
     * @return
     */
    public String typeTrans(String typeId)
    {
        initTypeMap();
        return typeMap.get(typeId);
    }
    
    /**
     * 生成主机配置下拉菜单
     * 
     * @return
     */
    public IDataBus getHostSelectList()
    {
        initHostMap();
        IDataBus bus = ConfigPageUtil.getSelectList(hostMap);
        return bus;
    }
    
    /**
     * 转换主机
     * 
     * @param hostId
     * @return
     */
    public String hostTrans(String hostId)
    {
        initHostMap();
        return hostMap.get(hostId);
    }
    
    /**
     * 生成层下拉菜单
     * 
     * @return
     */
    public IDataBus getLayerSelectList()
    {
        try
        {
            initLayerMap();
        }
        catch (Exception e)
        {
            log.error(e);
            return null;
        }
        IDataBus bus = ConfigPageUtil.getSelectList(layerMap);
        return bus;
    }
    
    /**
     * 转换层
     * 
     * @param layer
     * @return
     */
    public String layerTrans(String layer)
    {
        try
        {
            initLayerMap();
        }
        catch (Exception e)
        {
            log.error(e);
            return null;
        }
        return layerMap.get(layer);
    }
    
    // public IDataBus getBusiSelectList(java.lang.String constCode) throws Exception
    // {
    // HashMap all = CacheFactory.getAll(AIMonStaticDataCacheImpl.class);
    // IBOAIMonStaticDataValue[] result
    // = (IBOAIMonStaticDataValue[]) all.get("_CT^" + constCode);
    // IDataContext context = new DataContext();
    // JSONArray jsonArray = new JSONArray();
    // for(int i = 0; i < result.length; i++) {
    // JSONObject obj = new JSONObject();
    // String key = result[i].getCodeName();
    // String val = result[i].getCodeValue();
    // obj.put("TEXT", key);
    // obj.put("VALUE", val);
    // jsonArray.add(obj);
    // }
    // return new DataBus(context, jsonArray);
    // }
    
    public String groupTrans(String groupId)
    {
        Map<String, String> result = null;
        try
        {
            Map map = MonCacheManager.getAll(AIMonPInfoGroupCacheImpl.class);
            Set<Entry> set = map.entrySet();
            result = new HashMap<String, String>();
            for (Entry entry : set)
            {
                MonPInfoGroup group = (MonPInfoGroup)entry.getValue();
                result.put(group.getId(), group.getName());
            }
        }
        catch (Exception e)
        {
            log.error(e);
            return null;
        }
        return result.get(groupId);
    }
    
    public String timeTrans(String timeId)
    {
        initTimeMap();
        return timeMap.get(timeId);
    }
    
    public String ttypeTrans(String ttypeId)
    {
        initTtypeMap();
        return ttypeMap.get(ttypeId);
    }
    
    public String thresholdTrans(String thresholdId)
    {
        initThresholdMap();
        return thresholdMap.get(thresholdId);
    }
    
    /**
     * 加载树
     * 
     * @param request
     * @throws Exception
     */
    public void loadTreeData(IRequestCycle request)
        throws Exception
    {
        // 获取角色标识
        TreeParam param = TreeParam.getTreeParam(request);
        
        // 同步全量加载树
        TreeBean[] treeBeanArr = ConfigPageUtil.getTreeItems();
        
        TreeItem[] root = TreeHelper.getWholeTreeData(treeBeanArr);
        IData treeNodes = TreeFactory.buildTreeData(param, root);
        
        getContext().setAjax(treeNodes);
    }
    
    /**
     * 查询数据
     * 
     * @param request
     * @throws Exception
     */
    public void loadTableData(IRequestCycle request)
        throws Exception
    {
        // 分页使用
        String offset = getContext().getParameter("offset");
        String linage = getContext().getParameter("linage");
        
        String treeNodeId = getContext().getParameter("treeNodeId");
        String level = getContext().getParameter("level");
        
        JSONObject jsonObject = new JSONObject();
        // 查询分组
        if (LEVEL_ONE.equals(level))
        {
            // 分页情况暂不考虑
            // int start = Integer.parseInt(qryformBus.getContext().getPaginStart() + "");
            // int end = Integer.parseInt(qryformBus.getContext().getPaginEnd() + "");
            // int totalCnt = sv.getUserCntByCond(userCode, userName);
            
            JSONArray groupArr = this.qryGroupInfo();
            
            /*
             * this.setGroupInfosItems(groupArr); this.setGroupInfosCount(groupArr.size());
             */
            
            jsonObject.put("groupInfosItems", PagingUtil.convertArray2Page(groupArr, offset, linage));
            jsonObject.put("total", groupArr.size());
        }
        // 查询分组下任务
        else if (LEVEL_TWO.equals(level))
        {
            JSONArray qryInfos = this.qryInfo(treeNodeId);
            
            /*
             * this.setInfosItems(qryInfos); this.setInfosCount(qryInfos.size());
             */
            
            jsonObject.put("infosItems", PagingUtil.convertArray2Page(qryInfos, offset, linage));
            jsonObject.put("total", qryInfos.size());
        }
        // 查询详细任务信息
        else if (LEVEL_THREE.equals(level))
        {
            JSONObject qryNodeInfo = this.qryInfoDetail(treeNodeId);
            JSONArray qryParamInfos = this.qryParam(treeNodeId);
            /*
             * this.setInfoDetail(qryNodeInfo); this.setParamInfosItems(qryParamInfos);
             * this.setParamInfosCount(qryParamInfos.size());
             */
            jsonObject.put("infoDetail", qryNodeInfo);
            jsonObject.put("paramInfosItems", PagingUtil.convertArray2Page(qryParamInfos, offset, linage));
            jsonObject.put("total", qryParamInfos.size());
        }
        // 查询应用
        // else if(level.equals(CommonConst.NODE_LEVEL)) {
        //
        // JSONArray qryServerInfo = this.qryServerInfo(treeNodeId);
        //
        // this.setServerTabItems(qryServerInfo);
        // this.setServerTabCount(qryServerInfo.size());
        // }
        
        this.setAjax(jsonObject);
        
    }
    
    /**
     * 查询所有分组信息
     * 
     * @return
     * @throws Exception
     * @throws
     */
    private JSONArray qryGroupInfo()
        throws Exception
    {
        // 查询所有分组
        IAIMonPInfoGroupSV groupSv = (IAIMonPInfoGroupSV)ServiceFactory.getService(IAIMonPInfoGroupSV.class);
        IBOAIMonPInfoGroupValue[] result = groupSv.getPInfoGroupByCodeAndName(null, null, null, -1, -1);
        IDataBus bus = null;
        if (result == null)
            bus = DataBusHelper.getEmptyArrayDataBus();
        else
            bus = DataBusHelper.getDataBusByBeans(result, IBOAIMonPInfoGroupValue.class);
        return bus.getDataArray();
    }
    
    /**
     * 查询分组下任务信息
     * 
     * @param groupId
     * @return
     * @throws Exception
     */
    private JSONArray qryInfo(String groupId)
        throws Exception
    {
        IAIMonPInfoSV infoSv = (IAIMonPInfoSV)ServiceFactory.getService(IAIMonPInfoSV.class);
        String[] arr = groupId.split("_");
        groupId = arr[0];
        IBOAIMonPInfoValue[] result = infoSv.getMonPInfoValueByParams(Long.parseLong(groupId), -1, -1, -1);
        IDataBus bus = null;
        if (result == null)
            bus = DataBusHelper.getEmptyArrayDataBus();
        else{
            bus = DataBusHelper.getDataBusByBeans(result, IBOAIMonPInfoValue.class);
            addPInfo(bus.getDataArray());
        }
        return bus.getDataArray();
    }
    
	private static void addPInfo(JSONArray array) {
		if (array == null) {
			return;
		}
		for (Object object : array) {
			try {
				JSONObject jsonObject = (JSONObject) object;
				if (jsonObject.get("thresholdId") instanceof Integer) {
					IAIMonPThresholdSV sv = (IAIMonPThresholdSV) ServiceFactory.getService(IAIMonPThresholdSV.class);
					IBOAIMonThresholdValue result = sv.getBeanById((Integer) jsonObject.get("thresholdId"));
					jsonObject.put("thresholdName", result.getThresholdName());
				}
				if (jsonObject.get("timeId") instanceof Integer) {
					IAIMonPTimeSV sv = (IAIMonPTimeSV) ServiceFactory.getService(IAIMonPTimeSV.class);
					IBOAIMonPTimeValue result = sv.getBeanById((Integer) jsonObject.get("timeId"));
					jsonObject.put("timeName", result.getExpr());
				}
				if (jsonObject.get("typeId") instanceof Integer) {
					IAIMonPExecSV sv = (IAIMonPExecSV) ServiceFactory.getService(IAIMonPExecSV.class);
					IBOAIMonPExecValue result = sv.getBeanById((Integer) jsonObject.get("typeId"));
					jsonObject.put("shellName", result.getName());
				}
			} catch (Exception e) {
			}
		}
	}
	
    
    /**
     * 查询任务详细信息
     * 
     * @param infoId
     * @return
     * @throws Exception
     */
    private JSONObject qryInfoDetail(String infoId)
        throws Exception
    {
        IAIMonPInfoSV infoSv = (IAIMonPInfoSV)ServiceFactory.getService(IAIMonPInfoSV.class);
        String[] arr = infoId.split("_");
        infoId = arr[0];
        IBOAIMonPInfoValue result = infoSv.getMonPInfoValue(Long.parseLong(infoId));
        IDataBus bus = null;
        if (result == null)
            bus = DataBusHelper.getEmptyArrayDataBus();
        else
            bus = DataBusHelper.getDataBusByBeans(result, IBOAIMonPInfoValue.class);
        return bus.getDataObject();
    }
    
    /**
     * 查询任务详细参数信息
     * 
     * @param infoId
     * @return
     * @throws RemoteException
     * @throws Exception
     */
    private JSONArray qryParam(String infoId)
        throws RemoteException, Exception
    {
        IAIMonParamValuesSV paramSv = (IAIMonParamValuesSV)ServiceFactory.getService(IAIMonParamValuesSV.class);
        String[] arr = infoId.split("_");
        infoId = arr[0];
        IBOAIMonParamValuesValue[] result = paramSv.getParamValuesByType("10", infoId);
        IDataBus bus = null;
        if (result == null)
            bus = DataBusHelper.getEmptyArrayDataBus();
        else
            bus = DataBusHelper.getDataBusByBeans(result, IBOAIMonParamValuesValue.class);
        return bus.getDataArray();
    }
    
    public void auto_getMonPInfoGroupById_groupUpdateOnclick(IRequestCycle cycle)
        throws Exception
    {
        long groupId = Long.parseLong(getContext().getParameter("groupId"));
        IAIMonPInfoGroupSV sv =
            (IAIMonPInfoGroupSV)ServiceFactory.getService(IAIMonPInfoGroupSV.class);
        IBOAIMonPInfoGroupValue result = sv.getMonPInfoGroupById(groupId);
        IDataBus bus = null;
        if (result == null)
            bus = DataBusHelper.getEmptyDataBus();
        else
            bus =
                DataBusHelper.getDataBusByBeans(result,
                    IBOAIMonPInfoGroupValue.class);
        
        /* setGroupInput(bus.getDataObject()); */
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("groupInput", bus.getDataObject());
        this.setAjax(jsonObject);
    }
    
    public void auto_delete_groupDeleteOnclick(IRequestCycle cycle)
        throws Exception
    {
        
        Map<String, String> retMap = new HashMap<String, String>();
        
        long groupId = Long.parseLong(getContext().getParameter("groupId"));
        IAIMonPInfoGroupSV sv =
            (IAIMonPInfoGroupSV)ServiceFactory.getService(IAIMonPInfoGroupSV.class);
        JSONObject jsonObj = new JSONObject();
        try
        {
            sv.delete(groupId);
        }
        catch (Exception e)
        {
            retMap.put("DEL_FLAG", ResultConstants.ResultCodeValue.FAILED);
            retMap.put("msg", e.getMessage());
            LOGGER.error("del pinfo exception," + e);
        }
        finally
        {
            IBOAIMonPInfoGroupValue value = sv.getMonPInfoGroupById(groupId);
            OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG,
                CommonConstants.OPERATE_TYPE_DELETE,
                CommonConstants.OPERTATE_OBJECT_MONITOR_GROUP,
                value.getGroupName() + ":" + value.getGroupId(),
                null);
        }
        
        jsonObj.put(ResultConstants.ResultKey.RESULT_CODE, retMap.get("DEL_FLAG"));
        jsonObj.put(ResultConstants.ResultKey.RESULT_MSG, retMap.get("msg"));
        
        this.setAjax(jsonObj);
    }
    
    public void auto_saveOrUpdate_groupDataSaveOnclick(IRequestCycle cycle)
        throws Exception
    {
        Map<String, String> retMap = new HashMap<String, String>();
        
        getData().put("groupInput", ProcessJsonUtil.removeNullValue(getData().getString("groupInput")));
        IDataBus groupInputBus = createData("groupInput");
        JSONObject groupInputCondition = (JSONObject)groupInputBus.getData();
        IBOAIMonPInfoGroupValue value =
            (IBOAIMonPInfoGroupValue)DataBusHelper.getBOBean(groupInputBus);
        IAIMonPInfoGroupSV sv =
            (IAIMonPInfoGroupSV)ServiceFactory.getService(IAIMonPInfoGroupSV.class);
        JSONObject jsonObj = new JSONObject();
        try
        {
            // 保存监控任务分组信息前进行校验
            boolean b = XssUtil.checkBeforeSave(value, value.getClass(), null);
            if (b)
            {
                sv.saveOrUpdate(value);
            }
            else
            {
                retMap.put("DEL_FLAG", ResultConstants.ResultCodeValue.FAILED);
                retMap.put("msg", ResultConstants.ResultCodeValue.CHECK_FAILED);
            }
        }
        catch (Exception e)
        {
            LOGGER.error("saveOrUpdate pinfo exception, " + e);
            retMap.put("DEL_FLAG", ResultConstants.ResultCodeValue.ERROR);
        }
        
        jsonObj.put(ResultConstants.ResultKey.RESULT_CODE, retMap.get("DEL_FLAG"));
        jsonObj.put(ResultConstants.ResultKey.RESULT_MSG, retMap.get("msg"));
        this.setAjax(jsonObj);
    }
    
    public void auto_delete_infoDeleteOnclick(IRequestCycle cycle)
        throws Exception
    {
        
        Map<String, String> retMap = new HashMap<String, String>();
        long infoId = Long.parseLong(getContext().getParameter("infoId"));
        IAIMonPInfoSV sv =
            (IAIMonPInfoSV)ServiceFactory.getService(IAIMonPInfoSV.class);
        JSONObject jsonObj = new JSONObject();
        try
        {
            sv.delete(infoId);
        }
        catch (Exception e)
        {
            retMap.put("DEL_FLAG", ResultConstants.ResultCodeValue.ERROR);
            LOGGER.error("del info exception," + e);
        }
        finally
        {
            IBOAIMonPInfoValue value = sv.getMonPInfoValue(infoId);
            OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG,
                CommonConstants.OPERATE_TYPE_DELETE,
                CommonConstants.OPERTATE_OBJECT_MONITOR_TASK,
                value.getName() + ":" + infoId,
                null);
        }
        
        jsonObj.put(ResultConstants.ResultKey.RESULT_CODE, retMap.get("DEL_FLAG"));
        jsonObj.put(ResultConstants.ResultKey.RESULT_MSG, retMap.get("msg"));
        this.setAjax(jsonObj);
    }
    
    public void auto_getMonPInfoValue_infoUpdateOnclick(IRequestCycle cycle)
        throws Exception
    {
        long infoId = Long.parseLong(getContext().getParameter("infoId"));
        IAIMonPInfoSV sv =
            (IAIMonPInfoSV)ServiceFactory.getService(IAIMonPInfoSV.class);
        IBOAIMonPInfoValue result = sv.getMonPInfoValue(infoId);
        IDataBus bus = null;
        if (result == null)
            bus = DataBusHelper.getEmptyDataBus();
        else
            bus =
                DataBusHelper.getDataBusByBeans(result,
                    IBOAIMonPInfoValue.class);
        
        /* setInfoDataInput(bus.getDataObject()); */
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("infoDataInput", bus.getDataObject());
        this.setAjax(jsonObject);
    }
    
    public void auto_saveOrUpdate_infoDataSaveOnclick(IRequestCycle cycle)
        throws Exception
    {
        Map<String, String> retMap = new HashMap<String, String>();
        
        getData().put("infoDataInput", ProcessJsonUtil.removeNullValue(getData().getString("infoDataInput")));
        IDataBus infoDataInputBus = createData("infoDataInput");
        String treeNodeId = getContext().getParameter("treeNodeId");
        JSONObject infoDataInputCondition = (JSONObject)infoDataInputBus.getData();
        IBOAIMonPInfoValue value =
            (IBOAIMonPInfoValue)DataBusHelper.getBOBean(infoDataInputBus);
        IAIMonPInfoSV sv =
            (IAIMonPInfoSV)ServiceFactory.getService(IAIMonPInfoSV.class);
        // 如果没有分组ID则说明是新增。新增任务的分组默认当前分组,故取节点ID的前半部分,即分组ID,插入数据
        if (value.getGroupId() == 0)
        {
            String groupIdStr = treeNodeId.split("_")[0];
            value.setGroupId(Long.parseLong(groupIdStr));
            // 新增时添加此字段值 默认为1
            value.setSplitRuleId(1);
        }
        JSONObject jsonObj = new JSONObject();
        try
        {
            // 保存前进行校验
            boolean b = XssUtil.checkBeforeSave(value, value.getClass(), null);
            if (b)
            {
                sv.saveOrUpdate(value);
            }
            else
            {
                retMap.put("DEL_FLAG", ResultConstants.ResultCodeValue.FAILED);
                retMap.put("msg", ResultConstants.ResultCodeValue.CHECK_FAILED);
            }
            
        }
        catch (Exception e)
        {
            retMap.put("DEL_FLAG", ResultConstants.ResultCodeValue.ERROR);
            LOGGER.error("save or update infodate exception, " + e);
        }
        
        jsonObj.put(ResultConstants.ResultKey.RESULT_CODE, retMap.get("DEL_FLAG"));
        jsonObj.put(ResultConstants.ResultKey.RESULT_MSG, retMap.get("msg"));
        this.setAjax(jsonObj);
    }
    
    public abstract void setInfoDetail(JSONObject infoDetail);
    
    public abstract void setGroupInfosItem(JSONObject groupInfosItem);
    
    public abstract void setGroupInfosRowIndex(int groupInfosRowIndex);
    
    public abstract void setInfosItems(JSONArray infosItems);
    
    public abstract void setParamInfosRowIndex(int paramInfosRowIndex);
    
    public abstract void setInfoDataInput(JSONObject infoDataInput);
    
    public abstract void setGroupInfosCount(long groupInfosCount);
    
    public abstract void setGroupInfosItems(JSONArray groupInfosItems);
    
    public abstract void setInfosItem(JSONObject infosItem);
    
    public abstract void setParamInfosCount(long paramInfosCount);
    
    public abstract void setInfosCount(long infosCount);
    
    public abstract void setInfosRowIndex(int infosRowIndex);
    
    public abstract void setParamInfosItem(JSONObject paramInfosItem);
    
    public abstract void setGroupInput(JSONObject groupInput);
    
    public abstract void setParamInfosItems(JSONArray paramInfosItems);
    
    @Override
    protected void initPageAttrs()
    {
        bindBoName("groupInfos", "com.asiainfo.monitor.busi.exe.task.bo.BOAIMonPInfoGroup");
        
        bindBoName("infos", "com.asiainfo.monitor.busi.exe.task.bo.BOAIMonPInfo");
        
        bindBoName("infoDetail", "com.asiainfo.monitor.busi.exe.task.bo.BOAIMonPInfo");
        
        bindBoName("paramInfos", "com.asiainfo.monitor.exeframe.config.bo.BOAIMonParamValues");
        
        bindBoName("groupInput", "com.asiainfo.monitor.busi.exe.task.bo.BOAIMonPInfoGroup");
        
        bindBoName("infoDataInput", "com.asiainfo.monitor.busi.exe.task.bo.BOAIMonPInfo");
        
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        IDataBus infoDetail = createData("infoDetail");
        if (infoDetail != null && !infoDetail.getDataObject().isEmpty())
        {
            setInfoDetail(infoDetail.getDataObject());
        }
        IDataBus groupInfosItem = createData("groupInfosItem");
        if (groupInfosItem != null && !groupInfosItem.getDataObject().isEmpty())
        {
            setGroupInfosItem(groupInfosItem.getDataObject());
        }
        IDataBus infoDataInput = createData("infoDataInput");
        if (infoDataInput != null && !infoDataInput.getDataObject().isEmpty())
        {
            setInfoDataInput(infoDataInput.getDataObject());
        }
        IDataBus infosItem = createData("infosItem");
        if (infosItem != null && !infosItem.getDataObject().isEmpty())
        {
            setInfosItem(infosItem.getDataObject());
        }
        IDataBus paramInfosItem = createData("paramInfosItem");
        if (paramInfosItem != null && !paramInfosItem.getDataObject().isEmpty())
        {
            setParamInfosItem(paramInfosItem.getDataObject());
        }
        IDataBus groupInput = createData("groupInput");
        if (groupInput != null && !groupInput.getDataObject().isEmpty())
        {
            setGroupInput(groupInput.getDataObject());
        }
        initExtend(cycle);
    }
    
}
