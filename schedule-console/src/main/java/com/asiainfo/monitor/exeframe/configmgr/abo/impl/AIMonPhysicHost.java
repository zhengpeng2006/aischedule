package com.asiainfo.monitor.exeframe.configmgr.abo.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.ailk.appengine.complex.abo.impl.AbstractABO;
import com.ailk.appengine.util.DataBeanUtils;
import com.asiainfo.monitor.exeframe.configmgr.abo.interfaces.IAIMonPhysicHost;
import com.asiainfo.monitor.exeframe.configmgr.abo.vo.interfaces.IAIMonPhysicHostValue;
import java.sql.Timestamp;

public class AIMonPhysicHost extends AbstractABO implements IAIMonPhysicHost
{

    private transient static Log log = LogFactory.getLog(AIMonPhysicHost.class);

    private long host_id = 0;

    private String host_code = null;

    private String host_name = null;

    private String host_desc = null;

    private String host_ip = null;

    private String state = null;

    private Timestamp create_date = null;

    private String remarks = null;

    private AIMonPhysicHost(String id)
    {
        super();
        this.host_id = Long.parseLong(id);
        this.setComponent("asiainfo/monitor/exeframe/configmgr/AIMonPhysicHost.cmpt");
    }

    public void setHost_id(long host_id)
    {
        this.host_id = host_id;
        this.setKey(String.valueOf(host_id));
    }

    public long getHost_id()
    {
        return this.host_id;
    }

    public void setHost_code(String host_code)
    {
        this.host_code = host_code;
    }

    public String getHost_code()
    {
        return this.host_code;
    }

    public void setHost_name(String host_name)
    {
        this.host_name = host_name;
    }

    public String getHost_name()
    {
        return this.host_name;
    }

    public void setHost_desc(String host_desc)
    {
        this.host_desc = host_desc;
    }

    public String getHost_desc()
    {
        return this.host_desc;
    }

    public void setHost_ip(String host_ip)
    {
        this.host_ip = host_ip;
    }

    public String getHost_ip()
    {
        return this.host_ip;
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

    public void setRemarks(String remarks)
    {
        this.remarks = remarks;
    }

    public String getRemarks()
    {
        return this.remarks;
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

    private long host_user_id = 0;
    private long con_id = 0;
    private String attend_mode = null;
    private short host_port = (short) 0;
    private String host_user = null;
    private String host_pwd = null;

    public IAIMonPhysicHostValue getIAIMonPhysicHostValue() throws Exception
    {
        return (IAIMonPhysicHostValue) DataBeanUtils.getVoByAboAndCopy(this, true);
    }

    public void setHost_user_id(long host_user_id)
    {
        this.host_user_id = host_user_id;
    }

    public long getHost_user_id()
    {
        return this.host_user_id;
    }

    public void setCon_id(long con_id)
    {
        this.con_id = con_id;
    }

    public long getCon_id()
    {
        return this.con_id;
    }

    public void setAttend_mode(String attend_mode)
    {
        this.attend_mode = attend_mode;
    }

    public String getAttend_mode()
    {
        return this.attend_mode;
    }

    public void setHost_port(short host_port)
    {
        this.host_port = host_port;
    }

    public short getHost_port()
    {
        return this.host_port;
    }

    public void setHost_user(String host_user)
    {
        this.host_user = host_user;
    }

    public String getHost_user()
    {
        return this.host_user;
    }

    public void setHost_pwd(String host_pwd)
    {
        this.host_pwd = host_pwd;
    }

    public String getHost_pwd()
    {
        return this.host_pwd;
    }

}
