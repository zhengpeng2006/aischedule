var OperationLogJS = (function(){
	return {
		qryLog : function(){
			 $.beginPageLoading(); 
			 ajaxSubmit("queryForm","qryLog","startTime="+startTime+"&endTime="+endTime,"operateLogs",function(result){
				$.endPageLoading();
				 if (result.get("flag") == "F"){
					MessageBox.error("操作失败",result.get("msg"),function(){});
				}
			});
		},
		//
		showDetail : function(obj){
			var content = obj.parent().next().html();
			$("#detailDiv").html(content);
	        popupDiv("detailDiv", 505, "操作对象标识详情");
		},
		exportOnclick : function(obj){
			var module = $("#module").val();
			var operType = $("#operType").val();
			var operObj = $("#operObj").val();
			var content = $("#content").val();
			var operator = $("#operator").val();
			var operationClientIp = $("#operationClientIp").val();
			var startTime = $("#startTime").val();
			var endTime = $("#endTime").val();
			var url = window.location.href;
			var index = url.indexOf("?", 0);
			var dest = url.substr(0,index)+"export?opertion=operationExport&module="+module
					+"&operType="+operType
					+"&operObj="+operObj
					+"&content="+content
					+"&operator="+operator
					+"&operationClientIp="+operationClientIp
					+"&startTime="+startTime
					+"&endTime="+endTime;
			window.location.href=dest;
		}
	};
})();