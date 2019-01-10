package com.asiainfo.monitor.acquire.service.interfaces;

import java.sql.Date;

import com.asiainfo.monitor.acquire.dto.BusiErrLogBean;
import com.asiainfo.monitor.acquire.dto.BusiLogBean;

/**
 * 采集业务日志服务接口
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: AI(NanJing)</p>
 * @author wu songkai
 * @version 3.0
 */
public interface IBusiLogSV
{

    /**
     * 采集业务日志接口，如处理量等。
     * @return
     * @throws Exception
     */
    public BusiLogBean[] acquireBusiLog(String serverCode, String taskId, String taskSplitId) throws Exception;

    /**
     * 采集业务日志历史信息接口，如历史处理量等。
     * @return
     * @throws Exception
     */
    public BusiLogBean[] acquireBusiHisLog(String serverCode, String taskId, String taskSplitId, java.util.Date beginDate, java.util.Date endDate) throws Exception;

    /**
     * 采集业务日志接口，如处理量等。
     * @return
     * @throws Exception
     */
    public BusiErrLogBean[] acquireBusiErrLog(String serverCode, String taskId, String taskSplitId) throws Exception;

    /**
     * 采集业务日志历史信息接口，如历史处理量等。
     * @return
     * @throws Exception
     */
    public BusiErrLogBean[] acquireBusiHisErrLog(String serverCode, String taskId, String taskSplitId, java.util.Date beginDate, java.util.Date endDate) throws Exception;



}
