package com.asiainfo.monitor.busi.web;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.complex.util.e.K;
import com.ai.appframe2.service.ServiceFactory;
import com.ai.appframe2.web.action.BaseAction;
import com.asiainfo.monitor.busi.cache.impl.PriEntity;
import com.asiainfo.monitor.busi.cache.impl.UserGroup;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonAttentionPanelSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonCustomizePanelSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonDomainSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonPanelRelatEntitySV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonUserGroupRelSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonUserGroupRoleSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonUserGroupSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonUserPriEntitySV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonUserRoleEntityRelSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonUserRoleSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonUserSV;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonPanelRelatEntityBean;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonUserBean;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonUserGroupBean;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonUserGroupRelBean;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonUserGroupRoleRelBean;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonUserPriEntityBean;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonUserRoleBean;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonUserRoleEntityRelBean;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonAttentionPanelValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonCustomizePanelValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonDomainValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonPanelRelatEntityValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserGroupRelValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserGroupRoleRelValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserGroupValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserPriEntityValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserRoleEntityRelValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserRoleValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserValue;
import com.asiainfo.monitor.tools.common.SimpleResult;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;


public class PermissionAction extends BaseAction {
	
	private static transient Log log = LogFactory.getLog(PermissionAction.class);

	private Timestamp getCurrentTimestamp(){
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * 验证登陆
	 * 
	 * @param userCode
	 * @param pwd
	 * @return String
	 * @throws Exception
	 */
	public SimpleResult vertifyLogin(String userCode, String pwd) throws Exception{
		SimpleResult ret = new SimpleResult();
		ret.setKey("N");
		ret.setSucc(false);
		// "登陆失败"
		ret.setMsg(AIMonLocaleFactory.getResource("MOS0000035"));
		try{
			IAIMonUserSV userSV = (IAIMonUserSV)ServiceFactory.getService(IAIMonUserSV.class);
			UserGroup userInfo = userSV.getUserRoleDomain(userCode);			
			if (userInfo != null) {
		
				if (userInfo.getGroup()==null)
				{
					ret.setKey("N");
					ret.setSucc(false);
					// "用户没有权限,没有为用户[{0}]指定所属组!"
					ret.setMsg(AIMonLocaleFactory.getResource("MOS0000036", userInfo.getName()));
					return ret;
				}
				if (userInfo.getGroup().getRoles()==null || userInfo.getGroup().getRoles().size()<1)
				{
					ret.setKey("N");					
					ret.setSucc(false);
					// "用户没有权限,用户所属组[{0}]没有指定角色!"
					ret.setMsg(AIMonLocaleFactory.getResource("MOS0000037", userInfo.getGroup().getName()));
					return ret;
				}
				
				Calendar effCalendar=Calendar.getInstance();
				Calendar expCalendar = Calendar.getInstance();
				
				effCalendar.setTime(userInfo.getEffectDate());
				expCalendar.setTime(userInfo.getExpireDate());
				
				Timestamp currentTime=getCurrentTimestamp();
				Calendar currCalendar = Calendar.getInstance();
				currCalendar.setTime(currentTime);
				if (effCalendar.after(currCalendar)){
					ret.setKey("N");					
					ret.setSucc(false);
					// "登陆失败,该用户暂未生效!"
					ret.setMsg(AIMonLocaleFactory.getResource("MOS0000038"));
					return ret;
				}				
				if (expCalendar.before(currCalendar)){
					ret.setKey("N");
					// "登陆失败,该用户已失效!"
					ret.setMsg(AIMonLocaleFactory.getResource("MOS0000039"));
					ret.setSucc(false);
					return ret;
				}
				if (K.k(userInfo.getPassword()).equalsIgnoreCase(pwd)) {
//					FlexSessionManager.setUser(userInfo);
					ret.setKey("Y");
					// "登陆成功."
					ret.setMsg(AIMonLocaleFactory.getResource("MOS0000040"));
					ret.setSucc(true);
					ret.setValue(userInfo.getId()+"");
				}else{
					ret.setKey("N");
					ret.setSucc(false);
					// "登陆失败,密码错误!"
					ret.setMsg(AIMonLocaleFactory.getResource("MOS0000041"));
				}
			}else{
				ret.setKey("N");
				ret.setSucc(false);
				// "登陆失败,用户不存在!"
				ret.setMsg(AIMonLocaleFactory.getResource("MOS0000042"));
			}
		}catch(Exception e){
			throw e;
		}
		return ret;
	}	
	
	/**
	 * 验证登陆
	 * 
	 * @param userCode
	 * @return String
	 * @throws Exception
	 */
	public Boolean vertifyUser(String userCode) throws Exception{
		if (StringUtils.isBlank(userCode))
			return false;
		String code="";
//		if(FlexSessionManager.getUser() != null)
//			code = FlexSessionManager.getUser().getCode();
		if (StringUtils.isBlank(code))
			return false;
		if (userCode.equals(code)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 锁定用户
	 * 
	 * @param userCode
	 * @throws Exception
	 */
	public void lockUserInfoByCode (String userCode) throws Exception{
		IAIMonUserSV userSV = (IAIMonUserSV)ServiceFactory.getService(IAIMonUserSV.class);
		IBOAIMonUserValue userInfo = userSV.getAvailableUserInfoByCode(userCode);
		userInfo.setStsToOld();
		userInfo.setLockFlag(1);
		userSV.saveOrUpdate(userInfo);
	}

	/**
	 * 注销用户
	 * 
	 * @throws Exception
	 */
	public void logoutUser() throws Exception{
//		FlexSessionManager.setUserNull();
	}
	
	/**
	 * 根据parentId查询实体信息
	 * 
	 * @param parentId
	 * @return IBOAIMonUserPriEntityValue[] 
	 * @throws Exception
	 */
	public List getEntityByParentId(String parentId) throws Exception{
		IAIMonUserPriEntitySV entitySV=(IAIMonUserPriEntitySV)ServiceFactory.getService(IAIMonUserPriEntitySV.class);
		return entitySV.getEntityByParentId(parentId);
	}
	
	/**
	 * 根据用户权限获取实体信息
	 * 
	 * @param parentId
	 * @param Type
	 * @return IBOAIMonUserPriEntityValue[] 
	 * @throws Exception
	 */
	public List getEntityByPermission(String parentId, String type) throws Exception{
		if (StringUtils.isBlank(parentId))
			return null;
		String userId="";
//		if(FlexSessionManager.getUser() != null)
//			userId = FlexSessionManager.getUser().getId();
		if (StringUtils.isBlank(userId))
			return null;
		IAIMonUserPriEntitySV entitySV=(IAIMonUserPriEntitySV)ServiceFactory.getService(IAIMonUserPriEntitySV.class);
		return entitySV.queryEntityByPermission(userId, parentId, type);
	}
	
	
	
	/**
	 * 根据条件检索用户信息
	 * 
	 * @param userName
	 * @param userCode
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonUserValue[] getUserInfoByCond(String userName, String userCode) throws Exception {
		IAIMonUserSV userSV = (IAIMonUserSV)ServiceFactory.getService(IAIMonUserSV.class);
		return userSV.getUserInfoByCond(userCode, userName);
	}
	
	/**
	 * 保存或修改用户信息
	 * 
	 * @param userInfo
	 * @throws Exception
	 */
	public SimpleResult saveUserInfo(Object[] userInfo) throws Exception {
		if (userInfo == null || userInfo.length <= 0) {
			return null;
		}
		SimpleResult ret = new SimpleResult();
		ret.setKey("Y");
		// "保存成功。"
		ret.setMsg(AIMonLocaleFactory.getResource("MOS0000043"));
		ret.setSucc(true);
		try {
			IAIMonUserSV userSV = (IAIMonUserSV)ServiceFactory.getService(IAIMonUserSV.class);
			IBOAIMonUserValue userValue = new BOAIMonUserBean();
			Timestamp nowDate = getCurrentTimestamp();
			if (StringUtils.isBlank(userInfo[0].toString())) {
				// 检查账号是否存在
				String userCode = userInfo[1].toString().trim();
				if (userSV.checkUserCodeExist(userCode)) {
					ret.setKey("N");
					ret.setSucc(false);
					// "保存失败，用户账号已存在！"
					ret.setMsg(AIMonLocaleFactory.getResource("MOS0000044"));
					return ret;
				} else {
					userValue.setStsToNew();
					userValue.setCreateDate(nowDate);
				}
			} else {
				userValue.setUserId(Long.parseLong(userInfo[0].toString().trim()));
				userValue.setStsToOld();
			}
			userValue.setUserCode(userInfo[1].toString().trim());
			userValue.setUserName(userInfo[2].toString().trim());
			userValue.setUserPass(K.j(userInfo[3].toString().trim()));
			userValue.setEffectDate(StringToTimestamp(userInfo[4].toString().trim()));
			userValue.setExpireDate(StringToTimestamp(userInfo[5].toString().trim()));
			// 1-允许 ,0-不允许
			userValue.setAllowChgPass(Integer.parseInt(userInfo[6].toString().trim()));
			userValue.setMultiLoginFlag(Integer.parseInt(userInfo[7].toString().trim()));
			userValue.setTryTimes(Integer.parseInt(userInfo[8].toString().trim()));
			// 0-正常 ,1-锁定
			userValue.setLockFlag(Integer.parseInt(userInfo[9].toString().trim()));
			userValue.setDoneDate(nowDate);
//			userValue.setOpId(Long.parseLong(userInfo[12].toString().trim()));
			userValue.setState(userInfo[10].toString().trim());
			userValue.setNotes(userInfo[11].toString().trim());
			
			userSV.saveOrUpdate(userValue);
			
		} catch(Exception e) {
			log.error("Call Permission's Method saveUserInfo has Exception :" + e.getMessage());
			throw e;
		}
		return ret;
	}

	/**
	 * String转Timestamp
	 * 
	 * @param s
	 * @return
	 */
	public Timestamp StringToTimestamp(String s) {
		Timestamp ts = null;
		try {
			Date armFormateDate = null;
			SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			armFormateDate = format.parse(s);
			s = df1.format(armFormateDate);
			ts = Timestamp.valueOf(s);
		} catch (Exception e) {
			log.error("Call Permission's Method StringToTimestamp has Exception :" + e.getMessage());
		}
		return ts;
	}
	
	/**
	 * 删除用户信息
	 * 
	 * @param userId
	 * @throws Exception
	 */
	public void deleteUserInfo(String userId) throws Exception {
		if (StringUtils.isBlank(userId)) {
			return;
		}
		try {
			IAIMonUserSV userSV = (IAIMonUserSV)ServiceFactory.getService(IAIMonUserSV.class);
			userSV.delete(Long.parseLong(userId));
		} catch (Exception e) {
			log.error("Call Permission's Method deleteUserInfo has Exception :" + e.getMessage());
			throw e;
		}
	}
	
	/**
	 * 根据主键取得用户信息
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonUserValue getUserByUserId(String userId) throws Exception {
		IAIMonUserSV userSV = (IAIMonUserSV)ServiceFactory.getService(IAIMonUserSV.class);
		return userSV.getBeanById(Long.parseLong(userId));
	}
	
	/**
	 * 根据条件取得用户组信息
	 * 
	 * @param groupCode
	 * @param groupName
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonUserGroupValue[] getUserGroupByCond(String groupCode, String groupName) throws Exception {
		IAIMonUserGroupSV userGroupSV = (IAIMonUserGroupSV)ServiceFactory.getService(IAIMonUserGroupSV.class);
		return userGroupSV.getUserGroupByCond(groupCode, groupName);
	}
	
	/**
	 * 根据主键取得用户组信息
	 * 
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonUserGroupValue getGroupByGroupId(String groupId) throws Exception {
		IAIMonUserGroupSV userGroupSV = (IAIMonUserGroupSV)ServiceFactory.getService(IAIMonUserGroupSV.class);
		return userGroupSV.getBeanById(Long.parseLong(groupId));
	}
	
	/**
	 * 保存或修改用户组信息
	 * 
	 * @param groupInfo
	 * @throws Exception
	 */
	public void saveUserGroup(Object[] groupInfo) throws Exception {
		if (groupInfo == null || groupInfo.length <= 0) {
			return;
		}
		try {
			IBOAIMonUserGroupValue groupValue = new BOAIMonUserGroupBean();
			Timestamp nowDate = getCurrentTimestamp();
			if (StringUtils.isBlank(groupInfo[0].toString())) {
				groupValue.setStsToNew();
				groupValue.setCreateDate(nowDate);
			} else {
				groupValue.setUserGroupId(Long.parseLong(groupInfo[0].toString().trim()));
				groupValue.setStsToOld();
			}
			groupValue.setGroupCode(groupInfo[1].toString().trim());
			groupValue.setGroupName(groupInfo[2].toString().trim());
			
			groupValue.setDoneDate(nowDate);
//			groupValue.setOpId(Long.parseLong(groupInfo[5].toString().trim()));
			groupValue.setState(groupInfo[3].toString().trim());
			groupValue.setNotes(groupInfo[4].toString().trim());
			
			IAIMonUserGroupSV userGroupSV = (IAIMonUserGroupSV)ServiceFactory.getService(IAIMonUserGroupSV.class);
			userGroupSV.saveOrUpdate(groupValue);
			
		} catch (Exception e) {
			log.error("Call Permission's Method saveUserGroup has Exception :" + e.getMessage());
			throw e;
		}
	}
	
	/**
	 * 删除用户组信息
	 * 
	 * @param userGroupId
	 * @throws Exception
	 */
	public void deleteUserGroup(String userGroupId) throws Exception {
		if (StringUtils.isBlank(userGroupId)) {
			return;
		}
		try {
			IAIMonUserGroupSV userGroupSV = (IAIMonUserGroupSV)ServiceFactory.getService(IAIMonUserGroupSV.class);
			userGroupSV.delete(Long.parseLong(userGroupId));
		} catch (Exception e) {
			log.error("Call Permission's Method deleteUserGroup has Exception :" + e.getMessage());
			throw e;
		}
	}
	
	/**
	 * 根据条件取得用户角色信息
	 * 
	 * @param roleCode
	 * @param roleName
	 * @return
	 * @throws Exception
	 */
	public DataContainer[] getUserRoleByCond(String roleCode, String roleName) throws Exception {
		IAIMonUserRoleSV userRoleSV = (IAIMonUserRoleSV)ServiceFactory.getService(IAIMonUserRoleSV.class);
		IBOAIMonUserRoleValue[] roles = userRoleSV.getUserRoleByCond(roleCode, roleName);
		IAIMonDomainSV sv = (IAIMonDomainSV)ServiceFactory.getService(IAIMonDomainSV.class);
		// domain_id-->domain_code
		
		if(roles == null) return null;
		DataContainer[] ret = new DataContainer[roles.length];
		for(int i=0;i<roles.length;i++){
			ret[i] = new DataContainer();
			ret[i].copy(roles[i]);
			if(roles[i].getDomainId()>0){
				IBOAIMonDomainValue domain = sv.getDomainBeanById(roles[i].getDomainId()); 
				ret[i].set(IBOAIMonDomainValue.S_DomainCode, domain.getDomainCode());
			}
		}
		return ret;
	}
	
	/**
	 * 根据主键取得用户角色信息
	 * 
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonUserRoleValue getRoleByRoleId(String roleId) throws Exception {
		IAIMonUserRoleSV userRoleSV = (IAIMonUserRoleSV)ServiceFactory.getService(IAIMonUserRoleSV.class);
		return userRoleSV.getBeanById(Long.parseLong(roleId));
	}
	
	/**
	 * 保存或修改用户角色信息
	 * 
	 * @param roleInfo
	 * @throws Exception
	 */
	public void saveUserRole(Object[] roleInfo) throws Exception {
		if (roleInfo == null || roleInfo.length <= 0) {
			return;
		}
		try {
			IBOAIMonUserRoleValue roleValue = new BOAIMonUserRoleBean();
			Timestamp nowDate = getCurrentTimestamp();
			if (StringUtils.isBlank(roleInfo[0].toString())) {
				roleValue.setStsToNew();
				roleValue.setCreateDate(nowDate);
			} else {
				roleValue.setUserRoleId(Long.parseLong(roleInfo[0].toString().trim()));
				roleValue.setStsToOld();
			}
			roleValue.setRoldCode(roleInfo[1].toString().trim());
			roleValue.setRoleName(roleInfo[2].toString().trim());
			
			roleValue.setDoneDate(nowDate);
//			roleValue.setOpId(Long.parseLong(roleInfo[5].toString().trim()));
			roleValue.setState(roleInfo[3].toString().trim());
			roleValue.setNotes(roleInfo[4].toString().trim());
			if(StringUtils.isBlank(roleInfo[5].toString()))
				roleValue.setDomainId(0);
			else
				roleValue.setDomainId(Long.parseLong(roleInfo[5].toString().trim()));
			
			IAIMonUserRoleSV userRoleSV = (IAIMonUserRoleSV)ServiceFactory.getService(IAIMonUserRoleSV.class);
			userRoleSV.saveOrUpdate(roleValue);
			
		} catch (Exception e) {
			//log.error("Call Permission's Method saveUserRole has Exception :" + e.getMessage());
			throw e;
		}
	}
	
	/**
	 * 删除用户角色信息
	 * 
	 * @param userRoleId
	 * @throws Exception
	 */
	public void deleteUserRole(String userRoleId) throws Exception {
		if (StringUtils.isBlank(userRoleId)) {
			return;
		}
		try {
			IAIMonUserRoleSV userRoleSV = (IAIMonUserRoleSV)ServiceFactory.getService(IAIMonUserRoleSV.class);
			userRoleSV.delete(Long.parseLong(userRoleId));
		} catch (Exception e) {
			log.error("Call Permission's Method deleteUserRole has Exception :" + e.getMessage());
			throw e;
		}
	}
	
	/**
	 * 根据条件检索用户实体信息
	 * 
	 * @param entityName
	 * @param entityType
	 * @return
	 * @throws Exception
	 */
	public DataContainerInterface[] getUserEntityByCond(String entityName, String entityType) throws Exception {
		IBOAIMonUserPriEntityValue[] entityValues = null;
		List ret = new ArrayList();
		try {
			IAIMonUserPriEntitySV entitySV = (IAIMonUserPriEntitySV)ServiceFactory.getService(IAIMonUserPriEntitySV.class);
			entityValues = entitySV.getUserEntityByCond(entityName, entityType);
			PriEntity entValue = null;
			if (entityValues != null && entityValues.length > 0) {
				for (int i = 0; i < entityValues.length; i++) {
					DataContainer dc = new DataContainer();
					long parentId = entityValues[i].getParentId();
					dc.set(IBOAIMonUserPriEntityValue.S_EntityId, entityValues[i].getEntityId());
					dc.set(IBOAIMonUserPriEntityValue.S_EntityCode, entityValues[i].getEntityCode());
					dc.set(IBOAIMonUserPriEntityValue.S_EntityName, entityValues[i].getEntityName());
					dc.set(IBOAIMonUserPriEntityValue.S_EntityType, entityValues[i].getEntityType());
					dc.set(IBOAIMonUserPriEntityValue.S_SelfType, entityValues[i].getSelfType());
					dc.set(IBOAIMonUserPriEntityValue.S_EntityAttr, entityValues[i].getEntityAttr());
					dc.set(IBOAIMonUserPriEntityValue.S_EntityStyle, entityValues[i].getEntityStyle());
					dc.set(IBOAIMonUserPriEntityValue.S_DeployType, entityValues[i].getDeployType());
					dc.set(IBOAIMonUserPriEntityValue.S_ParentId, parentId);
					
					// 系统所属
					if (parentId != 1) {
						entValue = entitySV.getEntityByEntityId(String.valueOf(parentId));
						if (entValue != null) {
							dc.set("PARENT_NAME", entValue.getName());
						}
					} else {
						// "系统菜单"
						dc.set("PARENT_NAME", AIMonLocaleFactory.getResource("MOS0000045"));
					}
					
					dc.set(IBOAIMonUserPriEntityValue.S_EntitySeq, entityValues[i].getEntitySeq());
					dc.set(IBOAIMonUserPriEntityValue.S_CreateDate, entityValues[i].getCreateDate());
					dc.set(IBOAIMonUserPriEntityValue.S_DoneDate, entityValues[i].getDoneDate());
					dc.set(IBOAIMonUserPriEntityValue.S_OpId, entityValues[i].getOpId());
					dc.set(IBOAIMonUserPriEntityValue.S_State, entityValues[i].getState());
					dc.set(IBOAIMonUserPriEntityValue.S_Notes, entityValues[i].getNotes());
					ret.add(dc);
				}
			}
			
		} catch (Exception e) {
//			log.error("Call Permission's Method getUserEntityByCond has Exception :" + e.getMessage());
			// "查询权限实体出现异常:"
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000046")+e.getMessage());
		}
		return (DataContainerInterface[])ret.toArray(new DataContainer[0]);
	}
	
	/**
	 * 根据主键取得实体权限信息
	 * 
	 * @param entityId
	 * @return
	 * @throws Exception
	 */
	public PriEntity getEntityByEntityId(String entityId) throws Exception {
		IAIMonUserPriEntitySV entitySV = (IAIMonUserPriEntitySV)ServiceFactory.getService(IAIMonUserPriEntitySV.class);
		return entitySV.getEntityByEntityId(entityId);
	}
	
	/**
	 * 根据主键取得实体权限信息
	 * 
	 * @param entityId
	 * @return
	 * @throws Exception
	 */
	public PriEntity getEntityByEntityIdNocache(String entityId) throws Exception {
		IAIMonUserPriEntitySV entitySV = (IAIMonUserPriEntitySV)ServiceFactory.getService(IAIMonUserPriEntitySV.class);
		return entitySV.getEntityByEntityIdNocache(entityId);
	}
	
	/**
	 * 保存或修改用户实体信息
	 * 
	 * @param entityInfo
	 * @throws Exception
	 */
	public void saveUserEntity(Object[] entityInfo) throws Exception {
		if (entityInfo == null || entityInfo.length <= 0 ) {
			return;
		}
		try {
			IBOAIMonUserPriEntityValue entityValue = new BOAIMonUserPriEntityBean();
			String selfType = entityInfo[10].toString().trim();
			Timestamp nowDate = getCurrentTimestamp();
			if (StringUtils.isBlank(entityInfo[0].toString())) {
				entityValue.setStsToNew();
				if ("1".equals(selfType)) {
					entityValue.setEntityAttr(entityInfo[4].toString().trim());
				} else {
					entityValue.setEntityAttr("module/htmlFrameModule/HtmlFrameModule.swf?url=" + entityInfo[4].toString().trim());
				}
				entityValue.setCreateDate(nowDate);
			} else {
				entityValue.setEntityId(Long.parseLong(entityInfo[0].toString().trim()));
				entityValue.setStsToOld();
				if ("1".equals(selfType)) {
					entityValue.setEntityAttr(entityInfo[4].toString().trim() + "?id=" + entityInfo[0].toString().trim());
				} else {
					entityValue.setEntityAttr("module/htmlFrameModule/HtmlFrameModule.swf?url=" + entityInfo[4].toString().trim());
				}
				
			}
			entityValue.setEntityCode(entityInfo[1].toString().trim());
			entityValue.setEntityName(entityInfo[2].toString().trim());
			entityValue.setEntityType(Integer.parseInt(entityInfo[3].toString().trim()));
			entityValue.setEntityStyle(entityInfo[5].toString().trim());
			entityValue.setParentId(Long.parseLong(entityInfo[6].toString().trim()));
			entityValue.setEntitySeq(Integer.parseInt(entityInfo[7].toString().trim()));
			entityValue.setDoneDate(nowDate);
//			entityValue.setOpId(Long.parseLong(entityInfo[10].toString().trim()));
			entityValue.setState(entityInfo[8].toString().trim());
			entityValue.setNotes(entityInfo[9].toString().trim());
			entityValue.setSelfType(Integer.parseInt(entityInfo[10].toString().trim()));
			// 部署类型
			entityValue.setDeployType(entityInfo[11].toString().trim());
			
			IAIMonUserPriEntitySV entitySV = (IAIMonUserPriEntitySV)ServiceFactory.getService(IAIMonUserPriEntitySV.class);
			entitySV.saveOrUpdate(entityValue);
		} catch (Exception e) {
			log.error("Call Permission's Method saveUserEntity has Exception :" + e.getMessage());
			throw e;
		}
	}
	
	/**
	 * 删除用户实体信息
	 * 
	 * @param userEntityId
	 * @throws Exception
	 */
	public void deleteUserEntity(String userEntityId) throws Exception {
		if (StringUtils.isBlank(userEntityId)) {
			return;
		}
		try {
			IAIMonUserPriEntitySV entitySV = (IAIMonUserPriEntitySV)ServiceFactory.getService(IAIMonUserPriEntitySV.class);
			entitySV.delete(Long.parseLong(userEntityId));
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 取得用户组用户关系信息
	 * 
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonUserValue[] getUserRelByGroupId (String groupId) throws Exception {
		if (StringUtils.isBlank(groupId)) {
			return null;
		}
		IBOAIMonUserValue[] userValues = null;
		try {
			IAIMonUserSV userSV = (IAIMonUserSV)ServiceFactory.getService(IAIMonUserSV.class);
			IAIMonUserGroupRelSV relSV = (IAIMonUserGroupRelSV)ServiceFactory.getService(IAIMonUserGroupRelSV.class);
			userValues = userSV.getUserInfoByCond("", "");
			
			if (userValues != null && userValues.length >0) {
				IBOAIMonUserGroupRelValue[] relValues = relSV.getUserIdByGroupId(groupId);
				for (int i = 0; i < userValues.length; i++) {
					userValues[i].setExtAttr("CHK","false");
					if (relValues != null && relValues.length > 0) {
						for (int j = 0; j < relValues.length; j++) {
							if (userValues[i].getUserId() == relValues[j].getUserId()) {
								userValues[i].setExtAttr("CHK","true");
								break;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Call PermissionAction's Method getUserRelByGroupId has Exception :" + e.getMessage());
			throw e;
		}
		
		return userValues;
	}
	
	/**
	 * 保存指定组与其用户关系
	 * 
	 * @param groupId
	 * @param userIds
	 * @throws Exception
	 */
	public void saveUserGroupRel(String groupId, Object[] userIds) throws Exception {
		if (StringUtils.isBlank(groupId)) {
			return;
		}
		try {
//			deleteUserGroupRel(groupId);

			IAIMonUserGroupRelSV relSV = (IAIMonUserGroupRelSV)ServiceFactory.getService(IAIMonUserGroupRelSV.class);
			IBOAIMonUserGroupRelValue[] oldRelValues = relSV.getUserIdByGroupId(groupId);
			
			if (oldRelValues != null && oldRelValues.length > 0) {
				for (int i = 0; i < oldRelValues.length; i++) {
					oldRelValues[i].delete();
				}
			}
			
			IBOAIMonUserGroupRelValue[] relValues = null;
			if (userIds != null && userIds.length > 0) {
				relValues = new IBOAIMonUserGroupRelValue[userIds.length];
				for (int i = 0; i < userIds.length; i++) {
					relValues[i] = new BOAIMonUserGroupRelBean();
					relValues[i].setUserGroupId(Long.parseLong(groupId));
					relValues[i].setUserId(Long.parseLong(userIds[i].toString().trim()));
					relValues[i].setStsToNew();
				}
			}
			
			relSV.saveOrUpdate(oldRelValues, relValues, userIds);

		} catch (Exception e ) {
			log.error("Call PermissionAction's Method saveUserGroupRel has Exception :" + e.getMessage());
			throw e;
		}
	}
	
	/**
	 * 检查用户是否只隶属于一个组
	 * @param groupId
	 * @param userIds
	 * @return
	 * @throws Exception
	 */
	public boolean checkUserGroupRel(String userId) throws Exception {
		IAIMonUserGroupRelSV relSV = (IAIMonUserGroupRelSV)ServiceFactory.getService(IAIMonUserGroupRelSV.class);
		return relSV.checkUserGroupRelByUserId(userId);
	}
	
	/**
	 * 删除指定组与其用户的关系
	 * 
	 * @param groupId
	 * @throws Exception
	 */
	public void deleteUserGroupRel(String groupId) throws Exception {
		List oldBeans = null;
		try {
			IAIMonUserGroupRelSV relSV = (IAIMonUserGroupRelSV)ServiceFactory.getService(IAIMonUserGroupRelSV.class);
			IBOAIMonUserGroupRelValue[] relValues = relSV.getUserIdByGroupId(groupId);
			
			if (relValues != null && relValues.length > 0) {
//				oldBeans = new ArrayList(relValues.length);
//				for (int i = 0; i < relValues.length; i++) {
//					relValues[i].delete();
//					oldBeans.add(relValues[i]);
//				}
//				relSV.saveOrUpdate((IBOAIMonUserGroupRelValue[])oldBeans.toArray(new IBOAIMonUserGroupRelValue[0]));
				for (int i = 0; i < relValues.length; i++) {
					relValues[i].delete();
				}
				relSV.saveOrUpdate(relValues);
			}
		} catch (Exception e) {
			log.error("Call PermissionAction's Method deleteUserGroupRel has Exception :" + e.getMessage());
			throw e;
		}
	}

	/**
	 * 根据组取得用户信息
	 * 
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonUserValue[] getUserByGroupId(String groupId) throws Exception {
		if (StringUtils.isBlank(groupId)) {
			return null;
		}
		List userResult = null;
		try {
			IAIMonUserGroupRelSV relSV = (IAIMonUserGroupRelSV)ServiceFactory.getService(IAIMonUserGroupRelSV.class);
			IBOAIMonUserGroupRelValue[] userRelValues = relSV.getUserIdByGroupId(groupId);
			
			if (userRelValues != null && userRelValues.length >0) {
				userResult = new ArrayList(userRelValues.length);
				for (int i = 0; i < userRelValues.length; i++) {
					IBOAIMonUserValue userValue = getUserByUserId(String
							.valueOf(userRelValues[i].getUserId()));
					if (userValue != null) {
						userResult.add(userValue);
					}
				}
			}
			if (userResult==null ||userResult.size()<=0)
				return null;
		} catch (Exception e) {
			log.error("Call PermissionAction's Method getUserByGroupId has Exception :" + e.getMessage());
			throw e;
		}
		return (IBOAIMonUserValue[])userResult.toArray(new IBOAIMonUserValue[0]);
	}
	
	/**
	 * 根据角色取得该角色与组关系信息
	 * 
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonUserRoleValue[] getRoleRelByGroupId(String groupId) throws Exception {
		if (StringUtils.isBlank(groupId)) {
			return null;
		}
		IBOAIMonUserRoleValue[] roleValues = null;
		try {
			IAIMonUserRoleSV roleSV = (IAIMonUserRoleSV)ServiceFactory.getService(IAIMonUserRoleSV.class);
			IAIMonUserGroupRoleSV roleRelSV = (IAIMonUserGroupRoleSV)ServiceFactory.getService(IAIMonUserGroupRoleSV.class);
			roleValues = roleSV.getUserRoleByCond("", "");
			
			if (roleValues != null && roleValues.length > 0) {
				IBOAIMonUserGroupRoleRelValue[] roleRelValues = roleRelSV.getRoleIdByGroupId(groupId);
				for (int i = 0; i < roleValues.length; i++) {
					roleValues[i].setExtAttr("CHK", "false");
					if (roleRelValues != null && roleRelValues.length > 0) {
						for (int j = 0; j < roleRelValues.length; j++) {
							if (roleValues[i].getUserRoleId() == roleRelValues[j].getUserRoleId()) {
								roleValues[i].setExtAttr("CHK", "true");
								break;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Call PermissionAction's Method getRoleRelByGroupId has Exception :" + e.getMessage());
			throw e;
		}
		return roleValues;
	}
	
	/**
	 * 保存角色与组关系
	 * 
	 * @param roleId
	 * @param groupIds
	 * @throws Exception
	 */
	public void saveGroupRoleRel(String groupId, Object[] roleIds) throws Exception {
		if (StringUtils.isBlank(groupId)) {
			return;
		}
		try {
			IAIMonUserGroupRoleSV roleRelSV = (IAIMonUserGroupRoleSV)ServiceFactory.getService(IAIMonUserGroupRoleSV.class);
			IBOAIMonUserGroupRoleRelValue[] oldRelValues = roleRelSV.getRoleIdByGroupId(groupId);
			if (oldRelValues != null && oldRelValues.length > 0) {
				for (int i = 0; i < oldRelValues.length; i++) {
					oldRelValues[i].delete();
				}
			}
			
			IBOAIMonUserGroupRoleRelValue[] roleRelValues = null;
			if (roleIds != null && roleIds.length >0) {
				roleRelValues = new IBOAIMonUserGroupRoleRelValue[roleIds.length];
				for (int i = 0; i < roleRelValues.length; i++) {
					roleRelValues[i] = new BOAIMonUserGroupRoleRelBean();
					roleRelValues[i].setStsToNew();
					roleRelValues[i].setUserGroupId(Long.parseLong(groupId));
					roleRelValues[i].setUserRoleId(Long.parseLong(roleIds[i].toString().trim()));
				}
			}
			roleRelSV.saveOrUpdate(oldRelValues, roleRelValues);

		} catch (Exception e) {
			log.error("Call PermissionAction's Method saveGroupRoleRel has Exception :" + e.getMessage());
			throw e;
		}
	}
	
	/**
	 * 删除角色与组关系
	 * 
	 * @param roleId
	 * @throws Exception
	 */
	public void deleteGroupRoleRel(String groupId) throws Exception {
		List oldBeans = null;
		try {
			IAIMonUserGroupRoleSV relSV = (IAIMonUserGroupRoleSV)ServiceFactory.getService(IAIMonUserGroupRoleSV.class);
			IBOAIMonUserGroupRoleRelValue[] relValues = relSV.getRoleIdByGroupId(groupId);
			
			if (relValues != null && relValues.length > 0) {
				oldBeans = new ArrayList(relValues.length);
				for (int i = 0; i < relValues.length; i++) {
					relValues[i].delete();
					oldBeans.add(relValues[i]);
				}
				relSV.saveOrUpdate((IBOAIMonUserGroupRoleRelValue[])oldBeans.toArray(new IBOAIMonUserGroupRoleRelValue[0]));
			}
		} catch (Exception e) {
			log.error("Call PermissionAction's Method deleteGroupRoleRel has Exception :" + e.getMessage());
			throw e;
		}
	}
	
	/**
	 * 根据角色取得组信息
	 * 
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonUserRoleValue[] getRoleByGroupId(String groupId) throws Exception {
		if (StringUtils.isBlank(groupId)) {
			return null;
		}
		List groupResult = null;
		try {
			IAIMonUserGroupRoleSV relSV = (IAIMonUserGroupRoleSV)ServiceFactory.getService(IAIMonUserGroupRoleSV.class);
			IBOAIMonUserGroupRoleRelValue[] relValues = relSV.getRoleIdByGroupId(groupId);
			
			if (relValues != null && relValues.length >0) {
				groupResult = new ArrayList(relValues.length);
				for (int i = 0; i < relValues.length; i++) {
					IBOAIMonUserRoleValue groupValue = getRoleByRoleId(String.valueOf(relValues[i].getUserRoleId()));
					if (groupValue != null) {
						groupResult.add(groupValue);
					}
				}
			}
			if (groupResult==null ||groupResult.size()<=0)
				return null;
		} catch (Exception e) {
			log.error("Call PermissionAction's Method getRoleByGroupId has Exception :" + e.getMessage());
			throw e;
		}
		return (IBOAIMonUserRoleValue[])groupResult.toArray(new IBOAIMonUserRoleValue[0]);
	}
	
	/**
	 * 根据角色ID取得实体权限关系
	 * 
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public DataContainerInterface[] getEntityRelByRoleId(String roleId) throws Exception {
		if (StringUtils.isBlank(roleId)) {
			return null;
		}
		DataContainerInterface[] entityValues = null;
		try {
//			IAIMonUserPriEntitySV entitySV = (IAIMonUserPriEntitySV)ServiceFactory.getService(IAIMonUserPriEntitySV.class);
			IAIMonUserRoleEntityRelSV relSV = (IAIMonUserRoleEntityRelSV)ServiceFactory.getService(IAIMonUserRoleEntityRelSV.class);
//			entityValues = entitySV.getUserEntityByCond("", "");
			entityValues = getUserEntityByCond("", "");
			
			if (entityValues != null && entityValues.length > 0) {
				IBOAIMonUserRoleEntityRelValue[] relValues = relSV.getEntityIdByRoleId(roleId);
				for (int i = 0; i < entityValues.length; i++) {
					entityValues[i].setExtAttr("CHK", "false");
					if (relValues != null && relValues.length > 0) {
						for (int j = 0; j < relValues.length; j++ ) {
							String entityId = String.valueOf(entityValues[i].get(IBOAIMonUserPriEntityValue.S_EntityId));
							String relId = String.valueOf(relValues[j].getEntityId());
							if (relId.equals(entityId)) {
								entityValues[i].setExtAttr("CHK", "true");
								break;
							}
						}
					}
				}
			}
			
		} catch (Exception e) {
			log.error("Call PermissionAction's Method getEntityRelByRoleId has Exception :" + e.getMessage());
			throw e;
		}
		return entityValues;
	}
	
	/**
	 * 保存角色和实体权限关系
	 * 
	 * @param roleId
	 * @param entityIds
	 * @throws Exception
	 */
	public void saveEntityRoleRel(String roleId, Object[] entityIds) throws Exception {
		if (StringUtils.isBlank(roleId)) {
			return;
		}
		try {

			IAIMonUserRoleEntityRelSV relSV = (IAIMonUserRoleEntityRelSV)ServiceFactory.getService(IAIMonUserRoleEntityRelSV.class);
			IBOAIMonUserRoleEntityRelValue[] oldRelValues = relSV.getEntityIdByRoleId(roleId);
			
			if (oldRelValues != null && oldRelValues.length > 0) {
				for (int i = 0; i < oldRelValues.length; i++) {
					oldRelValues[i].delete();
				}
			}

			IBOAIMonUserRoleEntityRelValue[] relValues = null;
			if (entityIds != null && entityIds.length >0) {
				relValues = new IBOAIMonUserRoleEntityRelValue[entityIds.length];
				for (int i = 0; i < relValues.length; i++) {
					relValues[i] = new BOAIMonUserRoleEntityRelBean();
					relValues[i].setStsToNew();
					relValues[i].setUserRoleId(Long.parseLong(roleId));
					relValues[i].setEntityId(Long.parseLong(entityIds[i].toString().trim()));
				}
			}

			relSV.saveOrUpdate(oldRelValues, relValues);
			
		} catch (Exception e) {
			log.error("Call PermissionAction's Method saveEntityRoleRel has Exception :" + e.getMessage());
			throw e;
		}
	}
	
	/**
	 * 删除角色和实体权限关系
	 * 
	 * @param roleId
	 * @throws Exception
	 */
	public void deleteEntityRoleRel(String roleId) throws Exception {
		if (StringUtils.isBlank(roleId)) {
			return;
		}
		List oldBeans = null;
		try {
			IAIMonUserRoleEntityRelSV relSV = (IAIMonUserRoleEntityRelSV)ServiceFactory.getService(IAIMonUserRoleEntityRelSV.class);
			IBOAIMonUserRoleEntityRelValue[] relValues = relSV.getEntityIdByRoleId(roleId);
			
			if (relValues != null && relValues.length > 0) {
				oldBeans = new ArrayList(relValues.length);
				for (int i = 0; i < relValues.length; i++) {
					relValues[i].delete();
					oldBeans.add(relValues[i]);
				}
				relSV.saveOrUpdate((IBOAIMonUserRoleEntityRelValue[])oldBeans.toArray(new IBOAIMonUserRoleEntityRelValue[0]));
			}
			
		} catch (Exception e) {
			log.error("Call PermissionAction's Method deleteEntityRoleRel has Exception :" + e.getMessage());
			throw e;
		}
	}
	
	/**
	 * 根据角色ID取得实体权限信息
	 * 
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public List getEntityByRoleId(String roleId) throws Exception{
		if (StringUtils.isBlank(roleId)) {
			return null;
		}
		List entityResult = null;
		try {
			IAIMonUserRoleEntityRelSV relSV = (IAIMonUserRoleEntityRelSV)ServiceFactory.getService(IAIMonUserRoleEntityRelSV.class);
			IBOAIMonUserRoleEntityRelValue[] relValues = relSV.getEntityIdByRoleId(roleId);
			
			if (relValues != null && relValues.length > 0) {
				entityResult = new ArrayList(relValues.length);
				for (int i = 0; i < relValues.length; i++) {
					PriEntity entityValue = getEntityByEntityId(String.valueOf(relValues[i].getEntityId()));
					if (entityValue != null) {
						entityResult.add(entityValue);
					}
				}
			}
			if (entityResult==null ||entityResult.size()<=0)
				return null;

		} catch (Exception e) {
			log.error("Call PermissionAction's Method getRoleByGroupId has Exception :" + e.getMessage());
			throw e;
		}
		return entityResult;
	}
	/**
	 * 根据角色ID取得实体权限信息
	 * 
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public List getEntityByRoleIdNocache(String roleId) throws Exception{
		if (StringUtils.isBlank(roleId)) {
			return null;
		}
		List entityResult = null;
		try {
			IAIMonUserRoleEntityRelSV relSV = (IAIMonUserRoleEntityRelSV)ServiceFactory.getService(IAIMonUserRoleEntityRelSV.class);
			IBOAIMonUserRoleEntityRelValue[] relValues = relSV.getEntityIdByRoleId(roleId);
			
			if (relValues != null && relValues.length > 0) {
				entityResult = new ArrayList(relValues.length);
				for (int i = 0; i < relValues.length; i++) {
					PriEntity entityValue = getEntityByEntityIdNocache(String.valueOf(relValues[i].getEntityId()));
					if (entityValue != null) {
						entityResult.add(entityValue);
					}
				}
			}
			if (entityResult==null ||entityResult.size()<=0)
				return null;
			
		} catch (Exception e) {
			log.error("Call PermissionAction's Method getRoleByGroupId has Exception :" + e.getMessage());
			throw e;
		}
		return entityResult;
	}
	
	public String getEntityTreeXML(String parentId) throws Exception {
//		StringBuilder treeXm1l = new StringBuilder("<nodes label=\"系统菜单\" id=\"1\" type=\"1\" icon=\"systemImage\" depth=\"0\" state=\"0\" isBranch=\"true\">\n");
		StringBuilder treeXml = new StringBuilder("<nodes label=\"");
		// 系统菜单
		treeXml.append(AIMonLocaleFactory.getResource("MOS0000045")).append("\" id=\"1\" type=\"1\" icon=\"systemImage\" depth=\"0\" state=\"0\" isBranch=\"true\">\n");
		
		try {
			IAIMonUserPriEntitySV entitySV = (IAIMonUserPriEntitySV)ServiceFactory.getService(IAIMonUserPriEntitySV.class);
			List values = entitySV.getEntityByParentId(parentId);
			if (null == values || values.size() < 1)
				return "";
			for (int i = 0; i < values.size(); i++) {
				treeXml.append("<node label=\"").append(((PriEntity)values.get(i)).getName()).append("\" type=\"").append(((PriEntity)values.get(i)).getType()).append("\" id=\"").append(((PriEntity)values.get(i)).getId()).append("\" icon=\"groupImage\" depth=\"1\" state=\"0\" isBranch=\"true\"/>\n");
			}
			treeXml.append("</nodes>");
		} catch (Exception e) {
			log.error("Call PermissionAction's Method getEntityTreeXML has Exception :" + e.getMessage());
		}
		return treeXml.toString();
	}
	
	public String getEntityTreeXMLNocache(String parentId) throws Exception {
//		StringBuilder treeXml = new StringBuilder("<nodes label=\"系统菜单\" id=\"1\" icon=\"systemImage\" depth=\"0\" state=\"0\" isBranch=\"true\">\n");
		StringBuilder treeXml = new StringBuilder("<nodes label=\"");
		// 系统菜单
		treeXml.append(AIMonLocaleFactory.getResource("MOS0000045")).append("\" id=\"1\" type=\"1\" icon=\"systemImage\" depth=\"0\" state=\"0\" isBranch=\"true\">\n");
		
		try {
			IAIMonUserPriEntitySV entitySV = (IAIMonUserPriEntitySV)ServiceFactory.getService(IAIMonUserPriEntitySV.class);
			List values = entitySV.getEntityByParentIdNocache(parentId);
			if (null == values || values.size() < 1)
				return "";
			for (int i = 0; i < values.size(); i++) {
				treeXml.append("<node label=\"").append(((PriEntity)values.get(i)).getName()).append("\" type=\"").append(((PriEntity)values.get(i)).getType()).append("\" id=\"").append(((PriEntity)values.get(i)).getId()).append("\" icon=\"groupImage\" depth=\"1\" state=\"0\" isBranch=\"true\"/>\n");
			}
			treeXml.append("</nodes>");
		} catch (Exception e) {
			log.error("Call PermissionAction's Method getEntityTreeXML has Exception :" + e.getMessage());
		}
		return treeXml.toString();
	}
	
	public List getOtherTreeNode(String parentId) throws Exception {
		if (StringUtils.isBlank(parentId)) {
			return null;
		}
		List result = null;
		try {
			IAIMonUserPriEntitySV entitySV = (IAIMonUserPriEntitySV)ServiceFactory.getService(IAIMonUserPriEntitySV.class);
			List values = entitySV.getEntityByParentId(parentId);
			if (null == values || values.size() < 1)
				return null;
			result = new ArrayList();
			for (int i = 0; i < values.size(); i++) {
				StringBuilder treeXml = new StringBuilder();
				treeXml.append("<node label=\"").append(((PriEntity)values.get(i)).getName()).append("\" type=\"").append(((PriEntity)values.get(i)).getType()).append("\" id=\"").append(((PriEntity)values.get(i)).getId()).append("\" icon=\"hostImage\" state=\"0\" isBranch=\"true\"/>\n");
				result.add(treeXml.toString());
			}
		} catch (Exception e) {
			log.error("Call PermissionAction's Method getOtherTreeNode has Exception :" + e.getMessage());
		}
		return result;
	}
	
	public List getOtherTreeNodeNocache(String parentId) throws Exception {
		if (StringUtils.isBlank(parentId)) {
			return null;
		}
		List result = null;
		try {
			IAIMonUserPriEntitySV entitySV = (IAIMonUserPriEntitySV)ServiceFactory.getService(IAIMonUserPriEntitySV.class);
			List values = entitySV.getEntityByParentIdNocache(parentId);
			if (null == values || values.size() < 1)
				return null;
			result = new ArrayList();
			for (int i = 0; i < values.size(); i++) {
				StringBuilder treeXml = new StringBuilder();
				treeXml.append("<node label=\"").append(((PriEntity)values.get(i)).getName()).append("\" type=\"").append(((PriEntity)values.get(i)).getType()).append("\" id=\"").append(((PriEntity)values.get(i)).getId()).append("\" icon=\"hostImage\" state=\"0\" isBranch=\"true\"/>\n");
				result.add(treeXml.toString());
			}
		} catch (Exception e) {
			log.error("Call PermissionAction's Method getOtherTreeNode has Exception :" + e.getMessage());
		}
		return result;
	}
	
	/**
	 * 获取预加载的实体信息
	 * @return
	 * @throws Exception
	 */
	public List getPreLoadEntity() throws Exception{
		List result=null;
		try{
			IAIMonUserPriEntitySV entitySV = (IAIMonUserPriEntitySV)ServiceFactory.getService(IAIMonUserPriEntitySV.class);
			result=entitySV.getEntityByLoadType("1");
		}catch(Exception e){
			log.error("Call PermissionAction's Method getPreLoadEntity has Exception :"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 根据区域标识获取扩展实体
	 * @param entityId
	 * @return
	 * @throws Exception
	 */
	public List getExtendEntitysByEntityId(String entityId) throws Exception{
		List result=null;
		try{
			IAIMonUserPriEntitySV entitySV = (IAIMonUserPriEntitySV)ServiceFactory.getService(IAIMonUserPriEntitySV.class);
			result=entitySV.getExtendEntitysByEntityId(entityId);
		}catch(Exception e){
			log.error("Call PermissionAction's Method getExtendEntitysByEntityId has Exception :"+e.getMessage());
			throw e;
		}
		return result;
	}
	
	/**
	 * 获取所有面板
	 * @return
	 * @throws Exception
	 */
	public DataContainerInterface[] getEntityRelatPanel(String entityId,String styMethod)	throws Exception{
		if(StringUtils.isBlank(entityId))
			// "请传入面板实体标识！"
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000047"));
		IAIMonAttentionPanelSV panelSV = (IAIMonAttentionPanelSV) ServiceFactory.getService(IAIMonAttentionPanelSV.class);
		IBOAIMonAttentionPanelValue[] panels = panelSV.getAllAttentionPanelBean();
		if(panels == null) return null;
		
		IAIMonPanelRelatEntitySV relatSV = (IAIMonPanelRelatEntitySV) ServiceFactory.getService(IAIMonPanelRelatEntitySV.class);		
		IBOAIMonPanelRelatEntityValue[] relats = relatSV.getRelatByEntityId(entityId);
		DataContainerInterface[] rets = new DataContainer[panels.length];
		for(int i=0;i<panels.length;i++){
			rets[i] = new DataContainer();
			rets[i].copy(panels[i]);
			rets[i].setExtAttr("CHK","false");
			for(int j=0;j<relats.length;j++){
				if(relats[j].getPanelId() == panels[i].getPanelId()){
					rets[i].setExtAttr("CHK","true");
					rets[i].set("RELAT_REMARKS",relats[j].getRemarks());
					rets[i].set("RELAT_ID",relats[j].getRelatId());
				}
			}
			
		}
		return rets;
	}
	
	/**
	 * 获取所有面板
	 * @return
	 * @throws Exception
	 */
	public DataContainerInterface[] getEntityRelatCustomPanel(String entityId,String styMethod)	throws Exception{
		if(StringUtils.isBlank(entityId))
			// "请传入面板实体标识！"
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000047"));
		IAIMonCustomizePanelSV panelSV = (IAIMonCustomizePanelSV) ServiceFactory.getService(IAIMonCustomizePanelSV.class);
		IBOAIMonCustomizePanelValue[] panels = panelSV.getAllCustomPanelBean();
		if(panels == null) return null;
		
		IAIMonPanelRelatEntitySV relatSV = (IAIMonPanelRelatEntitySV) ServiceFactory.getService(IAIMonPanelRelatEntitySV.class);		
		IBOAIMonPanelRelatEntityValue[] relats = relatSV.getRelatByEntityId(entityId);
		DataContainerInterface[] rets = new DataContainer[panels.length];
		for(int i=0;i<panels.length;i++){
			rets[i] = new DataContainer();
			rets[i].copy(panels[i]);
			rets[i].setExtAttr("CHK","false");
			for(int j=0;j<relats.length;j++){
				if(relats[j].getPanelId() == panels[i].getCpanelId()){
					rets[i].setExtAttr("CHK","true");
					rets[i].set("RELAT_REMARKS",relats[j].getRemarks());
					rets[i].set("RELAT_ID",relats[j].getRelatId());
				}
			}
			
		}
		return rets;
	}
	
	/**
	 * 保存实体与面板的关系数据
	 * @param entityId
	 * @param addPanelIds 增加的面板id
	 * @param delPanelIds 删除的面板id
	 * @throws Exception
	 */
	public void saveEntityPanelRelat(String entityId, Object[] addPanelIds,Object[] addPanelTypes,Object[] delRelatIds) throws Exception{
		if(StringUtils.isBlank(entityId))
			// "请传入面板实体标识！"
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000047"));
		IAIMonPanelRelatEntitySV relatSV = (IAIMonPanelRelatEntitySV) ServiceFactory.getService(IAIMonPanelRelatEntitySV.class);
		int l = 0;
		if(addPanelIds!=null && addPanelIds.length>0) l+=addPanelIds.length;
		if(delRelatIds!=null && delRelatIds.length>0) l+=delRelatIds.length;
		// "没有需要保存的数据!"
		if(l == 0) throw new Exception(AIMonLocaleFactory.getResource("MOS0000048"));
		IBOAIMonPanelRelatEntityValue[] relats = new IBOAIMonPanelRelatEntityValue[l];
		int i=0;
		if(addPanelIds!=null && addPanelIds.length>0){
			for(;i<addPanelIds.length;i++){
				relats[i] = new BOAIMonPanelRelatEntityBean();
				relats[i].setStsToNew();
				relats[i].setEntityId(Long.parseLong(entityId));
				relats[i].setPanelId(Long.parseLong(addPanelIds[i].toString()));
				relats[i].setState("U");
				relats[i].setPanelType(addPanelTypes[i].toString());
			}
		}
		
		if(delRelatIds!=null && delRelatIds.length>0){
			for(int j = 0; j < delRelatIds.length ;j++){
				relats[i+j] = new BOAIMonPanelRelatEntityBean();
				relats[i+j].setRelatId(Long.parseLong(delRelatIds[j].toString()));
				relats[i+j].setStsToOld();
				relats[i+j].delete();
			}
		}

		relatSV.saveEntityPanelRelat(relats);
	}
	
	/**
	 * 根据角色ID获取角色与实体的状态关系树
	 * 
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public String getRoleEntityTreeXml(String roleId) throws Exception {

		IAIMonUserRoleEntityRelSV relSv = (IAIMonUserRoleEntityRelSV)ServiceFactory.getService(IAIMonUserRoleEntityRelSV.class);
		return relSv.getRoleEntityTreeXml(roleId);
		
	}
	/**
	 * 根据角色ID获取角色与实体的状态关系树
	 * 
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public String getRoleEntityTreeXmlNocache(String roleId) throws Exception {
		
		IAIMonUserRoleEntityRelSV relSv = (IAIMonUserRoleEntityRelSV)ServiceFactory.getService(IAIMonUserRoleEntityRelSV.class);
		return relSv.getRoleEntityTreeXmlNocache(roleId);
		
	}
	
	public static void main(String args[]) throws Exception {
		PermissionAction perAction = new PermissionAction();
		String s = perAction.getEntityTreeXMLNocache("0");
		System.out.println(s);
	}
}
