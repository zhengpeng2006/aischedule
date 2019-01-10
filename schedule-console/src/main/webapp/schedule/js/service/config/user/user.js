define(["core", "grid", "form"], function() {
	                                         
	var $grid,
        $form,
        $searchBtn,
        requestParam = {};

	/*
	 * 初始化文档流
	 */
	function initDom($page) {
		
		$page.addClass("user-info").append([
  	    	"<div class='page-main-title'>用户配置</div>",
	    	"<div class='page-main-searcher'></div>",
	    	"<div class='page-main-panel'>",
	    	    "<div class='page-main-panel-bar'>",
	    	       "<a>新增</a>",
	    	    "</div>",
	    	"</div>",
		].join(""));
			
		$form = new UI.Form({
			cls: "common-pageui-form",
			columns: [
			   {
				   type: "TextInput",
				   name: "userCode",
				   prefix: "用户编码:"
			   }
			]
		}).appendTo($page.find("div.page-main-searcher"));
		
		$searchBtn = $("<div class='searcher-btn'><a class='pageui-btn page-query'>查询</a></div>").appendTo($page.find("div.page-main-searcher"));
		
		$grid = new UI.Grid({
			pager: {
				url: Request.get("config/user/loadUsersInfo"),
				getParams: function() {
					
					return requestParam;
					
				},
				totalFilter: function(data) {
					
					return data.data.total;
					
				}
			},
			loadFilter: function(data) {
				
			     return data.data.usersTabItems;
				
			},
			columns: [{
				field: "userName",
				title: "用户名称"
			}, {
				field: "userCode",
				title: "用户编码"
			}, {
				field: "createDate",
				title: "创建日期"
			}, {
				field: "modifyDate",
				title: "修改日期"
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
		
	}
	
	/*
	 * 初始化事件
	 */
	function initEvent($page) {
		
		/*
		 * 查询
		 */
		$searchBtn.on("click", "a", function() {
			
			var qryForm = [
						     {
						    	 userCode: $form.getItem("userCode").getValue()
						     },
						     {
						    	 userCode: ""
						     }
						  ];
			
			requestParam = {
				 qryForm: JSON.stringify(qryForm)
		    };
			
			$grid.onRequest(true);
			
		});
		
		/*
		 * 新增
		 */
		$page.on("click", "div.page-main-panel-bar>a", function() {
			
			require(["service/config/user/user-setting"], function(obj) {
				
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
					
					require(["service/config/user/user-setting"], function(obj) {
						
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
							text: "确认是否删除该用户",
							confirm: function() {
								
								$.ajax({
									url: Request.get("config/user/delUser"),
									data: {
										userId: row.userId
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