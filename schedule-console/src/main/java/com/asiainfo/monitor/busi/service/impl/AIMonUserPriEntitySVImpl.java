package com.asiainfo.monitor.busi.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.cache.AIMonUserPriEntityCheckListener;
import com.asiainfo.monitor.busi.cache.MonCacheManager;
import com.asiainfo.monitor.busi.cache.impl.AIMonUserPriEntityCacheImpl;
import com.asiainfo.monitor.busi.cache.impl.AIMonUserRoleDomainCacheImpl;
import com.asiainfo.monitor.busi.cache.impl.PriEntity;
import com.asiainfo.monitor.busi.cache.impl.RoleDomain;
import com.asiainfo.monitor.busi.cache.impl.UserGroup;
import com.asiainfo.monitor.busi.common.impl.PriEntitySeqComparator;
import com.asiainfo.monitor.busi.dao.interfaces.IAIMonUserPriEntityDAO;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonUserPriEntitySV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonUserRoleSV;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonUserPriEntityBean;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonUserPriEntityEngine;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserPriEntityValue;
import com.asiainfo.monitor.exeframe.controlmgr.common.UserPrivConst;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.license.LicenseManager;
import com.asiainfo.monitor.tools.license.LicenseWrapper;

public class AIMonUserPriEntitySVImpl implements IAIMonUserPriEntitySV{

	private static transient Log log=LogFactory.getLog(AIMonUserPriEntitySVImpl.class);

	/**
     * 将实体简单封装
     * @param value
     * @return
     * @throws Exception
     */
    public PriEntity wrapperPriEntityByBean(IBOAIMonUserPriEntityValue value) throws Exception
    {
        if(value == null || StringUtils.isBlank(value.getState()) || value.getState().equals("E"))
            return null;
        PriEntity result = new PriEntity();
        result.setId(value.getEntityId() + "");
        result.setCode(value.getEntityCode());
        result.setName(value.getEntityName());
        result.setDeployType(value.getDeployType());
        result.setLoadType(value.getLoadType());
        result.setAttr(value.getEntityAttr());
        result.setParentId(value.getParentId() + "");
        result.setSelfType(value.getSelfType() + "");
        result.setSeq(value.getEntitySeq());
        result.setType(value.getEntityType() + "");
        result.setStyle(value.getEntityStyle());
        result.setRemarks(value.getNotes());
        result.setCacheListener(new AIMonUserPriEntityCheckListener());
        return result;
    }

    /**
     * 根据EntityId查询实体信息
     * 
     * @param entityId
     * @return IBOAIMonUserPriEntityValue
     * @throws Exception
     */
    public PriEntity getEntityByEntityId(String entityId) throws Exception
    {
        PriEntity result = null;
        try {
            result = (PriEntity) MonCacheManager.get(AIMonUserPriEntityCacheImpl.class, entityId);
            if(result == null) {
                result = this.getPriEntiryByIdFromDb(entityId);
                if(!MonCacheManager.getCacheReadOnlySet() && result != null) {
                    MonCacheManager.put(AIMonUserPriEntityCacheImpl.class, result.getId(), result);
                }
            }
        } catch(Exception e) {
            // 异常:
            log.error(AIMonLocaleFactory.getResource("MOS0000049") + e.getMessage());
        }
        return result;
    }

    /**
     * 根据parentId查询实体信息
     * 
     * @param parentId
     * @return IBOAIMonUserPriEntityValue[] 
     * @throws Exception
     */
    public List getEntityByParentId(String parentId) throws Exception
    {
        List result = null;
        HashMap entityMap = MonCacheManager.getAll(AIMonUserPriEntityCacheImpl.class);
        if(entityMap != null && entityMap.size() > 0) {
            result = new ArrayList();
            Iterator it = entityMap.entrySet().iterator();
            while(it.hasNext()) {
                Entry entry = (Entry) it.next();
                String id = ((PriEntity) entry.getValue()).getParentId();
                if(StringUtils.isNotBlank(id) && Long.parseLong(id) == Long.parseLong(parentId)) {
                    result.add((PriEntity) entry.getValue());
                }
    
            }
        }
        // 对结果进行排序
        Collections.sort(result, new PriEntitySeqComparator());
        return result;
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
        IAIMonUserPriEntityDAO entityDAO = (IAIMonUserPriEntityDAO) ServiceFactory.getService(IAIMonUserPriEntityDAO.class);
        return entityDAO.getUserEntityByCond(entityName, entityType);
    }

    /**
     * 根据parentId查询实体信息
     * 
     * @param parentId
     * @return IBOAIMonUserPriEntityValue[] 
     * @throws Exception
     */
    public List getEntityByParentIdNocache(String parentId) throws Exception
    {
        List result = new ArrayList();
        StringBuilder cond = new StringBuilder();
        cond.append(IBOAIMonUserPriEntityValue.S_ParentId).append("=:").append(IBOAIMonUserPriEntityValue.S_ParentId);
        Map params = new HashMap();
        params.put(IBOAIMonUserPriEntityValue.S_ParentId, parentId);
        cond.append(" and ").append(IBOAIMonUserPriEntityValue.S_State).append("= :pstate");
        params.put("pstate", "U");
        IAIMonUserPriEntityDAO dao = (IAIMonUserPriEntityDAO) ServiceFactory.getService(IAIMonUserPriEntityDAO.class);
        IBOAIMonUserPriEntityValue[] entityBeans = dao.query(cond.toString(), params);
        if(entityBeans != null && entityBeans.length > 0) {
    
            for(int i = 0; i < entityBeans.length; i++) {
                PriEntity entity = new PriEntity();
                entity.setId(entityBeans[i].getEntityId() + "");
                entity.setCode(entityBeans[i].getEntityCode());
                entity.setName(entityBeans[i].getEntityName());
                entity.setDeployType(entityBeans[i].getDeployType());
                entity.setLoadType(entityBeans[i].getLoadType());
                entity.setAttr(entityBeans[i].getEntityAttr());
                entity.setParentId(entityBeans[i].getParentId() + "");
                entity.setSelfType(entityBeans[i].getSelfType() + "");
                entity.setSeq(entityBeans[i].getEntitySeq());
                entity.setType(entityBeans[i].getEntityType() + "");
                entity.setStyle(entityBeans[i].getEntityStyle());
                entity.setRemarks(entityBeans[i].getNotes());
                result.add(entity);
            }
        }
        // 对结果进行排序
        Collections.sort(result, new PriEntitySeqComparator());
        return result;
    }

    /**
     * 从数据库读取实体信息并简单封装
     * @param id
     * @return
     * @throws Exception
     */
    public PriEntity getPriEntiryByIdFromDb(String id) throws Exception
    {
        if(StringUtils.isBlank(id))
            return null;
        IAIMonUserPriEntityDAO dao = (IAIMonUserPriEntityDAO) ServiceFactory.getService(IAIMonUserPriEntityDAO.class);
        IBOAIMonUserPriEntityValue value = dao.queryEntityByEntityId(id);
        PriEntity result = this.wrapperPriEntityByBean(value);
        return result;
    }

    /**
     * 根据用户权限获取实体信息
     * 
     * @param userId
     * @param parentId
     * @param type
     * @return IBOAIMonUserPriEntityValue[] 
     * @throws Exception
     */
    public List queryEntityByPermission(String userId, String parentId, String type) throws Exception
    {
        List result = null;
        if(StringUtils.isBlank(userId))
            // 标识为空
            throw new Exception(AIMonLocaleFactory.getResource("MOS0000098"));
        UserGroup userRoleDomain = (UserGroup) MonCacheManager.get(AIMonUserRoleDomainCacheImpl.class, userId);
        if(userRoleDomain == null)
            // 没有该用户["+userId+"]缓存信息
            throw new Exception(AIMonLocaleFactory.getResource("MOS0000126", userId));
        if(userRoleDomain.getGroup() == null)
            throw new Exception(AIMonLocaleFactory.getResource("MOS0000127", userId));
        if(userRoleDomain.getGroup().getRoles() == null || userRoleDomain.getGroup().getRoles().size() < 1)
            // 没有为用户["+userId+"]所在的组["+userRoleDomain.getGroup().getId()+"]指定角色
            throw new Exception(AIMonLocaleFactory.getResource("MOS0000128", userId, userRoleDomain.getGroup().getId()));
        List roleList = userRoleDomain.getGroup().getRoles();
        result = new ArrayList();
        IAIMonUserRoleSV userRoleSV = (IAIMonUserRoleSV) ServiceFactory.getService(IAIMonUserRoleSV.class);
        for(int i = 0; i < roleList.size(); i++) {
            String roleId = String.valueOf(roleList.get(i));
            RoleDomain roleGuild = userRoleSV.getRoleGuildById(roleId);
            if(roleGuild != null) {
                List entities = roleGuild.getEntities();
                for(int j = 0; j < entities.size(); j++) {
                    String entityId = (String) entities.get(j);
                    PriEntity entity = (PriEntity) MonCacheManager.get(AIMonUserPriEntityCacheImpl.class, entityId);
    
                    if(entity != null && Long.parseLong(entity.getParentId()) == Long.parseLong(parentId)
                            && (StringUtils.isEmpty(type) || entity.getType().equals(type))) {
                        result.add(entity);
                    }
                }
            }
        }
        //校验license
        LicenseManager.validateLicense("Monitor Professional", "1.0");
    
        for(Iterator its = result.iterator(); LicenseWrapper.getInstance().getLicenseType().isEvaluation() && its.hasNext();) {
            boolean hasPermite = false;
            PriEntity item = (PriEntity) its.next();
            if(item.getCode().equals("SYSTEM") || LicenseWrapper.getInstance().contain(item.getCode()))
                hasPermite = true;
            if(!hasPermite)
                its.remove();
        }
        // 对结果进行排序
        Collections.sort(result, new PriEntitySeqComparator());
    
        return result;
    }

    /**
     * 获取所有实体信息
     * @return
     * @throws Exception
     */
    public IBOAIMonUserPriEntityValue[] getAllEntityBean() throws Exception
    {
        IAIMonUserPriEntityDAO entityDAO = (IAIMonUserPriEntityDAO) ServiceFactory.getService(IAIMonUserPriEntityDAO.class);
        String condition = IBOAIMonUserPriEntityValue.S_State + "='U'";
        return entityDAO.query(condition, null);
    }

    /**
     * 批量保存或修改实体信息
     * 
     * @param values
     * @throws Exception
     */
    public void saveOrUpdate(IBOAIMonUserPriEntityValue[] values) throws Exception
    {
        boolean modify = values[0].isModified();
        IAIMonUserPriEntityDAO entityDAO = (IAIMonUserPriEntityDAO) ServiceFactory.getService(IAIMonUserPriEntityDAO.class);
        entityDAO.saveOrUpdate(values);
        for(int i = 0; i < values.length; i++) {
            if(modify) {
                PriEntity item = (PriEntity) MonCacheManager.get(AIMonUserPriEntityCacheImpl.class, values[i].getEntityId() + "");
                if(item != null)
                    item.setEnable(false);
            } else {
                PriEntity item = this.wrapperPriEntityByBean(values[i]);
                MonCacheManager.put(AIMonUserPriEntityCacheImpl.class, item.getId(), item);
            }
        }
    }

    /**
     * 删除实体信息
     * 
     * @param entityId
     * @throws Exception
     */
    public void delete(long entityId) throws Exception
    {
        IAIMonUserPriEntityDAO entityDAO = (IAIMonUserPriEntityDAO) ServiceFactory.getService(IAIMonUserPriEntityDAO.class);
        // 校验
    
        List list = this.getEntityByParentIdNocache(String.valueOf(entityId));
        if(list != null && list.size() > 0)
            // "该实体存在下级,不允许删除!"
            throw new Exception(AIMonLocaleFactory.getResource("MOS0000129"));
        entityDAO.delete(entityId);
        PriEntity item = (PriEntity) MonCacheManager.get(AIMonUserPriEntityCacheImpl.class, entityId + "");
        if(item != null)
            item.setEnable(false);
    }

    /**
     * 根据区域获取扩展实体
     * @param entityId
     * @return
     * @throws Exception
     */
    public List getExtendEntitysByEntityId(String entityId) throws Exception
    {
        List result = null;
        try {
            HashMap entities = MonCacheManager.getAll(AIMonUserPriEntityCacheImpl.class);
            if(entities != null && entities.size() > 0) {
                result = new ArrayList();
                Iterator it = entities.entrySet().iterator();
                while(it.hasNext()) {
                    Entry entry = (Entry) it.next();
                    PriEntity entity = (PriEntity) entry.getValue();
                    if(entity != null && Integer.parseInt(entity.getSelfType()) == 2 && Long.parseLong(entity.getParentId()) == Long.parseLong(entityId)) {
                        result.add(entity);
                    }
                }
                Collections.sort(result, new PriEntitySeqComparator());
            }
        } catch(Exception e) {
            log.error(AIMonLocaleFactory.getResource("MOS0000049") + e.getMessage());
        }
        return result;
    }

    /**
     * 根据加载类型获取实体信息
     * @param loadType
     * @return
     * @throws Exception
     */
    public List getEntityByLoadType(String loadType) throws Exception
    {
        List result = null;
        try {
            HashMap entities = MonCacheManager.getAll(AIMonUserPriEntityCacheImpl.class);
            if(entities != null && entities.size() > 0) {
                result = new ArrayList();
                Iterator it = entities.entrySet().iterator();
                while(it.hasNext()) {
                    Entry entry = (Entry) it.next();
                    PriEntity entity = (PriEntity) entry.getValue();
                    if(entity != null && entity.getLoadType() != null && entity.getLoadType().equals(loadType)) {
                        result.add(entity);
                    }
                }
                Collections.sort(result, new PriEntitySeqComparator());
            }
        } catch(Exception e) {
            log.error(AIMonLocaleFactory.getResource("MOS0000049") + e.getMessage());
        }
        return result;
    }

    /**
     * 保存或修改实体信息
     * 
     * @param value
     * @throws Exception
     */
    public void saveOrUpdate(IBOAIMonUserPriEntityValue value) throws Exception
    {
        boolean modify = value.isModified();
        IAIMonUserPriEntityDAO entityDAO = (IAIMonUserPriEntityDAO) ServiceFactory.getService(IAIMonUserPriEntityDAO.class);
        entityDAO.saveOrUpdate(value);
        if(modify) {
            PriEntity item = (PriEntity) MonCacheManager.get(AIMonUserPriEntityCacheImpl.class, value.getEntityId() + "");
            if(item != null)
                item.setEnable(false);
        } else {
            PriEntity item = this.wrapperPriEntityByBean(value);
            MonCacheManager.put(AIMonUserPriEntityCacheImpl.class, item.getId(), item);
        }
    }

    /**
     * 返回缓存中所有的实体信息
     * @return
     * @throws Exception
     */
    public List getAllEntity() throws Exception
    {
        Map dataMap = MonCacheManager.getAll(AIMonUserPriEntityCacheImpl.class);
        if(dataMap == null)
            return null;
        List result = new ArrayList();
        result.addAll(dataMap.values());
        return result;
    }

    /**
     * 根据EntityId查询实体信息
     * 
     * @param entityId
     * @return IBOAIMonUserPriEntityValue
     * @throws Exception
     */
    public PriEntity getEntityByEntityIdNocache(String entityId) throws Exception
    {
        PriEntity result = null;
        try {
            IAIMonUserPriEntityDAO entityDAO = (IAIMonUserPriEntityDAO) ServiceFactory.getService(IAIMonUserPriEntityDAO.class);
            IBOAIMonUserPriEntityValue value = entityDAO.queryEntityByEntityId(entityId);
            if(value == null)
                return result;
            result = new PriEntity();
            result.setId(value.getEntityId() + "");
            result.setCode(value.getEntityCode());
            result.setName(value.getEntityName());
            result.setDeployType(value.getDeployType());
            result.setLoadType(value.getLoadType());
            result.setAttr(value.getEntityAttr());
            result.setParentId(value.getParentId() + "");
            result.setSelfType(value.getSelfType() + "");
            result.setSeq(value.getEntitySeq());
            result.setType(value.getEntityType() + "");
            result.setStyle(value.getEntityStyle());
            result.setRemarks(value.getNotes());
        } catch(Exception e) {
            log.error(AIMonLocaleFactory.getResource("MOS0000049") + e.getMessage());
        }
        return result;
    }

    /**
     * 根据父节点标识获取所有子节点，如果父节点为空，则层次查询所有节点信息
     * 
     * @param parentId
     *            父节点标识
     * @return 返回该父节点下的所有子节点
     */
    public BOAIMonUserPriEntityBean[] getPrivEntityPrior(String parentId) throws Exception
    {
        BOAIMonUserPriEntityBean[] resultArr = null;
        IAIMonUserPriEntityDAO dao = (IAIMonUserPriEntityDAO) ServiceFactory.getService(IAIMonUserPriEntityDAO.class);
        //如果父节点为空，在查询所有节点
        if(StringUtils.isBlank(parentId)){
            resultArr = dao.getPrivEntityPrior();
        }
        //按照父节点标识查询所有子节点信息
        else{
            StringBuilder conStr = new StringBuilder();
            conStr.append(IBOAIMonUserPriEntityValue.S_ParentId + "= :PARENT_ID AND ");
            conStr.append(IBOAIMonUserPriEntityValue.S_State + " = :STATE ");
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("PARENT_ID", parentId);
            paramMap.put("STATE", UserPrivConst.USER_STATE_U);
            resultArr = (BOAIMonUserPriEntityBean[]) dao.query(conStr.toString(), paramMap);
        }
        
        return resultArr;
    }

    @Override
    public IBOAIMonUserPriEntityValue getPriEntityByEntityId(String entityId) throws Exception
    {
        if(StringUtils.isBlank(entityId))
            return null;
        IAIMonUserPriEntityDAO dao = (IAIMonUserPriEntityDAO) ServiceFactory.getService(IAIMonUserPriEntityDAO.class);
        IBOAIMonUserPriEntityValue value = dao.queryEntityByEntityId(entityId);
        return value;
    }

    public IBOAIMonUserPriEntityValue[] getUserEntityListByRoleId(String userRoleId) throws Exception
    {
        StringBuilder sqlStr = new StringBuilder();
        sqlStr.append("select mpe.entity_id, mpe.entity_code, mpe.entity_name, mpe.entity_type, mpe.state, mpe.notes ")
                .append("  from ai_mon_user_pri_entity mpe, ai_mon_user_role_entity_rel mger").append("  where mpe.entity_id = mger.entity_id")
                .append(" and mger.user_role_id = :USER_ROLE_ID  and mpe.state = :STATE ");

        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("USER_ROLE_ID", String.valueOf(userRoleId));
        paramMap.put("STATE", UserPrivConst.USER_STATE_U);

        IBOAIMonUserPriEntityValue[] userBeanArr = BOAIMonUserPriEntityEngine.getBeansFromSql(sqlStr.toString(), paramMap);

        return userBeanArr;

    }

    public IBOAIMonUserPriEntityValue[] getRelUserEntityListByRoleId(String userRoleId) throws Exception
    {
        StringBuilder sqlStr = new StringBuilder();

        sqlStr.append("select mpe.entity_id, mpe.entity_code, mpe.entity_name, mpe.entity_type, mpe.state, mpe.notes ")
                .append("  from ai_mon_user_pri_entity mpe ").append("  where mpe.entity_id not in(")
                .append(" select mger.user_role_id from ai_mon_user_role_entity_rel mger where mger.user_role_id = :USER_ROLE_ID )");

        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("USER_ROLE_ID", String.valueOf(userRoleId));
        paramMap.put("STATE", UserPrivConst.USER_STATE_U);

        IBOAIMonUserPriEntityValue[] userBeanArr = BOAIMonUserPriEntityEngine.getBeansFromSql(sqlStr.toString(), paramMap);

        return userBeanArr;
    }

    /*
     * 根据实体编码和名字校验实体是否存在
     * 
     * @param entityCode
     * 
     * @param entityName
     * 
     * @return
     * 
     * @throws Exception
     */
    public boolean checkByCodeAndName(String entityCode, String entityName) throws Exception
    {
        IAIMonUserPriEntityDAO dao = (IAIMonUserPriEntityDAO) ServiceFactory.getService(IAIMonUserPriEntityDAO.class);
        return dao.checkByCodeAndName(entityCode, entityName);
    }
}
