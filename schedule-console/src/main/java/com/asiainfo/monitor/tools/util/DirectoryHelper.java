package com.asiainfo.monitor.tools.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.asiainfo.monitor.tools.common.TreeNodeInfo;
import com.asiainfo.monitor.tools.common.interfaces.INodeInfo;

public class DirectoryHelper {

	public static final String ROOT="ROOT";
	//树叶
	public static final String LEAF="LEAF";
	//树干
	public static final String TRUNK="TRUNK";
	
	/**
	 * 将字符串构建成树结构
	 * @param sb
	 * @return
	 * @throws Exception
	 */
	public static INodeInfo buildDirTreeNode(String sb) throws Exception{
		if (StringUtils.isBlank(sb))
			return null;
		String[] args=StringUtils.split(sb,",");
		INodeInfo rootNode=new TreeNodeInfo();
		Map tmpMap=new HashMap();
		
		for (int i=0;i<args.length;i++){
			String[] files=StringUtils.split(args[i],TypeConst._INTERPRET_CHAR);
			INodeInfo trunkNode=null;
			if (i>0){
				trunkNode=new TreeNodeInfo();
				trunkNode.setId(files[0]);
				String id=StringUtils.substring(files[0],0,files[0].lastIndexOf("/"));
				trunkNode.setType(TRUNK);
				if (tmpMap.containsKey(id)){
					trunkNode.setParent((INodeInfo)tmpMap.get(id));					
					((INodeInfo)tmpMap.get(id)).addChild(trunkNode);
				}else{
					trunkNode.setParent(rootNode);
					rootNode.addChild(trunkNode);
				}
				tmpMap.put(trunkNode.getId(), trunkNode);
			}else{
				rootNode.setId(files[0]);
				rootNode.setParent(null);
				rootNode.setType(ROOT);
				tmpMap.put(rootNode.getId(), rootNode);
			}
			for (int j=1;j<files.length;j++){
				INodeInfo itemNode=new TreeNodeInfo();
				itemNode.setId(files[j]);
				if (files[j].indexOf("/")>0){
					itemNode.setType(TRUNK);
				}else{
					itemNode.setType(LEAF);
				}
				if (i==0){
					itemNode.setParent(rootNode);
					rootNode.addChild(itemNode);
				}else{
					itemNode.setParent(trunkNode);
					trunkNode.addChild(itemNode);
				}
				
			}
		}
		return rootNode;
	}
	/*
	public static class PrintTreeOperate implements ITreeOperate{
		
		public PrintTreeOperate(){
			
		}
		
		public void operate(INodeInfo node) throws Exception{
			if (node!=null && node.hasChild()){
				Map child=(Map)node.getChild();
				for (Iterator it=child.keySet().iterator();it.hasNext();){
					String key=String.valueOf(it.next());
					INodeInfo item=(INodeInfo)child.get(key);
					if (item.hasChild()){
						System.out.println("目录:"+item.getId());
						operate(item);
					}else{
						System.out.println("上级目录:"+item.getParent().getId()+",文件:"+item.getId());
					}
				}
			}
		}
	}
	
	public static class WrapperTreeOperate implements ITreeOperate{
		
		private StringBuffer treeXml=new StringBuffer("");
		
		private AtomicInteger count=new AtomicInteger(0);
		public WrapperTreeOperate(){
			
		}
		
		public StringBuffer getTreeXml() {
			return treeXml;
		}

		public void setTreeXml(StringBuffer treeXml) {
			this.treeXml = treeXml;
		}

		public void operate(INodeInfo node) throws Exception{			
			if (node!=null && node.hasChild()){
				Map child=(Map)node.getChild();
				String rootDir=StringUtils.substring(node.getId(),node.getId().indexOf("/classes"));
				//根结点
				if (node.getType().equals(ROOT)){
					treeXml.append("<node label=\"").append(rootDir).append("\" id=\"").append(count.addAndGet(1)).append("\" >\n");
				}
				for (Iterator it=child.keySet().iterator();it.hasNext();){
					String key=String.valueOf(it.next());
					INodeInfo item=(INodeInfo)child.get(key);
					if (item.hasChild()){
//						System.out.println("目录:"+item.getId());
						String shortDir=StringUtils.substring(item.getId(),item.getId().indexOf("/classes"));
						String parentDir=StringUtils.substring(item.getParent().getId(),item.getParent().getId().indexOf("/classes"));
						treeXml.append("<node label=\"").append(shortDir).append("\" id=\"").append(count.addAndGet(1)).append("\">\n");
						operate(item);
					}else{
//						System.out.println("上级目录:"+item.getParent().getId()+",文件:"+item.getId());
						String shortDir=StringUtils.substring(item.getParent().getId(),item.getParent().getId().indexOf("/classes"));
						treeXml.append("<node label=\"").append(item.getId()).append("\" id=\"").append(count.addAndGet(1)).append("\"/>\n");
					}
				}
				treeXml.append("</node>");
			}
		}
	}
	*/
	public static void main(String[] args){
		try{
			String sb="/app/mon/develop/apache-tomcat-5.5.26/webapps/monitor/WEB-INF/classes:AIConfig.xml:AIRootConfig.xml:aisystem.ini:appmonitor.licenses:appmonitor_licenses.txt:log4j_exec."+
			          "properties:log4j.properties:log4j_table.properties:start_exec.sh:start.sh:start_table.sh:stop.sh:task.properties,/app/mon/develop/apache-tomcat-5.5.26/webapps/monitor/W"+
                      "EB-INF/classes/i18n:appframe_resource:appframe_resource_en_US.properties:appframe_resource_zh_CN.properties,/app/mon/develop/apache-tomcat-5.5.26/webapps/monitor/WEB-IN"+
                      "F/classes/system,/app/mon/develop/apache-tomcat-5.5.26/webapps/monitor/WEB-INF/classes/system/cache:cache.xml,/app/mon/develop/apache-tomcat-5.5.26/webapps/monitor/WEB-"+
                      "INF/classes/system/jmx:jmx.local.properties:jmx.properties:jmx.properties.bak:web_mbeans.xml:web_mbeans.xml.app:web_mbeans.xml.web,/app/mon/develop/apache-tomcat-5.5.26"+
                      "/webapps/monitor/WEB-INF/classes/system/service:defaults.xml:defaults.xml.bak1:defaults.xml.serve";
			INodeInfo root=DirectoryHelper.buildDirTreeNode(sb);
//			System.out.println("根目录:"+root.getId());
//			ITreeOperate operate=new DirectoryHelper.PrintTreeOperate();
//			((TreeNodeInfo)root).operate(operate);
//			ITreeOperate operate1=new DirectoryHelper.WrapperTreeOperate();
//			((TreeNodeInfo)root).operate(operate1);			
//			System.out.println(((DirectoryHelper.WrapperTreeOperate)operate1).getTreeXml());
		}catch(Exception e){
			
		}
	}
}
