package com.asiainfo.monitor.exeframe.configmgr.abo.vo.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.HashMap;
import java.util.Map;
import com.ailk.appengine.complex.abo.values.impl.AbstractValueBean;

import com.asiainfo.monitor.exeframe.configmgr.abo.vo.interfaces.IAIMonHostUserValue;
import java.sql.Timestamp;

public class AIMonHostUserValueBean extends AbstractValueBean implements IAIMonHostUserValue
{

    private transient static Log log = LogFactory.getLog(AIMonHostUserValueBean.class);

    private long host_user_id = 0;

    private String user_name = null;

    private String user_pwd = null;

    private String state = null;

    private Timestamp create_date = null;

    private String remark = null;

    private long host_id = 0;

    private String user_type = null;

    public AIMonHostUserValueBean()
    {
        super();
    }

    public void setHost_user_id(long host_user_id)
    {
        this.host_user_id = host_user_id;
        this.setKey(this.host_user_id + "");
    }

    public long getHost_user_id()
    {
        return this.host_user_id;
    }

    public void setUser_name(String user_name)
    {
        this.user_name = user_name;
    }

    public String getUser_name()
    {
        return this.user_name;
    }

    public void setUser_pwd(String user_pwd)
    {
        this.user_pwd = user_pwd;
    }

    public String getUser_pwd()
    {
        return this.user_pwd;
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

    public void setHost_id(long host_id)
    {
        this.host_id = host_id;
    }

    public long getHost_id()
    {
        return this.host_id;
    }

    public void setUser_type(String user_type)
    {
        this.user_type = user_type;
    }

    public String getUser_type()
    {
        return this.user_type;
    }

    public Map getClassMap()
    {
        if(this.classMap != null)
            return this.classMap;
        classMap = new HashMap();
        return classMap;
    }

}
