define(["core", "form", "dialog"], function() {
	                                         
	var $dialog,
	    $form;
	
	/*
	 * 创建页面对象
	 */
	function create(config) {
		
		var $dialog = new UI.Dialog({
			title: "新增分组",
			width: 420,
			height: 280,
			afterShow: function() {

				$form = new UI.Form({
					columns: [{
						   type: "TextInput",
						   name: "groupCode",
						   prefix: "分组编码:",
						   require: true
					   }, {
						   type: "TextInput",
						   name: "groupName",
						   prefix: "分组名称:",
						   require: true
					   }, {
						   type: "TextArea",
						   name: "groupDesc",
						   prefix: "分组描述:"
					   }
					]
				}).appendTo(this.dom.find("div.task-group-setting"));
				
			},
			btns: [{
				text: "确定",
				handler: function() {
					
					var _this = this, 
					    groupInfoInput = [],
					    data = $form.getData();
					
					if (!data) return;
						
					groupInfoInput.push(data);
					
					groupInfoInput.push({});
	
					$.ajax({
						url: Request.get("config/task/saveTaskGroup"),
						data: {
							groupInfoInput: JSON.stringify(groupInfoInput)
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
          .html("<div class='task-group-setting'></div>")
		  .pop(1);
		
		return $dialog;
		
	}
	
	return {
		create: create
	};
	
});