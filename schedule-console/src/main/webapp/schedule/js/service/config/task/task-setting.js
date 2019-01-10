define(["core", "form", "dialog"], function() {
	                                         
	var $dialog,
	    $form;
	
	/*
	 * 创建页面对象
	 */
	function create(config) {
		
        var BEAN = config.bean;
        
		var $dialog = new UI.Dialog({
			title: BEAN ? "修改调度任务" : "新增调度任务",
			width: 750,
			height: 520,
			afterShow: function() {

				$form = new UI.Form({
					data: BEAN ? BEAN : "",
					columns: [{
						   type: "TextInput",
						   name: "taskCode",
						   prefix: "任务编码:",
						   require: true,
						   readonly: BEAN ? true : false
					   }, {
						   type: "TextInput",
						   name: "taskName",
						   prefix: "任务名称:",
						   require: true
					   }, {
						   type: "ComboBox",
						   name: "taskGroup",
						   prefix: "任务分组:",
						   searcher: true,
						   valueField: "groupCode",
						   textField: "groupName",
						   url: Request.get("config/task/qryTaskGroup"),
						   loadFilter: function(data) {
							   
							   return data.data.groupInfosItems;
							   
						   },
						   require: true
					   }, {
						   type: "ComboBox",
						   name: "taskType",
						   prefix: "任务类型:",
						   data: CORE.getTaskType,
						   require: true,
						   onChange: function(data) {
							   
							   var isLog = $form.getItem("isLog");
							   if (data.value === 'complex' || data.value === 'tf') {
								   
								   isLog.setValue(true).readonly(true);
								   
							   } else {
								   
								   isLog.readonly(false);
								   
							   }
						   }
					   }, {
						   type: "TextInput",
						   name: "taskDesc",
						   prefix: "任务描述:"
					   }, {
						   type: "TextInput",
						   name: "startTime",
						   prefix: "开始时间:"
					   }, {
						   type: "TextInput",
						   name: "endTime",
						   prefix: "结束时间:"
					   }, {
						   type: "Number",
						   name: "scanNum",
						   prefix: "每次扫描数:",
						   require: true
					   }, {
						   type: "Number",
						   name: "executeNum",
						   prefix: "每次执行数:",
						   require: true
					   }, {
						   type: "Number",
						   name: "threadNum",
						   prefix: "线程数:",
						   require: true
					   }, {
						   type: "Number",
						   name: "scanIntervalTime",
						   prefix: "扫描间隔时间:",
						   require: true
					   }, {
						   type: "Number",
						   name: "idleSleepTime",
						   prefix: "空闲休眠时间:",
						   require: true
					   }, {
						   type: "ComboBox",
						   name: "faultProcessMethod",
						   prefix: "故障处理方式:",
						   require: true,
						   data: [
						       {
						    	   "text": "自动处理",
						    	   "value": "A"
						       }, {
						    	   "text": "人工处理",
						    	   "value": "M"
						       }
						   ]
					   }, {
						   type: "ComboBox",
						   name: "splitRegion",
						   prefix: "是否地市分片:",
						   require: true,
						   data: [
						       {
						    	   "text": "是",
						    	   "value": "true"
						       }, {
						    	   "text": "否",
						    	   "value": "false"
						       }
						   ]
					   }, {
						   type: "Number",
						   name: "priority",
						   prefix: "优先级:"
					   }, {
						   type: "ComboBox",
						   name: "isLog",
						   prefix: "是否记录日志:",
						   require: true,
						   data: [
						       {
						    	   "text": "是",
						    	   "value": true
						       }, {
						    	   "text": "否",
						    	   "value": false
						       }
						   ],
						   afterRender: function() {
							   
							   if (BEAN) {
								   
								   var taskType = $form.getItem("taskType").getValue();
								   
								   if (taskType === 'complex' || taskType === 'tf') {
									   
									   this.readonly(true);
									   
								   } 
								   
								   this.setValue(BEAN.isLog);
								   
							   } 
						   }
					   }, {
						   type: "TextArea",
						   name: "items",
						   prefix: "拆分细项:"
					   }, {
						   type: "TextArea",
						   name: "processClass",
						   prefix: "业务执行类:"
					   }
					]
				}).appendTo(this.dom.find("div.task-setting"));
				
			},
			btns: [{
				text: "确定",
				handler: function() {
					
					var _this = this, 
					    taskInfoData = [],
					    data = $form.getData();
					
					if (!data) return;
					
					if (BEAN) {
						
						data.taskCode = BEAN.taskCode;
						
						taskInfoData.push(data);
						
						taskInfoData.push(BEAN);
						
					} else {
						
						taskInfoData.push(data);
						
						taskInfoData.push({});
	
					}
					
					$.ajax({
						url: Request.get("config/task/saveTask"),
						data: {
							taskInfoData: JSON.stringify(taskInfoData),
							operation: BEAN ? "update" : "add"
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
          .html("<div class='task-setting'></div>")
		  .pop(1);
		
		return $dialog;
		
	}
	
	return {
		create: create
	};
	
});