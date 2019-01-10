define(["core", "form", "dialog"], function() {
	                                         
	var $dialog,
	    $form;
	
	/*
	 * 创建页面对象
	 */
	function create(config) {
		
        var BEAN = config.bean;
        
		var $dialog = new UI.Dialog({
			title: BEAN ? "修改监控阀值" : "新增监控阀值",
			width: 520,
			height: 520,
			afterShow: function() {

				$form = new UI.Form({
					data: BEAN ? BEAN : "",
					columns: [{
						   type: "TextInput",
						   name: "thresholdName",
						   prefix: "阀值名称:",
						   require: true,
						   width: 330
					   }, {
						   type: "TextInput",
						   name: "expiryDays",
						   prefix: "有效期限:",
						   width: 330
					   }, {
						   type: "TextArea",
						   name: "expr1",
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
				}).appendTo(this.dom.find("div.monitor-threshold-setting"));
				
			},
			btns: [{
				text: "确定",
				handler: function() {
					
					var _this = this, 
					    thresholdInput = [],
					    data = $form.getData();
					
					if (!data) return;
					
					if (BEAN) {
						
						data.thresholdId = BEAN.thresholdId;
						
						thresholdInput.push(data);
						
						thresholdInput.push(BEAN);
						
					} else {
						
						thresholdInput.push(data);
						
						thresholdInput.push({});
	
					}
					
					$.ajax({
						url: Request.get("config/monitor/saveMonitorThreshold"),
						data: {
							thresholdInput: JSON.stringify(thresholdInput)
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
          .html("<div class='monitor-threshold-setting'></div>")
		  .pop(1);
		
		return $dialog;
		
	}
	
	return {
		create: create
	};
	
});