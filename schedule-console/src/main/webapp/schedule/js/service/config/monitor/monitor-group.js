define(["core", "grid"], function() {
	                                         
	var $grid;

	/*
	 * 初始化文档流
	 */
	function initDom($page) {
		
		$page.addClass("monitor-group-info").append([
  	    	"<div class='page-main-title'>监控任务分组配置<i class='icon-back'></i></div>",
	    	"<div class='page-main-panel'>",
	    	    "<div class='page-main-panel-bar'>",
		   	       "<a name='add'>新增</a>",
		   	    "</div>",
	    	"</div>",
		].join(""));
			
		$grid = new UI.Grid({
			pager: {
				url: Request.get("config/monitor/qryMonitorGroup"),
				getParams: function() {
					
					return {
						level: 1,
						treeNodeId: -1
					};
					
				},
				totalFilter: function(data) {
					
					return data.data.total;
					
				}
			},
			loadFilter: function(data) {
				
			     return data.data.groupInfosItems;
				
			},
			columns: [{
				field: "groupName",
				title: "分组名称"
			}, {
				field: "groupCode",
				title: "分组编码"
			}, {
				field: "groupDesc",
				title: "分组描述"
			}, {
				field: "layer",
				title: "层"
			}, {
				field: "sortId",
				title: "顺序"
			}, {
				field: "remark",
				title: "备注"
			}, {
				field: "operation",
				title: "操作",
				formatter: function(val, row) {
					
					return [
					    "<a class='pageui-grid-icon icon-edit' name='edit' title='修改'></a>",
					    "<a class='pageui-grid-icon icon-remove' name='remove' title='删除'></a>",
					].join("");
					
				}
			}]
		}).appendTo($page.find("div.page-main-panel"));
		
		$grid.onRequest(true);
		
	}
	
	/*
	 * 初始化事件
	 */
	function initEvent($page) {
		
		/*
		 * 返回
		 */
		$page.on("click", "div.page-main-title>i", function() {
			
			 require(["service/config/monitor/monitor"], function(obj) {
	    		 
	    		    CORE.main.empty();
	    		    
	    			obj.create($("<div></div>").appendTo(CORE.main));
					
			 });
			
		});
		
		/*
		 * 新增
		 */
		$page.on("click", "div.page-main-panel-bar>a", function() {
			
			require(["service/config/monitor/monitor-group-setting"], function(obj) {
				
				obj.create({
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
					
					require(["service/config/monitor/monitor-group-setting"], function(obj) {
						
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
							text: "确认是否删除该分组",
							confirm: function() {
								
								$.ajax({
									url: Request.get("config/monitor/delMonitorGroup"),
									data: {
										groupId: row.groupId
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