define(["core", "grid"], function() {
	                                         
	var $grid,
        timeArray,
        TTypeMap =  {
			"F": "固定时间",
			"CRON": "周期执行",
			"I": "立即执行"
       };
	
	/*
	 * 创建页面对象
	 */
	function create(config) {
		
		timeArray = [];
		
		config.timeId && timeArray.push(config.timeId);
		
		var $dialog = new UI.Dialog({
			title: "监控时间配置",
			width: 800,
			height: 500,
			afterShow: function() {
				
          		$grid = new UI.Grid({
          			pager: {
          				url: Request.get("config/monitor/qryMonitorTime"),
          				totalFilter: function(data) {
          					
          					return data.data.total;
          					
          				}
          			},
          			loadFilter: function(data) {
          				
          			     return data.data.timeInfosItems;
          				
          			},
          			columns: [{
        				checkbox: true,
        				formatter: function(row) {
        					
        					if (timeArray.length && timeArray.indexOf(+row.timeId) >= 0) {
        						
        						return true;
        						
        					}
        				}
        			}, {
          				field: "expr",
          				title: "表达式"
          			}, {
          				field: "remarks",
          				title: "备注"
          			}, {
          				field: "TType",
          				title: "类型",
                        formatter: function(val, row) {
                        	
                        	return TTypeMap[val];
          					
          				}
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
          			checkOnRow: true,
        			onCheck: function(row, flag, index) {
        				
        				flag ?  timeArray.push(row.timeId) : timeArray.splice(timeArray.indexOf(+row.timeId),1);
        				
        			}
          		}).appendTo(this.dom.find("div.monitor-time"));
				
          		$grid.onRequest(true);
          		
			},
			btns: [{
				text: "新增时间",
				handler: function() {
					
					require(["service/config/monitor/monitor-time-setting"], function(obj) {
						
						obj.create({
							confirm: function() {
								
								$grid.onRequest(true);
									
							}
						});
						
					});
					
				}
			}, {
				text: "选择时间",
				handler: function() {

					var _this = this;
					
					if (!timeArray.length) {
						
						UI.alert("请选择一种时间配置!");
						
						return;
						
					}
					
					if (timeArray.length > 1) {
						
                        UI.alert("只能选择一种时间配置!");
						
						return;
						
					}
					
					$.ajax({
						url: Request.get("config/monitor/selectMonitorTime"),
						data: {
							infoId: config.infoId,
							timeId: timeArray[0]
						},
						success: function() {
							
							config.confirm();
							
							_this.pop(0);
							
						}
					});
					
				}
			}]
		})
          .html("<div class='monitor-time'></div>")
		  .pop(1);
		
		initEvent(config);
		
		return $dialog;
		
	}
	
	/*
	 * 初始化事件
	 */
	function initEvent(config) {
		
		/*
		 * 修改，删除
		 */
		$grid.on("click", "a.pageui-grid-icon", function() {

			var $this = $(this),
			    row = $grid.getRowData($this.closest("tr").index());
		
		    switch ($this.attr("name")) {
		    
			       case "edit":
					
					require(["service/config/monitor/monitor-time-setting"], function(obj) {
						
						obj.create({
							bean: row,
							confirm: function() {
								
								$grid.onRequest(true);
									
							}
						});
						
					});
					
					break;
					
			       case "remove":
						
						UI.confirm({
							text: "确认是否删除该时间",
							confirm: function() {
								
								$.ajax({
									url: Request.get("config/monitor/delMonitorTime"),
									data: {
										timeId: row.timeId
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
		    
		});
		
		/*
		 * 点击获取
		 */
		$grid.on("click", "a.pageui-grid-btn", function() {
			
			var $div = $(this).next();
			
			UI.showPop($div);
			
			UI($div.find(".pageui-scrollbox")).method("resize");
			
		});
		
	}
	
	return {
		create: create
	};
	
});