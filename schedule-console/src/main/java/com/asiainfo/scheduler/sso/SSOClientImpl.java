package com.asiainfo.scheduler.sso;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.HttpVersion;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.common.SessionManager;
import com.ai.appframe2.complex.util.RuntimeServerUtil;
import com.ai.appframe2.privilege.UserInfoInterface;
import com.ai.appframe2.service.ServiceFactory;
import com.ai.appframe2.web.BaseServer;
import com.ai.common.ivalues.IBOBsStaticDataValue;
import com.ai.common.util.StaticDataUtil;
import com.ai.common.util.TimeUtil;
import com.ai.secframe.common.bo.BOSecLoginLogBean;
import com.ai.secframe.common.ivalues.IBOSecLoginLogValue;
import com.ai.secframe.common.service.interfaces.ISecLogSV;
import com.ai.secframe.common.service.interfaces.ISecframeFSV;

/**
 * CRM侧SSO客户端实现类
 * 
 * @author wangdong
 *
 */
public class SSOClientImpl extends DefaultPopedomImpl implements IPopedom{

	private static transient Log log = LogFactory.getLog(SSOClientImpl.class);

	private static final String SSO_LOGINED_FLAG = "_SSO_CLIENT_SESSIONID";
	  private static Map Session = new HashMap();

	@Override
	protected boolean doSelfSession(HttpServletRequest request,
			HttpServletResponse response, OperInfo operInfo) {
		boolean isSuccess = false;
		String opid_str = operInfo.getOpId();
		String orgid_str = operInfo.getOporgid();
		try {
			long opId = 0L;
			long orgId = 0L;
			if (StringUtils.isNotBlank(opid_str)
					&& StringUtils.isNotBlank(orgid_str)) {
				opId = Long.parseLong(opid_str);
				orgId = Long.parseLong(orgid_str);
			} else {
				if (log.isErrorEnabled()) {
					log.error("OperInfo传入的opid或orgId为空，不能进行模拟登录！");
				}
				return isSuccess;
			}

			ISecframeFSV secFSV = (ISecframeFSV) ServiceFactory
					.getService(ISecframeFSV.class);
	//		   调用权限接口设置user信息
			UserInfoInterface user = secFSV.setUserInfoByOperIdAndOrgId(opId,
					orgId);
			
			
			//add by xuds3 for ase 20121027
			String strOrigin = request.getParameter("origin");
			String strRequestUrl = request.getRequestURI();
			
			if(StringUtils.isNotEmpty(strOrigin) && strOrigin.equalsIgnoreCase("ESOP"))
			{			
				user.set("MASK_FLAG","0"); //ESOP 不需要模糊化
				
				if (log.isDebugEnabled())
			    {
					log.debug("SSOClientImpl类doSelfSession函数  requestUrl  " + strRequestUrl );
					log.debug("SSOClientImpl类doSelfSession函数  strOrigin   " + strOrigin );
				}
			}
            			
			//end if 
			



			if (user == null) {
				if (log.isErrorEnabled())
					log.error("根据  [opId=" + opId + ",orgId=" + orgId
							+ "] 设置用户信息失败，模拟登录失败！");
				return isSuccess;
			}

		//	   调用Appframe接口设置SessionManager 
			BaseServer.processLogin(request, user);
			Cookie c1 = new Cookie(BaseServer.WBS_USER_ATTR, user.getSerialID());
			c1.setMaxAge(-1);
			response.addCookie(c1);
			String serverName = RuntimeServerUtil.getServerName();
			if (!StringUtils.isBlank(serverName)) {
				Cookie c2 = new Cookie("_BelongedSrvId", serverName);
				c2.setMaxAge(-1);
				response.addCookie(c2);
			}
		//	   cookie中获取sessionID,塞进Session,作为用户SSO模拟登录成功的标识
			String sessionId = PortalDataFetch.getSessionId(request);
			request.getSession().setAttribute(SSO_LOGINED_FLAG, sessionId);
			if (log.isDebugEnabled())
				log.debug("用户 " + SessionManager.getUser().getCode()
						+ " 通过SSO客户端模拟登录成功！");
			
			
			isSuccess = true;
		  	UserInfoInterface userInfo = SessionManager.getUser();
//		  	SSOClientImpl sso = new SSOClientImpl();
		  	long logId=0;	//登陆日志编号
		  	try
			{
		  		logId=setLoginLog(user.getCode(),getRemoteAddress(request),user.getSessionID());
			}
		  	catch (Exception e)
		  	{
		  		log.error("记录日志失败");
		  	}
	  		String lock =getlock()[0].getCodeValue();
		  	if(lock.equalsIgnoreCase("Y")||lock==null){
		  	String App_Sessid = userInfo.getSerialID();
  			String Ip = request.getLocalAddr();
		  	String Port = String.valueOf(request.getLocalPort());
		  	//sReskey是什么，代表什么？
		  	String Reskey = "10";
		  	//请求url地址
		  	String reqUrl=request.getRequestURL().toString();
		  	//如果url过长则进行截取，长度不超过1000
		  	if(reqUrl.length()>999){
		  		reqUrl=reqUrl.substring(0, 999);
		  	}
		  	// -----添加错误日志的记录 add by xinzun 13.07.02 -----//
		  	try {
		  		/*IErrLogSV errLogSV = (IErrLogSV) ServiceFactory.getService(IErrLogSV.class);
				IBOErrLogValue errLogvalue=new BOErrLogBean();
				errLogvalue.setReqUrl(reqUrl);
				errLogvalue.setErrlogText((String)request.getAttribute("errlog"));
				errLogvalue.setLoginDate(new Timestamp( System.currentTimeMillis()));
				errLogvalue.setErrlogId(logId);
				//保存错误日志信息
				errLogSV.saveLog(errLogvalue);*/
		  		//do nothing
			} catch (Exception e) {
				log.error("记录错误日志信息失败");
			}
		  	// ---------------------------------------------------//
		  	
//	  	  	SSOClientImpl impl = new SSOClientImpl();
	  		log.error(request.getRequestURL()+"传给sso的信息 "+" 自己的线程id"+App_Sessid+"地址 "+Ip+" 端口"+Port+" sso的id"+sessionId);
	  		boolean ssoStatusOf4A = DefaultPopedomImpl.get4ASSOStatus(request);
	  		if(ssoStatusOf4A){
	  		    Timestamp time1  = TimeUtil.getPrimaryDataSourceSysDate();
	  		 //   log.error("开始调用该接口getCountyList的时间 "+ time1);
	  		    String callStyle = getWslock()[0].getCodeValue();
	  	  	    String output ="";
	  		    if("HTTP".equals(callStyle)){
	  			    output= setLoginAppInfo( sessionId , Ip, Port, App_Sessid,reqUrl);
	  		    }else{
	  		        output = callWS(Ip, Port, App_Sessid, Reskey,
	  				sessionId, "setLoginAppInfo");
	  		    }
	  		
	  		   Timestamp time2  = TimeUtil.getPrimaryDataSourceSysDate();
			//   log.error("完成调用该接口getCountyList的时间 "+ time2);
			   log.error("调用该接口花费时间"+ (time2.getTime()-time1.getTime()));
	  		   if (output.equals("0")) {
	  		   	  log.error("调用sso接口成功");
	  		   } else {
	  			  log.error("调用sso接口失败");
	  		   }
	  		}else
	  		{
	  		   log.error("容灾情况，不调用4A接口");
	  		}
	  		HttpSession sessions = request.getSession();
	  		Session.put(App_Sessid, sessions);
		  	}

		} catch (Exception e) {
			log.error("通过SSO客户端模拟登录，创建Session失败！", e);
		}

		return isSuccess;
	}

	@Override
	protected boolean isLogin(HttpServletRequest request,
			HttpServletResponse response) {
		boolean flag = false;
		try {
			HttpSession session = request.getSession(false);
			if (session != null) {
				String serialId = (String) session
						.getAttribute(BaseServer.WBS_USER_ATTR);
			//	   CRM登录成功的Session标识
				if (StringUtils.isNotBlank(serialId)) {
			//		   当前两个SSO的状态 
					boolean ssoStatusOfCRM = DefaultPopedomImpl
							.getCRMSSOStatus(request);
					boolean ssoStatusOf4A = DefaultPopedomImpl
							.get4ASSOStatus(request);
			//		   SSO不能提供服务时,使用CRM自己的登录页面登录
					if (!ssoStatusOfCRM && !ssoStatusOf4A) {
						flag = true;
					} else {  // 有一个SSO正常时,即需要进行SSO的标志比对,Cookie中的ID与SessionManager中的userid比对
						String sessionId = (String) request.getSession()
								.getAttribute(SSO_LOGINED_FLAG);
						if (StringUtils.isNotBlank(sessionId)) {
							UserInfoInterface user = null;
							try {
								user = BaseServer.getCurUser(request);
								SessionManager.setUser(user);
							} catch (Exception e) {
								log.error(e);
							}
							//   cookie中的opid与当前线程变量中的用户ID一致,则认为是已登录
							String opId = PortalDataFetch.getOperInfo(request)
									.getOpId();
							if (user != null && StringUtils.isNotBlank(opId)
									&& opId.equals(user.getID() + "")) {
						  		String App_Sessid = user.getSerialID();
						  		if (!Session.containsKey(App_Sessid)) {
					  				HttpSession sessions = request.getSession();
					  				Session.put(App_Sessid, sessions);
					  			}
					  			
								String strOrigin = request.getParameter("origin");
						  		
						  		if(StringUtils.isNotEmpty(strOrigin) && strOrigin.equalsIgnoreCase("ESOP"))
								{			
									try {
										user.set("MASK_FLAG","0");
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} //ESOP 不需要模糊化
									
									if (log.isDebugEnabled())
								    {
										log.debug("SSOClientImpl类isLogin函数  requestUrl  " + request.getRequestURI()  );
										log.debug("SSOClientImpl类isLogin函数  strOrigin   " + user.get("MASK_FLAG") );
									}
								}
						  						  	
								flag = true;
							}else{
								System.err.println("进入isLogin的step4:user为空或opId为空或cookie中的opid与当前线程变量中的用户ID不一致。");
								request.setAttribute("errlog", "进入isLogin的step4:user为空或opId为空或cookie中的opid与当前线程变量中的用户ID不一致。");
							}
						}else{
							System.err.println("进入isLogin的step3:sessionId为空。");
							request.setAttribute("errlog", "进入isLogin的step3:sessionId为空。");
						}
					}
				}else{
					System.err.println("进入isLogin的step2:serialId为空。");
					request.setAttribute("errlog", "进入isLogin的step2:serialId为空。");
				}
			}else{
				System.err.println("进入isLogin的step1:session为空。");
				request.setAttribute("errlog", "进入isLogin的step1:session为空。");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

  	public Map getSession() throws Exception {
  		return Session;
  	}
  	public IBOBsStaticDataValue[] getlock() throws Exception
  	{
  		IBOBsStaticDataValue[] datas =null;
  		datas = StaticDataUtil.getStaticData("SSOLOCK");
  		return datas;
  	}
  	public IBOBsStaticDataValue[] getWslock() throws Exception
  	{
  		IBOBsStaticDataValue[] datas =null;
  		datas = StaticDataUtil.getStaticData("SSO_HTTP_WS");
  		return datas;
  	}
  	public IBOBsStaticDataValue[] getHttpAddr() throws Exception
  	{
  		IBOBsStaticDataValue[] datas =null;
  		datas = StaticDataUtil.getStaticData("SSOHTTPADDR");
  		return datas;
  	}
  	public IBOBsStaticDataValue[] geturl() throws Exception
  	{
  		IBOBsStaticDataValue[] datas =null;
  		datas = StaticDataUtil.getStaticData("CRMSSO");
  		 return datas;
  	}
  	public IBOBsStaticDataValue[] gettime() throws Exception
  	{
  		IBOBsStaticDataValue[] datas =null;
  		datas = StaticDataUtil.getStaticData("SSOTIMES");
  		return datas;
  	}
  	public void delSession(String sessionId) throws Exception {
  		Session.remove(sessionId);
  	}
  	public String callWS(String Ip, String Port, String App_Sessid,
  			String Reskey, String Sso_sessid, String interfaceMethod)
  			throws Exception {
  		//  调用提供的WSDL
  		Service service = new Service();
  		Call call = (Call) service.createCall();
  		SSOClientImpl sso = new SSOClientImpl();
  		String url =sso.geturl()[0].getCodeValue();
  		call.setTargetEndpointAddress(url);
  		String time =sso.gettime()[0].getCodeValue();
  		
  		call.setTimeout(new Integer(time));
	//	  设置调用WEBSERVICE的方法
  		call.setOperationName(new QName("", interfaceMethod));
	//	  调用WEBSERVICE，获取返回的XML串
  		String output = (String) call.invoke(new Object[] {Ip,Port,
  				App_Sessid,Reskey,Sso_sessid});
  		return output;
  	}
  	public long setLoginLog(String userCode,String ipAddr,String sessionId)throws Exception 
  	{
		   //设置登录日志信息
  		String macAddr=getMacAddressIP(ipAddr);
        ISecLogSV loginLog = (ISecLogSV)ServiceFactory.getService(ISecLogSV.class);
        IBOSecLoginLogValue loginValue = new BOSecLoginLogBean();
        loginValue.setStaffCode(userCode);
        loginValue.setIp(ipAddr);
        loginValue.setMac(macAddr);
        loginValue.setLoginDate(new Timestamp( System.currentTimeMillis() ));
        loginValue.setSessionId(sessionId);
        loginValue.setState( 3 );
        long logId=loginLog.saveSecLoginLogAndReturnLogId(loginValue);
        return logId;
  	}
  	 public  String getMacAddressIP(String remotePcIP) {
  		  String str = "";
  		  String macAddress = "";
  		  try {
  		   Process pp = Runtime.getRuntime().exec("nbtstat -A " + remotePcIP);
  		   InputStreamReader ir = new InputStreamReader(pp.getInputStream());
  		   LineNumberReader input = new LineNumberReader(ir);
  		   for (int i = 1; i < 100; i++) {
  		    str = input.readLine();
  		  System.out.println("测试mac地址="+str);
  		    if (str != null) {
  		     if (str.indexOf("MAC Address") > 1) {
  		      macAddress = str.substring(
  		        str.indexOf("MAC Address") + 14, str.length());
  		      break;
  		     }
  		     if (str.indexOf("MAC 地址") > 1) {
  	  		      macAddress = str.substring(
  	  		        str.indexOf("MAC 地址") + 9, str.length());
  	  		      break;
  	  		     }
  		    }
  		   }
  		  } catch (IOException ex) {
  		  }
  		  return macAddress;
  		 }
  	 public String getRemoteAddress(HttpServletRequest request){    
  	     String ip = request.getHeader("x-forwarded-for");    
  	     if(ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) ip = request.getHeader("Proxy-Client-IP");    
  	     if(ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) ip = request.getHeader("WL-Proxy-Client-IP");    
  	     if(ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) ip = request.getRemoteAddr();    
  	     return ip;    
  	 } 
  	 //设置登陆日志信息，新增请求url字段 modify by xinzun
  	public  String setLoginAppInfo(String sessionId ,String app_ip,String app_Port,String appSessId,String reqUrl){
		String rtn = "-1";
		if((app_ip!=null && app_Port!=null && appSessId!=null)||
				(!"".equalsIgnoreCase(app_ip)&& !"".equalsIgnoreCase(app_Port)&&!"".equalsIgnoreCase(appSessId))){
			
			try {
				String home = getHttpAddr()[0].getCodeValue();
				int timeOut = Integer.parseInt(gettime()[0].getCodeValue());
				
				String url = home+"?actionName=applogin&reskey=10&ssoSessid="+sessionId+"&ip="+app_ip+"&port="+app_Port+"&appsid="+appSessId+"&notes1="+URLEncoder.encode(reqUrl,"GBK");
				
				rtn = doHttpPost(url,"",timeOut,null,null);
				//System.out.println(url);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//System.out.println("end");
		}
		return rtn;
	}

  	public static String doHttpPost(String sUrl, String sContent, int timeOut,
  			String userName, String passwd) throws Exception {
  			 
  			if (log.isDebugEnabled()) {
  			//	log.debug("Post requst content time:");
  				log.debug("发送的内容:" + sContent);
  				log.debug("发送数据到: " + sUrl);
  			}
  			if (userName == null)
  			userName = "";
  			if (passwd == null)
  			passwd = "";
  			if (timeOut <= 0)
  			  timeOut = 2000;
  			HttpClient httpclient = new HttpClient();
  			Credentials defaultcreds = new UsernamePasswordCredentials(userName,passwd);
  			httpclient.getState().setCredentials(AuthScope.ANY, defaultcreds);
  			httpclient.getParams().setParameter("http.protocol.version",HttpVersion.HTTP_1_1);
  			httpclient.getParams().setParameter("http.socket.timeout",new Integer(timeOut ));
  			  httpclient.getParams().setParameter("http.protocol.content-charset","GBK");
  			PostMethod httppost = new PostMethod(sUrl);
  			//NameValuePair[] data = { new NameValuePair("$xmldata", sContent) };
  			try {
  			httppost.setRequestBody(sContent);
  			httpclient.executeMethod(httppost);
  			if (httppost.getStatusCode() == HttpStatus.SC_OK) {
  			String msg = httppost.getResponseBodyAsString();
  			// msg = StringUtil.parseHtml2String(msg);
  			
  			return msg;
  			} else {
  			throw new Exception("Unexpected failure: "+ httppost.getStatusLine().toString());
  			}
  			} catch (Exception e) {
  			log.error("Http协议post方法发送字符流时候出现异常：", e);
  			throw new Exception("Http协议post方法发送字符流时候出现异常：", e);
  			} finally {
  			httppost.releaseConnection();
  			}
  			}


}

