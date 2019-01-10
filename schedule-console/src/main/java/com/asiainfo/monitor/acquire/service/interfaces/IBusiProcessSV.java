package com.asiainfo.monitor.acquire.service.interfaces;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonPhysicHostValue;


/**
 * 获取后台业务进程相关信息
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: AI(NanJing)</p>
 * @author wu songkai
 * @version 3.0
 */
public interface IBusiProcessSV {

    /**
     * 查询对应应用所占CPU、MEM的比率
     * 
     * @param serverId      应用ID
     * @param qryType       查询类型: CPU-查询CPU比率;MEM-查询内存比率
     * @param threadCount   线程数
     * @param timeOut       超时时间
     * @return
     * @throws RemoteException
     * @throws Exception
     */
    public String[] getBusiKpiInfoByServerId(String[] serverId, String qryType, int threadCount, int timeOut) throws RemoteException, Exception;

    /**
     * 检查指定应用进程是否存在
     * 
     * @param serverId      应用ID
     * @param threadCount   线程数
     * @param timeOut       超时时间
     * @return
     * @throws RemoteException
     * @throws Exception
     */
    public Map<String, String> acqProcState(Map<String, List<String>> paramMap, int timeout) throws RemoteException, Exception;

    /**
     * 根据主机标识获取主机下进程的CPU，Mem信息
     * @param hostId
     * @return
     * @throws Exception
     */
    public JSONArray acqProcKpiInfo(String hostId) throws Exception;

    /**
     * 根据主机标识获取主机下所有java进程的CPU，Mem信息
     * @param hostId
     * @return
     * @throws Exception
     */
    public JSONArray acqProcessKpiInfo(String hostId) throws Exception;

    /**
     * 根据主机标识获取异常进程信息
     * 
     * @param request
     * @throws Exception
     */
    public JSONArray acqWarningInfo(String hostId) throws Exception;

    /**
     * 获取主机KPI信息，主要是主机CPU使用率，主机内存使用率，文件系统使用率
     * @param hostId
     * @return
     * @throws Exception
     */
    public JSONArray acqHostKpiInfo(String hostId) throws Exception;

    /**
     * 根据应用编码查询指定主机上对应应用的进程kpi信息
     * @param host
     * @param serverCode
     * @return
     * @throws Exception
     */
    public JSONArray acqOneProcessKPiInfo(IBOAIMonPhysicHostValue host, String serverCode) throws Exception;

}
