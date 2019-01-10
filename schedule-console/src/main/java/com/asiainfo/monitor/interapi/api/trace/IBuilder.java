package com.asiainfo.monitor.interapi.api.trace;

import org.dom4j.Element;

public interface IBuilder {

	public final static String PRE_STR="<![CDATA[";
	public final static String LST_STR="]]>";
	
	/**
	 * 解析Mdb
	 * @param elemt
	 * @return
	 * @throws Exception
	 */
	public ITrace parseMdbPojo(Element elemt) throws Exception;
	
	public ITrace parseMemPojo(Element elemt) throws Exception;
	
	public ITrace parseHttpPojo(Element elemt) throws Exception;
	
	public ITrace parseWsPojo(Element elemt) throws Exception;
	
	public ITrace parseJdbcPojo(Element elemt) throws Exception;
	
	public ITrace parseDaoPojo(Element elemt) throws Exception;
	
	public ITrace parseSvrPojo(Element elemt) throws Exception;
	
	public ITrace parseWebPojo(Element elemt) throws Exception;

	public SimpleParam parseParamPojo(Element elemt) throws Exception;
	
	public ITrace parseAppPojo(Element elemt) throws Exception;
}
