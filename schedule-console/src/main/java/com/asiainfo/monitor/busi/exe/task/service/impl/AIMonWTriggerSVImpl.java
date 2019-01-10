package com.asiainfo.monitor.busi.exe.task.service.impl;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.exe.task.bo.BOAIMonWTriggerHisBean;
import com.asiainfo.monitor.busi.exe.task.dao.interfaces.IAIMonWTriggerDAO;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonWTriggerHisValue;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonWTriggerValue;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonWTriggerSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonColgRuleSV;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonColgRuleValue;

public class AIMonWTriggerSVImpl implements IAIMonWTriggerSV {
	private static transient Log log = LogFactory.getLog(AIMonWTriggerSVImpl.class);

	/**
	 * 根据条件查询告警记录
	 * 
	 * @param sqlCondition
	 * @param startIndex
	 * @param endIndex
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonWTriggerValue[] getMonWTriggerByRecordId(String recordId, Integer startIndex, Integer endIndex) throws RemoteException,Exception{
		StringBuilder sb=new StringBuilder();
		sb.append(IBOAIMonWTriggerValue.S_RecordId+" = :recordId");
		HashMap parameter=new HashMap();
		parameter.put("recordId",recordId);
		IAIMonWTriggerDAO triggerDao = (IAIMonWTriggerDAO)ServiceFactory.getService(IAIMonWTriggerDAO.class);
		return triggerDao.getMonWTriggerByCondition(sb.toString(),parameter,startIndex,endIndex);
	}
	
	/**
	 * 批量保存或修改警告记录
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonWTriggerValue[] values) throws RemoteException,Exception {
		IAIMonWTriggerDAO triggerDao = (IAIMonWTriggerDAO)ServiceFactory.getService(IAIMonWTriggerDAO.class);
		triggerDao.saveOrUpdate(values);
	}
	
	/**
	 * 保存或修改警告记录
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonWTriggerValue value) throws RemoteException,Exception {
		IAIMonWTriggerDAO triggerDao = (IAIMonWTriggerDAO)ServiceFactory.getService(IAIMonWTriggerDAO.class);
		triggerDao.saveOrUpdate(value);
	}
	
	/**
	 * 保存告警并处理之前的告警，将X分钟之前的同任务告警转成历史
	 * @param values
	 * @throws RemoteException
	 * @throws Exception
	 */
	public void saveAndProcess(IBOAIMonWTriggerValue[] values) throws RemoteException,Exception {
		if (values==null || values.length<1)
			return;
		IAIMonWTriggerDAO triggerDao = (IAIMonWTriggerDAO)ServiceFactory.getService(IAIMonWTriggerDAO.class);
		IAIMonColgRuleSV ruleSV=(IAIMonColgRuleSV)ServiceFactory.getService(IAIMonColgRuleSV.class);
		IBOAIMonColgRuleValue ruleValue=ruleSV.getDefaultColgRuleByType("PROC_WTRIGGER");
		List hisList=new ArrayList();
		List delList=new ArrayList();
		if (ruleValue!=null){
			//删除second分钟之前的类似告警(转到历史表)
			int second=Integer.parseInt(ruleValue.getExpr0());
			for (int i=0;i<values.length;i++){
				if (values[i]==null)
					continue;
				IBOAIMonWTriggerValue[] oldValues=triggerDao.getMonWTriggerBefore(values[i].getInfoId(), second,values[i].getWarnLevel());
				if (oldValues!=null){
					for (int j=0;j<oldValues.length;j++){
						if (oldValues[j]==null)
							continue;
						oldValues[j].delete();
						delList.add(oldValues[j]);
						IBOAIMonWTriggerHisValue hisValue=new BOAIMonWTriggerHisBean();
						hisValue.setTriggerId(oldValues[j].getTriggerId());
						hisValue.setRecordId(oldValues[j].getRecordId());
						hisValue.setIp(oldValues[j].getIp());
						hisValue.setInfoId(oldValues[j].getInfoId());
						hisValue.setInfoName(oldValues[j].getInfoName());
						hisValue.setLayer(oldValues[j].getLayer());
						hisValue.setPhonenum(oldValues[j].getPhonenum());
						hisValue.setContent(oldValues[j].getContent());
						hisValue.setWarnLevel(oldValues[j].getWarnLevel());
						hisValue.setExpiryDate(ServiceManager.getOpDateTime());
						hisValue.setCreateDate(oldValues[j].getCreateDate());
						hisValue.setDoneDate(oldValues[j].getDoneDate());
						hisValue.setState("F");
						hisValue.setRemarks(StringUtils.isBlank(oldValues[j].getRemarks())?"告警恢复":oldValues[j].getRemarks()+"告警恢复");
						hisValue.setStsToNew();
						hisList.add(hisValue);
					}
				}
			}
		}
		//保存新预警记录
		triggerDao.saveOrUpdate(values);
		if (delList.size()>0){
			//删除新预警记录
			triggerDao.saveOrUpdate((IBOAIMonWTriggerValue[])delList.toArray(new IBOAIMonWTriggerValue[0]));
		}
		if (hisList.size()>0){
			//保存历史预警记录
			triggerDao.saveWTriggerHis((IBOAIMonWTriggerHisValue[])hisList.toArray(new IBOAIMonWTriggerHisValue[0]));
		}
		hisList=null;
		delList=null;
	}
	
	public int count(String layerType, String warnLevel, String startDate , String endDate) throws RemoteException,Exception{
		if (log.isDebugEnabled()) {
			log.debug("layerType=" + layerType + ", warnLevel=" + warnLevel + ", startDate=" + startDate + ", endDate=" + endDate);
		}
		StringBuilder sb = new StringBuilder();
		sb.append(IBOAIMonWTriggerValue.S_Layer).append("=:layer");
		if (warnLevel != null && StringUtils.isNotBlank(warnLevel)) {
			sb.append(" and ").append(IBOAIMonWTriggerValue.S_WarnLevel).append("=:warnlevel");
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (startDate == null || StringUtils.isBlank(startDate)) {
			startDate = sdf.format(new Date(System.currentTimeMillis()));
		}
		
		sb.append(" and ").append(IBOAIMonWTriggerValue.S_CreateDate).append(">= :startDate ");
		
		Map params = new HashMap();
		params.put("layer", layerType);
		params.put("warnlevel", warnLevel);
		params.put("startDate", startDate);
		
		if (endDate != null && StringUtils.isNotBlank(endDate)) {
			sb.append(" and ").append(IBOAIMonWTriggerValue.S_CreateDate).append("<= :endDate ");
			params.put("endDate", endDate);
		}
		
		String cond = sb.toString();

		IAIMonWTriggerDAO triggerDao = (IAIMonWTriggerDAO)ServiceFactory.getService(IAIMonWTriggerDAO.class);
		return triggerDao.count(cond, params);
	}
	
	public IBOAIMonWTriggerValue[] getAllByCond(String layerType, String warnLevel, String startDate, String endDate) throws RemoteException,Exception{
		StringBuilder sb = new StringBuilder();
		sb.append(IBOAIMonWTriggerValue.S_Layer).append("=:layer");
		if (warnLevel != null && StringUtils.isNotBlank(warnLevel)) {
			sb.append(" and ").append(IBOAIMonWTriggerValue.S_WarnLevel).append("=:warnlevel");
		}
		if (startDate != null && endDate != null 
				&& StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
			sb.append(" and ").append(IBOAIMonWTriggerValue.S_CreateDate).append(">= :startDate  ");
			sb.append(" and ").append(IBOAIMonWTriggerValue.S_CreateDate).append("<= :endDate  ");
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			startDate = sdf.format(new Date(System.currentTimeMillis()));
			sb.append(" and ").append(IBOAIMonWTriggerValue.S_CreateDate).append(">= :startDate ");
		}
		String cond = sb.toString();
		Map params = new HashMap();
		params.put("layer", layerType);
		params.put("warnlevel", warnLevel);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		IAIMonWTriggerDAO triggerDao = (IAIMonWTriggerDAO)ServiceFactory.getService(IAIMonWTriggerDAO.class);
		return triggerDao.query(cond, params);
	}
	
	public IBOAIMonWTriggerValue[] getAllByCond(String layerType, String warnLevel, String startDate, 
			String endDate, int startIndex, int endIndex) throws RemoteException,Exception {
		StringBuilder sb = new StringBuilder();
		sb.append(IBOAIMonWTriggerValue.S_Layer).append("=:layer");
		if (warnLevel != null && StringUtils.isNotBlank(warnLevel)) {
			sb.append(" and ").append(IBOAIMonWTriggerValue.S_WarnLevel).append("=:warnlevel");
		}
		if (startDate != null && endDate != null 
				&& StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
			sb.append(" and ").append(IBOAIMonWTriggerValue.S_CreateDate).append(">= :startDate  ");
			sb.append(" and ").append(IBOAIMonWTriggerValue.S_CreateDate).append("<= :endDate ");
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			startDate = sdf.format(new Date(System.currentTimeMillis()));
			sb.append(" and ").append(IBOAIMonWTriggerValue.S_CreateDate).append(">= :startDate ");
		}
		sb.append(" order by create_date desc ");
		String cond = sb.toString();
		Map params = new HashMap();
		params.put("layer", layerType);
		params.put("warnlevel", warnLevel);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		IAIMonWTriggerDAO triggerDao = (IAIMonWTriggerDAO)ServiceFactory.getService(IAIMonWTriggerDAO.class);
		IBOAIMonWTriggerValue[] triggerValues = triggerDao.getMonWTriggerByCondition(cond, params, startIndex, endIndex);
		if(triggerValues != null && triggerValues.length>0){
			for(int i=0;i<triggerValues.length;i++){
				triggerValues[i].setExtAttr("CHK", "false");
			}
		}
		return triggerValues;
	}

	public int countByInfoIdCond(String infoId, String warnLevel, String startDate , String endDate) throws RemoteException,Exception{
		if (log.isDebugEnabled()) {
			log.debug("infoId=" + infoId + ", warnLevel=" + warnLevel + ", startDate=" + startDate + ", endDate=" + endDate);
		}
		StringBuilder sb = new StringBuilder();
		sb.append(IBOAIMonWTriggerValue.S_InfoId).append("=:infoId");
		if (warnLevel != null && StringUtils.isNotBlank(warnLevel)) {
			sb.append(" and ").append(IBOAIMonWTriggerValue.S_WarnLevel).append("=:warnlevel");
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (startDate == null || StringUtils.isBlank(startDate)) {
			startDate = sdf.format(new Date(System.currentTimeMillis()));
		}
		
		sb.append(" and ").append(IBOAIMonWTriggerValue.S_CreateDate).append(">= :startDate ");
		
		Map params = new HashMap();
		params.put("infoId", infoId);
		params.put("warnlevel", warnLevel);
		params.put("startDate", startDate);
		
		if (endDate != null && StringUtils.isNotBlank(endDate)) {
			sb.append(" and ").append(IBOAIMonWTriggerValue.S_CreateDate).append("<= :endDate ");
			params.put("endDate", endDate);
		}
		
		String cond = sb.toString();

		IAIMonWTriggerDAO triggerDao = (IAIMonWTriggerDAO)ServiceFactory.getService(IAIMonWTriggerDAO.class);
		return triggerDao.count(cond, params);
	}
	
	public IBOAIMonWTriggerValue[] getTriggerValuesByInfoIdCond(String infoId,String warnLevel, String startDate,
			String endDate, Integer startIndex, Integer endIndex) throws RemoteException,Exception {
		StringBuilder sb = new StringBuilder();
		sb.append(IBOAIMonWTriggerValue.S_InfoId).append("=:infoId");
		if (warnLevel != null && StringUtils.isNotBlank(warnLevel)) {
			sb.append(" and ").append(IBOAIMonWTriggerValue.S_WarnLevel).append("=:warnlevel");
		}
		if (startDate != null && endDate != null 
				&& StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
			sb.append(" and ").append(IBOAIMonWTriggerValue.S_CreateDate).append("<= :endDate ");
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			startDate = sdf.format(new Date(System.currentTimeMillis()));
			sb.append(" and ").append(IBOAIMonWTriggerValue.S_CreateDate).append(">= :startDate ");
		}
		String cond = sb.toString();
		Map params = new HashMap();
		params.put("infoId", infoId);
		params.put("warnlevel", warnLevel);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		IAIMonWTriggerDAO triggerDao = (IAIMonWTriggerDAO)ServiceFactory.getService(IAIMonWTriggerDAO.class);
		IBOAIMonWTriggerValue[] triggerValues = triggerDao.getMonWTriggerByCondition(cond, params, startIndex, endIndex);
		if(triggerValues != null && triggerValues.length>0){
			for(int i=0;i<triggerValues.length;i++){
				triggerValues[i].setExtAttr("CHK", "false");
			}
		}
		return triggerValues;
	}

	/**
	 * 修复告警记录,将告警记录转移到历史表，历史表中失效日期为修复日期，状态为F
	 * @param triggerId
	 * @param remarks
	 * @throws Exception
	 */
	public void repairWTrigger(Object[] triggerIds,String remarks) throws RemoteException,Exception{
		if (triggerIds==null || triggerIds.length<1)
			return;
		IAIMonWTriggerDAO triggerDao = (IAIMonWTriggerDAO)ServiceFactory.getService(IAIMonWTriggerDAO.class);
		List srcList=new ArrayList();
		List desList=new ArrayList();
		for (int i=0;i<triggerIds.length;i++){
			IBOAIMonWTriggerValue value=triggerDao.getWTriggerBean(String.valueOf(triggerIds[i]));
			if (value==null)
				return;
			IBOAIMonWTriggerHisValue hisValue=new BOAIMonWTriggerHisBean();
			hisValue.setTriggerId(value.getTriggerId());
			hisValue.setRecordId(value.getRecordId());
			hisValue.setIp(value.getIp());
			hisValue.setInfoId(value.getInfoId());
			hisValue.setInfoName(value.getInfoName());
			hisValue.setLayer(value.getLayer());
			hisValue.setPhonenum(value.getPhonenum());
			hisValue.setContent(value.getContent());
			hisValue.setWarnLevel(value.getWarnLevel());
			hisValue.setExpiryDate(ServiceManager.getOpDateTime());
			hisValue.setCreateDate(value.getCreateDate());
			hisValue.setDoneDate(value.getDoneDate());
			hisValue.setState("F");
			hisValue.setRemarks(StringUtils.isBlank(remarks)?"告警恢复":remarks+"告警恢复");
			hisValue.setStsToNew();
			srcList.add(value);
			desList.add(hisValue);
		}
		if (srcList.size()>0)
			triggerDao.deleteWTrigger((IBOAIMonWTriggerValue[])srcList.toArray(new IBOAIMonWTriggerValue[0]));
		
		if (desList.size()>0)
			triggerDao.saveWTriggerHis((IBOAIMonWTriggerHisValue[])desList.toArray(new IBOAIMonWTriggerHisValue[0]));
		
	}
}
