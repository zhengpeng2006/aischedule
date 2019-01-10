define(["core", "combobox", "combodate", "grid", "echarts"], function() {
	                                         
	var $group, $host, $core;
	
	/*
	 * 创建主机组页面
	 */
	function createGroupPage(groupId) {
		
		var $content = $([
		    "<div class='page-main-panel'>",
		    	"<div class='page-main-panel-bar'>主机资源kpi</div>",
		    	"<div class='page-main-panel-content'></div>",
		    "</div>"
		].join("")).appendTo($core.empty()).find("div.page-main-panel-content");
		
		$.ajax({
			url: Request.get("monitor/host/qryHostList"),
			data: {
				level: 2,
				treeNodeId: groupId
			},
			success: function(data) {
				
				var arr = data.data.grpHostId.split("|"),
					titleArr = arr[1].split(",");
				
				if (titleArr.length) {
					
					arr[0].split(",").each(function(i, item) {
						
						var chart = echarts.init($("<div class='monitor-host-chart'></div>").appendTo($content)[0]);
						
						$.ajax({
							url: Request.get("monitor/host/qryKPI"),
							data: {
								level: 3,
								treeNodeId: item
							},
							success: function(data) {
								
								var dataArr = data.data.kpiHis.split("|");
								
								chart.setOption({
									title: {
										text: titleArr[i],
										x: "center",
										y: 20
									},
								    tooltip: {
								        trigger: "axis"
								    },
								    legend: {
								        data: ["CPU", "MEM", "FS"],
								        y: "bottom"
								    },
								    xAxis: [{
							            type: "category",
							            boundaryGap : false,
							            data: dataArr[3].split(",")
							        }],
								    yAxis: [{
							            type : "value"
							        }],
								    series: [{
								    	name: "CPU",
								    	type: "line",
								    	data: dataArr[0].split(",")
								    }, {
								    	name: "MEM",
								    	type: "line",
								    	data: dataArr[1].split(",")
								    }, {
								    	name: "FS",
								    	type: "line",
								    	data: dataArr[2].split(",")
								    }]
								});
								
							}
						});
						
					});
					
				} else {
					
					$content.html("<div class='monitor-host-empty'>该主机分组下暂无主机</div>");
					
				}
				
			}
		});
		
	}
	
	/*
	 * 创建主机页面
	 */
	function createHostPage(hostId) {
		
		$core.empty();
		
		/*
		 * 主机信息展示
		 */
		(function() {
			
			var $panel = $("<div class='page-main-panel'></div>").appendTo($core);
			
			$.ajax({
				url: Request.get("monitor/host/qryHostInfo"),
				data: {
					level: 3,
					treeNodeId: hostId
				},
				success: function(data) {
					
					$panel.html([
					    "<div class='page-main-panel-bar'>主机信息展示</div>",
					    "<div class='monitor-host-info'>",
					    	"<b>主机编码: </b>",
					    	"<span>{hostCode}</span>",
					    	"<b>主机名称: </b>",
					    	"<span>{hostName}</span>",
					    	"<b>主机IP: </b>",
					    	"<span>{hostIp}</span>",
					    "</div>"
					].join("").format(data.data.hostInfo));
					
				}
			});
			
		})();
		
		/*
		 * 主机资源kpi
		 */
		(function() {
			
			var $panel = $([
			    "<div class='page-main-panel'>",
			    	"<div class='page-main-panel-bar'>主机资源kpi</div>",
			    	"<div class='page-main-panel-content'>",
			    		"<div class='monitor-host-searcher' style='padding: 10px 20px 0;'>",
				    		"<input id='monitor-host-startTime' class='pageui-combodate' data-config='prefix: \"开始时间: \", clearBtn: true, clock: true' />",
					    	"<input id='monitor-host-endTime' class='pageui-combodate' data-config='prefix: \"结束时间: \", clearBtn: true, clock: true' />",
					    	"<a id='monitor-bus-qryChart' class='pageui-btn' style='margin-left: 10px;'>查询</a>",
			    		"</div>",
			    		"<div class='monitor-host-chart'></div>",
			    	"</div>",
			    "</div>"
			].join("")).appendTo($core);
			
			var ec = echarts.init($panel.find("div.monitor-host-chart")[0]);
			
			$("#monitor-bus-qryChart").on("click", function() {
				
				var start = $("#monitor-host-startTime").getPageUI().getValue(),
					end = $("#monitor-host-endTime").getPageUI().getValue();

				if (start.length && end.length && (CORE.timeFormat(start) >= CORE.timeFormat(end))) {
					
					UI.alert("开始时间必须小于结束时间");
					
					return;
					
				}
				
				$.ajax({
					url: Request.get("monitor/host/qryHostKPI"),
					data: {
						qryForm: JSON.stringify([
						    {"beginDate": start, "endDate": end},
						    {"beginDate": "", "endDate": ""}                         
						]),
						level: 3,
						treeNodeId: hostId
					},
					success: function(data) {
						
						var dataArr = data.data.kpiHis.split("|");
						
						ec.setOption({
						    tooltip: {
						        trigger: "axis"
						    },
						    legend: {
						        data: ["CPU", "MEM", "FS"],
						        x: "right",
						        y: "center",
						        orient: "vertical"
						    },
						    dataZoom: {
						        show: true,
						        start: 0,
						        end: 10
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
					            data: dataArr[3].split(",")
					        }],
						    yAxis: [{
					            type : "value"
					        }],
						    series: [{
						    	name: "CPU",
						    	type: "line",
						    	data: dataArr[0].split(",")
						    }, {
						    	name: "MEM",
						    	type: "line",
						    	data: dataArr[1].split(",")
						    }, {
						    	name: "FS",
						    	type: "line",
						    	data: dataArr[2].split(",")
						    }]
						});
						
					}
				});
				
			});
			
			$.ajax({
				url: Request.get("monitor/host/qryKPI"),
				data: {
					level: 3,
					treeNodeId: hostId
				},
				success: function(data) {
					
					var dataArr = data.data.kpiHis.split("|");
					
					ec.setOption({
					    tooltip: {
					        trigger: "axis"
					    },
					    legend: {
					        data: ["CPU", "MEM", "FS"],
					        x: "right",
					        y: "center",
					        orient: "vertical"
					    },
					    dataZoom: {
					        show: true,
					        start: 0,
					        end: 10
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
				            data: dataArr[3].split(",")
				        }],
					    yAxis: [{
				            type : "value"
				        }],
					    series: [{
					    	name: "CPU",
					    	type: "line",
					    	data: dataArr[0].split(",")
					    }, {
					    	name: "MEM",
					    	type: "line",
					    	data: dataArr[1].split(",")
					    }, {
					    	name: "FS",
					    	type: "line",
					    	data: dataArr[2].split(",")
					    }]
					});
					
				}
			});
			
		})();
		
		/*
		 * 文件系统使用率展示
		 */
		(function() {
			
			var $panel = $([
			    "<div class='page-main-panel'>",
			    	"<div class='page-main-panel-bar'>文件系统使用率展示</div>",
			    "</div>"
			].join("")).appendTo($core);
			
			new UI.Grid({
				pager: {
					url: Request.get("monitor/host/fileSysRatio"),
					getParams: function() {
						
						return {
							treeNodeId: hostId
						};
						
					},
					totalFilter: function(data) {
						
						return data.data.total;
						
					},
					immediately: true
				},
				loadFilter: function(data) {
					
				     return data.data.fsTabItems;
					
				},
				columns: [{
					field: "path",
					title: "挂载点"
				}, {
					field: "blocks",
					title: "总空间"
				}, {
					field: "percent",
					title: "使用百分比"
				}]
			}).appendTo($panel);
			
		})();
		
		/*
		 * 进程信息
		 */
		(function() {
			
			var $panel = $([
			    "<div class='page-main-panel'>",
			    	"<div class='page-main-panel-bar'>进程信息</div>",
			    	"<div class='page-main-panel-tab'>",
			    		"<div class='page-main-panel-tab-header'>",
			    			"<span name='yup' class='active'>应用进程信息展示</span>",
			    			"<span name='nope'>非应用进程信息展示</span>",
			    		"</div>",
			    		"<div class='page-main-panel-tab-content'>",
			    			"<div id='appScheduleInfo' name='yup' class='page-main-panel-tab-item active'></div>",
			    			"<div id='nonAppScheduleInfo' name='nope' class='page-main-panel-tab-item'></div>",
			    		"</div>",
			    	"</div>",
			    "</div>"
			].join("")).appendTo($core);
			
			new UI.Grid({
				pager: {
					url: Request.get("monitor/host/qryProcess"),
					getParams: function() {
						
						return {
							level: 3,
							treeNodeId: hostId
						};
						
					},
					totalFilter: function(data) {
						
						return data.data.total;
						
					},
					immediately: true
				},
				loadFilter: function(data) {
					
				     return data.data.processTabItems;
					
				},
				columns: [{
					field: "USER_NAME",
					title: "用户名称"
				}, {
					field: "PID",
					title: "进程标识"
				}, {
					field: "SERVER_CODE",
					title: "应用编码"
				}, {
					field: "KPI_CPU",
					title: "CPU_KPI"
				}, {
					field: "KPI_MEM",
					title: "内存KPI"
				}, {
					field: "STATE",
					title: "状态",
					formatter: function(val) {
						
						return val == "YC" ? "异常" : "正常";
						
					}
				}, {
					field: "TASK_COUNT",
					title: "任务数"
				}, {
					field: "task",
					title: "任务",
					formatter: function() {
						
						return "<a class='pageui-grid-btn' name='task-detail'>任务详细信息</a>";
						
					}
				}]
			}).appendTo($("#appScheduleInfo"));
			
			new UI.Grid({
				pager: {
					url: Request.get("monitor/host/qryProcKpi"),
					getParams: function() {
						
						return {
							level: 3,
							treeNodeId: hostId
						};
						
					},
					totalFilter: function(data) {
						
						return data.data.total;
						
					},
					immediately: true
				},
				loadFilter: function(data) {
					
				     return data.data.procKpiTabItems;
					
				},
				columns: [{
					field: "USER_NAME",
					title: "用户名称"
				}, {
					field: "PID",
					title: "进程标识"
				}, {
					field: "KPI_CPU",
					title: "CPU_KPI"
				}, {
					field: "KPI_MEM",
					title: "内存KPI"
				}, {
					field: "task",
					title: "任务",
					formatter: function() {
						
						return "<a class='pageui-grid-btn' name='cmd-detail'>命令详细信息</a>";
						
					}
				}]
			}).appendTo($("#nonAppScheduleInfo"));
			
		})();
		
	}
	
	/*
	 * 初始化文档流
	 */
	function initDom($page) {
		
		$page.addClass("monitor-host").append([
  	    	"<div class='page-main-title'>主机监控</div>",
  	    	"<div class='page-main-searcher'></div>",
  	    	"<div class='monitor-host-core'></div>"
		].join(""));
		
		$core = $page.find("div.monitor-host-core");
		
		var $searcher = $page.find("div.page-main-searcher");
		
		$.ajax({
			url: Request.get("config/host/qryHostGroup"),
			data: {
				level: 1,
				treeNodeId: -1
			},
			success: function(data) {
				
				$group = new UI.ComboBox({
					prefix: "主机分组:",
					data: data.data.groupTabItems,
					valueField: "groupId",
					textField: "groupName",
					searcher: true,
					onChange: function(data) {
						
						createGroupPage(data.groupId);
						
						$.ajax({
							url: Request.get("config/host/qryHost"),
							data: {
								level: 2,
								treeNodeId: data.groupId
							},
							success: function(data) {
								
								$host.loadData(data.data.hostTabItems);
								
							}
						});
						
					}
				}).appendTo($searcher);
				
				$host = new UI.ComboBox({
					prefix: "主机:",
					valueField: "hostId",
					textField: "hostName",
					searcher: true,
					clearBtn: true,
					onChange: function(data) {
						
						createHostPage(data.hostId);
						
					}
				}).appendTo($searcher);
				
			}
		});
		
	}
	
	/*
	 * 初始化事件
	 */
	function initEvent($page) {
		
		/*
		 * (非)应用进程信息详情
		 */
		$page.on("click", "a.pageui-grid-btn", function() {
			
			var $this = $(this),
				row = $this.closest("div.pageui-grid").getPageUI().getData()[$this.closest("tr").index()];
			
			switch ($this.attr("name")) {
			
				case "task-detail":
					
					new UI.Dialog({
						title: "任务详情",
						width: 700,
						height: 440,
						afterShow: function() {
							
							this.append(new UI.Grid({
								style: {
									margin: "5px 10px 0 10px"
								},
								pager: {
									url: Request.get("monitor/host/taskInfo"),
									getParams: function() {
										
										return {
											serverCode: row.SERVER_CODE
										};
										
									},
									totalFilter: function(data) {
										
										return data.data.total;
										
									},
									immediately: true
								},
								loadFilter: function(data) {
									
								     return data.data.taskTabItems;
									
								},
								columns: [{
									field: "taskCode",
									title: "任务编码"
								}, {
									field: "taskItemId",
									title: "拆分项"
								}, {
									field: "serverId",
									title: "应用编码"
								}, {
									field: "taskVersion",
									title: "任务版本"
								}, {
									field: "priority",
									title: "优先级"
								}]
							}));
							
						}
					}).pop(true);
					
					break;
			
				case "cmd-detail":
					
					new UI.Dialog({
						title: "命令"
					})
						.pop(true)
						.html("<div style='padding: 20px'>" + row.CMD_INFO + "</div>");
					
					break;
			
			}
			
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