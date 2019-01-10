package com.asiainfo.monitor.busi.dao.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.asiainfo.monitor.busi.dao.interfaces.IAIMonUserPriEntityDAO;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonUserPriEntityBean;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonUserPriEntityEngine;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserPriEntityValue;
import com.asiainfo.monitor.exeframe.controlmgr.common.UserPrivConst;

public class AIMonUserPriEntityDAOImpl implements IAIMonUserPriEntityDAO{

    private Timestamp nowDate = new Timestamp(System.currentTimeMillis());
	/**
	 * 根据条件获取查询结果
	 * 
	 * @param cond
	 * @param param
	 * @return IBOAIMonUserPriEntityValue[] 
	 * @throws Exception
	 */
	public IBOAIMonUserPriEntityValue[] query(String cond, Map param) throws Exception {
		return BOAIMonUserPriEntityEngine.getBeans(cond, param);
	}
	
	/**
	 * 根据EntityId查询实体信息
	 * 
	 * @param entityId
	 * @return IBOAIMonUserPriEntityValue
	 * @throws Exception
	 */
	public IBOAIMonUserPriEntityValue queryEntityByEntityId(String entityId) throws Exception {
		StringBuilder sb = new StringBuilder();
		Map m = new HashMap(); 
		if (StringUtils.isNotBlank(entityId)) {
			sb.append(IBOAIMonUserPriEntityValue.S_EntityId).append(" = :entityId");
			m.put("entityId", Long.parseLong(entityId));
		}
		IBOAIMonUserPriEntityValue[] values = query(sb.toString(),m);
		if (values != null && values.length > 0)
			return values[0];
		return null;
	}
	
	
	/**
     * 根据条件检索用户实体信息
     * 
     * @param entityName
     * @param entityType
     * @return
     * @throws Exception
     */
    public IBOAIMonUserPriEntityValue[] getUserEntityByCond(String entityName, String entityType) throws Exception
    {
        StringBuilder condition = new StringBuilder("");
        Map parameter = new HashMap();
    
        if(StringUtils.isNotBlank(entityName)) {
            condition.append(IBOAIMonUserPriEntityValue.S_EntityName).append(" like :entityName");
            parameter.put("entityName", "%" + entityName + "%");
        }
        if(StringUtils.isNotBlank(entityType)) {
            if(StringUtils.isNotBlank(entityName)) {
                condition.append(" and ");
            }
            condition.append(IBOAIMonUserPriEntityValue.S_EntityType).append(" = :entityType");
            parameter.put("entityType", entityType);
        }
        if(StringUtils.isNotBlank(condition.toString())) {
            condition.append(" and ");
        }
        condition.append(" state = 'U' order by PARENT_ID, ENTITY_SEQ");
        return query(condition.toString(), parameter);
    }

    /**
     * 批量保存或修改实体信息
     * 
     * @param values
     * @throws Exception
     */
    public void saveOrUpdate(IBOAIMonUserPriEntityValue[] values) throws Exception
    {
        if(values != null && values.length > 0) {
            for(int i = 0; i < values.length; i++) {
                if(values[i].isNew()) {
                    values[i].setEntityId(BOAIMonUserPriEntityEngine.getNewId().longValue());
                }
            }
        } else {
            throw new Exception("no data to saveOrUpdate...");
        }
        BOAIMonUserPriEntityEngine.saveBatch(values);
    }

    /**
     * 删除实体信息
     * 
     * @param entityId
     * @throws Exception
     */
    public void delete(long entityId) throws Exception
    {
        IBOAIMonUserPriEntityValue value = BOAIMonUserPriEntityEngine.getBean(entityId);
        if(null != value) {
            //			value.delete();
            value.setState("E");
            BOAIMonUserPriEntityEngine.save(value);
        }
    }

    /**
     * 保存或修改实体信息
     * 
     * @param value
     * @throws Exception
     */
    public void saveOrUpdate(IBOAIMonUserPriEntityValue value) throws Exception
    {
        if(value.isNew()) {
            long entityId = BOAIMonUserPriEntityEngine.getNewId().longValue();
            value.setEntityId(entityId);
            String entityAttr = value.getEntityAttr();
            value.setState(UserPrivConst.USER_STATE_U);
            value.setEntityAttr(entityAttr + "?id=" + String.valueOf(entityId));
        }
        value.setDoneDate(nowDate);
        BOAIMonUserPriEntityEngine.save(value);
    }

    public BOAIMonUserPriEntityBean[] getPrivEntityPrior() throws Exception
    {
        StringBuilder sqlStr = new StringBuilder();
        sqlStr.append("select upe.entity_id,upe.parent_id,upe.entity_code,upe.entity_name, upe.entity_type,upe.state")
                .append(" from ai_mon_user_pri_entity upe  where upe.state='U' ")
                .append(" start with upe.parent_id = 0 connect by prior upe.entity_id = upe.parent_id");
        Map<String, String> paramMap = new HashMap<String, String>();
        return BOAIMonUserPriEntityEngine.getBeansFromSql(sqlStr.toString(), paramMap);

    }

    /**
     * 根据实体名称和代码校验记录是否存在
     * 
     * @param entityCode
     * @param entityName
     * @return
     * @throws Exception
     */
    public boolean checkByCodeAndName(String entityCode, String entityName) throws Exception
    {
        StringBuilder sqlStr = new StringBuilder();
        sqlStr.append("select entity_name,entity_code from ai_mon_user_pri_entity ape ");
        sqlStr.append(" where ape.entity_name=:ENTITY_NAME OR ape.entity_CODE =:ENTITY_CODE");
        Map<String, String> parameter = new HashMap<String, String>();
        parameter.put("ENTITY_NAME", entityName);
        parameter.put("ENTITY_CODE", entityCode);

        BOAIMonUserPriEntityBean[] beans = BOAIMonUserPriEntityEngine.getBeansFromSql(sqlStr.toString(), parameter);

        if(beans.length > 0) {
            return true;
        }

        return false;
    }
}
