/**
 * 闭包对象,基础配置页面js
 */
var MonIndexInfoJs = (function() {
    var LEVEL_ONE = "1";// 一级节点
    var LEVEL_TWO = "2";// 二级节点
    var CUR_SEL_ID = null;// 当前选中节点标识
    var CUR_SEL_LEVEL = null;// 当前选中节点层级
    var timeTicket = null;// 定时器标识
    var $J = jQuery.noConflict();
    var timingTime = 60000;// 定时时间间隔
    var MAX_SEQ_ID=-1;

    return {
        // 树初始化后执行
        treeInitAfterAction : function() {
            $.tree.get("MonIndex").expandByPath("-1");
        },
        // 点击树节点text
        treeTextClick : function(node) {
            // 点击树的时候清空定时器
            window.clearInterval(MonIndexInfoJs.timeTicket);

            if (null != node) {
                CUR_SEL_LEVEL = node.level;
                CUR_SEL_ID = node.id;
            }
            if (CUR_SEL_LEVEL == LEVEL_ONE) {
                $J("#condition").hide();
            } else if (CUR_SEL_LEVEL == LEVEL_TWO) {
                // 当层级为2的时候，查询所有维度，维度显示方式为下拉框选项
                $.ajax.submit(null, 'loadCondData', "&monitorId=" + CUR_SEL_ID, null, function(result) {
                    var array = result.get("V");
                    var str = "";
                    if (array != null && array.length > 0) {
                        for (var i = 0; i < array.length; i++) {
                            var cond = array.get(i).split("@@");
                            if (cond.length == 3) {
                                var optionValue = "";
                                optionValue += "<option value=''>" + "请选择..." + "</option>";
                                var codeValue = cond[2].split("%");
                                for (var j = 0; j < codeValue.length; j++) {
                                    var option = codeValue[j].split("_");
                                    optionValue += "<option value='" + option[0] + "'>" + option[1] + "</option>";
                                }
                                str += "<li class='li'><span class='label'>" + cond[1].split("&")[0]
                                        + ":</span> <span class='e_elements'> <span class='e_select'>"
                                        + "<span><span><select jwcid='@Select' name='" + cond[0]
                                        + "' value='ognl:qryReal." + cond[0] + "' desc='" + cond[1].split("&")[0]
                                        + "' source='' changeValue='true' addDefault='true'"
                                        + "defaultText='root' uiid='qryReal_" + cond[0] + "'>" + optionValue
                                        + "</select>" + " </span></span></span></span></li>";
                            } else {
                                var optionValue = "";
                                optionValue += "<option value=''>" + "请选择..." + "</option>";
                                str += "<li class='li'><span class='label'>" + cond[1].split("&")[0]
                                        + ":</span> <span class='e_elements'> <span class='e_select'>"
                                        + "<span><span><select jwcid='@Select' name='" + cond[0]
                                        + "' value='ognl:qryReal." + cond[0] + "' desc='" + cond[1].split("&")[0]
                                        + "' source='' changeValue='true' addDefault='true'"
                                        + "defaultText='root' uiid='qryReal_" + cond[0] + "'>" + optionValue
                                        + "</select>" + " </span></span></span></span></li>";
                            }

                        }
                    }
                    $J("#dycDiv").hide();
                    $J("#condition").show();
                    $("#cond").html(str);
                }, null);
            }
        },
        showHisInfo : function() {
            $J("#dycDiv").show();
            window.clearInterval(MonIndexInfoJs.timeTicket);
            var flag = "clickHis";
            var result = ajaxCheck(null, "loadIndexInitData", "&monitorId=" + CUR_SEL_ID, 'index', null);
            var index = result.rscode();
            if (index.length > 0) {
                var dycLineChart = $J("#dycDiv").createLine(MonIndexInfoJs.getIniOption(flag, index));
                MonIndexInfoJs.lineChart = dycLineChart;
            }
        },
        showInfo : function() {
            $J("#dycDiv").show();
            window.clearInterval(MonIndexInfoJs.timeTicket);
            // 点击实时查询按钮
            var flag = "clickReal";
            var result = ajaxCheck("", "loadIndexInitData", "monitorId=" + CUR_SEL_ID, 'index', null);
            var index = result.rscode();
            if (index.length > 0) {
                // 初始化线形图组件
                var dycLineChart = $J("#dycDiv").createLine(MonIndexInfoJs.getIniOption(flag, index));
                MonIndexInfoJs.lineChart = dycLineChart;
                MonIndexInfoJs.timeTicket = setInterval(function() {
                    var param = MonIndexInfoJs.getCondition();
                    var result = ajaxCheck(null, "loadKpiFreshData", "param=" + param + "&monitorId=" + CUR_SEL_ID+"&maxSeqId="+MAX_SEQ_ID,
                            'kpiFresh', null);
                    var kpiFresh = result.rscode();
                    if(kpiFresh=="N"){
                        return;
                    }
                    var kpiFresh = result.rscode().get("kpiFresh");
                    MAX_SEQ_ID=result.rscode().get("maxSeqId");
                    if (kpiFresh.length > 1) {
                        var timeArr = kpiFresh.get(0);
                        for (var i = 0; i < timeArr.length; i++) {
                            var dataArr = new Array();
                            var xValue = timeArr.get(i);
                            for (var j = 1; j < kpiFresh.length; j++) {
                                var str = "";
                                var yValue = kpiFresh.get(j).get(i);
                                str += "{ x : '" + xValue + "', y : '" + yValue + "'}";
                                var info = eval("(" + str + ")");
                                dataArr.push(info);
                            }
                            MonIndexInfoJs.lineChart.appendData(dataArr);
                        }
                    }
                }, timingTime);
            }
        },
        // 获取初始化选项
        getIniOption : function(flag, index, title) {
            // 定义Y轴初始化坐标数据范围，默认min：0，max:100
            var yAxis = {
                min : 0,
            };
            var titleObj = {
                text : title,
                x : 'center'
            };
            var gridObj = {
                x : 80,
                y : 30,
                y2 : 55,
                x2 : 30,
            };
            var series = new Array();
            var colorArr = [ 'red', 'green', 'violet', 'blue', 'gray', 'black', 'pink', 'yellow' ];
            for (var i = 1; i < index.length; i++) {
                var obj = index.get(i);
                var str = "";
                str += "{ name : '" + obj + "', lineColor : '" + colorArr[i] + "'}";
                var info = eval("(" + str + ")");
                series.push(info);
            }
            // 定义动态曲线图初始化选项
            var dycLineOpt = {};
            dycLineOpt.yAxis = yAxis;
            dycLineOpt.series = series;
            dycLineOpt.title = titleObj;
            dycLineOpt.grid = gridObj;
            dycLineOpt.iniAxisData = MonIndexInfoJs.iniAxisData(flag, index);
            return dycLineOpt;
        },
        iniAxisData : function(flag, index) {
            if ("clickReal" == flag) {
                return MonIndexInfoJs.initKpi(index);
            } else {
                return MonIndexInfoJs.getHisKpi(index);
            }
        },
        initKpi : function(index) {
            var param = MonIndexInfoJs.getCondition();
            var result = ajaxCheck(null, "loadKpiInitData", "param=" + param + "&monitorId=" + CUR_SEL_ID, 'kpiHis',
                    null);
            var kpiHis = result.rscode();
            if(kpiHis=="N"){
                return MonIndexInfoJs.getKpiData(kpiHis, index);
            }else{
                kpiHis=kpiHis.get("kpiHis");
                MAX_SEQ_ID=result.rscode().get("maxSeqId");
                return MonIndexInfoJs.getKpiData(kpiHis, index);
            }
        },
        // 获取维度信息
        getCondition : function() {
            var child = $("#cond").children();
            var param = "";
            child.each(function() {
                param += $(this).children().eq(1).children().children().children().children().attr("name");
                param += ",";
                param += $(this).children().eq(1).children().children().children().children().val();
                param += ";";

            });
            return param;
        },
        getHisKpi : function(index) {
            var param = MonIndexInfoJs.getCondition();
            var result = ajaxCheck("qryForm", "showHisInfo", "param=" + param + "&monitorId=" + CUR_SEL_ID, 'kpiHis',
                    null);
            var kpiHis = result.rscode();
            return MonIndexInfoJs.getKpiData(kpiHis, index);
        },

        getKpiData : function(kpiHis, index) {
            if (kpiHis == "N") {
                var retArr0 = new Array();
                for (var i = 0; i < index.length; i++) {
                    var ret = new Array();
                    ret.push("");
                    retArr0.push(ret);
                }
                return retArr0;
            } else if (kpiHis == "A") {
                MessageBox.error("提示信息", "开始时间和结束时间都不能为空！");
                var retArr0 = new Array();
                for (var i = 0; i < index.length; i++) {
                    var ret = new Array();
                    ret.push("");
                    retArr0.push(ret);
                }
                return retArr0;
            } else if (kpiHis == "C") {
                MessageBox.error("提示信息", "开始时间不能大于结束时间！");
                var retArr0 = new Array();
                for (var i = 0; i < index.length; i++) {
                    var ret = new Array();
                    ret.push("");
                    retArr0.push(ret);
                }
                return retArr0;
            } else {
                var retArr = new Array();
                if (kpiHis != null && kpiHis.length > 0) {
                    for (var i = 1; i < kpiHis.length; i++) {
                        var arr = new Array();
                        if (kpiHis.get(i).length == 0) {
                            for (var m = 0; m < kpiHis.get(0).length; m++) {
                                arr.push(0);
                            }
                        } else {
                            for (var j = 0; j < kpiHis.get(i).length; j++) {
                                arr.push(kpiHis.get(i).get(j));
                            }
                        }
                        retArr.push(arr);
                    }
                    var arrTime = new Array();
                    for (var n = 0; n < kpiHis.get(0).length; n++) {
                        arrTime.push(kpiHis.get(0).get(n));
                    }
                    retArr.push(arrTime);
                }
                return retArr;
            }
        }
    };

})();