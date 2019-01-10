define(["core", "form", "dialog"], function() {
	                                         
	var $dialog,
	    $form;
	
	/*
	 * 创建页面对象
	 */
	function create(config) {

		var $dialog = new UI.Dialog({
			title: "新增主备机",
			width: 420,
			height: 150,
			afterShow: function() {

				$form = new UI.Form({
					columns: [{
						   type: "ComboBox",
						   name: "hostId",
						   prefix: "备机:",
						   require: true,
						   searcher: true,
						   valueField: "VALUE",
						   textField: "TEXT",
						   url: Request.get("config/task/qryAllHostInfo"),
						   loadFilter: function(data) {

							   if (data && data.data && data.data.hostList.length) {
								   
								   var hostList = data.data.hostList;
								   
								   for(var i = 0; i < hostList.length; i++) {
									   
									   if (+hostList[i].VALUE === config.hostId) {
										   
										   hostList.splice(i,1);
										   
									   }
									   
								   }
								   
							   }
							   return data.data.hostList;
							   
						   }
					   }
					]
				}).appendTo(this.dom.find("div.host-slave-setting"));
				
			},
			btns: [{
				text: "确定",
				handler: function() {
					
					var _this = this, 
					    slaveDetail = [],
					    data = $form.getData();
					
					if (!data) return;
					
					slaveDetail.push(data);
					
					slaveDetail.push({});
					
					$.ajax({
						url: Request.get("config/host/saveMasterSlaveInfo"),
						data: {
							slaveDetail: JSON.stringify(slaveDetail),
							masterHostId: config.hostId
						},
					    success: function(data) {
					    		
						    config.confirm();
					    		
					    	_this.pop(0);
					    	
					    }
						
					});
					
				}
			}, {
				text: "取消",
				handler: function() {

					this.pop(0);
					
				}
			}]
		})
          .html("<div class='host-slave-setting'></div>")
		  .pop(1);
		
		return $dialog;
		
	}
	
	return {
		create: create
	};
	
});