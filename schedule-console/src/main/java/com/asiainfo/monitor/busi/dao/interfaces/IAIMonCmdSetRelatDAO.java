package com.asiainfo.monitor.busi.dao.interfaces;

import java.util.Map;

import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonCmdSetRelatValue;

/**
 * AppMonitor 命令与命令集关系DAO
 * @author lisong
 *
 */
public interface IAIMonCmdSetRelatDAO {
	/**
	 * 通过命令ID和命令集ID查询关系
	 * @param cmdId
	 * @param cmdSetId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCmdSetRelatValue getCmdSetRelatByIdAndCmdId(long cmdSetId, long cmdId) throws Exception;
	
	/**
	 * 通过命令ID查询关系
	 * @param cmdId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCmdSetRelatValue[] getCmdSetRelatByCmdId(long cmdId) throws Exception;
	
	/**
	 * 通过命令集ID查询关系
	 * @param setId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCmdSetRelatValue[] getCmdSetRelatByCmdSetIdOrderBySort(long cmdSetId) throws Exception;
	
	/**
	 * 通过自定义查询条件查询以
	 * @param condition
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCmdSetRelatValue[] query(String condition, Map parameter) throws Exception;
	
	/**
	 * 保存、更新和删除记录
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonCmdSetRelatValue value) throws Exception;
	
	/**
	 * 保存、更新和删除记录
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonCmdSetRelatValue[] values) throws Exception;
}
