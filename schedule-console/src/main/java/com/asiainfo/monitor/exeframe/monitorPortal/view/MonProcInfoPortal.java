package com.asiainfo.monitor.exeframe.monitorPortal.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tapestry.IRequestCycle;

import com.ai.appframe2.privilege.UserInfoInterface;
import com.ai.appframe2.service.ServiceFactory;
import com.ailk.appengine.web.view.AppPage;
import com.ailk.common.data.IData;
import com.ailk.common.data.IDataBus;
import com.ailk.common.data.impl.TreeBean;
import com.ailk.common.data.impl.TreeHelper;
import com.ailk.common.data.impl.TreeItem;
import com.ailk.web.view.component.tree.TreeFactory;
import com.ailk.web.view.component.tree.TreeParam;
import com.asiainfo.common.utils.DateUtil;
import com.asiainfo.common.utils.PagingUtil;
import com.asiainfo.monitor.acquire.service.interfaces.IBusiProcessSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonGroupHostRelSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonGroupSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonPhysicHostSV;
import com.asiainfo.monitor.common.CommonConst;
import com.asiainfo.monitor.common.CommonSvUtil;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonGroupValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonPhysicHostValue;
import com.asiainfo.monitor.exeframe.loginfomgr.abo.ivalues.IBOABGMonHostLogValue;
import com.asiainfo.monitor.exeframe.loginfomgr.service.interfaces.IABGMonHostLogSV;
import com.asiainfo.monitor.exeframe.monitorItem.view.WarningInfo;
import com.asiainfo.schedule.core.data.DataManagerFactory;
import com.asiainfo.schedule.core.po.TaskItemJobBean;
import com.asiainfo.schedule.service.interfaces.ISchedulerSV;

/**
 * Created by SMT Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
public abstract class MonProcInfoPortal extends AppPage
{
    
    private static final Log LOG = LogFactory.getLog(MonProcInfoPortal.class);
    
    private void initExtend(IRequestCycle cycle)
    {
        
    }
    
    private void initExtend()
    {
        
    }
    
    /**
     * 加载组织树
     * 
     * @param request
     * @throws Exception
     */
    public void loadTreeData(IRequestCycle request)
        throws Exception
    {
        // 获取角色标识
        TreeParam param = TreeParam.getTreeParam(request);
        
        // 同步全量加载树--查询到主机层级
        TreeBean[] treeBeanArr = CommonSvUtil.getTreeItems(3);
        
        TreeItem[] root = TreeHelper.getWholeTreeData(treeBeanArr);
        IData treeNodes = TreeFactory.buildTreeData(param, root);
        
        getContext().setAjax(treeNodes);
    }
    
    // 判断主机是否联通
    public static boolean pingIp(String hostIp)
    {
        /* return InetAddress.getByName(hostIp).isReachable(2000); */
        /**
         * 每台被监控主机上都会有一个监控代理，通过连接监控代理的tcp协议代替ping的icmp协议 来测试连通性 （不可达时，可能是由于监控代理挂掉，或者是主机挂掉）
         */
        return WarningInfo.pingIp(hostIp);
    }
    
    public static void main(String[] args)
        throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        String s = StringUtils.EMPTY;
        while (!"q".equalsIgnoreCase((s = br.readLine())))
        {
            System.out.println(pingIp(s));
        }
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
        String treeNodeId = getContext().getParameter("treeNodeId");
        String level = getContext().getParameter("level");
        
        if (level.equals(CommonConst.SYS_LEVEL))
        {
            IAIMonGroupSV groupSv = (IAIMonGroupSV)ServiceFactory.getService(IAIMonGroupSV.class);
            IAIMonPhysicHostSV hostSv = (IAIMonPhysicHostSV)ServiceFactory.getService(IAIMonPhysicHostSV.class);
            IAIMonGroupHostRelSV ghSv = (IAIMonGroupHostRelSV)ServiceFactory.getService(IAIMonGroupHostRelSV.class);
            
            // IBOAIMonGroupValue[] grpResult = groupSv.getAllGroupBean();
            // 获取当前用户信息
            UserInfoInterface userInfo = CommonSvUtil.getUserInfo();
            IBOAIMonGroupValue[] grpResult = null;
            // 当用户信息不为空的时候，获取分组信息
            if (userInfo != null)
            {
                grpResult = groupSv.getGroupByPriv(String.valueOf(userInfo.getID()) + "_" + userInfo.getCode());
            }
            StringBuffer groupHostStr = new StringBuffer();
            // 分组与分组之间用“|”隔开，分组名与主机信息之间用“#”隔开，形如：分组一#主机一信息#主机二信息 | 分组二#主机一信息#主机二信息
            for (int i = 0; i < grpResult.length; i++)
            {
                String groupId = Long.toString(grpResult[i].getGroupId());
                String groupName = grpResult[i].getGroupName();
                
                List list = ghSv.getHostListByGroupId(groupId);
                
                IBOAIMonPhysicHostValue[] hostResult = null;
                groupHostStr.append(groupName + ":");
                
                if (list.size() > 0)
                {
                    hostResult = hostSv.qryByList(list);
                    
                    for (int j = 0; j < hostResult.length; j++)
                    {
                        String hostName = hostResult[j].getHostName();
                        String hostIp = hostResult[j].getHostIp();
                        if (pingIp(hostIp))
                        {
                            groupHostStr.append("#" + hostName + "(" + hostIp + ")" + "@ping");
                        }
                        else
                        {
                            groupHostStr.append("#" + hostName + "(" + hostIp + ")");
                        }
                        
                    }
                }
                if (i < (grpResult.length - 1))
                {
                    groupHostStr.append("|");
                }
            }
            String str = groupHostStr.toString();
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("grpHostStr", str);
            this.setAjax(jsonObj);
            
        }
        // 点击分组，获取主机标识以及Ip
        else if (level.equals(CommonConst.GROUP_LEVEL))
        {
            IBOAIMonPhysicHostValue[] result = this.qryGrpHostInfo(treeNodeId);
            if (null != result && result.length > 0)
            {
                StringBuffer str = new StringBuffer();
                for (int i = 0; i < result.length; i++)
                {
                    String hostId = Long.toString(result[i].getHostId());
                    str.append(hostId);
                    if (i < result.length - 1)
                    {
                        str.append(",");
                    }
                }
                str.append("|");
                for (int i = 0; i < result.length; i++)
                {
                    String hostIp = result[i].getHostIp();
                    str.append(hostIp);
                    if (i < result.length - 1)
                    {
                        str.append(",");
                    }
                }
                // 该分组下所有主机的标识，中间用逗号隔开,"|"后加的是主机Ip，也用逗号隔开
                String info = str.toString();
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("grpHostId", info);
                this.setAjax(jsonObj);
            }
            else
            {
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("grpHostId", "");
                this.setAjax(jsonObj);
            }
        }
        else if (level.equals(CommonConst.HOST_LEVEL))
        {
            String hostId = treeNodeId.split("_")[0];
            IAIMonPhysicHostSV hostSv = (IAIMonPhysicHostSV)ServiceFactory.getService(IAIMonPhysicHostSV.class);
            IBOAIMonPhysicHostValue hostBean = hostSv.getPhysicHostById(hostId);
            JSONObject obj = new JSONObject();
            obj.put("hostCode", hostBean.getHostCode());
            obj.put("hostName", hostBean.getHostName());
            obj.put("hostIp", hostBean.getHostIp());
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("hostInfo", obj);
            this.setAjax(jsonObj);
        }
        
    }
    
    /**
     * 通过组表识查询该组下的所有主机
     * 
     * @param request
     * @throws Exception
     */
    private IBOAIMonPhysicHostValue[] qryGrpHostInfo(String treeNodeId)
        throws Exception
    {
        String[] arr = treeNodeId.split("_");
        String groupId = arr[0];
        IAIMonPhysicHostSV hostSv = (IAIMonPhysicHostSV)ServiceFactory.getService(IAIMonPhysicHostSV.class);
        IAIMonGroupHostRelSV ghSv = (IAIMonGroupHostRelSV)ServiceFactory.getService(IAIMonGroupHostRelSV.class);
        List list = ghSv.getHostListByGroupId(groupId);
        IBOAIMonPhysicHostValue[] result = null;
        if (list.size() > 0)
        {
            result = hostSv.qryByList(list);
        }
        return result;
    }
    
    public String qryHostIpByHostId(String hostId)
        throws Exception
    {
        IAIMonPhysicHostSV hostSv = (IAIMonPhysicHostSV)ServiceFactory.getService(IAIMonPhysicHostSV.class);
        IBOAIMonPhysicHostValue hostBean = hostSv.getPhysicHostById(hostId);
        String hostIp = hostBean.getHostIp();
        return hostIp;
    }
    
    /**
     * 查询主机KPI数据
     * 
     * @param request
     * @throws Exception
     */
    public void loadKpiData(IRequestCycle request)
        throws Exception
    {
        String treeNodeId = getContext().getParameter("treeNodeId");
        String level = getContext().getParameter("level");
        
        if (level.equals(CommonConst.SYS_LEVEL))
        {
            
        }
        else if (level.equals(CommonConst.GROUP_LEVEL))
        {
            IBOAIMonPhysicHostValue[] result = this.qryGrpHostInfo(treeNodeId);
            JSONArray jsonKpiArr = new JSONArray();
            JSONArray arr = new JSONArray();
            IBusiProcessSV sv = (IBusiProcessSV)ServiceFactory.getService(IBusiProcessSV.class);
            for (int i = 0; i < result.length; i++)
            {
                String hostId = Long.toString(result[i].getHostId());
                String hostIp = this.qryHostIpByHostId(hostId);
                if (pingIp(hostIp))
                {
                    jsonKpiArr.add(sv.acqHostKpiInfo(hostId));
                }
                else
                {
                    jsonKpiArr.add(arr);
                }
            }
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("kpiInfo", jsonKpiArr);
            this.setAjax(jsonObj);
        }
        
        else if (level.equals(CommonConst.HOST_LEVEL))
        {
            String[] arr = treeNodeId.split("_");
            String hostId = arr[0];
            String hostIp = this.qryHostIpByHostId(hostId);
            JSONArray jsonKpiArr = new JSONArray();
            JSONObject jsonObj = new JSONObject();
            if (pingIp(hostIp))
            {
                jsonKpiArr = this.acqHostKpiInfo(treeNodeId);
                jsonObj.put("kpiInfo", jsonKpiArr);
            }
            else
            {
                jsonObj.put("kpiInfo", "N");
            }
            this.setAjax(jsonObj);
        }
        
    }
    
    /**
     * 通过主机标识获取主机kpi信息
     * 
     * @param request
     * @throws Exception
     */
    private JSONArray acqHostKpiInfo(String treeNodeId)
        throws Exception
    {
        IBusiProcessSV sv = (IBusiProcessSV)ServiceFactory.getService(IBusiProcessSV.class);
        String[] arr = treeNodeId.split("_");
        String hostId = arr[0];
        JSONArray jsonKpiArr = sv.acqHostKpiInfo(hostId);
        return jsonKpiArr;
    }
    
    /**
     * 初始化kpi信息
     * 
     * @param request
     * @throws Exception
     */
    public void loadKpiInitData(IRequestCycle request)
        throws Exception
    {
        String treeNodeId = getContext().getParameter("treeNodeId");
        String level = getContext().getParameter("level");
        
        if (level.equals(CommonConst.SYS_LEVEL))
        {
            
        }
        else if (level.equals(CommonConst.GROUP_LEVEL))
        {
            
        }
        else if (level.equals(CommonConst.HOST_LEVEL))
        {
            // 获取的结果集倒叙排一下，取第一个值为文件系统使用率的信息进行表格的展示
            IBOABGMonHostLogValue[] values = this.qryHisHostKpiInit(treeNodeId);
            StringBuffer cpuStr = new StringBuffer();
            StringBuffer memStr = new StringBuffer();
            StringBuffer fsStr = new StringBuffer();
            StringBuffer xStr = new StringBuffer();
            String path = "/";
			if (values != null && values.length > 0) {
				path = getFsRootPath(values[0].getHostIp());
			}
            for (int i = 0; i < values.length; i++)
            {
                cpuStr.append(values[i].getKpiCpu());
                memStr.append(values[i].getKpiMem());
                String fsInfo = values[i].getKpiFs();
                boolean isNumStr = this.isNumStr(fsInfo);
                if (isNumStr)
                {
                    fsStr.append(values[i].getKpiFs());
                }
                else
                {
                    if (StringUtils.isNotBlank(fsInfo))
                    {
                        String[] strFs = fsInfo.split("#");
                        if (strFs != null && strFs.length > 0)
                        {
                            for (int m = 0; m < strFs.length; m++)
                            {
                                String strDetail = strFs[m];
                                String[] arrDetail = strDetail.split("\\|");
                                if (arrDetail != null && arrDetail.length == 3)
                                {
                                    if (path.equals(arrDetail[0]))
                                    {
                                        String percent = arrDetail[2];
                                        fsStr.append(percent.split("%")[0]);
                                    }
                                }
                            }
                        }
                    }
                }
                xStr.append(DateUtil.date2String(new Date(values[i].getAcqDate().getTime()), "HH:mm"));
                
                if (i < (values.length - 1))
                {
                    cpuStr.append(",");
                    memStr.append(",");
                    fsStr.append(",");
                    xStr.append(",");
                }
            }
            String str =
                cpuStr.append("|").append(memStr).append("|").append(fsStr).append("|").append(xStr).toString();
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("kpiHis", str);
            
            this.setAjax(jsonObj);
        }
        
    }
    
    /**
     * 查询进程数据
     * 
     * @param request
     * @throws Exception
     */
    public void loadProcessData(IRequestCycle request)
        throws Exception
    {
        // 分页使用
        String offset = getContext().getParameter("offset");
        String linage = getContext().getParameter("linage");
        
        String treeNodeId = getContext().getParameter("treeNodeId");
        String level = getContext().getParameter("level");
        
        // 查询分组
        if (level.equals(CommonConst.SYS_LEVEL))
        {
            
        }
        // 查询主机
        else if (level.equals(CommonConst.GROUP_LEVEL))
        {
            
        }
        else if (level.equals(CommonConst.HOST_LEVEL))
        {
            
            IBusiProcessSV sv = (IBusiProcessSV)ServiceFactory.getService(IBusiProcessSV.class);
            String[] arr = treeNodeId.split("_");
            String hostId = arr[0];
            String hostIp = this.qryHostIpByHostId(hostId);
            JSONArray jsonProcessArr = new JSONArray();
            if (pingIp(hostIp))
            {
                jsonProcessArr = sv.acqProcessKpiInfo(hostId);
            }
            /* this.setProcessTabItems(jsonProcessArr); */
            
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("processTabItems", PagingUtil.convertArray2Page(jsonProcessArr, offset, linage));
            jsonObject.put("total", jsonProcessArr.size());
            this.setAjax(jsonObject);
        }
        
    }
    
    // 非应用进程信息展示
    public void loadProcKpiData(IRequestCycle request)
        throws Exception
    {
        // 分页使用
        String offset = getContext().getParameter("offset");
        String linage = getContext().getParameter("linage");
        
        String treeNodeId = getContext().getParameter("treeNodeId");
        String level = getContext().getParameter("level");
        
        // 查询分组
        if (level.equals(CommonConst.SYS_LEVEL))
        {
            
        }
        // 查询主机
        else if (level.equals(CommonConst.GROUP_LEVEL))
        {
            
        }
        else if (level.equals(CommonConst.HOST_LEVEL))
        {
            
            IBusiProcessSV sv = (IBusiProcessSV)ServiceFactory.getService(IBusiProcessSV.class);
            String[] arr = treeNodeId.split("_");
            String hostId = arr[0];
            String hostIp = this.qryHostIpByHostId(hostId);
            JSONArray jsonProcKpiArr = new JSONArray();
            if (pingIp(hostIp))
            {
                jsonProcKpiArr = sv.acqProcKpiInfo(hostId);
            }
            /* this.setProcKpiTabItems(jsonProcKpiArr); */
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("procKpiTabItems", PagingUtil.convertArray2Page(jsonProcKpiArr, offset, linage));
            jsonObject.put("total", jsonProcKpiArr.size());
            this.setAjax(jsonObject);
        }
        
    }
    
    /**
     * 查询主机kpi历史信息
     */
    public void qryHostKpiHisInfo(IRequestCycle cycle)
        throws Exception
    {
        String treeNodeId = getContext().getParameter("treeNodeId");
        String level = getContext().getParameter("level");
        String[] arr = treeNodeId.split("_");
        String hostId = arr[0];
        
        if (level.equals(CommonConst.SYS_LEVEL))
        {
            
        }
        else if (level.equals(CommonConst.GROUP_LEVEL))
        {
            
        }
        else if (level.equals(CommonConst.HOST_LEVEL))
        {
            IDataBus qryformBus = createData("qryForm");
            JSONObject qryformCondition = (JSONObject)qryformBus.getData();
            String beginDate = (String)qryformCondition.get("beginDate");
            String endDate = (String)qryformCondition.get("endDate");
            JSONObject jsonObj = new JSONObject();
            if (StringUtils.isBlank(beginDate) || StringUtils.isBlank(endDate))
            {
                String str = "|||";
                jsonObj.put("kpiHis", str);
                
                this.setAjax(jsonObj);
            }
            else
            {
                SimpleDateFormat temp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date begin = temp.parse(beginDate);// 将字符串转换成日期类型
                Date end = temp.parse(endDate);
                
                Calendar cal = Calendar.getInstance();
                int month = cal.get(Calendar.MONTH) + 1;
                // 项目开始时间，分表开始建表
                String minDate = "2014-10-01 00:00:00";
                Date min = temp.parse(minDate);
                
                // 获取当前月的最大时间
                Calendar calendar = Calendar.getInstance();
                Date maxDate = new Date();
                calendar.setTime(maxDate);
                int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                calendar.set(Calendar.DAY_OF_MONTH, maxDay);
                calendar.set(Calendar.HOUR_OF_DAY, 23);
                calendar.set(Calendar.MINUTE, 59);
                calendar.set(Calendar.SECOND, 59);
                maxDate.setTime(calendar.getTimeInMillis());
                
                if (begin.compareTo(min) >= 0 && end.compareTo(maxDate) <= 0 && begin.compareTo(end) <= 0)
                {
                    IABGMonHostLogSV logSv = (IABGMonHostLogSV)ServiceFactory.getService(IABGMonHostLogSV.class);
                    IBOABGMonHostLogValue[] values = logSv.qryHisHostKpiInfo(begin, end, hostId, "");
                    
                    StringBuffer cpuStr = new StringBuffer();
                    StringBuffer memStr = new StringBuffer();
                    StringBuffer fsStr = new StringBuffer();
                    StringBuffer xStr = new StringBuffer();
                    
					String path = "/";
					if (values != null && values.length > 0) {
						path = getFsRootPath(values[0].getHostIp());
					}
                    
                    for (int i = 0; i < values.length; i++)
                    {
                        cpuStr.append(values[i].getKpiCpu());
                        memStr.append(values[i].getKpiMem());
                        String fsInfo = values[i].getKpiFs();
                        boolean isNumStr = this.isNumStr(fsInfo);
                        if (isNumStr)
                        {
                            fsStr.append(values[i].getKpiFs());
                        }
                        else
                        {
                            if (StringUtils.isNotBlank(fsInfo))
                            {
                                String[] strFs = fsInfo.split("#");
                                if (strFs != null && strFs.length > 0)
                                {
                                    for (int m = 0; m < strFs.length; m++)
                                    {
                                        String strDetail = strFs[m];
                                        String[] arrDetail = strDetail.split("\\|");
                                        if (arrDetail != null && arrDetail.length == 3)
                                        {
                                            if (path.equals(arrDetail[0]))
                                            {
                                                String percent = arrDetail[2];
                                                fsStr.append(percent.split("%")[0]);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        xStr.append(DateUtil.date2String(new Date(values[i].getAcqDate().getTime()), "MM-dd HH:mm"));
                        if (i < (values.length - 1))
                        {
                            cpuStr.append(",");
                            memStr.append(",");
                            fsStr.append(",");
                            xStr.append(",");
                        }
                    }
                    String str =
                        cpuStr.append("|").append(memStr).append("|").append(fsStr).append("|").append(xStr).toString();
                    
                    jsonObj.put("kpiHis", str);
                    this.setAjax(jsonObj);
                }
                else
                {
                    if (begin.compareTo(min) < 0)
                    {
                        jsonObj.put("kpiHis", "A");
                        this.setAjax(jsonObj);
                    }
                    else
                    {
                        if (end.compareTo(maxDate) > 0)
                        {
                            jsonObj.put("kpiHis", "B");
                            this.setAjax(jsonObj);
                        }
                        else
                        {
                            if (begin.compareTo(end) > 0)
                            {
                                jsonObj.put("kpiHis", "C");
                                this.setAjax(jsonObj);
                            }
                        }
                    }
                }
                
            }
        }
    }
    
    private IBOABGMonHostLogValue[] qryHisHostKpiInit(String treeNodeId)
        throws Exception
    {
        String[] arr = treeNodeId.split("_");
        String hostId = arr[0];
        IABGMonHostLogSV logSv = (IABGMonHostLogSV)ServiceFactory.getService(IABGMonHostLogSV.class);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 6);
        
        Date beginDate = calendar.getTime();
        Date endDate = new Date();
        
        IBOABGMonHostLogValue[] values = logSv.qryHisHostKpiInfo(beginDate, endDate, hostId, "");
        return values;
        
    }
    
    /**
     * 获取动态刷新数据
     * 
     * @param request
     * @throws Exception
     */
    public void loadKpiFreshData(IRequestCycle request)
        throws Exception
    {
        String treeNodeId = getContext().getParameter("treeNodeId");
        String level = getContext().getParameter("level");
        
        if (level.equals(CommonConst.SYS_LEVEL))
        {
            
        }
        else if (level.equals(CommonConst.GROUP_LEVEL))
        {
            IBOAIMonPhysicHostValue[] result = this.qryGrpHostInfo(treeNodeId);
            JSONArray jsonKpiArr = new JSONArray();
            JSONArray arr = new JSONArray();
            IBusiProcessSV sv = (IBusiProcessSV)ServiceFactory.getService(IBusiProcessSV.class);
            for (int i = 0; i < result.length; i++)
            {
                String hostId = Long.toString(result[i].getHostId());
                String hostIp = this.qryHostIpByHostId(hostId);
                String path = getFsRootPath(hostIp);
                if (pingIp(hostIp))
                {
                    IBOABGMonHostLogValue[] values = this.qryHisHostKpiByLog(hostId);
                    JSONArray jsonArr = new JSONArray();
                    for (IBOABGMonHostLogValue value : values)
                    {
                        JSONObject jsonObj = new JSONObject();
                        jsonObj.put("KPI_CPU", value.getKpiCpu());
                        jsonObj.put("KPI_MEM", value.getKpiMem());
                        String fsInfo = value.getKpiFs();
                        boolean isNumStr = this.isNumStr(fsInfo);
                        if (isNumStr)
                        {
                            jsonObj.put("KPI_FILE", value.getKpiFs());
                        }
                        else
                        {
                            if (StringUtils.isNotBlank(fsInfo))
                            {
                                String[] strFs = fsInfo.split("#");
                                if (strFs != null && strFs.length > 0)
                                {
                                    for (int m = 0; m < strFs.length; m++)
                                    {
                                        String strDetail = strFs[m];
                                        String[] arrDetail = strDetail.split("\\|");
                                        if (arrDetail != null && arrDetail.length == 3)
                                        {
                                            if (path.equals(arrDetail[0]))
                                            {
                                                String percent = arrDetail[2];
                                                jsonObj.put("KPI_FILE", percent.split("%")[0]);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        DateUtil.date2String(DateUtil.getNowDate(), "HH:mm");
                        jsonObj.put("KPI_DATE", DateUtil.date2String(new Date(value.getAcqDate().getTime()), "HH:mm"));
                        jsonArr.add(jsonObj);
                    }
                    jsonKpiArr.add(jsonArr);
                }
                else
                {
                    jsonKpiArr.add(arr);
                }
            }
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("kpiInfo", jsonKpiArr);
            this.setAjax(jsonObj);
        }
        else if (level.equals(CommonConst.HOST_LEVEL))
        {
            String[] arr = treeNodeId.split("_");
            String hostId = arr[0];
            IBOABGMonHostLogValue[] values = this.qryHisHostKpiByLog(hostId);
            JSONArray jsonArr = new JSONArray();
			String path = "/";
			if (values != null && values.length > 0) {
				path = getFsRootPath(values[0].getHostIp());
			}
            for (IBOABGMonHostLogValue value : values)
            {
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("KPI_CPU", value.getKpiCpu());
                jsonObj.put("KPI_MEM", value.getKpiMem());
                String fsInfo = value.getKpiFs();
                boolean isNumStr = this.isNumStr(fsInfo);
                if (isNumStr)
                {
                    jsonObj.put("KPI_FILE", value.getKpiFs());
                }
                else
                {
                    if (StringUtils.isNotBlank(fsInfo))
                    {
                        String[] strFs = fsInfo.split("#");
                        if (strFs != null && strFs.length > 0)
                        {
                            for (int m = 0; m < strFs.length; m++)
                            {
                                String strDetail = strFs[m];
                                String[] arrDetail = strDetail.split("\\|");
                                if (arrDetail != null && arrDetail.length == 3)
                                {
                                    if (path.equals(arrDetail[0]))
                                    {
                                        String percent = arrDetail[2];
                                        jsonObj.put("KPI_FILE", percent.split("%")[0]);
                                    }
                                }
                            }
                        }
                    }
                }
                DateUtil.date2String(DateUtil.getNowDate(), "HH:mm");
                jsonObj.put("KPI_DATE", DateUtil.date2String(new Date(value.getAcqDate().getTime()), "HH:mm"));
                jsonArr.add(jsonObj);
            }
            
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("kpiInfo", jsonArr);
            
            this.setAjax(jsonObj);
        }
        
    }
    
    /**
     * 从日志库中取到当前时间半个小时的数据
     * 
     * @param request
     * @throws Exception
     */
    private IBOABGMonHostLogValue[] qryHisHostKpiByLog(String hostId)
        throws Exception
    {
        IABGMonHostLogSV logSv = (IABGMonHostLogSV)ServiceFactory.getService(IABGMonHostLogSV.class);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) - 30);
        
        Date beginDate = calendar.getTime();
        Date endDate = new Date();
        String monFlag = "N";
        IBOABGMonHostLogValue[] values = logSv.qryHisHostKpiInfo(beginDate, endDate, hostId, monFlag);
        
        return values;
        
    }
    
    /**
     * 前台状态翻译
     * 
     * @param type
     * @param key
     * @return
     * @throws Exception
     * @throws RemoteException
     */
    public String doTranslate(String type, String val)
        throws RemoteException, Exception
    {
        String text = null;
        if ("PROC_STATE".equals(type))
        {
            if ("JK".equals(val))
            {
                text = "正常";
            }
            else
            {
                text = "异常";
            }
        }
        return text;
    }
    
    /**
     * 根据serverCode查询任务详情
     * 
     * @param request
     * @throws Exception
     */
    public void loadTaskInfo(IRequestCycle request)
        throws Exception
    {
        // 分页使用
        String offset = getContext().getParameter("offset");
        String linage = getContext().getParameter("linage");
        
        JSONArray jsonArr = new JSONArray();
        String serverCode = getContext().getParameter("serverCode");
        ISchedulerSV sSv = (ISchedulerSV)ServiceFactory.getService(ISchedulerSV.class);
        TaskItemJobBean[] tasks = sSv.getAllTaskItemJobsByServerId(serverCode);
        // 将数组转换成json数组
        if (null != tasks)
        {
            for (int i = 0; i < tasks.length; i++)
            {
                JSONObject obj = new JSONObject();
                TaskItemJobBean taskInfo = tasks[i];
                obj.put("itemJobId", taskInfo.getItemJobId());
                obj.put("priority", taskInfo.getPriority());
                obj.put("serverId", taskInfo.getServerId());
                obj.put("taskCode", taskInfo.getTaskCode());
                obj.put("taskItemId", taskInfo.getTaskItemId());
                obj.put("taskJobId", taskInfo.getTaskJobId());
                obj.put("taskVersion", taskInfo.getTaskVersion());
                jsonArr.add(obj);
            }
        }
        /* this.setTaskTabItems(jsonArr); */
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("taskTabItems", PagingUtil.convertArray2Page(jsonArr, offset, linage));
        jsonObject.put("total", jsonArr.size());
        this.setAjax(jsonObject);
    }
    
    /**
     * 获取文件系统使用率
     * 
     * @param request
     * @throws Exception
     */
    public void loadFsInfo(IRequestCycle request)
        throws Exception
    {
        // 分页使用
        String offset = getContext().getParameter("offset");
        String linage = getContext().getParameter("linage");
        
        String treeNodeId = getContext().getParameter("treeNodeId");
        String[] arr = treeNodeId.split("_");
        String hostId = arr[0];
        IBOABGMonHostLogValue[] values = this.qryFsKpiInfo(hostId);
        String fsInfo = "";
        if (values != null && values.length > 0)
        {
            fsInfo = values[values.length - 1].getKpiFs();
        }
        JSONArray fsArr = new JSONArray();
        if (StringUtils.isNotBlank(fsInfo))
        {
            String[] strFs = fsInfo.split("#");
            if (strFs != null && strFs.length > 0)
            {
                for (int m = 0; m < strFs.length; m++)
                {
                    JSONObject obj = new JSONObject();
                    String strDetail = strFs[m];
                    String[] arrDetail = strDetail.split("\\|");
                    if (arrDetail != null && arrDetail.length == 3)
                    {
                        obj.put("path", arrDetail[0]);
                        obj.put("blocks", arrDetail[1] + "m");
                        obj.put("percent", arrDetail[2]);
                    }
                    fsArr.add(obj);
                }
            }
            /* this.setFsTabItems(fsArr); */
            
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("fsTabItems", PagingUtil.convertArray2Page(fsArr, offset, linage));
            jsonObject.put("total", fsArr.size());
            this.setAjax(jsonObject);
            
        }
    }
    
    /**
     * 获取30分钟之内的kpi信息
     * 
     * @param hostId
     * @return
     * @throws Exception
     */
    private IBOABGMonHostLogValue[] qryFsKpiInfo(String hostId)
        throws Exception
    {
        IABGMonHostLogSV logSv = (IABGMonHostLogSV)ServiceFactory.getService(IABGMonHostLogSV.class);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) - 30);
        
        Date beginDate = calendar.getTime();
        Date endDate = new Date();
        IBOABGMonHostLogValue[] values = logSv.qryHisHostKpiInfo(beginDate, endDate, hostId, "");
        
        return values;
        
    }
    
	private String getFsRootPath(String ip) {
		if (StringUtils.isBlank(ip)) {
			return "/";
		}
		try {
			Map<String,String> pathMap = DataManagerFactory.getDataManager().getScheduleConfig().getHostFsKpiPathMap();
			for(String key : pathMap.keySet()){
				if(key.equals(ip)){
					return pathMap.get(key);
				}
			}

		} catch (Exception e) {
			log.error("FsRootPath err, set /");
		}
		return "/";
	}
    
    /**
     * 判断一个字符串是否是数字
     * 
     * @param str
     * @return
     */
    public boolean isNumStr(String str)
    {
        return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
    }
    
    public abstract void setProcKpiTabItem(JSONObject procKpiTabItem);
    
    public abstract void setProcessTabItem(JSONObject processTabItem);
    
    public abstract void setFsTabRowIndex(int fsTabRowIndex);
    
    public abstract void setTaskTabCount(long taskTabCount);
    
    public abstract void setProcKpiTabCount(long procKpiTabCount);
    
    public abstract void setFsTabItem(JSONObject fsTabItem);
    
    public abstract void setTaskTabItem(JSONObject taskTabItem);
    
    public abstract void setProcessTabCount(long processTabCount);
    
    public abstract void setProcessTabItems(JSONArray processTabItems);
    
    public abstract void setFsTabItems(JSONArray fsTabItems);
    
    public abstract void setProcessTabRowIndex(int processTabRowIndex);
    
    public abstract void setTaskTabRowIndex(int taskTabRowIndex);
    
    public abstract void setFsTabCount(long fsTabCount);
    
    public abstract void setTaskTabItems(JSONArray taskTabItems);
    
    public abstract void setProcKpiTabRowIndex(int procKpiTabRowIndex);
    
    public abstract void setProcKpiTabItems(JSONArray procKpiTabItems);
    
    @Override
    protected void initPageAttrs()
    {
        bindBoName("fsTab", "com.asiainfo.monitor.exeframe.config.bo.BOAIMonCmd");
        
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        IDataBus procKpiTabItem = createData("procKpiTabItem");
        if (procKpiTabItem != null && !procKpiTabItem.getDataObject().isEmpty())
        {
            setProcKpiTabItem(procKpiTabItem.getDataObject());
        }
        IDataBus processTabItem = createData("processTabItem");
        if (processTabItem != null && !processTabItem.getDataObject().isEmpty())
        {
            setProcessTabItem(processTabItem.getDataObject());
        }
        IDataBus fsTabItem = createData("fsTabItem");
        if (fsTabItem != null && !fsTabItem.getDataObject().isEmpty())
        {
            setFsTabItem(fsTabItem.getDataObject());
        }
        IDataBus taskTabItem = createData("taskTabItem");
        if (taskTabItem != null && !taskTabItem.getDataObject().isEmpty())
        {
            setTaskTabItem(taskTabItem.getDataObject());
        }
        initExtend(cycle);
    }
    
}
