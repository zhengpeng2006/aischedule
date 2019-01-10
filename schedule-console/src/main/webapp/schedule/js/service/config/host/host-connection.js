define(["core", "grid", "dialog"], function() {
	                                         
	var $dialog,
	    $grid;
	
	/*
	 * 创建页面对象
	 */
	function create(config) {
		
		var hostId = config.hostId;
		
		var $dialog = new UI.Dialog({
			title: "主机连接方式",
			width: 700,
			height: 520,
			afterShow: function() {

				$grid = new UI.Grid({
					pager: {
						url: Request.get("config/host/qryConModeInfo"),
						getParams: function() {
							
							return {
								hostId: hostId
							};
							
						},
						totalFilter: function(data) {
							
							return data.data.total;
							
						}
					},
					loadFilter: function(data) {
						
					     return data.data.conTabItems;
						
					},
					columns: [{
						field: "conPort",
						title: "连接端口"
					}, {
						field: "conType",
						title: "连接类型"
					}, {
						field: "createDate",
						title: "创建日期"
					}, {
						field: "remarks",
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
				}).appendTo(this.dom.find("div.host-connection"));
				
				$grid.onRequest(true);
				
				$grid.on("click", "a.pageui-grid-icon", function() {

					var $this = $(this),
					    row = $grid.getRowData($this.closest("tr").index());
				
				    if ($this.attr("name") === 'remove') {
								
						UI.confirm({
							text: "确认是否删除该连接方式",
							confirm: function() {
								
								$.ajax({
									url: Request.get("config/host/delConMode"),
									data: {
										conId: row.conId,
										hostId: hostId
									},
									success: function() {
										
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
					
                   require(["service/config/host/host-connection-setting"], function(obj) {
						
						obj.create({
							hostId: hostId,
							confirm: function() {
								
								$grid.onRequest(true);
									
							}
						});
						
					});

				}
			}]
		})
          .html("<div class='host-connection'></div>")
		  .pop(1);
		
		return $dialog;
		
	}
	
	return {
		create: create
	};
	
});