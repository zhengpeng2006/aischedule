package com.asiainfo.scheduler.sso;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class DefaultPopedomImpl extends BasePopedomImpl
{
  private static transient Log log = LogFactory.getLog(DefaultPopedomImpl.class);
  private static final String _4A_SSO_STATUS = "_4A_SSO_HEALTH_FLAG";
  private static final String _CRM_SSO_STATUS = "_CRM_SSO_HEALTH_FLAG";

  public boolean setFirstPopedom(HttpServletRequest request, HttpServletResponse response, ArrayList arrPathList, String strPortalServerName)
    throws Exception
  {
    if (super.setFirstPopedom(request, response, arrPathList))
    {
      return true;
    }

    return checkSessionPriv(request, response, strPortalServerName);
  }

  private boolean checkSessionPriv(HttpServletRequest request, HttpServletResponse response, String strPortalServerName)
  {
    if (!isLogin(request, response))
    {
      String strSessionId = PortalDataFetch.getSessionId(request);

      if (strSessionId.equals(""))
      {
        if (getCRMSSOStatus(request)) {
          redirectPortalHome(request, response, strPortalServerName);
        }
        else if (get4ASSOStatus(request)) {
          LogUtil.printLog((short)3, "CRM SSO状态不正常，将转向到4A单点登录页面。");
          redirectPortalHome(request, response, PortalFirstFilter.get4ASSOServerName());
        } else {
          LogUtil.printLog((short)3, "CRM SSO和4A SSO状态不正常，将使用CRM自身的登录功能。");
          return true;
        }
        return false;
      }

      String strHostIp = PortalDataFetch.getHostIp(request);
      String strHostPort = PortalDataFetch.getPort(request);

      StringBuffer sb = new StringBuffer("http://");
      sb.append(strHostIp);
      if (!"80".equals(strHostPort)) {
        sb.append(":").append(strHostPort);
      }
      sb.append("/portalcheckout");
      String strCheckOutUrl = sb.toString();

      if (PortalFirstFilter.portalCheckOut(strCheckOutUrl, strSessionId))
      {
        OperInfo operInfo = PortalDataFetch.getOperInfo(request);

        if (doSelfSession(request, response, operInfo)) {
          return true;
        }
        redirectPortalHome(request, response, strPortalServerName);
        return false;
      }

      redirectPortalHome(request, response, strPortalServerName);
      return false;
    }

    return true;
  }

  public static boolean get4ASSOStatus(HttpServletRequest request)
  {
    boolean flag = true;
    Object statusObj = request.getSession().getAttribute("_4A_SSO_HEALTH_FLAG");
    if (statusObj == null) {
      String ssoHome = PortalFirstFilter.get4ASSOServerName();
      if ((null == ssoHome) || ("".equals(ssoHome)))
        flag = false;
      else {
        flag = getSSOStatus(ssoHome);
      }
      request.getSession().setAttribute("_4A_SSO_HEALTH_FLAG", Boolean.valueOf(flag));
    } else {
      flag = ((Boolean)request.getSession().getAttribute("_4A_SSO_HEALTH_FLAG")).booleanValue();
    }
    return flag;
  }

  public static boolean getCRMSSOStatus(HttpServletRequest request)
  {
    boolean flag = true;
    Object statusObj = request.getSession().getAttribute("_CRM_SSO_HEALTH_FLAG");
    if (statusObj == null) {
      String ssoHome = PortalFirstFilter.getCRMSSOServerName();
      if ((null == ssoHome) || ("".equals(ssoHome)))
        flag = false;
      else {
        flag = getSSOStatus(ssoHome);
      }
      request.getSession().setAttribute("_CRM_SSO_HEALTH_FLAG", Boolean.valueOf(flag));
    } else {
      flag = ((Boolean)request.getSession().getAttribute("_CRM_SSO_HEALTH_FLAG")).booleanValue();
    }
    return flag;
  }

  public static boolean getSSOStatus(String ssoHome)
  {
    boolean flag = false;
    String checkUrl = "";
    if ((ssoHome != null) && (!"".equals(ssoHome))) {
      if (ssoHome.endsWith("/"))
        checkUrl = ssoHome + "CheckSSOStatus?action=getStatus";
      else {
        checkUrl = ssoHome + "/CheckSSOStatus?action=getStatus";
      }
      String aa = HttpPost.doHttpPost(checkUrl, "");
      if ((aa != null) && ("true".equals(aa))) {
        flag = true;
      }
    }
    return flag;
  }

  protected void redirectPortalHome(HttpServletRequest request, HttpServletResponse response, String strPortalServerName)
  {
    try
    {
      HttpPost.sendRedirectUrl(request, response, strPortalServerName);
    } catch (IOException ex) {
      log.error("重定向到统一门户登录界面出错！", ex);
    }
  }

  protected abstract boolean isLogin(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse);

  protected abstract boolean doSelfSession(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, OperInfo paramOperInfo);
}