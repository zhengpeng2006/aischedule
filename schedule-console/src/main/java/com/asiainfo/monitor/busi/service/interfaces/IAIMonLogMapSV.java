package com.asiainfo.monitor.busi.service.interfaces;

import java.rmi.RemoteException;

import com.asiainfo.monitor.exeframe.config.bo.BOAIMonLogBean;




public interface IAIMonLogMapSV {


	public void recordLog(BOAIMonLogBean qlogBean) throws RemoteException,Exception;

}
