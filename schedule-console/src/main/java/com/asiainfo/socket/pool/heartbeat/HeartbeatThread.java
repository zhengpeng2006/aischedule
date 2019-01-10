package com.asiainfo.socket.pool.heartbeat;

import com.asiainfo.socket.pool.ConnectedSocketPool;
import com.asiainfo.socket.pool.HostManager;
import com.asiainfo.socket.pool.MessageHandler;
import com.asiainfo.socket.pool.ResponseDecoder;
import com.asiainfo.socket.pool.SocketConfig;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.Executors;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.timeout.ReadTimeoutHandler;
import org.jboss.netty.util.HashedWheelTimer;
import org.jboss.netty.util.Timer;

import com.asiainfo.socket.pool.SocketConstants.Message;
import com.asiainfo.socket.sequence.SequenceGenerator;
import com.asiainfo.socket.util.PackageUtils;

/**
 * 心跳检测
 * 
 * @author 孙德东(24204)
 */
public class HeartbeatThread extends Thread{
	private static ClientBootstrap bootstrap = null;
	private static ChannelFactory factory = null;
	private static Timer timer = null;
	
	private static transient Log LOG = LogFactory.getLog(HeartbeatThread.class);
	private volatile boolean stopped = false;

	public HeartbeatThread() {
		super();
		setName("monitor-heartbeat-thread");
        //boss worker连接池
		factory = new NioClientSocketChannelFactory(Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool());

	    bootstrap = new ClientBootstrap(factory);
	    timer = new HashedWheelTimer();	
		bootstrap.setPipelineFactory(new CustomChannelPipelineFactory(timer));
		bootstrap.setOption("tcpNoDelay", true);
		bootstrap.setOption("keepAlive", true);
        bootstrap.setOption("connectTimeoutMillis", SocketConfig.getConnectTimeout());
	}

	@Override
	public void run() {
		
		while(!stopped) {
			//获取所有需要检测的主机（从缓存中获取）
			Set<String> hosts = null;
			try {
				  hosts = HostManager.fetchAllPeers();
				  //hosts = HostManager.fetchAllPeersTest();
			} catch (Exception e1) {
				LOG.error("获取需要心跳检测的主机异常。");
			}
			
			if (hosts != null) {
				for (String host : hosts) {
					singlePeerHeartbeat(host);
				}
			}
			//默认10秒进行一次心跳请求
			try {
				Thread.sleep(SocketConfig.getHeartbeatInternal());
			} catch (InterruptedException e) {
				LOG.error("sleep exception.", e);
			}
		}
	}
	
	/**
	 * 单节点心跳检测
	 * @param host
	 */
	private void singlePeerHeartbeat(String host) {
		String[] infos = HostManager.split(host);
		
		if (infos == null || infos.length != 2) {
			LOG.error("不合法的主机信息:" + host);
			return;
		}
		//获取缓存的Channel
		Channel ch = ConnectedSocketPool.getChannel(infos[0], infos[1]);
		if (ch != null) {
			try {
				HeartbeatRequest.sendRequest(ch);
			} catch (UnsupportedEncodingException e) {
				LOG.error("construct heart beat request exception.", e);
			}
		}
		else {
			bootstrap.connect(new InetSocketAddress(infos[0], Integer.valueOf(infos[1])));
		}
	}
	
	/**
	 * 关闭线程，释放资源
	 */
	public void releaseChannels() {
		//停止线程
		stopped = true;
		
		//关闭连接，释放资源
		Collection<Channel> channels = ConnectedSocketPool.getAllChannels();
		for (Channel ch : channels) {
			ch.close().awaitUninterruptibly(1000);
		}
		factory.releaseExternalResources();
		
		//The Timer which was specified when the ReadTimeoutHandler is created should be stopped manually 
		//by calling releaseExternalResources() or Timer.stop() when your application shuts down.
		timer.stop();
	}
	
	/**
	 * 心跳请求发送类
	 * 
	 * @author 孙德东(24204)
	 */
	private static class HeartbeatRequest {
		private HeartbeatRequest(){}
		/**
		 * 构造心跳请求
		 * @return
		 */
		public static byte[] constructRequest() {
			JSONObject msgObj = new JSONObject();
	        //请求唯一标识
	        msgObj.put(Message.SERIAL_ID, String.valueOf(SequenceGenerator.next()));
	        //请求类型
	        msgObj.put(Message.TYPE_ID, Message.HEART_BEAT_TYPE_ID);
			
	        //封包
	        byte[] req = new byte[0]; 
	        try {
				req = PackageUtils.dataPackageCompose(msgObj.toString());
			} catch (Exception e) {
				LOG.error("构造心跳请求失败.");
			}
	        return req;
		}
		
		public static void sendRequest(Channel ch) throws UnsupportedEncodingException {
			byte[] b = constructRequest();
			ChannelBuffer buf = ChannelBuffers.buffer(b.length);
			buf.writeBytes(b);
			ch.write(buf);
		}
	}
	
	/**
	 * 带有读超时控制handler的ChannelPipelineFactory
	 * 
	 * @author 孙德东(24204)
	 */
	private static class CustomChannelPipelineFactory implements ChannelPipelineFactory {
		private final ChannelHandler timeoutHandler;

		public CustomChannelPipelineFactory(Timer timer) {
			this.timeoutHandler = new ReadTimeoutHandler(timer, SocketConfig.getReadTimeout());
		}
		@Override
		public ChannelPipeline getPipeline() throws Exception {
			return Channels.pipeline(timeoutHandler, new ResponseDecoder(), new MessageHandler());
		}

	}
}
