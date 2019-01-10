define(["core", "form", "dialog"], function() {
	                                         
	var $dialog,
	    $form;
	
	/*
	 * 创建页面对象
	 */
	function create(config) {
		
        var BEAN = config.bean;
        
		var $dialog = new UI.Dialog({
			title: BEAN ? "修改监控脚本" : "新增监控脚本",
			width: 520,
			height: 520,
			afterShow: function() {

				$form = new UI.Form({
					data: BEAN ? BEAN : "",
					columns: [{
						   type: "TextInput",
						   name: "name",
						   prefix: "脚本名称:",
						   require: true,
						   width: 330
					   }, {
						   type: "ComboBox",
						   name: "EType",
						   prefix: "脚本类型:",
						   require: true,
						   width: 330,
						   data: [
						       {
						    	  "text": "shell脚本",
						    	  "value": "SHELL"
						       },
						       {
						    	  "text": "命令",
							      "value": "JAVACOMMAND"
						       }
						   ]
					   }, {
						   type: "TextArea",
						   name: "expr",
						   prefix: "表达式:",
						   require: true,
						   width: 330,
						   height: 250
					   }, {
						   type: "TextInput",
						   name: "remarks",
						   prefix: "备注:",
						   width: 330
					   }
					]
				}).appendTo(this.dom.find("div.monitor-script-setting"));
				
			},
			btns: [{
				text: "确定",
				handler: function() {
					
					var _this = this, 
					    dataInput = [],
					    data = $form.getData();
					
					if (!data) return;
					
					if (BEAN) {
						
						data.execId = BEAN.execId;
						
						dataInput.push(data);
						
						dataInput.push(BEAN);
						
					} else {
						
						dataInput.push(data);
						
						dataInput.push({});
	
					}
					
					$.ajax({
						url: Request.get("config/monitor/saveMonitorScript"),
						data: {
							dataInput: JSON.stringify(dataInput)
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
          .html("<div class='monitor-script-setting'></div>")
		  .pop(1);
		
		return $dialog;
		
	}
	
	return {
		create: create
	};
	
});