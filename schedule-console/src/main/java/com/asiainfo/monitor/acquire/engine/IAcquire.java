package com.asiainfo.monitor.acquire.engine;

import java.util.List;
import java.util.Map;

import com.asiainfo.common.abo.ivalues.IBOABGMonBusiErrorLogValue;
import com.asiainfo.common.abo.ivalues.IBOABGMonBusiLogValue;
import com.asiainfo.monitor.acquire.dto.AcqParamBean;
import com.asiainfo.monitor.acquire.dto.LogParamBean;

/**
 * 数据采集接口类
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: AI(NanJing)</p>
 * @author wu songkai
 * @version 3.0
 */
public interface IAcquire
{
    /**
     * 采集主机资源信息接口接口
     * @throws Exception
     */
    public String acquire(AcqParamBean paramBean) throws Exception;

    /**
     * 采集业务日志接口，如处理量等。
     * @return
     * @throws Exception
     */
    public IBOABGMonBusiLogValue[] acquireBusiLog(LogParamBean logParam) throws Exception;

    /**
     * 采集业务日志历史信息接口，如历史处理量等。
     * @return
     * @throws Exception
     */
    public IBOABGMonBusiLogValue[] acquireBusiHisLog(LogParamBean logParam)
            throws Exception;
    
    /**
     * 采集业务日志接口，如处理量等。
     * @return
     * @throws Exception
     */
    public IBOABGMonBusiErrorLogValue[] acquireBusiErrLog(LogParamBean logParam) throws Exception;

    /**
     * 采集业务日志历史信息接口，如历史处理量等。
     * @return
     * @throws Exception
     */
    public IBOABGMonBusiErrorLogValue[] acquireBusiErrHisLog(LogParamBean logParam) throws Exception;

    /**
     * 查询该主机上的应用进程是否存在
     * @param paramMap Map<主机标识,List<该主机上的应用编码>>
     * @return
     * @throws Exception
     */
    public Map<String, String> acqProcessState(List<ProcInfoCallable> callableList) throws Exception;

}
