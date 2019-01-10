define(["core", "grid", "form", "buttonsetting"], function() {
	                                         
	var $grid,
        $form,
        $searchBtn,
        requestParam = {},
        stateMap =  {
			"array" : [
					      {
					    	  "text": "启用",
					    	  "value": "U"
					      }, {
					    	  "text": "未启用",
					    	  "value": "E"
					      }, {
					    	  "text": "挂起",
					    	  "value": "H"
					      }
					 ],
					 "map": {
						"U": "启用",
						"E": "未启用",
						"H": "挂起"
							}
        };

	/*
	 * 初始化文档流
	 */
	function initDom($page) {
		
		$page.addClass("task-info").append([
  	    	"<div class='page-main-title'>调度任务配置</div>",
	    	"<div class='page-main-searcher'></div>",
	    	"<div class='page-main-panel'>",
	    	    "<div class='page-main-panel-bar'>",
	    	       "<a name='group'>任务分组管理</a>",
		   	           "<p>|</p>",
		   	       "<a name='add'>新增</a>",
	    	    "</div>",
	    	"</div>",
		].join(""));
			
		$form = new UI.Form({
			itemWidth: 170,
			columns: [
			   {
				   type: "ComboBox",
				   name: "taskGroup",
				   prefix: "任务分组:",
				   searcher: true,
				   clearBtn: true,
				   valueField: "groupCode",
				   textField: "groupName",
				   url: Request.get("config/task/qryTaskGroup"),
				   loadFilter: function(data) {
					   
					   return data.data.groupInfosItems;
					   
				   }
			   }, {
				   type: "ComboBox",
				   name: "taskType",
				   prefix: "任务类型:",
				   clearBtn: true,
				   data: CORE.getTaskType
			   }, {
				   type: "ComboBox",
				   name: "taskState",
				   prefix: "任务状态:",
				   clearBtn: true,
				   data: stateMap.array
			   }, {
				   type: "TextInput",
				   name: "taskName",
				   prefix: "任务名称:"
			   }, {
				   type: "TextInput",
				   name: "taskCode",
				   prefix: "任务编码:"
			   }, {
				   type: "TextInput",
				   name: "taskSplit",
				   prefix: "任务分片:",
			   }
			]
		}).appendTo($page.find("div.page-main-searcher"));
		
		$searchBtn = $("<div class='searcher-btn task-info-searcher'><a class='pageui-btn page-query'>查询</a></div>").appendTo($page.find("div.page-main-searcher"));
		
		$grid = new UI.Grid({
			pager: {
				url: Request.get("config/task/qryTask"),
				getParams: function() {
					
					return requestParam;
					
				},
				totalFilter: function(data) {
					
					return data.data.taskTableItemsTotal;
					
				}
			},
			loadFilter: function(data) {
				
			     return data.data.taskTableItems;
				
			},
			columns: [{
				field: "taskCode",
				title: "任务编码",
				formatter: function(val) {
				    
				    return ["<div class='ellipsis' style='width: 200px' title='", val, "'>", val, "</div>"].join("");
				    
				}
			}, {
				field: "taskName",
				title: "任务名称",
				formatter: function(val) {
				    
				    return ["<div class='ellipsis' style='width: 200px' title='", val, "'>", val, "</div>"].join("");
				    
				}
			}, {
				field: "taskGroupName",
				title: "任务分组",
				formatter: function(val) {
				    
				    return ["<div class='ellipsis' style='width: 80px' title='", val, "'>", val, "</div>"].join("");
				    
				}
			}, {
				field: "taskType",
				title: "任务类型",
				formatter: function(val) {
				    
				    return ["<div class='ellipsis' style='width: 40px' title='", val, "'>", val, "</div>"].join("");
				    
				}
			}, {
				field: "items",
				title: "拆分项",
				formatter: function(val) {
				    
				    return ["<div class='ellipsis' style='width: 40px' title='", val, "'>", val, "</div>"].join("");
				    
				}
			}, {
				field: "state",
				title: "配置状态",
				width: 40,
				formatter: function(val) {
				    
				    return stateMap.map[val];
				    
				}
			}, {
				field: "startTime",
				title: "开始时间",
				formatter: function(val) {
				    
				    return ["<div class='ellipsis' style='width: 100px' title='", val, "'>", val, "</div>"].join("");
				    
				}
			}, {
				field: "operation",
				title: "操作",
				formatter: function(val, row) {
					
					var data = [
					     {
					    	"name": "修改",
					    	"value": "edit",
					    	"url": "service/config/task/task-setting"
					     }, {
					    	"name": "参数配置",
					    	"value": "param",
					    	"url": "service/config/task/task-param"
					     }, {
					    	"name": "分片配置",
					    	"value": "split",
					    	"url": "service/config/task/task-split"
						 }, {
					    	"name": "启用任务",
					    	"value": "enable",
					    	"url": ""
					     }, {
							"name": "删除",
							"value": "remove",
						    "url": "" 
						 }        
					];
					
					if (row.taskType == "tf" || row.taskType == "reload") {
						
						data.push({
					    	"name": "tf/reload详细配置",
					    	"value": "tf",
					    	"url": "service/config/task/task-tf"
						});
						
					}
					
					return [
				    	"<div class='pageui-button' data-config='data: " + JSON.stringify(data) + " , width: \"100px\" '/>",
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
			
			if (!data.taskGroup && !data.taskType && !data.taskName && !data.taskCode && !data.taskState && !data.taskSplit) {
				
				UI.alert("至少选择一个查询条件!");
				
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
		 * 新增
		 */
		$page.on("click", "div.page-main-panel-bar>a", function() {
			
			 switch ($(this).attr("name")){
			    
			     case "add":
			    	 
			    		require(["service/config/task/task-setting"], function(obj) {
							
							obj.create({
								confirm: function() {
									
									if(!requestParam.queryForm) return;
									
									$grid.onRequest(true);
								}
							});
							
						});
			     
			     break;
			     
			     case "group":
			    	 
			    	 require(["service/config/task/task-group"], function(obj) {
			    		 
			    		    CORE.main.empty();
			    		    
			    			obj.create($("<div></div>").appendTo(CORE.main));
							
					 });
			    	 
			     break;
	
	        }
			
		});
		
        var row;
		
		$grid.on("click", "a.pageui-button-setting", function() {
			
			 row = $grid.getRowData($(this).closest("tr").index());
			 
		});
		
		/*
		 * 操作按钮组
		 */
		 UI.document.off(".button").on("click.button", "ul.pageui-button-list li", function() {

			var $this = $(this),
        	    url = $this.attr("url");
		
		    switch ($this.attr("value")) {
		    
			       case "edit":
					
					require([url], function(obj) {
						
						obj.create({
							bean: row,
							confirm: function() {
								
								$grid.onRequest();
									
							}
						});
						
					});
					
					break;
					
			       case "param":
						
						require([url], function(obj) {
							
							obj.create({
								taskCode: row.taskCode
							});
							
						});
						
						break;
						
			       case "split":
						
						require([url], function(obj) {
							
							obj.create({
								taskCode: row.taskCode
							});
							
						});
						
						break;
						
			       case "tf":
						
						require([url], function(obj) {
							
							obj.create({
								taskCode: row.taskCode
							});
							
						});
						
						break;
						
			       case "enable":
						
			    	   UI.confirm({
							text: "确认启用该调度任务",
							confirm: function() {
								
								$.ajax({
									url: Request.get("config/task/enable"),
									data: {
										taskCode: row.taskCode
									},
									success: function() {
										
										UI.alert("启用成功!");
										
										$grid.onRequest();
										
									}
								});
								
							}
						});
						
						break;
					
			       case "remove":
						
						UI.confirm({
							text: "确认是否删除该调度任务",
							confirm: function() {
								
								$.ajax({
									url: Request.get("config/task/deleteTask"),
									data: {
										taskCode: row.taskCode
									},
									success: function() {
										
										UI.alert("删除成功!");
										
										$grid.onRequest(true);
										
									}
								});
								
							}
						});
						
						break;
		    
		    }
		    
		    UI.hideCombo();
		    
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