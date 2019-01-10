package com.asiainfo.socket.handler.impl;

/**
 * 处理心跳 typeid:1005
 * 
 * @author 孙德东(24204)
 */
public class HeartbeatMsgHandler extends DefaultMsgHandler {

	@Override
	public Object handle(Object param) {
		return System.currentTimeMillis();
	}
}
