package com.asiainfo.deploy.common.bo;

import java.sql.Timestamp;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.deploy.common.ivalues.IBODeployAppTemplateValue;

public class BODeployAppTemplateBean extends DataContainer implements DataContainerInterface, IBODeployAppTemplateValue
{
    
    private static String m_boName = "com.asiainfo.deploy.common.bo.BODeployAppTemplate";
    
    public final static String S_OperatorId = "OPERATOR_ID";
    
    public final static String S_TemplateName = "TEMPLATE_NAME";
    
    public final static String S_Remarks = "REMARKS";
    
    public final static String S_CreateTime = "CREATE_TIME";
    
    public final static String S_ModifyTime = "MODIFY_TIME";
    
    public final static String S_TemplateId = "TEMPLATE_ID";
    
    public final static String S_Contents = "CONTENTS";
    
    public static ObjectType S_TYPE = null;
    static
    {
        try
        {
            S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
    
    public BODeployAppTemplateBean()
        throws AIException
    {
        super(S_TYPE);
    }
    
    public static ObjectType getObjectTypeStatic()
        throws AIException
    {
        return S_TYPE;
    }
    
    @Override
    public void setObjectType(ObjectType value)
        throws AIException
    {
        // �������������������ҵ���������
        throw new AIException("Cannot reset ObjectType");
    }
    
    public void initOperatorId(long value)
    {
        this.initProperty(S_OperatorId, new Long(value));
    }
    
    @Override
    public void setOperatorId(long value)
    {
        this.set(S_OperatorId, new Long(value));
    }
    
    public void setOperatorIdNull()
    {
        this.set(S_OperatorId, null);
    }
    
    @Override
    public long getOperatorId()
    {
        return DataType.getAsLong(this.get(S_OperatorId));
        
    }
    
    public long getOperatorIdInitialValue()
    {
        return DataType.getAsLong(this.getOldObj(S_OperatorId));
    }
    
    public void initTemplateName(String value)
    {
        this.initProperty(S_TemplateName, value);
    }
    
    @Override
    public void setTemplateName(String value)
    {
        this.set(S_TemplateName, value);
    }
    
    public void setTemplateNameNull()
    {
        this.set(S_TemplateName, null);
    }
    
    @Override
    public String getTemplateName()
    {
        return DataType.getAsString(this.get(S_TemplateName));
        
    }
    
    public String getTemplateNameInitialValue()
    {
        return DataType.getAsString(this.getOldObj(S_TemplateName));
    }
    
    public void initRemarks(String value)
    {
        this.initProperty(S_Remarks, value);
    }
    
    @Override
    public void setRemarks(String value)
    {
        this.set(S_Remarks, value);
    }
    
    public void setRemarksNull()
    {
        this.set(S_Remarks, null);
    }
    
    @Override
    public String getRemarks()
    {
        return DataType.getAsString(this.get(S_Remarks));
        
    }
    
    public String getRemarksInitialValue()
    {
        return DataType.getAsString(this.getOldObj(S_Remarks));
    }
    
    public void initCreateTime(Timestamp value)
    {
        this.initProperty(S_CreateTime, value);
    }
    
    @Override
    public void setCreateTime(Timestamp value)
    {
        this.set(S_CreateTime, value);
    }
    
    public void setCreateTimeNull()
    {
        this.set(S_CreateTime, null);
    }
    
    @Override
    public Timestamp getCreateTime()
    {
        return DataType.getAsDateTime(this.get(S_CreateTime));
        
    }
    
    public Timestamp getCreateTimeInitialValue()
    {
        return DataType.getAsDateTime(this.getOldObj(S_CreateTime));
    }
    
    public void initModifyTime(Timestamp value)
    {
        this.initProperty(S_ModifyTime, value);
    }
    
    @Override
    public void setModifyTime(Timestamp value)
    {
        this.set(S_ModifyTime, value);
    }
    
    public void setModifyTimeNull()
    {
        this.set(S_ModifyTime, null);
    }
    
    @Override
    public Timestamp getModifyTime()
    {
        return DataType.getAsDateTime(this.get(S_ModifyTime));
        
    }
    
    public Timestamp getModifyTimeInitialValue()
    {
        return DataType.getAsDateTime(this.getOldObj(S_ModifyTime));
    }
    
    public void initTemplateId(long value)
    {
        this.initProperty(S_TemplateId, new Long(value));
    }
    
    @Override
    public void setTemplateId(long value)
    {
        this.set(S_TemplateId, new Long(value));
    }
    
    public void setTemplateIdNull()
    {
        this.set(S_TemplateId, null);
    }
    
    @Override
    public long getTemplateId()
    {
        return DataType.getAsLong(this.get(S_TemplateId));
        
    }
    
    public long getTemplateIdInitialValue()
    {
        return DataType.getAsLong(this.getOldObj(S_TemplateId));
    }
    
    public void initContents(String value)
    {
        this.initProperty(S_Contents, value);
    }
    
    @Override
    public void setContents(String value)
    {
        this.set(S_Contents, value);
    }
    
    public void setContentsNull()
    {
        this.set(S_Contents, null);
    }
    
    @Override
    public String getContents()
    {
        return DataType.getAsString(this.get(S_Contents));
        
    }
    
    public String getContentsInitialValue()
    {
        return DataType.getAsString(this.getOldObj(S_Contents));
    }
    
}
