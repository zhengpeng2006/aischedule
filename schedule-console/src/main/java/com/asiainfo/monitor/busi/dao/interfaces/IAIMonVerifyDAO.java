package com.asiainfo.monitor.busi.dao.interfaces;  

import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonVerifyValue;
  
public interface IAIMonVerifyDAO {
	/**
	 * 根据校验类型获取校验信息
	 * @param verifyType
	 * @return
	 * @throws Exception  
	 * @Description:
	 */
	public IBOAIMonVerifyValue qryVerifyInfoByType(String verifyType) throws Exception;
	
	/**
	 * 获取所有的校验信息
	 * @return
	 * @throws Exception  
	 * @Description:
	 */
	public IBOAIMonVerifyValue[] qryAllVerifyInfos() throws Exception;
}
