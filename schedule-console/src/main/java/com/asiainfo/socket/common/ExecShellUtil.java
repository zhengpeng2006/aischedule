package com.asiainfo.socket.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
     * @param shell
     * @param param
     * @return
     * @throws Exception
     */
    public static Object execShell(String shellFilePath, String param) throws Exception
    {
        Process process = null;
        StringBuffer strBufer = new StringBuffer();
        try {
            process = Runtime.getRuntime().exec(shellFilePath + " " + param);
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            while((line = input.readLine()) != null) {
                strBufer.append(line);
            }
            input.close();

        }
        catch(IOException e) {
            e.printStackTrace();
        }

        return strBufer.toString();
    }

    /**
     * 执行shell脚本
     * @param shellStr
     * @return
     * @throws Exception
     */
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
            e.printStackTrace();
        }

        return processList;
    }

    /**
     * 执行shell命令，无返回结果
     * @param shellStr
     * @throws Exception
     */
    public static void execShellCmd(String shellStr) throws Exception
    {
        //need to code

    }

}
