package com.asiainfo.monitor.analog;


/**
 * 模拟业务服务器,业务进程日志入库
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: AI(NanJing)</p>
 * @author wu songkai
 * @version 3.0
 */
public class BusiServer
{

    public static void main(String[] args)
    {
        RequestChannel busiLogchannel = new RequestChannel();
        System.out.println("analog thread start...");
        new AnalogBusiLogThread(busiLogchannel).start();
        new WriteBusiLogThread(busiLogchannel).start();
        new WriteBusiErrLogThread(busiLogchannel).start();

        System.out.println("analog thread End...");
    }
}
