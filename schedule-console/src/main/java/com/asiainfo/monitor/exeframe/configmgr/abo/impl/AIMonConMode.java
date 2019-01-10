package com.asiainfo.monitor.exeframe.configmgr.abo.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.ailk.appengine.complex.abo.impl.AbstractABO;
import com.ailk.appengine.util.DataBeanUtils;
import com.asiainfo.monitor.exeframe.configmgr.abo.interfaces.IAIMonConMode;
import com.asiainfo.monitor.exeframe.configmgr.abo.vo.interfaces.IAIMonConModeValue;
import java.sql.Timestamp;

public class AIMonConMode extends AbstractABO implements IAIMonConMode
{

    private transient static Log log = LogFactory.getLog(AIMonConMode.class);

    private long con_id = 0;

    private String con_type = null;

    private short con_port = (short) 0;

    private String state = null;

    private Timestamp create_date = null;

    private String remark = null;

    private AIMonConMode(String id)
    {
        super();
        this.con_id = Long.parseLong(id);
        this.setComponent("asiainfo/monitor/exeframe/configmgr/AIMonConMode.cmpt");
    }

    public void setCon_id(long con_id)
    {
        this.con_id = con_id;
        this.setKey(String.valueOf(con_id));
    }

    public long getCon_id()
    {
        return this.con_id;
    }

    public void setCon_type(String con_type)
    {
        this.con_type = con_type;
    }

    public String getCon_type()
    {
        return this.con_type;
    }

    public void setCon_port(short con_port)
    {
        this.con_port = con_port;
    }

    public short getCon_port()
    {
        return this.con_port;
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

    public IAIMonConModeValue getIAIMonConModeValue() throws Exception
    {
        return (IAIMonConModeValue) DataBeanUtils.getVoByAboAndCopy(this, true);
    }

}
