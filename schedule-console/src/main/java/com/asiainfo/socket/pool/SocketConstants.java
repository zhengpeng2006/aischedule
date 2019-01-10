package com.asiainfo.socket.pool;

/**
 * 常量
 *
 * @author 孙德东(24204)
 */
public interface SocketConstants {

    //心跳间隔10秒
    int DEFAULT_HEART_BEAT_INTERNAL = 10000;
    //连接超时2秒
    int DEFAULT_CONNNECT_TIMEOUT = 2000;
    //监控的默认端口
    int DEFAULT_MONITOR_PROT = 2045;
    //20秒没有收到服务器的任何消息则认为超时
    int DEFAULT_READ_TIMEOUT = 20;
    //等待响应的超时时间,毫秒为单位
    int DEFAULT_RESPONSE_TIMEOUT = 10000;

    String HEART_BEAT_INTERNAL_PROP = "heartbeat.internal";
    String CONNECT_TIMEOUT_PROP = "connect.timeout";
    String MONITOR_PORT_PROP = "monitor.port";
    String READ_TIMEOUT_PROP = "read.timeout";
    String RESPONSE_TIMEOUT_PROP = "response.timeout";

    /**
     *
     * @author 孙德东(24204)
     */
    interface Message {

        String SERIAL_ID = "SERIAL_ID";
        String TYPE_ID = "TYPE_ID";
        String CODE = "CODE";
        String RESULT = "RESULT";
        String CHARSET = "UTF-8";
        String HEART_BEAT_TYPE_ID = "1005";
        int LENGTH_OCCUPY = 4;//消息长度头占4位
    }
    
    
    interface Cmd {
        String EXIT_STATUS = "EXIT_STATUS";
        String OUTPUT = "OUTPUT";
        String SUCCESS = "0";
        String ERROR = "1";
    }
    
    interface Common {
         String MONITOR_PORT = String.valueOf(SocketConfig.getMonitorPort());
         int RESPONSE_TIME_OUT = SocketConfig.getResponseTimeout();
    }
}
