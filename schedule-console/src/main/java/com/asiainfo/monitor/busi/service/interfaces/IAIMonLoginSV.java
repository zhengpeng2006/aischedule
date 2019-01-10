package com.asiainfo.monitor.busi.service.interfaces;

import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonLoginValue;

public interface IAIMonLoginSV
{   
    /**
     * 根据ip和loginName查询登录信息
     * @param ip
     * @param loginName
     * @return
     * @throws Exception
     */
    public IBOAIMonLoginValue qryLoginInfoByIpAndName(String ip, String loginName) throws Exception;
    
    /**
     * 插入和更新登录信息
     * @param loginValue
     * @throws Exception
     */
    public void saveLoginInfo(IBOAIMonLoginValue loginValue) throws Exception;
    
    /**
     * 删除登录信息
     * @param loginValue
     * @throws Exception
     */
    public void deleteLoginInfo(IBOAIMonLoginValue loginValue) throws Exception;

}
