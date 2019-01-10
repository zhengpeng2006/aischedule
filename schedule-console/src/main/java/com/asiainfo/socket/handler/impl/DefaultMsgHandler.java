package com.asiainfo.socket.handler.impl;

import com.asiainfo.socket.handler.IMsgHandler;

/**
 * 默认消息处理类
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: AI(NanJing)</p>
 * @author wu songkai
 * @version 3.0
 */
public class DefaultMsgHandler implements IMsgHandler
{

    @Override
    public Object handle(Object param)
    {
        return "test result";
    }

}
