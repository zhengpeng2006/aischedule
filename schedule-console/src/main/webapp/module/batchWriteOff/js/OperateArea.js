var paramObj = {};
$(function() {
    paramObj.regionId = $.params.get('regionId');
    paramObj.runMode = $.params.get('runMode');
    paramObj.billDay = $.params.get('billDay');
    paramObj.taskId = $.params.get('taskId');
    paramObj.billingCycleId = $.params.get('billingCycleId');
    paramObj.state = $.params.get('state');
    OperateAreaJS.init(paramObj);
    OperateAreaJS.closeRight();
});
var OperateAreaJS = (function() {
    var regionId = null;
    var runMode = null;
    var billDay = null;
    var taskId = null;
    var billingCycleId = null;
    var state = null;
    return {
        init : function(param) {
            state = param.state;
            taskId = param.taskId;
            regionId = param.regionId;
            runMode = param.runMode;
            billDay = param.billDay;
            billingCycleId = param.billingCycleId;
            var result = ajaxCheck(null, "qryStartEndInfo", null, 'startEndInfo', null);
            var startEndInfo=result.rscode();
            var start=startEndInfo.get("start");
            var end=startEndInfo.get("end");
            $("#passwordDiv").css('display', '');
            if (start == taskId) {
                $("#startButton").css('display', '');
                $("#reStartButton").css('display', 'none');
                $("#confirmNode").attr('disabled', true);
            } else if (end == taskId) {
                $("#startButton").css('display', '');
                $("#startNode").attr('disabled', true);
            } else {
                $("#pwdInput").css('display', 'none');
                $("#reStartButton").css('display', '');
                $("#startButton").css('display', 'none');
                $("#startNode").attr('disabled', true);
                if (state == "2") {
                    $("#restartNode").attr('disabled', false);
                } else {
                    $("#restartNode").attr('disabled', true);
                }
                if (runMode != "AUTO" && state == "1") {
                    $("#confirmNode1").attr('disabled', false);
                } else {
                    $("#confirmNode1").attr('disabled', true);
                }
            }
        },
        test : function() {
            setPopupReturnValue("noOperate", null, true);
        },
        // 运行
        run : function() {
            // setPopupReturnValue(taskId+"#OK", null, true);
            if (state != 0) {
                reRun();
            } else {
                if ($('#password').val() == "") {
                    // 请输入密码！
                    MessageBox.alert("提示信息", "请输入密码！");
                    return;
                }
                $.ajax.submit(null, 'runProcess', "regionId=" + regionId + "&runMode=" + runMode + "&billDay="
                        + billDay + "&taskId=" + taskId + "&billingCycleId=" + billingCycleId + "&password="
                        + $("#password").val(), '', function(result) {
                    var ret = result.get("obj");
                    if (ret.get("retVal") == "N") {
                        if (ret.get("retMsg")) {
                            MessageBox.alert("提示信息", ret.get("retMsg"));
                        } else {
                            MessageBox.alert("提示信息", "无详细错误信息，请查询后台日志！");
                        }
                        return;
                    }
                   // MessageBox.alert("提示信息", "地市：" + regionId + " 启动成功!");
                    setPopupReturnValue(taskId + "#OK#"+regionId, null, true);
                });
            }
        },
        // 校验开始结点是否可以启动
        check : function() {
            if (state == 0) {
                $.ajax.submit(null, 'checkProcess', "regionId=" + regionId + "&runMode=" + runMode + "&billDay="
                        + billDay + "&taskId=" + taskId + "&billingCycleId=" + billingCycleId + "&password="
                        + $("#password").val(), '', function(result) {
                    var ret = result.get("obj");
                    if (ret.get("retVal") == "Y") {
                        $("#startNode").attr('disabled', true);
                    }
                });
            }
        },
        // 重启
        reRun : function() {
            if ($('#pwdInput').css("display") == "block") {
                if ($("#password").val() == "") {
                    // 请输入密码！
                    MessageBox.alert("提示信息", "请输入密码！");
                    return;
                }
            }
            var info = "regionId=" + regionId + "&runMode=" + runMode + "&billDay=" + billDay + "&taskId=" + taskId
                    + "&billingCycleId=" + billingCycleId;
            if ($('#pwdInput').css("display") == "block") {
                info += "&password=" + $("#password").val();
            }
            MessageBox.confirm("提示框", "您确认进程需要重启吗？", function(btn) {
                if ("ok" == btn) {
                    $.ajax.submit(null, 'confirmNodeSts', info, '', function(result) {
                        var ret = result.get("obj");
                        MessageBox.alert("提示信息", ret.get("retMsg"),function(){});
                        if (ret.get("retVal") == "N") {
                            return;
                        }
                    });
                }
            }, {
                ok : "是",
                cancel : "否"
            });
        },
     // 禁用鼠标右键
        closeRight : function() {
            document.onkeydown = function() {
                if (event.keyCode == 116) {
                    event.keyCode = 0;
                    event.returnValue = false;
                }
            };
            document.oncontextmenu = function() {
                event.returnValue = false;
            };
        },
        commit : function() {
            if ($('#pwdInput').css("display") ==  "block") {
                if ($('#password').val() == "") {
                    // 请输入密码！
                    MessageBox.alert("提示信息", "请输入密码！");
                    return;
                }
            }
            var info = "regionId=" + regionId + "&runMode=" + runMode + "&billDay=" + billDay + "&taskId=" + taskId
                    + "&billingCycleId=" + billingCycleId + "&taskId=" + taskId + "&state=" + "Y";
            if ($('#pwdInput').css("display") == "block") {
                info += "&password=" + $('#password').val();
            }
            MessageBox.confirm("提示框", "您确认进程没有问题吗？", function(btn) {
                if ("ok" == btn) {
                    $.ajax.submit(null, 'confirmNodeSts', info, '', function(result) {
                        var ret = result.get("obj");
                        MessageBox.alert("提示信息", ret.get("retMsg"),function(){});
                        if (ret.get("retVal") == "N") {
                            return;
                        }
                        setPopupReturnValue(taskId + "COMMIT", null, true);
                    });
                }
                ;
            });
        }
    };
})();
