package com.asiainfo.monitor.exeframe.configmgr.abo.vo.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.HashMap;
import java.util.Map;
import com.ailk.appengine.complex.abo.values.impl.AbstractValueBean;
import com.asiainfo.monitor.exeframe.configmgr.abo.vo.interfaces.IAIMonServerValue;
import java.sql.Timestamp;

public class AIMonServerValueBean extends AbstractValueBean implements IAIMonServerValue
{

    private transient static Log log = LogFactory.getLog(AIMonServerValueBean.class);

    private long server_id = 0;

    private String server_code = null;

    private long node_id = 0;

    private String server_name = null;

    private String business_type = null;

    private String locator_type = null;

    private String locator = null;

    private String server_ip = null;

    private short server_port = (short) 0;

    private String sversion = null;

    private String check_url = null;

    private String midware_type = null;

    private short start_cmd_id = (short) 0;

    private short stop_cmd_id = (short) 0;

    private String state = null;

    private Timestamp create_date = null;

    private String remark = null;

    private String temp_type = null;

    public AIMonServerValueBean()
    {
        super();
    }

    public void setServer_id(long server_id)
    {
        this.server_id = server_id;
        this.setKey(this.server_id + "");
    }

    public long getServer_id()
    {
        return this.server_id;
    }

    public void setServer_code(String server_code)
    {
        this.server_code = server_code;
    }

    public String getServer_code()
    {
        return this.server_code;
    }

    public void setNode_id(long node_id)
    {
        this.node_id = node_id;
    }

    public long getNode_id()
    {
        return this.node_id;
    }

    public void setServer_name(String server_name)
    {
        this.server_name = server_name;
    }

    public String getServer_name()
    {
        return this.server_name;
    }

    public void setBusiness_type(String business_type)
    {
        this.business_type = business_type;
    }

    public String getBusiness_type()
    {
        return this.business_type;
    }

    public void setLocator_type(String locator_type)
    {
        this.locator_type = locator_type;
    }

    public String getLocator_type()
    {
        return this.locator_type;
    }

    public void setLocator(String locator)
    {
        this.locator = locator;
    }

    public String getLocator()
    {
        return this.locator;
    }

    public void setServer_ip(String server_ip)
    {
        this.server_ip = server_ip;
    }

    public String getServer_ip()
    {
        return this.server_ip;
    }

    public void setServer_port(short server_port)
    {
        this.server_port = server_port;
    }

    public short getServer_port()
    {
        return this.server_port;
    }

    public void setSversion(String sversion)
    {
        this.sversion = sversion;
    }

    public String getSversion()
    {
        return this.sversion;
    }

    public void setCheck_url(String check_url)
    {
        this.check_url = check_url;
    }

    public String getCheck_url()
    {
        return this.check_url;
    }

    public void setMidware_type(String midware_type)
    {
        this.midware_type = midware_type;
    }

    public String getMidware_type()
    {
        return this.midware_type;
    }

    public void setStart_cmd_id(short start_cmd_id)
    {
        this.start_cmd_id = start_cmd_id;
    }

    public short getStart_cmd_id()
    {
        return this.start_cmd_id;
    }

    public void setStop_cmd_id(short stop_cmd_id)
    {
        this.stop_cmd_id = stop_cmd_id;
    }

    public short getStop_cmd_id()
    {
        return this.stop_cmd_id;
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

    public void setTemp_type(String temp_type)
    {
        this.temp_type = temp_type;
    }

    public String getTemp_type()
    {
        return this.temp_type;
    }

    public Map getClassMap()
    {
        if(this.classMap != null)
            return this.classMap;
        classMap = new HashMap();
        return classMap;
    }

    private long host_id = 0;

    public void setHost_id(long host_id)
    {
        this.host_id = host_id;
    }

    public long getHost_id()
    {
        return this.host_id;
    }

}
