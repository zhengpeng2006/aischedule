package com.asiainfo.monitor.busi.exe.task.dao.interfaces;

import java.util.Date;
import java.util.Map;

import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonLRecordValue;

public interface IAIMonLRecordDAO {
	
	/**
	 * 根据条件查询监控记录信息
	 * 
	 * @param condition
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonLRecordValue[] query(String condition, Map parameter) throws Exception;

	
	  
	  /**
	   * 根据任务标识、开始日期，结束日期读取监控结果
	   * @param infoId
	   * @param layer
	   * @param beginDate
	   * @param endDate
	   * @return
	   * @throws Exception
	   */
	  public IBOAIMonLRecordValue[] getMonLRecord(long infoId, String layer, String beginDate, String endDate) throws Exception;
	  
	  /**
	   * 根据任务标识、层、读取最新监控结果
	   * @param infoId
	   * @param layer
	   * @return
	   * @throws Exception
	   */
	  public IBOAIMonLRecordValue getLastMonLRecordById(long infoId, String layer) throws Exception;
	  
	  /**
	   * 根据任务标识、层、时间间隔t（单位小时）获取昨天和今天从当前时间往前推t小时内的两条数据
	   * 不支持跨月
	   * @param infoId
	   * @param layer
	   * @param durHour
	   * @return
	   * @throws Exception
	   */
	  public IBOAIMonLRecordValue[] getMonLRecord(long infoId, String layer, int durHour) throws Exception;
	/**
	 * 获得监控图形数据
	 *
	 * @param infoId long[]
	 * @param transformClass String
	 * @param startDate Date
	 * @param endDate Date
	 * @return HashMap
	 * @throws Exception
	 */
	public IBOAIMonLRecordValue[] getMonLRecordImage(Object[] infoIds, String layer, String viewTpyeId, Date startDate, Date endDate) throws Exception;
	  
	 /**
	   * 获得监控表格数据
	   * @param layer
	   * @param infocode
	   * @param startDate Date
	   * @param endDate Date
	   * @return IBOAIMonLRecordValue
	   * @throws Exception
	   * */
	  public IBOAIMonLRecordValue[] getMonLRecordByCode(String layer, String infocode) throws Exception;
	  
	  /**
	   * 获取Batch_ID
	   * @return
	   * @throws Exception
	   */
	  public long getBatchId() throws Exception;
	/**
	 * 批量保存或修改监控结果
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonLRecordValue[] values) throws Exception;
	
	/**
	 * 保存或修改监控结果
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonLRecordValue value) throws Exception;

}
