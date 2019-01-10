package com.asiainfo.common.service.interfaces;

import java.rmi.RemoteException;

import com.asiainfo.common.abo.ivalues.IBOABGMonWTriggerValue;

public interface IABGMonWTriggerSV
{
    /**
     * 根据条件查询告警记录
     * 
     * @param sqlCondition
     * @param startIndex
     * @param endIndex
     * @return
     * @throws Exception
     */
    public IBOABGMonWTriggerValue[] getMonWTriggerByRecordId(String recordId, Integer startIndex, Integer endIndex) throws RemoteException, Exception;

    /**
     * 批量保存或修改警告记录
     * 
     * @param values
     * @throws Exception
     */
    public void saveOrUpdate(IBOABGMonWTriggerValue[] values) throws RemoteException, Exception;

    /**
     * 保存或修改警告记录
     * 
     * @param value
     * @throws Exception
     */
    public void saveOrUpdate(IBOABGMonWTriggerValue value) throws RemoteException, Exception;

    /**
     * 保存告警并处理之前的告警，将X分钟之前的同任务告警转成历史
     * @param values
     * @throws RemoteException
     * @throws Exception
     */
    public void saveAndProcess(IBOABGMonWTriggerValue[] values) throws RemoteException, Exception;

    /**
     * 根据层类型，告警级别和起止日期查询结果数量
     * @param layerType
     * @param warnLevel
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     */
    public int count(String layerType, String warnLevel, String startDate, String endDate) throws RemoteException, Exception;

    /**
     * 根据层类型，告警级别和起止日期查询结果
     * @param layerType
     * @param warnLevel
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     */
    public IBOABGMonWTriggerValue[] getAllByCond(String layerType, String warnLevel, String startDate, String endDate) throws RemoteException,
            Exception;

    /**
     * 根据层类型，告警级别和起止日期查询结果(分页查询)
     * @param layerType
     * @param warnLevel
     * @param startDate
     * @param endDate
     * @param startIndex
     * @param endIndex
     * @return
     * @throws Exception
     */
    public IBOABGMonWTriggerValue[] getAllByCond(String layerType, String warnLevel, String startDate, String endDate, int startIndex, int endIndex)
            throws RemoteException, Exception;

    public int countByInfoIdCond(String infoId, String warnLevel, String startDate, String endDate) throws RemoteException, Exception;

    public IBOABGMonWTriggerValue[] getTriggerValuesByInfoIdCond(String infoId, String warnLevel, String startDate, String endDate,
                                                                 Integer startIndex, Integer endIndex) throws RemoteException, Exception;

    /**
     * 修复告警记录,将告警记录转移到历史表，历史表中失效日期为修复日期，状态为F
     * @param triggerId
     * @param remarks
     * @throws Exception
     */
    public void repairWTrigger(Object[] triggerId, String remarks) throws RemoteException, Exception;

    /**
     * 通过ip，信息名称查询告警信息
     * @param hostName 
     * @param qryEndDate 
     * @param qryBeginDate 
     * 
     * @param request
     * @throws Exception
     */
    public IBOABGMonWTriggerValue[] getTriggerValuesByIpInfoName(String ip, String infoName, String hostName, String beginDate, String endDate, int start, int end)
            throws RemoteException,
            Exception;

    /**
     * 查询结果行数
     * @param hostName 
     * @param endDate 
     * @param beginDate 
     * 
     * @param request
     * @throws Exception
     */
    public int getCntByCond(String ip, String infoName, String hostName, String beginDate, String endDate) throws RemoteException, Exception;
}
