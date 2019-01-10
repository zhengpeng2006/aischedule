package com.asiainfo.monitor.busi.exe.task.service.interfaces;


import java.rmi.RemoteException;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.DataContainerInterface;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPInfoValue;

public interface IAIMonPInfoSV {

	
	/**
	 * 根据执行类型查询指定类型所有任务信息
	 * @param taskMethod
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPInfoValue[] getAllTaskInfo(String taskMethod) throws RemoteException,Exception;
	
	/**
	 * 根据数据分片规则读取数据
	 * @param split
	 * @param mod
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPInfoValue[] getMonPInfoValuesBySplitAndMod(long split, long mod, long value) throws RemoteException,Exception;
	
	/**
	 * 根据条件查询任务信息
	 * 
	 * @param cond
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPInfoValue[] getTaskInfoByName(String taskName, String state, Integer startNum, Integer endNum) throws RemoteException,Exception;
	
	/**
	 * 根据任务名、状态读取任务数
	 * @param taskName
	 * @param state
	 * @return
	 * @throws Exception
	 */
	public int getTaskInfoCount(String taskName, String state) throws RemoteException,Exception;
	
	/**
	 * 根据解析分组标识，读取任务数
	 * @param resolveId
	 * @return
	 * @throws Exception
	 */
	public int getTaskInfoCountByResolveId(long resolveId) throws RemoteException,Exception;
	
	/**
	 * 根据主键读取任务Bean
	 * @param infoId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPInfoValue getMonPInfoValue(long infoId) throws RemoteException,Exception;
	
	/**
	 * 根据参数读取任务Bean 不需要的参数传-1
	 * @param groupId 	      分组主键
	 * @param timeId  	      监控时间主键
	 * @param thresholdId 监控阀值主键
	 * @param typeId      进程监控配置表主键
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPInfoValue[] getMonPInfoValueByParams(long groupId, long timeId, long thresholdId, long typeId) throws RemoteException,Exception;
	
	/**
	 * 根据条件获得任务记录数目
	 * 
	 * @param sqlCondition
	 * @return
	 * @throws Exception
	 */
//	public long countMonPInfoByCondition(String sqlCondition) throws Exception;

	/**
	 * 批量保存或修改任务信息
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonPInfoValue[] values) throws RemoteException,Exception;
	
	/**
	 * 保存或修改任务信息
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonPInfoValue value) throws RemoteException,Exception;
	

	/**
	 * 保存任务信息与参数信息
	 * @param infoValue
	 * @param paramDcs
	 * @throws RemoteException
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonPInfoValue infoValue, DataContainer[] paramDcs) throws RemoteException,Exception;
	
	/**
	 * 删除任务信息
	 * 
	 * @param infoId
	 * @throws Exception
	 */
	public void delete(long infoId) throws RemoteException,Exception;

	/**
	 * 通过组查询组下面的任务信息
	 * @param resolveId
	 * @throws Exception
	 */
	public IBOAIMonPInfoValue[] getTaskInfoByResolveId(String resolveId) throws RemoteException,Exception;
	
	/**
	 * 通过分组信息查询要显示的监控记录
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	public DataContainerInterface[] getTaskInfoByGroupId(String groupId) throws RemoteException,Exception;
	
	/**
	 * 通过分组信息查询要显示的监控记录,分页显示
	 * @param groupID
	 * @return
	 * @throws Exception
	 */
	public DataContainerInterface[] queryTaskInfoByCond(String groupId, int startIndex, int endIndex) throws RemoteException,Exception;
	
	/**
	 * 通过分组信息查询要显示的监控记录数
	 * @param groupID
	 * @return
	 * @throws Exception
	 */
	public int getTaskInfoCountByCond(String groupId) throws RemoteException,Exception;

	/**
	 * 根据分组编号和任务名查找任务
	 * @param groupId
	 * @param taskName
	 * @return
	 * @throws Exception
	 */
	public DataContainerInterface[] queryTaskInfoByGrpAndInfoName(String groupId, String taskName, int startIndex, int endIndex) throws RemoteException,Exception;
	
	/**
	 * 根据分组编号和任务名查找任务个数
	 * @param groupId
	 * @param taskName
	 * @return
	 * @throws Exception
	 */
	public int getTaskInfoCountByGrpAndInfoName(String groupId, String taskName) throws RemoteException,Exception;
	
	/**
	 * 根据任务分组ID，同步层信息
	 * @param groupId
	 * @param layer
	 * @throws Exception
	 */
	public void updateLayer(long groupId, String layer) throws RemoteException,Exception;

	/**
	 * 将指定具体任务设置成执行完成
	 * 在任务执行方式为I(立即执行)或F(固定时间执行一次)时,该任务执行完毕后会将状态设置成F
	 * @param infoId
	 * @throws RemoteException
	 * @throws Exception
	 */
	public void updateState(long infoId) throws RemoteException,Exception;
	
	/**
	 * 根据对象类型以及对象标识获取任务
	 * @param mType
	 * @param typeId
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public IBOAIMonPInfoValue[] getTaskByType(String mType, long typeId)throws RemoteException,Exception;
	
	/**
	 * 屏蔽任务
	 * 将任务状态设置为S
	 * @param infoIds
	 * @throws RemoteException
	 * @throws Exception
	 */
	public void shieldInfoByInfoIds(Object[] infoIds) throws RemoteException,Exception;
	
	/**
	 * 根据任务名和编号查询
	 * @param name
	 * @param code
	 * @param startIndex
	 * @param endIndex
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public IBOAIMonPInfoValue[] getInfosByNameAndCode(String name, String code, int startIndex, int endIndex) throws RemoteException,Exception;

    public IBOAIMonPInfoValue[] getPInfoByLocalIp() throws Exception;

}
