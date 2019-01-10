define(["core", "grid", "form"], function() {
	                                         
	var $page,
	    $grid,
        $form,
        $searchBtn,
        array;
	
	/*
	 * 创建页面对象
	 */
	function create(config) {
		
		array = [];
		
		var $dialog = new UI.Dialog({
			title: "选择备用进程",
			width: 700,
			height: 550,
			afterShow: function() {
				
				$page = this.dom.find("div.app-back-setting");
				
				$("<div class='page-dialog-form'></div>").appendTo($page);
				
          		$form = new UI.Form({
        			cls: "common-pageui-form",
          			columns: [{
          				   type: "ComboBox",
          				   name: "host",
          				   prefix: "主机:",
						   require: true,
          				   valueField: "VALUE",
						   textField: "TEXT",
						   url: Request.get("config/task/qryAllHostInfo"),
						   loadFilter: function(data) {
							   
							   return data.data.hostList;
							   
						   }
          			   }
          			]
          		}).appendTo($page.find("div.page-dialog-form"));
          		
          		$searchBtn = $("<div class='searcher-btn'><a class='pageui-btn page-query'>查询</a></div>").appendTo($page.find("div.page-dialog-form"));
          		
          		$grid = new UI.Grid({
          			pager: {
          				url: Request.get("config/host/qryServerInfosByHostId"),
          				getParams: function() {
          					
          					return {
          					     hostId: $form.getData().host
          				    };
          					
          				},
          				totalFilter: function(data) {
          					
          					return data.data.total;
          					
          				}
          			},
          			loadFilter: function(data) {
          				
          			     return data.data.serverInfos;
          				
          			},
          			columns: [{
        				checkbox: true
        			}, {
          				field: "serverCode",
          				title: "应用编码"
          			}, {
          				field: "serverName",
          				title: "应用名称"
          			}],
          			checkOnRow: true,
        			onCheck: function(row, flag, index) {
        				
        				flag ?  array.push(row.serverCode) : array.splice(array.indexOf(row.serverCode),1);
        				
        			}
          		}).appendTo($page);
				
			},
			btns: [{
				text: "选择",
				handler: function() {

					var _this = this;
					
                    if (!array.length) {
						
						UI.alert("请选择一个进程!");
						
						return;
						
					}
					
					if (array.length > 1) {
						
                        UI.alert("只能选择一个进程!");
						
						return;
						
					}
					
					$.ajax({
						url: Request.get("config/host/saveBackupServerCode"),
						data: {
							serverCode: config.serverCode,
							backupServerCode: array[0]
						},
						success: function() {
							
							array = [];
							
							config.confirm();
							
							_this.pop(0);
							
						}
					});
					
				}
			}]
		})
          .html("<div class='app-back-setting'></div>")
		  .pop(1);
		
		/*
		 * 查询
		 */
		$searchBtn.on("click", "a", function() {
			
			if (!$form.getData()) return;
			
			$grid.onRequest(true);
			
		});
		
		return $dialog;
		
	}
	
	return {
		create: create
	};
	
});