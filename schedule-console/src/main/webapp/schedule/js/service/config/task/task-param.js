define(["core", "grid", "dialog"], function() {
	                                         
	var $dialog,
	    $grid;
	
	/*
	 * 创建页面对象
	 */
	function create(config) {
		
		var taskCode = config.taskCode;
		
		var $dialog = new UI.Dialog({
			title: "查看参数",
			width: 700,
			height: 500,
			afterShow: function() {

				$grid = new UI.Grid({
					pager: {
						url: Request.get("config/task/qryTaskParam"),
						getParams: function() {
							
							return {
								taskCode: taskCode,
							};
							
						},
						totalFilter: function(data) {
							
							return data.data.total;
							
						}
					},
					loadFilter: function(data) {
						
					     return data.data.taskParamsItems;
						
					},
					columns: [{
						field: "paramKey",
						title: "参数"
					}, {
						field: "paramValue",
						title: "参数值"
					}, {
						field: "paramDesc",
						title: "参数描述"
					}, {
						field: "operation",
						title: "操作",
						formatter: function(val, row) {
							
							return [
							    "<a class='pageui-grid-icon icon-remove' name='remove' title='删除'></a>",
							].join("");
							
						}
					}]
				}).appendTo(this.dom.find("div.task-param"));
				
				$grid.onRequest(true);
				
				$grid.on("click", "a.pageui-grid-icon", function() {

					var $this = $(this),
					    row = $grid.getRowData($this.closest("tr").index());
				
				    if ($this.attr("name") === 'remove') {
				    
						UI.confirm({
							text: "确认是否删除该参数",
							confirm: function() {
								
								$.ajax({
									url: Request.get("config/task/delTaskParam"),
									data: {
										taskCode: taskCode,
										paramKey: row.paramKey
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
					
                   require(["service/config/task/task-param-setting"], function(obj) {
						
						obj.create({
							taskCode: taskCode,
							confirm: function() {
								
								$grid.onRequest(true);
									
							}
						});
						
					});

				}
			}]
		})
          .html("<div class='task-param'></div>")
		  .pop(1);
		
		return $dialog;
		
	}
	
	return {
		create: create
	};
	
});