package com.asiainfo.monitor.tools.model.interfaces;

import java.util.List;

import com.asiainfo.monitor.tools.model.WebConsoleTaskContext;

public interface ITaskCmdSet {

	public String getCmdSet_Desc();

	public void setCmdSet_Desc(String cmdSet_Desc);

	public long getCmdSet_Id();

	public void setCmdSet_Id(long cmdSet_Id);

	public String getCmdSet_Name();

	public void setCmdSet_Name(String cmdSet_Name);

	public String getRemark();

	public void setRemark(String remark) ;

	public String getState();

	public void setState(String state);
	
	public void putCmdItem(ITaskCmdContainer cmdItem);
	
	public int size();
	
	public List getCmdContainers();
	
	public String getCmdSet_Code();

	public void setCmdSet_Code(String cmdSet_Code);
	
	public String execute(WebConsoleTaskContext task) throws Exception;
}
