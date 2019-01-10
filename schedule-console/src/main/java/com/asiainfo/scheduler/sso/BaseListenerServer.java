package com.asiainfo.scheduler.sso;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.ai.appframe2.complex.util.RuntimeServerUtil;
import com.ai.appframe2.service.ServiceFactory;
import com.ai.appframe2.web.BaseServer;
import com.ai.secframe.common.bo.BOSecFunctionStatBean;
import com.ai.secframe.common.bo.BOSecFunctionStatDtlBean;
import com.ai.secframe.common.service.interfaces.ISecLogSV;
import com.asiainfo.appframe.ext.flyingserver.org.apache.commons.logging.Log;
import com.asiainfo.appframe.ext.flyingserver.org.apache.commons.logging.LogFactory;

public class BaseListenerServer extends HttpServlet implements HttpSessionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static transient Log log = LogFactory.getLog(BaseListenerServer.class);

	  public void sessionCreated(HttpSessionEvent se)
	  {
		  
	  }

	  public void sessionDestroyed(HttpSessionEvent se)
	  {
		
	    String sessionID = (String)se.getSession().getId();
	    log.error("sessionId = "+sessionID);
	    String serialId =(String) se.getSession().getAttribute(BaseServer.WBS_USER_ATTR);
	    log.error("线程id =  "+serialId);
	    try
	    {
	    	  this.saveFunctionStatistics(se.getSession());
	    	  
		      if(serialId!=null)
		      {
		    	  SSOClientImpl SSO = new SSOClientImpl();
		    	  SSO.delSession(serialId);
		      }
		      
	    }
	    catch (Exception ex)
	    {
	      String msg = null;
	      log.error(ex);
	      throw new RuntimeException(msg, ex);
	    }
	  }
	  
		 /**
		  * 将菜单点击数据存储到数据库中
		  * @param se
		  */
		 private void saveFunctionStatistics(HttpSession session) {
				
			 if( session == null){
				 return;
			 }
			 
			 try
			 {
				ConcurrentHashMap functionStatMap = (ConcurrentHashMap)session.getAttribute("KEY_FUNCTION_STAT");
				ArrayList functionStatDtlList 	= (ArrayList)		 session.getAttribute("KEY_FUNCTION_STAT_DTL");
				
				ISecLogSV logSv = (ISecLogSV) ServiceFactory.getService(ISecLogSV.class);

				if( functionStatMap != null ){
					Iterator iter = functionStatMap.keySet().iterator();
					
					List functionStatList = new ArrayList();
					
					while(iter.hasNext()){
						String key = (String)iter.next();
						AtomicLong al = (AtomicLong)functionStatMap.get(key);
						
						String[] keys = key.split("\\|");
						
						BOSecFunctionStatBean statBean = new BOSecFunctionStatBean();
					  
						statBean.setFunctionId(Long.parseLong(keys[0]));
						statBean.setOpCount(al.longValue());
						statBean.setServerName(RuntimeServerUtil.getServerName());
						statBean.setOpId(Long.parseLong(keys[1]));
						statBean.setOrgId(Long.parseLong(keys[2]));
						statBean.setSessionId(session.getId());
						statBean.setCreateDate(new Timestamp(System.currentTimeMillis()));
						
						functionStatList.add(statBean);
						
					}
									
					if( functionStatList != null && functionStatList.size() > 0 ){
						logSv.saveSecFunctionStat((BOSecFunctionStatBean[]) functionStatList.toArray(new BOSecFunctionStatBean[0]));
					}
				
				}
				
				if( functionStatDtlList != null && functionStatDtlList.size() > 0 ){
					logSv.saveSecFunctionStatDtl((BOSecFunctionStatDtlBean[]) functionStatDtlList.toArray(new BOSecFunctionStatDtlBean[0]));
				}
				
				session.setAttribute("KEY_FUNCTION_STAT", null);
				session.setAttribute("KEY_FUNCTION_STAT_DTL", null);
				
			 }
			  catch(Exception e)
			  {
					log.error("菜单点击量统计异常", e);
			  }
			  finally
			  {

			  }
			  
		}
		 
}
