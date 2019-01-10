define(["core", "grid", "dialog"], function() {
	                                         
	var $dialog,
	    $grid,
	    paramTypeMap = {
			"C": "自定义参数",
			"T": "模板参数"
	    };
	
	/*
	 * 创建页面对象
	 */
	function create(config) {
		
		var serverId = config.serverId;
		
		var $dialog = new UI.Dialog({
			title: "查看部署参数",
			width: 700,
			height: 520,
			afterShow: function() {

				$grid = new UI.Grid({
					pager: {
						url: Request.get("config/host/qryParamInfo"),
						getParams: function() {
							
							return {
								serverId: serverId
							};
							
						},
						totalFilter: function(data) {
							
							return data.data.total;
							
						}
					},
					loadFilter: function(data) {
						
					     return data.data.deployAppParamTabItems;
						
					},
					columns: [{
						field: "key",
						title: "参数"
					}, {
						field: "value",
						title: "参数值"
					}, {
						field: "paramType",
						title: "参数类型",
                        formatter: function(val, row) {
							
                        	return paramTypeMap[val];
                        	
						}
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
				}).appendTo(this.dom.find("div.app-param"));
				
				$grid.onRequest(true);
				
				$grid.on("click", "a.pageui-grid-icon", function() {

					var $this = $(this),
					    row = $grid.getRowData($this.closest("tr").index());
				
				    switch ($this.attr("name")) {
				    
					       case "edit":

								require(["service/config/host/app-param-setting"], function(obj) {
									
									obj.create({
										bean: row,
										serverId: serverId,
										confirm: function() {
											
											$grid.onRequest(true);
												
										}
									});
									
								});
							
						   break;
							
					       case "remove":
								
								UI.confirm({
									text: "确认是否删除该参数",
									confirm: function() {
										
										$.ajax({
											url: Request.get("config/host/delParam"),
											data: {
												applicationParamId: row.applicationParamId
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
				
			},
			btns: [{
				text: "新增",
				handler: function() {
					
                   require(["service/config/host/app-param-setting"], function(obj) {
						
						obj.create({
							serverId: serverId,
							confirm: function() {
								
								$grid.onRequest(true);
									
							}
						});
						
					});

				}
			}]
		})
          .html("<div class='app-param'></div>")
		  .pop(1);
		
		return $dialog;
		
	}
	
	return {
		create: create
	};
	
});