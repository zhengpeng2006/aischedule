package com.asiainfo.monitor.busi.exe.task.model;


import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.monitor.tools.common.KeyName;
import com.asiainfo.monitor.tools.common.interfaces.IJavaCommand;
import com.asiainfo.monitor.tools.common.interfaces.ITaskRtn;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.model.BaseContainer;
import com.asiainfo.monitor.tools.model.CmdType;
import com.asiainfo.monitor.tools.model.interfaces.IJavaType;
import com.asiainfo.monitor.tools.util.TypeConst;



public class JavaCommandType extends CmdType implements IJavaType,Serializable{

	private static Log log=LogFactory.getLog(JavaCommandType.class);

	public JavaCommandType() {
	}

	public String getType(){
		return CmdType.JAVACOMMAND;
	}
	
	/**
	 * 执行任务 
	 * 任务表达式：com.asiainfo.appframe.ext.monitor.exe.javacommand.TelnetPort
	 * @param task:
	 * @return
	 */
	public void doTask(ITaskRtn taskRtn) throws Exception{
		try{
			String expr =this.getContainer().getExpr();
			String result=null;
			String javaCmdImpl=null;
			
			if (StringUtils.contains(expr, TypeConst._NEWLINE_CHAR)){
				//对老的表达式格式的支持,例如com.asiainfo.mon.exe.task.javacommand.TelnetPort;10.96.19.71:25300
				String[] splitStr=StringUtils.split(expr, TypeConst._NEWLINE_CHAR);
				javaCmdImpl=splitStr[0];
				KeyName[] parameters=null;
				if (splitStr.length>1){
					String[] params=StringUtils.split(splitStr[1],TypeConst._INTERPRET_CHAR);
					parameters=new KeyName[params.length];
					for (int i=0;params!=null && i<params.length;i++){
						parameters[i]=new KeyName("PARAM"+i,params[i]);						
					}
				}
				if (!StringUtils.isBlank(javaCmdImpl)){
					IJavaCommand obj = (IJavaCommand) Class.forName(javaCmdImpl).newInstance();
					result=obj.execute(parameters);
				}else{
					log.error(AIMonLocaleFactory.getResource("MOS0000344", this.getContainer().getName()));
				}
			}else{
				javaCmdImpl=expr;
				IJavaCommand obj = (IJavaCommand) Class.forName(javaCmdImpl).newInstance();
				result = obj.execute((KeyName[])((BaseContainer)this.getContainer()).getParameter().toArray(new KeyName[0]));
			}
			taskRtn.setMsg(result);
		}catch(Exception e){
			// 执行类型为JavaCommand的任务["+this.getContainer().getName()+"]时发生异常
			log.error(AIMonLocaleFactory.getResource("MOS0000175", this.getContainer().getName()) + ":"+e.getMessage());
		}
		return;
	}
}
