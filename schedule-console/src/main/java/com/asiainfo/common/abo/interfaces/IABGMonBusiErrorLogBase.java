package com.asiainfo.common.abo.interfaces;

import java.io.Serializable;
import java.util.List;
import java.sql.Timestamp;

public interface IABGMonBusiErrorLogBase extends Serializable {

	public void setErr_order_id(long err_order_id);

	public long getErr_order_id();

	public void setServer_code(String server_code);

	public String getServer_code();

	public void setSerial_no(long serial_no);

	public long getSerial_no();

	public void setTask_id(String task_id);

	public String getTask_id();

	public void setTask_split_id(String task_split_id);

	public String getTask_split_id();

	public void setRegion_code(String region_code);

	public String getRegion_code();

	public void setErr_cnt(long err_cnt);

	public long getErr_cnt();

	public void setHandle_time(long handle_time);

	public long getHandle_time();

	public void setSystem_domain(String system_domain);

	public String getSystem_domain();

	public void setSubsystem_domain(String subsystem_domain);

	public String getSubsystem_domain();

	public void setApp_server_name(String app_server_name);

	public String getApp_server_name();

	public void setCreate_date(String create_date);

	public String getCreate_date();

	public void setAcq_date(Timestamp acq_date);

	public Timestamp getAcq_date();

	public void setMon_flag(String mon_flag);

	public String getMon_flag();

}
