package com.asiainfo.monitor.busi.service.interfaces;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.List;

import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonCtRecordValue;


public interface IAIMonCtRecordSV {

	/**
     * 根据标识读取控制记录
     * @param domainId
     */
    public IBOAIMonCtRecordValue getCtRecordById(String id) throws RemoteException, Exception;
    
    
    /**
     * 获取被控制对象还有效的控制记录
     * @return
     * @throws Exception
     */
    public IBOAIMonCtRecordValue[] getEffectCtRecordByObjId(int ct_Type, int obj_Type, String obj_Id) throws RemoteException,Exception;
    
    /**
     * 根据操作类型读取监控状态记录
     * @param ct_type
     * @return
     * @throws Exception
     */
    public List getCtRecordByCtType(String ct_type) throws Exception;
    
    /**
     * 读取监控状态记录
     * @return
     * @throws RemoteException
     * @throws Exception
     */
    public String getAllControlInfo() throws RemoteException,Exception;

    /**
     * 保存控制记录
     * @param ctType
     * @param objType
     * @param objId
     * @param objName
     * @param duration
     * @throws RemoteException
     * @throws Exception
     */
    public void saveOrUpdate(int ctType, int objType, int objId, String objName, Timestamp invalidDate, int duration, String actor) throws RemoteException,Exception;
    
    /**
	 * 批量回写控制记录
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonCtRecordValue[] values) throws RemoteException,Exception;

	/**
	 * 回写控制记录
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonCtRecordValue value) throws RemoteException,Exception;
}
