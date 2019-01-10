package com.asiainfo.socket.check.impl;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.socket.check.IMessageCheck;

/**
 * 请求、响应消息报文check
 * 
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: AI(NanJing)</p>
 * @author wu songkai
 * @version 3.0
 */
public class MessageChecker implements IMessageCheck
{
    private static transient Log log = LogFactory.getLog(MessageChecker.class);

    @Override
    public void requestMessageCheck(JSONObject reqMsgObj) throws Exception
    {
        //检查请求报文是否符合消息格式

    }

    @Override
    public void responseMessageCheck(JSONObject respMsgObj) throws Exception
    {
        //这里check数据响应消息格式，以及响应报文是否为请求报文的响应，通过serialId来check

        //如果返回失败报文
        String retCode = (String)respMsgObj.get("CODE");
        if(!StringUtils.isBlank(retCode)&& "F".equals(retCode)){
            log.error("-- response message check error,retCode:" + retCode);
            throw new Exception("-- response message check error,retCode:" + retCode);
        }

    }

}
