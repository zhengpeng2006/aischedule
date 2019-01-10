package com.asiainfo.monitor.busi.dao.interfaces;

import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonPanelRelatEntityValue;

public interface IAIMonPanelRelatEntityDAO {

	public IBOAIMonPanelRelatEntityValue[] getRelatByEntityId(String entityId) throws Exception;

	public void saveEntityPanelRelat(IBOAIMonPanelRelatEntityValue[] relats) throws Exception;
	
	public IBOAIMonPanelRelatEntityValue[] getAllRelat() throws Exception;
		

}
