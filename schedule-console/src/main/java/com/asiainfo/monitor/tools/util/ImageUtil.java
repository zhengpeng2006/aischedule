package com.asiainfo.monitor.tools.util;


import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class ImageUtil {
  private transient static Log log = LogFactory.getLog(ImageUtil.class);

  private ImageUtil() {
  }

  /**
   *
   * @param titleName String
   * @param map HashMap
   * @return String
   * @throws Exception
   */
  public static String createXmlForFlex(HashMap map) throws Exception{
	  if (map == null)
		  return null;
	  StringBuilder sb = new StringBuilder();
	  sb.append("<chart>");

	  Set key = map.keySet();
	  int i = 1;
	  for (Iterator iter = key.iterator(); iter.hasNext(); ) {
		  String lineName = (String) iter.next();
		  List l = (List)map.get(lineName);
		  sb.append("<node").append(i).append(" id=\"1\"").append(" name=\"").append(lineName).append("\" ").append(">");
	      for (Iterator iter2 = l.iterator(); iter2.hasNext(); ) {
			  LineValue objitem = (LineValue) iter2.next();
			  String tmpTime = TimeUtil.format7(objitem.date);
			  sb.append("<data").append(" id=\"2\"").append(" value=\"").append(objitem.value).append("\" ");
			  sb.append("date=\"").append(tmpTime).append("\"").append("/>");
	      }
	      sb.append("</node").append(i).append(">");
	      i++;
	  }
	  sb.append("</chart>");
      return sb.toString();
  }

  /**
   *
   * @param date Date
   * @return String
   */
  private static String getYYYYMMDDHH(Date date){
    String end = TimeUtil.getHH(date);
    Date d = TimeUtil.addOrMinusHours(date.getTime(),-1);
    String rtn = TimeUtil.getYYYYMMDDHH(d)+"-"+end;
    return rtn;
  }
}
