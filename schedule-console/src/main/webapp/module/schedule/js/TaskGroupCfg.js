$(function() {
	TaskGroupCfgJS.init();
});

// 任务分组配置页面JS
var TaskGroupCfgJS = (function() {
	return {
		// 初始化
		init : function() {
			TaskGroupCfgJS.qryGroup();
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
		// 查询所有分组
		qryGroup : function() {
			$.ajax.submit(null, "qryGroup", null, "groupInfos", null);
		},
		// 对选定行操作
		selectedOperation : function(operation) {
			var table = $.table.get("groupInfosTable");
			var dom = table.getSelected();
			if (dom) {
				if ("deleteGroup" == operation) {
					TaskGroupCfgJS.deleteGroup(dom);
				} else if ("selectGroup" == operation) {
					TaskGroupCfgJS.selectGroup(dom);
				}

			} else {
				MessageBox.alert("提示信息", "请先选择一个任务分组");
			}
		},
		// 删除分组
		deleteGroup : function(dom) {
			MessageBox.confirm("提示框", "确定删除？", function(btn) {
				if ("ok" == btn) {
					var taskGroupCode = dom.children().eq(1).html();
					// 调用删除接口
					$.ajax.submit(null,"deleteGroup","taskGroupCode="+taskGroupCode,null,function(result){
						if (result && result.get("flag") == "T")
						{
							MessageBox.success("操作成功", "删除任务分组成功", function() {
							});
						}
						else
						{
							MessageBox.error("操作失败", "删除任务分组失败,原因是："+result.get("msg"), function() {
							});
						}
						TaskGroupCfgJS.qryGroup();
					});
				}
			}, {
				ok : "是",
				cancel : "否"
			});
		},
		// 添加分组
		addGroup : function() {
			resetArea('groupDiv', true);
			popupDiv('groupDiv', '500', "新增任务分组");
		},
		// 退出DIV
		quitDiv : function() {
			$.closeExternalPopupDiv("groupDiv");
		},
		// 保存任务分组
		saveGroup : function() {
			var ret1 = $.validate.verifyAll("groupInfoInput");
			if (ret1 == false) {
				return false;
			}
			// 调用保存接口
			$.ajax.submit("groupInfoInput","saveGroup",null,null,function(result){
				var flag = result.get("flag");
				if ("I" == flag){ 
					MessageBox.alert("提示信息","任务信息中含有非法字符，请检查后重新填写！");
				}else {
					TaskGroupCfgJS.qryGroup();
					TaskGroupCfgJS.quitDiv();
				}
			});
		},
		// 关闭弹窗
		quitPage : function() {
			try {
				setPopupReturnValue(null, true, null, null, function() {
				});
			} catch (e) {
				return;
			}
		}
	};
})();