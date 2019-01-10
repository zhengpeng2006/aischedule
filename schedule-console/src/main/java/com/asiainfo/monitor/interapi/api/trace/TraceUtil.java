package com.asiainfo.monitor.interapi.api.trace;

import java.lang.reflect.Array;

//import example.so.bo.BOCustBean;
import org.apache.commons.lang.ClassUtils;

import com.ai.appframe2.bo.DataContainer;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: AI(NanJing)</p>
 * @author Yang Hua
 * @version 5.5
 */
public final class TraceUtil {

  private static long TRACE_ID = 0;
  private static XStream STREAM = new XStream(new DomDriver());

  private TraceUtil() {
  }

  public  static synchronized long getTraceId(){
    return ++TRACE_ID;
  }

  public static String object2xml(Object objects) {
    StringBuffer sb = new StringBuffer();
    if (objects.getClass().isArray()) {
      if (ClassUtils.isAssignable(objects.getClass().getComponentType(), DataContainer.class)) {
	int len = Array.getLength(objects);
	sb.append("<p s=\"" + 1 + "\">");
	for (int j = 0; j < len; j++) {
	  Object tmp2 = Array.get(objects, j);
	  if (tmp2 == null) {
	    sb.append("<![CDATA[" + null +"]]>");
	  }
	  else {
	    sb.append("<![CDATA[" + tmp2.toString() + "]]>");
	  }
	}
	sb.append("</p>");
      }
      else {
	sb.append("<p s=\"" + 1 + "\"><![CDATA[" + STREAM.toXML(objects) + "]]></p>");
      }
    }
    else if (objects instanceof DataContainer) {
      sb.append("<p s=\"" + 1 + "\"><![CDATA[" + objects.toString() + "]]></p>");
    }
    else {
      sb.append("<p s=\"" + 1 + "\"><![CDATA[" + STREAM.toXML(objects) + "]]></p>");
    }

    return sb.toString();
  }

  public static String object2xml(Object[] objects){
    StringBuffer sb = new StringBuffer();
    if (objects != null && objects.length > 0) {
      for (int i = 0; i < objects.length; i++) {
	if(objects[i]==null){
	  sb.append("<p s=\"" + (i + 1) + "\"><![CDATA[null]]></p>");
	  continue;
	}

	if(objects[i].getClass().isArray()){
	  if(ClassUtils.isAssignable(objects[i].getClass().getComponentType(),DataContainer.class) ){
	    int len = Array.getLength(objects[i]);
	    sb.append("<p s=\""+(i+1)+"\">");
	    for (int j = 0; j < len; j++) {
	      Object tmp2 = Array.get(objects[i],j);
	      if(tmp2==null){
		sb.append("<![CDATA["+null+"]]>");
	      }
	      else{
		sb.append("<![CDATA["+tmp2.toString()+"]]>");
	      }
	    }
	    sb.append("</p>");
	  }
	  else{
	    sb.append("<p s=\"" + (i + 1) + "\"><![CDATA[" + STREAM.toXML(objects[i]) + "]]></p>");
	  }
	}
	else if(objects[i] instanceof DataContainer){
	  sb.append("<p s=\""+(i+1)+"\"><![CDATA["+objects[i].toString()+"]]></p>");
	}
	else{
	  sb.append("<p s=\"" + (i + 1) + "\"><![CDATA[" + STREAM.toXML(objects[i]) + "]]></p>");
	}

      }
    }
    return sb.toString();
  }

  /*public static void main(String[] args)  throws Exception {
    BOCustBean[] objBOCustBean = new BOCustBean[2];
    for (int i = 1; i < 2; i++) {
      objBOCustBean[i] = new BOCustBean();
      objBOCustBean[i].setCustId(111);
    }

    HashMap map = new HashMap();
    List list = new ArrayList();
    list.add("1");
    list.add("2");
    map.put("aaa",list);
    System.out.println(TraceUtil.object2xml(objBOCustBean));;
  }*/
}
