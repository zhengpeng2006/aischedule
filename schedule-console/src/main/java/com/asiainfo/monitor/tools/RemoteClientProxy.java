package com.asiainfo.monitor.tools;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.remoting.transporter.TransporterClient;

public final class RemoteClientProxy {

	private static transient Log log=LogFactory.getLog(RemoteClientProxy.class);
	
	private RemoteClientProxy() {
	}

	/**
	 * remote方式获取对象(Socket,SlScoket)
	 * @param locator
	 * @param interfaceClass
	 * @return
	 * @throws Exception
	 */
	public static Object getObject(String locator, Class interfaceClass) throws Exception {
		Object rtn=null;
		try{
            // 目前实现的都是同步接口调用方式，可以试着改成异步调用方式
			rtn = TransporterClient.createTransporterClient(locator,interfaceClass);			
		}catch(Exception e){
			log.error("Call RemoteClientManager'Method getObject has Exception:"+e.getMessage());
			throw new Exception("Call RemoteClientManager'Method getObject has Exception:"+e.getMessage());
		}
		return rtn;
	}

	/**
	 * 销毁对象
	 * 
	 * @param obj
	 *            Object
	 */
	public static void destroyObject(Object obj) {
		TransporterClient.destroyTransporterClient(obj);
	}

	public static void main(String[] args) {
		try{
//			Object obj=ClientProxy.getObject(1,LogMBean.class);
			System.out.println("11");
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
