define(["core", "grid", "form", "buttonsetting"], function() {
	                                         
	var $grid,
        $form,
        $searchBtn,
        requestParam = {};

	/*
	 * 初始化文档流
	 */
	function initDom($page) {
		
		$page.addClass("host-info").append([
  	    	"<div class='page-main-title'>主机配置</div>",
	    	"<div class='page-main-searcher'></div>",
	    	"<div class='page-main-panel'>",
		    	 "<div class='page-main-panel-bar'>",
		   	       "<a name='group'>主机分组管理</a>",
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
				   name: "hostGroup",
				   prefix: "主机分组:",
				   searcher: true,
				   require: true,
				   valueField: "groupId",
				   textField: "groupName",
				   url: Request.get("config/host/qryHostGroup"),
				   getUrlParam: function() {
					   
					   return {
						   level: 1,
						   treeNodeId: -1
					   };
					   
				   },
				   loadFilter: function(data) {
					   
					   return data.data.groupTabItems;
					   
				   }
			   }
			]
		}).appendTo($page.find("div.page-main-searcher"));
		
		$searchBtn = $("<div class='searcher-btn'><a class='pageui-btn page-query'>查询</a></div>").appendTo($page.find("div.page-main-searcher"));
		
		$grid = new UI.Grid({
			pager: {
				url: Request.get("config/host/qryHost"),
				getParams: function() {
					
					return requestParam;
					
				},
				totalFilter: function(data) {
					
					return data.data.total;
					
				}
			},
			loadFilter: function(data) {
				
			     return data.data.hostTabItems;
				
			},
			columns: [{
				field: "hostCode",
				title: "主机编码"
			}, {
				field: "hostName",
				title: "主机名称"
			}, {
				field: "hostIp",
				title: "主机IP"
			}, {
				field: "hostDesc",
				title: "主机描述"
			}, {
				field: "createDate",
				title: "创建日期"
			}, {
				field: "remarks",
				title: "备注"
			}, {
				field: "operation",
				title: "操作",
				formatter: function(val, row) {
					
					var data = [
					     {
					    	"name": "修改",
					    	"value": "edit",
					    	"url": "service/config/host/host-setting"
					     }, {
					    	"name": "查看连接方式",
					    	"value": "connection",
					    	"url": "service/config/host/host-connection"
					     }, {
					    	"name": "查看主备机",
					    	"value": "slave",
					    	"url": "service/config/host/host-slave"
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
			    treeNodeId: data.hostGroup
		    };
			
			$grid.onRequest(true);
			
		});
		
		/*
		 * 新增
		 */
		$page.on("click", "div.page-main-panel-bar>a", function() {
			
			 switch ($(this).attr("name")){
			    
				     case "add":
				    	 
				    	 require(["service/config/host/host-setting"], function(obj) {
				    		 
				    		   var data = $form.getData();
				 			
				 			   if (!data) return;
								
								obj.create({
									hostGroup: $form.getItem("hostGroup").getValue(),
									confirm: function() {
										
										$grid.onRequest(true);
											
									}
								});
								
							});
				     
				     break;
				     
				     case "group":
				    	 
				    	 require(["service/config/host/host-group"], function(obj) {
				    		 
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
								hostGroup: $form.getItem("hostGroup").getValue(),
								confirm: function() {
									
									$grid.onRequest(true);
										
								}
							});
							
						});
						
						break;
						
				       case "connection":
							
							require([url], function(obj) {
								
								obj.create({
									hostId: row.hostId
								});
								
							});
							
							break;
							
				       case "slave":
							
							require([url], function(obj) {
								
								obj.create({
									hostId: row.hostId
								});
								
							});
							
							break;
						
				       case "remove":
							
							UI.confirm({
								text: "确认是否删除该主机",
								confirm: function() {
									
									$.ajax({
										url: Request.get("config/host/delHost"),
										data: {
											level: 2,
											recId: row.hostId,
											treeNodeId: $form.getItem("hostGroup").getValue()
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