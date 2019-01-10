$(function() {
	TaskOperationJS.init();
});

var TaskOperationJS = (function() {
	return {
		init : function() {
		},
		// 判空字符串和空格
		isNullOrEmpty : function(str) {
			if (str) {
				if ($.trim(str).length > 0) {
					return false;
				}
			}
			return true;
		},
		//展示任务执行详情
		showDetails : function(obj) {
			if (obj){
				var taskCode = obj.children().eq(1).html();
				var jobId = obj.children().eq(7).html();
				ajaxSubmit(null,"showDetails","taskCode="+taskCode+"&jobId="+jobId,"taskBaseLogs",function(){
					
				});
			}
		},
		//查询任务执行信息
		qryTaskInfos : function() {
			$.ajax.submit("taskQuery", "qryTaskInfos", null, "taskInfos", null);
		},
		//启动任务
		startTask : function(obj) {
			var taskCode = null;
			var paramArray = [];
			// 入参可能是taskCode或页面传入标志page之一
			if ("page" == obj) {
				var tbody = $("#paramData");
				tbody.children().each(function() {
					taskCode = $(this).children().eq(1).html();
					var param = {
						"key" : $(this).children().eq(2).html(),
						"value" : $(this).children().eq(3).children().val()
					}
					paramArray.push(param);
				});
			} else {
				taskCode = obj;
			}
			var params = JSON.stringify(paramArray);
			$.ajax.submit(null, "startTaskNow", "taskCode=" + taskCode
					+ "&params=" + params, null, function(result) {
				if (result.get("flag") == "F") {
					MessageBox.error("操作失败", "启动任务失败，原因是：" + result.get("msg"),
							function() {
							});
				} else {
					if ("page" == obj) {
						$.closeExternalPopupDiv("taskParamsDiv");
					}
				}
				TaskOperationJS.refreshTask();
			});
		},
		//展示任务启动参数
		showParam : function(obj) {
			var dom = obj.parent().parent();
			var taskCode = dom.children().eq(1).html();
			$.ajax
					.submit(
							null,
							"showParam",
							"taskCode=" + taskCode,
							"taskParams",
							function(result) {
								if (result.get("flag") == "F") {
									MessageBox.error("系统错误", "查询任务参数异常，原因是"
											+ result.get("msg"), function() {
									})
								} else if (result.get("flag") == "E") {
									MessageBox
											.confirm(
													"提示信息",
													"该任务没有配置参数，是否继续启动？",
													function(btn) {
														if ("ok" == btn) {
															TaskOperationJS
																	.startTask(taskCode);
														}
													}, {
														ok : "是",
														cancel : "否"
													});
								} else {
									popupDiv('taskParamsDiv', '800', "任务参数确认");
								}
							});
		},
		//挂起
		hangOn : function(obj) {
			var dom = obj.parent().parent();
			var taskCode = dom.children().eq(1).html();
			$.ajax.submit(null, "hangOn", "taskCode=" + taskCode, null,
					function(result) {
						if (result.get("flag") == "F") {
							MessageBox.error("操作失败", "挂起任务失败，原因是："
									+ result.get("msg"), function() {
							});
						} 
						TaskOperationJS.refreshTask();
						
					});
		},
		//
		refreshTask : function() {
			TaskOperationJS.qryTaskInfos();
			var table = $.table.get("taskBaseLogsTable");
			table.cleanRows();
		},
		//主机集群关联二级菜单
		getHost : function(obj){
			if (obj){
				var hostGroupCode = obj.val();
				var next = $("#param_hostId");
				next.empty();
				
				if (TaskOperationJS.isNullOrEmpty(hostGroupCode)){
					var option = document.createElement('option');
					option.value = "";
					option.innerHTML = "请选择主机";
					next.append(option);
				}else{
					ajaxSubmit(null,"showHosts","hostGroupCode="+hostGroupCode,null,function(result){
						if (result && result.length > 0){
							var option = document.createElement('option');
							option.value = "";
							option.innerHTML = "请选择主机";
							next.append(option);
							for (var i = 0; i < result.length; i++) {
								var option = document.createElement('option');
						    	option.value = result.get(i).get("key");
								option.innerHTML = result.get(i).get("value");
								next.append(option);
							};
						}else{
							var option = document.createElement('option');
							option.value = "";
							option.innerHTML = "该集群未配置主机";
							next.append(option);
						}
					});
				}
			}
		},
		//
		sort : function(index,obj, type) {
			var childIndex = "td:nth-child(" +index+")";
			$.sortTable(obj, type, function(tr1, tr2) {
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
		},
		//
		qryPro : function() {
			var groupCode = $("#param_hostGroup").val();
			var hostId = $("#param_hostId").val();
			var serverCode = $("#param_serverCode").val();
			if (TaskOperationJS.isNullOrEmpty(groupCode)
					&& TaskOperationJS.isNullOrEmpty(hostId)
					&& TaskOperationJS.isNullOrEmpty(serverCode)){
				MessageBox.alert("提示信息","查询条件不能都为空",function(){});
				return;
			}

			$.ajax.submit("queryFORM", "qryPro", null, "taskProcesses",
					function() {
					var table = $.table.get("taskBaseLogsTable");
					table.cleanRows();
					});
		},
		//
		showTaskPros : function(dom) {
			var serverId = dom.children().eq(1).html();
			$.ajax.submit(null, "showTaskPros", "serverId=" + serverId,
					"taskBaseLogs", function() {
					});
		},
		//
		hangOnServer : function(obj) {
			var dom = obj.parent().parent();
			var serverId = dom.children().eq(1).html();
			$.ajax.submit(null, "hangOnServer", "serverId=" + serverId, null,
					function(result) {
						if (result.get("flag") == "F") {
							MessageBox.error("操作失败", "挂起服务失败，原因是："
									+ result.get("msg"), function() {
							});
						} 
						TaskOperationJS.qryPro();
					});
		},
		//
		resumeServer : function(obj) {
			var dom = obj.parent().parent();
			var serverId = dom.children().eq(1).html();
			$.ajax.submit(null, "resumeServer", "serverId=" + serverId, null,
					function(result) {
						if (result.get("flag") == "F") {
							MessageBox.error("操作失败", "恢复服务失败，原因是："
									+ result.get("msg"), function() {
							});
						}
						TaskOperationJS.qryPro();
						
					});
		},
		//
		showServerInfo : function(obj){
			if (obj){
				var serverId = obj.html();
				if (TaskOperationJS.isNullOrEmpty(serverId)){
					return;
				}else{
					ajaxSubmit(null,"showServerInfo","serverId="+serverId,"serverDetail",function(result){
						if (result.get("flag") == "T"){
					        popupDiv("serverDiv", 600, "服务详情");
						}
					});
				}
			}
		}
	};
})();