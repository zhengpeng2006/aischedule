package com.asiainfo.monitor.exeframe.configmgr.abo.vo.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.HashMap;
import java.util.Map;
import com.ailk.appengine.complex.abo.values.impl.AbstractValueBean;

import com.asiainfo.monitor.exeframe.configmgr.abo.vo.interfaces.IAIMonGroupValue;
import java.sql.Timestamp;

public class AIMonGroupValueBean extends AbstractValueBean implements IAIMonGroupValue
{

    private transient static Log log = LogFactory.getLog(AIMonGroupValueBean.class);

    private long group_id = 0;

    private String group_name = null;

    private String group_desc = null;

    private Timestamp create_date = null;

    private String state = null;

    private String group_code = null;

    public AIMonGroupValueBean()
    {
        super();
    }

    public void setGroup_id(long group_id)
    {
        this.group_id = group_id;
        this.setKey(this.group_id + "");
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

    public Map getClassMap()
    {
        if(this.classMap != null)
            return this.classMap;
        classMap = new HashMap();
        return classMap;
    }

}
