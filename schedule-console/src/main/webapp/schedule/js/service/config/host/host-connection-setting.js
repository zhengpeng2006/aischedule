define(["core", "form", "dialog"], function() {
	                                         
	var $dialog,
	    $form,
	    CON = {
			conType: [
		      {
		    	  "text": "SSH",
		    	  "value": "1",
		      }, {
		    	  "text": "Telnet",
		    	  "value": "2", 
		      }, {
		    	  "text": "Ftp",
		    	  "value": "3",
		      }, {
		    	  "text": "SFTP",
		    	  "value": "4",
		      }
		    ],
		    conPort: {
		    	"1": "22",
		    	"2": "23",
		    	"3": "21",
		    	"4": "22"
		    }
	    };
	
	/*
	 * 创建页面对象
	 */
	function create(config) {

		var $dialog = new UI.Dialog({
			title: "新增主机连接方式",
			width: 420,
			height: 200,
			afterShow: function() {

				$form = new UI.Form({
					columns: [{
						   type: "ComboBox",
						   name: "conId",
						   prefix: "连接类型:",
						   require: true,  
						   data: CON.conType,
						   onChange: function(data) {

							   if (data) {
								   
								   $form.getItem("conPort").setValue(CON.conPort[data.value]);
								   
							   }
						   }
					   }, {
						   type: "Number",
						   name: "conPort",
						   prefix: "连接端口:"
					   }
					]
				}).appendTo(this.dom.find("div.host-connection-setting"));
				
			},
			btns: [{
				text: "确定",
				handler: function() {
					
					var _this = this, 
					    conDetail = [],
					    data = $form.getData();
					
					if (!data) return;
					
					conDetail.push(data);
					
					conDetail.push({});
					
					$.ajax({
						url: Request.get("config/host/saveConModeInfo"),
						data: {
							conDetail: JSON.stringify(conDetail),
							hostId: config.hostId
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
          .html("<div class='host-connection-setting'></div>")
		  .pop(1);
		
		return $dialog;
		
	}
	
	return {
		create: create
	};
	
});