define(["core", "form", "dialog"], function() {
	                                         
	var $dialog,
	    $form;
	
	/*
	 * 创建页面对象
	 */
	function create(config) {

		var BEAN = config.bean;
		
		var $dialog = new UI.Dialog({
			title: BEAN ? "修改安装规则" : "新增安装规则",
			width: 420,
			height: 300,
			afterShow: function() {

				$form = new UI.Form({
					data: BEAN ? BEAN : "",
					columns: [{
						   type: "TextInput",
						   name: "packageName",
						   prefix: "需获取的包名:",
						   require: true
					   }, {
						   type: "TextInput",
						   name: "installPath",
						   prefix: "安装路径:",
						   require: true
					   }, {
						   type: "ComboBox",
						   name: "unzipMethod",
						   prefix: "解压方式:",
						   require: true,
						   data: [
						       {
						    	   "text": "JAR",
						    	   "value": "jar"
						       }, {
						    	   "text": "TAR.GZ",
						    	   "value": "tar.gz"
						       }, {
						    	   "text": "TAR",
						    	   "value": "tar"
						       }, {
						    	   "text": "不解压",
						    	   "value": "plain"
						       }   
						   ]
					   }
					]
				}).appendTo(this.dom.find("div.strategy-rule-setting"));
				
			},
			btns: [{
				text: "确定",
				handler: function() {

					var _this = this, 
					    data = $form.getData(),
					    ruleData = [],
					    unzipMethod;
					
					if (!data) return;
					
					unzipMethod = data.unzipMethod === 'plain' ? "N:" + data.unzipMethod : "Y:" + data.unzipMethod;
						
					ruleData.push(data.packageName);
					
					ruleData.push(data.installPath);
					
					ruleData.push(unzipMethod);
					
			    	_this.pop(0);

				    config.confirm(ruleData.join(":"));
				    
				}
			}, {
				text: "取消",
				handler: function() {

					this.pop(0);
					
				}
			}]
		})
          .html("<div class='strategy-rule-setting'></div>")
		  .pop(1);
		
		return $dialog;
		
	}
	
	return {
		create: create
	};
	
});