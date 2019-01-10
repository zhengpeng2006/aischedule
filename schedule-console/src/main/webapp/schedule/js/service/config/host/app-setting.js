define(["core", "form", "dialog"], function() {
	                                         
	var $dialog,
	    $form;
	
	/*
	 * 创建页面对象
	 */
	function create(config) {
		
        var BEAN = config.bean,
            nodeId = config.nodeId;
        
		var $dialog = new UI.Dialog({
			title: BEAN ? "修改主机应用" : "新增主机应用",
			width: 420,
			height: 400,
			afterShow: function() {

				$form = new UI.Form({
					data: BEAN ? BEAN : "",
					columns: [{
						   type: "TextInput",
						   name: "serverCode",
						   prefix: "应用编码:",
						   require: true
					   }, {
						   type: "TextInput",
						   name: "serverName",
						   prefix: "应用名称:",
						   require: true
					   }, {
						   type: "TextInput",
						   name: "sversion",
						   prefix: "应用版本:"
					   }, {
						   type: "TextInput",
						   name: "serverIp",
						   prefix: "应用IP:"
					   }, {
						   type: "Number",
						   name: "serverPort",
						   prefix: "应用端口:"
					   }, {
						   type: "TextArea",
						   name: "remark",
						   prefix: "备注:"
					   }
					]
				}).appendTo(this.dom.find("div.app-setting"));
				
			},
			btns: [{
				text: "确定",
				handler: function() {
					
					var _this = this, 
					    serverDetail = [],
					    data = $form.getData();
					
					if (!data) return;
					
					if (BEAN) {
						
						data.serverId = BEAN.serverId;
						
						serverDetail.push(data);
						
						serverDetail.push(BEAN);
						
					} else {
						
						serverDetail.push(data);
						
						serverDetail.push({});
	
					}
					
					$.ajax({
						url: Request.get("config/host/saveApp"),
						data: {
							serverDetail: JSON.stringify(serverDetail),
							treeNodeId: nodeId
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
          .html("<div class='app-setting'></div>")
		  .pop(1);
		
		return $dialog;
		
	}
	
	return {
		create: create
	};
	
});