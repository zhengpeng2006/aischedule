package com.asiainfo.socket.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.deploy.exception.ExecuteResult;
import com.asiainfo.deploy.execute.remoteshell.RemoteCmdExecViaSocketUtils;
import com.asiainfo.socket.client.ClientUtils;
import com.asiainfo.socket.future.OperationFuture;
import com.asiainfo.socket.pool.SocketConfig;
import com.asiainfo.socket.pool.heartbeat.HeartbeatThread;
import com.asiainfo.socket.request.Request;
import com.asiainfo.socket.sequence.SequenceGenerator;

/**
 * Socket 客户端发送消息辅助类
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: AI(NanJing)</p>
 * @author wu songkai
 * @version 3.0
 */
public class ClientHelper
{
    private static final transient Log LOG = LogFactory.getLog(ClientHelper.class);

    private static final String MONITOR_PORT = String.valueOf(SocketConfig.getMonitorPort());
    private static final int RESPONSE_TIME_OUT = SocketConfig.getResponseTimeout();
    /**
     * 批量异步执行
     * @param list
     * @return 
     * @throws Exception
     */
    public static Map<String, String> batchAsyncExecShell(List<Map<String, String>> list) throws Exception {
        Map<String, String> rtns = new HashMap<String, String>();
        
        List<OperationFuture> futures = new LinkedList<OperationFuture>();
        for (Map<String, String> paramMap : list) {
            String ip = paramMap.get("IP");
            try {
            JSONObject reqMsgObj = decorShellParam(paramMap);
            futures.add(ClientUtils.asyncRequest(new Request(ip, MONITOR_PORT, reqMsgObj, ip)));
            } catch (Exception e) {
                LOG.error("处理IP：" + ip + " 请求失败！", e);
                rtns.put(ip, null);
            }
        }
        
        for (OperationFuture future : futures) {
            JSONObject rtn = future.getResult(RESPONSE_TIME_OUT);
            rtns.put((String)future.getAttachment(), rtn == null ? StringUtils.EMPTY : rtn.getString("RESULT"));
        }
        return rtns;
    }
    
    
    /**
     * 发送消息 ,map中必须包含的字段<SERIAL_ID,TYPE_ID,TMP_PATH,CONTENT<PARAM,SHELL>>
     * @param paramMap
     * @return
     * @throws Exception 
     */
    public static String execShell(Map<String, String> paramMap) throws Exception
    {
        JSONObject reqMsgObj = decorShellParam(paramMap);
        String ip = paramMap.get("IP");
        
        JSONObject rtn = ClientUtils.syncRequest(new Request(ip, MONITOR_PORT, reqMsgObj, ip));
        return rtn == null ? StringUtils.EMPTY : rtn.getString("RESULT");
    }
    
    /**
     * 参数修饰
     * @param paramMap
     * @return
     * @throws Exception
     */
    private static JSONObject decorShellParam(Map<String, String> paramMap)
    {
        //业务参数
        JSONObject msgObj = new JSONObject();
        //请求唯一标识
        msgObj.put("SERIAL_ID", String.valueOf(SequenceGenerator.next()));
        //请求类型
        msgObj.put("TYPE_ID", paramMap.get("TYPE_ID"));

        JSONObject subObj = new JSONObject();
        //subObj.put("TMP_PATH", "/app/" + paramMap.get("USER_NAME") + "/tmp/");
        subObj.put("TMP_PATH", paramMap.get("HOME_PATH") + "/tmp/");
        subObj.put("PARAM", paramMap.get("PARAM"));
        subObj.put("SHELL", paramMap.get("SHELL"));
        //设置请求Content
        msgObj.put("CONTENT", subObj);
        
        return msgObj;

    }

    public static void main(String[] args) throws Exception {
	   new HeartbeatThread().start();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
            LOG.error("sleep error");
        }

        final int num = 100;
        int threadNum = 10;

        long start = System.currentTimeMillis();
        System.out.println("start:" + start);
        
        ExecuteResult rtn = RemoteCmdExecViaSocketUtils.execRomoteCmd("10.70.165.4", "ls");
        System.out.println(rtn);
//        for (int i = 0; i < threadNum; i++) {
//
//            new Thread() {
//                @Override
//                public void run() {
//                    try {
//                    for (int i = 0; i < num; i++) {
//                        System.out.println(i + "=" + RemoteCmdExecViaSocketUtils.execRomoteCmd(paramMap));
//                    }
//                    } catch(Exception e) {
//                        //DO thing
//                        e.printStackTrace();
//                    }
//                }
//            }.start();
//
//        }

        long end = System.currentTimeMillis();
        System.out.println("cost:" + (end - start));
        System.out.println("average:" + (end - start) / num);
   }
}
