package com.asiainfo.schedule.core.utils;

import com.asiainfo.appframe.ext.flyingserver.org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;

/**
 * 常用工具类
 *
 * @author LiuQQ
 * @company Asiainfo
 * @date 2014年8月29日
 * @modify
 */
public class CommonUtils {

    public static String getIP() {
        String ip = null;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (Throwable ex) {

            ip = "127.0.0.1";
            ex.printStackTrace();
        }
        return ip;
    }

    public static String getHostName() {
        String hostname = null;
        try {
            hostname = InetAddress.getLocalHost().getCanonicalHostName();
        } catch (Throwable ex) {
            hostname = "Unrecognized";
            ex.printStackTrace();
        }
        return hostname;
    }

    public static String getPID() {
        String pid = null;
        try {
            pid = ManagementFactory.getRuntimeMXBean().getName();
        } catch (Throwable ex) {
            pid = "-1";
        }
        return pid;
    }

    public static String getServerName() {
        String serverName = System.getProperty("appframe.client.app.name");
        if (isBlank(serverName)) {
            serverName = System.getProperty("appframe.server.name");
        }
        // 默认生成
        if (isBlank(serverName)) {
            serverName = "DEFAULT_SERVER_" + System.currentTimeMillis();
        }

        return serverName;
    }

    public static boolean isBlank(String s) {
        return s == null || s == "" || s.trim().equals("");
    }

    public static String getExceptionMesssage(Throwable ex, int length) {
        Throwable t = ex;
        int i = 0;
        while (t.getCause() != null && i < 3) {
            t = t.getCause();
            i++;
        }
        return StringUtils.substring(ExceptionUtils.getFullStackTrace(t), 0, length);
    }

    /**
     * 翻译系统中的cron表达式到quartz中的表达式
     *
     * @param cron String
     * @return String
     */
    public static String translateCron(String cron) {
        if (StringUtils.isBlank(cron)) {
            return null;
        }

        // 我们系统46,47,48,49,50 -1 -1 -1 -1 2006
        // quartz系统0 46,47,48,49,50 * * * * 2006

        // 我们系统 1 0 -1 -1 -1 -1
        // quartz系统 0 1 0 * * * ?

        // 替换原则,所有-1替换成*符号
        // 同时将最后一个*符号，替换成?符号
        // 在字符串前面加入0 字符，记住有空格

        //根据帐管中心需求支撑秒级配置

        cron = StringUtils.replace(cron, "-1", "*").trim();
        cron = "0 " + cron;

        String[] tmp = StringUtils.split(cron, " ");
        if (tmp[5].equalsIgnoreCase("*")) {
            tmp[5] = "?";
        } else if (tmp[3].equalsIgnoreCase("*")) {  //增加原因:quartz规则明确日期和周不能同时指定，错误场景quartz会抛出
            tmp[3] = "?";
        }

        cron = StringUtils.join(tmp, " ");
        return cron;
    }

    public static String translateToExefameCron(String cron) {
        String[] tmp = StringUtils.split(cron, " ");
        if (tmp[5].equalsIgnoreCase("?")) {
            tmp[5] = "*";
        }
        String[] exeframeCronTmp = new String[tmp.length - 1];
        for (int i = 0; i < exeframeCronTmp.length; i++) {
            exeframeCronTmp[i] = tmp[i + 1];
        }
        String exeframeCron = StringUtils.join(exeframeCronTmp, " ");
        exeframeCron = StringUtils.replace(exeframeCron, "*", "-1");
        return exeframeCron;
    }

    public static void main(String[] args) {
        String cron = "0 0/1 1 6-31 * ? *";
        String exeframeCron = translateToExefameCron(cron);
        System.out.println(exeframeCron);

        String exeFrameCron = "0 0/4 -1 -1 -1 -1";
        String quartz = translateCron(exeFrameCron);
        System.out.println(quartz);
    }
}
