package com.asiainfo.monitor.busi.service.impl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.ai.appframe2.util.criteria.Criteria;
import com.ailk.common.data.IDataBus;
import com.ailk.common.data.IDataContext;
import com.ailk.common.data.impl.DataBus;
import com.ailk.common.data.impl.DataContext;
import com.asiainfo.monitor.busi.cache.AIMonPhostCheckListener;
import com.asiainfo.monitor.busi.cache.MonCacheManager;
import com.asiainfo.monitor.busi.cache.impl.AIMonPhostCacheImpl;
import com.asiainfo.monitor.busi.cache.impl.PhysicHost;
import com.asiainfo.monitor.busi.cache.interfaces.IPhysicHost;
import com.asiainfo.monitor.busi.cache.interfaces.IServerNode;
import com.asiainfo.monitor.busi.common.impl.TopuXmlStrategy;
import com.asiainfo.monitor.busi.common.interfaces.IObjectStrategy;
import com.asiainfo.monitor.busi.config.ServerConfig;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonNodeSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonPhysicHostSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;
import com.asiainfo.monitor.common.CommonConst;
import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonPhysicHostBean;
import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonPhysicHostEngine;
import com.asiainfo.monitor.exeframe.configmgr.abo.dao.interfaces.IAIMonPhysicHostDAO;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonPhysicHostValue;

public class AIMonPhysicHostSVImpl implements IAIMonPhysicHostSV
{

    private static transient Log log = LogFactory.getLog(AIMonPhysicHostSVImpl.class);

    /**
     * 删除物理主机信息
     * 
     * @param hostId
     * @throws Exception
     */
    public void deletePhysicHost(long hostId) throws Exception
    {
        IAIMonPhysicHostDAO hostDao = (IAIMonPhysicHostDAO) ServiceFactory.getService(IAIMonPhysicHostDAO.class);
        hostDao.deletePhysicHost(hostId);
        PhysicHost item = (PhysicHost) MonCacheManager.get(AIMonPhostCacheImpl.class, hostId + "");
        if(item != null)
            item.setEnable(false);
    }

    /**
     * 保存或修改物理主机设置
     * 
     * @param value
     * @throws Exception
     */
    public long saveOrUpdate(IBOAIMonPhysicHostValue value) throws Exception
    {
        boolean modify = value.isModified();
        IAIMonPhysicHostDAO hostDao = (IAIMonPhysicHostDAO) ServiceFactory.getService(IAIMonPhysicHostDAO.class);
        long hostId = hostDao.saveOrUpdate(value);
        if(modify) {
            PhysicHost item = (PhysicHost) MonCacheManager.get(AIMonPhostCacheImpl.class, value.getHostId() + "");
            if(item != null)
                item.setEnable(false);
        } else {
            PhysicHost item = this.wrapperPhysicHostByBean(value);
            MonCacheManager.put(AIMonPhostCacheImpl.class, item.getHostId(), item);
        }
        return hostId;
    }

    /**
     * 根据组标识从数据库读取组信息并封装
     * 
     * @param id
     * @return
     * @throws Exception
     */
    public PhysicHost getPhostByIdFromDb(String id) throws Exception
    {
        if(StringUtils.isBlank(id))
            return null;
        IAIMonPhysicHostDAO phostDAO = (IAIMonPhysicHostDAO) ServiceFactory.getService(IAIMonPhysicHostDAO.class);
        IBOAIMonPhysicHostValue value = phostDAO.getPhysicHostById(id);
        PhysicHost result = this.wrapperPhysicHostByBean(value);
        return result;
    }

    /**
     * 返回所有物理主机信息值对象
     * 
     * @return
     * @throws Exception
     */
    public IBOAIMonPhysicHostValue[] getAllPhostBean() throws Exception
    {
        IAIMonPhysicHostDAO phostDAO = (IAIMonPhysicHostDAO) ServiceFactory.getService(IAIMonPhysicHostDAO.class);
        return phostDAO.getAllPhost();
    }

    /**
     * 批量保存或修改物理主机设置
     * 
     * @param values
     * @throws Exception
     */
    public void saveOrUpdate(IBOAIMonPhysicHostValue[] values) throws Exception
    {
        boolean modify = values[0].isModified();
        IAIMonPhysicHostDAO hostDao = (IAIMonPhysicHostDAO) ServiceFactory.getService(IAIMonPhysicHostDAO.class);
        hostDao.saveOrUpdate(values);
        for(int i = 0; i < values.length; i++) {
            if(modify) {
                PhysicHost item = (PhysicHost) MonCacheManager.get(AIMonPhostCacheImpl.class, values[i].getHostId() + "");
                if(item != null)
                    item.setEnable(false);
            } else {
                PhysicHost item = this.wrapperPhysicHostByBean(values[i]);
                MonCacheManager.put(AIMonPhostCacheImpl.class, item.getHostId(), item);
            }
        }
    }

    /**
     * 根据主机标识，从缓存读取物理主机信息
     * 
     * @param phostId
     * @return
     * @throws RemoteException
     * @throws Exception
     */
    public PhysicHost getPhostByPhostId(String phostId) throws Exception
    {
        PhysicHost result = (PhysicHost) MonCacheManager.get(AIMonPhostCacheImpl.class, phostId);
        if(result == null) {
            // 没有从数据库确认
            result = this.getPhostByIdFromDb(phostId);
            if(!MonCacheManager.getCacheReadOnlySet() && result != null) {
                MonCacheManager.put(AIMonPhostCacheImpl.class, result.getHostId(), result);
            }
        }
        return result;
    }

    /**
     * 返回所有组信息 不考虑缓存未加载情况
     * 
     * @return
     * @throws Exception
     */
    public List getAllPhost() throws Exception
    {
        List result = null;
        HashMap phostMap = MonCacheManager.getAll(AIMonPhostCacheImpl.class);
        if(phostMap != null && phostMap.size() > 0) {
            result = new ArrayList();
            Iterator it = phostMap.entrySet().iterator();
            while(it != null && it.hasNext()) {
                Entry item = (Entry) it.next();
                if(((IPhysicHost) item.getValue()) != null)
                    result.add((IPhysicHost) item.getValue());
            }
        }
        return result;
    }

    /**
     * 根据组标识和用户标识，读取节点拓扑
     * 
     * @param groupId
     * @param userId
     *            ,用户标识暂且不用
     * @return
     * @throws Exception
     */
    public String getHostTopuXmlByGroupIdAndUserId(String groupId, String userId) throws Exception
    {
        StringBuilder result = new StringBuilder("");
        try {
            if(StringUtils.isNotBlank(groupId)) {
                IAIMonNodeSV nodeSV = (IAIMonNodeSV) ServiceFactory.getService(IAIMonNodeSV.class);
                IAIMonServerSV serverSV = (IAIMonServerSV) ServiceFactory.getService(IAIMonServerSV.class);
                List nodeValues = nodeSV.getNodeByGroupId(groupId);
                List hostIds = new ArrayList();
                List hostArray = new ArrayList();
                if(nodeValues != null && nodeValues.size() > 0) {
                    for(int i = 0; i < nodeValues.size(); i++) {
                        List serverArray = serverSV.getAppServerConfigByNodeId(((IServerNode) nodeValues.get(i)).getNode_Id());
                        if(serverArray != null && serverArray.size() > 0) {
                            for(int j = 0; j < serverArray.size(); j++) {
                                if(((ServerConfig) serverArray.get(j)).getPhysicHost() != null) {
                                    if(!hostIds.contains(((ServerConfig) serverArray.get(j)).getPHost_Id())) {
                                        hostIds.add(((ServerConfig) serverArray.get(j)).getPHost_Id());
                                        hostArray.add(((ServerConfig) serverArray.get(j)).getPhysicHost());
                                    }
                                }
                            }
                        }
                    }
                }
                hostIds = null;
                if(hostArray.size() > 0) {
                    result.append("<Graph>");
                    IObjectStrategy strategy = new TopuXmlStrategy();
                    for(int i = 0; i < hostArray.size(); i++) {
                        result.append(strategy.visitHost((IPhysicHost) hostArray.get(i)));
                    }
                    result.append("</Graph>");
                }

            }
        } catch(Exception e) {
            log.error("Call AIMonHostSVImpl's Method getHostTopuXmlByGroupIdAndUserId has Exception :" + e.getMessage());
        }
        return result.toString();
    }

    /**
     * 根据物理主机名称和IP查询主机信息
     * 
     * @param name
     * @param ip
     * @return
     * @throws Exception
     */
    public IBOAIMonPhysicHostValue[] getPhysicHostByNameAndIp(String name, String ip) throws Exception
    {
        IAIMonPhysicHostDAO hostDao = (IAIMonPhysicHostDAO) ServiceFactory.getService(IAIMonPhysicHostDAO.class);
        IBOAIMonPhysicHostValue[] hostValue = hostDao.getPhysicHostByNameAndIp(name, ip);

        /* if(hostValue != null && hostValue.length > 0) {

             for(int i = 0; i < hostValue.length; i++) {
                 String password = hostValue[i].getHostPwd();
                 hostValue[i].setHostPwd(K.k(password));
             }
         }*/

        return hostValue;
    }

    /**
     * 将值对象简单封装
     * 
     * @param value
     * @return
     * @throws Exception
     */
    public PhysicHost wrapperPhysicHostByBean(IBOAIMonPhysicHostValue value) throws Exception
    {
        if(value == null || StringUtils.isBlank(value.getState()) || value.getState().equals("E"))
            return null;
        PhysicHost result = new PhysicHost();
        result.setHostId(value.getHostId() + "");
        result.setHostCode(value.getHostCode());
        result.setHostName(value.getHostName());
        result.setHostDesc(value.getHostDesc());
        //result.setAttendMode(value.getAttendMode());
        result.setHostIp(value.getHostIp());
        // result.setHostPort(value.getHostPort() + "");
        // result.setHostUser(value.getHostUser());
        //  result.setHostPwd(value.getHostPwd());
        result.setRemarks(value.getRemarks());
        result.setState(value.getState());
        result.setCacheListener(new AIMonPhostCheckListener());
        return result;
    }

    /**
     * 根据物理主机名称查询主机信息
     * 
     * @param name
     * @return
     * @throws Exception
     */
    public IBOAIMonPhysicHostValue[] getPhysicHostByName(String name) throws Exception
    {
        IAIMonPhysicHostDAO hostDao = (IAIMonPhysicHostDAO) ServiceFactory.getService(IAIMonPhysicHostDAO.class);
        IBOAIMonPhysicHostValue[] hostValue = hostDao.getPhysicHostByName(name);

        /* if(hostValue != null && hostValue.length > 0) {

             for(int i = 0; i < hostValue.length; i++) {
                 String password = hostValue[i].getHostPwd();
                 hostValue[i].setHostPwd(K.k(password));
             }
         }*/

        return hostValue;
    }

    /**
     * 根据服务器标识，读取物理主机信息
     * 
     * @param HostId
     * @return
     * @throws Exception
     */
    public IBOAIMonPhysicHostValue getPhysicHostById(String hostId) throws Exception
    {
        IAIMonPhysicHostDAO hostDao = (IAIMonPhysicHostDAO) ServiceFactory.getService(IAIMonPhysicHostDAO.class);
        return hostDao.getPhysicHostById(hostId);
    }

    /**
     * 根据主机标识列表查询物理主机信息
     * 
     * @param request
     * @throws Exception
     * @author szh
     */
    @Override
    public IBOAIMonPhysicHostValue[] qryByList(List<String> list) throws Exception
    {
        Criteria sql = new Criteria();
        if(list.size() > 0)
            sql.addIn(IBOAIMonPhysicHostValue.S_HostId, list);

        sql.addEqual(IBOAIMonPhysicHostValue.S_State, CommonConst.STATE_U);
        sql.addAscendingOrderByColumn(IBOAIMonPhysicHostValue.S_HostId);
        return BOAIMonPhysicHostEngine.getBeans(sql);
    }

    /**
     * 查询主机标识列表
     * 
     * @param request
     * @throws Exception
     * @author szh
     */
    @Override
    public IDataBus getHostList() throws Exception
    {
        Criteria sql = new Criteria();
        sql.addEqual(IBOAIMonPhysicHostValue.S_State, "U");
        BOAIMonPhysicHostBean[] result = BOAIMonPhysicHostEngine.getBeans(sql);
        
        IDataContext context = new DataContext();
        JSONArray jsonArray = new JSONArray();
        for(int i = 0; i < result.length; i++) {
            JSONObject obj = new JSONObject();
            String key = result[i].getHostName();
            String hostId = Long.toString(result[i].getHostId());
            obj.put("TEXT", key);
            obj.put("VALUE", hostId);
            jsonArray.add(obj);
        }
        return new DataBus(context, jsonArray);
    }

    /**
     * 判断主机编码是否存在
     * 
     * @param request
     * @throws Exception
     * @author szh
     */
    @Override
    public boolean isExistHostByCode(String groupId, String hostCode) throws Exception
    {
        IAIMonPhysicHostDAO hostDao = (IAIMonPhysicHostDAO) ServiceFactory.getService(IAIMonPhysicHostDAO.class);
        return hostDao.isExistHostByCode(groupId, hostCode);
    }

    /**
     * 判断主机名称是否存在
     * 
     * @param request
     * @throws Exception
     * @author szh
     */
    @Override
    public boolean isExistHostByName(String groupId, String hostName) throws Exception
    {
        IAIMonPhysicHostDAO hostDao = (IAIMonPhysicHostDAO) ServiceFactory.getService(IAIMonPhysicHostDAO.class);
        return hostDao.isExistHostByName(groupId, hostName);
    }

    @Override
    public Map<String, IBOAIMonPhysicHostValue> qryHostByAppCode(List<String> list) throws Exception
    {
        IAIMonPhysicHostDAO hostDao = (IAIMonPhysicHostDAO) ServiceFactory.getService(IAIMonPhysicHostDAO.class);
        return hostDao.qryHostByAppCode(list);
    }

	@Override
	public Map<String, IBOAIMonPhysicHostValue> qryHostInfoByCondition(
			String groupCode, String hostId, String serverCode)  throws  Exception{
		  IAIMonPhysicHostDAO hostDao = (IAIMonPhysicHostDAO) ServiceFactory.getService(IAIMonPhysicHostDAO.class);
	        return hostDao.qryHostInfoByCondition(groupCode,hostId,serverCode) ;
	}

    /**
     * 根据ip查询主机信息
     * 
     * @param request
     * @throws Exception
     */
    @Override
    public IBOAIMonPhysicHostValue[] qryHostInfoByIp(String ip) throws Exception
    {
        IAIMonPhysicHostDAO hostDao = (IAIMonPhysicHostDAO) ServiceFactory.getService(IAIMonPhysicHostDAO.class);
        return hostDao.qryHostInfoByIp(ip);
    }

}
