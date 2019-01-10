package com.asiainfo.monitor.tools.model.interfaces;

import com.asiainfo.monitor.tools.common.interfaces.ITaskRtn;

public interface ITaskRtnTransform {

	/**
	 * 设置返回值
	 * @param taskRtn
	 * @throws Exception
	 */
	public void transform(ITaskRtn taskRtn) throws Exception;
	
}
