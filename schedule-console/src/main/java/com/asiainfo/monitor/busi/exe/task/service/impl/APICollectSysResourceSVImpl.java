package com.asiainfo.monitor.busi.exe.task.service.impl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.complex.service.impl.xml.Cluster;
import com.ai.appframe2.complex.service.impl.xml.Connect;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAPICollectSysResourceSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerAtomicSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerRouteSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonSetAtomicSV;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonServerRouteBean;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonServerRouteValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonSetValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonServerValue;
import com.asiainfo.monitor.interapi.api.SystemResourceApi;
import com.asiainfo.monitor.tools.common.SimpleResult;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.util.SysResourceXmlHelper;

public class APICollectSysResourceSVImpl implements IAPICollectSysResourceSV {
	
	private static transient Log log=LogFactory.getLog(APICollectSysResourceSVImpl.class);
	
	/**
	 * 供后台定时任务采集资源文件
	 * @param id
	 * @param filename
	 * @throws RemoteException
	 * @throws Exception
	 */
	public String collectSysResource(String id,String filename) throws RemoteException,Exception{
		String result="";
		try{
			IAIMonServerAtomicSV serverSV=(IAIMonServerAtomicSV)ServiceFactory.getService(IAIMonServerAtomicSV.class);
			IBOAIMonServerValue appServer=serverSV.getServerBeanById(id);
			if (appServer==null){
				// 没找到应用实例[{0}]
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000070", id));
			}
			IAIMonSetAtomicSV setSV=(IAIMonSetAtomicSV)ServiceFactory.getService(IAIMonSetAtomicSV.class);
			IBOAIMonSetValue setValue=setSV.getMonSetBeanBySCodeVCode(id,"JMX_STATE");
			if (setValue==null ){
				// "应用端[appId:"+appId+"]未启动Jmx注册"
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000071", id));
			}
			if (setValue.getSetValue().equalsIgnoreCase("ON") ||
					setValue.getSetValue().equalsIgnoreCase("TRUE")){
				result = SystemResourceApi.getResourceFromClasspath(appServer.getLocatorType(),appServer.getLocator(), filename);
			}else{
				// "应用端[appId:"+appId+"]未启动Jmx注册"
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000071", id));
			}
			
		}catch(Exception e){
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000245")+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 根据选择的WEB应用采集集群路由配置信息，并同步到数据库中
	 * @param id
	 * @throws RemoteException
	 * @throws Exception
	 */
	public void collectAppClusterResource(String id) throws RemoteException,Exception{
		try{
			String inWebConnect=this.collectSysResource(id, "/system/service/web_connect_app_cluster.xml");
			Connect[] connects=null;
			Cluster[] appClusters=null;
			if (!StringUtils.isBlank(inWebConnect)){
				connects = SysResourceXmlHelper.getClient(inWebConnect).getConnects();
			}else{
//				log.error("---------web应用:["+id+"],没有采集到/system/service/web_connect_app_cluster.xml信息["+inWebConnect+"]");
			}
			String inAppCluster=this.collectSysResource(id, "/system/service/app_cluster.xml");
			if (!StringUtils.isBlank(inAppCluster)){
				appClusters=SysResourceXmlHelper.getApp(inAppCluster).getClusters();
			}else{
//				log.error("---------web应用:["+id+"],没有采集到/system/service/app_cluster.xml信息["+inAppCluster+"]");
			}
			List datas=new ArrayList();
			if (connects!=null && appClusters!=null){
				IAIMonServerAtomicSV serverSV=(IAIMonServerAtomicSV)ServiceFactory.getService(IAIMonServerAtomicSV.class);				
				Map result=SysResourceXmlHelper.resolveWeb2AppClusters(connects,appClusters);
				String code="";
				
				for (Iterator it=result.keySet().iterator();it.hasNext();){
					code=String.valueOf(it.next());
//					log.error("---------web应用:["+code+"]");
					IBOAIMonServerValue serverValue=serverSV.getServerBeanByCode(code);
					if (serverValue!=null){
						List list=(List)result.get(code);
						for (int i=0;list!=null && list.size()>0 && i<list.size();i++){
							SimpleResult sr=(SimpleResult)list.get(i);
							if (sr!=null){
								IBOAIMonServerValue dServBO=serverSV.getServerBeanByIpPort(sr.getKey(),sr.getName());
								if (dServBO!=null){
									IBOAIMonServerRouteValue routeBean=new BOAIMonServerRouteBean(); 
									routeBean.setSServId(serverValue.getServerId());
									routeBean.setSServCode(code);
									routeBean.setSNodeId(serverValue.getNodeId());
									routeBean.setDServId(dServBO.getServerId());
									routeBean.setDServCode(dServBO.getServerCode());
									routeBean.setDNodeId(dServBO.getNodeId());
									routeBean.setState("U");
									routeBean.setStsToNew();
									datas.add(routeBean);
								}else{
//									log.error("---------没有找到对应的应用 ["+sr.getKey()+"],["+sr.getName()+"]");
								}
							}							
						}
					}
				}
			}else{
//				log.error("---------web应用:["+id+"],采集到信息没有转成对象");
			}
			if (datas!=null && datas.size()>0){
				IAIMonServerRouteSV routeSV=(IAIMonServerRouteSV)ServiceFactory.getService(IAIMonServerRouteSV.class);
				routeSV.saveOrUpdate((IBOAIMonServerRouteValue[])datas.toArray(new IBOAIMonServerRouteValue[0]));
			}
			
		}catch(Exception e){
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000010")+e.getMessage());
		}
	}
	
	
}
