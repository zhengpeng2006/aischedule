package com.asiainfo.monitor.exeframe.controlmgr.common;

import java.util.Iterator;
import java.util.Map;

public class UserPrivUtils
{

    /**
     * 前台状态翻译
     * 
     * @param type
     * @param key
     * @return
     */
    public static String doTranslate(String type, String val)
    {
        String displayLabel = "";
        Iterator<String> iterator = null;
        Map<String, String> resMap = null;
        // 允许不允许
        if(type.equals(UserPrivConst.CONST_ALLOW_NO)) {
            resMap = UserPrivConst.constAllowNoMap;
            iterator = resMap.keySet().iterator();
        }
        // 锁定状态
        else if(type.equals(UserPrivConst.CONST_LOCK_STATE)) {
            resMap = UserPrivConst.constLockStateMap;
            iterator = resMap.keySet().iterator();
        } else if(type.equals(UserPrivConst.USER_STATE)) {
            resMap = UserPrivConst.userStateMap;
            iterator = resMap.keySet().iterator();
        }
        if(null != resMap) {
            while(iterator.hasNext()) {
                String key = iterator.next();
                if(val.equals(resMap.get(key))) {
                    displayLabel = key;
                    break;
                }
            }
        }
        return displayLabel;
    }

}
