package com.asiainfo.scheduler.sso;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class BasePopedomImpl
{
  public boolean setFirstPopedom(HttpServletRequest request, HttpServletResponse response, ArrayList arrPathList)
    throws Exception
  {
    String requestPath = request.getRequestURI();
    boolean isPass = checkPathPriv(request, requestPath, arrPathList);
    if (isPass) {
      return true;
    }
    return false;
  }

  private boolean checkPathPriv(HttpServletRequest request, String requestPath, ArrayList arrPathList)
  {
    if (requestPath.equals("")) {
      return true;
    }
    if (requestPath.equals(request.getContextPath())) {
      return true;
    }
    if (requestPath.equals(request.getContextPath() + "/")) {
      return true;
    }
    String allowPath = "";
    for (int i = 0; i < arrPathList.size(); i++) {
      allowPath = request.getContextPath() + "/" + arrPathList.get(i);
      if (requestPath.equalsIgnoreCase(allowPath)) {
        return true;
      }

    }

    return false;
  }
}