package com.asiainfo.monitor.exeframe.configmgr.abo.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.ailk.appengine.complex.abo.impl.AbstractABO;
import com.ailk.appengine.util.DataBeanUtils;
import com.asiainfo.monitor.exeframe.configmgr.abo.interfaces.IAIMonHostUser;
import com.asiainfo.monitor.exeframe.configmgr.abo.vo.interfaces.IAIMonHostUserValue;
import java.sql.Timestamp;

public class AIMonHostUser extends AbstractABO implements IAIMonHostUser
{

    private transient static Log log = LogFactory.getLog(AIMonHostUser.class);

    private long host_user_id = 0;

    private String user_name = null;

    private String user_pwd = null;

    private String state = null;

    private Timestamp create_date = null;

    private String remark = null;

    private long host_id = 0;

    private String user_type = null;

    private AIMonHostUser(String id)
    {
        super();
        this.host_user_id = Long.parseLong(id);
        this.setComponent("asiainfo/monitor/exeframe/configmgr/AIMonHostUser.cmpt");
    }

    public IAIMonHostUserValue getIAIMonHostUserValue() throws Exception
    {
        return (IAIMonHostUserValue) DataBeanUtils.getVoByAboAndCopy(this, true);
    }

    public void setHost_user_id(long host_user_id)
    {
        this.host_user_id = host_user_id;
        this.setKey(String.valueOf(host_user_id));
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
