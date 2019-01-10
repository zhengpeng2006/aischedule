package com.asiainfo.monitor.busi.service.impl;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonLoginSV;
import com.asiainfo.monitor.exeframe.configmgr.abo.dao.interfaces.IAIMonLoginDAO;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonLoginValue;

public class AIMonLoginSVImpl implements IAIMonLoginSV
{

    @Override
    public IBOAIMonLoginValue qryLoginInfoByIpAndName(String ip, String loginName) throws Exception
    {   
        IAIMonLoginDAO dao=(IAIMonLoginDAO)ServiceFactory.getService(IAIMonLoginDAO.class);
        return dao.qryLoginInfoByIpAndName(ip,loginName);
    }

    @Override
    public void saveLoginInfo(IBOAIMonLoginValue loginValue) throws Exception
    {
        IAIMonLoginDAO dao=(IAIMonLoginDAO)ServiceFactory.getService(IAIMonLoginDAO.class);
        dao.saveLoginInfo(loginValue);
    }

    @Override
    public void deleteLoginInfo(IBOAIMonLoginValue loginValue) throws Exception
    {
        IAIMonLoginDAO dao=(IAIMonLoginDAO)ServiceFactory.getService(IAIMonLoginDAO.class);
        dao.deleteLoginInfo(loginValue);
    }

}
