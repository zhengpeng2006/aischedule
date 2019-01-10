package com.asiainfo.monitor.exeframe.configmgr.abo.vo.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.HashMap;
import java.util.Map;
import com.ailk.appengine.complex.abo.values.impl.AbstractValueBean;

import com.asiainfo.monitor.exeframe.configmgr.abo.vo.interfaces.IAIMonMasterSlaveRelValue;
import java.sql.Timestamp;

public class AIMonMasterSlaveRelValueBean extends AbstractValueBean implements IAIMonMasterSlaveRelValue
{

    private transient static Log log = LogFactory.getLog(AIMonMasterSlaveRelValueBean.class);

    private long relation_id = 0;

    private long master_host_id = 0;

    private long slave_host_id = 0;

    private String state = null;

    private Timestamp create_date = null;

    private String remark = null;

    public AIMonMasterSlaveRelValueBean()
    {
        super();
    }

    public void setRelation_id(long relation_id)
    {
        this.relation_id = relation_id;
        this.setKey(this.relation_id + "");
    }

    public long getRelation_id()
    {
        return this.relation_id;
    }

    public void setMaster_host_id(long master_host_id)
    {
        this.master_host_id = master_host_id;
    }

    public long getMaster_host_id()
    {
        return this.master_host_id;
    }

    public void setSlave_host_id(long slave_host_id)
    {
        this.slave_host_id = slave_host_id;
    }

    public long getSlave_host_id()
    {
        return this.slave_host_id;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getState()
    {
        return this.state;
    }

    public void setCreate_date(Timestamp create_date)
    {
        this.create_date = create_date;
    }

    public Timestamp getCreate_date()
    {
        return this.create_date;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public String getRemark()
    {
        return this.remark;
    }

    public Map getClassMap()
    {
        if(this.classMap != null)
            return this.classMap;
        classMap = new HashMap();
        return classMap;
    }

}
