package com.asiainfo.monitor.exeframe.monitorItem.dao.interfaces;

import java.rmi.RemoteException;

import com.asiainfo.monitor.exeframe.monitorItem.ivalues.IBOAIMonBatchValue;

public interface IAIMonBatchDAO
{
    /**
     * 根据批次查询任务编码信息
     * @param batchId
     * @return
     * @throws RemoteException
     * @throws Exception
     */
    public IBOAIMonBatchValue[] getAllBatchInfo(long batchId) throws RemoteException, Exception;
    
    /**
     *获取全部信息 
     * @return
     * @throws RemoteException
     * @throws Exception
     */
    public IBOAIMonBatchValue[] getAllInfos() throws RemoteException, Exception;
    
}
