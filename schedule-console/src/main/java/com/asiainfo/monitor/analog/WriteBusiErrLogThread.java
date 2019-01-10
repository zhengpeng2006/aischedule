package com.asiainfo.monitor.analog;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.common.abo.bo.BOABGMonBusiErrorLogBean;
import com.asiainfo.common.service.interfaces.IABGMonBusiLogSV;
import com.asiainfo.monitor.acquire.dto.BusiErrLogBean;

/**
 * 写业务日志接口
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: AI(NanJing)</p>
 * @author wu songkai
 * @version 3.0
 */
public class WriteBusiErrLogThread extends Thread
{

    private final RequestChannel channel;

    public WriteBusiErrLogThread(RequestChannel channel)
    {
        this.channel = channel;
    }

    @Override
    public void run()
    {
        DataProducer dataProducer = new DataProducer();
        IABGMonBusiLogSV logSv = (IABGMonBusiLogSV) ServiceFactory.getService(IABGMonBusiLogSV.class);
        System.out.println(".writing error log thread starting  ...");
        while(true) {
            BusiErrLogBean errLog = channel.takeErrLogRequest();
            
            try {
                //批量插入数据库数据
                BOABGMonBusiErrorLogBean busiErrLog = dataProducer.errLogProduce(errLog);
                //入库
                System.out.println(".save one error log.");
                logSv.saveErrLog(busiErrLog);
            }
            catch(Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
