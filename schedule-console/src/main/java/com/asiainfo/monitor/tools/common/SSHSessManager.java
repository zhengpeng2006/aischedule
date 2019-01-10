package com.asiainfo.monitor.tools.common;

import java.util.HashMap;
import java.util.Map;

import com.trilead.ssh2.Connection;
/**
 * 连接主机服务器session管理
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: AI(NanJing)</p>
 * @author wu songkai
 * @version 3.0
 */
public class SSHSessManager
{
    /** shell 脚本连接session **/
    private static ThreadLocal<Map<String, Connection>> sessionCtx = new ThreadLocal<Map<String, Connection>>();

    /**
     * @param key
     * @param conn
     */
    public static synchronized void setSSHConnection(String key, Connection conn)
    {
        Map<String, Connection> connMap = sessionCtx.get();
        if(null != connMap) {
            connMap.put(key, conn);
        }
        else if(null == connMap) {
            connMap = new HashMap<String, Connection>();
            connMap.put(key, conn);
            sessionCtx.set(connMap);
        }
    }

    /**
     * 获取ssh session
     * @param key
     * @return
     */
    public static synchronized Connection getSShConnection(String key)
    {
        Map<String, Connection> map = sessionCtx.get();
        if(null != map && map.containsKey(key)) {
            return map.get(key);
        }

        return null;
    }

}
