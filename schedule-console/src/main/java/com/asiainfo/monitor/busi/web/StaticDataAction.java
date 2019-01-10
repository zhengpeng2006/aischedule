package com.asiainfo.monitor.busi.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ai.appframe2.service.ServiceFactory;
import com.ai.appframe2.web.action.BaseAction;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonStaticDataSV;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonStaticDataValue;


public class StaticDataAction extends BaseAction {

	/**
	 * 根据类型获取静态数据集的CodeValue和CodeName
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public List getStaticDataByType(String type) throws Exception{
		if (StringUtils.isBlank(type))
			return null;
		List staticData=null;
		IAIMonStaticDataSV staticDataSV=(IAIMonStaticDataSV)ServiceFactory.getService(IAIMonStaticDataSV.class);
		IBOAIMonStaticDataValue[] result=staticDataSV.queryByCodeType(type);
		if (result!=null){
			staticData=new ArrayList(result.length);
			for (int i=0;i<result.length;i++){
				Map item=new HashMap();
				item.put("KEY",result[i].getCodeValue());
				item.put("VALUE",result[i].getCodeName());
				staticData.add(item);
			}
		}
		return staticData;
	}
	
	/**
	 * 根据类型获取静态数据集的CodeValue和CodeName
	 * @param types
	 * @return
	 * @throws Exception
	 */
	public List getStaticDataByType(Object[] typeObjs) throws Exception{
		if (typeObjs == null || typeObjs.length == 0)
			return null;
		List staticData=null;
		String[] types = new String[typeObjs.length];
		for(int i=0;i<typeObjs.length;i++)
			types[i] = typeObjs[i].toString();
		IAIMonStaticDataSV staticDataSV=(IAIMonStaticDataSV)ServiceFactory.getService(IAIMonStaticDataSV.class);
		IBOAIMonStaticDataValue[] result=staticDataSV.queryByCodeType(types);
		if (result!=null){
			staticData=new ArrayList(result.length);
			for (int i=0;i<result.length;i++){
				Map item=new HashMap();
				item.put("itemValue",result[i].getCodeValue());
				item.put("itemName",result[i].getCodeName());
				item.put("itemType",result[i].getCodeType());
				item.put("itemAlias",result[i].getCodeTypeAlias());
				item.put("itemExtern",result[i].getExternCodeType());
				staticData.add(item);
			}
		}
		return staticData;
	}
	
	/**
	 * 根据类型获取静态数据集
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonStaticDataValue[] getStaticData(String type) throws Exception {
		if (StringUtils.isBlank(type))
			return null;
		IAIMonStaticDataSV staticDataSV=(IAIMonStaticDataSV)ServiceFactory.getService(IAIMonStaticDataSV.class);
		IBOAIMonStaticDataValue[] result=staticDataSV.queryByCodeType(type);
		return result;
	}

}
