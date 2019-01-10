package com.asiainfo.socket.future;

import java.util.EventListener;


/**
 * 接收到响应后的处理监听器
 * @author grucee
 */
public interface OperationListener extends EventListener{
    
    /**
     * 接收到响应后的处理
     * @return 
     */
    Object operation(OperationFuture listener);
}
