package com.asiainfo.monitor.busi.panel.process.interfaces;

import com.asiainfo.monitor.tools.common.interfaces.ITaskRtn;

public interface IProcessFunction {

	public void process(ITaskRtn record) throws Exception;
}
