//发布部署页面JS
var DeployJS = (function() {
	var LEVEL_ONE = "1";// 一级节点
	var LEVEL_TWO = "2";// 二级节点

	var CUR_SEL_ID = null;// 当前选中节点标识
	var CUR_SEL_LEVEL = null;// 当前选中节点层级
	var CUR_SEL_NAME = null;// 当前选中节点名称
	var CUR_SEL_OPERATION = null;// 当前操作类型
	var CUR_SEL_NODE_IDS = null;// 记录当前操作的nodeId（在按节点操作时有值）
	var CUL_INTERVAL = null;// 记录当前页面定时器
	var CUL_INTERVAL_TIME = 5000;// 定时器刷新时间

	return {
		treeTextClick : function(node) {
			// 先清除定时器
			clearInterval(CUL_INTERVAL);

			if (null != node) {
				CUR_SEL_LEVEL = node.level;
				CUR_SEL_ID = node.id;
				CUR_SEL_NAME = node.text;
			}

			// 隐藏右侧信息
			if (CUR_SEL_LEVEL == LEVEL_ONE) {
				$("#nodeDiv").css("display", "none");
			}
			// 展示策略下节点信息
			else if (CUR_SEL_LEVEL == LEVEL_TWO) {
				$("#nodeDiv").css("display", "");
				// 展示节点信息
				var strategyId = CUR_SEL_ID.split("_")[0];
				$.ajax.submit(null, 'loadTableData', "strategyName="
						+ CUR_SEL_NAME + "&strategyId=" + strategyId,
						"nodeTable", function(result) {

						}, null);
			}
		},
		// 判空字符串和空格
		isNotNullOrEmpty : function(str) {
			if (str) {
				return $.trim(str).length > 0;
			}
			return false;
		},
		treeInitAfterAction : function() {
			$.tree.get("strategyTree").expandByPath("-1");
		},
		// 主机初始化 未完成
		hostInit : function() {
			// 先清除定时器
			clearInterval(CUL_INTERVAL);
			var stretagyId = CUR_SEL_ID.split("_")[0];
			var nodes = DeployJS.getSelectedDatas();
			if (nodes.length == 0) {
				MessageBox.alert("提示信息", "请选择至少一个节点");
				return;
			}
			CUR_SEL_NODE_IDS = nodes;
			$.ajax.submit(null, "initHost", "strategyId=" + stretagyId
					+ "&nodes=" + nodes, null, function(result) {
				if (result.get("flag") == "F") {
					MessageBox.error("操作失败", "初始化失败，原因是"
							+ result.get("msg"), function() {
					});
				} else if (result.get("flag") == "T") {
					MessageBox.success("操作成功", "初始化开始", function() {
					});
					DeployJS.checkOperationState();
				}
			});
		},
		// 按节点发布
		deployByNodes : function() {
			// 先清除定时器
			clearInterval(CUL_INTERVAL);

			CUR_SEL_OPERATION = "deploy";
			var nodes = DeployJS.getSelectedDatas();
			CUR_SEL_NODE_IDS = nodes;
			if (nodes.length == 0) {
				MessageBox.alert("提示信息", "请选择至少一个节点");
				return;
			}
	    	resetArea('versionInfo',true);
			DeployJS.createPopDiv("versionInfoInput", "请填写新版本信息");
			var $J = jQuery.noConflict();
			$J("button[class='e_button-page-cancel']").hide();

		},
		// 按节点回滚
		rollbackByNodes : function() {
			// 先清除定时器
			clearInterval(CUL_INTERVAL);

			var nodes = DeployJS.getSelectedDatas();
			if (nodes.length == 0) {
				MessageBox.alert("提示信息", "请选择至少一个节点");
				return;
			}
			CUR_SEL_NODE_IDS = nodes;
			var stretagyId = CUR_SEL_ID.split("_")[0];
			$.ajax.submit(null, "showHisVersion", "stretagyId=" + stretagyId,
					"hisVersionTable", function(){
				DeployJS.createPopDiv("hisVersionListDiv", "请选择回滚的版本");
				var $J = jQuery.noConflict();
				$J("button[class='e_button-page-cancel']").hide();
			});
		},
		// 获取选中节点的数据（目前格式是 节点id#版本ID)
		getSelectedDatas : function() {
			var datas = [];
			$('input:checkbox').each(function() {
				if ($(this).attr("checked")) {
					if ("on" != $(this).val()) {
						datas.push($(this).val());
					}
				}
			});
			return datas;
		},
		// 检查版本一致性: 不一致返回true
		checkErrVerion : function(data) {
			var flag = ajaxCheck(null, 'checkVerion', "data=" + data, "flag",
					null);
			return !(flag.rscode() == "T");
		},
		//
		createPopDiv : function(divId, name) {
			// 先清除定时器
			clearInterval(CUL_INTERVAL);

			popupDiv(divId, 750, name);
		},
		//
		closePopDiv : function(divId) {
			$.closeExternalPopupDiv(divId);
		},
		// 保存版本信息后执行操作
		saveVersion : function() {
			// 先清除定时器
			clearInterval(CUL_INTERVAL);
			var ret1 = $.validate.verifyAll("versionInfo");
			if (ret1 == false) {
				return false;
			}
			;
			var param = [];// 后台需要的其他参数信息
			param.push(CUR_SEL_OPERATION);// 操作类型
			param.push(CUR_SEL_ID.split("_")[0]);// 部署策略ID
			param.push(CUR_SEL_NODE_IDS);// 操作节点ID
			$.ajax.submit("versionInfo", "saveVersion", "param=" + param, null,
					function(result) {
						if (result.get("flag") == "F") {
							MessageBox.error("操作失败", "发布失败，原因是"
									+ result.get("msg"), function() {
							});
						} else {
							// 打开定时查询功能
							DeployJS.checkOperationState();
						}
					});
			DeployJS.closePopDiv("versionInfoInput");
		},
		//
		showVersionDetail : function(obj) {
			// 先清除定时器
			clearInterval(CUL_INTERVAL);

			var data = obj.parent().parent().children().eq(0).children().val();
			var versionId = data.split("#")[1];
			if (!DeployJS.isNotNullOrEmpty(versionId) || "null" == versionId) {
				MessageBox.alert("提示信息", "该记录没有版本号");
				return;
			}
			$.ajax.submit(null, "showVersionDetail", "versionId="
					+ versionId, "versionDetail", function() {
				popupDiv("versionDetail", 800, "版本详情");
			});
		},
		// 选择版本后执行回滚操作
		selectVersion : function() {
			// 先清除定时器
			clearInterval(CUL_INTERVAL);
			var table = $.table.get("hisVersionTableTable");
			var dom = table.getSelected();
			if (dom) {
			MessageBox.confirm("确认信息", "确认回滚至此版本？", function(btn) {
				if ("ok" == btn) {
						var versionId = dom.children().eq(1).html();
						var param = [];// 后台需要的其他参数信息
						param.push(versionId);// 版本号
						param.push(CUR_SEL_ID.split("_")[0]);// 部署策略ID
						$.ajax.submit(null, "rollback", "param=" + param+"&nodeIds="+CUR_SEL_NODE_IDS, null,
								function(result) {
									if (result.get("flag") == "F") {
										MessageBox.error("操作失败", "发布失败，原因是"
												+ result.get("msg"),
												function() {
												});
									} else {
										// 打开定时查询功能
										DeployJS.checkOperationState();
									}
									DeployJS.closePopDiv("hisVersionListDiv");
								});
					}
				}, {
					ok : "是",
					cancel : "否"
				});
			} else {
				MessageBox.alert("提示信息", "请选择一个版本");
			}
		},
		// 升级当前策略下所有节点至当前策略版本
		updateToCurVersion : function() {
			var stretagyId = CUR_SEL_ID.split("_")[0];
			$.ajax.submit(null, 'updateToCurVersion', "strategyId="
					+ stretagyId, null, function(result) {
				if (result.get("flag") == "F") {
					MessageBox.error("操作失败", "升级失败，原因是：" + result.get("msg"),
							function() {
							});
				}else{
					DeployJS.checkOperationState();
				}
			});
		},
		// 定时查询（主要用于刷新操作结果）
		checkOperationState : function() {
			var strategyId = CUR_SEL_ID.split("_")[0];
			CUL_INTERVAL = setInterval(function() {
				$.ajax.submit(null, 'loadTableData', "strategyName="
						+ CUR_SEL_NAME + "&strategyId=" + strategyId,
						"nodeTable", function() {

						}, null);
			}, CUL_INTERVAL_TIME);
		},
		//
		showError : function(obj){
			if (obj){
				var text = obj.next().html();
				if (DeployJS.isNotNullOrEmpty(text)){
					MessageBox.alert("错误详情",obj.next().html(),function(){});
				}
			}
		}

	};
})();
