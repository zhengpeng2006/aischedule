package com.asiainfo.monitor.exeframe.configmgr.abo.vo.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.HashMap;
import java.util.Map;
import com.ailk.appengine.complex.abo.values.impl.AbstractValueBean;

import com.asiainfo.monitor.exeframe.configmgr.abo.vo.interfaces.IAIMonNodeValue;
import java.sql.Timestamp;

public class AIMonNodeValueBean extends AbstractValueBean implements IAIMonNodeValue
{

    private transient static Log log = LogFactory.getLog(AIMonNodeValueBean.class);

    private long node_id = 0;

    private long host_id = 0;

    private String node_code = null;

    private String node_name = null;

    private String node_path = null;

    private String state = null;

    private Timestamp create_date = null;

    private String remark = null;

    public AIMonNodeValueBean()
    {
        super();
    }

    public void setNode_id(long node_id)
    {
        this.node_id = node_id;
        this.setKey(this.node_id + "");
    }

    public long getNode_id()
    {
        return this.node_id;
    }

    public void setHost_id(long host_id)
    {
        this.host_id = host_id;
    }

    public long getHost_id()
    {
        return this.host_id;
    }

    public void setNode_code(String node_code)
    {
        this.node_code = node_code;
    }

    public String getNode_code()
    {
        return this.node_code;
    }

    public void setNode_name(String node_name)
    {
        this.node_name = node_name;
    }

    public String getNode_name()
    {
        return this.node_name;
    }

    public void setNode_path(String node_path)
    {
        this.node_path = node_path;
    }

    public String getNode_path()
    {
        return this.node_path;
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

    public Map getClassMap()
    {
        if(this.classMap != null)
            return this.classMap;
        classMap = new HashMap();
        return classMap;
    }

    private String attend_mode = null;
    private String host_ip = null;
    private short host_port = (short) 0;
    private String host_user = null;
    private String host_pwd = null;
    private long group_id = 0;

    public void setAttend_mode(String attend_mode)
    {
        this.attend_mode = attend_mode;
    }

    public String getAttend_mode()
    {
        return this.attend_mode;
    }

    public void setHost_ip(String host_ip)
    {
        this.host_ip = host_ip;
    }

    public String getHost_ip()
    {
        return this.host_ip;
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

    public void setGroup_id(long group_id)
    {
        this.group_id = group_id;
    }

    public long getGroup_id()
    {
        return this.group_id;
    }

}
