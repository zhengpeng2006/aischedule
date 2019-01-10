package com.asiainfo.deploy.api.external;

import java.util.List;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.deploy.api.interfaces.IPackageDispatcherSVProvider;


/**
 * 安装包分发对外接口
 * @author 孙德东(24204)
 */
public class PackageDispatcherUtils {
	private PackageDispatcherUtils(){}
	
	/**
	 * 主机初始化
	 * @param nodeList
	 * @param deployStrategy
	 * @throws Exception
	 */
	public static void hostInitialization(List<Long> nodeList, long deployStrategy) throws Exception
	{
		IPackageDispatcherSVProvider sv = (IPackageDispatcherSVProvider)ServiceFactory.getService(IPackageDispatcherSVProvider.class);
		sv.hostInitialization(nodeList, deployStrategy);
	}
	/**
	 * 安装应用
	 * @param nodes
	 * @param versionId
	 * @throws Exception
	 */
	public static void install(List<Long> nodeList, long versionId) throws Exception {
		IPackageDispatcherSVProvider sv = (IPackageDispatcherSVProvider)ServiceFactory.getService(IPackageDispatcherSVProvider.class);
		sv.install(nodeList, versionId);
	}
	
	/**
	 * 统一同一部署策略下的主机所有应用到当前版本
	 * @param deployStrategy
	 * @return
	 * @throws Exception
	 */
	public static void unifyToCurrentVersion(long deployStrategy) throws Exception {
		IPackageDispatcherSVProvider sv = (IPackageDispatcherSVProvider)ServiceFactory.getService(IPackageDispatcherSVProvider.class);
		sv.unifyToCurrentVersion(deployStrategy);
	}
	
	/**
	 * 回滚应用
	 * @param nodes
	 * @param versionId
	 * @throws Exception
	 */
	public static void rollback(List<Long> nodes, long versionId)throws Exception {
		IPackageDispatcherSVProvider sv = (IPackageDispatcherSVProvider)ServiceFactory.getService(IPackageDispatcherSVProvider.class);
		sv.rollback(nodes, versionId);
	}
}
