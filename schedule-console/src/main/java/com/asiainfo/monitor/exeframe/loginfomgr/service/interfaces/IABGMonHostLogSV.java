package com.asiainfo.monitor.exeframe.loginfomgr.service.interfaces;

import java.util.Date;

import com.asiainfo.monitor.exeframe.loginfomgr.abo.bo.BOABGMonHostLogBean;
import com.asiainfo.monitor.exeframe.loginfomgr.abo.ivalues.IBOABGMonHostLogValue;

public interface IABGMonHostLogSV
{
    /**
     * 主机Kpi信息保存进日志库
     * 
     * @param request
     * @throws Exception
     */
    public void save(BOABGMonHostLogBean bean) throws Exception;

    /**
     * 查询主机kpi初始历史信息
     * 
     * @param request
     * @throws Exception
     */
    public IBOABGMonHostLogValue[] qryHisHostKpiInfo(Date beginDate, Date endDate, String hostId, String monFlag) throws Exception;



}
