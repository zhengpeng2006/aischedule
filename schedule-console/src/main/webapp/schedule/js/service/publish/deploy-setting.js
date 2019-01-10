define(["core", "form", "dialog"], function() {
	                                         
	var $dialog,
	    $form;
	
	/*
	 * 创建页面对象
	 */
	function create(config) {
		
		 var DATA = {};
		 
		 $.ajax({
		   url: Request.get("publish/showVersionDetail"),
		   async: false,
		   data: {
			   versionId: config.versionId
		   },
		   success: function(data) {
			   
			   DATA = data.data.versionDetail;
			   
		   }

	    });
		 
		var $dialog = new UI.Dialog({
			title: "查看版本详情",
			width: 750,
			height: 380,
			afterShow: function() {

				$form = new UI.Form({
					data: DATA,
					columns: [{
						   type: "TextInput",
						   name: "resolvedProblems",
						   prefix: "解决问题:"
					   }, {
						   type: "TextInput",
						   name: "contacts",
						   prefix: "联系人:"
					   }, {
						   type: "TextInput",
						   name: "packagePath",
						   prefix: "存放路径:"
					   }, {
						   type: "Number",
						   name: "parentVersionId",
						   prefix: "上个版本号:"
					   }, 
					   {
						   type: "TextInput",
						   name: "state",
						   prefix: "版本状态:"
					   },
					   {
						   type: "Number",
						   name: "externalVersionId",
						   prefix: "外部版本号:"
					   }, 
					   {
						   type: "TextInput",
						   name: "operateType",
						   prefix: "操作类型:"
					   }, {
						   type: "TextInput",
						   name: "operatorId",
						   prefix: "操作员:"
					   }, {
						   type: "TextInput",
						   name: "remarks",
						   prefix: "备注:"
					   }, {
						   type: "TextInput",
						   name: "createTime",
						   prefix: "创建时间:"
					   }, {
						   type: "TextArea",
						   name: "fileList",
						   prefix: "修改文件列表:"
					   }
					]
				}).appendTo(this.dom.find("div.deploy-setting"));
				
			}
		})
          .html("<div class='deploy-setting'></div>")
		  .pop(1);
		
		return $dialog;
		
	}
	
	return {
		create: create
	};
	
});