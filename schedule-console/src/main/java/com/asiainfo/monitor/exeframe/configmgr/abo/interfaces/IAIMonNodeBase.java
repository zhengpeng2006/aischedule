package com.asiainfo.monitor.exeframe.configmgr.abo.interfaces;

import java.io.Serializable;
import java.util.List;
import java.sql.Timestamp;

public interface IAIMonNodeBase extends Serializable
{

    public void setNode_id(long node_id);

    public long getNode_id();

    public void setHost_id(long host_id);

    public long getHost_id();

    public void setNode_code(String node_code);

    public String getNode_code();

    public void setNode_name(String node_name);

    public String getNode_name();

    public void setNode_path(String node_path);

    public String getNode_path();

    public void setState(String state);

    public String getState();

    public void setCreate_date(Timestamp create_date);

    public Timestamp getCreate_date();

    public void setRemark(String remark);

    public String getRemark();

}
