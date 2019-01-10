package com.asiainfo.monitor.tools.model;

import java.io.Serializable;
import java.sql.Timestamp;

import com.asiainfo.monitor.tools.model.interfaces.IClear;
import com.asiainfo.monitor.tools.model.interfaces.ISmsPersion;
import com.asiainfo.monitor.tools.model.interfaces.ISmsState;

public class SmsState implements IClear,ISmsState,Serializable{

	//警告标识
	private long id;
	
	//任务标识
	private long infoId;
	
	//短信内容
	private String msg;
	//级别
	private int level;
	//发送至人员
	private ISmsPersion[] persion=null;	

	private Timestamp createDate;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getInfoId() {
		return infoId;
	}

	public void setInfoId(long infoId) {
		this.infoId = infoId;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public ISmsPersion[] getPersion() {
		return persion;
	}

	public void setPersion(ISmsPersion[] persion) {
		this.persion = persion;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	
	public void clear(){
		this.id=0;
		this.infoId=0;
		this.msg=null;
		this.level=0;
		this.createDate=null;
	}
}
