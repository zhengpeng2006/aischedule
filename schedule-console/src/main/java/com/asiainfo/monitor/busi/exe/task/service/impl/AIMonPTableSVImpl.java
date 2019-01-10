package com.asiainfo.monitor.busi.exe.task.service.impl;


import java.rmi.RemoteException;

import org.apache.commons.lang.StringUtils;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.bo.DataContainerFactory;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.exe.task.bo.BOAIMonPTableBean;
import com.asiainfo.monitor.busi.exe.task.dao.interfaces.IAIMonPTableDAO;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPInfoValue;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPTableValue;
import com.asiainfo.monitor.busi.exe.task.model.TaskTableContainer;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPInfoSV;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPTableSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonAttentionPanelSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonParamDefineSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonParamValuesSV;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonParamDefineBean;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonParamValuesBean;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonAttentionPanelValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonParamDefineValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonParamValuesValue;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class AIMonPTableSVImpl implements IAIMonPTableSV {

	/**
	 * 根据条件查询表数据监控配置
	 * 
	 * @param cond
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPTableValue[] getTableInfoByName(String tableName, Integer startNum, Integer endNum) throws RemoteException,Exception {
		IAIMonPTableDAO tableDao = (IAIMonPTableDAO)ServiceFactory.getService(IAIMonPTableDAO.class);
		return tableDao.getTableInfoByName(tableName, startNum, endNum);
	}
	
	public int getTableInfoCount(String tableName) throws RemoteException,Exception {
		IAIMonPTableDAO tableDao = (IAIMonPTableDAO)ServiceFactory.getService(IAIMonPTableDAO.class);
		return tableDao.getTableInfoCount(tableName);
	}
	
	/**
	 * 根据主键取得表数据监控配置信息
	 * 
	 * @param tableId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPTableValue getBeanById(long tableId) throws RemoteException,Exception {
		IAIMonPTableDAO tableDao = (IAIMonPTableDAO)ServiceFactory.getService(IAIMonPTableDAO.class);
		return tableDao.getBeanById(tableId);
	}
	
	/**
	 * 批量保存或修改表数据监控配置
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonPTableValue[] values) throws RemoteException,Exception {
		IAIMonPTableDAO tableDao = (IAIMonPTableDAO)ServiceFactory.getService(IAIMonPTableDAO.class);
		tableDao.saveOrUpdate(values);
	}
	
	/**
	 * 保存或修改表数据监控配置
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonPTableValue value) throws RemoteException,Exception {
		IAIMonPTableDAO tableDao = (IAIMonPTableDAO)ServiceFactory.getService(IAIMonPTableDAO.class);
		tableDao.saveOrUpdate(value);
	}
	
	/**
	 * 保存表监控配置（向导模式）
	 * @param model
	 * @throws Exception
	 */
	public void savePTableByGuide(TaskTableContainer tableModel, DataContainer[] dcs) throws RemoteException,Exception{
		this.savePTableByGuide(tableModel);
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
			defines[i].setRegisteId(Long.parseLong(tableModel.getId()));
			defines[i].setRegisteType(2);
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
			values[i].setRegisteId(tableModel.getParentId());
			values[i].setRegisteType(10);
		}
		IAIMonParamValuesSV valuesSV = (IAIMonParamValuesSV)ServiceFactory.getService(IAIMonParamValuesSV.class);
		valuesSV.saveOrUpdate(values);
	}
	
	/**
	 * 保存表监控配置（向导模式）
	 * @param model
	 * @throws Exception
	 */
	public void savePTableByGuide(TaskTableContainer model) throws RemoteException,Exception{
		try{
			IBOAIMonPTableValue tableValue = new BOAIMonPTableBean();
			long tableId=0;
			if ( StringUtils.isBlank(model.getId())){
				tableValue.setStsToNew();
			}else{
				tableId=Long.parseLong(model.getId());
				tableValue.setTableId(tableId);
				tableValue.setStsToOld();
			}
			
			tableValue.setName(model.getName());
			tableValue.setSql(model.getExpr());
			tableValue.setDbAcctCode(model.getDbacctCode());
			tableValue.setDbUrlName(model.getDburlName());
			if (model.getEnable()){
				tableValue.setState("U");
			}else{
				tableValue.setState("E");
			}
			tableValue.setRemarks(model.getCmdType().getName());
			
			IAIMonPTableDAO tableDao = (IAIMonPTableDAO)ServiceFactory.getService(IAIMonPTableDAO.class);
			tableDao.saveOrUpdate(tableValue);
			model.setId(tableValue.getTableId()+"");
			
			IAIMonPInfoSV pInfoSV =(IAIMonPInfoSV)ServiceFactory.getService(IAIMonPInfoSV.class);
			IBOAIMonPInfoValue pInfoValue=pInfoSV.getMonPInfoValue(model.getParentId());
			if (pInfoValue!=null){
				pInfoValue.setTypeId(tableValue.getTableId());
				pInfoSV.saveOrUpdate(pInfoValue);
			}else{
				// 没有定义表监控的任务
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000186"));
			}
		}catch(Exception e){
			throw new Exception("Call AIMonPTableSVImpl's method savePTableByGuide has Exception :"+e.getMessage());
		}
	}
	/**
	 * 删除表数据监控配置
	 * 
	 * @param tableId
	 * @throws Exception
	 */
	public void delete(long tableId) throws RemoteException,Exception {
	// 是否被任务使用
		IAIMonPInfoSV infoSV = (IAIMonPInfoSV)ServiceFactory.getService(IAIMonPInfoSV.class);
		IBOAIMonPInfoValue[] infos = infoSV.getTaskByType("TABLE",tableId);
		if(infos != null && infos.length > 0)
			// "当前表SQL被正在使用的任务引用，不允许删除!"
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000187"));
		// 是否被面板引用
		IAIMonAttentionPanelSV panelSV = (IAIMonAttentionPanelSV)ServiceFactory.getService(IAIMonAttentionPanelSV.class);
		IBOAIMonAttentionPanelValue[] panels = panelSV.getPanelByType("TABLE",tableId);
		if(panels != null && panels.length > 0)
			// "当前表SQL被正在使用的面板引用，不允许删除!"
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000188"));
		
		IAIMonPTableDAO tableDao = (IAIMonPTableDAO)ServiceFactory.getService(IAIMonPTableDAO.class);
		tableDao.delete(tableId);
		//删除参数定义
		IAIMonParamDefineSV defSV = (IAIMonParamDefineSV)ServiceFactory.getService(IAIMonParamDefineSV.class);
		defSV.deleteParamDefineByRegisteType(2,tableId);
	}
}
