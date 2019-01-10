package com.asiainfo.scheduler.sso;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.misc.BASE64Encoder;

public class CookieUtil
{
  public static void addOrgCookie(HttpServletRequest request, HttpServletResponse response, String orgId)
  {
    Cookie orgCookie = newCookie(request, "AIPortal_Oper_OrgId", orgId, true);
    response.addCookie(orgCookie);
  }

  public static void addRegionCookie(HttpServletRequest request, HttpServletResponse response, String regionCode)
  {
    Cookie regionCookie = newCookie(request, "AIPortal_Oper_RegionCode", regionCode, true);
    response.addCookie(regionCookie);
  }

  private static Cookie newCookie(HttpServletRequest request, String name, String value, boolean isEncode)
  {
    if (value == null) {
      value = "";
    }
    if (isEncode)
      value = new BASE64Encoder().encode(value.getBytes());
    Cookie tempCookie = new Cookie(name, value);
    tempCookie.setDomain("zj.chinamobile.com");
    tempCookie.setPath("/");
    tempCookie.setMaxAge(-1);
    return tempCookie;
  }
}