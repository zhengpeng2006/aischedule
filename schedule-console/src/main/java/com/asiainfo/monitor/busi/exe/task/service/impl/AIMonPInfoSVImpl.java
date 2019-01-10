package com.asiainfo.monitor.busi.exe.task.service.impl;


import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.bo.DataContainerFactory;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.exe.task.dao.interfaces.IAIMonPInfoDAO;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPImgDataResolveValue;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPInfoGroupValue;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPInfoValue;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPImgDataResolveSV;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPInfoGroupAtomicSV;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPInfoSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonParamValuesSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonStaticDataSV;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonParamValuesBean;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonParamValuesValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonStaticDataValue;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.model.interfaces.ITaskCmdContainer;

public class AIMonPInfoSVImpl implements IAIMonPInfoSV {

	/**
	 * 根据执行类型查询指定类型所有任务信息
	 * @param taskMethod
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPInfoValue[] getAllTaskInfo(String taskMethod) throws RemoteException,Exception{
		StringBuilder sb=new StringBuilder("");
		Map parameter=new HashMap();
		if (StringUtils.isBlank(taskMethod))
			sb.append(IBOAIMonPInfoValue.S_State+"='U'");
		else{
			sb.append(IBOAIMonPInfoValue.S_TaskMethod+"= :taskMethod").append(" and ").append(IBOAIMonPInfoValue.S_State+"='U'");
			parameter.put("taskMethod",taskMethod);
		}
		IAIMonPInfoDAO infoDao = (IAIMonPInfoDAO)ServiceFactory.getService(IAIMonPInfoDAO.class);
		return infoDao.query(sb.toString(),parameter);
	}
	
	/**
	 * 根据数据分片规则读取数据
	 * @param split
	 * @param mod
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPInfoValue[] getMonPInfoValuesBySplitAndMod(long split,long mod,long value) throws RemoteException,Exception{
		IAIMonPInfoDAO infoDao = (IAIMonPInfoDAO)ServiceFactory.getService(IAIMonPInfoDAO.class);
		return infoDao.getMonPInfoValuesBySplitAndMod(split,mod,value);
	}
	
	/**
	 * 根据条件查询任务信息
	 * 
	 * @param taskName
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPInfoValue[] getTaskInfoByName(String taskName,String state,Integer startNum, Integer endNum) throws RemoteException,Exception {
		IAIMonPInfoDAO infoDao = (IAIMonPInfoDAO)ServiceFactory.getService(IAIMonPInfoDAO.class);
		return infoDao.getTaskInfoByName(taskName,state,startNum, endNum);
	}
	
	/**
	 * 根据任务名、状态读取任务数
	 * @param taskName
	 * @param state
	 * @return
	 * @throws Exception
	 */
	public int getTaskInfoCount(String taskName,String state) throws RemoteException,Exception {
		IAIMonPInfoDAO infoDao = (IAIMonPInfoDAO)ServiceFactory.getService(IAIMonPInfoDAO.class);
		return infoDao.getTaskInfoCount(taskName,state);
	}
	
	/**
	 * 根据解析标识，读取任务数
	 * @param resolveId
	 * @return
	 * @throws Exception
	 */
	public int getTaskInfoCountByResolveId(long resolveId) throws RemoteException,Exception{
		IAIMonPInfoDAO infoDao = (IAIMonPInfoDAO)ServiceFactory.getService(IAIMonPInfoDAO.class);
		return infoDao.getTaskInfoCountByResolveId(resolveId);
	}
	
	/**
	 *
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	public DataContainerInterface[] queryTaskInfoByCond(String groupId, int startIndex, int endIndex) throws RemoteException,Exception{
		DataContainerInterface[] result = null;
		IAIMonPInfoDAO infoDao = (IAIMonPInfoDAO)ServiceFactory.getService(IAIMonPInfoDAO.class);
		IBOAIMonPInfoValue[] ret = infoDao.queryTaskInfoByCond(groupId,startIndex,endIndex);
		if(ret!=null&&ret.length>0){
			result = new DataContainerInterface[ret.length];
			IAIMonPImgDataResolveSV resolveSV = (IAIMonPImgDataResolveSV)ServiceFactory.getService(IAIMonPImgDataResolveSV.class);
			IAIMonStaticDataSV staticSV=(IAIMonStaticDataSV)ServiceFactory.getService(IAIMonStaticDataSV.class);
			IBOAIMonStaticDataValue staticDataBean=null;			
			IAIMonPInfoGroupAtomicSV infoGroupSV=(IAIMonPInfoGroupAtomicSV)ServiceFactory.getService(IAIMonPInfoGroupAtomicSV.class);
			IBOAIMonPInfoGroupValue infoGroupBean=infoGroupSV.getMonPInfoGroupById(Long.parseLong(groupId));
			for(int i=0;i<ret.length;i++){
				result[i] = new DataContainer();
				result[i].copy(ret[i]);
				result[i].set("CHK", "false");
				IBOAIMonPImgDataResolveValue tmp =resolveSV.getMonPImgDataResolveById(ret[i].getResolveId());
				result[i].set("SHOW_TYPE", tmp.getShowType());
				if (tmp!=null){					
					staticDataBean= staticSV.queryByCodeTypeAndValue("VIEW_TYPE",tmp.getShowType());
					if (staticDataBean!=null)
						result[i].set("SHOW_TYPE_NAME", staticDataBean.getCodeName());
					else
						result[i].set("SHOW_TYPE_NAME", staticDataBean.getCodeValue());
				}
				
				if (ret[i].getMType().equals(ITaskCmdContainer._TASK_EXEC))
					result[i].set("M_TYPE_NAME",AIMonLocaleFactory.getResource("MOS0000323"));
				else if (ret[i].getMType().equals(ITaskCmdContainer._TASK_TABLE))
					result[i].set("M_TYPE_NAME",AIMonLocaleFactory.getResource("MOS0000324"));
				else
					result[i].set("M_TYPE_NAME",AIMonLocaleFactory.getResource("MOS0000325"));
				
				
				
				if (infoGroupBean!=null)
					result[i].set("GROUP_NAME", infoGroupBean.getGroupName());
				else
					result[i].set("GROUP_NAME", groupId);
				
			}
		}
		return result;
	}
	
	/**
	 *
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	public DataContainerInterface[] getTaskInfoByGroupId(String groupId) throws RemoteException,Exception{
		DataContainerInterface[] result = null;
		IAIMonPInfoDAO infoDao = (IAIMonPInfoDAO)ServiceFactory.getService(IAIMonPInfoDAO.class);
		IBOAIMonPInfoValue[] ret = infoDao.getTaskInfoByGroupId(groupId);
		if(ret!=null&&ret.length>0){
			result = new DataContainerInterface[ret.length];
			IAIMonPImgDataResolveSV resolveSV = (IAIMonPImgDataResolveSV)ServiceFactory.getService(IAIMonPImgDataResolveSV.class);
			IBOAIMonPImgDataResolveValue tmp =resolveSV.getMonPImgDataResolveById(ret[0].getResolveId());
			for(int i=0;i<ret.length;i++){
				result[i] = new DataContainer();
				result[i].copy(ret[i]);
				result[i].set("CHK", "false");
				result[i].set("SHOW_TYPE", tmp.getShowType());
				
			}
		}
		return result;
	}
	
	/**
	 * 
	 * @param groupID
	 * @return
	 * @throws Exception
	 */
	public int getTaskInfoCountByCond(String groupId) throws RemoteException,Exception {
		IAIMonPInfoDAO infoDao = (IAIMonPInfoDAO)ServiceFactory.getService(IAIMonPInfoDAO.class);
		return infoDao.getTaskInfoCountByCond(groupId);
	}
	
	/**
	 * 根据分组编号和任务名查找任务
	 * @param groupId
	 * @param taskName
	 * @return
	 * @throws Exception
	 */
	public DataContainerInterface[] queryTaskInfoByGrpAndInfoName(String groupId,String taskName,int startIndex,int endIndex) throws RemoteException,Exception{
		DataContainerInterface[] result = null;
		IAIMonPInfoDAO infoDao = (IAIMonPInfoDAO)ServiceFactory.getService(IAIMonPInfoDAO.class);
		IBOAIMonPInfoValue[] ret = infoDao.queryTaskInfoByGrpAndInfoName(groupId,taskName,startIndex,endIndex);
		if(ret!=null&&ret.length>0){
			result = new DataContainerInterface[ret.length];
			IAIMonPImgDataResolveSV resolveSV = (IAIMonPImgDataResolveSV)ServiceFactory.getService(IAIMonPImgDataResolveSV.class);
			IAIMonStaticDataSV staticSV=(IAIMonStaticDataSV)ServiceFactory.getService(IAIMonStaticDataSV.class);
			IBOAIMonStaticDataValue staticDataBean=null;			
			IAIMonPInfoGroupAtomicSV infoGroupSV=(IAIMonPInfoGroupAtomicSV)ServiceFactory.getService(IAIMonPInfoGroupAtomicSV.class);
			IBOAIMonPInfoGroupValue infoGroupBean=infoGroupSV.getMonPInfoGroupById(Long.parseLong(groupId));
			for(int i=0;i<ret.length;i++){
				result[i] = new DataContainer();
				result[i].copy(ret[i]);
				result[i].set("CHK", "false");
				IBOAIMonPImgDataResolveValue tmp =resolveSV.getMonPImgDataResolveById(ret[i].getResolveId());
				result[i].set("SHOW_TYPE", tmp.getShowType());
				if (tmp!=null){					
					staticDataBean= staticSV.queryByCodeTypeAndValue("VIEW_TYPE",tmp.getShowType());
					if (staticDataBean!=null)
						result[i].set("SHOW_TYPE_NAME", staticDataBean.getCodeName());
					else
						result[i].set("SHOW_TYPE_NAME", staticDataBean.getCodeValue());
				}
				
				if (ret[i].getMType().equals(ITaskCmdContainer._TASK_EXEC))
					result[i].set("M_TYPE_NAME",AIMonLocaleFactory.getResource("MOS0000323"));
				else if (ret[i].getMType().equals(ITaskCmdContainer._TASK_TABLE))
					result[i].set("M_TYPE_NAME",AIMonLocaleFactory.getResource("MOS0000324"));
				else
					result[i].set("M_TYPE_NAME",AIMonLocaleFactory.getResource("MOS0000325"));
				
				
				
				if (infoGroupBean!=null)
					result[i].set("GROUP_NAME", infoGroupBean.getGroupName());
				else
					result[i].set("GROUP_NAME", groupId);
				
			}
		}
		return result;
	}
	
	/**
	 * 根据分组编号和任务名查找任务个数
	 * @param groupId
	 * @param taskName
	 * @return
	 * @throws Exception
	 */
	public int getTaskInfoCountByGrpAndInfoName(String groupId,String taskName) throws RemoteException,Exception{
		IAIMonPInfoDAO infoDao = (IAIMonPInfoDAO)ServiceFactory.getService(IAIMonPInfoDAO.class);
		return infoDao.getTaskInfoCountByGrpAndInfoName(groupId,taskName);
	}
	
	/**
	 * 根据主键读取任务Bean
	 * @param infoId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPInfoValue getMonPInfoValue(long infoId) throws RemoteException,Exception{
		IAIMonPInfoDAO infoDao = (IAIMonPInfoDAO)ServiceFactory.getService(IAIMonPInfoDAO.class);
		return infoDao.getBeanById(infoId);
	}
	
	
	/**
	 * 批量保存或修改任务信息
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonPInfoValue[] values) throws RemoteException,Exception {
		IAIMonPInfoDAO infoDao = (IAIMonPInfoDAO)ServiceFactory.getService(IAIMonPInfoDAO.class);
		infoDao.saveOrUpdate(values);
	}
	
	/**
	 * 保存或修改任务信息
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonPInfoValue value ) throws RemoteException,Exception {
		IAIMonPInfoDAO infoDao = (IAIMonPInfoDAO)ServiceFactory.getService(IAIMonPInfoDAO.class);
		infoDao.saveOrUpdate(value);
	}
	
	/**
	 * 保存任务信息与参数信息
	 * @param infoValue
	 * @param paramDcs
	 * @throws RemoteException
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonPInfoValue infoValue, DataContainer[] paramDcs) throws RemoteException,Exception{
		saveOrUpdate(infoValue);
		
		// 删除参数
		IAIMonParamValuesSV valSV = (IAIMonParamValuesSV)ServiceFactory.getService(IAIMonParamValuesSV.class);
		valSV.deleteParamValues(10, infoValue.getInfoId());
		// 保存参数
		if(paramDcs == null || paramDcs.length == 0)
			return;
		IBOAIMonParamValuesValue[] values = new IBOAIMonParamValuesValue[paramDcs.length];
		for(int i = 0; i< paramDcs.length; i++){
			
			values[i] = new BOAIMonParamValuesBean();
//			if (StringUtils.isBlank(paramDcs[i].getAsString(IBOAIMonParamValuesValue.S_VId))) {
//				paramDcs[i].setStsToNew();
//			} else {
//				long value = paramDcs[i].getAsLong(IBOAIMonParamValuesValue.S_VId);
//				paramDcs[i].clearProperty(IBOAIMonParamValuesValue.S_VId);
//				paramDcs[i].initPropertyOld(IBOAIMonParamValuesValue.S_VId, value);
//
//			}
			paramDcs[i].setStsToNew();
			DataContainerFactory.copy(paramDcs[i], values[i]);
			values[i].setRegisteId(infoValue.getInfoId());
			values[i].setRegisteType(10);
		}
	
		valSV.saveOrUpdate(values);
	}
	
	/**
	 * 删除任务信息
	 * 
	 * @param infoId
	 * @throws Exception
	 */
	public void delete(long infoId) throws RemoteException,Exception {
		// delete ai_mon_param_values
		IAIMonParamValuesSV valuesSv =  (IAIMonParamValuesSV)ServiceFactory.getService(IAIMonParamValuesSV.class);
		valuesSv.deleteParamValues(10,infoId);
		
		IAIMonPInfoDAO infoDao = (IAIMonPInfoDAO)ServiceFactory.getService(IAIMonPInfoDAO.class);
		infoDao.delete(infoId);
	}
	
	/**
	 * 通过组查询组下面的任务信息
	 * @param resolveId
	 * @throws Exception
	 */
	public IBOAIMonPInfoValue[] getTaskInfoByResolveId(String resolveId) throws RemoteException,Exception{
		IAIMonPInfoDAO infoDao = (IAIMonPInfoDAO)ServiceFactory.getService(IAIMonPInfoDAO.class);
		return infoDao.getTaskInfoByResolveId(resolveId);
	}
	
	/**
	 * 根据任务分组ID，同步层信息
	 * @param groupId
	 * @param layer
	 * @throws Exception
	 */
	public void updateLayer(long groupId,String layer) throws RemoteException,Exception{
		IAIMonPInfoDAO infoDao = (IAIMonPInfoDAO)ServiceFactory.getService(IAIMonPInfoDAO.class);
		IBOAIMonPInfoValue[] infos = infoDao.getTaskInfoByGroupId(String.valueOf(groupId));
		if(infos==null || infos.length ==0)
			return;
		for(int i=0;i<infos.length;i++)
			infos[i].setLayer(layer);
		infoDao.saveOrUpdate(infos);
	}
	
	/**
	 * 将指定具体任务设置成执行完成
	 * 在任务执行方式为I(立即执行)或F(固定时间执行一次)时,该任务执行完毕后会将状态设置成F
	 * @param infoId
	 * @throws RemoteException
	 * @throws Exception
	 */
	public void updateState(long infoId) throws RemoteException,Exception{
		IAIMonPInfoDAO infoDao = (IAIMonPInfoDAO)ServiceFactory.getService(IAIMonPInfoDAO.class);
		IBOAIMonPInfoValue infoValue=infoDao.getBeanById(infoId);
		if (infoValue!=null){
			infoValue.setState("F");
		}
		infoDao.saveOrUpdate(infoValue);
	}
	/**
	 * 根据对象类型以及对象标识获取任务
	 * @param mType
	 * @param typeId
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public IBOAIMonPInfoValue[] getTaskByType(String mType, long typeId)throws RemoteException,Exception{
		IAIMonPInfoDAO infoDao = (IAIMonPInfoDAO)ServiceFactory.getService(IAIMonPInfoDAO.class);
		return infoDao.getTaskByType(mType,typeId);
	}
	
	/**
	 * 屏蔽任务
	 * 将任务状态设置为S
	 * @param infoIds
	 * @throws RemoteException
	 * @throws Exception
	 */
	public void shieldInfoByInfoIds(Object[] infoIds) throws RemoteException,Exception{
		if (infoIds==null || infoIds.length<1)
			return;
		List shieldDatas=new ArrayList();
		IAIMonPInfoDAO infoDao = (IAIMonPInfoDAO)ServiceFactory.getService(IAIMonPInfoDAO.class);
		for (int i=0;i<infoIds.length;i++){
			IBOAIMonPInfoValue voBean=infoDao.getBeanById(Long.parseLong(String.valueOf(infoIds[i])));
			if (voBean!=null){
				voBean.setStsToOld();
				voBean.setState("S");
				shieldDatas.add(voBean);
			}
		}
		if (shieldDatas.size()>0){
			infoDao.saveOrUpdate((IBOAIMonPInfoValue[])shieldDatas.toArray(new IBOAIMonPInfoValue[0]));
		}
	}

	@Override
	public IBOAIMonPInfoValue[] getMonPInfoValueByParams(long groupId,long timeId,long thresholdId,long typeId)
			throws RemoteException, Exception {
		IAIMonPInfoDAO infoDao = (IAIMonPInfoDAO)ServiceFactory.getService(IAIMonPInfoDAO.class);
		StringBuffer sb = new StringBuffer();
		Map paramter = new HashMap();
		sb.append(IBOAIMonPInfoValue.S_State).append(" = :state");
        paramter.put("state", "U");
		if (groupId > 0)
		{
			sb.append(" and ").append(IBOAIMonPInfoValue.S_GroupId).append(" = :groupId");
			paramter.put("groupId", groupId);
		}
		if (timeId > 0)
		{
			sb.append(" and ").append(IBOAIMonPInfoValue.S_TimeId).append(" = :timeId");
			paramter.put("timeId", timeId);
		}
		if (thresholdId > 0)
		{
			sb.append(" and ").append(IBOAIMonPInfoValue.S_ThresholdId).append(" = :thresholdId");
			paramter.put("thresholdId", thresholdId);
		}
		if (typeId > 0)
        {
            sb.append(" and ").append(IBOAIMonPInfoValue.S_TypeId).append(" = :typeId");
            paramter.put("typeId", typeId);
        }
		
		return infoDao.query(sb.toString(), paramter);
	}

	/**
     * 根据任务名和编号查询
     * @param name
     * @param code
     * @param startIndex 
     * @param endIndex
     * @return
     * @throws RemoteException
     * @throws Exception
     */
    @Override
    public IBOAIMonPInfoValue[] getInfosByNameAndCode(String name, String code, int startIndex, int endIndex) throws RemoteException, Exception
    {
        StringBuffer sb = new StringBuffer();
        Map param = new HashMap();
        IAIMonPInfoDAO infoDao = (IAIMonPInfoDAO) ServiceFactory.getService(IAIMonPInfoDAO.class);
        sb.append(IBOAIMonPInfoValue.S_State).append("= :state");
        param.put("state", "U");
        if(!StringUtils.isBlank(name)) {
            sb.append(" and ").append(IBOAIMonPInfoValue.S_Name).append(" like :name");
            param.put("name", "%" + name + "%");
        }
        if(!StringUtils.isBlank(code)) {
            sb.append(" and ").append(IBOAIMonPInfoValue.S_InfoCode).append(" like :code");
            param.put("code", "%" + code + "%");
        }
        return infoDao.query(sb.toString(), param, startIndex, endIndex);
    }

    @Override
    public IBOAIMonPInfoValue[] getPInfoByLocalIp() throws Exception
    {
        IAIMonPInfoDAO infoDao = (IAIMonPInfoDAO) ServiceFactory.getService(IAIMonPInfoDAO.class);
        return infoDao.getPInfoByLocalIp();
    }
	
}
