package com.asiainfo.monitor.tools.common.interfaces;

import java.io.Serializable;

public interface ITaskRtn extends Serializable{

	/**
	 * 返回寄主标识：命令容器标识对应的就是Table\Exec的标识
	 * @return
	 */
	public String getParentId();
	
	public void setParentId(String parentId);
	
	/**
	 * 祖宗唯一标识码(面板标识、任务标识、自定义面板标识)
	 * @return
	 */
	public String getIdCode();

	public void setIdCode(String idCode);

	/**
	 * 应用的名称、主机的名称
	 * @return
	 */
	public String getCode();
	
	public void setCode(String code);
	
	/**
	 * 名称
	 * @return
	 */
	public String getName();

	public void setName(String name);

	/**
	 * 警告级别
	 * @return
	 */
	public int getLevel();

	public void setLevel(int level);	
	
	/**
	 * 返回值串
	 * @return
	 */
	public String getMsg();

	public void setMsg(String msg);
	
	/**
	 * 返回值时刻
	 * @return
	 */
	public String getTime();

	public void setTime(String time);
	
	public Object clone();
	
	/**
	 * 警告信息值串
	 * @return
	 */
	public String getWarn();

	public void setWarn(String warn);
	
	public boolean isBeTure();

	public void setBeTure(boolean beTure);
	
	public String toString();
}
