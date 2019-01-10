package com.asiainfo.deploy.installpackage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 启动脚本缓存，在每次主机初始化后更新
 * 
 * @author 孙德东(24204)
 */
public class ScriptCacheManager {
	//key:部署策略 value:处理后的启动脚本
	private static Map<Long, Script> cache = new ConcurrentHashMap<Long, Script>();
	
	private ScriptCacheManager() {}

	public static void put(long key, Script value) {
		cache.put(key, value);
	}
	
	public static Script get(long key) {
		return null;
	}
	
	/**
	 * 缓存的脚本，包括脚本内容，以及脚本参数
	 * @author 孙德东(24204)
	 */
	public static class Script {
		public String script;
		public InnerContainer container;
		
		public Script(String script, InnerContainer container) {
			this.script = script;
			this.container = container;
		}
	}
}
