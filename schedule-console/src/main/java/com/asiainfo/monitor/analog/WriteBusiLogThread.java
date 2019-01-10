package com.asiainfo.monitor.analog;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.common.abo.bo.BOABGMonBusiLogBean;
import com.asiainfo.common.service.interfaces.IABGMonBusiLogSV;
import com.asiainfo.monitor.acquire.dto.BusiLogBean;

/**
 * 写业务日志接口
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: AI(NanJing)</p>
 * @author wu songkai
 * @version 3.0
 */
public class WriteBusiLogThread extends Thread
{
    private final RequestChannel channel;

    public WriteBusiLogThread(RequestChannel channel)
    {
        this.channel = channel;
    }

    @Override
    public void run()
    {
        DataProducer dataProducer = new DataProducer();
        IABGMonBusiLogSV logSv = (IABGMonBusiLogSV) ServiceFactory.getService(IABGMonBusiLogSV.class);
        System.out.println(".writing thread starting  ...");
        while(true) {
            BusiLogBean logBean = channel.takeRequest();
            
            try {
                //批量插入数据库数据
                BOABGMonBusiLogBean boBusiLog = dataProducer.busiLogProduce(logBean);
                //入库
                System.out.println(".save one log.");
                //logSv.save(boBusiLog);

                logSv.save(boBusiLog);
            }
            catch(Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
