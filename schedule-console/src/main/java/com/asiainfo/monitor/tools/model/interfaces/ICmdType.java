package com.asiainfo.monitor.tools.model.interfaces;

public interface ICmdType {

	public final static String JAVACOMMAND="JAVACOMMAND";
	
	public final static String SHELL="SHELL";
	
	public final static String COMMAND="COMMAND";
	
	public final static String TABLE="TABLE";
	
	public String getName();

	public void setName(String name);
	
	public ITaskCmdContainer getContainer();

	public void setContainer(ITaskCmdContainer container);

	abstract public String getType();
}
