define(["core", "form", "dialog"], function() {
	                                         
	var $dialog,
	    $form;
	
	/*
	 * 创建页面对象
	 */
	function create(config) {

		var BEAN = config.bean;
		
		var $dialog = new UI.Dialog({
			title: BEAN ? "修改参数" : "新增参数",
			width: 420,
			height: 200,
			afterShow: function() {

				$form = new UI.Form({
					data: BEAN ? BEAN : "",
					columns: [{
						   type: "TextInput",
						   name: "key",
						   prefix: "参数:",
						   require: true,
						   readonly: BEAN ? true : false
					   }, {
						   type: "TextInput",
						   name: "value",
						   prefix: "参数值:",
						   require: true
					   }
					]
				}).appendTo(this.dom.find("div.app-param-setting"));
				
			},
			btns: [{
				text: "确定",
				handler: function() {
					
					var _this = this, 
					    deployAppParamDetail = [],
					    data = $form.getData();
					
					if (!data) return;
					
					data.paramType = 'C';

					if (BEAN) {
						
						data.applicationParamId = BEAN.applicationParamId;
						
						deployAppParamDetail.push(data);
						
						deployAppParamDetail.push(BEAN);
						
					} else {
						
						deployAppParamDetail.push(data);
						
						deployAppParamDetail.push({});
	
					}
					
					$.ajax({
						url: Request.get("config/host/saveParam"),
						data: {
							deployAppParamDetail: JSON.stringify(deployAppParamDetail),
							serverId: config.serverId,
							applicationParamId: data.applicationParamId
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
          .html("<div class='app-param-setting'></div>")
		  .pop(1);
		
		return $dialog;
		
	}
	
	return {
		create: create
	};
	
});