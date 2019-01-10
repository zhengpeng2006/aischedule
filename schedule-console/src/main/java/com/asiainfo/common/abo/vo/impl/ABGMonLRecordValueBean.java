package com.asiainfo.common.abo.vo.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.HashMap;
import java.util.Map;
import com.ailk.appengine.complex.abo.values.impl.AbstractValueBean;
import com.asiainfo.common.abo.vo.interfaces.IABGMonLRecordValue;
import java.sql.Timestamp;

public class ABGMonLRecordValueBean extends AbstractValueBean implements IABGMonLRecordValue
{

    private transient static Log log = LogFactory.getLog(ABGMonLRecordValueBean.class);

    private long record_id = 0;

    private long info_id = 0;

    private String info_code = null;

    private String host_name = null;

    private String ip = null;

    private String mon_type = null;

    private String info_name = null;

    private String layer = null;

    private String from_type = null;

    private String group_id = null;

    private String is_trigger_warn = null;

    private String warn_level = null;

    private String info_value = null;

    private Timestamp done_date = null;

    private long done_code = 0;

    private long batch_id = 0;

    private Timestamp valid_date = null;

    private Timestamp expire_date = null;

    private long op_id = 0;

    private long org_id = 0;

    private String system_domain = null;

    private String subsystem_domain = null;

    private String app_server_name = null;

    private String create_date = null;

    private Timestamp create_time = null;

    private String remarks = null;

    public ABGMonLRecordValueBean()
    {
        super();
    }

    public void setRecord_id(long record_id)
    {
        this.record_id = record_id;
        this.setKey(this.record_id + "");
    }

    public long getRecord_id()
    {
        return this.record_id;
    }

    public void setInfo_id(long info_id)
    {
        this.info_id = info_id;
    }

    public long getInfo_id()
    {
        return this.info_id;
    }

    public void setInfo_code(String info_code)
    {
        this.info_code = info_code;
    }

    public String getInfo_code()
    {
        return this.info_code;
    }

    public void setHost_name(String host_name)
    {
        this.host_name = host_name;
    }

    public String getHost_name()
    {
        return this.host_name;
    }

    public void setIp(String ip)
    {
        this.ip = ip;
    }

    public String getIp()
    {
        return this.ip;
    }

    public void setMon_type(String mon_type)
    {
        this.mon_type = mon_type;
    }

    public String getMon_type()
    {
        return this.mon_type;
    }

    public void setInfo_name(String info_name)
    {
        this.info_name = info_name;
    }

    public String getInfo_name()
    {
        return this.info_name;
    }

    public void setLayer(String layer)
    {
        this.layer = layer;
    }

    public String getLayer()
    {
        return this.layer;
    }

    public void setFrom_type(String from_type)
    {
        this.from_type = from_type;
    }

    public String getFrom_type()
    {
        return this.from_type;
    }

    public void setGroup_id(String group_id)
    {
        this.group_id = group_id;
    }

    public String getGroup_id()
    {
        return this.group_id;
    }

    public void setIs_trigger_warn(String is_trigger_warn)
    {
        this.is_trigger_warn = is_trigger_warn;
    }

    public String getIs_trigger_warn()
    {
        return this.is_trigger_warn;
    }

    public void setWarn_level(String warn_level)
    {
        this.warn_level = warn_level;
    }

    public String getWarn_level()
    {
        return this.warn_level;
    }

    public void setInfo_value(String info_value)
    {
        this.info_value = info_value;
    }

    public String getInfo_value()
    {
        return this.info_value;
    }

    public void setDone_date(Timestamp done_date)
    {
        this.done_date = done_date;
    }

    public Timestamp getDone_date()
    {
        return this.done_date;
    }

    public void setDone_code(long done_code)
    {
        this.done_code = done_code;
    }

    public long getDone_code()
    {
        return this.done_code;
    }

    public void setBatch_id(long batch_id)
    {
        this.batch_id = batch_id;
    }

    public long getBatch_id()
    {
        return this.batch_id;
    }

    public void setValid_date(Timestamp valid_date)
    {
        this.valid_date = valid_date;
    }

    public Timestamp getValid_date()
    {
        return this.valid_date;
    }

    public void setExpire_date(Timestamp expire_date)
    {
        this.expire_date = expire_date;
    }

    public Timestamp getExpire_date()
    {
        return this.expire_date;
    }

    public void setOp_id(long op_id)
    {
        this.op_id = op_id;
    }

    public long getOp_id()
    {
        return this.op_id;
    }

    public void setOrg_id(long org_id)
    {
        this.org_id = org_id;
    }

    public long getOrg_id()
    {
        return this.org_id;
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
