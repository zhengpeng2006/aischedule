define(["core", "form", "dialog"], function() {
	                                         
	var $dialog,
	    $form;
	
	/*
	 * 创建页面对象
	 */
	function create(config) {
		
		var $dialog = new UI.Dialog({
			title: "新版本信息",
			width: 420,
			height: 320,
			afterShow: function() {

				$form = new UI.Form({
					columns: [{
						   type: "TextInput",
						   name: "resolvedProblems",
						   prefix: "解决问题:"
					   }, {
						   type: "TextInput",
						   name: "contacts",
						   prefix: "版本联系人:",
						   require: true
					   }, {
						   type: "TextInput",
						   name: "remarks",
						   prefix: "备注:"
					   }, {
						   type: "TextArea",
						   name: "fileList",
						   prefix: "修改文件列表:"
					   }
					]
				}).appendTo(this.dom.find("div.publish"));
				
			},
			btns: [{
				text: "确定",
				handler: function() {
					
					var _this = this, 
					    versionInfo = [],
					    data = $form.getData();
					
					if (!data) return;
					
					versionInfo.push(data);
					
					versionInfo.push({});
	
					_this.pop(0);
					
					config.confirm(versionInfo);
					
				}
			}, {
				text: "取消",
				handler: function() {

					this.pop(0);
					
				}
			}]
		})
          .html("<div class='publish'></div>")
		  .pop(1);
		
		return $dialog;
		
	}
	
	return {
		create: create
	};
	
});