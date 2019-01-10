package com.asiainfo.monitor.service.interfaces;

public interface IHostBackSV
{
    /**
     * 根据主机Id备份主机
     * 
     * @param request
     * @throws Exception
     * @author szh
     */
    public boolean hostBack(String hostId) throws Exception;

    /**
     * 根据应用编码备份应用
     * 
     * @param request
     * @throws Exception
     * @author szh
     */
    public boolean hostServerBack(String serverCode) throws Exception;

}
