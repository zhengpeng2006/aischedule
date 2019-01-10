package com.asiainfo.monitor.busi.exe.task.model;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.monitor.tools.common.interfaces.IWorker;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.model.BaseContainer;
import com.asiainfo.monitor.tools.model.CmdType;
import com.asiainfo.monitor.tools.model.interfaces.ITaskCmdContainer;


public class TaskTableContainer extends BaseContainer implements ITaskCmdContainer,IWorker,Serializable{
	
	private static Log log=LogFactory.getLog(TaskTableContainer.class);
	
	private String code;
	
	private CmdType cmdType;
	
	private String dburlName;
	
	private String dbacctCode;
	
	private long taskId;
	
	//	表达式
	private String expr;
	
	private String runType;
	
	public TaskTableContainer(){
		super();
	}
	
	public String getType() {
		return _TASK_TABLE;
	}

	public String getDbacctCode() {
		return dbacctCode;
	}

	public void setDbacctCode(String dbacctCode) {
		this.dbacctCode = dbacctCode;
	}

	public String getDburlName() {
		return dburlName;
	}

	public void setDburlName(String dburlName) {
		this.dburlName = dburlName;
	}

	public CmdType getCmdType() {
		return cmdType;
	}

	public void setCmdType(CmdType cmdType) {
		this.cmdType = cmdType;
	}

	public String getExpr() {
		return expr;
	}

	public void setExpr(String expr) {
		this.expr = expr;
	}
	
	public void setCode(String code){
		this.code=code;
	}
	
	public String getCode(){
		return this.code;
	}
	
	public long getParentId(){
		return this.taskId;
	}
	
	public void setParentId(long taskId){
		this.taskId=taskId;
	}
	
	public String getRunTimeType(){
		return this.runType;
	}
	
	public void setRunTimeType(String runType){
		this.runType=runType;
	}
	
	public void action() throws Exception{		
		try{
			fireContainerEvent(BaseContainer._DO_BEFORE_EVENT,null);
			this.cmdType.action();
			fireContainerEvent(BaseContainer._DO_AFTER_EVENT,null);
		}catch(Exception e){
			// "执行任务["+this.getName()+"]时发生异常"
			log.error(AIMonLocaleFactory.getResource("MOS0000177", this.getName()),e);
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000177", this.getName()),e);
		}finally{
			if (this.signal!=null)
				this.signal.countDown();
		}
	}
	
	
}
