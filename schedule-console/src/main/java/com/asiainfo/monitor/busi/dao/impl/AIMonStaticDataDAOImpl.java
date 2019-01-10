package com.asiainfo.monitor.busi.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.asiainfo.monitor.busi.dao.interfaces.IAIMonStaticDataDAO;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonStaticDataEngine;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonStaticDataValue;

public class AIMonStaticDataDAOImpl implements IAIMonStaticDataDAO {

	/**
	 * 通过查询条件查询
	 * @param cond
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonStaticDataValue[] query(String cond, Map parameter)
			throws Exception {
		return BOAIMonStaticDataEngine.getBeans(cond, parameter);
	}

	/**
	 * 通过CodeType查询静态数据集
	 * @param codeType
	 * @return 
	 * @throws Exception
	 */
	public IBOAIMonStaticDataValue[] queryByCodeType(String codeType)
			throws Exception {
		StringBuilder condition = new StringBuilder();
		condition.append(IBOAIMonStaticDataValue.S_CodeType).append("=:codeType").append(" and state = 'U' ");
		Map parameter = new HashMap();
		parameter.put("codeType", codeType);
		return BOAIMonStaticDataEngine.getBeans(condition.toString(), parameter);
	}
	
	/**
	 * 通过ExternCodeType查询静态数据集
	 * @param externCodeType
	 * @return 
	 * @throws Exception
	 */
	public IBOAIMonStaticDataValue[] queryByECT(String externCodeType)
			throws Exception {
		StringBuilder condition = new StringBuilder();
		condition.append(IBOAIMonStaticDataValue.S_ExternCodeType).append("=:externCodeType").append(" and state = 'U' ");
		Map parameter = new HashMap();
		parameter.put("externCodeType", externCodeType);
		return BOAIMonStaticDataEngine.getBeans(condition.toString(), parameter);
	}

	/**
	 * 通过CodeType和CodeValue查询具体的静态数据条目
	 * @param codeType
	 * @param codeValue
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonStaticDataValue queryByCodeTypeAndValue(String codeType,
			String codeValue) throws Exception {
		return BOAIMonStaticDataEngine.getBean(codeValue,codeType);
	}

	/**
	 * 保存静态数据
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonStaticDataValue value) throws Exception{		
		BOAIMonStaticDataEngine.save(value);
	}
	
	/**
	 * 批量保存静态数据
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonStaticDataValue[] values) throws Exception{
		BOAIMonStaticDataEngine.save(values);
	}
	
	/**
     * 通过静态表查询配置的任务信息
     * @param codeType（taskType）
     * @param codeValue（taskCode）
     * @return
     */
	public IBOAIMonStaticDataValue[] queryByEct(String externCodeType)
			throws Exception {
		//TODO
		return null;
	}

    @Override
    public IBOAIMonStaticDataValue[] qryTaskInfos(String codeType, String codeValue,String taskCode1) throws Exception
    {   
        StringBuilder condition = new StringBuilder("");
        condition.append(" state = 'U' ");
        Map parameter = new HashMap();
        if(StringUtils.isNotBlank(codeType)) {
            condition.append(" and ");
            condition.append(IBOAIMonStaticDataValue.S_CodeType).append(" = :codeType");
            parameter.put("codeType", codeType);
        }
        //当codeValue和taskCode1都不为空时
        if(StringUtils.isNotBlank(codeValue)&&StringUtils.isNotBlank(taskCode1)) {
            condition.append(" and ");
            condition.append(IBOAIMonStaticDataValue.S_CodeValue).append(" = :codeValue");
            parameter.put("codeValue", codeValue);
            condition.append(" or ");
            condition.append(IBOAIMonStaticDataValue.S_CodeValue).append(" like :taskCode1");
            parameter.put("taskCode1", "%"+taskCode1+"%");
        }
        //当codeValue不为空，taskCode1为空时
        if(StringUtils.isNotBlank(codeValue)&&StringUtils.isBlank(taskCode1)) {
            condition.append(" and ");
            condition.append(IBOAIMonStaticDataValue.S_CodeValue).append(" = :codeValue");
            parameter.put("codeValue", codeValue);
        }
        //当codeValue为空，taskCode1不为空时
        if(StringUtils.isBlank(codeValue)&&StringUtils.isNotBlank(taskCode1)) {
            condition.append(" and ");
            condition.append(IBOAIMonStaticDataValue.S_CodeValue).append(" like :taskCode1");
            parameter.put("taskCode1", "%"+taskCode1+"%");
        }
        
        IBOAIMonStaticDataValue[] values = BOAIMonStaticDataEngine.getBeans(condition.toString(), parameter);
        return values;
    }
	
}
