var StrategyCfgJS = (function() {
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
		// 查询策略
		query : function() {
			$.ajax
					.submit("queryForm", "qryStrategies", null, "stategies",
							null);
		},
		//
		add : function() {
			resetArea('strategyInfo', true);
			popupDiv('strategyInputDiv', '750', "新增部署策略");
		},
		// 删除策略
		deleteStrategy : function(dom) {
			var deployStrategyId = dom.children().eq(1).html();
			MessageBox.confirm("提示框", "确定删除？", function(btn) {
				if ("ok" == btn) {
					$.ajax.submit(null, "deleteStrategy", "deployStrategyId="
							+ deployStrategyId, null, function(result) {
						if (result.get("flag") == "F") {
							MessageBox.error("操作失败", "失败原因："
									+ result.get("msg"), function() {
							});
						} else if (result.get("flag") == "N") {
							MessageBox.alert("提示信息", "该策略有节点应用，无法删除",function(){});
						}
						StrategyCfgJS.query();
					});
				}
			}, {
				ok : "是",
				cancel : "否"
			});
		},
		// 行操作
		operation : function(operation) {
			var table = $.table.get("stategiesTable");
			var dom = table.getSelected();
			if (dom) {
				if ("delete" == operation) {
					StrategyCfgJS.deleteStrategy(dom);
				} else if ("update" == operation) {
					StrategyCfgJS.updateStategy(dom);
				}
			} else {
				MessageBox.alert("提示信息", "请先选择一个策略");
			}
		},
		// 修改策略
		updateStategy : function(dom) {
			var deployStrategyId = dom.children().eq(1).html();
			$.ajax.submit(null, "qryStrategyById", "deployStrategyId="
					+ deployStrategyId, "strategyInfo", function() {
				popupDiv('strategyInputDiv', '750', "修改部署策略");
			});
		},
		// 退出弹框
		quitDiv : function(id) {
			$.closeExternalPopupDiv(id);
		},
		// 保存策略
		saveStrategy : function() {
			var ret1 = $.validate.verifyAll("strategyInfo");
			if (ret1 == false) {
				return false;
			}

			$.ajax.submit("strategyInfo", "saveStrategy", null, null, function(
					result) {
				if (result.get("flag") == "F") {
					MessageBox.error("操作失败", "保存部署策略失败，错误原因是:"
							+ result.get("msg"), function() {
					});
				}else if(result.get("flag")=="I"){
					MessageBox.alert("提示信息","部署策略信息中含有非法字符，请检查后重新填写！");
				}else {
					StrategyCfgJS.query();
					StrategyCfgJS.quitDiv("strategyInputDiv");
				}
			});
		},
		// 管理模板
		templateCfg : function() {
			window['Wade_isStopResizeHeight'] = true;
			popupPage("module.deploy.TemplateCfg", null, null, "启停模板管理", 1000,
					530, null);
		},
		//配置规则
		ruleCfg : function(dom){
			if (!dom){
				return;
			}
			var deployStrategyId = dom.parent().parent().children().eq(1).html();
			var rule = dom.parent().prev().html();
			//将空则转换成空字符串
			if (!rule){
				rule = "";
			}
			if (!deployStrategyId){
				deployStrategyId ="";
			}
			$("#idRecord").val(deployStrategyId);
			$.ajax.submit(null, "ruleCfg", "rule="
					+ rule, "rules", function() {
				popupDiv('ruleDiv', '750', "配置安装规则");
			});
		},
		//添加一行
		addRuleRow : function(){
			var tBody = $("#ruleBody");
			var newRow = tBody.children(":last").clone();
			if (StrategyCfgJS.isNullOrEmpty(newRow.html())){
				var defaultValue = '<td style="display: none">1</td>';
				defaultValue += '<td class="e_left wrap">';
				defaultValue += '<input type="text" name="packageName" id="packageName" value="" onkeypress="var e=$.event.fix(event);if (e.which == 13) {$.textfield.focusNextRequiredField(e);e.stop();return false;}" desc="需获取的包名" uiid="rulesItem_packageName" style="width:100%"></td>';
				defaultValue += '<td class="e_left wrap">';
				defaultValue += '<input type="text" name="installPath" id="installPath" value="" onkeypress="var e=$.event.fix(event);if (e.which == 13) {$.textfield.focusNextRequiredField(e);e.stop();return false;}" desc="安装路径" uiid="rulesItem_installPath" style="width:100%"></td>';
				defaultValue += '<td class="e_left wrap">';
				defaultValue += '<select name="unzipMethod" id="unzipMethod" type="select-one" uitype="select" editable="true" uiid="rulesItem_unzipMethod"><option value="jar" title="JAR" >JAR</option><option value="tar.gz" title="TAR.GZ">TAR.GZ</option><option value="tar" title="TAR">TAR</option><option value="plain" title="不解压" selected="true">不解压</option></select>	</td>';
				tBody.html("<tr>"+defaultValue+"</tr>");
			}else{
				tBody.append("<tr>"+newRow.html()+"</tr>");
				newRow = tBody.children(":last");
				var index = parseInt(newRow.children().eq(0).html());
				newRow.children().eq(0).html(index + 1);
				newRow.children().eq(1).children().val("");
				newRow.children().eq(2).children().val("");
				newRow.children().eq(3).children().val("plain");
			}
			
		},
		//删除一行
		deleteRuleRow : function(){
			var table = $.table.get("rulesTable");
			var dom = table.getSelected();
			if (dom) {
				table.deleteRow(dom,true);
			} else {
				MessageBox.alert("提示信息", "请先选择一个安装规则");
			}
		},
		//保存规则
		saveRules : function(){
			var table = $.table.get("rulesTable");
			var tBody = $("#ruleBody");
			var rows = tBody.children();
			var deployStrategyId = $("#idRecord").val();
			var data = "";
			//以下两个为循环判断空的标志,值为空时为true 
			var srcFlag = false;
			var destFalg = false;
			rows.each(function(){
				var flag = table.isDeleted($(this).children().eq(0).html());
				if (!flag){
					//如果没有策略ID则传入
					if(!deployStrategyId){
						deployStrategyId = $(this).children().eq(1).html();
					}
					var src = $(this).children().eq(1).children().val();
					if (StrategyCfgJS.isNullOrEmpty(src)){
						srcFlag = true;
					}
					data += src;
					data += ":";
					var dest = $(this).children().eq(2).children().val();
					if (StrategyCfgJS.isNullOrEmpty(dest)){
						MessageBox.alert("提示信息","安装路径不能为空",function(){});
						destFalg = false;
					}
					data += dest;
					data += ":";
					var method = $(this).children().eq(3).children().val();
					if ("plain" == method){
						data += "N";
						data += ":";
					}else{
						data += "Y";
						data += ":";
					}
					data += $(this).children().eq(3).children().val();
					data += ";";
				}
			});
			
			if (srcFlag){
				MessageBox.alert("提示信息","需获取包名不能为空",function(){});
				return;
			};
			
			if (destFalg){
				MessageBox.alert("提示信息","安装路径不能为空",function(){});
				return;
			}
			if (!deployStrategyId){
				deployStrategyId = "";
			};
			ajaxSubmit(null,"saveRules","deployStrategyId="+deployStrategyId+"&data="+data,null,function(result){
				if (result.get("flag") == "F"){
					MessageBox.error("操作失败","保存安装规则失败，失败原因是："+result.get("msg"),function(){});
				}else if(result.get("flag")=="I"){
					MessageBox.alert("提示信息","安装规则中含有非法字符，请检查后重新填写！");
				}else{
					StrategyCfgJS.query();
					StrategyCfgJS.quitDiv("ruleDiv");
				}
			});
		}

	};
})();