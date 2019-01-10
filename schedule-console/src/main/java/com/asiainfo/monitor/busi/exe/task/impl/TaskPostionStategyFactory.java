package com.asiainfo.monitor.busi.exe.task.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import bsh.Interpreter;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.common.abo.bo.BOABGMonLRecordBean;
import com.asiainfo.common.abo.bo.BOABGMonWTriggerBean;
import com.asiainfo.common.abo.ivalues.IBOABGMonLRecordValue;
import com.asiainfo.common.abo.ivalues.IBOABGMonWTriggerValue;
import com.asiainfo.common.service.interfaces.IABGMonLRecordSV;
import com.asiainfo.common.service.interfaces.IABGMonWTriggerSV;
import com.asiainfo.common.utils.DateUtil;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonWSmsSV;
import com.asiainfo.monitor.common.CommonConst;
import com.asiainfo.monitor.tools.common.KeyName;
import com.asiainfo.monitor.tools.common.interfaces.ITaskRtn;
import com.asiainfo.monitor.tools.exe.task.model.TaskRtn;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.model.interfaces.IBackTimingTaskContext;
import com.asiainfo.monitor.tools.model.interfaces.IClear;
import com.asiainfo.monitor.tools.model.interfaces.ISmsState;
import com.asiainfo.monitor.tools.model.interfaces.ITaskCmdContainer;
import com.asiainfo.monitor.tools.model.interfaces.ITaskContext;
import com.asiainfo.monitor.tools.model.interfaces.IWebPanelTaskContext;
import com.asiainfo.monitor.tools.util.TimeUtil;
import com.asiainfo.monitor.tools.util.TypeConst;

public class TaskPostionStategyFactory
{

    private transient static Log log = LogFactory.getLog(TaskPostionStategyFactory.class);

    // private static ISmsOperation smsOptImpl=null;
    // 处理方式，默认顺序处理，直接保存数据
    private static String processType = "DEFAULT";

    // 是否持久化短信数据
    private static boolean sendSms = true;

    // 采用队列处理持久化数据，控制连接数
    private static TaskResultQueueManager queueManager = null;

    static {
        try {
            /*
             * HashMap tmpMap = AIConfigManager.getConfigItemsByKind("AppMonitorInit"); String optClassName=""; if
             * (tmpMap!=null && tmpMap.containsKey("APPMONITOR_SMS_OPT")){
             * optClassName=String.valueOf(tmpMap.get("APPMONITOR_SMS_OPT")); } if
             * (StringUtils.isNotBlank(optClassName)){
             * smsOptImpl=(ISmsOperation)Class.forName(optClassName).newInstance(); }
             */
            //if(System.getProperty("PROCESS_TYPE") != null)
            //processType = System.getProperty("PROCESS_TYPE").toUpperCase();

            // 是否持久化短信信息
            if(System.getProperty("SEND_SMS") != null && (System.getProperty("SEND_SMS").equals("N") || System.getProperty("SEND_SMS").equals("0")))
                sendSms = false;

            // 持久化数据的方式：采用队列处理，控制使用的连接数
            if(processType.equals("QUEUE")) {
                int maxnumRecordQueueSize = 5000;
                int maxnumTrigerQueueSize = 2000;
                int maxnumSmsQueueSize = 2000;
                if(System.getProperty("XRQ") != null)
                    maxnumRecordQueueSize = Integer.parseInt(System.getProperty("XRQ"));
                if(System.getProperty("XTQ") != null)
                    maxnumTrigerQueueSize = Integer.parseInt(System.getProperty("XTQ"));
                if(System.getProperty("XSQ") != null)
                    maxnumSmsQueueSize = Integer.parseInt(System.getProperty("XSQ"));
                queueManager = new TaskResultQueueManager(maxnumRecordQueueSize, maxnumTrigerQueueSize, maxnumSmsQueueSize);
            }

        }
        catch(Exception e) {
            log.error(AIMonLocaleFactory.getResource("MOS0000329"));
        }
    }

    /**
     * 是否持久化
     * 
     * @param task
     * @param taskRtn
     * @return
     * @throws Exception
     */
    public static boolean isPersistence(ITaskContext task, ITaskRtn taskRtn) throws Exception
    {

        try {
            if(taskRtn == null)
                return false;
            // 如果不是后台定时任务，则不做持久化过滤，直接通过
            if(!(task instanceof IBackTimingTaskContext))
                return true;
            // 没有过滤规则，则不做持久化过滤，直接通过
            if(((IBackTimingTaskContext) task).getFilter() == null)
                return true;
            // 有过滤规则，但没有过滤规则内容，则不做持久化过滤，直接通过
            if(StringUtils.isBlank(((IBackTimingTaskContext) task).getFilter().getExpr()))
                return true;
            // 执行阀值表达式计算
            Interpreter interpreter = new Interpreter();
            interpreter.setClassLoader(Thread.currentThread().getContextClassLoader());
            interpreter.set("$TASKRTN", taskRtn);

            String filterExpr = "";
            filterExpr = ((IBackTimingTaskContext) task).getFilter().getExpr();
            interpreter.eval(filterExpr);
            interpreter = null;
        }
        catch(Exception e) {
            // 任务运行异常
            log.error(AIMonLocaleFactory.getResource("MOS0000164") + ":taskName:" + task.getName() + ";error:" + e.getMessage());
        }
        return taskRtn.isBeTure();
    }

    /**
     * 任务后置处理--保存
     * 
     * @param task
     * @throws Exception
     */
    public static void process(ITaskContext task) throws Exception
    {
        ITaskRtn[] objMutilRtn = (ITaskRtn[]) task.getRtnModel().getRtns().toArray(new TaskRtn[0]);

        if(objMutilRtn != null && objMutilRtn.length != 0) {
            List recordList = new ArrayList();
            List triggerList = new ArrayList();
            List smsList = new ArrayList();

            //主机信息监控
            //IAIMonLRecordSV objMonLRecordSV = (IMonLRecordSV) ServiceFactory.getService(IAIMonLRecordSV.class);
            IABGMonLRecordSV objMonLRecordSV = (IABGMonLRecordSV) ServiceFactory.getService(IABGMonLRecordSV.class);
            long batchId = objMonLRecordSV.getBatchId();
            Timestamp start = new Timestamp(System.currentTimeMillis());//objMonLRecordSV.getSystemTime();

            //组织数据
            for(int i = 0; i < objMutilRtn.length; i++) {
                if(!isPersistence(task, objMutilRtn[i]))
                    continue;

                // 插入监控记录
                //IBOAIMonLRecordValue objMonLRecord = new BOAIMonLRecordBean();
                IBOABGMonLRecordValue objMonLRecord = new BOABGMonLRecordBean();
                objMonLRecord.setInfoId(Long.parseLong(task.getId()));
                objMonLRecord.setInfoCode(task.getCode());
                objMonLRecord.setBatchId(batchId);
                if(task instanceof IBackTimingTaskContext) {
                    objMonLRecord.setLayer(((IBackTimingTaskContext) task).getLayer());
                    objMonLRecord.setFromType("0");
                    objMonLRecord.setGroupId(((IBackTimingTaskContext) task).getGroupId());
                }
                // 后台定时任务仅有一条监控命令
                ITaskCmdContainer container = (ITaskCmdContainer) task.getCmdContainers().get(0);
                // 命令容器的Type为Table或Exec
                objMonLRecord.setMonType(container.getType());

                if(task.getParameter() != null && task.getParameter().size() > 0) {
                    KeyName hostParam = task.findParameter(TypeConst.HOST + TypeConst._NAME);
                    if(hostParam != null)
                        objMonLRecord.setHostName(hostParam.getKey());
                    KeyName ipParam = task.findParameter(TypeConst.IP);
                    if(ipParam != null)
                        objMonLRecord.setIp(ipParam.getKey());
                }
                objMonLRecord.setInfoName(task.getName());
                String infoValue = objMutilRtn[i].getMsg();
                String[] vars = com.ai.appframe2.util.StringUtils.getParamFromString(infoValue, "{", "}");
                if(vars != null && vars.length > 0) {
                    for(int pCount = 0; pCount < vars.length; pCount++) {
                        KeyName itemVar = task.findParameter(vars[pCount].toUpperCase());
                        if(itemVar != null) {
                            infoValue = StringUtils.replace(infoValue, "{" + vars[pCount] + "}", itemVar.getKey());
                        }
                    }
                }
                objMonLRecord.setInfoValue(infoValue);
                objMonLRecord.setCreateTime(start);
                objMonLRecord.setDoneDate(start);
                if(objMutilRtn[i].getLevel() > 0) {
                    objMonLRecord.setIsTriggerWarn("Y");
                }
                else {
                    objMonLRecord.setIsTriggerWarn("N");
                }
                objMonLRecord.setWarnLevel(String.valueOf(objMutilRtn[i].getLevel()));
                objMonLRecord.setStsToNew();

                //日誌庫必備的字段
                objMonLRecord.setSystemDomain(CommonConst.SYSTEM_DOMAIN);
                objMonLRecord.setSubsystemDomain(CommonConst.SUB_SYSTEM_DOMAIN);
                objMonLRecord.setCreateDate(DateUtil.date2String(start));
                objMonLRecord.setAppServerName(CommonConst.APP_SERVER_NAME);

                // 采用队列处理
                if(processType.equals("QUEUE"))
                    queueManager.offerRecord(objMonLRecord);
                else
                    recordList.add(objMonLRecord);

                //记录告警信息
                if(objMutilRtn[i].getLevel() > 0) {
                    // 根据告警明细插入告警表
                    StringBuffer phones = new StringBuffer("");// 还需要做调整

                    IBOABGMonWTriggerValue objMonWTrigger = new BOABGMonWTriggerBean();
                    objMonWTrigger.setInfoId(Long.parseLong(task.getId()));
                    objMonWTrigger.setInfoName(task.getName());

                    // 将告警信息中的{Var}变量转成实际值
                    String content = objMutilRtn[i].getWarn();
                    String[] warnVars = com.ai.appframe2.util.StringUtils.getParamFromString(content, "{", "}");
                    if(warnVars != null && warnVars.length > 0) {
                        for(int pCount = 0; pCount < warnVars.length; pCount++) {
                            KeyName itemVar = task.findParameter(warnVars[pCount].toUpperCase());
                            if(itemVar != null) {
                                content = StringUtils.replace(content, "{" + warnVars[pCount] + "}", itemVar.getKey());
                            }
                        }
                    }
                    objMonWTrigger.setContent(content);
                    objMonWTrigger.setWarnLevel(String.valueOf(objMutilRtn[i].getLevel()));
                    String expriyDays = null;
                    if(task instanceof IWebPanelTaskContext) {
                        expriyDays = ((IWebPanelTaskContext) task).getThreshold().getExpiryDays();
                    }
                    else if(task instanceof IBackTimingTaskContext) {
                        expriyDays = ((IBackTimingTaskContext) task).getThreshold().getExpiryDays();
                        ISmsState smsState = ((IBackTimingTaskContext) task).getSmsState();

                        // 短信是否记录
                        if(smsState != null && sendSms) {
                            ((IClear) smsState).clear();
                            // 因为警告信息是批量保存，所有无法提前记录警告记录的标识，特此记录批次号占用记录标识列
                            smsState.setId(batchId);
                            smsState.setInfoId(Long.parseLong(task.getId()));
                            smsState.setMsg(objMutilRtn[i].getWarn());
                            smsState.setLevel(objMutilRtn[i].getLevel());
                            smsState.setCreateDate(start);
                            if(smsState != null && smsState.getPersion() != null) {
                                for(int phoneCount = 0; phoneCount < smsState.getPersion().length; phoneCount++) {
                                    int warnLevel = smsState.getPersion()[phoneCount].getWarn_Level();
                                    // 短信警告内容级别大于人员设置的发送短信级别时，则记录短信发送记录表
                                    if(smsState.getLevel() >= warnLevel && StringUtils.isNotBlank(smsState.getPersion()[phoneCount].getPhone_Num())) {
                                        if(!StringUtils.isBlank(phones.toString()))
                                            phones.append(TypeConst._SPLIT_CHAR);
                                        phones.append(smsState.getPersion()[phoneCount].getPhone_Num());
                                    }
                                }
                                objMonWTrigger.setState("S");
                                if(processType.equals("QUEUE"))
                                    queueManager.offerSms(smsState);
                                else {
                                    smsList.add(smsState);
                                }
                            }

                        }
                        else {
                            objMonWTrigger.setState("C");// 只创建的告警记录，并未创建短信记录
                        }

                    }
                    if(StringUtils.isBlank(phones.toString()))
                        objMonWTrigger.setPhonenum(phones.toString());
                    else
                        objMonWTrigger.setState("U");
                    if(StringUtils.isNotBlank(expriyDays)) {
                        String expdate = TimeUtil.format(TimeUtil.addOrMinusHours(System.currentTimeMillis(), Integer.parseInt(expriyDays) * 24));
                        objMonWTrigger.setExpiryDate(Timestamp.valueOf(expdate));
                    }
                    objMonWTrigger.setCreateTime(start);
                    if(task.getParameter() != null && task.getParameter().size() > 0) {
                        KeyName ipParam = task.findParameter(TypeConst.IP);
                        if(ipParam != null)
                            objMonWTrigger.setIp(ipParam.getKey());
                    }
                    objMonWTrigger.setLayer(objMonLRecord.getLayer());
                    //objMonWTrigger.setRecordId(objMonLRecord.getRecordId());
                    objMonWTrigger.setStsToNew();

                    //入日誌庫必備字段
                    objMonWTrigger.setSystemDomain(CommonConst.SYSTEM_DOMAIN);
                    objMonWTrigger.setSubsystemDomain(CommonConst.SUB_SYSTEM_DOMAIN);
                    objMonWTrigger.setCreateDate(DateUtil.date2String(start));
                    objMonWTrigger.setAppServerName(CommonConst.APP_SERVER_NAME);

                    // 采用队列处理
                    if(processType.equals("QUEUE"))
                        queueManager.offerTriger(objMonWTrigger);
                    else
                        triggerList.add(objMonWTrigger);
                }
            }
            // 如果不是队列处理，则直接批量提交保存数据
            if(!processType.equals("QUEUE")) {
                //主机资源信息暫不寫入，已另有進程採集如表[ABG_MON_HOST_LOG]表
                //if(recordList.size() > 0) {
                //persistentRecord((IBOABGMonLRecordValue[]) recordList.toArray(new IBOABGMonLRecordValue[0]));
                //}
                //超过告警阀值的监控信息记录
                if(triggerList.size() > 0) {
                    persistentTriger((IBOABGMonWTriggerValue[]) triggerList.toArray(new IBOABGMonWTriggerValue[0]));
                }
                //不发送短信
                //if(smsList.size() > 0)
                //    persistentSms((ISmsState[]) smsList.toArray(new ISmsState[0]));
                recordList = null;
                triggerList = null;
                smsList = null;
            }

        }
        objMutilRtn = null;
    }

    /**
     * 持久化监控记录
     * 
     * @param records
     * @throws Exception
     */
    public static void persistentRecord(IBOABGMonLRecordValue[] records) throws Exception
    {
        try {
            IABGMonLRecordSV objMonLRecordSV = (IABGMonLRecordSV) ServiceFactory.getService(IABGMonLRecordSV.class);
            objMonLRecordSV.saveOrUpdate(records);
        }
        catch(Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * 持久化预警记录
     * 
     * @param trigers
     * @throws Exception
     */
    public static void persistentTriger(IBOABGMonWTriggerValue[] trigers) throws Exception
    {
        try {
            IABGMonWTriggerSV objMonWTriggerSV = (IABGMonWTriggerSV) ServiceFactory.getService(IABGMonWTriggerSV.class);
            objMonWTriggerSV.saveAndProcess(trigers);
        }
        catch(Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * 持久化短信记录
     * 
     * @param sms
     * @throws Exception
     */
    public static void persistentSms(ISmsState[] sms) throws Exception
    {
        try {
            IAIMonWSmsSV smsSV = (IAIMonWSmsSV) ServiceFactory.getService(IAIMonWSmsSV.class);
            smsSV.saveOrUpdate(sms);
        }
        catch(Exception e) {
            throw new Exception(e);
        }
    }

    /*
     * public static class SendSmsWork implements IWorker{ private ISmsState smsState;
     * 
     * public SendSmsWork(ISmsState smsState){ this.smsState=smsState; }
     * 
     * public void action() throws Exception{ try{ if (smsOptImpl!=null) smsOptImpl.sendSms(smsState); }catch(Exception
     * e){
     * 
     * }
     * 
     * } }
     */
    public static void main(String[] args)
    {
        String infoValue = "anc{IP}sds{HOST_ID}{HOST_CODE}";
        String[] vars = com.ai.appframe2.util.StringUtils.getParamFromString(infoValue, "{", "}");
        if(vars != null && vars.length > 0) {
            for(int pCount = 0; pCount < vars.length; pCount++) {
                if(vars[pCount].equals("IP"))
                    infoValue = StringUtils.replace(infoValue, "{" + vars[pCount] + "}", "10.70.181.5");
                else if(vars[pCount].equals("HOST_ID"))
                    infoValue = StringUtils.replace(infoValue, "{" + vars[pCount] + "}", "88");
                else if(vars[pCount].equals("HOST_CODE"))
                    infoValue = StringUtils.replace(infoValue, "{" + vars[pCount] + "}", "zjcrm");

            }
        }
        System.out.println(infoValue);
    }
}
