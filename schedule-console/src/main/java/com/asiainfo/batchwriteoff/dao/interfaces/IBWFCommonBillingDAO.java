package com.asiainfo.batchwriteoff.dao.interfaces;

import com.asiainfo.batchwriteoff.ivalues.*;

/**
 * <p> @Description </p>
 * <p> @company Asiainfo-linkage (NJ) </p>
 * <p> @author 闫瑞国 </p>
 * <p> @Email yanrg@asiainfo-linkage.com </p>
 * <p> @createDate 2012-7-7 下午3:48:51 </p>
 * <p> @modifyDate </p>
 * <p> @version 1.0 </p>
 */
public interface IBWFCommonBillingDAO {

    /**
     * 查询账期
     * <p/>
     * <p/>
     * Modification History:
     * Date         		    Author          	 Version
     * -------------------------------------------------------*
     * 2012-7-7 下午3:56:13       yanrg                 v1.0.0
     */
    public IBOBsBillingCycleValue[] getBillingCycle(String regionId, long billDay, String state, long billingCycleId) throws Exception;


    public IBOBsBatchWfFlowValue[] getBatchWfFlow(String name, long wfFlowId, String regionId, long billDay) throws Exception;


    public IBOBsBatchWfFlowMonValue getBatchWfFlowMonValue(long wfFlowId, String regionId, long billDay, long billingCycleId) throws Exception;

    public IBOBsBatchWfFlowMonValue[] getBatchWfFlowMonValues(String regionId, long billDay, long billingCycleId) throws Exception;


    public void saveWfFlowMonValues(IBOBsBatchWfFlowMonValue[] wfFlowMonValues) throws Exception;


    public IBOBsBatchWfPsValue getBatchWfPsValue(String wfProcessId) throws Exception;

    public IBOAmBatchWfThreadMonValue[] getWfThreadMonValues(String regionId, long billingCycleId, long billDay, long wfFlowId, String wfProcessId) throws Exception;

    public void saveWfThreadMonValues(IBOAmBatchWfThreadMonValue[] wfThreadMonValues) throws Exception;

    public IBOBsBatchWfPsMonValue[] getWfPsMonValues(String regionId, long billingCycleId, long billDay, long wfFlowId) throws Exception;

    public void saveWfPsMonValues(IBOBsBatchWfPsMonValue[] wfPsMonValues) throws Exception;

    public IBOBsBatchWfParaValue[] getWfParaValues(String regionId, String paraCode) throws Exception;

    public IQBOBsBatchWfFlowRelValue[] getBatchWfFlowRels(String regionId, long billDay) throws Exception;

    public IQBOAmBatchWfAllRegionInfoValue[] getAllBatchWfAllRegionInfoValues(int billingCycleId) throws Exception;
}
