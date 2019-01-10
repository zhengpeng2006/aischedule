define(["core", "form", "dialog"], function() {
	                                         
	var $dialog,
	    $form;
	
	/*
	 * 创建页面对象
	 */
	function create(config) {
		
        var BEAN = config.bean,
            groupId = config.groupId;
        
		var $dialog = new UI.Dialog({
			title: BEAN ? "修改监控任务" : "新增监控任务",
			width: 420,
			height: 350,
			afterShow: function() {

				$form = new UI.Form({
					data: BEAN ? BEAN : "",
					columns: [{
						   type: "TextInput",
						   name: "name",
						   prefix: "监控任务名称:",
						   require: true
					   }, {
						   type: "TextInput",
						   name: "infoCode",
						   prefix: "监控任务编码:",
						   require: true
					   }, {
						   type: "ComboBox",
						   name: "taskMethod",
						   prefix: "任务类型:",
						   require: true,  
						   data: [
						     {
						    	"text": "固定时间",
						    	"value": "F"
						     }, {
						        "text": "周期执行",
							    "value": "C"
							 }, {
						        "text": "立即执行",
							    "value": "I"
							 }
						   ]
					   },  {
						   type: "ComboBox",
						   name: "hostId",
						   prefix: "主机:",
						   require: true, 
						   valueField: "VALUE",
						   textField: "TEXT",
						   url: Request.get("config/task/qryAllHostInfo"),
						   loadFilter: function(data) {

							   return data.data.hostList;
							   
						   }
					   }, {
						   type: "TextArea",
						   name: "remarks",
						   prefix: "备注:"
					   }
					]
				}).appendTo(this.dom.find("div.monitor-setting"));
				
			},
			btns: [{
				text: "确定",
				handler: function() {
					
					var _this = this, 
					    infoDataInput = [],
					    data = $form.getData();
					
					if (!data) return;

					data.layer = "BASE";

					data.MType = "EXEC";
					
					if (BEAN) {
						
						data.infoId = BEAN.infoId;
						
						infoDataInput.push(data);
						
						infoDataInput.push(BEAN);
						
					} else {
						
						infoDataInput.push(data);
						
						infoDataInput.push({});
	
					}
					
					$.ajax({
						url: Request.get("config/monitor/saveMonitor"),
						data: {
							infoDataInput: JSON.stringify(infoDataInput),
							treeNodeId: groupId
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
          .html("<div class='monitor-setting'></div>")
		  .pop(1);
		
		return $dialog;
		
	}
	
	return {
		create: create
	};
	
});