/**
 * 闭包对象,基础配置页面js
 */
var MonBusiInfoPortalJs = (function() {
	var LEVEL_ONE = "1";// 一级节点
	var LEVEL_TWO = "2";// 二级节点
	var LEVEL_THREE = "3";// 三级节点

	var CUR_SERVER_CODE = null;// 记录当前应用编码
	var CUR_SEL_ID = null;// 当前选中节点标识
	var CUR_SEL_LEVEL = null;// 当前选中节点层级
	var CUR_SEL_IIMECODE = null;// 当前节点的最小元素标识

	var HAS_SECOND = false;// 是否有下级列表标志
	var BUSI_CHART = null;// 业务量图表
	var SPEED_CHART = null;// 速率图表
	var ERROR_CHART = null;// 错单图表
	var BUSI_INTERVAL = null;// 业务定时器
	var ERROR_INTERVAL = null;// 错单定时器
	var BUSI_INTERVAL_TIME = 10000;// 业务定时器间隔刷新时间

	var NORMAL_DIV = {
		border : '1px solid #BBBBBB',
		background : '#BBBBBB',
		color : 'black'
	};// 普通标签样式

	var HIGHLIGHT_DIV = {
		border : '1px solid #777777',
		background : '#777777',
		color : '#FFFAFA'
	};// 高亮标签样式

	var $J = jQuery.noConflict();

	return {
		// 树初始化后执行
		treeInitAfterAction : function() {
			$.tree.get("sysOrgTree").expandByPath("-1");
		},
		//判空
	    isBlank : function (obj)
	    {
	    	if (obj)
	    	{
	    		obj = obj.replace(/(^\s*)|(\s*$)/g, "");
	    		if (obj.length > 0)
	    		{
	    			return false;
	    		}
	    	}
	    	return true;
	    },
		// 初始化一级列表
		initfirst : function(dataArray) {
			var content = "";
			if (null != dataArray) {

				for (var i = 0; i < dataArray.length; i++) {
					content += "<td><div style='border:1px solid #BBBBBB;height:30px;width:70px;position:relative;background:#BBBBBB' onclick='MonBusiInfoPortalJs.getNext($(this),1)'><div style='position:absolute;left:20px;top:10px'>";
					content += dataArray.get(i);
					content += "</div></div></td>";
				}
			}
			$("#firstRow").html(content);
			var obj = $("#firstRow").children().eq(0).children();// 选择第一个分片模拟点击
			obj.trigger("click");
		},
		// 初始化二级列表
		initSecond : function(firstCode) {
			ajaxSubmit(null, 'getSecondList', "firstCode=" + firstCode,
					null, function(result) {
                        if (null != result) {             
                            var dataArray = result.get("secondArray");
                            var content = "";
                            if (null != dataArray) {
                                for (var i = 0; i < dataArray.length; i++) {
                                    content += "<td><div itemCodeAll='";
                                    content += dataArray.get(i).get("itemCodeAll");
                                    content += "' appCode='";
                                    if (dataArray.get(i).get("appCode")){
                                       content += dataArray.get(i).get("appCode");
                                    }
                                    content += "' style='border:1px solid #BBBBBB;height:30px;width:70px;position:relative;background:#BBBBBB' onclick='MonBusiInfoPortalJs.getNext($(this),2)'><div style='position:absolute;left:20px;top:10px'>";
                                    content += dataArray.get(i).get("itemCode");
                                    content += "</div></div></td>";
                                }
                            }
                            $("#secondRow").html(content);
                            var obj = $("#secondRow").children().eq(0).children();// 选择第一个分片模拟点击
                            obj.trigger("click");
                        }
                    });
		},
		// 点击树节点text
		treeTextClick : function(node) {
			// 先清除定时器
			clearInterval(BUSI_INTERVAL);
			clearInterval(ERROR_INTERVAL);

			if (null != node) {
				CUR_SEL_LEVEL = node.level;
				CUR_SEL_ID = node.id;
			}

			// 刷新区域
			var refreshTarget = null;

			// 点击一级节点展示任务分组
			if (CUR_SEL_LEVEL == LEVEL_ONE) {

			}
			// 点击二级节点展示分组下task
			else if (CUR_SEL_LEVEL == LEVEL_TWO) {

			}
			// 点击三级节点展示各任务下一级菜单列表
			else if (CUR_SEL_LEVEL == LEVEL_THREE) {
			    $("#secondRow").html("");
				$.ajax.submit(null, 'getFirstList', "taskCode=" + CUR_SEL_ID,
						null, function(result) {
							if (null != result) {
								var flag = result.get("hasSecond");// 获取是否有下次菜单的标志并更改此页面的
								if ("Y" == flag) {
									HAS_SECOND = true;
								} else {
									HAS_SECOND = false;
								}
								var dataArray = result.get("firstArray");
								MonBusiInfoPortalJs.initfirst(dataArray);
							}
							$("#cityDetail").css("display", "");
						});
				return;
			}

		},
		// 展示下级步骤信息（有二级菜单则显示二级菜单，没有二级菜单就查询应用编码后显示图表）
		getNext : function(obj, flag) {
			// 先清除定时器
			clearInterval(BUSI_INTERVAL);
			clearInterval(ERROR_INTERVAL);
			//传空清空一次错误内容
			MonBusiInfoPortalJs.showError();
			//然后清除原进程表内容
			var table = $.table.get("processTabTable");
			table.cleanRows();
			MonBusiInfoPortalJs.clearChart("busi");
			MonBusiInfoPortalJs.clearChart("error");
			MonBusiInfoPortalJs.clearChart("speed");

			if (null == CUR_SEL_ID) {
				MessageBox.error("系统出错", "任务标识为空，请联系管理员排查");
			}
			MonBusiInfoPortalJs.shwoClickDiv(obj);
			var itemCode = obj.children().html();
			//是否二级菜单标识 区别一级菜单点击和二级菜单双击操作
			var secondFlag = "F";
			var serverCode = "";
			if (HAS_SECOND && 1 == flag)// 若点击的是一级菜单且下有二级菜单，显示二级菜单
			{
				MonBusiInfoPortalJs.initSecond(itemCode);
				// ++++++++++++++++++++++++此处为清空图表代码
				// 研究中+++++++++++++++++++++++++++++++

			} else// 若点击的是二级菜单或没有下级菜单的一级菜单,则开始显示数据
			{
				var taskCode = CUR_SEL_ID.split("__")[0];
				if (2 == flag)// 若是2级菜单，itemcode获取方式变化
				{
					itemCode = obj.attr("itemCodeAll");
					secondFlag = "T";
					serverCode = obj.attr("appCode");
				}
				CUR_SEL_IIMECODE = itemCode;
				$.ajax.submit(null, 'getServerCode', "taskCode=" + taskCode
						+ "&itemCode=" + itemCode+"&secondFlag="+secondFlag+"&serverCode="+serverCode, "processTab", function(
						result) {
					if (result.get("flag") == "F"){
						MonBusiInfoPortalJs.showError(result.get("msg"));
					}
					else{
						CUR_SERVER_CODE = result.get("serverCode");
						MonBusiInfoPortalJs.showMsg();
					}
				});
			}

		},
		// 显示信息
		showMsg : function() {
			MonBusiInfoPortalJs.changeData();
		},
		// 高亮点击DIV
		shwoClickDiv : function(obj) {
			obj.css(HIGHLIGHT_DIV);
			obj.parent().siblings().children().css(NORMAL_DIV);// 取消其他DIV的高亮效果
		},
		// 初始化图表或更改图表中数据后打开定时器
		changeData : function() {
			if (!CUR_SERVER_CODE){
				return;
			};
			var taskCode = CUR_SEL_ID.split("__")[0];
			$.ajax.submit(null, 'queryHisDataQry', "serverCode="
							+ CUR_SERVER_CODE + "&itemCode=" + CUR_SEL_IIMECODE
							+ "&taskCode=" + taskCode, null,
							function(result) {
								// $.ajax.submit('queryForm','queryHisData',"appCode="+CUR_SERVER_CODE+"&itemCode="+CUR_SEL_IIMECODE+"&taskCode="+taskCode,"processTab",function(result){
						if (result)	{	
						var option = null;
								var speedOpt = null;
								var errorOpt = null;

								if ("T" == result.get("busiFlag")) {
									MonBusiInfoPortalJs.showError("目前没有业务数据");
									option = MonBusiInfoPortalJs
											.initBusiOption();
									speedOpt = MonBusiInfoPortalJs
											.initSpeedOpt();
								} else {
									MonBusiInfoPortalJs.showError();
									option = MonBusiInfoPortalJs
											.initBusiOption(result);
									speedOpt = MonBusiInfoPortalJs
											.initSpeedOpt(result);
								}
								BUSI_CHART = $J("#busiDiv").createLine(option);
								SPEED_CHART = $J("#speedDiv").createLine(
										speedOpt);
								if ("T" == result.get("errorFlag")) {
									MonBusiInfoPortalJs.showError("目前没有错单数据");
									errorOpt = MonBusiInfoPortalJs
											.initErrorOpt();
								} else {
									errorOpt = MonBusiInfoPortalJs
											.initErrorOpt(result);
									MonBusiInfoPortalJs.showError();
								}
								ERROR_CHART = $J("#errorDiv").createLine(
										errorOpt);
								
								//启动定时器
								clearInterval(BUSI_INTERVAL);
								BUSI_INTERVAL = setInterval(function() {
									MonBusiInfoPortalJs.updateBusiData();
								}, BUSI_INTERVAL_TIME);
							}
						});
//			clearInterval(ERROR_INTERVAL);
//			ERROR_INTERVAL = setInterval(function() {
//				MonBusiInfoPortalJs.updateErrorData();
//			}, ERROR_INTERVAL_TIME);
		},
		// 实时更新业务及速率数据（后台刷新主机信息）
		updateBusiData : function() {
			//传空清空一次错误内容
			MonBusiInfoPortalJs.showError();
			var taskCode = CUR_SEL_ID.split("__")[0];
			if (!CUR_SERVER_CODE){
				return;
			}
			$.ajax.submit(null, "getBusiDataQry", "serverCode="
					+ CUR_SERVER_CODE + "&itemCode=" + CUR_SEL_IIMECODE
					+ "&taskCode=" + taskCode, null,
					function(dataArray)
					// $.ajax.submit(null,"getBusiData","serverCode="+CUR_SERVER_CODE+"&itemCode="+CUR_SEL_IIMECODE+"&taskCode="+taskCode,"processTab",function(dataArray)
					{
						if (dataArray) {
							var xArray = dataArray.get("xArray");
							var handleArray = dataArray.get("handleArray");
							var leftArray = dataArray.get("leftArray");
							var speedArray = dataArray.get("speedArray");
							var errorArray = dataArray.get("errorArray");
							for (var i = 0; i < xArray.length; i++) {
								var dataArr = [ {
									y : handleArray.get(i)
								}, {
									x : xArray.get(i),
									y : leftArray.get(i)
								} ];
								BUSI_CHART.appendData(dataArr);
								var dataArr1 = [ {
									x : xArray.get(i),
									y : speedArray.get(i)
								} ];
								SPEED_CHART.appendData(dataArr1);
								
								var dataArr2 = [ {
									x : xArray.get(i),
									y : errorArray.get(i)
								} ];
								ERROR_CHART.appendData(dataArr2);
							}
						}

					});
		},
//		// 实时更新错单数据
//		updateErrorData : function() {
//			var taskCode = CUR_SEL_ID.split()[0];
//			$.ajax.submit(null, "getBusiErrorDataQry", "serverCode="
//					+ CUR_SERVER_CODE + "&itemCode=" + CUR_SEL_IIMECODE
//					+ "&taskCode=" + taskCode, null, function(dataArray)
//			// $.ajax.submit(null,"getBusiErrorData","serverCode="+CUR_SERVER_CODE+"&itemCode="+CUR_SEL_IIMECODE+"&taskCode="+taskCode,null,function(dataArray)
//			{
//				if (dataArray){
//					var errorArray = dataArray.get("errorArray");
//					var errorXArray = dataArray.get("errorXArray");
//					for (var i = 0; i < errorXArray.length; i++) {
//						var dataArr = [ {
//							x : errorXArray.get(i),
//							y : errorArray.get(i)
//						} ];
//						ERROR_CHART.appendData(dataArr);
//					}
//				}
//			});
//		},
		// 查询历史数据
		qryHis : function() {
			var $J = jQuery.noConflict();
			//传空清空一次错误内容
			MonBusiInfoPortalJs.showError();
			// 先清除定时器
			clearInterval(BUSI_INTERVAL);
			clearInterval(ERROR_INTERVAL);
			if (!CUR_SERVER_CODE) {
				MonBusiInfoPortalJs.showError("请给该分片配置服务");
				return;
			}
			var ret1 = $.validate.verifyAll("queryForm");
			if (ret1 == false) {
				return false;
			}
			var taskCode = CUR_SEL_ID.split("__")[0];
			var startTime = MonBusiInfoPortalJs.getValue($dp.$D("startTime"));
			var endTime = MonBusiInfoPortalJs.getValue($dp.$D("endTime"));
			if (MonBusiInfoPortalJs.isBlank(startTime) ){
				MessageBox.alert("提示信息","请输入开始时间",function(){});
				return;
			}
			if (MonBusiInfoPortalJs.isBlank(endTime)){
				MessageBox.alert("提示信息","请输入结束时间",function(){});
				return;
			}
			$.ajax.submit(null, 'queryHisDataQry', "serverCode="
					+ CUR_SERVER_CODE + "&itemCode=" + CUR_SEL_IIMECODE+"&taskCode="+taskCode
					+"&startTime="+startTime+"&endTime="+endTime,
					null, function(result) {
						// $.ajax.submit('queryForm','queryHisData',"serverCode="+CUR_SERVER_CODE+"&itemCode="+CUR_SEL_IIMECODE,"processTab",function(result){
				if (result){	
				if ("F" == result.get("flag")){
					MessageBox.error("操作失败",result.get("msg"),function(){});
				}
				if ("T" == result.get("busiFlag")) {
					MonBusiInfoPortalJs.showError("目前没有业务数据");
						} else {
							var option = MonBusiInfoPortalJs
									.initBusiOption(result);
							var speedOpt = MonBusiInfoPortalJs
									.initSpeedOpt(result);
							BUSI_CHART = $J("#busiDiv").createLine(option);
							SPEED_CHART = $J("#speedDiv").createLine(speedOpt);
							MonBusiInfoPortalJs.showError();
						}
				if ("T" == result.get("errorFlag")) {
					MonBusiInfoPortalJs.showError("目前没有错单数据");
						} else {
							var errorOpt = MonBusiInfoPortalJs
									.initErrorOpt(result);
							ERROR_CHART = $J("#errorDiv").createLine(errorOpt);
							MonBusiInfoPortalJs.showError();
						}
					}
			});

		},
		//获取my97控件的值
		getValue  : function(obj){
			if (typeof  obj == "object"){
				return obj.y + "-" + obj.M + "-" + obj.d +" " + obj.H + ":" + obj.m + ":" + obj.s;
			}else{
				return "";
			}
		},
		// 将后台的JSONArray转化为JS的Array
		transArray : function(array) {
			if (null == array || array.length == 0) {
				return [ "-" ];
			}
			var result = new Array();
			for (var i = 0; i < array.length; i++) {
				result[i] = array.get(i);
			}
			return result;
		},
		// 初始化业务图表
		initBusiOption : function(dataArray) {
			var dycLineOpt = {};
			// 定义Y轴初始化坐标数据范围，默认min：0，max:100
			var yAxis = {
				min : 0
			};
			//图表边距
			 var gridObj = {
		                x : 60,
		                y : 30,
		                y2 : 55,
		                x2 : 30,
		            };
			// 序列初始化选项，就是要显示几条线
			var series = [ {
				name : '业务量', // 序列显示名称
				lineColor : 'green'
			}, {
				name : '积压量', // 序列显示名称
				lineColor : 'red'
			} ];
			// 定义动态曲线图初始化选项
			dycLineOpt.yAxis = yAxis;
			dycLineOpt.series = series;
            dycLineOpt.grid = gridObj;

			if (dataArray) {
				var xArray = dataArray.get("xArray");
				var handleArray = dataArray.get("handleArray");
				var leftArray = dataArray.get("leftArray");
				var y1 = new Array();
				var y2 = new Array();
				var x = new Array();
				y1 = MonBusiInfoPortalJs.transArray(handleArray);
				y2 = MonBusiInfoPortalJs.transArray(leftArray);
				x = MonBusiInfoPortalJs.transArray(xArray);
				var retArr = new Array();
				retArr.push(y1);
				retArr.push(y2);
				retArr.push(x);
				dycLineOpt.iniAxisData = retArr;
			} else {
				var y1 = [];
				y1.push("-");
				// 线2
				var y2 = [];
				y2.push("-");

				// x轴
				var now = new Date();
				var x = [];
				x.push("");

				var retArr = new Array();
				retArr.push(y1);
				retArr.push(y2);
				retArr.push(x);
				dycLineOpt.iniAxisData = retArr;

			}
			return dycLineOpt;
		},
		// 初始化速率图表
		initSpeedOpt : function(dataArray) {
			var dycLineOpt = {};
			//图表边距
			 var gridObj = {
		                x : 40,
		                y : 15,
		                y2 : 50,
		                x2 : 30,
		            };
			// 定义Y轴初始化坐标数据范围，默认min：0
			var yAxis = {
			};
			// 序列初始化选项，就是要显示几条线
			var series = [ {
				name : '处理速率', // 序列显示名称
				lineColor : 'green'
			} ];
			// 定义动态曲线图初始化选项
			dycLineOpt.yAxis = yAxis;
			dycLineOpt.series = series;
			dycLineOpt.grid = gridObj;
			
			if (dataArray) {
				var xArray = dataArray.get("xArray");
				var speedArray = dataArray.get("speedArray");
				var y1 = new Array();
				var x = new Array();
				y1 = MonBusiInfoPortalJs.transArray(speedArray);
				x = MonBusiInfoPortalJs.transArray(xArray);
				var retArr = new Array();
				retArr.push(y1);
				retArr.push(x);
				dycLineOpt.iniAxisData = retArr;
			} else {
				var y1 = [];
				y1.push("-");

				// x轴
				var now = new Date();
				var x = [];
				x.push("");

				var retArr = new Array();
				retArr.push(y1);
				retArr.push(x);
				dycLineOpt.iniAxisData = retArr;

			}
			return dycLineOpt;
		},
		// 初始化错单图表
		initErrorOpt : function(dataArray,maxValue) {
			var dycLineOpt = {};
			// 定义Y轴初始化坐标数据范围，默认min：0，max:500
			var	yAxis = {
						min : 0
					};
			
			 var gridObj = {
		                x : 40,
		                y : 15,
		                y2 : 50,
		                x2 : 30,
		            };
			// 序列初始化选项，就是要显示几条线
			var series = [ {
				name : '错单量', // 序列显示名称
				lineColor : 'green'
			} ];
			// 定义动态曲线图初始化选项
			dycLineOpt.yAxis = yAxis;
			dycLineOpt.series = series;
			dycLineOpt.grid = gridObj;

			if (dataArray) {
				var errorArray = dataArray.get("errorArray");
				var errorXArray = dataArray.get("xArray");
				var y1Array = new Array();
				var xArray = new Array();
				y1Array = MonBusiInfoPortalJs.transArray(errorArray);
				xArray = MonBusiInfoPortalJs.transArray(errorXArray);
				var retArr = new Array();
				retArr.push(y1Array);
				retArr.push(xArray);
				dycLineOpt.iniAxisData = retArr;
			} else {
				var y1 = [];
				y1.push("-");

				// x轴
				var now = new Date();
				var x = [];
				x.push("");

				var retArr = new Array();
				retArr.push(y1);
				retArr.push(x);
				dycLineOpt.iniAxisData = retArr;

			}
			return dycLineOpt;
		},
		//清除图表内容
		clearChart : function(name){
			if ("busi" == name){
				var option = MonBusiInfoPortalJs
				.initBusiOption();
				BUSI_CHART = $J("#busiDiv").createLine(option);
			}else if("speed" == name){
				var speedOpt = MonBusiInfoPortalJs
				.initSpeedOpt();
				SPEED_CHART = $J("#speedDiv").createLine(speedOpt);
			}else if("error" == name){
				var errorOpt = MonBusiInfoPortalJs
				.initErrorOpt();
				ERROR_CHART = $J("#errorDiv").createLine(errorOpt);
			} 
		},
		//展示错误信息
		showError : function(str){
			var content = "";
			if (str){
				content  = "("+str+")";
			}
			$("#errorMsgDiv").html(content);
		}

	};
})();