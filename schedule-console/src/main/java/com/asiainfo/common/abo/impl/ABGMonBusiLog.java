package com.asiainfo.common.abo.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.ailk.appengine.complex.abo.impl.AbstractABO;
import com.ailk.appengine.util.DataBeanUtils;
import com.asiainfo.common.abo.interfaces.IABGMonBusiLog;
import com.asiainfo.common.abo.vo.interfaces.IABGMonBusiLogValue;
import java.sql.Timestamp;

public class ABGMonBusiLog extends AbstractABO implements IABGMonBusiLog {

	private transient static Log log = LogFactory.getLog(ABGMonBusiLog.class);

	private long throughput_id = 0;

	private long serial_no = 0;

	private String task_id = null;

	private String task_split_id = null;

	private String server_code = null;

	private long total_cnt = 0;

	private long per_handle_cnt = 0;

	private long handle_cnt = 0;

	private String region_code = null;

	private long consume_time = 0;

	private String system_domain = null;

	private String subsystem_domain = null;

	private String app_server_name = null;

	private String create_date = null;

	private Timestamp acq_date = null;

	private long seq = 0;

	private String mon_flag = null;

	private ABGMonBusiLog(String id) {
		super();
		this.throughput_id = Long.parseLong(id);
		this.setComponent("asiainfo/common/ABGMonBusiLog.cmpt");
	}

	public IABGMonBusiLogValue getIABGMonBusiLogValue() throws Exception {
		return (IABGMonBusiLogValue) DataBeanUtils
				.getVoByAboAndCopy(this, true);
	}

	public void setThroughput_id(long throughput_id) {
		this.throughput_id = throughput_id;
		this.setKey(String.valueOf(throughput_id));
	}

	public long getThroughput_id() {
		return this.throughput_id;
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

	public void setServer_code(String server_code) {
		this.server_code = server_code;
	}

	public String getServer_code() {
		return this.server_code;
	}

	public void setTotal_cnt(long total_cnt) {
		this.total_cnt = total_cnt;
	}

	public long getTotal_cnt() {
		return this.total_cnt;
	}

	public void setPer_handle_cnt(long per_handle_cnt) {
		this.per_handle_cnt = per_handle_cnt;
	}

	public long getPer_handle_cnt() {
		return this.per_handle_cnt;
	}

	public void setHandle_cnt(long handle_cnt) {
		this.handle_cnt = handle_cnt;
	}

	public long getHandle_cnt() {
		return this.handle_cnt;
	}

	public void setRegion_code(String region_code) {
		this.region_code = region_code;
	}

	public String getRegion_code() {
		return this.region_code;
	}

	public void setConsume_time(long consume_time) {
		this.consume_time = consume_time;
	}

	public long getConsume_time() {
		return this.consume_time;
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

	public void setSeq(long seq) {
		this.seq = seq;
	}

	public long getSeq() {
		return this.seq;
	}

	public void setMon_flag(String mon_flag) {
		this.mon_flag = mon_flag;
	}

	public String getMon_flag() {
		return this.mon_flag;
	}

	public void save() throws Exception {
		synchronized (this) {
			if (this.isReqPersistence())
				return;
			this.setReqPersistence(true);
			if (this.isCorrelationConsole()) {
			}
		}
	}

	public void executeLazyMethod() {
	}

	public void preload() throws Exception {
	}

}
