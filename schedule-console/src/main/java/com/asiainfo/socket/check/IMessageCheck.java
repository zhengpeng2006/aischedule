package com.asiainfo.socket.check;

import net.sf.json.JSONObject;

public interface IMessageCheck
{
    /**
     * 请求消息报文check
     * @param reqMsgObj
     * @throws Exception
     */
    public void requestMessageCheck(JSONObject reqMsgObj) throws Exception;
    
    /**
     * 响应消息报文检查
     * @param respMsgObj
     * @throws Exception
     */
    public void responseMessageCheck(JSONObject respMsgObj) throws Exception;

}
