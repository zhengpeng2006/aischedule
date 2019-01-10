package com.asiainfo.monitor.busi.cache.impl;

import java.io.Serializable;

import com.asiainfo.monitor.busi.cache.DefaultCheckCache;
import com.asiainfo.monitor.busi.cache.interfaces.IHostUser;

public class HostUser extends DefaultCheckCache implements Serializable, IHostUser
{

    private String user_Name;

    private String user_Pwd;

    private String state;

    private String create_Date;

    private String remark;

    private String host_Id;

    private String user_Type;

    private String host_User_Id;

    public HostUser()
    {
        super();
    }

    public void setHost_User_Id(String host_User_Id)
    {
        this.host_User_Id = host_User_Id;
    }

    public void setUser_Name(String user_Name)
    {
        this.user_Name = user_Name;
    }

    public void setUser_Pwd(String user_Pwd)
    {
        this.user_Pwd = user_Pwd;
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

    public void setHost_Id(String host_Id)
    {
        this.host_Id = host_Id;
    }

    public void setUser_Type(String user_Type)
    {
        this.user_Type = user_Type;
    }

    @Override
    public String getKey()
    {
        return host_User_Id;
    }

    @Override
    public String getHost_User_Id()
    {
        return host_User_Id;
    }

    @Override
    public String getUser_Name()
    {
        return user_Name;
    }

    @Override
    public String getUser_Pwd()
    {
        return user_Pwd;
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

    @Override
    public String getHost_Id()
    {
        return host_Id;
    }

    @Override
    public String getUser_Type()
    {
        return user_Type;
    }

}
