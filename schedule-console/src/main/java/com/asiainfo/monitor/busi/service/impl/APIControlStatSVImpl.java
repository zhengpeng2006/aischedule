package com.asiainfo.monitor.busi.service.impl;

import java.rmi.RemoteException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.monitor.busi.service.interfaces.IAPIControlStatSV;
import com.asiainfo.monitor.busi.stat.control.ServerStatControl;
import com.asiainfo.monitor.busi.stat.control.ServerStatControl.StatControlEnum;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class APIControlStatSVImpl implements IAPIControlStatSV
{

    private static transient Log log = LogFactory.getLog(APIControlStatSVImpl.class);

    /**
     * 启动在线服务统计监控
     * 
     * @param appServerIds
     * @param second
     * @return
     */
    public List startSVMethod(Object[] ids, String second) throws RemoteException, Exception
    {
        List result = null;
        try {
            if(ids == null || ids.length < 1) {
                // "启动服务统计请提供操作应用"
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000136"));
            }
            if(StringUtils.isBlank(second)) {
                // "启动服务统计时请提供时间参数"
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000137"));
            }
            int threadCount = (ids.length >> 1) + 1;
            int cpuCount = Runtime.getRuntime().availableProcessors();
            if(threadCount > (cpuCount * 3)) {
                threadCount = cpuCount * 3;
            }
            result = ServerStatControl.controlStatStatus(threadCount, -1, ids, Long.parseLong(second), StatControlEnum.START_SV);

        } catch(Exception e) {
            log.error(AIMonLocaleFactory.getResource("MOS0000049") + e.getMessage());
        }
        return result;
    }

    /**
     * 停止在线服务统计监控
     * 
     * @param appServerIds
     * @param second
     * @return
     */
    public List stopSVMethod(Object[] ids) throws RemoteException, Exception
    {
        List result = null;
        try {
            if(ids == null || ids.length < 1)
                // "停止服务统计请提供操作应用"
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000136"));
            int threadCount = (ids.length >> 1) + 1;
            int cpuCount = Runtime.getRuntime().availableProcessors();
            if(threadCount > (cpuCount * 3))
                threadCount = cpuCount * 3;
            result = ServerStatControl.controlStatStatus(threadCount, -1, ids, 0, StatControlEnum.STOP_SV);
        } catch(Exception e) {
            log.error(AIMonLocaleFactory.getResource("MOS0000049") + e.getMessage());
        }
        return result;
    }

    /**
     * 启动在线服务统计监控
     * 
     * @param appServerIds
     * @param second
     * @return
     */
    public List startSQL(Object[] ids, String second) throws RemoteException, Exception
    {
        List result = null;
        try {
            if(ids == null || ids.length < 1)
                // "启动SQL统计请提供操作应用"
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000136"));
            if(StringUtils.isBlank(second))
                // "启动SQL统计时请提供时间参数"
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000137"));
            int threadCount = (ids.length >> 1) + 1;
            int cpuCount = Runtime.getRuntime().availableProcessors();
            if(threadCount > (cpuCount * 3))
                threadCount = cpuCount * 3;
            result = ServerStatControl.controlStatStatus(threadCount, -1, ids, Long.parseLong(second), StatControlEnum.START_SQL);
        } catch(Exception e) {
            log.error(AIMonLocaleFactory.getResource("MOS0000049") + e.getMessage());
        }
        return result;
    }

    /**
     * 停止在线服务统计监控
     * 
     * @param appServerIds
     * @param second
     * @return
     */
    public List stopSQL(Object[] ids) throws RemoteException, Exception
    {
        List result = null;
        try {
            if(ids == null || ids.length < 1)
                // "停止SQL统计请提供操作应用"
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000136"));
            int threadCount = (ids.length >> 1) + 1;
            int cpuCount = Runtime.getRuntime().availableProcessors();
            if(threadCount > (cpuCount * 3))
                threadCount = cpuCount * 3;
            result = ServerStatControl.controlStatStatus(threadCount, -1, ids, 0, StatControlEnum.STOP_SQL);
        } catch(Exception e) {
            log.error(AIMonLocaleFactory.getResource("MOS0000049") + e.getMessage());
        }
        return result;
    }

    /**
     * 启动在线服务统计监控
     * 
     * @param appServerIds
     * @param second
     * @return
     */
    public List startAction(Object[] ids, String second) throws RemoteException, Exception
    {
        List result = null;
        try {
            if(ids == null || ids.length < 1)
                // "启动Action统计请提供操作应用"
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000136"));
            if(StringUtils.isBlank(second))
                // "启动Action统计时请提供时间参数"
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000137"));
            int threadCount = (ids.length >> 1) + 1;
            int cpuCount = Runtime.getRuntime().availableProcessors();
            if(threadCount > (cpuCount * 3))
                threadCount = cpuCount * 3;
            result = ServerStatControl.controlStatStatus(threadCount, -1, ids, Long.parseLong(second), StatControlEnum.START_ACTION);
        } catch(Exception e) {
            log.error(AIMonLocaleFactory.getResource("MOS0000049") + e.getMessage());
        }
        return result;
    }

    /**
     * 停止在线服务统计监控
     * 
     * @param appServerIds
     * @param second
     * @return
     */
    public List stopAction(Object[] ids) throws RemoteException, Exception
    {
        List result = null;
        try {
            if(ids == null || ids.length < 1)
                // "停止ActionL统计请提供操作应用"
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000136"));
            int threadCount = (ids.length >> 1) + 1;
            int cpuCount = Runtime.getRuntime().availableProcessors();
            if(threadCount > (cpuCount * 3))
                threadCount = cpuCount * 3;
            result = ServerStatControl.controlStatStatus(threadCount, -1, ids, 0, StatControlEnum.STOP_ACTION);
        } catch(Exception e) {
            log.error(AIMonLocaleFactory.getResource("MOS0000049") + e.getMessage());
        }
        return result;
    }

}
