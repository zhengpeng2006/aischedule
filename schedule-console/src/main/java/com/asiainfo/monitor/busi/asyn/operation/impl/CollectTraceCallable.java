package com.asiainfo.monitor.busi.asyn.operation.impl;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonColgRuleSV;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonColgRuleValue;

public class CollectTraceCallable extends CollectFileCallable {

	public CollectTraceCallable(String id){
		super(id);
	}
	
	/**
	 * 读取默认采集Trace参数规则
	 */
	public IBOAIMonColgRuleValue getDefaultCollectRule() throws Exception {
		IAIMonColgRuleSV colgRuleSV=(IAIMonColgRuleSV)ServiceFactory.getService(IAIMonColgRuleSV.class);
		IBOAIMonColgRuleValue result=colgRuleSV.getDefaultColgRuleByType("CTRACE");
		return result;
	}
	
	/**
	 * 读去自定义采集Trace参数规则
	 */
	public IBOAIMonColgRuleValue getSelfCollectRule() throws Exception{
		IAIMonColgRuleSV colgRuleSV=(IAIMonColgRuleSV)ServiceFactory.getService(IAIMonColgRuleSV.class);
		IBOAIMonColgRuleValue result=colgRuleSV.getColgRuleByHostIdAndAppIdAndRuleType(null,id,"CTRACE");
		return result;
	}

}
