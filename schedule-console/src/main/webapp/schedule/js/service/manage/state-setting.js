define(["core", "grid", "dialog"], function() {
	                                         
	var $dialog,
	    $grid;
	
	/*
	 * 创建页面对象
	 */
	function create(config) {
		
		var type = config.type,
		    BEAN = config.bean;
		
		var $dialog = new UI.Dialog({
			title: "查看执行详情",
			width: 900,
			height: 480,
			afterShow: function() {

				$grid = new UI.Grid({
					pager: {
						url: type === '1' ? Request.get("manage/showDetails") : Request.get("manage/showTaskPros"),
						getParams: function() {
							
							return type === '1' ? {
								taskCode: BEAN.taskCode,
								jobId: BEAN.jobId
							} : {
								serverId: BEAN.serverId
							};
							
						},
						totalFilter: function(data) {
							
							return data.data.total;
							
						}
					},
					loadFilter: function(data) {
						
					     return data.data.taskBaseLogsItems;
						
					},
					columns: [{
						field: "taskCode",
						title: "任务编码"
					}, {
						field: "taskItemId",
						title: "拆分项"
					}, {
						field: "itemJobId",
						title: "拆分项流水号"
					}, {
						field: "serverId",
						title: "应用编码"
					}, {
						field: "priority",
						title: "优先级"
					}, ]
				}).appendTo(this.dom.find("div.state-setting"));
				
				$grid.onRequest(true);
				
			}
		})
          .html("<div class='state-setting'></div>")
		  .pop(1);
		
		return $dialog;
		
	}
	
	return {
		create: create
	};
	
});