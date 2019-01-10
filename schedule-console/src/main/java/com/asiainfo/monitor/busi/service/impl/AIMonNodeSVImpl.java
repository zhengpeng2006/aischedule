package com.asiainfo.monitor.busi.service.impl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.ailk.common.data.IDataBus;
import com.ailk.common.data.IDataContext;
import com.ailk.common.data.impl.DataBus;
import com.ailk.common.data.impl.DataContext;
import com.asiainfo.deploy.api.external.DeployStrategyUtils;
import com.asiainfo.deploy.common.bo.BODeployStrategyBean;
import com.asiainfo.monitor.busi.cache.AIMonNodeCheckListener;
import com.asiainfo.monitor.busi.cache.MonCacheManager;
import com.asiainfo.monitor.busi.cache.impl.AIMonNodeCacheImpl;
import com.asiainfo.monitor.busi.cache.impl.ServerNode;
import com.asiainfo.monitor.busi.cache.interfaces.IServerNode;
import com.asiainfo.monitor.busi.common.impl.NodeComparator;
import com.asiainfo.monitor.busi.common.impl.TopuXmlStrategy;
import com.asiainfo.monitor.busi.common.impl.TreeXmlStrategy;
import com.asiainfo.monitor.busi.common.interfaces.IObjectStrategy;
import com.asiainfo.monitor.busi.config.ServerNodeConfig;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonGroupSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonNodeSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;
import com.asiainfo.monitor.exeframe.configmgr.abo.dao.interfaces.IAIMonNodeDAO;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonNodeValue;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class AIMonNodeSVImpl implements IAIMonNodeSV{

	private static transient Log log=LogFactory.getLog(AIMonNodeSVImpl.class);
	/**
	 * 根据服务器标识，读取服务器信息
	 * @param nodeId
	 * @return
	 * @throws Exception
	 */
	public ServerNodeConfig getNodeByNodeId(String nodeId) throws RemoteException,Exception{
		ServerNode result=(ServerNode)MonCacheManager.get(AIMonNodeCacheImpl.class,nodeId);
		if (result==null){			
			result=this.getNodeByIdFormDb(nodeId);
			if (!MonCacheManager.getCacheReadOnlySet() && result!=null){
				MonCacheManager.put(AIMonNodeCacheImpl.class,result.getNode_Id(),result);
			}
		}
		return new ServerNodeConfig(result);
	}
	
	/**
	 * 读取所有正常的节点
	 * 不考虑缓存未加载的情况
	 * @return
	 * @throws Exception
	 */
	public List getAllNode() throws RemoteException,Exception{
		List result=null;
		HashMap nodeMap=MonCacheManager.getAll(AIMonNodeCacheImpl.class);
		if (nodeMap!=null && nodeMap.size()>0){
			result=new ArrayList();
			Iterator it=nodeMap.entrySet().iterator();
			while (it !=null && it.hasNext()){
				Entry item=(Entry)it.next();
				if ( ((ServerNode)item.getValue()).getEnable() )
					result.add(new ServerNodeConfig((ServerNode)item.getValue()));
			}
		}
		return result;
	}

	/**
	 * 读取所有节点值对象
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonNodeValue[] getAllNodeBean() throws RemoteException,Exception{
		IAIMonNodeDAO nodeDAO=(IAIMonNodeDAO)ServiceFactory.getService(IAIMonNodeDAO.class);
		return nodeDAO.getAllNode();
	}
	
	/**
	 * 根据组标识、节点名，匹配查找节点信息
	 * @param groupId
	 * @param nodeName
	 * @param matchWhole
	 * @return
	 * @throws Exception
	 */
	public List getNodeByGrpIdAndName(String groupId,String nodeName,boolean matchWhole) throws RemoteException,Exception{
		List result=null;
		HashMap nodeMap=MonCacheManager.getAll(AIMonNodeCacheImpl.class);
		if (nodeMap==null || nodeMap.size()==0)
			return result;
		Iterator it=nodeMap.entrySet().iterator();
		result=new ArrayList();
		boolean finded=false;
		while (it!=null && it.hasNext()){
			finded=false;
			Entry item=(Entry)it.next();
			IServerNode node=(IServerNode)item.getValue();
			if (node.getGroup_Id().equals(groupId)){
				if (matchWhole){
					if (node.getNode_Name().toLowerCase().equalsIgnoreCase(nodeName.toLowerCase())){
						finded=true;
					}
				}else{
					if (node.getNode_Name().toLowerCase().indexOf(nodeName.toLowerCase())>=0){
						finded=true;
					}
				}
				if (finded)
					result.add(new ServerNodeConfig(node));
			}
		}
		return result;
	}
	
	/**
	 * 根据组标识、节点名，匹配查找节点信息
	 * @param groupId
	 * @param nodeName
	 * @param matchWhole
	 * @return
	 * @throws Exception
	 */
	public List getNodeByGrpIdAndNameNocache(String groupId,String nodeName,boolean matchWhole) throws RemoteException,Exception{
		List result=new ArrayList();
		List tmp = getNodeByGroupIdNocache(groupId);
		boolean finded=false;
		for (int i = 0; i < tmp.size();i++) {
			finded=false;
			ServerNodeConfig node = (ServerNodeConfig)tmp.get(i);
			if (matchWhole){
				if (node.getNode_Name().toLowerCase().equalsIgnoreCase(nodeName.toLowerCase())){
					finded=true;
				}
			}else{
				if (node.getNode_Name().toLowerCase().indexOf(nodeName.toLowerCase())>=0){
					finded=true;
				}
			}
			if (finded)
				result.add(node);
		}
		return result;
	}
	
	/**
	 * 根据节点名读取节点信息,模糊查询
	 * @param nodeName
	 * @return
	 * @throws Exception
	 */
	public List getNodeByName(String nodeName,boolean matchWhole) throws RemoteException,Exception{
		List result=null;
		HashMap nodeMap=MonCacheManager.getAll(AIMonNodeCacheImpl.class);
		if (nodeMap==null || nodeMap.size()==0)
			return result;
		Iterator it=nodeMap.entrySet().iterator();
		result=new ArrayList();
		boolean finded=false;
		while (it!=null && it.hasNext()){
			finded=false;
			Entry item=(Entry)it.next();
			IServerNode node=(IServerNode)item.getValue();
			if (matchWhole){
				if (node.getNode_Name().toLowerCase().equalsIgnoreCase(nodeName.toLowerCase()))
					finded=true;
			}else{
				if (node.getNode_Name().toLowerCase().indexOf(nodeName.toLowerCase())>=0)
					finded=true;
			}
			if (finded)
				result.add(node);
		}
		return result;
	}
	
	/**
	 * 根据节点名读取节点信息,模糊查询
	 * @param nodeName
	 * @param matchWhole
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public String getNodeXmlByName(String nodeName, boolean matchWhole) throws RemoteException,Exception{
		List list = this.getNodeByName(nodeName, matchWhole);
		Map ret = new HashMap();
		List nodeList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			IServerNode node = (IServerNode)list.get(i);
			if (ret.get(node.getGroup_Id()) != null) {
				nodeList = (List)ret.get(node.getGroup_Id());
			} else {
				nodeList = new ArrayList();
			}
			nodeList.add(node);
			ret.put(node.getGroup_Id(), nodeList);
		}
		
		StringBuilder treeXml=new StringBuilder();
//		treeXml.append("<Node name=\"系统\" id=\"-1\" nodeClass=\"root\">");
		treeXml.append("<Node name=\"").append(AIMonLocaleFactory.getResource("MOS0000104")).append("\" id=\"-1\" nodeClass=\"root\">");
		
		try{
			Iterator it = ret.entrySet().iterator();
			IAIMonGroupSV groupSV = (IAIMonGroupSV)ServiceFactory.getService(IAIMonGroupSV.class);
			ArrayList hs = null;
			while(it.hasNext()) {
				Entry ent = (Entry) it.next();
				hs = (ArrayList) ent.getValue();
				treeXml.append("<Node name=\"").append(groupSV.getGroupByGroupId(ent.getKey().toString())
								.getGroup_Name()).append("\" id=\"").append(ent.getKey()).append("\" nodeClass=\"group\">");
				for (int i = 0; i < hs.size(); i++) {
					IServerNode node = (IServerNode) hs.get(i);
					treeXml.append("<Node name=\"").append(node.getNode_Name()).append("\" id=\"").append(node.getNode_Id()).append("\" nodeClass=\"node\" />");
				}
				treeXml.append("</Node>");
			}
			treeXml.append("</Node>");

		}catch(Exception e){
			log.error("Call AIMonHostSVImpl's Method getHostXmlByName has Exception :",e);
		}		
		return treeXml.toString();
	}
	
	/**
	 * 根据组标识，读取服务器信息
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	public List getNodeByGroupId(String groupId) throws RemoteException,Exception{
		HashMap nodeMap=MonCacheManager.getAll(AIMonNodeCacheImpl.class);
		Iterator it=nodeMap.entrySet().iterator();
		List result=new ArrayList();
		while (it!=null && it.hasNext()){
			Entry item=(Entry)it.next();
			
			if (((ServerNode)item.getValue()).getGroup_Id().equals(groupId)){
				result.add(new ServerNodeConfig((ServerNode)item.getValue()));
			}
		}
		Collections.sort(result,new NodeComparator());
		return result;		
	}
	
	/**
	 * 根据组标识，读取服务器信息
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	public List getNodeByGroupIdNocache(String groupId) throws RemoteException,Exception{
		List result=new ArrayList();
		IAIMonNodeDAO nodeDAO=(IAIMonNodeDAO)ServiceFactory.getService(IAIMonNodeDAO.class);
		if (nodeDAO!=null){
			IBOAIMonNodeValue[] nodeValues=nodeDAO.getNodeByGroupId(groupId);
			if (nodeValues!=null){
				for (int i=0;i<nodeValues.length;i++){
					ServerNode item=new ServerNode();
					item.setNode_Id(nodeValues[i].getNodeId()+"");
					item.setNode_Name(nodeValues[i].getNodeName());
					item.setState(nodeValues[i].getState());
					item.setRemark(nodeValues[i].getRemark());
					item.setNode_Code(nodeValues[i].getNodeCode());					
					result.add(new ServerNodeConfig(item));
				}
			}
		}
		Collections.sort(result,new NodeComparator());
		return result;		
	}
	
	/**
	 * 根据标识从数据库读取节点信息并封装
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public ServerNode getNodeByIdFormDb(String id) throws RemoteException,Exception{
		if (StringUtils.isBlank(id))
			return null;
		IAIMonNodeDAO nodeDAO=(IAIMonNodeDAO)ServiceFactory.getService(IAIMonNodeDAO.class);
		IBOAIMonNodeValue value=nodeDAO.getNodeByNodeId(id);
		ServerNode result=this.wrapperNodeByBean(value);
		return result;
	}
	
	/**
	 * 将节点值对象简单封装
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public ServerNode wrapperNodeByBean(IBOAIMonNodeValue value) throws RemoteException,Exception{
		if (value==null || StringUtils.isBlank(value.getState())  || value.getState().equals("E"))
			return null;
		ServerNode result=new ServerNode();
		result.setNode_Id(value.getNodeId()+"");
		result.setNode_Name(value.getNodeName());		
		result.setState(value.getState());
		result.setRemark(value.getRemark());
		result.setNode_Code(value.getNodeCode());
        result.setHost_id(String.valueOf(value.getHostId()));
		result.setCacheListener(new AIMonNodeCheckListener());
		return result;
	}
	
	/**
	 * 根据组标识和用户标识，读取节点拓扑
	 * @param groupId
	 * @param userId,用户标识暂且不用
	 * @return
	 * @throws Exception
	 */
	public String getNodeTopuXmlByGroupIdAndUserId(String groupId,String userId) throws RemoteException,Exception{
		StringBuilder result = new StringBuilder("");
		try{	
			if (StringUtils.isNotBlank(groupId)) {
				List nodeValues=this.getNodeByGroupId(groupId);
				if (nodeValues != null && nodeValues.size() > 0){
					result.append("<Graph>");
					IObjectStrategy strategy=new TopuXmlStrategy();
					for (int i = 0;i < nodeValues.size();i++) {
						result.append(strategy.visitNode((IServerNode)nodeValues.get(i)));
					}
					result.append("</Graph>");
				}
			}
		} catch (Exception e) {
			log.error("Call AIMonHostSVImpl's Method getHostTopuXmlByGroupIdAndUserId has Exception :"+e.getMessage());
		}
		return result.toString();
	}

	/**
	 * 获取节点信息XML作为树节点
	 * @param groupId
	 * @return List
	 * @throws Exception
	 */
	public List getNodeTreeXmlByGrpId(String groupId) throws RemoteException,Exception {
		if (StringUtils.isBlank(groupId))
			return null;
		List result = null;
		try{
			List nodeValues=getNodeByGroupId(groupId);
			if (null == nodeValues || nodeValues.size() < 1)
				return null;
			result = new ArrayList();
			IObjectStrategy visitorStrategy=new TreeXmlStrategy();
			for (int i = 0;i < nodeValues.size();i++){
				IServerNode item=(IServerNode)nodeValues.get(i);
				result.add(visitorStrategy.visitNode(item));
			}
		} catch (Exception e) {
			log.error("Call AIMonHostSVImpl's Method getHostTreeXmlByGrpId has Exception :"+e.getMessage());
		}
		return result;
	}
	
	public List getNodeTreeXmlByGrpIdNocache(String groupId) throws RemoteException,Exception {
		if (StringUtils.isBlank(groupId))
			return null;
		List result = null;
		try{
			IObjectStrategy visitorStrategy=new TreeXmlStrategy();
			List nodeValues=getNodeByGroupIdNocache(groupId);
			if (null == nodeValues || nodeValues.size() < 1)
				return null;
			result = new ArrayList();
			for (int i = 0;i < nodeValues.size();i++){
				IServerNode item=(IServerNode)nodeValues.get(i);
				result.add(String.valueOf(visitorStrategy.visitNode(item)));
			}
		} catch (Exception e) {
			log.error("Call AIMonHostSVImpl's Method getHostTreeXmlByGrpIdNocache has Exception :"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 批量保存或修改服务器设置
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonNodeValue[] values) throws RemoteException,Exception {
		boolean modify=values[0].isModified();
		IAIMonNodeDAO nodeDAO=(IAIMonNodeDAO)ServiceFactory.getService(IAIMonNodeDAO.class);
		nodeDAO.saveOrUpdate(values);
		for (int i=0;i<values.length;i++){
			if (modify){
				ServerNode item=(ServerNode)MonCacheManager.get(AIMonNodeCacheImpl.class,values[i].getNodeId()+"");
				if (item!=null)
					item.setEnable(false);
			}else{
				ServerNode item=this.wrapperNodeByBean(values[i]);
				MonCacheManager.put(AIMonNodeCacheImpl.class,item.getNode_Id(),item);
			}
		}
	}

	    /**
     * 保存或修改服务器设置
     * @param value
     * @return 
     * @throws Exception
     */
    public long saveOrUpdate(IBOAIMonNodeValue value) throws RemoteException, Exception
    {
		boolean modify=value.isModified();
		IAIMonNodeDAO nodeDAO=(IAIMonNodeDAO)ServiceFactory.getService(IAIMonNodeDAO.class);
		
        long nodeId = nodeDAO.saveOrUpdate(value);
		if (modify){
			ServerNode item=(ServerNode)MonCacheManager.get(AIMonNodeCacheImpl.class,value.getNodeId()+"");
			if (item!=null)
				item.setEnable(false);
		}else{
			ServerNode item=this.wrapperNodeByBean(value);
			MonCacheManager.put(AIMonNodeCacheImpl.class,item.getNode_Id(),item);
		}

        return nodeId;
	}
	
	
	/**
	 * 删除服务器信息
	 * @param nodeId
	 * @throws Exception
	 */
	public void deleteNode(long nodeId) throws RemoteException,Exception {
		IAIMonServerSV serverSV = (IAIMonServerSV) ServiceFactory.getService(IAIMonServerSV.class);
		List servers = serverSV.getAppServerConfigByNodeIdNocache(String.valueOf(nodeId));
		
		if(servers!=null && servers.size() > 0)
			// "当前节点存在正使用的应用，无法删除！"
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000110"));
		
		IAIMonNodeDAO nodeDAO=(IAIMonNodeDAO)ServiceFactory.getService(IAIMonNodeDAO.class);
		nodeDAO.deleteNode(nodeId);
		ServerNode item=(ServerNode)MonCacheManager.get(AIMonNodeCacheImpl.class,nodeId+"");
		if (item!=null)
			item.setEnable(false);
	}

    @Override
    public IBOAIMonNodeValue[] qryNodeInfo(String hostId) throws RemoteException, Exception
    {
        IAIMonNodeDAO nodeDAO = (IAIMonNodeDAO) ServiceFactory.getService(IAIMonNodeDAO.class);
        IBOAIMonNodeValue[] value = nodeDAO.qryNodeInfo(hostId);
        return value;
    }

    @Override
    public IBOAIMonNodeValue qryNodeInfoByNodeId(String nodeId) throws RemoteException, Exception
    {
        IAIMonNodeDAO nodeDAO = (IAIMonNodeDAO) ServiceFactory.getService(IAIMonNodeDAO.class);
        IBOAIMonNodeValue value = nodeDAO.qryNodeInfoByNodeId(nodeId);
        return value;
    }

    @Override
    public boolean isExistNodeByCode(String hostId, String nodeCode) throws RemoteException, Exception
    {
        IAIMonNodeDAO nodeDAO = (IAIMonNodeDAO) ServiceFactory.getService(IAIMonNodeDAO.class);
        boolean b = nodeDAO.isExistNodeByCode(hostId, nodeCode);
        return b;
    }

    @Override
    public boolean isExistNodeByName(String hostId, String nodeName) throws RemoteException, Exception
    {
        IAIMonNodeDAO nodeDAO = (IAIMonNodeDAO) ServiceFactory.getService(IAIMonNodeDAO.class);
        boolean b = nodeDAO.isExistNodeByName(hostId, nodeName);
        return b;
    }

    @Override
    public IDataBus getSelectList() throws Exception
    {
        Map<Long, BODeployStrategyBean> map = DeployStrategyUtils.getDeployStrategyList();

        IDataContext context = new DataContext();
        JSONArray jsonArray = new JSONArray();
        
        Iterator iterator = map.keySet().iterator();
        Object dsKey = null;
        List list=new ArrayList<BODeployStrategyBean>();
       
        while(iterator.hasNext()) {
            JSONObject obj = new JSONObject();
            dsKey = iterator.next();
            BODeployStrategyBean dsBean=map.get(dsKey);
            String key = dsBean.getDeployStrategyName();
            String value = Long.toString(dsBean.getDeployStrategyId());
            obj.put("TEXT", key);
            obj.put("VALUE", value);
            jsonArray.add(obj);
        }
        return new DataBus(context, jsonArray);
    }

    /**
     * 根据用户标识和节点编码查询节点信息
     * 
     * @param request
     * @throws Exception
     */
    @Override
    public IBOAIMonNodeValue qryNodeInfoByHostIdAndNodeCode(String hostId, String nodeCode) throws Exception
    {
        IAIMonNodeDAO nodeDAO = (IAIMonNodeDAO) ServiceFactory.getService(IAIMonNodeDAO.class);
        IBOAIMonNodeValue value = nodeDAO.qryNodeInfoByHostIdAndNodeCode(hostId, nodeCode);
        return value;
    }

    /**
     * 判断该主机下是否存在监控节点
     * 
     * @param request
     * @throws Exception
     */
    @Override
    public boolean isExistMonitorNode(String hostId) throws Exception
    {
        IAIMonNodeDAO nodeDAO = (IAIMonNodeDAO) ServiceFactory.getService(IAIMonNodeDAO.class);
        return nodeDAO.isExistMonitorNode(hostId);
    }

}
