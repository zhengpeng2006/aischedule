package com.asiainfo.monitor.busi.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.config.ServerConfig;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;
import com.asiainfo.monitor.busi.service.interfaces.IAPIJvmCapabilitySV;
import com.asiainfo.monitor.busi.stat.JvmMultitaskStat;
import com.asiainfo.monitor.interapi.api.JVM5Api;
import com.asiainfo.monitor.interapi.api.SystemResourceApi;
import com.asiainfo.monitor.interapi.config.AIMemoryInfo;
import com.asiainfo.monitor.interapi.config.AIThreadInfo;
import com.asiainfo.monitor.tools.common.KeyName;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class APIJvmCapabilitySVImpl implements IAPIJvmCapabilitySV
{

    private static transient Log log = LogFactory.getLog(APIJvmCapabilitySVImpl.class);

    /**
     * 获取多应用的虚拟内存信息
     * 
     * @param appIds
     * @return
     * @throws Exception
     */
    public List getJVM5MemoryInfos(Object[] appIds) throws Exception
    {
        List result = null;
        try {
            int threadCount = (appIds.length >> 1) + 1;
            int cpuCount = Runtime.getRuntime().availableProcessors();
            if(threadCount > (cpuCount * 3)) {
                threadCount = cpuCount * 3;
            }
            JvmMultitaskStat jvmStat = new JvmMultitaskStat(threadCount, -1, appIds);
            Map jvmMap = jvmStat.getSummary();
            if(jvmMap != null && jvmMap.size() > 0) {
                result = new ArrayList();
                result.addAll(jvmMap.values());
            }
        } catch(Exception e) {
            log.error(AIMonLocaleFactory.getResource("MOS0000049") + e.getMessage());
        }
        return result;
    }

    /**
     * 获取系统信息
     * 
     * @param appId
     * @return
     * @throws Exception
     */
    public KeyName[] getSystemInfo(String appId, String findKey) throws Exception
    {
        List result = null;
        try {
            if(StringUtils.isBlank(appId)) {
                log.error("Call APIJvmCapabilitySV's Method getSystemInfo,But appId is null");
                return null;
            }
            IAIMonServerSV serverSV = (IAIMonServerSV) ServiceFactory.getService(IAIMonServerSV.class);
            ServerConfig appServer = serverSV.getServerByServerId(appId);

            if(appServer == null) {
                // 没找到应用实例[{0}]
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000070", appId));
            }
            if(appServer.getJmxSet() == null || appServer.getJmxSet().getValue().equals("OFF") || appServer.getJmxSet().getValue().equals("FALSE")) {
                // "应用端[appId:"+appId+"]未启动Jmx注册"
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000071", appId));
            }
            HashMap systemInfo = SystemResourceApi.getPropertiesFromSystem(appServer.getLocator_Type(), appServer.getLocator(), "");
            if(systemInfo == null)
                return null;
            result = new ArrayList();
            Iterator it = systemInfo.keySet().iterator();
            while(it.hasNext()) {
                String key = it.next().toString();
                if(StringUtils.isNotBlank(findKey)) {
                    if(key.indexOf(findKey) >= 0) {
                        String value = systemInfo.get(key).toString();
                        result.add(new KeyName(key, value));
                        continue;
                    }
                } else {
                    String value = systemInfo.get(key).toString();
                    result.add(new KeyName(key, value));
                }

            }

        } catch(Exception e) {
            log.error("Call APIJvmCapabilitySV's Method getSystemInfo has Exception :" + e.getMessage());
        }
        if(result == null)
            return null;
        return (KeyName[]) result.toArray(new KeyName[0]);
    }

    /**
     * 获取服务器线程信息
     * 
     * @param appId
     * @return
     * @throws Exception
     */
    public AIThreadInfo[] getThreadInfo(String appId, String name) throws Exception
    {
        AIThreadInfo[] result = null;
        try {
            if(StringUtils.isBlank(appId)) {
                return null;
            }
            IAIMonServerSV serverSV = (IAIMonServerSV) ServiceFactory.getService(IAIMonServerSV.class);
            ServerConfig appServer = serverSV.getServerByServerId(appId);
            if(appServer == null) {
                // 没找到应用实例[{0}]
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000070", appId));
            }
            if(appServer.getJmxSet() == null || appServer.getJmxSet().getValue().equals("OFF") || appServer.getJmxSet().getValue().equals("FALSE")) {
                // "应用端[appId:"+appId+"]未启动Jmx注册"
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000071", appId));
            }
            String locatorType = appServer.getLocator_Type();
            String locator = appServer.getLocator();
            result = JVM5Api.getThreadInfo(locatorType, locator);

        } catch(Exception e) {
            log.error("Call APIJvmCapabilitySV's Method getThreadInfo has Exception :" + e.getMessage());
        }

        return result;
    }

    /**
     * 返回服务器内存信息
     * 
     * @param appId
     * @return
     * @throws Exception
     */
    public AIMemoryInfo getJVM5MemoryInfo(String appId) throws Exception
    {
        AIMemoryInfo result = null;
        try {
            if(StringUtils.isBlank(appId)) {
                return null;
            }
            IAIMonServerSV serverSV = (IAIMonServerSV) ServiceFactory.getService(IAIMonServerSV.class);
            ServerConfig appServer = serverSV.getServerByServerId(appId);
            if(appServer == null) {
                // 没找到应用实例[{0}]
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000070", appId));
            }
            if(appServer.getJmxSet() == null || appServer.getJmxSet().getValue().equals("OFF") || appServer.getJmxSet().getValue().equals("FALSE")) {
                // "应用端[appId:"+appId+"]未启动Jmx注册"
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000071", appId));
            }
            String locatorType = appServer.getLocator_Type();
            String locator = appServer.getLocator();
            result = JVM5Api.getMemoryInfo(locatorType, locator);

        } catch(Exception e) {
            log.error("Call APIJvmCapabilitySVImpl's Method getJVM5MemoryInfo has Exception :" + e.getMessage());
        }
        return result;

    }

    public DataContainerInterface[] getSystemInfoForAppFrame(String appId, String findKey) throws Exception
    {
        List result = null;
        try {
            if(StringUtils.isBlank(appId)) {
                log.error("Call APIJvmCapabilitySV's Method getSystemInfo,But appId is null");
                return null;
            }
            IAIMonServerSV serverSV = (IAIMonServerSV) ServiceFactory.getService(IAIMonServerSV.class);
            ServerConfig appServer = serverSV.getServerByServerId(appId);

            if(appServer == null) {
                // 没找到应用实例[{0}]
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000070", appId));
            }
            if(appServer.getJmxSet() == null || appServer.getJmxSet().getValue().equals("OFF") || appServer.getJmxSet().getValue().equals("FALSE")) {
                // "应用端[appId:"+appId+"]未启动Jmx注册"
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000071", appId));
            }
            HashMap systemInfo = SystemResourceApi.getPropertiesFromSystem(appServer.getLocator_Type(), appServer.getLocator(), "");
            if(systemInfo == null) {
                return null;
            }
            result = new ArrayList();
            Iterator it = systemInfo.keySet().iterator();
            while(it.hasNext()) {
                String key = it.next().toString();
                if(StringUtils.isNotBlank(findKey)) {
                    if(key.indexOf(findKey) >= 0) {
                        String value = systemInfo.get(key).toString();
                        DataContainerInterface item = new DataContainer();
                        item.set("SYSTEM_KEY", key);
                        item.set("SYSTEM_VALUE", value);
                        result.add(item);
                        continue;
                    }
                } else {
                    String value = systemInfo.get(key).toString();
                    DataContainerInterface item = new DataContainer();
                    item.set("SYSTEM_KEY", key);
                    item.set("SYSTEM_VALUE", value);
                    result.add(item);
                }

            }

        } catch(Exception e) {
            log.error("Call APIJvmCapabilitySV's Method getSystemInfo has Exception :" + e.getMessage());
        }

        if(result == null) {
            return new DataContainerInterface[0];
        }
        return (DataContainerInterface[]) result.toArray(new DataContainerInterface[0]);
    }

}
