package com.asiainfo.monitor.exeframe.configmgr.abo.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.ailk.appengine.complex.abo.impl.AbstractABO;
import com.ailk.appengine.util.DataBeanUtils;
import com.asiainfo.monitor.exeframe.configmgr.abo.interfaces.IAIMonGroup;
import com.asiainfo.monitor.exeframe.configmgr.abo.vo.interfaces.IAIMonGroupValue;
import java.sql.Timestamp;

public class AIMonGroup extends AbstractABO implements IAIMonGroup
{

    private transient static Log log = LogFactory.getLog(AIMonGroup.class);

    private long group_id = 0;

    private String group_name = null;

    private String group_desc = null;

    private Timestamp create_date = null;

    private String state = null;

    private String group_code = null;

    private AIMonGroup(String id)
    {
        super();
        this.group_id = Long.parseLong(id);
        this.setComponent("asiainfo/monitor/exeframe/configmgr/AIMonGroup.cmpt");
    }

    public IAIMonGroupValue getIAIMonGroupValue() throws Exception
    {
        return (IAIMonGroupValue) DataBeanUtils.getVoByAboAndCopy(this, true);
    }

    public void setGroup_id(long group_id)
    {
        this.group_id = group_id;
        this.setKey(String.valueOf(group_id));
    }

    public long getGroup_id()
    {
        return this.group_id;
    }

    public void setGroup_name(String group_name)
    {
        this.group_name = group_name;
    }

    public String getGroup_name()
    {
        return this.group_name;
    }

    public void setGroup_desc(String group_desc)
    {
        this.group_desc = group_desc;
    }

    public String getGroup_desc()
    {
        return this.group_desc;
    }

    public void setCreate_date(Timestamp create_date)
    {
        this.create_date = create_date;
    }

    public Timestamp getCreate_date()
    {
        return this.create_date;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getState()
    {
        return this.state;
    }

    public void setGroup_code(String group_code)
    {
        this.group_code = group_code;
    }

    public String getGroup_code()
    {
        return this.group_code;
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
