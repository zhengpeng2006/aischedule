package com.asiainfo.monitor.analyse;

import com.asiainfo.common.abo.bo.BOABGMonBusiErrorLogBean;
import com.asiainfo.common.abo.ivalues.IBOABGMonBusiErrorLogValue;
import com.asiainfo.common.abo.ivalues.IBOABGMonBusiLogValue;
import com.asiainfo.monitor.acquire.dto.BusiErrLogBean;
import com.asiainfo.monitor.acquire.dto.BusiLogBean;


/**
 * 采集数据分析接口
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: AI(NanJing)</p>
 * @author wu songkai
 * @version 3.0
 */
public interface IDataAnalyse
{

    /**
     * 进程数据分析，针对shell脚本采集的数据，主要分析出<PID，List(CPU,MEM,CMD)
     * @param processInfo
     * @return
     * @throws Exception
     */
    public Object procInfoAnalyse(String analyseType, String processInfo) throws Exception;
    
    /**
     * 业务日志分析
     * @param busiLogValueArr
     * @return
     * @throws Exception
     */
    public BusiLogBean[] busiLogAnalyse(IBOABGMonBusiLogValue[] busiLogArr) throws Exception;

    /**
     * 业务错单日志分析
     * @param busiLogValueArr
     * @return
     * @throws Exception
     */
    public BusiErrLogBean[] busiErrLogAnalyse(IBOABGMonBusiErrorLogValue[] busiErrLogArr) throws Exception;
}
