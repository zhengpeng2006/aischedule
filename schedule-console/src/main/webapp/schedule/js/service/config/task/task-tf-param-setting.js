define(["core", "form", "dialog"], function() {
	                                         
	var $dialog,
	    $form;
	
	/*
	 * 创建页面对象
	 */
	function create(config) {
		
		var $dialog = new UI.Dialog({
			title: "新增映射字段",
			width: 420,
			height: 280,
			afterShow: function() {

				$form = new UI.Form({
					columns: [{
						   type: "TextInput",
						   name: "srcColumnName",
						   prefix: "源表字段:",
						   require: true
					   }, {
						   type: "TextInput",
						   name: "tfColumnName",
						   prefix: "目标表字段:",
						   require: true
					   }, {
						   type: "TextArea",
						   name: "remark",
						   prefix: "备注:"
					   }
					]
				}).appendTo(this.dom.find("div.task-tf-param-setting"));
				
			},
			btns: [{
				text: "确定",
				handler: function() {
					
					var _this = this, 
					    colInput = [],
					    data = $form.getData();
					
					if (!data) return;
						
					colInput.push(data);
					
					colInput.push({});
					
					$.ajax({
						url: Request.get("config/task/addCol"),
						data: {
							colInput: JSON.stringify(colInput),
							cfgTfDtlId: config.cfgTfDtlId,
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
          .html("<div class='task-tf-param-setting'></div>")
		  .pop(1);
		
		return $dialog;
		
	}
	
	return {
		create: create
	};
	
});