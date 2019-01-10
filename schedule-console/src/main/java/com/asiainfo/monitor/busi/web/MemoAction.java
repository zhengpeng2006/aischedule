package com.asiainfo.monitor.busi.web;

import java.sql.Timestamp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.ai.appframe2.web.action.BaseAction;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonMemoSV;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonMemoBean;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonMemoValue;

public class MemoAction extends BaseAction {

	private static transient Log log = LogFactory.getLog(MemcachedMonitorAction.class);
	
	private Timestamp nowDate = new Timestamp(System.currentTimeMillis());
	
	public IBOAIMonMemoValue[] queryMemoByCond(String startDate, String endDate) throws Exception {
		IAIMonMemoSV memoSV = (IAIMonMemoSV) ServiceFactory.getService(IAIMonMemoSV.class);
		return memoSV.getMemoByCond(startDate, endDate);
	}
	
	/**
	 * 保存备忘录
	 * 
	 * @param memoInfo
	 * @throws Exception
	 */
	public void saveMemo(Object[] memoInfo) throws Exception {
		if (memoInfo == null || memoInfo.length < 0 ) {
			return;
		}
		try {
			IAIMonMemoSV memoSV = (IAIMonMemoSV) ServiceFactory.getService(IAIMonMemoSV.class);
			
			IBOAIMonMemoValue value = new BOAIMonMemoBean();
			value.setStsToNew();
			value.setMemoSubject(memoInfo[0].toString());
			value.setMemoNote(memoInfo[1].toString());
			String userId="";
//			if(FlexSessionManager.getUser() != null)
//				userId = FlexSessionManager.getUser().getId();
			value.setCreator(userId);
			value.setCreateDate(nowDate);
			value.setState(memoInfo[2].toString());
			
			memoSV.saveMemo(value);
		} catch (Exception e) {
			log.error("Call ControlServerStatusAction's Method saveMemo has Exception :"+e.getMessage());
		}
	}
}
