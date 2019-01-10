/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.asiainfo.socket.request;

import com.asiainfo.socket.pool.SocketConstants.Common;
import com.asiainfo.socket.sequence.SequenceGenerator;
import net.sf.json.JSONObject;

/**
 * 请求构造器
 * @author sundedong(grucee)
 */
public class RequestConstructor {
    private RequestConstructor(){}
    
    /**
     * 构造执行远程Command请求
     * @param ip
     * @param cmd
     * @param attachment
     * @return 
     */
    public static Request constructCmdRequest(String ip, String cmd, Object attachment) {
        return new Request(ip, Common.MONITOR_PORT, constructCmdJson(cmd), attachment);
    }
    
    private static JSONObject constructCmdJson(String cmd)
    {
        //业务参数
        JSONObject msgObj = new JSONObject();
        //请求唯一标识
        msgObj.put("SERIAL_ID", String.valueOf(SequenceGenerator.next()));
        //请求类型
        msgObj.put("TYPE_ID", "1001");

        JSONObject subObj = new JSONObject();
        subObj.put("CMD", cmd);
        //设置请求Content
        msgObj.put("CONTENT", subObj);
        
        return msgObj;
    }

}
