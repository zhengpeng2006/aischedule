package com.asiainfo.monitor.busi.exe.task.javacommand;

import java.net.InetSocketAddress;
import java.net.Socket;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.monitor.tools.common.KeyName;
import com.asiainfo.monitor.tools.common.interfaces.IJavaCommand;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

/**
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: AI(NanJing)</p>
 *
 * @author Yang Hua
 * @version 1.0
 * 后台定时任务,测试主机端口
 */
public class TelnetPort implements IJavaCommand {
	private transient static Log log = LogFactory.getLog(TelnetPort.class);

	public TelnetPort() {
	}

	/**
	 * com.asiainfo.appframe.ext.monitor.exe.task.javacommand.TelnetPort;TELIP:20.21.6.117|TELPORT:8080
	 * @param KeyName[] TELIP:***.**.***.**,TELPORT:****
	 * @return String
	 * @throws Exception
	 */
	public String execute(KeyName[] parameter) throws Exception {
		// 测试端口
		String rtn = "NAME:" + AIMonLocaleFactory.getResource("MOS0000306") + "|CONNECT:0";
		Socket socket = null;
		String telip="",telport="";
		try {
			
			for (int i=0;i<parameter.length;i++){
				if (parameter[i].getName().equals("TELIP")){
					telip=parameter[i].getKey();
				}else if (parameter[i].getName().equals("TELPORT")){
					telport=parameter[i].getKey();
				}
				if (StringUtils.isNotBlank(telip) && StringUtils.isNotBlank(telport)){
					break;
				}
			}
			if (StringUtils.isBlank(telip)){
				telip=parameter[0].getKey();
				telport=parameter[1].getKey();
			}
			
			socket = new Socket();
			socket.connect(new InetSocketAddress(telip,Integer.parseInt(telport)), 2000);
			rtn = "NAME:" + AIMonLocaleFactory.getResource("MOS0000306") + "|CONNECT:1";
		} catch (Throwable ex) {
			rtn = "NAME:" + AIMonLocaleFactory.getResource("MOS0000306") + "|CONNECT:0";
			// "连接:" + telip + ":" + telport + "失败"
			log.error(AIMonLocaleFactory.getResource("MOS0000169", telip, telport), ex);
		} finally {
			if (socket != null) {
				socket.close();
			}
		}
		return rtn;
	}

	public static void main(String[] args) throws Exception {
		TelnetPort a = new TelnetPort();
		//    System.out.println(a.execute("10.96.19.70:22"));
	}
}
