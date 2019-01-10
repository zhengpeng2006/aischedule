define(["core", "form", "dialog"], function() {
	                                         
	var $dialog,
	    $form;
	
	/*
	 * 创建页面对象
	 */
	function create(config) {
		
        var BEAN = config.bean;
        
		var $dialog = new UI.Dialog({
			title: BEAN ? "修改监控时间" : "新增监控时间",
			width: 520,
			height: 470,
			afterShow: function() {

				$form = new UI.Form({
					data: BEAN ? BEAN : "",
					columns: [{
						   type: "ComboBox",
						   name: "TType",
						   prefix: "时间类型:",
						   require: true,
						   width: 330,
						   data: [
						      {
						    	 "text": "固定时间",
						    	 "value": "F"
						      },
						      {
						    	 "text": "周期执行",
							     "value": "CRON"  
						      },
						      {
						    	 "text": "立即执行",
							     "value": "I"  
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
				}).appendTo(this.dom.find("div.monitor-time-setting"));
				
			},
			btns: [{
				text: "确定",
				handler: function() {
					
					var _this = this, 
					    timeInput = [],
					    data = $form.getData();
					
					if (!data) return;
					
					if (BEAN) {
						
						data.timeId = BEAN.timeId;
						
						timeInput.push(data);
						
						timeInput.push(BEAN);
						
					} else {
						
						timeInput.push(data);
						
						timeInput.push({});
	
					}
					
					$.ajax({
						url: Request.get("config/monitor/saveMonitorTime"),
						data: {
							timeInput: JSON.stringify(timeInput)
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
          .html("<div class='monitor-time-setting'></div>")
		  .pop(1);
		
		return $dialog;
		
	}
	
	return {
		create: create
	};
	
});