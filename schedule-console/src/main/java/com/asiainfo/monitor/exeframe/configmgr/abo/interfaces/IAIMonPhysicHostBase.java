package com.asiainfo.monitor.exeframe.configmgr.abo.interfaces;

import java.io.Serializable;
import java.util.List;
import java.sql.Timestamp;

public interface IAIMonPhysicHostBase extends Serializable
{

    public void setHost_id(long host_id);

    public long getHost_id();

    public void setHost_code(String host_code);

    public String getHost_code();

    public void setHost_name(String host_name);

    public String getHost_name();

    public void setHost_desc(String host_desc);

    public String getHost_desc();

    public void setHost_ip(String host_ip);

    public String getHost_ip();

    public void setState(String state);

    public String getState();

    public void setCreate_date(Timestamp create_date);

    public Timestamp getCreate_date();

    public void setRemarks(String remarks);

    public String getRemarks();

}
