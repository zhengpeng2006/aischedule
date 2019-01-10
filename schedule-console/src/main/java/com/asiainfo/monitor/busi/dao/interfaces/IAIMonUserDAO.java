package com.asiainfo.monitor.busi.dao.interfaces;

import java.util.Map;

import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserValue;

public interface IAIMonUserDAO {

	/**
	 * 根据条件检索登录用户信息
	 * 
	 * @param condition
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonUserValue[] query(String condition, Map parameter) throws Exception;
	
	/**
	 * 读取所有正常状态用户
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonUserValue[] getAllUser() throws Exception;
	
	/**
	 * 根据用户代码读取用户信息
	 * @param userCode
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonUserValue[] getMonUserBeanByCode(String userCode) throws Exception;
	/**
     * 根据用户Code检索用户信息
     * 
     * @param userCode
     * @return IBOAIMonUserValue
     * @throws Exception
     */
    public IBOAIMonUserValue getUserInfoByCode(String userCode) throws Exception;

    /**
     * 删除用户信息
     * 
     * @param userId
     * @throws Exception
     */
    public void delete(long userId) throws Exception;

    /**
     * 保存或修改用户信息
     * 
     * @param value
     * @throws Exception
     */
    public void saveOrUpdate(IBOAIMonUserValue value) throws Exception;

    /**
     * 根据条件检索用户信息
     * 
     * @param userName
     * @param userCode
     * @return
     * @throws Exception
     */
    public IBOAIMonUserValue[] getUserInfoByCond(String userCode, String userName) throws Exception;

    /**
     * 根据条件检索用户信息
     * 
     * @param userName
     * @param userCode
     * @return
     * @throws Exception
     */
    public IBOAIMonUserValue[] getUserInfoByCond(String userCode, String userName, int start, int end) throws Exception;

    /**
     * 检查用户账号是否存在
     * 
     * @param userCode
     * @throws Exception
     */
    public boolean checkUserCodeExist(String userCode) throws Exception;

    /**
     * 根据主键取得登录用户信息
     * 
     * @param userId
     * @return
     * @throws Exception
     */
    public IBOAIMonUserValue getBeanById(long userId) throws Exception;

    /**
     * 批量保存或修改用户信息
     * 
     * @param values
     * @throws Exception
     */
    public void saveOrUpdate(IBOAIMonUserValue[] values) throws Exception;
}
