package com.asiainfo.monitor.busi.exe.task.javacommand;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAPITaskSV;
import com.asiainfo.monitor.interapi.config.AITmSummary;
import com.asiainfo.monitor.tools.common.KeyName;
import com.asiainfo.monitor.tools.common.interfaces.IJavaCommand;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.util.TypeConst;

/**
 * 面板任务,实时事务
 * @author Guocx
 *
 */
public class TransactionJCommand implements IJavaCommand {

	private static transient Log log=LogFactory.getLog(TransactionJCommand.class);
	
	
	public TransactionJCommand() {
		super();
		// TODO 自动生成构造函数存根
	}

	/**
	 * 实时事务
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
			AITmSummary trsSummary=apiTaskSV.getTransaction(appId);
			if (trsSummary!=null){
				StringBuilder sb=new StringBuilder("");
				sb.append("NAME:");
				sb.append("Transaction");
				sb.append(TypeConst._SPLIT_CHAR);
				sb.append("STARTCOUNT:");
				sb.append(trsSummary.getStartCount());
				sb.append(TypeConst._SPLIT_CHAR);
				sb.append("COMMITCOUNT:");
				sb.append(trsSummary.getCommitCount());
				sb.append(TypeConst._SPLIT_CHAR);
				sb.append("ROLLCOUNT:");
				sb.append(trsSummary.getRollbackCount());
				sb.append(TypeConst._SPLIT_CHAR);
				sb.append("SUSPENDCOUNT:");
				sb.append(trsSummary.getSuspendCount());
				sb.append(TypeConst._SPLIT_CHAR);
				sb.append("RESUMECOUNT:");
				sb.append(trsSummary.getResumeCount());
				
				result=sb.toString();
				if (log.isDebugEnabled()){
					// 虚拟机内存情况
					log.debug(AIMonLocaleFactory.getResource("MOS0000170") + ":"+result);
				}
			}
		}
		
		return result;
	}

}
