package com.asiainfo.monitor.busi.dao.impl;  

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.dao.interfaces.IAIMonVerifyDAO;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonVerifySV;
import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonGroupBean;
import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonLoginEngine;
import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonVerifyEngine;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonLoginValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonVerifyValue;
  
public class AIMonVerifyDAOImpl implements IAIMonVerifyDAO{

	@Override
	public IBOAIMonVerifyValue qryVerifyInfoByType(String verifyType) throws Exception {
		IBOAIMonVerifyValue verifyValue=null;
        StringBuilder condition = new StringBuilder("");
        Map parameter = new HashMap();
        if(StringUtils.isNotBlank(verifyType)) {
            condition.append(IBOAIMonVerifyValue.S_VerifyType + "= :verifyType");
            parameter.put("verifyType", verifyType);
        }
       
        IBOAIMonVerifyValue[] values=BOAIMonVerifyEngine.getBeans(condition.toString(), parameter);
        if(values!=null&&values.length>0) {
        	verifyValue= values[0];
        }
        return verifyValue;
	}

	@Override
	public IBOAIMonVerifyValue[] qryAllVerifyInfos() throws Exception {
		IBOAIMonVerifyValue[] values=BOAIMonVerifyEngine.getBeans(null, null);
		return values;
	}
	public static void main(String[] args) throws Exception {
		IAIMonVerifySV sv=(IAIMonVerifySV)ServiceFactory.getService(IAIMonVerifySV.class);
		IBOAIMonVerifyValue[] values=sv.qryAllVerifyInfos();
		IBOAIMonVerifyValue value=sv.qryVerifyInfoByType("common");
		System.out.println(value);
	}
	
}
