package com.asiainfo.monitor.exeframe.configmgr.abo.dao.interfaces;

import java.util.List;
import java.util.Map;

import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonPhysicHostBean;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonPhysicHostValue;

public interface IAIMonPhysicHostDAO
{

    public long getBeanId() throws Exception;

    public BOAIMonPhysicHostBean getBeanById(long id) throws Exception;

    public void save(BOAIMonPhysicHostBean value) throws Exception;

    /** 
    * 根据条件查询物理主机
    * @param cond
    * @param para
    * @return
    * @throws Exception
    */
    public IBOAIMonPhysicHostValue[] queryPhysicHost(String cond, Map param) throws Exception;

    /** 
     * 根据条件查询物理主机信息
     * @param cond
     * @param para
     * @return
     * @throws Exception
     */
    public IBOAIMonPhysicHostValue[] queryPhost(String cond, Map para) throws Exception;

    /** 
     * 删除物理主机信息
     * @param hostId
     * @throws Exception
     */
    public void deletePhysicHost(long hostId) throws Exception;

    /** 
     * 保存或修改物理主机设置
     * @param value
     * @throws Exception
     */
    public long saveOrUpdate(IBOAIMonPhysicHostValue value) throws Exception;

    /** 
     * 批量保存或修改物理主机设置
     * @param values
     * @throws Exception
     */
    public void saveOrUpdate(IBOAIMonPhysicHostValue[] values) throws Exception;

    public IBOAIMonPhysicHostValue[] getAllPhostBean() throws Exception;

    /** 
     * 返回物理主机信息
     * @return
     * @throws Exception
     */
    public IBOAIMonPhysicHostValue[] getAllPhost() throws Exception;

    /** 
     * 根据物理主机名称和ip查询主机信息
     * @param name
     * @param ip
     * @return
     * @throws Exception
     */
    public IBOAIMonPhysicHostValue[] getPhysicHostByNameAndIp(String name, String ip) throws Exception;

    /** 
     * 根据物理主机名称查询主机信息
     * @param name
     * @return
     * @throws Exception
     */
    public IBOAIMonPhysicHostValue[] getPhysicHostByName(String name) throws Exception;

    /** 
     * 根据标识，读取物理主机信息
     * @param hostId
     * @return
     * @throws Exception
     */
    public IBOAIMonPhysicHostValue getPhysicHostById(String hostId) throws Exception;

    /**
     * 判断主机编码是否存在
     * 
     * @param request
     * @throws Exception
     * @author szh
     */
    public boolean isExistHostByCode(String groupId, String hostCode) throws Exception;

    /**
     * 判断主机名称是否存在
     * 
     * @param request
     * @throws Exception
     * @author szh
     */
    public boolean isExistHostByName(String groupId, String hostName) throws Exception;

    /**
     * 通过应用编码列表查询主机信息
     * 
     * @param request
     * @throws Exception
     */
    public Map<String, IBOAIMonPhysicHostValue> qryHostByAppCode(List<String> list) throws Exception;

	/**
	 * 根据调价查询主机信息
	 * @param groupCode
	 * @param hostId
	 * @param serverCode
	 * @return
	 */
	public Map<String, IBOAIMonPhysicHostValue> qryHostInfoByCondition(
            String groupCode, String hostId, String serverCode)  throws  Exception;

    /**
     * 根据ip查询主机信息
     * 
     * @param request
     * @throws Exception
     */
    public IBOAIMonPhysicHostValue[] qryHostInfoByIp(String ip) throws Exception;

}
