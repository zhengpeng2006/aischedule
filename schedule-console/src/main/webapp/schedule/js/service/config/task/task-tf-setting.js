define(["core", "form", "dialog"], function() {
	                                         
	var $dialog,
	    $form;
	
	/*
	 * 创建页面对象
	 */
	function create(config) {
		
		var $dialog = new UI.Dialog({
			title: "新增目标表",
			width: 420,
			afterShow: function() {

				$form = new UI.Form({
					titleWidth: 100,
					columns: [{
						   type: "TextInput",
						   name: "destTableName",
						   prefix: "目标表:",
						   require: true
					   }, {
						   type: "TextInput",
						   name: "destDbName",
						   prefix: "目标表数据源名:",
						   require: true
					   }, {
						   type: "ComboBox",
						   name: "tfType",
						   prefix: "转移类型:",
						   require: true,
						   data:[
						      {
						    	"text": "HIS",
						    	"value": "HIS"
						      }, {
						    	"text": "DEST",
							    "value": "DEST" 
						      }
						   ]
					   }, {
						   type: "TextArea",
						   name: "finSql",
						   prefix: "完成SQL:"
					   }, {
						   type: "TextArea",
						   name: "remark",
						   prefix: "备注:"
					   }
					]
				}).appendTo(this.dom.find("div.task-tf-setting"));
				
			},
			btns: [{
				text: "确定",
				handler: function() {
					
					var _this = this, 
					    colInfo = [],
					    data = $form.getData();
					
					if (!data) return;
						
					colInfo.push(data);
					
					colInfo.push({});
					
					$.ajax({
						url: Request.get("config/task/saveDestTable"),
						data: {
							colInfo: JSON.stringify(colInfo),
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
          .html("<div class='task-tf-setting'></div>")
		  .pop(1);
		
		return $dialog;
		
	}
	
	return {
		create: create
	};
	
});