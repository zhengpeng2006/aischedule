define(["core", "grid", "form", "buttonsetting"], function() {
	                                         
	var $grid,
        $form,
        $searchBtn,
        requestParam = {},
        taskMethodMap =  {
			"F": "固定时间",
			"C": "周期执行",
			"I": "立即执行"
        };

	/*
	 * 初始化文档流
	 */
	function initDom($page) {
		
		var hostMap = CORE.getAllHost();
		
		$page.addClass("monitor-info").append([
  	    	"<div class='page-main-title'>监控任务配置</div>",
	    	"<div class='page-main-searcher'></div>",
	    	"<div class='page-main-panel'>",
	    	    "<div class='page-main-panel-bar'>",
		   	       "<a name='group'>监控任务分组管理</a>",
		   	       "<p>|</p>",
		   	       "<a name='add'>新增</a>",
		   	    "</div>",
	    	"</div>",
		].join(""));
			
		$form = new UI.Form({
			cls: "common-pageui-form",
			columns: [
			   {
				   type: "ComboBox",
				   name: "groupId",
				   prefix: "监控任务分组:",
				   require: true,
				   searcher: true,
				   valueField: "groupId",
				   textField: "groupName",
				   url: Request.get("config/monitor/qryMonitorGroup"),
                   getUrlParam: function() {
					   
					   return {
						   level: 1,
						   treeNodeId: -1
					   };
					   
				   },
				   loadFilter: function(data) {
					   
					   return data.data.groupInfosItems;
					   
				   }
			   }
			]
		}).appendTo($page.find("div.page-main-searcher"));
		
		$searchBtn = $("<div class='searcher-btn'><a class='pageui-btn page-query'>查询</a></div>").appendTo($page.find("div.page-main-searcher"));
		
		$grid = new UI.Grid({
			pager: {
				url: Request.get("config/monitor/qryMonitor"),
				getParams: function() {
					
					return requestParam;
					
				},
				totalFilter: function(data) {
					
					return data.data.total;
					
				}
			},
			loadFilter: function(data) {
				
			     return data.data.infosItems;
				
			},
			columns: [{
				field: "name",
				title: "监控任务名称",
				formatter: function(val) {
				    
				    return ["<div class='ellipsis' style='width: 80px' title='", val, "'>", val, "</div>"].join("");
				    
				}
			}, {
				field: "infoCode",
				title: "监控任务编码",
				formatter: function(val) {
				    
				    return ["<div class='ellipsis' style='width: 80px' title='", val, "'>", val, "</div>"].join("");
				    
				}
			}, {
				field: "hostId",
				title: "主机",
				width: 60,
                formatter: function(val) {
					
					return hostMap[val];
					
				}
			}, {
				field: "taskMethod",
				title: "任务类型",
				width: 80,
				formatter: function(val) {
					
					return taskMethodMap[val];
					
				}
			}, {
				field: "shellName",
				title: "监控脚本",
				formatter: function(val) {
				    
				    return ["<div class='ellipsis' style='width: 140px' title='", val, "'>", val, "</div>"].join("");
				    
				}
			}, {
				field: "thresholdName",
				title: "监控阀值",
				formatter: function(val) {
				    
				    return ["<div class='ellipsis' style='width: 140px' title='", val, "'>", val, "</div>"].join("");
				    
				}
			}, {
				field: "timeName",
				title: "监控时间",
				formatter: function(val) {
				    
				    return ["<div class='ellipsis' style='width: 140px' title='", val, "'>", val, "</div>"].join("");
				    
				}
			}, {
				field: "remarks",
				title: "备注",
				width: 40
			}, {
				field: "operation",
				title: "操作",
				formatter: function(val, row) {
					
					var data = [
					     {
					    	"name": "修改",
					    	"value": "edit",
					    	"url": "service/config/monitor/monitor-setting"
					     }, {
					    	"name": "参数配置",
					    	"value": "param",
					    	"url": "service/config/monitor/monitor-param"
					     }, {
					    	"name": "监控脚本配置",
					    	"value": "script",
					    	"url": "service/config/monitor/monitor-script"
						 }, {
					    	"name": "监控阀值配置",
					    	"value": "threshold",
					    	"url": "service/config/monitor/monitor-threshold"
					     }, {
					    	"name": "监控时间配置",
					    	"value": "time",
					    	"url": "service/config/monitor/monitor-time"
						 }, {
							"name": "删除",
							"value": "remove",
						    "url": "" 
						 }        
					];
					
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
			
			var data = $form.getData();
			
			if (!data) return;
			
			requestParam = {
			    level: 2,
			    treeNodeId: data.groupId
		    };
			
			$grid.onRequest(true);
			
		});
		
		/*
		 * 新增
		 */
		$page.on("click", "div.page-main-panel-bar>a", function() {
			
			 switch ($(this).attr("name")){
			    
				     case "add":
				    	 
				    	 var data = $form.getData();
							
					     if (!data) return;
				    	 
				    	 require(["service/config/monitor/monitor-setting"], function(obj) {
								
								obj.create({
									groupId: $form.getItem("groupId").getValue(),
									confirm: function() {
										
										$grid.onRequest(true);
											
									}
								});
								
							});
				     
				     break;
				     
				     case "group":
				    	 
				    	 require(["service/config/monitor/monitor-group"], function(obj) {
				    		 
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
							groupId: $form.getItem("groupId").getValue(),
							confirm: function() {
								
								$grid.onRequest(true);
									
							}
						});
						
					});
					
					break;
					
			       case "param":
			    	   
			    		require([url], function(obj) {
							
			    			obj.create({
			    				infoId: row.infoId
							});
							
						});
						
						break;
						
			       case "script":
			    	   
			    		require([url], function(obj) {
							
			    			obj.create({
			    				infoId: row.infoId,
			    				execId: row.typeId,
			    				confirm: function() {
									
									$grid.onRequest(true);
										
								}
							});
							
						});
						
						break;
				   
			       case "threshold":
			    	   
			    		require([url], function(obj) {
							
			    			obj.create({
			    				infoId: row.infoId,
			    				thresholdId: row.thresholdId,
                                confirm: function() {
									
									$grid.onRequest(true);
										
								}
							});
							
						});
						
						break;
						
			       case "time":
			    	   
			    		require([url], function(obj) {
							
			    			obj.create({
			    				infoId: row.infoId,
			    				timeId: row.timeId,
		    				    confirm: function() {
									
									$grid.onRequest(true);
										
								}
							});
							
						});
						
						break;
						
			       case "remove":
						
						UI.confirm({
							text: "确认是否删除该监控任务",
							confirm: function() {
								
								$.ajax({
									url: Request.get("config/monitor/delMonitor"),
									data: {
										infoId: row.infoId
									},
									success: function(data) {
								    		
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