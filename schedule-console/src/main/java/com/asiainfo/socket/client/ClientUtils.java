/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asiainfo.socket.client;

import com.asiainfo.socket.request.Request;
import com.asiainfo.socket.pool.ConnectedSocketPool;
import com.asiainfo.socket.pool.SocketConfig;
import com.asiainfo.socket.queue.ResponseHolder;
import com.asiainfo.socket.future.OperationFuture;
import com.asiainfo.socket.util.PackageUtils;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;

/**
 * 向服务端发送请求统一接口
 * @author grucee孙德东
 */
public class ClientUtils {
    
    private static final transient Log LOG = LogFactory.getLog(ClientUtils.class);
    private static final int RESPONSE_TIME_OUT = SocketConfig.getResponseTimeout();
    
    /**
     * 发送同步请求,返回值为响应
     * @param req
     * @return 
     * @throws Exception
     */
    public static JSONObject syncRequest(Request req) throws Exception {
        OperationFuture future = asyncRequest(req);
        //等待响应
        return future.getResult(RESPONSE_TIME_OUT);
    }
    
    
    public static OperationFuture asyncRequest(Request req) throws Exception {
        String ip = req.getIp();
        String port = req.getPort();
        JSONObject reqJson = req.getReqJson();
        Object attachment = req.getAttachment();
        
        if (LOG.isDebugEnabled()) {
        	LOG.debug("准备发送请求：" + req);
        }
        Channel ch = ConnectedSocketPool.getChannel(ip, port);
    	if (ch == null) {
                LOG.error("当前主机[" + ip + ":" + port + "未联通。");
    		throw new Exception("当前主机[" + ip + ":" + port + "未联通。");
    	}
        
    	long serialId = reqJson.getLong("SERIAL_ID");
    	//压缩报文
    	byte[] b = PackageUtils.dataPackageCompose(reqJson.toString());
        
        //写之前监听响应
        OperationFuture future = ResponseHolder.registerWaiter(attachment, serialId);
        
        //写请求
        ChannelBuffer buf = ChannelBuffers.buffer(b.length);
        buf.writeBytes(b);
        ch.write(buf);
        
        //等待响应
        return future;
    }
    
    public static boolean ping(String hostIp) {
		// 去连接池中获取连接
		String port = String.valueOf(SocketConfig.getMonitorPort());
		if (ConnectedSocketPool.getChannel(hostIp, port) == null) {
			LOG.error("channel of [" + hostIp + ":" + port + "] not exist in pool.");
			return false;
		}

		LOG.debug("channel of [" + hostIp + ":" + port + "] exist in pool.");
		return true;
	}
    
    
}
