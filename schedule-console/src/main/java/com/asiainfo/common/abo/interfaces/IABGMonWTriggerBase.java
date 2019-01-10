package com.asiainfo.common.abo.interfaces;

import java.io.Serializable;
import java.util.List;
import java.sql.Timestamp;

public interface IABGMonWTriggerBase extends Serializable
{

    public void setTrigger_Id(long trigger_Id);

    public long getTrigger_Id();

    public void setRecord_Id(long record_Id);

    public long getRecord_Id();

    public void setIp(String ip);

    public String getIp();

    public void setInfo_Id(long info_Id);

    public long getInfo_Id();

    public void setInfo_Name(String info_Name);

    public String getInfo_Name();

    public void setLayer(String layer);

    public String getLayer();

    public void setPhonenum(String phonenum);

    public String getPhonenum();

    public void setContent(String content);

    public String getContent();

    public void setWarn_level(String warn_level);

    public String getWarn_level();

    public void setExpiry_date(Timestamp expiry_date);

    public Timestamp getExpiry_date();

    public void setSystem_domain(String system_domain);

    public String getSystem_domain();

    public void setSubsystem_domain(String subsystem_domain);

    public String getSubsystem_domain();

    public void setApp_server_name(String app_server_name);

    public String getApp_server_name();

    public void setCreate_date(String create_date);

    public String getCreate_date();

    public void setCreate_time(Timestamp create_time);

    public Timestamp getCreate_time();

    public void setDone_date(Timestamp done_date);

    public Timestamp getDone_date();

    public void setState(String state);

    public String getState();

    public void setRemarks(String remarks);

    public String getRemarks();

}
