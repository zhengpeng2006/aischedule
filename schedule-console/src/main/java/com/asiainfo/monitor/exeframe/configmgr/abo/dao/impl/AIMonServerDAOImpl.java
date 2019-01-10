package com.asiainfo.monitor.exeframe.configmgr.abo.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonNodeSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;
import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonServerBean;
import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonServerEngine;
import com.asiainfo.monitor.exeframe.configmgr.abo.dao.interfaces.IAIMonServerDAO;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonNodeValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonServerValue;
import com.asiainfo.monitor.exeframe.controlmgr.common.UserPrivConst;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.schedule.util.ScheduleConstants;

public class AIMonServerDAOImpl implements IAIMonServerDAO
{

    @Override
	public long getBeanId() throws Exception
    {
        return BOAIMonServerEngine.getNewId().longValue();
    }

    @Override
	public BOAIMonServerBean getBeanById(long id) throws Exception
    {
        return BOAIMonServerEngine.getBean(id);
    }

    @Override
	public void save(BOAIMonServerBean value) throws Exception
    {
        if(value.isNew() && value.getServerId() <= 0)
            value.setServerId(getBeanId());
        BOAIMonServerEngine.save(value);
    }

    private Timestamp nowDate = new Timestamp(System.currentTimeMillis());

    /** 
     * 根据条件查询Server
     * @param cond
     * @param para
     * @return
     * @throws Exception
     */
    @Override
	public IBOAIMonServerValue[] queryServer(String cond, Map para) throws Exception
    {
        return BOAIMonServerEngine.getBeans(cond, para);
    }

    /** 
     * 根据服务应用标识，读取应用服务信�?
     * @param serId
     * @return
     * @throws Exception
     */
    @Override
	public IBOAIMonServerValue getServerByServerId(String serverId) throws Exception
    {
        return BOAIMonServerEngine.getBean(Long.parseLong(serverId));
    }

    /** 
     * 根据应用标识码查询应用信�?
     * @param serverCode
     * @return
     * @throws Exception
     */
    @Override
	public IBOAIMonServerValue getServerByServerCode(String serverCode) throws Exception
    {
        // String condition = IBOAIMonServerValue.S_ServerCode + " = :serverCode ";
        String condition = IBOAIMonServerValue.S_ServerCode + " = :serverCode " + " and state='U'";
        Map parameter = new HashMap();
        parameter.put("serverCode", serverCode);
        IBOAIMonServerValue[] values = BOAIMonServerEngine.getBeans(condition, parameter);
        if(values == null || values.length == 0)
            return null;
        if(values.length > 1)
            throw new Exception(AIMonLocaleFactory.getResource("MOS0000214", serverCode));
        return values[0];
    }

    /** 
     * 根据应用类型查询应用信息
     * @param type
     * @return
     * @throws Exception
     */
    @Override
	public IBOAIMonServerValue[] getServerByTempType(String type) throws Exception
    {
        String condition = IBOAIMonServerValue.S_TempType + " = :type AND " + IBOAIMonServerValue.S_State + "='U' ";
        Map parameter = new HashMap();
        parameter.put("type", type);
        IBOAIMonServerValue[] values = BOAIMonServerEngine.getBeans(condition, parameter);
        if(values == null || values.length == 0)
            return null;
        return values;
    }

    /** 
     * 根据应用的ip和port查找出对应的应用
     * @param ip
     * @param port
     * @return
     * @throws Exception
     */
    @Override
	public IBOAIMonServerValue getServerByIpPort(String ip, String port) throws Exception
    {
        String condition = IBOAIMonServerValue.S_ServerIp + "= :ip AND " + IBOAIMonServerValue.S_ServerPort + "= :port " + " AND "
                + IBOAIMonServerValue.S_State + "='U' ";
        Map parameter = new HashMap();
        parameter.put("ip", ip);
        parameter.put("port", port);
        IBOAIMonServerValue[] values = BOAIMonServerEngine.getBeans(condition, parameter);
        if(values == null || values.length == 0)
            return null;
        return values[0];
    }

    /** 
     * 读取正常的Server
     * @return
     * @throws Exception
     */
    @Override
	public IBOAIMonServerValue[] getAllServer() throws Exception
    {
        return this.queryServer(IBOAIMonServerValue.S_State + "='U'", null);
    }

    /** 
     * 根据主机标识和应用名，读取应用服务信�?
     * @param hostId
     * @param serverName
     * @return
     * @throws Exception
     */
    @Override
	public IBOAIMonServerValue[] getServerByCond(String nodeId, String serverName) throws Exception
    {
        StringBuilder condition = new StringBuilder();
        Map parameter = new HashMap();
        if(StringUtils.isNotBlank(nodeId)) {
            condition.append(IBOAIMonServerValue.S_NodeId).append(" = :nodeId ");
            parameter.put("nodeId", nodeId);
        }
        if(StringUtils.isNotBlank(serverName)) {
            if(StringUtils.isNotBlank(condition.toString())) {
                condition.append(" and ");
            }
            condition.append(IBOAIMonServerValue.S_ServerName).append(" like :name ");
            parameter.put("name", "%" + serverName + "%");
        }
        if(StringUtils.isNotBlank(condition.toString())) {
            condition.append(" and ");
        }
        condition.append(" state = 'U' ");
        return queryServer(condition.toString(), parameter);
    }

    /** 
     * 根据组标识，读取该组包含的具体主机信息
     * @param groupId
     * @return
     * @throws Exception
     */
    //    public IBOAIMonServerValue[] getServerByGroupId(String groupId) throws Exception
    //    {
    //        StringBuilder condition = new StringBuilder();
    //        condition.append(IBOAIMonServerValue.S_HostId).append(" IN (SELECT HOST_ID FROM AI_MON_HOST WHERE GROUP_ID= :GROUPID) ");
    //        Map parameter = new HashMap();
    //        parameter.put("GROUPID", groupId);
    //        if(StringUtils.isNotBlank(condition.toString())) {
    //            condition.append(" and ");
    //        }
    //        condition.append(" state = 'U' ");
    //        return queryServer(condition.toString(), parameter);
    //    }

    /** 
     * 批量保存或修改应用服务器设置
     * @param values
     * @throws Exception
     */
    @Override
	public void saveOrUpdate(IBOAIMonServerValue[] values) throws Exception
    {
        if(values != null && values.length > 0) {
            for(int i = 0; i < values.length; i++) {
                if(values[i].isNew()) {
                    values[i].setServerId(BOAIMonServerEngine.getNewId().longValue());
                }
            }
        }
        else {
            throw new Exception("no data to saveOrUpdate...");
        }
        BOAIMonServerEngine.saveBatch(values);
    }

    /** 
     * 保存或修改应用服务器设置
     * @param value
     * @throws Exception
     */
    @Override
	public long saveOrUpdate(IBOAIMonServerValue value) throws Exception
    {
        if(value.isNew()) {
            value.setServerId(BOAIMonServerEngine.getNewId().longValue());
            value.setCreateDate(nowDate);
            value.setState(UserPrivConst.USER_STATE_U);
        }
        BOAIMonServerEngine.save(value);
        return value.getServerId();
    }

    /** 
     * 删除应用服务器信息
     * @param serverId
     * @throws Exception
     */
    @Override
	public void deleteServer(long serverId) throws Exception
    {
        IBOAIMonServerValue value = BOAIMonServerEngine.getBean(serverId);
        if(null != value) {
            value.setState("E");
            BOAIMonServerEngine.save(value);
        }
    }

    /** 
     * 通过nodeId取出服务节点
     * @param nodeId
     * @throws Exception
     */
    @Override
    public IBOAIMonServerValue[] qryServerInfo(String nodeId) throws Exception
    {
        StringBuilder condition = new StringBuilder("");
        Map parameter = new HashMap();
        long node = Long.parseLong(nodeId);
        if(StringUtils.isNotBlank(nodeId)) {
            condition.append(IBOAIMonServerValue.S_NodeId + "= :nodeId");
            parameter.put("nodeId", node);
        }
        if(StringUtils.isNotBlank(condition.toString())) {
            condition.append(" and ");
        }
        condition.append(" state = 'U' order by ").append(IBOAIMonServerValue.S_ServerId);
        IBOAIMonServerValue[] values = BOAIMonServerEngine.getBeans(condition.toString(), parameter);
        return values;
    }

    @Override
    public IBOAIMonServerValue qryServerByServerId(String serverId) throws Exception
    {
        StringBuilder condition = new StringBuilder();
        Map parameter = new HashMap();
        long serverIdLong = Long.parseLong(serverId);
        if(StringUtils.isNotBlank(serverId)) {
            condition.append(IBOAIMonServerValue.S_ServerId).append(" = :serverId ");
            parameter.put("serverId", serverIdLong);
        }
        if(StringUtils.isNotBlank(condition.toString())) {
            condition.append(" and ");
        }
        condition.append(" state = 'U' ");
        IBOAIMonServerValue[] values = BOAIMonServerEngine.getBeans(condition.toString(), parameter);
        if(values.length > 0) {
            return values[0];
        }
        return null;
    }

    @Override
    public boolean isExistServerByCode(String serverCode) throws Exception
    {
        StringBuilder condition = new StringBuilder("");
        Map parameter = new HashMap();
        if(StringUtils.isNotBlank(serverCode)) {
            condition.append(IBOAIMonServerValue.S_ServerCode + "= :serverCode");
            parameter.put("serverCode", serverCode);
        }
        if(StringUtils.isNotBlank(condition.toString())) {
            condition.append(" and ");
        }
        condition.append(" state = 'U' ");
        int count = BOAIMonServerEngine.getBeansCount(condition.toString(), parameter);
        if(count > 0) {
            return true;
        }
        return false;

    }

    @Override
    public IBOAIMonServerValue qryServerByServerCode(String serverCode) throws Exception
    {
        StringBuilder condition = new StringBuilder("");
        Map parameter = new HashMap();

        if(StringUtils.isNotBlank(serverCode)) {
            condition.append(IBOAIMonServerValue.S_ServerCode + "= :serverCode");
            parameter.put("serverCode", serverCode);
        }
        if(StringUtils.isNotBlank(condition.toString())) {
            condition.append(" and ");
        }
        condition.append(" state = 'U' ");
        IBOAIMonServerValue[] values = BOAIMonServerEngine.getBeans(condition.toString(), parameter);
        if(values.length > 0) {
            return values[0];
        }
        return null;

    }

    /**
     * 通过应用编码查询主机标识
     * 
     * @param request
     * @throws Exception
     */
    @Override
    public String qryHostIdByServerCode(String serverCode) throws Exception
    {
        IAIMonServerSV serverSv = (IAIMonServerSV) ServiceFactory.getService(IAIMonServerSV.class);
        IBOAIMonServerValue serverValue = serverSv.qryServerByServerCode(serverCode);
        if(null != serverValue) {
            String nodeId = Long.toString(serverValue.getNodeId());
            IAIMonNodeSV nodeSv = (IAIMonNodeSV) ServiceFactory.getService(IAIMonNodeSV.class);
            IBOAIMonNodeValue nodeValue = nodeSv.qryNodeInfoByNodeId(nodeId);
            return Long.toString(nodeValue.getHostId());
        }
        return null;

    }

	@Override
	public JSONArray qryServerByConditions(Map<String, String> map)
			throws Exception {
		StringBuffer sql = new StringBuffer();
		JSONArray array = new JSONArray();
		Map<String,String> parameter = new HashMap<String, String>();
		sql.append("SELECT S.SERVER_CODE,G.GROUP_NAME,N.NODE_NAME,H.HOST_NAME,H.HOST_IP,H.HOST_ID FROM AI_MON_GROUP G,AI_MON_GROUP_HOST_REL GHR,AI_MON_PHYSIC_HOST H,AI_MON_NODE N,AI_MON_SERVER S ")
		.append("WHERE G.GROUP_ID=GHR.GROUP_ID AND GHR.HOST_ID=H.HOST_ID AND H.HOST_ID=N.HOST_ID AND N.NODE_ID=S.NODE_ID and G.STATE='U' AND H.STATE='U' AND N.STATE='U' AND S.STATE='U' ");
		if(StringUtils.isNotBlank(map.get(ScheduleConstants.PARAM_HOST_GROUP)))
		{
			sql.append("AND G.GROUP_ID = :groupId ");
			parameter.put("groupId", map.get(ScheduleConstants.PARAM_HOST_GROUP));
		}
		if(StringUtils.isNotBlank(map.get(ScheduleConstants.PARAM_HOST_NAME)))
		{
			sql.append("AND H.HOST_NAME LIKE :hostName ");
			parameter.put("hostName", "%"+map.get(ScheduleConstants.PARAM_HOST_NAME)+"%");
		}if(StringUtils.isNotBlank(map.get(ScheduleConstants.PARAM_HOST_IP)))
		{
			sql.append("AND H.HOST_IP = :hostIp ");
			parameter.put("hostIp", map.get(ScheduleConstants.PARAM_HOST_IP));
		}if(StringUtils.isNotBlank(map.get(ScheduleConstants.PARAM_TASK_CODE)))
		{
			sql.append("AND S.SERVER_CODE like :serverCode ");
			parameter.put("serverCode", "%"+map.get(ScheduleConstants.PARAM_TASK_CODE)+"%");
		}if(StringUtils.isNotBlank(map.get(ScheduleConstants.PARAM_NODE)))
		{
			sql.append("AND N.NODE_NAME LIKE :nodeName ");
			parameter.put("nodeName", "%"+map.get(ScheduleConstants.PARAM_NODE)+"%");
		}if(StringUtils.isNotBlank(map.get(ScheduleConstants.PARAM_HOST_ID)))
		{
			sql.append("AND H.HOST_ID = :hostId ");
			parameter.put("hostId",map.get(ScheduleConstants.PARAM_HOST_ID));
		}
		if(StringUtils.isNotBlank(map.get(ScheduleConstants.PARAM_NODE_ID)))
		{
			sql.append("AND N.NODE_ID = :nodeId ");
			parameter.put("nodeId",map.get(ScheduleConstants.PARAM_NODE_ID));
		}
		Connection conn = null;
	    ResultSet resultset = null;
		try {
		conn = ServiceManager.getSession().getConnection();
		resultset =ServiceManager.getDataStore().retrieve(conn,sql.toString(),parameter);
		while(resultset.next())
		{
			JSONObject obj = new JSONObject();
			obj.put(ScheduleConstants.PARAM_SERVER_CODE,resultset.getString("SERVER_CODE"));
			obj.put(ScheduleConstants.PARAM_HOST_GROUP,resultset.getString("GROUP_NAME"));
			obj.put(ScheduleConstants.PARAM_NODE,resultset.getString("NODE_NAME"));
			obj.put(ScheduleConstants.PARAM_HOST_NAME,resultset.getString("HOST_NAME")+"("+resultset.getString("HOST_IP")+")");
			obj.put(ScheduleConstants.PARAM_HOST_ID,resultset.getString("HOST_ID"));
			array.add(obj);
		}
		}catch(Exception e){
		throw e;
		}finally{
		if(resultset != null)
		    resultset.close();
		if (conn != null)
		    conn.close();
		}
		return array;
	}

	@Override
	public JSONArray qryServersInfoByServerCodes(Set<String> serverCodes) throws Exception{
		StringBuffer sql = new StringBuffer();
		JSONArray array = new JSONArray();
		Map<String,String> parameter = new HashMap<String, String>();
		sql.append("SELECT S.SERVER_CODE,G.GROUP_NAME,N.NODE_NAME,H.HOST_NAME,H.HOST_IP,H.HOST_ID FROM AI_MON_GROUP G,AI_MON_GROUP_HOST_REL GHR,AI_MON_PHYSIC_HOST H,AI_MON_NODE N,AI_MON_SERVER S ")
		.append("WHERE G.GROUP_ID=GHR.GROUP_ID AND GHR.HOST_ID=H.HOST_ID AND H.HOST_ID=N.HOST_ID AND N.NODE_ID=S.NODE_ID and G.STATE='U' AND H.STATE='U' AND N.STATE='U' AND S.STATE='U' ");
		if (null != serverCodes && serverCodes.size() > 0){
			List<String> list = new ArrayList<String>(serverCodes);
			sql.append("AND S.SERVER_CODE IN (");
			for (int i = 0; i < list.size(); i++) {
				sql.append("'");
				sql.append(list.get(i));
				sql.append("'");
				if (i < list.size() - 1){
					sql.append(",");
				}
			}
			sql.append(")");
		}
		Connection conn = null;
	    ResultSet resultset = null;
		try {
		conn = ServiceManager.getSession().getConnection();
		resultset =ServiceManager.getDataStore().retrieve(conn,sql.toString(),parameter);
			while(resultset.next())
			{
				JSONObject obj = new JSONObject();
				obj.put(ScheduleConstants.PARAM_SERVER_CODE,resultset.getString("SERVER_CODE"));
				obj.put(ScheduleConstants.PARAM_HOST_GROUP,resultset.getString("GROUP_NAME"));
				obj.put(ScheduleConstants.PARAM_NODE,resultset.getString("NODE_NAME"));
				obj.put(ScheduleConstants.PARAM_HOST_NAME,resultset.getString("HOST_NAME")+"("+resultset.getString("HOST_IP")+")");
				obj.put(ScheduleConstants.PARAM_HOST_ID,resultset.getString("HOST_ID"));
				array.add(obj);
			}
		}catch(Exception e){
			throw e;
		}finally{
			if(resultset != null)
			    resultset.close();
			if (conn != null)
			    conn.close();
		}
		return array;
	}

	@Override
	public Map<String, Long> getServerHosts() throws Exception {
		Map<String, Long> map = new HashMap<String, Long>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT S.SERVER_CODE,H.HOST_ID FROM AI_MON_SERVER S JOIN AI_MON_NODE N ON S.NODE_ID = N.NODE_ID ")
		.append(" JOIN AI_MON_PHYSIC_HOST H ON N.HOST_ID = H.HOST_ID ")
		.append(" WHERE S.STATE = 'U' AND N.STATE = 'U' AND H.STATE = 'U' ");
		Connection conn = null;
	    ResultSet resultset = null;
		try {
		conn = ServiceManager.getSession().getConnection();
		resultset =ServiceManager.getDataStore().retrieve(conn,sql.toString(),null);
			while(resultset.next())
			{
				map.put(resultset.getString("SERVER_CODE"), resultset.getLong("HOST_ID"));
			}
		}catch(Exception e){
			throw e;
		}finally{
			try {
				if(resultset != null)
				    resultset.close();
				if (conn != null)
				    conn.close();
			} catch (SQLException e) {
				throw e;
			}
		}
		return map;
	}

	@Override
	public JSONObject getServerHostsMap() throws Exception {
		JSONObject result  = new JSONObject();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT H.HOST_ID,S.SERVER_CODE FROM  AI_MON_PHYSIC_HOST H LEFT JOIN AI_MON_NODE N ON N.HOST_ID = H.HOST_ID ")
		.append(" LEFT JOIN AI_MON_SERVER S ON S.NODE_ID = N.NODE_ID ")
		.append(" WHERE S.STATE = 'U' AND N.STATE = 'U' AND H.STATE = 'U' ");
		Connection conn = null;
	    ResultSet resultset = null;
		try {
		conn = ServiceManager.getSession().getConnection();
		resultset =ServiceManager.getDataStore().retrieve(conn,sql.toString(),null);
		JSONArray array =  null;
			while(resultset.next())
			{
				if (result.containsKey(resultset.getString("HOST_ID"))){
					array = result.getJSONArray(String.valueOf((resultset.getString("HOST_ID"))));
					array.add(resultset.getString("SERVER_CODE"));
					result.put(resultset.getLong("HOST_ID"),array);
				}else{
					array = new JSONArray();
					array.add(resultset.getString("SERVER_CODE"));
					result.put(resultset.getString("HOST_ID"),array);
				}
			}
		}catch(Exception e){
			throw e;
		}finally{
			try {
				if(resultset != null)
				    resultset.close();
				if (conn != null)
				    conn.close();
			} catch (SQLException e) {
				throw e;
			}
		}
		return result;
	}

}
