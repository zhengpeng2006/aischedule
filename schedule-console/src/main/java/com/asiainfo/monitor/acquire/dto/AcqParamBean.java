package com.asiainfo.monitor.acquire.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 采集参数对象
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: AI(NanJing)</p>
 * @author wu songkai
 * @version 3.0
 */
public class AcqParamBean implements Cloneable
{
    //添加脚本路径
    private String homePath;
    private String ip;
    private String port;
    /** 采集类型 **/
    private String acqType;
    private String username;
    private String password;
    private String serverCode;
    private String path;
    private String shell;
    private String kpiType;
    private String tmpShellFileName;
    private int timeout = 1000;//(ms)
    private List<String> paramList = new ArrayList<String>();

    /** 根据应用用户查找应用进程，目前只为调度那边查找应用进程使用 **/
    private String lookupUserName;

    public String getHomePath()
    {
        return homePath;
    }

    public void setHomePath(String homePath)
    {
        this.homePath = homePath;
    }

    public String getIp()
    {
        return ip;
    }

    public void setIp(String ip)
    {
        this.ip = ip;
    }


    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getServerCode()
    {
        return serverCode;
    }

    public void setServerCode(String serverCode)
    {
        this.serverCode = serverCode;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public String getShell()
    {
        return shell;
    }

    public void setShell(String shell)
    {
        this.shell = shell;
    }


    public String getAcqType()
    {
        return acqType;
    }

    public void setAcqType(String acqType)
    {
        this.acqType = acqType;
    }

    public void setParamList(List<String> paramList)
    {
        this.paramList = paramList;
    }

    public String getParamStr()
    {
        String paramStr = "";
        if(null != paramList) {
            for(String param : paramList) {
                paramStr += " " + param;
            }
        }
        return paramStr;
    }

    public String getPort()
    {
        return port;
    }

    public void setPort(String port)
    {
        this.port = port;
    }

    public String getKpiType()
    {
        return kpiType;
    }

    public void setKpiType(String kpiType)
    {
        this.kpiType = kpiType;
    }

    public List<String> getParamList()
    {
        return paramList;
    }

    public String getTmpShellFileName()
    {
        return tmpShellFileName;
    }

    public void setTmpShellFileName(String tmpShellFileName)
    {
        this.tmpShellFileName = tmpShellFileName;
    }

    @Override
    public AcqParamBean clone() throws CloneNotSupportedException
    {
        // TODO Auto-generated method stub
        return (AcqParamBean) super.clone();
    }

    public String getLookupUserName()
    {
        return lookupUserName;
    }

    public void setLookupUserName(String lookupUserName)
    {
        this.lookupUserName = lookupUserName;
    }

    public int getTimeout()
    {
        return timeout;
    }

    public void setTimeout(int timeout)
    {
        this.timeout = timeout;
    }

}
