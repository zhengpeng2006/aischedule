package com.asiainfo.monitor.tools.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.bo.SysdateManager;

public class Util {
  private static transient Log log = LogFactory.getLog(Util.class);
  
  public static final String DEFAULT_CHARSET = "GB2312";
  
  public static String localIP;

  public static Log getLogger(Class logClass) {
    return LogFactory.getLog(logClass);
  }

  public static Log getLogger(String name) {
    return LogFactory.getLog(name);
  }

  public static long getCurrentTimeMillis() {
    return SysdateManager.getCurrentTimeMillis();
  }

  public static java.sql.Date getSysDate() {
    return SysdateManager.getSysDate();
  }


  public static String unionWhereSql(String aCond1, String aCond2) {
    if (aCond1 == null || (aCond1.trim().length() == 0))
      return aCond2;
    if (aCond2 == null || (aCond2.trim().length() == 0))
      return aCond1;

    String[] sqls = splitString(aCond1, "order by");
    String strAnd1 = sqls[0];
    String strOrderBy1 = sqls[1];

    sqls = splitString(aCond2, "order by");
    String strAnd2 = sqls[0];
    String strOrderBy2 = sqls[1];

    StringBuffer buffer = new StringBuffer();
    if((StringUtils.isNotBlank(strAnd1)) &&(StringUtils.isNotBlank(strAnd2)) )
      buffer.append("(").append(strAnd1).append(") and (").append(
          strAnd2).append(")");
    else if ((StringUtils.isNotBlank(strAnd1)) )
      buffer.append(strAnd1);
    else if ((StringUtils.isNotBlank(strAnd2)) )
      buffer.append(strAnd2);

    if ( (StringUtils.isNotBlank(strOrderBy1)) && (StringUtils.isNotBlank(strOrderBy2)))
      buffer.append(" order by ").append(strOrderBy2).append(",").append(
          strOrderBy1);
    else if ((StringUtils.isNotBlank(strOrderBy1)))
      buffer.append(" order by ").append(strOrderBy1);
    else if ((StringUtils.isNotBlank(strOrderBy2)))
      buffer.append(" order by ").append(strOrderBy2);
    return buffer.toString();
  }

  public static String[] splitString(String s, String tag) {
    String[] result = new String[2];
    result[0] = "";
    result[1] = "";
    if ( (s == null) || (tag == null))
      return result;
    s = s.trim();
    tag = tag.trim();
    int index = s.toLowerCase().indexOf(tag.toLowerCase());

    if (index == -1)
      result[0] = s;
    else if (index != 0) {
      result[0] = s.substring(0, index);
      result[1] = s.substring(index + tag.length(), s.length());
    }
    else
      result[1] = s.substring(tag.length(), s.length());
    return result;
  }

  /**
   * 替换字符串中的特殊字符为对应的转移字符
   * @param str String  包含特殊字符的字符串
   * @return String 转换后的字符串
   */
  public static String checkAndTransStr(String str) {
    if ( (str == null) || (str.equals("")))
      return str;

    if (! (str.indexOf("&") >= 0 || str.indexOf(">") >= 0 ||
           str.indexOf("<") >= 0 || str.indexOf("'") >= 0 ||
           str.indexOf('"') >= 0)
        )
      return str;
    else {
      if (str.indexOf("&") >= 0) {
        str = StringUtils.replace(str, "&", "&amp;");
      }
      if (str.indexOf(">") >= 0) {
        str = StringUtils.replace(str, ">", "&gt;");
      }
      if (str.indexOf("<") >= 0) {
        str = StringUtils.replace(str, "<", "&lt;");
      }
      if (str.indexOf("\"") >= 0) {
        str = StringUtils.replace(str, "\"", "&quot;");
      }
      return str;
    }
  }

  public static String checkAndTransStrForHTML(String str) {
    if ( (str == null) || (str.equals("")))
      return str;

    if (! (str.indexOf("&") >= 0 || str.indexOf(">") >= 0 ||
           str.indexOf("<") >= 0 || str.indexOf("'") >= 0 ||
           str.indexOf('"') >= 0 || str.indexOf(" ") >= 0
        )
        )
      return str;
    else {
      if (str.indexOf("&") >= 0) {
        str = StringUtils.replace(str, "&", "&amp;");
      }
      if (str.indexOf(">") >= 0) {
        str = StringUtils.replace(str, ">", "&gt;");
      }
      if (str.indexOf("<") >= 0) {
        str = StringUtils.replace(str, "<", "&lt;");
      }
      if (str.indexOf("\"") >= 0) {
        str = StringUtils.replace(str, "\"", "&quot;");
      }
      if (str.indexOf(" ") >= 0) {
        str = StringUtils.replace(str, " ", "&nbsp;");
      }

      return str;

    }
  }

  /**
   * 对将要进行sql查询的语句进行编码
   * @param str String
   * @return String
   */
  public static String checkAndTransSqlStr(String str) {
    StringUtils.replace(str, "'", "''");
    return str;
  }


  public static InputStream getResourceAsStream(Class aClass, String fileName) {
    InputStream result = Thread.currentThread().getContextClassLoader().
        getResourceAsStream(fileName);
    if (result == null) {
      result = aClass.getClassLoader().getResourceAsStream(fileName);
    }
    return result;
  }

  public static URL getResource(Class aClass, String fileName) {
    URL result = Thread.currentThread().getContextClassLoader().getResource(
        fileName);
    if (result == null) {
      result = aClass.getClassLoader().getResource(fileName);
    }
    return result;
  }
  
  public static byte[] getResourceAsBytes(Class aClass,String fileName)throws Exception{
  	InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
    if (in == null) {
      in = aClass.getClassLoader().getResourceAsStream(fileName);
    }
    return getResourceAsBytes(in);
  }
  
  public static byte[] getResourceAsBytes(InputStream in)throws Exception{
  	if(in == null)
    	return null;
    ByteArrayOutputStream bytestream = new ByteArrayOutputStream(); 
    try{
      int ch;   
      while ((ch = in.read()) != -1) {   
        bytestream.write(ch);   
      }   
      byte imgdata[] = bytestream.toByteArray();  
      return imgdata; 
    }
    finally{
      bytestream.close();   
    }
  }

  /**
   * 数据与操作
   * @param a1
   * @param a2
   * @return
   */
  public static String[] arrayIntersection(String[] a1, String[] a2) {
    if ( (a1 == null) || (a2 == null))
      return new String[0];
    ArrayList list = new ArrayList();
    for (int i = 0; i < a1.length; i++) {
      for (int j = 0; j < a2.length; j++)
        if (a1[i].toUpperCase().equals(a2[j].toUpperCase()) )
          list.add(a1[i]);
    }
    return (String[]) list.toArray(new String[0]);
  }

  /**
   * 数据或操作
   * @param a1
   * @param a2
   * @return
   */
  public static String[] arrayUnion(String[] a1, String[] a2) {
    if ( (a1 == null) && (a2 == null))
      return new String[0];
    if (a1 == null)
      return a2;
    if (a2 == null)
      return a1;
    ArrayList list = new ArrayList();
    for (int i = 0; i < a1.length; i++)
      list.add(a1[i]);

    for (int i = 0; i < a2.length; i++) {
      boolean isfind = false;
      for (int j = 0; j < a1.length; j++)
        if (a2[i].equalsIgnoreCase(a1[j])) {
          isfind = true;
          break;
        }
      if (isfind == false)
        list.add(a2[i]);
    }
    return (String[]) list.toArray(new String[0]);
  }



  /**
   * 获取主机上空闲的端口
   * @return int
   */
  public static int getFreeSocketPort() {
    try {
      ServerSocket ss = new ServerSocket(0);
      int freePort = ss.getLocalPort();
      ss.close();
      return freePort;
    }
    catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }
  
  public static boolean isValidUtf8(byte[] b, int aMaxCount) {
		int lLen = b.length, lCharCount = 0;
		for (int i = 0; i < lLen && lCharCount < aMaxCount; ++lCharCount) {
			byte lByte = b[i++];// to fast operation, ++ now, ready for the following
													// for(;;)
			if (lByte >= 0)
				continue;// >=0 is normal ascii
			if (lByte < (byte) 0xc0 || lByte > (byte) 0xfd)
				return false;
			int lCount = lByte > (byte) 0xfc ? 5 : lByte > (byte) 0xf8 ? 4
					: lByte > (byte) 0xf0 ? 3 : lByte > (byte) 0xe0 ? 2 : 1;
			if (i + lCount > lLen)
				return false;
			for (int j = 0; j < lCount; ++j, ++i)
				if (b[i] >= (byte) 0xc0)
					return false;
		}
		return true;
	}
  
  public static boolean parseBoolean(String flag){
	  if (flag.equalsIgnoreCase("true"))
		  return true;
	  else
		  return false;
  }
  
  public static String formatDateFromLog(long time){
	 if (time==0)
		 return "";
	 String result="";
	 
	 try {
		  Date nowTime = new Date(time);
		  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  result=df.format(nowTime);
	 }catch (Exception ex) {
		log.error("Call Util'Method formatDateFromLog has Exception:"+ex.getMessage()); 
	 }
	 return result;
  }
  
  public static void main(String[] args) {
	  System.out.println(System.currentTimeMillis());
	  System.out.println(Util.formatDateFromLog(1277259506750l));
  }
}
