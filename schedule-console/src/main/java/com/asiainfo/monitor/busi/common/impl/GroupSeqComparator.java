package com.asiainfo.monitor.busi.common.impl;

import java.util.Comparator;

import com.asiainfo.monitor.busi.cache.interfaces.IGroup;

public class GroupSeqComparator implements Comparator {

	public int compare(Object o1, Object o2) {
		int ret = 0;
		if (o1 == null || o2 == null)
			return ret;
		IGroup group1 = (IGroup) o1;
		IGroup group2 = (IGroup) o2;

		if (Integer.parseInt(group1.getGroup_Id()) == Integer.parseInt(group2.getGroup_Id())) {
			ret = 0;
		} else if ( Integer.parseInt(group1.getGroup_Id()) < Integer.parseInt(group2.getGroup_Id())) {
			ret = -1;
		} else if (Integer.parseInt(group1.getGroup_Id()) > Integer.parseInt(group2.getGroup_Id())) {
			ret = 1;
		}
		return ret;
	}
}
