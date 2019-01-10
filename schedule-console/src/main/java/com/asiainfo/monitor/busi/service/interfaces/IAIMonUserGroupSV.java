package com.asiainfo.monitor.busi.service.interfaces;

import com.asiainfo.monitor.busi.cache.impl.GroupRole;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserGroupValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserValue;

public interface IAIMonUserGroupSV {

	/**
     * 保存或修改用户组信息
     * 
     * @param value
     * @throws Exception
     */
    public void saveOrUpdate(IBOAIMonUserGroupValue value) throws Exception;

    /**
     * 批量保存或修改用户组信息
     * 
     * @param values
     * @throws Exception
     */
    public void saveOrUpdate(IBOAIMonUserGroupValue[] values) throws Exception;

    /**
     * 将用户组值对象封装
     * @param value
     * @return
     * @throws Exception
     */
    public GroupRole wrapperUserGroupByBean(IBOAIMonUserGroupValue value) throws Exception;

    /**
     * 根据用户组标识读取用户组信息
     * @param id
     * @return
     * @throws Exception
     */
    public GroupRole getUserGroupById(String id) throws Exception;

    /**
     * 根据条件取得用户组信息
     * 
     * @param groupCode
     * @param groupName
     * @return
     * @throws Exception
     */
    public IBOAIMonUserGroupValue[] getUserGroupByCond(String groupCode, String groupName) throws Exception;

    /**
     * 根据条件取得用户组记录总数
     * 
     * @param groupCode
     * @param groupName
     * @return
     * @throws Exception
     */
    public int getUserGroupCntByCond(String groupCode, String groupName) throws Exception;

    /**
     * 根据条件取得用户组信息
     * 
     * @param groupCode
     * @param groupName
     * @return
     * @throws Exception
     */
    public IBOAIMonUserGroupValue[] getUserGroupByCond(String groupCode, String groupName, int start, int end) throws Exception;

    /**
     * 删除用户组信息
     * 
     * @param userGroupId
     * @throws Exception
     */
    public void delete(long userGroupId) throws Exception;

    /**
     * 从数据库读取用户组对象并封装
     * @param id
     * @return
     * @throws Exception
     */
    public GroupRole getUserGroupByIdFromDb(String id) throws Exception;

    /**
     * 读取所有用户的组信息
     * @return
     * @throws Exception
     */
    public IBOAIMonUserGroupValue[] getAllUserGroupBean() throws Exception;

    /**
     * 根据主键取得用户组信息
     * 
     * @param userGroupId
     * @return
     * @throws Exception
     */
    public IBOAIMonUserGroupValue getBeanById(long userGroupId) throws Exception;

    /**
     * 根据组标识获取所有用户信息
     * 
     * @param userGroupId
     * @return
     * @throws Exception
     */
    public IBOAIMonUserValue[] getUserListByGroupId(long userGroupId) throws Exception;

    /**
     * 查询未绑定该用户组的用户信息
     * 
     * @param userGroupId
     * @return
     * @throws Exception
     */
    public IBOAIMonUserValue[] getRelUserListByGroupId(long userGroupId) throws Exception;
    
    /**
     * 查询未绑定该用户组的用户总数，便于分页
     * 
     * @param userGroupId
     * @return
     * @throws Exception
     */
    public long getRelUserListCntByGroupId(long userGroupId) throws Exception;
    
}
