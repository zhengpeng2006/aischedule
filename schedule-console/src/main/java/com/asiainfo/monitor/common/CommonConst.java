package com.asiainfo.monitor.common;

/**
 * 监控模块通用常量
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: AI(NanJing)</p>
 * @author wu songkai
 * @version 3.0
 */
public class CommonConst
{
    /** 树节点层级 **/
    public final static String SYS_LEVEL = "1";
    public final static String GROUP_LEVEL = "2";
    public final static String HOST_LEVEL = "3";
    public final static String NODE_LEVEL = "4";

    /** 状态标志常量 U:有效，E：无效 **/
    public final static String STATE_U = "U";
    public final static String STATE_E = "E";

    /** 用户类型标识 **/
    public final static String USER_TYPE_MONITOR = "M";
    public final static String USER_TYPE_PUBLISH = "P";

    /** 用户名密码 **/
    public final static String USER_NAME = "USER_NAME";
    public final static String USER_PASSWD = "USER_PASSWD";

    /** 主机连接方式 **/
    public final static String CON_TYPE_SSH = "SSH";
    public final static String CON_TYPE_TELNET = "Telnet";
    public final static String CON_TYPE_FTP = "FTP";
    public final static String CON_TYPE_SFTP = "SFTP";

    /**主机日志**/
    public final static String SYSTEM_DOMAIN = "CRM";
    public final static String SUB_SYSTEM_DOMAIN = "SUBSYSTEM";
    public final static String APP_SERVER_NAME = "APPCODE";
    public final static String MON_FLAG_N = "N";
    public final static String MON_FLAG_Y = "Y";

}
