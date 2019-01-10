define(["core", "grid", "dialog"], function() {
	                                         
	var $dialog,
	    $grid;
	
	/*
	 * 创建页面对象
	 */
	function create(config) {
		
		var hostId = config.hostId;
		
		var $dialog = new UI.Dialog({
			title: "主备机",
			width: 700,
			height: 480,
			afterShow: function() {

				$grid = new UI.Grid({
					pager: {
						url: Request.get("config/host/qryMasterSlaveInfo"),
						getParams: function() {
							
							return {
								hostId: hostId
							};
							
						}
					},
					loadFilter: function(data) {
						
					     return data.data.slaveTabItems;
						
					},
					columns: [{
						field: "hostCode",
						title: "主机编码"
					}, {
						field: "hostName",
						title: "主机名称"
					}, {
						field: "hostIp",
						title: "主机IP"
					}, {
						field: "hostDesc",
						title: "主机描述"
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
				}).appendTo(this.dom.find("div.host-slave"));
				
				$grid.onRequest(true);
				
				$grid.on("click", "a.pageui-grid-icon", function() {

					var $this = $(this),
					    row = $grid.getRowData($this.closest("tr").index());
					
					if ($this.attr("name") === 'remove') {
						
						UI.confirm({
							text: "确认是否删除该主备机",
							confirm: function() {
								
								$.ajax({
									url: Request.get("config/host/delSlave"),
									data: {
										masterHostId: hostId,
										slaveHostId: row.hostId
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
					
                   require(["service/config/host/host-slave-setting"], function(obj) {
						
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
          .html("<div class='host-slave'></div>")
		  .pop(1);
		
		return $dialog;
		
	}
	
	return {
		create: create
	};
	
});