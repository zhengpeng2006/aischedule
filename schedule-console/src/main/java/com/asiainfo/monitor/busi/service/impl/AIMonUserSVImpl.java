package com.asiainfo.monitor.busi.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ai.appframe2.complex.util.e.K;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.cache.AIMonUserRoleDomainCheckListener;
import com.asiainfo.monitor.busi.cache.MonCacheManager;
import com.asiainfo.monitor.busi.cache.impl.AIMonUserRoleDomainCacheImpl;
import com.asiainfo.monitor.busi.cache.impl.RoleDomain;
import com.asiainfo.monitor.busi.cache.impl.UserGroup;
import com.asiainfo.monitor.busi.dao.interfaces.IAIMonUserDAO;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonUserGroupRelSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonUserRoleSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonUserSV;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonUserEngine;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserGroupRelValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserValue;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class AIMonUserSVImpl implements IAIMonUserSV{

	/**
     * 根据用户Code读取用户信息,不判断生效失效时间
     * @param userCode
     * @return
     * @throws Exception
     */
    public IBOAIMonUserValue getUserInfoByCode(String userCode) throws Exception
    {
        IAIMonUserDAO userDao = (IAIMonUserDAO) ServiceFactory.getService(IAIMonUserDAO.class);
        Map parameter = new HashMap();
        StringBuilder condition = new StringBuilder("");
        condition.append(IBOAIMonUserValue.S_UserCode).append(" =:userCode");
        condition.append(" and ");
        condition.append(IBOAIMonUserValue.S_State).append("='U'");
        parameter.put("userCode", userCode);
        IBOAIMonUserValue[] userValues = userDao.query(condition.toString(), parameter);
        if (userValues == null || userValues.length == 0) {
            return null;
        }
        if (userValues.length > 1) {
            // "代码为["+userCode+"]的用户信息不唯一，存在多记录"
            throw new Exception(AIMonLocaleFactory.getResource("MOS0000132", userCode));
        }
        return userValues[0];
    }

    /**
     * 根据主键删除用户信息
     * 
     * @param userId
     * @throws Exception
     */
    public void delete(long userId) throws Exception
    {
        IAIMonUserDAO userDao = (IAIMonUserDAO) ServiceFactory.getService(IAIMonUserDAO.class);
        userDao.delete(userId);
        UserGroup item = (UserGroup) MonCacheManager.get(AIMonUserRoleDomainCacheImpl.class, userId + "");
        if (item != null)
            item.setEnable(false);
    }

    /**
     * 根据用户编码在缓存中获取用户信息
     * @param userCode
     * @return
     * @throws Exception
     */
    public UserGroup getUserRoleDomain(String userCode) throws Exception
    {
    
        HashMap allUser = MonCacheManager.getAll(AIMonUserRoleDomainCacheImpl.class);
        if (allUser == null || allUser.size() == 0)
            return null;
        UserGroup result = null;
        for (Iterator it = allUser.keySet().iterator(); it.hasNext();) {
            UserGroup user = (UserGroup) allUser.get(it.next());
            if (user.getCode().equals(userCode)) {
                result = user;
                break;
            }
        }
        if (result == null) {
            result = this.getUserRoleDomainByCodeFromDb(userCode);
            if (!MonCacheManager.getCacheReadOnlySet() && result != null) {
                MonCacheManager.put(AIMonUserRoleDomainCacheImpl.class, result.getId(), result);
            }
        }
        return result;
    
    }

    /**
     * 根据用户Code检索用户信息
     * 
     * @param userCode
     * @return IBOAIMonUserValue
     * @throws Exception
     */
    public IBOAIMonUserValue getAvailableUserInfoByCode(String userCode) throws Exception
    {
        IAIMonUserDAO userDao = (IAIMonUserDAO) ServiceFactory.getService(IAIMonUserDAO.class);
        return userDao.getUserInfoByCode(userCode);
    }

    /**
     * 根据用户标识读取用户拥有域信息
     * @param userId
     * @return
     * @throws Exception
     */
    public List getDomainsByUserId(String userId) throws Exception
    {
        UserGroup userInfo = (UserGroup) MonCacheManager.get(AIMonUserRoleDomainCacheImpl.class, userId);
        if (userInfo == null) {
            userInfo = this.getUserRoleDomainByIdFromDb(userId);
            if (!MonCacheManager.getCacheReadOnlySet() && userInfo != null) {
                MonCacheManager.put(AIMonUserRoleDomainCacheImpl.class, userInfo.getId(), userInfo);
            }
        }
        if (userInfo == null) {
            // 没有该用户[{0}]缓存信息
            throw new Exception(AIMonLocaleFactory.getResource("MOS0000126", userId));
        }
        if (userInfo.getGroup() == null)
            // 没有为用户[{0}]指定组信息
            throw new Exception(AIMonLocaleFactory.getResource("MOS0000127", userId));
        List roles = userInfo.getGroup().getRoles();
        if (roles == null || roles.size() < 1)
            // 没有为用户[{0}]所在的组[{1}]指定角色
            throw new Exception(AIMonLocaleFactory.getResource("MOS0000128", userId, userInfo.getGroupId()));
        List domains = new ArrayList();
        IAIMonUserRoleSV userRoleSV = (IAIMonUserRoleSV) ServiceFactory.getService(IAIMonUserRoleSV.class);
        for (int i = 0; i < roles.size(); i++) {
            String roleId = String.valueOf(roles.get(i));
            RoleDomain roleGuild = userRoleSV.getRoleGuildById(roleId);
            if (roleGuild != null && StringUtils.isNotBlank(roleGuild.getDomainId())
                    && Long.parseLong(roleGuild.getDomainId()) > 0)
                domains.add(roleGuild.getDomainId());
        }
        return domains;
    }

    /**
     * 根据标识从数据库读取用户信息并封装成用户角色域
     * @param id
     * @return
     * @throws Exception
     */
    public UserGroup getUserRoleDomainByIdFromDb(String id) throws Exception
    {
        if (StringUtils.isBlank(id))
            return null;
        IBOAIMonUserValue value = this.getBeanById(Long.valueOf(id));
        return this.wrapperUserRoleDomainByBean(value);
    }

    /**
     * 根据标识从数据库读取用户信息并封装成用户角色域
     * @param id
     * @return
     * @throws Exception
     */
    public UserGroup getUserRoleDomainByCodeFromDb(String userCode) throws Exception
    {
        if (StringUtils.isBlank(userCode))
            return null;
        IAIMonUserDAO userDao = (IAIMonUserDAO) ServiceFactory.getService(IAIMonUserDAO.class);
        IBOAIMonUserValue[] values = userDao.getMonUserBeanByCode(userCode);
        if (values != null && values.length > 1)
            // 有多条工号为["+userCode+"]的用户
            throw new Exception(AIMonLocaleFactory.getResource("MOS0000131", userCode));
        if (values != null && values.length == 0)
            throw new Exception(AIMonLocaleFactory.getResource("MOS0000310", userCode));
        return this.wrapperUserRoleDomainByBean(values[0]);
    }

    /**
     * 保存或修改登录用户信息
     * 
     * @param value
     * @throws Exception
     */
    public void saveOrUpdate(IBOAIMonUserValue value) throws Exception
    {
        boolean modify = value.isModified();
        
        IAIMonUserDAO userDao = (IAIMonUserDAO) ServiceFactory.getService(IAIMonUserDAO.class);
        value.setUserPass(K.j(value.getUserPass()));//加密密码
        userDao.saveOrUpdate(value);
        if (modify) {
            UserGroup item = (UserGroup) MonCacheManager.get(AIMonUserRoleDomainCacheImpl.class, value.getUserId() + "");
            if (item != null)
                item.setEnable(false);
        } else {
            UserGroup item = this.wrapperUserRoleDomainByBean(value);
            MonCacheManager.put(AIMonUserRoleDomainCacheImpl.class, item.getId(), item);
        }
    }

    /**
     * 根据条件检索用户信息
     * 
     * @param userName
     * @param userCode
     * @return
     * @throws Exception
     */
    public IBOAIMonUserValue[] getUserInfoByCond(String userCode, String userName) throws Exception
    {
        IAIMonUserDAO userDao = (IAIMonUserDAO) ServiceFactory.getService(IAIMonUserDAO.class);
        IBOAIMonUserValue[] values = userDao.getUserInfoByCond(userCode, userName);
        if (values != null && values.length > 0 && values[0] != null) {
            for (int i = 0; i < values.length; i++) {
                values[i].setUserPass(K.k(values[i].getUserPass()));
            }
        }
        return values;
    }

    /**
     * 根据条件获取用户记录总数
     */
    public int getUserCntByCond(String userName, String userCode) throws Exception
    {
        StringBuilder condition = new StringBuilder("");
        Map parameter = new HashMap();

        if(StringUtils.isNotBlank(userCode)) {
            condition.append(IBOAIMonUserValue.S_UserCode).append(" like :userCode");
            parameter.put("userCode", "%" + userCode + "%");
        }
        if(StringUtils.isNotBlank(userName)) {
            if(StringUtils.isNotBlank(userCode)) {
                condition.append(" and ");
            }
            condition.append(IBOAIMonUserValue.S_UserName).append(" like :userName");
            parameter.put("userName", "%" + userName + "%");
        }
        if(StringUtils.isNotBlank(condition.toString())) {
            condition.append(" and ");
        }
        condition.append(" state = 'U' ");

        return BOAIMonUserEngine.getBeansCount(condition.toString(), parameter);
    }

    /**
     * 分页查询
     * 
     * @param userCode
     * @param userName
     * @param start
     * @param end
     * @return
     * @throws Exception
     */
    public IBOAIMonUserValue[] getUserInfoByCond(String userCode, String userName, int start, int end) throws Exception
    {
        IAIMonUserDAO userDao = (IAIMonUserDAO) ServiceFactory.getService(IAIMonUserDAO.class);
        IBOAIMonUserValue[] values = userDao.getUserInfoByCond(userCode, userName, start, end);
        if(values != null && values.length > 0 && values[0] != null) {
            for(int i = 0; i < values.length; i++) {
                values[i].setUserPass(K.k(values[i].getUserPass()));
            }
        }
        return values;
    }

    /**
     * 检查用户账号是否存在
     * 
     * @param userCode
     * @throws Exception
     */
    public boolean checkUserCodeExist(String userCode) throws Exception
    {
        IAIMonUserDAO userDao = (IAIMonUserDAO) ServiceFactory.getService(IAIMonUserDAO.class);
        return userDao.checkUserCodeExist(userCode);
    }

    /**
     * 获取所有用户信息
     * @return
     * @throws Exception
     */
    public IBOAIMonUserValue[] getAllUserBean() throws Exception
    {
        IAIMonUserDAO userDao = (IAIMonUserDAO) ServiceFactory.getService(IAIMonUserDAO.class);
        return userDao.getAllUser();
    }

    /**
     * 根据主键取得登录用户信息
     * 
     * @param userId
     * @return
     * @throws Exception
     */
    public IBOAIMonUserValue getBeanById(long userId) throws Exception
    {
        IAIMonUserDAO userDao = (IAIMonUserDAO) ServiceFactory.getService(IAIMonUserDAO.class);
        return userDao.getBeanById(userId);
    }

    /**
     * 批量保存或修改登录用户信息
     * 
     * @param values
     * @throws Exception
     */
    public void saveOrUpdate(IBOAIMonUserValue[] values) throws Exception
    {
        boolean modify = values[0].isModified();
        IAIMonUserDAO userDao = (IAIMonUserDAO) ServiceFactory.getService(IAIMonUserDAO.class);
        userDao.saveOrUpdate(values);
        for (int i = 0; i < values.length; i++) {
            if (modify) {
                UserGroup item = (UserGroup) MonCacheManager.get(AIMonUserRoleDomainCacheImpl.class, values[i].getUserId()
                        + "");
                if (item != null)
                    item.setEnable(false);
            } else {
                UserGroup item = this.wrapperUserRoleDomainByBean(values[i]);
                MonCacheManager.put(AIMonUserRoleDomainCacheImpl.class, item.getId(), item);
            }
        }
    }

    /**
     * 将用户值对象封装成用户角色域
     * @param value
     * @return
     * @throws Exception
     */
    public UserGroup wrapperUserRoleDomainByBean(IBOAIMonUserValue value) throws Exception
    {
        if (value == null || StringUtils.isBlank(value.getState()) || value.getState().equals("E"))
            return null;
        UserGroup result = new UserGroup();
        result.setId(value.getUserId() + "");
        result.setCode(value.getUserCode());
        result.setName(value.getUserName());
        result.setAllowChangePwd(value.getAllowChgPass());
        result.setEffectDate(value.getEffectDate());
        result.setExpireDate(value.getExpireDate());
        result.setLockFlag(value.getLockFlag());
        result.setMultLoginFlag(value.getMultiLoginFlag());
        result.setPassword(value.getUserPass());
        result.setTryTimes(value.getTryTimes());
        //用户关联组
        IAIMonUserGroupRelSV groupRelSV = (IAIMonUserGroupRelSV) ServiceFactory.getService(IAIMonUserGroupRelSV.class);
        IBOAIMonUserGroupRelValue groupRelValue = groupRelSV.getUserGroupRelatByUserId(result.getId());
        if (groupRelValue != null) {
            result.setGroupId(groupRelValue.getUserGroupId() + "");
        }
        result.setCacheListener(new AIMonUserRoleDomainCheckListener());
        return result;
    }
}
