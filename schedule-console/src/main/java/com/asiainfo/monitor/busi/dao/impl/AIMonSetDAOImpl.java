package com.asiainfo.monitor.busi.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.asiainfo.monitor.busi.dao.interfaces.IAIMonSetDAO;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonSetEngine;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonSetValue;

public class AIMonSetDAOImpl implements IAIMonSetDAO{

	public AIMonSetDAOImpl() {
		super();
		// TODO 自动生成构造函数存根
	}

	/**
	 * 根据标识获取设置
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonSetValue getMonSetBeanById(long id) throws Exception{
		return BOAIMonSetEngine.getBean(id);
	}
	
	/**
	 * 根据应用标识、设置码查询设置
	 * @param appId
	 * @param vcode
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonSetValue[] getMonSetBeanByAppIdAndVCode(long appId,String vcode) throws Exception{
		StringBuilder sb=new StringBuilder("");
		sb.append(IBOAIMonSetValue.S_ServerId+"= :appId ");
		sb.append("AND ");
		sb.append(IBOAIMonSetValue.S_SetVcode+" =:vcode ");
		Map parameter=new HashMap();
		parameter.put("appId",appId);
		parameter.put("vcode",vcode);
		IBOAIMonSetValue[] values=BOAIMonSetEngine.getBeans(sb.toString(),parameter);
		return values;
	}
	/**
	 * 读取所有设置
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonSetValue[] getAllMonSetBean() throws Exception{
		return BOAIMonSetEngine.getBeans(IBOAIMonSetValue.S_State+" ='U' ",null); 
	}
	
	/**
	 * 保存设置
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public long saveOrUpdate(IBOAIMonSetValue value) throws Exception {
		if (value.isNew()) {
			value.setSetId(BOAIMonSetEngine.getNewId().longValue());
		}
		BOAIMonSetEngine.save(value);
		return value.getSetId();
	}
	
	/**
	 * 批量保存设置
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonSetValue[] values) throws Exception {
		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				if (values[i].isNew()) {
					values[i].setSetId(BOAIMonSetEngine.getNewId().longValue());
				}
			}
		} else {
			throw new Exception("no data to saveOrUpdate...");
		}		
		BOAIMonSetEngine.save(values);
	}
	
	/**
	 * 删除设置
	 * @param id
	 * @throws Exception
	 */
	public void deleteSet(long id) throws Exception{
		IBOAIMonSetValue value = BOAIMonSetEngine.getBean(id);
		if (null != value) {
			value.delete();
			value.setState("E");
			BOAIMonSetEngine.save(value);
		}
	}
}
