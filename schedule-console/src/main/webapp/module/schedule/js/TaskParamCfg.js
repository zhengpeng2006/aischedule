var paramObj = {};
$(function() {
	// 取得传过来的任务ID
	paramObj.taskCode = $.params.get('taskCode');

	// 初始化分组信息
	TaskParamCfgJS.init(paramObj);

});

/**
 * 调度任务参数配置JS闭包对象
 */
var TaskParamCfgJS = (function() {
	// 传过来的task主键
	var taskCode = null;
	return {
		// 初始化
		init : function(param) {
			taskCode = param.taskCode;
			TaskParamCfgJS.qryParam();
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
		// 查询task相关参数
		qryParam : function() {
			if (null != taskCode) {
				$.ajax.submit(null, "qryParam", "taskCode=" + taskCode,
						"taskParams", null);
			} else {
				MessageBox.alert("系统异常", "任务编码未传递至本页面");
			}
		},
		// 对选定行操作
		selectedOperation : function(operation) {
			var table = $.table.get("taskParamsTable");
			var dom = table.getSelected();
			if (dom) {
				if ("deleteParam" == operation) {
					TaskParamCfgJS.deleteParam(dom);
				} else if ("updateParam" == operation) {
					TaskParamCfgJS.updateParam(dom);
				}

			} else {
				MessageBox.alert("提示信息", "请先选择一个任务参数");
			}
		},
		// 删除参数
		deleteParam : function(dom) {
			MessageBox.confirm("提示框", "确定删除？", function(btn) {
				if ("ok" == btn) {
					// 任务
					var paramKey = dom.children().eq(2).html();
					// 调用删除接口
					$.ajax.submit(null, "deleteParam", "taskCode=" + taskCode
							+ "&paramKey=" + paramKey, null, function(result) {
						//根据结果标识DELFLAG给出结果提示
						TaskParamCfgJS.qryParam();
					});
				}
			}, {
				ok : "是",
				cancel : "否"
			});
		},
		//
		updateParam : function(dom) {
			var paramKey = dom.children().eq(2).html();
			$.ajax.submit(null, "qryParam", "taskCode=" + taskCode
					+ "&paramKey=" + paramKey, null, function(result) {
				popupDiv('paramDiv', '600', "修改任务参数");
				var $J = jQuery.noConflict();
				$J("button[class='e_button-page-cancel']").hide();
			});
		},
		//
		addParam : function() {
			resetArea('paramInput', true);
			popupDiv('paramDiv', '600', "新增任务参数");
			var $J = jQuery.noConflict();
			$J("button[class='e_button-page-cancel']").hide();
		},
		// 保存任务分组
		saveParam : function() {
			var ret1 = $.validate.verifyAll("paramInput");
			if (ret1 == false) {
				return false;
			}
			// 调用保存接口
			$.ajax.submit("paramInput","saveParam","taskCode=" + taskCode,null,function(result){
				//根据保存结果表示给出相应提示
				var flag=result.get("flag");
				if (!result || flag == "F")
				{
					MessageBox.error("操作失败", "保存参数失败，原因是："+result.get("msg"), function() {
					});
				}
				else if(flag == "I"){
					MessageBox.alert("提示信息", "参数信息中含有非法字符，请检查后重填！", function() {
					});
				}else{
					TaskParamCfgJS.qryParam();
					TaskParamCfgJS.quitDiv();
				}
			});
		},
		// 退出DIV
		quitDiv : function() {
			$.closeExternalPopupDiv("paramDiv");
		}
	};
})();