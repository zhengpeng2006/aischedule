$(function() {
	TemplateCfgJS.qryTemplate();
});

var TemplateCfgJS = (function() {
	return {
		qryTemplate : function() {
			$.ajax.submit(null, "qryTemplate", null, "templates", null);
		},
		// 行操作
		operation : function(operation) {
			var table = $.table.get("templatesTable");
			var dom = table.getSelected();
			if (dom) {
				if ("delete" == operation) {
					TemplateCfgJS.deleteTemplate(dom);
				} else if ("update" == operation) {
					TemplateCfgJS.updateTemplate(dom);
				}
			} else {
				MessageBox.alert("提示信息", "请先选择一个模板");
			}
		},
		// 删除模板 未完成
		deleteTemplate : function(dom) {
			var templateId = dom.children().eq(1).html();
			$.ajax.submit(null, "deleteTemplate", "templateId=" + templateId,
					null, function(result) {
						if(result.get("flag") == "F")
						{
							MessageBox.error("操作失败","失败详情："+result.get("msg"),function(){});
						}
						else if(result.get("flag") == "N")
						{
							MessageBox.error("提示信息","该模板有策略引用，无法删除",function(){});
						}else
						{
							TemplateCfgJS.qryTemplate();
						}
					});
		},
		// 修改模板
		updateTemplate : function(dom) {
			var templateId = dom.children().eq(1).html();
			$.ajax.submit(null, "qryTemplateById", "templateId=" + templateId,
					"tamplateInput", function(result) {
						popupDiv('tamplateInputDiv', '800', "修改模板");
					});
		},
		// 新增
		add : function() {
			resetArea('tamplateInput', true);
			popupDiv('tamplateInputDiv', '800', "新增模板");
		},
		// 退出弹框
		quitDiv : function(id) {
			$.closeExternalPopupDiv(id);
		},
		// 保存 未完成
		saveTemplate : function() {
			var ret1 = $.validate.verifyAll("tamplateInput");
			if (ret1 == false) {
				return false;
			}
			
			$.ajax.submit("tamplateInput", "saveTemplate", null, null, function(
					result) {
				if (result.get("flag") == "F") {
					MessageBox.error("操作失败", "保存模板失败，错误原因是:"
							+ result.get("msg"), function() {
					});
				} else if(result.get("flag") == "I"){
					MessageBox.alert("提示信息","模板信息中含有非法字符，请检查后重新填写！");
				}else{
					TemplateCfgJS.qryTemplate();
					TemplateCfgJS.quitDiv("tamplateInputDiv");
				}
			});
		}

	};
})();