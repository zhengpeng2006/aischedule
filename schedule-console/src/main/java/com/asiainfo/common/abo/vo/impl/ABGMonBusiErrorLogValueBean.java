package com.asiainfo.common.abo.vo.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.HashMap;
import java.util.Map;
import com.ailk.appengine.complex.abo.values.impl.AbstractValueBean;

import com.asiainfo.common.abo.vo.interfaces.IABGMonBusiErrorLogValue;
import java.sql.Timestamp;

public class ABGMonBusiErrorLogValueBean extends AbstractValueBean implements
		IABGMonBusiErrorLogValue {

	private transient static Log log = LogFactory
			.getLog(ABGMonBusiErrorLogValueBean.class);

	private long err_order_id = 0;

	private String server_code = null;

	private long serial_no = 0;

	private String task_id = null;

	private String task_split_id = null;

	private String region_code = null;

	private long err_cnt = 0;

	private long handle_time = 0;

	private String system_domain = null;

	private String subsystem_domain = null;

	private String app_server_name = null;

	private String create_date = null;

	private Timestamp acq_date = null;

	private String mon_flag = null;

	public ABGMonBusiErrorLogValueBean() {
		super();
	}

	public void setErr_order_id(long err_order_id) {
		this.err_order_id = err_order_id;
		this.setKey(this.err_order_id + "");
	}

	public long getErr_order_id() {
		return this.err_order_id;
	}

	public void setServer_code(String server_code) {
		this.server_code = server_code;
	}

	public String getServer_code() {
		return this.server_code;
	}

	public void setSerial_no(long serial_no) {
		this.serial_no = serial_no;
	}

	public long getSerial_no() {
		return this.serial_no;
	}

	public void setTask_id(String task_id) {
		this.task_id = task_id;
	}

	public String getTask_id() {
		return this.task_id;
	}

	public void setTask_split_id(String task_split_id) {
		this.task_split_id = task_split_id;
	}

	public String getTask_split_id() {
		return this.task_split_id;
	}

	public void setRegion_code(String region_code) {
		this.region_code = region_code;
	}

	public String getRegion_code() {
		return this.region_code;
	}

	public void setErr_cnt(long err_cnt) {
		this.err_cnt = err_cnt;
	}

	public long getErr_cnt() {
		return this.err_cnt;
	}

	public void setHandle_time(long handle_time) {
		this.handle_time = handle_time;
	}

	public long getHandle_time() {
		return this.handle_time;
	}

	public void setSystem_domain(String system_domain) {
		this.system_domain = system_domain;
	}

	public String getSystem_domain() {
		return this.system_domain;
	}

	public void setSubsystem_domain(String subsystem_domain) {
		this.subsystem_domain = subsystem_domain;
	}

	public String getSubsystem_domain() {
		return this.subsystem_domain;
	}

	public void setApp_server_name(String app_server_name) {
		this.app_server_name = app_server_name;
	}

	public String getApp_server_name() {
		return this.app_server_name;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	public String getCreate_date() {
		return this.create_date;
	}

	public void setAcq_date(Timestamp acq_date) {
		this.acq_date = acq_date;
	}

	public Timestamp getAcq_date() {
		return this.acq_date;
	}

	public void setMon_flag(String mon_flag) {
		this.mon_flag = mon_flag;
	}

	public String getMon_flag() {
		return this.mon_flag;
	}

	public Map getClassMap() {
		if (this.classMap != null)
			return this.classMap;
		classMap = new HashMap();
		return classMap;
	}

}
