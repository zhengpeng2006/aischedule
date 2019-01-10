define(["core", "grid", "form"], function() {
	                                         
	var $grid,
	$form,
	$searchBtn,
	requestParam;

	/*
	 * 初始化文档流
	 */
	function initDom($page) {
		
		$page.addClass("task-group-info").append([
  	    	"<div class='page-main-title'>任务分组配置<i class='icon-back'></i></div>",
  	    	"<div class='page-main-searcher'></div>",
	    	"<div class='page-main-panel'>",
	    	    "<div class='page-main-panel-bar'>",
		   	       "<a name='add'>新增</a>",
		   	    "</div>",
	    	"</div>",
		].join(""));
		
		$form = new UI.Form({
			itemWidth: 170,
			columns: [
			   {
				   type: "TextInput",
				   name: "groupName",
				   prefix: "分组名称:"
			   }, {
				   type: "TextInput",
				   name: "groupCode",
				   prefix: "分组编码:"
			   }
			]
		}).appendTo($page.find("div.page-main-searcher"));
		
		$searchBtn = $("<div class='searcher-btn group-info-searcher'><a class='pageui-btn page-query'>查询</a></div>").appendTo($page.find("div.page-main-searcher"));
			
		$grid = new UI.Grid({
			pager: {
				url: Request.get("config/task/qryTaskGroup"),
				getParams: function() {
					
					return requestParam;
					
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
				field: "createTime",
				title: "创建时间"
			}, {
				field: "operation",
				title: "操作",
				formatter: function(val, row) {
					
					return [
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
		 * 查询
		 */
		$searchBtn.on("click", "a", function() {
			
			var data = $form.getData();
			
			requestParam = {
					groupName: data.groupName,
					groupCode: data.groupCode
		    };
			
			$grid.onRequest(true);
			
		});
		
		/*
		 * 返回
		 */
		$page.on("click", "div.page-main-title>i", function() {
			
			 require(["service/config/task/task"], function(obj) {
	    		 
	    		    CORE.main.empty();
	    		    
	    			obj.create($("<div></div>").appendTo(CORE.main));
					
			 });
			
		});
		
		/*
		 * 新增
		 */
		$page.on("click", "div.page-main-panel-bar>a", function() {
			
			require(["service/config/task/task-group-setting"], function(obj) {
				
				obj.create({
					confirm: function() {
						
						$grid.onRequest(true);
							
					}
				});
				
			});
			
		});
		
		/*
		 * 删除
		 */
		$grid.on("click", "a.pageui-grid-icon", function() {

			var $this = $(this),
			    row = $grid.getRowData($this.closest("tr").index());
		
		    if ($this.attr("name") === 'remove') {
    
				UI.confirm({
					text: "确认是否删除该分组",
					confirm: function() {
						
						$.ajax({
							url: Request.get("config/task/delTaskGroup"),
							data: {
								taskGroupCode: row.groupCode
							},
							success: function(data) {
						    		
								UI.alert("删除成功!");
								
								$grid.onRequest(true);
						    		
							}
						});
						
					}
				});
						
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