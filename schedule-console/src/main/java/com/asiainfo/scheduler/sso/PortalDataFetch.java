package com.asiainfo.scheduler.sso;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.misc.BASE64Decoder;

public class PortalDataFetch
{
  public static String getSessionId(HttpServletRequest httprequest)
  {
    String strSessionId = "";
    Cookie[] cookies = httprequest.getCookies();
    if ((cookies != null) && (cookies.length > 0))
    {
      for (int i = 0; i < cookies.length; i++)
      {
        if (cookies[i].getName().equals("AIPortal_SessionId"))
        {
          strSessionId = cookies[i].getValue();
          break;
        }
      }
    }
    return strSessionId;
  }

  public static String getCaSign(HttpServletRequest httprequest)
  {
    String strCaSign = "";
    Cookie[] cookies = httprequest.getCookies();
    if ((cookies != null) && (cookies.length > 0))
    {
      for (int i = 0; i < cookies.length; i++)
      {
        if (cookies[i].getName().equals("AIPortal_CASign"))
        {
          strCaSign = useBASE64Decoder(cookies[i].getValue());
          break;
        }
      }
    }
    return strCaSign;
  }

  public static String getHostIp(HttpServletRequest httprequest)
  {
    String strHostIp = "";
    Cookie[] cookies = httprequest.getCookies();
    if ((cookies != null) && (cookies.length > 0))
    {
      for (int i = 0; i < cookies.length; i++)
      {
        if (cookies[i].getName().equals("AIPortal_HostIp"))
        {
          System.out.println(".....cookies[i].getValue()="+cookies[i].getValue());
          strHostIp = useBASE64Decoder(cookies[i].getValue());
          break;
        }
      }
    }
    return strHostIp;
  }

  public static String getPort(HttpServletRequest httprequest)
  {
    String strHostPort = "";
    Cookie[] cookies = httprequest.getCookies();
    if ((cookies != null) && (cookies.length > 0))
    {
      for (int i = 0; i < cookies.length; i++)
      {
        if (cookies[i].getName().equals("AIPortal_Port"))
        {
          System.out.println(".....cookies[i].getValue()="+cookies[i].getValue());
          strHostPort = useBASE64Decoder(cookies[i].getValue());
          break;
        }
      }
    }
    return strHostPort;
  }

  public static long getLastAccessedTime(HttpServletRequest request)
  {
    Cookie[] cookies = request.getCookies();

    if (cookies != null)
    {
      for (int index = 0; index < cookies.length; index++)
      {
        if (cookies[index].getName().equals("AIPortal_Oper_LastAccessedTime"))
        {
          return Long.parseLong(useBASE64Decoder(cookies[index].getValue()));
        }
      }
    }
    return 0L;
  }

  public static String getActiveUrl(HttpServletRequest request)
  {
    Cookie[] cookies = request.getCookies();

    if (cookies != null)
    {
      for (int index = 0; index < cookies.length; index++)
      {
        if (cookies[index].getName().equals("AIPortal_Oper_ActiveUrl"))
        {
          return useBASE64Decoder(cookies[index].getValue());
        }
      }
    }
    return "";
  }

  public static OperInfo getOperInfo(HttpServletRequest httprequest)
  {
    OperInfo operInfo = new OperInfo();
    Cookie[] cookies = httprequest.getCookies();
    for (int i = 0; i < cookies.length; i++)
    {
      if (cookies[i].getName().equals("AIPortal_Oper_OpId"))
      {
        operInfo.setOpId(useBASE64Decoder(cookies[i].getValue()));
      }
      else if (cookies[i].getName().equals("AIPortal_Oper_OrgId"))
      {
        operInfo.setOporgid(useBASE64Decoder(cookies[i].getValue()));
      }
      else if (cookies[i].getName().equals("AIPortal_Oper_OpName"))
      {
        operInfo.setOpname(useBASE64Decoder(cookies[i].getValue()));
      }
      else if (cookies[i].getName().equals("AIPortal_Oper_LogName"))
      {
        operInfo.setOplogname(useBASE64Decoder(cookies[i].getValue()));
      }
      else if (cookies[i].getName().equals("AIPortal_Oper_AdmFlag"))
      {
        operInfo.setOpadmflag(useBASE64Decoder(cookies[i].getValue()));
      }
      else if (cookies[i].getName().equals("AIPortal_Oper_EntId"))
      {
        operInfo.setOpentid(useBASE64Decoder(cookies[i].getValue()));
      }
      else if (cookies[i].getName().equals("AIPortal_Oper_DeptId"))
      {
        operInfo.setOpdeptid(useBASE64Decoder(cookies[i].getValue()));
      }
      else if (cookies[i].getName().equals("AIPortal_Oper_RegionCode"))
      {
        operInfo.setRegioncode(useBASE64Decoder(cookies[i].getValue()));
      }
      else if (cookies[i].getName().equals("AIPortal_Oper_FirstType"))
      {
        operInfo.setFirstorgtype(useBASE64Decoder(cookies[i].getValue()));
      }
      else if (cookies[i].getName().equals("AIPortal_Oper_SecondType"))
      {
        operInfo.setSecorgtype(useBASE64Decoder(cookies[i].getValue()));
      }
      else if (cookies[i].getName().equals("AIPortal_Oper_ThirdType"))
      {
        operInfo.setThirdorgtype(useBASE64Decoder(cookies[i].getValue()));
      }
      else if (cookies[i].getName().equals("AIPortal_SessionId"))
      {
        operInfo.setTokenId(cookies[i].getValue());
      }
    }

    return operInfo;
  }

  public static String getPortAddress(HttpServletRequest httprequest)
  {
    String strHostIp = getHostIp(httprequest);
    String strHostPort = getPort(httprequest);
    String strPortAddress = "http://" + strHostIp + ":" + strHostPort;
    return strPortAddress;
  }

  public static String useBASE64Decoder(String strTemp)
  {
    byte[] bytes = null;
    try
    {
      bytes = new BASE64Decoder().decodeBuffer(strTemp);
    }
    catch (IOException ex)
    {
      ex.printStackTrace();
    }
    String strReturn = new String(bytes);

    return strReturn;
  }

  public static void setAccessErrorInfo(ServletResponse response, int id, String strMessage)
  {
    try
    {
      HttpServletResponse res = (HttpServletResponse)response;
      res.setContentType("text/html;charset=GBK");
      PrintWriter out = res.getWriter();
      String title = "";
      String head = "";
      String text = "";

      switch (id)
      {
      case 601:
        title = "没有分配IP地址";
        head = "没有分配IP地址";
        text = "您的IP没有经管理员分配，请和管理员联系！";
        break;
      case 602:
        title = "没有权限访问";
        head = "没有权限访问";
        text = "请确定您的身份是否有权限访问该功能！<br><br>如果有疑问，请和系统管理员联系！";
        break;
      case 660:
        title = "权限验证出错";
        head = "权限验证出错";
        text = strMessage;
        break;
      default:
        title = "未知错误";
        head = "未知错误";
        text = "未知错误<br><br>如果有疑问，请和系统管理员联系！";
      }

      out.println("<html><head><title>" + title + "</title></head><body>");
      out.println("<h1>" + id + " " + head + "</h1>");
      out.println("<hr>");
      out.println("<p>" + text + "</p>");
      out.println("</body></html>");
      out.close();
    }
    catch (IOException ix)
    {
      ix.printStackTrace();
    }
  }
}