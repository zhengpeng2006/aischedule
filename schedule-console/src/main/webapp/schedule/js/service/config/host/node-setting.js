define(["core", "form", "dialog"], function() {
	                                         
	var $dialog,
	    $form;
	
	/*
	 * 创建页面对象
	 */
	function create(config) {
		
        var BEAN = config.bean,
            hostId = config.hostId;
        
		var $dialog = new UI.Dialog({
			title: BEAN ? "修改主机节点" : "新增主机节点",
			width: 420,
			height: 370,
			afterShow: function() {

				$form = new UI.Form({
					data: BEAN ? BEAN : "",
					columns: [{
						   type: "TextInput",
						   name: "nodeCode",
						   prefix: "节点编码:",
						   require: true
					   }, {
						   type: "TextInput",
						   name: "nodeNameNew",
						   prefix: "节点名称:",
						   require: true,
						   afterRender: function() {
							   
							   BEAN && this.setValue(BEAN.nodeName);
							   
						   }
					   }, {
						   type: "ComboBox",
						   name: "deployStrategyId",
						   prefix: "部署策略:",
						   require: true,
						   valueField: "deployStrategyId",
						   textField: "deployStrategyName",
						   url: Request.get("config/strategy/qryStrategies"),
						   getUrlParam: function() {
							   
							   return {
								   queryForm: JSON.stringify([{"name":""},{"name":""}])
							   };
							   
						   },
						   loadFilter: function(data) {
							   
							   return data.data.stategiesItems;
							   
						   }
					   }, {
						   type: "ComboBox",
						   name: "isMonitorNode",
						   prefix: "是否监控节点:",
						   require: true,
						   data: [
						      {
						    	  "text": "是",
						    	  "value": "Y"
						      },
						      {
						    	  "text": "否",
						    	  "value": "N"
						      }
						   ]
					   }, {
						   type: "TextArea",
						   name: "remark",
						   prefix: "备注:"
					   }
					]
				}).appendTo(this.dom.find("div.node-setting"));
				
			},
			btns: [{
				text: "确定",
				handler: function() {
					
					var _this = this, 
					    nodeDetail = [],
					    data = $form.getData();
					
					if (!data) return;
					
					if (BEAN) {
						
						data.nodeId = BEAN.nodeId;
						
						nodeDetail.push(data);
						
						nodeDetail.push(BEAN);
						
					} else {
						
						nodeDetail.push(data);
						
						nodeDetail.push({});
	
					}
					
					$.ajax({
						url: Request.get("config/host/saveNode"),
						data: {
							nodeDetail: JSON.stringify(nodeDetail),
							treeNodeId: hostId
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
          .html("<div class='node-setting'></div>")
		  .pop(1);
		
		return $dialog;
		
	}
	
	return {
		create: create
	};
	
});