package com.asiainfo.monitor.acquire.engine.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.common.abo.bo.BOABGMonBusiErrorLogEngine;
import com.asiainfo.common.abo.bo.BOABGMonBusiLogEngine;
import com.asiainfo.common.abo.ivalues.IBOABGMonBusiErrorLogValue;
import com.asiainfo.common.abo.ivalues.IBOABGMonBusiLogValue;
import com.asiainfo.logger.common.LoggerDateUtils;
import com.asiainfo.monitor.acquire.AcquireConst;
import com.asiainfo.monitor.acquire.dto.AcqParamBean;
import com.asiainfo.monitor.acquire.dto.LogParamBean;
import com.asiainfo.monitor.acquire.engine.IAcquire;
import com.asiainfo.monitor.acquire.engine.ProcInfoCallable;
import com.asiainfo.socket.service.ClientHelper;

/**
 * 基础采集引擎，用于采集监控信息数据，支持多种类型采集
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
public class BaseAcquireEngine implements IAcquire
{
    private static Log logger = LogFactory.getLog(BaseAcquireEngine.class);
    
    /** 业务表表名_ */
    private static final String BUSI_TABLE_NAME = "ABG_MON_BUSI_LOG_";
    
    /** 错单表表名_ */
    private static final String ERR_TABLE_NAME = "ABG_MON_BUSI_ERR_LOG_";
    
    @Override
    public String acquire(AcqParamBean paramBean)
        throws Exception
    {
        // 采集类型：Shell脚本
        if (AcquireConst.ACQ_TYPE_SHELL.equals(paramBean.getAcqType()))
        {
            
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("TYPE_ID", AcquireConst.EXE_TYPE_SHELL);
            paramMap.put("USER_NAME", paramBean.getUsername());
            paramMap.put("PARAM", paramBean.getParamStr());
            paramMap.put("SHELL", paramBean.getShell());
            paramMap.put("IP", paramBean.getIp());
            paramMap.put("HOME_PATH", paramBean.getHomePath());
            
            // 数据采集
            return ClientHelper.execShell(paramMap);
            
            // 执行ssh脚本
            // return SSHUtil.ssh4Shell(ip, Integer.parseInt(port), username, password, tmpShellFileName, paramStr,
            // shell);
        }
        
        return null;
    }
    
    @Override
    public IBOABGMonBusiLogValue[] acquireBusiLog(LogParamBean logParam)
        throws Exception
    {
        StringBuffer sqlStr = new StringBuffer();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");// 分表字段格式
        
        // ++++++++++++++++++固定查询语句开头++++++++++++++++++
        StringBuffer pre = new StringBuffer();
        pre.append("SELECT THROUGHPUT_ID,SERIAL_NO,TASK_ID,TASK_SPLIT_ID,SERVER_CODE,TOTAL_CNT,")
            .append("PER_HANDLE_CNT,HANDLE_CNT,REGION_CODE,CONSUME_TIME,SYSTEM_DOMAIN,")
            .append("SUBSYSTEM_DOMAIN,APP_SERVER_NAME,CREATE_DATE,ACQ_DATE,SEQ,MON_FLAG FROM ");
        // +++++++++++++++++++拼接通用过滤条件++++++++++++++++
        StringBuffer condition = new StringBuffer();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        condition.append(" AND SERVER_CODE = :server_code ");
        paramMap.put("server_code", logParam.getServerCode());
        if (!StringUtils.isEmpty(logParam.getMonFlag()))
        {
            condition.append(" AND MON_FLAG = :mon_flag");
            paramMap.put("mon_flag", logParam.getMonFlag());
        }
        condition.append(" AND TASK_ID = :task_id ");
        paramMap.put("task_id", logParam.getTaskId());
        
        // 看看是否有分片标识
        if (!StringUtils.isEmpty(logParam.getTaskSplitId()))
        {
            condition.append(" AND TASK_SPLIT_ID = :task_split_id ");
            paramMap.put("task_split_id", logParam.getTaskSplitId());
        }
        // +++++++++++++++++++开始拼接表相关信息 ++++++++++++++++++++++++++++++++
        java.util.Date[] tables = LoggerDateUtils.dividedByMonth(logParam.getBeginDate(), logParam.getEndDate());
        if (null == tables || tables.length == 0)
        {
            logger.error("分表解析失败");
            return null;
        }
        else if (tables.length == 1)// 若果在同一张表内 则开始时间结束时间都在此拼接
        {
            StringBuffer tableSql = new StringBuffer();
            tableSql.append(BUSI_TABLE_NAME).append(sdf.format(tables[0])).append(" WHERE 1 = 1");
            tableSql.append(" AND ACQ_DATE >= :begin_date ");
            paramMap.put("begin_date", new Timestamp(logParam.getBeginDate().getTime()));
            tableSql.append(" AND ACQ_DATE <= :end_date ");
            paramMap.put("end_date", new Timestamp(logParam.getEndDate().getTime()));
            sqlStr.append(pre).append(tableSql).append(condition).append(" ORDER BY ACQ_DATE");
        }
        else
        {
            for (int i = 0; i < tables.length; i++)
            {
                StringBuffer tableSql = new StringBuffer();
                tableSql.append(BUSI_TABLE_NAME).append(sdf.format(tables[i])).append(" WHERE 1 = 1");
                if (i == 0)// 开始时间在第一张表拼接
                {
                    tableSql.append(" AND ACQ_DATE >= :begin_date ");
                    paramMap.put("begin_date", new Timestamp(logParam.getBeginDate().getTime()));
                    sqlStr.append(pre).append(tableSql).append(condition);
                    continue;
                }
                else if (i == (tables.length - 1))// 结束时间在最后一张表拼接
                {
                    tableSql.append(" AND ACQ_DATE <= :end_date ");
                    paramMap.put("end_date", new Timestamp(logParam.getEndDate().getTime()));
                    sqlStr.append(" UNION ALL ")
                        .append(pre)
                        .append(tableSql)
                        .append(condition)
                        .append(" ORDER BY ACQ_DATE");
                }
                else
                {
                    sqlStr.append(" UNION ALL ").append(pre).append(tableSql).append(condition);
                }
                
            }
        }
        IBOABGMonBusiLogValue[] values = BOABGMonBusiLogEngine.getBeansFromSql(sqlStr.toString(), paramMap);
        return values;
    }
    
    @Override
    public IBOABGMonBusiLogValue[] acquireBusiHisLog(LogParamBean logParam)
        throws Exception
    {
        return this.acquireBusiLog(logParam);
    }
    
    @Override
    public IBOABGMonBusiErrorLogValue[] acquireBusiErrLog(LogParamBean logParam)
        throws Exception
    {
        StringBuffer sqlStr = new StringBuffer();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");// 分表字段格式
        
        // ++++++++++++++++++固定查询语句开头++++++++++++++++++
        StringBuffer pre = new StringBuffer();
        pre.append("SELECT ERR_ORDER_ID,SERVER_CODE,SERIAL_NO,TASK_ID,TASK_SPLIT_ID,REGION_CODE,")
            .append("ERR_CNT,HANDLE_TIME,SYSTEM_DOMAIN,SUBSYSTEM_DOMAIN,APP_SERVER_NAME,CREATE_DATE,")
            .append("ACQ_DATE,MON_FLAG FROM ");
        
        // pre.append("SELECT * FROM ");
        // +++++++++++++++++++拼接通用过滤条件++++++++++++++++
        StringBuffer condition = new StringBuffer();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        condition.append(" AND SERVER_CODE = :server_code ");
        paramMap.put("server_code", logParam.getServerCode());
        if (!StringUtils.isEmpty(logParam.getMonFlag()))
        {
            condition.append(" AND MON_FLAG = :mon_flag");
            paramMap.put("mon_flag", logParam.getMonFlag());
        }
        condition.append(" AND TASK_ID = :task_id ");
        paramMap.put("task_id", logParam.getTaskId());
        
        // 看看是否有分片标识
        if (!StringUtils.isEmpty(logParam.getTaskSplitId()))
        {
            condition.append(" AND TASK_SPLIT_ID = :task_split_id ");
            paramMap.put("task_split_id", logParam.getTaskSplitId());
        }
        // +++++++++++++++++++开始拼接表相关信息 ++++++++++++++++++++++++++++++++
        java.util.Date[] tables = LoggerDateUtils.dividedByMonth(logParam.getBeginDate(), logParam.getEndDate());
        if (null == tables || tables.length == 0)
        {
            logger.error("分表解析失败");
            return null;
        }
        else if (tables.length == 1)// 若果在同一张表内 则开始时间结束时间都在此拼接
        {
            StringBuffer tableSql = new StringBuffer();
            tableSql.append(BUSI_TABLE_NAME).append(sdf.format(tables[0])).append(" WHERE 1 = 1");
            tableSql.append(" AND ACQ_DATE >= :begin_date ");
            paramMap.put("begin_date", new Timestamp(logParam.getBeginDate().getTime()));
            tableSql.append(" AND ACQ_DATE <= :end_date ");
            paramMap.put("end_date", new Timestamp(logParam.getEndDate().getTime()));
            sqlStr.append(pre).append(tableSql).append(condition).append(" ORDER BY ACQ_DATE");
        }
        else
        {
            for (int i = 0; i < tables.length; i++)
            {
                StringBuffer tableSql = new StringBuffer();
                tableSql.append(ERR_TABLE_NAME).append(sdf.format(tables[i])).append(" WHERE 1 = 1");
                if (i == 0)// 开始时间在第一张表拼接
                {
                    tableSql.append(" AND ACQ_DATE >= :begin_date ");
                    paramMap.put("begin_date", new Timestamp(logParam.getBeginDate().getTime()));
                    sqlStr.append(pre).append(tableSql).append(condition);
                    continue;
                }
                else if (i == (tables.length - 1))// 结束时间在最后一张表拼接
                {
                    tableSql.append(" AND ACQ_DATE <= :end_date ");
                    paramMap.put("end_date", new Timestamp(logParam.getEndDate().getTime()));
                    sqlStr.append(" UNION ALL ")
                        .append(pre)
                        .append(tableSql)
                        .append(condition)
                        .append(" ORDER BY ACQ_DATE");
                }
                else
                {
                    sqlStr.append(" UNION ALL ").append(pre).append(tableSql).append(condition);
                }
                
            }
        }
        return BOABGMonBusiErrorLogEngine.getBeansFromSql(sqlStr.toString(), paramMap);
    }
    
    @Override
    public IBOABGMonBusiErrorLogValue[] acquireBusiErrHisLog(LogParamBean logParam)
        throws Exception
    {
        return this.acquireBusiErrLog(logParam);
    }
    
    public static void main(String[] args)
        throws Exception
    {
        /*
         * BaseAcquireEngine engine = new BaseAcquireEngine();
         * 
         * CmbHostBean hostBean = CommonSvUtil.qryCmbHostInfo("3"); Map<String, String> userInfoMap =
         * hostBean.getMonUserInfo();
         * 
         * IAIMonCmdSV cmdSv = (IAIMonCmdSV) ServiceFactory.getService(IAIMonCmdSV.class); IBOAIMonCmdValue cmdVal =
         * cmdSv.getCmdById(15l);
         * 
         * AcqParamBean paramBean = new AcqParamBean(); paramBean.setIp("20.26.12.158");
         * paramBean.setUsername("monexe"); paramBean.setPassword("monexe-123");
         * paramBean.setPort(hostBean.getConPort(CommonConst.CON_TYPE_SSH));
         * paramBean.setAcqType(AcquireConst.ACQ_TYPE_SHELL); paramBean.setShell(cmdVal.getCmdExpr());
         * paramBean.setServerCode("serverCode"); List<String> paramList = new ArrayList<String>();
         * paramList.add("CPU"); paramList.add("monexe");
         * 
         * paramBean.setParamList(paramList);
         * 
         * engine.acquire(paramBean);
         */
        
    }
    
    @Override
    public Map<String, String> acqProcessState(List<ProcInfoCallable> callableList)
        throws Exception
    {
        // 开启5-10个线程
        int threadCount = callableList.size() > 10 ? 10 : 5;
        
        Map<String, String> stateMap = new HashMap<String, String>();
        ExecutorService execService = Executors.newCachedThreadPool();
        try
        {
            CompletionService serv = new ExecutorCompletionService(execService);
            
            for (ProcInfoCallable callable : callableList)
            {
                Future submit = serv.submit(callable);
            }
            
            for (ProcInfoCallable callable : callableList)
            {
                Future task = serv.take();
                stateMap.putAll((Map)task.get());
            }
        }
        catch (InterruptedException ex)
        {
            logger.error(" BaseAcquireEngine.acqProcessState() occur exception:", ex);
        }
        
        execService.shutdown();
        
        return stateMap;
    }
    
}
