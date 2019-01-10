package com.asiainfo.monitor.busi.asyn.operation.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.appframe.ext.flyingserver.util.e.K;
import com.asiainfo.monitor.busi.asyn.operation.OperateCallable;
import com.asiainfo.monitor.busi.asyn.operation.ServerOperateCallable;
import com.asiainfo.monitor.busi.cache.interfaces.IServer;
import com.asiainfo.monitor.busi.config.ServerConfig;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonColgRuleValue;
import com.asiainfo.monitor.tools.common.SimpleResult;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.util.SSHUtil2;

public abstract class CollectFileCallable extends ServerOperateCallable {

	private static transient Log log = LogFactory.getLog(CollectFileCallable.class);
	
	public CollectFileCallable(String id){
		super(id);
	}
	
	/**
	 * 采集文件
	 * 例如trc文件
	 * shell:"/tmp/aitrc_tmp/collect_trc.sh"
	 * loPath:"/tmp/aitrc_tmp/data/"
	 * rePath:"/tmp/"
	 * fileType:"trc"
	 */
	public void concreteOperate(OperateCallable callable, SimpleResult sr) throws Exception {		
		try {			
			if (sr.getAttach()!=null && sr.getAttach() instanceof IServer){
				ServerConfig appServer=(ServerConfig)sr.getAttach();
				String code=appServer.getApp_Code();
				if (appServer.getPhysicHost()==null){
					// 没有查找到应用["+id+"]配置的主机信息
					sr.setMsg(AIMonLocaleFactory.getResource("MOS0000328", id));
					sr.setSucc(false);
					return;
				}
					
                String ip = appServer.getPhysicHost().getHostIp();
                String port = appServer.getPhysicHost().getHostPort();
                String user = appServer.getPhysicHost().getHostUser();
                String password = K.k(appServer.getPhysicHost().getHostPwd());
				if (StringUtils.isBlank(ip) || StringUtils.isBlank(port) || StringUtils.isBlank(user) || StringUtils.isBlank(password)){
					// 应用["+id+"]配置的主机信息中缺少
					sr.setMsg(AIMonLocaleFactory.getResource("MOS0000242", id) + "ip:"+ip+",port:"+port+",user:"+user);
					sr.setSucc(false);
					return;
				}
				IBOAIMonColgRuleValue defaultParaValue=getDefaultCollectRule();
				IBOAIMonColgRuleValue selfParaValue=getSelfCollectRule();
				
				if (defaultParaValue==null && selfParaValue==null){
					// 没有为应用["+id+"]配置采集Jmx属性文件参数信息
					sr.setMsg(AIMonLocaleFactory.getResource("MOS0000243", id));
					sr.setSucc(false);
					return;
				}
				String shell="",loPath="",rePath="",fileType="";
				if (defaultParaValue!=null){
					if (StringUtils.isNotBlank(defaultParaValue.getExpr0()))
						shell=defaultParaValue.getExpr0();
					if (StringUtils.isNotBlank(defaultParaValue.getExpr1()))
						loPath=defaultParaValue.getExpr1();
					if (StringUtils.isNotBlank(defaultParaValue.getExpr2()))
						rePath=defaultParaValue.getExpr2();
					if (StringUtils.isNotBlank(defaultParaValue.getExpr3()))
						fileType=defaultParaValue.getExpr3();
				}
				if (selfParaValue!=null){
					if (StringUtils.isNotBlank(selfParaValue.getExpr0()))
						shell=selfParaValue.getExpr0();
					if (StringUtils.isNotBlank(selfParaValue.getExpr1()))
						loPath=selfParaValue.getExpr1();
					if (StringUtils.isNotBlank(selfParaValue.getExpr2()))
						rePath=selfParaValue.getExpr2();
					if (StringUtils.isNotBlank(selfParaValue.getExpr3()))
						fileType=selfParaValue.getExpr3();
				}
				
				if (StringUtils.isBlank(shell) || StringUtils.isBlank(loPath) || StringUtils.isBlank(rePath) || StringUtils.isBlank(fileType)){
					// 应用["+id+"]配置的采集参数信息部分为空
					sr.setMsg(AIMonLocaleFactory.getResource("MOS0000244", id) + ",shell:"+shell+",loPath:"+loPath+",rePath:"+rePath+",fileType:"+fileType);
					sr.setSucc(false);
					return;
				}
				int c = SSHUtil2.collectFile(ip,Integer.parseInt(port), user,password,shell,loPath,rePath,fileType,code);
				sr.setSucc(true);
				sr.setValue(c);
			}
			
		} catch (Exception ex) {
			// 采集文件异常
			log.error(AIMonLocaleFactory.getResource("MOS0000245"),ex);
			sr.setSucc(false);
			sr.setMsg(AIMonLocaleFactory.getResource("MOS0000245")+ex.getMessage());
		}
	}

	public abstract IBOAIMonColgRuleValue getDefaultCollectRule() throws Exception;

	public abstract IBOAIMonColgRuleValue getSelfCollectRule() throws Exception;
	
}
