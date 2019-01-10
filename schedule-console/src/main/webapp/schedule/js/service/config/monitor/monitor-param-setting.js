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
			height: 300,
			afterShow: function() {

				$form = new UI.Form({
					data: BEAN ? BEAN : "",
					columns: [{
						   type: "TextInput",
						   name: "paramCode",
						   prefix: "参数:",
						   require: true,
						   readonly: BEAN ? true : false
					   }, {
						   type: "TextInput",
						   name: "paramValue",
						   prefix: "参数值:",
						   require: true
					   }, {
						   type: "TextInput",
						   name: "sortId",
						   prefix: "顺序:"
					   }, {
						   type: "TextInput",
						   name: "paramDesc",
						   prefix: "描述:",
					   }
					]
				}).appendTo(this.dom.find("div.monitor-param-setting"));
				
			},
			btns: [{
				text: "确定",
				handler: function() {
					
					var _this = this, 
					    paramInput = [],
					    data = $form.getData();
					
					if (!data) return;
					

					if (BEAN) {
						
						data.VId = BEAN.VId;
						
						paramInput.push(data);
						
						paramInput.push(BEAN);
						
					} else {
						
						paramInput.push(data);
						
						paramInput.push({});
	
					}
					
					$.ajax({
						url: Request.get("config/monitor/saveMonitorParam"),
						data: {
							paramInput: JSON.stringify(paramInput),
							infoId: config.infoId,
							registeType: 10
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
          .html("<div class='monitor-param-setting'></div>")
		  .pop(1);
		
		return $dialog;
		
	}
	
	return {
		create: create
	};
	
});