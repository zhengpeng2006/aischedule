package com.asiainfo.monitor.interapi.api;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;

import com.ai.appframe2.complex.mbean.standard.jvm5.JVM5MonitorMBean;
import com.ai.appframe2.util.XmlUtil;
import com.asiainfo.monitor.interapi.config.AIMemoryInfo;
import com.asiainfo.monitor.interapi.config.AIThreadInfo;
import com.asiainfo.monitor.tools.TransporterClientFactory;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class JVM5Api {
	private static transient Log log = LogFactory.getLog(JVM5Api.class);
	
	private static String S_MBEAN_NAME="appframe:name=JVM5Monitor";
	/**
	 * 远程方法调用查询主机内存
	 * @param rmiURL
	 * @return
	 */
	public static AIMemoryInfo getMemoryInfo(String type,String locator) throws Exception{
		AIMemoryInfo result = null;
		try {
			Object proxyObject = TransporterClientFactory.getProxyObject(type,locator,S_MBEAN_NAME,JVM5MonitorMBean.class);
			if (proxyObject != null){
				String memStr = null;
				try{
					memStr = ((JVM5MonitorMBean)proxyObject).getMemoryInfo();
				}catch(NoClassDefFoundError ex){
					throw new Exception(AIMonLocaleFactory.getResource("MOS0000273", locator) + "JVM5MonitorMBean");
				}
				if (StringUtils.isNotBlank(memStr)){
					Element root = XmlUtil.parseXmlOfString(memStr);
					String total = root.elementText("t");
					String use = root.elementText("u");
					result = new AIMemoryInfo();
					result.setTotal(Long.parseLong(total));
					result.setUsed(Long.parseLong(use));
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String time = dateFormat.format(new Date());
					result.setTime(time);
				}
			}
		} catch (Exception e) {
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000327"));
		}
		return result;
	}

	/**
	 * 远程方法调用查询线程信息
	 * @param rmiURL
	 * @return
	 */
	public static AIThreadInfo[] getThreadInfo(String type,String locator) throws Exception{
		AIThreadInfo[] result=null;
		try {
			Object proxyObject = TransporterClientFactory.getProxyObject(type,locator,S_MBEAN_NAME,JVM5MonitorMBean.class);
			if (proxyObject!=null){
				String threadStr=null;
				try{
					threadStr=((JVM5MonitorMBean)proxyObject).getAllThreadInfo();
				}catch(NoClassDefFoundError ex){
					throw new Exception(AIMonLocaleFactory.getResource("MOS0000273", locator) + "JVM5MonitorMBean");
				}
				if (StringUtils.isNotBlank(threadStr)){
					StringBuffer sb=new StringBuffer("");
					sb.append("<root>");
					sb.append(threadStr);
					sb.append("</root>");
					InputStream in=new ByteArrayInputStream(sb.toString().getBytes());
					Element root=XmlUtil.parseXmlGBK(in);
					List l=root.elements("data");
					if (l!=null && l.size()>0){
						result=new AIThreadInfo[l.size()];
						for (int i=0;i<l.size();i++){
							Element item=(Element)l.get(i);
							String id=item.elementText("id");
							String name=item.elementText("name");
							String stack=item.elementText("stack");
							String state=item.elementText("state");
							result[i]=new AIThreadInfo(Long.parseLong(id),name,state,stack);
						}
						
					}
					
				}
			}
			
		} catch (Exception e) {
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000327")+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 查询线程数量
	 * @param rmiURL
	 * @return
	 */
	public static int getThreadCount(String type,String locator) throws Exception{
		AIThreadInfo[] tInfo=null;
		try{
			tInfo = getThreadInfo(type,locator);
			
		}catch(Exception e){
			throw new Exception("Call JVM5Api'Method getThreadCount has Exception:"+e.getMessage());
		}
		return tInfo != null ? tInfo.length : -1;
	}
	
}
