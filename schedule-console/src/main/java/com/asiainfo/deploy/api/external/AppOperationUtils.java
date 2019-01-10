package com.asiainfo.deploy.api.external;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.deploy.api.interfaces.IAppOperationSVProvider;
import com.asiainfo.deploy.exception.ExecuteResult;
import java.util.List;
import java.util.Map;

/**
 * 应用操作对外接口
 * @author 孙德东(24204)
 */
public class AppOperationUtils {

	private AppOperationUtils(){}
	
	public static void startViaSSH(String appCode) throws Exception {
		 ((IAppOperationSVProvider)ServiceFactory.getService(IAppOperationSVProvider.class)).startViaSSH(appCode);
	}

	public static void stopViaSSH(String appCode) throws Exception {
		 ((IAppOperationSVProvider)ServiceFactory.getService(IAppOperationSVProvider.class)).stopViaSSH(appCode);
	}

	public static boolean isRunningViaSSH(String appCode) throws Exception {
		return ((IAppOperationSVProvider)ServiceFactory.getService(IAppOperationSVProvider.class)).isRunningViaSSH(appCode);
	}
        
        /**
         * 通过应用编码批量判断应用是否运行
         * 如果ExecuteResult.isSuccess为true，表示命令被正确执行并返回了结果；此时，再次通过(Boolean)ExecuteResult.getMessage()判断应用是否正在运行；
         * 如果ExecuteResult.isSuccess为false，表示命令执行过程中出错，无法判断应用是否正在运行
         * @param appCodeList
         * @return
         * @throws Exception 
         */
        public static Map<String, ExecuteResult> batchIsRunningViaSocket(List<String> appCodeList) throws Exception {
		return ((IAppOperationSVProvider)ServiceFactory.getService(IAppOperationSVProvider.class)).batchIsRunningViaSocket(appCodeList);
	}
}
