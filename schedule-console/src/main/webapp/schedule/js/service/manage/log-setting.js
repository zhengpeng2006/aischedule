define(["core", "grid", "dialog"], function() {
	                                         
	var $dialog,
	    $grid;
	
	/*
	 * 创建页面对象
	 */
	function create(config) {
		
		var BEAN = config.bean;
		
		var $dialog = new UI.Dialog({
			title: "查看详情",
			width: 900,
			height: 480,
			afterShow: function() {

				$grid = new UI.Grid({
					pager: {
						url: Request.get("manage/qryLogDetail"),
						getParams: function() {
							
							return {
								taskCode: BEAN.taskCode,
								jobId: BEAN.jobId
							};
							
						},
						totalFilter: function(data) {
							
							return data.data.total;
							
						}
					},
					loadFilter: function(data) {
						
					     return data.data.detailsItems;
						
					},
					columns: [{
						field: "logType",
						title: "记录类型"
					}, {
						field: "taskItem",
						title: "拆分项"
					}, {
						field: "exMsg",
						title: "异常信息"
					}, {
						field: "opInfo",
						title: "操作信息"
					}, {
						field: "operator",
						title: "操作员"
					},  {
						field: "createDate",
						title: "创建时间"
					}]
				}).appendTo(this.dom.find("div.log-setting"));
				
				$grid.onRequest(true);
				
			}
		})
          .html("<div class='log-setting'></div>")
		  .pop(1);
		
		return $dialog;
		
	}
	
	return {
		create: create
	};
	
});