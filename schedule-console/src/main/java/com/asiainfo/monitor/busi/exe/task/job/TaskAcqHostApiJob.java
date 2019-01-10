package com.asiainfo.monitor.busi.exe.task.job;

import com.ai.appframe2.complex.cache.CacheFactory;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.appframe.ext.flyingserver.org.apache.commons.lang.StringUtils;
import com.asiainfo.monitor.busi.cache.impl.AIMonCmdCacheImpl;
import com.asiainfo.monitor.busi.cache.impl.Cmd;
import com.asiainfo.monitor.busi.common.ExecShellUtil;
import com.asiainfo.monitor.common.CommonConst;
import com.asiainfo.monitor.exeframe.loginfomgr.abo.bo.BOABGMonHostLogBean;
import com.asiainfo.monitor.exeframe.loginfomgr.service.interfaces.IABGMonHostLogSV;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class TaskAcqHostApiJob implements Job
{

    @SuppressWarnings("unchecked")
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException
    {
        try {
            Cmd cmd = (Cmd) CacheFactory.get(AIMonCmdCacheImpl.class, "16");

            String shell = cmd.getCmd_Expr();
            //获取当前主机的信息
//            InetAddress netAddress = getInetAddress();
            String ip = getHostIp();
            String shellFile = ExecShellUtil.getShellFile(shell);
            String info = ExecShellUtil.execShell(shellFile, "");

            if(StringUtils.isNotBlank(info)) {
                String[] arr = info.split(":");
                String kpiCpu = arr[0];
                String kpiMem = arr[1];
                String kpiFile = arr[2];

                BOABGMonHostLogBean bean = new BOABGMonHostLogBean();
                bean.setStsToNew();

                //设置主机标识
                bean.setHostIp(ip);
                bean.setKpiCpu(kpiCpu);
                bean.setKpiMem(kpiMem);
                bean.setKpiFs(kpiFile);
                bean.setSystemDomain(CommonConst.SYSTEM_DOMAIN);
                //子系统编码
                bean.setSubsystemDomain(CommonConst.SUB_SYSTEM_DOMAIN);
                bean.setAppServerName(CommonConst.APP_SERVER_NAME);
                bean.setMonFlag(CommonConst.MON_FLAG_N);
                IABGMonHostLogSV logSv = (IABGMonHostLogSV) ServiceFactory.getService(IABGMonHostLogSV.class);
                logSv.save(bean);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static InetAddress getInetAddress()
    {
        try {
            return InetAddress.getLocalHost();
        }
        catch(UnknownHostException e) {
            System.out.println("unknown host!");
        }
        return null;

    }

    public static String getHostIp(InetAddress netAddress)
    {
        if(null == netAddress) {
            return null;
        }
        //get the ip address
        String ip = netAddress.getHostAddress();
        return ip;
    }

    public static String getHostName(InetAddress netAddress)
    {
        if(null == netAddress) {
            return null;
        }
        //get the host address 
        String name = netAddress.getHostName();
        return name;
    }


    public static String getHostIp(){
//        StringBuilder ip = new StringBuilder();
//        try{
//            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
//                NetworkInterface networkInterface = en.nextElement();
//                for (Enumeration<InetAddress> enumIpAddress = networkInterface.getInetAddresses(); enumIpAddress.hasMoreElements();) {
//                    InetAddress inetAddress = enumIpAddress.nextElement();
//                    if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress() && inetAddress.isSiteLocalAddress()) {
//                        ip.append(inetAddress.getHostAddress().toString());
//                    }
//                }
//            }
//        }catch (SocketException e){
//            throw new RuntimeException("get host ip error",e);
//        }
//        return ip.toString();
        String ip;
        try{
            ip = InetAddress.getLocalHost().getHostAddress();
        }catch (UnknownHostException e){
            throw new RuntimeException("get host ip error",e);
        }
        return ip;
    }

    public static void main(String[] args) throws Exception
    {
        String ip = InetAddress.getLocalHost().getHostAddress();
        System.out.println(ip);
    }

}
