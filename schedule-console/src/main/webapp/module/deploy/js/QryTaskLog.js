var QryTaskLogJS = (function(){
	return{
		//
		qryLogs : function(){
			 $.beginPageLoading(); 
			ajaxSubmit("queryForm","qryLogs","startTime="+startTime+"&endTime="+endTime,"logs",function(result){
				$.endPageLoading();
				 if (result.get("flag") == "F"){
						MessageBox.error("操作失败",result.get("msg"),function(){});
					}
			});
		},
		showTaskCodes : function(obj){
			var groupCode = obj.val();
			var next = obj.parent().parent().parent().parent().parent().next().children().eq(1).children().children().children().children();
			next.empty();
			ajaxSubmit(null,"showTaskCodes","groupCode="+groupCode,null,function(result){
				if (result && result.length > 0){
					if (result.get(0).get("value") != "请先选择分组"){
						var option = document.createElement('option');
						option.value = "";
						option.innerHTML = "请选择任务编码";
						next.append(option);
					}
						for (var i = 0; i < result.length; i++) {
							
							var option = document.createElement('option');
					    	option.value = result.get(i).get("key");
							option.innerHTML = result.get(i).get("value");
							next.append(option);
						};
				}else{
					var option = document.createElement('option');
					option.value = "";
					option.innerHTML = "该组未配置任务";
					next.append(option);
				}
			});
		},
		//
		qryDetail : function(obj){
			var taskCode = obj.children().eq(1).html();
			var jobId = obj.children().eq(4).html();
			ajaxSubmit(null,"qryDetail","taskCode="+taskCode+"&jobId="+jobId,"details",null);
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
	    //
		sort : function(index,obj, type) {
			var childIndex = "td:nth-child(" +index+")";
			$.sortTable(obj, type, function(tr1, tr2) {
				var tr1Data = $(tr1).children(childIndex).html();
				var tr2Data = $(tr2).children(childIndex).html();
				var result = 0;
				if (tr1Data < tr2Data){
					result = -1;
				}else if (tr1Data == tr2Data){
					result = 0;
				}else{
					result = 1;
				}
				return result;
			}, "desc");
		},
		//
		showDetail : function(obj){
			var content = obj.next().html();
			if (QryTaskLogJS.isBlank(content)){
				return;
			}
			$("#detailDiv").html(content);
	        popupDiv("detailDiv", 505, "异常详情");
		}
	};
})();