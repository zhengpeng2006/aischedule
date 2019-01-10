package com.asiainfo.monitor.exeframe.loginfomgr.service.impl;

import java.util.Date;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.exeframe.loginfomgr.abo.bo.BOABGMonHostLogBean;
import com.asiainfo.monitor.exeframe.loginfomgr.abo.dao.interfaces.IABGMonHostLogDAO;
import com.asiainfo.monitor.exeframe.loginfomgr.abo.ivalues.IBOABGMonHostLogValue;
import com.asiainfo.monitor.exeframe.loginfomgr.service.interfaces.IABGMonHostLogSV;

public class ABGMonHostLogSVImpl implements IABGMonHostLogSV
{

    @Override
    public void save(BOABGMonHostLogBean bean) throws Exception
    {
        IABGMonHostLogDAO logDAO = (IABGMonHostLogDAO) ServiceFactory.getService(IABGMonHostLogDAO.class);
        logDAO.save(bean);
    }

    @Override
    public IBOABGMonHostLogValue[] qryHisHostKpiInfo(Date beginDate, Date endDate, String hostId, String monFlag) throws Exception
    {
        IABGMonHostLogDAO logDAO = (IABGMonHostLogDAO) ServiceFactory.getService(IABGMonHostLogDAO.class);
        return logDAO.qryHisHostKpiInfo(beginDate, endDate, hostId, monFlag);
    }

}
