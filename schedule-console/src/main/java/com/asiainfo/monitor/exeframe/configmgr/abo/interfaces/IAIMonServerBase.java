package com.asiainfo.monitor.exeframe.configmgr.abo.interfaces;

import java.io.Serializable;
import java.util.List;
import java.sql.Timestamp;

public interface IAIMonServerBase extends Serializable
{

    public void setServer_id(long server_id);

    public long getServer_id();

    public void setServer_code(String server_code);

    public String getServer_code();

    public void setNode_id(long node_id);

    public long getNode_id();

    public void setServer_name(String server_name);

    public String getServer_name();

    public void setBusiness_type(String business_type);

    public String getBusiness_type();

    public void setLocator_type(String locator_type);

    public String getLocator_type();

    public void setLocator(String locator);

    public String getLocator();

    public void setServer_ip(String server_ip);

    public String getServer_ip();

    public void setServer_port(short server_port);

    public short getServer_port();

    public void setSversion(String sversion);

    public String getSversion();

    public void setCheck_url(String check_url);

    public String getCheck_url();

    public void setMidware_type(String midware_type);

    public String getMidware_type();

    public void setStart_cmd_id(short start_cmd_id);

    public short getStart_cmd_id();

    public void setStop_cmd_id(short stop_cmd_id);

    public short getStop_cmd_id();

    public void setState(String state);

    public String getState();

    public void setCreate_date(Timestamp create_date);

    public Timestamp getCreate_date();

    public void setRemark(String remark);

    public String getRemark();

    public void setTemp_type(String temp_type);

    public String getTemp_type();

}
