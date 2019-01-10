package com.asiainfo.monitor.busi.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.ai.appframe2.web.action.BaseAction;
import com.asiainfo.monitor.busi.cache.interfaces.IGroup;
import com.asiainfo.monitor.busi.cache.interfaces.IServer;
import com.asiainfo.monitor.busi.cache.interfaces.IServerNode;
import com.asiainfo.monitor.busi.config.ServerConfig;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonGroupSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonNodeSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonPhysicHostSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;
import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonGroupBean;
import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonNodeBean;
import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonServerBean;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonGroupValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonNodeValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonServerValue;
import com.asiainfo.monitor.interapi.ServerControlInfo;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.util.TypeConst;

/**
 * @author WDY
 *
 */
public class AppConfigAction extends BaseAction {

	private static transient Log log=LogFactory.getLog(AppConfigAction.class);
	
	
	
	/**
	 * 根据组标识获取主机信息
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	public List getNodeByGroupId(String groupId) throws Exception {
		if (StringUtils.isBlank(groupId))
			return null;
		List nodeValues=null;
		try{
			IAIMonNodeSV nodeSV=(IAIMonNodeSV)ServiceFactory.getService(IAIMonNodeSV.class);
			nodeValues=nodeSV.getNodeByGroupId(groupId);			
		}catch(Exception e){
			log.error("Call AppConfigAction's Method getHostByGroupId has Exception :"+e.getMessage());
			throw e;
		}
		return nodeValues;
	}
	
	/**
	 * 根据组标识、主机名、匹配值获取主机信息
	 * @param groupId
	 * @param hostName
	 * @param matchWhole
	 * @return
	 * @throws Exception
	 */
	public List getNodeByGroupIdAndName(String groupId,String nodeName,Boolean matchWhole) throws Exception{
		if (StringUtils.isBlank(groupId))
			return null;
		List nodeValues=null;
		try{
			IAIMonNodeSV nodeSV=(IAIMonNodeSV)ServiceFactory.getService(IAIMonNodeSV.class);
			nodeValues=nodeSV.getNodeByGrpIdAndName(groupId,nodeName,matchWhole.booleanValue());			
		}catch(Exception e){
			log.error("Call AppConfigAction's Method getHostByGroupIdAndName has Exception ",e);
			throw e;
		}
		return nodeValues;
	}
	
	
	public List getNodeByGroupIdAndNameNocache(String groupId,String nodeName,Boolean matchWhole) throws Exception{
		if (StringUtils.isBlank(groupId))
			return null;
		List nodeValues=null;
		try{
			IAIMonNodeSV nodeSV=(IAIMonNodeSV)ServiceFactory.getService(IAIMonNodeSV.class);
			nodeValues=nodeSV.getNodeByGrpIdAndNameNocache(groupId,nodeName,matchWhole.booleanValue());			
		}catch(Exception e){
			log.error("Call AppConfigAction's Method getHostByGroupIdAndNameNocache has Exception ",e);
			throw e;
		}
		return nodeValues;
	}
	
	/**
	 * 构建全局结构饼图Xml
	 * @return
	 * @throws Exception
	 */
	public String getWholeFramePieXml() throws Exception{
		String result="";
		try{
			IAIMonGroupSV groupSV=(IAIMonGroupSV)ServiceFactory.getService(IAIMonGroupSV.class);
			result=groupSV.getWholeFramePieXml();
		}catch(Exception e){
			log.error("Call AppConfigAction's Method getWholeFramePieXml has Exception :"+e.getMessage());
			throw e;
		}
		return result;
	}
	
	/**
	 * 获取组(金字塔形拓扑图)
	 * @return
	 * @throws Exception
	 */
	public String getTopTopuXml() throws Exception {
		String result="";
		try{
			String userId="";
//			if(FlexSessionManager.getUser() != null)
//				userId = FlexSessionManager.getUser().getId();
			if (StringUtils.isBlank(userId))
				// "没有登陆用户信息"
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000002"));
			IAIMonGroupSV groupSV=(IAIMonGroupSV)ServiceFactory.getService(IAIMonGroupSV.class);
			result=groupSV.getTopuXmlByUserId(userId);
		}catch(Exception e){
			log.error("Call AppConfigAction's Method getTopuXmlBySessionUser has Exception :"+e.getMessage());
			throw e;
		}
		return result;
	}
	
	/**
	 * 根据组id和主机id以及区域标识获取下属服务节点的xml
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String getNodeTopuXmlByGroupId(String groupId) throws Exception {
		String result = "";
		try{
			if (StringUtils.isBlank(groupId)){
				// "没有传入主机参数或参数格式错误"
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000003"));
				
			}
			String userId="";
//			if(FlexSessionManager.getUser() != null)
//				userId = FlexSessionManager.getUser().getId();
			if (StringUtils.isBlank(userId))
				// "没有登陆用户信息"
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000002"));
			IAIMonNodeSV nodeSV=(IAIMonNodeSV)ServiceFactory.getService(IAIMonNodeSV.class);
			result=nodeSV.getNodeTopuXmlByGroupIdAndUserId(groupId,userId);
		} catch (Exception e) {
			log.error("Call AppConfigAction's Method getHostTopuXmlByGroupId has Exception :"+e.getMessage());
			throw e;
		}
		return result;
	}
	
	
	/**
	 * 根据组id和主机id以及区域标识获取下属服务节点的xml
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String getServerTopuXmlByNodeId(String nodeId) throws Exception {
		String result = "";
		try{
			if (StringUtils.isBlank(nodeId)){
				// "没有传入主机参数或参数格式错误"
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000003"));
				
			}
			String userId="";
//			if(FlexSessionManager.getUser() != null)
//				userId = FlexSessionManager.getUser().getId();
			if (StringUtils.isBlank(userId))
				// "没有登陆用户信息"
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000002"));
			IAIMonServerSV serverSV=(IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
			result=serverSV.getServerTopuXmlByNodeIdAndUserId(nodeId,userId);
		} catch (Exception e) {
			log.error("Call AppConfigAction's Method getServerTopuXmlByHostId has Exception :"+e.getMessage());
			throw e;
		}
		return result;
	}
	
	/**
	 * 根据条件查询应用信息,拼成xml传给Topu展示
	 * @param serverName
	 * @return
	 * @throws Exception
	 */
	public String getServerTopuXmlByName(String serverName) throws Exception {
		String result="";
		try{
			String userId="";
//			if(FlexSessionManager.getUser() != null)
//				userId = FlexSessionManager.getUser().getId();
			if (StringUtils.isBlank(userId))
				// "没有登陆用户信息"
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000002"));
			IAIMonServerSV serverSV=(IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
			result=serverSV.getServerTopuXmlByName(serverName,userId);
		}catch(Exception e){
			log.error("Call AppConfigAction's Method getServerTopuXmlByName has Exception :"+e.getMessage());
			throw e;
		}
		return result;
	}

	/**
	 * 获取所有组信息XML作为树节点
	 * @return List
	 * @throws Exception
	 */
	public String getAllGroupTreeXml() throws Exception{
		String result = null;
		try{
			IAIMonGroupSV groupSV=(IAIMonGroupSV)ServiceFactory.getService(IAIMonGroupSV.class);			
			result=groupSV.getAllGroupTreeXml();
		}catch (Exception e) {
			log.error("Call AppConfigAction's Method getAllGroupTreeXml has Exception :"+e.getMessage());
			throw e;
		}
		return result;
	}
	
	public String getAllGroupTreeXmlNocache() throws Exception {
		String result = null;
		try{
			IAIMonGroupSV groupSV=(IAIMonGroupSV)ServiceFactory.getService(IAIMonGroupSV.class);			
			result=groupSV.getAllGroupTreeXmlNocache();
		}catch (Exception e) {
			log.error("Call AppConfigAction's Method getAllGroupTreeXml has Exception :"+e.getMessage());
			throw e;
		}
		return result;
	}
	
	/**
	 * 获取主机信息XML作为树节点
	 * @param groupId
	 * @return List
	 * @throws Exception
	 */
	public List getNodeTreeXmlByGrpId(String groupId) throws Exception {
		if (StringUtils.isBlank(groupId))
			return null;
		List result = null;
		try{
			IAIMonNodeSV nodeSV=(IAIMonNodeSV)ServiceFactory.getService(IAIMonNodeSV.class);
			result=nodeSV.getNodeTreeXmlByGrpId(groupId);
		} catch (Exception e) {
			log.error("Call AppConfigAction's Method getNodeTreeXmlByGrpId has Exception :"+e.getMessage());
			throw e;
		}
		return result;
	}
	
	public List getNodeTreeXmlByGrpIdNocache(String groupId) throws Exception {
		if (StringUtils.isBlank(groupId))
			return null;
		List result = null;
		try{
			IAIMonNodeSV nodeSV=(IAIMonNodeSV)ServiceFactory.getService(IAIMonNodeSV.class);
			result=nodeSV.getNodeTreeXmlByGrpIdNocache(groupId);
		} catch (Exception e) {
			log.error("Call AppConfigAction's Method getHostTreeXmlByGrpId has Exception :"+e.getMessage());
			throw e;
		}
		return result;
	}
	
	/**
	 * 获取应用信息XML作为树节点
	 * @param hostId
	 * @return List
	 * @throws Exception
	 */
	public List getServerTreeXmlByNodeId(String nodeId) throws Exception {
		if (StringUtils.isBlank(nodeId))
			return null;
		List result = null;
		try{
			IAIMonServerSV serverSV=(IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
			result=serverSV.getServerTreeXmlByNodeId(nodeId);
		} catch (Exception e) {
			log.error("Call AppConfigAction's Method getServerTreeXmlByHostId has Exception :"+e.getMessage());
			throw e;
		}
		return result;
	}

	/**
	 * 获取应用信息XML作为树节点
	 * @param hostId
	 * @return List
	 * @throws Exception
	 */
	public List getServerTreeXmlByNodeIdNocache(String nodeId) throws Exception {
		if (StringUtils.isBlank(nodeId))
			return null;
		List result = null;
		try{
			IAIMonServerSV serverSV=(IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
			result=serverSV.getServerTreeXmlByNodeIdNocache(nodeId);
		} catch (Exception e) {
			log.error("Call AppConfigAction's Method getServerTreeXmlByHostId has Exception :"+e.getMessage());
			throw e;
		}
		return result;
	}
	
	/**
	 * 根据组标识获取应用服务信息和统计信息
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	public List getAppServerByGroupId(String groupId) throws Exception {
		if (StringUtils.isBlank(groupId))
			return null;
		List appValues = null;
		int abnormal = 0;
		try{
			IAIMonNodeSV hostSV=(IAIMonNodeSV)ServiceFactory.getService(IAIMonNodeSV.class); 
			List hostValues=hostSV.getNodeByGroupId(groupId);
			
			if (hostValues!=null && hostValues.size()>0){
				IAIMonServerSV serverSV=(IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
				appValues = new ArrayList();
				for (int j=0;j<hostValues.size();j++){
					List serverValues=serverSV.getAppServerConfigByNodeId(((IServerNode)hostValues.get(j)).getNode_Id());
					if (serverValues!=null && serverValues.size()>0){
						for (int k=0;k<serverValues.size();k++){
							appValues.add(serverValues.get(k));
							if ("E".equals(((IServer)serverValues.get(k)).getState())) 
								abnormal++;
						}
					}
				}
			}
			Map hm = new HashMap();
			hm.put("serverCount", new Integer(hostValues.size()));
			hm.put("appCount", new Integer(appValues.size()));
			hm.put("abnormal", new Integer(abnormal));
			appValues.add(hm);
		}catch(Exception e){
			log.error("Call AppConfigAction's Method getAppServerByGroupId has Exception :"+e.getMessage());
			throw e;
		}
		return appValues;
	}
	
	/**
	 * 根据主机标识获取应用服务信息和统计信息
	 * @param hostId
	 * @return
	 * @throws Exception
	 */
	public List getServerInfoByNodeId(String nodeId) throws Exception {
		if (StringUtils.isBlank(nodeId))
			return null;
		List appValues = null;
		int abnormal = 0;
		try{
			appValues = new ArrayList();
			IAIMonServerSV serverSV=(IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
			List serverValues=serverSV.getAppServerConfigByNodeId(nodeId);
			if (serverValues!=null && serverValues.size()>0){
				for (int k=0;k<serverValues.size();k++){
					appValues.add(serverValues.get(k));
					if ("U".equals(((IServer)serverValues.get(k)).getState())) 
						abnormal++;
				}
			}	
			Map hm = new HashMap();
			hm.put("appCount", new Integer(appValues.size()));
			hm.put("abnormal", new Integer(abnormal));
			appValues.add(hm);
		}catch(Exception e){
			log.error("Call AppConfigAction's Method getServerInfoByHostId has Exception :"+e.getMessage());
			throw e;
		}
		return appValues;
	}
	
	
	/**
	 * 根据组名获取组信息
	 * @param groupName
	 * @return
	 * @throws Exception
	 */
	public List getGroupByName(String groupName,Boolean matchWhole) throws Exception {
		IAIMonGroupSV groupSV = (IAIMonGroupSV) ServiceFactory.getService(IAIMonGroupSV.class);
		return groupSV.getGroupByName(groupName.trim(),matchWhole.booleanValue());
	}
	/**
	 * 根据组名获取组信息
	 * @param groupName
	 * @return
	 * @throws Exception
	 */
	public List getGroupByNameNocache(String groupName,Boolean matchWhole) throws Exception {
		IAIMonGroupSV groupSV = (IAIMonGroupSV) ServiceFactory.getService(IAIMonGroupSV.class);
		return groupSV.getGroupByNameNocache(groupName.trim(),matchWhole.booleanValue());
	}
	
	/**
	 * 保存组信息
	 * @param groupInfo
	 * @throws Exception
	 */
	public void saveGroup (Object[] groupInfo) throws Exception{
		if (groupInfo==null || groupInfo.length<=0)
			return;
		try{
			IBOAIMonGroupValue groupValue=new BOAIMonGroupBean();
			if (StringUtils.isBlank(groupInfo[0].toString())) {
				groupValue.setStsToNew();
			} else {
				groupValue.setGroupId(Long.parseLong(groupInfo[0].toString().trim()));
				groupValue.setStsToOld();
			}
			groupValue.setGroupName(groupInfo[1].toString().trim());
			groupValue.setGroupDesc(groupInfo[2].toString().trim());
			groupValue.setState(groupInfo[3].toString().trim());
			IAIMonGroupSV groupSV = (IAIMonGroupSV) ServiceFactory.getService(IAIMonGroupSV.class);
			groupSV.saveOrUpdate(groupValue,groupInfo[4].toString().trim());
		}catch(Exception e){
			log.error("Call AppConfigAction's Method saveGroup has Exception :"+e.getMessage());
			throw e;
		}				
	}
	
	/**
	 * 删除组信息
	 * @param groupId
	 * @throws Exception
	 */
	public void deleteGroup(String groupId) throws Exception{
		if (StringUtils.isBlank(groupId))
			return;
		try{
			IAIMonGroupSV groupSV = (IAIMonGroupSV) ServiceFactory.getService(IAIMonGroupSV.class);
			groupSV.deleteGroup(Long.parseLong(groupId));
		}catch(Exception e){
			log.error("Call AppConfigAction's Method deleteGroup has Exception :"+e.getMessage());
			throw e;
		}	
	}
	
	/**
	 * 保存主机信息
	 * @param hostInfo
	 * @throws Exception
	 */
	public void saveNode (Object[] nodeInfo) throws Exception{
		if (nodeInfo==null || nodeInfo.length<=0)
			return;
		try{
			IBOAIMonNodeValue nodeValue=new BOAIMonNodeBean();
			
			if (StringUtils.isBlank(nodeInfo[0].toString())) {
				nodeValue.setStsToNew();
			} else {
				nodeValue.setNodeId(Long.parseLong(nodeInfo[0].toString().trim()));
				nodeValue.setStsToOld();
			}
			nodeValue.setNodeName(nodeInfo[1].toString().trim());
            //nodeValue.setGroupId(Long.parseLong(nodeInfo[2].toString().trim()));
			nodeValue.setState(nodeInfo[3].toString().trim());
			nodeValue.setRemark(nodeInfo[4].toString().trim());
			IAIMonNodeSV serverSV = (IAIMonNodeSV) ServiceFactory.getService(IAIMonNodeSV.class);
			serverSV.saveOrUpdate(nodeValue);

		}catch(Exception e){
			log.error("Call AppConfigAction's Method saveNode has Exception :"+e.getMessage());
			throw e;
		}				
	}
	
	/**
	 * 删除主机信息
	 * @param hostId
	 * @throws Exception
	 */
	public void deleteNode(String nodeId) throws Exception{
		if (StringUtils.isBlank(nodeId))
			return;
		try{
			IAIMonNodeSV nodeSV = (IAIMonNodeSV) ServiceFactory.getService(IAIMonNodeSV.class);
			nodeSV.deleteNode(Long.parseLong(nodeId));
		}catch(Exception e){
			log.error("Call AppConfigAction's Method deleteHost has Exception :"+e.getMessage());
			throw e;
		}
	}
	
	
	
	/**
	 * 根据主机获取应用信息
	 * 
	 * @param hostId
	 * @return
	 * @throws Exception
	 */
	public List getServerByNodeId(String nodeId) throws Exception {
		IAIMonServerSV appSV = (IAIMonServerSV) ServiceFactory.getService(IAIMonServerSV.class);
		List result = appSV.getAppServerConfigByNodeId(nodeId);		
		return result;
	}

	/**
	 * 根据主机获取应用信息
	 * 
	 * @param hostId
	 * @return
	 * @throws Exception
	 */
	public List getServerByNodeIdNocache(String nodeId) throws Exception {
		IAIMonServerSV appSV = (IAIMonServerSV) ServiceFactory.getService(IAIMonServerSV.class);
		List result = appSV.getAppServerConfigByNodeIdNocache(nodeId);		
		return result;
	}
	
	
	/**
	 * 保存应用信息
	 * @param serverInfo
	 * @throws Exception
	 */
	public void saveServer (Object[] serverInfo) throws Exception{
		if (serverInfo==null || serverInfo.length<=0)
			return;
		try{
			IBOAIMonServerValue serverValue=new BOAIMonServerBean();
			
			if (StringUtils.isBlank(serverInfo[0].toString())) {
				serverValue.setStsToNew();
			} else {
				serverValue.setServerId(Long.parseLong(serverInfo[0].toString().trim()));
				serverValue.setStsToOld();
			}
			serverValue.setServerCode(serverInfo[0].toString().trim());
			serverValue.setServerName(serverInfo[1].toString().trim());
			serverValue.setLocatorType(serverInfo[2].toString().trim());
			serverValue.setLocator(serverInfo[3].toString().trim());
			serverValue.setCheckUrl(serverInfo[4].toString().trim());
			serverValue.setNodeId(Long.parseLong(serverInfo[5].toString().trim()));
			if (StringUtils.isNotBlank(serverInfo[6].toString())) {
                serverValue.setStartCmdId(Short.parseShort(serverInfo[6].toString().trim()));
			}
			if (StringUtils.isNotBlank(serverInfo[7].toString())) {
                serverValue.setStopCmdId(Short.parseShort(serverInfo[7].toString().trim()));
			}
			serverValue.setMidwareType(serverInfo[8].toString().trim());
			serverValue.setState(serverInfo[9].toString().trim());
			serverValue.setRemark(serverInfo[10].toString().trim());
			
			serverValue.setServerCode(serverInfo[12].toString().trim());
			serverValue.setServerIp(serverInfo[13].toString().trim());
            serverValue.setServerPort(Short.parseShort(serverInfo[14].toString().trim()));
			serverValue.setTempType(serverInfo[15].toString().trim());
			if(StringUtils.isBlank(serverInfo[16].toString().trim()))
				// "JMX是否开启不能为空!"
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000004"));
            //			serverValue.setHostId(Long.parseLong(serverInfo[17].toString().trim()));
			IAIMonServerSV serverSV = (IAIMonServerSV) ServiceFactory.getService(IAIMonServerSV.class);
			serverSV.saveOrUpdate(serverValue,serverInfo[11].toString().trim(),serverInfo[16].toString().trim());
			
		}catch(Exception e){
			log.error("Call AppConfigAction's Method saveServer has Exception :"+e.getMessage());
			throw e;
		}				
	}
	
	/**
	 * 删除应用信息
	 * @param appServerId
	 * @throws Exception
	 */
	public void deleteServer(String serverId) throws Exception{
		if (StringUtils.isBlank(serverId))
			return;
		try{
			IAIMonServerSV serverSV = (IAIMonServerSV) ServiceFactory.getService(IAIMonServerSV.class);
			serverSV.deleteServer(Long.parseLong(serverId));
		}catch(Exception e){
			log.error("Call AppConfigAction's Method deleteServer has Exception :"+e.getMessage());
			throw e;
		}
	}
	
	
	/**
	 * 根据应用ID取得应用信息
	 * 
	 * @param serverId
	 * @return
	 * @throws Exception
	 */
	public ServerConfig getServerInfoById(String serverId) throws Exception {
		IAIMonServerSV serverSV = (IAIMonServerSV) ServiceFactory.getService(IAIMonServerSV.class);
		ServerConfig result = serverSV.getServerByServerId(serverId);
		return result;
	}
	/**
	 * 根据应用ID取得应用信息
	 * 
	 * @param serverId
	 * @return
	 * @throws Exception
	 */
	public ServerConfig getServerInfoByIdNocache(String serverId) throws Exception {
		IAIMonServerSV serverSV = (IAIMonServerSV) ServiceFactory.getService(IAIMonServerSV.class);
		ServerConfig result = serverSV.getServerByServerIdNocache(serverId);
		return result;
	}
	
	/**
	 * 根据应用标识集获取应用信息
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public List getServersByIds(Object[] ids) throws Exception{
		List result = null;
		try{
			IAIMonServerSV serverSV = (IAIMonServerSV) ServiceFactory.getService(IAIMonServerSV.class);
			result = new ArrayList();
			for (int i=0;i<ids.length;i++){
				IServer server = serverSV.getServerByServerId(String.valueOf(ids[i]));
				result.add(server);
			}
			ServerControlInfo[] serverInfo = new ServerControlInfo[ids.length];
			ServerConfig server = null;
			for (int i = 0; i < result.size(); i++) {
				server = (ServerConfig)result.get(i);
				serverInfo[i] = new ServerControlInfo();
				serverInfo[i].setGrpName(server.getNodeConfig().getGroupConfig().getGroup_Name());
				serverInfo[i].setHostname(server.getNodeConfig().getNode_Name());
				serverInfo[i].setServerName(server.getApp_Name());
				serverInfo[i].setServerId(server.getApp_Code());
				serverInfo[i].setUrl(server.getServer().getCheck_Url());
			}
			serverInfo = serverSV.getServerStatus(serverInfo);
//			result = new ArrayList();
			for (int i = 0; i < result.size(); i++) {
				server = (ServerConfig)result.get(i);
				for (int j = 0; j < serverInfo.length; j++) {
					if (server.getApp_Code().equals(serverInfo[j].getServerId())) {
						server.getServer().setState(serverInfo[j].getStatus());
					}
				}
//				server.getServer().setSversion(serverInfo[i].getInfo());
//				result.add(server);
			}
		}catch(Exception e){
			throw new Exception("Call AppConfigAction's Method getServersByIds has Exception :"+e.getMessage());
		}
		return result;
	}
	/**
	 * 根据应用标识集获取应用信息
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public List getServersByIdsNocache(Object[] ids) throws Exception{
		List result=null;
		try{
			IAIMonServerSV serverSV = (IAIMonServerSV) ServiceFactory.getService(IAIMonServerSV.class);
			result=new ArrayList();
			for (int i=0;i<ids.length;i++){
				IServer server=serverSV.getServerByServerIdNocache(String.valueOf(ids[i]));
				result.add(server);
			}
		}catch(Exception e){
			throw new Exception("Call AppConfigAction's Method getServersByIdsNocache has Exception :"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 根据区域ID取得所有组中服务的统计信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getServerStatInGroupBySessionUser() throws Exception {
		StringBuilder treeXml=new StringBuilder();
		try{
			String userId="";
//			if(FlexSessionManager.getUser() != null)
//				userId = FlexSessionManager.getUser().getId();
			if (StringUtils.isBlank(userId))
				// "没有登陆用户信息"
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000002"));
			IAIMonGroupSV groupSV=(IAIMonGroupSV)ServiceFactory.getService(IAIMonGroupSV.class);
			
			List groupValues =groupSV.getAllGroupByUserId(userId);
			
			if (groupValues != null && groupValues.size() > 0) {
				IAIMonNodeSV hostSV=(IAIMonNodeSV)ServiceFactory.getService(IAIMonNodeSV.class); 
				// 系统
				treeXml.append("<Node name=\"").append(AIMonLocaleFactory.getResource("MOS0000104")).append("\" id=\"-1\" nodeClass=\"root\">");
				for (int i=0;i<groupValues.size();i++){
					treeXml.append("<Node name=\"").append(((IGroup)groupValues.get(i)).getGroup_Name());
					treeXml.append("\" id=\"").append(((IGroup)groupValues.get(i)).getGroup_Id());
					treeXml.append("\" nodeClass=\"group\"> ");
					List nodeValues=hostSV.getNodeByGroupId(((IGroup)groupValues.get(i)).getGroup_Id()+"");
					if (nodeValues!=null && nodeValues.size()>0){
						int webCount = 0;
						int appCount = 0;
						int otherCount = 0;
						IAIMonServerSV serverSV=(IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
						for (int j=0;j<nodeValues.size();j++){
							List serverValues=serverSV.getAppServerConfigByNodeIdAndUserId(((IServerNode)nodeValues.get(j)).getNode_Id(),userId);
							if (serverValues!=null && serverValues.size()>0){
								for (int k=0;k<serverValues.size();k++){
									if ((TypeConst.WEB).equals(((IServer)serverValues.get(k)).getTemp_Type())) {
										webCount++;
									} else if ((TypeConst.APP).equals(((IServer)serverValues.get(k)).getTemp_Type())) {
										appCount++;
									} else if ((TypeConst.OTHER).equals(((IServer)serverValues.get(k)).getTemp_Type())) {
										otherCount++;
									}
								}
							}
						}
						treeXml.append("<Node name=\"").append(TypeConst.WEB);
						treeXml.append("\" id=\"").append(((IGroup)groupValues.get(i)).getGroup_Id());
						treeXml.append("\" nodeClass=\"server\" amount=\"").append(webCount).append("\" /> ");
						treeXml.append("<Node name=\"").append(TypeConst.APP);
						treeXml.append("\" id=\"").append(((IGroup)groupValues.get(i)).getGroup_Id());
						treeXml.append("\" nodeClass=\"server\" amount=\"").append(appCount).append("\" /> ");
						treeXml.append("<Node name=\"").append(TypeConst.OTHER);
						treeXml.append("\" id=\"").append(((IGroup)groupValues.get(i)).getGroup_Id());
						treeXml.append("\" nodeClass=\"server\" amount=\"").append(otherCount).append("\" /> ");
					}
					treeXml.append("</Node>");
				}
				treeXml.append("</Node>");
			}
		} catch (Exception e) {
			log.error("Call AppConfigAction's Method getServerStatInGroupBySessionUser has Exception :"+e.getMessage());
		}
		return treeXml.toString();
	}	
	
	/**
	 * 根据字符串解析按组标识或主机标识获得应用标识
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public List getServerIdByGroupIdOrNodeId(Object[] ids) throws Exception{
		List result=null;
		try{
			if (ids==null || ids.length<1)
				// "没有传入参数"
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000005"));
			String userId="";
//			if(FlexSessionManager.getUser() != null)
//				userId = FlexSessionManager.getUser().getId();
			if (StringUtils.isBlank(userId))
				// "没有登陆用户信息"
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000002"));
			
			List servers=new ArrayList();
			IAIMonServerSV serverSV=(IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
			IAIMonNodeSV hostSV=(IAIMonNodeSV)ServiceFactory.getService(IAIMonNodeSV.class);
			for (int i=0;i<ids.length;i++){
				if (String.valueOf(ids[i]).indexOf("_")<0)
					continue;
				String[] idTypeStr=StringUtils.split(String.valueOf(ids[i]),"_");
				if (idTypeStr.length>2)
					continue;
				if (idTypeStr[1].toUpperCase().equals(TypeConst.HOST)){
					List nodeServers=serverSV.getAppServerConfigByNodeIdAndUserId(idTypeStr[0],userId);
					servers.addAll(nodeServers);
				}else if (idTypeStr[1].toUpperCase().equals(TypeConst.GROUP)){
					List hosts=hostSV.getNodeByGroupId(idTypeStr[0]);
					if (hosts!=null && hosts.size()>0){
						for (int j=0;j<hosts.size();j++){
							List hostServers=serverSV.getAppServerConfigByNodeIdAndUserId( ((IServerNode)hosts.get(j)).getNode_Id(),userId);
							servers.addAll(hostServers);
						}
					}
				}else{
					IServer item=serverSV.getServerByServerId(idTypeStr[0]);
					if (item!=null){
						servers.add(item);
					}
					
				}
				
			}
			if (servers!=null || servers.size()>0){
				result=new ArrayList();
				for (int i=0;i<servers.size();i++){
					if (!result.contains(( (IServer)servers.get(i)).getApp_Id()))
						result.add( ( (IServer)servers.get(i)).getApp_Id() + TypeConst._SPLIT_CHAR + ( (IServer)servers.get(i)).getApp_Name() );
				}
			}
		}catch(Exception e){
			throw new Exception("Call AppConfigAction's Method getServerIdByGroupIdOrHostId has Exception :"+e.getMessage());
		}
		return result;
	}
	
	public List getServerInfo(String groupName,String hostName,String serverName) throws Exception {
		IAIMonServerSV serverSV = (IAIMonServerSV) ServiceFactory.getService(IAIMonServerSV.class);
		
		return serverSV.getServerInfo(groupName,hostName,serverName);
		
	}
	
	public List getServerIdsByType(String tempType) throws Exception {
		IAIMonServerSV serverSV = (IAIMonServerSV) ServiceFactory.getService(IAIMonServerSV.class);
		
		return serverSV.getServerIdsByType(tempType);
	}
	
	public List getServerInfoNocache(String groupName,String hostName,String serverName) throws Exception {
		IAIMonServerSV serverSV = (IAIMonServerSV) ServiceFactory.getService(IAIMonServerSV.class);
		
		return serverSV.getServerInfoNocache(groupName,hostName,serverName);
		
	}
	
	public String getWholeTreeXml() throws Exception{
		
		IAIMonGroupSV groupSV = (IAIMonGroupSV)ServiceFactory.getService(IAIMonGroupSV.class);
		return groupSV.getWholeTreeXml();
		
	}
	
	/**
	 * 模糊查询主机信息
	 * @param hostName
	 * @param matchWhole
	 * @return 返回组-主机树结构
	 * @throws Exception
	 */
	public String getNodeXmlByName(String nodeName, Boolean matchWhole) throws Exception {
		IAIMonNodeSV nodeSV = (IAIMonNodeSV)ServiceFactory.getService(IAIMonNodeSV.class);
		String result=nodeSV.getNodeXmlByName(nodeName,matchWhole);
		return result;
	}
	
	/**
	 * 获取系统结构信息（组、主机、各类型应用数）
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	public List getSystemStructureInfo() throws Exception {
		List result = null;
		try{
			int groupCount = 0;
			int hostCount = 0;
			int webCount = 0;
			int appCount = 0;
			int otherCount = 0;
			IAIMonGroupSV groupSV=(IAIMonGroupSV)ServiceFactory.getService(IAIMonGroupSV.class);
			List groupValues =groupSV.getAllGroup();
			if (groupValues != null && groupValues.size() > 0) {
				IAIMonNodeSV hostSV=(IAIMonNodeSV)ServiceFactory.getService(IAIMonNodeSV.class); 
				for (int i=0;i<groupValues.size();i++){
					groupCount++;
					List nodeValues=hostSV.getNodeByGroupId(((IGroup)groupValues.get(i)).getGroup_Id()+"");
					if (nodeValues!=null && nodeValues.size()>0){
						IAIMonServerSV serverSV=(IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
						for (int j=0;j<nodeValues.size();j++){
							hostCount++;
							List serverValues=serverSV.getAppServerConfigByNodeId(((IServerNode)nodeValues.get(j)).getNode_Id());
							if (serverValues!=null && serverValues.size()>0){
								for (int k=0;k<serverValues.size();k++){
									if ((TypeConst.WEB).equals(((IServer)serverValues.get(k)).getTemp_Type())) {
										webCount++;
									} else if ((TypeConst.APP).equals(((IServer)serverValues.get(k)).getTemp_Type())) {
										appCount++;
									} else if ((TypeConst.OTHER).equals(((IServer)serverValues.get(k)).getTemp_Type())) {
										otherCount++;
									}
								}
							}
						}
					}
				}
			}
			Map hm = new HashMap();
			hm.put("group", new Integer(groupCount));
			hm.put("host", new Integer(hostCount));
			hm.put("web", new Integer(webCount));
			hm.put("app", new Integer(appCount));
			hm.put("other", new Integer(otherCount));
			result=new ArrayList();
			result.add(hm);
		}catch(Exception e){
			log.error("Call AppConfigAction's Method getSystemStructureInfo has Exception :"+e.getMessage());
			throw e;
		}
		return result;
	}
	
	/**
	 * 根据组id获取下属物理主机的xml
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String getPHostTopuXmlByGroupId(String groupId) throws Exception {
		String result = "";
		try{
			if (StringUtils.isBlank(groupId)){
				// "没有传入主机参数或参数格式错误"
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000003"));
				
			}
			String userId="";
//			if(FlexSessionManager.getUser() != null)
//				userId = FlexSessionManager.getUser().getId();
			if (StringUtils.isBlank(userId))
				// "没有登陆用户信息"
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000002"));
			IAIMonPhysicHostSV hostSV=(IAIMonPhysicHostSV)ServiceFactory.getService(IAIMonPhysicHostSV.class);
			result=hostSV.getHostTopuXmlByGroupIdAndUserId(groupId,userId);
		} catch (Exception e) {
			log.error("Call AppConfigAction's Method getPHostTopuXmlByGroupId has Exception :"+e.getMessage());
			throw e;
		}
		return result;
	}
}
