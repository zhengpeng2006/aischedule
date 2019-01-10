package com.asiainfo.monitor.tools.model.interfaces;


import java.util.List;

import com.asiainfo.monitor.tools.common.interfaces.ITaskRtn;
import com.asiainfo.monitor.tools.common.interfaces.IWorker;
import com.asiainfo.monitor.tools.exe.task.model.TaskRtnModel;

public interface ITaskContext extends IWorker,IContext,IClear,Cloneable{

	public String getCode();

	public void setCode(String code);

	public boolean isAsyn();

	public void setAsyn(boolean asyn);
	
	public String getExecMethod();

	public void setExecMethod(String execMethod);

	public String getId();

	public void setId(String id);
	
	public void putCmdItem(ITaskCmdContainer cmdItem);
		
	public int size();
	
	public List getCmdContainers();
	
	public TaskRtnModel getRtnModel() ;

	public void setRtnModel(TaskRtnModel rtnModel);
	
	public void addTaskRtn(ITaskRtn taskRtn);
	
	public abstract void action() throws Exception;
	
	public Object clone();
}
