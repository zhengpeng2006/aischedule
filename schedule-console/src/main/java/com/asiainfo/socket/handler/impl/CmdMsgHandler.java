package com.asiainfo.socket.handler.impl;

import com.asiainfo.appframe.ext.flyingserver.org.apache.commons.lang.StringUtils;
import com.asiainfo.socket.pool.SocketConstants.Cmd;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 命令类型消息处理
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: AI(NanJing)</p>
 * @author wu songkai
 * @version 3.0
 */
public class CmdMsgHandler extends DefaultMsgHandler
{
    private static final Log LOG = LogFactory.getLog(CmdMsgHandler.class);
    private static final String LINE_SEPERATOR = System.getProperty("line.separator");
    
    @Override
    public Object handle(Object param)
    {
        JSONObject cmdJson = (JSONObject) param;
        
        JSONObject rtnJson = new JSONObject();
        String cmd = (String) cmdJson.get("CMD");
        LOG.debug("准备执行命令：" + cmd);
        
        if (StringUtils.isEmpty(cmd)) {
            rtnJson.put(Cmd.EXIT_STATUS, Cmd.ERROR);
            rtnJson.put(Cmd.OUTPUT, "empty command.");
            return rtnJson;
        }
        
        String[] cmds = new String[3];
        cmds[0] = "sh";
        cmds[1] = "-c";
        cmds[2] = cmd;
        
        try {
            Process process = Runtime.getRuntime().exec(cmds);
            
            //正常输出
            List<String> normalOutputList = new ArrayList<String>();  
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));  
            String normalLine = null;  
            while ((normalLine = input.readLine()) != null) {  
                normalOutputList.add(normalLine);  
            }  
            input.close();
            
            //错误输出
            List<String> errOutputList = new ArrayList<String>();
            BufferedReader errput = new BufferedReader(new InputStreamReader(process.getErrorStream()));  
            String errorLine = null;
            while ((errorLine = errput.readLine()) != null) {  
                errOutputList.add(errorLine);  
            }  
            errput.close();
            
            //等待运行结束获取退出码
            process.waitFor();
            int exitStatus = process.exitValue();
            if (exitStatus == 0) {
                rtnJson.put(Cmd.EXIT_STATUS, Cmd.SUCCESS);
                rtnJson.put(Cmd.OUTPUT, StringUtils.join(normalOutputList, LINE_SEPERATOR));
                LOG.error("执行命令：" + cmd + "成功：" + rtnJson.toString());
            }
            else {
                rtnJson.put(Cmd.EXIT_STATUS, Cmd.ERROR);
                rtnJson.put(Cmd.OUTPUT, StringUtils.join(errOutputList, LINE_SEPERATOR));
                LOG.error("执行命令：" + cmd + "出错:" + rtnJson.toString());
            }
        } catch (IOException ex) {
            LOG.error("执行命令：" + cmd + "出错。", ex);
            rtnJson.put(Cmd.EXIT_STATUS, Cmd.ERROR);
            rtnJson.put(Cmd.OUTPUT, ex.getLocalizedMessage());
        } catch (InterruptedException ex) {
            LOG.error("执行命令：" + cmd + "出错。", ex);
            rtnJson.put(Cmd.EXIT_STATUS, Cmd.ERROR);
            rtnJson.put(Cmd.OUTPUT, ex.getLocalizedMessage());
        }
        
        return rtnJson;
    }

    
}
