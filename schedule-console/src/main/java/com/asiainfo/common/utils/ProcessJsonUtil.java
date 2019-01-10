package com.asiainfo.common.utils;

public class ProcessJsonUtil
{
    private static final String key = "\":null";
    
    public static String removeNullValue(String value)
    {
        if (value.contains(key))
        {
            int index = value.indexOf(key);
            String valueBefore = value.substring(0, index);
            
            int lastIndex = valueBefore.lastIndexOf("\"");
            String valueSearched = valueBefore.substring(lastIndex, valueBefore.length());
            // 找到value为null的属性，删除
            return removeNullValue(value.replaceAll(valueSearched + key, "").replaceAll(",,", ","));
        }
        else
        {
            return value;
        }
        
    }
    
}
