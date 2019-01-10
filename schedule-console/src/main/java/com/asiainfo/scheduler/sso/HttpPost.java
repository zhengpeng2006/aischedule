package com.asiainfo.scheduler.sso;


import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpPost
{
  public static String doHttpPost(String strUrl, String sContent)
  {
    String strReturnVal = "";

    HttpURLConnection httpURLConnection = null;
    Calendar calBegin;
    try
    {
      calBegin = Calendar.getInstance();

      URL url = new URL(strUrl);
      URLConnection urlcn = url.openConnection();
      httpURLConnection = (HttpURLConnection)urlcn;
      httpURLConnection.addRequestProperty("Content-Type", "text/html");
      httpURLConnection.setRequestMethod("POST");
      httpURLConnection.setConnectTimeout(3000);
      httpURLConnection.setReadTimeout(3000);

      httpURLConnection.setDoOutput(true);
      httpURLConnection.setDoInput(true);
      httpURLConnection.connect();

      OutputStreamWriter osw = new OutputStreamWriter(httpURLConnection.getOutputStream(), "GBK");
      osw.write(sContent);
      osw.close();
      LogUtil.printLog((short)0, "数据发送成功……");
    }
    catch (Exception e)
    {
      e.printStackTrace();

      return strReturnVal;
    }

    try
    {
      LogUtil.printLog((short)1, "开始接收应答……");

      InputStreamReader isr = new InputStreamReader(httpURLConnection.getInputStream(), "GBK");
      while (true)
      {
        char[] c = new char[10];
        int ilen = isr.read(c);

        if (ilen <= 0)
        {
          break;
        }

        String sTemp = new String(c, 0, ilen);
        strReturnVal = strReturnVal.concat(sTemp);
      }

      isr.close();
      LogUtil.printLog((short)0, strReturnVal);
      Calendar calEnd = Calendar.getInstance();
      LogUtil.printLog((short)1, "传送开始时间：" + calBegin.getTime() + " 接收结束时间：" + calEnd.getTime() + " 花费时间：" + getBetweenTime(calBegin, calEnd) + "ms");
    }
    catch (Exception e)
    {
      e.printStackTrace();
      LogUtil.printLog((short)3, e.getMessage());

      return strReturnVal;
    }

    return strReturnVal;
  }

  public static String getRequestContent(HttpServletRequest request)
    throws IOException
  {
    String strReturnVal = "";
    request.getParameterValues("");
    InputStreamReader isr = new InputStreamReader(request.getInputStream(), "GBK");

    char[] chars = new char[100];
    while (true)
    {
      int ilen = isr.read(chars);

      if (ilen <= 0)
      {
        break;
      }

      String sTemp = new String(chars, 0, ilen);
      strReturnVal = strReturnVal.concat(sTemp);
    }

    isr.close();

    return strReturnVal;
  }

  public static void postContent(String strUrl, String sContent)
  {
    HttpURLConnection httpURLConnection = null;
    try
    {
      URL url = new URL(strUrl);
      URLConnection urlcn = url.openConnection();
      httpURLConnection = (HttpURLConnection)urlcn;
      httpURLConnection.setRequestMethod("POST");
      httpURLConnection.setDoOutput(true);
      httpURLConnection.setDoInput(true);

      OutputStreamWriter osw = new OutputStreamWriter(httpURLConnection.getOutputStream(), "GBK");

      osw.write(sContent);
      osw.close();
      LogUtil.printLog((short)0, "数据发送成功……");
    }
    catch (Exception e)
    {
      LogUtil.printLog((short)3, e.getMessage());
    }
  }

  public static long getBetweenTime(Calendar calenBegin, Calendar calenEnd)
  {
    Date dateBegin = calenBegin.getTime();
    Date dateEnd = calenEnd.getTime();
    long lBegin = dateBegin.getTime();
    long lEnd = dateEnd.getTime();

    return lEnd - lBegin;
  }

  public static final void sendRedirectUrl(HttpServletRequest request, HttpServletResponse response, String servletUrl)
    throws IOException, UnsupportedEncodingException
  {
    String urlCharset = "ISO-8859-1";
    String requestCharset = request.getCharacterEncoding();

    if ((requestCharset != null) && (requestCharset.length() > 0))
    {
      urlCharset = requestCharset;
    }

    int pos = servletUrl.indexOf("?");

    if (pos > 0)
      servletUrl = servletUrl + "&referer=";
    else {
      servletUrl = servletUrl + "?referer=";
    }

    StringBuffer sourceUrl = getRequestUrl(request);

    String queryStr = request.getQueryString();

    if (queryStr != null)
    {
      sourceUrl.append("?").append(queryStr);
    }

    servletUrl = servletUrl + URLEncoder.encode(sourceUrl.toString(), urlCharset);
    response.sendRedirect(servletUrl);
  }

  private static StringBuffer getRequestUrl(HttpServletRequest request)
  {
    StringBuffer sourceUrl = request.getRequestURL();

    return sourceUrl;
  }
}