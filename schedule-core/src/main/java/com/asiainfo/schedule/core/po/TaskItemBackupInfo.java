package com.asiainfo.schedule.core.po;

/**
 * @author JIYM
 * @created 16/10/11.
 */
public class TaskItemBackupInfo
{
    String taskCode;
    
    String itemCode;
    
    String normalServerName;
    
    String backupServerName;
    
    public String getTaskCode()
    {
        return taskCode;
    }
    
    public void setTaskCode(String taskCode)
    {
        this.taskCode = taskCode;
    }
    
    public String getItemCode()
    {
        return itemCode;
    }
    
    public void setItemCode(String itemCode)
    {
        this.itemCode = itemCode;
    }
    
    public String getNormalServerName()
    {
        return normalServerName;
    }
    
    public void setNormalServerName(String normalServerName)
    {
        this.normalServerName = normalServerName;
    }
    
    public String getBackupServerName()
    {
        return backupServerName;
    }
    
    public void setBackupServerName(String backupServerName)
    {
        this.backupServerName = backupServerName;
    }
    
    @Override
    public String toString()
    {
        return "TaskItemBackupInfo [taskCode=" + taskCode + ", itemCode=" + itemCode + ", normalServerName="
            + normalServerName + ", backupServerName=" + backupServerName + "]";
    }
    
}
