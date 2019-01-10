package com.asiainfo.monitor.busi.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.ai.appframe2.web.action.BaseAction;
import com.asiainfo.monitor.busi.cache.interfaces.IServer;
import com.asiainfo.monitor.busi.common.impl.MemcachedStatUtil;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;

public class MemcachedMonitorAction extends BaseAction {

	private static transient Log log = LogFactory.getLog(MemcachedMonitorAction.class);
	
	/**
	 * 
	 * @param appIds
	 * @return
	 * 主机和端口，当前连接数，总查询次数，内存对象字节数，当前对象数量，所有对象数量，设置对象次数，弹出对象次数，uptime，hint查询次数,限制内存字节数
	 * 输出字节数，输入字节数
	 * @throws Exception
	 */
	public List showMemcached(Object[] appIds) throws Exception {
		if (appIds==null || appIds.length<1)
			return null;
		List rsList = new ArrayList();
		try {
			
			IAIMonServerSV serverSV = (IAIMonServerSV) ServiceFactory.getService(IAIMonServerSV.class);
			IServer value = null;
			String str = null;
			for (int i = 0; i < appIds.length; i++) {
				value = serverSV.getServerByServerId(String.valueOf(appIds[i]));
				
				if("MEM".equals(value.getTemp_Type())){
				//10.70.205.104:33333,10.70.205.105:33333,10.70.215.104:33333,10.70.215.105:33333
				str = value.getLocator();
				
				String[] tmp = StringUtils.split(str,",");

				for (int j = 0; j < tmp.length; j++) {
					String[] tmp2 = StringUtils.split(tmp[j], ":");
					String host = tmp2[0].trim();
					String port = tmp2[1].trim();
					HashMap map = MemcachedStatUtil.getStat(host, Integer.parseInt(port));
					/*
					StringBuffer sb=new StringBuffer("");
					sb.append("<data>");
					Set key = map.keySet();
					for (Iterator iter = key.iterator(); iter.hasNext();) {
						String item = (String) iter.next();
						sb.append("<" + item + ">" + map.get(item).toString() + "</"
								+ item + ">");
					}
					sb.append("<stat>" + map.toString() + "</stat>");
					sb.append("</data>");
					log.error("Show Mem:"+sb.toString());
					*/
					String state=map.toString();
					map.put("stat", state);
					rsList.add(map);
				}	
				}
			}

		} catch (Exception e) {
			log.error("MemcachedMonitorAction has error: ", e);
		}
		return rsList;
	}
	
	
	/**
	 * 检查多应用的MemberCache状态
	 * @param appIds
	 * @return
	 * @throws Exception
	 */
	public List checkMemberCacheByAppIds(Object[] appIds) throws Exception{
		if (appIds==null || appIds.length<1)
			return null;
		List result=new ArrayList();
		IServer value = null;
		try{
			IAIMonServerSV showStatSV=(IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
			for (int i = 0; i < appIds.length; i++){
				value=showStatSV.getServerByServerId(String.valueOf(appIds[i]));
				result.add(value);
			}
		}catch(Exception e){
			log.error("Call MemStatAction's Method checkSqlStatByAppIds has Exception :"+e.getMessage());
		}
		return result;
	}
}
