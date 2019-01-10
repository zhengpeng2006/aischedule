define(["core", "grid", "form"], function() {
	                                         
	var $page,
	    $grid,
        $form,
        $searchBtn,
        requestParam = {},
        thresholdArray;
	
	/*
	 * 创建页面对象
	 */
	function create(config) {
		
		thresholdArray = [];
		
		config.thresholdId && thresholdArray.push(config.thresholdId);
		
		var $dialog = new UI.Dialog({
			title: "监控脚本阀值",
			width: 700,
			height: 550,
			afterShow: function() {
				
				$page = this.dom.find("div.monitor-threshold");
				
				$("<div class='page-dialog-form'></div>").appendTo($page);
				
          		$form = new UI.Form({
        			cls: "common-pageui-form",
          			columns: [{
          				   type: "TextInput",
          				   name: "name",
          				   prefix: "阀值名称:"
          			   }
          			]
          		}).appendTo($page.find("div.page-dialog-form"));
          		
          		$searchBtn = $("<div class='searcher-btn'><a class='pageui-btn page-query'>查询</a></div>").appendTo($page.find("div.page-dialog-form"));
          		
          		$grid = new UI.Grid({
          			pager: {
          				url: Request.get("config/monitor/qryMonitorThreshold"),
          				getParams: function() {
          					
          					return requestParam;
          					
          				},
          				totalFilter: function(data) {
          					
          					return data.data.total;
          					
          				}
          			},
          			loadFilter: function(data) {
          				
          			     return data.data.thresholdInfosItems;
          				
          			},
          			columns: [{
        				checkbox: true,
        				formatter: function(row) {
        					
        					if (thresholdArray.length && thresholdArray.indexOf(+row.thresholdId) >= 0) {
        						
        						return true;
        						
        					}
        				}
        			}, {
          				field: "thresholdName",
          				title: "阀值名称"
          			}, {
          				field: "expr1",
          				title: "表达式",
          				formatter: function(val) {
         					
         					if (val) {
         						
         		                return [
         							    "<span>",
         							    	"<a class='pageui-grid-btn'>点击获取</a>",
         							    	"<div class='popup-content Non-Global' style='line-height: 15px;'>",
         							    		"<div class='pageui-scrollbox'><pre>", val, "</pre></div>",
         							    	"</div>",
         					    		"</span>"
         							].join("");
         						
         					}
         					
         				}
          			}, {
          				field: "expiryDays",
          				title: "有效期限"
          			}, {
          				field: "remarks",
          				title: "备注"
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
        				
        				flag ?  thresholdArray.push(row.thresholdId) : thresholdArray.splice(thresholdArray.indexOf(+row.thresholdId),1);
        				
        			}
          		}).appendTo($page);
				
          		qryMonitorThreshold(true);
          		
			},
			btns: [{
				text: "新增阀值",
				handler: function() {
					
					require(["service/config/monitor/monitor-threshold-setting"], function(obj) {
						
						obj.create({
							confirm: function() {
								
								$grid.onRequest(true);
									
							}
						});
						
					});
					
				}
			}, {
				text: "选择阀值",
				handler: function() {

					var _this = this;
					
					if (!thresholdArray.length) {
						
						UI.alert("请选择一个阀值!");
						
						return;
						
					}
					
					if (thresholdArray.length > 1) {
						
                        UI.alert("只能选择一个阀值!");
						
						return;
						
					}
					
					$.ajax({
						url: Request.get("config/monitor/selectMonitorThreshold"),
						data: {
							infoId: config.infoId,
							thresholdId: thresholdArray[0]
						},
						success: function() {
							
							config.confirm();
							
							_this.pop(0);
							
						}
					});
					
				}
			}]
		})
          .html("<div class='monitor-threshold'></div>")
		  .pop(1);
		
		initEvent(config);
		
		return $dialog;
		
	}
	
	function qryMonitorThreshold(flag) {
		
		var queryForm = [
					     {
					    	 name: flag ? "" : $form.getItem("name").getValue()
					     },
					     {
					    	 name: ""
					     }
					  ];
		
		requestParam = {
			queryForm: JSON.stringify(queryForm)
	    };
		
		$grid.onRequest(true);
		
	}
	/*
	 * 初始化事件
	 */
	function initEvent(config) {
		
		/*
		 * 查询
		 */
		$searchBtn.on("click", "a", function() {
			
			qryMonitorThreshold();
			
		});
		
		/*
		 * 修改，删除
		 */
		$grid.on("click", "a.pageui-grid-icon", function() {

			var $this = $(this),
			    row = $grid.getRowData($this.closest("tr").index());
		
		    switch ($this.attr("name")) {
		    
			       case "edit":
					
					require(["service/config/monitor/monitor-threshold-setting"], function(obj) {
						
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
							text: "确认是否删除该阀值",
							confirm: function() {
								
								$.ajax({
									url: Request.get("config/monitor/delMonitorThreshold"),
									data: {
										thresholdId: row.thresholdId
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
			
			return false;
			
		});
		
	}
	
	return {
		create: create
	};
	
});