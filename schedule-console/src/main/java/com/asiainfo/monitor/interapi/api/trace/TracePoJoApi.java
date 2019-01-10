package com.asiainfo.monitor.interapi.api.trace;

import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class TracePoJoApi {

	
	
	private final static transient Log log=LogFactory.getLog(TracePoJoApi.class);
	
	public static ITrace parseTracePoJo(InputStreamReader in) throws Exception {
		if (in==null)
			return null;
		ITrace trace=null;
		try{
			SAXReader saxReader = new SAXReader();
			Document document=saxReader.read(in);
			Element root=document.getRootElement();
			if (root!=null){
				IBuilder builder=new TraceBuilder();
				trace=builder.parseAppPojo(root);
			}
			
		}catch(Exception e){
			log.error("Call TracePoJoApi's Method parseTracePoJo has Exception :"+e.getMessage());
			throw new Exception("Call TracePoJoApi's Method parseTracePoJo has Exception :"+e.getMessage());
		}
		return trace;
	}
	
	
	
	public static void main(String[] args){
		try{

		}catch(Exception e){
			e.printStackTrace();
		}
		
	    
	}
	
	
}
