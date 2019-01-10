package com.asiainfo.monitor.busi.dao.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import com.asiainfo.monitor.busi.dao.interfaces.IAIMonCtRecordDAO;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonCtRecordEngine;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonCtRecordValue;

public class AIMonCtRecordDAOImpl implements IAIMonCtRecordDAO {

	/**
	 * 根据条件查询Group
	 * 
	 * @param cond : String
	 * @param para : Map
	 * @return IBOAIMonGroupValue[]
	 * @throws Exception, RemoteException
	 */
	public IBOAIMonCtRecordValue[] getCtRecordByCondition(String cond, Map para) throws Exception {
		return BOAIMonCtRecordEngine.getBeans(cond, para);
	}
	

	/**
	 * 根据组标识，读取组信息
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCtRecordValue getCtRecordById(String id) throws Exception{
		return BOAIMonCtRecordEngine.getBean(Long.parseLong(id));
	}
	
	/**
	 * 根据操作类型获取控制记录
	 * @param ctType
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCtRecordValue[] getCtRecordByCtType(String ctType) throws Exception {
		StringBuilder condition = new StringBuilder();
		condition.append(IBOAIMonCtRecordValue.S_CtType + " = :ctType");
		condition.append(" and");
		
//		if(DialectFactory.getDialect().getDatabaseType().equalsIgnoreCase(DialectFactory.MYSQL)) {
//			condition.append(" sysdate() >= " + IBOAIMonCtRecordValue.S_EffectDate + " and sysdate() <=" + IBOAIMonCtRecordValue.S_InvalidDate);
//		}else if(DialectFactory.getDialect().getDatabaseType().equalsIgnoreCase(DialectFactory.ORACLE)){
			condition.append(" sysdate >= " + IBOAIMonCtRecordValue.S_EffectDate + " and sysdate <=" + IBOAIMonCtRecordValue.S_InvalidDate);
//		}
		
		condition.append(" and ");
		condition.append(IBOAIMonCtRecordValue.S_CtFlag +" = 1 ");
		Map param = new HashMap();
		param.put("ctType", Integer.parseInt(ctType));
		return this.getCtRecordByCondition(condition.toString(), param);
	}
	
	/**
	 * 查询当前有效的控制记录
	 * @param ct_Type操作类型
	 * @param obj_Type操作对象的类型
	 * @param obj_Id操作对象的标识
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCtRecordValue[] getEffectCtRecordByObjId(int ct_Type,int obj_Type,String obj_Id) throws Exception {
		StringBuffer condition=new StringBuffer("");
		condition.append(IBOAIMonCtRecordValue.S_CtType+" =:ct_Type ");
		condition.append(" and ");
		condition.append(IBOAIMonCtRecordValue.S_CtObjtype+" =:obj_Type ");
		condition.append(" and ");
		condition.append(IBOAIMonCtRecordValue.S_CtObjid+" =:obj_Id");
		condition.append(" and ");
		
//		if(DialectFactory.getDialect().getDatabaseType().equalsIgnoreCase(DialectFactory.MYSQL)) {
//			condition.append(" sysdate() between " + IBOAIMonCtRecordValue.S_EffectDate + " and " + IBOAIMonCtRecordValue.S_InvalidDate);
//		}else if(DialectFactory.getDialect().getDatabaseType().equalsIgnoreCase(DialectFactory.ORACLE)){
			condition.append(" sysdate between " + IBOAIMonCtRecordValue.S_EffectDate + " and " + IBOAIMonCtRecordValue.S_InvalidDate);
//		}
		
		condition.append(" and ");
		condition.append(IBOAIMonCtRecordValue.S_CtFlag+"=1 ");
		Map parameter=new HashMap();
		parameter.put("ct_Type",ct_Type);
		parameter.put("obj_Type",obj_Type);
		parameter.put("obj_Id",obj_Id);
		return this.getCtRecordByCondition(condition.toString(),parameter);
	}
	
	/**
	 * 查询当前有效的控制记录总数
	 * @param ct_Type
	 * @param obj_Type
	 * @return
	 * @throws Exception
	 */
	public int getEffectCtRecordCountByObjId (int ct_Type,int obj_Type) throws Exception {
		StringBuffer condition=new StringBuffer("");
		condition.append(IBOAIMonCtRecordValue.S_CtType+" =:ct_Type ");
		condition.append(" and ");
		condition.append(IBOAIMonCtRecordValue.S_CtObjtype+" =:obj_Type ");
		condition.append(" and ");
		
//		if(DialectFactory.getDialect().getDatabaseType().equalsIgnoreCase(DialectFactory.MYSQL)) {
//			condition.append(" sysdate() between "+IBOAIMonCtRecordValue.S_EffectDate+" and "+IBOAIMonCtRecordValue.S_InvalidDate);
//		}else if(DialectFactory.getDialect().getDatabaseType().equalsIgnoreCase(DialectFactory.ORACLE)){
			condition.append(" sysdate between "+IBOAIMonCtRecordValue.S_EffectDate+" and "+IBOAIMonCtRecordValue.S_InvalidDate);
//		}
		
		condition.append(" and ");
		condition.append(IBOAIMonCtRecordValue.S_CtFlag+"=1 ");
		Map parameter=new HashMap();
		parameter.put("ct_Type",ct_Type);
		parameter.put("obj_Type",obj_Type);
		return BOAIMonCtRecordEngine.getBeansCount(condition.toString(),parameter);
	}
	/**
	 * 批量保存
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonCtRecordValue[] values) throws Exception {
		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				if (values[i].isNew()) {
					values[i].setCtId(BOAIMonCtRecordEngine.getNewId().longValue());
				}
			}
		} else {
			throw new Exception("no data to saveOrUpdate...");
		}
		BOAIMonCtRecordEngine.saveBatch(values);
	}

	/**
	 * 保存或修改
	 * @param value
	 * @throws Exception
	 */
	public long saveOrUpdate(IBOAIMonCtRecordValue value) throws Exception {
		Timestamp sysDate = BOAIMonCtRecordEngine.getSysDate();
		if (value.isNew()) {
			value.setCtId(BOAIMonCtRecordEngine.getNewId().longValue());
			value.setEffectDate(sysDate);
			int ctType = value.getCtType();
			if (ctType == 1 || ctType == 3 || ctType == 5 || ctType == 7 || ctType == 9) {
				long tmpTime = sysDate.getTime() + value.getInvalidDuti() * 1000;
				value.setInvalidDate(new Timestamp(tmpTime));
			} else {
				value.setInvalidDate(sysDate);
			}

		} else {
			value.setEffectDate(sysDate);
			value.setInvalidDate(sysDate);
		}
		BOAIMonCtRecordEngine.save(value);
		return value.getCtId();
	}
	
	/**
	 * 删除信息
	 * @param groupId
	 * @throws Exception
	 */
	public void deleteGroup (long groupId) throws Exception{
		IBOAIMonCtRecordValue value = BOAIMonCtRecordEngine.getBean(groupId);
		if (null != value) {
			value.delete();
//			value.setState("E");
			BOAIMonCtRecordEngine.save(value);
		}
	}
}
