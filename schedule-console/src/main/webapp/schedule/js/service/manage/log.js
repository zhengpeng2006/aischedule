define(["core", "grid", "form"], function() {
	                                         
	var $grid,
        $form,
        $searchBtn,
        requestParam = {},
        STATE = {
			"array" : [
			      {
			    	  "text": "执行异常",
			    	  "value": "E"
			      }, {
			    	  "text": "执行结束",
			    	  "value": "F"
			      }, {
			    	  "text": "执行中",
			    	  "value": "C"
			      }
			 ],
			 "map": {
				"E": "执行异常",
				"F": "执行结束",
				"C": "执行中"
			 }
	    };

	/*
	 * 初始化文档流
	 */
	function initDom($page) {
		
		$page.addClass("log-info").append([
  	    	"<div class='page-main-title'>任务执行日志监控</div>",
	    	"<div class='page-main-searcher'></div>",
	    	"<div class='page-main-panel'>",
	    	    "<div class='page-main-panel-bar'>",
	    	    "</div>",
	    	"</div>",
		].join(""));
			
		$form = new UI.Form({
			columns: [
			   {
				   type: "ComboBox",
				   name: "taskGroup",
				   prefix: "任务分组:",
				   width: 150,
				   clearBtn: true,
				   searcher: true,
				   valueField: "groupCode",
				   textField: "groupName",
				   url: Request.get("config/task/qryTaskGroup"),
				   loadFilter: function(data) {
					   
					   return data.data.groupInfosItems;
					   
				   },
                   onChange: function(data) {
					   
					   $form.getItem("taskCodeName").clear();
					   
					   if (data) {
						   
						   $.ajax({
							   url: Request.get("manage/showTaskCodes"),
							   data: {
								   groupCode: data.groupCode
							   },
							   success: function(data) {
								   
								   $form.getItem("taskCodeName").loadData(data.data);
								   
							   }
					
						   });
						   
					   }

				   }
			   }, {
				   type: "ComboBox",
				   name: "taskCodeName",
				   prefix: "任务名称:",
				   clearBtn: true,
				   searcher: true,
				   width: 150,
				   textField: "value",
				   valueField: "key"
			   }, {
				   type: "TextInput",
				   name: "taskCode",
				   prefix: "任务编码:",
				   width: 150
			   }, {
				   type: "ComboBox",
				   name: "state",
				   prefix: "状态:",
				   clearBtn: true,
				   width: 150,
				   data: STATE.array
			   }, {
				   type: "ComboDate",
				   name: "startTime",
				   prefix: "开始时间:",
				   width: 150,
				   clock: true,
				   clearBtn: true
			   }, {
				   type: "ComboDate",
				   name: "endTime",
				   prefix: "结束时间:",
				   width: 150,
				   clock: true,
				   clearBtn: true
			   }
			]
		}).appendTo($page.find("div.page-main-searcher"));
		
		$searchBtn = $("<div class='searcher-btn log-info-searcher'><a class='pageui-btn page-query'>查询</a></div>").appendTo($page.find("div.page-main-searcher"));
		
		$grid = new UI.Grid({
			pager: {
				url: Request.get("manage/qryLogs"),
				getParams: function() {
					
					return requestParam;
					
				},
				totalFilter: function(data) {
					
					return data.data.total;
					
				}
			},
			loadFilter: function(data) {
				
			     return data.data.logsItems;
				
			},
			columns: [{
				field: "taskCode",
				title: "任务编码"
			}, {
				field: "taskName",
				title: "任务名称"
			}, {
				field: "taskVersion",
				title: "版本"
			}, {
				field: "jobId",
				title: "执行流水号"
			}, {
				field: "startTime",
				title: "开始时间"
			}, {
				field: "finishTime",
				title: "结束时间"
			}, {
				field: "state",
				title: "状态",
				formatter: function(val) {
						
				    return STATE.map[val];
						
			    }
			}, {
				field: "operation",
				title: "操作",
				formatter: function(val, row) {
					
					return [
					    "<a class='pageui-grid-icon icon-view' name='view' title='详情'></a>",
					].join("");
					
				}
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
		$searchBtn.on("click", "a", function() {
			
			var queryForm = [],
			    data = $form.getData();
			
			if (!data) return;
			
			if (!data.taskCodeName && !data.taskCode) {
				
				UI.alert("任务名称和任务编码请至少填写一项!");
				
				return;
				
			}
			
			var  start = data.startTime,
			     end = data.endTime;
			
			if (start.length && end.length && (CORE.timeFormat(start) >= CORE.timeFormat(end))) {
				
				UI.alert("开始时间必须小于结束时间");
				
				return;
				
			}
			
			queryForm.push(data);
			
			queryForm.push({});
			
			requestParam = {
				queryForm: JSON.stringify(queryForm)
		    };
			
			$grid.onRequest(true);
			
		});
		
		/*
		 * 详情
		 */
		$grid.on("click", "a.pageui-grid-icon", function() {

			var $this = $(this),
			    row = $grid.getRowData($this.closest("tr").index());
		
			if ($this.attr("name") === 'view') {
				
				require(["service/manage/log-setting"], function(obj) {
					
					obj.create({
						bean: row
					});
					
				});
				
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