package com.asiainfo.monitor.exeframe.loginfomgr.abo.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.ailk.appengine.complex.abo.impl.AbstractABO;
import com.ailk.appengine.util.DataBeanUtils;
import com.asiainfo.monitor.exeframe.loginfomgr.abo.interfaces.IABGMonHostLog;
import com.asiainfo.monitor.exeframe.loginfomgr.abo.vo.interfaces.IABGMonHostLogValue;
import java.sql.Timestamp;

public class ABGMonHostLog extends AbstractABO implements IABGMonHostLog
{

    private transient static Log log = LogFactory.getLog(ABGMonHostLog.class);

    private long acq_log_id = 0;

    private long host_id = 0;

    private String kpi_cpu = null;

    private String kpi_mem = null;

    private String kpi_fs = null;

    private String ext_kpi_1 = null;

    private String ext_kpi_2 = null;

    private String ext_kpi_3 = null;

    private String ext_kpi_4 = null;

    private String system_domain = null;

    private String subsystem_domain = null;

    private String app_server_name = null;

    private String create_date = null;

    private String mon_flag = null;

    private Timestamp acq_date = null;

    private String state = null;

    private ABGMonHostLog(String id)
    {
        super();
        this.acq_log_id = Long.parseLong(id);
        this.setComponent("asiainfo/monitor/exeframe/loginfomgr/ABGMonHostLog.cmpt");
    }

    public IABGMonHostLogValue getIABGMonHostLogValue() throws Exception
    {
        return (IABGMonHostLogValue) DataBeanUtils.getVoByAboAndCopy(this, true);
    }

    public void setAcq_log_id(long acq_log_id)
    {
        this.acq_log_id = acq_log_id;
        this.setKey(String.valueOf(acq_log_id));
    }

    public long getAcq_log_id()
    {
        return this.acq_log_id;
    }

    public void setHost_id(long host_id)
    {
        this.host_id = host_id;
    }

    public long getHost_id()
    {
        return this.host_id;
    }

    public void setKpi_cpu(String kpi_cpu)
    {
        this.kpi_cpu = kpi_cpu;
    }

    public String getKpi_cpu()
    {
        return this.kpi_cpu;
    }

    public void setKpi_mem(String kpi_mem)
    {
        this.kpi_mem = kpi_mem;
    }

    public String getKpi_mem()
    {
        return this.kpi_mem;
    }

    public void setKpi_fs(String kpi_fs)
    {
        this.kpi_fs = kpi_fs;
    }

    public String getKpi_fs()
    {
        return this.kpi_fs;
    }

    public void setExt_kpi_1(String ext_kpi_1)
    {
        this.ext_kpi_1 = ext_kpi_1;
    }

    public String getExt_kpi_1()
    {
        return this.ext_kpi_1;
    }

    public void setExt_kpi_2(String ext_kpi_2)
    {
        this.ext_kpi_2 = ext_kpi_2;
    }

    public String getExt_kpi_2()
    {
        return this.ext_kpi_2;
    }

    public void setExt_kpi_3(String ext_kpi_3)
    {
        this.ext_kpi_3 = ext_kpi_3;
    }

    public String getExt_kpi_3()
    {
        return this.ext_kpi_3;
    }

    public void setExt_kpi_4(String ext_kpi_4)
    {
        this.ext_kpi_4 = ext_kpi_4;
    }

    public String getExt_kpi_4()
    {
        return this.ext_kpi_4;
    }

    public void setSystem_domain(String system_domain)
    {
        this.system_domain = system_domain;
    }

    public String getSystem_domain()
    {
        return this.system_domain;
    }

    public void setSubsystem_domain(String subsystem_domain)
    {
        this.subsystem_domain = subsystem_domain;
    }

    public String getSubsystem_domain()
    {
        return this.subsystem_domain;
    }

    public void setApp_server_name(String app_server_name)
    {
        this.app_server_name = app_server_name;
    }

    public String getApp_server_name()
    {
        return this.app_server_name;
    }

    public void setCreate_date(String create_date)
    {
        this.create_date = create_date;
    }

    public String getCreate_date()
    {
        return this.create_date;
    }

    public void setMon_flag(String mon_flag)
    {
        this.mon_flag = mon_flag;
    }

    public String getMon_flag()
    {
        return this.mon_flag;
    }

    public void setAcq_date(Timestamp acq_date)
    {
        this.acq_date = acq_date;
    }

    public Timestamp getAcq_date()
    {
        return this.acq_date;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getState()
    {
        return this.state;
    }

    public void save() throws Exception
    {
        synchronized(this) {
            if(this.isReqPersistence())
                return;
            this.setReqPersistence(true);
            if(this.isCorrelationConsole()) {
            }
        }
    }

    public void executeLazyMethod()
    {
    }

    public void preload() throws Exception
    {
    }

}
