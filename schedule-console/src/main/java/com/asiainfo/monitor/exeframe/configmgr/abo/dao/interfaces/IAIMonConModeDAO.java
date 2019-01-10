package com.asiainfo.monitor.exeframe.configmgr.abo.dao.interfaces;

import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonConModeBean;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonConModeValue;

public interface IAIMonConModeDAO
{

    public long getBeanId() throws Exception;

    public BOAIMonConModeBean getBeanById(long id) throws Exception;

    public void save(BOAIMonConModeBean value) throws Exception;

    public IBOAIMonConModeValue qryConModeInfoByConId(String conId) throws Exception;

    public long saveOrUpdate(IBOAIMonConModeValue value) throws Exception;

    public IBOAIMonConModeValue[] getAllConMode() throws Exception;

    public IBOAIMonConModeValue qryConInfoById(String conId) throws Exception;

}
