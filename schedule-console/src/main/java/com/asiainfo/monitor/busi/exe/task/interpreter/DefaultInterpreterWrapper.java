package com.asiainfo.monitor.busi.exe.task.interpreter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.monitor.tools.common.interfaces.IKeyName;
import com.asiainfo.monitor.tools.common.interfaces.ITaskRtn;

public class DefaultInterpreterWrapper extends InterpreterWrapper {

	private transient static Log log = LogFactory.getLog(DefaultInterpreterWrapper.class);
	
	public DefaultInterpreterWrapper(String id,String code,String name,IKeyName[] parameters,String expr,ITaskRtn taskRtn){
		super(id,code,name,parameters,expr,taskRtn);
	}
	
	@Override
	public void interpreter() throws Exception {
		return;
	}

}
