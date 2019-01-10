package com.asiainfo.deploy.installpackage;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

/**
 * 存放变量的容器
 * 
 * @author 孙德东(24204)
 */
public class InnerContainer extends TreeMap<Integer, String>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3685139516657293595L;
	/**
	 * 内部序列
	 */
	private int index = 1;
	
	/**
	 * 如果已经存在，则返回其key
	 * 不存在加入到map中
	 * @param value
	 * @return
	 */
	public int put(String value) {
		if (containsValue(value)) {
			return getKey(value);
		}
		
		int temp = index++;
		put(temp, value);
		return temp;
	}
	
	/**
	 * 前提：该value存在
	 * @param value
	 * @return
	 */
	private int getKey(String value) {
		Set<Map.Entry<Integer, String>> entrys = this.entrySet();
		for (Map.Entry<Integer, String> entry : entrys) {
			if (StringUtils.equals(value, entry.getValue())) {
				return entry.getKey();
			}
		}
		
		return -1;
	}
}
