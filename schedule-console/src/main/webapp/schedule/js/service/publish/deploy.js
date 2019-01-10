define(["core", "grid", "form", "buttonsetting"], function() {
	                                         
	var $grid,
        $form,
        $searchBtn,
        requestParam = {},
        array;

	/*
	 * 初始化文档流
	 */
	function initDom($page) {
		
		$page.addClass("deploy-info").append([
  	    	"<div class='page-main-title'>应用发布</div>",
	    	"<div class='page-main-searcher'></div>",
	    	"<div class='page-main-panel'>",
	    	    "<div class='page-main-panel-bar'>",
	    	    "<div class='page-main-panel-bar'>",
		   	       "<a name='monitor'>监控</a>",
		   	       "<p>|</p>",
		   	       "<a name='update'>同步</a>",
		   	       "<p>|</p>",
		   	       "<a name='rollback'>回滚</a>",
		   	       "<p>|</p>",
		   	       "<a name='publish'>发布</a>",
		   	       "<p>|</p>",
		   	       "<a name='init'>初始化</a>",
		   	    "</div>",
	    	    "</div>",
	    	"</div>",
		].join(""));
			
		$form = new UI.Form({
			cls: "common-pageui-form",
			columns: [
			   {
				   type: "ComboBox",
				   name: "strategy",
				   prefix: "部署策略:",
				   require: true,
				   searcher: true,
				   valueField: "deployStrategyId",
				   textField: "deployStrategyName",
				   url: Request.get("config/strategy/qryStrategies"),
				   getUrlParam: function() {
					   
					   return {
						   queryForm: JSON.stringify([{"name":""},{"name":""}])
					   };
					   
				   },
				   loadFilter: function(data) {
					   
					   return data.data.stategiesItems;
					   
				   }
			   }
			]
		}).appendTo($page.find("div.page-main-searcher"));
		
		$searchBtn = $("<div class='searcher-btn'><a class='pageui-btn page-query'>查询</a></div>").appendTo($page.find("div.page-main-searcher"));
		
		$grid = new UI.Grid({
			pager: {
				url: Request.get("publish/qryDeploy"),
				getParams: function() {
					
					return requestParam;
					
				},
				totalFilter: function(data) {
					
					return data.data.total;
					
				}
			},
			loadFilter: function(data) {
				
			     return data.data.nodeTableItems;
				
			},
			columns: [{
				checkbox: true
			}, {
				field: "nodeCode",
				title: "节点编码"
			}, {
				field: "nodeName",
				title: "节点名称"
			}, {
				field: "hostName",
				title: "主机"
			}, {
				field: "hostGroup",
				title: "主机分组"
			}, {
				field: "versionId",
				title: "版本号"
			}, {
				field: "operationState",
				title: "操作结果"
			}, {
				field: "createDate",
				title: "创建时间"
			}, {
				field: "remark",
				title: "备注"
			}, {
				field: "operation",
				title: "操作",
				formatter: function(val, row) {
					
					if (row.versionId) {
						
						return [
							    "<a class='pageui-grid-icon icon-view' name='view' title='删除'></a>",
						].join("");
						
					}
					
				}
			}],
			checkOnRow: true,
			onCheck: function(row, flag, index) {

				flag ?  array.push(row.nodeId + "#" + row.versionId) : array.splice(array.indexOf(row.nodeId + "#" + row.versionId),1);
				
			}
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
				strategyId: data.strategy,
				strategyName: $form.getItem("strategy").getText()
		    };
			
			array = [];

			$grid.onRequest(true);
			 
		});
		
		/*
		 * 详情
		 */
		$grid.on("click", "a.pageui-grid-icon", function() {

			var $this = $(this),
			    row = $grid.getRowData($this.closest("tr").index());
		
		    if ($this.attr("name") === 'view') {
		    
					require(["service/publish/deploy-setting"], function(obj) {
						
						obj.create({
							versionId: row.versionId
						});
						
					});
					
		    }
		    
		});
		
		/*
		 * 发布，初始化
		 */
		$page.on("click", "div.page-main-panel-bar>a", function() {

			 switch ($(this).attr("name")){
			    
				     case "init":
				    	 
				    	 if (!array.length) {
								
							UI.alert("请选择一个节点!");
							
							return;
							
						 }
				    	 
				    	 $.ajax({
							url: Request.get("publish/initHost"),
							data: {
								strategyId: $form.getItem("strategy").getValue(),
								nodes: array.join(",")
							},
							success: function(data) {
						    		
								UI.alert("初始化开始!");
								
								interval();
								
							}
						});
				    	 
				     break;
				     
				     case "publish":
				    	 
				    	 if (!array.length) {
								
							UI.alert("请选择一个节点!");
							
							return;
							
						 }
				    	 
				    	 require(["service/publish/publish"], function(obj) {
								
							obj.create({
								
								confirm: function(param) {
									
									$.ajax({
										url: Request.get("publish/saveVersion"),
										data: {
											versionInfo: JSON.stringify(param),
											param: "deploy" + "," + $form.getItem("strategy").getValue() + "," + array.join(",")
										},
									    success: function(data) {
									    	
									    	interval();
									    	
									    }
										
									});
								}
							
							});
							
						 });
				    	 
				     break;
				     
				    case "rollback":
				    	
				    	if (!array.length) {
								
							UI.alert("请选择一个节点!");
							
							return;
							
						}
				    	
				    	require(["service/publish/rollback"], function(obj) {
							
							obj.create({
								stretagyId: $form.getItem("strategy").getValue(),
                                confirm: function(param) {
									
									$.ajax({
										url: Request.get("publish/rollback"),
										data: {
											param: param + "," + $form.getItem("strategy").getValue(),
											nodeIds: array.join(",")
										},
										success: function(data) {
											
											interval();
											
										}
									});
									
								}

							});
							
						 });
				    	
				    	 break;
					     
                    case "update":
                    	
                    	$.ajax({
							url: Request.get("publish/updateToCurVersion"),
							data: {
								strategyId: $form.getItem("strategy").getValue()
							},
							success: function(data) {
								
								
							}
						});
				    	 
					     break;
					     
                    case "monitor":
                    	
                    	var data = $form.getData();
        				
            		    if (!data) return;
                    	
                    	 $grid.onRequest(true);
				    	 
					     break;
	 
	         }
			
		});
		
	}
	
    function interval() {
    	
    	var i = 0;

		var interval = setInterval(function() {
			
	    	$grid.onRequest(true);
			
	    	i++;
	    	
	    	if (i === 5) {
				
				clearInterval(interval);
				
			}
	    	
		}, 3000);
		
    }
	
	/*
	 * 创建页面对象
	 */
	function create($page) {
		
		array = [];
		
		initDom($page);
		
		initEvent($page);
		
		return $page;
		
	}
	
	return {
		create: create
	};
	
});