package com.asiainfo.monitor.busi.service.impl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import com.ai.appframe2.service.ServiceFactory;
import com.ai.appframe2.util.criteria.Criteria;
import com.asiainfo.monitor.busi.cache.MonCacheManager;
import com.asiainfo.monitor.busi.cache.impl.AIMonUserGroupCacheImpl;
import com.asiainfo.monitor.busi.cache.impl.GroupRole;
import com.asiainfo.monitor.busi.dao.interfaces.IAIMonUserGroupRoleDAO;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonUserGroupRoleSV;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonUserGroupRoleRelBean;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonUserGroupRoleRelEngine;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserGroupRoleRelValue;

public class AIMonUserGroupRoleSVImpl implements IAIMonUserGroupRoleSV{

	/**
	 * 根据主键取得用户组角色关系信息
	 * 
	 * @param relateId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonUserGroupRoleRelValue getBeanById(long relateId) throws RemoteException,Exception {
		IAIMonUserGroupRoleDAO groupRoleDao = (IAIMonUserGroupRoleDAO)ServiceFactory.getService(IAIMonUserGroupRoleDAO.class);
		return groupRoleDao.getBeanById(relateId);
	}
	
	/**
	 * 根据组ID取得对应的角色ID
	 * 
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonUserGroupRoleRelValue[] getRoleIdByGroupId(String groupId) throws RemoteException,Exception {
		IAIMonUserGroupRoleDAO groupRoleDao = (IAIMonUserGroupRoleDAO)ServiceFactory.getService(IAIMonUserGroupRoleDAO.class);
		return groupRoleDao.getRoleIdByGroupId(groupId);
	}
	
	/**
	 * 保存或修改用户组角色关系信息
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonUserGroupRoleRelValue value) throws RemoteException,Exception {
		IAIMonUserGroupRoleDAO groupRoleDao = (IAIMonUserGroupRoleDAO)ServiceFactory.getService(IAIMonUserGroupRoleDAO.class);
		groupRoleDao.saveOrUpdate(value);
		GroupRole item=(GroupRole)MonCacheManager.get(AIMonUserGroupCacheImpl.class,value.getUserGroupId()+"");
		if (item!=null)
			item.setEnable(false);
	}
	
	/**
	 * 批量保存或修改用户组角色关系信息
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonUserGroupRoleRelValue[] values) throws RemoteException,Exception {
		IAIMonUserGroupRoleDAO groupRoleDao = (IAIMonUserGroupRoleDAO)ServiceFactory.getService(IAIMonUserGroupRoleDAO.class);
		groupRoleDao.saveOrUpdate(values);
		for (int i=0;i<values.length;i++){
			GroupRole item=(GroupRole)MonCacheManager.get(AIMonUserGroupCacheImpl.class,values[i].getUserGroupId()+"");
			if (item!=null)
				item.setEnable(false);
		}
	}
	
	/**
	 * 保存用户组和角色关系信息
	 * 
	 * @param oldValues
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonUserGroupRoleRelValue[] oldValues, IBOAIMonUserGroupRoleRelValue[] values) throws RemoteException,Exception {
		IAIMonUserGroupRoleDAO groupRoleDao = (IAIMonUserGroupRoleDAO)ServiceFactory.getService(IAIMonUserGroupRoleDAO.class);
		List groups=new ArrayList();
		if (oldValues != null && oldValues.length > 0) {
			groupRoleDao.saveOrUpdate(oldValues);
			for (int i=0;i<oldValues.length;i++){
				if (!groups.contains(oldValues[i].getUserGroupId()+"")){
					groups.add(oldValues[i].getUserGroupId()+"");
				}
			}
		}
		if (values != null && values.length > 0) {
			groupRoleDao.saveOrUpdate(values);
			for (int i=0;i<values.length;i++){
				if (!groups.contains(values[i].getUserGroupId()+"")){
					groups.add(values[i].getUserGroupId()+"");
				}
			}
		}
		
		if (groups.size()>0){
			for (int i=0;i<groups.size();i++){
				GroupRole item=(GroupRole)MonCacheManager.get(AIMonUserGroupCacheImpl.class,groups.get(i));
				if (item!=null)
					item.setEnable(false);
			}
		}
	}

	/**
	 * 删除用户组角色关系信息
	 * 
	 * @param relateId
	 * @throws Exception
	 */
	public void delete(long relateId) throws RemoteException,Exception {
		IBOAIMonUserGroupRoleRelValue value=this.getBeanById(relateId);
		if (value==null)
			return;
		IAIMonUserGroupRoleDAO groupRoleDao = (IAIMonUserGroupRoleDAO)ServiceFactory.getService(IAIMonUserGroupRoleDAO.class);
		groupRoleDao.delete(relateId);		
		GroupRole item=(GroupRole)MonCacheManager.get(AIMonUserGroupCacheImpl.class,relateId+"");
		if (item!=null)
			item.setEnable(false);
	}
	
    /**
     * 根据组和用户标识删除信息
     * 
     * @param userGroupId
     * @param userId
     */
    public boolean delete(long userGroupId, long userRoleId) throws Exception
    {
        Criteria sql = new Criteria();
        sql.addEqual("user_group_id", String.valueOf(userGroupId));
        sql.addEqual("user_role_id", String.valueOf(userRoleId));

        IAIMonUserGroupRoleDAO groupRelDao = (IAIMonUserGroupRoleDAO) ServiceFactory.getService(IAIMonUserGroupRoleDAO.class);
        BOAIMonUserGroupRoleRelBean[] beans = BOAIMonUserGroupRoleRelEngine.getBeans(sql);

        for(BOAIMonUserGroupRoleRelBean bean : beans) {
            groupRelDao.delete(bean.getRelatId());
        }

        return true;
    }

    /**
     * 检查该角色是否已经加入用户组
     * 
     * @param userId
     * @return
     * @throws RemoteException
     * @throws Exception
     */
    public boolean checkUserGroupRoleRelByUserId(String userRoleId, String userGroupId) throws RemoteException, Exception
    {

        IAIMonUserGroupRoleDAO groupRoleRelDao = (IAIMonUserGroupRoleDAO) ServiceFactory.getService(IAIMonUserGroupRoleDAO.class);
        return groupRoleRelDao.checkUserGroupRoleRelByUserId(userRoleId, userGroupId);
    }

}
