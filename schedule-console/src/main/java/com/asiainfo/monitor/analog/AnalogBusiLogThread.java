package com.asiainfo.monitor.analog;

/**
 * 模拟业务产生日志线程
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: AI(NanJing)</p>
 * @author wu songkai
 * @version 3.0
 */
public class AnalogBusiLogThread extends Thread
{
    private final RequestChannel logChannel;//普通日志通道

    public AnalogBusiLogThread(RequestChannel channel)
    {
        this.logChannel = channel;
    }
    @Override
    public void run()
    {
        DataProducer dataProducer = new DataProducer();

        try {
            System.out.println(" start  batch insert ...");
            dataProducer.batchBusiLogInsert(logChannel);
        }
        catch(Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
