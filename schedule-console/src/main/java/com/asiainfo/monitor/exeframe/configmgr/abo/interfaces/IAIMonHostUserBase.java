package com.asiainfo.monitor.exeframe.configmgr.abo.interfaces;

import java.io.Serializable;
import java.util.List;
import java.sql.Timestamp;

public interface IAIMonHostUserBase extends Serializable
{

    public void setHost_user_id(long host_user_id);

    public long getHost_user_id();

    public void setUser_name(String user_name);

    public String getUser_name();

    public void setUser_pwd(String user_pwd);

    public String getUser_pwd();

    public void setState(String state);

    public String getState();

    public void setCreate_date(Timestamp create_date);

    public Timestamp getCreate_date();

    public void setRemark(String remark);

    public String getRemark();

    public void setHost_id(long host_id);

    public long getHost_id();

    public void setUser_type(String user_type);

    public String getUser_type();

}
