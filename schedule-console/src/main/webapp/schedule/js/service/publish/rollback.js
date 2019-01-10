define(["core", "grid", "dialog"], function() {
	                                         
	var $dialog,
	    $grid;
	
	/*
	 * 创建页面对象
	 */
	function create(config) {
		
		var $dialog = new UI.Dialog({
			title: "回滚的版本",
			width: 900,
			height: 480,
			afterShow: function() {

				$grid = new UI.Grid({
					pager: {
						url: Request.get("publish/showHisVersion"),
						getParams: function() {
							
							return {
								stretagyId: config.stretagyId
							};
							
						},
						totalFilter: function(data) {
							
							return data.data.total;
							
						}
					},
					loadFilter: function(data) {
						
					     return data.data.hisVersionTableItems;
						
					},
					columns: [{
						field: "versionId",
						title: "版本号"
					}, {
						field: "parentVersionId",
						title: "上个版本号"
					}, {
						field: "packagePath",
						title: "存放路径"
					}, {
						field: "fileList",
						title: "修改文件列表"
					}, {
						field: "resolvedProblems",
						title: "解决问题"
					}, {
						field: "contacts",
						title: "版本联系人"
					}, {
						field: "operateType",
						title: "操作类型"
					}, {
						field: "externalVersionId",
						title: "关联的外部版本号"
					}, {
						field: "operation",
						title: "操作",
						formatter: function(val, row) {
							
							return [
							    "<span><a class='pageui-grid-btn'>回滚</a></span>"
							].join("");
							
						}
					}]
				}).appendTo(this.dom.find("div.rollback"));
				
				$grid.onRequest(true);
				
				$grid.on("click", "a.pageui-grid-btn", function(){
					
					 var $this = $(this),
				         row = $grid.getRowData($this.closest("tr").index());
					
					 config.confirm(row.versionId);
						
					 $dialog.pop(0);
					
				});
				
			}
		})
          .html("<div class='rollback'></div>")
		  .pop(1);
		
		return $dialog;
		
	}
	
	return {
		create: create
	};
	
});