define(["core", "form", "dialog"], function() {
	                                         
	var $dialog,
	    $form;
	
	/*
	 * 创建页面对象
	 */
	function create(config) {
		
        var BEAN = config.bean;
        
		var $dialog = new UI.Dialog({
			title: BEAN ? "修改主机" : "新增主机",
			width: 420,
			height: 350,
			afterShow: function() {

				$form = new UI.Form({
					data: BEAN ? BEAN : "",
					columns: [{
						   type: "TextInput",
						   name: "hostCode",
						   prefix: "主机编码:",
						   require: true
					   }, {
						   type: "TextInput",
						   name: "hostName",
						   prefix: "主机名称:",
						   require: true
					   }, {
						   type: "TextInput",
						   name: "hostIp",
						   prefix: "主机IP:",
						   require: true
					   }, {
						   type: "TextInput",
						   name: "hostDesc",
						   prefix: "主机描述:"
					   }, {
						   type: "TextInput",
						   name: "remarks",
						   prefix: "备注:"
					   }
					]
				}).appendTo(this.dom.find("div.host-setting"));
				
			},
			btns: [{
				text: "确定",
				handler: function() {
					
					var _this = this, 
					    hostDetail = [],
					    data = $form.getData();
					
					if (!data) return;
					
					if (BEAN) {
						
						data.hostId = BEAN.hostId;
						
						hostDetail.push(data);
						
						hostDetail.push(BEAN);
						
					} else {
						
						hostDetail.push(data);
						
						hostDetail.push({});
	
					}
					
					$.ajax({
						url: Request.get("config/host/saveOrUpdate"),
						data: {
							hostDetail: JSON.stringify(hostDetail),
							treeNodeId: config.hostGroup
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
          .html("<div class='host-setting'></div>")
		  .pop(1);
		
		return $dialog;
		
	}
	
	return {
		create: create
	};
	
});