package com.asiainfo.monitor.analog;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.common.abo.bo.BOABGMonBusiLogBean;
import com.asiainfo.common.service.interfaces.IABGMonBusiLogSV;

public class SingleWriteThread extends Thread
{
    private BOABGMonBusiLogBean log = null;

    public SingleWriteThread(BOABGMonBusiLogBean log)
    {
        this.log = log;
    }

    @Override
    public void run()
    {
        IABGMonBusiLogSV logSv = (IABGMonBusiLogSV) ServiceFactory.getService(IABGMonBusiLogSV.class);
    }

}
