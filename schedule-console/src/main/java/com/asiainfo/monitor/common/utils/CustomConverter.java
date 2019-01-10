package com.asiainfo.monitor.common.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

import net.sf.cglib.core.Converter;

import org.apache.log4j.Logger;

/**
 * 转换器，对象拷贝使用
 * 
 * @author wsk
 * 
 */
public class CustomConverter implements Converter {
    public static final Logger logger = Logger.getLogger(CustomConverter.class.getName());

    public Object target;

    public CustomConverter(Object target) {
        this.target = target;
    }

    @Override
    public Object convert(Object value, Class target, Object context) {

        if (context.toString().startsWith("set")) {
            String methodName = context.toString().replace(context.toString().substring(0, 3), "get");
            Map<String, PropertyDescriptor> getterMap = getPropertiesHelper(this.target.getClass());
            if (getterMap.containsKey(methodName)) {
                try {
                    Object retValue = getterMap.get(methodName).getReadMethod().invoke(this.target, null);
					if ((retValue instanceof String) && null != retValue && retValue.toString().trim().length() > 0) {
                        return retValue;
                    }
                } catch (Exception e) {
                    logger.error("reflect get value error:", e);
                }
            }
        }
        return value;
    }

    /**
     * 获取类的所有get方法
     * 
     * @param type
     * @return
     */
    private Map<String, PropertyDescriptor> getPropertiesHelper(Class type) {
        Map<String, PropertyDescriptor> propertyMap = new HashMap<String, PropertyDescriptor>();
        try {
            BeanInfo info = Introspector.getBeanInfo(type, Object.class);
            PropertyDescriptor[] all = info.getPropertyDescriptors();
            for (int i = 0; i < all.length; i++) {
                PropertyDescriptor pd = all[i];
                if (pd.getReadMethod() != null) {
                    propertyMap.put(pd.getReadMethod().getName(), pd);
                }
            }
        } catch (Exception e) {
            logger.error("reflect get value error:", e);
        }
        return propertyMap;
    }

}
