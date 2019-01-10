package com.asiainfo.monitor.busi.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataStructInterface;
import com.ai.appframe2.service.ServiceFactory;
import com.ai.appframe2.web.action.BaseAction;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonCmdSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonStaticDataSV;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonCmdBean;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonCmdSetBean;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonCmdSetRelatBean;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonCmdSetRelatValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonCmdSetValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonCmdValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonStaticDataValue;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;


public class CmdSetAction extends BaseAction {
	
	private static transient Log log=LogFactory.getLog(RuleSetAction.class);
	
	/**
	 * 根据命令集名，读取命令集信息
	 * @param cmdSetName
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCmdSetValue[] getCmdSetByName(String cmdSetName) throws Exception{
		IAIMonCmdSV cmdSetSV=(IAIMonCmdSV)ServiceFactory.getService(IAIMonCmdSV.class);
		return cmdSetSV.getCmdSetByName(cmdSetName);
	}
	
	/**
	 * 保存命令集
	 * @param cmdSetInfo
	 * @throws Exception
	 */
	public void saveCmdSet(Object[] cmdSetInfo) throws Exception{
		if (cmdSetInfo==null || cmdSetInfo.length<=0)
			return;
		try{
			IBOAIMonCmdSetValue cmdSetValue=new BOAIMonCmdSetBean();
			
			if (StringUtils.isBlank(cmdSetInfo[0].toString())) {
				cmdSetValue.setStsToNew();
			} else {
				cmdSetValue.setCmdsetId(Long.parseLong(cmdSetInfo[0].toString().trim()));
				cmdSetValue.setStsToOld();
			}
			cmdSetValue.setCmdsetName(cmdSetInfo[1].toString().trim());
			cmdSetValue.setCmdsetDesc(cmdSetInfo[2].toString().trim());
			cmdSetValue.setRemark(cmdSetInfo[3].toString().trim());
			cmdSetValue.setState(cmdSetInfo[4].toString().trim());
			cmdSetValue.setCmdsetCode(cmdSetInfo[5].toString().trim());

			IAIMonCmdSV cmdSetSV=(IAIMonCmdSV)ServiceFactory.getService(IAIMonCmdSV.class);
			cmdSetSV.saveOrUpdate(cmdSetValue);
			
		}catch(Exception e){
			log.error("Call CmdSetAction's Method saveCmdSet has Exception :"+e.getMessage());
			throw e;
		}				
	}
	
	/**
	 * 删除命令集
	 * @param cmdSetId
	 * @throws Exception
	 */
	public void deleteCmdSet(String cmdSetId) throws Exception{
		if (StringUtils.isBlank(cmdSetId))
			return;
		try{
			IAIMonCmdSV cmdSetSV=(IAIMonCmdSV)ServiceFactory.getService(IAIMonCmdSV.class);
			cmdSetSV.deleteCmdSet(Long.parseLong(cmdSetId));
		}catch(Exception e){
			log.error("Call CmdSetAction's Method deleteCmdSet has Exception :"+e.getMessage());
			throw e;
		}	
	}

	/**
	 * 根据命令名，读取命令信息
	 * @param cmdName
	 * @return
	 * @throws Exception
	 */
	public DataContainerInterface[] getCmdByName(String cmdName) throws Exception{
		IAIMonCmdSV cmdSetSV=(IAIMonCmdSV)ServiceFactory.getService(IAIMonCmdSV.class);
		IBOAIMonCmdValue[] result = cmdSetSV.getCmdByName(cmdName);
		IAIMonStaticDataSV staticDataSV=(IAIMonStaticDataSV)ServiceFactory.getService(IAIMonStaticDataSV.class);
		
		List ret = new ArrayList();
		for(int i=0;i<result.length;i++){
			DataContainer dc = new DataContainer();
			dc.set(IBOAIMonCmdValue.S_CmdId, result[i].getCmdId());
			dc.set(IBOAIMonCmdValue.S_CmdName, result[i].getCmdName());
			dc.set(IBOAIMonCmdValue.S_CmdDesc, result[i].getCmdDesc());
			dc.set(IBOAIMonCmdValue.S_CmdExpr, result[i].getCmdExpr());
			dc.set(IBOAIMonCmdValue.S_CmdType, result[i].getCmdType());
			dc.set(IBOAIMonCmdValue.S_Remark, result[i].getRemark());
			dc.set(IBOAIMonCmdValue.S_State, result[i].getState());
			IBOAIMonStaticDataValue cmdType = staticDataSV.queryByCodeTypeAndValue( result[i].getCmdType(),"CMD_TYPE");
			dc.set("CMD_TYPE_NAME", cmdType.getCodeName());
			ret.add(dc);
		}
		return (DataContainerInterface[])ret.toArray(new DataContainer[0]);
		
	}
	
	/**
	 * 根据命令集ID，读取命令信息
	 * @param cmdSetId
	 * @return
	 * @throws Exception
	 */
	public DataContainer[] queryCmdBySetId(String cmdSetId) throws Exception{	
		List result=null;
		IAIMonCmdSV cmdSetSV=(IAIMonCmdSV)ServiceFactory.getService(IAIMonCmdSV.class);
		IBOAIMonCmdSetRelatValue[] relatValues=cmdSetSV.getCmdSetRelatByCmdSetIdOrderBySort(Long.parseLong(cmdSetId));
		if (relatValues!=null){
			result=new ArrayList(relatValues.length);
			for (int i=0;i<relatValues.length;i++){
				IBOAIMonCmdValue ruleValue=cmdSetSV.getCmdById(relatValues[i].getCmdId());
				if (ruleValue!=null){
					DataContainer dc = new DataContainer();
					dc.copy(ruleValue);
					//dc.set(relatValues[i].S_CmdSeq, relatValues[i].getCmdSeq());
					dc.set(IBOAIMonCmdSetRelatValue.S_CmdSeq, relatValues[i].getCmdSeq());
					result.add(dc);
				}
			}
		}
		if (result==null || result.size()<=0)
			return null;
		return (DataContainer[])result.toArray(new DataContainer[0]);
		
	}
	
	/**
	 * 保存命令
	 * @param cmdInfo
	 * @throws Exception
	 */
//	public void saveCmd (Object[] cmdInfo, ArrayCollection paramVals) throws Exception{
//		if (cmdInfo==null || cmdInfo.length<=0)
//			return;
//		try{
//			IBOAIMonCmdValue cmdSetValue=new BOAIMonCmdBean();
//			
//			if (StringUtils.isBlank(cmdInfo[0].toString())) {
//				cmdSetValue.setStsToNew();
//			} else {
//				cmdSetValue.setCmdId(Long.parseLong(cmdInfo[0].toString().trim()));
//				cmdSetValue.setStsToOld();
//			}
//			cmdSetValue.setCmdName(cmdInfo[1].toString().trim());
//			cmdSetValue.setCmdDesc(cmdInfo[2].toString().trim());
//			cmdSetValue.setCmdType(cmdInfo[3].toString().trim());
//			cmdSetValue.setCmdExpr(cmdInfo[4].toString().trim());
//			cmdSetValue.setRemark(cmdInfo[5].toString().trim());
//			cmdSetValue.setState(cmdInfo[6].toString().trim());
//
//			IAIMonCmdSV cmdSetSV=(IAIMonCmdSV)ServiceFactory.getService(IAIMonCmdSV.class);
//			
//			DataContainer[] paramDcs = null;
//			//处理参数
//			if(paramVals != null && paramVals.size() > 0) {
//				ASObject tmpObj = null;
//				paramDcs = new DataContainer[paramVals.size()];
//				for(int i = 0; i < paramVals.size(); i++){
//					tmpObj = (ASObject)paramVals.get(i);
//					paramDcs[i] = FlexHelper.transferAsToDc(tmpObj);
//				}
//			}
//			
////			cmdSetSV.saveOrUpdate(cmdSetValue);
//			
//			cmdSetSV.saveCmdAndParam(cmdSetValue, paramDcs);
//			
//		}catch(Exception e){
//			log.error("Call CmdSetAction's Method saveCmd has Exception :"+e.getMessage());
//			throw e;
//		}				
//	}
	
	/**
	 * 删除命令
	 * @param cmdId
	 * @throws Exception
	 */
	public void deleteCmd(String cmdId) throws Exception{
		if (StringUtils.isBlank(cmdId))
			return;
		try{
			IAIMonCmdSV cmdSetSV=(IAIMonCmdSV)ServiceFactory.getService(IAIMonCmdSV.class);
			cmdSetSV.deleteCmd(Long.parseLong(cmdId));
		}catch(Exception e){
			log.error("Call CmdSetAction's Method deleteCmd has Exception :"+e.getMessage());
			throw e;
		}	
	}
	
	/**
	 * 根据命令集ID，读取命令关系信息
	 * @param cmdSetId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCmdSetRelatValue[] queryRelateBySetId(String cmdSetId) throws Exception{
		IAIMonCmdSV cmdSetSV=(IAIMonCmdSV)ServiceFactory.getService(IAIMonCmdSV.class);
		return cmdSetSV.getCmdSetRelatByCmdSetIdOrderBySort(Long.parseLong(cmdSetId));
	}
	
	/**
	 * 保存命令集关联命令设置
	 * @param cmdSetId
	 * @param relatCmdIds
	 * @param relateCmdSeqs
	 * @throws Exception
	 */
	public void saveRelatData(String cmdSetId,Object[] relatCmdIds,Object[] relateCmdSeqs) throws Exception{
		if (StringUtils.isBlank(cmdSetId))
			return;
		if (relatCmdIds==null || relateCmdSeqs == null)
			return;
		if(relatCmdIds.length != relateCmdSeqs.length)
			// "传入的数据不正确"
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000008"));
		try{
			IBOAIMonCmdSetRelatValue[] cmdSetRelatValues=new IBOAIMonCmdSetRelatValue[relatCmdIds.length];
			
			for (int j=0;j<relatCmdIds.length;j++){
				cmdSetRelatValues[j]=new BOAIMonCmdSetRelatBean();
				cmdSetRelatValues[j].setCmdsetId(Long.parseLong(cmdSetId));
				cmdSetRelatValues[j].setCmdId(Long.parseLong(relatCmdIds[j].toString()));
				cmdSetRelatValues[j].setCmdSeq(Integer.parseInt(relateCmdSeqs[j].toString()));
				cmdSetRelatValues[j].setState("U");	
				cmdSetRelatValues[j].setStsToNew();
			}
			
			IBOAIMonCmdSetRelatValue[] oldRelatValues = queryRelateBySetId(cmdSetId);
			if (oldRelatValues!=null && oldRelatValues.length>0){
				for (int i=0;i<oldRelatValues.length;i++){
					oldRelatValues[i].delete();
				}
			}
			
			IAIMonCmdSV cmdSetSV=(IAIMonCmdSV)ServiceFactory.getService(IAIMonCmdSV.class);
			cmdSetSV.saveOrUpdate(oldRelatValues, cmdSetRelatValues);			
		}catch(Exception e){
			log.error("Call CmdSetAction's Method saveRelatData has Exception :"+e.getMessage());
			throw e;
		}				
	}
	
	/**
	 * 返回所有符合指定命令集类型的命令信息,并且将该隶属该命令集的命令做标记
	 * @param cmdSetId
	 * @return
	 * @throws Exception
	 */
	public DataStructInterface[] getAllCmdByCmdSetId(String cmdSetId) throws Exception{
		if (StringUtils.isBlank(cmdSetId)){
			return null;			
		}
		IAIMonCmdSV cmdSetSV=(IAIMonCmdSV)ServiceFactory.getService(IAIMonCmdSV.class);
		IBOAIMonCmdValue[] cmdValues = cmdSetSV.getAllCmd();
		DataStructInterface[] dc = new DataStructInterface[cmdValues.length];
		if (cmdValues!=null && cmdValues.length>0){		
			IBOAIMonCmdSetRelatValue[] relatValues=cmdSetSV.getCmdSetRelatByCmdSetIdOrderBySort(Long.parseLong(cmdSetId));
			for (int rec=0;rec<cmdValues.length;rec++){
				dc[rec]=new DataContainer();
				dc[rec].copy(cmdValues[rec]);
				dc[rec].setExtAttr("CHK","false");
				if (relatValues!=null && relatValues.length>0){
					for (int i=0;i<relatValues.length;i++){
						if (cmdValues[rec].getCmdId()==relatValues[i].getCmdId()){
							dc[rec].setExtAttr("CHK","true");
							dc[rec].set(IBOAIMonCmdSetRelatValue.S_CmdSeq, ""+relatValues[i].getCmdSeq());
							break;
						}
					}
				}
			}
		}
		return dc;
	}
	
	/**
	 * 获取CMD类型列表
	 * @param cmdtype
	 * @return
	 */
	public List getCmdTypeList(String cmdtype){
		List ret = new ArrayList();
		try {
			IAIMonStaticDataSV staticDataSV=(IAIMonStaticDataSV)ServiceFactory.getService(IAIMonStaticDataSV.class);
			IBOAIMonStaticDataValue[] tmpRet = staticDataSV.queryByCodeType(cmdtype);
			for(int i=0;i<tmpRet.length;i++){
				HashMap map = new HashMap();
				map.put(IBOAIMonStaticDataValue.S_CodeValue, tmpRet[i].getCodeValue());
				map.put(IBOAIMonStaticDataValue.S_CodeName, tmpRet[i].getCodeName());
				ret.add(map);
			}
		} catch (Exception e) {
			log.error("Call CmdSetAction's Method getCmdTypeList has Exception :" + e.getMessage());
		}
		return ret;
	}
	
	/**
	 * 根据命令类型读取所有符合条件的命令
	 * @param cmdType
	 * @return
	 */
	public IBOAIMonCmdValue[] getCmdByType(String cmdType){
		IBOAIMonCmdValue[] ret = null;
		try {
			IAIMonCmdSV cmd = (IAIMonCmdSV)ServiceFactory.getService(IAIMonCmdSV.class); 			
			ret = cmd.queryCmdByType(cmdType);
			
		} catch (Exception e) {
			log.error("Call CmdSetAction's Method getCmdByType has Exception :" + e.getMessage());
		}
		return ret;
	}

	public void saveParamDefineAndValByCmd(DataContainer dc) throws Exception{
		if (dc == null)
			// "传入数据集为空，无法保存！"
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000009"));

		IAIMonCmdSV cmdSV = (IAIMonCmdSV)ServiceFactory.getService(IAIMonCmdSV.class);
		// 保存
		cmdSV.saveParamDefineAndVal(dc);
	}

	public void deleteParamDefineAndVal(String vId,String paramId) throws Exception{
		if(StringUtils.isBlank(vId)|| StringUtils.isBlank(paramId))
			// "没有传入参数"
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000005"));
		IAIMonCmdSV cmdSV = (IAIMonCmdSV)ServiceFactory.getService(IAIMonCmdSV.class);
		// 保存
		cmdSV.deleteParamDefineAndVal(vId,paramId);
	}
	
	public IBOAIMonCmdSetValue[] getCmdSetByCode(String cmdSetCode) throws Exception {
		IAIMonCmdSV cmdSetSV=(IAIMonCmdSV)ServiceFactory.getService(IAIMonCmdSV.class);
		return cmdSetSV.getCmdSetsByCode(cmdSetCode);
	}
	
}