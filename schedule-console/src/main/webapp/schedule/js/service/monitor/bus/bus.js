define(["core", "combobox", "combodate", "grid", "echarts"], function() {
	                                         
	var $group, $task, $core, $taskContent;
	
	var aEc, bEc, cEc;
	
	var Timeout;
	
	/*
	 * 任务及分片信息
	 */
	function infoRender(id) {
		
		clearTimeout(Timeout);
		
		$core.empty();
		
		var $content = $([
  		    "<div class='page-main-panel'>",
  		    	"<div class='page-main-panel-bar'>任务及分片信息</div>",
  		    	"<div class='page-main-panel-content'></div>",
  		    "</div>"
  		].join("")).appendTo($core).find("div.page-main-panel-content");
		
		$taskContent = $("<div></div>").appendTo($core);
		
		$.ajax({
			url: Request.get("monitor/bus/busTask"),
			data: {
				taskCode: id
			},
			success: function(data) {
				
				var arr = [];

				
				data.data.firstArray.each(function(i, item) {
					
					arr.push("<span class='monitor-bus-task-item'>" + item + "</span>");
					
				});
				
				$content.html(arr.join(""));

				$content.find('span.monitor-bus-task-item:first').trigger('click');

				
			}
		});
			
	}
	
	/*
	 * 
	 */
	function taskRender(code) {
		
		clearTimeout(Timeout);
		
		$taskContent.empty();
		
		/*
		 * 进程信息
		 */
		(function() {
			
			var $panel = $([
			    "<div class='page-main-panel'>",
			    	"<div class='page-main-panel-bar'>进程信息</div>",
			    "</div>"
			].join("")).appendTo($taskContent);
			
			$.ajax({
				url: Request.get("monitor/bus/busProc"),
				data: {
					secondFlag: "F",
					taskCode: $task.getValue().split("__")[0],
					itemCode: code
				},
				success: function(data) {
					
					new UI.Grid({
						id: "monitor-bus-server",
						linage: 1,
						columns: [{
							field: "SERVER_ID",
							title: "应用标识"
						}, {
							field: "USER_NAME",
							title: "用户名称"
						}, {
							field: "PID",
							title: "进程标识"
						}, {
							field: "SERVER_CODE",
							title: "应用编码"
						}, {
							field: "SERVER_NAME",
							title: "应用名称"
						}, {
							field: "HOST_NAME",
							title: "主机"
						}, {
							field: "HOST_GROUP",
							title: "所属集群"
						}, {
							field: "KPI_CPU",
							title: "CPU_KPI"
						}, {
							field: "KPI_MEM",
							title: "内存KPI"
						}, {
							field: "PROC_STATE",
							title: "进程状态"
						}]
					}).appendTo($panel).loadData(data.data.processTabItems);
					
					/*
					 * 业务信息
					 */
					(function() {
						
						$taskContent.append([
			 			    "<div class='page-main-searcher' style='margin-top: 20px;'>",
						    	"<input id='monitor-bus-startTime' class='pageui-combodate' data-config='prefix: \"开始时间: \", clearBtn: true, clock: true' />",
						    	"<input id='monitor-bus-endTime' class='pageui-combodate' data-config='prefix: \"结束时间: \", clearBtn: true, clock: true' />",
						    	"<a id='monitor-bus-qryChart' class='pageui-btn' name='", code, "' style='margin-left: 10px;'>查询</a>",
						    "</div>",
						    "<div class='page-main-panel'>",
				  		    	"<div class='page-main-panel-bar'>业务信息</div>",
				  		    	"<div class='page-main-panel-content'>",
				  		    		"<div class='monitor-bus-chart' name='a'></div>",
				  		    		"<div class='monitor-bus-chart' name='b'></div>",
				  		    		"<div class='monitor-bus-chart' name='c'></div>",
				  		    	"</div>",
				  		    "</div>"
						].join(""));
						
						var $charts = $taskContent.find("div.monitor-bus-chart");
						
						aEc = echarts.init($charts.filter("div[name='a']")[0]);
						bEc = echarts.init($charts.filter("div[name='b']")[0]);
						cEc = echarts.init($charts.filter("div[name='c']")[0]);
						
						qryChart(code);
						
					})();
					
				}
			});
			
		})();
		
	}
	
	/*
	 * 查询图表
	 */
	function qryChart(code) {
		
		var start = $("#monitor-bus-startTime").getPageUI().getValue(),
			end = $("#monitor-bus-endTime").getPageUI().getValue();

		if (start.length && end.length && (CORE.timeFormat(start) >= CORE.timeFormat(end))) {
			
			UI.alert("开始时间必须小于结束时间");
			
			return;
			
		}
		
		var callee = arguments.callee;
		
		$.ajax({
			url: Request.get("monitor/bus/busChart"),
			data: {
				taskCode: $task.getValue().split("__")[0],
				itemCode: code,
				startTime: start,
				endTime: end,
				serverCode: $("#monitor-bus-server").getPageUI().getData()[0].SERVER_CODE
			},
			success: function(data) {
				
				var d = data.data;
				
				aEc.setOption({
				    tooltip: {
				        trigger: "axis"
				    },
				    legend: {
				        data: ["业务量", "积压量"],
				        y: "bottom"
				    },
				    grid: {
				    	x: 60,
				    	x2: 80,
				    	y: 40,
				    	y2: 60
				    },
				    xAxis: [{
			            type: "category",
			            boundaryGap : false,
			            data: d.xArray
			        }],
				    yAxis: [{
			            type : "value"
			        }],
				    series: [{
				    	name: "业务量",
				    	type: "line",
				    	data: d.handleArray
				    }, {
				    	name: "积压量",
				    	type: "line",
				    	data: d.leftArray
				    }]
				});
				
				bEc.setOption({
				    tooltip: {
				        trigger: "axis"
				    },
				    legend: {
				        data: ["处理速率"],
				        y: "bottom"
				    },
				    grid: {
				    	x: 60,
				    	x2: 80,
				    	y: 40,
				    	y2: 60
				    },
				    xAxis: [{
			            type: "category",
			            boundaryGap : false,
			            data: d.xArray
			        }],
				    yAxis: [{
			            type : "value"
			        }],
				    series: [{
				    	name: "处理速率",
				    	type: "line",
				    	data: d.speedArray
				    }]
				});
				
				cEc.setOption({
				    tooltip: {
				        trigger: "axis"
				    },
				    legend: {
				        data: ["错单量"],
				        y: "bottom"
				    },
				    grid: {
				    	x: 60,
				    	x2: 80,
				    	y: 40,
				    	y2: 60
				    },
				    xAxis: [{
			            type: "category",
			            boundaryGap : false,
			            data: d.xArray
			        }],
				    yAxis: [{
			            type : "value"
			        }],
				    series: [{
				    	name: "错单量",
				    	type: "line",
				    	data: d.errorArray
				    }]
				});
				
				Timeout = setTimeout(function() {
					
					if ($core.is(":visible")) callee(code);
					
				}, 10000);
				
			}
		});
		
	}
	
	/*
	 * 初始化文档流
	 */
	function initDom($page) {

		$page.addClass("monitor-bus").append([ 
		                        "<div class='page-main-title'>业务监控</div>",
								"<div class='page-main-searcher'></div>",
								"<div class='monitor-bus-core'></div>" ].join(""));

		$core = $page.find("div.monitor-bus-core");

		var $searcher = $page.find("div.page-main-searcher");

		$group = new UI.ComboBox({
					prefix : "业务分组:",
					searcher : true,
					clearBtn : true,
					valueField : "groupCode",
					textField : "groupName",
					url : Request.get("config/task/qryTaskGroup"),
					loadFilter : function(data) {

						return data.data.groupInfosItems;

					},
					onChange : function(data) {

						var param = {
							taskGroup : data.groupCode,
							taskType : "",
							taskCode : "",
							taskName : "",
							taskState : ""
						};

						$.ajax({
									url : Request.get("config/task/qryTaskName"),
									data : {
										queryForm : JSON.stringify(param)
									},
									success : function(data) {

										$task.loadData(data.data.taskNames).showPanel();

									}
								});
					}
				}).appendTo($searcher);

				$task = new UI.ComboBox({
					prefix : "任务:",
					valueField : "taskCode",
					textField : "taskName",
					searcher : true,
					clearBtn : true,
					onChange : function(data) {

						infoRender(data.taskCode);

					}
				}).appendTo($searcher);

			}
		

	
	/*
	 * 初始化事件
	 */
	function initEvent($page) {
		
		/*
		 * 选择分片
		 */
		$page.on("click", "span.monitor-bus-task-item", function() {
			
			var $this = $(this).addClass("active");
			
			$this.siblings().removeClass("active");
			
			taskRender($this.html());
			
		});
		
		/*
		 * 查询图表
		 */
		$page.on("click", "#monitor-bus-qryChart", function() {
			
			qryChart($(this).attr("name"));
			
		});
		
	}
	
	/*
	 * 创建页面对象
	 */
	function create($page) {
		
		initDom($page);
		
		initEvent($page);
		
		return $page;
		
	}
	
	return {
		create: create
	};
	
});