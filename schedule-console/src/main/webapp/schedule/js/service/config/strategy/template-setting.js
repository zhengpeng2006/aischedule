define(["core", "form", "dialog"], function() {
	                                         
	var $dialog,
	    $form;
	
	/*
	 * 创建页面对象
	 */
	function create(config) {
		
        var BEAN = config.bean;
        
		var $dialog = new UI.Dialog({
			title: BEAN ? "修改模板" : "新增模板",
			width: 520,
			height: 520,
			afterShow: function() {

				$form = new UI.Form({
					data: BEAN ? BEAN : "",
					columns: [{
						   type: "TextInput",
						   name: "templateName",
						   prefix: "模板名称:",
						   require: true,
						   width: 330
					   }, {
						   type: "TextArea",
						   name: "contents",
						   prefix: "模板内容:",
						   require: true,
						   width: 330,
						   height: 250
					   }, {
						   type: "TextArea",
						   name: "remarks",
						   prefix: "备注:",
						   width: 330
					   }
					]
				}).appendTo(this.dom.find("div.template-setting"));
				
			},
			btns: [{
				text: "确定",
				handler: function() {
					
					var _this = this, 
					    tamplateInput = [],
					    data = $form.getData();
					
					if (!data) return;
					
					if (BEAN) {
						
						data.templateId = BEAN.templateId;
						
						tamplateInput.push(data);
						
						tamplateInput.push(BEAN);
						
					} else {
						
						tamplateInput.push(data);
						
						tamplateInput.push({});
	
					}
					
					$.ajax({
						url: Request.get("config/strategy/saveTemplate"),
						data: {
							tamplateInput: JSON.stringify(tamplateInput)
						},
					    success: function(data) {
					    	
				    		_this.pop(0);

							config.confirm();
					    		
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
          .html("<div class='template-setting'></div>")
		  .pop(1);
		
		return $dialog;
		
	}
	
	return {
		create: create
	};
	
});