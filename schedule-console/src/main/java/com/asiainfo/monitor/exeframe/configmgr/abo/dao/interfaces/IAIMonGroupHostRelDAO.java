package com.asiainfo.monitor.exeframe.configmgr.abo.dao.interfaces;

import java.util.List;
import java.util.Map;

import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonGroupHostRelBean;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonGroupHostRelValue;

public interface IAIMonGroupHostRelDAO
{

    public long getBeanId() throws Exception;

    public BOAIMonGroupHostRelBean getBeanById(long id) throws Exception;

    public void save(BOAIMonGroupHostRelBean value) throws Exception;

    /** 
     * 根据分组查找�?��主机
     * @param groupId
     * @return
     * @throws Exception
     */
    public BOAIMonGroupHostRelBean[] getBeansByGroupId(long groupId) throws Exception;

    public BOAIMonGroupHostRelBean[] qryGroupHost(String groupId, String hostId) throws Exception;

    public void delete(IBOAIMonGroupHostRelValue[] values) throws Exception;

    public List<String> getHostListByGroupId(String groupId) throws Exception;

	/**
	 * 返回所有主机ID和对应的组名
	 * @return
	 */
	public Map<Long, String> getHostGroups() throws Exception;

}
