package com.asiainfo.monitor.busi.dao.interfaces;

import java.util.Map;

import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonCmdValue;

/**
 * AppMonitor所用shell脚本的数据配置dao
 * @author lisong
 *
 */
public interface IAIMonCmdDAO {
	/**
	 * 通过主键查询
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCmdValue queryById(long id) throws Exception;
	
	/**
	 * 通过自定义查询条件查询
	 * @param condition
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCmdValue[] query(String condition, Map parameter) throws Exception;
	
	/**
	 * 通过shell脚本名查询脚本信息
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCmdValue[] queryByName(String name) throws Exception;
	
	/**
	 * 保存或者更新数据库
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonCmdValue value) throws Exception;
	
	/**
	 * 保存或者更新数据库
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonCmdValue[] values) throws Exception;
	
	/**
	 * 删除命令
	 * @param cmdId
	 * @throws Exception
	 */
	public void deleteCmd(long cmdId) throws Exception;

	/**
	 * 通过类型查询脚本信息集
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCmdValue[] queryCmdByType(String type) throws Exception;
	
	/**
	 * 获取命令主键
	 * @return
	 * @throws Exception
	 */
	public long getCmdId() throws Exception;
}
