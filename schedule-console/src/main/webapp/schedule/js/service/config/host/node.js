define(["core", "grid", "form"], function() {
	                                         
	var $grid,
        $form,
        $searchBtn,
        requestParam = {};

	/*
	 * 初始化文档流
	 */
	function initDom($page) {
		
		$page.addClass("node-info").append([
  	    	"<div class='page-main-title'>主机节点配置</div>",
	    	"<div class='page-main-searcher'></div>",
	    	"<div class='page-main-panel'>",
	    	    "<div class='page-main-panel-bar'>",
	    	       "<a>新增</a>",
	    	    "</div>",
	    	"</div>"
		].join(""));
			
		$form = new UI.Form({
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
				   prefix: "主机名称:",
				   searcher: true,
				   require: true,
				   valueField: "hostId",
				   textField: "hostName"
			   }
			]
		}).appendTo($page.find("div.page-main-searcher"));
		
		$searchBtn = $("<div class='searcher-btn node-info-searcher'><a class='pageui-btn page-query'>查询</a></div>").appendTo($page.find("div.page-main-searcher"));
		
		$grid = new UI.Grid({
			pager: {
				url: Request.get("config/host/qryNode"),
				getParams: function() {
					
					return requestParam;
					
				},
				totalFilter: function(data) {
					
					return data.data.total;
					
				}
			},
			loadFilter: function(data) {
				
			     return data.data.nodeTabItems;
				
			},
			columns: [{
				field: "nodeCode",
				title: "节点编码"
			}, {
				field: "nodeName",
				title: "节点名称"
			}, {
				field: "deployStrategyName",
				title: "部署策略"
			}, {
				field: "isMonitorNode",
				title: "是否监控节点",
				formatter: function(val) {
					
					return (val && val === 'Y') ? "是" : "否";
					
				}
			}, {
				field: "createDate",
				title: "创建日期"
			}, {
				field: "remark",
				title: "备注"
			}, {
				field: "operation",
				title: "操作",
				formatter: function(val, row) {
					
					return [
					    "<a class='pageui-grid-icon icon-edit' name='edit' title='修改'></a>",
					    "<a class='pageui-grid-icon icon-view' name='view' title='查看节点用户'></a>",
					    "<a class='pageui-grid-icon icon-remove' name='remove' title='删除'></a>",
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
			    level: 3,
			    treeNodeId: data.hostId
		    };
			
			$grid.onRequest(true);
			
		});
		
		/*
		 * 新增
		 */
		$page.on("click", "div.page-main-panel-bar>a", function() {
			
			var data = $form.getData();
				
		    if (!data) return;
			
			require(["service/config/host/node-setting"], function(obj) {
				
				obj.create({
					hostId: $form.getItem("hostId").getValue(),
					confirm: function() {
						
						$grid.onRequest(true);
							
					}
				});
				
			});
			
		});
		
		/*
		 * 修改，删除
		 */
		$grid.on("click", "a.pageui-grid-icon", function() {

			var $this = $(this),
			    row = $grid.getRowData($this.closest("tr").index());
		
		    switch ($this.attr("name")) {
		    
			       case "edit":
					
					require(["service/config/host/node-setting"], function(obj) {
						
						obj.create({
							bean: row,
							hostId: $form.getItem("hostId").getValue(),
							confirm: function() {
								
								$grid.onRequest(true);
									
							}
						});
						
					});
					
					break;
					
			       case "view":
						
						require(["service/config/host/node-user"], function(obj) {
							
							obj.create({
								nodeId: row.nodeId
							});
							
						});
						
						break;
					
			       case "remove":
						
						UI.confirm({
							text: "确认是否删除该节点",
							confirm: function() {
								
								$.ajax({
									url: Request.get("config/host/delNode"),
									data: {
										level: 3,
										recId: row.nodeId,
										treeNodeId: $form.getItem("hostId").getValue()
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