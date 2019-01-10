define(["core", "form", "grid"], function() {
	
	var $form, $grid;
	
	/*
	 * 初始化文档流
	 */
	function initDom($page) {
		
		$page.addClass("monitor-log").append([
  	    	"<div class='page-main-title'>操作日志查询</div>",
	    	"<div class='page-main-searcher'>",
	    		"<a id='monitor-log-query' class='pageui-btn' style='float: left; margin-top: 10px;'>查询</a>",
	    		"<a id='monitor-log-export' class='pageui-btn' style='float: left; margin: 10px 0 0 10px;'>操作日志导出</a>",
	    	"</div>",
	    	"<div class='page-main-panel'>",
	    	    "<div class='page-main-panel-bar'>日志</div>",
	    	"</div>"
		].join(""));
		
		$form = new UI.Form({
			itemWidth: 170,
			columns: [{
			   type: "ComboBox",
			   name: "module",
			   prefix: "模块:",
			   clearBtn: true,
			   data: [{
				   value: "调度",
				   text: "调度"
			   }, {
				   value: "配置",
				   text: "配置"
			   }, {
				   value: "部署",
				   text: "部署"
			   }, {
				   value: "监控",
				   text: "监控"
			   }, {
				   value: "启停",
				   text: "启停"
			   }]
			}, {
			   type: "ComboBox",
			   name: "operType",
			   prefix: "操作类型:",
			   clearBtn: true,
			   data: [{
				   value: "恢复",
				   text: "恢复"
			   }, {
				   value: "删除",
				   text: "删除"
			   }, {
				   value: "回滚",
				   text: "回滚"
			   }, {
				   value: "发布部署",
				   text: "发布部署"
			   }, {
				   value: "同步至策略当前版本",
				   text: "同步至策略当前版本"
			   }, {
				   value: "主机初始化",
				   text: "主机初始化"
			   }, {
				   value: "新增",
				   text: "新增"
			   }, {
				   value: "启动",
				   text: "启动"
			   }, {
				   value: "修改",
				   text: "修改"
			   }, {
				   value: "停止",
				   text: "停止"
			   }, {
				   value: "挂起",
				   text: "挂起"
			   }]
			}, {
			   type: "ComboBox",
			   name: "operObj",
			   prefix: "操作对象:",
			   clearBtn: true,
			   data: [{
				   value: "任务分组",
				   text: "任务分组"
			   }, {
				   value: "告警任务",
				   text: "告警任务"
			   }, {
				   value: "告警分组",
				   text: "告警分组"
			   }, {
				   value: "主机集群",
				   text: "主机集群"
			   }, {
				   value: "应用",
				   text: "应用"
			   }, {
				   value: "任务",
				   text: "任务"
			   }, {
				   value: "部署策略",
				   text: "部署策略"
			   }, {
				   value: "告警任务相关配置数据",
				   text: "告警任务相关配置数据"
			   }, {
				   value: "节点",
				   text: "节点"
			   }, {
				   value: "主机",
				   text: "主机"
			   }, {
				   value: "用户",
				   text: "用户"
			   }]
			}, {
			   type: "TextInput",
			   name: "content",
			   prefix: "操作对象标识:",
			   clearBtn: true
			}, {
			   type: "TextInput",
			   name: "operator",
			   prefix: "操作员:",
			   clearBtn: true
			}, {
			   type: "TextInput",
			   name: "operationClientIp",
			   prefix: "操作客户端IP:",
			   clearBtn: true
			}, {
			   type: "ComboDate",
			   name: "startTime",
			   prefix: "开始时间:",
			   clock: true,
			   clearBtn: true
			}, {
			   type: "ComboDate",
			   name: "endTime",
			   prefix: "结束时间:",
			   clock: true,
			   clearBtn: true
			}]
		}).prependTo($page.find("div.page-main-searcher"));
		
		$grid = new UI.Grid({
			pager: {
				url: Request.get("monitor/log/logList"),
				getParams: function() {
					
					return {
						queryForm: JSON.stringify([
						    $form.getData(),
						    {"module":"","operType":"","operObj":"","content":"","operator":"","operationClientIp":"","startTime":"","endTime":""}
						])
					};
					
				},
				totalFilter: function(data) {
					
					return data.data.total;
					
				}
			},
			loadFilter: function(data) {
				
			     return data.data.operateLogsItems;
				
			},
			columns: [{
				field: "operationModel",
				title: "模块"
			}, {
				field: "operationType",
				title: "操作类型"
			}, {
				field: "operationObjectType",
				title: "操作对象类型"
			}, {
				field: "operationObjectContent",
				title: "操作对象标识"
			}, {
				field: "createDate",
				title: "操作时间"
			}, {
				field: "operator",
				title: "操作员"
			}, {
				field: "operationClientIp",
				title: "操作客户端IP"
			}, {
				field: "remarks",
				title: "备注"
			}]
		}).appendTo($page.find("div.page-main-panel"));
		
	}
	
	/*
	 * 初始化事件
	 */
	function initEvent($page) {
		
		/*
		 * 查询
		 */
		$("#monitor-log-query").on("click", function() {
			
			var param = $form.getData(),
				start = param.startTime,
				end = param.endTime;

			if (start.length && end.length && (CORE.timeFormat(start) >= CORE.timeFormat(end))) {
				
				UI.alert("开始时间必须小于结束时间");
				
				return;
				
			}
			
			$grid.onRequest(true);
			
		});
		
		/*
		 * 导出
		 */
		$("#monitor-log-export").on("click", function() {
			
			var param = $form.getData(),
				url = window.location.href;
				index = url.lastIndexOf("schedule");
			
			window.location.href = [
			    url.substr(0,index),
			    "export?opertion=operationExport",
			    "&module=", param.module,
			    "&operType=", param.operType,
			    "&operObj=", param.operObj,
			    "&content=", param.content,
			    "&operator=", param.operator,
			    "&operationClientIp=", param.operationClientIp,
			    "&startTime=", param.startTime,
			    "&endTime=", param.endTime
			].join("");
			
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