package com.asiainfo.monitor.busi.dao.impl;

import java.util.HashMap;

import com.asiainfo.monitor.busi.dao.interfaces.IAIMonPanelRelatEntityDAO;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonPanelRelatEntityEngine;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonPanelRelatEntityValue;

public class AIMonPanelRelatEntityDAOImpl implements IAIMonPanelRelatEntityDAO {

	public IBOAIMonPanelRelatEntityValue[] getRelatByEntityId(String entityId) throws Exception {

		HashMap param = new HashMap();
		StringBuilder condition = new StringBuilder();
		condition.append(IBOAIMonPanelRelatEntityValue.S_EntityId).append("= :pEntityId");
		condition.append(" and ").append(IBOAIMonPanelRelatEntityValue.S_State).append("= :pState");
		param.put("pEntityId", entityId);
		param.put("pState", "U");
		return BOAIMonPanelRelatEntityEngine.getBeans(condition.toString(), param);
	}

	public void saveEntityPanelRelat( IBOAIMonPanelRelatEntityValue[] relats) throws Exception {
		if(relats == null) return;
		for(int i=0;i<relats.length;i++){
			if(relats[i].isNew())
				relats[i].setRelatId(BOAIMonPanelRelatEntityEngine.getNewId().longValue());
		}
		BOAIMonPanelRelatEntityEngine.saveBatch(relats);
	}

	public IBOAIMonPanelRelatEntityValue[] getAllRelat() throws Exception {
		HashMap param = new HashMap();
		StringBuilder condition = new StringBuilder();
		condition.append(IBOAIMonPanelRelatEntityValue.S_State).append("= :pState");
		param.put("pState", "U");
		return BOAIMonPanelRelatEntityEngine.getBeans(condition.toString(), param);
	}

}
