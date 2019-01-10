package com.asiainfo.monitor.busi.exe.task.impl;


import java.util.List;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.cache.impl.Panel;
import com.asiainfo.monitor.busi.cache.interfaces.ICustomPanel;
import com.asiainfo.monitor.busi.exe.task.Listener.TaskProcessListener;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonAttentionPanelSV;
import com.asiainfo.monitor.tools.exe.task.model.TaskRtnModel;
import com.asiainfo.monitor.tools.model.BaseContainer;
import com.asiainfo.monitor.tools.model.CustomPanelTaskContext;
import com.asiainfo.monitor.tools.model.interfaces.IContext;
import com.asiainfo.monitor.tools.model.interfaces.ICustomIterator;
import com.asiainfo.monitor.tools.model.interfaces.ITaskCmdContainer;
import com.asiainfo.monitor.tools.model.interfaces.ITaskContext;

public class CustomPanelTaskFactory extends DefaultTaskFactory {

	/**
	 * 根据自定义面板获得任务
	 * 自定义面板任务也是采用后台定时任务的设置
	 * @param panel: CutomPanel自定义Panel,内部关联多个公共面板
	 * @return WebPanelTaskContext:一个自定义面板被认为是一个任务，而子公共面板就所对应的Table\Exec被加载到自定义面板中
	 * @throws Exception
	 * 
	 */
	public static CustomPanelTaskContext getCustomPanelTaskContext(ICustomPanel customPanel) throws Exception{
		CustomPanelTaskContext result=null;
		try{
			if (customPanel==null)
				return result;
			//根据
			TaskProcessListener listener=new TaskProcessListener();
			result=new CustomPanelTaskContext();
			result.addContainerListener(listener);
			((IContext)result).addPropertyChangeListener(listener);
			result.setId(customPanel.getPanel_Id());
			result.setName(customPanel.getPanel_Name());
			result.setLayer(customPanel.getLayer());
			//自定义不设置执行方式，具体执行方式定义在命令容器上
			result.setExecMethod(null);
			
			ICustomIterator iterator=customPanel.createIterator();
			IAIMonAttentionPanelSV attentionPanelSV=(IAIMonAttentionPanelSV)ServiceFactory.getService(IAIMonAttentionPanelSV.class);
			
			
			//将自定义面板关联的子面板对应的任务的命令都设置到自定义面板任务的命令容器中
			while (!iterator.isDone()){
				Panel itemPanel=(Panel)iterator.currentItem();
				iterator.next();
				if (itemPanel==null)
					continue;
				ITaskContext taskCache=attentionPanelSV.getTaskContextById(itemPanel.getPanel_Id());
				if (taskCache==null)
					continue;
				List cmdList=taskCache.getCmdContainers();
				if (cmdList==null || cmdList.size()<1)
					continue;
				//获取自面板关联公共面板的参数,目前参数就以公共面板配置的参数为准(20)
//				List params=getInnerParams("40",itemShape.getPanel().getPanel_Id());
				
				for (int cCount=0;cCount<cmdList.size();cCount++){
					ITaskCmdContainer cmdContainer=(ITaskCmdContainer)cmdList.get(cCount);
					if (cmdContainer!=null){
						ITaskCmdContainer newCmdContainer=(ITaskCmdContainer)((BaseContainer)cmdContainer).clone();
						((BaseContainer)newCmdContainer).setParent(result);
						//自定义面板关联的面板运行方式必须由关联关系中定义的运行方式为准
						newCmdContainer.setRunTimeType(itemPanel.getExec_Method());
						result.putViewWrapperClass(newCmdContainer.getId(),itemPanel.getView_strategy(),itemPanel.getView_transform());
						result.putCmdRelatePanel(newCmdContainer.getId(), itemPanel.getPanel_Id());
						result.putCmdItem(newCmdContainer);
					}
				}
				
			}
			
			TaskRtnModel rtnModel=new TaskRtnModel();
			result.setRtnModel(rtnModel);

			//设置周期对象,面板任务暂时不做周期设置
						
		}catch(Exception e){
			throw new Exception("Call TaskFactory is Exception:"+e.getMessage());
		}
		return result;
	}
}
