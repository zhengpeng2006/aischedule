package com.asiainfo.common.abo.interfaces;

import java.io.Serializable;
import java.util.List;
import java.sql.Timestamp;

public interface IABGMonBusiLogBase extends Serializable {

	public void setThroughput_id(long throughput_id);

	public long getThroughput_id();

	public void setSerial_no(long serial_no);

	public long getSerial_no();

	public void setTask_id(String task_id);

	public String getTask_id();

	public void setTask_split_id(String task_split_id);

	public String getTask_split_id();

	public void setServer_code(String server_code);

	public String getServer_code();

	public void setTotal_cnt(long total_cnt);

	public long getTotal_cnt();

	public void setPer_handle_cnt(long per_handle_cnt);

	public long getPer_handle_cnt();

	public void setHandle_cnt(long handle_cnt);

	public long getHandle_cnt();

	public void setRegion_code(String region_code);

	public String getRegion_code();

	public void setConsume_time(long consume_time);

	public long getConsume_time();

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

	public void setSeq(long seq);

	public long getSeq();

	public void setMon_flag(String mon_flag);

	public String getMon_flag();

}
