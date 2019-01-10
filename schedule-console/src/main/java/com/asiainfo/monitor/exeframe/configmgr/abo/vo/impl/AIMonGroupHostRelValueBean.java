package com.asiainfo.monitor.exeframe.configmgr.abo.vo.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.HashMap;
import java.util.Map;
import com.ailk.appengine.complex.abo.values.impl.AbstractValueBean;

import com.asiainfo.monitor.exeframe.configmgr.abo.vo.interfaces.IAIMonGroupHostRelValue;
import java.sql.Timestamp;

public class AIMonGroupHostRelValueBean extends AbstractValueBean implements IAIMonGroupHostRelValue
{

    private transient static Log log = LogFactory.getLog(AIMonGroupHostRelValueBean.class);

    private long relation_id = 0;

    private long group_id = 0;

    private long host_id = 0;

    private Timestamp create_date = null;

    public AIMonGroupHostRelValueBean()
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

    public Map getClassMap()
    {
        if(this.classMap != null)
            return this.classMap;
        classMap = new HashMap();
        return classMap;
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
