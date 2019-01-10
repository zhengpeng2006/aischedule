define(["core", "form", "dialog"], function() {
	                                         
	var $dialog,
	    $form;
	
	/*
	 * 创建页面对象
	 */
	function create(config) {
		
        var BEAN = config.bean;
        
		var $dialog = new UI.Dialog({
			title: BEAN ? "修改用户" : "新增用户",
			width: 420,
			height: 320,
			afterShow: function() {

				$form = new UI.Form({
					data: BEAN ? BEAN : "",
					columns: [{
						   type: "TextInput",
						   name: "userName",
						   prefix: "用户名称:",
						   require: true
					   }, {
						   type: "TextInput",
						   name: "userCode",
						   prefix: "用户编码:",
						   require: true
					   }, {
						   type: "password",
						   name: "userPwd",
						   prefix: "用户密码:",
						   require: true
					   }, {
						   type: "password",
						   name: "reUserPwd",
						   prefix: "确认密码:",
						   require: true,
						   afterRender: function() {
							   
							   BEAN && this.setValue(BEAN.userPwd);
							   
						   }
					   }, {
						   type: "TextInput",
						   name: "remark",
						   prefix: "备注:"
					   }
					]
				}).appendTo(this.dom.find("div.user-setting"));
				
			},
			btns: [{
				text: "确定",
				handler: function() {
					
					var _this = this, 
					    usersForm = [],
					    data = $form.getData();
					
					if (!data) return;
					
					if (data.userPwd !== data.reUserPwd) {
						
						UI.alert("两次输入的密码不一致!");
						
						return;
						
					}
					
					if (BEAN) {
						
						data.userId = BEAN.userId;
						
						usersForm.push(data);
						
						usersForm.push(BEAN);
						
					} else {
						
						usersForm.push(data);
						
						usersForm.push({});
	
					}
					
					$.ajax({
						url: Request.get("config/user/saveOrUpdate"),
						data: {
							usersForm: JSON.stringify(usersForm)
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
          .html("<div class='user-setting'></div>")
		  .pop(1);
		
		return $dialog;
		
	}
	
	return {
		create: create
	};
	
});