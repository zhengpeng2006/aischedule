package com.asiainfo.monitor.busi.exe.task.impl;



import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.cache.interfaces.IPanel;
import com.asiainfo.monitor.busi.cache.interfaces.IPhysicHost;
import com.asiainfo.monitor.busi.cache.interfaces.IServer;
import com.asiainfo.monitor.busi.exe.task.Listener.TaskProcessListener;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPExecValue;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPTableValue;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPTimeValue;
import com.asiainfo.monitor.busi.exe.task.model.TaskExecContainer;
import com.asiainfo.monitor.busi.exe.task.model.TaskTableContainer;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPExecSV;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPTableSV;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPTimeSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonPhysicHostSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;
import com.asiainfo.monitor.tools.common.KeyName;
import com.asiainfo.monitor.tools.exe.task.model.TaskRtnModel;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.model.TimeModel;
import com.asiainfo.monitor.tools.model.WebPanelTaskContext;
import com.asiainfo.monitor.tools.model.interfaces.IContext;
import com.asiainfo.monitor.tools.model.interfaces.ITaskCmdContainer;
import com.asiainfo.monitor.tools.util.TypeConst;


public class WebPanelTaskFactory extends DefaultTaskFactory{
	
	/**
	 * 根据关注面板获得任务
	 * 面板任务也是采用后台定时任务的设置
	 * @param panels
	 * @return
	 * @throws Exception
	 */
	public static WebPanelTaskContext getWebPanelTaskContext(IPanel panel) throws Exception{
		WebPanelTaskContext result=null;
		try{
			if (panel==null)
				return result;
			IAIMonPTableSV objMonTableSV=(IAIMonPTableSV)ServiceFactory.getService(IAIMonPTableSV.class);
			IAIMonPExecSV objExecSV=(IAIMonPExecSV)ServiceFactory.getService(IAIMonPExecSV.class);
			IAIMonPTimeSV objTimeSV = (IAIMonPTimeSV) ServiceFactory.getService(IAIMonPTimeSV.class);
			TaskProcessListener listener=new TaskProcessListener();
			result=new WebPanelTaskContext();
			result.addContainerListener(listener);
			((IContext)result).addPropertyChangeListener(listener);
			
			result.setId(panel.getPanel_Id());
			result.setName(panel.getPanel_Name());
			result.setLayer(panel.getLayer());
			result.setExecMethod(panel.getExec_Method());
			TaskRtnModel rtnModel=new TaskRtnModel();
			if (StringUtils.isNotBlank(panel.getView_transform()))
				rtnModel.setTransform(panel.getView_transform());
			result.setRtnModel(rtnModel);
			if (StringUtils.isNotBlank(panel.getView_strategy())){
				result.setViewWrapperClass(panel.getView_strategy());
			}
			//设置周期对象,面板任务暂时不做周期设置
			if (StringUtils.isNotBlank(panel.getTime_Id()) && Long.parseLong(panel.getTime_Id())>0){
				IBOAIMonPTimeValue objTime=objTimeSV.getBeanById(Long.parseLong(panel.getTime_Id()));
				if (objTime!=null){
					TimeModel time=new TimeModel();
					time.setId(objTime.getTimeId());
					time.setType(objTime.getTType());
					time.setExpr(objTime.getExpr());
					result.setTime(time);
				}
			}
			//获取面板任务参数
			List params=getInnerParams("20",panel.getPanel_Id());
			Map parameterMap=paraseParameter(params);
			if (parameterMap.size()>5){
				result.setAsyn(true);
			}
			for (Iterator pts=parameterMap.keySet().iterator();pts.hasNext();){
				String key=String.valueOf(pts.next());
				List itemParamList=(List)parameterMap.get(key);
				//设置具体命令对象
				if (panel.getObj_Type().equals(ITaskCmdContainer._TASK_TABLE)){
					IBOAIMonPTableValue objMonTable = objMonTableSV.getBeanById(Long.parseLong(panel.getObj_Id()));
					if (objMonTable!=null){
						TaskTableContainer table=new TaskTableContainer();
						table.addContainerListener(listener);
						table.setId(objMonTable.getTableId()+"");
						table.setName(objMonTable.getName());
						table.setCmdType(getCmdType(ITaskCmdContainer._TASK_TABLE));
						table.setDburlName(objMonTable.getDbUrlName());
						table.setDbacctCode(objMonTable.getDbAcctCode());
						table.setExpr(objMonTable.getSql());
						table.setParent(result);
						if (itemParamList!=null && itemParamList.size()>0)
							table.addParameter(itemParamList);
						result.putCmdItem(table);
						rtnModel.setType(ITaskCmdContainer._TASK_TABLE);
					}
				}else if (panel.getObj_Type().equals(ITaskCmdContainer._TASK_EXEC)){
					IBOAIMonPExecValue objMonExec = objExecSV.getBeanById(Long.parseLong(panel.getObj_Id()));
					if (objMonExec!=null){
						TaskExecContainer exec=new TaskExecContainer();
						exec.addContainerListener(listener);
						exec.setId(objMonExec.getExecId()+"");
						exec.setName(objMonExec.getName());
						exec.setParent(result);
						if (objMonExec.getEType()==null)							
							throw new Exception(AIMonLocaleFactory.getResource("MOS0000353", panel.getObj_Id()));
						exec.setCmdType(getCmdType(objMonExec.getEType()));
						exec.setExpr(objMonExec.getExpr());
						if (itemParamList!=null && itemParamList.size()>0)
							exec.addParameter(itemParamList);//设置参数
						result.putCmdItem(exec);
						rtnModel.setType(objMonExec.getEType());
					}
				}else{
					// 未定义面板任务["+panel.getPanel_Id()+"]对应类型["+panel.getObj_Type()+"]的领域数据模型
					throw new Exception(AIMonLocaleFactory.getResource("MOS0000150", panel.getPanel_Id(), panel.getObj_Type()));
				}
			}
			
			//设置警告对象
			if (StringUtils.isNotBlank(panel.getThreshold_Id())){
				result.setThreshold(builderThreshold(Long.parseLong(panel.getThreshold_Id())));
			}
			
						
		}catch(Exception e){
			throw new Exception("Call TaskFactory is Exception:"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 解析面板任务的数据库配置的参数
	 * 如果只有1个参数，并且值以|分割，则任务是多任务，但值中不能有$；
	 * 如果只有1个参数，值没有|分割，则为单任务，如果值以$打头，则为单任务并且参数值是由前台传入
	 * 
	 * 
	 * 如果有多个参数，并且参数值以|分割，则要求必须这几个参数的值都是以|分割，个数一致，值中不能有$
	 * 如果有多个参数，并且参数值没有|分割,则为单任务，如果值以$打头，则为单任务并且参数值是由前台传入
	 * 
	 * 如果有多个参数，并且其中一个参数是ALL_SERVER或ALL_HOST，则其他参数不能以|分割也不能以$打头
	 * @param list
	 * @return
	 * @throws Exception
	 */
	private static Map paraseParameter(List list) throws Exception{
		Map result=new HashMap();
		if (list==null || list.size()<1)
			return result;
		//取第一个参数值做判断,因为:如果是以|分割的参数，则要求其他参数也必须都是|分割
		String oneParamCode=((KeyName)list.get(0)).getName();
		String oneParamValue=((KeyName)list.get(0)).getKey();
		if (oneParamValue.indexOf(TypeConst._SPLIT_CHAR)<0){
			boolean isAllServer=false;
			boolean isAllHost=false;
			//检查参数中是否存在ALL_SERVER or ALL_HOST
			for (int i=0;i<list.size();i++){
				String itemParamValue=((KeyName)list.get(i)).getKey();
				if (itemParamValue.equals("ALL_SERVER")){
					isAllServer=true;
					break;
				}
				if (itemParamValue.equals("ALL_HOST")){
					isAllHost=true;
					break;
				}
			}
			if (isAllServer && isAllHost)
				//参数配置错误,ALL_SERVER和ALL_HOST同时只能配其一
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000356"));
			if (isAllServer){
				IAIMonServerSV serverSV=(IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
				List serverList=serverSV.getAllServer();
				//ALL_SERVER:克隆其他参数将其和每个SERVER_ID组成一个命令任务的参数集合，保持参数原来的次序
				for (int sCount=0;sCount<serverList.size();sCount++){
					KeyName itemKey=new KeyName("SERVER_ID",((IServer)serverList.get(sCount)).getApp_Id()+"");
					List itemList=new LinkedList();
					for (int i=0;i<list.size();i++){
						String itemParamValue=((KeyName)list.get(i)).getKey();
						if (itemParamValue.equals("ALL_SERVER")){
							itemList.add(itemKey);
						}else{
							itemList.add(list.get(i));
						}
					}
					result.put("P_"+sCount,itemList);
				}
				return result;
			}
			//参数中存在ALL_HOST
			if (isAllHost){
				IAIMonPhysicHostSV hostSV=(IAIMonPhysicHostSV)ServiceFactory.getService(IAIMonPhysicHostSV.class);
				List hostList=hostSV.getAllPhost();
				//ALL_HOST:克隆其他参数将其和每个HOST_ID组成一个命令任务的参数集合，保持参数原来的次序
				for (int hCount=0;hCount<hostList.size();hCount++){
                    KeyName itemKey = new KeyName("HOST_ID", ((IPhysicHost) hostList.get(hCount)).getHostId());
					List itemList=new LinkedList();
					for (int i=0;i<list.size();i++){
						String itemParamValue=((KeyName)list.get(i)).getKey();
						if (itemParamValue.equals("ALL_HOST")){
							itemList.add(itemKey);
						}else{
							itemList.add(list.get(i));
						}
					}
					result.put("P_"+hCount,itemList);
				}
				return result;
			}
			
			//单任务
			result.put("P_0", list);
			return result;			
		}
		String[] cmdCount=StringUtils.split(oneParamValue, TypeConst._SPLIT_CHAR);
		for (int i=0;i<cmdCount.length;i++){
			List itemList=new LinkedList();
			KeyName itemKey=new KeyName(oneParamCode,cmdCount[i]);
			itemList.add(itemKey);
			result.put("P_"+i,itemList);
		}
		for (int i=1;i<list.size();i++){
			String itemParamCode=((KeyName)list.get(i)).getName();
			String itemParamValue=((KeyName)list.get(i)).getKey();
			
			if (itemParamValue.indexOf(TypeConst._SPLIT_CHAR)<0){
				//参数配置错误
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000066")+":"+itemParamValue);
			}
			String[] itemCmdCount=StringUtils.split(itemParamValue,TypeConst._SPLIT_CHAR);
			if (cmdCount.length!=itemCmdCount.length){
				//参数配置错误
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000066")+":"+itemParamValue);
			}
			for (int count=0;count<cmdCount.length;count++){
				if (!result.containsKey("P_"+count))
					throw new Exception(AIMonLocaleFactory.getResource("MOS0000066")+":"+itemParamValue);
				List existList=(List)result.get("P_"+count);
				KeyName itemKey=new KeyName(itemParamCode,itemCmdCount[count]);
				existList.add(itemKey);
			}		
		}
		return result;
	}
}
