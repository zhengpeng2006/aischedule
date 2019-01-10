package com.asiainfo.monitor.busi.common;

import com.ai.appframe2.complex.tab.split.function.IFunction;



public class LayerFunctionImpl implements IFunction {

	public LayerFunctionImpl() {
		super();
		// TODO 自动生成构造函数存根
	}

	public String convert(Object arg0) throws Exception {
		if (arg0==null)
			return "";
		return arg0.toString();
	}

}
