package com.asiainfo.monitor.busi.exe.task.impl;


import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonThresholdValue;
import com.asiainfo.monitor.busi.exe.task.model.CommandType;
import com.asiainfo.monitor.busi.exe.task.model.JavaCommandType;
import com.asiainfo.monitor.busi.exe.task.model.SQLCmdType;
import com.asiainfo.monitor.busi.exe.task.model.ShellType;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPThresholdSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonParamValuesSV;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonParamValuesValue;
import com.asiainfo.monitor.tools.common.KeyName;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.model.CmdType;
import com.asiainfo.monitor.tools.model.ThresholdModel;
import com.asiainfo.monitor.tools.model.interfaces.ICmdType;

public class DefaultTaskFactory {
	
	private final static Log log=LogFactory.getLog(DefaultTaskFactory.class);	

	
	/**
	 * 构建命令对象
	 * @param cmdType
	 * @return
	 */
	public static CmdType getCmdType(String cmdType){
		if (cmdType.toUpperCase().equals(ICmdType.SHELL))
			return new ShellType();
		else if (cmdType.toUpperCase().equals(ICmdType.COMMAND))
			return new CommandType();
		else if (cmdType.toUpperCase().equals(ICmdType.JAVACOMMAND))
			return new JavaCommandType();
		else if (cmdType.toUpperCase().equals(ICmdType.TABLE))
			return new SQLCmdType();
		else 
			return null;
	}
	
	/**
	 * 查询内部参数
	 * @param type
	 * @param registeId
	 * @return
	 * @throws Exception
	 */
	public static List getInnerParams(String type,String registeId) throws Exception{
		List result = null;
		try{
			//获取面板任务参数
			IAIMonParamValuesSV paramValuesSV = (IAIMonParamValuesSV)ServiceFactory.getService(IAIMonParamValuesSV.class);
			IBOAIMonParamValuesValue[] paramValues = paramValuesSV.getParamValuesByType(type,registeId);
			result = new LinkedList();
			if (paramValues != null && paramValues.length > 0){
				for (int i=0;i<paramValues.length;i++){
					KeyName item = new KeyName(paramValues[i].getParamCode(),paramValues[i].getParamValue());
					result.add(item);
				}
			}
		}catch(Exception e){
			// "获取内置参数异常"
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000151"),e);
		}
		return result;
	}
	
	/**
	 * 构建警告模型
	 * @param thresholdId
	 * @return
	 * @throws Exception
	 */
	public static ThresholdModel builderThreshold(long thresholdId) throws Exception{
		if (thresholdId <= 0){
			return null;
		}
		ThresholdModel result = null;
		try{
			IAIMonPThresholdSV objMonThresholdSV = (IAIMonPThresholdSV)ServiceFactory.getService(IAIMonPThresholdSV.class);
			IBOAIMonThresholdValue objMonThreshold = objMonThresholdSV.getBeanById(thresholdId);
			if (objMonThreshold != null && objMonThreshold.getState().equals("U")){
				result = new ThresholdModel();
				result.setName(objMonThreshold.getThresholdId()+"");
				result.setSelfId(objMonThreshold.getThresholdId());
				
				if(!StringUtils.isBlank(objMonThreshold.getExpr1())){
					result.setExpr1(objMonThreshold.getExpr1());
				}
				if(!StringUtils.isBlank(objMonThreshold.getExpr2())){
					result.setExpr2(objMonThreshold.getExpr2());
				}
				if(!StringUtils.isBlank(objMonThreshold.getExpr3())){
					result.setExpr3(objMonThreshold.getExpr3());
				}
				if(!StringUtils.isBlank(objMonThreshold.getExpr4())){
					result.setExpr4(objMonThreshold.getExpr4());
				}
				if(!StringUtils.isBlank(objMonThreshold.getExpr5())){
					result.setExpr5(objMonThreshold.getExpr5());
				}
				if (objMonThreshold.getExpiryDays() > 0){
					result.setExpiryDays(objMonThreshold.getExpiryDays()+"");
				}
			}
		}catch(Exception e){
			// "生成预警对象异常:"
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000152")+e.getMessage());
		}
		return result;
	}
}
