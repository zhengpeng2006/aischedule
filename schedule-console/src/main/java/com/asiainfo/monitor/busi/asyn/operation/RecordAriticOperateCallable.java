package com.asiainfo.monitor.busi.asyn.operation;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public abstract class RecordAriticOperateCallable extends OperateCallable {

	protected List datas=new ArrayList();
	
	protected int deepCount=1;
	
	public RecordAriticOperateCallable(List datas,int deepCount){
		super();
		this.datas=datas;
		this.deepCount=deepCount;
	}
	
	/**
	 * 取值
	 * @param fromStr
	 * @param deep
	 * @return
	 */
	protected String getValueFromInfo(String src,int deep){
		String[] tmpStr = StringUtils.split(src, '|');
		if(tmpStr.length>=deep){
			String[] tmp = StringUtils.split(tmpStr[deep], ':');
			if(tmp.length==2){
				return tmp[1];
			}
		}
		return "0";
	}
	
}
