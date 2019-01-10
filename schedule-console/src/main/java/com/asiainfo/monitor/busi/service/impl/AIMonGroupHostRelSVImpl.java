package com.asiainfo.monitor.busi.service.impl;

import java.util.List;
import java.util.Map;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonGroupHostRelSV;
import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonGroupHostRelBean;
import com.asiainfo.monitor.exeframe.configmgr.abo.dao.interfaces.IAIMonGroupHostRelDAO;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonGroupHostRelValue;

public class AIMonGroupHostRelSVImpl implements IAIMonGroupHostRelSV
{

    @Override
    public void save(BOAIMonGroupHostRelBean bean) throws Exception
    {
        IAIMonGroupHostRelDAO ghDAO = (IAIMonGroupHostRelDAO) ServiceFactory.getService(IAIMonGroupHostRelDAO.class);
        ghDAO.save(bean);

    }

    @Override
    public BOAIMonGroupHostRelBean[] qryGroupHost(String groupId, String hostId) throws Exception
    {
        IAIMonGroupHostRelDAO ghDAO = (IAIMonGroupHostRelDAO) ServiceFactory.getService(IAIMonGroupHostRelDAO.class);
        return ghDAO.qryGroupHost(groupId, hostId);
    }

    @Override
    public void delete(IBOAIMonGroupHostRelValue[] values) throws Exception
    {
        IAIMonGroupHostRelDAO ghDAO = (IAIMonGroupHostRelDAO) ServiceFactory.getService(IAIMonGroupHostRelDAO.class);
        ghDAO.delete(values);
    }

    @Override
    public List<String> getHostListByGroupId(String groupId) throws Exception
    {
        IAIMonGroupHostRelDAO ghDAO = (IAIMonGroupHostRelDAO) ServiceFactory.getService(IAIMonGroupHostRelDAO.class);
        return ghDAO.getHostListByGroupId(groupId);
    }

	@Override
	public Map<Long, String> getHostGroups() throws Exception{
        IAIMonGroupHostRelDAO ghDAO = (IAIMonGroupHostRelDAO) ServiceFactory.getService(IAIMonGroupHostRelDAO.class);
		return ghDAO.getHostGroups();
	}

}
