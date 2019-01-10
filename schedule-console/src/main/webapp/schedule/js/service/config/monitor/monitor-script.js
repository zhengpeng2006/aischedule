define(["core", "grid", "form"], function() {
	                                         
	var $page,
	    $grid,
        $form,
        $searchBtn,
        requestParam = {},
        scriptArray,
        ETypeMap =  {
			"SHELL": "shell脚本",
			"JAVACOMMAND": "命令"
        };
	
	/*
	 * 创建页面对象
	 */
	function create(config) {
		
		scriptArray = [];
		
		config.execId && scriptArray.push(config.execId);
		
		var $dialog = new UI.Dialog({
			title: "监控脚本配置",
			width: 700,
			height: 550,
			afterShow: function() {
				
				$page = this.dom.find("div.monitor-script");
				
				$("<div class='page-dialog-form'></div>").appendTo($page);
				
          		$form = new UI.Form({
        			cls: "common-pageui-form",
          			columns: [{
          				   type: "TextInput",
          				   name: "name",
          				   prefix: "脚本名称:"
          			   }
          			]
          		}).appendTo($page.find("div.page-dialog-form"));
          		
          		$searchBtn = $("<div class='searcher-btn'><a class='pageui-btn page-query'>查询</a></div>").appendTo($page.find("div.page-dialog-form"));
          		
          		$grid = new UI.Grid({
          			pager: {
          				url: Request.get("config/monitor/qryMonitorScript"),
          				getParams: function() {
          					
          					return requestParam;
          					
          				},
          				totalFilter: function(data) {
          					
          					return data.data.total;
          					
          				}
          			},
          			loadFilter: function(data) {
          				
          			     return data.data.execInfosItems;
          				
          			},
          			columns: [{
        				checkbox: true,
        				formatter: function(row) {
        					
        					if (scriptArray.length && scriptArray.indexOf(+row.execId) >= 0) {
        						
        						return true;
        						
        					}
        				}
        			}, {
          				field: "name",
          				title: "脚本名称"
          			}, {
          				field: "expr",
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
          				field: "EType",
          				title: "脚本类型",
          				formatter: function(val) {
          					
        					return ETypeMap[val];
        					
        				}
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
        				
        				flag ?  scriptArray.push(row.execId) : scriptArray.splice(scriptArray.indexOf(+row.execId),1);
        				
        			}
          		}).appendTo($page);
				
          		qryMonitorScript(true);
          		
			},
			btns: [{
				text: "新增脚本",
				handler: function() {
					
					require(["service/config/monitor/monitor-script-setting"], function(obj) {
						
						obj.create({
							confirm: function() {
								
								$grid.onRequest(true);
									
							}
						});
						
					});
					
				}
			}, {
				text: "选择脚本",
				handler: function() {

					var _this = this;
					
					if (!scriptArray.length) {
						
						UI.alert("请选择一个脚本!");
						
						return;
						
					}
					
					if (scriptArray.length > 1) {
						
                        UI.alert("只能选择一个脚本!");
						
						return;
						
					}
					
					$.ajax({
						url: Request.get("config/monitor/selectMonitorScript"),
						data: {
							infoId: config.infoId,
							typeId: scriptArray[0]
						},
						success: function() {
							
							config.confirm();
							
							_this.pop(0);
							
						}
					});
					
				}
			}]
		})
          .html("<div class='monitor-script'></div>")
		  .pop(1);
		
		initEvent(config);
		
		return $dialog;
		
	}
	
	function qryMonitorScript(flag) {
		
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
			
			qryMonitorScript();
			
		});
		
		/*
		 * 修改，删除
		 */
		$grid.on("click", "a.pageui-grid-icon", function() {

			var $this = $(this),
			    row = $grid.getRowData($this.closest("tr").index());
		
		    switch ($this.attr("name")) {
		    
			       case "edit":
					
					require(["service/config/monitor/monitor-script-setting"], function(obj) {
						
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
							text: "确认是否删除该脚本",
							confirm: function() {
								
								$.ajax({
									url: Request.get("config/monitor/delMonitorScript"),
									data: {
										execId: row.execId
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