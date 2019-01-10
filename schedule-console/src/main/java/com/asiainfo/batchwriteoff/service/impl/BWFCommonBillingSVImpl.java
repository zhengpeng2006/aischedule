package com.asiainfo.batchwriteoff.service.impl;


import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.batchwriteoff.dao.interfaces.IBWFCommonBillingDAO;
import com.asiainfo.batchwriteoff.ivalues.*;
import com.asiainfo.batchwriteoff.service.interfaces.IBWFCommonBillingSV;
import com.asiainfo.batchwriteoff.util.BillHelper;


/**
 * <p> @Description </p>
 * <p> @company Asiainfo-linkage (NJ) </p>
 * <p> @author 闫瑞国 </p>
 * <p> @Email yanrg@asiainfo-linkage.com </p>
 * <p> @createDate 2012-7-7 下午3:48:32 </p>
 * <p> @modifyDate </p>
 * <p> @version 1.0 </p>
 */
public class BWFCommonBillingSVImpl implements IBWFCommonBillingSV {


    @Override
    public IBOBsBatchWfParaValue[] getWfParaValues(String regionId, String paraCode) throws Exception {
        IBWFCommonBillingDAO commonDAO = (IBWFCommonBillingDAO) ServiceFactory.getService(IBWFCommonBillingDAO.class);
        return commonDAO.getWfParaValues(regionId, paraCode);
    }

    @Override
    public IBOBsBatchWfFlowMonValue getBatchWfFlowMonValue(long wfFlowId, String regionId, long billDay, long billingCycleId) throws Exception {
        IBWFCommonBillingDAO commonDAO = (IBWFCommonBillingDAO) ServiceFactory.getService(IBWFCommonBillingDAO.class);
        return commonDAO.getBatchWfFlowMonValue(wfFlowId, regionId, billDay, billingCycleId);
    }

    @Override
    public IQBOBsBatchWfFlowRelValue[] getBatchWfFlowRels(String regionId, long billDay) throws Exception {
        IBWFCommonBillingDAO commonDAO = (IBWFCommonBillingDAO) ServiceFactory.getService(IBWFCommonBillingDAO.class);
        return commonDAO.getBatchWfFlowRels(regionId, billDay);
    }

    @Override
    public IBOBsBatchWfPsMonValue[] getWfPsMonValues(String regionId, long billingCycleId, long billDay, long wfFlowId) throws Exception {
        IBWFCommonBillingDAO commonDAO = (IBWFCommonBillingDAO) ServiceFactory.getService(IBWFCommonBillingDAO.class);
        return commonDAO.getWfPsMonValues(regionId, billingCycleId, billDay, wfFlowId);
    }

    @Override
    public IBOAmBatchWfThreadMonValue[] getWfThreadMonValues(String regionId, long billingCycleId, long billDay, long wfFlowId, String wfProcessId) throws Exception {
        IBWFCommonBillingDAO commonDAO = (IBWFCommonBillingDAO) ServiceFactory.getService(IBWFCommonBillingDAO.class);
        return commonDAO.getWfThreadMonValues(regionId, billingCycleId, billDay, wfFlowId, wfProcessId);
    }

    @Override
    public void saveWfFlowMonValues(IBOBsBatchWfFlowMonValue[] wfFlowMonValues) throws Exception {
        IBWFCommonBillingDAO commonDAO = (IBWFCommonBillingDAO) ServiceFactory.getService(IBWFCommonBillingDAO.class);
        commonDAO.saveWfFlowMonValues(wfFlowMonValues);
    }

    @Override
    public void saveWfPsMonValues(IBOBsBatchWfPsMonValue[] wfPsMonValues) throws Exception {
        IBWFCommonBillingDAO commonDAO = (IBWFCommonBillingDAO) ServiceFactory.getService(IBWFCommonBillingDAO.class);
        commonDAO.saveWfPsMonValues(wfPsMonValues);
    }

    @Override
    public void saveWfThreadMonValues(IBOAmBatchWfThreadMonValue[] wfThreadMonValues) throws Exception {
        IBWFCommonBillingDAO commonDAO = (IBWFCommonBillingDAO) ServiceFactory.getService(IBWFCommonBillingDAO.class);
        commonDAO.saveWfThreadMonValues(wfThreadMonValues);
    }

    @Override
    public IBOBsBillingCycleValue[] getBillingCycle(String regionId, long billDay, String state, long billingCycleId) throws Exception {
        IBWFCommonBillingDAO commonDAO = (IBWFCommonBillingDAO) ServiceFactory.getService(IBWFCommonBillingDAO.class);
        return commonDAO.getBillingCycle(regionId, billDay, state, billingCycleId);
    }

    @Override
    public IBOBsBatchWfFlowValue[] getBatchWfFlow(String name, long wfFlowId, String regionId, long billDay) throws Exception {
        IBWFCommonBillingDAO commonDAO = (IBWFCommonBillingDAO) ServiceFactory.getService(IBWFCommonBillingDAO.class);
        return commonDAO.getBatchWfFlow(name, wfFlowId, regionId, billDay);
    }

    @Override
    public IBOBsBatchWfFlowMonValue[] getBatchWfFlowMonValues(String regionId, long billDay, long billingCycleId) throws Exception {
        IBWFCommonBillingDAO commonDAO = (IBWFCommonBillingDAO) ServiceFactory.getService(IBWFCommonBillingDAO.class);
        return commonDAO.getBatchWfFlowMonValues(regionId, billDay, billingCycleId);
    }

    @Override
    public IBOBsBatchWfPsValue getBatchWfPsValue(String wfProcessId) throws Exception {
        IBWFCommonBillingDAO commonDAO = (IBWFCommonBillingDAO) ServiceFactory.getService(IBWFCommonBillingDAO.class);
        return commonDAO.getBatchWfPsValue(wfProcessId);
    }

    @Override
    public IQBOAmBatchWfAllRegionInfoValue[] getAllBatchWfAllRegionInfoValues(int billingCycleId) throws Exception {
        IBWFCommonBillingDAO commonDAO = (IBWFCommonBillingDAO) ServiceFactory.getService(IBWFCommonBillingDAO.class);
        return commonDAO.getAllBatchWfAllRegionInfoValues(billingCycleId);
    }

    @Override
    public IBOBsBatchWfPsMonValue[] qryBatchWfPsMonValues(String regionId, int billingCycleId, int billDay, long wfFlowId) throws Exception {
        IBOBsBatchWfPsMonValue[] batchWfPsMonValues=getWfPsMonValues(regionId, billingCycleId, billDay, wfFlowId);
        if(batchWfPsMonValues!=null&&batchWfPsMonValues.length>0){
            for (IBOBsBatchWfPsMonValue batchWfPsMonValue : batchWfPsMonValues) {
                if (batchWfPsMonValue.getTotalCount() > 0) {
                    batchWfPsMonValue.initProperty("PERCENT", BillHelper.fmPercent(batchWfPsMonValue.getDealCount(), batchWfPsMonValue.getTotalCount()));
                }
            }
            return batchWfPsMonValues;
        }
        return new IBOBsBatchWfPsMonValue[0];
    }

    @Override
    public IBOAmBatchWfThreadMonValue[] qryBatchWfThreadMonValues(String regionId, int billingCycleId, int billDay, long wfFlowId, String wfProcessId) throws Exception {
        IBOAmBatchWfThreadMonValue[] batchWfThreadMonValues=getWfThreadMonValues(regionId, billingCycleId, billDay, wfFlowId, wfProcessId);
        if(batchWfThreadMonValues!=null&&batchWfThreadMonValues.length>0){
            for (IBOAmBatchWfThreadMonValue batchWfThreadMonValue : batchWfThreadMonValues) {
                if (batchWfThreadMonValue.getTotalCount() > 0) {
                    batchWfThreadMonValue.initProperty("PERCENT", BillHelper.fmPercent(batchWfThreadMonValue.getDealCount(), batchWfThreadMonValue.getTotalCount()));
                }
            }
            return batchWfThreadMonValues;
        }
        return new IBOAmBatchWfThreadMonValue[0];
    }


}
