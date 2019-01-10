package com.asiainfo.socket.pool;

import net.sf.json.JSONObject;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

import com.asiainfo.socket.pool.SocketConstants.Message;
import com.asiainfo.socket.util.ConvertUtil;

/**
 * 粘包（组包）
 * 
 * @author 孙德东(24204)
 */
public class ResponseDecoder extends FrameDecoder{
	private int msgLength = -1;
    
	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
		//消息长度
		if (this.msgLength == -1 && buffer.readableBytes() < Message.LENGTH_OCCUPY) {
			return null;
		}
		
		//读取消息长度头
		if (this.msgLength == -1 && buffer.readableBytes() >= Message.LENGTH_OCCUPY) {
			this.msgLength = ConvertUtil.byteArrayToInt(buffer.readBytes(Message.LENGTH_OCCUPY).array(), 4, ConvertUtil.ALIGNMENT_HIGHT);
		}
		
		//读取消息体
		if (buffer.readableBytes() >= this.msgLength) {
			ChannelBuffer msg = buffer.readBytes(this.msgLength);
			this.msgLength = -1;
			
			return JSONObject.fromObject(new String(msg.array(), Message.CHARSET));
		}
		else {
			return null;
		}
	}
}
