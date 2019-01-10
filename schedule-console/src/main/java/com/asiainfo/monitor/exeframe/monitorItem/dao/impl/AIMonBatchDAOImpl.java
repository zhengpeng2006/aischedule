package com.asiainfo.monitor.exeframe.monitorItem.dao.impl;

import java.rmi.RemoteException;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.exeframe.monitorItem.bo.BOAIMonBatchBean;
import com.asiainfo.monitor.exeframe.monitorItem.bo.BOAIMonBatchEngine;
import com.asiainfo.monitor.exeframe.monitorItem.dao.interfaces.IAIMonBatchDAO;
import com.asiainfo.monitor.exeframe.monitorItem.ivalues.IBOAIMonBatchValue;
import com.asiainfo.monitor.exeframe.monitorItem.service.interfaces.IAIMonBatchSV;

public class AIMonBatchDAOImpl implements IAIMonBatchDAO
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
        return BOAIMonBatchEngine.getBeans(BOAIMonBatchBean.S_BatchId+"="+batchId,null);
    }
    
    public static void main(String[] args) throws RemoteException, Exception
    {
        IAIMonBatchSV sv=(IAIMonBatchSV)ServiceFactory.getService(IAIMonBatchSV.class);
        IBOAIMonBatchValue[] values=sv.getAllInfos();
        System.out.println(values);
    }
    
    /**
     * 获取全部信息
     */
    @Override
    public IBOAIMonBatchValue[] getAllInfos() throws RemoteException, Exception
    {
        return BOAIMonBatchEngine.getBeans(null,null);
    }

}
