package com.asiainfo.socket.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.appframe.ext.exeframe.socket.SocketFrameWork;

/**
 * Socket server服务端
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: AI(NanJing)</p>
 * @author wu songkai
 * @version 3.0
 */
public class AcqSocketServer
{
    private static transient Log log = LogFactory.getLog(AcqSocketServer.class);

    /** SOCKET采集服务器 **/
    private static String ACQ_SOCKET_SERVER_CODE = "MSG_DISPATCH_SERVER";

    public static void main(String[] args)
    {
        try{
            SocketFrameWork.main(new String[]{ACQ_SOCKET_SERVER_CODE});
        }
        catch(Exception ex) {
            log.error("--Socket start Exception :", ex);
        }
        
    }

}
