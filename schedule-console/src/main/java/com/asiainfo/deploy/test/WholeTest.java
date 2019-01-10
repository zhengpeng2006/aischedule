package com.asiainfo.deploy.test;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;


/**
 * 整个流程测试
 * @author 孙德东(24204)
 */
public class WholeTest {
	
	/**
	 *  测试安装包
	 */
	private static void testPackageInstall() {
		/*long deployStrategy = 0, versionId = 0;
		try {
			PackageDispatcherUtils.fullInstallByStrategy(deployStrategy, versionId);
			if (res.isSuccess()) {
				System.out.println("install success.");
			} else {
				System.out.println(res.getErrorCode() + ":" + res.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
	
	
	public static void main(String[] args) {
		//测试安装包的分发
		//testPackageInstall();
		TreeMap<String, String> map = new TreeMap<String, String>();
		map.put("1", "1");
		map.put("2", "2");
		map.put("3", "3");
		map.put("4", "4");
		
		/*Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
		
		while(it.hasNext()) {
			System.out.println(it.next().getKey());
			it.remove();
		}*/
		
		Iterator<String> it = map.keySet().iterator();
		while(it.hasNext()) {
			System.out.println(it.next());
			it.remove();
		}
		System.out.println(map.entrySet().size());
	}

}
