package com.asiainfo.monitor.common.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.core.Converter;

import org.apache.log4j.Logger;

/**
 * bean操作辅助类，如对象拷贝等
 * 
 * @author wsk
 * 
 */
public class BeanUtils {

	public static final Logger logger = Logger.getLogger(BeanUtils.class.getName());
	// 拷贝实例map
	private static Map<String, BeanCopier> beanCopierMap = new ConcurrentHashMap<String, BeanCopier>();

	/**
	 * 对象拷贝
	 * 
	 * @param source
	 * @param target
	 * @param overFlag
	 *            ,true，覆盖原有值，false不覆盖，
	 */
	public static void copyProperties(Object source, Object target, boolean overFlag) {
		BeanCopier copier;
		// 组合主键
		String compKey = source.getClass().getName() + target.getClass().getName();
		if (!beanCopierMap.containsKey(compKey)) {
			copier = BeanCopier.create(source.getClass(), target.getClass(), !overFlag);
			beanCopierMap.put(compKey, copier);
		} else {
			copier = beanCopierMap.get(compKey);
		}

		Converter converter = null;
		// 不覆盖拷贝
		if (!overFlag) {
			converter = new CustomConverter(target);

		}
		copier.copy(source, target, converter);
	}

	/**
	 * 默认全量拷贝
	 * 
	 * @param source
	 * @param target
	 */
	public static void copyProperties(Object source, Object target) {
		copyProperties(source, target, true);
	}

}
