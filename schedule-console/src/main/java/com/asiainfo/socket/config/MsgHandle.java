package com.asiainfo.socket.config;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: AI(NanJing)</p>
 * @author wu songkai
 * @version 3.0
 */
public class MsgHandle
{
    private List<Handle> handleList = new ArrayList<Handle>();

    public void addHandle(Handle handle)
    {
        this.handleList.add(handle);
    }

    public List<Handle> getHandleList()
    {
        return handleList;
    }

    public void setHandleList(List<Handle> handleList)
    {
        this.handleList = handleList;
    }

}
