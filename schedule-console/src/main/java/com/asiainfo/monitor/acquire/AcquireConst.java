package com.asiainfo.monitor.acquire;

/**
 * 数据采集模块常量，
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: AI(NanJing)</p>
 * @author wu songkai
 * @version 3.0
 */
public class AcquireConst
{
    /** shell脚本采集 **/
    public final static String ACQ_TYPE_SHELL = "ACQ_SHELL";

    /**SQl脚本采集 **/
    public final static String ACQ_TYPE_SQL = "ACQ_SQL";

    /** 通过日志采集 **/
    public final static String ACQ_TYPE_LOG = "ACQ_LOG";

    /*******************数据分析常量*********************/
    public final static String ANALYSE_TYPE_HOST = "KPI_HOST_INFO";//主机资源kpi分析
    public final static String ANALYSE_TYPE_PROCESS = "KPI_CPU_MEM";//进程资源分析
    public final static String ANALYSE_TYPE_BUSI_PROCESS = "BUSI_PROC_KPI";//单个进程资源分析

    /*****************业务日志采集 **********************/
    public final static String ACQ_MON_FLAG_Y = "Y";//被监控标识
    public final static String ACQ_MON_FLAG_N = "N";//被监控标识

    public final static String FLAG_T = "T";//T标识
    public final static String FLAG_F = "F";//F标识

    /** 执行类型为shell脚本 **/
    public final static String EXE_TYPE_SHELL = "1002";


}
