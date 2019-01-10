package com.asiainfo.monitor.exeframe.configmgr.abo.vo.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.HashMap;
import java.util.Map;
import com.ailk.appengine.complex.abo.values.impl.AbstractValueBean;

import com.asiainfo.monitor.exeframe.configmgr.abo.vo.interfaces.IAIMonHostConModeValue;

public class AIMonHostConModeValueBean extends AbstractValueBean implements IAIMonHostConModeValue
{

    private transient static Log log = LogFactory.getLog(AIMonHostConModeValueBean.class);

    private long con_id = 0;

    private long host_id = 0;

    private long relation_id = 0;

    public AIMonHostConModeValueBean()
    {
        super();
    }

    public void setCon_id(long con_id)
    {
        this.con_id = con_id;
    }

    public long getCon_id()
    {
        return this.con_id;
    }

    public void setHost_id(long host_id)
    {
        this.host_id = host_id;
    }

    public long getHost_id()
    {
        return this.host_id;
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

    public Map getClassMap()
    {
        if(this.classMap != null)
            return this.classMap;
        classMap = new HashMap();
        return classMap;
    }

    private String con_type = null;
    private short con_port = (short) 0;
    private String state = null;
    private java.sql.Timestamp create_date = null;
    private String remark = null;

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

    public void setCreate_date(java.sql.Timestamp create_date)
    {
        this.create_date = create_date;
    }

    public java.sql.Timestamp getCreate_date()
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

}
