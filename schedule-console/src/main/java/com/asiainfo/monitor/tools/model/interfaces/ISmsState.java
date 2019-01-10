package com.asiainfo.monitor.tools.model.interfaces;

import java.sql.Timestamp;

public interface ISmsState {
	
	/**
	 * 标识
	 * @return
	 */
	public long getId();

	public void setId(long id);
	
	
	public long getInfoId();

	public void setInfoId(long infoId);
	/**
	 * 短信息内容
	 * @return
	 */
	public String getMsg();

	public void setMsg(String msg);

	/**
	 * 级别
	 * @return
	 */
	public int getLevel();

	public void setLevel(int level);

	/**
	 * 人员
	 * @return
	 */
	public ISmsPersion[] getPersion();

	public void setPersion(ISmsPersion[] persion);
	
	/**
	 * 创建日期
	 * @return
	 */
	public Timestamp getCreateDate();

	public void setCreateDate(Timestamp createDate);
}
