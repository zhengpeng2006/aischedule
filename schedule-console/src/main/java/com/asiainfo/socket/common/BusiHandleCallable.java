package com.asiainfo.socket.common;

import java.util.concurrent.Callable;

import com.asiainfo.socket.handler.IMsgHandler;

/**
 * 业务处理handle
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: AI(NanJing)</p>
 * @author wu songkai
 * @version 3.0
 */
public class BusiHandleCallable implements Callable<Object>
{
    private IMsgHandler msgHandle = null;

    /** 处理参数对象 **/
    private Object paramObj = null;

    @Override
    public Object call() throws Exception
    {
        return msgHandle.handle(paramObj);
    }

    public void setMsgHandle(IMsgHandler msgHandle)
    {
        this.msgHandle = msgHandle;
    }

    public void setParamObj(Object paramObj)
    {
        this.paramObj = paramObj;
    }

}
