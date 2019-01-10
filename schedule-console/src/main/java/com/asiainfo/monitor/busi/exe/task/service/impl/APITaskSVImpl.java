package com.asiainfo.monitor.busi.exe.task.service.impl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.complex.mbean.standard.datasource.DataSourceRuntime;
import com.ai.appframe2.complex.mbean.standard.sv.SVMethodSummary;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAPITaskSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerAtomicSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonSetAtomicSV;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonSetValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonServerValue;
import com.asiainfo.monitor.interapi.api.JVM5Api;
import com.asiainfo.monitor.interapi.api.statistics.SVMethodMonitorApi;
import com.asiainfo.monitor.interapi.api.transaction.TransactionMonitorApi;
import com.asiainfo.monitor.interapi.api.user.UserManagerMonitorApi;
import com.asiainfo.monitor.interapi.config.AIMemoryInfo;
import com.asiainfo.monitor.interapi.config.AITmSummary;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.util.TypeConst;

public class APITaskSVImpl implements IAPITaskSV
{

    private static transient Log log = LogFactory.getLog(APITaskSVImpl.class);

    /**
     * 后台定时任务,获取App应用的服务调用情况
     * 
     * @param appId
     * @param clazz
     * @param method
     * @param start
     * @param end
     * @return
     * @throws Exception
     */
    public List getSVMehtodInfo(String appId) throws RemoteException, Exception
    {
        if(StringUtils.isBlank(appId))
            return null;
        List result = null;
        try {
            IAIMonServerAtomicSV serverSV = (IAIMonServerAtomicSV) ServiceFactory.getService(IAIMonServerAtomicSV.class);
            IBOAIMonServerValue serverBO = serverSV.getServerBeanById(appId);
            if(serverBO == null) {
                // 没找到应用实例[{0}]
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000070", appId));
            }
            if(!(serverBO.getTempType().equals(TypeConst.APP) || serverBO.getTempType().equals(TypeConst.BOTH))) {
                // 该应用不支持Method检查和统计
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000314", appId));
            }
            IAIMonSetAtomicSV setSV = (IAIMonSetAtomicSV) ServiceFactory.getService(IAIMonSetAtomicSV.class);
            IBOAIMonSetValue setValue = setSV.getMonSetBeanBySCodeVCode(appId, "JMX_STATE");
            if(setValue == null) {
                // "应用端[appId:"+appId+"]未启动Jmx注册"
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000071", appId));
            }

            if(setValue.getSetValue().equalsIgnoreCase("ON") || setValue.getSetValue().equalsIgnoreCase("TRUE")) {
                SVMethodSummary[] summarys = SVMethodMonitorApi.getSVMehtodInfo(serverBO.getLocatorType(), serverBO.getLocator(), "");
                if(summarys != null && summarys.length > 0) {
                    result = new ArrayList();
                    Collections.addAll(result, summarys);
                }

            } else {
                // "应用端[appId:"+appId+"]未启动Jmx注册"
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000071", appId));
            }

        } catch(Exception e) {
            log.error("Call APIShowStatSVImpl's Method getSVMehtodInfo has Exception :" + e.getMessage());
        }
        return result;
    }

    /**
     * 后台定时任务,获取运行时数据源信息
     * 
     * @param appId
     * @return
     * @throws Exception
     */
    public List getDataSourceRuntime(String appId) throws RemoteException, Exception
    {
        List result = new ArrayList();
        try {
            IAIMonServerAtomicSV serverSV = (IAIMonServerAtomicSV) ServiceFactory.getService(IAIMonServerAtomicSV.class);
            IBOAIMonServerValue serverBO = serverSV.getServerBeanById(appId);
            if(serverBO == null) {
                // 没找到应用实例[{0}]
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000070", appId));
            }
            if(!(serverBO.getTempType().equals(TypeConst.APP) || serverBO.getTempType().equals(TypeConst.BOTH))) {
                // 该应用不支持Method检查和统计
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000314", appId));
            }
            IAIMonSetAtomicSV setSV = (IAIMonSetAtomicSV) ServiceFactory.getService(IAIMonSetAtomicSV.class);
            IBOAIMonSetValue setValue = setSV.getMonSetBeanBySCodeVCode(appId, "JMX_STATE");
            if(setValue == null) {
                // "应用端[appId:"+appId+"]未启动Jmx注册"
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000071", appId));
            }
            if(setValue.getSetValue().equalsIgnoreCase("ON") || setValue.getSetValue().equalsIgnoreCase("TRUE")) {
                DataSourceRuntime[] dsr = TransactionMonitorApi.fetchAllDataSourceRuntime(serverBO.getLocatorType(), serverBO.getLocator());
                for(int i = 0; i < dsr.length; i++) {
                    result.add(dsr[i]);
                }
            } else {
                // "应用端[appId:"+appId+"]未启动Jmx注册"
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000071", appId));
            }
        } catch(Exception e) {
            log.error("Call APIShowDsTsSVImpl's Method getDataSourceRuntime has Exception :" + e.getMessage());
        }
        return result;
    }

    /**
     * 后台定时任务,返回服务器内存信息
     * 
     * @param appId
     * @return
     * @throws Exception
     */
    public AIMemoryInfo getJVM5MemoryInfo(String appId) throws RemoteException, Exception
    {
        AIMemoryInfo result = null;
        try {
            IAIMonServerAtomicSV serverSV = (IAIMonServerAtomicSV) ServiceFactory.getService(IAIMonServerAtomicSV.class);
            IBOAIMonServerValue serverBO = serverSV.getServerBeanById(appId);
            if(serverBO == null) {
                // 没找到应用实例[{0}]
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000070", appId));
            }
            IAIMonSetAtomicSV setSV = (IAIMonSetAtomicSV) ServiceFactory.getService(IAIMonSetAtomicSV.class);
            IBOAIMonSetValue setValue = setSV.getMonSetBeanBySCodeVCode(appId, "JMX_STATE");
            if(setValue == null) {
                // "应用端[appId:"+appId+"]未启动Jmx注册"
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000071", appId));
            }
            if(setValue.getSetValue().equalsIgnoreCase("ON") || setValue.getSetValue().equalsIgnoreCase("TRUE")) {
                result = JVM5Api.getMemoryInfo(serverBO.getLocatorType(), serverBO.getLocator());
            } else {
                // "应用端[appId:"+appId+"]未启动Jmx注册"
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000071", appId));
            }
        } catch(Exception e) {
            log.error("Call APIJvmCapabilitySVImpl's Method getJVM5MemoryInfo has Exception :" + e.getMessage());
        }
        return result;
    }

    /**
     * 后台定时任务,获取在线用户信息
     * 
     * @param appId
     * @return
     * @throws Exception
     */
    public List getOnlineUsers(String appId) throws RemoteException, Exception
    {
        if(StringUtils.isBlank(appId))
            return null;
        List result = null;
        try {
            IAIMonServerAtomicSV serverSV = (IAIMonServerAtomicSV) ServiceFactory.getService(IAIMonServerAtomicSV.class);
            IBOAIMonServerValue serverBO = serverSV.getServerBeanById(appId);
            if(serverBO == null) {
                // 没找到应用实例[{0}]
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000070", appId));
            }
            IAIMonSetAtomicSV setSV = (IAIMonSetAtomicSV) ServiceFactory.getService(IAIMonSetAtomicSV.class);
            IBOAIMonSetValue setValue = setSV.getMonSetBeanBySCodeVCode(appId, "JMX_STATE");
            if(setValue == null) {
                // "应用端[appId:"+appId+"]未启动Jmx注册"
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000071", appId));
            }
            if(setValue.getSetValue().equalsIgnoreCase("ON") || setValue.getSetValue().equalsIgnoreCase("TRUE")) {
                result = UserManagerMonitorApi.getOnlineUsers(serverBO.getLocatorType(), serverBO.getLocator());
            } else {
                // "应用端[appId:"+appId+"]未启动Jmx注册"
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000071", appId));
            }
        } catch(Exception e) {
            log.error("Call UserManagerMonitorAction's Method getOnlineUsers has Exception :" + e.getMessage());
        }
        return result;
    }

    /**
     * 后台定时任务,读取事务概况信息
     * 
     * @param appId
     * @return
     * @throws Exception
     */
    public AITmSummary getTransaction(String appId) throws RemoteException, Exception
    {
        if(StringUtils.isBlank(appId))
            return null;
        AITmSummary result = null;
        try {
            IAIMonServerAtomicSV serverSV = (IAIMonServerAtomicSV) ServiceFactory.getService(IAIMonServerAtomicSV.class);
            IBOAIMonServerValue serverBO = serverSV.getServerBeanById(appId);
            if(serverBO == null) {
                // 没找到应用实例[{0}]
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000070", appId));
            }
            if(!(serverBO.getTempType().equals(TypeConst.APP) || serverBO.getTempType().equals(TypeConst.BOTH))) {
                // 该应用不支持Method检查和统计
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000314", appId));
            }
            IAIMonSetAtomicSV setSV = (IAIMonSetAtomicSV) ServiceFactory.getService(IAIMonSetAtomicSV.class);
            IBOAIMonSetValue setValue = setSV.getMonSetBeanBySCodeVCode(appId, "JMX_STATE");
            if(setValue == null) {
                // "应用端[appId:"+appId+"]未启动Jmx注册"
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000071", appId));
            }
            if(setValue.getSetValue().equalsIgnoreCase("ON") || setValue.getSetValue().equalsIgnoreCase("TRUE")) {
                result = TransactionMonitorApi.getOpenTransaction(serverBO.getLocatorType(), serverBO.getLocator());
            } else {
                // "应用端[appId:"+appId+"]未启动Jmx注册"
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000071", appId));
            }

        } catch(Exception e) {
            log.error("Call APIShowDsTsSVImpl's Method getTransactions has Exception :" + e.getMessage());
        }
        return result;
    }
}
