define(["core", "grid", "form", "buttonsetting"], function() {
	                                         
	var $page,
	    $grid,
        $form,
        $searchBtn,
        formData;
	
	/*
	 * 创建页面对象
	 */
	function create(config) {
		
		var $dialog = new UI.Dialog({
			title: "tf/reload详细配置",
			width: 900,
			height: 550,
			afterShow: function() {
				
				$page = this.dom.find("div.task-tf");
				
				$("<div class='page-dialog-form'></div><div class='page-dialog-grid'></div>").appendTo($page);
				
          		$form = new UI.Form({
          			itemWidth: 310,
          			titleWidth: 90,
          			columns: [{
          				   type: "TextInput",
          				   name: "taskCode",
          				   prefix: "任务编码:",
        				   require: true
          			   }, {
          				   type: "TextInput",
          				   name: "srcDbCode",
          				   prefix: "源表数据源名称:",
        				   require: true
          			   }, {
          				   type: "TextInput",
          				   name: "srcTableName",
          				   prefix: "源表名:",
        				   require: true
          			   }, {
          				   type: "TextInput",
          				   name: "pkCol",
          				   prefix: "主键字段名:"
          			   }, {
          				   type: "TextArea",
          				   name: "querySql",
          				   prefix: "查询SQL:"
          			   }, {
          				   type: "TextArea",
          				   name: "proSql",
          				   prefix: "处理中SQL:"
          			   }, {
          				   type: "TextArea",
          				   name: "finSql",
          				   prefix: "完成SQL:"
          			   }, {
          				   type: "TextArea",
          				   name: "errSql",
          				   prefix: "错误处理SQL:"
          			   }, {
          				   type: "TextArea",
          				   name: "conutSql",
          				   prefix: "统计SQL:"
          			   }, {
          				   type: "TextArea",
          				   name: "remark",
          				   prefix: "备注:"
          			   }
          			]
          		}).appendTo($page.find("div.page-dialog-form"));
          		
          		$searchBtn = $("<div class='searcher-btn task-tf-btn'><a class='pageui-btn'>保存</a></div>").appendTo($page.find("div.page-dialog-form"));
          		
          		$grid = new UI.Grid({
          			pager: {
          				url: Request.get("config/task/qryDestTable"),
          				getParams: function() {
          					
          					return {
          						taskCode: config.taskCode
          					};
          					
          				},
          				totalFilter: function(data) {
          					
          					return data.data.tfDetailTotal;
          					
          				}
          			},
          			linage: 5,
          			loadFilter: function(data) {
          				
          				 formData = data.data.tfInfo;
          				 
          				 $form.loadData(formData);
          				 
          			     return data.data.tfDetailItems;
          				
          			},
          			columns: [{
          				field: "cfgTfCode",
          				title: "任务编码"
          			}, {
          				field: "destTableName",
          				title: "目标表名"
          			}, {
          				field: "destDbName",
          				title: "目标表数据源名称"
          			}, {
          				field: "tfType",
          				title: "转移类型"
          			}, {
          				field: "finSql",
          				title: "完成SQL"
          			}, {
          				field: "remark",
          				title: "备注"
          			}, {
          				field: "operation",
          				title: "操作",
          				formatter: function(val, row) {
          					
          					var data = [
							     {
							    	"name": "配置字段映射关系",
							    	"value": "tf-param",
							    	"url": "service/config/task/task-tf-param"
							     }, {
									"name": "删除",
									"value": "remove",
								    "url": "" 
								 }        
							];
							
							return [
						    	"<div class='pageui-button' data-config='data: " + JSON.stringify(data) + " , width: \"100px\" '/>",
						    ].join("");
          					
          				}
          			}]
          		}).appendTo($page.find("div.page-dialog-grid"));
          		
    			$grid.onRequest(true);
    			
			},
			btns: [{
				text: "新增目标表",
				handler: function() {
					
                   require(["service/config/task/task-tf-setting"], function(obj) {
						
						obj.create({
							taskCode: config.taskCode,
							confirm: function() {
								
								$grid.onRequest(true);
									
							}
						});
						
					});
				}
			}]
		})
          .html("<div class='task-tf'></div>")
		  .pop(1);
		
		initEvent(config);
		
		return $dialog;
		
	}
	
	/*
	 * 初始化事件
	 */
	function initEvent(config) {
		
		/*
		 * 保存
		 */
		$searchBtn.on("click",function() {
			
			var tfInfo = [],
		        data = $form.getData();
		
		    if (!data) return;
			
		    tfInfo.push(data);
		
		    tfInfo.push(formData);
			
			$.ajax({
				url: Request.get("config/task/saveTfCfg"),
				data: {
					tfInfo: JSON.stringify(tfInfo)
				},
				success: function() {
					
					UI.alert("保存成功!");
					
				}
			});
			
		});
		
		/*
		 * 删除
		 */
		var row;
		
		$grid.on("click", "a.pageui-button-setting", function() {
			
			 row = $grid.getRowData($(this).closest("tr").index());
			 
		});
		
		/*
		 * 操作按钮组
		 */
		 UI.document.off(".button-tf").on("click.button-tf", "ul.pageui-button-list li", function() {
         	
          	var $this = $(this),
        	    url = $this.attr("url");
          	    
          	    switch ($this.attr("value")) {
		  		    
				       case "tf-param":
						
			    	   require([url], function(obj) {
							
							obj.create({
								taskCode: config.taskCode,
								cfgTfDtlId: row.cfgTfDtlId
							});
							
						});
						
						break;
						
				       case "remove":
							
				    	    UI.confirm({
								text: "确认是否删除该目标表",
								confirm: function() {
									
									$.ajax({
										url: Request.get("config/task/delDestTable"),
										data: {
											cfgTfDtlId: row.cfgTfDtlId,
											taskCode: config.taskCode
										},
										success: function() {
											
											UI.alert("删除成功!");
											
											$grid.onRequest(true);
											
										}
									});
									
								}
							});
							
							break;
			    
			    }
        	
          	  UI.hideCombo();
          	  
    	});
		
	}
	
	return {
		create: create
	};
	
});