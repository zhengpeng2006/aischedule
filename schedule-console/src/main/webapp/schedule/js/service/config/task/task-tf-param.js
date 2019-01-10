define(["core", "grid", "dialog"], function() {
	                                         
	var $dialog,
	    $grid;
	
	/*
	 * 创建页面对象
	 */
	function create(config) {
		
		var $dialog = new UI.Dialog({
			title: "映射字段配置",
			width: 700,
			height: 520,
			afterShow: function() {

				$grid = new UI.Grid({
					pager: {
						url: Request.get("config/task/qryCols"),
						getParams: function() {
							
							return {
								cfgTfDtlId: config.cfgTfDtlId,
								taskCode: config.taskCode
							};
							
						},
						totalFilter: function(data) {
							
							return data.data.total;
							
						}
					},
					loadFilter: function(data) {
						
					     return data.data.colRelsItems;
						
					},
					columns: [{
						field: "srcColumnName",
						title: "源表字段"
					}, {
						field: "tfColumnName",
						title: "目标表字段"
					}, {
						field: "remark",
						title: "备注"
					}, {
						field: "operation",
						title: "操作",
						formatter: function(val, row) {
							
							return [
							    "<a class='pageui-grid-icon icon-remove' name='remove' title='删除'></a>",
							].join("");
							
						}
					}]
				}).appendTo(this.dom.find("div.task-tf-param"));
				
				$grid.onRequest(true);
				
				$grid.on("click", "a.pageui-grid-icon", function() {

					var $this = $(this),
					    row = $grid.getRowData($this.closest("tr").index());
				
				    if ($this.attr("name") === 'remove') {
				    
						UI.confirm({
							text: "确认是否删除该字段",
							confirm: function() {
								
								$.ajax({
									url: Request.get("config/task/deleteCols"),
									data: {
										cfgTfDtlId: config.cfgTfDtlId,
										taskCode: config.taskCode,
										ids: row.mappingId
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
					
                   require(["service/config/task/task-tf-param-setting"], function(obj) {
						
						obj.create({
							cfgTfDtlId: config.cfgTfDtlId,
							taskCode: config.taskCode,
							confirm: function() {
								
								$grid.onRequest(true);
									
							}
						});
						
					});

				}
			}]
		})
          .html("<div class='task-tf-param'></div>")
		  .pop(1);
		
		return $dialog;
		
	}
	
	return {
		create: create
	};
	
});