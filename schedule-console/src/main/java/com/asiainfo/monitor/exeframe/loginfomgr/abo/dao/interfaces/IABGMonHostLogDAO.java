package com.asiainfo.monitor.exeframe.loginfomgr.abo.dao.interfaces;

import java.util.Date;

import com.asiainfo.monitor.exeframe.loginfomgr.abo.bo.BOABGMonHostLogBean;
import com.asiainfo.monitor.exeframe.loginfomgr.abo.ivalues.IBOABGMonHostLogValue;

public interface IABGMonHostLogDAO
{

    public long getBeanId() throws Exception;

    public BOABGMonHostLogBean getBeanById(long id) throws Exception;

    public void save(BOABGMonHostLogBean value) throws Exception;

    public IBOABGMonHostLogValue[] qryHisHostKpiInfo(Date beginDate, Date endDate, String hostId, String monFlag) throws Exception;


}
