package com.asiainfo.monitor.busi.service.impl;


import java.rmi.RemoteException;

import org.apache.commons.lang.StringUtils;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.bo.DataContainerFactory;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.cache.AIMonCmdCheckListener;
import com.asiainfo.monitor.busi.cache.impl.Cmd;
import com.asiainfo.monitor.busi.cache.interfaces.ICheckCache;
import com.asiainfo.monitor.busi.dao.interfaces.IAIMonCmdDAO;
import com.asiainfo.monitor.busi.dao.interfaces.IAIMonCmdSetDAO;
import com.asiainfo.monitor.busi.dao.interfaces.IAIMonCmdSetRelatDAO;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonCmdSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonParamDefineSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonParamValuesSV;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonParamDefineBean;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonParamValuesBean;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonCmdSetRelatValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonCmdSetValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonCmdValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonParamDefineValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonParamValuesValue;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class AIMonCmdSVImpl implements IAIMonCmdSV {
	
	/**
	 * 通过脚本集ID查询脚本关系信息
	 * @param setId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCmdSetRelatValue[] getCmdSetRelatByCmdSetIdOrderBySort(long setId) throws RemoteException,Exception {
		IAIMonCmdSetRelatDAO dao = (IAIMonCmdSetRelatDAO) ServiceFactory.getService(IAIMonCmdSetRelatDAO.class);
		return dao.getCmdSetRelatByCmdSetIdOrderBySort(setId);
	}

	/**
	 * 通过脚本ID查询脚本信息
	 * @param cmdId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCmdValue getCmdById(long cmdId) throws RemoteException,Exception {
		IAIMonCmdDAO dao = (IAIMonCmdDAO) ServiceFactory.getService(IAIMonCmdDAO.class);
		return dao.queryById(cmdId);
	}

	/**
	 * 通过命令名查询脚本信息集
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCmdValue[] getCmdByName(String name) throws RemoteException,Exception {
		IAIMonCmdDAO dao = (IAIMonCmdDAO) ServiceFactory.getService(IAIMonCmdDAO.class);
		return dao.queryByName(name);
	}
	
	/**
	 * 查询所有命令
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCmdValue[] getAllCmd() throws RemoteException,Exception{		
		StringBuilder sb=new StringBuilder("");
		sb.append(IBOAIMonCmdValue.S_State+" ='U'");
		IAIMonCmdDAO dao = (IAIMonCmdDAO) ServiceFactory.getService(IAIMonCmdDAO.class);
		return dao.query(sb.toString(),null);
	}
	
	/**
	 * 通过类型查询脚本信息集
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCmdValue[] queryCmdByType(String type) throws RemoteException,Exception{
		IAIMonCmdDAO dao = (IAIMonCmdDAO) ServiceFactory.getService(IAIMonCmdDAO.class);
		return dao.queryCmdByType(type);
	}

	/**
	 * 通过脚本ID和脚本集ID查询之间的关系信息
	 * @param setId
	 * @param cmdId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCmdSetRelatValue getCmdSetRelatByIdAndCmdId(long setId,long cmdId) throws RemoteException,Exception {
		IAIMonCmdSetRelatDAO dao = (IAIMonCmdSetRelatDAO) ServiceFactory.getService(IAIMonCmdSetRelatDAO.class);
		return dao.getCmdSetRelatByIdAndCmdId(setId,cmdId);
	}


	/**
	 * 通过脚本集ID查询脚本集信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCmdSetValue getCmdSetById(long id) throws RemoteException,Exception {
		IAIMonCmdSetDAO dao = (IAIMonCmdSetDAO) ServiceFactory.getService(IAIMonCmdSetDAO.class);
		return dao.getCmdSetById(id);
	}
	
	/**
	 * 根据标识码读取命令集
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCmdSetValue getCmdSetByCode(String code) throws RemoteException,Exception{
		IAIMonCmdSetDAO dao = (IAIMonCmdSetDAO) ServiceFactory.getService(IAIMonCmdSetDAO.class);
		return dao.getCmdSetByCode(code);
	}
	
	/**
	 * 根据标识码读取命令集
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCmdSetValue[] getCmdSetsByCode(String code) throws RemoteException,Exception {
		IAIMonCmdSetDAO dao = (IAIMonCmdSetDAO) ServiceFactory.getService(IAIMonCmdSetDAO.class);
		return dao.getCmdSetsByCode(code);
	}

	/**
	 * 通过脚本集名称查询脚本集信息
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCmdSetValue[] getCmdSetByName(String name) throws RemoteException,Exception {
		IAIMonCmdSetDAO dao = (IAIMonCmdSetDAO) ServiceFactory.getService(IAIMonCmdSetDAO.class);
		return dao.getCmdSetByName(name);
	}

	/**
	 * 通过命令ID查询脚本关系信息
	 * @param cmdId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCmdSetRelatValue[] getCmdSetRelatByCmdId(long cmdId) throws RemoteException,Exception {
		IAIMonCmdSetRelatDAO dao = (IAIMonCmdSetRelatDAO) ServiceFactory.getService(IAIMonCmdSetRelatDAO.class);
		return dao.getCmdSetRelatByCmdId(cmdId);
	}


	public void saveOrUpdate(IBOAIMonCmdValue cmd) throws RemoteException,Exception {
		IAIMonCmdDAO dao = (IAIMonCmdDAO) ServiceFactory.getService(IAIMonCmdDAO.class);
		dao.saveOrUpdate(cmd);
	}

	public void saveOrUpdate(IBOAIMonCmdSetValue cmdSet) throws RemoteException,Exception {
		IAIMonCmdSetDAO dao = (IAIMonCmdSetDAO) ServiceFactory.getService(IAIMonCmdSetDAO.class);
		dao.saveOrUpdate(cmdSet);
	}

	public void saveOrUpdate(IBOAIMonCmdSetRelatValue relat) throws RemoteException,Exception {
		IAIMonCmdSetRelatDAO dao = (IAIMonCmdSetRelatDAO) ServiceFactory
				.getService(IAIMonCmdSetRelatDAO.class);
		dao.saveOrUpdate(relat);
	}

	public void saveOrUpdate(IBOAIMonCmdValue[] cmds) throws RemoteException,Exception {
		IAIMonCmdDAO dao = (IAIMonCmdDAO) ServiceFactory.getService(IAIMonCmdDAO.class);
		dao.saveOrUpdate(cmds);
	}

	public void saveOrUpdate(IBOAIMonCmdSetValue[] sets) throws RemoteException,Exception {
		IAIMonCmdSetDAO dao = (IAIMonCmdSetDAO) ServiceFactory.getService(IAIMonCmdSetDAO.class);
		dao.saveOrUpdate(sets);
	}

	public void saveOrUpdate(IBOAIMonCmdSetRelatValue[] relats)
			throws RemoteException,Exception {
		IAIMonCmdSetRelatDAO dao = (IAIMonCmdSetRelatDAO) ServiceFactory
				.getService(IAIMonCmdSetRelatDAO.class);
		dao.saveOrUpdate(relats);
	}
	
	public void saveOrUpdate(IBOAIMonCmdSetRelatValue[] oldValues, IBOAIMonCmdSetRelatValue[] values) throws RemoteException,Exception {
		IAIMonCmdSetRelatDAO dao = (IAIMonCmdSetRelatDAO) ServiceFactory.getService(IAIMonCmdSetRelatDAO.class);
		if (oldValues != null && oldValues.length > 0) {
			dao.saveOrUpdate(oldValues);
		}
		if (values != null && values.length > 0) {
			dao.saveOrUpdate(values);
		}
	}

	/**
	 * 删除命令集
	 * @param cmdSetId
	 * @throws Exception
	 */
	public void deleteCmdSet (long cmdSetId) throws RemoteException,Exception {
		IAIMonCmdSetDAO dao = (IAIMonCmdSetDAO) ServiceFactory.getService(IAIMonCmdSetDAO.class);
		dao.deleteCmdSet(cmdSetId);
	}
	
	/**
	 * 删除命令
	 * @param cmdId
	 * @throws Exception
	 */
	public void deleteCmd (long cmdId) throws RemoteException,Exception {
		// 校验是否被命令集关联
		IBOAIMonCmdSetRelatValue[] relats = this.getCmdSetRelatByCmdId(cmdId);
		if(relats != null && relats.length > 0)
			// "当前命令已经被命令集关联，无法删除!"
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000096"));
		
		//删除命令关联参数值
		IAIMonParamValuesSV valSv = (IAIMonParamValuesSV) ServiceFactory.getService(IAIMonParamValuesSV.class);
		IBOAIMonParamValuesValue[] valValues = valSv.getParamValuesByType("30", String.valueOf(cmdId));
		if(valValues != null && valValues.length >0)
		{
			for(int i=0;i<valValues.length;i++)
				valValues[i].delete();
			valSv.saveOrUpdate(valValues);
		}
		
		//删除命令关联参数定义
		IAIMonParamDefineSV defSv = (IAIMonParamDefineSV) ServiceFactory.getService(IAIMonParamDefineSV.class);
		IBOAIMonParamDefineValue[] defValues = defSv.getParamDefineByType("3", String.valueOf(cmdId));
		if(defValues != null && defValues.length >0)
		{
			for(int i=0;i<defValues.length;i++)
				defValues[i].delete();
			defSv.saveOrUpdate(defValues);
		}
		
		IAIMonCmdDAO dao = (IAIMonCmdDAO) ServiceFactory.getService(IAIMonCmdDAO.class);
		dao.deleteCmd(cmdId);
	}

	public void saveParamDefineAndVal(DataContainer dc) throws RemoteException, Exception {
		if(dc == null)
			return;
		IBOAIMonParamDefineValue defineValue = new BOAIMonParamDefineBean();
		if (StringUtils.isBlank(dc.getAsString(IBOAIMonParamDefineValue.S_ParamId))) {
			dc.setStsToNew();
		} else {
			Object value = dc.get(IBOAIMonParamDefineValue.S_ParamId);
			dc.clearProperty(IBOAIMonParamDefineValue.S_ParamId);
			dc.initPropertyOld(IBOAIMonParamDefineValue.S_ParamId, value);

		}
		DataContainerFactory.copy(dc, defineValue);
		defineValue.setRegisteType(3);
		IAIMonParamDefineSV defineSV = (IAIMonParamDefineSV)ServiceFactory.getService(IAIMonParamDefineSV.class);
		// 保存
		defineSV.saveOrUpdate(defineValue);
		
		IBOAIMonParamValuesValue valValue = new BOAIMonParamValuesBean();
		if (StringUtils.isBlank(dc.getAsString(IBOAIMonParamValuesValue.S_VId))) {
			dc.setStsToNew();
		} else {
			Object value = dc.get(IBOAIMonParamValuesValue.S_VId);
			dc.clearProperty(IBOAIMonParamValuesValue.S_VId);
			dc.initPropertyOld(IBOAIMonParamValuesValue.S_VId, value);

		}
		DataContainerFactory.copy(dc, valValue);
		valValue.setRegisteType(30);
		valValue.setParamId(defineValue.getParamId());
		IAIMonParamValuesSV valueSV = (IAIMonParamValuesSV)ServiceFactory.getService(IAIMonParamValuesSV.class);
		// 保存
		valueSV.saveOrUpdate(valValue);
		
		
	}

	public void deleteParamDefineAndVal(String vId, String paramId) throws RemoteException, Exception {
		
		IAIMonParamValuesSV valSV = (IAIMonParamValuesSV)ServiceFactory.getService(IAIMonParamValuesSV.class);
		valSV.deleteParamValues(Long.parseLong(vId));
		
		IAIMonParamDefineSV defSV = (IAIMonParamDefineSV)ServiceFactory.getService(IAIMonParamDefineSV.class);
		defSV.deleteParamDefine(Long.parseLong(paramId));
		
		
	}
	
	/**
	 * 保存命令以及命令关联的参数
	 * 
	 * @param cmd
	 * @param param
	 * @throws RemoteException
	 * @throws Exception
	 */
	public void saveCmdAndParam(IBOAIMonCmdValue cmd, DataContainer[] paramDcs) throws RemoteException,Exception {
		// 保存命令
		IAIMonCmdDAO dao = (IAIMonCmdDAO) ServiceFactory.getService(IAIMonCmdDAO.class);
		long cmdId = 0;
		if (cmd.isNew()) {
			cmdId = dao.getCmdId();
			cmd.setCmdId(cmdId);
		} else {
			cmdId = cmd.getCmdId();
		}
		dao.saveOrUpdate(cmd);
		
		//删除参数
//		IAIMonParamValuesSV valSV = (IAIMonParamValuesSV)ServiceFactory.getService(IAIMonParamValuesSV.class);
//		valSV.deleteParamValues(30, cmdId);
		
		// 保存参数
		if(paramDcs == null || paramDcs.length == 0)
			return;

		for (int i = 0; i < paramDcs.length; i++) {
			paramDcs[i].set("REGISTE_ID", cmdId);
			paramDcs[i].set("STATE", "U");
			saveParamDefineAndVal(paramDcs[i]);
		}
	}

    /**
     * 将值对象简单封装
     * @param value
     * @return
     * @throws Exception
     */
    @Override
    public Cmd wrapperCmdConfigByBean(IBOAIMonCmdValue value) throws RemoteException, Exception
    {
        if(value == null || StringUtils.isBlank(value.getState()) || value.getState().equals("E"))
            return null;
        Cmd result = new Cmd();
        result.setCmd_Id(value.getCmdId() + "");
        result.setCmd_Name(value.getCmdName());
        result.setCmd_Desc(value.getCmdDesc());
        result.setCmd_Expr(value.getCmdExpr());
        result.setCmd_Type(value.getCmdType());
        result.setState(value.getState());
        result.setParam_Type(value.getParamType());
        result.setRemark(value.getRemark());

        result.setCacheListener(new AIMonCmdCheckListener());
        return result;
    }

    /**
     * 根据命令标识从数据库读取命令信息并封装
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public ICheckCache getCmdByIdFromDb(String id) throws RemoteException, Exception
    {
        if(StringUtils.isBlank(id))
            return null;
        IAIMonCmdDAO cmdDAO = (IAIMonCmdDAO) ServiceFactory.getService(IAIMonCmdDAO.class);
        IBOAIMonCmdValue value = cmdDAO.queryById(Long.parseLong(id));
        Cmd result = this.wrapperCmdConfigByBean(value);
        return result;
    }
	
}
