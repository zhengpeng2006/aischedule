/*
 * require.js
 * 为什么用require.js
 * 按需加载异步加载,减少页面加载js的次数.
 * 同时将页面模块化,分工明确,问题容易定位
 * @date 2016-06-09
 * @auth AnNing
 */
window._Config_ = {
	date: new Date()
};

require.config({
	//baseUrl: "../common/",
	paths: JsPath,
	urlArgs: "date=" + _Config_.date.getDate()
});

require(["core", "url"], function(CORE, url) {
	
	url.init();
	
	CORE.init();
	
});