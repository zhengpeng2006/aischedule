package com.asiainfo.deploy.api.interfaces;

import com.asiainfo.deploy.exception.ExecuteResult;
import java.util.List;
import java.util.Map;


/**
 * 提供应用启动、停止、监控接口
 * @author 孙德东(24204)
 */
public interface IAppOperationSVProvider 
{
	void startViaSSH(String appCode) throws Exception;
	void stopViaSSH(String appCode) throws Exception;
	boolean isRunningViaSSH(String appCode) throws Exception;
        
        Map<String, ExecuteResult> batchIsRunningViaSocket(List<String> appCodeList) throws Exception;
}
