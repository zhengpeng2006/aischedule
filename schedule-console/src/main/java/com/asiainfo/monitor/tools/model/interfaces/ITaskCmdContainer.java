package com.asiainfo.monitor.tools.model.interfaces;

import com.asiainfo.monitor.tools.model.BaseContainer;
import com.asiainfo.monitor.tools.model.CmdType;


public interface ITaskCmdContainer {

	//每次都运行
	public String EVERY_RUN="EVER_RUN";
	//头一次运行
	public String FIRST_RUN="FIRST_RUN";
	
	
	//定时任务用参数
	
	public final static String _TASK_EXEC="EXEC";
	
	public final static String _TASK_TABLE="TABLE";
	
	public String getId();
	
	//条形码
	public void setCode(String code);
	
	public String getCode();
	
	public String getName();
	
	public CmdType getCmdType();
	
	public String getExpr();
	
	public BaseContainer getParent();
	
	public String getType();
	
	//运行时刻类型：每次运行、一次运行
	public String getRunTimeType();
	
	public void setRunTimeType(String runType);
}
