package com.asiainfo.common.abo.interfaces;

import java.io.Serializable;
import java.util.List;
import java.sql.Timestamp;

public interface IABGMonLRecordBase extends Serializable
{

    public void setRecord_id(long record_id);

    public long getRecord_id();

    public void setInfo_id(long info_id);

    public long getInfo_id();

    public void setInfo_code(String info_code);

    public String getInfo_code();

    public void setHost_name(String host_name);

    public String getHost_name();

    public void setIp(String ip);

    public String getIp();

    public void setMon_type(String mon_type);

    public String getMon_type();

    public void setInfo_name(String info_name);

    public String getInfo_name();

    public void setLayer(String layer);

    public String getLayer();

    public void setFrom_type(String from_type);

    public String getFrom_type();

    public void setGroup_id(String group_id);

    public String getGroup_id();

    public void setIs_trigger_warn(String is_trigger_warn);

    public String getIs_trigger_warn();

    public void setWarn_level(String warn_level);

    public String getWarn_level();

    public void setInfo_value(String info_value);

    public String getInfo_value();

    public void setDone_date(Timestamp done_date);

    public Timestamp getDone_date();

    public void setDone_code(long done_code);

    public long getDone_code();

    public void setBatch_id(long batch_id);

    public long getBatch_id();

    public void setValid_date(Timestamp valid_date);

    public Timestamp getValid_date();

    public void setExpire_date(Timestamp expire_date);

    public Timestamp getExpire_date();

    public void setOp_id(long op_id);

    public long getOp_id();

    public void setOrg_id(long org_id);

    public long getOrg_id();

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

    public void setRemarks(String remarks);

    public String getRemarks();

}
