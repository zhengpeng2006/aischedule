package com.asiainfo.monitor.exeframe.configmgr.service.interfaces;

import java.util.List;

import com.ailk.common.data.IDataBus;
import com.asiainfo.monitor.busi.cache.impl.ConMode;
import com.asiainfo.monitor.busi.cache.interfaces.ICheckCache;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonConModeValue;

public interface IAIMonConModeSV
{
    /**
     * 根据连接方式标识查询
     * @param conIds
     * @return
     * @throws Exception
     */
    public IBOAIMonConModeValue[] qryByConIdList(List<String> conIdList) throws Exception;

    public IBOAIMonConModeValue qryConModeInfoByConId(String conId) throws Exception;

    public IDataBus getSelectList() throws Exception;

    public long saveOrUpdate(IBOAIMonConModeValue value) throws Exception;

    /**
     * 获取所有的连接方式
     * 
     * @param request
     * @throws Exception
     */
    public IBOAIMonConModeValue[] getAllConMode() throws Exception;

    public ConMode wrapperConModeConfigByBean(IBOAIMonConModeValue iboaiMonConModeValue) throws Exception;

    public ICheckCache getConModeByIdFromDb(String key) throws Exception;

}
