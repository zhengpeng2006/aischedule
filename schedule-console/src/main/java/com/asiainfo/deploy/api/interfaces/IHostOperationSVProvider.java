package com.asiainfo.deploy.api.interfaces;

/**
 * 主机操作服务
 * @author 孙德东(24204)
 */
public interface IHostOperationSVProvider {
	/**
	 * 查看appcode归属的主机是否可以ping通
	 * @param appCode
	 * @return
	 */
	boolean ping(String appCode);
}
