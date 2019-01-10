package com.asiainfo.common.abo.vo.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.HashMap;
import java.util.Map;
import com.ailk.appengine.complex.abo.values.impl.AbstractValueBean;
import com.asiainfo.common.abo.vo.interfaces.IABGMonWTriggerValue;
import java.sql.Timestamp;

public class ABGMonWTriggerValueBean extends AbstractValueBean implements IABGMonWTriggerValue
{

    private transient static Log log = LogFactory.getLog(ABGMonWTriggerValueBean.class);

    private long trigger_Id = 0;

    private long record_Id = 0;

    private String ip = null;

    private long info_Id = 0;

    private String info_Name = null;

    private String layer = null;

    private String phonenum = null;

    private String content = null;

    private String warn_level = null;

    private Timestamp expiry_date = null;

    private String system_domain = null;

    private String subsystem_domain = null;

    private String app_server_name = null;

    private String create_date = null;

    private Timestamp create_time = null;

    private Timestamp done_date = null;

    private String state = null;

    private String remarks = null;

    public ABGMonWTriggerValueBean()
    {
        super();
    }

    public void setTrigger_Id(long trigger_Id)
    {
        this.trigger_Id = trigger_Id;
        this.setKey(this.trigger_Id + "");
    }

    public long getTrigger_Id()
    {
        return this.trigger_Id;
    }

    public void setRecord_Id(long record_Id)
    {
        this.record_Id = record_Id;
    }

    public long getRecord_Id()
    {
        return this.record_Id;
    }

    public void setIp(String ip)
    {
        this.ip = ip;
    }

    public String getIp()
    {
        return this.ip;
    }

    public void setInfo_Id(long info_Id)
    {
        this.info_Id = info_Id;
    }

    public long getInfo_Id()
    {
        return this.info_Id;
    }

    public void setInfo_Name(String info_Name)
    {
        this.info_Name = info_Name;
    }

    public String getInfo_Name()
    {
        return this.info_Name;
    }

    public void setLayer(String layer)
    {
        this.layer = layer;
    }

    public String getLayer()
    {
        return this.layer;
    }

    public void setPhonenum(String phonenum)
    {
        this.phonenum = phonenum;
    }

    public String getPhonenum()
    {
        return this.phonenum;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getContent()
    {
        return this.content;
    }

    public void setWarn_level(String warn_level)
    {
        this.warn_level = warn_level;
    }

    public String getWarn_level()
    {
        return this.warn_level;
    }

    public void setExpiry_date(Timestamp expiry_date)
    {
        this.expiry_date = expiry_date;
    }

    public Timestamp getExpiry_date()
    {
        return this.expiry_date;
    }

    public void setSystem_domain(String system_domain)
    {
        this.system_domain = system_domain;
    }

    public String getSystem_domain()
    {
        return this.system_domain;
    }

    public void setSubsystem_domain(String subsystem_domain)
    {
        this.subsystem_domain = subsystem_domain;
    }

    public String getSubsystem_domain()
    {
        return this.subsystem_domain;
    }

    public void setApp_server_name(String app_server_name)
    {
        this.app_server_name = app_server_name;
    }

    public String getApp_server_name()
    {
        return this.app_server_name;
    }

    public void setCreate_date(String create_date)
    {
        this.create_date = create_date;
    }

    public String getCreate_date()
    {
        return this.create_date;
    }

    public void setCreate_time(Timestamp create_time)
    {
        this.create_time = create_time;
    }

    public Timestamp getCreate_time()
    {
        return this.create_time;
    }

    public void setDone_date(Timestamp done_date)
    {
        this.done_date = done_date;
    }

    public Timestamp getDone_date()
    {
        return this.done_date;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getState()
    {
        return this.state;
    }

    public void setRemarks(String remarks)
    {
        this.remarks = remarks;
    }

    public String getRemarks()
    {
        return this.remarks;
    }

    public Map getClassMap()
    {
        if(this.classMap != null)
            return this.classMap;
        classMap = new HashMap();
        return classMap;
    }

}
