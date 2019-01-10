package com.asiainfo.deploy.test;

import junit.framework.TestCase;

/**
 * 应用模板缓存测试
 * 
 * @author 孙德东(24204)
 */
public class ApplicationTemplateCacheTest extends TestCase {

	/*public void testCache() {
		HashMap<Long, BODeployAppTemplateBean> appTemplates = null;
		try {
			appTemplates = CacheFactory.getAll(AppTemplateCache.class);
		} catch (Exception e) {
			fail("get application template error.");
		}

		Set<Map.Entry<Long, BODeployAppTemplateBean>> set = appTemplates.entrySet();
		// TODO 待完善 和 直接从数据库中取数据做对比
		for (Map.Entry<Long, BODeployAppTemplateBean> one : set) {
			System.out.println("[id:" + one.getKey() + ",value:" + one.getValue().getTemplateName() + "]");
		}
	}*/
}
