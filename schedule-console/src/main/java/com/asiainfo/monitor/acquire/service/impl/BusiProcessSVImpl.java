package com.asiainfo.monitor.acquire.service.impl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.complex.util.e.K;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.common.utils.DateUtil;
import com.asiainfo.monitor.acquire.dto.AcqParamBean;
import com.asiainfo.monitor.acquire.engine.IAcquire;
import com.asiainfo.monitor.acquire.engine.ProcInfoCallable;
import com.asiainfo.monitor.acquire.engine.impl.BaseAcquireEngine;
import com.asiainfo.monitor.acquire.service.ParallelUtil;
import com.asiainfo.monitor.acquire.service.interfaces.IBusiProcessSV;
import com.asiainfo.monitor.analyse.IDataAnalyse;
import com.asiainfo.monitor.analyse.impl.DataAnalyseImpl;
import com.asiainfo.monitor.busi.config.ServerConfig;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonCmdSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonPhysicHostSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;
import com.asiainfo.monitor.common.CommonConst;
import com.asiainfo.monitor.common.CommonSvUtil;
import com.asiainfo.monitor.common.IdWorker;
import com.asiainfo.monitor.common.combin.CmbHostBean;
import com.asiainfo.monitor.common.service.interfaces.IBaseCommonSV;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonCmdValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonGroupBean;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonPhysicHostValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonServerValue;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.schedule.core.po.TaskItemJobBean;
import com.asiainfo.schedule.service.interfaces.ISchedulerSV;

/**
 * 业务进程服务，提供查询该进程相关信息
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
public class BusiProcessSVImpl implements IBusiProcessSV {

    private static transient Log log = LogFactory.getLog(BusiProcessSVImpl.class);

    public String[] getBusiKpiInfoByServerId(String[] serverId, String qryType, int threadCount, int timeOut) throws Exception {
        List list = new ArrayList(serverId.length);

        if ((serverId != null || serverId.length != 0) && StringUtils.isNotBlank(qryType)) {

            IAIMonServerSV serverSV = (IAIMonServerSV) ServiceFactory.getService(IAIMonServerSV.class);
            IAIMonCmdSV cmdSV = (IAIMonCmdSV) ServiceFactory.getService(IAIMonCmdSV.class);
            ServerConfig server = null;

            for (int i = 0; i < serverId.length; i++) {
                server = serverSV.getServerByServerId(serverId[i]);
                if (server == null) {
                    // 没找到应用实例[{0}]
                    log.error(AIMonLocaleFactory.getResource("MOS0000070", serverId[i]));
                    continue;
                }

                // 获取主机用户信息
                CmbHostBean hostBean = CommonSvUtil.qryCmbHostInfoByServerId(server.getApp_Id());
                if (null == hostBean) {
                    continue;
                }
                //Map<String, String> userInfoMap = hostBean.getMonUserInfo();
                Map<String, String> userInfoMap = new HashMap<String, String>();
                userInfoMap = CommonSvUtil.qryMonitorNodeUserInfoByServerId(server.getApp_Id());
                // 查询命令放到了命令表里
                IBOAIMonCmdValue[] monCmd = cmdSV.getCmdByName("BUSI_PROC_KPI");
                if (monCmd == null || monCmd.length == 0) {
                    throw new Exception(AIMonLocaleFactory.getResource("MOS0000135"));
                } else if (monCmd.length > 1) {
                    throw new Exception("查询出的结果不唯一，无法执行进程KPI的查询！");
                }

                ParallelUtil.MidServerTask objMidServerTask = new ParallelUtil.MidServerTask();
                objMidServerTask.ip = server.getApp_Ip();
                objMidServerTask.port = Integer.parseInt(server.getPhysicHost().getHostPort());
                objMidServerTask.username = userInfoMap.get(CommonConst.USER_NAME);
                objMidServerTask.password = K.k(userInfoMap.get(CommonConst.USER_PASSWD));
                objMidServerTask.serverName = monCmd[0].getCmdName();
                objMidServerTask.params = qryType + " " + server.getApp_Code();
                objMidServerTask.shell = monCmd[0].getCmdExpr();
                list.add(objMidServerTask);

            }
        }

        String[] rtn = ParallelUtil.getBusiInfoFromService(threadCount, timeOut,
                (ParallelUtil.MidServerTask[]) (ParallelUtil.MidServerTask[]) list.toArray(new ParallelUtil.MidServerTask[0]));

        return rtn;

    }

    @Override
    public JSONArray acqWarningInfo(String hostId) throws Exception {
        //PhysicHost host = (PhysicHost) CacheFactory.get(AIMonPhostCacheImpl.class, hostId);
        //把缓存去掉
        IAIMonPhysicHostSV hostSV = (IAIMonPhysicHostSV) ServiceFactory.getService(IAIMonPhysicHostSV.class);
        IBOAIMonPhysicHostValue host = hostSV.getPhysicHostById(hostId);
        JSONArray jsonArr = new JSONArray();
        JSONObject obj = new JSONObject();
        obj.put("title", host.getHostName() + "(" + host.getHostIp() + "):");
        JSONArray jsonArray = new JSONArray();
        Map<String, List<String>> processInfoMap = this.getProcessInfo(hostId);
        // 获取当前主机部署的应用进程信息
        List<IBOAIMonServerValue> appList = CommonSvUtil.qryServerList(hostId);
        //查找应用进程时，匹配的字符串
        String procParamStr = "appframe.server.name=";
        if (null == processInfoMap) {
            return null;
        }
        Map map = new HashMap<String, List>();
        Map mapNoStart = new HashMap<String, IBOAIMonServerValue>();
        Iterator it = processInfoMap.keySet().iterator();
        while (it.hasNext()) {
            List<String> procInfoList = processInfoMap.get(it.next());
            // 获取最后一个元素，该元素记录进程启动Command
            String cmdInfo = procInfoList.get(procInfoList.size() - 4);
            for (IBOAIMonServerValue appVal : appList) {
                // 如果启动Command包含appCode，则认为是该进程的信息
                String parttenStr = procParamStr + appVal.getServerCode();
                if (cmdInfo.contains(parttenStr)) {
                    List list = new ArrayList<List>();
                    String serverId = Long.toString(appVal.getServerId());
                    if (map.containsKey(serverId)) {
                        List list1 = (List) map.get(serverId);
                        list1.add(procInfoList);
                        map.put(serverId, list1);
                    } else {
                        list.add(procInfoList);
                        map.put(serverId, list);
                    }
                    break;
                } else {
                    String serverId = Long.toString(appVal.getServerId());
                    if (!mapNoStart.containsKey(serverId)) {
                        mapNoStart.put(serverId, appVal);
                    }
                }
            }
        }
        Object s[] = map.keySet().toArray();
        for (int i = 0; i < map.size(); i++) {
            String key = (String) s[i];
            if (mapNoStart.containsKey(key)) {
                mapNoStart.remove(key);
            }
            IAIMonServerSV serverSv = (IAIMonServerSV) ServiceFactory.getService(IAIMonServerSV.class);
            IBOAIMonServerValue appVal = serverSv.qryServerByServerId(key);
            List list = (List) map.get(s[i]);
            JSONObject jsonObj = new JSONObject();
            if (list.size() > 1) {
                jsonObj.put("state", "进程异常");
                jsonObj.put("desc", "重复的进程");
            } else {
                jsonObj.put("state", "进程正常");
                jsonObj.put("desc", "正常的进程");
            }
            jsonObj.put("hostId", host.getHostId());
            jsonObj.put("hostName", host.getHostName());
            jsonObj.put("serverCode", appVal.getServerCode());
            jsonObj.put("serverName", appVal.getServerName());
            jsonArray.add(jsonObj);
        }
        Object ss[] = mapNoStart.keySet().toArray();
        for (int i = 0; i < mapNoStart.size(); i++) {
            IBOAIMonServerValue appVal = (IBOAIMonServerValue) mapNoStart.get(ss[i]);
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("hostId", host.getHostId());
            jsonObj.put("hostName", host.getHostName());
            jsonObj.put("serverCode", appVal.getServerCode());
            jsonObj.put("serverName", appVal.getServerName());
            jsonObj.put("state", "进程异常");
            jsonObj.put("desc", "未启动的进程");
            jsonArray.add(jsonObj);
        }
        jsonArr.add(obj);
        jsonArr.add(jsonArray);
        return jsonArr;
    }

    /**
     * 应用进程信息展示
     *
     * @param request
     * @throws Exception
     */
    public JSONArray acqProcessKpiInfo(String hostId) throws Exception {

        Map<String, List<String>> processInfoMap = this.getProcessInfo(hostId);
        // 获取当前主机部署的应用进程信息
        List<IBOAIMonServerValue> appList = CommonSvUtil.qryServerList(hostId);

        //查找应用进程时，匹配的字符串
        String procParamStr = "appframe.server.name=";

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObj = null;

        if (null == processInfoMap) {
            return jsonArray;
        }
        Map map = new HashMap<String, List>();
        Map mapNoStart = new HashMap<String, IBOAIMonServerValue>();
        Iterator it = processInfoMap.keySet().iterator();
        while (it.hasNext()) {
            jsonObj = new JSONObject();
            List<String> procInfoList = processInfoMap.get(it.next());
            // 获取最后一个元素，该元素记录进程启动Command
            String cmdInfo = procInfoList.get(procInfoList.size() - 4);
            for (IBOAIMonServerValue appVal : appList) {
                // 如果启动Command包含appCode，则认为是该进程的信息
                String parttenStr = procParamStr + appVal.getServerCode();

                if (cmdInfo.contains(parttenStr)) {

                    List list = new ArrayList<List>();
                    String serverId = Long.toString(appVal.getServerId());
                    if (map.containsKey(serverId)) {
                        List list1 = (List) map.get(serverId);
                        list1.add(procInfoList);
                        map.put(serverId, list1);
                    } else {
                        list.add(procInfoList);
                        map.put(serverId, list);
                    }
                    break;
                } else {
                    String serverId = Long.toString(appVal.getServerId());
                    if (!mapNoStart.containsKey(serverId)) {
                        mapNoStart.put(serverId, appVal);
                    }
                }
            }
        }
        Object s[] = map.keySet().toArray();
        for (int i = 0; i < map.size(); i++) {

            String key = (String) s[i];
            if (mapNoStart.containsKey(key)) {
                mapNoStart.remove(key);
            }
            IAIMonServerSV serverSv = (IAIMonServerSV) ServiceFactory.getService(IAIMonServerSV.class);
            IBOAIMonServerValue appVal = serverSv.qryServerByServerId(key);

            //根据serverCode查询正在具体实例跑的任务
            ISchedulerSV sSv = (ISchedulerSV) ServiceFactory.getService(ISchedulerSV.class);
            TaskItemJobBean[] tasks = sSv.getAllTaskItemJobsByServerId(appVal.getServerCode());
            int taskCount = 0;
            String showTasks = "";
            //当任务不为空的时候，获取任务数
            if (null != tasks) {
                taskCount = tasks.length;
            }
            List list = (List) map.get(s[i]);
            if (list.size() > 1) {
                jsonObj.put("STATE", "YC");
            } else {
                jsonObj.put("STATE", "JK");
            }

            List procInfoList = (List) list.get(0);
            jsonObj.put("SERVER_ID", appVal.getServerId());
            jsonObj.put("USER_NAME", procInfoList.get(0));
            jsonObj.put("PID", procInfoList.get(1));
            jsonObj.put("SERVER_CODE", appVal.getServerCode());
            jsonObj.put("SERVER_NAME", appVal.getServerName());
            jsonObj.put("KPI_CPU", procInfoList.get(4) + "%");
            String res = (String) procInfoList.get(3);
            if (res.contains("m")) {
                jsonObj.put("KPI_MEM", procInfoList.get(5) + "%(" + res + ")");
            } else {
                if (res.contains("g")) {
                    jsonObj.put("KPI_MEM", procInfoList.get(5) + "%(" + res + ")");
                } else {
                    int re = (Integer.parseInt(res) / 1024);
                    jsonObj.put("KPI_MEM", procInfoList.get(5) + "%(" + re + "m" + ")");
                }
            }
            jsonObj.put("TASK_COUNT", taskCount);
            jsonArray.add(jsonObj);
        }
        Object ss[] = mapNoStart.keySet().toArray();

        for (int i = 0; i < mapNoStart.size(); i++) {
            IBOAIMonServerValue appVal = (IBOAIMonServerValue) mapNoStart.get(ss[i]);
            //根据serverCode查询正在具体实例跑的任务
            ISchedulerSV sSv = (ISchedulerSV) ServiceFactory.getService(ISchedulerSV.class);
            TaskItemJobBean[] tasks = sSv.getAllTaskItemJobsByServerId(appVal.getServerCode());
            int taskCount = 0;
            String showTasks = "";
            //当任务不为空的时候，获取任务数
            if (null != tasks) {
                taskCount = tasks.length;
            }
            JSONObject jsonObj1 = new JSONObject();
            jsonObj1.put("SERVER_ID", appVal.getServerId());
            jsonObj1.put("SERVER_CODE", appVal.getServerCode());
            jsonObj1.put("SERVER_NAME", appVal.getServerName());
            jsonObj1.put("USER_NAME", "NONE");
            jsonObj1.put("PID", "NONE");
            jsonObj1.put("KPI_CPU", "0");
            jsonObj1.put("KPI_MEM", "0");
            jsonObj1.put("STATE", "YC");
            jsonObj1.put("TASK_COUNT", taskCount);
            jsonArray.add(jsonObj1);
        }
        return jsonArray;
    }

    /**
     * 非应用进程信息展示
     *
     * @param request
     * @throws Exception
     */
    public JSONArray acqProcKpiInfo(String hostId) throws Exception {

        Map<String, List<String>> processInfoMap = this.getProcessInfo(hostId);
        // 获取当前主机部署的应用进程信息
        List<IBOAIMonServerValue> appList = CommonSvUtil.qryServerList(hostId);

        //查找应用进程时，根据应用编码配置的进程参数
        String procParamStr = "appframe.server.name=";

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObj = null;
        if (null == processInfoMap) {
            return jsonArray;
        }
        Iterator it = processInfoMap.keySet().iterator();
        while (it.hasNext()) {
            jsonObj = new JSONObject();
            List<String> procInfoList = processInfoMap.get(it.next());
            if (procInfoList.size() == 6) {
                // 获取最后一个元素，该元素记录进程启动Command
                String cmdInfo = procInfoList.get(procInfoList.size() - 4);
                if (appList.size() > 0) {
                    for (int i = 0; i < appList.size(); i++) {
                        IBOAIMonServerValue appVal = appList.get(i);
                        String parttenStr = procParamStr + appVal.getServerCode();

                        // 如果启动Command不包含appCode，则认为是该进程的信息
                        if (cmdInfo.contains(parttenStr)) {
                            break;
                        }
                        if ((i == appList.size() - 1) && !cmdInfo.contains(parttenStr)) {
                            jsonObj.put("USER_NAME", procInfoList.get(0));
                            jsonObj.put("PID", procInfoList.get(1));
                            jsonObj.put("KPI_CPU", procInfoList.get(4) + "%");
                            String res = procInfoList.get(3);
                            if (res.contains("m")) {
                                jsonObj.put("KPI_MEM", procInfoList.get(5) + "%(" + res + ")");
                            } else {
                                if (res.contains("g")) {
                                    jsonObj.put("KPI_MEM", procInfoList.get(5) + "%(" + res + ")");
                                } else {
                                    int re = (Integer.parseInt(res) / 1024);
                                    jsonObj.put("KPI_MEM", procInfoList.get(5) + "%(" + re + "m" + ")");
                                }
                            }
                            jsonObj.put("CMD_INFO", procInfoList.get(2).replace("~", " "));
                            jsonArray.add(jsonObj);
                            break;
                        }
                    }
                } else {
                    jsonObj.put("USER_NAME", procInfoList.get(0));
                    jsonObj.put("PID", procInfoList.get(1));
                    jsonObj.put("KPI_CPU", procInfoList.get(4) + "%");
                    String res = procInfoList.get(3);
                    if (res.contains("m")) {
                        jsonObj.put("KPI_MEM", procInfoList.get(5) + "%(" + res + ")");
                    } else {
                        if (res.contains("g")) {
                            jsonObj.put("KPI_MEM", procInfoList.get(5) + "%(" + res + ")");
                        } else {
                            int re = (Integer.parseInt(res) / 1024);
                            jsonObj.put("KPI_MEM", procInfoList.get(5) + "%(" + re + "m" + ")");
                        }
                    }
                    jsonObj.put("CMD_INFO", procInfoList.get(2).replace("~", " "));
                    jsonArray.add(jsonObj);
                }
            }
        }
        return jsonArray;
    }

    /**
     * 获取进程信息
     *
     * @param request
     * @throws Exception
     */
    public Map<String, List<String>> getProcessInfo(String hostId) throws RemoteException, Exception {
        // 获取主机参数信息
        AcqParamBean paramBean = CommonSvUtil.getHostExeShellInfo(hostId, "15");
        String analyseType = paramBean.getKpiType();// 分析类型

        // 数据采集
        IAcquire acquireEngine = new BaseAcquireEngine();
        String processInfo = acquireEngine.acquire(paramBean);

        if (StringUtils.isBlank(processInfo)) {
            return null;
        }

        // 数据分析
        IDataAnalyse dataAnalyse = new DataAnalyseImpl();
        Map<String, List<String>> processInfoMap = (Map<String, List<String>>) dataAnalyse.procInfoAnalyse(analyseType, processInfo);
        return processInfoMap;

    }

    /**
     * 获取主机KPI信息，主要是主机CPU使用率，主机内存使用率，文件系统使用率
     *
     * @param hostId
     * @return
     * @throws Exception
     */
    @Override
    public JSONArray acqHostKpiInfo(String hostId) throws Exception {
        // 获取主机参数信息
        AcqParamBean paramBean = CommonSvUtil.getHostExeShellInfo(hostId, "16");
        // 分析类型
        String analyseType = paramBean.getKpiType();// 分析类型

        // 数据采集
        IAcquire acquireEngine = new BaseAcquireEngine();
        String processInfo = acquireEngine.acquire(paramBean);
        if (StringUtils.isBlank(processInfo)) {
            return new JSONArray();
        }

        // 数据分析
        IDataAnalyse dataAnalyse = new DataAnalyseImpl();
        processInfo = (String) dataAnalyse.procInfoAnalyse(analyseType, processInfo);

        JSONArray jsonArray = new JSONArray();
        String[] infoArr = processInfo.split(":");
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("KPI_CPU", infoArr[0]);
        jsonObj.put("KPI_MEM", infoArr[1]);
        jsonObj.put("KPI_FILE", infoArr[2]);
        jsonObj.put("KPI_DATE", DateUtil.date2String(DateUtil.getNowDate(), "HH:mm"));
        jsonArray.add(jsonObj);

        return jsonArray;
    }

    @Override
    public JSONArray acqOneProcessKPiInfo(IBOAIMonPhysicHostValue host, String serverCode) throws Exception {
        // shell返回的错误码转化为日志错误信息用map
        Map<String, String> errorMap = new HashMap<String, String>();
        errorMap.put("ERR_PARAM", "入参错误，数量多于一个");
        errorMap.put("NONE", "未查到数据");
        errorMap.put("GT1", "查到数据不止一个");

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObj = new JSONObject();
        try {
            /****** 这里需要查询应用基本信息，匹配当前返回的kpi信息，展示到前台 **************/
            IBaseCommonSV sv = (IBaseCommonSV) ServiceFactory.getService(IBaseCommonSV.class);
            IBOAIMonServerValue serverInfo = sv.qryServerByServerCode(serverCode);
            if (serverInfo == null) {
                jsonObj.put("SERVER_ID", "NONE");
                jsonObj.put("SERVER_CODE", "NONE");
                jsonObj.put("SERVER_NAME", "NONE");
            } else {
                jsonObj.put("SERVER_ID", serverInfo.getServerId());
                jsonObj.put("SERVER_CODE", serverInfo.getServerCode());
                jsonObj.put("SERVER_NAME", serverInfo.getServerName());
            }
            /******* 如果查询进程kpi信息中出现异常或返回信息格式不对（没有以：分隔），则表明该进程异常 *******************/

            String hostId = Long.toString(host.getHostId());
            BOAIMonGroupBean bean = sv.queryGroupByHostId(hostId);
            jsonObj.put("HOST_GROUP", bean == null ? "NONE" : bean.getGroupName());
            jsonObj.put("HOST_NAME", host.getHostName() + "(" + host.getHostIp() + ")");
            // 获取主机参数信息
            AcqParamBean paramBean = CommonSvUtil.getHostExeShellInfo(hostId, "14");
            paramBean.getParamList().add(serverCode);
            // 分析类型
            String analyseType = paramBean.getKpiType();// 分析类型

            // 数据采集
            IAcquire acquireEngine = new BaseAcquireEngine();
            String processInfo = acquireEngine.acquire(paramBean);

            if (StringUtils.isNotBlank(processInfo) && processInfo.indexOf(":") > 0) {
                // 数据分析
                IDataAnalyse dataAnalyse = new DataAnalyseImpl();
                processInfo = (String) dataAnalyse.procInfoAnalyse(analyseType, processInfo);
                String[] infoArr = processInfo.split(":");
                jsonObj.put("PID", infoArr[0]);
                jsonObj.put("USER_NAME", infoArr[1]);
                jsonObj.put("KPI_CPU", infoArr[2] + "%");
                jsonObj.put("KPI_MEM", infoArr[3] + "%");
                jsonObj.put("PROC_STATE", "\u6b63\u5e38");
            } else {
                jsonObj.put("PID", "NONE");
                jsonObj.put("USER_NAME", "NONE");
                jsonObj.put("KPI_CPU", "0%");
                jsonObj.put("KPI_MEM", "0%");
                jsonObj.put("PROC_STATE", "\u5f02\u5e38");
                log.error("查询主机KPI异常，主机ID为" + hostId + ",异常为：" + errorMap.get(processInfo));
            }
        } catch (Exception e) {
            String hostId = host == null ? "null" : Long.toString(host.getHostId());
            jsonObj.put("HOST_GROUP", "NONE");
            jsonObj.put("HOST_NAME", "NONE");
            log.error("查询主机KPI异常，主机ID为" + hostId + ",异常为：", e);
            jsonObj.put("PID", "NONE");
            jsonObj.put("USER_NAME", "NONE");
            jsonObj.put("KPI_CPU", "0%");
            jsonObj.put("KPI_MEM", "0%");
            jsonObj.put("PROC_STATE", "\u5f02\u5e38");
        }

        jsonArray.add(jsonObj);
        return jsonArray;
    }

    @Override
    public Map<String, String> acqProcState(Map<String, List<String>> paramMap, int timeout) throws RemoteException, Exception {
        // 封装线程调用接口
        Iterator<String> it = paramMap.keySet().iterator();
        String keyHostId = null;
        List<ProcInfoCallable> callableList = new ArrayList<ProcInfoCallable>();
        //辅助类，生成不同的Id，单例模式
        IdWorker work = IdWorker.getInstance();
        while (it.hasNext()) {
            keyHostId = it.next();
            AcqParamBean paramBean = CommonSvUtil.getHostExeShellInfo(keyHostId, "10");

            List<String> appCodeList = paramMap.get(keyHostId);
            if (null != appCodeList && appCodeList.size() > 0) {
                for (String appCode : appCodeList) {
                    AcqParamBean tempBean = new AcqParamBean();
                    tempBean.setAcqType(paramBean.getAcqType());
                    tempBean.setIp(paramBean.getIp());
                    tempBean.setKpiType(paramBean.getKpiType());
                    tempBean.setPassword(paramBean.getPassword());
                    tempBean.setPath(paramBean.getPath());
                    tempBean.setPort(paramBean.getPort());
                    tempBean.setShell(paramBean.getShell());
                    tempBean.setUsername(paramBean.getUsername());
                    tempBean.setTmpShellFileName(work.nextId() + paramBean.getTmpShellFileName());
                    tempBean.setServerCode(appCode);
                    tempBean.setTimeout(timeout);
                    tempBean.setLookupUserName(paramBean.getLookupUserName());
                    tempBean.setParamList(paramBean.getParamList());

                    List<String> paramList = new ArrayList<String>();
                    paramList.add(appCode);
                    paramList.add(tempBean.getLookupUserName());
                    tempBean.setParamList(paramList);
                    callableList.add(new ProcInfoCallable(tempBean));
                }
            }
        }

        // 进程状态采集
        IAcquire acqEngine = new BaseAcquireEngine();

        Map<String, String> map = acqEngine.acqProcessState(callableList);

        return map;
    }

    public static void main(String[] args) throws Exception {
        IBusiProcessSV sv = (IBusiProcessSV) ServiceFactory.getService(IBusiProcessSV.class);
        String hostId = "5";
        //  String str = sv.acqWarningInfo(hostId);
        // System.out.println(str);
        //System.out.println(K.j("aicrm"));

        //        BusiProcessSVImpl sv = 

        //String serverCode = "monexe";
        /*  Map<String, List<String>> map = new HashMap<String, List<String>>();
         List<String> list = new ArrayList<String>();
         list.add("ams_task_d03_A_exe03");
         list.add("ams_task_d03_A_exe02");
         list.add("ams_task_d03_A_exe01");
         map.put("520", list);
         System.out.println(map);
         System.out.println(ExternalSvUtil.acqProcState(map, 5000));
         map.clear();
         List<String> list1 = new ArrayList<String>();
         list1.add("ams_task_d09_D_exe01");
         list1.add("ams_task_d09_D_exe03");
         list1.add("ams_task_d09_D_exe02");
         map.put("521", list1);
         System.out.println(map);
         System.out.println(ExternalSvUtil.acqProcState(map, 5000));*/
        /*String hostId = "4";
        sv.acqProcessKpiInfo(hostId);*/
        // sv.acqProcKpiInfo(hostId);
        // sv.acqProcKpiInfo(hostId);
        // JSONArray jsonArr = sv.acqOneProcessKPiInfo(hostId, serverCode);
        // System.out.println(jsonArr);
        // long beginTime = System.currentTimeMillis();
        // for (int i = 0; i < 5; i++) {
        // long bt = System.currentTimeMillis();
        // jsonArr = sv.acqProcessKpiInfo("4");
        // long et = System.currentTimeMillis();
        //
        // System.out.println("Per Time :" + (et - bt));
        // }
        //
        // long endTime = System.currentTimeMillis();
        // System.out.println("Total Use Time:" + (endTime - beginTime));

        // jsonArr = sv.acqHostKpiInfo("4");
        // System.out.println(jsonArr);
        //
        // JSONArray acqOneProcessKPiInfo = sv.acqOneProcessKPiInfo("4",
        // "monexe");
        // System.out.println(acqOneProcessKPiInfo);

    }


}
