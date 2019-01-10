package com.asiainfo.monitor.exeframe.monitorItem.service.impl;

import java.rmi.RemoteException;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.exeframe.monitorItem.dao.interfaces.IAIMonBatchDAO;
import com.asiainfo.monitor.exeframe.monitorItem.ivalues.IBOAIMonBatchValue;
import com.asiainfo.monitor.exeframe.monitorItem.service.interfaces.IAIMonBatchSV;

public class AIMonBatchSVImpl implements IAIMonBatchSV
{
    /**
     * 根据批次查询任务编码信息
     * @param batchId
     * @return
     * @throws RemoteException
     * @throws Exception
     */
    @Override
    public IBOAIMonBatchValue[] getAllBatchInfo(long batchId) throws RemoteException, Exception
    {   
        IAIMonBatchDAO dao=(IAIMonBatchDAO)ServiceFactory.getService(IAIMonBatchDAO.class);
        return dao.getAllBatchInfo(batchId);
    }
    
    /**
     *获取全部信息 
     * @return
     * @throws RemoteException
     * @throws Exception
     */
    @Override
    public IBOAIMonBatchValue[] getAllInfos() throws RemoteException, Exception
    {
        IAIMonBatchDAO dao=(IAIMonBatchDAO)ServiceFactory.getService(IAIMonBatchDAO.class);
        return dao.getAllInfos();
    }

}
