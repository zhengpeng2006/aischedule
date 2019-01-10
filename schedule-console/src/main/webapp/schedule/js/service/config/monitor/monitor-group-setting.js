define(["core", "form", "dialog"], function() {
	                                         
	var $dialog,
	    $form;
	
	/*
	 * 创建页面对象
	 */
	function create(config) {
		
        var BEAN = config.bean;
        
		var $dialog = new UI.Dialog({
			title: BEAN ? "修改分组" : "新增分组",
			width: 420,
			afterShow: function() {

				$form = new UI.Form({
					data: BEAN ? BEAN : "",
					columns: [{
						   type: "TextInput",
						   name: "groupName",
						   prefix: "分组名称:",
						   require: true
					   }, {
						   type: "TextInput",
						   name: "groupCode",
						   prefix: "分组编码:",
						   require: true
					   }, {
						   type: "ComboBox",
						   name: "layer",
						   prefix: "层:",
						   require: true,  
						   data: [
						     {
						    	"text": "BASE",
						    	"value": "BASE"
						     },
						     {
						        "text": "BUSI",
							    "value": "BUSI",
						     }
						   ]
					   }, {
						   type: "Number",
						   name: "sortId",
						   prefix: "顺序:"
					   },{
						   type: "TextArea",
						   name: "groupDesc",
						   prefix: "分组描述:"
					   }
					]
				}).appendTo(this.dom.find("div.monitor-group-setting"));
				
			},
			btns: [{
				text: "确定",
				handler: function() {
					
					var _this = this, 
					    groupInput = [],
					    data = $form.getData();
					
					if (!data) return;
					
					if (BEAN) {
						
						data.groupId = BEAN.groupId;
						
						groupInput.push(data);
						
						groupInput.push(BEAN);
						
					} else {
						
						groupInput.push(data);
						
						groupInput.push({});
	
					}
					
					$.ajax({
						url: Request.get("config/monitor/saveMonitorGroup"),
						data: {
							groupInput: JSON.stringify(groupInput)
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
          .html("<div class='monitor-group-setting'></div>")
		  .pop(1);
		
		return $dialog;
		
	}
	
	return {
		create: create
	};
	
});