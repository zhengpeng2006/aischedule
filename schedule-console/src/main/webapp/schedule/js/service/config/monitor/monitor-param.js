define(["core", "grid", "dialog"], function() {
	                                         
	var $dialog,
	    $grid;
	
	/*
	 * 创建页面对象
	 */
	function create(config) {
		
		var infoId = config.infoId;
		
		var $dialog = new UI.Dialog({
			title: "查看参数",
			width: 700,
			height: 520,
			afterShow: function() {

				$grid = new UI.Grid({
					pager: {
						url: Request.get("config/monitor/qryMonitorParam"),
						getParams: function() {
							
							return {
								infoId: infoId,
								registeType: 10
							};
							
						},
						totalFilter: function(data) {
							
							return data.data.total;
							
						}
					},
					loadFilter: function(data) {
						
					     return data.data.paramValuesItems;
						
					},
					columns: [{
						field: "paramCode",
						title: "参数"
					}, {
						field: "paramValue",
						title: "参数值"
					}, {
						field: "sortId",
						title: "顺序"
					}, {
						field: "paramDesc",
						title: "参数描述"
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
				}).appendTo(this.dom.find("div.monitor-param"));
				
				$grid.onRequest(true);
				
				$grid.on("click", "a.pageui-grid-icon", function() {

					var $this = $(this),
					    row = $grid.getRowData($this.closest("tr").index());
				
				    switch ($this.attr("name")) {
				    
					       case "edit":

								require(["service/config/monitor/monitor-param-setting"], function(obj) {
									
									obj.create({
										bean: row,
										infoId: infoId,
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
											url: Request.get("config/monitor/delMonitorParam"),
											data: {
												vId: row.VId
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
					
                   require(["service/config/monitor/monitor-param-setting"], function(obj) {
						
						obj.create({
							infoId: infoId,
							confirm: function() {
								
								$grid.onRequest(true);
									
							}
						});
						
					});

				}
			}]
		})
          .html("<div class='monitor-param'></div>")
		  .pop(1);
		
		return $dialog;
		
	}
	
	return {
		create: create
	};
	
});