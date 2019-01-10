package com.asiainfo.monitor.busi.exe.task.javacommand;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAPITaskSV;
import com.asiainfo.monitor.interapi.config.AIMemoryInfo;
import com.asiainfo.monitor.tools.common.KeyName;
import com.asiainfo.monitor.tools.common.interfaces.IJavaCommand;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.util.TypeConst;

/**
 * 面板任务JVM
 * @author Guocx
 *
 */
public class JvmRemCircsJCommand implements IJavaCommand {

	private final static transient Log log=LogFactory.getLog(JvmRemCircsJCommand.class);
	
	public JvmRemCircsJCommand(){
		
	}
	
	/**
	 * 执行内存命令
	 * @param parameter
	 * @return Object
	 */
	public String execute(KeyName[] parameter) throws Exception {
		String result="";
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
			AIMemoryInfo info=apiTaskSV.getJVM5MemoryInfo(appId);
			if (info!=null){				
				StringBuilder sb=new StringBuilder("");
				sb.append("NAME:");
				sb.append("JvmMemory");
				sb.append(TypeConst._SPLIT_CHAR);
				
				sb.append("MEM_TOT:");
				long total=new Long(info.getTotal()/(1024*1024));
				sb.append(total);
				sb.append(TypeConst._SPLIT_CHAR);
				sb.append("MEM_USE:");
				long used=new Long(info.getUsed()/(1024*1024));
				sb.append(used);
				
				result=sb.toString();
				if (log.isDebugEnabled()){
					// 虚拟机内存情况为
					log.debug(AIMonLocaleFactory.getResource("MOS0000167")+":"+result);
				}
			}
		}
		
		return result;
	}

}
