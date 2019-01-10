package com.asiainfo.monitor.exeframe.configmgr.abo.dao.interfaces;

import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonLoginValue;

public interface IAIMonLoginDAO
{

    public IBOAIMonLoginValue qryLoginInfoByIpAndName(String ip, String loginName) throws Exception;

    public void saveLoginInfo(IBOAIMonLoginValue loginValue) throws Exception;

    public void deleteLoginInfo(IBOAIMonLoginValue loginValue) throws Exception;

}
