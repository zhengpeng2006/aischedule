package com.asiainfo.monitor.busi.exe.task;

import com.asiainfo.socket.service.AcqSocketServer;

/**
 * 进程采集进程
 * @author SZH
 *
 */
class ProcTaskThread extends Thread
{
    String[] args;

    public String[] getArgs()
    {
        return args;
    }

    public void setArgs(String[] args)
    {
        this.args = args;
    }

    public ProcTaskThread(String[] args)
    {
        this.args = args;
    }

    public void run()
    {
        try {
            MonitorProcTask.main(args);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}

/**
 * socket服务启动
 * @author SZH
 *
 */
class AcqSocketThread extends Thread
{
    String[] args;

    public String[] getArgs()
    {
        return args;
    }

    public void setArgs(String[] args)
    {
        this.args = args;
    }

    public AcqSocketThread(String[] args)
    {
        this.args = args;
    }

    public void run()
    {
        AcqSocketServer.main(args);
    }
}

/**
 * 将进程采集进程和socket服务启动进程放在一起
 * @author SZH
 *
 */
public class MonProcServer
{
    public static void main(String[] args) throws InterruptedException
    {
        ProcTaskThread procThread = new ProcTaskThread(args);
        procThread.start();
        AcqSocketThread socketThread = new AcqSocketThread(args);
        socketThread.start();
    }
}
