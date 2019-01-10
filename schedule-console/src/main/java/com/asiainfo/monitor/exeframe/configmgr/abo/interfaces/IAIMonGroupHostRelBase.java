package com.asiainfo.monitor.exeframe.configmgr.abo.interfaces;

import java.io.Serializable;
import java.util.List;
import java.sql.Timestamp;

public interface IAIMonGroupHostRelBase extends Serializable
{

    public void setRelation_id(long relation_id);

    public long getRelation_id();

    public void setGroup_id(long group_id);

    public long getGroup_id();

    public void setHost_id(long host_id);

    public long getHost_id();

    public void setCreate_date(Timestamp create_date);

    public Timestamp getCreate_date();

}
