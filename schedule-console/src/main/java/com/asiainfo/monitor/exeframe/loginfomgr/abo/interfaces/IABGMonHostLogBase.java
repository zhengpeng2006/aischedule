package com.asiainfo.monitor.exeframe.loginfomgr.abo.interfaces;

import java.io.Serializable;
import java.util.List;
import java.sql.Timestamp;

public interface IABGMonHostLogBase extends Serializable
{

    public void setAcq_log_id(long acq_log_id);

    public long getAcq_log_id();

    public void setHost_id(long host_id);

    public long getHost_id();

    public void setKpi_cpu(String kpi_cpu);

    public String getKpi_cpu();

    public void setKpi_mem(String kpi_mem);

    public String getKpi_mem();

    public void setKpi_fs(String kpi_fs);

    public String getKpi_fs();

    public void setExt_kpi_1(String ext_kpi_1);

    public String getExt_kpi_1();

    public void setExt_kpi_2(String ext_kpi_2);

    public String getExt_kpi_2();

    public void setExt_kpi_3(String ext_kpi_3);

    public String getExt_kpi_3();

    public void setExt_kpi_4(String ext_kpi_4);

    public String getExt_kpi_4();

    public void setSystem_domain(String system_domain);

    public String getSystem_domain();

    public void setSubsystem_domain(String subsystem_domain);

    public String getSubsystem_domain();

    public void setApp_server_name(String app_server_name);

    public String getApp_server_name();

    public void setCreate_date(String create_date);

    public String getCreate_date();

    public void setMon_flag(String mon_flag);

    public String getMon_flag();

    public void setAcq_date(Timestamp acq_date);

    public Timestamp getAcq_date();

    public void setState(String state);

    public String getState();

}
