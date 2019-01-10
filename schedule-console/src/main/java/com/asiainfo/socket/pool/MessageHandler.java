package com.asiainfo.socket.pool;

import java.net.InetSocketAddress;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import com.asiainfo.schedule.core.utils.CommonUtils;
import com.asiainfo.schedule.core.utils.Constants;
import com.asiainfo.schedule.core.utils.SchedulerLogger;
import com.asiainfo.socket.pool.SocketConstants.Message;
import com.asiainfo.socket.queue.ResponseHolder;

/**
 * 心跳处理
 *
 * @author 孙德东(24204)
 */
public class MessageHandler extends SimpleChannelHandler {

    private static final transient Log LOG = LogFactory.getLog(MessageHandler.class);

    /**
     * 连接建立以后，发送心跳请求
     */
    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        Channel ch = e.getChannel();
        InetSocketAddress remote = (InetSocketAddress) ch.getRemoteAddress();
        String host = remote.getAddress().getHostAddress();
        String port = String.valueOf(remote.getPort());

        //log
        StringBuilder sb = new StringBuilder();
        sb.append("channel[").append(host).append(":").append(port).append("]").append(" connected.");
        LOG.debug(sb.toString());

        //连接建立以后放入连接池
        ConnectedSocketPool.addChannel(host, port, ch);
    }

    /**
     * 心跳测试使用短连接 例外的情况是，如果连接池中还没有该连接，则将连接
     *
     * @param ctx
     * @param e
     * @throws Exception
     */
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        JSONObject response = (JSONObject) e.getMessage();

        //打印消息
        if (LOG.isDebugEnabled()) {
            Channel ch = e.getChannel();
            InetSocketAddress remote = (InetSocketAddress) ch.getRemoteAddress();
            String host = remote.getAddress().getHostAddress();
            String port = String.valueOf(remote.getPort());

            StringBuilder sb = new StringBuilder();
            sb.append("接收到响应消息[").append(host).append(":").append(port).append("]").append("\n").append(response.toString());
            LOG.debug(sb.toString());
        }

        //是否错误消息
        String code = (String) response.get(Message.CODE);
        if (StringUtils.equalsIgnoreCase(code, "F")) {
            StringBuilder sb = new StringBuilder();
            sb.append("\n接收到错误响应消息[").append(response.toString()).append("]\n");
            LOG.error(sb.toString());

            String serialId = (String) response.get(Message.SERIAL_ID);
            if (StringUtils.isEmpty(serialId)) {
                //do nothing:未知序列号的消息直接丢弃
                return;
            }
        }

        //如果是心跳信息直接丢弃
        String messageType = (String) response.get(Message.TYPE_ID);
        if (StringUtils.equalsIgnoreCase(messageType, Message.HEART_BEAT_TYPE_ID)) {
            //do nothing
        } else {
            //业务消息，放入消息队列
            ResponseHolder.responseReceive(response);
        }
    }

    /**
     * 打印channel
     *
     * @param ch
     */
    private void removeChannelFromPool(Channel ch, String keywords) {
        InetSocketAddress remote = (InetSocketAddress) ch.getRemoteAddress();
        if (remote != null) {
            String host = remote.getAddress().getHostAddress();
            String port = String.valueOf(remote.getPort());

            StringBuilder sb = new StringBuilder();
            sb.append("channel[").append(host).append(":").append(port).append("] ").append(keywords);
            LOG.error(sb.toString());

            ConnectedSocketPool.removeChannel(host, port);
        }
    }

    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        //移除缓存
        removeChannelFromPool(e.getChannel(), "channel disconnnected.");
        super.channelDisconnected(ctx, e);
    }

    @Override
    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        LOG.debug("channel closed.");
        super.channelDisconnected(ctx, e);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        LOG.error("exception occurs:", e.getCause());
        Channel ch = e.getChannel();

        //移除缓存
        removeChannelFromPool(ch, "exception occurs");
        // 关闭channel
        ch.close();
        
        // 记录错误日志
     	SchedulerLogger.addTaskErrorLogDtl("-1", "-1", "-1", "FrontPage", "", "AiScheduler", "监控器异常。", Constants.TaskErrLevel.LEVEL_9,
     					CommonUtils.getExceptionMesssage(e.getCause(), 1000));
    }
}
