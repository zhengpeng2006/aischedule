package com.asiainfo.monitor.busi.web;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.bo.DataContainerFactory;
import com.ai.appframe2.service.ServiceFactory;
import com.ai.appframe2.web.action.BaseAction;
import com.asiainfo.monitor.busi.exe.task.bo.BOAIMonPInfoBean;
import com.asiainfo.monitor.busi.exe.task.impl.DefaultTaskFactory;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPInfoValue;
import com.asiainfo.monitor.busi.exe.task.model.TaskExecContainer;
import com.asiainfo.monitor.busi.exe.task.model.TaskTableContainer;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPExecSV;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPInfoSV;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPTableSV;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPThresholdSV;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPTimeSV;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.model.CmdType;
import com.asiainfo.monitor.tools.model.ThresholdModel;
import com.asiainfo.monitor.tools.model.TimeModel;

public class TaskGuideAction extends BaseAction {

	private static final Log log=LogFactory.getLog(TaskGuideAction.class);
	
	/**
	 * 保存任务定义（向导方式）
	 * @param taskInfo
	 * @throws Exception
	 */
	public String saveTask(DataContainer dc) throws Exception {
		String result="";
		if (dc == null ) {
			// 没有需要保存的数据！
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000048"));
		}		
		try {
			IBOAIMonPInfoValue infoValue = new BOAIMonPInfoBean();
			if (StringUtils.isBlank(dc.getAsString(IBOAIMonPInfoValue.S_InfoId))) {

				dc.setStsToNew();
			} else {

				long infoId = dc.getAsLong(IBOAIMonPInfoValue.S_InfoId);
				dc.clearProperty(IBOAIMonPInfoValue.S_InfoId);
				dc.initPropertyOld(IBOAIMonPInfoValue.S_InfoId, infoId);

			}
			DataContainerFactory.copy(dc, infoValue);
			IAIMonPInfoSV infoSV = (IAIMonPInfoSV)ServiceFactory.getService(IAIMonPInfoSV.class);
			infoSV.saveOrUpdate(infoValue);
			result=infoValue.getInfoId()+"";
		} catch (Exception e) {
			log.error("Call TaskGuideAction's Method saveTask has Exception : " + e.getMessage());
			// "保存任务定义异常:"
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000056")+e.getMessage(),e);
		}
		return result;
	}
	
	/**
	 * 保存进程监控定义
	 * @param execInfo
	 * @throws Exception
	 */
//	public String saveExec(Object[] execInfo,ArrayCollection params) throws Exception {
//		String result="";
//		if (execInfo==null || execInfo.length<=0)
//			return result;
//		try{
//			TaskExecContainer execModel = new TaskExecContainer();
//			if (execInfo[0]!=null && StringUtils.isNotBlank(execInfo[0].toString())) {
//				execModel.setId(execInfo[0].toString().trim());
//			}
//			execModel.setName(execInfo[1].toString().trim());
//			String type=execInfo[2].toString().trim();
//			CmdType cmdType=DefaultTaskFactory.getCmdType(type);
//			cmdType.setName(execInfo[5].toString().trim());
//			execModel.setCmdType(cmdType);
//			execModel.setExpr(execInfo[3].toString().trim());
//			String state=execInfo[4].toString().trim();
//			if (state.equalsIgnoreCase("U")){
//				execModel.setEnable(true);
//			}else{
//				execModel.setEnable(false);
//			}
//			if(execInfo[6] != null && StringUtils.isNotBlank(execInfo[6].toString())) {
//				execModel.setParentId(Long.parseLong(execInfo[6].toString().trim()));
//			} else {
//				execModel.setParentId(0);
//			}
//			
//			DataContainer[] paramDcs = null;
//			//处理参数
//			if(params != null && params.size() > 0)
//			{
//				ASObject tmpObj = null;
//				paramDcs = new DataContainer[params.size()];
//				for(int i=0;i<params.size();i++){
//					tmpObj = (ASObject)params.get(i);
//					paramDcs[i] = FlexHelper.transferAsToDc(tmpObj);
//				}
//			}
//			
//			IAIMonPExecSV execSV = (IAIMonPExecSV) ServiceFactory.getService(IAIMonPExecSV.class);
//			execSV.savePExecByGuide(execModel,paramDcs);
//			result=execModel.getId();
//			execModel=null;
//		} catch (Exception e) {
//			log.error("Call TaskGuideAction's Method saveExec has Exception : " + e.getMessage());
//			// "保存进程监控定义异常:"
//			throw new Exception(AIMonLocaleFactory.getResource("MOS0000057")+e.getMessage(),e);
//		}
//		return result;
//	}
	
	/**
	 * 保存或修改数据监控配置
	 * 
	 * @param tableInfo
	 * @throws Exception
	 */
//	public String saveTable(Object[] tableInfo,ArrayCollection params) throws Exception {
//		String result="";
//		if (tableInfo == null || tableInfo.length <= 0) {
//			return result;
//		}
//		try {
//			TaskTableContainer tableModel=new TaskTableContainer();
//			
//			if (tableInfo[0]!=null && StringUtils.isNotBlank(tableInfo[0].toString())) {
//				tableModel.setId(tableInfo[0].toString().trim());
//			}
//			tableModel.setName(tableInfo[1].toString().trim());
//			
//			CmdType cmdType=DefaultTaskFactory.getCmdType("TABLE");
//			cmdType.setName(tableInfo[6].toString().trim());
//			tableModel.setCmdType(cmdType);
//			tableModel.setExpr(tableInfo[2].toString().trim());
//			tableModel.setDbacctCode(tableInfo[3].toString().trim());
//			tableModel.setDburlName(tableInfo[4].toString().trim());
//			String state=tableInfo[5].toString().trim();
//			if (state.equalsIgnoreCase("U")){
//				tableModel.setEnable(true);
//			}else{
//				tableModel.setEnable(false);
//			}
//			if(tableInfo[7] != null && StringUtils.isNotBlank(tableInfo[7].toString())) {
//				tableModel.setParentId(Long.parseLong(tableInfo[7].toString().trim()));
//			} else {
//				tableModel.setParentId(0);
//			}
//			DataContainer[] paramDcs = null;
//			//处理参数
//			if(params != null && params.size() > 0)
//			{
//				ASObject tmpObj = null;
//				paramDcs = new DataContainer[params.size()];
//				for(int i=0;i<params.size();i++){
//					tmpObj = (ASObject)params.get(i);
//					paramDcs[i] = FlexHelper.transferAsToDc(tmpObj);
//				}
//			}
//			IAIMonPTableSV tableSV = (IAIMonPTableSV)ServiceFactory.getService(IAIMonPTableSV.class);
//			tableSV.savePTableByGuide(tableModel,paramDcs);
//			result=tableModel.getId();
//			tableModel=null;
//		} catch (Exception e) {
//			log.error("Call TaskGuideAction's Method saveTable has Exception : " + e.getMessage());
//			// "保存数据监控配置异常:"
//			throw new Exception(AIMonLocaleFactory.getResource("MOS0000058")+e.getMessage(),e);
//		}
//		return result;
//	}
	
	/**
	 * 保存或修改进程阀值配置
	 * @param thresholdInfo
	 * @throws Exception
	 */
	public String saveThreshold(Object[] thresholdInfo) throws Exception {
		String result="";
		if (thresholdInfo == null || thresholdInfo.length <= 0) {
			return result;
		}
		try {
			ThresholdModel thresholdModel=new ThresholdModel();
			if (thresholdInfo[0]!=null && StringUtils.isNotBlank(thresholdInfo[0].toString())) {
				thresholdModel.setSelfId(Long.parseLong(thresholdInfo[0].toString().trim()));
			}
			
			thresholdModel.setRemarks(thresholdInfo[4].toString().trim());
			thresholdModel.setExpr1(thresholdInfo[1].toString().trim());
			thresholdModel.setExpr2(thresholdInfo[2].toString().trim());
			if(thresholdInfo[5] != null && StringUtils.isNotBlank(thresholdInfo[5].toString())) {
				thresholdModel.setParentId(Long.parseLong(thresholdInfo[5].toString().trim()));
			} else {
				thresholdModel.setParentId(0);
			}
			thresholdModel.setName(thresholdInfo[6].toString().trim());
			IAIMonPThresholdSV thresholdSV = (IAIMonPThresholdSV)ServiceFactory.getService(IAIMonPThresholdSV.class);
			thresholdSV.savePThresholdByGuide(thresholdModel);
			result=thresholdModel.getSelfId()+"";
			thresholdModel=null;
		} catch (Exception e) {
			log.error("Call TaskGuideAction's Method saveThreshold has Exception : " + e.getMessage());
			// "保存进程阀值配置异常:"
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000059")+e.getMessage(),e);
		}
		return result;
	}
	
	/**
	 * 保存监控时间段配置
	 * @param timeInfo
	 * @throws Exception
	 */
	public String saveTime(Object[] timeInfo) throws Exception {
		String result="";
		if (timeInfo == null || timeInfo.length <= 0) {
			return result;
		}
		try {
			TimeModel timeModel=new TimeModel();
			if (timeInfo[0]!=null && StringUtils.isNotBlank(timeInfo[0].toString())) {
				timeModel.setSelfId(Long.parseLong(timeInfo[0].toString().trim()));
			}
			timeModel.setName(timeInfo[4].toString().trim());
			timeModel.setType(timeInfo[1].toString().trim());
			timeModel.setExpr(timeInfo[2].toString().trim());
			if(timeInfo[5] != null && StringUtils.isNotBlank(timeInfo[5].toString())) {
				timeModel.setParentId(Long.parseLong(timeInfo[5].toString().trim()));
			} else {
				timeModel.setParentId(0);
			}
			IAIMonPTimeSV timeSV = (IAIMonPTimeSV)ServiceFactory.getService(IAIMonPTimeSV.class);
			timeSV.savePTimeByGuide(timeModel);
			result=timeModel.getSelfId()+"";
			timeModel=null;
		} catch (Exception e) {
			log.error("Call TaskAction's Method saveTime has Exception : " + e.getMessage());
			// "保存监控时间段配置异常:"
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000060")+e.getMessage(),e);
		}
		return result;
	}
}