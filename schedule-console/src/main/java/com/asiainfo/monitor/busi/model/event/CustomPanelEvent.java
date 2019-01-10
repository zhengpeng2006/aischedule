package com.asiainfo.monitor.busi.model.event;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.model.interfaces.IDomainEvent;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonCustomizePanelRelateSV;
import com.asiainfo.monitor.tools.util.TypeConst;

public class CustomPanelEvent implements IDomainEvent {

	public Object getEventResult(Object obj) {
		Object result=null;
		try{
			IAIMonCustomizePanelRelateSV relateSV=(IAIMonCustomizePanelRelateSV)ServiceFactory.getService(IAIMonCustomizePanelRelateSV.class);
			String[] keyStr=String.valueOf(obj).split(TypeConst._INTERPRET_CHAR);
			
			result=relateSV.getCustomPanelRelateById(keyStr[0],keyStr[1]);
			
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		return result;
	}

}
