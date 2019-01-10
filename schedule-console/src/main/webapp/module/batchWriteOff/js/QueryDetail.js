var paramObj = {};
$(function() {
    var paramObj = window.dialogArguments;
    QueryDetailJs.init(paramObj);
});
var QueryDetailJs = (function() {
    var regionId = null;
    var runMode = null;
    var billDay = null;
    var taskId = null;
    var billingCycleId = null;
    var timeTicket = null;
    return {
        init : function(param) {
            regionId = param.regionId;
            runMode = param.runMode;
            billDay = param.billDay;
            taskId = param.taskId;
            billingCycleId = param.billingCycleId;
            // 进程和线程数据初始查询
            $.ajax.submit(null, 'loadQueryDetailData', "regionId=" + regionId + "&runMode=" + runMode + "&billDay="
                    + billDay + "&taskId=" + taskId + "&billingCycleId=" + billingCycleId, 'processListTab,threadList',
                    function(result) {
                        QueryDetailJs.refreshTable();
                        QueryDetailJs.timeTicket = setInterval(function() {
                            QueryDetailJs.refreshTable();
                        }, 20000);
                        var flag = true;
                        var tt1 = $("#tt1").children();
                        tt1.each(function() {
                            var state = $(this).children().eq(11).html();
                            if (state != "F") {
                                flag = false;
                            }
                            if (flag) {
                                window.clearInterval(QueryDetailJs.timeTicket);
                            }
                        });
                    }, null);
        },
        refreshTable : function() {
            $.ajax.submit(null, 'getNodeSts', "regionId=" + regionId + "&runMode=" + runMode + "&billDay=" + billDay
                    + "&taskId=" + taskId + "&billingCycleId=" + billingCycleId, '', function(result) {
                var ret = result.get("obj");
                if (ret.get("retValue") == "N") {
                    MessageBox.alert("提示信息", ret.get("retMsg"));
                    return;
                }
                var startDate = ret.get("startDate");
                var endDate = ret.get("endDate");
                var taskName = ret.get("wfFlowName");
                var tt1 = $("#tt1").children();
                var text = "";
                if (!startDate) {
                    // 结点{0}未开始执行。
                    text = taskName + "未开始执行";
                    window.clearInterval(QueryDetailJs.timeTicket);
                } else {
                    if (!endDate) {
                        // 结点{0}正在执行中，开始时间为{1}。
                        text = "结点" + taskName + "正在执行中，开始时间为" + startDate;
                    } else {
                        // 结点{0}执行完成，开始时间为{1}，结束时间为{2}。
                        text = "结点" + taskName + "执行完成，开始时间为" + startDate + "，结束时间为" + endDate;
                    }
                    var flag=true;
                    if (tt1.length > 0) {
                        tt1.each(function() {
                            var state = $(this).children().eq(11).html();
                            if (state != "F") {
                                flag = false;
                            }
                            if (flag) {
                                window.clearInterval(QueryDetailJs.timeTicket);
                            }

                        });
                    }
                }
                $("#promote").html(text);
            }, null);
        }
    };

})();
