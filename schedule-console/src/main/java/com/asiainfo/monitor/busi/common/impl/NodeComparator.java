package com.asiainfo.monitor.busi.common.impl;

import java.util.Collections;
import java.util.Comparator;

import com.asiainfo.monitor.busi.cache.interfaces.IServerNode;

public class NodeComparator implements Comparator {

	public int compare(Object o1, Object o2) {
		int ret = 0;
		if (o1 == null || o2 == null)
			return ret;
		IServerNode node1 = (IServerNode) o1;
		IServerNode node2 = (IServerNode) o2;

		return node1.getNode_Name().compareTo(node2.getNode_Name());		
	}
	
	public static void main(String[] args){
		java.util.List list=new java.util.ArrayList();
		list.add("C");
		list.add("A");
		list.add("D");
		list.add("G");
		list.add("F");
		NodeComparator comparator=new NodeComparator();
		Collections.sort(list,comparator);
		
		for (int i=0;i<list.size();i++){
			System.out.println(list.get(i));
		}
	}
}
