package com.asiainfo.monitor.busi.asyn.operation.impl;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonColgRuleSV;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonColgRuleValue;

public class CollectJmxProCallable extends CollectFileCallable {

	public CollectJmxProCallable(String id) {
		super(id);
	}

	public IBOAIMonColgRuleValue getDefaultCollectRule() throws Exception {
		IAIMonColgRuleSV colgRuleSV=(IAIMonColgRuleSV)ServiceFactory.getService(IAIMonColgRuleSV.class);
		IBOAIMonColgRuleValue result=colgRuleSV.getDefaultColgRuleByType("CJMXPRO");
		return result;
	}

	public IBOAIMonColgRuleValue getSelfCollectRule() throws Exception {
		IAIMonColgRuleSV colgRuleSV=(IAIMonColgRuleSV)ServiceFactory.getService(IAIMonColgRuleSV.class);
		IBOAIMonColgRuleValue result=colgRuleSV.getColgRuleByHostIdAndAppIdAndRuleType(null,id,"CJMXPRO");
		return result;
	}

}
