define(["core", "grid", "dialog"], function() {
	                                         
	var $dialog,
	    $grid;
	
	/*
	 * 创建页面对象
	 */
	function create(config) {
		
		var nodeId = config.nodeId,
		    userMap = {};
		
		$.ajax({
			url: Request.get("config/user/loadUsersInfo"),
			async: false,
			success: function(data) {
	        	
			    if ( data.data && data.data.usersTabItems) {
			    	
			    	var usersTabItems = data.data.usersTabItems;
			    	
			    	for (var i = 0; i < usersTabItems.length; i++) {
			    		
			    		userMap[usersTabItems[i].userId] = usersTabItems[i].userCode;
			    		
			    	}
			    	
			    }
				
			}
		});
		
		var $dialog = new UI.Dialog({
			title: "查看节点用户",
			width: 700,
			height: 480,
			afterShow: function() {

				$grid = new UI.Grid({
					pager: {
						url: Request.get("config/host/qryNodeUserInfo"),
						getParams: function() {
							
							return {
								nodeId: nodeId
							};
							
						}
					},
					loadFilter: function(data) {
						
					     return data.data.nodeUserTabItems;
						
					},
					columns: [{
						field: "userId",
						title: "用户编码",
						formatter: function(val) {
							
							return userMap[val];
							
						}
					}, {
						field: "remark",
						title: "备注"
					}, {
						field: "createDate",
						title: "创建日期"
					}, {
						field: "modifyDate",
						title: "修改日期"
					}, {
						field: "operation",
						title: "操作",
						formatter: function(val, row) {
							
							return [
							    "<a class='pageui-grid-icon icon-remove' name='remove' title='删除'></a>",
							].join("");
							
						}
					}]
				}).appendTo(this.dom.find("div.node-user"));
				
				$grid.onRequest(true);
				
				$grid.on("click", "a.pageui-grid-icon", function() {

					var $this = $(this),
					    row = $grid.getRowData($this.closest("tr").index());
					
					if ($this.attr("name") === 'remove') {
						
						UI.confirm({
							text: "确认是否删除该用户",
							confirm: function() {
								
								$.ajax({
									url: Request.get("config/host/delNodeUser"),
									data: {
										nodeUserId: row.nodeUserId,
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
				
			},
			btns: [{
				text: "新增",
				handler: function() {
					
                   require(["service/config/host/node-user-setting"], function(obj) {
						
						obj.create({
							nodeId: nodeId,
							confirm: function() {
								
								$grid.onRequest(true);
									
							}
						});
						
					});

				}
			}]
		})
          .html("<div class='node-user'></div>")
		  .pop(1);
		
		return $dialog;
		
	}
	
	return {
		create: create
	};
	
});