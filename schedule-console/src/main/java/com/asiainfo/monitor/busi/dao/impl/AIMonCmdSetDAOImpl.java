package com.asiainfo.monitor.busi.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.asiainfo.monitor.busi.dao.interfaces.IAIMonCmdSetDAO;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonCmdSetEngine;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonCmdSetValue;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class AIMonCmdSetDAOImpl implements IAIMonCmdSetDAO {

	public IBOAIMonCmdSetValue[] query(String condition, Map parameter)
			throws Exception {
		return BOAIMonCmdSetEngine.getBeans(condition, parameter);
	}

	public IBOAIMonCmdSetValue getCmdSetById(long id) throws Exception {
		return BOAIMonCmdSetEngine.getBean(id);
	}
	
	/**
	 * 根据标识码读取命令集
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCmdSetValue getCmdSetByCode(String code) throws Exception{
		if (StringUtils.isBlank(code))
			// 标识为空
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000098"));
		StringBuilder condition=new StringBuilder("");
		Map parameter=new HashMap();
		condition.append(IBOAIMonCmdSetValue.S_CmdsetCode+" = :code ");
		parameter.put("code",code);
		condition.append(" and ");
		condition.append(IBOAIMonCmdSetValue.S_State+"='U' ");
		IBOAIMonCmdSetValue[] result=query(condition.toString(),parameter);
		if (result==null || result.length<=0)
			return null;
		if (result.length>1)
			// 标识码为["+code+"]的命令集记录为多条
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000211", code));
		return result[0];
	}

	/**
	 * 根据标识码读取命令集
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCmdSetValue[] getCmdSetsByCode(String code) throws Exception {
		if (StringUtils.isBlank(code))
			// 标识为空
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000098"));
		StringBuilder condition=new StringBuilder("");
		Map parameter=new HashMap();
		condition.append(IBOAIMonCmdSetValue.S_CmdsetCode+" = :code ");
		parameter.put("code",code);
		condition.append(" and ");
		condition.append(IBOAIMonCmdSetValue.S_State+"='U' ");
		IBOAIMonCmdSetValue[] result=query(condition.toString(),parameter);
		if (result==null || result.length<=0)
			return null;
		return result;
	}
	
	/**
	 * 通过脚本集名称查询
	 * @param setName
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCmdSetValue[] getCmdSetByName(String setName)
			throws Exception {
		StringBuilder condition=new StringBuilder("");	
		Map parameter = new HashMap();
		if (StringUtils.isNotBlank(setName)) {
			condition.append(IBOAIMonCmdSetValue.S_CmdsetName).append(" like :setName");
			parameter.put("setName", "%" + setName + "%");
		}
		if (StringUtils.isNotBlank(condition.toString())){
			condition.append(" and ");
		}
		condition.append(" state = 'U' ");
		return query(condition.toString(), parameter);
	}

	public void saveOrUpdate(IBOAIMonCmdSetValue[] values) throws Exception {
		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				if (values[i].isNew()) {
					values[i].setCmdsetId(BOAIMonCmdSetEngine.getNewId().longValue());
				}
			}
		} else {
			throw new Exception("no data to saveOrUpdate...");
		}
		BOAIMonCmdSetEngine.saveBatch(values);
	}

	public void saveOrUpdate(IBOAIMonCmdSetValue value) throws Exception {
		if (value.isNew()) {
			value.setCmdsetId(BOAIMonCmdSetEngine.getNewId().longValue());
		}
		BOAIMonCmdSetEngine.save(value);
	}
	
	/**
	 * 删除命令集
	 * @param cmdSetId
	 * @throws Exception
	 */
	public void deleteCmdSet (long cmdSetId) throws Exception{
		IBOAIMonCmdSetValue value = BOAIMonCmdSetEngine.getBean(cmdSetId);
		if (null != value) {
//			value.delete();
			value.setState("E");
			BOAIMonCmdSetEngine.save(value);
		}
	}

}
