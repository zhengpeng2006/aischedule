var paramObj = {};
$(function() {
	// 取得传过来的任务ID
	paramObj.taskCode = $.params.get('taskCode');

	// 初始化分组信息
	SplitCfgJS.init(paramObj);
});

// 任务分组配置页面JS
var SplitCfgJS = (function() {
	// 传过来的task主键
	var taskCode = null;
	return {
		init : function(param) {
			taskCode = param.taskCode;
			SplitCfgJS.qrySplit();
		},
		// 判空字符串和空格
		isNotNullOrEmpty : function(str) {
			if (str) {
				if ($.trim(str).length > 0) {
					return true;
				}
			}
			return false;
		},
		//查询分片信息
		qrySplit : function() {
			 $.beginPageLoading(); 
		  if (null != taskCode) {
			ajaxSubmit(null, "qrySplit", "taskCode=" + taskCode,
					"splitInfos", function(result){
				if (result.get("flag") == "T"){
					SplitCfgJS.initServer(result.get("array"));
				}
			});
		   }
		},
		//保存配片配置
		saveSplits : function() {
			var table = $("#splitDatas");
			var rows = table.children();
			var array = [];
			rows.each(function(){
				var taskcode = $(this).children().eq(2).html();
				var taskItem = $(this).children().eq(3).html();
				var serverCode = $(this).children().eq(4).children().val();
				var splitData = {
					"taskCode":	taskcode,
					"taskItem":taskItem,
					"serverCode":serverCode
				};
				array.push(splitData);
			});
			var param = JSON.stringify(array);
			if (array.length > 0){
				$.ajax.submit(null,"saveSplits","dataArray="+param,null,function(){
					SplitCfgJS.quitSplits();
				});
			}
			else
			{
				SplitCfgJS.quitSplits();
			}
		},
		//退出窗体
		quitSplits : function() {
			setPopupReturnValue(null, true, null);
		},
		//关联获取应用编码下拉菜单
		getAppcodeList : function(obj)
		{
			var hostId = obj.val();
			if (SplitCfgJS.isNotNullOrEmpty(hostId)){
				$.ajax.submit(null,"getAppcodeList","hostId="+hostId,null,function(result){
					if (null != result){
						var appCodeSelect = obj.next();
						appCodeSelect.empty();
					    if (result.length == 0)
				    	{
					    	var option = document.createElement('option');
					    	option.value = "";
							option.innerHTML = "没有关联编码";
							appCodeSelect.append(option);
				    	}
					    else
					    {
					    	var option = document.createElement('option');
					    	option.value = "";
							option.innerHTML = "请选择应用编码";
							appCodeSelect.append(option);
					    	for (var i = 0;i<result.length;i++)
							{
								 var option = document.createElement('option');
								 option.value =result.get(i).get("appCode");
								 option.innerHTML =result.get(i).get("appCode");
								 appCodeSelect.append(option);
							}
					    }
						appCodeSelect.attr("disabled","");
					}
				});
			}
		},
		//更改分片配置值
		changeValue : function(obj){
			var selectValue = obj.val();
			obj.parent().prev().children().val(selectValue);
		},
		//清空任务编码
		clearServer : function(){
			$('input:checkbox').each(function() {
				if ($(this).attr("checked") && $(this).val() == 1) {
					$(this).parent().parent().children().eq(4).children().val("");
				}
			});
		},
		//
		initServer : function(result){
			var table = $("#splitDatas");
			var rows = table.children();
			//若没有内容 则返回
			if (!rows || rows.length == 0){
				return;
			}
			//查询结果
				if (result){
					var serverCodes;
					rows.each(function(){
						var hostId = $(this).children().eq(5).children().eq(0).val();
						if (SplitCfgJS.isNotNullOrEmpty(hostId)){
							serverCodes = result.get(hostId);
							if (serverCodes && serverCodes.length > 0){
								$(this).children().eq(5).children().eq(1).empty();
								var serverCode = $(this).children().eq(4).children().val();
								var option = document.createElement('option');
						    	option.value = "";
								option.innerHTML = "请选择应用编码";
								$(this).children().eq(5).children().eq(1).append(option);
								for(var i = 0;i < serverCodes.length;i++){
									 var option = document.createElement('option');
									 option.value =serverCodes.get(i);
									 option.innerHTML =serverCodes.get(i);
									 if (serverCode == serverCodes.get(i)){
										 option.selected = true;
									 }
									 $(this).children().eq(5).children().eq(1).append(option);
								}
							}
						}
					});
				}
				$.endPageLoading();
		},
		//排序
		sort : function(index,obj, type) {
			$.sortTable(obj, type, function(tr1, tr2) {
				var childIndex = "td:nth-child("+ index + ")";
				var tr1Data = $(tr1).children(childIndex).html();
				var tr2Data = $(tr2).children(childIndex).html();
				if (!isNaN(tr1Data) && !isNaN(tr2Data)){
					return parseInt(tr1Data) - parseInt(tr2Data);
				}else{
					var result = 0;
					if (tr1Data < tr2Data){
						result = -1;
					}else if (tr1Data == tr2Data){
						result = 0;
					}else{
						result = 1;
					}
					return result;
				}
			}, "desc");
		}
	};
})();