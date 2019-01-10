package com.asiainfo.monitor.busi.config;



import java.util.Collections;
import java.util.List;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ai.appframe2.service.ServiceFactory;
import com.ai.appframe2.util.XmlUtil;
import com.asiainfo.monitor.busi.cache.interfaces.IGroup;
import com.asiainfo.monitor.busi.cache.interfaces.IServer;
import com.asiainfo.monitor.busi.cache.interfaces.IServerNode;
import com.asiainfo.monitor.busi.common.impl.GroupSeqComparator;
import com.asiainfo.monitor.busi.common.impl.TreeXmlStrategy;
import com.asiainfo.monitor.busi.common.interfaces.IObjectStrategy;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonGroupSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonNodeSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class TreeXmlFactory {

	/**
	 * 返回顶层树结构
	 * @return
	 * @throws Exception
	 */
	public static String getTopTreeXml() throws Exception{
		IObjectStrategy visitorStrategy=new TreeXmlStrategy();
		StringBuilder treeXml = new StringBuilder("<nodes label=\"");
		treeXml.append(AIMonLocaleFactory.getResource("MOS0000104")).append("\" id=\"-1\" icon=\"systemImage\" depth=\"0\" state=\"0\" isBranch=\"true\">\n");		
		try{
			IAIMonGroupSV groupSV=(IAIMonGroupSV)ServiceFactory.getService(IAIMonGroupSV.class);
			List groupList=groupSV.getAllGroup();
			if (groupList==null || groupList.size()<1)
				return treeXml.toString();
			String tmp = "";
			Collections.sort(groupList, new GroupSeqComparator());
			for (int i=0;i<groupList.size();i++){
				IGroup group=(IGroup)groupList.get(i);
				tmp = String.valueOf(visitorStrategy.visitGroup(group));
				treeXml.append(tmp);					
			}
			
		}catch(Exception e){
			throw new Exception("Call TreeXmlFactory's method getTopTreeXml has Exception :"+e.getMessage(),e);
		}finally{
			treeXml.append("</nodes>");
		}
		return treeXml.toString();
	}
	
	
	/**
	 * 返回整个树结构
	 * @return
	 * @throws Exception
	 */
	public static String getWholeTreeXml() throws Exception{
		String result="";
		IObjectStrategy visitorStrategy=new TreeXmlStrategy();
		StringBuilder treeXml = new StringBuilder("<nodes label=\"");
		treeXml.append(AIMonLocaleFactory.getResource("MOS0000104")).append("\" id=\"-1\" icon=\"systemImage\" depth=\"0\" state=\"0\" isBranch=\"true\">\n");	
		Element rootElement = DocumentHelper.parseText(new StringBuilder(treeXml).append("</nodes>").toString()).getRootElement();
		try{
			IAIMonGroupSV groupSV=(IAIMonGroupSV)ServiceFactory.getService(IAIMonGroupSV.class);
			List groupList=groupSV.getAllGroup();
			if (groupList==null || groupList.size()<1)
				return treeXml.toString();
			
			IAIMonNodeSV hostSV=(IAIMonNodeSV)ServiceFactory.getService(IAIMonNodeSV.class);
			IAIMonServerSV serverSV=(IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
			Collections.sort(groupList, new GroupSeqComparator());
			Element groupElement = null;
			Element hostElement = null;
			String tmp = "";
			for (int i=0;i<groupList.size();i++){
				IGroup group=(IGroup)groupList.get(i);
				tmp = String.valueOf(visitorStrategy.visitGroup(group));
				treeXml.append(tmp);
				groupElement =DocumentHelper.parseText(tmp).getRootElement();
				rootElement.add(groupElement);
				List hostValues=hostSV.getNodeByGroupId(group.getGroup_Id());
				
				if (null == hostValues || hostValues.size() < 1)
					continue;
				for (int j = 0;j < hostValues.size();j++){
					IServerNode serverNode=(IServerNode)hostValues.get(j);
					if (serverNode==null)
						continue;					
					tmp = String.valueOf(visitorStrategy.visitNode(serverNode));					
					hostElement = DocumentHelper.parseText(tmp).getRootElement();
					groupElement.add(hostElement);
					//读取应用的Tree xml
					List serverValues=serverSV.getAppServerConfigByNodeId(serverNode.getNode_Id());
					if (null == serverValues || serverValues.size() < 1)
						continue;
					for (int k = 0;k < serverValues.size();k++) {
						IServer server=(IServer)serverValues.get(k);
						tmp = String.valueOf(visitorStrategy.visitServer(server));
						hostElement.add(DocumentHelper.parseText(tmp).getRootElement());
					}
					
				}
			}
			
		}catch(Exception e){
			throw new Exception("Call TreeXmlFactory's method getWholeTreeXml has Exception :",e);
		}finally{
			treeXml.append("</nodes>");
		}
		result=XmlUtil.formatElement(rootElement);
		return result;
	}

}
