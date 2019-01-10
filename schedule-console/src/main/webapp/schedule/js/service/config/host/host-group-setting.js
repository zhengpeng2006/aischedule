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
			height: 270,
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
						   type: "TextArea",
						   name: "groupDesc",
						   prefix: "分组描述:"
					   }
					]
				}).appendTo(this.dom.find("div.host-group-setting"));
				
			},
			btns: [{
				text: "确定",
				handler: function() {
					
					var _this = this, 
					    groupDetail = [],
					    data = $form.getData();
					
					if (!data) return;
					
					if (BEAN) {
						
						data.groupId = BEAN.groupId;
						
						groupDetail.push(data);
						
						groupDetail.push(BEAN);
						
					} else {
						
						groupDetail.push(data);
						
						groupDetail.push({});
	
					}
					
					$.ajax({
						url: Request.get("config/host/saveHostGroup"),
						data: {
							groupDetail: JSON.stringify(groupDetail)
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
          .html("<div class='host-group-setting'></div>")
		  .pop(1);
		
		return $dialog;
		
	}
	
	return {
		create: create
	};
	
});