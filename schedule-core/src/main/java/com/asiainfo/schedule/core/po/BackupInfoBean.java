package com.asiainfo.schedule.core.po;

public class BackupInfoBean implements java.io.Serializable
{
    private static final long serialVersionUID = 6265764546310143690L;
    
    private String serverCode;
    
    private String backupServerCode;
    
    public String getServerCode()
    {
        return serverCode;
    }
    
    public void setServerCode(String serverCode)
    {
        this.serverCode = serverCode;
    }
    
    public String getBackupServerCode()
    {
        return backupServerCode;
    }
    
    public void setBackupServerCode(String backupServerCode)
    {
        this.backupServerCode = backupServerCode;
    }
    
}
