package com.asiainfo.monitor.exeframe.configmgr.abo.interfaces;

import java.io.Serializable;
import java.util.List;
import java.sql.Timestamp;

public interface IAIMonGroupBase extends Serializable
{

    public void setGroup_id(long group_id);

    public long getGroup_id();

    public void setGroup_name(String group_name);

    public String getGroup_name();

    public void setGroup_desc(String group_desc);

    public String getGroup_desc();

    public void setCreate_date(Timestamp create_date);

    public Timestamp getCreate_date();

    public void setState(String state);

    public String getState();

    public void setGroup_code(String group_code);

    public String getGroup_code();

}
