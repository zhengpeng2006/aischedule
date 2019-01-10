package com.asiainfo.monitor.exeframe.configmgr.abo.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.ailk.appengine.complex.abo.impl.AbstractABO;
import com.ailk.appengine.util.DataBeanUtils;
import com.asiainfo.monitor.exeframe.configmgr.abo.interfaces.IAIMonMasterSlaveRel;
import com.asiainfo.monitor.exeframe.configmgr.abo.vo.interfaces.IAIMonMasterSlaveRelValue;
import java.sql.Timestamp;

public class AIMonMasterSlaveRel extends AbstractABO implements IAIMonMasterSlaveRel
{

    private transient static Log log = LogFactory.getLog(AIMonMasterSlaveRel.class);

    private long relation_id = 0;

    private long master_host_id = 0;

    private long slave_host_id = 0;

    private String state = null;

    private Timestamp create_date = null;

    private String remark = null;

    private AIMonMasterSlaveRel(String id)
    {
        super();
        this.relation_id = Long.parseLong(id);
        this.setComponent("asiainfo/monitor/exeframe/configmgr/AIMonMasterSlaveRel.cmpt");
    }

    public IAIMonMasterSlaveRelValue getIAIMonMasterSlaveRelValue() throws Exception
    {
        return (IAIMonMasterSlaveRelValue) DataBeanUtils.getVoByAboAndCopy(this, true);
    }

    public void setRelation_id(long relation_id)
    {
        this.relation_id = relation_id;
        this.setKey(String.valueOf(relation_id));
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

    public void save() throws Exception
    {
        synchronized(this) {
            if(this.isReqPersistence())
                return;
            this.setReqPersistence(true);
            if(this.isCorrelationConsole()) {
            }
        }
    }

    public void executeLazyMethod()
    {
    }

    public void preload() throws Exception
    {
    }

}
