define(["core", "grid", "dialog"], function() {
	                                         
	var $dialog,
	    $grid;
	
	/*
	 * 创建页面对象
	 */
	function create(config) {

		var installRule = config.installRule;
		
		var $dialog = new UI.Dialog({
			title: "安装规则配置",
			width: 700,
			height: 480,
			afterShow: function() {

				$grid = new UI.Grid({
					columns: [{
						field: "packageName",
						title: "需获取的包名"
					}, {
						field: "installPath",
						title: "安装路径"
					}, {
						field: "unzipMethod",
						title: "解压方式"
					}, {
						field: "operation",
						title: "操作",
						formatter: function(val, row) {
							
							return [
								"<a class='pageui-grid-icon icon-edit' name='edit' title='修改'></a>",
							    "<a class='pageui-grid-icon icon-remove' name='remove' title='删除'></a>",
							].join("");
							
						}
					}],
					afterRender: function() {
						
					    this.loadData(transferData(installRule));
						
					}
				}).appendTo(this.dom.find("div.strategy-rule"));
				
				$grid.on("click", "a.pageui-grid-icon", function() {

					var $this = $(this),
					    index = $this.closest("tr").index(),
					    row = $grid.getRowData(index);
				
				    switch ($this.attr("name")) {
				    
					       case "edit":

								require(["service/config/strategy/strategy-rule-setting"], function(obj) {
									
									obj.create({
										bean: row,
										confirm: function(param) {
											
											if (param) {
												
												if (installRule) {

													var ruleData,
												        arr = [];
												   
											        arr = installRule.split(";");

											        arr.splice(index,1,param);   
											       
											        installRule = arr.join(";");
											        
												    save(config, installRule);

												}
												
											}
												
										}
									});
									
								});
							
						   break;
							
					       case "remove":
								
								UI.confirm({
									text: "确认是否删除该安装规则",
									confirm: function() {
										
										if (installRule) {

											var ruleData,
										        arr = [];
										   
									        arr = installRule.split(";");
									        
									        arr.splice(index,1);   
									        
									        installRule =  arr.join(";");
									        
										    save(config, installRule);

										}
										
									}
								});
								
								break;
				    
				    }
				    
				});
				
			},
			btns: [{
				text: "新增",
				handler: function() {
					
                   require(["service/config/strategy/strategy-rule-setting"], function(obj) {
						
						obj.create({
							deployStrategyId: config.deployStrategyId,
							confirm: function(param) {
								
								if (param) {
									
									installRule = installRule ? (installRule + ";" + param) : param;
										
									save(config, installRule);
									
								}
								
							}
						});
						
					});

				}
			}]
		})
          .html("<div class='strategy-rule'></div>")
		  .pop(1);
		
		return $dialog;
		
	}
	
	function transferData(installRule) {
		
	    var data = [],
	        arr = [],
	        list = [];
	
		if (installRule) {
			
			arr = installRule.split(";");
			
			for (i = 0;i<arr.length;i++) {
				
				var list = arr[i].split(":");
				
				data.push({
					packageName: list[0],
					installPath: list[1],
					unzipMethod: list[3]
				});
				
			}
			
		}
		
		return data;

	}
	
    function save(config,ruleData) {
    	
    	$.ajax({
			url: Request.get("config/strategy/saveRules"),
			data: {
				deployStrategyId: config.deployStrategyId,
				data: ruleData
			},
		    success: function(data) {
		    		
		        UI.alert("成功!");

		        $grid.loadData(transferData(ruleData));
		        
		        config.confirm();
		    	
		    }
			
		});
    	
    }
	
	return {
		create: create
	};
	
});