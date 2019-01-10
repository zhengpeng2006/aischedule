package com.asiainfo.monitor.busi.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.asyn.operation.AsynOperation;
import com.asiainfo.monitor.busi.asyn.operation.impl.CollectJmxProAsynOperate;
import com.asiainfo.monitor.busi.cache.MonCacheManager;
import com.asiainfo.monitor.busi.cache.impl.AIMonServerCacheImpl;
import com.asiainfo.monitor.busi.cache.impl.MonSet;
import com.asiainfo.monitor.busi.cache.impl.Server;
import com.asiainfo.monitor.busi.config.ServerConfig;
import com.asiainfo.monitor.busi.dao.interfaces.IAIMonSetDAO;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonColgRuleSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonSetSV;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonSetBean;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonColgRuleValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonSetValue;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;


public class AIMonSetSVImpl implements IAIMonSetSV {
	
	private static transient Log log=LogFactory.getLog(AIMonSetSVImpl.class);
	
	/**
	 * 根据标识获取设置
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonSetValue getMonSetBeanById(String id) throws RemoteException,Exception{
		IAIMonSetDAO dao=(IAIMonSetDAO)ServiceFactory.getService(IAIMonSetDAO.class);
		return dao.getMonSetBeanById(Long.parseLong(id));
	}
	
	/**
	 * 根据应用标识、设置码查询设置
	 * @param appId
	 * @param vcode
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonSetValue[] getMonSetBeanByAppIdAndVCode(String appId,String vcode) throws RemoteException,Exception{
		IAIMonSetDAO dao=(IAIMonSetDAO)ServiceFactory.getService(IAIMonSetDAO.class);
		IBOAIMonSetValue[] sets = dao.getMonSetBeanByAppIdAndVCode(Long.parseLong(appId),vcode);
		return sets;
	}
	
	/**
	 * 根据应用标识、设置码从缓存中查询设置
	 * @param appId
	 * @param vcode
	 * @return
	 * @throws Exception
	 */
	public MonSet getMonSetByAppIdAndVCode(String appId,String vcode) throws RemoteException,Exception{
		if (StringUtils.isBlank(appId) || StringUtils.isBlank(vcode))
			// "参数传入错误
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000008") + ", appid="+appId+",vcode="+vcode);
		IBOAIMonSetValue[] values=this.getMonSetBeanByAppIdAndVCode(appId,vcode);
		MonSet result=null;
		if (values!=null && values.length>0){
			result=this.wrapperMonSetByBean(values[0]);
		}
		return result;
	}
	
	
	/**
	 * 读取所有设置
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonSetValue[] getAllMonSetBean() throws RemoteException,Exception{
		IAIMonSetDAO dao=(IAIMonSetDAO)ServiceFactory.getService(IAIMonSetDAO.class);
		return dao.getAllMonSetBean(); 
	}
	
	/**
	 * 同步Jmx注册状态
	 * 读取Jmx属性文件,并且设置Jmx是否启用
	 * @throws Exception
	 */
	public void synchroJmxState(Object[] appIds) throws RemoteException,Exception{
		try{
			if (appIds==null || appIds.length<1)
				// "没有传入参数"
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000005"));
			
			int threadCount=(appIds.length>>1)+1;
			int cpuCount=Runtime.getRuntime().availableProcessors();
			if (threadCount> (cpuCount*3))
				threadCount=cpuCount*3;
			AsynOperation operate=new CollectJmxProAsynOperate();
			List opList=operate.asynOperation(threadCount,-1,appIds,-1);
			IAIMonColgRuleSV colgRuleSV=(IAIMonColgRuleSV)ServiceFactory.getService(IAIMonColgRuleSV.class);
			IBOAIMonColgRuleValue result=colgRuleSV.getDefaultColgRuleByType("CJMXPRO");
			if (result==null || StringUtils.isBlank(result.getExpr1()))
				// "没有设置采集Jmx属性文件的参数"
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000120"));
			
			String jmxPath=result.getExpr1();//"/tmp/jmx_tmp"			
			File f = new File(jmxPath);
			File[] tmp = f.listFiles();
			boolean isEnable=false;
			IAIMonServerSV serverSV=null;
			List beans=new ArrayList();
			for (int j = 0; j < tmp.length; j++) {
				//jmx_****.properties
				String appCode=null;
				if (tmp[j].getName().indexOf("_")>0 && tmp[j].getName().indexOf(".")>0){
					appCode=tmp[j].getName().substring(tmp[j].getName().indexOf("_")+1,tmp[j].getName().indexOf("."));
				}
				if (StringUtils.isBlank(appCode)){
					if (log.isDebugEnabled())
						// "文件"+tmp[j]+"不符合命名定义jmx_****.properties,****代表应用的JVM环境变量appframe.client.app.name或appframe.server.name的值"
						log.debug(AIMonLocaleFactory.getResource("MOS0000121", tmp[j].getName()));
					continue;
				}				
				InputStream input=new FileInputStream(tmp[j]);
				if (input == null)
					continue;
				Properties properties = new Properties();
				try {					
					properties.load(input);
					if(properties!=null && properties.getProperty("jmx.enable").equalsIgnoreCase("true"))
						isEnable=true;
					else
						isEnable=false;
					if (serverSV==null)
						serverSV=(IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
					ServerConfig serverConfig=serverSV.getServerByServerCode(appCode);	
					if (serverConfig==null){
						// 没找到标识码为["+appCode+"]的应用定义信息
						log.error(AIMonLocaleFactory.getResource("MOS0000122", appCode));
						continue;
					}
					IBOAIMonSetValue[] setValues=this.getMonSetBeanByAppIdAndVCode(serverConfig.getApp_Id(),"JMX_STATE");
					IBOAIMonSetValue setValue;
					if (setValues==null || setValues.length == 0){
						setValue=new BOAIMonSetBean();
						setValue.setHostId(Long.getLong(serverConfig.getNode_Id()));
						setValue.setServerId(Long.getLong(serverConfig.getApp_Id()));
						setValue.setAppName(serverConfig.getApp_Name());
						setValue.setSetVcode("JMX_STATE");
						setValue.setSetValue(isEnable?"ON":"OFF");
						setValue.setCreateDate(new Timestamp(System.currentTimeMillis()));
						setValue.setState("U");
						setValue.setStsToNew();						
					}else{
						setValue = setValues[0];
						setValue.setSetValue(isEnable?"ON":"OFF");
						setValue.setCreateDate(new Timestamp(System.currentTimeMillis()));
					}
					beans.add(setValue);
				}catch (IOException ex) {
					// "读取Jmx属性文件["+tmp[j]+"]异常"
					log.error(AIMonLocaleFactory.getResource("MOS0000123", tmp[j].getName()),ex);
				}catch(Exception e){
					log.error(AIMonLocaleFactory.getResource("MOS0000049"),e);
				}finally{
					properties=null;
				}
			}
			if (beans.size()>0){
				this.saveOrUpdate((IBOAIMonSetValue[])beans.toArray(new IBOAIMonSetValue[0]));
				for (int i=0;i<beans.size();i++){
					IBOAIMonSetValue item=(IBOAIMonSetValue)beans.get(i);
					Server server=(Server)MonCacheManager.get(AIMonServerCacheImpl.class,item.getServerId()+"");
					server.setJmxState(item.getSetValue());//同步Jmx状态操作固定在深夜做
				}
			}
		}catch(Exception e){
			log.error("Call AppTraceAction's Method saveOrUpdateByJmxProperites has Exception :"+e.getMessage());
			// 采集Jmx属性文件失败
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000124")+e.getMessage());
		}
		return;
	}
	
	/**
	 * 将设置简单封装
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public MonSet wrapperMonSetByBean(IBOAIMonSetValue value) throws RemoteException,Exception{
		if (value==null || StringUtils.isBlank(value.getState())  || value.getState().equals("E"))
			return null;
		MonSet result=new MonSet();
		result.setId(value.getSetId()+"");
		result.setCode(value.getSetCode());
		result.setHostId(value.getHostId()+"");
		result.setAppId(value.getServerId()+"");
		result.setAppName(value.getAppName());
		result.setVcode(value.getSetVcode());
		result.setValue(value.getSetValue());
		result.setVdesc(value.getSetDesc());
		result.setRemarks(value.getRemarks());
		return result;
	}
	/**
	 * 保存设置
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public long saveOrUpdate(IBOAIMonSetValue value) throws RemoteException,Exception {
		IAIMonSetDAO dao=(IAIMonSetDAO)ServiceFactory.getService(IAIMonSetDAO.class);
		long id=dao.saveOrUpdate(value);
		return id;
	}
	
	/**
	 * 批量保存设置
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonSetValue[] values) throws RemoteException,Exception {		
		IAIMonSetDAO dao=(IAIMonSetDAO)ServiceFactory.getService(IAIMonSetDAO.class);
		dao.saveOrUpdate(values);
	}
	
	/**
	 * 删除设置
	 * @param id
	 * @throws Exception
	 */
	public void deleteSet(String id) throws RemoteException,Exception{
		IAIMonSetDAO dao=(IAIMonSetDAO)ServiceFactory.getService(IAIMonSetDAO.class);
		dao.deleteSet(Long.parseLong(id));
	}

}
