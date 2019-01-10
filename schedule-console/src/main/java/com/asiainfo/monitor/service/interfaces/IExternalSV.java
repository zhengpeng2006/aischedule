package com.asiainfo.monitor.service.interfaces;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonNodeValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonPhysicHostValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonServerValue;

/**
 * 
 * 外部服务接口，暂给调度平台提供通用外部接口
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: AI(NanJing)</p>
 * @author wu songkai
 * @version 3.0
 */
public interface IExternalSV
{

    /**
     * 通过应用编码查询节点信息
     * 
     * @param request
     * @throws Exception
     * @author szh
     */
    public IBOAIMonNodeValue qryNodeInfoByServerCode(String serverCode) throws Exception;

    /**
     * 通过应用编码查询应用信息
     * 
     * @param request
     * @throws Exception
     * @author szh
     */
    public IBOAIMonServerValue qryServerByServerCode(String serverCode) throws Exception;
    
    /**
     * 通过应用编码查询应用相关信息
     * 
     * @param request
     * @throws Exception
     * @author szh
     */
    public Map<String, String> qryAppInfoByCondition(String serverCode) throws Exception;

    /**
     * 根据应用编码和连接类型，查询应用信息
     * @param servCode
     * @param transType
     * @return
     * @throws Exception
     */
    public Map<String, String> qryAppInfoByCondition(String servCode, String connType) throws Exception;

    /**
     * 根据节点标识查询该节点下的所有应用信息
     * 
     * @param request
     * @throws Exception
     * @author szh
     */
    public List<IBOAIMonServerValue> qryServerInfo(String nodeId) throws Exception;
    
    /**
     * 根据节点标识查询节点相关信息
     * 
     * @param request
     * @throws Exception
     * @author szh
     */
    public Map<String, String> qryNodeRelInfoByCondition(String nodeId, String connType) throws Exception;
    
    /**
     * 通过分组名称、主机名、主机Ip、节点名称、应用编码查询应用信息
     * 
     * @param request
     * @throws Exception
     */

    public JSONArray qryServerInfoByCondition(Map<String, String> map) throws Exception;
    /**
     * 获取指定主机下，指定进程的状态
     * @param paramMap
     * @return
     * @throws Exception
     */
    public Map<String, String> acqProcState(Map<String, List<String>> paramMap, int timeout) throws Exception;
    
    /**
     * 通过应用编码查询主机信息
     * 
     * @param request
     * @throws Exception
     */
    public JSONArray qryServersInfoByServerCodes(Set<String> serverCodes) throws Exception;

    /**
     * 根据分组标识查询主机信息
     * 
     * @param request
     * @throws Exception
     */
    public IBOAIMonPhysicHostValue[] qryHostInfoByGroupId(String groupId) throws Exception;

	/**     
	 * 返回所有主机ID和对应的组名
	 * @return
	 */
	public Map<Long, String> getHostGroups() throws Exception;

	/**
	 * 返回所有应用和对应的主机ID
	 * @return
	 * @throws Exception
	 */
	public Map<String, Long> getServerHosts()  throws Exception;

	/**
	 * 返回所有主机和对应的应用列表
	 * @return
	 */
	public JSONObject getServerHostsMap() throws Exception;

}
