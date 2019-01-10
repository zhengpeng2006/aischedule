package com.asiainfo.deploy.api.external;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.deploy.api.interfaces.IHostOperationSVProvider;

/**
 * 主机相关的操作
 * @author 孙德东(24204)
 */
public class HostOperatioinUtils {
	
	/**
	 * 查看appcode归属的主机是否可以ping通
	 * @param appCode
	 * @return
	 */
	public static boolean ping(String appCode) {
		return ((IHostOperationSVProvider)ServiceFactory.getService(IHostOperationSVProvider.class)).ping(appCode);
	}
}
