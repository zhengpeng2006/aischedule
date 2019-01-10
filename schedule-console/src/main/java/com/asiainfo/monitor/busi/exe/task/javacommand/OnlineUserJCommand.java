package com.asiainfo.monitor.busi.exe.task.javacommand;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAPITaskSV;
import com.asiainfo.monitor.tools.common.KeyName;
import com.asiainfo.monitor.tools.common.interfaces.IJavaCommand;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.util.TypeConst;

/**
 * 面板任务，在线用户
 * @author Guocx
 *
 */
public class OnlineUserJCommand implements IJavaCommand {

	private final static transient Log log=LogFactory.getLog(OnlineUserJCommand.class);
	
	public OnlineUserJCommand() {
		super();
		// TODO 自动生成构造函数存根
	}

	public String execute(KeyName[] parameter) throws Exception {
		String result=null;
		if (parameter==null || parameter.length<1)
			return result;
		String appId="";
		for (int i=0;i<parameter.length;i++){
			if (parameter[i].getName().equals(TypeConst.SERVER+TypeConst._ID)){
				appId=parameter[i].getKey();
				break;
			}
		}
		if (StringUtils.isBlank(appId))
			appId=parameter[0].getKey();
		
		if (StringUtils.isNotBlank(appId)){
			IAPITaskSV apiTaskSV=(IAPITaskSV)ServiceFactory.getService(IAPITaskSV.class);
			List info=apiTaskSV.getOnlineUsers(appId);
			if (info!=null && info.size()>0){				
				StringBuilder sb=new StringBuilder("");
				sb.append("NAME:");
				sb.append("OnlineUser");
				sb.append(TypeConst._SPLIT_CHAR);
				
				sb.append("COUNT:");
				sb.append(info.size());
				
				result=sb.toString();
				if (log.isDebugEnabled()){
					// 在线用户数
					log.debug(AIMonLocaleFactory.getResource("MOS0000168")+ ":"+result);
				}
			}
		}
		return result;
	}

}
