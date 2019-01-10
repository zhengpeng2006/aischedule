package com.asiainfo.monitor.busi.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.util.FileUtils;
import com.asiainfo.appframe.ext.flyingserver.org.apache.commons.lang.StringUtils;
import com.asiainfo.monitor.common.CommonSvUtil;
import com.asiainfo.monitor.common.IdWorker;

/**
 * shell脚本执行类
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: AI(NanJing)</p>
 * @author wu songkai
 * @version 3.0
 */
public class ExecShellUtil
{

    private static transient Log log = LogFactory.getLog(ExecShellUtil.class);

    /**
     * 执行shell脚本
     * @param shellFile脚本路径
     * @param param脚本参数
     * @return
     * @throws Exception
     */
    public static String execShell(String shellFile, String param) throws Exception
    {
        //根据shell创建脚本文件
        String info = "";
        BufferedReader input = null;
        try {
            Process process = null;
            StringBuffer infoStr = new StringBuffer("");
            if(StringUtils.isNotBlank(shellFile)) {
                //执行脚本
                execShell("chmod 750 " + shellFile);
                process = Runtime.getRuntime().exec(shellFile + " " + param);
                input = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line = "";
                while((line = input.readLine()) != null) {
                    infoStr.append(line);
                }
                info = infoStr.toString();
            }
        }catch(Exception e) {
            log.error("execute shell exception:", e);
        }finally {
            //关闭在fianlly中进行
            input.close();
            //删除
            execShell("rm -rf " + shellFile);
        }
        return info;
    }

    /**
     * 通过主机标识获取shell脚本文件路径,并将脚本写入到该路径中
     * 
     * @param request
     * @throws Exception
     */
    public static String getShellFile(String shell) throws Exception
    {
        String shellFile = "";
        //脚本临时路径
        String homeDir = System.getProperty("user.home");
        if(StringUtils.isEmpty(homeDir)) {
            //根据ip查询当前主机下监控节点的路径
            InetAddress netAddress = InetAddress.getLocalHost();
            //获取当前主机的IP
            String ip = "";
            if(null != netAddress) {
                ip = netAddress.getHostAddress();
            }
            String homePath = CommonSvUtil.qryMonNodeDeployPathByIp(ip);
            if(null != homePath) {
                homeDir = homePath;
            }
            else {
                return null;
            }
        }
        String tmpPath = homeDir + "/tmp";
        File tmpDirFile = new File(tmpPath);
        if(!tmpDirFile.exists()) {
            tmpDirFile.mkdirs();
        }

        shellFile = tmpPath + "/" + IdWorker.getInstance().nextId() + ".sh";
        try {
            File file = new File(shellFile);
            if(!file.exists()) {
                file.createNewFile();
            }
            //上传文件
            FileUtils.writeFile(shellFile, shell.getBytes());
        }
        catch(Exception e) {
            log.error("create shellFile exception:", e);
        }
        return shellFile;
    }

    public static void main(String[] args) throws Exception
    {
        getShellFile("hello world");
    }

    public static Object execShell(String shellStr) throws Exception
    {
        Process process = null;
        List<String> processList = new ArrayList<String>();
        try {
            process = Runtime.getRuntime().exec(shellStr);
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            while((line = input.readLine()) != null) {
                processList.add(line);
            }
            input.close();
        }
        catch(IOException e) {
            log.error("execute shell exception:", e);
        }
        return processList;
    }

    public static void main2(String[] args) throws Exception
    {
        execShell("echo hello", "");
    }

}
