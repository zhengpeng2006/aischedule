package com.asiainfo.scheduler.sso;


public class LogUtil
{
    public static final short FATAL_LEVEL = 4;
    public static final short ERROR_LEVEL = 3;
    public static final short WARN_LEVEL = 2;
    public static final short INFO_LEVEL = 1;
    public static final short DEBUG_LEVEL = 0;

    public static void printLog(short type, String strTemp)
    {
      if (PortalFirstFilter.isLog.equalsIgnoreCase("false")) {
        return;
      }
      if (type == 4) {
        System.out.println("LogUtil FATAL_LEVEL:" + strTemp);
      }
      if (type == 3) {
        System.out.println("LogUtil ERROR_LEVEL:" + strTemp);
      }
      if (type == 2) {
        System.out.println("LogUtil WARN_LEVEL:" + strTemp);
      }
      if (type == 1) {
        System.out.println("LogUtil INFO_LEVEL:" + strTemp);
      }
      if (type == 0)
        System.out.println("LogUtil DEBUG_LEVEL:" + strTemp);
    }
}
