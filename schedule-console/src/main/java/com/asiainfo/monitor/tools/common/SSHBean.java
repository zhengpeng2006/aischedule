package com.asiainfo.monitor.tools.common;

public class SSHBean
{
    private String ip;
    private int sshPort;
    private String username;
    private String password;
    private String tmpFileName;
    private String parameters;
    private String shell;
    private int timeout = 1000;//(ms)

    public String getIp()
    {
        return ip;
    }

    public void setIp(String ip)
    {
        this.ip = ip;
    }

    public int getSshPort()
    {
        return sshPort;
    }

    public void setSshPort(int sshPort)
    {
        this.sshPort = sshPort;
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

    public String getTmpFileName()
    {
        return tmpFileName;
    }

    public void setTmpFileName(String tmpFileName)
    {
        this.tmpFileName = tmpFileName;
    }

    public String getParameters()
    {
        return parameters;
    }

    public void setParameters(String parameters)
    {
        this.parameters = parameters;
    }

    public String getShell()
    {
        return shell;
    }

    public void setShell(String shell)
    {
        this.shell = shell;
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
