package com.asiainfo.monitor.busi.service.impl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.dao.interfaces.IAIMonRuleSetRelatDAO;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonRuleSetRelatSV;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonRuleSetRelatValue;

public class AIMonRuleSetRelatSVImpl implements IAIMonRuleSetRelatSV{

	private static Log log=LogFactory.getLog(AIMonRuleSetRelatSVImpl.class);
	/**
	 * 根据规则集标识，读取规则集关联规则定义
	 * @param ruleSetId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonRuleSetRelatValue[] getRuleSetRelatByRuleSetId(String ruleSetId) throws RemoteException,Exception{
		IAIMonRuleSetRelatDAO ruleSetRelatDAO=(IAIMonRuleSetRelatDAO)ServiceFactory.getService(IAIMonRuleSetRelatDAO.class);
		return ruleSetRelatDAO.getRuleSetRelatByRuleSetId(ruleSetId);
	}
	
	/**
	 * 根据规则标识，读取规则集关联规则定义
	 * @param ruleId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonRuleSetRelatValue[] getRuleSetRelatByRuleId(String ruleId) throws RemoteException,Exception{
		IAIMonRuleSetRelatDAO ruleSetRelatDAO=(IAIMonRuleSetRelatDAO)ServiceFactory.getService(IAIMonRuleSetRelatDAO.class);
		return ruleSetRelatDAO.getRuleSetRelatByRuleId(ruleId);
	}
	
	/**
	 * 批量保存或修改规则集关联的规则设置
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonRuleSetRelatValue[] values) throws RemoteException,Exception {
		IAIMonRuleSetRelatDAO ruleSetRelatDAO=(IAIMonRuleSetRelatDAO)ServiceFactory.getService(IAIMonRuleSetRelatDAO.class);
		ruleSetRelatDAO.saveOrUpdate(values);
	}

	/**
	 * 保存或修改规则集关联的规则设置
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonRuleSetRelatValue value) throws RemoteException,Exception {
		IAIMonRuleSetRelatDAO ruleSetRelatDAO=(IAIMonRuleSetRelatDAO)ServiceFactory.getService(IAIMonRuleSetRelatDAO.class);
		ruleSetRelatDAO.saveOrUpdate(value);
	}
	
	/**
	 * 设置规则集关联规则信息
	 * @param values
	 * @throws Exception
	 */
	public void saveRelatData(String ruleSetId,IBOAIMonRuleSetRelatValue[] values) throws RemoteException,Exception{
//		if (values==null || values.length<=0)
//			return;		
		try{
			List beans=null;
			IAIMonRuleSetRelatDAO ruleSetRelatDAO=(IAIMonRuleSetRelatDAO)ServiceFactory.getService(IAIMonRuleSetRelatDAO.class);
			
			IBOAIMonRuleSetRelatValue[] oldRelatValues=this.getRuleSetRelatByRuleSetId(ruleSetId);
			
			if (oldRelatValues!=null && oldRelatValues.length>0){
				beans=new ArrayList(oldRelatValues.length);
				for (int i=0;i<oldRelatValues.length;i++){
					oldRelatValues[i].delete();
					beans.add(oldRelatValues[i]);
				}
				//删除
				ruleSetRelatDAO.saveOrUpdate((IBOAIMonRuleSetRelatValue[])beans.toArray(new IBOAIMonRuleSetRelatValue[0]));
			}
			//保存;注:删除和保存的数据没有放在同一个操作里，是由于DataStoreImpl里的saveOrUpdate方法执行语句时没有顺序
			if (values!=null && values.length>0){
				ruleSetRelatDAO.saveOrUpdate(values);
			}
		}catch(Exception e){
			log.error("Call AIMonRuleSetRelatSVImpl's Method saveRelatData has Exception:"+e.getMessage());
		}
		
		
	}
}
