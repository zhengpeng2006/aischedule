define(["core", "grid"], function() {
	                         
	var $group, $list;
	
	var STATUS = {
		"正常的进程": "run",
		"未启动的进程": "stop",
		"重复的进程": "repeat"
	};
	
	var VER;
	
	/*
	 * 渲染
	 */
	function render() {
		
		$group.empty();
		
		$list.empty();
		
		$.ajax({
			url: Request.get("monitor/status/WarningInfo"),
			success: function(data) {
				
				VER = data.data.curVersion;
				
				data.data.hostServer.each(function(i, item) {
					
					var arr = [],
						host, _arr;
					
					for (var j = 1, jl = item.length; j < jl; j++) {
						
						host = item[j];
						
						_arr = [];
						
						host[1] && host[1].each(function(k, process) {
							
							_arr.push([
							    "<li class='", STATUS[process.desc], "'>",
							    	"<p>{serverCode}</p>",
							    	"<b>任务数</b>",
							    	"<span>{totolCnt}</span>",
							    	"<b>执行中</b>",
							    	"<span>{runCnt}</span>",
							    	"<b>未启动</b>",
							    	"<span>{stopCut}</span>",
							    "</li>"
							].join("").format(process));
							
						});
						
						arr.push([
						    "<div class='page-main-panel'>",
							    "<div class='page-main-panel-bar'>",
							    	host[0].title,
							    "</div>",
							    "<ul class='monitor-status-process'>", _arr.join(""), "</ul>",
						    "</div>"
						].join(""));
						
					}

					$group.append("<div>" + item[0].group + "</div>");
					
					$list.append("<div>" + arr.join("") + "</div>");
					
				});
				
				$group.find("div:first").trigger("click");
				
			}
		});
		
	}
	
	/*
	 * 初始化文档流
	 */
	function initDom($page) {
		
		$page.addClass("monitor-status").append([
  	    	"<div class='page-main-title'>进程状态监控</div>",
  	    	"<div class='monitor-status-group'></div>",
			"<div class='monitor-status-sign'>",
			"<span class='run'>进程正常启动</span>",
			"<span class='stop'>进程未启动</span>",
			"<span class='repeat'>进程重复启动</span>",
			"</div>",
  	    	"<div class='monitor-status-list'></div>"
		].join(""));
		
		$group = $page.find(".monitor-status-group");
		
		$list = $page.find(".monitor-status-list");
		
	}
	
	/*
	 * 初始化事件
	 */
	function initEvent($page) {
		
		$group.on("click", "div", function() {
			
			var $this = $(this).addClass("active");
			
			$this.siblings().removeClass("active");
			
			$list.find(">div")
				.hide()
			.eq($this.index())
				.show();
			
		});
		
		$list.on("dblclick", "li", function() {
			
			var $this = $(this);
			
			$.ajax({
				url: Request.get("monitor/status/version"),
				data: {
					version: VER
				},
				success: function(data) {
					
					if (data.data.flag == "T") {
						
						$.ajax({
							url: Request.get("monitor/status/detail"),
							data: {
								serverCode: $this.find("p").text()
							},
							success: function(data) {
								
								new UI.Dialog({
									title: "任务详情",
									width: 800,
									afterShow: function() {
										
										this.append(new UI.Grid({
											data: data.data.taskInfos,
											columns: [{
												title: "任务编码",
												field: "taskCode"
											}, {
												title: "任务类型",
												field: "taskType"
											}, {
												title: "任务名称",
												field: "taskName"
											}, {
												title: "拆分项",
												field: "item"
											}, {
												title: "执行状态",
												field: "state"
											}, {
												title: "执行流水号",
												field: "taskJobId"
											}, {
												title: "创建时间",
												field: "createTime"
											}]
										}));
										
									}
								}).pop(true);
								
							}
						});
						
					} else {
						
						render();
						
					}
					
				}
			});
			
		});
		
	}
	
	/*
	 * 创建页面对象
	 */
	function create($page) {
		
		initDom($page);
		
		initEvent($page);
		
		render();
		
		return $page;
		
	}
	
	return {
		create: create
	};
	
});