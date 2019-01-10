package com.asiainfo.common.service.interfaces;

import com.asiainfo.common.abo.bo.BOABGMonBusiErrorLogBean;
import com.asiainfo.common.abo.bo.BOABGMonBusiLogBean;
import com.asiainfo.common.abo.ivalues.IBOABGMonBusiLogValue;
import com.asiainfo.monitor.acquire.dto.LogParamBean;

public interface IABGMonBusiLogSV
{

    /**
     * 保存业务日志信息
     * @param logBean
     * @throws Exception
     */
    public void save(BOABGMonBusiLogBean logBean) throws Exception;

    /**
     * 保存业务日志信息
     * @param logBean
     * @throws Exception
     */
    public void saveErrLog(BOABGMonBusiErrorLogBean logBean) throws Exception;
    
    /**
     * 修改标记
     * @param logParam
     * @return
     * @throws Exception
     */
    public int updateMonFlag(LogParamBean logParam) throws Exception;

    /**
     * 批量保存业务日志
     * @param logBeanArr
     * @throws Exception
     */
    public void batchSaveLog(IBOABGMonBusiLogValue[] logBeanArr) throws Exception;

}
