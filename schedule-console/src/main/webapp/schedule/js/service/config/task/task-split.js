define(["core", "grid", "dialog"], function() {
	                                         
	var $dialog,
	    $grid;
	
	/*
	 * 创建页面对象
	 */
	function create(config) {
		
		var taskCode = config.taskCode,
		    hostMap = CORE.getAllHost();
		
		var $dialog = new UI.Dialog({
			title: "查询分片配置",
			width: 700,
			height: 480,
			afterShow: function() {

				$grid = new UI.Grid({
					pager: {
						url: Request.get("config/task/qryTaskSplit"),
						getParams: function() {
							
							return {
								taskCode: taskCode,
							};
							
						},
						totalFilter: function(data) {
							
							return data.data.splitInfosTotal;
							
						}
					},
					loadFilter: function(data) {
						
					     return data.data.splitInfosItems;
						
					},
					columns: [{
						field: "taskItem",
						title: "任务拆分项"
					}, {
						field: "host",
						title: "主机",
						formatter: function(val) {
							
							return hostMap[val];
							
						}
					}, {
						field: "assignServer",
						title: "指定任务处理器编码"
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
				}).appendTo(this.dom.find("div.task-split"));
				
				$grid.onRequest(true);
				
				$grid.on("click", "a.pageui-grid-icon", function() {

					var $this = $(this),
					    row = $grid.getRowData($this.closest("tr").index());
				
					 switch ($this.attr("name")) {
			  		    
				       case "edit":
				    	   
				    	   require(["service/config/task/task-split-setting"], function(obj) {
								
								obj.create({
									bean: row,
									confirm: function() {
										
										$grid.onRequest(true);
											
									}
								});
								
						  });
				    	   
				    	  break;
				    	   
				       case "remove":
				    	   
				    	   var dataArray = [];
				    	   
				    	   dataArray.push({
								taskCode: row.taskCode,
								taskItem: row.taskItem,
								serverCode: ""
						   });
				    	   
				    	   UI.confirm({
								text: "确认是否删除该分片配置",
								confirm: function() {
									
									$.ajax({
										url: Request.get("config/task/saveTaskSplit"),
										data: {
											dataArray: JSON.stringify(dataArray)
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
		})
          .html("<div class='task-split'></div>")
		  .pop(1);
		
		return $dialog;
		
	}
	
	return {
		create: create
	};
	
});