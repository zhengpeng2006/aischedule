package com.asiainfo.monitor.exeframe.controlmgr.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 保存用户权限常量信息
 * 
 * @author wsk
 * 
 */
public class UserPrivConst
{

    /**
     * 是否允许常量标志：0：不允许 ，1：允许
     */
    public static String CONST_ALLOW_NO = "CONST_ALLOW_NO";

    public static final Map<String, String> constAllowNoMap = new HashMap<String, String>() {
        {
            put("不允许", "0");
            put("允许", "1");
        }
    };
    /**
     * 锁定状态 1锁定 0 正常
     */
    public static String CONST_LOCK_STATE = "CONST_LOCK_STATE";

    public static final Map<String, String> constLockStateMap = new HashMap<String, String>() {
        {
            put("正常", "1");
            put("锁定", "0");
        }
    };

    /** 正常在用状态 **/
    public static final String USER_STATE_U = "U";

    /** 删除状态 **/
    public static final String USER_STATE_E = "E";

    public static final String USER_STATE = "USER_STATE";
    public static final Map<String,String> userStateMap = new HashMap<String, String>(){
        {
            put("使用", "U");
            put("已删除", "E");
        }
    };

}
