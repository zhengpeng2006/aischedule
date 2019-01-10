/**
 * 进程启停JS闭包对象
 */
var PocessCtrlJS = (function() {
	var CUR_FORM = null;//记录当前提交的表单名
	return {
		// 判空字符串和空格
		isNotNullOrEmpty : function(str) {
			if (str) {
				return $.trim(str).length > 0;
			}
			return false;
		},
		// 提交查询
		qryProcess : function(formName) {
			CUR_FORM = formName;
			var flag = true;
				if ("BusiQueryForm" == formName){
					var bGroup = $("#bGroup").val();
					var bTaskCode = $("#bTaskCode").val();
					// 业务查询条件判空标识
					flag = PocessCtrlJS.isNotNullOrEmpty(bGroup)
							&& PocessCtrlJS.isNotNullOrEmpty(bTaskCode);
					if (!flag){
						MessageBox.alert("提示信息", "请填写必要查询条件",function(){});
						return;
					}
				}else if ("hostQueryForm" == formName){
					var hGroup = $("#hGroup").val();
					var hostName = $("#hostName").val();
					var hTaskCode = $("#hTaskCode").val();
					// 主机查询条件判空标识
					flag = PocessCtrlJS.isNotNullOrEmpty(hGroup)
							|| PocessCtrlJS.isNotNullOrEmpty(hostName)
							|| PocessCtrlJS.isNotNullOrEmpty(hTaskCode);
					if (!flag){
						MessageBox.alert("提示信息", "至少填写一个查询条件",function(){});
						return;
					}
				}
				 $.beginPageLoading(); 
					$.ajax.submit(formName, "qryProcess", "formName=" + formName,
							"processTable", function(result){
							$.endPageLoading();
							if (result){
								$("#totalCnt").html(result.get("total"));
							}
					});

		},
		// 操作进程
		operateProcess : function(operationId) {
			var appCodes = [];
			var checkBoxes = $('input:checkbox').each(function() {
				if ($(this).attr("checked")) {
					if ("on" != $(this).val())
					{
						appCodes.push($(this).val());
					}
				}

			});
			if (appCodes.length > 0) {
				$.ajax.submit(null, "operateProcess", "appCodes=" + appCodes
						+ "&operationId=" + operationId, null,
						function(result) {
                            var successMsg = "操作成功数为：" + result.get("successNum") + "<br/>";
                            var failedNum = result.get("failedNum");
                            var failedMsg = "操作失败数为：" + failedNum;
                            //var heartBeat=result.get("checkHeartBeat");
                            if (0 != failedNum) {
                                failedMsg += "<br/>失败详情:<br/>";
                                failedMsg += result.get("failedCode");
                            }
                            /*if(""!=heartBeat){
                                var heartBeatMsg="<br/>存在心跳，需要操作员30秒后操作的应用有：<br/>";
                                heartBeatMsg+=result.get("checkHeartBeat");
                                MessageBox.alert("操作结果", successMsg + failedMsg + heartBeatMsg);
                            }else{
                                MessageBox.alert("操作结果", successMsg + failedMsg);
                            }*/
                            MessageBox.alert("操作结果", successMsg + failedMsg);
                            PocessCtrlJS.qryProcess(CUR_FORM);
						});
			} else {
				MessageBox.alert("提示信息", "请选择一个应用");
			}

		},
		//排序
		sort : function(index,obj, type) {
			$.sortTable(obj, type, function(tr1, tr2) {
				var childIndex = "td:nth-child("+ index + ")";
				var tr1Data = $(tr1).children(childIndex).html();
				var tr2Data = $(tr2).children(childIndex).html();
				var result = 0;
				if (!isNaN(tr1Data) && !isNaN(tr2Data)){
					return parseInt(tr1Data) - parseInt(tr2Data);
				}else{
					if (tr1Data < tr2Data){
						result = -1;
					}else if (tr1Data == tr2Data){
						result = 0;
					}else{
						result = 1;
					}
				}
				return result;
			}, "desc");
		},
		// 展现详情
		showDetail : function(obj) {
			var curRow = obj.parent().parent();
			var appCode = curRow.children().eq(2).html();
			$.ajax.submit(null, "showTaskDetail", "appCode=" + appCode,
					"busiDetail", function() {
						popupDiv('detailDiv', '800', "业务详情");
					});
		},
		//
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
		//主机下拉菜单
		showHosts : function(obj){
			var GroupId = obj.val();
			var host = obj.parent().parent().parent().parent().parent().next().children().eq(1).children().children().children().children();
			ajaxSubmit(null,"showHosts","GroupId="+GroupId,null,function(result){
				host.empty();
				if (result && result.length > 0){
					if (result.get(0).get("value") != "请先选择集群"){
						var option = document.createElement('option');
						option.value = "";
						option.innerHTML = "请选择主机";
						host.append(option);
					}
					
						for (var i = 0; i < result.length; i++) {
							var option = document.createElement('option');
					    	option.value = result.get(i).get("key");
							option.innerHTML = result.get(i).get("value");
							host.append(option);
						};
				}else{
					var option = document.createElement('option');
					option.value = "";
					option.innerHTML = "该组未配置主机";
					host.append(option);
				}
			});
		}
	};
})();