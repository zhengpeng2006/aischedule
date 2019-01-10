package com.asiainfo.monitor.busi.exe.task.dao.impl;



import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.monitor.busi.exe.task.bo.BOAIMonWTriggerEngine;
import com.asiainfo.monitor.busi.exe.task.bo.BOAIMonWTriggerHisEngine;
import com.asiainfo.monitor.busi.exe.task.dao.interfaces.IAIMonWTriggerDAO;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonWTriggerHisValue;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonWTriggerValue;


public class AIMonWTriggerDAOImpl implements IAIMonWTriggerDAO {
	private static transient Log log = LogFactory.getLog(AIMonWTriggerDAOImpl.class);

	/**
	 * 根据条件查询告警记录信息
	 * 
	 * @param condition
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonWTriggerValue[] query(String condition, Map parameter) throws Exception{
		return BOAIMonWTriggerEngine.getBeans(condition, parameter);
	}

	/**
	 * 根据告警标识查找告警记录
	 * @param trigger_Id
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonWTriggerValue getWTriggerBean(String trigger_Id)  throws Exception{
		return BOAIMonWTriggerEngine.getBean(Long.parseLong(trigger_Id));
	}
	/**
	 * 根据条件查询告警记录
	 * 
	 * @param condition
	 * @param startIndex
	 * @param endIndex
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonWTriggerValue[] getMonWTriggerByCondition(String condition,Map parameter,int startIndex,int endIndex) throws Exception{
		return BOAIMonWTriggerEngine.getBeans(null,condition,parameter,startIndex,endIndex,true);
	}
	
	/**
	 * 保存告警并处理之前的告警，将X分钟之前的同任务告警转成历史
	 * @param infoId
	 * @param second
	 * @param level
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonWTriggerValue[] getMonWTriggerBefore(long infoId,int second,String level) throws Exception{
		StringBuilder sb=new StringBuilder();
		sb.append(IBOAIMonWTriggerValue.S_InfoId).append("=").append(" :infoId ");
		sb.append(" and ");
		sb.append(IBOAIMonWTriggerValue.S_CreateDate).append("<=(sysdate- :second /24/60) ");
		Map parameter=new HashMap();
		parameter.put("infoId", infoId);
		parameter.put("second", second);
		return BOAIMonWTriggerEngine.getBeans(sb.toString(),parameter);
	}
	
	/**
	 * 查询历史告警记录
	 * @param condition
	 * @param parameter
	 * @param startIndex
	 * @param endIndex
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonWTriggerHisValue[] getMonWTriggerHisByCondition(String condition,Map parameter,int startIndex,int endIndex) throws Exception{
		return BOAIMonWTriggerHisEngine.getBeans(null,condition,parameter,startIndex,endIndex,true);
	}
	
	/**
	   * 分页信息
	   *
	   * @param realSQL String
	   * @param startIndex long
	   * @param endIndex long
	   * @return String
	   */
	  private String getPagingSQL(String realSQL,long startIndex,long endIndex){
	    StringBuilder pagingSelect = new StringBuilder();
	    pagingSelect.append("select TRIGGER_ID,RECORD_ID,IP,INFO_ID,INFO_NAME,PHONENUM,CONTENT,WARN_LEVEL,");
	    pagingSelect.append("CREATE_DATE,DONE_DATE,STATE,REMARKS,rownum_ from ( select row_.*, rownum rownum_ from ( ");
	    pagingSelect.append(realSQL);
	    pagingSelect.append(" ) row_ where rownum <= "+endIndex+" ) where rownum_ > "+startIndex);
	    return pagingSelect.toString();
	  }
	
	/**
	 * 批量保存或修改警告记录
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonWTriggerValue[] values) throws Exception {
		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				if (values[i].isNew() && values[i].getTriggerId()<=0) {
					values[i].setTriggerId(BOAIMonWTriggerEngine.getNewId().longValue());
				}
			}
		} else {
			throw new Exception();
		}
		BOAIMonWTriggerEngine.saveBatch(values);
	}
	
	/**
	 * 保存或修改警告记录
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonWTriggerValue value) throws Exception {
		if (value.isNew()) {
			value.setTriggerId(BOAIMonWTriggerEngine.getNewId().longValue());
		}
		BOAIMonWTriggerEngine.save(value);
	}
	
	/**
	 * 删除警告记录
	 * @param value
	 * @throws Exception
	 */
	public void deleteWTrigger(IBOAIMonWTriggerValue value) throws Exception {
		value.delete();
		BOAIMonWTriggerEngine.save(value);
	}
	
	/**
	 * 批量删除警告记录
	 * @param value
	 * @throws Exception
	 */
	public void deleteWTrigger(IBOAIMonWTriggerValue[] values) throws Exception {
		for (int i=0;i<values.length;i++){
			values[i].delete();
		}
		BOAIMonWTriggerEngine.saveBatch(values);
	}
	
	/**
	 * 保存告警记录历史表
	 * @param value
	 * @throws Exception
	 */
	public void saveWTriggerHis(IBOAIMonWTriggerHisValue value) throws Exception{
		BOAIMonWTriggerHisEngine.save(value);
	}
	
	/**
	 * 批量保存告警记录历史表
	 * @param value
	 * @throws Exception
	 */
	public void saveWTriggerHis(IBOAIMonWTriggerHisValue[] values) throws Exception{
		BOAIMonWTriggerHisEngine.saveBatch(values);
	}
	
	public int count(String condition, Map params) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("condition={" + condition + "}");
		}
		return BOAIMonWTriggerEngine.getBeansCount(condition, params);
	}

}
