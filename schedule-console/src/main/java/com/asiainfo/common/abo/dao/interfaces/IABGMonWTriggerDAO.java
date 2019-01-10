package com.asiainfo.common.abo.dao.interfaces;

import java.util.Map;

import com.asiainfo.common.abo.bo.BOABGMonWTriggerBean;
import com.asiainfo.common.abo.ivalues.IBOABGMonWTriggerValue;

public interface IABGMonWTriggerDAO
{

    public long getBeanId() throws Exception;

    public BOABGMonWTriggerBean getBeanById(long id) throws Exception;

    public void save(BOABGMonWTriggerBean value) throws Exception;

    /** 
    * 根据条件查询告警记录信息
    * @param condition
    * @param parameter
    * @return
    * @throws Exception
    */
    public IBOABGMonWTriggerValue[] query(String condition, Map parameter) throws Exception;

    /** 
     * 根据告警标识查找告警记录
     * @param trigger_Id
     * @return
     * @throws Exception
     */
    public IBOABGMonWTriggerValue getWTriggerBean(String trigger_Id) throws Exception;

    /** 
     * 保存告警并处理之前的告警，将X分钟之前的同任务告警转成历史
     * @param infoId
     * @param second
     * @param level
     * @return
     * @throws Exception
     */
    public IBOABGMonWTriggerValue[] getMonWTriggerBefore(long infoId, int second, String level) throws Exception;

    /** 
     * 根据条件查询告警记录
     * @param condition
     * @param startIndex
     * @param endIndex
     * @return
     * @throws Exception
     */
    public IBOABGMonWTriggerValue[] getMonWTriggerByCondition(String condition, Map parameter, int startIndex, int endIndex) throws Exception;

    /** 
     * 批量保存或修改警告记�?
     * @param values
     * @throws Exception
     */
    public void saveOrUpdate(IBOABGMonWTriggerValue[] values) throws Exception;

    /** 
     * 保存或修改警告记�?
     * @param value
     * @throws Exception
     */
    public void saveOrUpdate(IBOABGMonWTriggerValue value) throws Exception;

    /** 
     * 删除警告记录
     * @param value
     * @throws Exception
     */
    public void deleteWTrigger(IBOABGMonWTriggerValue value) throws Exception;

    /** 
     * 批量删除警告记录
     * @param value
     * @throws Exception
     */
    public void deleteWTrigger(IBOABGMonWTriggerValue[] values) throws Exception;

    /** 
     * 根据查询条件查询记录数量
     * @param condition
     * @param params
     * @return
     * @throws Exception
     */
    public int count(String condition, Map params) throws Exception;

    /**
     * 通过ip，信息名称查询告警信息
     * @param hostName 
     * @param endDate 
     * @param beginDate 
     * 
     * @param request
     * @throws Exception
     */
    public IBOABGMonWTriggerValue[] getTriggerValuesByIpInfoName(String ip, String infoName, String hostName, String beginDate, String endDate, int start, int end) throws Exception;

    /**
     * 查询结果行数
     * @param hostName 
     * @param endDate 
     * @param beginDate 
     * 
     * @param request
     * @throws Exception
     */
    public int getCntByCond(String ip, String infoName, String hostName, String beginDate, String endDate) throws Exception;

}
