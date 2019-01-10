package com.asiainfo.monitor.busi.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.bo.DataContainerFactory;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.complex.util.e.K;
import com.ai.appframe2.service.ServiceFactory;
import com.ai.appframe2.web.action.BaseAction;
import com.asiainfo.monitor.busi.cache.impl.PriEntity;
import com.asiainfo.monitor.busi.exe.task.bo.BOAIMonDBAcctBean;
import com.asiainfo.monitor.busi.exe.task.bo.BOAIMonDBURLBean;
import com.asiainfo.monitor.busi.exe.task.bo.BOAIMonPImgDataResolveBean;
import com.asiainfo.monitor.busi.exe.task.bo.BOAIMonPInfoFilterBean;
import com.asiainfo.monitor.busi.exe.task.bo.BOAIMonPInfoGroupBean;
import com.asiainfo.monitor.busi.exe.task.bo.BOAIMonPTableBean;
import com.asiainfo.monitor.busi.exe.task.bo.BOAIMonPTimeBean;
import com.asiainfo.monitor.busi.exe.task.bo.BOAIMonThresholdBean;
import com.asiainfo.monitor.busi.exe.task.bo.BOAIMonWDtlBean;
import com.asiainfo.monitor.busi.exe.task.bo.BOAIMonWPersonBean;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonDBAcctValue;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonDBURLValue;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPExecValue;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPImgDataResolveValue;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPInfoFilterValue;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPInfoGroupValue;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPInfoValue;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPTableValue;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPTimeValue;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonThresholdValue;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonWDtlValue;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonWPersonValue;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonWTriggerValue;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonDBAcctSV;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonDBUrlSV;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonLRecordSV;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPExecSV;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPImgDataResolveSV;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPInfoFilterSV;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPInfoGroupSV;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPInfoSV;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPTableSV;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPThresholdSV;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPTimeSV;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonWDtlSV;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonWPersonSV;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonWTriggerSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonParamDefineSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonParamValuesSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonPhysicHostSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonUserPriEntitySV;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonParamDefineBean;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonParamDefineValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonParamValuesValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonPhysicHostValue;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.util.TimeUtil;

public class TaskAction extends BaseAction
{

    private static final Log log = LogFactory.getLog(TaskAction.class);

    /**
     * 根据进程监控名称取得检索结果
     * 
     * @param execName
     * @return
     * @throws Exception
     */
    public IBOAIMonPExecValue[] getExecInfoByName(String execName, Integer startNum, Integer endNum) throws Exception
    {
        IAIMonPExecSV execSV = (IAIMonPExecSV) ServiceFactory.getService(IAIMonPExecSV.class);
        return execSV.getExecByName(execName, startNum, endNum);
    }

    /**
     * 取得总件数
     * 
     * @param execName
     * @return
     * @throws Exception
     */
    public int getExecInfoCount(String execName) throws Exception
    {
        IAIMonPExecSV execSV = (IAIMonPExecSV) ServiceFactory.getService(IAIMonPExecSV.class);
        return execSV.getExecCount(execName);
    }

    /**
     * 根据进程监控ID取得信息
     * 
     * @param execId
     * @return
     * @throws Exception
     */
    public IBOAIMonPExecValue getExecInfoById(String execId) throws Exception
    {
        IAIMonPExecSV execSV = (IAIMonPExecSV) ServiceFactory.getService(IAIMonPExecSV.class);
        return execSV.getBeanById(Long.parseLong(execId));
    }

    /**
     * 保存进程监控配置
     * 
     * @param execInfo
     * @throws Exception
     */
    //	public void saveExec(Object[] execInfo, ArrayCollection paramVals) throws Exception {
    //		if (execInfo==null || execInfo.length<=0)
    //			return;
    //		try{
    //			IBOAIMonPExecValue execValue = new BOAIMonPExecBean();
    //			
    //			if (StringUtils.isBlank(execInfo[0].toString())) {
    //				execValue.setStsToNew();
    //			} else {
    //				execValue.setExecId(Long.parseLong(execInfo[0].toString().trim()));
    //				execValue.setStsToOld();
    //			}
    //			execValue.setName(execInfo[1].toString().trim());
    //			execValue.setEType(execInfo[2].toString().trim());
    //			execValue.setExpr(execInfo[3].toString().trim());
    //			execValue.setState(execInfo[4].toString().trim());
    //			execValue.setRemarks(execInfo[5].toString().trim());
    //			
    //			DataContainer[] paramDcs = null;
    //			//处理参数
    //			if(paramVals != null && paramVals.size() > 0) {
    //				ASObject tmpObj = null;
    //				paramDcs = new DataContainer[paramVals.size()];
    //				for(int i = 0; i < paramVals.size(); i++){
    //					tmpObj = (ASObject)paramVals.get(i);
    //					paramDcs[i] = FlexHelper.transferAsToDc(tmpObj);
    //				}
    //			}
    //			
    //			IAIMonPExecSV execSV = (IAIMonPExecSV) ServiceFactory.getService(IAIMonPExecSV.class);
    ////			execSV.saveOrUpdate(execValue);
    //			execSV.saveExecAndParams(execValue, paramDcs);
    //
    //		} catch (Exception e) {
    //			log.error("Call TaskAction's Method saveExec has Exception : " + e.getMessage());
    //			throw e;
    //		}
    //	}

    /**
     * 删除进程监控配置
     * 
     * @param execId
     * @throws Exception
     */
    public void deleteExec(String execId) throws Exception
    {
        if(StringUtils.isBlank(execId)) {
            return;
        }
        try {
            IAIMonPExecSV execSV = (IAIMonPExecSV) ServiceFactory.getService(IAIMonPExecSV.class);
            execSV.delete(Long.parseLong(execId));
        }
        catch(Exception e) {
            log.error("Call TaskAction's Method deleteExec has Exception : " + e.getMessage());
            throw e;
        }
    }

    /**
     * 根据表达式检索时间段
     * 
     * @param timeExpr
     * @return
     * @throws Exception
     */
    public IBOAIMonPTimeValue[] getTimeInfoByExpr(String timeExpr, Integer startNum, Integer endNum) throws Exception
    {
        IAIMonPTimeSV timeSV = (IAIMonPTimeSV) ServiceFactory.getService(IAIMonPTimeSV.class);
        return timeSV.getTimeInfoByExpr(timeExpr, startNum, endNum);
    }

    /**
     * 根据表达式取得总件数
     * 
     * @param timeExpr
     * @return
     * @throws Exception
     */
    public int getTimeInfoCount(String timeExpr) throws Exception
    {
        IAIMonPTimeSV timeSV = (IAIMonPTimeSV) ServiceFactory.getService(IAIMonPTimeSV.class);
        return timeSV.getTimeInfoCount(timeExpr);
    }

    /**
     * 保存监控时间段配置
     * 
     * @param timeInfo
     * @throws Exception
     */
    public void saveTime(Object[] timeInfo) throws Exception
    {
        if(timeInfo == null || timeInfo.length <= 0) {
            return;
        }
        try {
            IBOAIMonPTimeValue timeValue = new BOAIMonPTimeBean();

            if(StringUtils.isBlank(timeInfo[0].toString())) {
                timeValue.setStsToNew();
            }
            else {
                timeValue.setTimeId(Long.parseLong(timeInfo[0].toString().trim()));
                timeValue.setStsToOld();
            }
            timeValue.setTType(timeInfo[1].toString().trim());
            timeValue.setExpr(timeInfo[2].toString().trim());
            timeValue.setState(timeInfo[3].toString().trim());
            timeValue.setRemarks(timeInfo[4].toString().trim());

            IAIMonPTimeSV timeSV = (IAIMonPTimeSV) ServiceFactory.getService(IAIMonPTimeSV.class);
            timeSV.saveOrUpdate(timeValue);

        }
        catch(Exception e) {
            log.error("Call TaskAction's Method saveTime has Exception : " + e.getMessage());
            throw e;
        }
    }

    /**
     * 删除监控时间段配置
     * 
     * @param timeId
     * @throws Exception
     */
    public void deleteTime(String timeId) throws Exception
    {
        if(StringUtils.isBlank(timeId)) {
            return;
        }
        try {
            IAIMonPTimeSV timeSV = (IAIMonPTimeSV) ServiceFactory.getService(IAIMonPTimeSV.class);
            timeSV.delete(Long.parseLong(timeId));
        }
        catch(Exception e) {
            log.error("Call TaskAction's Method deleteTime has Exception : " + e.getMessage());
            throw e;
        }
    }

    /** 
     * 根据表达式检索进程阀值配置
     * 
     * @param expr
     * @return
     * @throws Exception
     */
    public IBOAIMonThresholdValue[] getThresholdByExpr(String name, Integer startNum, Integer endNum) throws Exception
    {
        IAIMonPThresholdSV thresholdSV = (IAIMonPThresholdSV) ServiceFactory.getService(IAIMonPThresholdSV.class);
        return thresholdSV.getThresholdByExpr(name, startNum, endNum);
    }

    /**
     * 根据表达式检索总件数
     * 
     * @param expr
     * @return
     * @throws Exception
     */
    public int getThresholdCount(String expr) throws Exception
    {
        IAIMonPThresholdSV thresholdSV = (IAIMonPThresholdSV) ServiceFactory.getService(IAIMonPThresholdSV.class);
        return thresholdSV.getThresholdCount(expr);
    }

    /**
     * 保存或修改进程阀值配置
     * 
     * @param thresholdInfo
     * @throws Exception
     */
    public void saveThreshold(Object[] thresholdInfo) throws Exception
    {
        if(thresholdInfo == null || thresholdInfo.length <= 0) {
            return;
        }
        try {

            IBOAIMonThresholdValue thresholdValue = new BOAIMonThresholdBean();

            if(StringUtils.isBlank(thresholdInfo[0].toString())) {
                thresholdValue.setStsToNew();
            }
            else {
                thresholdValue.setThresholdId(Long.parseLong(thresholdInfo[0].toString().trim()));
                thresholdValue.setStsToOld();
            }

            thresholdValue.setExpr1(thresholdInfo[1].toString().trim());
            thresholdValue.setExpr2(thresholdInfo[2].toString().trim());
            thresholdValue.setState(thresholdInfo[3].toString().trim());
            thresholdValue.setRemarks(thresholdInfo[4].toString().trim());
            thresholdValue.setThresholdName(thresholdInfo[5].toString().trim());
            IAIMonPThresholdSV thresholdSV = (IAIMonPThresholdSV) ServiceFactory.getService(IAIMonPThresholdSV.class);
            thresholdSV.saveOrUpdate(thresholdValue);

        }
        catch(Exception e) {
            log.error("Call TaskAction's Method saveThreshold has Exception : " + e.getMessage());
            throw e;
        }
    }

    /**
     * 删除进程阀值配置
     * 
     * @param thresholdId
     * @throws Exception
     */
    public void deleteThreshold(String thresholdId) throws Exception
    {
        if(StringUtils.isBlank(thresholdId)) {
            return;
        }
        try {
            IAIMonPThresholdSV thresholdSV = (IAIMonPThresholdSV) ServiceFactory.getService(IAIMonPThresholdSV.class);
            thresholdSV.delete(Long.parseLong(thresholdId));

        }
        catch(Exception e) {
            log.error("Call TaskAction's Method deleteThreshold has Exception : " + e.getMessage());
            throw e;
        }
    }

    /**
     * 根据表数据监控配置名称检索结果
     * 
     * @param tableName
     * @return
     * @throws Exception
     */
    public IBOAIMonPTableValue[] getTableInfoByName(String tableName, Integer startNum, Integer endNum) throws Exception
    {
        IAIMonPTableSV tableSV = (IAIMonPTableSV) ServiceFactory.getService(IAIMonPTableSV.class);
        return tableSV.getTableInfoByName(tableName, startNum, endNum);
    }

    /**
     * 检索总件数
     * 
     * @param tableName
     * @return
     * @throws Exception
     */
    public int getTableInfoCount(String tableName) throws Exception
    {
        IAIMonPTableSV tableSV = (IAIMonPTableSV) ServiceFactory.getService(IAIMonPTableSV.class);
        return tableSV.getTableInfoCount(tableName);
    }

    /**
     * 保存或修改数据监控配置
     * 
     * @param tableInfo
     * @throws Exception
     */
    public void saveTable(Object[] tableInfo) throws Exception
    {
        if(tableInfo == null || tableInfo.length <= 0) {
            return;
        }
        try {
            IBOAIMonPTableValue tableValue = new BOAIMonPTableBean();

            if(StringUtils.isBlank(tableInfo[0].toString())) {
                tableValue.setStsToNew();
            }
            else {
                tableValue.setTableId(Long.parseLong(tableInfo[0].toString().trim()));
                tableValue.setStsToOld();
            }
            tableValue.setName(tableInfo[1].toString().trim());
            tableValue.setSql(tableInfo[2].toString().trim());
            tableValue.setDbAcctCode(tableInfo[3].toString().trim());
            tableValue.setDbUrlName(tableInfo[4].toString().trim());
            tableValue.setState(tableInfo[5].toString().trim());
            tableValue.setRemarks(tableInfo[6].toString().trim());

            IAIMonPTableSV tableSV = (IAIMonPTableSV) ServiceFactory.getService(IAIMonPTableSV.class);
            tableSV.saveOrUpdate(tableValue);

        }
        catch(Exception e) {
            log.error("Call TaskAction's Method saveTable has Exception : " + e.getMessage());
            throw e;
        }
    }

    /**
     * 删除表数据监控配置
     * 
     * @param tableId
     * @throws Exception
     */
    public void deleteTable(String tableId) throws Exception
    {
        if(StringUtils.isBlank(tableId)) {
            return;
        }
        try {
            IAIMonPTableSV tableSV = (IAIMonPTableSV) ServiceFactory.getService(IAIMonPTableSV.class);
            tableSV.delete(Long.parseLong(tableId));
        }
        catch(Exception e) {
            log.error("Call TaskAction's Method saveTable has Exception : " + e.getMessage());
            throw e;
        }
    }

    public List getAllMonPInfoGroup() throws Exception
    {
        IAIMonPInfoGroupSV groupSV = (IAIMonPInfoGroupSV) ServiceFactory.getService(IAIMonPInfoGroupSV.class);
        return groupSV.getAllMonPInfoGroup();
    }

    public DataContainerInterface[] getAllMonPInfoGroupNoCache() throws Exception
    {
        IAIMonPInfoGroupSV groupSV = (IAIMonPInfoGroupSV) ServiceFactory.getService(IAIMonPInfoGroupSV.class);
        IBOAIMonPInfoGroupValue[] values = groupSV.getAllMonPInfoGroupBean();
        if(values == null)
            return null;
        IAIMonUserPriEntitySV entitySv = (IAIMonUserPriEntitySV) ServiceFactory.getService(IAIMonUserPriEntitySV.class);
        DataContainerInterface[] dcs = new DataContainerInterface[values.length];
        String parentName = "";
        for(int i = 0; i < values.length; i++) {
            dcs[i] = new DataContainer();
            dcs[i].copy(values[i]);
            PriEntity entity = entitySv.getEntityByEntityIdNocache(String.valueOf(values[i].getParentId()));
            parentName = (entity == null ? "" : entity.getName());
            dcs[i].set("PARENT_NAME", parentName);
        }
        return dcs;
    }

    public int getPInfoGroupCount(String groupCode, String groupName, String layer) throws Exception
    {
        IAIMonPInfoGroupSV groupSV = (IAIMonPInfoGroupSV) ServiceFactory.getService(IAIMonPInfoGroupSV.class);
        return groupSV.getPInfoGroupCount(groupCode, groupName, layer);
    }

    public DataContainerInterface[] getPInfoGroupByCodeAndName(String groupCode, String groupName, String layer, Integer startNum, Integer endNum)
            throws Exception
    {
        IAIMonPInfoGroupSV groupSV = (IAIMonPInfoGroupSV) ServiceFactory.getService(IAIMonPInfoGroupSV.class);
        IBOAIMonPInfoGroupValue[] values = groupSV.getPInfoGroupByCodeAndName(groupCode, groupName, layer, startNum, endNum);
        if(values == null)
            return null;
        IAIMonUserPriEntitySV entitySv = (IAIMonUserPriEntitySV) ServiceFactory.getService(IAIMonUserPriEntitySV.class);
        DataContainerInterface[] dcs = new DataContainerInterface[values.length];
        String parentName = "";
        for(int i = 0; i < values.length; i++) {
            dcs[i] = new DataContainer();
            dcs[i].copy(values[i]);
            PriEntity entity = entitySv.getEntityByEntityIdNocache(String.valueOf(values[i].getParentId()));
            parentName = (entity == null ? "" : entity.getName());
            dcs[i].set("PARENT_NAME", parentName);
        }
        return dcs;
    }

    /**
     * 根据实体标识获得归属于该区域的再分组信息
     * 
     * @return IBOAIMonAreaValue[]
     * @throws Exception
     */
    public List getMonPInfoGroupByParentId(String parentId) throws Exception
    {
        IAIMonPInfoGroupSV groupSV = (IAIMonPInfoGroupSV) ServiceFactory.getService(IAIMonPInfoGroupSV.class);
        return groupSV.getMonPInfoGroupByParentId(Long.parseLong(parentId));
    }

    public List getMonPInfoGroupByCode(String groupCode) throws Exception
    {
        IAIMonPInfoGroupSV groupSV = (IAIMonPInfoGroupSV) ServiceFactory.getService(IAIMonPInfoGroupSV.class);
        List result = groupSV.getMonPInfoGroupByCode(groupCode);
        return result;
    }

    /**
     * 保存或修改所属任务分组信息
     * 
     * @param areaInfo
     * @throws Exception
     */
    public void saveMonPInfoGroup(Object[] groupInfo) throws Exception
    {
        if(groupInfo == null || groupInfo.length < 0) {
            return;
        }
        try {
            IBOAIMonPInfoGroupValue value = new BOAIMonPInfoGroupBean();

            if(StringUtils.isBlank(groupInfo[0].toString())) {
                value.setStsToNew();
            }
            else {
                value.setGroupId(Long.parseLong(groupInfo[0].toString().trim()));
                value.setStsToOld();
            }
            value.setGroupCode(groupInfo[1].toString().trim());
            value.setGroupName(groupInfo[2].toString().trim());
            value.setParentId(Long.parseLong(groupInfo[3].toString().trim()));
            value.setGroupDesc(groupInfo[4].toString().trim());
            value.setState(groupInfo[5].toString().trim());
            value.setRemark(groupInfo[6].toString().trim());
            if(groupInfo[7] != null && StringUtils.isNotBlank(groupInfo[7].toString()))
                value.setSortId(Integer.parseInt(groupInfo[7].toString().trim()));
            else
                value.setSortId(0);
            value.setLayer(groupInfo[8].toString().trim());
            value.setGroupStyle(groupInfo[9].toString().trim());

            IAIMonPInfoGroupSV groupSV = (IAIMonPInfoGroupSV) ServiceFactory.getService(IAIMonPInfoGroupSV.class);
            groupSV.saveOrUpdate(value);
            //所属层有更改，则更新组所有关联的任务的所属层
            if("1".equals(groupInfo[10].toString().trim())) {
                IAIMonPInfoSV infosv = (IAIMonPInfoSV) ServiceFactory.getService(IAIMonPInfoSV.class);
                infosv.updateLayer(value.getGroupId(), value.getLayer());
            }

        }
        catch(Exception e) {
            log.error("Call TaskAction's Method saveMonPInfoGroup has Exception ", e);
            throw e;
        }
    }

    /**
     * 删除任务分组信息
     * 
     * @param areaId
     * @throws Exception
     */
    public void deleteMonPInfoGroup(String groupId) throws Exception
    {
        if(StringUtils.isBlank(groupId)) {
            return;
        }
        try {
            IAIMonPInfoGroupSV groupSV = (IAIMonPInfoGroupSV) ServiceFactory.getService(IAIMonPInfoGroupSV.class);
            groupSV.delete(Long.parseLong(groupId));
        }
        catch(Exception e) {
            log.error("Call TaskAction's Method deleteAreaInfo has Exception : " + e.getMessage());
            throw e;
        }
    }

    /**
     * 根据数据源代码检索数据源信息
     * 
     * @param acctCode
     * @return
     * @throws Exception
     */
    public IBOAIMonDBAcctValue[] getDBAcctByCode(String acctCode) throws Exception
    {
        IAIMonDBAcctSV acctSV = (IAIMonDBAcctSV) ServiceFactory.getService(IAIMonDBAcctSV.class);
        return acctSV.getDBAcctByCode(acctCode);
    }

    public List saveDBAcct(Object[] acctInfo) throws Exception
    {
        List msgList = new ArrayList();
        if(acctInfo == null || acctInfo.length <= 0) {
            // "没有需要保存的数据！"
            msgList.add(AIMonLocaleFactory.getResource("MOS0000048"));
        }
        try {
            IBOAIMonDBAcctValue acctValue = new BOAIMonDBAcctBean();

            IAIMonDBAcctSV acctSV = (IAIMonDBAcctSV) ServiceFactory.getService(IAIMonDBAcctSV.class);
            if(StringUtils.isBlank(acctInfo[0].toString())) {
                // 检查数据源代码是否存在
                String chechCode = acctInfo[1].toString().trim();
                if(acctSV.checkCodeExists(chechCode)) {
                    // "数据源代码已存在！"
                    msgList.add(AIMonLocaleFactory.getResource("MOS0000050"));
                    return msgList;
                }
                else {
                    acctValue.setStsToNew();
                    acctValue.setDbAcctCode(acctInfo[1].toString().trim());
                    acctValue.setPassword(K.j(acctInfo[3].toString().trim()));
                }
            }
            else {
                acctValue.setDbAcctCode(acctInfo[1].toString().trim());
                acctValue.setStsToOld();
                String oldPassword = acctInfo[8].toString().trim();
                String newPassword = acctInfo[3].toString().trim();
                if(newPassword.equals(oldPassword)) {
                    acctValue.setPassword(newPassword);
                }
                else {
                    acctValue.setPassword(K.j(newPassword));
                }
            }

            acctValue.setUsername(acctInfo[2].toString().trim());
            acctValue.setConnMin(Integer.parseInt(acctInfo[4].toString().trim()));
            acctValue.setConnMax(Integer.parseInt(acctInfo[5].toString().trim()));
            acctValue.setState(acctInfo[6].toString().trim());
            acctValue.setRemarks(acctInfo[7].toString().trim());

            acctSV.saveOrUpdate(acctValue);
        }
        catch(Exception e) {
            log.error("Call TaskAction's Method saveDBAcct has Exception : " + e.getMessage());
            throw e;
        }

        return msgList;
    }

    /**
     * 删除数据源配置信息
     * 
     * @param acctCode
     * @throws Exception
     */
    public void deleteDBAcct(String acctCode) throws Exception
    {
        if(StringUtils.isBlank(acctCode)) {
            return;
        }
        try {
            IAIMonDBAcctSV acctSV = (IAIMonDBAcctSV) ServiceFactory.getService(IAIMonDBAcctSV.class);
            acctSV.delete(acctCode);
        }
        catch(Exception e) {
            log.error("Call TaskAction's Method deleteDBAcct has Exception : " + e.getMessage());
            throw e;
        }
    }

    /**
     * 根据节点URL名称检索数据库节点URL配置信息
     * 
     * @param urlName
     * @return
     * @throws Exception
     */
    public IBOAIMonDBURLValue[] getDBUrlByName(String urlName) throws Exception
    {
        IAIMonDBUrlSV urlSV = (IAIMonDBUrlSV) ServiceFactory.getService(IAIMonDBUrlSV.class);
        return urlSV.getDBUrlByName(urlName);
    }

    public void saveUrl(Object[] urlInfo) throws Exception
    {

        if(urlInfo == null || urlInfo.length <= 0) {
            // "没有需要保存的数据！"
            throw new Exception(AIMonLocaleFactory.getResource("MOS0000048"));
        }
        try {
            IBOAIMonDBURLValue urlValue = new BOAIMonDBURLBean();
            IAIMonDBUrlSV urlSV = (IAIMonDBUrlSV) ServiceFactory.getService(IAIMonDBUrlSV.class);

            if(StringUtils.isBlank(urlInfo[0].toString())) {
                String checkName = urlInfo[1].toString().trim();
                if(urlSV.checkUrlNameExists(checkName)) {
                    // "节点URL名称已存在！"
                    throw new Exception(AIMonLocaleFactory.getResource("MOS0000051"));
                }
                else {
                    urlValue.setName(urlInfo[1].toString().trim());
                    urlValue.setStsToNew();
                }
            }
            else {
                urlValue.setName(urlInfo[1].toString().trim());
                urlValue.setStsToOld();
            }
            urlValue.setUrl(urlInfo[2].toString().trim());
            urlValue.setState(urlInfo[3].toString().trim());
            urlValue.setRemarks(urlInfo[4].toString().trim());

            urlSV.saveOrUpdate(urlValue);

        }
        catch(Exception e) {
            log.error("Call TaskAction's Method saveUrl has Exception : " + e.getMessage());
            throw e;
        }
    }

    /**
     * 删除数据库节点URL配置信息
     * 
     * @param urlName
     * @throws Exception
     */
    public void deleteUrl(String urlName) throws Exception
    {
        if(StringUtils.isBlank(urlName)) {
            return;
        }
        try {
            IAIMonDBUrlSV urlSV = (IAIMonDBUrlSV) ServiceFactory.getService(IAIMonDBUrlSV.class);
            urlSV.delete(urlName);
        }
        catch(Exception e) {
            log.error("Call TaskAction's Method deleteUrl has Exception : " + e.getMessage());
            throw e;
        }
    }

    /**
     * 根据条件查询告警记录
     * 
     * @param recordId
     * @param startIndex
     * @param endIndex
     * @return
     * @throws Exception
     */
    public IBOAIMonWTriggerValue[] getMonWTriggerByCondition(String recordId, Integer startIndex, Integer endIndex) throws Exception
    {
        IAIMonWTriggerSV triggerSV = (IAIMonWTriggerSV) ServiceFactory.getService(IAIMonWTriggerSV.class);
        return triggerSV.getMonWTriggerByRecordId(recordId, startIndex, endIndex);
    }

    /**
     * 根据条件检索告警短信发送人员配置信息
     * 
     * @param personName
     * @param region
     * @return
     * @throws Exception
     */
    public IBOAIMonWPersonValue[] getPersonByCond(String personName, String region, Integer startNum, Integer endNum) throws Exception
    {
        IAIMonWPersonSV personSV = (IAIMonWPersonSV) ServiceFactory.getService(IAIMonWPersonSV.class);
        return personSV.getPersonByCond(personName, region, startNum, endNum);
    }

    /**
     * 根据条件取得总件数
     * 
     * @param personName
     * @param region
     * @return
     * @throws Exception
     */
    public int getPersonCount(String personName, String region) throws Exception
    {
        IAIMonWPersonSV personSV = (IAIMonWPersonSV) ServiceFactory.getService(IAIMonWPersonSV.class);
        return personSV.getPersonCount(personName, region);
    }

    /**
     * 保存或修改告警短信发送人员配置信息
     * 
     * @param personInfo
     * @throws Exception
     */
    public void savePerson(Object[] personInfo) throws Exception
    {
        if(personInfo == null || personInfo.length <= 0) {
            return;
        }
        try {
            IBOAIMonWPersonValue personValue = new BOAIMonWPersonBean();

            if(StringUtils.isBlank(personInfo[0].toString())) {
                personValue.setStsToNew();
            }
            else {
                personValue.setPersonId(Long.parseLong(personInfo[0].toString().trim()));
                personValue.setStsToOld();
            }
            personValue.setName(personInfo[1].toString().trim());
            personValue.setPhonenum(personInfo[2].toString().trim());
            personValue.setRegion(personInfo[3].toString().trim());
            personValue.setState(personInfo[4].toString().trim());
            personValue.setRemarks(personInfo[5].toString().trim());

            IAIMonWPersonSV personSV = (IAIMonWPersonSV) ServiceFactory.getService(IAIMonWPersonSV.class);
            personSV.saveOrUpdate(personValue);
        }
        catch(Exception e) {
            log.error("Call TaskAction's Method savePerson has Exception : " + e.getMessage());
            throw e;
        }
    }

    /**
     * 删除告警短信发送人员配置信息
     * 
     * @param personId
     * @throws Exception
     */
    public void deletePerson(String personId) throws Exception
    {
        if(StringUtils.isBlank(personId)) {
            return;
        }
        try {
            IAIMonWPersonSV personSV = (IAIMonWPersonSV) ServiceFactory.getService(IAIMonWPersonSV.class);
            personSV.delete(Long.parseLong(personId));
        }
        catch(Exception e) {
            log.error("Call TaskAction's Method deletePerson has Exception : " + e.getMessage());
            throw e;
        }
    }

    /**
     * 根据条件检索监控信息发送人员关联配置信息
     * 
     * @param taskId
     * @param personId
     * @return
     * @throws Exception
     */
    public DataContainerInterface[] getDtlByCond(String taskId, String personId, Integer startNum, Integer endNum) throws Exception
    {
        IAIMonWDtlSV dtlSV = (IAIMonWDtlSV) ServiceFactory.getService(IAIMonWDtlSV.class);
        IAIMonPInfoSV infoSV = (IAIMonPInfoSV) ServiceFactory.getService(IAIMonPInfoSV.class);
        IAIMonWPersonSV personSV = (IAIMonWPersonSV) ServiceFactory.getService(IAIMonWPersonSV.class);
        IBOAIMonWDtlValue[] dtlValues = dtlSV.getDtlByCond(taskId, personId, startNum, endNum);
        List ret = new ArrayList();
        IBOAIMonPInfoValue infoValue = null;
        IBOAIMonWPersonValue personValue = null;
        for(int i = 0; i < dtlValues.length; i++) {
            DataContainer dc = new DataContainer();
            dc.set(IBOAIMonWDtlValue.S_DtlId, dtlValues[i].getDtlId());
            dc.set(IBOAIMonWDtlValue.S_InfoId, dtlValues[i].getInfoId());
            infoValue = infoSV.getMonPInfoValue(dtlValues[i].getInfoId());
            dc.set("INFO_NAME", infoValue.getName());
            dc.set(IBOAIMonWDtlValue.S_PersonId, dtlValues[i].getPersonId());
            personValue = personSV.getBeanById(dtlValues[i].getPersonId());
            dc.set("PERSON_NAME", personValue.getName());
            dc.set(IBOAIMonWDtlValue.S_WarnLevel, dtlValues[i].getWarnLevel());
            dc.set(IBOAIMonWDtlValue.S_State, dtlValues[i].getState());
            dc.set(IBOAIMonWDtlValue.S_Remarks, dtlValues[i].getRemarks());

            ret.add(dc);
        }
        return (DataContainerInterface[]) ret.toArray(new DataContainer[0]);
    }

    /**
     * 根据条件取得总件数
     * 
     * @param taskId
     * @param personId
     * @return
     * @throws Exception
     */
    public int getDtlCount(String taskId, String personId) throws Exception
    {
        IAIMonWDtlSV dtlSV = (IAIMonWDtlSV) ServiceFactory.getService(IAIMonWDtlSV.class);
        return dtlSV.getDtlCount(taskId, personId);
    }

    /**
     * 设置监控信息发送人员关联配置信息
     * 
     * @param dtlInfo
     * @throws Exception
     */
    public void saveDtl(Object[] dtlInfo) throws Exception
    {
        if(dtlInfo == null || dtlInfo.length <= 0) {
            return;
        }
        try {
            IBOAIMonWDtlValue dtlValue = new BOAIMonWDtlBean();
            if(StringUtils.isBlank(dtlInfo[0].toString())) {
                dtlValue.setStsToNew();
            }
            else {
                dtlValue.setDtlId(Long.parseLong(dtlInfo[0].toString().trim()));
                dtlValue.setStsToOld();
            }
            dtlValue.setInfoId(Long.parseLong(dtlInfo[1].toString().trim()));
            dtlValue.setPersonId(Long.parseLong(dtlInfo[2].toString().trim()));
            dtlValue.setWarnLevel(dtlInfo[3].toString().trim());
            dtlValue.setState(dtlInfo[4].toString().trim());
            dtlValue.setRemarks(dtlInfo[5].toString().trim());

            IAIMonWDtlSV dtlSV = (IAIMonWDtlSV) ServiceFactory.getService(IAIMonWDtlSV.class);
            dtlSV.saveOrUpdate(dtlValue);
        }
        catch(Exception e) {
            log.error("Call TaskAction's Method saveDtl has Exception : " + e.getMessage());
            throw e;
        }
    }

    /**
     * 删除监控信息发送人员关联配置
     * 
     * @param dtlId
     * @throws Exception
     */
    public void deleteDtl(String dtlId) throws Exception
    {
        if(StringUtils.isBlank(dtlId)) {
            return;
        }
        try {
            IAIMonWDtlSV dtlSV = (IAIMonWDtlSV) ServiceFactory.getService(IAIMonWDtlSV.class);
            dtlSV.delete(Long.parseLong(dtlId));
        }
        catch(Exception e) {
            log.error("Call TaskAction's Method deleteDtl has Exception : " + e.getMessage());
            throw e;
        }
    }

    public IBOAIMonPInfoValue[] getTaskInfoByResolveId(String resolveId) throws Exception
    {
        IBOAIMonPInfoValue[] ret = null;
        try {
            IAIMonPInfoSV infoSV = (IAIMonPInfoSV) ServiceFactory.getService(IAIMonPInfoSV.class);
            ret = infoSV.getTaskInfoByResolveId(resolveId);
            if(ret.length > 0) {
                for(int i = 0; i < ret.length; i++) {
                    ret[i].setExtAttr("CHK", "true");
                }
            }
        }
        catch(Exception e) {
            log.error(e);
        }
        return ret;
    }

    /**
     * 根据检索条件查询任务信息
     * 
     * @param taskName
     * @return
     * @throws Exception
     */
    public DataContainerInterface[] getTaskInfoByName(String taskName, String state, Integer startNum, Integer endNum) throws Exception
    {
        IAIMonPInfoSV infoSV = (IAIMonPInfoSV) ServiceFactory.getService(IAIMonPInfoSV.class);
        IAIMonPTableSV tableSV = (IAIMonPTableSV) ServiceFactory.getService(IAIMonPTableSV.class);
        IAIMonPExecSV execSV = (IAIMonPExecSV) ServiceFactory.getService(IAIMonPExecSV.class);
        IAIMonPThresholdSV thresholdSV = (IAIMonPThresholdSV) ServiceFactory.getService(IAIMonPThresholdSV.class);
        IAIMonPTimeSV timeSV = (IAIMonPTimeSV) ServiceFactory.getService(IAIMonPTimeSV.class);
        IAIMonPInfoGroupSV areaMappingSV = (IAIMonPInfoGroupSV) ServiceFactory.getService(IAIMonPInfoGroupSV.class);
        IAIMonPInfoFilterSV filterSV = (IAIMonPInfoFilterSV) ServiceFactory.getService(IAIMonPInfoFilterSV.class);
        IAIMonPImgDataResolveSV imgDataResolveSV = (IAIMonPImgDataResolveSV) ServiceFactory.getService(IAIMonPImgDataResolveSV.class);
        IBOAIMonPInfoValue[] infoValues = infoSV.getTaskInfoByName(taskName, state, startNum, endNum);
        IBOAIMonPTableValue tableValue = null;
        IBOAIMonPExecValue execValue = null;
        IBOAIMonThresholdValue thresholdValue = null;
        IBOAIMonPTimeValue timeValue = null;
        IBOAIMonPInfoGroupValue monPInfoGroupValue = null;
        IBOAIMonPInfoFilterValue filterValue = null;
        IBOAIMonPImgDataResolveValue imgDataResolveValue = null;
        IBOAIMonPhysicHostValue pHostValue = null;
        List ret = new ArrayList();
        for(int i = 0; i < infoValues.length; i++) {
            DataContainer dc = new DataContainer();
            dc.set(IBOAIMonPInfoValue.S_InfoId, infoValues[i].getInfoId());
            dc.set(IBOAIMonPInfoValue.S_Name, infoValues[i].getName());
            //去除缓存
            //pHostValue = (IPhysicHost) CacheFactory.get(AIMonPhostCacheImpl.class, String.valueOf(infoValues[i].getHostId()));
            IAIMonPhysicHostSV hostSV=(IAIMonPhysicHostSV)ServiceFactory.getService(IAIMonPhysicHostSV.class);
            pHostValue=hostSV.getPhysicHostById(String.valueOf(infoValues[i].getHostId()));
            if(pHostValue != null) {
                dc.set("HOST_NAME", pHostValue.getHostName());
            }
            dc.set(IBOAIMonPInfoValue.S_HostId, infoValues[i].getHostId());
            dc.set(IBOAIMonPInfoValue.S_MType, infoValues[i].getMType());
            dc.set(IBOAIMonPInfoValue.S_TypeId, infoValues[i].getTypeId());
            dc.set(IBOAIMonPInfoValue.S_TimeId, infoValues[i].getTimeId());
            dc.set(IBOAIMonPInfoValue.S_ThresholdId, infoValues[i].getThresholdId());
            dc.set(IBOAIMonPInfoValue.S_State, infoValues[i].getState());
            dc.set(IBOAIMonPInfoValue.S_SplitRuleId, infoValues[i].getSplitRuleId());
            dc.set(IBOAIMonPInfoValue.S_FilterId, infoValues[i].getFilterId());

            if(infoValues[i].getFilterId() != 0) {
                filterValue = filterSV.getFilterById(infoValues[i].getFilterId());
                dc.set("FILTER_NAME", filterValue.getFilterName());
            }
            else {
                dc.set("FILTER_NAME", "");
            }

            dc.set(IBOAIMonPInfoValue.S_Remarks, infoValues[i].getRemarks());
            dc.set(IBOAIMonPInfoValue.S_Layer, infoValues[i].getLayer());
            String type = infoValues[i].getMType();
            if("EXEC".equals(type)) {
                execValue = execSV.getBeanById(infoValues[i].getTypeId());
                dc.set("TYPE_NAME", execValue.getName());
                dc.set("TYPE_CONTENT", execValue.getExpr());
            }
            else {
                tableValue = tableSV.getBeanById(infoValues[i].getTypeId());
                dc.set("TYPE_NAME", tableValue.getName());
                dc.set("TYPE_CONTENT", tableValue.getSql());
            }
            thresholdValue = thresholdSV.getBeanById(infoValues[i].getThresholdId());
            String expr = "";
            if(StringUtils.isNotBlank(thresholdValue.getExpr1())) {
                // 表达式
                expr = AIMonLocaleFactory.getResource("MOS0000052") + "1：" + thresholdValue.getExpr1() + "\n";
            }
            if(StringUtils.isNotBlank(thresholdValue.getExpr2())) {
                expr = expr + AIMonLocaleFactory.getResource("MOS0000052") + "2：" + thresholdValue.getExpr2() + "\n";
            }
            if(StringUtils.isNotBlank(thresholdValue.getExpr3())) {
                expr = expr + AIMonLocaleFactory.getResource("MOS0000052") + "3：" + thresholdValue.getExpr3() + "\n";
            }
            if(StringUtils.isNotBlank(thresholdValue.getExpr4())) {
                expr = expr + AIMonLocaleFactory.getResource("MOS0000052") + "4：" + thresholdValue.getExpr4() + "\n";
            }
            if(StringUtils.isNotBlank(thresholdValue.getExpr5())) {
                expr = expr + AIMonLocaleFactory.getResource("MOS0000052") + "5：" + thresholdValue.getExpr5() + "\n";
            }
            dc.set("THRESHOLD_EXPR", expr);
            dc.set("THRESHOLD_NAME", thresholdValue.getThresholdName());

            timeValue = timeSV.getBeanById(infoValues[i].getTimeId());
            dc.set("TIME_NAME", timeValue.getExpr());

            imgDataResolveValue = imgDataResolveSV.getMonPImgDataResolveById(infoValues[i].getResolveId());
            dc.set("RESOLVE_NAME", imgDataResolveValue.getName());
            dc.set(IBOAIMonPInfoValue.S_ResolveId, infoValues[i].getResolveId());
            dc.set(IBOAIMonPInfoValue.S_Layer, infoValues[i].getLayer());
            //			dc.set(IBOAIMonPInfoValue.S_BusiareaId,infoValues[i].getBusiareaId());
            //			areaValue = areaSV.getBusiAreaBeanById(infoValues[i].getBusiareaId());
            //			dc.set(IBOAIMonBusiAreaValue.S_BusiareaName, areaValue.getBusiareaName());
            dc.set(IBOAIMonPInfoValue.S_GroupId, infoValues[i].getGroupId());
            monPInfoGroupValue = areaMappingSV.getMonPInfoGroupById(infoValues[i].getGroupId());
            dc.set("GROUP_NAME", monPInfoGroupValue.getGroupName());
            ret.add(dc);
        }

        return (DataContainerInterface[]) ret.toArray(new DataContainer[0]);
    }

    public int getTaskInfoCount(String taskName, String state) throws Exception
    {
        IAIMonPInfoSV infoSV = (IAIMonPInfoSV) ServiceFactory.getService(IAIMonPInfoSV.class);
        return infoSV.getTaskInfoCount(taskName, state);
    }

    /**
     * 保存或修改任务信息
     * 
     * @param taskInfo
     * @throws Exception
     */
    //	public void saveTask(Object[] taskInfo,ArrayCollection paramVals) throws Exception {
    //		if (taskInfo == null || taskInfo.length <= 0) {
    //			return;
    //		}
    //		try {
    //			IBOAIMonPInfoValue infoValue = new BOAIMonPInfoBean();
    //			if (StringUtils.isBlank(taskInfo[0].toString())) {
    //				infoValue.setStsToNew();
    //			} else {
    //				infoValue.setInfoId(Long.parseLong(taskInfo[0].toString().trim()));
    //				infoValue.setStsToOld();
    //			}
    //			infoValue.setName(taskInfo[1].toString().trim());
    //			if(taskInfo[2] != null && StringUtils.isNotBlank(taskInfo[2].toString()))
    //			{
    //				infoValue.setHostId(Long.parseLong(taskInfo[2].toString().trim()));
    //			}else
    //				infoValue.setHostId(0);
    //			infoValue.setMType(taskInfo[3].toString().trim());
    //			if (StringUtils.isNotBlank(taskInfo[4].toString())) {
    //				infoValue.setTypeId(Long.parseLong(taskInfo[4].toString().trim()));
    //			}
    //			if (StringUtils.isNotBlank(taskInfo[5].toString())) {
    //				infoValue.setThresholdId(Long.parseLong(taskInfo[5].toString().trim()));
    //			}
    //			if (StringUtils.isNotBlank(taskInfo[6].toString())) {
    //				infoValue.setTimeId(Long.parseLong(taskInfo[6].toString().trim()));
    //			}
    //			if (StringUtils.isNotBlank(taskInfo[7].toString())) {
    //				infoValue.setResolveId(Long.parseLong(taskInfo[7].toString().trim()));
    //			}
    //			if (StringUtils.isNotBlank(taskInfo[8].toString())){
    //				infoValue.setGroupId(Long.parseLong(taskInfo[8].toString().trim()));
    //			}
    //			infoValue.setState(taskInfo[9].toString().trim());
    //			if (StringUtils.isNotBlank(taskInfo[10].toString())) {
    //				infoValue.setSplitRuleId(Long.parseLong(taskInfo[10].toString().trim()));
    //			}
    //			infoValue.setRemarks(taskInfo[11].toString().trim());
    //			infoValue.setLayer(taskInfo[12].toString().trim());
    //			if (StringUtils.isNotBlank(taskInfo[13].toString().trim())) {
    //				infoValue.setFilterId(Long.parseLong(taskInfo[13].toString().trim()));
    //			}
    //			
    //			DataContainer[] paramDcs = null;
    //			//处理参数
    //			if(paramVals != null && paramVals.size() > 0)
    //			{
    //				ASObject tmpObj = null;
    //				paramDcs = new DataContainer[paramVals.size()];
    //				for(int i=0;i<paramVals.size();i++){
    //					tmpObj = (ASObject)paramVals.get(i);
    //					paramDcs[i] = FlexHelper.transferAsToDc(tmpObj);
    //				}
    //			}
    //			
    //			IAIMonPInfoSV infoSV = (IAIMonPInfoSV)ServiceFactory.getService(IAIMonPInfoSV.class);
    //			infoSV.saveOrUpdate(infoValue,paramDcs);
    //			
    //		} catch (Exception e) {
    //			log.error("Call TaskAction's Method saveTask has Exception : " + e.getMessage());
    //			throw e;
    //		}
    //	}

    /**
     * 删除任务信息
     * 
     * @param taskInfoId
     * @throws Exception
     */
    public void deleteTask(String taskInfoId) throws Exception
    {
        if(StringUtils.isBlank(taskInfoId)) {
            return;
        }
        try {
            IAIMonPInfoSV infoSV = (IAIMonPInfoSV) ServiceFactory.getService(IAIMonPInfoSV.class);
            infoSV.delete(Long.parseLong(taskInfoId));
        }
        catch(Exception e) {
            log.error("Call TaskAction's Method deleteTask has Exception : " + e.getMessage());
        }
    }

    /**
     * 根据条件查询所有的监控组
     * 
     * @param condition
     * @param parameter
     * @return
     * @throws Exception
     */
    public IBOAIMonPImgDataResolveValue[] getMonAllPGrp() throws Exception
    {
        IAIMonPImgDataResolveSV grpSV = (IAIMonPImgDataResolveSV) ServiceFactory.getService(IAIMonPImgDataResolveSV.class);
        return grpSV.getAllMonPImgDataResolve();
    }

    /**
     * 根据ID查询监控组
     * showCharts
     * @return
     * @throws Exception
     */
    public IBOAIMonPImgDataResolveValue getMonPImgDataResolveById(String resolveId) throws Exception
    {
        IAIMonPImgDataResolveSV resolveSV = (IAIMonPImgDataResolveSV) ServiceFactory.getService(IAIMonPImgDataResolveSV.class);
        return resolveSV.getMonPImgDataResolveById(Long.parseLong(resolveId));
    }

    /**
      * 根据条件获得监控记录数目
      *
      * @param grpId
      * @return List
      * @throws Exception
      */
    public long countMonPInfoByCondition(String resolveId) throws Exception
    {
        IAIMonPInfoSV infoSV = (IAIMonPInfoSV) ServiceFactory.getService(IAIMonPInfoSV.class);
        return infoSV.getTaskInfoCountByResolveId(Long.parseLong(resolveId));
    }

    /**
      * 获取图形Xml列表
      * @param 
      * @param 
      * @throws Exception
      */
    public List showCharts(Object[] infoIds, String last, String viewTpyeId, String groupId) throws Exception
    {
        if(infoIds == null || infoIds.length < 1)
            // "请选择内容"
            throw new Exception(AIMonLocaleFactory.getResource("MOS0000053"));
        if(StringUtils.isBlank(last))
            return null;
        IAIMonLRecordSV recordSV = (IAIMonLRecordSV) ServiceFactory.getService(IAIMonLRecordSV.class);
        List ret = recordSV.getMonLRecordImage(infoIds, groupId, viewTpyeId,
                TimeUtil.addOrMinusHours(System.currentTimeMillis(), Integer.parseInt(last)), recordSV.getSystemTime());
        return ret;
    }

    /**
     * 获取历史图形数据
     * @param infoId
     * @param layer
     * @param durHour
     * @return
     * @throws Exception
     */
    public List showHistoryCharts(String infoId, String layer, String durHour) throws Exception
    {
        if(StringUtils.isBlank(infoId))
            // "请选择内容"
            throw new Exception(AIMonLocaleFactory.getResource("MOS0000053"));
        if(StringUtils.isBlank(layer))
            return null;
        IAIMonLRecordSV recordSV = (IAIMonLRecordSV) ServiceFactory.getService(IAIMonLRecordSV.class);
        List ret = recordSV.getMonLRecordImage(infoId, layer, Integer.parseInt(durHour));
        return ret;
    }

    /**
      * 获取图形Xml列表
      * @param 
      * @param 
      * @throws Exception
      */
    public List showChartsByCycle(Object[] infoIds, String start, String end, String viewTpyeId, String groupId) throws Exception
    {
        if(infoIds == null || infoIds.length < 1)
            // "请选择内容"
            throw new Exception(AIMonLocaleFactory.getResource("MOS0000053"));
        if(StringUtils.isBlank(start))
            return null;
        if(StringUtils.isBlank(end))
            return null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        IAIMonLRecordSV recordSV = (IAIMonLRecordSV) ServiceFactory.getService(IAIMonLRecordSV.class);
        return recordSV.getMonLRecordImage(infoIds, groupId, viewTpyeId, format.parse(start), format.parse(end));
    }

    /**
      * 获取表格的Xml列表
      * @param 
      * @param 
      * @throws Exception
      */
    public List showGridRecord(String layer, String infocode) throws Exception
    {
        if(StringUtils.isBlank(layer))
            return null;
        if(StringUtils.isBlank(infocode))
            return null;
        IAIMonLRecordSV recordSV = (IAIMonLRecordSV) ServiceFactory.getService(IAIMonLRecordSV.class);
        return recordSV.getMonLRecordGrid();
    }

    /**
     * 根据图形显示分组名称检索结果
     * 
     * @param groupName
     * @return
     * @throws Exception
     */
    public IBOAIMonPImgDataResolveValue[] getGroupInfoByName(String groupName, Integer startNum, Integer endNum) throws Exception
    {
        IAIMonPImgDataResolveSV groupSV = (IAIMonPImgDataResolveSV) ServiceFactory.getService(IAIMonPImgDataResolveSV.class);
        return groupSV.getPImgDataResovleByName(groupName, startNum, endNum);
    }

    /**
     * 检索总件数
     * 
     * @param groupName
     * @return
     * @throws Exception
     */
    public int getGroupInfoCount(String groupName) throws Exception
    {
        IAIMonPImgDataResolveSV groupSV = (IAIMonPImgDataResolveSV) ServiceFactory.getService(IAIMonPImgDataResolveSV.class);
        return groupSV.getPImgDataResolveCountByName(groupName);
    }

    /**
     * 保存或修改图形显示分组配置
     * 
     * @param groupInfo
     * @throws Exception
     */
    public void saveGroup(Object[] groupInfo) throws Exception
    {
        if(groupInfo == null || groupInfo.length <= 0) {
            return;
        }
        try {
            IBOAIMonPImgDataResolveValue groupValue = new BOAIMonPImgDataResolveBean();

            if(StringUtils.isBlank(groupInfo[0].toString())) {
                groupValue.setStsToNew();
            }
            else {
                groupValue.setResolveId(Long.parseLong(groupInfo[0].toString().trim()));
                groupValue.setStsToOld();
            }
            groupValue.setName(groupInfo[1].toString().trim());
            groupValue.setTransformClass(groupInfo[2].toString().trim());
            groupValue.setShowType(groupInfo[3].toString().trim());
            groupValue.setSortBy(groupInfo[4].toString().trim());
            groupValue.setState(groupInfo[5].toString().trim());
            groupValue.setRemarks(groupInfo[6].toString().trim());
            groupValue.setShowNamePos(Integer.parseInt(groupInfo[7].toString().trim()));
            groupValue.setShowValuePos(Integer.parseInt(groupInfo[8].toString().trim()));
            IAIMonPImgDataResolveSV groupSV = (IAIMonPImgDataResolveSV) ServiceFactory.getService(IAIMonPImgDataResolveSV.class);
            groupSV.saveOrUpdate(groupValue);

        }
        catch(Exception e) {
            log.error("Call TaskAction's Method saveGroup has Exception : " + e.getMessage());
            throw e;
        }
    }

    /**
     * 删除图形显示分组配置
     * 
     * @param groupId
     * @throws Exception
     */
    public void deleteGroup(String groupId) throws Exception
    {
        if(StringUtils.isBlank(groupId)) {
            return;
        }
        try {
            IAIMonPImgDataResolveSV groupSV = (IAIMonPImgDataResolveSV) ServiceFactory.getService(IAIMonPImgDataResolveSV.class);
            groupSV.delete(Long.parseLong(groupId));
        }
        catch(Exception e) {
            log.error("Call TaskAction's Method deleteGroup has Exception : " + e.getMessage());
            throw e;
        }
    }

    public List getPInfoGroupByEntityID(String entityID) throws Exception
    {
        List ret = null;
        try {
            IAIMonPInfoGroupSV pgs = (IAIMonPInfoGroupSV) ServiceFactory.getService(IAIMonPInfoGroupSV.class);
            ret = pgs.getMonPInfoGroupByParentId(Long.parseLong(entityID));
        }
        catch(Exception e) {
            log.error("getAttentionPanelByEntityID error!", e);
            throw e;
        }
        return ret;
    }

    /**
     * 通过分组ID查询分组下要显示监控的记录
     * @param groupID
     * @param startIndex
     * @param endIndex
     * @return
     * @throws Exception 
     */
    public DataContainerInterface[] queryTaskInfoByCond(String groupID, Integer startIndex, Integer endIndex) throws Exception
    {
        DataContainerInterface[] ret = null;
        try {
            IAIMonPInfoSV iap = (IAIMonPInfoSV) ServiceFactory.getService(IAIMonPInfoSV.class);
            ret = iap.queryTaskInfoByCond(groupID, startIndex.intValue(), endIndex.intValue());
        }
        catch(Exception e) {
            log.error("queryTaskInfoByCond error!", e);
            throw e;
        }
        return ret;
    }

    /**
     * 
     * @param groupID
     * @return
     * @throws Exception
     */
    public int getTaskInfoCountByCond(String groupID) throws Exception
    {
        IAIMonPInfoSV sv = (IAIMonPInfoSV) ServiceFactory.getService(IAIMonPInfoSV.class);
        return sv.getTaskInfoCountByCond(groupID);
    }

    /**
     * 根据分组编号和任务名查找任务
     * @param groupId
     * @param taskName
     * @return
     * @throws Exception
     */
    public DataContainerInterface[] queryTaskInfoByGrpAndInfoName(String groupId, String taskName, Integer startIndex, Integer endIndex)
            throws Exception
    {
        DataContainerInterface[] ret = null;
        try {
            IAIMonPInfoSV iap = (IAIMonPInfoSV) ServiceFactory.getService(IAIMonPInfoSV.class);
            ret = iap.queryTaskInfoByGrpAndInfoName(groupId, taskName, startIndex.intValue(), endIndex.intValue());
        }
        catch(Exception e) {
            log.error("queryTaskInfoByGrpAndInfoName error!", e);
            throw e;
        }
        return ret;
    }

    /**
     * 根据分组编号和任务名查找任务个数
     * @param groupId
     * @param taskName
     * @return
     * @throws Exception
     */
    public int getTaskInfoCountByGrpAndInfoName(String groupId, String taskName) throws Exception
    {
        IAIMonPInfoSV sv = (IAIMonPInfoSV) ServiceFactory.getService(IAIMonPInfoSV.class);
        return sv.getTaskInfoCountByGrpAndInfoName(groupId, taskName);
    }

    /**
     * 根据寄主标识以及寄主类型获取参数定义
     * 
     * @param registeId
     * @param registeType
     * @return
     * @throws Exception
     */
    public IBOAIMonParamDefineValue[] getParamDefine(String registeId, String registeType) throws Exception
    {
        IAIMonParamDefineSV defineSV = (IAIMonParamDefineSV) ServiceFactory.getService(IAIMonParamDefineSV.class);
        return defineSV.getParamDefineByType(registeType, registeId);
    }

    public void saveParamDefine(DataContainer dc) throws Exception
    {

        if(dc == null)
            // "没有需要保存的数据！"
            throw new Exception(AIMonLocaleFactory.getResource("MOS0000048"));

        IBOAIMonParamDefineValue defineValue = new BOAIMonParamDefineBean();

        if(StringUtils.isBlank(dc.getAsString(IBOAIMonParamDefineValue.S_ParamId))) {

            dc.setStsToNew();
        }
        else {
            Object value = dc.get(IBOAIMonParamDefineValue.S_ParamId);
            dc.clearProperty(IBOAIMonParamDefineValue.S_ParamId);
            dc.initPropertyOld(IBOAIMonParamDefineValue.S_ParamId, value);

        }
        DataContainerFactory.copy(dc, defineValue);
        IAIMonParamDefineSV defineSV = (IAIMonParamDefineSV) ServiceFactory.getService(IAIMonParamDefineSV.class);
        // 保存
        defineSV.saveOrUpdate(defineValue);

    }

    public void deleteParamDefine(String paramId) throws Exception
    {
        if(StringUtils.isBlank(paramId))
            // "传入的参数定义标识为空,无法删除!"
            throw new Exception(AIMonLocaleFactory.getResource("MOS0000054"));
        IAIMonParamDefineSV defineSV = (IAIMonParamDefineSV) ServiceFactory.getService(IAIMonParamDefineSV.class);
        defineSV.deleteParamDefine(Long.parseLong(paramId));
    }

    public DataContainerInterface[] getParamValues(String registeType, String registeId) throws Exception
    {
        if(StringUtils.isBlank(registeType) || StringUtils.isBlank(registeId))
            // "传入的寄主类型与标识为空!"
            throw new Exception(AIMonLocaleFactory.getResource("MOS0000055"));

        IAIMonParamValuesSV valueSV = (IAIMonParamValuesSV) ServiceFactory.getService(IAIMonParamValuesSV.class);
        IBOAIMonParamValuesValue[] values = valueSV.getParamValuesByType(registeType, registeId);
        if(values == null || values.length == 0)
            return null;

        IAIMonParamDefineSV defSV = (IAIMonParamDefineSV) ServiceFactory.getService(IAIMonParamDefineSV.class);

        DataContainerInterface[] rets = new DataContainerInterface[values.length];
        for(int i = 0; i < values.length; i++) {
            IBOAIMonParamDefineValue define = defSV.getParamDefineById(String.valueOf(values[i].getParamId()));
            rets[i] = new DataContainer();
            rets[i].copy(values[i]);
            if(define != null) {
                rets[i].set(IBOAIMonParamDefineValue.S_IsMust, define.getIsMust());
                rets[i].set(IBOAIMonParamDefineValue.S_DataType, define.getDataType());
                rets[i].set(IBOAIMonParamDefineValue.S_ParamDesc, define.getParamDesc());
            }
        }
        return rets;
    }

    public void deleteParam(String paramId) throws Exception
    {
        if(StringUtils.isBlank(paramId))
            // "传入的参数定义标识为空,无法删除!"
            throw new Exception(AIMonLocaleFactory.getResource("MOS0000054"));
        IAIMonParamDefineSV defineSV = (IAIMonParamDefineSV) ServiceFactory.getService(IAIMonParamDefineSV.class);
        defineSV.deleteParam(Long.parseLong(paramId));
    }

    public IBOAIMonPInfoFilterValue[] getFilterByName(String name, Integer startNum, Integer endNum) throws Exception
    {
        IAIMonPInfoFilterSV filterSV = (IAIMonPInfoFilterSV) ServiceFactory.getService(IAIMonPInfoFilterSV.class);
        return filterSV.getFilterByCond(name, startNum, endNum);
    }

    public int getFilterCount(String name) throws Exception
    {
        IAIMonPInfoFilterSV filterSV = (IAIMonPInfoFilterSV) ServiceFactory.getService(IAIMonPInfoFilterSV.class);
        return filterSV.getFilterCount(name);
    }

    public void saveFilter(Object[] filterInfo) throws Exception
    {
        if(filterInfo == null || filterInfo.length < 1) {
            return;
        }

        try {

            IBOAIMonPInfoFilterValue value = new BOAIMonPInfoFilterBean();

            if(StringUtils.isBlank(filterInfo[0].toString())) {
                value.setStsToNew();
            }
            else {
                value.setFilterId(Long.parseLong(filterInfo[0].toString()));
                value.setStsToOld();
            }

            value.setFilterName(filterInfo[1].toString());
            value.setFilterDesc(filterInfo[2].toString());
            value.setExpr1(filterInfo[3].toString());
            value.setState(filterInfo[4].toString());
            value.setRemarks(filterInfo[5].toString());

            IAIMonPInfoFilterSV filterSV = (IAIMonPInfoFilterSV) ServiceFactory.getService(IAIMonPInfoFilterSV.class);
            filterSV.saveOrUpdate(value);

        }
        catch(Exception e) {
            log.error("Call TaskAction's Method saveFilter has Exception : " + e.getMessage());
            throw e;
        }
    }

    public void deleteFilter(String filterId) throws Exception
    {
        if(StringUtils.isBlank(filterId)) {
            return;
        }

        try {
            IAIMonPInfoFilterSV filterSV = (IAIMonPInfoFilterSV) ServiceFactory.getService(IAIMonPInfoFilterSV.class);
            filterSV.delete(Long.parseLong(filterId));
        }
        catch(Exception e) {
            log.error("Call TaskAction's Method deleteFilter has Exception : " + e.getMessage());
            throw e;
        }
    }

    /**
       * 根据任务标识、层、读取最新监控结果
       * @param infoId
       * @param layer
       * @return
       * @throws Exception
       */
    public List getLastMonLRecordByIds(Object[] infoIds, String layer) throws Exception
    {
        if(infoIds == null || infoIds.length < 1)
            // "请选择内容"
            throw new Exception(AIMonLocaleFactory.getResource("MOS0000053"));
        if(StringUtils.isBlank(layer))
            return null;
        IAIMonLRecordSV recordSV = (IAIMonLRecordSV) ServiceFactory.getService(IAIMonLRecordSV.class);
        return recordSV.getLastMonLRecordByIds(infoIds, layer);
    }

    //	public static void main(String[] args) {
    //		try{
    //			TaskAction action=new TaskAction();
    //			action.runTask("1","6000003,6000004");
    //			IAIMonLRecordSV recordSV = (IAIMonLRecordSV) ServiceFactory.getService(IAIMonLRecordSV.class);
    //			recordSV.queryRecord("300046");
    //		}catch(Exception e){
    //			
    //		}
    //	}

}
