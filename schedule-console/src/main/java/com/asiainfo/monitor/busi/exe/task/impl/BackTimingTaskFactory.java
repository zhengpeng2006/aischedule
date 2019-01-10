package com.asiainfo.monitor.busi.exe.task.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.exe.task.Listener.TaskProcessListener;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPExecValue;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPInfoFilterValue;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPInfoValue;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPTableValue;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPTimeValue;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonWDtlValue;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonWPersonValue;
import com.asiainfo.monitor.busi.exe.task.model.TaskExecContainer;
import com.asiainfo.monitor.busi.exe.task.model.TaskTableContainer;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPExecAtomicSV;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPInfoFilterSV;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPInfoSV;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPTableAtomicSV;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPTimeSV;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonWDtlSV;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonWPersonSV;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.model.BackTimingTaskContext;
import com.asiainfo.monitor.tools.model.SmsPerson;
import com.asiainfo.monitor.tools.model.SmsState;
import com.asiainfo.monitor.tools.model.TaskFilterModel;
import com.asiainfo.monitor.tools.model.TimeModel;
import com.asiainfo.monitor.tools.model.interfaces.ISmsPersion;
import com.asiainfo.monitor.tools.model.interfaces.ISmsState;
import com.asiainfo.monitor.tools.model.interfaces.ITaskCmdContainer;

/**
 * 后台定时任务工厂
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2014
 * </p>
 * <p>
 * Company: AI(NanJing)
 * </p>
 * 
 * @author wu songkai
 * @version 3.0
 */
public class BackTimingTaskFactory extends DefaultTaskFactory
{

    private static final Log log = LogFactory.getLog(BackTimingTaskFactory.class);

    /**
     * 根据数据分片读取任务
     * 
     * @param split
     * @param mod
     * @param value
     * @return
     * @throws Exception
     */
    private static IBOAIMonPInfoValue[] getMonPInfoValue(long split, long mod, long value) throws Exception
    {
        IAIMonPInfoSV infoSV = (IAIMonPInfoSV) ServiceFactory.getService(IAIMonPInfoSV.class);
        return infoSV.getMonPInfoValuesBySplitAndMod(split, mod, value);
    }

    private static IBOAIMonPInfoValue[] getPInfoByLocalIp() throws Exception
    {
        IAIMonPInfoSV infoSV = (IAIMonPInfoSV) ServiceFactory.getService(IAIMonPInfoSV.class);
        return infoSV.getPInfoByLocalIp();
    }

    /**
     * 根据任务执行方式读取任务
     * 
     * @param ids
     * @return
     * @throws Exception
     */
    private static IBOAIMonPInfoValue[] getMonPInfoValue(String execMethod) throws Exception
    {
        IBOAIMonPInfoValue[] result = null;
        IAIMonPInfoSV objInfoSV = (IAIMonPInfoSV) ServiceFactory.getService(IAIMonPInfoSV.class);
        result = objInfoSV.getAllTaskInfo(execMethod);
        return result;
    }

    /**
     * 构建警告状态信息
     * 
     * @param taskId
     * @return
     * @throws Exception
     */
    private static ISmsState buildSmsState(String taskId) throws Exception
    {
        ISmsState result = null;
        IBOAIMonWDtlValue[] values = null;
        IAIMonWDtlSV dtlSV = (IAIMonWDtlSV) ServiceFactory.getService(IAIMonWDtlSV.class);
        values = dtlSV.getDtlByTaskId(taskId);
        if(values != null && values.length > 0) {
            result = new SmsState();
            IAIMonWPersonSV persionSV = (IAIMonWPersonSV) ServiceFactory.getService(IAIMonWPersonSV.class);
            IBOAIMonWPersonValue persionValue = null;
            ISmsPersion[] persions = new ISmsPersion[values.length];
            for(int i = 0; i < values.length; i++) {
                persionValue = persionSV.getBeanById(values[i].getPersonId());
                if(persionValue != null) {
                    persions[i] = new SmsPerson();
                    persions[i].setPerson_Id(persionValue.getPersonId() + "");
                    persions[i].setPerson_Name(persionValue.getName());
                    persions[i].setPhone_Num(persionValue.getPhonenum());
                    persions[i].setRegion_Id(persionValue.getRegionId() + "");
                    persions[i].setWarn_Level(StringUtils.isBlank(values[i].getWarnLevel()) ? 0 : Integer.parseInt(values[i].getWarnLevel()));
                }
            }
            if(persions != null && persions.length > 0) {
                result.setPersion(persions);
            }
        }
        return result;
    }

    /**
     * 构建任务上下文
     * 
     * @param objMonInfo
     * @return
     * @throws Exception
     */
    private static BackTimingTaskContext[] builderTaskContext(IBOAIMonPInfoValue[] objMonInfo) throws Exception
    {
        BackTimingTaskContext[] result = null;
        List<BackTimingTaskContext> list = null;
        try {
            list = new ArrayList<BackTimingTaskContext>();
            if(objMonInfo != null && objMonInfo.length > 0) {

                // 获取任务配置信息
                IAIMonPTimeSV objTimeSV = (IAIMonPTimeSV) ServiceFactory.getService(IAIMonPTimeSV.class);
                IAIMonPTableAtomicSV objMonTableSV = (IAIMonPTableAtomicSV) ServiceFactory.getService(IAIMonPTableAtomicSV.class);
                IAIMonPExecAtomicSV objExecSV = (IAIMonPExecAtomicSV) ServiceFactory.getService(IAIMonPExecAtomicSV.class);
                IAIMonPInfoFilterSV objFilterSV = (IAIMonPInfoFilterSV) ServiceFactory.getService(IAIMonPInfoFilterSV.class);

                // 任务处理监听器
                TaskProcessListener listener = new TaskProcessListener();
                for(int count = 0; count < objMonInfo.length; count++) {
                    try {
                        if(objMonInfo[count] == null) {
                            continue;
                        }
                        // 定时任务上下文信息
                        BackTimingTaskContext task = new BackTimingTaskContext();
                        task.addPropertyChangeListener(listener);
                        task.addContainerListener(listener);
                        task.setId(objMonInfo[count].getInfoId() + "");
                        task.setCode(objMonInfo[count].getInfoCode());
                        task.setName(objMonInfo[count].getName());
                        task.setGroupId(objMonInfo[count].getGroupId() + "");
                        task.setLayer(objMonInfo[count].getLayer());
                        task.setExecMethod(objMonInfo[count].getTaskMethod());
                        if(objMonInfo[count].getHostId() > 0) {
                            task.setHostId(objMonInfo[count].getHostId() + "");
                        }
                        task.setState(objMonInfo[count].getState());

                        /***************** 设置任务过滤规则暂时用不到 ************************/
                        if(objMonInfo[count].getFilterId() > 0) {
                            IBOAIMonPInfoFilterValue objFliter = objFilterSV.getFilterById(objMonInfo[count].getFilterId());
                            if(objFliter != null) {
                                TaskFilterModel filter = new TaskFilterModel();
                                filter.setFilter_Id(objFliter.getFilterId());
                                filter.setName(objFliter.getFilterName());
                                filter.setExpr1(objFliter.getExpr1());
                                task.setFilter(filter);
                            }
                        }

                        // 获取定时任务执行时间
                        IBOAIMonPTimeValue objTime = objTimeSV.getBeanById(objMonInfo[count].getTimeId());
                        if(objTime != null) {
                            TimeModel time = new TimeModel();
                            time.setId(objTime.getTimeId());
                            time.setType(objTime.getTType());
                            time.setExpr(objTime.getExpr());
                            task.setTime(time);
                        }

                        /***************** 获取面板任务参数 *****************************************/
                        List params = getInnerParams("10", objMonInfo[count].getInfoId() + "");

                        /************************** 获取具体执行的命令 ******************************/
                        // SQL命令，查询日志表
                        if(objMonInfo[count].getMType().toUpperCase().equals(ITaskCmdContainer._TASK_TABLE)) {

                            IBOAIMonPTableValue objMonTable = objMonTableSV.getBeanById(objMonInfo[count].getTypeId());
                            if(objMonTable != null) {
                                TaskTableContainer tableContainer = new TaskTableContainer();
                                tableContainer.addContainerListener(listener);
                                tableContainer.setId(objMonTable.getTableId() + "");
                                tableContainer.setName(objMonTable.getName());
                                tableContainer.setParent(task);
                                tableContainer.setCmdType(getCmdType(ITaskCmdContainer._TASK_TABLE));
                                tableContainer.setDburlName(objMonTable.getDbUrlName());
                                tableContainer.setDbacctCode(objMonTable.getDbAcctCode());
                                tableContainer.setExpr(objMonTable.getSql());
                                if(params != null && params.size() > 0) {
                                    tableContainer.addParameter(params);
                                }
                                task.putCmdItem(tableContainer);
                            }
                        }
                        // Shell脚本，java发送脚本执行，
                        else if(objMonInfo[count].getMType().toUpperCase().equals(ITaskCmdContainer._TASK_EXEC)) {

                            IBOAIMonPExecValue objMonExec = objExecSV.getBeanById(objMonInfo[count].getTypeId());
                            if(objMonExec != null) {
                                TaskExecContainer execContainer = new TaskExecContainer();
                                execContainer.addContainerListener(listener);
                                execContainer.setParent(task);
                                execContainer.setId(objMonExec.getExecId() + "");
                                execContainer.setName(objMonExec.getName());
                                if(StringUtils.isBlank(objMonExec.getEType())) {
                                    log.error(AIMonLocaleFactory.getResource("MOS0000345", objMonInfo[count].getInfoId() + ""));
                                    continue;
                                }
                                execContainer.setCmdType(getCmdType(objMonExec.getEType()));
                                execContainer.setExpr(objMonExec.getExpr());
                                if(params != null && params.size() > 0) {
                                    execContainer.addParameter(params);// 设置参数
                                }
                                task.putCmdItem(execContainer);
                            }
                        }
                        else {
                            // 未定义任务["+objMonInfo[count].getInfoId()+"]对应类型["+objMonInfo[count].getMType()+"]的领域数据模型
                            throw new Exception(AIMonLocaleFactory.getResource("MOS0000150", objMonInfo[count].getInfoId() + "",
                                    objMonInfo[count].getMType()));
                        }
                        // 设置警告对象
                        if(objMonInfo[count].getThresholdId() > 0) {
                            task.setThreshold(builderThreshold(objMonInfo[count].getThresholdId()));
                        }
                        // 设置警告发送对象
                        task.setSmsState(buildSmsState(task.getId()));
                        list.add(task);
                    }
                    catch(Exception e) {
                        log.error(e);
                    }
                }
            }

            if(list != null && list.size() > 0) {
                result = (BackTimingTaskContext[]) list.toArray(new BackTimingTaskContext[0]);
            }
        }
        catch(Exception e) {
            log.error("Call BackTimingTaskFactory's method builderTaskContext has Exception :", e);
        }
        return result;
    }

    /**
     * 根据执行方式自构建返回后台任务上下文
     * 
     * @param execMethod
     * @return
     * @throws Exception
     */
    public static BackTimingTaskContext[] getBackTimingTaskContext(String execMethod) throws Exception
    {
        IBOAIMonPInfoValue[] infos = getMonPInfoValue(execMethod);
        return builderTaskContext(infos);
    }

    /**
     * 根据数据分片自构建返回后台任务上下文
     * 
     * @param execMethod
     * @return
     * @throws Exception
     */
    public static BackTimingTaskContext[] getBackTimingTaskContext(long split, long mod, long value) throws Exception
    {
        //IBOAIMonPInfoValue[] infos = getMonPInfoValue(split, mod, value);
        IBOAIMonPInfoValue[] infos = getPInfoByLocalIp();
        return builderTaskContext(infos);
    }

}
