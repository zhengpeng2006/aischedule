package com.asiainfo.monitor.busi.exe.task.impl;



import java.util.List;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.config.ServerConfig;
import com.asiainfo.monitor.busi.config.ServerNodeConfig;
import com.asiainfo.monitor.busi.exe.task.Listener.JmxBanListener;
import com.asiainfo.monitor.busi.exe.task.Listener.JmxInspireListener;
import com.asiainfo.monitor.busi.exe.task.Listener.ServerStateListener;
import com.asiainfo.monitor.busi.exe.task.Listener.TaskBanListener;
import com.asiainfo.monitor.busi.exe.task.Listener.TaskProcessListener;
import com.asiainfo.monitor.busi.exe.task.Listener.VersionChangeListener;
import com.asiainfo.monitor.busi.exe.task.model.TaskExecContainer;
import com.asiainfo.monitor.busi.exe.task.model.TaskTypeCode;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonCmdSV;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonCmdSetRelatValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonCmdSetValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonCmdValue;
import com.asiainfo.monitor.tools.model.BaseContainer;
import com.asiainfo.monitor.tools.model.WebConsoleTaskContext;
import com.asiainfo.monitor.tools.model.interfaces.IContainerListener;
import com.asiainfo.monitor.tools.model.interfaces.ITaskCmdContainer;
import com.asiainfo.monitor.tools.model.interfaces.IWebConsoleTaskContext;
import com.asiainfo.monitor.tools.util.TypeConst;

public abstract class AbstractWebConsoleTaskFactory {

	
	public abstract IBOAIMonCmdSetValue getCmdSetValue(ConstructType type,String value) throws Exception;
	
	public IWebConsoleTaskContext getWebConsoleTaskContext(ServerConfig server,TaskTypeCode typeCode) throws Exception{
		IWebConsoleTaskContext webConsoleTask = null;
		
		if (server != null){
			ServerNodeConfig node = server.getNodeConfig();
			
			//组织构造任务上下文,以后可采用建造模式重构
			webConsoleTask = new WebConsoleTaskContext();
			//将typeCode设置成附件
			((BaseContainer)webConsoleTask).setAttach(typeCode);
			
			//监听应用各种状态，暂时只提供对启动、发布、停止以及重启状态的监听			
			ServerStateListener stateListener = new ServerStateListener();
			webConsoleTask.addContainerListener(stateListener);//
			
			//将JMX_STATE设置关闭
			TaskProcessListener listener = new TaskProcessListener();
			IContainerListener jmxBanListener = new JmxBanListener();
			
			//增加容器监听
			webConsoleTask.addContainerListener(jmxBanListener);
			if (typeCode == TaskTypeCode.START){
				//同步JMX_STATE状态
				IContainerListener jmxInspireListener = new JmxInspireListener();
				webConsoleTask.addContainerListener(jmxInspireListener);
			}
			//版本检查、设置
			IContainerListener versionChangeListener = new VersionChangeListener();
			webConsoleTask.addContainerListener(versionChangeListener);
			//增加属性监听
			webConsoleTask.addPropertyChangeListener(listener);
			
			
			webConsoleTask.setExecMethod(TypeConst._TASKMETHOD_I);					
			webConsoleTask.setHostId(node.getNode_Id());
			webConsoleTask.setServerId(server.getApp_Id());
			
			
			IAIMonCmdSV cmdSV=(IAIMonCmdSV)ServiceFactory.getService(IAIMonCmdSV.class);
			IBOAIMonCmdSetValue boCmdSetValue=null;
			if (typeCode==TaskTypeCode.START)
				boCmdSetValue=this.getCmdSetValue(ConstructType.ID,server.getStart_CmdId());
			else if (typeCode==TaskTypeCode.STOP)
				boCmdSetValue=this.getCmdSetValue(ConstructType.ID,server.getStop_CmdId());
			else if (typeCode == TaskTypeCode.RELEASE)
				boCmdSetValue=this.getCmdSetValue(ConstructType.CODE,typeCode.toString());//发布
			else if (typeCode == TaskTypeCode.RESTART)
				boCmdSetValue=this.getCmdSetValue(ConstructType.CODE, typeCode.toString());
			else if (typeCode == TaskTypeCode.VERSIONBACK)
				boCmdSetValue=this.getCmdSetValue(ConstructType.ID, server.getVersionBack_CmdId());
			
			if (boCmdSetValue!=null){
				
				//构造命令集						
				webConsoleTask.setId(boCmdSetValue.getCmdsetId()+"");
				webConsoleTask.setCode(boCmdSetValue.getCmdsetCode());
				webConsoleTask.setName(boCmdSetValue.getCmdsetName());
				
				
				IBOAIMonCmdSetRelatValue[] cmdSetRelats=cmdSV.getCmdSetRelatByCmdSetIdOrderBySort(boCmdSetValue.getCmdsetId());
				if (cmdSetRelats!=null && cmdSetRelats.length>0){
					
//					IContainerListener subscribeListener=new ControlSubscribeListener(); 
					IContainerListener taskBanListener=new TaskBanListener();					
					for (int count=0;count<cmdSetRelats.length;count++){
						IBOAIMonCmdValue cmdValue=cmdSV.getCmdById(cmdSetRelats[count].getCmdId());
						
						
						//循环构造具体命令容器
						ITaskCmdContainer cmdContainer=new TaskExecContainer();
						List params=DefaultTaskFactory.getInnerParams("30",cmdSetRelats[count].getCmdId()+"");
						if (cmdContainer instanceof BaseContainer){
							((BaseContainer)cmdContainer).addContainerListener(taskBanListener);//检测命令类型，必须是Shell命令
							((BaseContainer)cmdContainer).addContainerListener(listener);
//							((BaseContainer)cmdContainer).addContainerListener(subscribeListener);//增加前台订阅
							((BaseContainer)cmdContainer).setParent((BaseContainer)webConsoleTask);
							if (params!=null && params.size()>0)
								((BaseContainer)cmdContainer).addParameter(params);//设置具体命令参数
						}
						((TaskExecContainer)cmdContainer).setId(cmdValue.getCmdId()+"");
						((TaskExecContainer)cmdContainer).setHostId(node.getNode_Id());
						((TaskExecContainer)cmdContainer).setServerId(server.getApp_Id()+"");
						((TaskExecContainer)cmdContainer).setName(cmdValue.getCmdName());
						((TaskExecContainer)cmdContainer).setCmdType(DefaultTaskFactory.getCmdType(cmdValue.getCmdType()));
						((TaskExecContainer)cmdContainer).setExpr(cmdValue.getCmdExpr());
						
						((TaskExecContainer)cmdContainer).setParentId(cmdSetRelats[count].getCmdsetId());
						((TaskExecContainer)cmdContainer).setSort(count);
						webConsoleTask.putCmdItem(cmdContainer);
														
					}
				}
			}
		}
		return webConsoleTask;
	}
	
	public enum ConstructType{
		ID,CODE		
	}
}
