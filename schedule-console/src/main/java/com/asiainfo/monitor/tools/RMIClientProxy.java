package com.asiainfo.monitor.tools;

import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;


public class RMIClientProxy {


	private static transient Log log = LogFactory.getLog(RMIClientProxy.class);
	
	/**
	 * 返回MBean代理对象
	 * @param rmi
	 * @param mBeanNam
	 * @param intefaceClazz
	 * @return
	 * @throws Exception
	 */
	public static Object getObject(String rmi,String mBeanName,Class interfaceClazz) throws Exception{
		Object rtn=null;
		try{
			ObjectName objectName = new ObjectName(mBeanName);
			//"service:jmx:rmi://localhost/jndi/rmi://" + InetAddress.getLocalHost().getHostAddress() + ":" + port + "/jmxconnector";
			JMXServiceURL jmxUrl=new JMXServiceURL(rmi);
//			启用安全		    
//		    Map environment = new HashMap();   
//		    environment.put(JMXConnector.CREDENTIALS, credentials);
			JMXConnector jmxConn=JMXConnectorFactory.connect(jmxUrl,null);
			MBeanServerConnection mbsConn = jmxConn.getMBeanServerConnection();
			rtn=MBeanServerInvocationHandler.newProxyInstance(mbsConn,objectName,interfaceClazz,true);
		}catch(NoClassDefFoundError ex){
			// 没有从目标[{0}]找到对象:
			log.error(AIMonLocaleFactory.getResource("MOS0000272", rmi)+interfaceClazz);
		}catch(Exception e){
			log.error("Call RMIClientProxy's Method getObject has Exception:"+e.getMessage());
			throw new Exception("Call RMIClientProxy's Method getObject has Exception:"+e.getMessage());
		}		
		return rtn;
	}
	

	public static void main(String[] args) {
		try{
//			RMIClientProxy.getObject("service:jmx:rmi://localhost/jndi/rmi://localhost:2162/appframe_server","Appframe:name=LogMBean");
		}catch(Exception e){
			
		}
	}
}
