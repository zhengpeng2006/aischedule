package com.asiainfo.monitor.busi.panel.transform;

import org.apache.commons.lang.StringUtils;

import com.asiainfo.monitor.tools.exe.task.model.TaskRtnModel;
import com.asiainfo.monitor.tools.util.TypeConst;

public abstract class DefaultPanelTransform implements IPanelTransform {

	public abstract String transform(TaskRtnModel obj) throws Exception;

	/**
	 * 取指定位数的值,位数为竖线分割的位数
	 * @param fromStr
	 * @param deep
	 * @return
	 */
	public String getValueFromInfo(String fromStr,int deep){
		String[] tmpStr = StringUtils.split(fromStr, '|');
		if(tmpStr.length>deep){
			String[] tmp = StringUtils.split(tmpStr[deep], ':');
			if(tmp.length==2){
				return tmp[1];
			}
		}
		return "0";
	}
	
	public String getValueFromInfo(String infoValue) {
		String[] tmpStr = StringUtils.split(infoValue, TypeConst._INTERPRET_CHAR);
		if(tmpStr.length==2){
			return tmpStr[1];
		}
		return "";
	}
	
	public String getNameFromInfo(String infoValue) {
		String[] tmpStr = StringUtils.split(infoValue, TypeConst._INTERPRET_CHAR);
		if(tmpStr.length==2){
			return tmpStr[0];
		}
		return "";
	}
}
