package com.asiainfo.common.service.impl;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.common.abo.dao.interfaces.IABGMonWTriggerDAO;
import com.asiainfo.common.abo.ivalues.IBOABGMonWTriggerValue;
import com.asiainfo.common.service.interfaces.IABGMonWTriggerSV;
import com.asiainfo.monitor.busi.exe.task.bo.BOAIMonWTriggerHisBean;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonWTriggerHisValue;
import com.asiainfo.monitor.busi.exe.task.service.impl.AIMonWTriggerSVImpl;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonColgRuleSV;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonColgRuleValue;

public class ABGMonWTriggerSVImpl implements IABGMonWTriggerSV
{
    private static transient Log log = LogFactory.getLog(AIMonWTriggerSVImpl.class);

    /**
     * 根据条件查询告警记录
     * 
     * @param sqlCondition
     * @param startIndex
     * @param endIndex
     * @return
     * @throws Exception
     */
    public IBOABGMonWTriggerValue[] getMonWTriggerByRecordId(String recordId, Integer startIndex, Integer endIndex) throws RemoteException, Exception
    {
        StringBuilder sb = new StringBuilder();
        sb.append(IBOABGMonWTriggerValue.S_RecordId + " = :recordId");
        HashMap parameter = new HashMap();
        parameter.put("recordId", recordId);
        IABGMonWTriggerDAO triggerDao = (IABGMonWTriggerDAO) ServiceFactory.getService(IABGMonWTriggerDAO.class);
        return triggerDao.getMonWTriggerByCondition(sb.toString(), parameter, startIndex, endIndex);
    }

    /**
     * 批量保存或修改警告记录
     * 
     * @param values
     * @throws Exception
     */
    public void saveOrUpdate(IBOABGMonWTriggerValue[] values) throws RemoteException, Exception
    {
        IABGMonWTriggerDAO triggerDao = (IABGMonWTriggerDAO) ServiceFactory.getService(IABGMonWTriggerDAO.class);
        triggerDao.saveOrUpdate(values);
    }

    /**
     * 保存或修改警告记录
     * 
     * @param value
     * @throws Exception
     */
    public void saveOrUpdate(IBOABGMonWTriggerValue value) throws RemoteException, Exception
    {
        IABGMonWTriggerDAO triggerDao = (IABGMonWTriggerDAO) ServiceFactory.getService(IABGMonWTriggerDAO.class);
        triggerDao.saveOrUpdate(value);
    }

    /**
     * 保存告警并处理之前的告警，将X分钟之前的同任务告警转成历史
     * @param values
     * @throws RemoteException
     * @throws Exception
     */
    public void saveAndProcess(IBOABGMonWTriggerValue[] values) throws RemoteException, Exception
    {
        if(values == null || values.length < 1)
            return;
        IABGMonWTriggerDAO triggerDao = (IABGMonWTriggerDAO) ServiceFactory.getService(IABGMonWTriggerDAO.class);
        IAIMonColgRuleSV ruleSV = (IAIMonColgRuleSV) ServiceFactory.getService(IAIMonColgRuleSV.class);
        IBOAIMonColgRuleValue ruleValue = ruleSV.getDefaultColgRuleByType("PROC_WTRIGGER");
        List hisList = new ArrayList();
        List delList = new ArrayList();
        if(ruleValue != null) {
            //删除second分钟之前的类似告警(转到历史表)
            int second = Integer.parseInt(ruleValue.getExpr0());
            for(int i = 0; i < values.length; i++) {
                if(values[i] == null)
                    continue;
                IBOABGMonWTriggerValue[] oldValues = triggerDao.getMonWTriggerBefore(values[i].getInfoId(), second, values[i].getWarnLevel());
                if(oldValues != null) {
                    for(int j = 0; j < oldValues.length; j++) {
                        if(oldValues[j] == null)
                            continue;
                        oldValues[j].delete();
                        delList.add(oldValues[j]);
                        IBOAIMonWTriggerHisValue hisValue = new BOAIMonWTriggerHisBean();
                        hisValue.setTriggerId(oldValues[j].getTriggerId());
                        hisValue.setRecordId(oldValues[j].getRecordId());
                        hisValue.setIp(oldValues[j].getIp());
                        hisValue.setInfoId(oldValues[j].getInfoId());
                        hisValue.setInfoName(oldValues[j].getInfoName());
                        hisValue.setLayer(oldValues[j].getLayer());
                        hisValue.setPhonenum(oldValues[j].getPhonenum());
                        hisValue.setContent(oldValues[j].getContent());
                        hisValue.setWarnLevel(oldValues[j].getWarnLevel());
                        hisValue.setExpiryDate(ServiceManager.getOpDateTime());
                        //                        hisValue.(oldValues[j].getCreateDate());
                        hisValue.setDoneDate(oldValues[j].getDoneDate());
                        hisValue.setState("F");
                        hisValue.setRemarks(StringUtils.isBlank(oldValues[j].getRemarks()) ? "告警恢复" : oldValues[j].getRemarks() + "告警恢复");
                        hisValue.setStsToNew();
                        hisList.add(hisValue);
                    }
                }
            }
        }
        //保存新预警记录
        triggerDao.saveOrUpdate(values);

        if(delList.size() > 0) {
            //删除新预警记录
            triggerDao.saveOrUpdate((IBOABGMonWTriggerValue[]) delList.toArray(new IBOABGMonWTriggerValue[0]));
        }
        if(hisList.size() > 0) {
            //保存历史预警记录
            //            triggerDao.saveWTriggerHis((IBOAIMonWTriggerHisValue[]) hisList.toArray(new IBOAIMonWTriggerHisValue[0]));
        }
        hisList = null;
        delList = null;
    }

    public int count(String layerType, String warnLevel, String startDate, String endDate) throws RemoteException, Exception
    {
        if(log.isDebugEnabled()) {
            log.debug("layerType=" + layerType + ", warnLevel=" + warnLevel + ", startDate=" + startDate + ", endDate=" + endDate);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(IBOABGMonWTriggerValue.S_Layer).append("=:layer");
        if(warnLevel != null && StringUtils.isNotBlank(warnLevel)) {
            sb.append(" and ").append(IBOABGMonWTriggerValue.S_WarnLevel).append("=:warnlevel");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(startDate == null || StringUtils.isBlank(startDate)) {
            startDate = sdf.format(new Date(System.currentTimeMillis()));
        }

        sb.append(" and ").append(IBOABGMonWTriggerValue.S_CreateDate).append(">= :startDate ");

        Map params = new HashMap();
        params.put("layer", layerType);
        params.put("warnlevel", warnLevel);
        params.put("startDate", startDate);

        if(endDate != null && StringUtils.isNotBlank(endDate)) {
            sb.append(" and ").append(IBOABGMonWTriggerValue.S_CreateDate).append("<= :endDate ");
            params.put("endDate", endDate);
        }

        String cond = sb.toString();

        IABGMonWTriggerDAO triggerDao = (IABGMonWTriggerDAO) ServiceFactory.getService(IABGMonWTriggerDAO.class);
        return triggerDao.count(cond, params);
    }

    public IBOABGMonWTriggerValue[] getAllByCond(String layerType, String warnLevel, String startDate, String endDate) throws RemoteException,
            Exception
    {
        StringBuilder sb = new StringBuilder();
        sb.append(IBOABGMonWTriggerValue.S_Layer).append("=:layer");
        if(warnLevel != null && StringUtils.isNotBlank(warnLevel)) {
            sb.append(" and ").append(IBOABGMonWTriggerValue.S_WarnLevel).append("=:warnlevel");
        }
        if(startDate != null && endDate != null && StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
            sb.append(" and ").append(IBOABGMonWTriggerValue.S_CreateDate).append(">= :startDate  ");
            sb.append(" and ").append(IBOABGMonWTriggerValue.S_CreateDate).append("<= :endDate  ");
        }
        else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            startDate = sdf.format(new Date(System.currentTimeMillis()));
            sb.append(" and ").append(IBOABGMonWTriggerValue.S_CreateDate).append(">= :startDate ");
        }
        String cond = sb.toString();
        Map params = new HashMap();
        params.put("layer", layerType);
        params.put("warnlevel", warnLevel);
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        IABGMonWTriggerDAO triggerDao = (IABGMonWTriggerDAO) ServiceFactory.getService(IABGMonWTriggerDAO.class);
        return triggerDao.query(cond, params);
    }

    public IBOABGMonWTriggerValue[] getAllByCond(String layerType, String warnLevel, String startDate, String endDate, int startIndex, int endIndex)
            throws RemoteException, Exception
    {
        StringBuilder sb = new StringBuilder();
        sb.append(IBOABGMonWTriggerValue.S_Layer).append("=:layer");
        if(warnLevel != null && StringUtils.isNotBlank(warnLevel)) {
            sb.append(" and ").append(IBOABGMonWTriggerValue.S_WarnLevel).append("=:warnlevel");
        }
        if(startDate != null && endDate != null && StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
            sb.append(" and ").append(IBOABGMonWTriggerValue.S_CreateDate).append(">= :startDate  ");
            sb.append(" and ").append(IBOABGMonWTriggerValue.S_CreateDate).append("<= :endDate ");
        }
        else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            startDate = sdf.format(new Date(System.currentTimeMillis()));
            sb.append(" and ").append(IBOABGMonWTriggerValue.S_CreateDate).append(">= :startDate ");
        }
        sb.append(" order by create_date desc ");
        String cond = sb.toString();
        Map params = new HashMap();
        params.put("layer", layerType);
        params.put("warnlevel", warnLevel);
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        IABGMonWTriggerDAO triggerDao = (IABGMonWTriggerDAO) ServiceFactory.getService(IABGMonWTriggerDAO.class);
        IBOABGMonWTriggerValue[] triggerValues = triggerDao.getMonWTriggerByCondition(cond, params, startIndex, endIndex);
        if(triggerValues != null && triggerValues.length > 0) {
            for(int i = 0; i < triggerValues.length; i++) {
                triggerValues[i].setExtAttr("CHK", "false");
            }
        }
        return triggerValues;
    }

    public int countByInfoIdCond(String infoId, String warnLevel, String startDate, String endDate) throws RemoteException, Exception
    {
        if(log.isDebugEnabled()) {
            log.debug("infoId=" + infoId + ", warnLevel=" + warnLevel + ", startDate=" + startDate + ", endDate=" + endDate);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(IBOABGMonWTriggerValue.S_InfoId).append("=:infoId");
        if(warnLevel != null && StringUtils.isNotBlank(warnLevel)) {
            sb.append(" and ").append(IBOABGMonWTriggerValue.S_WarnLevel).append("=:warnlevel");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(startDate == null || StringUtils.isBlank(startDate)) {
            startDate = sdf.format(new Date(System.currentTimeMillis()));
        }

        sb.append(" and ").append(IBOABGMonWTriggerValue.S_CreateDate).append(">= :startDate ");

        Map params = new HashMap();
        params.put("infoId", infoId);
        params.put("warnlevel", warnLevel);
        params.put("startDate", startDate);

        if(endDate != null && StringUtils.isNotBlank(endDate)) {
            sb.append(" and ").append(IBOABGMonWTriggerValue.S_CreateDate).append("<= :endDate ");
            params.put("endDate", endDate);
        }

        String cond = sb.toString();

        IABGMonWTriggerDAO triggerDao = (IABGMonWTriggerDAO) ServiceFactory.getService(IABGMonWTriggerDAO.class);
        return triggerDao.count(cond, params);
    }

    public IBOABGMonWTriggerValue[] getTriggerValuesByInfoIdCond(String infoId, String warnLevel, String startDate, String endDate,
            Integer startIndex, Integer endIndex) throws RemoteException, Exception
    {
        StringBuilder sb = new StringBuilder();
        sb.append(IBOABGMonWTriggerValue.S_InfoId).append("=:infoId");
        if(warnLevel != null && StringUtils.isNotBlank(warnLevel)) {
            sb.append(" and ").append(IBOABGMonWTriggerValue.S_WarnLevel).append("=:warnlevel");
        }
        if(startDate != null && endDate != null && StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
            sb.append(" and ").append(IBOABGMonWTriggerValue.S_CreateDate).append("<= :endDate ");
        }
        else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            startDate = sdf.format(new Date(System.currentTimeMillis()));
            sb.append(" and ").append(IBOABGMonWTriggerValue.S_CreateDate).append(">= :startDate ");
        }
        String cond = sb.toString();
        Map params = new HashMap();
        params.put("infoId", infoId);
        params.put("warnlevel", warnLevel);
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        IABGMonWTriggerDAO triggerDao = (IABGMonWTriggerDAO) ServiceFactory.getService(IABGMonWTriggerDAO.class);
        IBOABGMonWTriggerValue[] triggerValues = triggerDao.getMonWTriggerByCondition(cond, params, startIndex, endIndex);
        if(triggerValues != null && triggerValues.length > 0) {
            for(int i = 0; i < triggerValues.length; i++) {
                triggerValues[i].setExtAttr("CHK", "false");
            }
        }
        return triggerValues;
    }

    /**
     * 修复告警记录,将告警记录转移到历史表，历史表中失效日期为修复日期，状态为F
     * @param triggerId
     * @param remarks
     * @throws Exception
     */
    public void repairWTrigger(Object[] triggerIds, String remarks) throws RemoteException, Exception
    {
        if(triggerIds == null || triggerIds.length < 1)
            return;
        IABGMonWTriggerDAO triggerDao = (IABGMonWTriggerDAO) ServiceFactory.getService(IABGMonWTriggerDAO.class);
        List srcList = new ArrayList();
        List desList = new ArrayList();
        for(int i = 0; i < triggerIds.length; i++) {
            IBOABGMonWTriggerValue value = triggerDao.getWTriggerBean(String.valueOf(triggerIds[i]));
            if(value == null)
                return;
            IBOAIMonWTriggerHisValue hisValue = new BOAIMonWTriggerHisBean();
            hisValue.setTriggerId(value.getTriggerId());
            hisValue.setRecordId(value.getRecordId());
            hisValue.setIp(value.getIp());
            hisValue.setInfoId(value.getInfoId());
            hisValue.setInfoName(value.getInfoName());
            hisValue.setLayer(value.getLayer());
            hisValue.setPhonenum(value.getPhonenum());
            hisValue.setContent(value.getContent());
            hisValue.setWarnLevel(value.getWarnLevel());
            hisValue.setExpiryDate(ServiceManager.getOpDateTime());
            //            hisValue.setCreateDate(value.getCreateDate());
            hisValue.setDoneDate(value.getDoneDate());
            hisValue.setState("F");
            hisValue.setRemarks(StringUtils.isBlank(remarks) ? "告警恢复" : remarks + "告警恢复");
            hisValue.setStsToNew();
            srcList.add(value);
            desList.add(hisValue);
        }
        if(srcList.size() > 0)
            triggerDao.deleteWTrigger((IBOABGMonWTriggerValue[]) srcList.toArray(new IBOABGMonWTriggerValue[0]));

        //        if(desList.size() > 0)
//            triggerDao.saveWTriggerHis((IBOAIMonWTriggerHisValue[]) desList.toArray(new IBOAIMonWTriggerHisValue[0]));

    }

    /**
     * 通过ip，信息名称查询告警信息
     * 
     * @param request
     * @throws Exception 
     */
    @Override
    public IBOABGMonWTriggerValue[] getTriggerValuesByIpInfoName(String ip, String infoName, String hostName,String beginDate,String endDate, int start, int end) throws Exception
    {
        IABGMonWTriggerDAO triggerDao = (IABGMonWTriggerDAO) ServiceFactory.getService(IABGMonWTriggerDAO.class);
        return triggerDao.getTriggerValuesByIpInfoName(ip, infoName, hostName,beginDate,endDate,start, end);
    }

    /**
     * 查询结果行数
     * 
     * @param request
     * @throws Exception
     */
    @Override
    public int getCntByCond(String ip, String infoName, String hostName,String beginDate,String endDate) throws RemoteException, Exception
    {
        IABGMonWTriggerDAO triggerDao = (IABGMonWTriggerDAO) ServiceFactory.getService(IABGMonWTriggerDAO.class);
        return triggerDao.getCntByCond(ip, infoName, hostName,beginDate,endDate);
    }

}
