package com.asiainfo.monitor.busi.service.interfaces;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import com.ailk.common.data.IDataBus;
import com.asiainfo.monitor.busi.cache.impl.PhysicHost;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonPhysicHostValue;

public interface IAIMonPhysicHostSV
{

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
     * 根据组标识从数据库读取组信息并封装
     * @param id
     * @return
     * @throws Exception
     */
    public PhysicHost getPhostByIdFromDb(String id) throws Exception;

    /**
     * 返回所有物理主机信息值对象
     * @return
     * @throws Exception
     */
    public IBOAIMonPhysicHostValue[] getAllPhostBean() throws Exception;

    /**
     * 批量保存或修改物理主机设置
     * @param values
     * @throws Exception
     */
    public void saveOrUpdate(IBOAIMonPhysicHostValue[] values) throws Exception;

    /**
     * 根据主机标识，从缓存读取物理主机信息
     * @param phostId
     * @return
     * @throws RemoteException
     * @throws Exception
     */
    public PhysicHost getPhostByPhostId(String phostId) throws Exception;

    /**
     * 返回所有组信息
     * 不考虑缓存未加载情况
     * @return
     * @throws Exception
     */
    public List getAllPhost() throws Exception;

    /**
     * 根据组标识和用户标识，读取节点拓扑
     * @param groupId
     * @param userId,用户标识暂且不用
     * @return
     * @throws Exception
     */
    public String getHostTopuXmlByGroupIdAndUserId(String groupId, String userId) throws Exception;

    /**
     * 根据物理主机名称和IP查询主机信息
     * @param name
     * @param ip
     * @return
     * @throws Exception
     */
    public IBOAIMonPhysicHostValue[] getPhysicHostByNameAndIp(String name, String ip) throws Exception;

    /**
     * 将值对象简单封装
     * @param value
     * @return
     * @throws Exception
     */
    public PhysicHost wrapperPhysicHostByBean(IBOAIMonPhysicHostValue value) throws Exception;

    /**
     * 根据物理主机名称查询主机信息
     * @param name
     * @return
     * @throws Exception
     */
    public IBOAIMonPhysicHostValue[] getPhysicHostByName(String name) throws Exception;

    /**
     * 根据服务器标识，读取物理主机信息
     * @param HostId
     * @return
     * @throws Exception
     */
    public IBOAIMonPhysicHostValue getPhysicHostById(String hostId) throws Exception;

    /**
     * 根据主机标识的列表，查询物理主机信息
     * 
     * @param request
     * @throws Exception
     * @author szh
     */
    public IBOAIMonPhysicHostValue[] qryByList(List<String> list) throws Exception;

    /**
     * 查询主机列表
     * 
     * @param request
     * @throws Exception
     * @author szh
     */
    public IDataBus getHostList() throws Exception;

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
	 * 根据条件查询主机信息
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
