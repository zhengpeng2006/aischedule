package com.asiainfo.batchwriteoff.dao.impl;

import com.ai.appframe2.util.criteria.Criteria;
import com.asiainfo.batchwriteoff.bo.*;
import com.asiainfo.batchwriteoff.dao.interfaces.IBWFCommonBillingDAO;
import com.asiainfo.batchwriteoff.ivalues.*;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;


/**
 * <p> @Description </p>
 * <p> @company Asiainfo-linkage (NJ) </p>
 * <p> @author 闫瑞国 </p>
 * <p> @Email yanrg@asiainfo-linkage.com </p>
 * <p> @createDate 2012-7-7 下午3:49:11 </p>
 * <p> @modifyDate </p>
 * <p> @version 1.0 </p>
 */
public class BWFCommonBillingDAOImpl implements IBWFCommonBillingDAO {

    public IBOBsBatchWfParaValue[] getWfParaValues(String regionId, String paraCode) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(" 1=1 ");
        Map map = new HashMap();

        if (StringUtils.isNotBlank(regionId)) {
            sb.append(" AND ").append(IBOBsBatchWfParaValue.S_RegionId).append(" =:regionId ");
            map.put("regionId", regionId);
        }

        if (StringUtils.isNotBlank(paraCode)) {
            sb.append(" AND ").append(IBOBsBatchWfParaValue.S_ParaCode).append(" =:paraCode ");
            map.put("paraCode", paraCode);
        }

        IBOBsBatchWfParaValue[] retValues = BOBsBatchWfParaEngine.getBeans(sb.toString(), map);

        return retValues;
    }

    @Override
    public IQBOBsBatchWfFlowRelValue[] getBatchWfFlowRels(String regionId, long billDay) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(" 1=1 ");
        Map map = new HashMap();

        if(StringUtils.isNotBlank(regionId)){
            sb.append(" AND ").append(IQBOBsBatchWfFlowRelValue.S_RegionId).append(" =:regionId ");
            map.put("regionId", regionId);

            sb.append(" AND ").append(IQBOBsBatchWfFlowRelValue.S_RelRegionId).append(" =:RelRegionId ");
            map.put("RelRegionId", regionId);
        }

        if(billDay>0){
            sb.append(" AND ").append(IQBOBsBatchWfFlowRelValue.S_BillDay).append(" =:billDay ");
            map.put("billDay", Long.valueOf(billDay));
        }

        IQBOBsBatchWfFlowRelValue[] retValues = QBOBsBatchWfFlowRelEngine.getBeans(sb.toString(), map);

        if(StringUtils.isNotBlank(regionId) && (retValues==null || retValues.length<1)){
            map.put("regionId", "X");
            map.put("RelRegionId", "X");

            retValues = QBOBsBatchWfFlowRelEngine.getBeans(sb.toString(), map);
        }

        return retValues;
    }

    @Override
    public IQBOAmBatchWfAllRegionInfoValue[] getAllBatchWfAllRegionInfoValues(int billingCycleId) throws Exception {
            StringBuilder sb = new StringBuilder();
            sb.append(" 1=1 ");
            Map map = new HashMap();

            if(billingCycleId>0){
                sb.append(" AND ").append("BILLING_CYCLE_ID").append(" =:billingCycleId ");
                map.put("billingCycleId", Long.valueOf(billingCycleId));

            }
        return QBOAmBatchWfAllRegionInfoEngine.getBeans(sb.toString(), map);
    }

    public IBOBsBatchWfPsMonValue[] getWfPsMonValues(String regionId, long billingCycleId, long billDay, long wfFlowId) throws Exception {

        StringBuilder sb = new StringBuilder();
        sb.append(" 1=1 ");
        Map map = new HashMap();

        sb.append(" AND ").append(IBOBsBatchWfPsMonValue.S_RegionId).append(" =:regionId ");
        map.put("regionId", regionId);

        sb.append(" AND ").append(IBOBsBatchWfPsMonValue.S_BillingCycleId).append(" =:billingCycleId ");
        map.put("billingCycleId", Long.valueOf(billingCycleId));

        sb.append(" AND ").append(IBOBsBatchWfPsMonValue.S_BillDay).append(" =:billDay ");
        map.put("billDay", Long.valueOf(billDay));

        sb.append(" AND ").append(IBOBsBatchWfPsMonValue.S_WfFlowId).append(" =:wfFlowId ");
        map.put("wfFlowId", Long.valueOf(wfFlowId));

        IBOBsBatchWfPsMonValue[] retValues = BOBsBatchWfPsMonEngine.getBeans(sb.toString(), map);

        return retValues;

    }


    public IBOAmBatchWfThreadMonValue[] getWfThreadMonValues(String regionId, long billingCycleId, long billDay,
                                                             long wfFlowId, String wfProcessId) throws Exception {

        Criteria sql = new Criteria();
        sql.addEqual(IBOAmBatchWfThreadMonValue.S_RegionId, regionId);
        sql.addEqual(IBOAmBatchWfThreadMonValue.S_BillingCycleId, Long.valueOf(billingCycleId));
        sql.addEqual(IBOAmBatchWfThreadMonValue.S_BillDay, Long.valueOf(billDay));
        sql.addEqual(IBOAmBatchWfThreadMonValue.S_WfFlowId, Long.valueOf(wfFlowId));

        if (StringUtils.isNotBlank(wfProcessId)) {
            String temp = wfProcessId.substring(0, wfProcessId.length() - 2) + "%";

            sql.addLIKE(IBOAmBatchWfThreadMonValue.S_WfProcessId, temp);
        }


        IBOAmBatchWfThreadMonValue[] retValues = BOAmBatchWfThreadMonEngine.getBeans(sql);

        return retValues;
    }

    public void saveWfFlowMonValues(IBOBsBatchWfFlowMonValue[] wfFlowMonValues) throws Exception {
        if (wfFlowMonValues != null && wfFlowMonValues.length > 0) {
            BOBsBatchWfFlowMonEngine.saveBatch(wfFlowMonValues);
        }
    }

    public void saveWfPsMonValues(IBOBsBatchWfPsMonValue[] wfPsMonValues) throws Exception {
        if (wfPsMonValues != null && wfPsMonValues.length > 0) {
            BOBsBatchWfPsMonEngine.saveBatch(wfPsMonValues);
        }
    }


    public void saveWfThreadMonValues(IBOAmBatchWfThreadMonValue[] wfThreadMonValues) throws Exception {
        if (wfThreadMonValues != null && wfThreadMonValues.length > 0) {
            BOAmBatchWfThreadMonEngine.saveBatch(wfThreadMonValues);
        }
    }


    public IBOBsBillingCycleValue[] getBillingCycle(String regionId, long billDay, String state, long billingCycleId) throws Exception {

        StringBuilder sb = new StringBuilder();
        sb.append(" 1=1 ");
        Map map = new HashMap();

        if (StringUtils.isNotBlank(regionId)) {
            sb.append(" AND ").append(IBOBsBillingCycleValue.S_RegionId).append(" =:regionId ");
            map.put("regionId", regionId);
        }

        if (StringUtils.isNotBlank(state)) {
            sb.append(" AND ").append(IBOBsBillingCycleValue.S_State).append(" =:state ");
            map.put("state", state);
        }

        if (billingCycleId > 0) {
            sb.append(" AND ").append(IBOBsBillingCycleValue.S_BillingCycleId).append(" =:billingCycleId ");
            map.put("billingCycleId", Long.valueOf(billingCycleId));
        }
        sb.append(" ORDER BY ").append(IBOBsBillingCycleValue.S_BillingCycleId);
        return BOBsBillingCycleEngine.getBeans(sb.toString(), map);
    }


    public IBOBsBatchWfFlowValue[] getBatchWfFlow(String name, long wfFlowId, String regionId, long billDay) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(" 1=1 ");
        Map map = new HashMap();

        if (StringUtils.isNotBlank(regionId)) {
            sb.append(" AND ").append(IBOBsBatchWfFlowValue.S_RegionId).append(" =:regionId ");
            map.put("regionId", regionId);
        }

        if (billDay > 0) {
            sb.append(" AND ").append(IBOBsBatchWfFlowValue.S_BillDay).append(" =:billDay ");
            map.put("billDay", Long.valueOf(billDay));
        }

        if (StringUtils.isNotBlank(name)) {
            sb.append(" AND ").append(IBOBsBatchWfFlowValue.S_WfFlowName).append(" =:name ");
            map.put("name", name);
        }

        if (wfFlowId > 0) {
            sb.append(" AND ").append(IBOBsBatchWfFlowValue.S_WfFlowId).append(" =:wfFlowId ");
            map.put("wfFlowId", Long.valueOf(wfFlowId));
        }
        IBOBsBatchWfFlowValue[] retValues = BOBsBatchWfFlowEngine.getBeans(sb.toString(), map);

        if (StringUtils.isNotBlank(regionId) && (retValues == null || retValues.length < 1)) {
            map.put("regionId", "X");
            retValues = BOBsBatchWfFlowEngine.getBeans(sb.toString(), map);
        }

        return retValues;
    }

    @Override
    public IBOBsBatchWfFlowMonValue getBatchWfFlowMonValue(long wfFlowId, String regionId, long billDay, long billingCycleId) throws Exception {

        StringBuilder sb = new StringBuilder();
        sb.append(" 1=1 ");
        Map map = new HashMap();

        if(wfFlowId>0){
            sb.append(" AND ").append(IBOBsBatchWfFlowMonValue.S_WfFlowId).append(" =:wfFlowId ");
            map.put("wfFlowId", Long.valueOf(wfFlowId));
        }

        if(StringUtils.isNotBlank(regionId)){
            sb.append(" AND ").append(IQBOBsBatchWfFlowRelValue.S_RegionId).append(" =:regionId ");
            map.put("regionId", regionId);
        }

        if(billDay>0){
            sb.append(" AND ").append(IBOBsBatchWfFlowMonValue.S_BillDay).append(" =:billDay ");
            map.put("billDay", Long.valueOf(billDay));
        }
        if(billingCycleId>0){
            sb.append(" AND ").append(IBOBsBatchWfFlowMonValue.S_BillingCycleId).append(" =:billingCycleId ");
            map.put("billingCycleId", Long.valueOf(billingCycleId));

        }


        IBOBsBatchWfFlowMonValue[] retValues = BOBsBatchWfFlowMonEngine.getBeans(sb.toString(), map);

        if(retValues!=null && retValues.length>0){
            return retValues[0];
        }

        return null;
    }


    public IBOBsBatchWfFlowMonValue[] getBatchWfFlowMonValues(String regionId, long billDay, long billingCycleId) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(" 1=1 ");
        Map map = new HashMap();

        if (StringUtils.isNotBlank(regionId)) {
            sb.append(" AND ").append(IBOBsBatchWfFlowMonValue.S_RegionId).append(" =:regionId ");
            map.put("regionId", regionId);
        }

        if (billDay > 0) {
            sb.append(" AND ").append(IBOBsBatchWfFlowMonValue.S_BillDay).append(" =:billDay ");
            map.put("billDay", Long.valueOf(billDay));
        }
        if (billingCycleId > 0) {
            sb.append(" AND ").append(IBOBsBatchWfFlowMonValue.S_BillingCycleId).append(" =:billingCycleId ");
            map.put("billingCycleId", Long.valueOf(billingCycleId));

        }


        IBOBsBatchWfFlowMonValue[] retValues = BOBsBatchWfFlowMonEngine.getBeans(sb.toString(), map);


        return retValues;
    }

    public IBOBsBatchWfPsValue getBatchWfPsValue(String wfProcessId) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(" 1=1 ");
        Map map = new HashMap();

        sb.append(" AND ").append(IBOBsBatchWfPsValue.S_WfProcessId).append(" =:wfProcessId ");
        map.put("wfProcessId", wfProcessId);

        IBOBsBatchWfPsValue[] retValues = BOBsBatchWfPsEngine.getBeans(sb.toString(), map);
        if (retValues != null && retValues.length > 0) {
            return retValues[0];
        }
        return null;
    }

    /*************分割线*****************/

}
