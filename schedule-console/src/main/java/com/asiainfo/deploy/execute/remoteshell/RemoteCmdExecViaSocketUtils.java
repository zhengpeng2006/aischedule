/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asiainfo.deploy.execute.remoteshell;

import com.asiainfo.appframe.ext.flyingserver.org.apache.commons.lang.StringUtils;
import com.asiainfo.deploy.exception.ErrorCode;
import com.asiainfo.deploy.exception.ExecuteResult;
import com.asiainfo.socket.client.ClientUtils;
import com.asiainfo.socket.pool.SocketConstants.Cmd;
import com.asiainfo.socket.request.Request;
import com.asiainfo.socket.request.RequestConstructor;
import java.util.HashMap;
import java.util.Map;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * RemoteCmdExecUtils类中提供了通过SSH协议执行命令的接口
 * RemoteCmdExecViaSocketUtils类中提供通过socket方式执行 远程命令的接口
 *
 * @author sundedong(grucee)
 */
public class RemoteCmdExecViaSocketUtils {

    private static final Log LOG = LogFactory.getLog(RemoteCmdExecViaSocketUtils.class);

    /**
     * 执行远程命令
     *
     * @param ip
     * @param command
     * @return
     */
    public static ExecuteResult execRomoteCmd(String ip, String command) {
        logcmd(ip, command);
        
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("IP", ip);
        paramMap.put("CMD", command);
        
        try {
            Request req = RequestConstructor.constructCmdRequest(ip, command, null);
            JSONObject rtn = ClientUtils.syncRequest(req);
            JSONObject cmdRtn = rtn.getJSONObject("RESULT");
            
            String exitStatus = cmdRtn.getString(Cmd.EXIT_STATUS);
            String output = cmdRtn.getString(Cmd.OUTPUT);
            if (StringUtils.equalsIgnoreCase(exitStatus, Cmd.SUCCESS)) {
                return ExecuteResult.successResult(output);
            }
            else {
                return ExecuteResult.errorResult(ErrorCode.RemoteExecute.EXEC_CMD_ERROR, output);
            }
        } catch (Exception ex) {
            LOG.error("执行命令失败：", ex);
            return ExecuteResult.errorResult(ErrorCode.RemoteExecute.EXEC_CMD_ERROR, ex);
        }
    }
    
    private static void logcmd(String ip, String command) {
        if (LOG.isDebugEnabled()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Execute Remote Command Via Socket[ip=")
                    .append(ip)
                    .append(",command={")
                    .append(command).append("}]");
            LOG.debug(sb.toString());
        }
    }

}
