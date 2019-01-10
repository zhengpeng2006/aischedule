package com.asiainfo.monitor.busi.exe.task.service.impl;


import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.bo.DataContainerFactory;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.exe.task.bo.BOAIMonPExecBean;
import com.asiainfo.monitor.busi.exe.task.bo.BOAIMonPExecEngine;
import com.asiainfo.monitor.busi.exe.task.dao.interfaces.IAIMonPExecDAO;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPExecValue;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPInfoValue;
import com.asiainfo.monitor.busi.exe.task.model.TaskExecContainer;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPExecSV;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPInfoSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonAttentionPanelSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonParamDefineSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonParamValuesSV;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonParamDefineBean;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonParamValuesBean;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonAttentionPanelValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonParamDefineValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonParamValuesValue;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class AIMonPExecSVImpl implements IAIMonPExecSV {

	/**
	 * 根据条件查询进程监控配置
	 * 
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPExecValue[] getExecByName(String execName, Integer startNum, Integer endNum) throws RemoteException,Exception {
		IAIMonPExecDAO execDao = (IAIMonPExecDAO)ServiceFactory.getService(IAIMonPExecDAO.class);
		return execDao.getExecByName(execName, startNum, endNum);
	}
	
	public int getExecCount(String execName) throws RemoteException,Exception {
		IAIMonPExecDAO execDao = (IAIMonPExecDAO)ServiceFactory.getService(IAIMonPExecDAO.class);
		return execDao.getExecCount(execName);
	}

	/**
	 * 根据主键取得进程监控配置信息
	 * 
	 * @param execId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPExecValue getBeanById(long execId) throws RemoteException,Exception {
		IAIMonPExecDAO execDao = (IAIMonPExecDAO)ServiceFactory.getService(IAIMonPExecDAO.class);
		return execDao.getBeanById(execId);
	}
	
	/**
	 * 批量保存或修改进程监控配置
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonPExecValue[] values) throws RemoteException,Exception {
		IAIMonPExecDAO execDao = (IAIMonPExecDAO)ServiceFactory.getService(IAIMonPExecDAO.class);
		execDao.saveOrUpdate(values);
	}
	
	
	/**
	 * 保存或修改进程监控配置
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonPExecValue value) throws RemoteException,Exception {
		IAIMonPExecDAO execDao = (IAIMonPExecDAO)ServiceFactory.getService(IAIMonPExecDAO.class);
		execDao.saveOrUpdate(value);
	}
	
	/**
	 * 保存进程监控配置
	 * @param model
	 * @throws Exception
	 */
	public void savePExecByGuide(TaskExecContainer model,DataContainer[] dcs) throws RemoteException,Exception{
		this.savePExecByGuide(model);
		// 处理参数定义
		if(dcs == null || dcs.length ==0 )
			return ;
		IBOAIMonParamDefineValue[] defines = new IBOAIMonParamDefineValue[dcs.length];
		for(int i = 0; i< dcs.length; i++){
			
			defines[i] = new BOAIMonParamDefineBean();
			if (StringUtils.isBlank(dcs[i].getAsString(IBOAIMonParamDefineValue.S_ParamId))) {
				dcs[i].setStsToNew();
			} else {
				long value = dcs[i].getAsLong(IBOAIMonParamDefineValue.S_ParamId);
				dcs[i].clearProperty(IBOAIMonParamDefineValue.S_ParamId);
				dcs[i].initPropertyOld(IBOAIMonParamDefineValue.S_ParamId, value);

			}
			DataContainerFactory.copy(dcs[i], defines[i]);
			defines[i].setRegisteId(Long.parseLong(model.getId()));
			defines[i].setRegisteType(1);
			defines[i].setState("U");
		}
		IAIMonParamDefineSV defineSV = (IAIMonParamDefineSV)ServiceFactory.getService(IAIMonParamDefineSV.class);
		defineSV.saveOrUpdate(defines);
		// 处理参数值
		IBOAIMonParamValuesValue[] values = new IBOAIMonParamValuesValue[defines.length];
		for(int i=0;i<defines.length;i++){
			values[i] = new BOAIMonParamValuesBean();
			values[i].copy(defines[i]);
			values[i].setStsToNew();
			if(StringUtils.isBlank(dcs[i].getAsString(IBOAIMonParamValuesValue.S_ParamValue))){
				// 参数["+defines[i].getParamCode()+"]值不能为空！
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000182", defines[i].getParamCode()));
			}
			values[i].setParamValue(dcs[i].getAsString(IBOAIMonParamValuesValue.S_ParamValue));
			values[i].setRegisteId(model.getParentId());
			values[i].setRegisteType(10);
		}
		IAIMonParamValuesSV valuesSV = (IAIMonParamValuesSV)ServiceFactory.getService(IAIMonParamValuesSV.class);
		valuesSV.saveOrUpdate(values);
	}
	
	/**
	 * 保存进程监控配置(向导模式)
	 * @param model
	 * @throws Exception
	 */
	public void savePExecByGuide(TaskExecContainer model) throws RemoteException,Exception{
		try{
			IBOAIMonPExecValue execValue = new BOAIMonPExecBean();
			long execId=0;
			if ( StringUtils.isBlank(model.getId())){
				execValue.setStsToNew();
			}else{				
				execId=Long.parseLong(model.getId());
				execValue.setExecId(execId);
				execValue.setStsToOld();
			}
			execValue.setName(model.getName());
			execValue.setEType(model.getCmdType().getType());
			execValue.setExpr(model.getExpr());
			if (model.getEnable()){
				execValue.setState("U");
			}else{
				execValue.setState("E");
			}
			execValue.setRemarks(model.getCmdType().getName());
			
			IAIMonPExecDAO execDao = (IAIMonPExecDAO)ServiceFactory.getService(IAIMonPExecDAO.class);
			execDao.saveOrUpdate(execValue);
			model.setId(execValue.getExecId()+"");
			IAIMonPInfoSV pInfoSV =(IAIMonPInfoSV)ServiceFactory.getService(IAIMonPInfoSV.class);
			IBOAIMonPInfoValue pInfoValue=pInfoSV.getMonPInfoValue(model.getParentId());
			if (pInfoValue!=null){
				pInfoValue.setTypeId(execValue.getExecId());
				pInfoSV.saveOrUpdate(pInfoValue);
			}else{
				// 没有定义进程监控的任务
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000183"));
			}
		}catch(Exception e){
			throw new Exception("Call AIMonPExecSVImpl's method savePExecByGuide has Exception ::"+e.getMessage());
		}
		
	}
	
	/**
	 * 删除进程监控配置
	 * 
	 * @param execId
	 * @throws Exception
	 */
	public void delete(long execId) throws RemoteException,Exception {
		// 是否被任务使用
		IAIMonPInfoSV infoSV = (IAIMonPInfoSV)ServiceFactory.getService(IAIMonPInfoSV.class);
		IBOAIMonPInfoValue[] infos = infoSV.getTaskByType("EXEC",execId);
		if(infos != null && infos.length > 0)
			// "当前进程被正在使用的任务引用，不允许删除!"
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000184"));
		
		IAIMonPExecDAO execDao = (IAIMonPExecDAO)ServiceFactory.getService(IAIMonPExecDAO.class);
		execDao.delete(execId);
		//删除参数定义
		IAIMonParamDefineSV defSV = (IAIMonParamDefineSV)ServiceFactory.getService(IAIMonParamDefineSV.class);
		defSV.deleteParamDefineByRegisteType(1,execId);
	}
	
	/**
	 * 保存进程以及进程关联的参数
	 * 
	 * @param value
	 * @param paramDcs
	 * @throws Exception
	 */
	public void saveExecAndParams(IBOAIMonPExecValue value, DataContainer[] paramDcs) throws RemoteException,Exception {
		IAIMonPExecDAO execDao = (IAIMonPExecDAO)ServiceFactory.getService(IAIMonPExecDAO.class);
		
		long execId = value.getExecId();
		if (execId == 0) {
			execId = execDao.saveOrUpdate(value);
		} else {
			execDao.saveOrUpdate(value);
		}

		// 保存参数
		if(paramDcs == null || paramDcs.length == 0)
			return;

		for (int i = 0; i < paramDcs.length; i++) {
			paramDcs[i].set("REGISTE_ID", execId);
			paramDcs[i].set("STATE", "U");
			
			DataContainer dc = paramDcs[i];
			
			IBOAIMonParamDefineValue defineValue = new BOAIMonParamDefineBean();
			if (StringUtils.isBlank(dc.getAsString(IBOAIMonParamDefineValue.S_ParamId))) {
				dc.setStsToNew();
			} else {
				Object valueId = dc.get(IBOAIMonParamDefineValue.S_ParamId);
				dc.clearProperty(IBOAIMonParamDefineValue.S_ParamId);
				dc.initPropertyOld(IBOAIMonParamDefineValue.S_ParamId, valueId);

			}
			DataContainerFactory.copy(dc, defineValue);
			defineValue.setRegisteType(1);
			IAIMonParamDefineSV defineSV = (IAIMonParamDefineSV)ServiceFactory.getService(IAIMonParamDefineSV.class);
			// 保存
			defineSV.saveOrUpdate(defineValue);
//			saveParamDefineAndVal(paramDcs[i]);
		}
	}
	
	public void saveParamDefineAndVal(DataContainer dc) throws RemoteException, Exception {
		if(dc == null)
			return;
		IBOAIMonParamDefineValue defineValue = new BOAIMonParamDefineBean();
		if (StringUtils.isBlank(dc.getAsString(IBOAIMonParamDefineValue.S_ParamId))) {
			dc.setStsToNew();
		} else {
			Object value = dc.get(IBOAIMonParamDefineValue.S_ParamId);
			dc.clearProperty(IBOAIMonParamDefineValue.S_ParamId);
			dc.initPropertyOld(IBOAIMonParamDefineValue.S_ParamId, value);

		}
		DataContainerFactory.copy(dc, defineValue);
		defineValue.setRegisteType(1);
		IAIMonParamDefineSV defineSV = (IAIMonParamDefineSV)ServiceFactory.getService(IAIMonParamDefineSV.class);
		// 保存
		defineSV.saveOrUpdate(defineValue);
		
		IBOAIMonParamValuesValue valValue = new BOAIMonParamValuesBean();
		if (StringUtils.isBlank(dc.getAsString(IBOAIMonParamValuesValue.S_VId))) {
			dc.setStsToNew();
		} else {
			Object value = dc.get(IBOAIMonParamValuesValue.S_VId);
			dc.clearProperty(IBOAIMonParamValuesValue.S_VId);
			dc.initPropertyOld(IBOAIMonParamValuesValue.S_VId, value);

		}
		DataContainerFactory.copy(dc, valValue);
		valValue.setRegisteType(10);
		valValue.setParamId(defineValue.getParamId());
		IAIMonParamValuesSV valueSV = (IAIMonParamValuesSV)ServiceFactory.getService(IAIMonParamValuesSV.class);
		// 保存
		valueSV.saveOrUpdate(valValue);
	}

	/**
     * 根据条件查询进程监控配置
     * 
     * @param name
     * @param expr
     * @return
     * @throws Exception
     */
    @Override
    public IBOAIMonPExecValue[] getExecByNameAndExpr(String execName, String expr, Integer startNum, Integer endNum) throws RemoteException,
            Exception
    {
        StringBuilder condition=new StringBuilder("");
        Map parameter=new HashMap();
        condition.append(" state = 'U' ");
        if (StringUtils.isNotBlank(execName)) {
            condition.append(" and ");
            condition.append(IBOAIMonPExecValue.S_Name).append(" like :execName");
            parameter.put("execName", "%" + execName + "%");
        }
        if (StringUtils.isNotBlank(expr)){
            condition.append(" and ");
            condition.append(IBOAIMonPExecValue.S_Expr).append(" like :expr");
            parameter.put("expr", "%" + expr + "%");
        }
        IAIMonPExecDAO execDao = (IAIMonPExecDAO)ServiceFactory.getService(IAIMonPExecDAO.class);
        return execDao.query(condition.toString(), parameter, startNum, endNum);
    }
}
