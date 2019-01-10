define(["core", "grid", "form"], function() {
	                                         
	var $grid;

	/*
	 * 初始化文档流
	 */
	function initDom($page) {
		
		$page.addClass("template-info").append([
  	    	"<div class='page-main-title'>启停模板配置<i class='icon-back'></i></div>",
	    	"<div class='page-main-panel'>",
	    	    "<div class='page-main-panel-bar'>",
	    	       "<a>新增</a>",
	    	    "</div>",
	    	"</div>",
		].join(""));
			
		$grid = new UI.Grid({
			pager: {
				url: Request.get("config/strategy/qryTemplate"),
				totalFilter: function(data) {
					
					return data.data.total;
					
				}
			},
			loadFilter: function(data) {
				
			     return data.data.templatesItems;
				
			},
			columns: [{
				field: "templateName",
				title: "模板名称"
			}, {
				field: "contents",
				title: "模板内容",
                formatter: function(val) {
					
					if (val) {
						
		                return [
							    "<span>",
							    	"<a class='pageui-grid-btn'>点击获取</a>",
							    	"<div class='popup-content Non-Global' style='line-height: 15px;'>",
							    		"<div class='pageui-scrollbox'><pre>", val, "</pre></div>",
							    	"</div>",
					    		"</span>"
							].join("");
						
					}
					
				}
			}, {
				field: "createTime",
				title: "创建时间"
			}, {
				field: "modifyTime",
				title: "修改时间"
			}, {
				field: "operatorId",
				title: "操作员"
			}, {
				field: "remarks",
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
			
			 require(["service/config/strategy/strategy"], function(obj) {
	    		 
	    		    CORE.main.empty();
	    		    
	    			obj.create($("<div></div>").appendTo(CORE.main));
					
			 });
			
		});
		
		/*
		 * 新增
		 */
		$page.on("click", "div.page-main-panel-bar>a", function() {
			
			require(["service/config/strategy/template-setting"], function(obj) {
				
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
					
					require(["service/config/strategy/template-setting"], function(obj) {
						
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
							text: "确认是否删除该模板",
							confirm: function() {
								
								$.ajax({
									url: Request.get("config/strategy/deleteTemplate"),
									data: {
										templateId: row.templateId
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
		
		/*
		 * 点击获取
		 */
		$grid.on("click", "a.pageui-grid-btn", function() {
			
			var $div = $(this).next();
			
			UI.showPop($div);
			
			UI($div.find(".pageui-scrollbox")).method("resize");
			
			return false;
			
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