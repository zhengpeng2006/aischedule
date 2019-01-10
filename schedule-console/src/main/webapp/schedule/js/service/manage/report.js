define(["core", "grid", "form"], function() {
	                                         
	var $grid,
        $form,
        $searchBtn,
        requestParam = {};

	/*
	 * 初始化文档流
	 */
	function initDom($page) {
		
		$page.addClass("report-info").append([
  	    	"<div class='page-main-title'>任务当天执行报表</div>",
	    	"<div class='page-main-searcher'></div>",
	    	"<div class='page-main-panel'>",
	    	    "<div class='page-main-panel-bar'>",
	    	    "</div>",
	    	"</div>",
		].join(""));
			
		$form = new UI.Form({
			columns: [{
				 type: "ComboBox",
				   name: "taskType",
				   prefix: "任务类型:",
				   clearBtn: true,
				   data: CORE.getTaskType,
				   width: 150
			   }, {
				   type: "ComboBox",
				   name: "taskCode",
				   prefix: "任务编码:",
				   clearBtn: true,
				   searcher: true,
				   width: 150,
				   url: Request.get("manage/qryTaskCode"),
				   textField: "TEXT",
				   valueField: "TEXT",
                   loadFilter: function(data) {
					   
					   return data.data;
					   
				   }
			   }, {
				   type: "TextInput",
				   name: "taskCode1",
				   prefix: "任务编码:",
				   width: 150
			   }
			]
		}).appendTo($page.find("div.page-main-searcher"));
		
		$searchBtn = $("<div class='searcher-btn report-info-searcher'><a class='pageui-btn page-query'>查询</a></div>").appendTo($page.find("div.page-main-searcher"));
		
		$grid = new UI.Grid({
			pager: {
				url: Request.get("manage/qryTaskInfos"),
				getParams: function() {
					
					return requestParam;
					
				},
				totalFilter: function(data) {
					
					return data.data.total;
					
				}
			},
			loadFilter: function(data) {
				
			     return data.data.taskInfosTabItems;
				
			},
			columns: [{
				field: "taskType",
				title: "任务类型"
			}, {
				field: "taskCode",
				title: "任务编码"
			}, {
				field: "taskName",
				title: "任务名称"
			}, {
				field: "startTime",
				title: "开始时间"
			}, {
				field: "endTime",
				title: "结束时间"
			}, {
				field: "realityStartTime",
				title: "当天实际开始时间"
			}, {
				field: "realityEndTime",
				title: "当天实际结束时间"
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
			
			var qryCondition = [],
		        data = $form.getData();
		       
			qryCondition.push(data);
			
			qryCondition.push({});
			
			requestParam = {
				qryCondition: JSON.stringify(qryCondition)
		    };
			
			$grid.onRequest(true);
			
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