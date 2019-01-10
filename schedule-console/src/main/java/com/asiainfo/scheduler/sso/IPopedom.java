package com.asiainfo.scheduler.sso;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IPopedom
{
    public abstract boolean setFirstPopedom(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, ArrayList paramArrayList, String paramString)
            throws Exception;
}
