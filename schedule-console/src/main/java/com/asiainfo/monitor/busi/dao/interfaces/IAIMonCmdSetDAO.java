package com.asiainfo.monitor.busi.dao.interfaces;

import java.util.Map;

import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonCmdSetValue;

/**
 * AppMonitor 远程脚本集配置DAO
 * @author lisong
 *
 */
public interface IAIMonCmdSetDAO {
	/**
	 * 通过主键查询脚本集信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCmdSetValue getCmdSetById(long id) throws Exception;
	
	/**
	 * 根据标识码读取命令集
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCmdSetValue getCmdSetByCode(String code) throws Exception;
	
	/**
	 * 根据标识码读取命令集
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCmdSetValue[] getCmdSetsByCode(String code) throws Exception;
	
	/**
	 * 通过自定义查询条件查询
	 * @param condition
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCmdSetValue[] query(String condition, Map parameter) throws Exception;
	
	/**
	 * 通过脚本集名称查询
	 * @param setName
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCmdSetValue[] getCmdSetByName(String setName) throws Exception;
	
	/**
	 * 保存、更新和删除记录
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonCmdSetValue[] values) throws Exception;
	
	/**
	 * 保存、更新和删除记录
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonCmdSetValue value) throws Exception;
	
	/**
	 * 删除命令集
	 * @param cmdSetId
	 * @throws Exception
	 */
	public void deleteCmdSet(long cmdSetId) throws Exception;
}
