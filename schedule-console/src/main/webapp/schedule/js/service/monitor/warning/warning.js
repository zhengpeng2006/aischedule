define(["core", "form", "grid"], function() {
	
	var $form, $grid;
	
	/*
	 * 初始化文档流
	 */
	function initDom($page) {
		
		$page.addClass("monitor-warning").append([
  	    	"<div class='page-main-title'>告警监控</div>",
	    	"<div class='page-main-searcher'>",
	    		"<a id='monitor-warning-query' class='pageui-btn' style='float: left; margin-top: 10px;'>查询</a>",
	    		"<a id='monitor-warning-export' class='pageui-btn' style='float: left; margin: 10px 0 0 10px;'>告警信息导出</a>",
	    	"</div>",
	    	"<div class='page-main-panel'>",
	    	    "<div class='page-main-panel-bar'>告警信息</div>",
	    	"</div>"
		].join(""));
		
		$form = new UI.Form({
			itemWidth: 170,
			columns: [{
			   type: "TextInput",
			   name: "ip",
			   prefix: "IP:",
			   clearBtn: true
			}, {
			   type: "TextInput",
			   name: "infoName",
			   prefix: "信息名称:",
			   clearBtn: true
			}, {
			   type: "TextInput",
			   name: "hostName",
			   prefix: "主机名称:",
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
				url: Request.get("monitor/warning/warningList"),
				getParams: function() {
					
					return {
						qryForm: JSON.stringify([
						    $form.getData(),
						    {"ip":"","infoName":"","hostName":"","beginDate":"","endDate":""}
						])
					};
					
				},
				totalFilter: function(data) {
					
					return data.data.total;
					
				}
			},
			loadFilter: function(data) {
				
			     return data.data.triggerTabItems;
				
			},
			columns: [{
				field: "triggerId",
				title: "触发标识"
			}, {
				field: "ip",
				title: "IP"
			}, {
				field: "infoName",
				title: "信息名称"
			}, {
				field: "content",
				title: "内容"
			}, {
				field: "warnLevel",
				title: "告警级别"
			}, {
				field: "createDate",
				title: "创建日期",
				width: 100
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
		$("#monitor-warning-query").on("click", function() {
			
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
		$("#monitor-warning-export").on("click", function() {
			
			var param = $form.getData(),
				url = window.location.href,
				index = url.lastIndexOf("schedule");
			
			window.location.href = [
			    url.substr(0,index),
			    "export?opertion=triggerInfoExport",
			    "&ip=", param.ip,
			    "&infoName=", param.infoName,
			    "&hostName=", param.hostName,
			    "&beginDate=", param.beginDate,
			    "&endDate=", param.endDate
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