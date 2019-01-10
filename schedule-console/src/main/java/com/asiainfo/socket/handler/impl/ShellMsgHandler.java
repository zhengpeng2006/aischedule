package com.asiainfo.socket.handler.impl;

import java.io.File;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.util.FileUtils;
import com.asiainfo.monitor.common.IdWorker;
import com.asiainfo.socket.common.ExecShellUtil;

/**
 * 脚本类型的消息处理
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: AI(NanJing)</p>
 * @author wu songkai
 * @version 3.0
 */
public class ShellMsgHandler extends DefaultMsgHandler
{

    private static transient Log log = LogFactory.getLog(ShellMsgHandler.class);

    @Override
    public Object handle(Object param)
    {
        JSONObject shellObj = (JSONObject) param;
        //脚本参数
        String paramStr = (String) shellObj.get("PARAM");
        //脚本
        String shellStr = (String) shellObj.get("SHELL");
        //脚本临时路径
        String tmpPath = (String) shellObj.get("TMP_PATH");

        log.debug("-- file Path:" + tmpPath);

        String shellFile = tmpPath + IdWorker.getInstance().nextId() + ".sh";

        log.debug("-- file name:" + shellFile);
        String respResult = "";
        try {

            File tmpFile = new File(tmpPath);
            //如果文件夹不存在，则创建

            if(!(tmpFile.exists() && tmpFile.isDirectory())) {
                tmpFile.mkdirs();
            }


            File file = new File(shellFile);
            if(!file.exists()) {
                file.createNewFile();
            }

            //上传文件
            FileUtils.writeFile(shellFile, shellStr.getBytes());

            //修改文件权限
            ExecShellUtil.execShell("chmod 750 " + shellFile);

            //执行并返回结果
            respResult = (String) ExecShellUtil.execShell(shellFile, paramStr);

        }
        catch(Exception ex) {
            log.error("execute shell exception:", ex);
        }
        finally {
            try {
                //删除
                ExecShellUtil.execShell("rm -rf " + shellFile);
            } catch (Exception ex) {
                return ex.getLocalizedMessage();
            }
        }
        return respResult;
    }
}
