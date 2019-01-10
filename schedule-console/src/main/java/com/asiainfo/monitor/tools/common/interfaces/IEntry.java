package com.asiainfo.monitor.tools.common.interfaces;

import com.asiainfo.monitor.tools.model.ItemEntry;

public interface IEntry {

	public Object getKey();

	public void setKey(Object key);

	public Object getValue();

	
	public Object setValue(Object newValue);

	/**
	 * 判断两属性是否一致
	 * @param o
	 * @return
	 */
	public boolean equals(ItemEntry o);
	
	
	public int hashCode();

	public String toString();
}
