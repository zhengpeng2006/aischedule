package com.asiainfo.common.utils;

import net.sf.json.JSONArray;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class PagingUtil
{
    private static final Logger LOGGER = Logger.getLogger(PagingUtil.class);
    
    public static JSONArray convertArray2Page(JSONArray array, String offset, String linage)
    {
        
        JSONArray arrayBack = new JSONArray();
        
        if (array == null || CollectionUtils.isEmpty(array))
        {
            return arrayBack;
        }
        
        try
        {
            if (StringUtils.isNotBlank(offset) && StringUtils.isNotBlank(linage))
            {
                int startIndex = Integer.parseInt(offset);
                int endIndex = Integer.parseInt(linage) + startIndex;
                
                int size = array.size();
                
                if (startIndex >= size)
                {
                    return arrayBack;
                }
                
                if (startIndex < size && endIndex > size)
                {
                    endIndex = size;
                }
                
                // 不可以用subList，存在缺陷。处理的返回不正确
                for (int i = startIndex; i < endIndex; i++)
                {
                    arrayBack.add(array.get(i));
                }
                return arrayBack;
            }
        }
        catch (Exception e)
        {
            // 异常
            LOGGER.error("paging failed, " + e.getMessage());
        }
        return array;
    }
}
