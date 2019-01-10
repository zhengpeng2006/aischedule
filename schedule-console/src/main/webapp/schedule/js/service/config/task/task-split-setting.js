define(["core", "form", "dialog"], function() {
	                                         
	var $dialog,
	    $form;
	
	/*
	 * 创建页面对象
	 */
	function create(config) {

		var BEAN = config.bean;
		
		var $dialog = new UI.Dialog({
			title: "修改分片配置",
			width: 420,
			height: 280,
			afterShow: function() {

				$form = new UI.Form({
					data: BEAN ? BEAN : "",
					columns: [{
						   type: "ComboBox",
						   name: "host",
						   prefix: "主机:",
						   require: true,
						   valueField: "VALUE",
						   textField: "TEXT",
						   url: Request.get("config/task/qryAllHostInfo"),
						   getUrlParam: function() {
							   
							   return {
								   taskCode: BEAN.taskCode,
							   };
							   
						   },
						   loadFilter: function(data) {
							   
							   return data.data.hostList;
							   
						   },
						   onChange: function(data) {
							   
							   $form.getItem("assignServer").clear();
							   
							   if (data) {
								   
								   $.ajax({
									   url: Request.get("config/task/qryAppCode"),
									   data: {
										   hostId: data.VALUE,
									   },
									   success: function(data) {
										   
										   $form.getItem("assignServer").loadData(data.data);
										   
									   }
								   });
								   
							   }
							   
						   }
					   }, {
						   type: "ComboBox",
						   name: "assignServer",
						   prefix: "应用编码:",
						   require: true,
						   valueField: "appCode",
						   textField: "appCode",
						   afterRender: function() {
							   
							   if (BEAN.host) {
									
									 $.ajax({
										   url: Request.get("config/task/qryAppCode"),
										   data: {
											   hostId: BEAN.host,
										   },
										   success: function(data) {
											   
											   $form.getItem("assignServer").loadData(data.data);
											   
										   }
								
									   });
								}
							   
						   }
					   }
					]
				}).appendTo(this.dom.find("div.task-split-setting"));
				
			},
			btns: [{
				text: "确定",
				handler: function() {
					
					var _this = this, 
					    dataArray = [],
					    data = $form.getData();
					
					if (!data) return;

					dataArray.push({
						taskCode: BEAN.taskCode,
						taskItem: BEAN.taskItem,
						serverCode: data.assignServer
					});
						
					$.ajax({
						url: Request.get("config/task/saveTaskSplit"),
						data: {
							dataArray: JSON.stringify(dataArray)
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
          .html("<div class='task-split-setting'></div>")
		  .pop(1);
		
		return $dialog;
		
	}
	
	return {
		create: create
	};
	
});