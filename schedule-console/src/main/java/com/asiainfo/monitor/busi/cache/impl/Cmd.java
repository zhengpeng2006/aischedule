package com.asiainfo.monitor.busi.cache.impl;

import java.io.Serializable;

import com.asiainfo.monitor.busi.cache.DefaultCheckCache;
import com.asiainfo.monitor.busi.cache.interfaces.ICmd;

public class Cmd extends DefaultCheckCache implements Serializable, ICmd
{
    private String cmd_Id;
    private String cmd_Name;
    private String cmd_Desc;
    private String cmd_Type;
    private String cmd_Expr;
    private String state;
    private String param_Type;
    private String remark;

    @Override
    public String getKey()
    {
        return cmd_Id;
    }

    public Cmd()
    {
        super();
    }

    @Override
    public String getCmd_Id()
    {
        return cmd_Id;
    }

    @Override
    public String getCmd_Name()
    {
        return cmd_Name;
    }

    @Override
    public String getCmd_Desc()
    {
        return cmd_Desc;
    }

    @Override
    public String getCmd_Type()
    {
        return cmd_Type;
    }

    @Override
    public String getCmd_Expr()
    {
        return cmd_Expr;
    }

    @Override
    public String getState()
    {
        return state;
    }

    @Override
    public String getParam_Type()
    {
        return param_Type;
    }

    @Override
    public String getRemark()
    {
        return remark;
    }

    public void setCmd_Id(String cmd_Id)
    {
        this.cmd_Id = cmd_Id;
    }

    public void setCmd_Name(String cmd_Name)
    {
        this.cmd_Name = cmd_Name;
    }

    public void setCmd_Desc(String cmd_Desc)
    {
        this.cmd_Desc = cmd_Desc;
    }

    public void setCmd_Type(String cmd_Type)
    {
        this.cmd_Type = cmd_Type;
    }

    public void setCmd_Expr(String cmd_Expr)
    {
        this.cmd_Expr = cmd_Expr;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public void setParam_Type(String param_Type)
    {
        this.param_Type = param_Type;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

}
