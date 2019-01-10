package com.asiainfo.monitor.busi.common.impl;

import java.util.Comparator;

import com.asiainfo.monitor.busi.cache.impl.PriEntity;

/**
 * 根据权限实体的seq进行从小到大排序
 * @author 
 *
 */
public class PriEntitySeqComparator implements Comparator {

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(T, T)
	 */
	public int compare(Object o1, Object o2) {
		int ret = 0;
		if (o1 == null || o2 == null)
			return ret;
		PriEntity pEntity1 = (PriEntity) o1;
		PriEntity pEntity2 = (PriEntity) o2;

		if (pEntity1.getSeq() == pEntity2.getSeq()) {
			ret = 0;
		} else if (pEntity1.getSeq() < pEntity2.getSeq()) {
			ret = -1;
		} else if (pEntity1.getSeq() > pEntity2.getSeq()) {
			ret = 1;
		}
		return ret;
	}
}
