package com.asiainfo.monitor.busi.asyn.operation.impl;



import java.util.List;

import com.asiainfo.monitor.busi.asyn.operation.OperateCallable;
import com.asiainfo.monitor.busi.asyn.operation.RecordAriticOperateCallable;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonLRecordValue;
import com.asiainfo.monitor.tools.common.SimpleResult;
import com.asiainfo.monitor.tools.util.TimeUtil;

public class DefaultDayMaxhourlyAriticCallable extends RecordAriticOperateCallable {


	/**
	 * datas:为一小时记录集
	 * @param datas
	 */
	public DefaultDayMaxhourlyAriticCallable(List datas,int deepCount){
		super(datas,deepCount);
	}
	
	/**
	 * 按天（一天按每小时取一条最大记录，共24条记录）
	 * 
	 */
	public void operate(OperateCallable callable, SimpleResult sr) throws Exception {
		if (datas!=null){
			long used=0;
			for (int i=0;i<datas.size();i++){
				String infoValue=((IBOAIMonLRecordValue)datas.get(i)).getInfoValue();
				sr.setName(((IBOAIMonLRecordValue)datas.get(i)).getInfoName());
				
				if (i==0){
					String hour=TimeUtil.getHH(((IBOAIMonLRecordValue)datas.get(i)).getCreateDate());
					sr.setKey(hour);
				}
				String value=getValueFromInfo(infoValue,deepCount);
				
				if (Long.parseLong(value)>used)
					used=Long.parseLong(value);				
			}
			sr.setValue(used);
		}
	}

}
