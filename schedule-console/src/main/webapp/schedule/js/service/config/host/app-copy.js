define(["core", "form", "dialog"], function() {
	                                         
	var $dialog,
	    $form;
	
	/*
	 * 创建页面对象
	 */
	function create(config) {
		
		var $dialog = new UI.Dialog({
			title: "复制应用",
			width: 420,
			height: 300,
			afterShow: function() {

				$form = new UI.Form({
					columns: [{
						   type: "ComboBox",
						   name: "hostGroup",
						   prefix: "主机分组:",
						   require: true,
						   valueField: "groupId",
						   textField: "groupName",
						   url: Request.get("config/host/qryHostGroup"),
						   loadFilter: function(data) {
							   
							   return data.data.groupTabItems;
							   
						   },
						   getUrlParam: function() {
							   
							   return {
								   level: 1,
								   treeNodeId: -1
							   };
							   
						   },
						   onChange: function(data) {
							   
							   $form.getItem("hostId").clear();
							   
							   $form.getItem("nodeId").clear().loadData([]);
							   
							   $form.getItem("serverCode").hide();
							   
							   if (data) {
								   
								   $.ajax({
									   url: Request.get("config/host/qryHost"),
									   data: {
										   level: 2,
										   treeNodeId: data.groupId
									   },
									   success: function(data) {
										   
										   $form.getItem("hostId").loadData(data.data.hostTabItems);
										   
									   }
							
								   });
								   
							   }

						   }
					   }, {
						   type: "ComboBox",
						   name: "hostId",
						   prefix: "主机名称:",
						   require: true,
						   valueField: "hostId",
						   textField: "hostName",
		                   onChange: function(data) {
							   
							   $form.getItem("nodeId").clear();
							   
							   $form.getItem("serverCode").hide();
							   
							   if (data) {
								   
								   $.ajax({
									   url: Request.get("config/host/qryNode"),
									   data: {
										   level: 3,
										   treeNodeId: data.hostId
									   },
									   success: function(data) {
										   
										   $form.getItem("nodeId").loadData(data.data.nodeTabItems);
										   
									   }
							
								   });
								   
							   }

						   }
					   }, {
						   type: "ComboBox",
						   name: "nodeId",
						   prefix: "节点名称:",
						   require: true,
						   valueField: "nodeId",
						   textField: "nodeName",
						   onChange: function(data) {
							   
							   if (data) {
								  
								   $form.getItem("serverCode").show();
								   
							   } 
						   }
					   }, {
						   type: "TextInput",
						   name: "serverCode",
						   prefix: "应用编码:", 
						   require: true,
						   afterRender: function() {
							   
							   this.hide();
							   
						   }
					   }
					]
				}).appendTo(this.dom.find("div.app-copy"));
				
			},
			btns: [{
				text: "复制",
				handler: function() {
					
					var _this = this, 
					    serverDetail = [],
					    data = $form.getData();
					
					if (!data) return;
					
					serverDetail.push({serverCode: data.serverCode});
					
					serverDetail.push({});
					
					$.ajax({
						url: Request.get("config/host/copyServer"),
						data: {
							serverDetail: JSON.stringify(serverDetail),
							serverId: config.serverId,
							treeNodeId: data.nodeId
						},
					    success: function(data) {
					    		
					    	_this.pop(0);
					    	
					    }
						
					});
					
				}
			}]
		})
          .html("<div class='app-copy'></div>")
		  .pop(1);
		
		return $dialog;
		
	}
	
	return {
		create: create
	};
	
});