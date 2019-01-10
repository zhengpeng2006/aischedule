package com.asiainfo.monitor.busi.service.impl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.dao.interfaces.IAIMonPanelRelatEntityDAO;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonPanelRelatEntitySV;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonPanelRelatEntityValue;

public class AIMonPanelRelatEntitySVImpl implements IAIMonPanelRelatEntitySV {

	public IBOAIMonPanelRelatEntityValue[] getRelatByEntityId(String entityId) throws Exception, RemoteException {
		
		IAIMonPanelRelatEntityDAO relatDao = (IAIMonPanelRelatEntityDAO)ServiceFactory.getService(IAIMonPanelRelatEntityDAO.class);
		
		return relatDao.getRelatByEntityId(entityId);
	}

	public void saveEntityPanelRelat(IBOAIMonPanelRelatEntityValue[] relats) throws Exception, RemoteException {

		IAIMonPanelRelatEntityDAO relatDao = (IAIMonPanelRelatEntityDAO)ServiceFactory.getService(IAIMonPanelRelatEntityDAO.class);
		
		relatDao.saveEntityPanelRelat(relats);
		
	}

	/**
	 * 获取所有配置的事体与面板关联配置
	 */
	public Map getAllRelat() throws Exception,RemoteException {
		Map ret = new HashMap();
		List panelList = new ArrayList();
		IAIMonPanelRelatEntityDAO relatDao = (IAIMonPanelRelatEntityDAO)ServiceFactory.getService(IAIMonPanelRelatEntityDAO.class);
		IBOAIMonPanelRelatEntityValue[] values = relatDao.getAllRelat();
		if(values==null||values.length==0){
			return null;
		}else{
			for(int i=0;i<values.length;i++){
				if(ret.get(String.valueOf(values[i].getEntityId()))!=null){
				  panelList = (List)ret.get(String.valueOf(values[i].getEntityId()));
				}else{
				  panelList = new ArrayList();
				}
				panelList.add(values[i]);
        ret.put(String.valueOf(values[i].getEntityId()), panelList);
			}
		}
		return ret;
	}
}
