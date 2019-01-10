package com.asiainfo.socket.common;
/**
 * 消息定义
 * 
 * @author 孙德东(24204)
 */
public interface MsgDefinition {

	/**
     * 
     * 消息处理类型
     * <p>Title: </p>
     * <p>Description: </p>
     * <p>Copyright: Copyright (c) 2014</p>
     * <p>Company: AI(NanJing)</p>
     * @author wu songkai
     * @version 3.0
     */
    public static interface MsgType
    {
        /** 命令类型 **/
        static String CMD_TYPE = "1001";

        /** shell脚本 **/
        static String SHELL_TYPE = "1002";
    }
}
