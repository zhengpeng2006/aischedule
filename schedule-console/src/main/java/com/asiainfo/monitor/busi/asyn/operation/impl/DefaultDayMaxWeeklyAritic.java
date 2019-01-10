package com.asiainfo.monitor.busi.asyn.operation.impl;

import java.util.List;

import com.asiainfo.monitor.busi.asyn.operation.AsynOperation;
import com.asiainfo.monitor.busi.asyn.operation.OperateCallable;

/**
 * 
 * @author Guocx
 *
 */
public class DefaultDayMaxWeeklyAritic extends AsynOperation {

	private int deepCount;
	
	public DefaultDayMaxWeeklyAritic(int deepCount) {
		super();
		this.deepCount=deepCount;
	}
	
	/**
	 * 按周（一周按每天取早[4-11]、中[12-19]、晚[20-3]三条最大记录，共21条记录）
	 */
	@Override
	public OperateCallable takeOperateCallable(Object data) {
		if (data==null)
			return null;
		OperateCallable callable=new DefaultDayMaxhourlyAriticCallable((List)data,deepCount);
		return callable;
	}
	

}
