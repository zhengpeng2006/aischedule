package com.asiainfo.monitor.busi.model.event;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.config.PanelShape;
import com.asiainfo.monitor.busi.exe.task.interfaces.AbstractTaskProcess;
import com.asiainfo.monitor.busi.model.interfaces.IDomainEvent;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonAttentionPanelSV;
import com.asiainfo.monitor.tools.common.KeyName;
import com.asiainfo.monitor.tools.model.BaseContainer;
import com.asiainfo.monitor.tools.model.DefaultTaskContext;
import com.asiainfo.monitor.tools.model.interfaces.ITaskCmdContainer;
import com.asiainfo.monitor.tools.model.interfaces.ITaskContext;


public class PanelTaskEvent extends AbstractTaskProcess implements IDomainEvent {

	private final static transient Log log=LogFactory.getLog(PanelTaskEvent.class);
	
	public PanelTaskEvent() {
		super();
		// TODO 自动生成构造函数存根
	}

	public Object getEventResult(Object shape) {
		Object result=null;
		try{
			IAIMonAttentionPanelSV attentionPanelSV=(IAIMonAttentionPanelSV)ServiceFactory.getService(IAIMonAttentionPanelSV.class);			
			ITaskContext taskCache=attentionPanelSV.getTaskContextById(((PanelShape)shape).getPanel().getPanel_Id());
			ITaskContext newTask=(ITaskContext)taskCache.clone();
			result=new ArrayList(1);
			
			if (((PanelShape)shape).getParameter()!=null && ((PanelShape)shape).getParameter().size()>0){
				
				List cmdList=newTask.getCmdContainers();
				List newCmdList=new LinkedList();
				for (int cCount=0;cCount<cmdList.size();cCount++){
					ITaskCmdContainer cmdContainer=(ITaskCmdContainer)cmdList.get(cCount);
					//先将指定的参数替换成界面设置的值,SERVER_ID和HOST_ID除外
					List webParams=((PanelShape)shape).getParameter();					
					for (int i=0;i<webParams.size();i++){
						KeyName webParam=(KeyName)webParams.get(i);						
						if (webParam!=null && !webParam.getName().equalsIgnoreCase("SERVER_ID") && !webParam.getName().equalsIgnoreCase("HOST_ID")){
							KeyName dbParam=((BaseContainer)cmdContainer).findParameter(webParam.getName());
							if (dbParam!=null){
								((BaseContainer)cmdContainer).addParameter(webParam);
							}
						}
					}
					
					KeyName serverParam=((BaseContainer)cmdContainer).findParameter("SERVER_ID");
					if (serverParam!=null){
						//如果数据库配置的参数有$SERVER_ID,则要求前台参数必须存在SERVER_ID
						for (int i=0;i<((PanelShape)shape).getParameter().size();i++){
							KeyName item=(KeyName)((PanelShape)shape).getParameter().get(i);
							if (item.getName().equals("SERVER_ID")){
								String[] values=StringUtils.split(item.getKey(),",");
								for (int count=0;count<values.length;count++){									
									ITaskCmdContainer newCmdContainer=null;
									if (values.length==1)
										newCmdContainer=cmdContainer;
									else
										newCmdContainer=(ITaskCmdContainer)((BaseContainer)cmdContainer).clone();
									
									KeyName realServerParam=new KeyName("SERVER_ID",values[count]);
									((BaseContainer)newCmdContainer).addParameter(realServerParam);
									newCmdContainer.setCode(realServerParam.getKey());//是否设置成应用的名称
									((BaseContainer)newCmdContainer).setServerId(values[count]);
									newCmdList.add(newCmdContainer);
								}
							}else{
								newTask.addParameter(item);
							}
						}
					}else {
						KeyName hostParam=((BaseContainer)cmdContainer).findParameter("HOST_ID");
						if (hostParam!=null){
							//如果数据库配置的参数值有$HOST_ID,则要求前台参数必须存在HOST_ID
							for (int i=0;i<((PanelShape)shape).getParameter().size();i++){
								KeyName item=(KeyName)((PanelShape)shape).getParameter().get(i);
								if (item.getName().equals("HOST_ID")){
									String[] values=StringUtils.split(item.getKey(),",");
									for (int count=0;count<values.length;count++){
										ITaskCmdContainer newCmdContainer=null;
										if (values.length==1)
											newCmdContainer=cmdContainer;
										else
											newCmdContainer=(ITaskCmdContainer)((BaseContainer)cmdContainer).clone();
										//newCmdContainer不能增加属性变化监听，如果 增加了属性监听，则ShellType设置Shell参数时就要过滤IP\Port等信息 
										KeyName realHostParam=new KeyName("HOST_ID",values[count]);
										((BaseContainer)newCmdContainer).addParameter(realHostParam);
										newCmdContainer.setCode(realHostParam.getKey());
										((BaseContainer)newCmdContainer).setHostId(values[count]);
										newCmdList.add(newCmdContainer);
									}
								}else{
									newTask.addParameter(item);
								}
							}
						}
					}
					
					
				}
				if (newCmdList.size()>0){
					((DefaultTaskContext)newTask).setCmdContainers(newCmdList);					
				}
			}
			if (newTask.getCmdContainers().size()>1){
				newTask.setAsyn(true);
			}
			execute(newTask);
			
			((List)result).add(newTask);
			
		}catch(Exception ex){
			log.error("Call PanelTaskEvent's method getEventResult has Exception :"+ex.getMessage());
		}
		return result;
	}

	/**
	 * 任务后继处理方法
	 * @param task
	 * @param rtn
	 * @return
	 * @throws Exception
	 */
	public void process(ITaskContext task) throws Exception{		
	}
}
