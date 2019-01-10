package com.asiainfo.monitor.busi.service.interfaces;

import java.util.List;
import java.util.Map;

import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonGroupHostRelBean;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonGroupHostRelValue;

public interface IAIMonGroupHostRelSV
{

    /**
     * 保存组主机关系
     * 
     * @Function: IAIMonGroupHostRelSV.java
     * @Description: 该函数的功能描述
     * 
     * @param:参数描述
     * @return：返回结果描述
     * @throws：异常描述
     */
    public void save(BOAIMonGroupHostRelBean bean) throws Exception;

    public BOAIMonGroupHostRelBean[] qryGroupHost(String groupId, String hostId) throws Exception;

    public void delete(IBOAIMonGroupHostRelValue[] values) throws Exception;

    public List<String> getHostListByGroupId(String groupId) throws Exception;

	/**
	 * 返回所有主机ID和对应的组名
	 * @return
	 */
	public Map<Long, String> getHostGroups() throws Exception;

}
