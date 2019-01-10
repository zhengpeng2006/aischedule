package com.asiainfo.monitor.exeframe.configmgr.abo.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.util.criteria.Criteria;
import com.asiainfo.monitor.common.CommonConst;
import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonGroupHostRelBean;
import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonGroupHostRelEngine;
import com.asiainfo.monitor.exeframe.configmgr.abo.dao.interfaces.IAIMonGroupHostRelDAO;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonGroupHostRelValue;

public class AIMonGroupHostRelDAOImpl implements IAIMonGroupHostRelDAO
{

    public long getBeanId() throws Exception
    {
        return BOAIMonGroupHostRelEngine.getNewId().longValue();
    }

    public BOAIMonGroupHostRelBean getBeanById(long id) throws Exception
    {
        return BOAIMonGroupHostRelEngine.getBean(id);
    }

    public void save(BOAIMonGroupHostRelBean value) throws Exception
    {
        if(value.isNew() && value.getRelationId() <= 0) {
            value.setRelationId(getBeanId());
            value.setCreateDate(nowDate);
        }
        BOAIMonGroupHostRelEngine.save(value);
    }

    private Timestamp nowDate = new Timestamp(System.currentTimeMillis());

    @Override
    public BOAIMonGroupHostRelBean[] getBeansByGroupId(long groupId) throws Exception
    {
        Criteria sql = new Criteria();
        sql.addEqual(IBOAIMonGroupHostRelValue.S_GroupId, groupId);
        sql.addAscendingOrderByColumn(IBOAIMonGroupHostRelValue.S_HostId);
        return BOAIMonGroupHostRelEngine.getBeans(sql);
    }

    @Override
    public BOAIMonGroupHostRelBean[] qryGroupHost(String groupId, String hostId) throws Exception
    {
        StringBuilder condition = new StringBuilder(" 1=1 ");
        Map parameter = new HashMap();
        if(StringUtils.isNotBlank(groupId)) {
        	condition.append(" and ");
            condition.append(IBOAIMonGroupHostRelValue.S_GroupId).append(" like :groupId");
            parameter.put("groupId", "%" + groupId + "%");
        }
        if(StringUtils.isNotBlank(hostId)) {
            condition.append(" and ");
            condition.append(IBOAIMonGroupHostRelValue.S_HostId).append(" like :hostId");
            parameter.put("hostId", "%" + hostId + "%");
        }
        return BOAIMonGroupHostRelEngine.getBeans(condition.toString(), parameter);
    }

    @Override
    public void delete(IBOAIMonGroupHostRelValue[] values) throws Exception
    {
        if(values != null && values.length > 0) {
            for(int i = 0; i < values.length; i++) {
                values[i].delete();
                BOAIMonGroupHostRelEngine.save(values[i]);
            }
        } else {
            throw new Exception("no data to saveOrUpdate...");
        }
    }

    @Override
    public List<String> getHostListByGroupId(String groupId) throws Exception
    {
        StringBuilder condition = new StringBuilder("");
        if(StringUtils.isNotBlank(groupId)) {
            condition.append(IBOAIMonGroupHostRelValue.S_GroupId).append(" = :groupId");
        }
        condition.append(" order by ").append(IBOAIMonGroupHostRelValue.S_HostId);
        Map param = new HashMap();
        param.put("groupId", Long.parseLong(groupId));
        IBOAIMonGroupHostRelValue[] value = BOAIMonGroupHostRelEngine.getBeans(condition.toString(), param);

        List<String> list = new ArrayList<String>();
        for(int i = 0; i < value.length; i++) {
            String hostId = Long.toString(value[i].getHostId());
            list.add(hostId);
        }
        return list;
    }

	@Override
	public Map<Long, String> getHostGroups() throws Exception{
		Map<Long, String> map = new HashMap<Long, String>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT H.HOST_ID,G.GROUP_NAME FROM AI_MON_PHYSIC_HOST H,AI_MON_GROUP_HOST_REL R,AI_MON_GROUP G ")
		.append("WHERE H.HOST_ID = R.HOST_ID AND R.GROUP_ID = G.GROUP_ID ")
		.append("AND H.STATE = 'U' AND G.STATE = 'U'");
		Connection conn = null;
	    ResultSet resultset = null;
		try {
		conn = ServiceManager.getSession().getConnection();
		resultset =ServiceManager.getDataStore().retrieve(conn,sql.toString(),null);
			while(resultset.next())
			{
				map.put(resultset.getLong("HOST_ID"), resultset.getString("GROUP_NAME"));
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

}
