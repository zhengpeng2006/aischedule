package com.asiainfo.monitor.busi.service.impl;

import java.rmi.RemoteException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.monitor.busi.asyn.operation.impl.SwitchAppClusterAsynOperate;
import com.asiainfo.monitor.busi.service.interfaces.IAPIControlAppSwitchSV;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class APIControlAppSwitchSVImpl implements IAPIControlAppSwitchSV {

	private static transient Log log=LogFactory.getLog(APIControlAppSwitchSVImpl.class);
	
	public List switchApp(Object[] appIds,String newAppClusterCode) throws RemoteException,Exception {
		List result=null;
		try{
			if (appIds==null || appIds.length<1)
				// 清除平台缓存没有传入应用参数
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000133"));
			if (StringUtils.isBlank(newAppClusterCode))
				//没有指定目标APP集群
				throw new Exception("没有指定目标APP集群");
			SwitchAppClusterAsynOperate asynOperate=new SwitchAppClusterAsynOperate(newAppClusterCode);
			int threadCount=(appIds.length>>1)+1;
			int cpuCount=Runtime.getRuntime().availableProcessors();
			if (threadCount> (cpuCount*3))
				threadCount=cpuCount*3;
			result=asynOperate.asynOperation(threadCount, -1, appIds, -1);
		}catch(Exception e){
			log.error("Call APIControlAppSwitchSVImpl Method switchApp has Exception :"+e.getMessage());
		}
		return result;
	}
}
