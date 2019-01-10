package com.asiainfo.deploy.common.ivalues;

import java.sql.Timestamp;

import com.ai.appframe2.common.DataStructInterface;

public interface IBODeployAppTemplateValue extends DataStructInterface
{
    
    public final static String S_OperatorId = "OPERATOR_ID";
    
    public final static String S_TemplateName = "TEMPLATE_NAME";
    
    public final static String S_Remarks = "REMARKS";
    
    public final static String S_CreateTime = "CREATE_TIME";
    
    public final static String S_ModifyTime = "MODIFY_TIME";
    
    public final static String S_TemplateId = "TEMPLATE_ID";
    
    public final static String S_Contents = "CONTENTS";
    
    public long getOperatorId();
    
    public String getTemplateName();
    
    public String getRemarks();
    
    public Timestamp getCreateTime();
    
    public Timestamp getModifyTime();
    
    public long getTemplateId();
    
    public String getContents();
    
    public void setOperatorId(long value);
    
    public void setTemplateName(String value);
    
    public void setRemarks(String value);
    
    public void setCreateTime(Timestamp value);
    
    public void setModifyTime(Timestamp value);
    
    public void setTemplateId(long value);
    
    public void setContents(String value);
}
