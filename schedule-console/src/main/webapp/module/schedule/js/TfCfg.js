var paramObj = {};
$(function() {
	// 取得传过来的任务ID
	paramObj.taskCode = $.params.get('taskCode');

	// 初始化分组信息
	TfCfgJS.init(paramObj);

});

/**
 * 调度任务TF/RELOAD配置JS闭包对象
 */
var TfCfgJS = (function() {
	// 传过来的task主键
	var taskCode = null;
	return {
		// 初始化
		init : function(param) {
			taskCode = param.taskCode;
			TfCfgJS.qryData();
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
		// 查询数据
		qryData : function() {
			if (null != taskCode) {
				$.ajax.submit(null, "qryData", "taskCode=" + taskCode,
						"tfInfo,tfDetail", null);
			}
		},
		// 保存tf配置 未完成
		saveTfCfg : function() {

			var ret1 = $.validate.verifyAll("tfInfo");
			if (ret1 == false) {
				return false;
			}

			// 调用保存接口
			$.ajax.submit("tfInfo", "saveTfCfg", null, null, function(result) {
				var flag = result.get("flag");
				if ("T" == flag) {
					MessageBox.success("操作成功", "保存完成",function(){});
				}else if("I" == flag){
					MessageBox.alert("提示信息", "tf信息中含有非法字符，请检查后重新填写！",function(){});
				}else {
					MessageBox.error("操作失败", "保存失败，原因是："+result.get("msg"),function(){});
				}
			});
		},
		// 对选定行操作
		selectedOperation : function(operation) {
			var table = $.table.get("tfDetailTable");
			var dom = table.getSelected();
			if (dom) {
				if ("delete" == operation) {
					TfCfgJS.deleteDtlCfg(dom);
				} else if ("colCfg" == operation) {
					TfCfgJS.colCfg(dom);
				}
			} else {
				MessageBox.alert("提示信息", "请先选择一个详细配置");
			}
		},
		// 删除详细配置
		deleteDtlCfg : function(dom) {
			MessageBox.confirm("提示框", "确定删除？", function(btn) {
				if ("ok" == btn) {
					var cfgTfDtlId = dom.children().eq(1).html();
					$.ajax.submit(null, "deleteDtlCfg", "cfgTfDtlId="
							+ cfgTfDtlId + "&taskCode=" + taskCode, "tfDetail",
							function(result){
						if ("F" == result.get("flag")) {
							MessageBox.error("操作失败", "删除失败，原因是："+result.get("msg"),function(){});
						}
					});
				}
			}, {
				ok : "是",
				cancel : "否"
			});
		},
		// 配置字段对应关系 未完成
		colCfg : function(dom) {
			var cfgTfDtlId = dom.children().eq(1).html();
			window['Wade_isStopResizeHeight'] = true;
			popupPage("module.schedule.ColRel", null, "taskCode=" + taskCode
					+ "&cfgTfDtlId=" + cfgTfDtlId, "映射字段配置", 800, 530, null);
		},
		// 新增tf详细配置
		addTfDtl : function() {
			resetArea('colInfo', true);
			popupDiv('colInfoDiv', '600', "新增任务参数");
			var $J = jQuery.noConflict();
			$J("button[class='e_button-page-cancel']").hide();
		},
		// 退出DIV
		quitDiv : function() {
			$.closeExternalPopupDiv("colInfoDiv");
		},
		// 保存TF详细配置 未完成
		saveTfDtl : function() {
			var ret1 = $.validate.verifyAll("colInfo");
			if (ret1 == false) {
				return false;
			}

			if (taskCode != null) {
				$.ajax.submit("colInfo", "saveTfDtl", "taskCode=" + taskCode,
						"tfDetail", function(result) {
					  		var flag = result.get("flag");
					  		if("I" == flag){
								MessageBox.alert("提示信息", "tf详细配置信息中含有非法字符，请检查后重新填写！",function(){});
							}else{
								TfCfgJS.quitDiv();
							}
						});
			}
		}
	};
})();