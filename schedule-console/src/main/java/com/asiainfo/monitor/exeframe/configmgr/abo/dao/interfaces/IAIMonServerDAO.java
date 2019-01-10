package com.asiainfo.monitor.exeframe.configmgr.abo.dao.interfaces;

import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonServerBean;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonServerValue;

public interface IAIMonServerDAO
{

    public long getBeanId() throws Exception;

    public BOAIMonServerBean getBeanById(long id) throws Exception;

    public void save(BOAIMonServerBean value) throws Exception;

    /** 
    * 根据条件查询Server
    * @param cond
    * @param para
    * @return
    * @throws Exception
    */
    public IBOAIMonServerValue[] queryServer(String cond, Map para) throws Exception;

    /** 
     * 根据服务应用标识，读取应用服务信�?
     * @param serId
     * @return
     * @throws Exception
     */
    public IBOAIMonServerValue getServerByServerId(String serverId) throws Exception;

    /** 
     * 根据应用标识码查询应用信�?
     * @param serverCode
     * @return
     * @throws Exception
     */
    public IBOAIMonServerValue getServerByServerCode(String serverCode) throws Exception;

    /** 
     * 根据应用类型查询应用信息
     * @param type
     * @return
     * @throws Exception
     */
    public IBOAIMonServerValue[] getServerByTempType(String type) throws Exception;

    /** 
     * 根据应用的ip和port查找出对应的应用
     * @param ip
     * @param port
     * @return
     * @throws Exception
     */
    public IBOAIMonServerValue getServerByIpPort(String ip, String port) throws Exception;

    /** 
     * 读取�?��正常的Server
     * @return
     * @throws Exception
     */
    public IBOAIMonServerValue[] getAllServer() throws Exception;

    /** 
     * 根据主机标识和应用名，读取应用服务信�?
     * @param hostId
     * @param serverName
     * @return
     * @throws Exception
     */
    public IBOAIMonServerValue[] getServerByCond(String hostId, String serverName) throws Exception;

    /** 
     * 根据组标识，读取该组包含的具体主机信�?
     * @param groupId
     * @return
     * @throws Exception
     */
    // public IBOAIMonServerValue[] getServerByGroupId(String groupId) throws Exception;

    /** 
     * 批量保存或修改应用设�?
     * @param values
     * @throws Exception
     */
    public void saveOrUpdate(IBOAIMonServerValue[] values) throws Exception;

    /** 
     * 保存或修改应用设�?
     * @param value
     * @throws Exception
     */
    public long saveOrUpdate(IBOAIMonServerValue value) throws Exception;

    /** 
     * 删除应用信息
     * @param appServerId
     * @throws Exception
     */
    public void deleteServer(long serverId) throws Exception;
    
    /**
     * 根据条件查询应用信息
     * @param map
     * @return
     * @throws Exception
     */
    public JSONArray qryServerByConditions(Map<String, String> map) throws Exception;

    public IBOAIMonServerValue[] qryServerInfo(String nodeId) throws Exception;

    public IBOAIMonServerValue qryServerByServerId(String serverId) throws Exception;

    public boolean isExistServerByCode(String serverCode) throws Exception;

    public IBOAIMonServerValue qryServerByServerCode(String serverCode) throws Exception;

    public String qryHostIdByServerCode(String serverCode) throws Exception;

	public JSONArray qryServersInfoByServerCodes(Set<String> serverCodes) throws Exception;

	/**
	 * 返回所有应用和对应的主机ID
	 * @return
	 * @throws Exception
	 */
	public Map<String, Long> getServerHosts() throws Exception;

	/**
	 * @return
	 * @throws Exception
	 */
	public JSONObject getServerHostsMap() throws Exception;

}
