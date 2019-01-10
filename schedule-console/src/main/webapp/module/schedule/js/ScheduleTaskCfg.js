/**
 * 调度任务配置JS闭包对象
 */
var ScheduleTaskCtfJS = (function() {
	// 保存当前修改的是否地市分片和拆分项 用以判断是否更改
	var CUR_SPLIT_RIGION = null;
	var CUR_ITMES = null;
	// 区分新增或者修改操作
	var CUR_OPERATION = null;

	return {
		// 判空字符串和空格
		isNullOrEmpty : function(str) {
			if (str) {
				if ($.trim(str).length > 0) {
					return false;
				}
			}
			return true;
		},
		// 提交查询
		qryTask : function() {
			var taskName = $("#taskName").val();
			var taskGroup = $("#taskGroup").val();
			var taskCode = $("#taskCode").val();
			var taskType = $("#taskType").val();
			if (ScheduleTaskCtfJS.isNullOrEmpty(taskName)
					&& ScheduleTaskCtfJS.isNullOrEmpty(taskGroup)
					&& ScheduleTaskCtfJS.isNullOrEmpty(taskCode)
					&& ScheduleTaskCtfJS.isNullOrEmpty(taskType)) {
				MessageBox.alert("提示信息", "请填写查询条件");
				return;
			}
			 $.beginPageLoading("查询中，请稍候"); 
			$.ajax.submit("queryForm", "qryTask", null, "taskTable", function(){
				$.endPageLoading();
			});
		},
		// 展示详情
		showDetail : function(obj) {
			var dom = obj.parent().parent();
			var taskCode = dom.children().eq(1).html();
			if (!ScheduleTaskCtfJS.isNullOrEmpty(taskCode)) {
				$.ajax.submit(null, "qryTaskDetail",
						"operation=showDetail&taskCode=" + taskCode,
						"taskDtlView,splitView", function() {
							popupDiv('taskDetailsDiv', '800', "任务详情");
							var $J = jQuery.noConflict();
							$J("button[class='e_button-page-cancel']").hide();
						});
			}
		},
		// 配置参数
		paramCfg : function(dom) {
			var taskCode = dom.children().eq(1).html();
			window['Wade_isStopResizeHeight'] = true;
			popupPage("module.schedule.TaskParamCfg", null, "taskCode="
					+ taskCode, "任务参数配置", 700, 530, null);
		},
		// 配置任务分组
		groupCfg : function() {
			popupPage("module.schedule.TaskGroupCfg", null, null, "任务分组配置");
		},
		// 配置分片
		splitCfg : function(dom) {
			var taskCode = dom.children().eq(1).html();
			window['Wade_isStopResizeHeight'] = true;
			popupPage("module.schedule.SplitCfg", null, "taskCode=" + taskCode,
					"任务分片配置", 1000, 530, null);
		},
		// tf配置
		tfCfg : function(dom) {
			var taskCode = dom.children().eq(1).html();
			var taskType = dom.children().eq(9).html();
			taskType = $.trim(taskType);
			if ("tf" == taskType || "reload" == taskType) {
				window['Wade_isStopResizeHeight'] = true;
				popupPage("module.schedule.TfCfg", null,
						"taskCode=" + taskCode, "tf/reload配置", 1000, 530, null);
			} else {
				MessageBox.alert("操作错误", "不是tf或reload类型不需要配置此项");
			}

		},
		// 添加任务
		addTask : function() {
			CUR_OPERATION = "add";
			$("#taskCodeInput").attr("disabled", false);
			resetArea('taskInfoData', true);
			ScheduleTaskCtfJS.reInitGroupSelect("新增任务");
		},
		// 修改任务
		updateTask : function(dom) {
			CUR_OPERATION = "update";
			CUR_SPLIT_RIGION = null;
			CUR_ITMES = null;
			var taskCode = dom.children().eq(1).html();
			ajaxSubmit(null, 'qryTaskDetail', "operation=update&taskCode="
					+ taskCode, "taskInfoData", function(data) {
				CUR_SPLIT_RIGION = data.get("splitRegion");
				CUR_ITMES = data.get("item");
				var type = data.get("type");
				$("#taskCodeInput").attr("disabled", true);
				ScheduleTaskCtfJS.reInitGroupSelect("修改任务");
				if (type == "tf" || type == "complex"){
					$("#isLogInput").val("true");
					$("#isLogInput").attr("disabled",true);
				}else{
					$("#isLogInput").attr("disabled",false);
				}
			},null,{async: true});
		},
		// 删除任务
		deleteTask : function(dom) {
			var taskCode = dom.children().eq(1).html();
			var taskName = dom.children().eq(2).html();
			var content = "确定删除此任务（"+taskName+";"+taskCode+")?";
			MessageBox.confirm("提示框", content, function(btn) {
				if ("ok" == btn) {
					
					$.ajax.submit(null, 'deleteTask', "taskCode=" + taskCode,
							null, function(result) {
								var delFlag = result.get("DEL_FLAG");
								if ("T" == delFlag) {
									MessageBox.success("提示信息", "删除成功",
											function() {
											});
									ScheduleTaskCtfJS.qryTask();
								} else {
									MessageBox.error("错误信息", "删除失败，原因是："+result.get("msg"),
											function() {
											});
								}
							});

				}
			}, {
				ok : "是",
				cancel : "否"
			});
		},
		// 退出弹出框
		quitDiv : function(id) {
			$.closeExternalPopupDiv(id);
		},
		// 保存任务
		saveTask : function() {
			// 先检验文本域（原控件无效）
			var taskGroupCode = $("#taskGroupSel").val();
			if (ScheduleTaskCtfJS.isNullOrEmpty(taskGroupCode)) {
				MessageBox.alert("提示信息", "业务分组不能为空");
				return;
			}

			var ret1 = $.validate.verifyAll("taskInfoData");
			if (ret1 == false) {
				return false;
			}

			// 当修改操作时 校验分片相关信息是否有修改，有则提示
			if (CUR_OPERATION == "update") {
				var itemsInput = $("#itemsInput").val();
				var splitRegionInput = $("#splitRegionInput").val();
				if ($.trim(splitRegionInput) != CUR_SPLIT_RIGION
						|| CUR_ITMES != $.trim(itemsInput)) {
					MessageBox
							.confirm("提示框", "拆分策略有修改，分片相关配置将会被重置，确定修改？",
									function(btn) {
										if ("ok" == btn) {
											ScheduleTaskCtfJS
													.submitTask(CUR_OPERATION,taskGroupCode);
										}
									}, {
										ok : "是",
										cancel : "否"
									});
				} else {
					ScheduleTaskCtfJS.submitTask(CUR_OPERATION, taskGroupCode);
				}
			}
			// 新增时则无需校验
			else {
				ScheduleTaskCtfJS.submitTask(CUR_OPERATION, taskGroupCode);
			}

		},
		// 任务提交
		submitTask : function(operation, taskGroupCode) {
			$("#isLogInput").attr("disabled",false);
			$.ajax.submit("taskInfoData", "saveTask", "operation=" + operation
					+ "&taskGroupCode=" + taskGroupCode, null, function(data) {
				var result = data.get("result");

				if ("T" == result) {
					ScheduleTaskCtfJS.quitDiv('taskInfoDiv');
					ScheduleTaskCtfJS.qryTask();
					MessageBox.success("提示信息", "保存成功", function() {
					});
				} else if ("I" == result) {
					MessageBox.alert("提示信息", "任务信息中含有非法字符，请检查后重新填写！",
							function() {
							});
				} else {
					MessageBox.error("错误信息", "保存失败，原因是：" + data.get("msg"),
							function() {
							});
				}
			});
		},
		// 激活任务
		enable : function(dom) {
			var taskCode = dom.children().eq(1).html();
			$.ajax.submit(null, "enable", "taskCode=" + taskCode, null,
					function(result) {
						if (result && result.get("flag") == "T") {
							ScheduleTaskCtfJS.qryTask();
							MessageBox.success("提示信息", "启用任务成功",function(){});
						} else {
							MessageBox.error("错误信息", "启用任务失败，原因是："+result.get("msg"),function(){});
						}
					});
		},
		// 对选定行操作
		selectedOperation : function(operation) {
			var table = $.table.get("taskTableTable");
			var dom = table.getSelected();
			if (dom) {
				if ("deleteTask" == operation) {
					ScheduleTaskCtfJS.deleteTask(dom);
				} else if ("updateTask" == operation) {
					ScheduleTaskCtfJS.updateTask(dom);
				} else if ("paramCfg" == operation) {
					ScheduleTaskCtfJS.paramCfg(dom);
				} else if ("splitCfg" == operation) {
					ScheduleTaskCtfJS.splitCfg(dom);
				} else if ("tfCfg" == operation) {
					ScheduleTaskCtfJS.tfCfg(dom);
				} else if ("enable" == operation) {
					ScheduleTaskCtfJS.enable(dom);
				}
			} else {
				MessageBox.alert("提示信息", "请先选择一个任务");
			}
		},
		// 重新初始化任务分组下拉菜单
		reInitGroupSelect : function(name) {
			var groupCode = $("#taskGroupStr").val();
			var groupSel = $("#taskGroupSel");
			groupSel.empty();
			var option = document.createElement('option');
			option.value = "";
			option.innerHTML = "请选择任务分组";
			groupSel.append(option);
			$.ajax.submit(null, "reInitGroup", null, null, function(data) {
				groupSel = $("#taskGroupSel");
				for (var i = 0; i < data.length; i++) {
					option = document.createElement('option');
					option.value = data.get(i).get("key");
					option.innerHTML = data.get(i).get("value");
					if (option.value == groupCode) {
						option.selected = "true";
					}
					groupSel.append(option);
				}
			},null,{async: true});
			popupDiv('taskInfoDiv', '750', name);
		},
		//
		checkType : function(obj){
			if (null != obj){
				var type = obj.val();
				if (type == "tf" || type == "complex"){
					$("#isLogInput").val("true");
					$("#isLogInput").attr("disabled",true);
				}else{
					$("#isLogInput").attr("disabled",false);
				}
			}
		}
	};
})();