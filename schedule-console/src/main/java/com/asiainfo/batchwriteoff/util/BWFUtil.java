package com.asiainfo.batchwriteoff.util;

import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.complex.center.CenterFactory;
import com.ai.appframe2.complex.center.CenterInfo;
import com.ai.appframe2.complex.util.e.K;
import com.ai.appframe2.service.ServiceFactory;
import com.ai.common.util.ExceptionUtil;
import com.asiainfo.batchwriteoff.ivalues.IBOAmBatchWfThreadMonValue;
import com.asiainfo.batchwriteoff.ivalues.IBOBsBatchWfFlowMonValue;
import com.asiainfo.batchwriteoff.ivalues.IBOBsBatchWfParaValue;
import com.asiainfo.batchwriteoff.ivalues.IBOBsBatchWfPsMonValue;
import com.asiainfo.batchwriteoff.service.interfaces.IBWFCommonBillingSV;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * <p> @Description </p>
 * <p> @company Asiainfo-linkage (NJ) </p>
 * <p> @author 闫瑞国 </p>
 * <p> @Email yanrg@asiainfo-linkage.com </p>
 * <p> @createDate 2012-7-9 下午2:19:40 </p>
 * <p> @modifyDate </p>
 * <p> @version 1.0 </p>
 */
public class BWFUtil {

    private static final transient Log log = LogFactory.getLog(BWFUtil.class);


    public static void checkPassword(String password,String regionId) throws Exception {

        IBWFCommonBillingSV commonSV = (IBWFCommonBillingSV) ServiceFactory.getService(IBWFCommonBillingSV.class);
        String checkPasswordFlag = "Y";
        IBOBsBatchWfParaValue[] paraCheckPasswordFlag = commonSV.getWfParaValues(regionId,
                "CHECK_PASSWORD_FLAG");
        if (paraCheckPasswordFlag != null && paraCheckPasswordFlag.length > 0) {
            checkPasswordFlag = paraCheckPasswordFlag[0].getParaValue();
        }

        if ("Y".equalsIgnoreCase(checkPasswordFlag)) {

            IBOBsBatchWfParaValue[] paraPassword = commonSV.getWfParaValues(regionId, "BWF_PASSWORD");

            if (paraPassword != null && paraPassword.length > 0) {
                String realPassword = paraPassword[0].getParaValue();

                if (StringUtils.isNotBlank(realPassword)) {
                    if (!(K.k_s(realPassword)).equals(password)) {
                        ExceptionUtil.throwBusinessException("AMS0700548", password);
                    }
                }

            }
        }
    }


    public static void confirmWfFlowNode(long wfFlowId, String regionId, long billDay, long billlingCycleId,
                                         String state) throws Exception {

        IBWFCommonBillingSV commonSV = (IBWFCommonBillingSV) ServiceFactory.getService(IBWFCommonBillingSV.class);
        IBOBsBatchWfFlowMonValue nodeFlowMon = commonSV.getBatchWfFlowMonValue(wfFlowId, regionId, billDay,
                billlingCycleId);

        if (nodeFlowMon == null || !"H".equals(nodeFlowMon.getState())) {
            String[] paras = new String[]{regionId, Long.toString(billlingCycleId), Long.toString(billDay),
                    Long.toString(wfFlowId)};
            ExceptionUtil.throwBusinessException("AMS0700542", paras);

        } else {
            if (StringUtils.isBlank(state)) {
                ExceptionUtil.throwBusinessException("AMS0700544");
            } else if (!state.equals("Y") && !state.equals("N")) {
                ExceptionUtil.throwBusinessException("AMS0700543");
            }
            nodeFlowMon.setState(state);
        }

        commonSV.saveWfFlowMonValues(new IBOBsBatchWfFlowMonValue[]{nodeFlowMon});

    }

    public static void resetWfFlowNode(long wfFlowId, String regionId, long billDay, long billlingCycleId)
            throws Exception {

        try {
            ServiceManager.getSession().startTransaction();

            IBWFCommonBillingSV commonSV = (IBWFCommonBillingSV) ServiceFactory.getService(IBWFCommonBillingSV.class);
            IBOBsBatchWfFlowMonValue nodeFlowMon = commonSV.getBatchWfFlowMonValue(wfFlowId, regionId, billDay,
                    billlingCycleId);

            if (nodeFlowMon == null || !("X".equals(nodeFlowMon.getState()) || "N".equals(nodeFlowMon.getState()))) {

                ExceptionUtil.throwBusinessException("AMS0700536", Long.toString(wfFlowId));

            } else {
                nodeFlowMon.setState("REDO");
                nodeFlowMon.setRemarks("REDO");
            }

            IBOBsBatchWfPsMonValue[] wfPsMonValues = commonSV.getWfPsMonValues(regionId, billlingCycleId, billDay,
                    wfFlowId);
            List psMonList = new ArrayList();
            if (wfPsMonValues != null && wfPsMonValues.length > 0) {
                for (int i = 0; i < wfPsMonValues.length; i++) {
                    if ("C".equals(wfPsMonValues[i].getState())) {
                        ExceptionUtil.throwBusinessException("AMS0700537", Long.toString(wfFlowId),
                                wfPsMonValues[i].getWfProcessId());
                    } else if ("X".equals(wfPsMonValues[i].getState())) {
                        wfPsMonValues[i].delete();
                        psMonList.add(wfPsMonValues[i]);
                    }

                }

            }

            IBOAmBatchWfThreadMonValue[] wfThreadMonValues = commonSV.getWfThreadMonValues(regionId, billlingCycleId,
                    billDay, wfFlowId, null);

            List threadMonList = new ArrayList();
            if (wfThreadMonValues != null && wfThreadMonValues.length > 0) {
                for (int i = 0; i < wfThreadMonValues.length; i++) {

                    if ("C".equals(wfThreadMonValues[i].getState())) {
                        ExceptionUtil.throwBusinessException("AMS0700538", Long.toString(wfFlowId),
                                wfPsMonValues[i].getWfProcessId(), wfThreadMonValues[i].getWfThreadId());
                    } else if ("X".equals(wfThreadMonValues[i].getState())) {
                        wfThreadMonValues[i].delete();
                        threadMonList.add(wfThreadMonValues[i]);
                    }

                }

            }

            commonSV.saveWfFlowMonValues(new IBOBsBatchWfFlowMonValue[]{nodeFlowMon});
            if (psMonList.size() > 0) {
                commonSV.saveWfPsMonValues((IBOBsBatchWfPsMonValue[]) psMonList.toArray(new IBOBsBatchWfPsMonValue[0]));
            }
            if (threadMonList.size() > 0) {
                commonSV.saveWfThreadMonValues((IBOAmBatchWfThreadMonValue[]) threadMonList
                        .toArray(new IBOAmBatchWfThreadMonValue[0]));
            }

            ServiceManager.getSession().commitTransaction();

        } catch (Exception e) {
            ServiceManager.getSession().rollbackTransaction();
            throw e;
        }

    }


}
