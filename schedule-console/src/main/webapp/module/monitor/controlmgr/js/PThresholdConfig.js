var paramObj = {};
$(function() {
	// 取得传过来的任务ID
	paramObj.infoId = $.params.get('infoId');
	paramObj.showSelect = $.params.get('showSelect');

	// 初始化分组信息
	PThresholdJS.init(paramObj);

});

// 阀值管理配置页面JS
var PThresholdJS = (function() {
	// 取得传过来的任务ID
	var infoId = null;
	var showSelect = false;
	return {
		// 初始化
		init : function(param) {
			infoId = param.infoId;
			showSelect = param.showSelect;
			if (showSelect) {
				$("#configbuttons").css("display", "");
			}
			PThresholdJS.queryOnclick();
			disabledArea("thresholdInput", true);
		},
		// 查询
		queryOnclick : function(obj) {
			$.ajax.submit('queryForm',
					'auto_getThresholdByExprAndName_queryOnclick', null,
					'thresholdInfos', null, null);
		},
		// 删除
		deleteOnclick : function(obj) {
			var table = $.table.get("thresholdInfosTable");
			var dom = table.getSelected();
			if (dom) {
				MessageBox.confirm("提示框", "确定删除？", function(btn) {
					if ("ok" == btn) {
						var thresholdId = dom.children().eq(1).html();
						$.ajax.submit(null, 'auto_delete_deleteOnclick',
								"thresholdId=" + thresholdId, null, function(
										result) {
									var delFlag = result.get("DEL_FLAG");
									if ("F" == delFlag) {
										MessageBox.error("操作错误",
												"错误详情："+result.get("msg"),function(){});
									} else {
										resetArea('thresholdInput', true);
										$("#queryButton").trigger("click");
									}
								});
					}
				}, {
					ok : "是",
					cancel : "否"
				});
			} else {
				MessageBox.error("操作错误", "请选择要删除的记录");
			}
		},
		// 新增弹框
		addInfo : function(operation) {
			if ('add' == operation) {
				resetArea('thresholdInput', true);
			}
			disabledArea("thresholdInput", false);
			$("#editDiv").css("display", "none");
			$("#saveDiv").css("display", "");
		},

		// 修改弹框
		updateInfo : function(obj) {
			if (obj) {
				var thresholdId = obj.children().eq(1).html();
				$.ajax.submit(null, 'auto_getBeanById_updateOnclick',
						"thresholdId=" + thresholdId, "thresholdInput",
						function() {
							disabledArea("thresholdInput", true);
							$("#editDiv").css("display", "");
							$("#saveDiv").css("display", "none");
						});

			}
		},
		// 弹框保存
		infoSave : function() {
			
			var ret1 = $.validate.verifyAll("thresholdInput");
			if (ret1 == false) {
				return false;
			};
			
			if (this.check()) {
				$.ajax.submit('thresholdInput',
						'auto_saveOrUpdate_saveOnclick', null, null,
						function(result) {
							if (result.get("flag") == "F")
							{
								MessageBox.error("操作失败","保存失败，失败详情："+result.get("msg"),function(){});
							}else if(result.get("flag") == "I"){
								MessageBox.alert("提示信息","监控阀值信息中含有非法字符，请检查后重新填写！");
							}
							else
							{
								$("#queryButton").trigger("click");
								MessageBox.success("提示信息", "保存成功");
								PThresholdJS.cancel();
							}
						}, null);
			}
		},
		// 参数检查(文本域)
		check : function() {
			var expr = $("#exprInput").val();
			if (PThresholdJS.isBlank(expr)) {
				MessageBox.error("信息未填写完全", "表达式未填写");
				return false;
			}
			return true;
		},
		// 判空
		isBlank : function(obj) {
			if (obj) {
				obj = obj.replace(/(^\s*)|(\s*$)/g, "");
				if (obj.length > 0) {
					return false;
				}
			}
			return true;
		},
		// 退出弹框
		quitDiv : function() {
			$("#thresholdInput").css("display", "none");
			// $.closeExternalPopupDiv("thresholdInput");
		},
		// 给任务选择阀值配置
		dataSelect : function() {
			if (infoId != null) {
				var table = $.table.get("thresholdInfosTable");
				var dom = table.getSelected();
				if (dom) {

					var thresholdId = dom.children().eq(1).html();
					$.ajax.submit(null, 'auto_saveOrUpdate_dataSelectOnclick',
							"infoId=" + infoId + "&thresholdId=" + thresholdId,
							null, function() {
								// 关闭弹窗
								PThresholdJS.quitPage();
							});

				} else {
					MessageBox.error("操作错误", "请选择一条阀值配置信息");
				}
			}

		},
		// 关闭弹窗
		quitPage : function() {
			try {
				setPopupReturnValue(null, null, true);
			} catch (e) {
				return;
			}
		},
		// 显示表达式详情
		showDetail : function(obj) {
			var tr = obj.parent().parent();
			var thresholdId = tr.children().eq(1).html();
			$.ajax.submit(null, 'auto_getBeanById_detailOnclick',
					"thresholdId=" + thresholdId, "exprDetail", null, null);
			popupDiv('exprDetail', 550, "表达式详情");
		},
		//取消回退至编辑按钮区
		cancel : function() {
			var table = $.table.get("thresholdInfosTable");
			var dom = table.getSelected();
			if (dom) {
				var thresholdId = dom.children().eq(1).html();
				$.ajax.submit(null, 'auto_getBeanById_updateOnclick',
						"thresholdId=" + thresholdId, "thresholdInput",
						function() {
							disabledArea("thresholdInput", true);
							$("#editDiv").css("display", "");
							$("#saveDiv").css("display", "none");
						});
			} else {
				disabledArea("thresholdInput", true);
				$("#editDiv").css("display", "");
				$("#saveDiv").css("display", "none");
			}
		}
	};
})();
