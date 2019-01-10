define(["core", "grid", "form", "buttonsetting"], function() {
	                                         
	var $grid,
        $form,
        $searchBtn,
        requestParam = {};

	/*
	 * 初始化文档流
	 */
	function initDom($page) {
		
		$page.addClass("app-info").append([
  	    	"<div class='page-main-title'>主机应用配置</div>",
	    	"<div class='page-main-searcher'></div>",
	    	"<div class='page-main-panel'>",
	    	    "<div class='page-main-panel-bar'>",
	    	       "<a>新增</a>",
	    	    "</div>",
	    	"</div>",
		].join(""));
			
		$form = new UI.Form({
			columns: [{
				   type: "ComboBox",
				   name: "hostGroup",
				   prefix: "主机分组:",
				   width: 150,
				   require: true,
				   searcher: true,
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
					   
					   $form.getItem("nodeId").clear().loadData([]);
					   
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
				   prefix: "主机名称:",
				   width: 150,
				   require: true,
				   searcher: true,
				   valueField: "hostId",
				   textField: "hostName",
                   onChange: function(data) {
					   
					   $form.getItem("nodeId").clear();
					   
					   if (data) {
						   
						   $.ajax({
							   url: Request.get("config/host/qryNode"),
							   data: {
								   level: 3,
								   treeNodeId: data.hostId
							   },
							   success: function(data) {
								   
								   $form.getItem("nodeId").loadData(data.data.nodeTabItems);
								   
							   }
					
						   });
						   
					   }

				   }
			   }, {
				   type: "ComboBox",
				   name: "nodeId",
				   prefix: "节点名称:",
				   width: 150,
				   require: true,
				   searcher: true,
				   valueField: "nodeId",
				   textField: "nodeName"
			   }
			]
		}).appendTo($page.find("div.page-main-searcher"));
		
		$searchBtn = $("<div class='searcher-btn app-info-searcher'><a class='pageui-btn page-query'>查询</a></div>").appendTo($page.find("div.page-main-searcher"));
		
		$grid = new UI.Grid({
			pager: {
				url: Request.get("config/host/qryApp"),
				getParams: function() {
					
					return requestParam;
					
				},
				totalFilter: function(data) {
					
					return data.data.total;
					
				}
			},
			loadFilter: function(data) {
				
			     return data.data.serverTabItems;
				
			},
			columns: [{
				field: "serverCode",
				title: "应用编码"
			}, {
				field: "serverName",
				title: "应用名称"
			}, {
				field: "sversion",
				title: "应用版本"
			}, {
				field: "remark",
				title: "描述"
			}, {
				field: "operation",
				title: "操作",
				formatter: function(val, row) {
					
					var data = [
					     {
					    	"name": "修改",
					    	"value": "edit",
					    	"url": "service/config/host/app-setting"
					     }, {
					    	"name": "查看部署参数",
					    	"value": "param",
					    	"url": "service/config/host/app-param"
					     }, {
					    	"name": "复制应用",
					    	"value": "copy",
					    	"url": "service/config/host/app-copy"
						 }, {
					    	"name": "查看备用进程",
					    	"value": "back",
					    	"url": "service/config/host/app-back"
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
			    level: 4,
			    treeNodeId: data.nodeId
		    };
			
			$grid.onRequest(true);
			
		});
		
		/*
		 * 新增
		 */
		$page.on("click", "div.page-main-panel-bar>a", function() {
			
			var data = $form.getData();
				
		    if (!data) return;
			
			require(["service/config/host/app-setting"], function(obj) {
				
				obj.create({
					nodeId: $form.getItem("nodeId").getValue(),
					confirm: function() {
						
						$grid.onRequest(true);
							
					}
				});
				
			});
			
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
									nodeId: $form.getItem("nodeId").getValue(),
									confirm: function() {
										
										$grid.onRequest(true);
											
									}
								});
								
							});
							
							break;
						
				       case "param":
							
							require([url], function(obj) {
								
								obj.create({
									serverId: row.serverId
								});
								
							});
							
							break;
							
				       case "copy":
							
							require([url], function(obj) {
								
								obj.create({
									serverId: row.serverId
								});
								
							});
							
							break;
							
				       case "back":
				    	   
	                        require([url], function(obj) {
								
								obj.create({
									serverCode: row.serverCode
								});
								
							});
				    	   
				    	   break;
						
				       case "remove":
							
							UI.confirm({
								text: "确认是否删除该应用",
								confirm: function() {
									
									$.ajax({
										url: Request.get("config/host/delApp"),
										data: {
											level: 4,
											recId: row.serverId,
											treeNodeId: $form.getItem("nodeId").getValue()
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