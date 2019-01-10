define(["core", "form", "dialog"], function() {
	                                         
	var $dialog,
	    $form;
	
	/*
	 * 创建页面对象
	 */
	function create(config) {

		var $dialog = new UI.Dialog({
			title: "新增节点用户",
			width: 420,
			height: 200,
			afterShow: function() {

				$form = new UI.Form({
					columns: [{
						   type: "ComboBox",
						   name: "userId",
						   prefix: "用户编码:",
						   require: true,  
						   valueField: "userId",
						   textField: "userCode",
						   url: Request.get("config/user/loadUsersInfo"),
						   loadFilter: function(data) {
							   
							   return data.data.usersTabItems;
							   
						   },
					   }, {
						   type: "TextInput",
						   name: "remark",
						   prefix: "备注:"
					   }
					]
				}).appendTo(this.dom.find("div.node-user-setting"));
				
			},
			btns: [{
				text: "确定",
				handler: function() {
					
					var _this = this, 
					    nodeUserDetail = [],
					    data = $form.getData();
					
					if (!data) return;
					
					nodeUserDetail.push(data);
					
					nodeUserDetail.push({});
					
					$.ajax({
						url: Request.get("config/host/saveNodeUser"),
						data: {
							nodeUserDetail: JSON.stringify(nodeUserDetail),
							nodeId: config.nodeId
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
          .html("<div class='node-user-setting'></div>")
		  .pop(1);
		
		return $dialog;
		
	}
	
	return {
		create: create
	};
	
});