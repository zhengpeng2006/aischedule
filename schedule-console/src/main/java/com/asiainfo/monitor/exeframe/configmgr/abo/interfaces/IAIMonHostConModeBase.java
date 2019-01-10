package com.asiainfo.monitor.exeframe.configmgr.abo.interfaces;

import java.io.Serializable;
import java.util.List;

public interface IAIMonHostConModeBase extends Serializable
{

    public void setCon_id(long con_id);

    public long getCon_id();

    public void setHost_id(long host_id);

    public long getHost_id();

    public void setRelation_id(long relation_id);

    public long getRelation_id();

}
