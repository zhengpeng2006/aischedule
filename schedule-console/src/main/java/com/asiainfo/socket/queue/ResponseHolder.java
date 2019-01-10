package com.asiainfo.socket.queue;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.json.JSONObject;

import com.asiainfo.socket.pool.SocketConstants.Message;
import com.asiainfo.socket.future.DefaultOperationFuture;
import com.asiainfo.socket.future.OperationFuture;

/**
 * 响应
 * 
 * @author 孙德东(24204)
 */
public class ResponseHolder {
	private static final transient Log LOG = LogFactory.getLog(ResponseHolder.class);
	// 存放消息等待者
	private static final BoundedConcurrentMap<Long, OperationFuture> waiters = new BoundedConcurrentMap<Long, OperationFuture>(1024); 
	
	private ResponseHolder(){}
	
	/**
	 * 接收到消息
	 * @param response
	 */
	public static void responseReceive(JSONObject response) {
		Long serialId = Long.valueOf((String)response.get(Message.SERIAL_ID));
		OperationFuture future = waiters.remove(serialId);
		if (future == null) {
			LOG.error("消息" + serialId + "没有监听者，直接丢弃。");
			return;
		}
		
		// 通知已经接收到消息
                future.setResult(response);
	}
	
	
        /**
         * 注册监听
         * @param serialId 
     * @return  
     * @throws InterruptedException
         */
        public static OperationFuture registerWaiter(Object attachment, Long serialId) throws InterruptedException {
            OperationFuture future = new DefaultOperationFuture(attachment);
            // 放入等待队列中
	    waiters.put(serialId, future);
            LOG.debug("监听消息:" + serialId + "的响应。");
            return future;
        }
}
