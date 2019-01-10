package com.asiainfo.monitor.exeframe.configmgr.abo.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.ailk.appengine.complex.abo.impl.AbstractABO;
import com.ailk.appengine.util.DataBeanUtils;
import com.asiainfo.monitor.exeframe.configmgr.abo.interfaces.IAIMonGroupHostRel;
import com.asiainfo.monitor.exeframe.configmgr.abo.vo.interfaces.IAIMonGroupHostRelValue;
import java.sql.Timestamp;

public class AIMonGroupHostRel extends AbstractABO implements IAIMonGroupHostRel
{

    private transient static Log log = LogFactory.getLog(AIMonGroupHostRel.class);

    private long relation_id = 0;

    private long group_id = 0;

    private long host_id = 0;

    private Timestamp create_date = null;

    private AIMonGroupHostRel(String id)
    {
        super();
        this.relation_id = Long.parseLong(id);
        this.setComponent("asiainfo/monitor/exeframe/configmgr/AIMonGroupHostRel.cmpt");
    }

    public IAIMonGroupHostRelValue getIAIMonGroupHostRelValue() throws Exception
    {
        return (IAIMonGroupHostRelValue) DataBeanUtils.getVoByAboAndCopy(this, true);
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

    public void setGroup_id(long group_id)
    {
        this.group_id = group_id;
    }

    public long getGroup_id()
    {
        return this.group_id;
    }

    public void setHost_id(long host_id)
    {
        this.host_id = host_id;
    }

    public long getHost_id()
    {
        return this.host_id;
    }

    public void setCreate_date(Timestamp create_date)
    {
        this.create_date = create_date;
    }

    public Timestamp getCreate_date()
    {
        return this.create_date;
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

    private String state = null;
    private String remark = null;

    public void setState(String state)
    {
        this.state = state;
    }

    public String getState()
    {
        return this.state;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public String getRemark()
    {
        return this.remark;
    }

}
