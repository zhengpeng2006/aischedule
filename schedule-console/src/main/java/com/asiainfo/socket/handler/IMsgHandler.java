package com.asiainfo.socket.handler;


/**
 * Socket请求发送过来的消息处理接口，子类需要实现该接口
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: AI(NanJing)</p>
 * @author wu songkai
 * @version 3.0
 */
public interface IMsgHandler
{

    /**
     * 消息处理函数
     * @param jsonParam
     * @return
     * @throws Exception
     */
    public Object handle(Object param);

}
