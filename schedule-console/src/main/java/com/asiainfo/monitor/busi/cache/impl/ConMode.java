package com.asiainfo.monitor.busi.cache.impl;

import java.io.Serializable;

import com.asiainfo.monitor.busi.cache.DefaultCheckCache;
import com.asiainfo.monitor.busi.cache.interfaces.IConMode;

public class ConMode extends DefaultCheckCache implements Serializable, IConMode
{

    private String con_Id;

    private String con_Type;

    private String con_Port;

    private String state;

    private String create_Date;

    private String remark;

    public ConMode()
    {
        super();
    }

    @Override
    public String getKey()
    {
        return con_Id;
    }

    @Override
    public String getCon_Id()
    {
        return con_Id;
    }

    @Override
    public String getCon_Type()
    {
        return con_Type;
    }

    @Override
    public String getCon_Port()
    {
        return con_Port;
    }

    @Override
    public String getState()
    {
        return state;
    }

    @Override
    public String getCreate_Date()
    {
        return create_Date;
    }

    @Override
    public String getRemark()
    {
        return remark;
    }

    public void setCon_Id(String con_Id)
    {
        this.con_Id = con_Id;
    }

    public void setCon_Type(String con_Type)
    {
        this.con_Type = con_Type;
    }

    public void setCon_Port(String con_Port)
    {
        this.con_Port = con_Port;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public void setCreate_Date(String create_Date)
    {
        this.create_Date = create_Date;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }
}
