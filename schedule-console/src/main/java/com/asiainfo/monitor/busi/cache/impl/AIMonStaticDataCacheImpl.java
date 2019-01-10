package com.asiainfo.monitor.busi.cache.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.ai.appframe2.complex.cache.impl.AbstractCache;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonStaticDataSV;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonStaticDataValue;

public class AIMonStaticDataCacheImpl extends AbstractCache
{

    // code_type和code_value组成key的分割字符
    public static final String CODE_TYPE_CODE_VALUE_SPLIT_CHAR = "^";

    // code_type前缀
    public static final String CODE_TYPE_PREFIX_KEY = "_CT^";

    public AIMonStaticDataCacheImpl()
    {
        super();
        // TODO 自动生成构造函数存根
    }

    @Override
    public HashMap getData() throws Exception
    {
        HashMap map = new HashMap();
        IAIMonStaticDataSV staticDataSV = (IAIMonStaticDataSV) ServiceFactory.getService(IAIMonStaticDataSV.class);
        if(staticDataSV == null)
            return null;
        IBOAIMonStaticDataValue[] staticData = staticDataSV.getAllStaticDataBean();

        HashMap codeTypeMap = new HashMap();
        for(int i = 0; i < staticData.length; i++) {

            map.put(staticData[i].getCodeType() + CODE_TYPE_CODE_VALUE_SPLIT_CHAR + staticData[i].getCodeValue(), staticData[i]);

            if(codeTypeMap.containsKey(staticData[i].getCodeType())) {
                List list = (List) codeTypeMap.get(staticData[i].getCodeType());
                list.add(staticData[i]);
            } else {
                List list = new ArrayList();
                list.add(staticData[i]);
                codeTypeMap.put(staticData[i].getCodeType(), list);
            }
        }

        Set keys = codeTypeMap.keySet();
        for(Iterator iter = keys.iterator(); iter.hasNext();) {
            String item = (String) iter.next();
            map.put(CODE_TYPE_PREFIX_KEY + item, (IBOAIMonStaticDataValue[]) ((List) codeTypeMap.get(item)).toArray(new IBOAIMonStaticDataValue[0]));
        }
        return map;

    }

}
