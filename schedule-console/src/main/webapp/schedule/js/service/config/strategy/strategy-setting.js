define(["core", "form", "dialog"], function() {
	                                         
	var $dialog,
	    $form;
	
	/*
	 * 创建页面对象
	 */
	function create(config) {
		
        var BEAN = config.bean;
            isView = config.view;
        
		var $dialog = new UI.Dialog({
			title: BEAN ? (isView ? "部署策略详情" : "修改部署策略") : "新增部署策略",
			width: 750,
			height: 480,
			afterShow: function() {

				$form = new UI.Form({
					data: BEAN ? BEAN : "",
					columns: [{
						   type: "TextInput",
						   name: "deployStrategyName",
						   prefix: "策略名称:",
						   require: true,
						   readonly: isView
					   }, {
						   type: "TextInput",
						   name: "clientBinPath",
						   prefix: "bin路径:",
						   require: true,
						   readonly: isView
					   }, {
						   type: "TextInput",
						   name: "clientHomePath",
						   prefix: "home路径:",
						   require: true,
						   readonly: isView
					   }, {
						   type: "TextInput",
						   name: "clientSbinPath",
						   prefix: "sbin路径:",
						   require: true,
						   readonly: isView
					   }, {
						   type: "TextInput",
						   name: "clientLogPath",
						   prefix: "log路径:",
						   require: true,
						   readonly: isView
					   }, {
						   type: "TextInput",
						   name: "ftpHostIp",
						   prefix: "FTP主机IP:",
						   readonly: isView
					   }, {
						   type: "Number",
						   name: "ftpHostPort",
						   prefix: "FTP主机端口:",
						   readonly: isView
					   }, {
						   type: "TextInput",
						   name: "ftpPackagePath",
						   prefix: "FTP包路径:",
						   readonly: isView
					   }, {
						   type: "TextInput",
						   name: "ftpUserName",
						   prefix: "FTP用户名:",
						   readonly: isView
					   }, {
						   type: "password",
						   name: "ftpPassword",
						   prefix: "FTP密码:",
						   readonly: isView
					   }, {
						   type: "ComboBox",
						   name: "templateId",
						   prefix: "部署模板:",
						   require: true,
						   url: Request.get("config/strategy/qryTemplate"),
						   valueField: "templateId",
						   textField: "templateName",
						   loadFilter: function(data) {
							   
							   return data.data.templatesItems;
							   
						   },
						   readonly: isView
					   }, {
						   type: "ComboBox",
						   name: "stopTemplateId",
						   prefix: "部署停止模板:",
						   clearBtn: true,
						   url: Request.get("config/strategy/qryTemplate"),
						   valueField: "templateId",
						   textField: "templateName",
						   loadFilter: function(data) {
							   
							   return data.data.templatesItems;
							   
						   },
						   readonly: isView
					   }, {
						   type: "ComboBox",
						   name: "ftpProtocol",
						   prefix: "文件传输协议:",
						   require: true,
						   data: [
						      {
						    	 "text": "FTP",
						         "value": "1"
						      },
						      {
						    	 "text": "SFTP",
							     "value": "0" 
						      }
						   ],
						   readonly: isView
					   }, {
						   type: "TextInput",
						   name: "serverPackagePath",
						   prefix: "服务器包路径:",
						   readonly: isView
					   }, {
						   type: "Number",
						   name: "historyPackageNum",
						   prefix: "历史版本数:",
						   readonly: isView
					   }
					].concat(isView ? [
							{
							   type: "ComboDate",
							   name: "createTime",
							   prefix: "创建时间:",
							   readonly: true
							}, {
							   type: "ComboDate",
							   name: "modifyTime",
							   prefix: "修改时间:",
							   readonly: true
						    }
					] : "").concat([
							{
							   type: "TextArea",
							   name: "remarks",
							   prefix: "备注:",
							   readonly: isView
							}           
					])
				}).appendTo(this.dom.find("div.strategy-setting"));
				
			},
			btns: isView ? "" : [{
				text: "确定",
				handler: function() {
					
					var _this = this, 
					    strategyInfo = [],
					    data = $form.getData();
					
					if (!data) return;
					
					if (BEAN) {
						
						data.deployStrategyId = BEAN.deployStrategyId;
						
						strategyInfo.push(data);
						
						strategyInfo.push(BEAN);
						
					} else {
						
						strategyInfo.push(data);
						
						strategyInfo.push({});
	
					}
					
					$.ajax({
						url: Request.get("config/strategy/saveStrategy"),
						data: {
							strategyInfo: JSON.stringify(strategyInfo)
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
          .html("<div class='strategy-setting'></div>")
		  .pop(1);
		
		return $dialog;
		
	}
	
	return {
		create: create
	};
	
});