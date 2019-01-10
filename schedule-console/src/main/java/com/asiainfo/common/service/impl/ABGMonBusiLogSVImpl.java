package com.asiainfo.common.service.impl;

import com.asiainfo.common.abo.bo.BOABGMonBusiErrorLogBean;
import com.asiainfo.common.abo.bo.BOABGMonBusiErrorLogEngine;
import com.asiainfo.common.abo.bo.BOABGMonBusiLogBean;
import com.asiainfo.common.abo.bo.BOABGMonBusiLogEngine;
import com.asiainfo.common.abo.ivalues.IBOABGMonBusiLogValue;
import com.asiainfo.common.service.interfaces.IABGMonBusiLogSV;
import com.asiainfo.monitor.acquire.dto.LogParamBean;

public class ABGMonBusiLogSVImpl implements IABGMonBusiLogSV
{

    @Override
    public void save(BOABGMonBusiLogBean logBean) throws Exception
    {
        if(logBean.isNew()) {
            logBean.setThroughputId(BOABGMonBusiLogEngine.getNewId().longValue());
            logBean.setMonFlag("N");
        }
        BOABGMonBusiLogEngine.save(logBean);
    }

    @Override
    public void saveErrLog(BOABGMonBusiErrorLogBean logBean) throws Exception
    {
        if(logBean.isNew()) {
            logBean.setErrOrderId(BOABGMonBusiErrorLogEngine.getNewId().longValue());
            logBean.setMonFlag("N");
        }
        BOABGMonBusiErrorLogEngine.save(logBean);
    }

    public void batchSaveLog(IBOABGMonBusiLogValue[] logBeanArr) throws Exception
    {
        BOABGMonBusiLogEngine.saveBatch(logBeanArr);
    }

    @Override
    public int updateMonFlag(LogParamBean logParam) throws Exception
    {
        //明天考虑，如何用sql语句来执行
        return 0;
    }

}
