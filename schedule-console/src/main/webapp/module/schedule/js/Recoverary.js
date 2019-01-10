$(function() {
	// 初始化
	RecoverJS.init();

});


var RecoverJS = (function(){
	return {
		init : function(){
			$.ajax.submit(null,"init",null,"switchFalg,errors",null);
		},
		//判空
	    isBlank : function (obj)
	    {
	    	if (obj)
	    	{
	    		obj = obj.replace(/(^\s*)|(\s*$)/g, "");
	    		if (obj.length > 0)
	    		{
	    			return false;
	    		}
	    	}
	    	return true;
	    },
		//切换吃力方式开关
		changeSwitch : function(oper){
			$.ajax.submit(null,"changeSwitch","oper="+oper,"switchFalg",function(result){
				if (result.get("flag") == "F"){
					MessageBox.error("操作失败","更改开关失败，原因是"+result.get("msg"),function(){});
				}
			});
		},
		//查询当前开关状态
		qrySwitch : function(){
			$.ajax.submit(null,"qrySwitchState",null,"switchFalg",null);
		},
		//查询故障信息
		qryErrors : function(){
			$.ajax.submit(null,"qryErrors",null,"errors",null);
		},
		//恢复故障操作
		recover : function(){
			var table = $.table.get("errorsTable");
			var dom = table.getSelected();
			if (dom) {
				var faultServerId = dom.children().eq(1).html();
				$.ajax.submit(null,"recover","faultServerId="+faultServerId,null,function(result){
					if (result.get("flag") == "F"){
						MessageBox.error("操作失败","恢复故障失败，原因是"+result.get("msg"),function(){});
					}else{
						RecoverJS.qryErrors();
					}
				});
			}else{
				MessageBox.alert("提示信息","清选择一个故障",function(){});
			}
		},
		//展示任务信息
		showTaskInfo : function(obj){
			var serverIds = [];
			var serverId = obj.children().eq(1).html();
			var bakServerId = obj.children().eq(6).html();
			serverIds.push(serverId);
			serverIds.push(bakServerId);
			$.ajax.submit(null,"showTaskInfo","serverIds="+serverIds,"TaskInfos",null);
		},
		//显示详情
		showDetail : function(obj){
			var content = obj.next().html();
			if (RecoverJS.isBlank(content)){
				return;
			}
			$("#detailDiv").html(content);
	        popupDiv("detailDiv", 505, "操作结果详情");
		}
		
	};
})();