package com.asiainfo.monitor.exeframe.configmgr.abo.interfaces;

import java.io.Serializable;
import java.util.List;
import java.sql.Timestamp;

public interface IAIMonConModeBase extends Serializable
{

    public void setCon_id(long con_id);

    public long getCon_id();

    public void setCon_type(String con_type);

    public String getCon_type();

    public void setCon_port(short con_port);

    public short getCon_port();

    public void setState(String state);

    public String getState();

    public void setCreate_date(Timestamp create_date);

    public Timestamp getCreate_date();

    public void setRemark(String remark);

    public String getRemark();

}
