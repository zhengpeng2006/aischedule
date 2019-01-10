package com.asiainfo.monitor.busi.exe.task.model;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.monitor.tools.common.interfaces.IKeyName;
import com.asiainfo.monitor.tools.common.interfaces.ITaskRtn;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.model.BaseContainer;
import com.asiainfo.monitor.tools.model.CmdType;
import com.asiainfo.monitor.tools.model.interfaces.IShellType;
import com.asiainfo.monitor.tools.util.SSHUtil;
import com.asiainfo.monitor.tools.util.TypeConst;

public class CommandType extends CmdType implements IShellType,Serializable{

	private static Log log=LogFactory.getLog(CommandType.class);
	
	public CommandType(){		
	}
	
	public String getType(){
		return CmdType.COMMAND;
	}
	
	/**
	 * 执行命令
	 * @param task
	 * @return
	 * @exception
	 */
	public void doTask(ITaskRtn taskRtn) throws Exception{
		try{
			String ip="",port="",user="",password="";
			if (((BaseContainer)this.getContainer()).getParent().getParameter()!=null && ((BaseContainer)this.getContainer()).getParent().getParameter().size()>0){
				
				IKeyName ipParam=((BaseContainer)this.getContainer()).getParent().findParameter(TypeConst.IP);
				if (ipParam!=null)
					ip=ipParam.getKey();
				IKeyName userParam=((BaseContainer)this.getContainer()).getParent().findParameter(TypeConst.USER);
				if (userParam!=null)
					user=userParam.getKey();
				IKeyName passwordParam=((BaseContainer)this.getContainer()).getParent().findParameter(TypeConst.PASSWORD);
				if (passwordParam!=null)
					password=passwordParam.getKey();
			}else{
				// "没有为Shell任务["+this.getContainer().getName()+"]设置登陆信息"
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000155", this.getContainer().getName()));
			}
			if (StringUtils.isNotBlank(ip) && StringUtils.isNotBlank(port) && StringUtils.isNotBlank(user) && StringUtils.isNotBlank(password)){
				String result = SSHUtil.ssh4Command(ip,Integer.parseInt(port),user, password,this.getContainer().getExpr());
				taskRtn.setMsg(result);
			}
		}catch(Exception e){
			// 执行类型为Command的任务["+this.getContainer().getName()+"]时发生异常
			log.error(AIMonLocaleFactory.getResource("MOS0000175", this.getContainer().getName()) + ":"+e.getMessage());
		}
		return;
	}
}
