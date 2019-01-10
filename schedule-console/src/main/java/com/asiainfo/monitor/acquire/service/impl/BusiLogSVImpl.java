package com.asiainfo.monitor.acquire.service.impl;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.common.abo.bo.BOABGMonBusiErrorLogEngine;
import com.asiainfo.common.abo.ivalues.IBOABGMonBusiErrorLogValue;
import com.asiainfo.common.abo.ivalues.IBOABGMonBusiLogValue;
import com.asiainfo.common.service.interfaces.IABGMonBusiLogSV;
import com.asiainfo.common.utils.DateUtil;
import com.asiainfo.monitor.acquire.AcquireConst;
import com.asiainfo.monitor.acquire.dto.BusiErrLogBean;
import com.asiainfo.monitor.acquire.dto.BusiLogBean;
import com.asiainfo.monitor.acquire.dto.LogParamBean;
import com.asiainfo.monitor.acquire.engine.IAcquire;
import com.asiainfo.monitor.acquire.engine.impl.BaseAcquireEngine;
import com.asiainfo.monitor.acquire.service.interfaces.IBusiLogSV;
import com.asiainfo.monitor.analyse.IDataAnalyse;
import com.asiainfo.monitor.analyse.impl.DataAnalyseImpl;

/**
 * 业务日志查询，处理量，耗时，错单量等。
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: AI(NanJing)</p>
 * @author wu songkai
 * @version 3.0
 */
public class BusiLogSVImpl implements IBusiLogSV
{

    @Override
    public BusiLogBean[] acquireBusiLog(String serverCode, String taskId, String taskSplitId) throws Exception
    {
        //取当前时间半个小时内的数据
        Date endDate = DateUtil.getNowDate();
        Date beginDate = DateUtil.offsetDay(endDate, -2);

        LogParamBean logParam = new LogParamBean();
        logParam.setServerCode(serverCode);
        logParam.setBeginDate(beginDate);
        logParam.setEndDate(endDate);
        logParam.setTaskId(taskId);
        logParam.setTaskSplitId(taskSplitId);
        logParam.setMonFlag(AcquireConst.ACQ_MON_FLAG_N);

        //日志采集
        IAcquire acquireEngine = new BaseAcquireEngine();
        IBOABGMonBusiLogValue[] busiLogArr = acquireEngine.acquireBusiLog(logParam);

        //修改记录监控标识为已读
        for(IBOABGMonBusiLogValue logVal : busiLogArr) {
            logVal.setMonFlag(AcquireConst.ACQ_MON_FLAG_Y);
        }
        IABGMonBusiLogSV logSv = (IABGMonBusiLogSV) ServiceFactory.getService(IABGMonBusiLogSV.class);
        logSv.batchSaveLog(busiLogArr);

        //日志分析
        IDataAnalyse analyse = new DataAnalyseImpl();
        BusiLogBean[] retLogArr = analyse.busiLogAnalyse(busiLogArr);

        return retLogArr;
    }

    @Override
    public BusiLogBean[] acquireBusiHisLog(String serverCode, String taskId, String taskSplitId, java.util.Date beginDate, java.util.Date endDate) throws Exception
    {
        LogParamBean logParam = new LogParamBean();
        logParam.setServerCode(serverCode);
        logParam.setBeginDate(beginDate);
        logParam.setEndDate(endDate);
        logParam.setTaskId(taskId);
        logParam.setTaskSplitId(taskSplitId);

        //日志采集
        IAcquire acquireEngine = new BaseAcquireEngine();
        IBOABGMonBusiLogValue[] busiLogArr = acquireEngine.acquireBusiHisLog(logParam);

        //日志分析
        IDataAnalyse analyse = new DataAnalyseImpl();
        BusiLogBean[] retHisLogArr = analyse.busiLogAnalyse(busiLogArr);

        return retHisLogArr;
    }

    public static void main(String[] args)
    {
        IBusiLogSV sv = (IBusiLogSV) ServiceFactory.getService(IBusiLogSV.class);
        Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, -6);
		java.util.Date start = cal.getTime();
		java.util.Date end = new java.util.Date();
		Date sqlStart = DateUtil.dateToSqlDate(start);
		Date sqlEnd = DateUtil.dateToSqlDate(end);
        try {
        	BusiErrLogBean[] logArr = sv.acquireBusiHisErrLog("MON-EXE", "44000", "44000",sqlStart,sqlEnd);
            System.out.println("length:" + logArr.length);
        }
        catch(Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public BusiErrLogBean[] acquireBusiErrLog(String serverCode, String taskId, String taskSplitId) throws Exception
    {
    	//取当前时间半个小时内的数据
        Date endDate = DateUtil.getNowDate();
        Date beginDate = DateUtil.offsetDay(endDate, -2);

        LogParamBean logParam = new LogParamBean();
        logParam.setServerCode(serverCode);
        logParam.setBeginDate(beginDate);
        logParam.setEndDate(endDate);
        logParam.setTaskId(taskId);
        logParam.setTaskSplitId(taskSplitId);
        logParam.setMonFlag(AcquireConst.ACQ_MON_FLAG_N);

        //日志采集
        IAcquire acquireEngine = new BaseAcquireEngine();
        IBOABGMonBusiErrorLogValue[] busiLogArr = acquireEngine.acquireBusiErrLog(logParam);
        IABGMonBusiLogSV logSv = (IABGMonBusiLogSV) ServiceFactory.getService(IABGMonBusiLogSV.class);
        //修改记录监控标识为已读
        for(IBOABGMonBusiErrorLogValue logVal : busiLogArr) {
            logVal.setMonFlag(AcquireConst.ACQ_MON_FLAG_Y);
            logSv.saveErrLog(BOABGMonBusiErrorLogEngine.transfer(logVal));
        }

        //日志分析
        IDataAnalyse analyse = new DataAnalyseImpl();
        BusiErrLogBean[] retLogArr = analyse.busiErrLogAnalyse(busiLogArr);

        return retLogArr;
    }

    @Override
    public BusiErrLogBean[] acquireBusiHisErrLog(String serverCode, String taskId, String taskSplitId, java.util.Date beginDate, java.util.Date endDate) throws Exception
    {
    	LogParamBean logParam = new LogParamBean();
        logParam.setServerCode(serverCode);
        logParam.setBeginDate(beginDate);
        logParam.setEndDate(endDate);
        logParam.setTaskId(taskId);
        logParam.setTaskSplitId(taskSplitId);

        //日志采集
        IAcquire acquireEngine = new BaseAcquireEngine();
        IBOABGMonBusiErrorLogValue[] busiLogArr = acquireEngine.acquireBusiErrHisLog(logParam);

        //日志分析
        IDataAnalyse analyse = new DataAnalyseImpl();
        BusiErrLogBean[] retHisLogArr = analyse.busiErrLogAnalyse(busiLogArr);

        return retHisLogArr;
    }
    

}
