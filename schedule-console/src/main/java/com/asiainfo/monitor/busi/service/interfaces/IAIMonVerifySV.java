package com.asiainfo.monitor.busi.service.interfaces;  

import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonVerifyValue;
  /**
   * 校验规则
   *@Title:  
   *@Description:  
   *@Author:szh  
   *@Since:2015年8月10日  
   *@Version:1.1.0
   */
public interface IAIMonVerifySV {
	/**
	 * 
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
