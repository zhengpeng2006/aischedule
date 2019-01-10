package com.asiainfo.monitor.busi.service.impl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ai.appframe2.service.ServiceFactory;
import com.ai.appframe2.util.criteria.Criteria;
import com.asiainfo.monitor.busi.cache.MonCacheManager;
import com.asiainfo.monitor.busi.cache.impl.AIMonUserRoleDomainCacheImpl;
import com.asiainfo.monitor.busi.cache.impl.UserGroup;
import com.asiainfo.monitor.busi.dao.interfaces.IAIMonUserGroupRelDAO;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonUserGroupRelSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonUserSV;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonUserGroupRelBean;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonUserGroupRelEngine;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserGroupRelValue;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class AIMonUserGroupRelSVImpl implements IAIMonUserGroupRelSV{

	/**
	 * 根据主键取得用户与组关系
	 * 
	 * @param relateId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonUserGroupRelValue getBeanById(long relateId) throws RemoteException,Exception {
		IAIMonUserGroupRelDAO groupRelDao = (IAIMonUserGroupRelDAO)ServiceFactory.getService(IAIMonUserGroupRelDAO.class);
		return groupRelDao.getBeanById(relateId);
	}
	
	/**
	 * 根据组ID取得该组的用户ID
	 * 
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonUserGroupRelValue[] getUserIdByGroupId(String groupId) throws RemoteException,Exception {
		IAIMonUserGroupRelDAO groupRelDao = (IAIMonUserGroupRelDAO)ServiceFactory.getService(IAIMonUserGroupRelDAO.class);
		return groupRelDao.getUserIdByGroupId(groupId);
	}
	
	/**
	 * 根据用户标识获取用户与组的关联关系
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonUserGroupRelValue getUserGroupRelatByUserId(String userId) throws RemoteException,Exception{
		if (StringUtils.isBlank(userId))
			// 标识为空
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000098"));
		IAIMonUserGroupRelDAO groupRelDao = (IAIMonUserGroupRelDAO)ServiceFactory.getService(IAIMonUserGroupRelDAO.class);
		return groupRelDao.getUserGroupRelatByUserId(userId);
	}
	
	/**
	 * 保存或修改用户与组关系
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonUserGroupRelValue value) throws RemoteException,Exception {
		IAIMonUserGroupRelDAO groupRelDao = (IAIMonUserGroupRelDAO)ServiceFactory.getService(IAIMonUserGroupRelDAO.class);
		groupRelDao.saveOrUpdate(value);
		UserGroup item=(UserGroup)MonCacheManager.get(AIMonUserRoleDomainCacheImpl.class,value.getUserId()+"");
		if (item!=null)
			item.setEnable(false);
	}
	
	/**
	 * 批量保存或修改用户与组关系
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonUserGroupRelValue[] values) throws RemoteException,Exception {
		IAIMonUserGroupRelDAO groupRelDao = (IAIMonUserGroupRelDAO)ServiceFactory.getService(IAIMonUserGroupRelDAO.class);
		groupRelDao.saveOrUpdate(values);
		for (int i=0;i<values.length;i++){
			UserGroup item=(UserGroup)MonCacheManager.get(AIMonUserRoleDomainCacheImpl.class,values[i].getUserId()+"");
			if (item!=null)
				item.setEnable(false);
		}
	}
	
	/**
	 * 保存用户组与用户的关系
	 * 将用户组缓存设置为不可用
	 * @param oldValues
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonUserGroupRelValue[] oldValues ,IBOAIMonUserGroupRelValue[] values, Object[] userIds) throws RemoteException,Exception {
		IAIMonUserGroupRelDAO groupRelDao = (IAIMonUserGroupRelDAO)ServiceFactory.getService(IAIMonUserGroupRelDAO.class);
		List users=new ArrayList();
		if (oldValues != null && oldValues.length > 0) {
			groupRelDao.saveOrUpdate(oldValues);
			for (int i=0;i<oldValues.length;i++){
				if (!users.contains(oldValues[i].getUserId()+"")){
					users.add(oldValues[i].getUserId()+"");
				}
			}
		}
		
		IAIMonUserSV userSV = (IAIMonUserSV)ServiceFactory.getService(IAIMonUserSV.class);
		if (userIds != null && userIds.length > 0) {
			for (int i = 0; i < userIds.length; i++) {
				String userId = userIds[i].toString();
				boolean checkFlg = this.checkUserGroupRelByUserId(userId);
				if (checkFlg) {
					String userName = userSV.getBeanById(Long.parseLong(userId)).getUserName();
					//"该用户[" + userName + "]已经指定用户组"
					throw new Exception(AIMonLocaleFactory.getResource("MOS0000326", userName));
				}
			}
		}
		
		if (values != null && values.length > 0) {
			groupRelDao.saveOrUpdate(values);
			for (int i=0;i<values.length;i++){
				if (!users.contains(values[i].getUserId()+"")){
					users.add(values[i].getUserId()+"");
				}
			}
		}
		if (users.size()>0){
			for (int i=0;i<users.size();i++){
				UserGroup item=(UserGroup)MonCacheManager.get(AIMonUserRoleDomainCacheImpl.class,users.get(i));
				if (item!=null)
					item.setEnable(false);
			}
		}
		
	}
	
	/**
	 * 删除用户与组关系
	 * 
	 * @param relateId
	 * @throws Exception
	 */
	public void delete(long relateId) throws RemoteException,Exception {
		IAIMonUserGroupRelDAO groupRelDao = (IAIMonUserGroupRelDAO)ServiceFactory.getService(IAIMonUserGroupRelDAO.class);
		groupRelDao.delete(relateId);
		IBOAIMonUserGroupRelValue value=this.getBeanById(relateId);
		UserGroup item=(UserGroup)MonCacheManager.get(AIMonUserRoleDomainCacheImpl.class,value.getUserId()+"");
		if (item!=null)
			item.setEnable(false);
	}
	
	/**
	 * 检查该用户是否已经加入用户组
	 * @param userId
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
    public boolean checkUserGroupRelByUserId(String userId) throws RemoteException, Exception
    {
        IAIMonUserGroupRelDAO groupRelDao = (IAIMonUserGroupRelDAO) ServiceFactory.getService(IAIMonUserGroupRelDAO.class);
		return groupRelDao.checkUserGroupRelByUserId(userId);
	}

    /**
     * 检查该用户是否已经加入用户组
     * 
     * @param userId
     * @return
     * @throws RemoteException
     * @throws Exception
     */
    public boolean checkUserGroupRelByUserId(String userId, String userGroupId) throws RemoteException, Exception
    {
        IAIMonUserGroupRelDAO groupRelDao = (IAIMonUserGroupRelDAO) ServiceFactory.getService(IAIMonUserGroupRelDAO.class);
        return groupRelDao.checkUserGroupRelByUserId(userId);
    }

    /**
     * 根据组和用户标识删除信息
     * 
     * @param userGroupId
     * @param userId
     */
    public boolean delete(long userGroupId, long userId) throws Exception
    {
        Criteria sql = new Criteria();
        sql.addEqual("user_group_id", String.valueOf(userGroupId));
        sql.addEqual("user_id", String.valueOf(userId));

        IAIMonUserGroupRelDAO groupRelDao = (IAIMonUserGroupRelDAO) ServiceFactory.getService(IAIMonUserGroupRelDAO.class);
        BOAIMonUserGroupRelBean[] beans = BOAIMonUserGroupRelEngine.getBeans(sql);
        
        for(BOAIMonUserGroupRelBean bean : beans) {
            groupRelDao.delete(bean.getRelateId());
        }

        return true;
    }
	
}
