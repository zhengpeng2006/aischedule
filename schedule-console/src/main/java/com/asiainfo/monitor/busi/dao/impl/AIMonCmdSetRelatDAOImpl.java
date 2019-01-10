package com.asiainfo.monitor.busi.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.asiainfo.monitor.busi.dao.interfaces.IAIMonCmdSetRelatDAO;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonCmdSetRelatEngine;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonCmdSetRelatValue;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class AIMonCmdSetRelatDAOImpl implements IAIMonCmdSetRelatDAO {

	public IBOAIMonCmdSetRelatValue[] query(String condition, Map parameter)
			throws Exception {
		return BOAIMonCmdSetRelatEngine.getBeans(condition, parameter);
	}

	/**
	 * 通过命令ID查询关系
	 * @param cmdId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCmdSetRelatValue[] getCmdSetRelatByCmdId(long cmdId) throws Exception {
		StringBuilder condition=new StringBuilder("");	
		condition.append(IBOAIMonCmdSetRelatValue.S_CmdId).append("=:cmdId").append(" and state = 'U' ");
		Map parameter = new HashMap();
		parameter.put("cmdId", new Long(cmdId));
		return query(condition.toString(), parameter);
	}

	public IBOAIMonCmdSetRelatValue getCmdSetRelatByIdAndCmdId(long cmdSetId,long cmdId) throws Exception {
		
		StringBuilder condition=new StringBuilder("");
		condition.append(IBOAIMonCmdSetRelatValue.S_CmdsetId).append(" = :setId ");
		condition.append(" AND ");
		condition.append(IBOAIMonCmdSetRelatValue.S_CmdId).append(" = :cmdId ");
		Map parameter = new HashMap();
		parameter.put("setId",cmdSetId);
		parameter.put("cmdId",cmdId);
		IBOAIMonCmdSetRelatValue[] relatValues=BOAIMonCmdSetRelatEngine.getBeans(condition.toString(), parameter);
		if (relatValues==null || relatValues.length>1 || relatValues.length<1)
			// 根据命令集标识和命令标识读取到的关联信息多于一条
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000212") + "["+cmdSetId+","+cmdId+"]");		
		return relatValues[0]; 
	}

	public IBOAIMonCmdSetRelatValue[] getCmdSetRelatByCmdSetIdOrderBySort(long cmdSetId) throws Exception {
		StringBuilder condition=new StringBuilder("");	
		condition.append(IBOAIMonCmdSetRelatValue.S_CmdsetId).append("=:setId").append(" AND STATE = 'U' ");
		condition.append(" ORDER BY "+IBOAIMonCmdSetRelatValue.S_CmdSeq);
		Map parameter = new HashMap();
		parameter.put("setId", new Long(cmdSetId));
		return query(condition.toString(), parameter);
	}

	public void saveOrUpdate(IBOAIMonCmdSetRelatValue value) throws Exception {
		BOAIMonCmdSetRelatEngine.save(value);
	}

	public void saveOrUpdate(IBOAIMonCmdSetRelatValue[] values) throws Exception {
		BOAIMonCmdSetRelatEngine.saveBatch(values);
	}

}
