package com.asiainfo.monitor.busi.exe.task.javacommand;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.complex.mbean.standard.sv.SVMethodSummary;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAPITaskSV;
import com.asiainfo.monitor.tools.common.KeyName;
import com.asiainfo.monitor.tools.common.interfaces.IJavaCommand;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.util.TypeConst;

public class ServiceStatisJCommand implements IJavaCommand {

	private static transient Log log=LogFactory.getLog(ServiceStatisJCommand.class);
	
	public ServiceStatisJCommand() {
		super();
	}
	
	public String execute(KeyName[] parameter) throws Exception {
		StringBuffer result=new StringBuffer("");

		if (parameter==null || parameter.length<1)
			return result.toString();
		
		String appId="",appName="";

		for (int i=0;i<parameter.length;i++){
			if (parameter[i].getName().equals(TypeConst.SERVER+TypeConst._ID)){
				appId=parameter[i].getKey();
				if (!StringUtils.isBlank(appName))
					break;
			}
			if (parameter[i].getName().equals(TypeConst.SERVER+TypeConst._NAME)){
				appName=parameter[i].getKey();
				if (!StringUtils.isBlank(appId))
					break;
			}
		}
		if (StringUtils.isBlank(appId))
			appId=parameter[0].getKey();
		
		if (StringUtils.isNotBlank(appId)){
			IAPITaskSV apiTaskSV=(IAPITaskSV)ServiceFactory.getService(IAPITaskSV.class);
			List summarys=apiTaskSV.getSVMehtodInfo(appId);
			if (summarys!=null && summarys.size()>0){
				for (int i=0;i<summarys.size();i++){
					SVMethodSummary item=(SVMethodSummary)summarys.get(i);
					if (i>0){
						result.append(TypeConst._NEWLINE_CHAR);
					}
					// 应用
					result.append(AIMonLocaleFactory.getResource("MOS0000331"));
					result.append(TypeConst._INTERPRET_CHAR);
					result.append(appId);
					result.append(TypeConst._SPLIT_CHAR);
					// 应用名称
					result.append(AIMonLocaleFactory.getResource("MOS0000332"));
					result.append(TypeConst._INTERPRET_CHAR);
					result.append(appName);
					result.append(TypeConst._SPLIT_CHAR);
					// 服务类
					result.append(AIMonLocaleFactory.getResource("MOS0000333"));
					result.append(TypeConst._INTERPRET_CHAR);
					result.append(item.getClassName());					
					result.append(TypeConst._SPLIT_CHAR);
					// 服务方法
					result.append(AIMonLocaleFactory.getResource("MOS0000334"));
					result.append(TypeConst._INTERPRET_CHAR);
					result.append(item.getMethodName());					
					result.append(TypeConst._SPLIT_CHAR);
					// 最小耗时
					result.append(AIMonLocaleFactory.getResource("MOS0000335"));
					result.append(TypeConst._INTERPRET_CHAR);
					result.append(item.getMin()+"");
					result.append(TypeConst._SPLIT_CHAR);
					// 最大耗时
					result.append(AIMonLocaleFactory.getResource("MOS0000336"));
					result.append(TypeConst._INTERPRET_CHAR);
					result.append(item.getMax()+"");
					result.append(TypeConst._SPLIT_CHAR);
					// 平均耗时
					result.append(AIMonLocaleFactory.getResource("MOS0000337"));
					result.append(TypeConst._INTERPRET_CHAR);
					result.append(item.getAvg()+"");
					result.append(TypeConst._SPLIT_CHAR);
					// 调用次数
					result.append(AIMonLocaleFactory.getResource("MOS0000338"));
					result.append(TypeConst._INTERPRET_CHAR);
					result.append(item.getTotalCount());
					result.append(TypeConst._SPLIT_CHAR);
					// 成功次数
					result.append(AIMonLocaleFactory.getResource("MOS0000339"));
					result.append(TypeConst._INTERPRET_CHAR);
					result.append(item.getSuccessCount());
					result.append(TypeConst._SPLIT_CHAR);
					// 失败次数
					result.append(AIMonLocaleFactory.getResource("MOS0000340"));
					result.append(TypeConst._INTERPRET_CHAR);
					result.append(item.getFailCount());
					result.append(TypeConst._SPLIT_CHAR);
					// 最后调用用时
					result.append(AIMonLocaleFactory.getResource("MOS0000341"));
					result.append(TypeConst._INTERPRET_CHAR);
					result.append(item.getLastUseTime());
					result.append(TypeConst._SPLIT_CHAR);
					// 总用时
					result.append(AIMonLocaleFactory.getResource("MOS0000342"));
					result.append(TypeConst._INTERPRET_CHAR);
					result.append(item.getTotalUseTime());
					result.append(TypeConst._SPLIT_CHAR);
					// 最后调用时间
					result.append(AIMonLocaleFactory.getResource("MOS0000343"));
					result.append(TypeConst._INTERPRET_CHAR);
					result.append(item.getLast());
				}
			}
		}
		
		return result.toString();
	}
}
