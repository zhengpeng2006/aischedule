define(["core", "grid", "dialog"], function() {
	                                         
	var $dialog,
	    $grid;
	
	/*
	 * 创建页面对象
	 */
	function create(config) {
		
		var $dialog = new UI.Dialog({
			title: "查看详情",
			width: 700,
			height: 480,
			afterShow: function() {

				$grid = new UI.Grid({
					pager: {
						url: Request.get("publish/showTaskDetail"),
						getParams: function() {
							
							return {
								appCode: config.serverCode
							};
							
						},
						totalFilter: function(data) {
							
							return data.data.total;
							
						}
					},
					loadFilter: function(data) {
						
					     return data.data.busiDetailItems;
						
					},
					columns: [{
						field: "groupCode",
						title: "分组编码"
					}, {
						field: "groupName",
						title: "分组名称"
					}, {
						field: "taskCode",
						title: "任务编码"
					}, {
						field: "taskName",
						title: "任务名称"
					}, {
						field: "appCode",
						title: "应用编码"
					}, {
						field: "version",
						title: "版本号"
					}, {
						field: "runState",
						title: "任务运行状态"
					}, {
						field: "splitRegion",
						title: "是否按地区分片"
					}]
				}).appendTo(this.dom.find("div.process-setting"));
				
				$grid.onRequest(true);
				
			}
		})
          .html("<div class='process-setting'></div>")
		  .pop(1);
		
		return $dialog;
		
	}
	
	return {
		create: create
	};
	
});