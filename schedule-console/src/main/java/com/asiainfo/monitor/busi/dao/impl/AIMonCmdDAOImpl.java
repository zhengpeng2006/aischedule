package com.asiainfo.monitor.busi.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.asiainfo.monitor.busi.dao.interfaces.IAIMonCmdDAO;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonCmdEngine;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonCmdValue;

public class AIMonCmdDAOImpl implements IAIMonCmdDAO {

	public IBOAIMonCmdValue[] query(String condition, Map parameter)
			throws Exception {
		return BOAIMonCmdEngine.getBeans(condition, parameter);
	}

	public IBOAIMonCmdValue queryById(long id) throws Exception {
		return BOAIMonCmdEngine.getBean(id);
	}

	public IBOAIMonCmdValue[] queryByName(String name) throws Exception {
		StringBuilder condition=new StringBuilder("");	
		Map parameter = new HashMap();
		if (StringUtils.isNotBlank(name)) {
			condition.append(IBOAIMonCmdValue.S_CmdName).append(" like :name ");
			parameter.put("name", "%" + name + "%");
		}
		if (StringUtils.isNotBlank(condition.toString())){
			condition.append(" and ");
		}
		condition.append(" state = 'U' ");
		return query(condition.toString(), parameter);
	}
	
	/**
	 * 通过类型查询脚本信息集
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCmdValue[] queryCmdByType(String type) throws Exception{
		StringBuilder condition=new StringBuilder("");	
		Map parameter = new HashMap();
		if (StringUtils.isNotBlank(type)) {
			condition.append(IBOAIMonCmdValue.S_CmdType).append("=:type");
			parameter.put("type", type);
		}
		if (StringUtils.isNotBlank(condition.toString())){
			condition.append(" and ");
		}
		condition.append(" state = 'U' ");
		return query(condition.toString(), parameter);
	}

	public void saveOrUpdate(IBOAIMonCmdValue[] values) throws Exception {
		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				if (values[i].isNew()) {
					values[i].setCmdId(BOAIMonCmdEngine.getNewId().longValue());
				}
			}
		} else {
			throw new Exception("no data to saveOrUpdate...");
		}
		BOAIMonCmdEngine.saveBatch(values);
	}
	
	public long getCmdId() throws Exception {
		return BOAIMonCmdEngine.getNewId().longValue();
	}

	public void saveOrUpdate(IBOAIMonCmdValue value) throws Exception {
//		if (value.isNew()) {
//			value.setCmdId(BOAIMonCmdEngine.getNewId().longValue());
//		}
		BOAIMonCmdEngine.save(value);
	}
	
	/**
	 * 删除命令
	 * @param cmdId
	 * @throws Exception
	 */
	public void deleteCmd (long cmdId) throws Exception{
		IBOAIMonCmdValue value = BOAIMonCmdEngine.getBean(cmdId);
		if (null != value) {
//			value.delete();
			value.setState("E");
			BOAIMonCmdEngine.save(value);
		}
	}

}
