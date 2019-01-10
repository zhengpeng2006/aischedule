package com.asiainfo.monitor.busi.service.interfaces;

import java.rmi.RemoteException;

import com.ai.appframe2.bo.DataContainer;
import com.asiainfo.monitor.busi.cache.impl.Cmd;
import com.asiainfo.monitor.busi.cache.interfaces.ICheckCache;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonCmdSetRelatValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonCmdSetValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonCmdValue;


/**
 * AppMonitor 远程调用脚本信息的服务接口
 * @author lisong
 *
 */
public interface IAIMonCmdSV {
	
	
	/**
	 * 通过脚本集ID查询脚本关系信息
	 * @param setId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCmdSetRelatValue[] getCmdSetRelatByCmdSetIdOrderBySort(long setId) throws RemoteException,Exception;
	
	/**
	 * 通过脚本ID查询脚本信息
	 * @param cmdId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCmdValue getCmdById(long cmdId) throws RemoteException,Exception;
	
	/**
	 * 通过脚本名查询脚本信息集
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCmdValue[] getCmdByName(String name) throws RemoteException,Exception;
	
	/**
	 * 查询所有命令
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCmdValue[] getAllCmd() throws RemoteException,Exception;
	/**
	 * 通过类型询脚本信息集
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCmdValue[] queryCmdByType(String type) throws RemoteException,Exception;
	
	
	/**
	 * 通过脚本集ID查询脚本集信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCmdSetValue getCmdSetById(long id) throws RemoteException,Exception;
	
	/**
	 * 通过脚本集名称查询脚本集信息
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCmdSetValue[] getCmdSetByName(String name) throws RemoteException,Exception;
	
	
	/**
	 * 通过脚本ID和脚本集ID查询之间的关系信息
	 * @param setId
	 * @param cmdId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCmdSetRelatValue getCmdSetRelatByIdAndCmdId(long setId, long cmdId) throws RemoteException,Exception;
	
	/**
	 * 通过脚本ID查询脚本关系信息
	 * @param cmdId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCmdSetRelatValue[] getCmdSetRelatByCmdId(long cmdId) throws RemoteException,Exception;
	
	
	
	
	/**
	 * 根据标识码读取命令集
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCmdSetValue getCmdSetByCode(String code) throws RemoteException,Exception;
	
	/**
	 * 根据标识码读取命令集
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCmdSetValue[] getCmdSetsByCode(String code) throws RemoteException,Exception;
	
	/**
	 * 添加、修改和删除
	 * @param cmd
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonCmdValue cmd) throws RemoteException,Exception;
	
	/**
	 * 添加、修改和删除
	 * @param cmdSet
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonCmdSetValue cmdSet) throws RemoteException,Exception;
	
	/**
	 * 添加、修改和删除
	 * @param relat
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonCmdSetRelatValue relat) throws RemoteException,Exception;
	
	/**
	 * 添加、修改和删除
	 * @param cmds
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonCmdValue[] cmds) throws RemoteException,Exception;
	
	/**
	 * 添加、修改和删除
	 * @param sets
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonCmdSetValue[] sets) throws RemoteException,Exception;
	
	/**
	 * 添加、修改和删除
	 * @param relats
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonCmdSetRelatValue[] relats) throws RemoteException,Exception;
	
	/**
	 * 添加、修改和删除
	 * 
	 * @param oldValues
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonCmdSetRelatValue[] oldValues, IBOAIMonCmdSetRelatValue[] values) throws RemoteException,Exception;
	
	/**
	 * 删除命令集
	 * @param cmdSetId
	 * @throws Exception
	 */
	public void deleteCmdSet(long cmdSetId) throws RemoteException,Exception;
	
	/**
	 * 删除命令
	 * @param cmdId
	 * @throws Exception
	 */
	public void deleteCmd(long cmdId) throws RemoteException,Exception;

	/**
	 * 保存参数定义与参数值
	 * @param dc
	 * @throws RemoteException
	 * @throws Exception
	 */
	public void saveParamDefineAndVal(DataContainer dc)throws RemoteException,Exception;

	/**
	 * 删除删除定义与值
	 * @param vId
	 * @param paramId
	 * @throws RemoteException
	 * @throws Exception
	 */
	public void deleteParamDefineAndVal(String vId, String paramId) throws RemoteException,Exception;
	
	/**
	 * 保存命令以及命令关联的参数
	 * 
	 * @param cmd
	 * @param param
	 * @throws RemoteException
	 * @throws Exception
	 */
	public void saveCmdAndParam(IBOAIMonCmdValue cmd, DataContainer[] paramDcs) throws RemoteException,Exception;

    /**
     * 将值对象简单封装
     * @param value
     * @return
     * @throws Exception
     */
    public Cmd wrapperCmdConfigByBean(IBOAIMonCmdValue iboaiMonCmdValue) throws RemoteException, Exception;

    public ICheckCache getCmdByIdFromDb(String key) throws RemoteException, Exception;
}
