package com.asiainfo.schedule.core.data;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;

/**
 * zookeeper数据操作工具类
 * 
 * @author LiuQQ
 * @company Asiainfo
 * @date 2014年8月29日
 * @modify
 */
public class ZKTools {

	public static void createPath(ZooKeeper zk, String path, CreateMode createMode, List<ACL> acl) throws Exception {
		String[] list = path.split("/");
		String zkPath = "";
		for (String str : list) {
			if (str.equals("") == false) {
				zkPath = zkPath + "/" + str;
				if (zk.exists(zkPath, false) == null) {
					zk.create(zkPath, null, acl, createMode);
				}
			}
		}
	}

	public static void printTree(ZooKeeper zk, String path, Writer writer, String lineSplitChar) throws Exception {
		String[] list = getTree(zk, path);
		for (String name : list) {
			byte[] value = zk.getData(name, false, null);
			if (value == null) {
				writer.write(name + lineSplitChar);
			} else {
				writer.write(name + "[" + new String(value) + "]" + lineSplitChar);
			}
		}
	}

	public static void deleteTree(ZooKeeper zk, String path) throws Exception {
		String[] list = getTree(zk, path);
		for (int i = list.length - 1; i >= 0; i--) {
			zk.delete(list[i], -1);
		}
	}

	public static String[] getTree(ZooKeeper zk, String path) throws Exception {
		if (zk.exists(path, false) == null) {
			return new String[0];
		}
		List<String> dealList = new ArrayList<String>();
		dealList.add(path);
		int index = 0;
		while (index < dealList.size()) {
			String tempPath = dealList.get(index);
			List<String> children = zk.getChildren(tempPath, false);
			if (tempPath.equalsIgnoreCase("/") == false) {
				tempPath = tempPath + "/";
			}
			Collections.sort(children);
			for (int i = children.size() - 1; i >= 0; i--) {
				dealList.add(index + 1, tempPath + children.get(i));
			}
			index++;
		}
		return (String[]) dealList.toArray(new String[0]);
	}
}
