package com.asiainfo.monitor.interapi.api.trace;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;

public class TraceBuilder implements IBuilder {

	private final static transient Log log=LogFactory.getLog(TraceBuilder.class);

	private int svrCount=0;
	
	private int daoCount=0;
	
	private int memCount=0;
	
	private int secMemCount=0;
	
	private int cauCount=0;
	
	private int jdbcCount=0;
	
	private int mdbCount=0;
	
	private int httpCount=0;
	
	private int wsCount=0;
	
	/**
	 * 解析Mdb
	 * @param elemt
	 * @return
	 * @throws Exception
	 */
	public ITrace parseMdbPojo(Element elemt) throws Exception{
		ITrace trace=null;
		try{
			if (elemt!=null){
				mdbCount++;
				trace=new MdbTrace();				
				trace.setId(elemt.attributeValue("id"));
				trace.setName("Mdb");
				trace.setType("Mdb");
				trace.setThreadId(elemt.attributeValue("threadId"));
				trace.setCreateTime(elemt.attributeValue("time"));
				Element hostElem=elemt.element("host");
				if (hostElem!=null)
					((MdbTrace)trace).setHost(hostElem.getText());
				Element cenElement=elemt.element("cen");
				if (cenElement!=null)
					((MdbTrace)trace).setCenter(cenElement.getText());
				Element codeElem=elemt.element("code");
				if (codeElem!=null)
					((MdbTrace)trace).setCode(codeElem.getText());
				
				Element inElement=elemt.element("in");
				if (inElement!=null){
					
					//解析in参数
					List paraList=inElement.elements("p");
					StringBuffer sb=new StringBuffer("");
					for (int i=0;i<paraList.size();i++){
						SimpleParam sp=parseParamPojo((Element)paraList.get(i));
						sb.append(sp.getPicture());
					}
					((MdbTrace)trace).setParameter(sb.toString());
				}
				Element outElement=elemt.element("out");
				if (outElement!=null){
					//解析in参数
					List paraList=outElement.elements("p");
					StringBuffer sb=new StringBuffer("");
					for (int i=0;i<paraList.size();i++){
						SimpleParam sp=parseParamPojo((Element)paraList.get(i));
						sb.append(sp.getPicture());
					}
					((MdbTrace)trace).setOut(sb.toString());
				}
				
				Element etElement=elemt.element("et");
				if (etElement!=null)
					((MdbTrace)trace).setUseTime(etElement.getText());
				Element sElem=elemt.element("s");
				if (sElem!=null)
					((MdbTrace)trace).setSuccess(sElem.getText());
			}
		}catch(Exception e){
			log.error("Call TracePoJoApi's Method parseMdbPojo has Exception :"+e.getMessage());
		}
		return trace;
	}
	
	public ITrace parseMemPojo(Element elemt) throws Exception{
		ITrace trace=null;
		try{
			if (elemt!=null){				
				trace=new MemTrace();				
				trace.setId(elemt.attributeValue("id"));
				trace.setThreadId(elemt.attributeValue("threadId"));
				trace.setCreateTime(elemt.attributeValue("time"));
				trace.setType("Mem");
				Element hostElem=elemt.element("host");
				if (hostElem!=null)
					((MemTrace)trace).setHost(hostElem.getText());
				Element gtElem=elemt.element("gt");
				if (gtElem!=null)
					((MemTrace)trace).setGetTime(gtElem.getText());
				Element cenElement=elemt.element("cen");
				if (cenElement!=null)
					((MemTrace)trace).setCenter(cenElement.getText());
				Element codeElement=elemt.element("code");
				if (codeElement!=null)
					((MemTrace)trace).setCode(codeElement.getText());
				
				Element inElement=elemt.element("in");
				if (inElement!=null){
					//解析in参数
					List paraList=inElement.elements("p");
					StringBuffer sb=new StringBuffer("");
					for (int i=0;i<paraList.size();i++){
						SimpleParam sp=parseParamPojo((Element)paraList.get(i));
						sb.append(sp.getPicture());
					}
					((MemTrace)trace).setParameter(sb.toString());
				}
				Element outElement=elemt.element("c");
				if (outElement!=null){
					//解析in参数
					((MemTrace)trace).setResultCount(outElement.getText());
				}
				
				Element etElement=elemt.element("et");
				if (etElement!=null)
					((MemTrace)trace).setUseTime(etElement.getText());
				Element sElem=elemt.element("s");
				if (sElem!=null)
					((MemTrace)trace).setSuccess(sElem.getText());
			}
		}catch(Exception e){
			log.error("Call TracePoJoApi's Method parseMemPojo has Exception :"+e.getMessage());
		}
		return trace;
	}
	
	public ITrace parseHttpPojo(Element elemt) throws Exception{
		ITrace trace=null;
		try{
			if (elemt!=null){
				httpCount++;
				trace=new HttpTrace();				
				trace.setId(elemt.attributeValue("id"));
				trace.setName("Http");
				trace.setType("Http");
				trace.setThreadId(elemt.attributeValue("threadId"));				
				trace.setCreateTime(elemt.attributeValue("time"));
				Element urlElem=elemt.element("url");
				if (urlElem!=null)
					((HttpTrace)trace).setUrl(urlElem.getText());
				Element pmElem=elemt.element("pm");
				if (pmElem!=null)
					((HttpTrace)trace).setProcessMethod(pmElem.getText());
				Element toElem=elemt.element("to");
				if (toElem!=null)
					((HttpTrace)trace).setTimeOut(toElem.getText());
				Element scElem=elemt.element("sc");
				if (scElem!=null)
					((HttpTrace)trace).setStateCode(scElem.getText());
				Element cenElement=elemt.element("cen");
				if (cenElement!=null)
					((HttpTrace)trace).setCenter(cenElement.getText());
				Element codeElem=elemt.element("code");
				if (codeElem!=null)
					((HttpTrace)trace).setCode(codeElem.getText());
				
				Element headElement=elemt.element("h");
				if (headElement!=null){
					//解析head
					List paraList=headElement.elements("p");
					StringBuffer sb=new StringBuffer("");
					for (int i=0;i<paraList.size();i++){
						SimpleParam sp=parseParamPojo((Element)paraList.get(i));
						sb.append(sp.getPicture());
					}
					((HttpTrace)trace).setHeader(sb.toString());
				}
				Element qElement=elemt.element("q");
				if (qElement!=null){
					//解析参数
					List paraList=qElement.elements("p");
					StringBuffer sb=new StringBuffer("");
					for (int i=0;i<paraList.size();i++){
						SimpleParam sp=parseParamPojo((Element)paraList.get(i));
						sb.append(sp.getPicture());
					}
					((HttpTrace)trace).setParameter(sb.toString());
				}
				Element bElement=elemt.element("b");
				if (bElement!=null){
					//解析参数
					List paraList=bElement.elements("p");
					StringBuffer sb=new StringBuffer("");
					for (int i=0;i<paraList.size();i++){
						SimpleParam sp=parseParamPojo((Element)paraList.get(i));
						sb.append(sp.getPicture());
					}
					((HttpTrace)trace).setResult(sb.toString());
				}
				Element sElem=elemt.element("s");
				if (sElem!=null)
					((HttpTrace)trace).setSuccess(sElem.getText());
			}
		}catch(Exception e){
			log.error("Call TracePoJoApi's Method parseHttpPojo has Exception :"+e.getMessage());
		}
		return trace;
	}
	
	public ITrace parseWsPojo(Element elemt) throws Exception{
		ITrace trace=null;
		try{
			if (elemt!=null){
				wsCount++;
				
				trace=new WsTrace();				
				trace.setId(elemt.attributeValue("id"));
				trace.setName("Ws");
				trace.setType("Ws");
				trace.setThreadId(elemt.attributeValue("threadId"));
				trace.setCreateTime(elemt.attributeValue("time"));				
				Element urlElem=elemt.element("url");
				if (urlElem!=null)
					((WsTrace)trace).setUrl(urlElem.getText());
				Element mnElem=elemt.element("mn");
				if (mnElem!=null)
					((WsTrace)trace).setMethodName(mnElem.getText());
				Element toElem=elemt.element("to");
				if (toElem!=null)
					((WsTrace)trace).setTimeOut(toElem.getText());
				Element cenElement=elemt.element("cen");
				if (cenElement!=null)
					((WsTrace)trace).setCenter(cenElement.getText());
				
				Element codeElem=elemt.element("code");
				if (codeElem!=null)
					((WsTrace)trace).setCode(codeElem.getText());
				
				Element inElement=elemt.element("in");
				if (inElement!=null){
					//解析in参数
					List paraList=inElement.elements("p");
					StringBuffer sb=new StringBuffer("");
					for (int i=0;i<paraList.size();i++){
						SimpleParam sp=parseParamPojo((Element)paraList.get(i));
						sb.append(sp.getPicture());
					}
					((WsTrace)trace).setParameter(sb.toString());
				}
				Element outElement=elemt.element("out");
				if (outElement!=null){
					//解析in参数
					List paraList=outElement.elements("p");
					StringBuffer sb=new StringBuffer("");
					for (int i=0;i<paraList.size();i++){
						SimpleParam sp=parseParamPojo((Element)paraList.get(i));
						sb.append(sp.getPicture());
					}
					((WsTrace)trace).setOut(sb.toString());
				}
				
				Element etElement=elemt.element("et");
				if (etElement!=null)
					((WsTrace)trace).setUseTime(etElement.getText());
				Element sElem=elemt.element("s");
				if (sElem!=null)
					((WsTrace)trace).setSuccess(sElem.getText());
			}
		}catch(Exception e){
			log.error("Call TracePoJoApi's Method parseWsPojo has Exception :"+e.getMessage());
		}
		return trace;
	}
	
	public ITrace parseJdbcPojo(Element elemt) throws Exception {
		ITrace trace=null;
		try{
			if (elemt!=null){
				jdbcCount++;
				trace=new JdbcTrace();
				trace.setId(elemt.attributeValue("id"));
				trace.setCreateTime(elemt.attributeValue("time"));
				trace.setThreadId(elemt.attributeValue("threadId"));
				trace.setType("Jdbc");
				Element uElement=elemt.element("u");
				if (uElement!=null)
					((JdbcTrace)trace).setType(uElement.getText());
				Element sqlElement=elemt.element("sql");
				if (sqlElement!=null){
					String sql=sqlElement.getText();
					if (StringUtils.isNotBlank(sql)){
//						String txt=sql.substring(sql.indexOf(PRE_STR)+9,sql.indexOf(LST_STR));															
						((JdbcTrace)trace).setSql(sql);
					}
				}
				Element tElement=elemt.element("t");
				if (tElement!=null)
					((JdbcTrace)trace).setType(tElement.getText());
				Element inElement=elemt.element("in");
				if (inElement!=null){
					//解析in参数
					List paraList=inElement.elements("p");
					StringBuffer sb=new StringBuffer("");
					for (int i=0;i<paraList.size();i++){
						SimpleParam sp=parseParamPojo((Element)paraList.get(i));
						sb.append(sp.getPicture());
					}
					((JdbcTrace)trace).setParameter(sb.toString());
				}
				Element etElement=elemt.element("et");
				if (etElement!=null)
					((JdbcTrace)trace).setUseTime(etElement.getText());
			}
		}catch(Exception e){
			log.error("Call TracePoJoApi's Method parseJdbcPojo has Exception :"+e.getMessage());
		}
		return trace;
	}

	public ITrace parseDaoPojo(Element elemt) throws Exception {
		ITrace trace=null;
		try{
			if (elemt!=null){
				daoCount++;
				trace=new DaoTrace();
				trace.setId(elemt.attributeValue("id"));
				trace.setCreateTime(elemt.attributeValue("time"));
				trace.setThreadId(elemt.attributeValue("threadId"));
				trace.setType("Dao");
				Element cnElement=elemt.element("cn");
				if (cnElement!=null)
					((DaoTrace)trace).setClassName(cnElement.getText());
				Element mnElement=elemt.element("mn");
				if (mnElement!=null)
					((DaoTrace)trace).setMethodName(mnElement.getText());
				Element cenElement=elemt.element("cen");
				if (cenElement!=null)
					((DaoTrace)trace).setCenter(cenElement.getText());
				Element succElement=elemt.element("s");
				if (succElement!=null)
					((DaoTrace)trace).setSuccess(succElement.getText());
				Element etElement=elemt.element("et");
				if (etElement!=null)
					((DaoTrace)trace).setUseTime(etElement.getText());
				//解析数据源对象
				List jdbcList=elemt.elements("jdbc");
				if (jdbcList!=null && jdbcList.size()>0){
					for (int jc=0;jc<jdbcList.size();jc++){
						Element jdbcItem=(Element)jdbcList.get(jc);
						
						trace.addChild(parseJdbcPojo(jdbcItem));
					}
				}
			}
		}catch(Exception e){
			log.error("Call TracePoJoApi's Method parseDaoPojo has Exception :"+e.getMessage());
		}
		return trace;
	}

	public ITrace parseSvrPojo(Element elemt) throws Exception {
		ITrace trace=null;
		try{
			if (elemt!=null){
				svrCount++;
				trace=new SvrTrace();
				trace.setId(elemt.attributeValue("id"));
				trace.setCreateTime(elemt.attributeValue("time"));
				trace.setThreadId(elemt.attributeValue("threadId"));
				trace.setType("Svr");
				Element cnElement=elemt.element("cn");
				if (cnElement!=null){
					String clazz=cnElement.getText();
					if (StringUtils.isNotBlank(clazz)){
						((SvrTrace)trace).setClassName(clazz);
					}
				}
				Element mnElement=elemt.element("mn");
				if (mnElement!=null){
					String method=mnElement.getText();
					if (StringUtils.isNotBlank(method)){
						((SvrTrace)trace).setMethodName(method);
					}
				}
				Element inElement=elemt.element("in");
				if (inElement!=null){
					List paraList=inElement.elements("p");
					if (paraList!=null && paraList.size()>0){
						for (int pCount=0;pCount<paraList.size();pCount++){
							Element paraItem=(Element)paraList.get(pCount);
							((SvrTrace)trace).addParameter(parseParamPojo(paraItem));							
						}
					}
				}
				Element aiElement=elemt.element("ai");
				if (aiElement!=null)
					((SvrTrace)trace).setAppIp(aiElement.getText());
				Element snElement=elemt.element("sn");
				if (snElement!=null)
					((SvrTrace)trace).setAppServerName(snElement.getText());
				Element cenElement=elemt.element("cen");
				if (cenElement!=null)
					((SvrTrace)trace).setCenter(cenElement.getText());
				Element codeElement=elemt.element("code");
				if (codeElement!=null)
					((SvrTrace)trace).setCode(codeElement.getText());
				Element sElement=elemt.element("s");
				if (sElement!=null)
					((SvrTrace)trace).setSuccess(sElement.getText());
				Element etElement=elemt.element("et");
				if (etElement!=null)
					((SvrTrace)trace).setUseTime(etElement.getText());
				
				
				//解析DAO对象
				List daoList=elemt.elements("dao");
				if (daoList!=null && daoList.size()>0){
					for (int dc=0;dc<daoList.size();dc++){
						Element daoItem=(Element)daoList.get(dc);
						((SvrTrace)trace).addChild(parseDaoPojo(daoItem));
					}
				}
				
				List srvList=elemt.elements("srv");
				if (srvList!=null && srvList.size()>0){
					for (int childSrvCount=0;childSrvCount<srvList.size();childSrvCount++){
						trace.addChild(this.parseSvrPojo((Element)srvList.get(childSrvCount)));
					}
				}
				List httpList=elemt.elements("http");
				for (int hc=0;httpList!=null && hc<httpList.size();hc++){
					trace.addChild(this.parseHttpPojo((Element)httpList.get(hc)));
				}
				List wsList=elemt.elements("ws");
				for (int wc=0;wsList!=null && wc<wsList.size();wc++){
					trace.addChild(this.parseWsPojo((Element)wsList.get(wc)));
				}
				List memList=elemt.elements("mem");
				for (int mc=0;memList!=null && mc<memList.size();mc++){
					ITrace memTrace=this.parseMemPojo((Element)memList.get(mc));
					memCount++;
					memTrace.setName("Mem");
					trace.addChild(memTrace);
				}
				List secmemList=elemt.elements("secmem");
				for (int mc=0;secmemList!=null && mc<secmemList.size();mc++){
					ITrace memTrace=this.parseMemPojo((Element)secmemList.get(mc));
					secMemCount++;
					memTrace.setName("Secmem");
					trace.addChild(memTrace);
				}
				List cauList=elemt.elements("cau");
				for (int mc=0;cauList!=null && mc<cauList.size();mc++){
					ITrace memTrace=this.parseMemPojo((Element)cauList.get(mc));
					cauCount++;
					memTrace.setName("Cau");
					trace.addChild(memTrace);
				}
			}
		}catch(Exception e){
			log.error("Call TracePoJoApi's Method parseSvrPojo has Exception :"+e.getMessage());
		}
		return trace;
	}

	public ITrace parseWebPojo(Element elemt) throws Exception {
		ITrace webTrace=null;
		try{
			if (elemt!=null){
				webTrace=new WebTrace();
				webTrace.setId(elemt.attributeValue("id"));
				webTrace.setCreateTime(elemt.attributeValue("time"));
				webTrace.setThreadId(elemt.attributeValue("threadid"));
				webTrace.setName("Web");
				webTrace.setType("Web");
				webTrace.setIcon("webIcon");
				Element urlElement=elemt.element("url");
				if (urlElement!=null){
					String txt=urlElement.getText();
					if (StringUtils.isNotBlank(txt))
//						((WebTrace)webTrace).setUrl(txt.substring(txt.indexOf(PRE_STR)+1,txt.indexOf(LST_STR)));
						((WebTrace)webTrace).setUrl(txt);
				}
				Element ciElement=elemt.element("ci");
				if (ciElement!=null){
					String txt=ciElement.getText();
					if (StringUtils.isNotBlank(txt))
						((WebTrace)webTrace).setClientIp(txt);
				}
				Element wiElement=elemt.element("wi");
				if (wiElement!=null){
					String txt=wiElement.getText();
					if (StringUtils.isNotBlank(txt))
						((WebTrace)webTrace).setServerIp(txt);
				}
				Element snElement=elemt.element("sn");
				if (snElement!=null){
					String txt=snElement.getText();
					if (StringUtils.isNotBlank(txt))
						((WebTrace)webTrace).setServerName(txt);
				}
				Element cdElement=elemt.element("code");
				if (cdElement!=null){
					String txt=cdElement.getText();
					if (StringUtils.isNotBlank(txt))
						((WebTrace)webTrace).setCode(txt);
				}
				Element orgElement=elemt.element("orgcode");
				if (orgElement!=null){
					String txt=orgElement.getText();
					if (StringUtils.isNotBlank(txt))
						((WebTrace)webTrace).setOrgId(txt);
				}
			}
		}catch(Exception e){
			log.error("Call TracePoJoApi's Method parseWebPojo has Exception :"+e.getMessage());
		}
		return webTrace;
	}

	/**
	 * 解析输入参数
	 * 1、SV参数
	 * 2、Jdbc参数
	 */
	public SimpleParam parseParamPojo(Element elemt) throws Exception{
		SimpleParam param=null;
		try{
			if (elemt!=null ){
				param=new SimpleParam();
				String index=elemt.attributeValue("s");
				if (StringUtils.isNotBlank(index))
					param.setIndex(index);
				String type=elemt.attributeValue("t");
				if (StringUtils.isNotBlank(type))
					param.setType(type);
				
				StringBuilder sb=new StringBuilder("");
				if (StringUtils.isNotBlank(type)){
					sb.append("<"+type+">");
//					String paraData=elemt.getText();
					String paraData=elemt.attributeValue("v");
					sb.append(paraData);
					sb.append("</"+type+">");
				}else{
					String paraData=elemt.getText();
					sb.append(paraData);
				}
				
				param.setPicture(sb.toString());
				/*String[] splitStrs=paraData.split("/>");
				if (splitStrs.length<3){
					if (StringUtils.isNotBlank(paraData) && paraData.indexOf("<")>=0 && paraData.indexOf(">")>=0){
						String paraType=paraData.substring(paraData.indexOf("<")+1,paraData.indexOf(">"));
						if (StringUtils.isNotBlank(paraType))
							param.setType(paraType);
						String paraValue=paraData.substring(paraData.indexOf(">")+1,paraData.lastIndexOf("<"));
						if (StringUtils.isNotBlank(paraValue))
							param.setValue(paraValue);											
					}else{
						param.setValue(paraData);
					}
				}else{
					param.setIndex(paraData);
				}*/
			}
		}catch(Exception e){
			log.error("Call TracePoJoApi's Method parseParamPojo has Exception :"+e.getMessage());
		}
		return param;
	}
	
	/**
	 * 解析根Trace:AppTrace
	 */
	public ITrace parseAppPojo(Element elemt) throws Exception {
		ITrace trace=null;
		try{			
		    if (elemt!=null){
		    	trace=new AppTrace();
		    	trace.setId(elemt.attributeValue("id"));
		    	trace.setCreateTime(elemt.attributeValue("time"));
		    	trace.setThreadId(elemt.attributeValue("threadid"));
		    	trace.setType("App");
		    	Element webElement=elemt.element("web");
		    	if (webElement != null)
		    		trace.addChild(this.parseWebPojo(webElement));
				
				List svList=elemt.elements("srv");
				if (svList!=null && svList.size()>0){
					for (int i=0;i<svList.size();i++){
						Element svItem=(Element)svList.get(i);
						trace.addChild(this.parseSvrPojo(svItem));
					}
				}
				StringBuffer sb=new StringBuffer("共");				
				if(jdbcCount>0){
					sb.append(jdbcCount+"次jdbc操作\n");
				}
				if(memCount>0){
					sb.append(memCount+"次memcached操作\n");
				}
				if(mdbCount>0){
					sb.append(mdbCount+"次mdb操作\n");
				}
				if(svrCount>0){
					sb.append(svrCount+"次srv操作\n");
				}
				if(daoCount>0){
					sb.append(daoCount+"次dao操作\n");
				}
				if(secMemCount>0){
					sb.append(secMemCount+"次secmem操作\n");
				}
				if(cauCount>0){
					sb.append(cauCount+"次cau操作\n");
				}
				if(httpCount>0){
					sb.append(httpCount+"次http操作\n");
				}
				if(wsCount>0){
					sb.append(wsCount+"次ws操作\n");
				}
				((AppTrace)trace).setMsg(sb.toString());
		    }
		}catch(Exception e){
			
		}
		return trace;
	}
	
	public static void main(String[] args){
		String txt="<![CDATA[<string>PBX2SpeechProdPriceSrvpkgId</string>]]> ";
		System.out.println(txt.substring(txt.indexOf(PRE_STR)+9,txt.indexOf(LST_STR)));
	}
}
