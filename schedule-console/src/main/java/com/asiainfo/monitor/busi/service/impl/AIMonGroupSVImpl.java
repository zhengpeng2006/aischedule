package com.asiainfo.monitor.busi.service.impl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.cache.AIMonGroupCheckListener;
import com.asiainfo.monitor.busi.cache.MonCacheManager;
import com.asiainfo.monitor.busi.cache.impl.AIMonGroupCacheImpl;
import com.asiainfo.monitor.busi.cache.impl.Group;
import com.asiainfo.monitor.busi.cache.interfaces.IGroup;
import com.asiainfo.monitor.busi.cache.interfaces.IServer;
import com.asiainfo.monitor.busi.cache.interfaces.IServerNode;
import com.asiainfo.monitor.busi.common.impl.TreeXmlStrategy;
import com.asiainfo.monitor.busi.common.interfaces.IObjectStrategy;
import com.asiainfo.monitor.busi.config.GroupConfig;
import com.asiainfo.monitor.busi.config.TopuXmlFactory;
import com.asiainfo.monitor.busi.config.TreeXmlFactory;
import com.asiainfo.monitor.busi.dao.interfaces.IAIMonDomainDAO;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonDomainSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonGroupSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonNodeSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonPhysicHostSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonUserSV;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonDomainRelatBean;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonDomainRelatValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonDomainValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonGroupEngine;
import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonGroupHostRelBean;
import com.asiainfo.monitor.exeframe.configmgr.abo.dao.interfaces.IAIMonGroupDAO;
import com.asiainfo.monitor.exeframe.configmgr.abo.dao.interfaces.IAIMonGroupHostRelDAO;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonGroupValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonPhysicHostValue;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.util.TypeConst;

public class AIMonGroupSVImpl implements IAIMonGroupSV {
	
	private static transient Log log=LogFactory.getLog(AIMonGroupSVImpl.class);
	/**
	 * 根据组标识，读取组信息
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	public GroupConfig getGroupByGroupId(String groupId) throws RemoteException,Exception{
		Group result=(Group)MonCacheManager.get(AIMonGroupCacheImpl.class,groupId);;
		if (result==null){
			//没有从数据库确认
			result=this.getGroupByIdFromDb(groupId);
			if (!MonCacheManager.getCacheReadOnlySet() && result!=null){
				MonCacheManager.put(AIMonGroupCacheImpl.class,result.getGroup_Id(),result);
			}
		}
		return new GroupConfig(result);		
	}
	
	/**
	 * 根据组名查询Group
	 * @param groupName
	 * @return List
	 * @throws Exception, RemoteException
	 */
	public List getGroupByName(String groupName,boolean matchWhole) throws Exception,RemoteException {
		List result=null;
		HashMap groupMap=MonCacheManager.getAll(AIMonGroupCacheImpl.class);
		if (groupMap!=null && groupMap.size()>0){
			result=new ArrayList();
			Iterator it=groupMap.entrySet().iterator();
			
			while (it !=null && it.hasNext()){
				Entry item=(Entry)it.next();
				Group group=(Group)item.getValue();
				if (matchWhole){
					if (group.getGroup_Name().toLowerCase().equalsIgnoreCase(groupName.toLowerCase())){
						result.add(new GroupConfig(group));
					}
				}else{
					if (group.getGroup_Name().toLowerCase().indexOf(groupName.toLowerCase())>=0){
						result.add(new GroupConfig(group));
					}
				}
			}
		}
		return result;
	}
	
	/**
	 * 根据组名查询Group
	 * @param groupName
	 * @return List
	 * @throws Exception, RemoteException
	 */
	public List getGroupByNameNocache(String groupName,boolean matchWhole) throws Exception,RemoteException {
		List result=new ArrayList();
		IAIMonGroupDAO dao = (IAIMonGroupDAO)ServiceFactory.getService(IAIMonGroupDAO.class);		
		IBOAIMonGroupValue[] groups = dao.queryGroupByName(groupName);
		if (groups != null && groups.length > 0) {
			for (int i = 0 ; i < groups.length; i++) {
				Group group=this.wrapperGroupConfigByBean(groups[i]);
				
				if (matchWhole){
					if (group.getGroup_Name().toLowerCase().equalsIgnoreCase(groupName.toLowerCase())){
						result.add(new GroupConfig(group));
					}
				}else{
					if (group.getGroup_Name().toLowerCase().indexOf(groupName.toLowerCase())>=0){
						result.add(new GroupConfig(group));
					}
				}
			}
		}
		return result;
	}
	
	
	
	/**
	 * 返回所有组信息
	 * 不考虑缓存未加载情况
	 * @return
	 * @throws Exception
	 */
	public List getAllGroup() throws RemoteException,Exception {
		List result=null;
		HashMap groupMap=MonCacheManager.getAll(AIMonGroupCacheImpl.class);
		if (groupMap!=null && groupMap.size()>0){
			result=new ArrayList();
			Iterator it=groupMap.entrySet().iterator();
			while (it !=null && it.hasNext()){
				Entry item=(Entry)it.next();
				if ( ((Group)item.getValue())!=null )
					result.add(new GroupConfig((Group)item.getValue()));
			}
		}
		return result;
	}
	
	/**
	 * 根据用户获取组信息
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List getAllGroupByUserId(String userId) throws RemoteException,Exception{
		List result=null;
		if (StringUtils.isBlank(userId))
			// 没有传入用户
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000103"));
		IAIMonUserSV userSV=(IAIMonUserSV)ServiceFactory.getService(IAIMonUserSV.class);
		List domains=userSV.getDomainsByUserId(userId);
		HashMap groupMap=MonCacheManager.getAll(AIMonGroupCacheImpl.class);
		if (groupMap!=null && groupMap.size()>0){
			result=new ArrayList();
			Iterator it=groupMap.entrySet().iterator();
			while (it !=null && it.hasNext()){
				Entry item=(Entry)it.next();
				if (domains==null || domains.size()<1)
					result.add(new GroupConfig((Group)item.getValue()));
				else{
					if (((Group)item.getValue()).getDomain()!=null && domains.contains(((Group)item.getValue()).getDomain())){
						result.add(new GroupConfig((Group)item.getValue()));
					}
				}
			}
		}
		return result;
	}
	
	/**
	 * 返回所有组信息值对象
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonGroupValue[] getAllGroupBean() throws RemoteException,Exception {
		IAIMonGroupDAO groupDAO=(IAIMonGroupDAO)ServiceFactory.getService(IAIMonGroupDAO.class);
		return groupDAO.getAllGroup();
	}
	
	/**
	 * 根据组标识从数据库读取组信息并封装
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Group getGroupByIdFromDb(String id) throws RemoteException,Exception{
		if (StringUtils.isBlank(id))
			return null;
		IAIMonGroupDAO groupDAO=(IAIMonGroupDAO)ServiceFactory.getService(IAIMonGroupDAO.class);
		IBOAIMonGroupValue value=groupDAO.getGroupByGroupId(id);
		Group result=this.wrapperGroupConfigByBean(value);
		return result;
	}
	/**
	 * 将值对象简单封装
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public Group wrapperGroupConfigByBean(IBOAIMonGroupValue value) throws RemoteException,Exception{
		if (value==null || StringUtils.isBlank(value.getState())  || value.getState().equals("E"))
			return null;
		Group result=new Group();
		result.setGroup_Id(value.getGroupId()+"");
		result.setGroup_Name(value.getGroupName());
		result.setGroup_Desc(value.getGroupDesc());
		result.setState(value.getState());
		IAIMonDomainSV domainSV=(IAIMonDomainSV)ServiceFactory.getService(IAIMonDomainSV.class);
		IBOAIMonDomainValue[] domainRelatValue = domainSV.getRelateDomains("1", result.getGroup_Id());
		if (null!=domainRelatValue&&domainRelatValue.length>0&&null!=domainRelatValue[0]){
			result.setDomain(domainRelatValue[0].getDomainId()+"");
			result.setDomainCode(domainRelatValue[0].getDomainCode());
		}
		result.setCacheListener(new AIMonGroupCheckListener());
		return result;
	}
	
	/**
	 * 读取拓扑图XML(金字塔形)
	 * 后面再将做为缓存
	 * @return
	 * @throws Exception
	 */
	public String getTopuXml() throws RemoteException,Exception{
		String result=null;
		try{
			result=TopuXmlFactory.getTopTopuXml();
		}catch (Exception e) {
			log.error("Call AppConfigAction's Method getTopuXml has Exception :"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 根据Session用户获取关联域拓扑信息
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public String getTopuXmlByUserId(String userId) throws RemoteException,Exception{
		if (StringUtils.isBlank(userId))
			// 没有传入用户
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000103"));	
		String result=null;
		try{
			IAIMonUserSV userSV=(IAIMonUserSV)ServiceFactory.getService(IAIMonUserSV.class);
			List domains=userSV.getDomainsByUserId(userId);
			if (domains==null || domains.size()<1)
				result=TopuXmlFactory.getTopTopuXml();
			else{
				//单个域
				result=TopuXmlFactory.getTopuXmlByDomain((String[])domains.toArray(new String[0]));
			}
				
		}catch (Exception e) {
			log.error("Call AppConfigAction's Method getTopuXml has Exception :"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 读取只有组信息树的XML
	 * @return
	 * @throws Exception
	 */
	public String getAllGroupTreeXml() throws RemoteException,Exception{
		String result=null;		
		try{
			result=TreeXmlFactory.getTopTreeXml();			
		}catch (Exception e) {
			log.error("Call AppConfigAction's Method getGroupTreeXML has Exception :"+e.getMessage());
		}
		return result;
	}
	
	public String getAllGroupTreeXmlNocache() throws RemoteException,Exception {
		String ret = null;
		IObjectStrategy visitorStrategy=new TreeXmlStrategy();
		StringBuilder treeXml = new StringBuilder("<nodes label=\"");
		treeXml.append(AIMonLocaleFactory.getResource("MOS0000104")).append("\" id=\"-1\" nodeClass=\"root\" icon=\"systemImage\" depth=\"0\" state=\"0\" isBranch=\"true\">\n");
		
		List gs = new ArrayList();
		
		IAIMonGroupSV groupSV=(IAIMonGroupSV)ServiceFactory.getService(IAIMonGroupSV.class);
		
		IAIMonDomainSV domainSV=(IAIMonDomainSV)ServiceFactory.getService(IAIMonDomainSV.class);
		
		if (groupSV!=null){
			IBOAIMonGroupValue[] groups=groupSV.getAllGroupBean();
			if (groups!=null && groups.length>0){
				for (int i=0;i<groups.length;i++){
					Group group=new Group();
					group.setGroup_Id(groups[i].getGroupId()+"");
					group.setGroup_Name(groups[i].getGroupName());
					group.setGroup_Desc(groups[i].getGroupDesc());
					group.setState(groups[i].getState());
					IBOAIMonDomainValue[] domainRelatValue = domainSV.getRelateDomains("1", group.getGroup_Id());
					if (null!=domainRelatValue&&domainRelatValue.length>0&&null!=domainRelatValue[0]){
						group.setDomain(domainRelatValue[0].getDomainId()+"");
						group.setDomainCode(domainRelatValue[0].getDomainCode());
					}
					gs.add(group);
				}
			}
		}
		
		String tmp = "";
		for (int i = 0; i < gs.size(); i++) {
			IGroup group=(IGroup)gs.get(i);
			tmp = String.valueOf(visitorStrategy.visitGroup(group));
			treeXml.append(tmp);
		}
		ret = treeXml.append("</nodes>").toString();
		
		return ret;
	}
	
	/**
	 * 构建全局结构饼图Xml
	 * @return
	 * @throws Exception
	 */
	public String getWholeFramePieXml() throws RemoteException,Exception{
		StringBuilder result=new StringBuilder("<chart>");
		try{
			HashMap groupMap=MonCacheManager.getAll(AIMonGroupCacheImpl.class);
			if (groupMap!=null && groupMap.size()>0){				
				Iterator it=groupMap.entrySet().iterator();
				IAIMonNodeSV hostSV=(IAIMonNodeSV)ServiceFactory.getService(IAIMonNodeSV.class);
				IAIMonServerSV serverSV=(IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
				while (it !=null && it.hasNext()){
					Entry item=(Entry)it.next();
					Group group=(Group)item.getValue();
					if (group!=null){
						result.append("<node id=\""+group.getGroup_Id()+"\" name=\""+group.getGroup_Name()+"\" ");
						List hostList=hostSV.getNodeByGroupId(group.getGroup_Id());
						if (hostList!=null && hostList.size()>0){
							result.append(" value=\""+hostList.size()+"\"");
							int webCount=0,appCount=0,othCount=0;
							for (int i=0;i<hostList.size();i++){
								List serverList=serverSV.getAppServerConfigByNodeId( ((IServerNode)hostList.get(i)).getNode_Id());
								if (serverList!=null && serverList.size()>0){
									for (int j=0;j<serverList.size();j++){
										if (StringUtils.isBlank(((IServer)serverList.get(j)).getTemp_Type()))
											othCount++;
										else if (((IServer)serverList.get(j)).getTemp_Type().equals(TypeConst.WEB))
											webCount++;
										else if (((IServer)serverList.get(j)).getTemp_Type().equals(TypeConst.APP))
											appCount++;
										else
											othCount++;
									}
								}
							}
							result.append(" web=\""+webCount+"\" app=\""+appCount+"\" other=\""+othCount+"\" />");
						}else{
							result.append(" value=\"0\" web=\"0\" app=\"0\" other=\"0\" />");
						}
					}
				}
			}
		}catch(Exception e){
			log.error(AIMonLocaleFactory.getResource("MOS0000105"),e);
		}finally{
			result.append("</chart>");
		}
		return result.toString();
	}
	
	/**
	 * 批量保存或修改应用服务器组设置
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonGroupValue[] values) throws RemoteException,Exception {
		boolean modify=values[0].isModified();
		IAIMonGroupDAO groupDAO=(IAIMonGroupDAO)ServiceFactory.getService(IAIMonGroupDAO.class);
		groupDAO.saveOrUpdate(values);
		for (int i=0;i<values.length;i++){
			if (modify){
				Group item=(Group)MonCacheManager.get(AIMonGroupCacheImpl.class,values[i].getGroupId()+"");
				if (item!=null)
					item.setEnable(false);
			}else{
				Group item=this.wrapperGroupConfigByBean(values[i]);
				MonCacheManager.put(AIMonGroupCacheImpl.class,item.getGroup_Id(),item);
			}
		}
	}

	/**
	 * 保存或修改应用服务器组设置
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonGroupValue value,String domainId) throws RemoteException,Exception {
	    long groupId;
		IAIMonGroupDAO groupDAO=(IAIMonGroupDAO)ServiceFactory.getService(IAIMonGroupDAO.class);
		IAIMonDomainDAO domainDAO = (IAIMonDomainDAO)ServiceFactory.getService(IAIMonDomainDAO.class);
		boolean modify=value.isModified();
		if(StringUtils.isBlank(value.getGroupName()))
			// "组名称不能为空!"
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000106"));
		if(value.isNew())
		{
			List groups = this.getGroupByNameNocache(value.getGroupName(), true);
			if(groups!=null && groups.size() > 0)
				// 组名称["+ value.getGroupName() + "]已经存在
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000107", value.getGroupName()));
		}
		groupId = groupDAO.saveOrUpdate(value);
		
		// 保存组与域的关系
		// 1：查询该组是否存在域关系
		// 2：如果传入的domainId不为空，有域则更新，无域则新增
		// 3：如果传入的domainId为空，有域更新状态；
		IBOAIMonDomainRelatValue[] rltValue = domainDAO.getDomainRelatByTypeAndRelatId("1", String.valueOf(groupId));
		IBOAIMonDomainRelatValue bean = null;
		
		if(StringUtils.isNotBlank(domainId)){
			if(rltValue.length>0){
				bean = rltValue[0];
				bean.setDomainId(Long.parseLong(domainId));
				bean.setState("U");
			}else{
				bean = new BOAIMonDomainRelatBean();
				bean.setDomainId(Long.parseLong(domainId));
				bean.setRelatdomainId(groupId);
				bean.setRelatdomainType("1");
				bean.setState("U");
			}
		}else{
			if(rltValue.length>0){
				bean = rltValue[0];
				//bean.setDomainId(Long.parseLong(domainId));
				bean.setState("E");
			}
		}
		if (bean!=null)
			domainDAO.saveOrUpdate(bean);
		
		if (modify){
			Group item=(Group)MonCacheManager.get(AIMonGroupCacheImpl.class,value.getGroupId()+"");
			if (item!=null)
				item.setEnable(false);
		}else{
			Group item=this.wrapperGroupConfigByBean(value);
			MonCacheManager.put(AIMonGroupCacheImpl.class,item.getGroup_Id(),item);
		}
	}
	
	/**
	 * 删除组信息
	 * @param groupId
	 * @throws Exception
	 */
	public void deleteGroup (long groupId) throws RemoteException,Exception {		
		// 校验当前组是否存在主机
		IAIMonNodeSV hostSV=(IAIMonNodeSV)ServiceFactory.getService(IAIMonNodeSV.class);
        /*List hosts = hostSV.getNodeByGroupIdNocache(String.valueOf(groupId));
        if(hosts != null && hosts.size() > 0)
        	// "当前组标识存在正在使用的主机，无法删除!"
        	throw new Exception(AIMonLocaleFactory.getResource("MOS0000108"));
        */
		IAIMonGroupDAO groupDAO=(IAIMonGroupDAO)ServiceFactory.getService(IAIMonGroupDAO.class);
		groupDAO.deleteGroup(groupId);
		IAIMonDomainDAO domainDAO = (IAIMonDomainDAO)ServiceFactory.getService(IAIMonDomainDAO.class);
		IBOAIMonDomainRelatValue[] value = domainDAO.getDomainRelatByTypeAndRelatId("1", String.valueOf(groupId));
		if(value.length>0&&null!=value[0]){
			domainDAO.delDomainRelatByPk(String.valueOf(value[0].getRelatId()));
		}
		Group item=(Group)MonCacheManager.get(AIMonGroupCacheImpl.class,groupId+"");
		if (item!=null)
			item.setEnable(false);
	}

	public String getWholeTreeXml() throws RemoteException,Exception {
		String result=null;		
		try{
			result=TreeXmlFactory.getWholeTreeXml();			
		}catch (Exception e) {
			log.error("Call AppConfigAction's Method getWholeTreeXml has Exception :"+e.getMessage(),e);
		}
		return result;
	}
	
	public static void main(String[] args) {
		try {
			IAIMonGroupSV sv = (IAIMonGroupSV)ServiceFactory.getService(IAIMonGroupSV.class);
			String xml = sv.getAllGroupTreeXmlNocache();
			String xm2 = sv.getAllGroupTreeXml();
			System.out.println(xml);
			System.out.println(xm2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

    @Override
    public IBOAIMonGroupValue getGroupBean(String groupId) throws Exception
    {
        return BOAIMonGroupEngine.getBean(Long.parseLong(groupId));

    }

    @Override
    public List<IBOAIMonPhysicHostValue> qryGroupHostInfo(String groupId) throws Exception
    {
        IAIMonGroupHostRelDAO relDao = (IAIMonGroupHostRelDAO) ServiceFactory.getService(IAIMonGroupHostRelDAO.class);
        BOAIMonGroupHostRelBean[] relArr = relDao.getBeansByGroupId(Long.parseLong(groupId));
        List<IBOAIMonPhysicHostValue> hostList = new ArrayList<IBOAIMonPhysicHostValue>();
        if(relArr!=null&&relArr.length>0) {
            for(BOAIMonGroupHostRelBean relBean : relArr) {
                //去除缓存
                //Object object = CacheFactory.get(AIMonPhostCacheImpl.class, String.valueOf(relBean.getHostId()));
                IAIMonPhysicHostSV hostSV=(IAIMonPhysicHostSV)ServiceFactory.getService(IAIMonPhysicHostSV.class);
                IBOAIMonPhysicHostValue value=hostSV.getPhysicHostById(Long.toString(relBean.getHostId()));
                if(null != value) {
                    hostList.add((IBOAIMonPhysicHostValue) value);
                }
            }
        }
        return hostList;
    }

    @Override
    public void saveOrUpdate(IBOAIMonGroupValue value) throws Exception
    {
        IAIMonGroupDAO groupDAO = (IAIMonGroupDAO) ServiceFactory.getService(IAIMonGroupDAO.class);
        groupDAO.saveOrUpdate(value);
    }

    @Override
    public boolean isExistGroupByCode(String groupCode) throws Exception
    {

        IAIMonGroupDAO groupDAO = (IAIMonGroupDAO) ServiceFactory.getService(IAIMonGroupDAO.class);
        return groupDAO.isExistGroupByCode(groupCode);
    }

    @Override
    public boolean isExistGroupByName(String groupName) throws Exception
    {
        IAIMonGroupDAO groupDAO = (IAIMonGroupDAO) ServiceFactory.getService(IAIMonGroupDAO.class);
        return groupDAO.isExistGroupByName(groupName);
    }
    
    /**
     * 根据权限信息获取分组信息
     */
    @Override
    public IBOAIMonGroupValue[] getGroupByPriv(String priv) throws RemoteException, Exception
    {
        IAIMonGroupDAO groupDAO = (IAIMonGroupDAO) ServiceFactory.getService(IAIMonGroupDAO.class);
        //根据操作员id获取相应的数据
        if(StringUtils.isNotBlank(priv)) {
            //1代表核心维护人员,2代表一般维护人员，3代表上线人员，4代表结算人员，5代表流量人员，6代表支付人员，-110代表system
            if(priv.split("_")[0].equals("-110")) {
                return getAllGroupBean();
            }
            else {
                return groupDAO.getGroupByPriv(priv);
            }
        }
        return null;
    }

}