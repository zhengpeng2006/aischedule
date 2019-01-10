define(["core", "grid", "form", "buttonsetting"], function() {
	                                         
	var $grid,
        $form,
        $searchBtn,
        requestParam = {};

	/*
	 * 初始化文档流
	 */
	function initDom($page) {
		
		$page.addClass("strategy-info").append([
  	    	"<div class='page-main-title'>部署策略配置</div>",
	    	"<div class='page-main-searcher'></div>",
	    	"<div class='page-main-panel'>",
	    	    "<div class='page-main-panel-bar'>",
	    	       "<a name='template'>启停模板管理</a>",
	    	       "<p>|</p>",
	    	       "<a name='add'>新增</a>",
	    	    "</div>",
	    	"</div>",
		].join(""));
			
		$form = new UI.Form({
			cls: "common-pageui-form",
			columns: [
			   {
				   type: "TextInput",
				   name: "strategyName",
				   prefix: "部署策略名称:"
			   }
			]
		}).appendTo($page.find("div.page-main-searcher"));
		
		$searchBtn = $("<div class='searcher-btn'><a class='pageui-btn page-query'>查询</a></div>").appendTo($page.find("div.page-main-searcher"));
		
		$grid = new UI.Grid({
			pager: {
				url: Request.get("config/strategy/qryStrategies"),
				getParams: function() {
					
					return requestParam;
					
				},
				totalFilter: function(data) {
					
					return data.data.total;
					
				}
			},
			loadFilter: function(data) {
				
			     return data.data.stategiesItems;
				
			},
			columns: [{
				field: "deployStrategyName",
				title: "策略名称"
			}, {
				field: "clientHomePath",
				title: "home路径"
			}, {
				field: "clientBinPath",
				title: "bin路径"
			}, {
				field: "clientSbinPath",
				title: "sbin路径"
			}, {
				field: "operation",
				title: "操作",
				formatter: function(val, row) {
					
					var data = [
					     {
					    	"name": "修改",
					    	"value": "edit",
					    	"url": "service/config/strategy/strategy-setting"
					     }, {
					    	"name": "详情",
					    	"value": "view",
					    	"url": "service/config/strategy/strategy-setting"
					     }, {
					    	"name": "安装规则配置",
					    	"value": "rule",
					    	"url": "service/config/strategy/strategy-rule"
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
			
			var queryForm = [
						     {
						    	 name: $form.getItem("strategyName").getValue()
						     },
						     {
						    	 name: ""
						     }
						  ];
			
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
			    	 
			    	 require(["service/config/strategy/strategy-setting"], function(obj) {
							
							obj.create({
								confirm: function() {
									
									$grid.onRequest(true);
										
								}
							});
							
					 });
			     
			     break;
			     
			     case "template":
			    	 
			    	 require(["service/config/strategy/template"], function(obj) {
			    		 
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
								
								$grid.onRequest(true);
									
							}
						});
						
					});
					
				   break;
					
			       case "remove":
						
						UI.confirm({
							text: "确认是否删除该部署策略",
							confirm: function() {
								
								$.ajax({
									url: Request.get("config/strategy/deleteStrategy"),
									data: {
										deployStrategyId: row.deployStrategyId
									},
									success: function(data) {
											
										UI.alert("删除成功!");
										
										$grid.onRequest(true);
											
									}
								});
								
							}
						});
						
				  break;
						
			     case "view":
			    	 
			    	 require([url], function(obj) {
							
						obj.create({
							bean: row,
							view: true
						});
							
					});
			      
			      break;
			      
			     case "rule":
						
						require([url], function(obj) {
							
							obj.create({
								installRule: row.installRule,
								deployStrategyId: row.deployStrategyId,
                                confirm: function() {
									
									$grid.onRequest(true);
										
								}
							});
							
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