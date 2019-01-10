package com.asiainfo.monitor.busi.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.service.ServiceFactory;
import com.ai.appframe2.web.action.BaseAction;
import com.asiainfo.monitor.busi.cache.interfaces.IServer;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonAppRuleSetSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonColgRuleSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonRuleSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonRuleSetRelatSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonRuleSetSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonAppRuleSetBean;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonColgRuleBean;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonRuleBean;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonRuleSetBean;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonRuleSetRelatBean;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonAppRuleSetValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonColgRuleValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonRuleSetRelatValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonRuleSetValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonRuleValue;

public class RuleSetAction extends BaseAction {

	private static transient Log log=LogFactory.getLog(RuleSetAction.class);
	
	/**
	 * 根据规则集标识，读取规则集信息
	 * @param ruleSetId
	 * @throws Exception
	 */
	public IBOAIMonRuleSetValue getRuleSet(String ruleSetId) throws Exception{
		if (StringUtils.isBlank(ruleSetId)){
			return null;
		}
		IAIMonRuleSetSV ruleSetSV=(IAIMonRuleSetSV)ServiceFactory.getService(IAIMonRuleSetSV.class);
		IBOAIMonRuleSetValue ruleSetVO=ruleSetSV.getRuleSetById(ruleSetId);
		return ruleSetVO;
	}
	
	/**
	 * 根据类型，读取规则集信息
	 * @param type
	 * @param kind
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonRuleSetValue[] getAllRuleSet(String type,String kind) throws Exception{
		IAIMonRuleSetSV ruleSetSV=(IAIMonRuleSetSV)ServiceFactory.getService(IAIMonRuleSetSV.class);
		IBOAIMonRuleSetValue[] ruleSetVOs=ruleSetSV.getRuleSetByType(type,kind);
		if (ruleSetVOs != null && ruleSetVOs.length > 0) {
			for (int i=0;i < ruleSetVOs.length;i++){
				ruleSetVOs[i].setExtAttr("CHK","false");
			}
		}
		return ruleSetVOs;
	}
	
	/**
	 * 根据规则集，读取规则信息
	 * @param ruleSetId
	 * @return
	 * @throws Exception
	 */
	public List getRuleByRuleSetId(String ruleSetId) throws Exception {
		if (StringUtils.isBlank(ruleSetId)){
			return null;
		}
		List result=null;
		IAIMonRuleSetRelatSV ruleSetRelatSV=(IAIMonRuleSetRelatSV)ServiceFactory.getService(IAIMonRuleSetRelatSV.class);
		IBOAIMonRuleSetRelatValue[] relatValues=ruleSetRelatSV.getRuleSetRelatByRuleSetId(ruleSetId);
		if (relatValues!=null){
			result=new ArrayList(relatValues.length);
			IAIMonRuleSV ruleSV=(IAIMonRuleSV)ServiceFactory.getService(IAIMonRuleSV.class);
			for (int i=0;i<relatValues.length;i++){
				IBOAIMonRuleValue ruleValue=ruleSV.getRuleById(relatValues[i].getRuleId()+"");
				if (ruleValue!=null){
					result.add(ruleValue);
				}
			}
		}
		return result;
	}
	
	/**
	 * 保存规则集
	 * @param ruleInfo
	 * @throws Exception
	 */
	public void saveRuleSet(Object[] ruleInfo) throws Exception{
		if (ruleInfo==null || ruleInfo.length<=0)
			return;
		try{
			IBOAIMonRuleSetValue ruleSetValue=new BOAIMonRuleSetBean();
			
			if (StringUtils.isBlank(ruleInfo[0].toString())) {
				ruleSetValue.setState("U");
				ruleSetValue.setStsToNew();
			} else {
				ruleSetValue.setRulesetId(Long.parseLong(ruleInfo[0].toString().trim()));
				ruleSetValue.setState(ruleInfo[6].toString().trim());
				ruleSetValue.setStsToOld();
			}
			ruleSetValue.setRulesetCode(ruleInfo[1].toString().trim());
			ruleSetValue.setRulesetName(ruleInfo[2].toString().trim());
			ruleSetValue.setRulesetType(ruleInfo[3].toString().trim());
			ruleSetValue.setRulesetKind(ruleInfo[4].toString().trim());
			ruleSetValue.setRemark(ruleInfo[5].toString().trim());
			
			IAIMonRuleSetSV ruleSetSV=(IAIMonRuleSetSV)ServiceFactory.getService(IAIMonRuleSetSV.class);
			ruleSetSV.saveOrUpdate(ruleSetValue);
			
		}catch(Exception e){
			log.error("Call RuleSetAction's Method saveRuleSet has Exception :"+e.getMessage());
		}				
	}
	
	/**
	 * 删除规则集
	 * @param ruleSetId
	 * @throws Exception
	 */
	public void deleteRuleSet(String ruleSetId) throws Exception{
		if (StringUtils.isBlank(ruleSetId))
			return;
		try{
			//删除规则集表中对应信息
			IAIMonRuleSetSV ruleSetSV = (IAIMonRuleSetSV)ServiceFactory.getService(IAIMonRuleSetSV.class);
			ruleSetSV.deleteRuleSet(Long.parseLong(ruleSetId));
			//删除应用服务关联规则集表中的对应信息
			IAIMonAppRuleSetSV ruleSetRelatSV=(IAIMonAppRuleSetSV)ServiceFactory.getService(IAIMonAppRuleSetSV.class);
			IBOAIMonAppRuleSetValue[] relateVOs=ruleSetRelatSV.getAppRuleSet("");
			if (relateVOs!=null && relateVOs.length>0){
				Object [] appIds = new Object[relateVOs.length];
				for (int i = 0;i < relateVOs.length;i++){
					appIds[i] = (Object) relateVOs[i].getAppId();
				}
				Object [] rulesetIds = new Object[0];
				rulesetIds[0] = (Object)ruleSetId;
				delAppRelatData(rulesetIds,"",appIds);
			}
		}catch(Exception e){
			log.error("Call RuleSetAction's Method deleteRuleSet has Exception :"+e.getMessage());
		}	
	}
	
	/**
	 * 根据类型，读取规则信息
	 * @param type
	 * @param kind
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonRuleValue[] getAllRule(String type,String kind) throws Exception{
		IAIMonRuleSV ruleSV=(IAIMonRuleSV)ServiceFactory.getService(IAIMonRuleSV.class);
		IBOAIMonRuleValue[] ruleSetVOs=ruleSV.getRuleByType(type,kind);
		return ruleSetVOs;
	}
	
	/**
	 * 保存规则
	 * @param ruleInfo
	 * @throws Exception
	 */
	public void saveRule(Object[] ruleInfo) throws Exception{
		if (ruleInfo==null || ruleInfo.length<=0)
			return;
		try{
			IBOAIMonRuleValue ruleValue=new BOAIMonRuleBean();
			
			if (StringUtils.isBlank(ruleInfo[0].toString())) {
				ruleValue.setState("U");
				ruleValue.setStsToNew();
			} else {
				ruleValue.setRuleId(Long.parseLong(ruleInfo[0].toString().trim()));
				ruleValue.setState(ruleInfo[7].toString().trim());
				ruleValue.setStsToOld();
			}
			
			ruleValue.setRuleCode(ruleInfo[1].toString().trim());
			ruleValue.setRuleType(ruleInfo[2].toString().trim());
			ruleValue.setRuleKind(ruleInfo[3].toString().trim());
			ruleValue.setRuleKey(ruleInfo[4].toString().trim());
			ruleValue.setRuleValue(ruleInfo[5].toString().trim());
			ruleValue.setRemark(ruleInfo[6].toString().trim());
			
			IAIMonRuleSV ruleSV=(IAIMonRuleSV)ServiceFactory.getService(IAIMonRuleSV.class);
			ruleSV.saveOrUpdate(ruleValue);
			
		}catch(Exception e){
			log.error("Call RuleSetAction's Method saveRule has Exception :"+e.getMessage());
		}				
	}
	
	/**
	 * 删除规则
	 * @param ruleId
	 * @throws Exception
	 */
	public void deleteRule(String ruleId) throws Exception{
		if (StringUtils.isBlank(ruleId))
			return;
		try{
			//删除规则表中对应信息
			IAIMonRuleSV ruleSV = (IAIMonRuleSV)ServiceFactory.getService(IAIMonRuleSV.class);
			ruleSV.deleteRule(Long.parseLong(ruleId));
			//删除规则集关联规则表中的对应信息
			IAIMonRuleSetRelatSV ruleSetRelatSV=(IAIMonRuleSetRelatSV)ServiceFactory.getService(IAIMonRuleSetRelatSV.class);
			IBOAIMonRuleSetRelatValue[] ruleSetRelatVos = ruleSetRelatSV.getRuleSetRelatByRuleId(ruleId);
			if (ruleSetRelatVos != null && ruleSetRelatVos.length > 0){
				for (int i = 0;i < ruleSetRelatVos.length;i++){
					ruleSetRelatVos[i].delete();
				}
				ruleSetRelatSV.saveOrUpdate(ruleSetRelatVos);
			}
		}catch(Exception e){
			log.error("Call RuleSetAction's Method deleteRule has Exception :"+e.getMessage());
		}	
	}
	
	/**
	 * 返回所有符合指定规则集类型的规则信息,并且将该隶属该规则集的规则做标记
	 * @param ruleSetId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonRuleValue[] getAllRuleByRuleSetId(String ruleSetId) throws Exception{
		if (StringUtils.isBlank(ruleSetId)){
			return null;			
		}
		IBOAIMonRuleValue[] ruleValues = null;
		try{
			IAIMonRuleSetSV ruleSetSV=(IAIMonRuleSetSV)ServiceFactory.getService(IAIMonRuleSetSV.class);
			IBOAIMonRuleSetValue ruleSetVO=ruleSetSV.getRuleSetById(ruleSetId);
			if (ruleSetVO==null){
				return null;
			}
			IAIMonRuleSV ruleSV=(IAIMonRuleSV)ServiceFactory.getService(IAIMonRuleSV.class);
			ruleValues=ruleSV.getRuleByType(ruleSetVO.getRulesetType(),ruleSetVO.getRulesetKind());
			if (ruleValues!=null && ruleValues.length>0){
				List result=null;
				IAIMonRuleSetRelatSV ruleSetRelatSV=(IAIMonRuleSetRelatSV)ServiceFactory.getService(IAIMonRuleSetRelatSV.class);
				IBOAIMonRuleSetRelatValue[] relatValues=ruleSetRelatSV.getRuleSetRelatByRuleSetId(ruleSetId);
				if (relatValues!=null){
					result=new ArrayList(relatValues.length);
					for (int i=0;i<relatValues.length;i++){
						IBOAIMonRuleValue ruleValue=ruleSV.getRuleById(relatValues[i].getRuleId()+"");
						if (ruleValue!=null){
							result.add(ruleValue);
						}
					}
				}
				for (int rec=0;rec<ruleValues.length;rec++){
					ruleValues[rec].setExtAttr("CHK","false");
					if (result!=null && result.size()>0){
						for (int i=0;i<result.size();i++){
							if (ruleValues[rec].getRuleId()==((IBOAIMonRuleValue)result.get(i)).getRuleId()){
								ruleValues[rec].setExtAttr("CHK","true");
								break;
							}
						}
					}
				}
			}
		}catch(Exception e){
			log.error("Call RuleSetAction's Method deleteRule has Exception :"+e.getMessage());
		}
		return ruleValues;
	}
	
	/**
	 * 根据应用服务ID查询规则集
	 * @param appId
	 * @throws Exception
	 */
	public List getRelatDataByAppId(String appId) throws Exception{
		if (StringUtils.isBlank(appId)){
			return null;			
		}
		List results = null;
		//根据应用服务ID查询关联规则集信息
		IAIMonAppRuleSetSV ruleSetRelatSV=(IAIMonAppRuleSetSV)ServiceFactory.getService(IAIMonAppRuleSetSV.class);
		IBOAIMonAppRuleSetValue[] relateVOs=ruleSetRelatSV.getAppRuleSet(appId);
		if (relateVOs!=null && relateVOs.length>0){
			List ruleSetIds = new ArrayList();
			String[] lineargs = null;
			for (int i=0;i<relateVOs.length;i++){
				lineargs = relateVOs[i].getRulesetIds().toString().split(",");
				if (lineargs !=null && lineargs.length > 0){
					for (int j=0;j<lineargs.length;j++){
						ruleSetIds.add(lineargs[j].trim());
					}
				}
			}
			//根据规则集ID查询规则集信息
			if (ruleSetIds !=null && ruleSetIds.size() > 0) {
				results = new ArrayList();
				IAIMonRuleSetSV ruleSetSV=(IAIMonRuleSetSV)ServiceFactory.getService(IAIMonRuleSetSV.class);
				for (int i=0;i<ruleSetIds.size();i++){
					IBOAIMonRuleSetValue ruleSetVO=ruleSetSV.getRuleSetById(ruleSetIds.get(i).toString());
					if (ruleSetVO != null){
						results.add(ruleSetVO);
					}	
				}
			}
		}
		return results;
	}
	
	/**
	 * 根据应用服务ID和规则集类型查询规则集
	 * @param appId
	 * @param type
	 * @throws Exception
	 */
	public IBOAIMonRuleSetValue[] getRelatDataByCond(String appId,String type) throws Exception{
		if (StringUtils.isBlank(appId)){
			return null;			
		}
		if (StringUtils.isBlank(type)){
			return null;
		}
		IAIMonRuleSetSV ruleSetSV=(IAIMonRuleSetSV)ServiceFactory.getService(IAIMonRuleSetSV.class);
		IBOAIMonRuleSetValue[] ruleSetVOs=ruleSetSV.getRuleSetByType(type,"");
		if (ruleSetVOs!=null && ruleSetVOs.length>0){
			IAIMonAppRuleSetSV ruleSetRelatSV=(IAIMonAppRuleSetSV)ServiceFactory.getService(IAIMonAppRuleSetSV.class);
			IBOAIMonAppRuleSetValue relateVO=ruleSetRelatSV.getAppRuleSet(appId,type);
			for (int rec=0;rec<ruleSetVOs.length;rec++){
				ruleSetVOs[rec].setExtAttr("CHK","false");
				if (relateVO!=null){
					String ruleSetIds = relateVO.getRulesetIds().toString();
					if (-1 != ruleSetIds.indexOf(""+ruleSetVOs[rec].getRulesetId())){
						ruleSetVOs[rec].setExtAttr("CHK","true");
					}
				}
			}
		}
		return ruleSetVOs;
	}
	

	/**
	 * 保存应用服务关联规则集设置
	 * @param relatRuleSetIds
	 * @param ruleType
	 * @param appIds
	 * @throws Exception
	 */
	public String saveAppRelatData(Object[] relatRuleSetIds,String ruleType,Object[] appIds) throws Exception{
		if (appIds==null || appIds.length<=0)
			return null;
		if (relatRuleSetIds==null || relatRuleSetIds.length<=0)
			return null;
		try{
			IAIMonAppRuleSetSV ruleSetRelatSV=(IAIMonAppRuleSetSV)ServiceFactory.getService(IAIMonAppRuleSetSV.class);
			IBOAIMonAppRuleSetValue[] rulesetRelatVos = new BOAIMonAppRuleSetBean[appIds.length];
			
			for (int j = 0;j < appIds.length; j++) {
				IBOAIMonAppRuleSetValue oldRelatValue=ruleSetRelatSV.getAppRuleSet(appIds[j].toString(),ruleType);
				if (oldRelatValue!=null){
					String dbRuleSetIds=oldRelatValue.getRulesetIds();
					if (StringUtils.isNotBlank(dbRuleSetIds)){
						String[] ruleSetIds=StringUtils.split(dbRuleSetIds,",");
						StringBuilder typeStr = new StringBuilder(dbRuleSetIds);
						
						for (int i = 0;i < relatRuleSetIds.length;i++){
							boolean has=false;
							for (int recount=0;recount<ruleSetIds.length;recount++){
								if (relatRuleSetIds[i].equals(ruleSetIds[recount])){
									has=true;
									break;
								}
							}
							if (!has){
								typeStr.append(","+relatRuleSetIds[i]);
							}
						}
						oldRelatValue.setRulesetIds(typeStr.toString());
					}else{
						StringBuilder typeStr=new StringBuilder("");
						for (int i=0;i<relatRuleSetIds.length;i++){
							if (i==0){
								typeStr.append(relatRuleSetIds[i]);
							}else{
								typeStr.append(",");
								typeStr.append(relatRuleSetIds[i]);
							}							
						}
						oldRelatValue.setRulesetIds(typeStr.toString());
					}
					rulesetRelatVos[j]=oldRelatValue;
					
				}else{
					IBOAIMonAppRuleSetValue ruleSetRelatValue=null;
					ruleSetRelatValue=new BOAIMonAppRuleSetBean();
					ruleSetRelatValue.setAppRuleType(ruleType);
					
					ruleSetRelatValue.setState("U");
					ruleSetRelatValue.setStsToNew();
					ruleSetRelatValue.setAppId(Long.parseLong(appIds[j].toString()));
					
					StringBuilder typeStr=new StringBuilder("");
					for (int i=0;i<relatRuleSetIds.length;i++){
						if (i==0){
							typeStr.append(relatRuleSetIds[i]);								
						}else{
							typeStr.append(",");
							typeStr.append(relatRuleSetIds[i]);
						}							
					}
					ruleSetRelatValue.setRulesetIds(typeStr.toString());
					rulesetRelatVos[j] = ruleSetRelatValue;
				}
				
			}
			ruleSetRelatSV.saveOrUpdate(rulesetRelatVos);
		}catch(Exception e){
			log.error("Call RuleSetAction's Method saveAppRelatData has Exception :"+e.getMessage());
		}
		return "OK";
	}
	
	/**
	 * 移除应用服务关联规则集
	 * @param relatRuleSetIds
	 * @param ruleType
	 * @param appIds
	 * @throws Exception
	 */
	public String delAppRelatData(Object[] relatRuleSetIds,String ruleType,Object[] appIds) throws Exception{
		if (null == appIds || appIds.length<=0)
			return null;
		if (null == relatRuleSetIds || relatRuleSetIds.length<=0)
			return null;
		try{
			IAIMonAppRuleSetSV ruleSetRelatSV=(IAIMonAppRuleSetSV)ServiceFactory.getService(IAIMonAppRuleSetSV.class);
			IBOAIMonAppRuleSetValue oldRelatValue = null;
			for (int i = 0;i < appIds.length; i++) {
				oldRelatValue=ruleSetRelatSV.getAppRuleSet(appIds[i].toString(),ruleType);
				if (null == oldRelatValue)
					continue;
				int findIndex = -1;
				StringBuilder newRuleSetIds = new StringBuilder(oldRelatValue.getRulesetIds());
				String oldRuleSetIds = "";
				String splitChar = ",";
				for (int j = 0;j < relatRuleSetIds.length;j++){
					oldRuleSetIds = newRuleSetIds.toString();
					String[] lineargs = oldRuleSetIds.split(splitChar);
					for (int k = 0;k < lineargs.length;k++){
						if (relatRuleSetIds[j].toString().equals(lineargs[k])){
							findIndex = k;
							break;
						}
					}
					if (-1 != findIndex){
						newRuleSetIds = new StringBuilder("");
						for (int k = 0;k < lineargs.length;k++){
							if (k == findIndex)
								continue;
							if (StringUtils.isNotBlank(newRuleSetIds.toString())){
								newRuleSetIds.append(splitChar).append(lineargs[k]);
							} else {
								newRuleSetIds.append(lineargs[k]);
							}
						}
						oldRelatValue.delete();
						ruleSetRelatSV.saveOrUpdate(oldRelatValue);
						oldRelatValue.setRulesetIds(newRuleSetIds.toString());
						oldRelatValue.setStsToNew();
						ruleSetRelatSV.saveOrUpdate(oldRelatValue);
					}
				}
			}
			return "OK";
		}catch(Exception e){
			log.error("Call RuleSetAction's Method saveAppRelatData has Exception :"+e.getMessage());
		}
		return null;
	}
	
	/**
	 * 保存规则集关联规则设置
	 * @param ruleSetId
	 * @param relatRuleIds
	 * @throws Exception
	 */
	public void saveRelatData(String ruleSetId,Object[] relatRuleIds) throws Exception{
		if (StringUtils.isBlank(ruleSetId))
			return;
//		if (relatRuleIds==null || relatRuleIds.length<=0)
//			return;
		try{
			IBOAIMonRuleSetRelatValue[] ruleSetRelatValues=null;
			if (relatRuleIds!=null && relatRuleIds.length>0){
				ruleSetRelatValues=new IBOAIMonRuleSetRelatValue[relatRuleIds.length];
				for (int j=0;j<relatRuleIds.length;j++){
					ruleSetRelatValues[j]=new BOAIMonRuleSetRelatBean();
					ruleSetRelatValues[j].setRulesetId(Long.parseLong(ruleSetId));
					ruleSetRelatValues[j].setRuleId(Long.parseLong(relatRuleIds[j].toString()));			
					ruleSetRelatValues[j].setState("U");	
					ruleSetRelatValues[j].setStsToNew();
				}
			}			
			IAIMonRuleSetRelatSV ruleSetRelatSV=(IAIMonRuleSetRelatSV)ServiceFactory.getService(IAIMonRuleSetRelatSV.class);
			ruleSetRelatSV.saveRelatData(ruleSetId,ruleSetRelatValues);
		}catch(Exception e){
			log.error("Call RuleSetAction's Method saveRelatData has Exception :"+e.getMessage());
		}				
	}
	
	/**
	 * 根据规则ID，读取采集规则信息
	 * @param colgRuleId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonColgRuleValue getColgRuleByColtId(String colgRuleId) throws Exception{
		IAIMonColgRuleSV ruleSV=(IAIMonColgRuleSV)ServiceFactory.getService(IAIMonColgRuleSV.class);
		return ruleSV.getColgRuleById(colgRuleId);
	}
	
	/**
	 * 根据应用服务ID，读取采集规则信息
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonColgRuleValue[] getColgRuleByAppId(String appId) throws Exception{
		IAIMonColgRuleSV ruleSV=(IAIMonColgRuleSV)ServiceFactory.getService(IAIMonColgRuleSV.class);
		return ruleSV.getColgRuleByAppId(appId);
	}
	
	/**
	 * 根据条件，检索采集规则信息
	 * 
	 * @param ruleName
	 * @param ruleType
	 * @return
	 * @throws Exception
	 */
	public DataContainerInterface[] getColgRuleByCond(String ruleName, String ruleType) throws Exception {
		IAIMonColgRuleSV ruleSV=(IAIMonColgRuleSV)ServiceFactory.getService(IAIMonColgRuleSV.class);
		IAIMonServerSV serverSV = (IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
		
		IBOAIMonColgRuleValue[] ruleValues = ruleSV.getColgRuleByCond(ruleName, ruleType);
		IServer serverValue = null;
		List ret = new ArrayList();
		for (int i = 0; i < ruleValues.length; i++) {
			DataContainer dc = new DataContainer();
			dc.set(IBOAIMonColgRuleValue.S_RuleId, ruleValues[i].getRuleId());
			dc.set(IBOAIMonColgRuleValue.S_RuleName, ruleValues[i].getRuleName());
			dc.set(IBOAIMonColgRuleValue.S_AppId, ruleValues[i].getAppId());
			serverValue = serverSV.getServerByServerId(String.valueOf(ruleValues[i].getAppId()));
			dc.set("APP_NAME", serverValue.getApp_Name());
			dc.set(IBOAIMonColgRuleValue.S_RuleType, ruleValues[i].getRuleType());
			dc.set(IBOAIMonColgRuleValue.S_Expr0, ruleValues[i].getExpr0());
			dc.set(IBOAIMonColgRuleValue.S_Expr1, ruleValues[i].getExpr1());
			dc.set(IBOAIMonColgRuleValue.S_Expr2, ruleValues[i].getExpr2());
			dc.set(IBOAIMonColgRuleValue.S_Expr3, ruleValues[i].getExpr3());
			dc.set(IBOAIMonColgRuleValue.S_State, ruleValues[i].getState());
			dc.set(IBOAIMonColgRuleValue.S_Remark, ruleValues[i].getRemark());
			
			ret.add(dc);
		}
		return (DataContainerInterface[])ret.toArray(new DataContainer[0]);
	}
	
	/**
	 * 保存采集规规则
	 * @param coltInfo
	 * @throws Exception
	 */
	public void saveColgRule(Object[] coltInfo) throws Exception{
		if (coltInfo==null || coltInfo.length<=0)
			return;
		try{
			IBOAIMonColgRuleValue coltValue=new BOAIMonColgRuleBean();
			
			if (StringUtils.isBlank(coltInfo[0].toString())) {
				coltValue.setStsToNew();
			} else {
				coltValue.setRuleId(Long.parseLong(coltInfo[0].toString().trim()));
				coltValue.setStsToOld();
			}
			
			coltValue.setRuleName(coltInfo[1].toString().trim());
			coltValue.setAppId(Long.parseLong(coltInfo[2].toString().trim()));
			coltValue.setRuleKind(coltInfo[3].toString().trim());
			coltValue.setRuleType(coltInfo[4].toString().trim());
			coltValue.setExpr0(coltInfo[5].toString().trim());
			coltValue.setExpr1(coltInfo[6].toString().trim());
			coltValue.setExpr2(coltInfo[7].toString().trim());
			coltValue.setExpr3(coltInfo[8].toString().trim());
			coltValue.setState(coltInfo[9].toString().trim());
			coltValue.setRemark(coltInfo[10].toString().trim());

			IAIMonColgRuleSV ruleSV=(IAIMonColgRuleSV)ServiceFactory.getService(IAIMonColgRuleSV.class);
			ruleSV.saveOrUpdate(coltValue);
			
		}catch(Exception e){
			log.error("Call RuleSetAction's Method saveColgRule has Exception :"+e.getMessage());
		}				
	}
	
	/**
	 * 删除采集规则
	 * @param colgRuleId
	 * @throws Exception
	 */
	public void deleteColgRule(String colgRuleId) throws Exception{
		if (StringUtils.isBlank(colgRuleId))
			return;
		try{
			IAIMonColgRuleSV ruleSV=(IAIMonColgRuleSV)ServiceFactory.getService(IAIMonColgRuleSV.class);
			ruleSV.deleteColgRule(Long.parseLong(colgRuleId));
		}catch(Exception e){
			log.error("Call RuleSetAction's Method deleteColgRule has Exception :"+e.getMessage());
		}	
	}
	
}
