package com.asiainfo.monitor.busi.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.ai.appframe2.web.action.BaseAction;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonWTriggerValue;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPInfoSV;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonWTriggerSV;
import com.asiainfo.monitor.tools.util.TimeUtil;


public class WTriggerInfoAction extends BaseAction {
	private static transient Log log = LogFactory.getLog(WTriggerInfoAction.class);
	
	/**
	 * 
	 * @param layerType
	 * @param warnLevel
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public int count(String layerType, String warnLevel, String startDate, String endDate) throws Exception {
		IAIMonWTriggerSV sv = (IAIMonWTriggerSV)ServiceFactory.getService(IAIMonWTriggerSV.class);
		return sv.count(layerType, warnLevel, startDate, endDate);
	}
	
	/**
	 * 
	 * @param layerType
	 * @param warnLevel
	 * @param startDate
	 * @param endDate
	 * @param startIndex
	 * @param endIndex
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonWTriggerValue[] queryByCond(String layerType, String warnLevel, String startDate, 
			String endDate, Integer startIndex, Integer endIndex) throws Exception {
		IAIMonWTriggerSV sv = (IAIMonWTriggerSV)ServiceFactory.getService(IAIMonWTriggerSV.class);
		return sv.getAllByCond(layerType, warnLevel, startDate, endDate, startIndex.intValue(), endIndex.intValue());
	}
	
	/**
	 * 
	 * @param layerType
	 * @param warnLevel
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonWTriggerValue[] getTriggerValuesByCond(String layerType, String warnLevel, String startDate,
			String endDate) throws Exception {
		IAIMonWTriggerSV sv = (IAIMonWTriggerSV)ServiceFactory.getService(IAIMonWTriggerSV.class);
		return sv.getAllByCond(layerType, warnLevel, startDate, endDate);
	}
	
	public List statCount(Object[] layerTypes, Object[] warnLevels, String startDate, String endDate) throws Exception {
		if (log.isDebugEnabled()){
			log.debug("layerTypes" + layerTypes.length + ";warnLevels " + warnLevels.length + "; startDate=" + startDate + "; endDate=" + endDate);
		}
		List ret = new ArrayList();
		IAIMonWTriggerSV sv = (IAIMonWTriggerSV)ServiceFactory.getService(IAIMonWTriggerSV.class);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (startDate.indexOf(':') == -1) {
			startDate = sdf.format(TimeUtil.addOrMinusHours(System.currentTimeMillis(), Integer.parseInt(startDate)));
		} 
		
		for (int i = 0; i < layerTypes.length; i++) {
			for (int j = 0; j < warnLevels.length; j++) {
				ret.add(sv.count(String.valueOf(layerTypes[i]), String.valueOf(warnLevels[j]), startDate, endDate));
			}
		}
		return ret;
	}
	
	/**
	 * 
	 * @param layerType
	 * @param warnLevel
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public int countByInfoIdCond(String infoId, String warnLevel, String startDate, String endDate) throws Exception {
		IAIMonWTriggerSV sv = (IAIMonWTriggerSV)ServiceFactory.getService(IAIMonWTriggerSV.class);
		return sv.countByInfoIdCond(infoId, warnLevel, startDate, endDate);
	}
	
	public IBOAIMonWTriggerValue[] getTriggerValuesByInfoIdCond(String infoId,String warnLevel, String startDate,
			String endDate, Integer startIndex, Integer endIndex) throws Exception {
		if (StringUtils.isBlank(infoId))
			return null;
		IAIMonWTriggerSV sv = (IAIMonWTriggerSV)ServiceFactory.getService(IAIMonWTriggerSV.class);
		return sv.getTriggerValuesByInfoIdCond(infoId, warnLevel, startDate, endDate, startIndex.intValue(), endIndex.intValue());
	}
	
	/**
	 * 修复告警
	 * @param triggerIds:选择告警标识列表
	 * @param remarks:修复备注
	 * @throws Exception
	 */
	public void repairWTrigger(Object[] triggerIds,String remarks) throws Exception {
		IAIMonWTriggerSV triggerSV = (IAIMonWTriggerSV)ServiceFactory.getService(IAIMonWTriggerSV.class);
		triggerSV.repairWTrigger(triggerIds, remarks);
	}
	
	/**
	 * 由告警屏蔽该告警的任务
	 * @param infoIds
	 * @throws Exception
	 */
	public void shieldInfoFromWTrigger(Object[] infoIds) throws Exception{
		IAIMonPInfoSV infoSV=(IAIMonPInfoSV)ServiceFactory.getService(IAIMonPInfoSV.class);
		infoSV.shieldInfoByInfoIds(infoIds);
	}
}
