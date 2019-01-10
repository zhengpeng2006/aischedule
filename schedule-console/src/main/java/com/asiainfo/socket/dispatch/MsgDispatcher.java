package com.asiainfo.socket.dispatch;

import java.util.concurrent.FutureTask;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.socket.common.BusiHandleCallable;
import com.asiainfo.socket.common.ConfigHelper;
import com.asiainfo.socket.handler.IMsgHandler;

/**
 * 消息分发、调度器
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: AI(NanJing)</p>
 * @author wu songkai
 * @version 3.0
 */
public class MsgDispatcher
{

    private static transient Log log = LogFactory.getLog(MsgDispatcher.class);

    /**
     * 消息分发
     * @param jsonMsg
     * @return
     * @throws Exception
     */
    public Object dispatch(JSONObject jsonMsg) throws Exception
    {

        String msgType = (String) jsonMsg.get("TYPE_ID");

        //此处从配置文件中读取每个消息类型对应的处理类
        String handleCls = ConfigHelper.getHandle(msgType);

        if(StringUtils.isBlank(handleCls)) {
            log.error("-- can't find handle class by message type:" + msgType);
            throw new Exception("-- can't find handle class by message type:" + msgType + ",please check the config.");
        }

        Object result = null;
        try {

            BusiHandleCallable callable = new BusiHandleCallable();
            callable.setMsgHandle((IMsgHandler) Class.forName(handleCls).newInstance());
            callable.setParamObj(jsonMsg.get("CONTENT"));

            FutureTask<Object> task = new FutureTask<Object>(callable);
            Thread taskThread = new Thread(task);
            taskThread.start();

            //获取结果
            result = task.get();
        }
        catch(InterruptedException ex) {
            log.error(" message dispatch occur exception:", ex);
            throw ex;
        }

        return result;
    }
}
