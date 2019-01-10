package com.asiainfo.monitor.exeframe.configmgr.abo.interfaces;

import java.io.Serializable;
import java.util.List;
import java.sql.Timestamp;

public interface IAIMonMasterSlaveRelBase extends Serializable
{

    public void setRelation_id(long relation_id);

    public long getRelation_id();

    public void setMaster_host_id(long master_host_id);

    public long getMaster_host_id();

    public void setSlave_host_id(long slave_host_id);

    public long getSlave_host_id();

    public void setState(String state);

    public String getState();

    public void setCreate_date(Timestamp create_date);

    public Timestamp getCreate_date();

    public void setRemark(String remark);

    public String getRemark();

}
