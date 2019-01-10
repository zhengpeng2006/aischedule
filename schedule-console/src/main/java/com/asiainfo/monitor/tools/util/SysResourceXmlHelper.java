package com.asiainfo.monitor.tools.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.digester.Digester;
import org.apache.commons.lang.StringUtils;

import com.ai.appframe2.complex.service.impl.xml.App;
import com.ai.appframe2.complex.service.impl.xml.Client;
import com.ai.appframe2.complex.service.impl.xml.Cluster;
import com.ai.appframe2.complex.service.impl.xml.Connect;
import com.ai.appframe2.complex.xml.cfg.defaults.Env;
import com.ai.appframe2.complex.xml.cfg.defaults.Property;
import com.asiainfo.monitor.tools.common.SimpleResult;

public final class SysResourceXmlHelper {
	
	public final static String java_naming_provider_url="java.naming.provider.url";
	
	public final static String prefix_str="t3://";
	
	public static App getApp(String inStr) throws Exception {
		App rtn = null;
		InputStream input = new ByteArrayInputStream(inStr.getBytes());
		Digester digester = new Digester();
		// digester.setLogger(null);
		digester.setValidating(false);
		digester.addObjectCreate("app", App.class.getName());
		digester.addSetProperties("app");

		digester.addObjectCreate("app/cluster", Cluster.class.getName());
		digester.addSetProperties("app/cluster");

		digester.addObjectCreate("app/cluster/env", Env.class.getName());
		digester.addSetProperties("app/cluster/env");

		digester.addObjectCreate("app/cluster/env/property", Property.class.getName());
		digester.addSetProperties("app/cluster/env/property");

		// 值设置
		digester.addSetNext("app/cluster", "addCluster", Cluster.class.getName());
		digester.addSetNext("app/cluster/env", "addEnv", Env.class.getName());
		digester.addSetNext("app/cluster/env/property", "addProperty",Property.class.getName());
		rtn = (App) digester.parse(input);
		return rtn;
	}

	/**
	 * 
	 * @param path
	 *            String
	 * @throws Exception
	 * @return Client
	 */
	public static Client getClient(String inStr) throws Exception {
		Client rtn = null;
		InputStream input = new ByteArrayInputStream(inStr.getBytes());


		Digester digester = new Digester();
		// digester.setLogger(null);
		digester.setValidating(false);
		digester.addObjectCreate("client", Client.class.getName());
		digester.addSetProperties("client");

		digester.addObjectCreate("client/connect", Connect.class.getName());
		digester.addSetProperties("client/connect");

		// 值设置
		digester.addSetNext("client/connect", "addConnect", Connect.class.getName());

		rtn = (Client) digester.parse(input);
		return rtn;
	}
	
	public static Map resolveWeb2AppClusters(Connect[] connects,Cluster[] appClusters) throws RemoteException,Exception{
		Map result=new HashMap();
		List list=null;
		String code="",appClusterName="";
		for (int i=0;i<connects.length;i++){
			code=connects[i].getName();
			appClusterName=connects[i].getAppcluster();
			if (!StringUtils.isBlank(code) && !StringUtils.isBlank(appClusterName)){
				for (int j=0;j<appClusters.length;j++){
					if (appClusterName.equals(appClusters[j].getName())){
						Env[] envs=appClusters[j].getEnvs();
						if (envs!=null && envs.length>0){
							list=new ArrayList();
							for (int k=0;k<envs.length;k++){
								Property[] properties=envs[k].getProperties();
								if (properties!=null && properties.length>0){
									for (int l=0;l<properties.length;l++){
										if (properties[l].getName().equals(java_naming_provider_url)){
											String urlValue=properties[l].getValue();
											urlValue=StringUtils.substring(urlValue,prefix_str.length());											
											String[] ipPorts=StringUtils.split(urlValue,",");
											for (int m=0;m<ipPorts.length;m++){
												String[] items=StringUtils.split(ipPorts[m],TypeConst._INTERPRET_CHAR);
												if (items.length==2){
													SimpleResult sr=new SimpleResult();
													sr.setKey(items[0]);
													sr.setName(items[1]);
													sr.setValue(appClusterName);
													list.add(sr);
												}
											}
											break;
										}
									}
								}
							}
						}
						break;
					}
				}
			}

			if (list!=null && list.size()>0)
				result.put(code, list);
		}
		return result;
	}
	
	public static void main(String[] args) {
		try {
			
			StringBuilder s1 = new StringBuilder("");
			s1.append("<app>");
			s1.append("<cluster name=\"crm-app-g1\" bigdistrictflag=\"true\" desc=\"\">");
			s1.append("     <env name=\"jndiTemplate1\" center=\"1\" bigdistrict=\"1\" group=\"normal\">");
			s1.append("       <property name=\"java.naming.factory.initial\" value=\"weblogic.jndi.WLInitialContextFactory\"/>");
			s1.append("       <property name=\"java.naming.provider.url\" value=\"t3://10.70.205.36:20111,10.70.205.36:20112,10.70.215.36:20113,10.70.215.36:20114\"/>");
			s1.append("     </env>");

			s1.append("     <env name=\"jndiTemplate2\" center=\"1\" bigdistrict=\"2\" group=\"normal\">");
			s1.append("       <property name=\"java.naming.factory.initial\" value=\"weblogic.jndi.WLInitialContextFactory\"/>");
			s1.append("       <property name=\"java.naming.provider.url\" value=\"t3://10.70.205.36:20121,10.70.205.36:20122,10.70.215.36:20123,10.70.215.36:20124\"/>");
			s1.append("     </env>");

			s1.append("     <env name=\"jndiTemplate3\" center=\"2\" bigdistrict=\"3\" group=\"normal\">");
			s1.append("       <property name=\"java.naming.factory.initial\" value=\"weblogic.jndi.WLInitialContextFactory\"/>");
			s1.append("       <property name=\"java.naming.provider.url\" value=\"t3://10.70.205.36:20131,10.70.205.36:20132,10.70.215.36:20133,10.70.215.36:20134\"/>");
			s1.append("     </env>");

			s1.append("     <env name=\"jndiTemplate4\" center=\"2\" bigdistrict=\"4\" group=\"normal\">");
			s1.append("       <property name=\"java.naming.factory.initial\" value=\"weblogic.jndi.WLInitialContextFactory\"/>");
			s1.append("       <property name=\"java.naming.provider.url\" value=\"t3://10.70.205.36:20141,10.70.205.36:20142,10.70.215.36:20143,10.70.215.36:20144\"/>");
			s1.append("     </env>");

			s1.append("     <env name=\"jndiTemplate5\" center=\"3\" bigdistrict=\"5\" group=\"normal\">");
			s1.append("       <property name=\"java.naming.factory.initial\" value=\"weblogic.jndi.WLInitialContextFactory\"/>");
			s1.append("       <property name=\"java.naming.provider.url\" value=\"t3://10.70.205.36:20151,10.70.205.36:20152,10.70.215.36:20153,10.70.215.36:20154\"/>");
			s1.append("     </env>");

			s1.append("     <env name=\"jndiTemplate6\" center=\"3\" bigdistrict=\"6\" group=\"normal\">");
			s1.append("       <property name=\"java.naming.factory.initial\" value=\"weblogic.jndi.WLInitialContextFactory\"/>");
			s1.append("       <property name=\"java.naming.provider.url\" value=\"t3://10.70.205.36:20161,10.70.205.36:20162,10.70.215.36:20163,10.70.215.36:20164\"/>");
			s1.append("     </env>");

			s1.append("     <env name=\"jndiTemplate7\" center=\"4\" bigdistrict=\"7\" group=\"normal\">");
			s1.append("       <property name=\"java.naming.factory.initial\" value=\"weblogic.jndi.WLInitialContextFactory\"/>");
			s1.append("       <property name=\"java.naming.provider.url\" value=\"t3://10.70.205.36:20171,10.70.205.36:20172,10.70.215.36:20173,10.70.215.36:20174\"/>");
			s1.append("     </env>");

			s1.append("     <env name=\"jndiTemplate8\" center=\"4\" bigdistrict=\"8\" group=\"normal\">");
			s1.append("       <property name=\"java.naming.factory.initial\" value=\"weblogic.jndi.WLInitialContextFactory\"/>");
			s1.append("       <property name=\"java.naming.provider.url\" value=\"t3://10.70.205.36:20181,10.70.205.36:20182,10.70.215.36:20183,10.70.215.36:20184\"/>");
			s1.append("     </env>");

			s1.append("     <env name=\"jndiTemplate0\" center=\"0\" group=\"cross\">");
			s1.append("       <property name=\"java.naming.factory.initial\" value=\"weblogic.jndi.WLInitialContextFactory\"/>");
			s1.append("       <property name=\"java.naming.provider.url\" value=\"t3://10.70.205.36:20101,10.70.205.36:20102,10.70.215.36:20103,10.70.215.36:20104\"/>");
			s1.append("     </env>");
			s1.append("</cluster>");

			s1.append("<cluster name=\"crm-app-g10\" bigdistrictflag=\"true\" desc=\"\">");
			s1.append("     <env name=\"jndiTemplate1\" center=\"1\" bigdistrict=\"1\" group=\"normal\">");
			s1.append("       <property name=\"java.naming.factory.initial\" value=\"weblogic.jndi.WLInitialContextFactory\"/>");
			s1.append("       <property name=\"java.naming.provider.url\" value=\"t3://10.70.205.69:21011,10.70.205.69:21012,10.70.215.69:21013,10.70.215.69:21014\"/>");
			s1.append("     </env>");

			s1.append("     <env name=\"jndiTemplate2\" center=\"1\" bigdistrict=\"2\" group=\"normal\">");
			s1.append("       <property name=\"java.naming.factory.initial\" value=\"weblogic.jndi.WLInitialContextFactory\"/>");
			s1.append("       <property name=\"java.naming.provider.url\" value=\"t3://10.70.205.69:21021,10.70.205.69:21022,10.70.215.69:21023,10.70.215.69:21024\"/>");
			s1.append("     </env>");

			s1.append("     <env name=\"jndiTemplate3\" center=\"2\" bigdistrict=\"3\" group=\"normal\">");
			s1.append("       <property name=\"java.naming.factory.initial\" value=\"weblogic.jndi.WLInitialContextFactory\"/>");
			s1.append("       <property name=\"java.naming.provider.url\" value=\"t3://10.70.205.69:21031,10.70.205.69:21032,10.70.215.69:21033,10.70.215.69:21034\"/>");
			s1.append("     </env>");

			s1.append("     <env name=\"jndiTemplate4\" center=\"2\" bigdistrict=\"4\" group=\"normal\">");
			s1.append("       <property name=\"java.naming.factory.initial\" value=\"weblogic.jndi.WLInitialContextFactory\"/>");
			s1.append("       <property name=\"java.naming.provider.url\" value=\"t3://10.70.205.69:21041,10.70.205.69:21042,10.70.215.69:21043,10.70.215.69:21044\"/>");
			s1.append("     </env>");

			s1.append("     <env name=\"jndiTemplate5\" center=\"3\" bigdistrict=\"5\" group=\"normal\">");
			s1.append("       <property name=\"java.naming.factory.initial\" value=\"weblogic.jndi.WLInitialContextFactory\"/>");
			s1.append("       <property name=\"java.naming.provider.url\" value=\"t3://10.70.205.69:21051,10.70.205.69:21052,10.70.215.69:21053,10.70.215.69:21054\"/>");
			s1.append("     </env>");

			s1.append("     <env name=\"jndiTemplate6\" center=\"3\" bigdistrict=\"6\" group=\"normal\">");
			s1.append("       <property name=\"java.naming.factory.initial\" value=\"weblogic.jndi.WLInitialContextFactory\"/>");
			s1.append("       <property name=\"java.naming.provider.url\" value=\"t3://10.70.205.69:21061,10.70.205.69:21062,10.70.215.69:21063,10.70.215.69:21064\"/>");
			s1.append("     </env>");

			s1.append("     <env name=\"jndiTemplate7\" center=\"4\" bigdistrict=\"7\" group=\"normal\">");
			s1.append("       <property name=\"java.naming.factory.initial\" value=\"weblogic.jndi.WLInitialContextFactory\"/>");
			s1.append("       <property name=\"java.naming.provider.url\" value=\"t3://10.70.205.69:21071,10.70.205.69:21072,10.70.215.69:21073,10.70.215.69:21074\"/>");
			s1.append("     </env>");

			s1.append("     <env name=\"jndiTemplate8\" center=\"4\" bigdistrict=\"8\" group=\"normal\">");
			s1.append("       <property name=\"java.naming.factory.initial\" value=\"weblogic.jndi.WLInitialContextFactory\"/>");
			s1.append("       <property name=\"java.naming.provider.url\" value=\"t3://10.70.205.69:21081,10.70.205.69:21082,10.70.215.69:21083,10.70.215.69:21084\"/>");
			s1.append("     </env>");

			s1.append("     <env name=\"jndiTemplate0\" center=\"0\" group=\"cross\">");
			s1.append("       <property name=\"java.naming.factory.initial\" value=\"weblogic.jndi.WLInitialContextFactory\"/>");
			s1.append("       <property name=\"java.naming.provider.url\" value=\"t3://10.70.205.69:21001,10.70.205.69:21002,10.70.215.69:21003,10.70.215.69:21004\"/>");
			s1.append("     </env>");
			s1.append("</cluster>");
			s1.append("</app>");
			Cluster[] appClusters=SysResourceXmlHelper.getApp(s1.toString()).getClusters();
			/*
			for (int i = 0; i < appClusters.length; i++) {
				System.out.println(appClusters[i].getName());
			}
			*/
			StringBuilder s2=new StringBuilder();
			s2.append("<?xml version=\"1.0\" encoding=\"gb2312\"?>");
			s2.append("<client>");
			s2.append("<!--crm中心1-->");
			s2.append("	<connect name=\"crm-web-g1-srv1\"  			type=\"web\" appcluster=\"crm-app-g1\" desc=\"\" />");
			s2.append("	<connect name=\"crm-web-g1-srv2\"  			type=\"web\" appcluster=\"crm-app-g2\" desc=\"\" />");
			s2.append("	<connect name=\"crm-web-g1-srv3\"  			type=\"web\" appcluster=\"crm-app-g3\" desc=\"\" />");
			s2.append("	<connect name=\"crm-web-g1-srv4\"  			type=\"web\" appcluster=\"crm-app-g4\" desc=\"\" />");
			s2.append("	<connect name=\"crm-web-g1-srv5\"  			type=\"web\" appcluster=\"crm-app-g5\" desc=\"\" />");
			s2.append("	<connect name=\"crm-web-g1-srv6\"  			type=\"web\" appcluster=\"crm-app-g6\" desc=\"\" />");
			s2.append("	<connect name=\"crm-web-g1-srv7\"  			type=\"web\" appcluster=\"crm-app-g7\" desc=\"\" />");
			s2.append("</client>");
			Connect[] connects = SysResourceXmlHelper.getClient(s2.toString()).getConnects();
			/*
			for (int i = 0; i < connects.length; i++) {
				System.out.println(connects[i].getName());
			}
			*/
			Map result=SysResourceXmlHelper.resolveWeb2AppClusters(connects,appClusters);
			String code="";
			for (Iterator it=result.keySet().iterator();it.hasNext();){
				code=String.valueOf(it.next());
				List list=(List)result.get(code);
				for (int i=0;list!=null && list.size()>0 && i<list.size();i++){
					SimpleResult sr=(SimpleResult)list.get(i);
					if (sr!=null){
						System.out.println("Code:["+code+"]路由集群["+sr.getValue()+"]APP应用的IP:"+sr.getKey()+",端口:"+sr.getName());
					}
				}
			}
		} catch (Exception e) {

		}
	}
}
