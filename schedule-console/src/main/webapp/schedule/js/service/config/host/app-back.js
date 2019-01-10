define(["core", "grid", "dialog"], function() {
	                                         
	var $dialog,
	    $grid;
	
	/*
	 * 创建页面对象
	 */
	function create(config) {
		
		var serverCode = config.serverCode;
		
		var $dialog = new UI.Dialog({
			title: "查看备用进程",
			width: 700,
			height: 520,
			afterShow: function() {

				$grid = new UI.Grid({
					pager: {
						url: Request.get("config/host/qryBackupServerCode"),
						getParams: function() {
							
							return {
								serverCode: serverCode 
							};
							
						}
					},
					loadFilter: function(data) {
						
						 if (!$.isEmptyObject(data.data)) {
							 
							 var arr = [];
							 
							 arr.push(data.data);
							 
						     return arr;
							 
						 }
					},
					columns: [{
						field: "backupServerName",
						title: "备用进程名"
					}, {
						field: "hostName",
						title: "备用进程所在主机"
					}, {
						field: "operation",
						title: "操作",
						formatter: function(val, row) {
							
							return [
							    "<a class='pageui-grid-icon icon-remove' name='remove' title='删除'></a>",
							].join("");
							
						}
					}]
				}).appendTo(this.dom.find("div.app-back"));
				
				$grid.onRequest(true);
				
				$grid.on("click", "a.pageui-grid-icon", function() {

					var $this = $(this),
					    row = $grid.getRowData($this.closest("tr").index());
				
				    if ($this.attr("name") === 'remove') {
				    
						UI.confirm({
							text: "确认是否删除该备用进程",
							confirm: function() {
								
								$.ajax({
									url: Request.get("config/host/deleteBackupServerCode"),
									data: {
										serverCode: serverCode
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
				text: "选择备用进程",
				handler: function() {
					
                   require(["service/config/host/app-back-setting"], function(obj) {
						
						obj.create({
							serverCode: serverCode,
							confirm: function() {
								
								$grid.onRequest(true);
									
							}
						});
						
					});

				}
			}]
		})
          .html("<div class='app-back'></div>")
		  .pop(1);
		
		return $dialog;
		
	}
	
	return {
		create: create
	};
	
});