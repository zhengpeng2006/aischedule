package com.asiainfo.scheduler.sso;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.privilege.UserInfoInterface;
import com.ai.appframe2.privilege.UserManagerFactory;
import com.ai.appframe2.util.MD5;
import com.ai.appframe2.web.CustomProperty;
import com.ai.appframe2.web.HttpUtil;

public class Crm4SSO extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private transient static Log log = LogFactory.getLog(Crm4SSO.class);
	public Crm4SSO()
	{
	}
	 public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	  {
	    doGet(request, response);
	  }
	 public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	  {
		 try {
			this.loginOut(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	public void loginOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String serialId = HttpUtil.getParameter(request, "sessionId");
		String loginname =HttpUtil.getParameter(request, "loginname");
		String pwd =HttpUtil.getParameter(request, "pwd");
		String password =loginname+serialId+"crm";
		MD5 md = new MD5();
		String password2 =md.getMD5ofStr(password);
		log.error("输入值：" +"登陆名称"+ loginname +"  登陆信息CRM线程id"+ serialId
				+ " 密码串 "+pwd);
		CustomProperty cp = CustomProperty.getInstance();
		cp.set("resultString", "8");
		// 8 为初始值
		// 返回结果
		 if(password2.equalsIgnoreCase(pwd))
		 {
		try {
			SSOClientImpl SSO = new SSOClientImpl();
			UserInfoInterface UserInfo = UserManagerFactory.getUserManager().getLogedUsersBySerialID(serialId);
			if (UserInfo == null) {
				SSO.delSession(serialId);
				cp.set("resultString", "2");
				// 该用户登录信息不存在，直接清除map
			} 
			else 
			{
				try {
					ServiceManager.setServiceUserInfo(UserInfo);
					UserManagerFactory.getUserManager().loginOut(UserInfo);
					HttpSession ss = (HttpSession) SSO.getSession().get(
							serialId);
					ss.removeAttribute(serialId);
					ss.invalidate();
					SSO.delSession(serialId);
					cp.set("resultString", "0");
					// 注销成功
				} catch (Exception e) {
					cp.set("resultString", "1");
					// 存在登录信息但注销失败
				}
			}
		} catch (Exception e) {
			log.error("", e);
			cp.set("resultString", "3");
			// 未知错误
		}
		 
		log.error(cp.get("resultString"));
		 HttpUtil.showInfo(response, cp);
	}
	else
	{
		cp.set("resultString", "11");
		log.error(cp.get("resultString"));
		HttpUtil.showInfo(response, cp);
		//11 为非法用户
	}
	}
}
