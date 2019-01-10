package com.asiainfo.monitor.busi.cache.impl;

import java.io.Serializable;

import com.asiainfo.monitor.busi.cache.DefaultCheckCache;
import com.asiainfo.monitor.busi.cache.interfaces.IPhysicHost;

public class PhysicHost extends DefaultCheckCache implements Serializable,IPhysicHost{

    private String hostId;
	
    private String hostCode;
	
    private String hostName;
	
    private String hostDesc;
	
    private String attendMode;
	
    private String hostIp;
	
    private String hostPort;
	
    private String hostUser;
	
    private String hostPwd;
	
	private String state;
	
	private String remarks;

    private String parentId;
	
	
    public String getState()
    {
        return state;
	}

    public void setState(String state)
    {
        this.state = state;
	}

    public String getRemarks()
    {
        return remarks;
	}

    public void setRemarks(String remarks)
    {
        this.remarks = remarks;
	}

    public String getHostId()
    {
        return hostId;
    }

    public void setHostId(String hostId)
    {
        this.hostId = hostId;
    }

    public String getHostCode()
    {
        return hostCode;
    }

    public void setHostCode(String hostCode)
    {
        this.hostCode = hostCode;
    }

    public String getHostName()
    {
        return hostName;
    }

    public void setHostName(String hostName)
    {
        this.hostName = hostName;
    }

    public String getHostDesc()
    {
        return hostDesc;
    }

    public void setHostDesc(String hostDesc)
    {
        this.hostDesc = hostDesc;
    }

    public String getAttendMode()
    {
        return attendMode;
    }

    public void setAttendMode(String attendMode)
    {
        this.attendMode = attendMode;
    }

    public String getHostIp()
    {
        return hostIp;
    }

    public void setHostIp(String hostIp)
    {
        this.hostIp = hostIp;
    }

    public String getHostPort()
    {
        return hostPort;
    }

    public void setHostPort(String hostPort)
    {
        this.hostPort = hostPort;
    }

    public String getHostUser()
    {
        return hostUser;
    }

    public void setHostUser(String hostUser)
    {
        this.hostUser = hostUser;
    }


    public String getParentId()
    {
        return parentId;
    }

    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }

    public String getHostPwd()
    {
        return hostPwd;
    }

    public void setHostPwd(String hostPwd)
    {
        this.hostPwd = hostPwd;
    }

    @Override
    public String getKey()
    {
        // TODO Auto-generated method stub
        return this.hostId;
    }
	
	
	
}
