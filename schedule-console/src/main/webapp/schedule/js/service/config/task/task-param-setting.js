define(["core", "form", "dialog"], function() {
	                                         
	var $dialog,
	    $form;
	
	/*
	 * 创建页面对象
	 */
	function create(config) {

		var $dialog = new UI.Dialog({
			title: "新增参数",
			width: 420,
			height: 280,
			afterShow: function() {

				$form = new UI.Form({
					columns: [{
						   type: "TextInput",
						   name: "paramKey",
						   prefix: "参数:",
						   require: true
					   }, {
						   type: "TextInput",
						   name: "paramValue",
						   prefix: "参数值:",
						   require: true
					   }, {
						   type: "TextArea",
						   name: "paramDesc",
						   prefix: "描述:",
					   }
					]
				}).appendTo(this.dom.find("div.task-param-setting"));
				
			},
			btns: [{
				text: "确定",
				handler: function() {
					
					var _this = this, 
					    paramInput = [],
					    data = $form.getData();
					
					if (!data) return;
						
					paramInput.push(data);
					
					paramInput.push({});
	
					$.ajax({
						url: Request.get("config/task/saveTaskParam"),
						data: {
							paramInput: JSON.stringify(paramInput),
							taskCode: config.taskCode
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
          .html("<div class='task-param-setting'></div>")
		  .pop(1);
		
		return $dialog;
		
	}
	
	return {
		create: create
	};
	
});