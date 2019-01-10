define(["core", "grid", "form"], function() {
	                                         
	var $grid,
        $form,
        $searchBtn,
        requestParam = {},
        type;

	/*
	 * 初始化文档流
	 */
	function initDom($page) {
		
		$page.addClass("state-info").append([
  	    	"<div class='page-main-title'>任务执行状态监控</div>",
  	    	"<div class='page-main-tab'>",
  	    	    "<a type='1' class='active'>业务纬度</a>",
  	    	    "<a type='2'>主机纬度</a>",
  	    	"</div>",
	    	"<div class='page-main-searcher'></div>",
	    	"<div class='page-main-panel'>",
	    	    "<div class='page-main-panel-bar'>",
	    	    "</div>",
	    	"</div>",
		].join(""));
			
		createForm($page);
		
		$searchBtn = $("<div class='searcher-btn state-info-searcher'><a class='pageui-btn page-query'>查询</a></div>").appendTo($page.find("div.page-main-searcher"));
		
		createGrid($page);
		
	}
	
	function createForm($page) {

		if($form) $form.destroy();
		
		$form = new UI.Form({
			cls: "common-pageui-form autoWidthForm",
			columns: [].concat(type === '1' ? [
			   {
				   type: "ComboBox",
				   name: "taskGroup",
				   prefix: "任务分组:",
				   clearBtn: true,
				   width: 150,
				   searcher: true,
				   valueField: "groupCode",
				   textField: "groupName",
				   url: Request.get("config/task/qryTaskGroup"),
				   loadFilter: function(data) {
					   
					   return data.data.groupInfosItems;
					   
				   }
			   }, {
				   type: "TextInput",
				   name: "taskCode",
				   prefix: "任务编码:",
				   width: 150
			   }
			] : [{
				   type: "ComboBox",
				   name: "hostGroup",
				   prefix: "主机集群:",
				   clearBtn: true,
				   searcher: true,
				   width: 150,
				   valueField: "groupId",
				   textField: "groupName",
				   url: Request.get("config/host/qryHostGroup"),
				   loadFilter: function(data) {
					   
					   return data.data.groupTabItems;
					   
				   },
                   getUrlParam: function() {
					   
					   return {
						   level: 1,
						   treeNodeId: -1
					   };
					   
				   },
				   onChange: function(data) {
					   
					   $form.getItem("hostId").clear();
					   
					   if (data) {
						   
						   $.ajax({
							   url: Request.get("config/host/qryHost"),
							   data: {
								   level: 2,
								   treeNodeId: data.groupId
							   },
							   success: function(data) {
								   
								   $form.getItem("hostId").loadData(data.data.hostTabItems);
								   
							   }
					
						   });
						   
					   }

				   }
			   }, {
				   type: "ComboBox",
				   name: "hostId",
				   prefix: "主机:",
				   clearBtn: true,
				   searcher: true,
				   width: 150,
				   valueField: "hostId",
				   textField: "hostName",
			   }, {
				   type: "TextInput",
				   name: "serverCode",
				   prefix: "应用编码:",
				   width: 150
			   }])
		}).prependTo($page.find("div.page-main-searcher"));
		
	}
	
	function createGrid($page) {
		
		if($grid) $grid.destroy();
		
		$grid = new UI.Grid({
			pager: {
				url: type === "1" ? Request.get("manage/qryTaskStateByBusi") : Request.get("manage/qryTaskStateByHost"),
				getParams: function() {
					
					return requestParam;
					
				},
				totalFilter: function(data) {
					
					return data.data.total;
					
				}
			},
			loadFilter: function(data) {
					 
				 return type === "1" ? data.data.taskInfosItems : data.data.taskProcessesItems;
				
			},
			columns: [].concat(type === '1' ? [{
				checkbox: true
			}, {
				field: "taskCode",
				title: "任务编码"
			}, {
				field: "taskName",
				title: "任务名称"
			}, {
				field: "taskType",
				title: "任务类型"
			}, {
				field: "curVersion",
				title: "当前版本号"
			}, {
				field: "jobId",
				title: "流水号"
			}, {
				field: "taskState",
				title: "调度状态",
				formatter: function(val, row) {
					if(row.state ==='H'){
						return ["<span><a name='restart' class='pageui-grid-btn'>挂起恢复</a></span>"
						].join("");
					}else if(row.state ==='U'){
						return [
							val === 'start' ? "<span><a name='start' class='pageui-grid-btn'>挂起</a></span>" : "<span><a name='stop' class='pageui-grid-btn'>启动</a></span>"
						].join("");
					}
						

						
				}
			}, {
				field: "operation",
				title: "操作",
				formatter: function(val, row) {
					
					return [
					    "<a class='pageui-grid-icon icon-view' name='view' title='详情'></a>",
					].join("");
					
				}
			}] : [{
				checkbox: true
			}, {
				field: "serverId",
				title: "应用编码"
			}, {
				field: "pid",
				title: "PID"
			}, {
				field: "heartbeatTime",
				title: "心跳时间"
			}, {
				field: "heartbeatInfo",
				title: "心跳信息"
			}, {
				field: "serverState",
				title: "服务状态"
			}, {
				field: "operation",
				title: "操作",
				formatter: function(val, row) {
					
					return [
					    "<a class='pageui-grid-icon icon-view' name='view' title='详情'></a>",
					].join("");
					
				}
			}])
		}).appendTo($page.find("div.page-main-panel"));
		
	}
	
	/*
	 * 初始化事件
	 */
	function initEvent($page) {
		
		/*
		 * 切换tab
		 */
		$page.on("click", "div.page-main-tab>a", function(){
			
			 $(this).addClass("active").siblings().removeClass("active");
			
			 type = $(this).attr("type");
			 
			 createForm($page);
			 
			 createGrid($page);
			
		});
		
		/*
		 * 查询
		 */
		$searchBtn.on("click", "a", function() {
			
			var data = $form.getData(),
			    qryData = [];
				
		    if (!data) return;
		     
		    if (type === '2') {
		    	
		    	if (!data.hostGroup && !data.hostId && !data.serverCode) {
		    		
		    		UI.alert("至少填写一个查询条件!");
		    		
		    		return;
		    		
		    	}
		    }
		    qryData.push(data);
		    
		    qryData.push({});
				
		    requestParam = (type === '1') ? {
		    	taskQuery: JSON.stringify(qryData)
		    } : {
		    	queryFORM: JSON.stringify(qryData)
		    };
			
			$grid.onRequest(true);
			
		});
		
		$page.on("click", "a.pageui-grid-btn", function(){
			
			var $this = $(this),
		        row = $grid.getRowData($this.closest("tr").index());
	
			switch ($this.attr("name")) {
			
				  case "start":
					
					 $.ajax({
						url: Request.get("manage/hangOn"),
						data: {
							taskCode: row.taskCode
						},
						success: function() {
							
							$grid.onRequest(true);
							
						}
					});	
					 
				   break;
				   
				  case "stop":
						
					     var params = [];
					     
					     $.ajax({
							url: Request.get("manage/showParam"),
							data: {
								taskCode: row.taskCode
							},
							success: function(data) {

								if (data && data.data && data.data.taskParamsItems) {
									
									var taskParamsItems =  data.data.taskParamsItems;
									
									for (var i = 0; i < taskParamsItems.length; i++) {
										
										params.push({
											key: taskParamsItems[i].paramKey,
											value: taskParamsItems[i].paramValue
										});
									}
									
									require(["service/manage/state-param"], function(obj) {
										
										obj.create({
											params: params,
											confirm: function() {
												
												$.ajax({
													url: Request.get("manage/startTaskNow"),
													data: {
														taskCode: row.taskCode,
														params: JSON.stringify(params)
													},
													success: function() {
														
														$grid.onRequest(true);
														
													}
												});	
												
											}
										});
										
									});
									
								} else {
									
									UI.confirm({
										text: "该任务没有配置参数，是否继续启动",
										confirm: function() {
											
											 $.ajax({
												url: Request.get("manage/startTaskNow"),
												data: {
													taskCode: row.taskCode,
													params: JSON.stringify(params)
												},
												success: function() {
													
													$grid.onRequest(true);
													
												}
											});	
											
										}
									});
									
								}
								
							}
						 });
					     
					   break;
				case "restart":

					$.ajax({
						url: Request.get("config/task/enable"),
						data: {
							taskCode: row.taskCode
						},
						success: function() {

							$grid.onRequest(true);

						}
					});

					break;


			}
			
		});
		
		/*
		 * 详情
		 */
		$page.on("click", "a.pageui-grid-icon", function() {

			var $this = $(this),
			    row = $grid.getRowData($this.closest("tr").index());
		
			if ($this.attr("name") === 'view') {
				
				require(["service/manage/state-setting"], function(obj) {
					
					obj.create({
						bean: row,
						type: type
					});
					
				});
				
			}
		    
		});
		
	}
	
	/*
	 * 创建页面对象
	 */
	function create($page) {
		
		type = "1";
		
		initDom($page);
		
		initEvent($page);
		
		return $page;
		
	}
	
	return {
		create: create
	};
	
});