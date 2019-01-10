define(["core", "grid", "dialog"], function() {
	                                         
	var $dialog,
	    $grid;
	
	/*
	 * 创建页面对象
	 */
	function create(config) {
		
		var $dialog = new UI.Dialog({
			title: "任务参数确认",
			width: 700,
			height: 460,
			afterShow: function() {

				$grid = new UI.Grid({
					columns: [{
						field: "key",
						title: "参数"
					}, {
						field: "value",
						title: "参数值"
					}]
				}).appendTo(this.dom.find("div.state-param"));
				
				$grid.loadData(config.params);
				
			},
			btns: [{
				text: "确定",
				handler: function() {
					
                   config.confirm();
                   
                   this.pop(0);

				}
			}]
		})
          .html("<div class='state-param'></div>")
		  .pop(1);
		
		return $dialog;
		
	}
	
	return {
		create: create
	};
	
});