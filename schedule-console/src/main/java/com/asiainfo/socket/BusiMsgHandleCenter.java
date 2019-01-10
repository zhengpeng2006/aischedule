package com.asiainfo.socket;

import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.appframe.ext.exeframe.socket.data.SecurityData;
import com.asiainfo.appframe.ext.exeframe.socket.interfaces.IAdvanceBusinessBinary;
import com.asiainfo.socket.dispatch.MsgDispatcher;
import com.asiainfo.socket.util.PackageUtils;

/**
 * 业务消息处理中心
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: AI(NanJing)</p>
 * @author wu songkai
 * @version 3.0
 */
public class BusiMsgHandleCenter implements IAdvanceBusinessBinary
{
    private static transient Log LOG = LogFactory.getLog(BusiMsgHandleCenter.class);

    /** 分发处理器 **/
    private static MsgDispatcher dispatcher = new MsgDispatcher();

    @SuppressWarnings("rawtypes")
	@Override
    public Object work(byte[] msgByteArr, SecurityData arg1, Map arg2)
    {
        JSONObject respJson = new JSONObject();
        byte[] resp = new byte[0];
        try{
            //解析消息获取消息内容，并转化为JSON对象
        	String recv = PackageUtils.dataPackageParse(msgByteArr);
        	JSONObject recvJson = JSONObject.fromObject(recv);
        	
        	//log it
            LOG.debug("handling received message:" + recvJson.toString());
            
            //构造返回数据体
            respJson.put("SERIAL_ID", recvJson.get("SERIAL_ID"));
            respJson.put("TYPE_ID", recvJson.get("TYPE_ID"));
            
            //消息分发处理
            Object retMsg = dispatcher.dispatch(recvJson);

            //消息处理成功
            respJson.put("CODE", "T");
            respJson.put("RESULT", retMsg);
        }
        catch(Exception ex) {
            LOG.error("-- message handle exception:", ex);
            respJson.put("CODE", "F");
            respJson.put("RESULT", "handling message  occurs exception:" + ex.getLocalizedMessage());
        } finally {
             try {
            	 resp = PackageUtils.dataPackageCompose(respJson.toString());
			} catch (Exception e) {
				LOG.error("compose response message error,message:" + respJson.toString(), e);
			}
        }
		return resp;
    }

    
    @Override
    public boolean isNeedSecurity(String arg0, byte[] arg1)
    {
        return false;
    }

    @Override
    public boolean isSecurityMessage(String arg0, byte[] arg1)
    {
        return false;
    }

    @Override
    public byte[] notLoginMessage(String arg0, byte[] arg1)
    {
        return null;
    }
}
