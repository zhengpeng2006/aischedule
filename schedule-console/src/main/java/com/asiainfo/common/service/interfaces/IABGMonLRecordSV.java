package com.asiainfo.common.service.interfaces;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.asiainfo.common.abo.ivalues.IBOABGMonLRecordValue;

public interface IABGMonLRecordSV
{
    public IBOABGMonLRecordValue[] queryRecord(String infoId) throws RemoteException, Exception;

    /**
     * 获得监控图形数据
     *
     * @param infoId long[]
     * @param transformClass String
     * @param startDate Date
     * @param endDate Date
     * @return HashMap
     * @throws Exception
     */
    public List getMonLRecordImage(Object[] infoId, String groupId, String viewTpyeId, Date startDate, Date endDate) throws RemoteException,
            Exception;

    /**
     * 获得监控图形数据
     * @param infoId
     * @param layer String
     * @param durHour
     * @return List
     * @throws Exception
     */
    public List getMonLRecordImage(String infoId, String layer, int durHour) throws RemoteException, Exception;

    /**
     * 获得监控表格数据
     * @param layer
     * @param infocode
     * @param startDate Date
     * @param endDate Date
     * @return List
     * @throws Exception
     */
    public List getMonLRecordGrid() throws RemoteException, Exception;

    /**
     * 获取BatchId
     * @return
     * @throws RemoteException
     * @throws Exception
     */
    public long getBatchId() throws RemoteException, Exception;

    /**
     * 获取sysdate
     * @return
     * @throws RemoteException
     * @throws Exception
     */
    public Timestamp getSystemTime() throws RemoteException, Exception;

    /**
     * 批量保存或修改监控结果
     * 
     * @param values
     * @throws Exception
     */
    public void saveOrUpdate(IBOABGMonLRecordValue[] values) throws RemoteException, Exception;

    /**
     * 保存或修改监控结果
     * 
     * @param values
     * @throws Exception
     */
    public void saveOrUpdate(IBOABGMonLRecordValue value) throws RemoteException, Exception;

    /**
       * 根据任务标识、层、读取最新监控结果
       * @param infoId
       * @param layer
       * @return
       * @throws Exception
       */
    public List getLastMonLRecordByIds(Object[] infoIds, String layer) throws RemoteException, Exception;

}
