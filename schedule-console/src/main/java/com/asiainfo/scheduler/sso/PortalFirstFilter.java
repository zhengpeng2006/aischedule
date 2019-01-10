package com.asiainfo.scheduler.sso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PortalFirstFilter
  implements Filter
{
  private ArrayList arrPathList = new ArrayList();
  private String strImplClassName = "";
  public static String isLog = "false";

  private static String crmSSOServerName = "";

  private static String ssoServerNameOf4A = "";
  public static final String actionName = "activeSession";
  private IPopedom ipopedom = null;

  public void init(FilterConfig filterConfig)
    throws ServletException
  {
    crmSSOServerName = filterConfig.getInitParameter("portal-servername");
    this.strImplClassName = filterConfig.getInitParameter("impl-classname");
    isLog = filterConfig.getInitParameter("ISLOG");
    ssoServerNameOf4A = filterConfig.getInitParameter("4asso-servername");

    if (isLog == null) {
      isLog = "false";
    }
    String allPath = filterConfig.getInitParameter("ALLOWPATH");
    if (allPath == null) {
      allPath = "";
    }
    String[] tempArr = split(allPath, ";");
    for (int i = 0; i < tempArr.length; i++) {
      this.arrPathList.add(tempArr[i]);
    }

    try
    {
      Class cls = Class.forName(this.strImplClassName);
      this.ipopedom = ((IPopedom)cls.newInstance());
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
  }

  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
    throws IOException, ServletException
  {
    HttpServletRequest httprequest = (HttpServletRequest)servletRequest;
    HttpServletResponse httpresponse = (HttpServletResponse)servletResponse;
    try
    {
      boolean isPass = this.ipopedom.setFirstPopedom(httprequest, httpresponse, this.arrPathList, crmSSOServerName);

      if (isPass) {
        HttpSession session = httprequest.getSession(false);
        if ((session != null) && (PortalDataFetch.getSessionId(httprequest) != null) && (PortalDataFetch.getSessionId(httprequest).length() > 0) && (httprequest.getMethod().equalsIgnoreCase("GET")))
        {
          long lastAccessedTime = PortalDataFetch.getLastAccessedTime(httprequest);
          if ((lastAccessedTime > 0L) && (System.currentTimeMillis() - lastAccessedTime > 3000000L))
          {
            String sessionActiveUrl = PortalDataFetch.getActiveUrl(httprequest) + "/portalcheckout?action=" + "activeSession";

            HttpPost.sendRedirectUrl(httprequest, httpresponse, sessionActiveUrl);
          }
        }
        filterChain.doFilter(servletRequest, servletResponse);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static boolean portalCheckOut(String strCheckOutUrl, String strSessionId)
  {
    boolean flag = false;
    String returnVal = HttpPost.doHttpPost(strCheckOutUrl, strSessionId);
    if (returnVal.equalsIgnoreCase("success")) {
      flag = true;
    }
    return flag;
  }

  public static String[] split(String str, String x)
  {
    if (str == null) {
      return null;
    }
    if (x == null) {
      return null;
    }
    Vector v = new Vector();
    StringTokenizer stToken = new StringTokenizer(str, x);
    int iIndex = 0;
    while (stToken.hasMoreTokens()) {
      String strToken = stToken.nextToken();
      v.add(iIndex++, strToken);
    }
    String[] seqResult = new String[v.size()];
    for (int i = 0; i < v.size(); i++) {
      String strTemp = (String)v.get(i);
      seqResult[i] = strTemp;
    }
    return seqResult;
  }

  public void destroy()
  {
    this.arrPathList = new ArrayList();
    this.ipopedom = null;
  }

  public static String getCRMSSOServerName() {
    return crmSSOServerName;
  }

  public static String get4ASSOServerName() {
    return ssoServerNameOf4A;
  }
}