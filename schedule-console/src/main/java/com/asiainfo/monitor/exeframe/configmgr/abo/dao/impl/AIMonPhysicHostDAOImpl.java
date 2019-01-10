package com.asiainfo.monitor.exeframe.configmgr.abo.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonGroupHostRelSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonPhysicHostSV;
import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonPhysicHostBean;
import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonPhysicHostEngine;
import com.asiainfo.monitor.exeframe.configmgr.abo.dao.interfaces.IAIMonPhysicHostDAO;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonPhysicHostValue;
import com.asiainfo.monitor.exeframe.controlmgr.common.UserPrivConst;

public class AIMonPhysicHostDAOImpl implements IAIMonPhysicHostDAO
{

    public long getBeanId() throws Exception
    {
        return BOAIMonPhysicHostEngine.getNewId().longValue();
    }

    public BOAIMonPhysicHostBean getBeanById(long id) throws Exception
    {
        return BOAIMonPhysicHostEngine.getBean(id);
    }

    public void save(BOAIMonPhysicHostBean value) throws Exception
    {
        if(value.isNew() && value.getHostId() <= 0)
            value.setHostId(getBeanId());
        BOAIMonPhysicHostEngine.save(value);
    }

    private Timestamp nowDate = new Timestamp(System.currentTimeMillis());

    /** 
     * 根据条件查询物理主机
     * @param cond
     * @param para
     * @return
     * @throws Exception
     */
    public IBOAIMonPhysicHostValue[] queryPhysicHost(String cond, Map param) throws Exception
    {
        return BOAIMonPhysicHostEngine.getBeans(cond, param);
    }

    /** 
     * 根据条件查询物理主机信息
     * @param cond
     * @param para
     * @return
     * @throws Exception
     */
    public IBOAIMonPhysicHostValue[] queryPhost(String cond, Map para) throws Exception
    {
        return BOAIMonPhysicHostEngine.getBeans(cond, para);
    }

    /** 
     * 删除物理主机信息
     * @param hostId
     * @throws Exception
     */
    public void deletePhysicHost(long hostId) throws Exception
    {
        IBOAIMonPhysicHostValue value = BOAIMonPhysicHostEngine.getBean(hostId);
        if(null != value) {
            value.setState("E");
            BOAIMonPhysicHostEngine.save(value);
        }
    }

    /** 
     * 保存或修改物理主机
     * @param value
     * @throws Exception
     */
    public long saveOrUpdate(IBOAIMonPhysicHostValue value) throws Exception
    {
        if(value.isNew()) {
            value.setHostId(BOAIMonPhysicHostEngine.getNewId().longValue());
            value.setCreateDate(nowDate);
            value.setState(UserPrivConst.USER_STATE_U);
        }
        long hostId = value.getHostId();
        BOAIMonPhysicHostEngine.save(value);
        return hostId;
    }

    /** 
     * 批量保存或修改物理主机
     * @param values
     * @throws Exception
     */
    public void saveOrUpdate(IBOAIMonPhysicHostValue[] values) throws Exception
    {
        if(values != null && values.length > 0) {
            for(int i = 0; i < values.length; i++) {
                if(values[i].isNew()) {
                    values[i].setHostId(BOAIMonPhysicHostEngine.getNewId().longValue());
                    values[i].setCreateDate(nowDate);
                    values[i].setState(UserPrivConst.USER_STATE_U);
                }
            }
        }
        else {
            throw new Exception("no data to saveOrUpdate...");
        }
        BOAIMonPhysicHostEngine.saveBatch(values);
    }

    public IBOAIMonPhysicHostValue[] getAllPhostBean() throws Exception
    {
        return null;
    }

    /** 
     * 返回物理主机信息
     * @return
     * @throws Exception
     */
    public IBOAIMonPhysicHostValue[] getAllPhost() throws Exception
    {
        return this.queryPhost(IBOAIMonPhysicHostValue.S_State + "='U'"+ " order by " +IBOAIMonPhysicHostValue.S_HostId , null);
    }

    /** 
     * 根据物理主机名称和ip查询主机信息
     * @param name
     * @param ip
     * @return
     * @throws Exception
     */
    public IBOAIMonPhysicHostValue[] getPhysicHostByNameAndIp(String name, String ip) throws Exception
    {
        StringBuilder condition = new StringBuilder("");
        condition.append(IBOAIMonPhysicHostValue.S_State).append(" = 'U'");
        Map param = new HashMap();
        if(StringUtils.isNotBlank(name)) {
            condition.append(" and ");
            condition.append(IBOAIMonPhysicHostValue.S_HostName).append(" like :hostName");
            param.put("hostName", "%" + name + "%");
        }
        if(StringUtils.isNotBlank(ip)) {
            condition.append(" and ");
            condition.append(IBOAIMonPhysicHostValue.S_HostIp).append(" like :hostIp");
            param.put("hostIp", "%" + ip + "%");
        }
        return BOAIMonPhysicHostEngine.getBeans(condition.toString(), param);
    }

    /** 
     * 根据物理主机名称查询主机信息
     * @param name
     * @return
     * @throws Exception
     */
    public IBOAIMonPhysicHostValue[] getPhysicHostByName(String name) throws Exception
    {
        StringBuilder condition = new StringBuilder("");
        condition.append(IBOAIMonPhysicHostValue.S_State).append(" = 'U'");
        if(StringUtils.isNotBlank(name)) {
            condition.append(" and ");
            condition.append(IBOAIMonPhysicHostValue.S_HostName).append(" like :hostName");
        }
        Map param = new HashMap();
        param.put("hostName", "%" + name + "%");
        return BOAIMonPhysicHostEngine.getBeans(condition.toString(), param);
    }

    /** 
     * 根据标识，读取物理主机信息
     * @param hostId
     * @return
     * @throws Exception
     */
    public IBOAIMonPhysicHostValue getPhysicHostById(String hostId) throws Exception
    {
        if(StringUtils.isBlank(hostId)) {
            return null;
        }
        StringBuilder condition = new StringBuilder("");
        Map parameter = new HashMap();
        long hostIdLong = Long.parseLong(hostId);
        condition.append(IBOAIMonPhysicHostValue.S_HostId).append(" = :hostId");
        parameter.put("hostId", hostIdLong);
        if(StringUtils.isNotBlank(condition.toString())) {
            condition.append(" and ");
        }
        condition.append("state='U'");
        IBOAIMonPhysicHostValue[] values = BOAIMonPhysicHostEngine.getBeans(condition.toString(), parameter);
        if(values.length > 0) {
            return values[0];
        }
        return null;
    }

    public static void main(String[] args) throws Exception
    {
        IAIMonPhysicHostSV sv = (IAIMonPhysicHostSV) ServiceFactory.getService(IAIMonPhysicHostSV.class);
        IBOAIMonPhysicHostValue[] values = sv.getPhysicHostByName("");
        System.out.println(values);

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
        IAIMonPhysicHostSV hostSv = (IAIMonPhysicHostSV) ServiceFactory.getService(IAIMonPhysicHostSV.class);
        IAIMonGroupHostRelSV ghSv = (IAIMonGroupHostRelSV) ServiceFactory.getService(IAIMonGroupHostRelSV.class);
        List list = ghSv.getHostListByGroupId(groupId);
        IBOAIMonPhysicHostValue[] result = null;
        if(list.size() > 0) {
            result = hostSv.qryByList(list);
        }
        if(null != result) {
             for(IBOAIMonPhysicHostValue value : result) {
                 if(hostCode.equals(value.getHostCode())) {
                     return true;
                 }
             }
        }
        return false;

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
        IAIMonPhysicHostSV hostSv = (IAIMonPhysicHostSV) ServiceFactory.getService(IAIMonPhysicHostSV.class);
        IAIMonGroupHostRelSV ghSv = (IAIMonGroupHostRelSV) ServiceFactory.getService(IAIMonGroupHostRelSV.class);
        List list = ghSv.getHostListByGroupId(groupId);
        IBOAIMonPhysicHostValue[] result = null;
        if(list.size() > 0) {
            result = hostSv.qryByList(list);
        }
        if(null != result) {
            for(IBOAIMonPhysicHostValue value : result) {
                if(hostName.equals(value.getHostName())) {
                    return true;
                }
            }
        }
        return false;

    }

    /**
     * 主机节点应用三表联查主机信息
     * 
     * @param request
     * @throws Exception
     */
    @Override
    public Map<String, IBOAIMonPhysicHostValue> qryHostByAppCode(List<String> list) throws Exception
    {
        Map<String, IBOAIMonPhysicHostValue> map = new HashMap<String, IBOAIMonPhysicHostValue>();
        if(null != list && list.size() > 0) {
            StringBuffer condition = new StringBuffer("");
            Map parameter = new HashMap();
            condition
                    .append("SELECT h.host_id,h.host_name,h.host_ip,s.server_code from ai_mon_server s,ai_mon_node n,ai_mon_physic_host h WHERE s.node_id = n.node_id AND n.host_id = h.host_id AND s.state = 'U' AND n.STATE = 'U' AND h.state = 'U' AND s.server_code IN (");
            for(int i = 0; i < list.size(); i++) {
                condition.append("'" + list.get(i) + "'");
                if(i < list.size() - 1) {
                    condition.append(",");
                }
            }
            condition.append(")");

            Connection conn = null;
            ResultSet resultset = null;
            try {
                conn = ServiceManager.getSession().getConnection();
                resultset = ServiceManager.getDataStore().retrieve(conn, condition.toString(), parameter);
                while(resultset.next()) {
                    IBOAIMonPhysicHostValue value = new BOAIMonPhysicHostBean();
                    value.setHostId(resultset.getLong("HOST_ID"));
                    value.setHostName(resultset.getString("HOST_NAME"));
                    value.setHostIp(resultset.getString("HOST_IP"));
                    map.put(resultset.getString("SERVER_CODE"), value);
                }
            }
            catch(Exception e) {
                throw e;
            }
            finally {
                if(resultset != null)
                    resultset.close();
                if(conn != null)
                    conn.close();
            }

        }
        return map;
    }

	@Override
	public Map<String, IBOAIMonPhysicHostValue> qryHostInfoByCondition (
			String groupCode, String hostId, String serverCode) throws  Exception{
			Map<String, IBOAIMonPhysicHostValue> map = new HashMap<String, IBOAIMonPhysicHostValue>();
	        Map parameter = new HashMap();
            StringBuffer condition = new StringBuffer("SELECT H.HOST_NAME, H.HOST_IP, S.SERVER_CODE ")
            .append("FROM AI_MON_SERVER   S,AI_MON_NODE   N, AI_MON_PHYSIC_HOST    H, AI_MON_GROUP_HOST_REL R,AI_MON_GROUP   G ")
            .append("WHERE S.NODE_ID = N.NODE_ID  AND N.HOST_ID = H.HOST_ID  AND H.HOST_ID = R.HOST_ID   AND R.GROUP_ID = G.GROUP_ID ")
            .append("AND S.STATE = 'U'  AND N.STATE = 'U' AND H.STATE = 'U' AND G.STATE = 'U' AND 1 = 1 ");
            if (StringUtils.isNotBlank(groupCode)){
            	condition.append("AND G.GROUP_ID = :groupCode ");
            	parameter.put("groupCode", groupCode);
            }
            if (StringUtils.isNotBlank(hostId)){
            	condition.append("AND H.Host_Id = :hostId ");
            	parameter.put("hostId", hostId);
            }
			if (StringUtils.isNotBlank(serverCode)){
				condition.append("AND S.Server_Code LIKE :serverCode");
            	parameter.put("serverCode", "%"+serverCode+"%");
			}
 

            Connection conn = null;
            ResultSet resultset = null;
            try {
                conn = ServiceManager.getSession().getConnection();
                resultset = ServiceManager.getDataStore().retrieve(conn, condition.toString(), parameter);
                while(resultset.next()) {
                    IBOAIMonPhysicHostValue value = new BOAIMonPhysicHostBean();
                    value.setHostName(resultset.getString("HOST_NAME"));
                    value.setHostIp(resultset.getString("HOST_IP"));
                    map.put(resultset.getString("SERVER_CODE"), value);
                }
            }
            catch(Exception e) {
                throw e;
            }
            finally {
                if(resultset != null)
                    resultset.close();
                if(conn != null)
                    conn.close();
            }
        return map;
	}

    @Override
    public IBOAIMonPhysicHostValue[] qryHostInfoByIp(String ip) throws Exception
    {
        StringBuilder condition = new StringBuilder("");
        Map parameter = new HashMap();
        if(StringUtils.isNotBlank(ip)) {
            condition.append(IBOAIMonPhysicHostValue.S_HostIp).append("= :ip");
            parameter.put("ip", ip);
        }
        if(StringUtils.isNotBlank(condition.toString())) {
            condition.append(" and ");
        }
        condition.append(" state = 'U' ");
        return BOAIMonPhysicHostEngine.getBeans(condition.toString(), parameter);
    }

}
