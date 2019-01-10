package com.asiainfo.monitor.busi.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.complex.mbean.standard.action.ActionSummary;
import com.ai.appframe2.complex.mbean.standard.sql.SQLSummary;
import com.ai.appframe2.complex.mbean.standard.sv.SVMethodSummary;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.asyn.operation.impl.CheckActionStat;
import com.asiainfo.monitor.busi.asyn.operation.impl.CheckSVMethodStat;
import com.asiainfo.monitor.busi.asyn.operation.impl.CheckSqlStat;
import com.asiainfo.monitor.busi.config.ServerConfig;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;
import com.asiainfo.monitor.busi.service.interfaces.IAPIShowStatSV;
import com.asiainfo.monitor.busi.stat.ActionMultitaskStat;
import com.asiainfo.monitor.busi.stat.SVMethodMultitaskStat;
import com.asiainfo.monitor.busi.stat.SqlMultitaskStat;
import com.asiainfo.monitor.interapi.api.statistics.ActionMonitorApi;
import com.asiainfo.monitor.interapi.api.statistics.SQLMonitorApi;
import com.asiainfo.monitor.interapi.api.statistics.SVMethodMonitorApi;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.util.TypeConst;
import com.asiainfo.monitor.tools.util.Util;

public class APIShowStatSVImpl implements IAPIShowStatSV
{

    private static transient Log log = LogFactory.getLog(APIShowStatSVImpl.class);

    /**
     * 读取在线Sql统计的状态
     * 
     * @param appId
     * @return
     */
    public boolean fetchSqlMonitorState(String appId) throws Exception
    {
        if(StringUtils.isBlank(appId)) {
            return false;
        }
        boolean result = false;
        try {
            IAIMonServerSV serverSV = (IAIMonServerSV) ServiceFactory.getService(IAIMonServerSV.class);
            ServerConfig appServer = serverSV.getServerByServerId(appId);
            if(appServer == null) {
                // 没找到应用实例[{0}]
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000070", appId));
            }
            if(!appServer.supportSql()) {
                // 该应用不支持Sql检查和统计
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000313", appId));
            }
            if(appServer.getJmxSet() == null || appServer.getJmxSet().getValue().equals("OFF") || appServer.getJmxSet().getValue().equals("FALSE")) {
                // "应用端[appId:"+appId+"]未启动Jmx注册"
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000071", appId));
            }
            result = SQLMonitorApi.fetchStatus(appServer.getLocator_Type(), appServer.getLocator());

        } catch(Exception e) {
            log.error("getSqlInfo" + e.getMessage());
        }
        return result;
    }

    /**
     * 检查多应用SQL统计状态信息
     * 
     * @param appIds
     * @return
     * @throws Exception
     */
    public List checkSqlStatByAppIds(Object[] appIds) throws Exception
    {
        List result = null;
        try {
            // 单个应用可能不是App，WEB没有SQL
            if(appIds != null && appIds.length == 1) {
                IAIMonServerSV serverSV = (IAIMonServerSV) ServiceFactory.getService(IAIMonServerSV.class);
                ServerConfig appServer = serverSV.getServerByServerId(String.valueOf(appIds[0]));
                if(appServer != null && !appServer.supportSql()) {
                    // 该应用不支持SQL统计
                    throw new Exception(AIMonLocaleFactory.getResource("MOS0000313", appServer.getApp_Id()));
                }
            }
            CheckSqlStat checkStat = new CheckSqlStat();
            int threadCount = (appIds.length >> 1) + 1;
            int cpuCount = Runtime.getRuntime().availableProcessors();
            if(threadCount > (cpuCount * 3))
                threadCount = cpuCount * 3;
            result = checkStat.asynOperation(threadCount, -1, appIds, -1);
        } catch(Exception e) {
            log.error("Call APIShowStatSVImpl's Method checkSqlStatByAppIds has Exception :" + e.getMessage());
        }
        return result;
    }

    /**
     * 检查多应用SVMethod统计状态信息
     * 
     * @param appIds
     * @return
     * @throws Exception
     */
    public List checkSVMethodStatByAppIds(Object[] appIds) throws Exception
    {
        List result = null;
        try {
            // 单个应用可能不是App，WEB没有Method
            if(appIds != null && appIds.length == 1) {
                IAIMonServerSV serverSV = (IAIMonServerSV) ServiceFactory.getService(IAIMonServerSV.class);
                ServerConfig appServer = serverSV.getServerByServerId(String.valueOf(appIds[0]));
                if(appServer != null && !appServer.supportMethod()) {
                    // 该应用不支持Method统计
                    throw new Exception(AIMonLocaleFactory.getResource("MOS0000314", appServer.getApp_Id()));
                }
            }
            CheckSVMethodStat checkStat = new CheckSVMethodStat();
            int threadCount = (appIds.length >> 1) + 1;
            int cpuCount = Runtime.getRuntime().availableProcessors();
            if(threadCount > (cpuCount * 3))
                threadCount = cpuCount * 3;
            result = checkStat.asynOperation(threadCount, -1, appIds, -1);
        } catch(Exception e) {
            log.error("Call APIShowStatSVImpl's Method checkSVMethodStatByAppIds has Exception :" + e.getMessage());
        }
        return result;
    }

    /**
     * 检查多应用Action统计状态信息
     * 
     * @param appIds
     * @return
     * @throws Exception
     */
    public List checkActionStatByAppIds(Object[] appIds) throws Exception
    {
        List result = null;
        try {
            // 单个应用可能是App，App没有Action
            if(appIds != null && appIds.length == 1) {
                IAIMonServerSV serverSV = (IAIMonServerSV) ServiceFactory.getService(IAIMonServerSV.class);
                ServerConfig appServer = serverSV.getServerByServerId(String.valueOf(appIds[0]));
                if(appServer != null && !appServer.supportAction()) {
                    // 该应用不支持Action统计
                    throw new Exception(AIMonLocaleFactory.getResource("MOS0000315", appServer.getApp_Id()));
                }
            }
            CheckActionStat checkStat = new CheckActionStat();
            int threadCount = (appIds.length >> 1) + 1;
            int cpuCount = Runtime.getRuntime().availableProcessors();
            if(threadCount > (cpuCount * 3))
                threadCount = cpuCount * 3;
            result = checkStat.asynOperation(threadCount, -1, appIds, -1);
        } catch(Exception e) {
            log.error("Call APIShowStatSVImpl's Method checkActionStatByAppIds has Exception :" + e.getMessage());
        }
        return result;
    }

    /**
     * 根据条件返回
     * 
     * @param appId
     * @param clazz
     * @param method
     * @param start
     * @param end
     * @return
     * @throws Exception
     */
    public List getSVMehtodInfo(String appId) throws Exception
    {
        if(StringUtils.isBlank(appId))
            return null;
        List result = null;
        try {
            IAIMonServerSV serverSV = (IAIMonServerSV) ServiceFactory.getService(IAIMonServerSV.class);
            ServerConfig appServer = serverSV.getServerByServerId(appId);
            if(appServer == null) {
                // 没找到应用实例[{0}]
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000070", appId));
            }
            if(!appServer.supportMethod()) {
                // 该应用不支持Method检查和统计
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000314", appId));
            }
            if(appServer.getJmxSet() == null || appServer.getJmxSet().getValue().equals("OFF") || appServer.getJmxSet().getValue().equals("FALSE")) {
                // "应用端[appId:"+appId+"]未启动Jmx注册"
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000071", appId));
            }

            SVMethodSummary[] summarys = SVMethodMonitorApi.getSVMehtodInfo(appServer.getLocator_Type(), appServer.getLocator(), "");
            if(summarys != null && summarys.length > 0) {
                result = new ArrayList();
                Collections.addAll(result, summarys);
            }
        } catch(Exception e) {
            log.error("Call APIShowStatSVImpl's Method getSVMehtodInfo has Exception :" + e.getMessage());
        }
        return result;
    }

    /**
     * 读取服务器列表的在线服务调用情况
     * 
     * @param appIds
     * @param clazz
     * @param method
     * @param start
     * @param end
     * @return
     * @throws Exception
     */
    public List getActionInfoByIds(Object[] appIds, String clazz, String method, Integer start, Integer end) throws Exception
    {
        if(appIds == null || appIds.length < 1) {
            return null;
        }
        List result = new ArrayList();
        try {
            int threadCount = (appIds.length >> 1) + 1;
            int cpuCount = Runtime.getRuntime().availableProcessors();
            if(threadCount > (cpuCount * 3)) {
                threadCount = cpuCount * 3;
            }
            String condition = "";
            if(!StringUtils.isBlank(clazz)) {
                condition = clazz;
            }
            if(!StringUtils.isBlank(method)) {
                condition = condition + "|" + method;
            }
            ActionMultitaskStat svStat = new ActionMultitaskStat(threadCount, -1, appIds, condition);
            Map summaryMap = svStat.getSummary();
            if(!summaryMap.isEmpty()) {
                Iterator it = summaryMap.keySet().iterator();
                int startCount = start.intValue() == -1 ? 0 : start.intValue() - 1;
                int endCount = end.intValue() == -1 ? summaryMap.size() : end.intValue();
                int i = 0;
                while(it.hasNext()) {
                    if(i < startCount) {
                        i++;
                        it.next();
                        continue;
                    }
                    if(i >= endCount) {
                        break;
                    }
                    String key = it.next().toString();
                    ActionSummary item = (ActionSummary) summaryMap.get(key);
                    Map itemContainer = new HashMap();
                    itemContainer.put("HOSTID", "");
                    itemContainer.put("HOSTNAME", "");
                    itemContainer.put("APPSERVERID", "");
                    itemContainer.put("APPSERVERNAME", "");
                    itemContainer.put("CLASSNAME", item.getClassName());
                    itemContainer.put("METHODNAME", item.getMethodName());
                    itemContainer.put("MIN", new Long(item.getMin()));
                    itemContainer.put("MAX", new Long(item.getMax()));
                    itemContainer.put("AVG", new Long(item.getAvg()));
                    itemContainer.put("TOTALCOUNT", new Long(item.getTotalCount()));
                    itemContainer.put("SUCCESSCOUNT", new Long(item.getSuccessCount()));
                    itemContainer.put("FAILCOUNT", new Long(item.getFailCount()));
                    itemContainer.put("LASTUSETIME", new Long(item.getLastUseTime()));
                    itemContainer.put("TOTALUSETIME", new Long(item.getTotalUseTime()));
                    itemContainer.put("LAST", Util.formatDateFromLog(item.getLast()));

                    result.add(itemContainer);
                    i++;
                }
            }

        } catch(Exception e) {
            log.error("Call APIShowStatSVImpl's Method getActionInfoByIds has Exception :" + e.getMessage());
        }
        return result;
    }

    /**
     * 读取服务器列表的在线SQL调用情况
     * 
     * @param appIds
     * @param condition
     * @param start
     * @param end
     * @return
     * @throws Exception
     */
    public List getSqlInfoByIds(Object[] appIds, String condition, Integer start, Integer end) throws Exception
    {
        if(appIds == null || appIds.length < 1)
            return null;
        List result = null;
        try {
            int threadCount = (appIds.length >> 1) + 1;
            int cpuCount = Runtime.getRuntime().availableProcessors();
            if(threadCount > (cpuCount * 3))
                threadCount = cpuCount * 3;
            SqlMultitaskStat sqlStat = new SqlMultitaskStat(threadCount, -1, appIds, condition);
            Map summaryMap = sqlStat.getSummary();
            if(!summaryMap.isEmpty()) {
                result = new ArrayList();
                Iterator it = summaryMap.keySet().iterator();
                int startCount = start.intValue() == -1 ? 0 : start.intValue() - 1;
                int endCount = end.intValue() == -1 ? summaryMap.size() : end.intValue();
                int i = 0;
                while(it.hasNext()) {
                    if(i < startCount) {
                        i++;
                        it.next();
                        continue;
                    }
                    if(i >= endCount)
                        break;
                    String key = it.next().toString();
                    SQLSummary item = (SQLSummary) summaryMap.get(key);
                    Map itemContainer = new HashMap();
                    itemContainer.put("SERVER_ID", "");
                    itemContainer.put("SERVER_NAME", "");
                    itemContainer.put("APPSERVER_ID", "");
                    itemContainer.put("APPSERVER_NAME", "");

                    itemContainer.put("SQL", item.getSql());
                    itemContainer.put("TYPE", item.getType());
                    itemContainer.put("LAST", Util.formatDateFromLog(item.getLast()));
                    itemContainer.put("MIN", item.getMin());
                    itemContainer.put("MAX", item.getMax());
                    itemContainer.put("AVG", item.getAvg());
                    itemContainer.put("LASTUSETIME", item.getLastUseTime());
                    itemContainer.put("COUNT", item.getCount());
                    itemContainer.put("TOTALUSETIME", item.getTotalUseTime());

                    result.add(itemContainer);
                    i++;
                }
            }
        } catch(Exception e) {
            log.error("Call APIShowStatSVImpl's Method getSqlInfoByIds has Exception :" + e.getMessage());
        }
        return result;
    }

    /**
     * 查询实时sql统计信息
     * 
     * @param appId
     * @param condition
     * @return
     */
    public List getSqlInfo(String appId, String condition) throws Exception
    {
        List ret = new ArrayList();
        try {
            IAIMonServerSV serverSV = (IAIMonServerSV) ServiceFactory.getService(IAIMonServerSV.class);
            ServerConfig appServer = serverSV.getServerByServerId(appId);
            if(appServer == null) {
                // 没找到应用实例[{0}]
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000070", appId));
            }
            if(!appServer.supportSql()) {
                // 该应用不支持SQL检查和统计
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000313", appId));
            }
            if(appServer.getJmxSet() == null || appServer.getJmxSet().getValue().equals("OFF") || appServer.getJmxSet().getValue().equals("FALSE")) {
                // "应用端[appId:"+appId+"]未启动Jmx注册"
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000071", appId));
            }
            SQLSummary[] ss = SQLMonitorApi.getSQLInfo(appServer.getLocator_Type(), appServer.getLocator(), condition);
            for(int i = 0; i < ss.length; i++) {
                HashMap map = new HashMap();
                map.put("SQL", ss[i].getSql());
                map.put("TYPE", ss[i].getType());
                map.put("LAST", Util.formatDateFromLog(ss[i].getLast()));
                map.put("MIN", ss[i].getMin());
                map.put("MAX", ss[i].getMax());
                map.put("AVG", ss[i].getAvg());
                map.put("LASTUSETIME", ss[i].getLastUseTime());
                map.put("COUNT", ss[i].getCount());
                map.put("TOTALUSETIME", ss[i].getTotalUseTime());
                ret.add(map);
            }
        } catch(Exception e) {
            log.error("getSqlInfo" + e.getMessage());
        }
        return ret;
    }

    /**
     * 读取服务器列表的在线服务调用情况
     * 
     * @param appIds
     * @param clazz
     * @param method
     * @param start
     * @param end
     * @return
     * @throws Exception
     */
    public List getSVMehtodInfoByIds(Object[] appIds, String clazz, String method, Integer start, Integer end) throws Exception
    {
        if(appIds == null || appIds.length < 1)
            return null;
        List result = null;
        try {
            int threadCount = (appIds.length >> 1) + 1;
            int cpuCount = Runtime.getRuntime().availableProcessors();
            if(threadCount > (cpuCount * 3))
                threadCount = cpuCount * 3;
            String condition = "";
            if(!StringUtils.isBlank(clazz))
                condition = clazz;
            if(!StringUtils.isBlank(method))
                condition = condition + TypeConst._SPLIT_CHAR + method;

            SVMethodMultitaskStat svStat = new SVMethodMultitaskStat(threadCount, -1, appIds, condition);
            Map summaryMap = svStat.getSummary();
            if(!summaryMap.isEmpty()) {
                result = new ArrayList();
                Iterator it = summaryMap.keySet().iterator();

                int startCount = start.intValue() == -1 ? 0 : start.intValue() - 1;

                int endCount = end.intValue() == -1 ? summaryMap.size() : end.intValue();

                int i = 0;

                while(it.hasNext()) {

                    if(i < startCount) {
                        i++;
                        it.next();
                        continue;
                    }
                    if(i >= endCount)
                        break;

                    String key = it.next().toString();
                    SVMethodSummary item = (SVMethodSummary) summaryMap.get(key);
                    Map itemContainer = new HashMap();
                    itemContainer.put("SERVER_ID", "");
                    itemContainer.put("SERVER_NAME", "");
                    itemContainer.put("APPSERVER_ID", "");
                    itemContainer.put("APPSERVER_NAME", "");
                    itemContainer.put("CLASSNAME", item.getClassName());
                    itemContainer.put("METHODNAME", item.getMethodName());
                    itemContainer.put("MIN", new Long(item.getMin()));
                    itemContainer.put("MAX", new Long(item.getMax()));
                    itemContainer.put("AVG", new Long(item.getAvg()));
                    itemContainer.put("TOTALCOUNT", new Long(item.getTotalCount()));
                    itemContainer.put("SUCCESSCOUNT", new Long(item.getSuccessCount()));
                    itemContainer.put("FAILCOUNT", new Long(item.getFailCount()));
                    itemContainer.put("LASTUSETIME", new Long(item.getLastUseTime()));
                    itemContainer.put("TOTALUSETIME", new Long(item.getTotalUseTime()));
                    itemContainer.put("LAST", Util.formatDateFromLog(item.getLast()));

                    result.add(itemContainer);
                    i++;
                }
            }
        } catch(Exception e) {
            log.error("Call APIShowStatSVImpl's Method getSVMehtodInfoByIds has Exception :" + e.getMessage());
        }
        return result;
    }

    /**
     * 读取服务器列表的在线服务调用情况
     * 
     * @param appIds
     * @param clazz
     * @param method
     * @param start
     * @param end
     * @return
     * @throws Exception
     */
    public List getSVMehtodInfoByIds(Object[] appIds, String clazz, String method) throws Exception
    {
        return getSVMehtodInfoByIds(appIds, clazz, method, -1, -1);
    }

    public DataContainerInterface[] getSqlInfoForAppFrame(String appId, String condition) throws Exception
    {
        List ret = new ArrayList();
        try {
            IAIMonServerSV serverSV = (IAIMonServerSV) ServiceFactory.getService(IAIMonServerSV.class);
            ServerConfig appServer = serverSV.getServerByServerId(appId);
            if(appServer == null) {
                // 没找到应用实例[{0}]
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000070", appId));
            }
            if(!appServer.supportSql()) {
                // 该应用不支持SQL检查和统计
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000313", appId));
            }
            if(appServer.getJmxSet() == null || appServer.getJmxSet().getValue().equals("OFF") || appServer.getJmxSet().getValue().equals("FALSE")) {
                // "应用端[appId:"+appId+"]未启动Jmx注册"
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000071", appId));
            }
            SQLSummary[] ss = SQLMonitorApi.getSQLInfo(appServer.getLocator_Type(), appServer.getLocator(), condition);
            for(int i = 0; i < ss.length; i++) {
                DataContainerInterface item = new DataContainer();
                item.set("SQL", ss[i].getSql());
                item.set("TYPE", ss[i].getType());
                item.set("LAST", Util.formatDateFromLog(ss[i].getLast()));
                item.set("MIN", ss[i].getMin());
                item.set("MAX", ss[i].getMax());
                item.set("AVG", ss[i].getAvg());
                item.set("LASTUSETIME", ss[i].getLastUseTime());
                item.set("COUNT", ss[i].getCount());
                item.set("TOTALUSETIME", ss[i].getTotalUseTime());
                ret.add(item);
            }
        } catch(Exception e) {
            log.error("getSqlInfo" + e.getMessage());
        }

        return (DataContainerInterface[]) ret.toArray(new DataContainerInterface[0]);
    }

    /**
     * 根据条件返回
     * 
     * @param appId
     * @param clazz
     * @param method
     * @param start
     * @param end
     * @return
     * @throws Exception
     */
    public List getActionInfo(String appId, String clazz, String method, Integer start, Integer end) throws Exception
    {
        if(StringUtils.isBlank(appId))
            return null;
        List result = null;
        try {
            IAIMonServerSV serverSV = (IAIMonServerSV) ServiceFactory.getService(IAIMonServerSV.class);
            ServerConfig appServer = serverSV.getServerByServerId(appId);

            if(appServer == null) {
                // 没找到应用实例[{0}]
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000070", appId));
            }
            if(!appServer.supportAction()) {
                // 该应用不支持Action检查和统计
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000315", appId));
            }
            if(appServer.getJmxSet() == null || appServer.getJmxSet().getValue().equals("OFF") || appServer.getJmxSet().getValue().equals("FALSE")) {
                // "应用端[appId:"+appId+"]未启动Jmx注册"
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000071", appId));
            }
            String condition = "";
            if(!StringUtils.isBlank(clazz))
                condition = clazz;
            if(!StringUtils.isBlank(method))
                condition = condition + "|" + method;

            ActionSummary[] summarys = ActionMonitorApi.getActionInfo(appServer.getLocator_Type(), appServer.getLocator(), condition);

            if(summarys != null) {
                result = new ArrayList(summarys.length);
                int endCount = summarys.length;
                if(endCount > end.intValue() && end.intValue() != -1)
                    endCount = end.intValue();
                for(int i = start.intValue() == -1 ? 0 : start.intValue() - 1; i < endCount; i++) {
                    result.add(summarys[i]);
                }
            }
        } catch(Exception e) {
            log.error("Call APIShowStatSVImpl's Method getActionInfo has Exception :" + e.getMessage());
        }
        return result;
    }

    /**
     * 读取在线Action统计的状态
     * 
     * @param appId
     * @return
     */
    public boolean fecthActionMonitorState(String appId) throws Exception
    {
        boolean result = false;
        if(StringUtils.isBlank(appId)) {
            return result;
        }

        try {
            IAIMonServerSV serverSV = (IAIMonServerSV) ServiceFactory.getService(IAIMonServerSV.class);
            ServerConfig appServer = serverSV.getServerByServerId(appId);
            if(appServer == null) {
                // 没找到应用实例[{0}]
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000070", appId));
            }
            if(!appServer.supportAction()) {
                // 该应用不支持Action检查和统计
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000315", appId));
            }
            if(appServer.getJmxSet() == null || appServer.getJmxSet().getValue().equals("OFF") || appServer.getJmxSet().getValue().equals("FALSE")) {
                // "应用端[appId:"+appId+"]未启动Jmx注册"
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000071", appId));
            }
            result = ActionMonitorApi.fetchStatus(appServer.getLocator_Type(), appServer.getLocator());

        } catch(Exception e) {
            log.error("Call ActionStaticAction's Method open has Exception :" + e.getMessage());
        }
        return result;
    }

    /**
     * 根据条件返回
     * 
     * @param appId
     * @param clazz
     * @param method
     * @param start
     * @param end
     * @return
     * @throws Exception
     */
    public List getSVMehtodInfo(String appId, String clazz, String method, Integer start, Integer end) throws Exception
    {
        if(StringUtils.isBlank(appId)) {
            return null;
        }
        List result = null;
        try {
            IAIMonServerSV serverSV = (IAIMonServerSV) ServiceFactory.getService(IAIMonServerSV.class);
            ServerConfig appServer = serverSV.getServerByServerId(appId);
            if(appServer == null) {
                // 没找到应用实例[{0}]
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000070", appId));
            }
            if(!appServer.supportMethod()) {
                // 该应用不支持Method检查和统计
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000314", appId));
            }
            if(appServer.getJmxSet() == null || appServer.getJmxSet().getValue().equals("OFF") || appServer.getJmxSet().getValue().equals("FALSE")) {
                // "应用端[appId:"+appId+"]未启动Jmx注册"
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000071", appId));
            }
            String condition = "";
            if(!StringUtils.isBlank(clazz))
                condition = clazz;
            if(!StringUtils.isBlank(method))
                condition = condition + "|" + method;

            SVMethodSummary[] summarys = SVMethodMonitorApi.getSVMehtodInfo(appServer.getLocator_Type(), appServer.getLocator(), condition);

            int endCount = summarys.length;
            if(endCount > end.intValue() && end.intValue() != -1)
                endCount = end.intValue();

            if(summarys != null) {
                result = new ArrayList(summarys.length);
                for(int i = start.intValue() == -1 ? 0 : start.intValue() - 1; i < endCount; i++) {
                    Map itemContainer = new HashMap();
                    itemContainer.put("SERVER_ID", appServer.getNodeConfig().getNode_Id());
                    itemContainer.put("SERVER_NAME", appServer.getNodeConfig().getNode_Name());
                    itemContainer.put("APPSERVER_ID", new Long(appServer.getApp_Id()));
                    itemContainer.put("APPSERVER_NAME", appServer.getApp_Name());
                    itemContainer.put("RMIURL", appServer.getLocator());
                    itemContainer.put("CLASSNAME", summarys[i].getClassName());
                    itemContainer.put("METHODNAME", summarys[i].getMethodName());
                    itemContainer.put("MIN", new Long(summarys[i].getMin()));
                    itemContainer.put("MAX", new Long(summarys[i].getMax()));
                    itemContainer.put("AVG", new Long(summarys[i].getAvg()));
                    itemContainer.put("TOTALCOUNT", new Long(summarys[i].getTotalCount()));
                    itemContainer.put("SUCCESSCOUNT", new Long(summarys[i].getSuccessCount()));
                    itemContainer.put("FAILCOUNT", new Long(summarys[i].getFailCount()));
                    itemContainer.put("LASTUSETIME", new Long(summarys[i].getLastUseTime()));
                    itemContainer.put("TOTALUSETIME", new Long(summarys[i].getTotalUseTime()));
                    itemContainer.put("LAST", Util.formatDateFromLog(summarys[i].getLast()));
                    itemContainer.put("PAGE_TOTAL_CNT", summarys.length);
                    result.add(itemContainer);
                }
            }
        } catch(Exception e) {
            log.error("Call APIShowStatSVImpl's Method getSVMehtodInfo has Exception :" + e.getMessage());
        }
        return result;
    }

    /**
     * 读取在线服务统计的状态
     * 
     * @param appId
     * @return
     * @throws Exception
     */
    public boolean fetchSVMethodState(String appId) throws Exception
    {
        if(StringUtils.isBlank(appId))
            return false;
        boolean result = false;
        try {
            IAIMonServerSV serverSV = (IAIMonServerSV) ServiceFactory.getService(IAIMonServerSV.class);
            ServerConfig appServer = serverSV.getServerByServerId(appId);
            if(appServer == null) {
                // 没找到应用实例[{0}]
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000070", appId));
            }
            if(!appServer.supportMethod()) {
                // 该应用不支持Method检查和统计
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000314", appId));
            }
            if(appServer.getJmxSet() == null || appServer.getJmxSet().getValue().equals("OFF") || appServer.getJmxSet().getValue().equals("FALSE")) {
                // "应用端[appId:"+appId+"]未启动Jmx注册"
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000071", appId));
            }
            result = SVMethodMonitorApi.fetchState(appServer.getLocator_Type(), appServer.getLocator());
        } catch(Exception e) {
            log.error("Call APIShowStatSVImpl's Method getSVMehtodInfo has Exception :" + e.getMessage());
            return false;
        }
        return result;
    }

    /**
     * 根据条件返回
     * 
     * @param appId
     * @param clazz
     * @param method
     * @param start
     * @param end
     * @return
     * @throws Exception
     */
    public DataContainerInterface[] getSVMehtodInfoForAppFrame(String appId, String clazz, String method, Integer start, Integer end)
            throws Exception
    {

        if(StringUtils.isBlank(appId)) {
            return null;
        }
        List result = null;
        try {
            IAIMonServerSV serverSV = (IAIMonServerSV) ServiceFactory.getService(IAIMonServerSV.class);
            ServerConfig appServer = serverSV.getServerByServerId(appId);
            if(appServer == null) {
                // 没找到应用实例[{0}]
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000070", appId));
            }
            if(!appServer.supportMethod()) {
                // 该应用不支持Method检查和统计
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000314", appId));
            }
            if(appServer.getJmxSet() == null || appServer.getJmxSet().getValue().equals("OFF") || appServer.getJmxSet().getValue().equals("FALSE")) {
                // "应用端[appId:"+appId+"]未启动Jmx注册"
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000071", appId));
            }
            String condition = "";
            if(!StringUtils.isBlank(clazz))
                condition = clazz;
            if(!StringUtils.isBlank(method))
                condition = condition + "|" + method;

            SVMethodSummary[] summarys = SVMethodMonitorApi.getSVMehtodInfo(appServer.getLocator_Type(), appServer.getLocator(), condition);

            int endCount = summarys.length;
            if(endCount > end.intValue() && end.intValue() != -1)
                endCount = end.intValue();

            if(summarys != null) {
                result = new ArrayList(summarys.length);
                for(int i = start.intValue() == -1 ? 0 : start.intValue() - 1; i < endCount; i++) {
                    DataContainerInterface item = new DataContainer();
                    item.set("SERVER_ID", appServer.getNodeConfig().getNode_Id());
                    item.set("SERVER_NAME", appServer.getNodeConfig().getNode_Name());
                    item.set("APPSERVER_ID", new Long(appServer.getApp_Id()));
                    item.set("APPSERVER_NAME", appServer.getApp_Name());
                    item.set("RMIURL", appServer.getLocator());
                    item.set("CLASSNAME", summarys[i].getClassName());
                    item.set("METHODNAME", summarys[i].getMethodName());
                    item.set("MIN", new Long(summarys[i].getMin()));
                    item.set("MAX", new Long(summarys[i].getMax()));
                    item.set("AVG", new Long(summarys[i].getAvg()));
                    item.set("TOTALCOUNT", new Long(summarys[i].getTotalCount()));
                    item.set("SUCCESSCOUNT", new Long(summarys[i].getSuccessCount()));
                    item.set("FAILCOUNT", new Long(summarys[i].getFailCount()));
                    item.set("LASTUSETIME", new Long(summarys[i].getLastUseTime()));
                    item.set("TOTALUSETIME", new Long(summarys[i].getTotalUseTime()));
                    item.set("LAST", Util.formatDateFromLog(summarys[i].getLast()));
                    result.add(item);
                }
            }
        } catch(Exception e) {
            log.error("Call APIShowStatSVImpl's Method getSVMehtodInfo has Exception :" + e.getMessage());
        }
        return (DataContainerInterface[]) result.toArray(new DataContainerInterface[0]);
    }

}
