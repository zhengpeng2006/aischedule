package com.asiainfo.monitor.busi.exe.task.dao.impl;


import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.asiainfo.common.operation.log.OperationLogUtils;
import com.asiainfo.common.utils.CommonConstants;
import com.asiainfo.monitor.busi.exe.task.bo.BOAIMonPInfoEngine;
import com.asiainfo.monitor.busi.exe.task.dao.interfaces.IAIMonPInfoDAO;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPInfoValue;

public class AIMonPInfoDAOImpl implements IAIMonPInfoDAO {

	/**
	 * 根据条件查询任务信息
	 * @param condition
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@Override
	public IBOAIMonPInfoValue[] query(String condition, Map parameter) throws Exception {
		return BOAIMonPInfoEngine.getBeans(condition, parameter);
	}
	/**
	 * 根据条件查询任务信息
	 * 分页
	 * @param cond
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Override
	public IBOAIMonPInfoValue[] query(String condition, Map parameter,Integer startNum, Integer endNum) throws Exception {
		return BOAIMonPInfoEngine.getBeans(null, condition, parameter, startNum, endNum, false);
	}
	
	/**
	 * 根据主键取得任务信息
	 * 
	 * @param infoId
	 * @return
	 * @throws Exception
	 */
	@Override
	public IBOAIMonPInfoValue getBeanById(long infoId) throws Exception {
		return BOAIMonPInfoEngine.getBean(infoId);	
	}
	
	/**
	 * 根据数据分片规则读取数据
	 * @param split
	 * @param mod
	 * @param value
	 * @return
	 * @throws Exception
	 */
	@Override
	public IBOAIMonPInfoValue[] getMonPInfoValuesBySplitAndMod(long split,long mod,long value) throws Exception{
		 StringBuffer sb = new StringBuffer();
		 HashMap parameter = new HashMap();
		 if (split>0){
			 sb.append(IBOAIMonPInfoValue.S_SplitRuleId+" =:split");
			 parameter.put("split",new Long(split));
		 }
		 
		 if (mod > 1 && value >= 0){
			 if (StringUtils.isNotBlank(sb.toString())){
				 sb.append(" AND ");
			 }
			 sb.append(" mod("+IBOAIMonPInfoValue.S_InfoId+",:mod) = :value");
			 parameter.put("mod",new Long(mod));
			 parameter.put("value",new Long(value));
		 }
		 if (StringUtils.isBlank(sb.toString())){
			 sb.append(" STATE='U'");
		 }else{
			 sb.append(" AND STATE='U' ");
		 }
		 return BOAIMonPInfoEngine.getBeans(sb.toString(),parameter);
	}
	
	/**
	 * 根据开始、结束点读取任务
	 */
	@Override
	public IBOAIMonPInfoValue[] getTaskInfoByName(String taskName,String state, Integer startNum, Integer endNum) throws Exception {
		StringBuilder condition = new StringBuilder("");
		Map parameter = new HashMap();
		
		if (StringUtils.isNotBlank(taskName)) {
			condition.append(IBOAIMonPInfoValue.S_Name).append(" like :taskName ");
			parameter.put("taskName", "%" + taskName + "%");
		}
		if (StringUtils.isNotBlank(state) && !state.toUpperCase().equals("A")){
			if (StringUtils.trim(condition.toString()).length()>=1){
				condition.append(" AND ");
			}
			condition.append(IBOAIMonPInfoValue.S_State).append(" = :state");
			parameter.put("state",state);
		}
		IBOAIMonPInfoValue[] values = query(condition.toString(), parameter, startNum, endNum);
		return values;
	}
	
	/**
	 * 读取任务总数
	 * @param taskName
	 * @param state
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getTaskInfoCount(String taskName,String state) throws Exception {
		StringBuilder condition = new StringBuilder("");
		Map parameter = new HashMap();
		if (StringUtils.isNotBlank(taskName)) {
			condition.append(IBOAIMonPInfoValue.S_Name).append(" like :taskName ");
			parameter.put("taskName", "%" + taskName + "%");
		}
		if (StringUtils.isNotBlank(state) && !state.toUpperCase().equals("A")){
			if (StringUtils.trim(condition.toString()).length()>=1){
				condition.append(" AND ");
			}
			condition.append(IBOAIMonPInfoValue.S_State).append(" = :state");
			parameter.put("state",state);
		}
		return BOAIMonPInfoEngine.getBeansCount(condition.toString(), parameter);
	}
	
	/**
	 * 根据解析分组读取任务数
	 * @param resolveId
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getTaskInfoCountByResolveId(long resolveId) throws Exception{
		StringBuilder sb=new StringBuilder("");
		Map parameter=new HashMap();
		sb.append(IBOAIMonPInfoValue.S_ResolveId).append(" =:resolveId");
		parameter.put("resolveId",resolveId);
		return BOAIMonPInfoEngine.getBeansCount(sb.toString(),parameter);
	}
	
	/**
	 * 
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	@Override
	public IBOAIMonPInfoValue[] getTaskInfoByGroupId(String groupId) throws Exception{
		StringBuilder sb=new StringBuilder("");
		Map parameter=new HashMap();
		sb.append(IBOAIMonPInfoValue.S_GroupId).append(" =:groupId");
		parameter.put("groupId",groupId);
		return BOAIMonPInfoEngine.getBeans(sb.toString(),parameter);
	}
	
	/**
	 * 
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	@Override
	public IBOAIMonPInfoValue[] queryTaskInfoByCond(String groupId,int startIndex,int endIndex) throws Exception{
		StringBuilder sb=new StringBuilder("");
		Map parameter=new HashMap();
		sb.append(IBOAIMonPInfoValue.S_GroupId).append(" =:groupId");
		sb.append(" and ").append(IBOAIMonPInfoValue.S_State).append(" = 'U'");
		parameter.put("groupId",groupId);
		return query(sb.toString(), parameter, startIndex, endIndex);
	}
	
	/**
	 * 
	 * @param groupID
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getTaskInfoCountByCond(String groupId) throws Exception {
		StringBuilder sb=new StringBuilder("");
		Map parameter=new HashMap();
		sb.append(IBOAIMonPInfoValue.S_GroupId).append(" =:groupId");
		sb.append(" and ").append(IBOAIMonPInfoValue.S_State).append(" = 'U'");
		parameter.put("groupId",groupId);
		return BOAIMonPInfoEngine.getBeansCount(sb.toString(),parameter);
	}
	
	/**
	 * 根据分组编号和任务名查找任务
	 * @param groupId
	 * @param taskName
	 * @return
	 * @throws Exception
	 */
	@Override
	public IBOAIMonPInfoValue[] queryTaskInfoByGrpAndInfoName(String groupId,String taskName,int startIndex,int endIndex) throws Exception{
		StringBuilder sb=new StringBuilder("");
		Map parameter=new HashMap();
		sb.append(IBOAIMonPInfoValue.S_GroupId).append(" =:groupId");
		parameter.put("groupId",groupId);
		if (StringUtils.isNotBlank(taskName)) {
			sb.append(" and ").append(IBOAIMonPInfoValue.S_Name).append(" like :taskName");
			parameter.put("taskName", "%" + taskName + "%");
		}
		sb.append(" and ").append(IBOAIMonPInfoValue.S_State).append(" = 'U'");
		return query(sb.toString(), parameter, startIndex, endIndex);
	}
	
	/**
	 * 根据分组编号和任务名查找任务个数
	 * @param groupId
	 * @param taskName
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getTaskInfoCountByGrpAndInfoName(String groupId,String taskName) throws Exception {
		StringBuilder sb=new StringBuilder("");
		Map parameter=new HashMap();
		sb.append(IBOAIMonPInfoValue.S_GroupId).append(" =:groupId");
		parameter.put("groupId",groupId);
		if (StringUtils.isNotBlank(taskName)) {
			sb.append(" and ").append(IBOAIMonPInfoValue.S_Name).append(" like :taskName");
			parameter.put("taskName", "%" + taskName + "%");
		}
		sb.append(" and ").append(IBOAIMonPInfoValue.S_State).append(" = 'U'");
		return BOAIMonPInfoEngine.getBeansCount(sb.toString(),parameter);
	}

	/**
	 * 批量保存或修改任务信息
	 * 
	 * @param values
	 * @throws Exception
	 */
	@Override
	public void saveOrUpdate(IBOAIMonPInfoValue[] values) throws Exception {
		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				if (values[i].isNew()) {
					values[i].setInfoId(BOAIMonPInfoEngine.getNewId().longValue());
					values[i].setState("U");
				}
			}
		} else {
			throw new Exception("");
		}
		BOAIMonPInfoEngine.saveBatch(values);
	}
	
	/**
	 * 保存或修改任务信息
	 * 
	 * @param value
	 * @throws Exception
	 */
	@Override
	public void saveOrUpdate(IBOAIMonPInfoValue value ) throws Exception {
		if (value.isNew()) {
			value.setInfoId(BOAIMonPInfoEngine.getNewId().longValue());
			value.setState("U");
				OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG, 
						CommonConstants.OPERATE_TYPE_ADD,
						CommonConstants.OPERTATE_OBJECT_MONITOR_TASK, 
						value.getName(), null);
		}else{
			OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG, 
					CommonConstants.OPERATE_TYPE_MODIFY,
					CommonConstants.OPERTATE_OBJECT_MONITOR_TASK, 
					value.getName()+":"+value.getInfoId(), null);
		}
		BOAIMonPInfoEngine.save(value);
	}
	
	/**
	 * 删除任务信息
	 * 
	 * @param infoId
	 * @throws Exception
	 */
	@Override
	public void delete(long infoId) throws Exception {
		IBOAIMonPInfoValue value = BOAIMonPInfoEngine.getBean(infoId);
		if (null != value) {
			value.delete();
//			value.setState("E");
			BOAIMonPInfoEngine.save(value);
		}
	}
	
	/**
	 * 通过组查询组下面的任务信息
	 * @param resolveId
	 * @throws Exception
	 */
	@Override
	public IBOAIMonPInfoValue[] getTaskInfoByResolveId(String resolveId)throws Exception{
		IBOAIMonPInfoValue[] ret= null;
		try {
			HashMap param=new HashMap();
			StringBuilder sb = new StringBuilder(" 1=1 ");
			if(resolveId!=null&&resolveId.length()>0){
				sb.append(" and RESOLVE_ID=:RESOLVE_ID");
				param.put("RESOLVE_ID", resolveId);
			}
			ret=BOAIMonPInfoEngine.getBeans(sb.toString(), param);			
		} catch (Exception e) {
			throw e;		
		}
		return ret;
	}
	
	/**
	 * 根据对象类型以及对象标识获取任务
	 * @param mType
	 * @param typeId
	 * @return
	 * @throws Exception
	 */
	@Override
	public IBOAIMonPInfoValue[] getTaskByType(String mType, long typeId)throws Exception{
		StringBuilder sb=new StringBuilder("");
		Map parameter=new HashMap();
		sb.append(IBOAIMonPInfoValue.S_MType).append(" =:mType");
		parameter.put("mType",mType);
		sb.append(" and ").append(IBOAIMonPInfoValue.S_TypeId).append(" =:mTypeId");
		parameter.put("mTypeId",typeId);
		sb.append(" and ").append(IBOAIMonPInfoValue.S_State).append(" =:mState");
		parameter.put("mState","U");
		return BOAIMonPInfoEngine.getBeans(sb.toString(),parameter);
	}

    @Override
    public IBOAIMonPInfoValue[] getPInfoByLocalIp() throws Exception
    {
        StringBuffer sql = new StringBuffer();
        InetAddress netAddress = InetAddress.getLocalHost();
        String ip = "";
        if(null != netAddress) {
            ip = netAddress.getHostAddress();
        }
        Map<String, String> parameter = new HashMap<String, String>();
        sql.append("select f.* from ai_mon_physic_host h,ai_mon_p_info f  ").append("where h.host_id=f.host_id and f.state='U'");
        if(StringUtils.isNotBlank(ip)) {
            sql.append(" and ").append("h.host_ip='").append(ip).append("'");
        }
        IBOAIMonPInfoValue[] values = BOAIMonPInfoEngine.getBeansFromSql(sql.toString(), parameter);
        return values;
    }
	
}
