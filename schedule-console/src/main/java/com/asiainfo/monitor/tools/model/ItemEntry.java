package com.asiainfo.monitor.tools.model;

import java.io.Serializable;

import com.asiainfo.monitor.tools.common.interfaces.IEntry;

public class ItemEntry implements Serializable,IEntry{

	private Object key;
	
	private Object value;

	
	public ItemEntry(Object key,Object value){
		this.key=key;
		this.value=value;
	}
	
	public Object getKey() {
		return key;
	}

	public void setKey(Object key) {
		this.key = key;
	}

	public Object getValue() {
		return value;
	}

	
	public Object setValue(Object newValue) {
		Object oldValue = value;
		value = newValue;
		return oldValue;
	}

	public boolean equals(ItemEntry o){
		return equals(o,true);
	}
	/**
	 * 判断两属性是否一致
	 * @param o
	 * @param perfectMatch:为false时，只要满足key相等或value相等就为true
	 * @return
	 */
	public boolean equals(ItemEntry o,boolean perfectMatch) {

		Object k1 = getKey();
		Object k2 = o.getKey();
		if (perfectMatch){
			if (k1 == k2 || (k1 != null && k1.equals(k2))) {
				Object v1 = getValue();
				Object v2 = o.getValue();
				if (v1 == v2 || (v1 != null && v1.equals(v2)))
					return true;
			}
		}else{
			if (k1 == k2 || (k1 != null && k1.equals(k2))) {
				return true;
			}else{
				Object v1 = getValue();
				Object v2 = o.getValue();
				if (v1 == v2 || (v1 != null && v1.equals(v2)))
					return true;
			}
		}
		
		return false;
	}
	
	public int hashCode() {
		return (key == null ? 0 : key.hashCode()) ^ (value == null ? 0 : value.hashCode());
	}

	public String toString() {
		return getKey() + "=" + getValue();
	}
}
