package com.asiainfo.monitor.busi.exe.task.dao.interfaces;

import java.util.Map;

import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPInfoValue;

public interface IAIMonPInfoDAO {

	
	/**
	 * 根据条件查询任务信息
	 * @param condition
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPInfoValue[] query(String condition, Map parameter) throws Exception;
	
	/**
	 * 根据条件查询任务信息
	 * 分页
	 * @param cond
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPInfoValue[] query(String cond, Map param, Integer startNum, Integer endNum) throws Exception;
	
	/**
	 * 根据主键取得任务信息
	 * 
	 * @param infoId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPInfoValue getBeanById(long infoId) throws Exception;
	
	/**
	 * 根据条件查询任务信息
	 * 
	 * @param taskName
	 * @param startNum
	 * @param endNum
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPInfoValue[] getTaskInfoByName(String taskName, String state, Integer startNum, Integer endNum) throws Exception;
	
	/**
	 * 根据数据分片规则读取数据
	 * @param split
	 * @param mod
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPInfoValue[] getMonPInfoValuesBySplitAndMod(long split, long mod, long value) throws Exception;
	
	/**
	 * 根据任务名、状态读取任务数
	 * @param taskName
	 * @param state
	 * @return
	 * @throws Exception
	 */
	public int getTaskInfoCount(String taskName, String state) throws Exception;
	
	/**
	 * 根据解析分组标识读取任务数
	 * @param resolveId
	 * @return
	 * @throws Exception
	 */
	public int getTaskInfoCountByResolveId(long resolveId) throws Exception;
	
	/**
	 * 根据条件获得任务数目
	 * 
	 * @param condition
	 * @param startIndex
	 * @param endIndex
	 * @return
	 * @throws Exception
	 */
//	public long countMonPInfoByCondition(String condition) throws Exception;
	
	/**
	 * 批量保存或修改任务信息
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonPInfoValue[] values) throws Exception;
	
	/**
	 * 保存或修改任务信息
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonPInfoValue value) throws Exception;
	
	/**
	 * 删除任务信息
	 * 
	 * @param infoId
	 * @throws Exception
	 */
	public void delete(long infoId) throws Exception;

	/**
	 * 通过组查询组下面的任务信息
	 * @param resolveId
	 * @throws Exception
	 */
	public IBOAIMonPInfoValue[]  getTaskInfoByResolveId(String resolveId)throws Exception;

	/**
	 * 通过分组信息查询要显示的监控记录
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPInfoValue[] getTaskInfoByGroupId(String groupId) throws Exception;
	
	/**
	 * 通过分组信息查询要显示的监控记录，分页显示
	 * @param itemID
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPInfoValue[] queryTaskInfoByCond(String groupId, int startIndex, int endIndex)throws Exception;
	
	/**
	 * 
	 * @param groupID
	 * @return
	 * @throws Exception
	 */
	public int getTaskInfoCountByCond(String groupId) throws Exception;

	
	/**
	 * 根据分组编号和任务名查找任务
	 * @param groupId
	 * @param taskName
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPInfoValue[] queryTaskInfoByGrpAndInfoName(String groupId, String taskName, int startIndex, int endIndex) throws Exception;
	
	/**
	 * 根据分组编号和任务名查找任务个数
	 * @param groupId
	 * @param taskName
	 * @return
	 * @throws Exception
	 */
	public int getTaskInfoCountByGrpAndInfoName(String groupId, String taskName) throws Exception;
	/**
	 * 根据对象类型以及对象标识获取任务
	 * @param mType
	 * @param typeId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPInfoValue[] getTaskByType(String mType, long typeId)throws Exception;

    /**
     * 根据当前主机的ip获取当前主机的pinfo
     * 
     * @param request
     * @throws Exception
     */
    public IBOAIMonPInfoValue[] getPInfoByLocalIp() throws Exception;
}
