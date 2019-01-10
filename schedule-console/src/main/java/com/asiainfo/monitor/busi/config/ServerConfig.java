package com.asiainfo.monitor.busi.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.monitor.busi.cache.impl.MonSet;
import com.asiainfo.monitor.busi.cache.impl.Server;
import com.asiainfo.monitor.busi.cache.interfaces.IPhysicHost;
import com.asiainfo.monitor.busi.cache.interfaces.IServer;
import com.asiainfo.monitor.busi.exe.task.impl.WebConsoleTaskFactory;
import com.asiainfo.monitor.busi.exe.task.model.TaskTypeCode;
import com.asiainfo.monitor.busi.model.event.JmxSetEvent;
import com.asiainfo.monitor.busi.model.event.PhysicHostEvent;
import com.asiainfo.monitor.busi.model.event.ServerNodeEvent;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.model.interfaces.IWebConsoleTaskContext;
import com.asiainfo.monitor.tools.util.TypeConst;



/**
 * 应用
 * @author Guocx
 *
 */
public class ServerConfig implements IServer{

	private final static transient Log log=LogFactory.getLog(ServerConfig.class);
	
	private Server server;
	
	private ServerNodeConfig nodeConfig;
	
	private IPhysicHost phost;
	
	private IWebConsoleTaskContext start_CmdSet;
	
	private IWebConsoleTaskContext stop_CmdSet;
	
	private MonSet jmxSet;
	
	public ServerConfig(){
		
	}
	
	public ServerConfig(Server value){
		this.server=value;
	}

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public String getApp_Code() {
		return server.getApp_Code();
	}

	public String getApp_Id() {
		return server.getApp_Id();
	}


	public String getApp_Name() {
		return server.getApp_Name();
	}
	
	public String getApp_Ip() {
		return server.getApp_Ip();
	}

	public String getApp_Port() {
		return server.getApp_Port();
	}

	public String getCheck_Url() {
		return server.getCheck_Url();
	}
	
	public String getJmx_Http() {
		return server.getJmx_Http();
	}


	public String getLocator() {
		return server.getLocator();
	}


	public String getLocator_Type() {
		return server.getLocator_Type();
	}


	public String getRemark() {
		return server.getRemark();
	}
	
	public String getNode_Id() {
		return server.getNode_Id();
	}

	public String getPHost_Id(){
		return server.getHost_Id();
	}
	
	/**
	 * 读取物理主机信息
	 * @return
	 */
	public IPhysicHost getPhysicHost(){
		if (phost!=null){
			return phost;
		}
		synchronized(this){
			if (phost!=null)
				return phost;
			phost=(IPhysicHost)(new PhysicHostEvent()).getEventResult(getPHost_Id());
		}
		return phost;
	}
	
	public void setPhysicHost(IPhysicHost physicHost) {
		this.phost = physicHost;
	}
	
	public String getStart_CmdId() {
		return server.getStart_CmdId();
	}


	public String getStop_CmdId() {
		return server.getStop_CmdId();		
	}

	public String getVersionBack_CmdId() {
		return server.getVersionBack_CmdId();
	}

	public String getTemp_Type(){
		return server.getTemp_Type();
	}
	
	public String getSversion() {
		return server.getSversion();
	}
	
	//在不取主机对象时,该主机信息为空，减少内存浪费和节约调用时间
	public ServerNodeConfig getNodeConfig() {
		if (nodeConfig!=null){
			return nodeConfig;
		}
		synchronized (this){
			if (nodeConfig!=null)
				return nodeConfig;
			nodeConfig=(ServerNodeConfig)(new ServerNodeEvent()).getEventResult(server.getNode_Id());			
		}
		return nodeConfig;
	}

	public void setNodeConfig(ServerNodeConfig nodeConfig) {
		this.nodeConfig = nodeConfig;
	}
	
	//
	public MonSet getJmxSet(){
		if (jmxSet!=null)
			return jmxSet;
		synchronized (this){
			if (jmxSet!=null)
				return jmxSet;
			jmxSet=(MonSet)(new JmxSetEvent()).getEventResult(server.getApp_Id());
		}
		return jmxSet;
	}
	
	public void setJmxSet(MonSet jmxSet){
		this.jmxSet=jmxSet;
	}

	/**
	 * 读取启动应用命令集对象
	 * @return
	 */
	public IWebConsoleTaskContext getStart_CmdSet() {
		if (start_CmdSet!=null)
			return start_CmdSet;
		synchronized (this){
			if (start_CmdSet!=null)
				return start_CmdSet;
			WebConsoleTaskFactory factory=new WebConsoleTaskFactory();
			try{
				start_CmdSet=factory.getWebConsoleTaskContext(this,TaskTypeCode.START);
			}catch(Exception e){
				// 读取应用["+getApp_Id()+"]启动命令发生异常
				log.error(AIMonLocaleFactory.getResource("MOS0000220", getApp_Id())+e.getMessage());
			}finally{
				factory=null;
			}
		}
		return start_CmdSet;
	}

	public void setStart_CmdSet(IWebConsoleTaskContext start_CmdSet) {
		this.start_CmdSet = start_CmdSet;
	}

	public String getState() {
		return server.getState();
	}


	/**
	 * 读取停止应用命令集对象
	 * @return
	 */
	public IWebConsoleTaskContext getStop_CmdSet() {		
		if (stop_CmdSet!=null)
			return stop_CmdSet;
		synchronized (this){
			if (stop_CmdSet!=null)
				return stop_CmdSet;
			WebConsoleTaskFactory factory=new WebConsoleTaskFactory();
			try{
				stop_CmdSet=factory.getWebConsoleTaskContext(this,TaskTypeCode.STOP);
			}catch(Exception e){
				log.error(AIMonLocaleFactory.getResource("MOS0000220", getApp_Id())+e.getMessage());
			}finally{
				factory=null;
			}
			
		}
		return stop_CmdSet;
	}
	
	public void setStop_CmdSet(IWebConsoleTaskContext stop_CmdSet){
		this.stop_CmdSet = stop_CmdSet;
	}

	public String getMidware_Type() {
		return server.getMidware_Type();
	}
	
	public String getDomain(){
		return server.getDomain();
	}
	
	public String getJmxState(){
		return server.getJmxState();
	}
	
	//支持Action
	public boolean supportAction(){
		return getTemp_Type().equals(TypeConst.WEB) || getTemp_Type().equals(TypeConst.BOTH);
	}
	
	//支持方法统计
	public boolean supportMethod(){
		return getTemp_Type().equals(TypeConst.APP) || getTemp_Type().equals(TypeConst.BOTH);
	}
	
	//支持SQL统计
	public boolean supportSql(){
		return getTemp_Type().equals(TypeConst.APP) || getTemp_Type().equals(TypeConst.BOTH);
	}
	
	//支持事务统计
	public boolean supportDataSource(){
		return this.supportMethod();
	}

	//脚本放置路径
	public String getPf_path() {
		return server.getPf_path();
	}
	
	//支持
}
