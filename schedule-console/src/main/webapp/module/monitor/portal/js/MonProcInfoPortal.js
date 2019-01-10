/**
 * 闭包对象,基础配置页面js
 */

var MonProcInfoPortalJs = (function() {
    var LEVEL_ONE = "1";// 一级节点
    var LEVEL_TWO = "2";// 二级节点
    var LEVEL_THREE = "3";// 三级节点
    var interValId = null;// 定时器标识
    var timeTicket = null;
    var timeTicketGrp = null;
    var timingTime = 60000;// 定时时间间隔

    var CUR_SEL_ID = null;// 当前选中节点标识
    var CUR_SEL_LEVEL = null;// 当前选中节点层级
    var $J = jQuery.noConflict();
    var hostLineArr = new Array();
    var flagBtn = 1;

    return {

        // 树初始化后执
        treeInitAfterAction : function() {
            $.tree.get("sysOrgTree").expandByPath("-1");
            // 无此方法
            // $.tree.get("sysOrgTree").selectNode("-1");
        },
        // 点击树节点text
        treeTextClick : function(node) {
            if (null != node) {
                CUR_SEL_LEVEL = node.level;
                CUR_SEL_ID = node.id;
            }

            // 清空定时器
            MonProcInfoPortalJs.clearInterval();

            // 点击系统节点
            if (CUR_SEL_LEVEL == LEVEL_ONE) {

                $J("#hostKpiDiv").hide();
                $J("#processInfoTab").hide();
                $J("#grpHostKpiDiv").hide();
                $J("#showGrpHostDiv").show();
                $J("#fsTab").hide();

                // 展示集群分组信息
                MonProcInfoPortalJs.showGrpHostInfo();
            }
            // 点击分组节点
            else if (CUR_SEL_LEVEL == LEVEL_TWO) {

                // 展示主机KPI，主机详细信息
                $J("#hostKpiDiv").hide();
                $J("#processInfoTab").hide();
                $J("#grpHostKpiDiv").show();// 隐藏分组下主机kpi信息
                $J("#showGrpHostDiv").hide();
                $J("#fsTab").hide();

                var paramStr = "level=" + CUR_SEL_LEVEL + "&treeNodeId=" + CUR_SEL_ID;
                var result = ajaxCheck(null, "loadTableData", paramStr, 'grpHostId', null);
                if(""==result.rscode()){
                    return;
                }
                
                var hostIdIp = result.rscode();
                var hostIdArr = new Array();
                var hostIpArr = new Array();
                var hostIdIpArr = new Array();
                hostIdIpArr = hostIdIp.split("|");
                if (hostIdIpArr.length == 1 && "0" == hostIdIpArr) {
                    $J("#grpHostKpiDiv").hide();
                    return;
                }
                hostIdArr = hostIdIpArr[0].split(",");
                hostIpArr = hostIdIpArr[1].split(",");
                var htmlStr = "";
                var flag = "clickGrp";
                for (var i = 0; i < hostIdArr.length; i++) {
                    var str = "dycDivHost" + (i + 1);
                    htmlStr += "<div id='" + str + "' style='height: 200px;'></div>";
                }

                $J("#grpHostInfoDiv").html(htmlStr);
                for (var i = 0; i < hostIpArr.length; i++) {
                    var dycLineChartHost = "dycLineChartHost" + (i + 1);
                    var LineStr = "#dycDivHost" + (i + 1);
                    var hostIp = hostIpArr[i];
                    var hostId = hostIdArr[i];
                    dycLineChartHost = $J(LineStr).createLine(MonProcInfoPortalJs.getIniOption(flag, hostIp, hostId));
                    hostLineArr[i] = dycLineChartHost;
                }

                MonProcInfoPortalJs.timeTicketGrp = setInterval(function() {

                    var info = "level=" + CUR_SEL_LEVEL + "&treeNodeId=" + CUR_SEL_ID;
                    var result = ajaxCheck(null, "loadKpiFreshData", info, 'kpiInfo', null);
                    var kpiInfo = result.rscode();
                    for (var i = 0; i < kpiInfo.length; i++) {
                        var arr = kpiInfo.get(i);
                        if (arr.length == 0) {
                            continue;
                        }
                        for (var j = 0; j < arr.length; j++) {
                            var kpiCpu = arr.get(j).get("KPI_CPU");
                            var kpiMem = arr.get(j).get("KPI_MEM");
                            var kpiFile = arr.get(j).get("KPI_FILE");
                            var kpiDate = arr.get(j).get("KPI_DATE");

                            // 动态数据接口 addData
                            var dataArr = [ {
                                x : kpiDate,
                                y : kpiCpu
                            }, {
                                x : kpiDate,
                                y : kpiMem
                            }, {
                                x : kpiDate,
                                y : kpiFile
                            } ];
                            // 往线性图标里追加实时数据
                            (hostLineArr[i]).appendData(dataArr);
                        }
                    }

                }, timingTime);
            } else if (CUR_SEL_LEVEL == LEVEL_THREE) {

                // 展示主机KPI，主机详细信息
                $J("#hostKpiDiv").show();
                $J("#processInfoTab").show();
                $J("#grpHostKpiDiv").hide();// 隐藏分组下主机kpi信息
                $J("#showGrpHostDiv").hide();
                $J("#beginDate").val("");
                $J("#endDate").val("");
                $J("#fsTab").show();

                MonProcInfoPortalJs.reSetProcTab();

                // 点击主机，显示主机编码、主机名称、主机Ip信息
                MonProcInfoPortalJs.showHostInfo();
                
                //点击主机的时候先清空应用进程和非应用进程的表格内容
                var table1 = $.table.get("processTabTable");
                table1.cleanRows();//清空表格的内容
                var table2 = $.table.get("procKpiTabTable");
                table2.cleanRows();//清空表格的内容
                
                MonProcInfoPortalJs.refreshHandlerProcess();
                MonProcInfoPortalJs.refreshProcessInfo();

                $.ajax.submit(null, 'loadFsInfo', "&treeNodeId=" + CUR_SEL_ID,
                        "fsTab", function(result) {
                        }, null);
                
                MonProcInfoPortalJs.interValId = setInterval(MonProcInfoPortalJs.refreshHandlerProcess, timingTime);
                var flag = "clickHost";

                // 初始化线形图组件
                var dycLineChart = $J("#dycDiv").createLine(MonProcInfoPortalJs.getIniOption("clickHost"));
                MonProcInfoPortalJs.lineChart = dycLineChart;

                MonProcInfoPortalJs.timeTicket = setInterval(function() {
                    $.ajax.submit(null, 'loadKpiFreshData', "level=" + CUR_SEL_LEVEL + "&treeNodeId=" + CUR_SEL_ID,
                            null, function(result) {
                                var kpiInfo = result.get("kpiInfo");
                                if ("N" == kpiInfo) {
                                    MessageBox.alert("提示信息", "该主机未连接");
                                    // 点击主机清空定时器，重新启动
                                    window.clearInterval(MonProcInfoPortalJs.interValId);
                                    // 清空主机Kpi刷新的定时器
                                    window.clearInterval(MonProcInfoPortalJs.timeTicket);
                                    return;
                                }
                                for (var i = 0; i < kpiInfo.length; i++) {
                                    var kpiCpu = kpiInfo.get(i).get("KPI_CPU");
                                    var kpiMem = kpiInfo.get(i).get("KPI_MEM");
                                    var kpiFile = kpiInfo.get(i).get("KPI_FILE");
                                    var kpiDate = kpiInfo.get(i).get("KPI_DATE");

                                    // 动态数据接口 addData
                                    var dataArr = [ {
                                        x : kpiDate,
                                        y : kpiCpu
                                    }, {
                                        x : kpiDate,
                                        y : kpiMem
                                    }, {
                                        x : kpiDate,
                                        y : kpiFile
                                    } ];

                                    // 往线性图标里追加实时数据
                                    MonProcInfoPortalJs.lineChart.appendData(dataArr);
                                }
                            }, null);
                }, timingTime);
            }
        },
        // 刷新进程信息方法
        refreshHandlerProcess : function() {
            $.ajax.submit(null, 'loadProcessData', "level=" + CUR_SEL_LEVEL + "&treeNodeId=" + CUR_SEL_ID,
                    "processTab", function(result) {
                    }, null);
        },
        refreshProcessInfo : function() {
            $.ajax.submit(null, 'loadProcKpiData', "level=" + CUR_SEL_LEVEL + "&treeNodeId=" + CUR_SEL_ID,
                    "procKpiTab", function(result) {
                    }, null);
        },
        iniAxisData : function(flag, hostId) {
            if ("clickHost" == flag) {
                return MonProcInfoPortalJs.initKpi();
            } else {
                if ("clickGrp" == flag) {
                    return MonProcInfoPortalJs.initKpiGrp(hostId);
                } else {
                    return MonProcInfoPortalJs.getHisKpi();
                }
            }
        },
        // 获取初始化选项
        getIniOption : function(flag, title, hostId) {
            // 定义Y轴初始化坐标数据范围，默认min：0，max:100
            var yAxis = {
                min : 0,
                max : 100,
                name : '%'
            };
            var titleObj = {
                text : title,
                x : 'center'
            };
            var gridObj = {
                x : 30,
                y : 30,
                y2 : 55,
                x2 : 30,
            };

            // 序列初始化选项，就是要显示几条线
            var series = [ {
                name : 'CPU', // 序列显示名称
                lineColor : 'green'
            }, {
                name : 'MEM', // 序列显示名称
                lineColor : 'red'
            }, {
                name : 'FS', // 序列显示名称
                lineColor : 'black'
            } ];

            // 定义动态曲线图初始化选项
            var dycLineOpt = {};
            dycLineOpt.yAxis = yAxis;
            dycLineOpt.series = series;
            dycLineOpt.title = titleObj;
            dycLineOpt.grid = gridObj;
            dycLineOpt.iniAxisData = MonProcInfoPortalJs.iniAxisData(flag, hostId);
            return dycLineOpt;
        },
        qryHostKpiHisInfo : function(obj) {
            // 清空主机Kpi刷新的定时器
            window.clearInterval(MonProcInfoPortalJs.timeTicket);
            // 初始化线形图组件
            var dycLineChart = $J("#dycDiv").createLine(MonProcInfoPortalJs.getIniOption("his"));
        },
        showHostInfo : function() {
            var result = ajaxCheck(null, "loadTableData", "level=" + CUR_SEL_LEVEL + "&treeNodeId=" + CUR_SEL_ID,
                    'hostInfo', null);
            var hostInfo = result.rscode();
            var hostCode = hostInfo.get("hostCode");
            var hostName = hostInfo.get("hostName");
            var hostIp = hostInfo.get("hostIp");
            $("#showHostCode").html(hostCode);
            $("#showHostName").html(hostName);
            $("#showHostIp").html(hostIp);
        },
        initKpi : function() {
            var result = ajaxCheck(null, "loadKpiInitData", "level=" + LEVEL_THREE + "&treeNodeId=" + CUR_SEL_ID,
                    'kpiHis', null);
            var kpiHis = result.rscode();
            return MonProcInfoPortalJs.getKpiData(kpiHis);
        },
        initKpiGrp : function(hostId) {
            var result = ajaxCheck(null, "loadKpiInitData", "level=" + LEVEL_THREE + "&treeNodeId=" + hostId + "_"
                    + LEVEL_THREE, 'kpiHis', null);
            var kpiHis = result.rscode();
            return MonProcInfoPortalJs.getKpiData(kpiHis);
        },
        getHisKpi : function() {
            clearInterval(MonProcInfoPortalJs.timeTicket);
            var result = ajaxCheck("qryForm", "qryHostKpiHisInfo", "level=" + CUR_SEL_LEVEL + "&treeNodeId="
                    + CUR_SEL_ID, 'kpiHis', null);
            var kpiHis = result.rscode();
            return MonProcInfoPortalJs.getKpiData(kpiHis);
        },
        getKpiData : function(kpiHis) {
            var tip1 = "查询起始时间小于开始时间，请重新选择起始时间";
            var tip2 = "查询结束时间大于当前月";
            var tip3 = "查询起始时间大于结束时间";
            if ("A" == kpiHis) {
                MessageBox.alert("提示信息", tip1);
                kpiHis = "|||";
            }
            if ("B" == kpiHis) {
                MessageBox.alert("提示信息", tip2);
                kpiHis = "|||";
            }
            if ("C" == kpiHis) {
                MessageBox.alert("提示信息", tip3);
                kpiHis = "|||";
            }
            /*
             * if(kpiHis==0){ kpiHis="|||"; }
             */
            hisArr = kpiHis.split("|");
            var str0 = hisArr[0];
            var str1 = hisArr[1];
            var str2 = hisArr[2];
            var str3 = hisArr[3];
            // 线1
            var y1 = str0.split(",");
            // 线2
            var y2 = str1.split(",");
            // 线3
            var y3 = str2.split(",");
            // x轴
            var x = str3.split(",");
            var retArr = new Array();
            retArr.push(y1);
            retArr.push(y2);
            retArr.push(y3);
            retArr.push(x);
            return retArr;
        },
        clickRefresh : function() {
            // 清空主机Kpi定时刷新定时器
            window.clearInterval(MonProcInfoPortalJs.timeTicket);
            MonProcInfoPortalJs.lineChart = $J("#dycDiv").createLine(MonProcInfoPortalJs.getIniOption("clickHost"));

            MonProcInfoPortalJs.lineChart.showLoading();

            setTimeout(MonProcInfoPortalJs.refresh, 1000);
        },
        refresh : function() {
            MonProcInfoPortalJs.lineChart = $J("#dycDiv").createLine(MonProcInfoPortalJs.getIniOption("clickHost"));
            MonProcInfoPortalJs.timeTicket = setInterval(function() {
                $.ajax.submit(null, 'loadKpiFreshData', "level=" + CUR_SEL_LEVEL + "&treeNodeId=" + CUR_SEL_ID, null,
                        function(result) {
                            var kpiInfo = result.get("kpiInfo");
                            if ("N" == kpiInfo) {
                                MessageBox.alert("提示信息", "该主机未连接");
                                // 点击主机清空定时器，重新启动
                                window.clearInterval(MonProcInfoPortalJs.interValId);
                                // 清空主机Kpi刷新的定时器
                                window.clearInterval(MonProcInfoPortalJs.timeTicket);
                                return;
                            }
                            for (var i = 0; i < kpiInfo.length; i++) {
                                var kpiCpu = kpiInfo.get(i).get("KPI_CPU");
                                var kpiMem = kpiInfo.get(i).get("KPI_MEM");
                                var kpiFile = kpiInfo.get(i).get("KPI_FILE");
                                var kpiDate = kpiInfo.get(i).get("KPI_DATE");

                                // 动态数据接口 addData
                                var dataArr = [ {
                                    x : kpiDate,
                                    y : kpiCpu
                                }, {
                                    x : kpiDate,
                                    y : kpiMem
                                }, {
                                    x : kpiDate,
                                    y : kpiFile
                                } ];

                                // 往线性图标里追加实时数据
                                MonProcInfoPortalJs.lineChart.appendData(dataArr);
                            }

                        }, null);
            }, timingTime);
            MonProcInfoPortalJs.lineChart.hideLoading();
        },
        showBtn : function(obj) {
            if (obj == flagBtn) {
                $J("#fn").show();
            } else {
                $J("#fn").hide();
            }
            flagBtn = 1 - flagBtn;
            return true;
        },
        // 点击到进程Kpi展示，flagBtn设置为1，刷新按钮隐藏
        reSetProcTab : function() {
            flagBtn = 1;
            $J("#fn").hide();
            MonProcInfoPortalJs.testSwitchTab();
        },
        testSwitchTab : function() {
            // 获取当前标签页标题
            var title = procTab.getCurrentTitle();
            // 按标题切换到令一个标签页
            procTab.switchTo("应用进程信息展示");
        },
        // 点击应用进程cpu_kpi的时候，按照百分比的大小进行排序
        kpiCpuServerSort : function(obj, type) {
            $.sortTable(obj, type, function(tr1, tr2) {
                var tr1Data = $(tr1).children("td:nth-child(7)").html();
                var tr1Comp = tr1Data.split("%")[0];
                var tr2Data = $(tr2).children("td:nth-child(7)").html();
                var tr2Comp = tr2Data.split("%")[0];

                return parseFloat(tr2Comp) - parseFloat(tr1Comp);
            }, "desc");
        },
        //内存和cpu_kpi排序
        sortPercent:function(index,obj,type){
            $.sortTable(obj, type, function(tr1, tr2) {
                var childIndex = "td:nth-child("+ index + ")";
                var tr1Data = $(tr1).children(childIndex).html();
                var tr1Comp = tr1Data.split("%")[0];
                var tr2Data = $(tr2).children(childIndex).html();
                var tr2Comp = tr2Data.split("%")[0];
                return parseFloat(tr2Comp) - parseFloat(tr1Comp);
            }, "desc");
        },
        //排序
        sortStr : function(index,obj, type) {
            $.sortTable(obj, type, function(tr1, tr2) {
                var childIndex = "td:nth-child("+ index + ")";
                var tr1Data = $(tr1).children(childIndex).html();
                var tr2Data = $(tr2).children(childIndex).html();
                var result = 0;
                if (tr1Data < tr2Data){
                    result = -1;
                }else if (tr1Data == tr2Data){
                    result = 0;
                }else{
                    result = 1;
                }
                return result;
            }, "desc");
        },
        sortMath : function(index,obj, type) {
            $.sortTable(obj, type, function(tr1, tr2) {
                var childIndex = "td:nth-child("+ index + ")";
                var tr1Data = $(tr1).children(childIndex).html();
                var tr2Data = $(tr2).children(childIndex).html();
                return parseFloat(tr2Data)-parseFloat(tr1Data);
            }, "desc");
        },
        sort : function(index,obj, type) {
            var childIndex = "td:nth-child(" +index+")";
            $.sortTable(obj, type, function(tr1, tr2) {
                var tr1Data = $(tr1).children(childIndex).html();
                var tr2Data = $(tr2).children(childIndex).html();
                if (!isNaN(tr1Data) && !isNaN(tr2Data)){
                    return parseInt(tr1Data) - parseInt(tr2Data);
                }else{
                    var result = 0;
                    if (tr1Data < tr2Data){
                        result = -1;
                    }else if (tr1Data == tr2Data){
                        result = 0;
                    }else{
                        result = 1;
                    }
                    return result;
                }
            }, "desc");
        },
        refreshGrpHostInfo : function() {
            MonProcInfoPortalJs.showGrpHostInfo();
        },
        showGrpHostInfo : function() {
            // 分组与分组之间用“|”隔开，分组名与主机信息之间用“#”隔开，形如：分组一#主机一信息#主机二信息 | 分组二#主机一信息#主机二信息
            var result = ajaxCheck(null, "loadTableData", "level=" + CUR_SEL_LEVEL, 'grpHostStr', null);
            var groupHostStr = result.rscode();
            var grpHostArr = new Array();
            grpHostArr = groupHostStr.split("|");
            var htmlStr = "";
            for (var i = 0; i < grpHostArr.length; i++) {
                var str = "grpHostDiv" + (i + 1);
                htmlStr += "<div id='" + str
                        + "' style='margin:10px 0 0 0;border-left:0px;border-right:0px' class='c_box'>";
                htmlStr += "</div>";
            }
            $J("#grpHostDiv").html(htmlStr);
            for (var i = 0; i < grpHostArr.length; i++) {
                var str = "grpHostDiv" + (i + 1);
                var hostArr = new Array();
                hostArr = grpHostArr[i].split("#");
                if (hostArr.length == 1) {
                    var spanStr1 = "";
                    spanStr1 += "<span id='" + str + "span0" + "' class='grpSpan'></span><br/>";
                    $("#" + str).html(spanStr1);
                    $("#" + str + "span0").html(hostArr[0]);
                    $("#" + str).css("height", "40px");
                } else {
                    var spanStr = "";
                    spanStr += "<span id='" + str + "span0" + "' class='grpSpan'></span><br/>";
                    var m = 0;
                    for (var j = 1; j < hostArr.length; j++) {
                        var sp = "span" + (j + 1);
                        var pingArr = hostArr[j].split("@");
                        if (pingArr.length == 1) {
                            spanStr += "<span id='" + str + sp + "' class='hostSpan'></span>";
                        } else {
                            spanStr += "<span id='" + str + sp + "' class='hostSpan1'></span>";
                        }
                        m++;
                        if (m % 4 == 0) {
                            spanStr += "<br/><br/>"
                        }
                        if (m % 4 != 0 && j == hostArr.length - 1) {
                            spanStr += "<br/><br/>"
                        }
                    }
                    $("#" + str).html(spanStr);
                    $("#" + str + "span0").html(hostArr[0]);
                    for (var j = 1; j < hostArr.length; j++) {
                        var sp = "span" + (j + 1);
                        var pingArr = hostArr[j].split("@");
                        if (pingArr.length == 1) {
                            $("#" + str + sp).html(hostArr[j]);
                        } else {
                            $("#" + str + sp).html(pingArr[0]);
                        }
                    }
                }
            }
        },
        clearInterval : function() {
            // 清空点击分组，刷新主机Kpi的定时器
            window.clearInterval(MonProcInfoPortalJs.timeTicketGrp);
            // 点击主机清空定时器，重新启动
            window.clearInterval(MonProcInfoPortalJs.interValId);
            // 清空主机Kpi刷新的定时器
            window.clearInterval(MonProcInfoPortalJs.timeTicket);
        },
        showCmdInfo : function(obj) {
            //alert(obj.attr("hostId"));
            $("#showCmd").html(obj.attr("hostId"));
            popupDiv('showCmd',500,'命令');
        },
        showTasksInfo:function(obj){
            var serverCode=obj.attr("serverCode");
            $.ajax.submit(null, 'loadTaskInfo', "serverCode=" + serverCode,
                    "taskTab", function(result) {
                    }, null);
            popupDiv('taskTab',700,'任务详情');
        }

    };
})();