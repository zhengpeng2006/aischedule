$(function()
{
    // 默认操作区域不可用
    disabledArea("qrySvForm", true);
    // 应用服务启停不可用
    disabledArea("refreshCycleform", true);
})

/**
 * 服务调用统计js 闭包对象
 */
var SvInvokeStaticJs = (function()
{
    var NODE_LEVEL = null;
    var NODE_ID = null;
    var NODE_NAME = null;

    return {
        // 捕获树点击事件
        treeClick : function(nodedata)
        {
            NODE_LEVEL = nodedata.level;
            NODE_ID = nodedata.id;
            NODE_NAME = nodedata.text;

            // 如果选择的应用节点，查看是否已经开启该应用监控
            if(NODE_LEVEL == '4'){
                if(this.checkMonitorState() == "T"){
                    disabledArea("startBtnSpan", true);
                    disabledArea("stopBtnSpan", false);
                    disabledArea("qrySvForm", false);

                    $("#monitorStateTxt").html("应用<strong>" + NODE_NAME + "</strong>服务统计已经开启")
                }
                else{
                    disabledArea("startBtnSpan", false);
                    disabledArea("stopBtnSpan", true);
                    disabledArea("qrySvForm", true);

                    $("#monitorStateTxt").html("应用<strong>" + NODE_NAME + "</strong>服务统计已经关闭")
                }
            }
            else{
                disabledArea("refreshCycleform", true);
            }
        },
        // 检查应用否启动状态
        checkMonitorState : function()
        {
            var runState = ajaxCheck(null, "fetchSVMethodState", "appId=" + NODE_ID, 'code', null);

            return runState.rscode();

        },
        // 点击开始监控按钮
        startMonitor : function()
        {
            // 检查是否选择的是应用节点
            if(NODE_LEVEL != '4'){
                MessageBox.alert("提示信息", "请选择应用服务器节点！")
                return;
            }

            // 检查是否输入刷新周期
            var refreshCycleTime = $("input[name='refreshCycle']:checked").val();
            if(refreshCycleTime == "0"){
                refreshCycleTime = $("#customerCycle").val();
            }
            if(refreshCycleTime == "undefined" || "" == refreshCycleTime){
                MessageBox.alert("提示信息", "请输入刷新时间周期");
                return;
            }

            // 调用监控后台
            $.ajax.submit(null, 'startMonitor', "appId=" + NODE_ID + "&second=" + refreshCycleTime, null, function(result)
            {
                // 启动成功
                if(result.get("SUCC_FLAG") == "T"){
                    disabledArea("startBtnSpan", true);
                    disabledArea("stopBtnSpan", false);
                    // 是操作区域可用
                    disabledArea("qrySvForm", false);

                    $("#monitorStateTxt").html("应用<strong>" + NODE_NAME + "</strong>服务统计已经开启")
                }

            }, null);

        },
        // 点击关闭监控按钮
        stopMonitor : function()
        {
            // 如果节点部位应用节点级别
            if(NODE_LEVEL != '4'){
                MessageBox.alert("提示信息", "请选择应用服务器节点！")
                return;
            }

            $.ajax.submit(null, 'stopMonitor', "appId=" + NODE_ID, null, function(result)
            {
                if(result.get("SUCC_FLAG") == "T"){
                    disabledArea("startBtnSpan", false);
                    disabledArea("stopBtnSpan", true);
                    disabledArea("qrySvForm", true);

                    $("#monitorStateTxt").html("应用<strong>" + NODE_NAME + "</strong>服务统计已经关闭")

                    // 操作区域灰化
                    disabledArea("qrySvForm", true);
                }
            }, null)

        },
        // 点击查询服务信息
        qryServiceInfo : function()
        {
            var classNameVal = $("#className").val();
            var methodNameVal = $("#methodName").val();

            $.ajax.submit("qrySvForm", 'qryServiceInfo', "className=" + classNameVal + "&methodName=" + methodNameVal + "&appId=" + NODE_ID,
                    "serviceInfoTab", function(result)
                    {

                    }, null);
        }

    }

})();