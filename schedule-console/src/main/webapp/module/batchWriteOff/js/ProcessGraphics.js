$(function() {
    ProcessGraphicsJS.closeRight();
});

var ProcessGraphicsJS = (function() {
    // 保存画布
    var paper = null;

    // 原坐标适应当前页面时提高的距离（原顶点离顶端距离需减去的高度）
    var height = 90;

    // 监控节点定时器
    var timer = null;

    // 定时器刷新时间
    var INTERVAL_TIME = 3000;

    // 箭头长度
    var length = 10;

    // 账期
    var billingCycleId = null;

    return {
        // 开始画流程图
        drawPic : function() {
            var region_id = $("#city").val();
            var bill_day = $("#billDate").val();
            var run_mode = $("#mode").val();
            $("#commitBtn").attr("disabled", true);
            var param = "regionId=" + region_id;
            param += "&billDay=" + bill_day;
            param += "&runMode=" + run_mode;
            ajaxSubmit(null, "createXml", param, null, function(ret) {
                if (ret.get("retVal") == "N") {
                    alert(ret.get("retMsg"));
                    return;
                }
                if (!ret.get("billingCycleId")) {
                    alert("未取得账期");
                    return;
                }
                billingCycleId = ret.get("billingCycleId");
                var testProcess = ret.get("processXml");
                if (testProcess) {
                    $("#picDiv").css("display", "");
                    ProcessGraphicsJS.XmlToProcess(testProcess);
                    disabledArea("paramterSet", true);
                    if (timer != null) {
                        window.clearInterval(timer);
                    }
                    timer = setInterval(function() {
                        ProcessGraphicsJS.monNode();
                    }, INTERVAL_TIME);
                } else {
                    MessageBox.alert("提示信息", "后台流程文件未生成", function() {
                    });
                }
            });
        },
        // 获取画布
        getPaper : function() {
            if (!paper) {
                paper = Raphael("center", 1600, 600);
            }
            return paper;
        },
        // 从XML画流程图
        XmlToProcess : function(loadXml) {
            // 生成画布
            Ext.getDom("center").innerHTML = "";
            if (loadXml) {
                xml = new ActiveXObject("Microsoft.XMLDOM");
                xml.loadXML(loadXml);
                var root = ProcessGraphicsJS.findXmlNode("process");
                var rootChilds = root.childNodes;
                for (var i = 0; i < rootChilds.length; i++) {
                    ProcessGraphicsJS.drawNode(paper, rootChilds[i]);
                }
                var lines = xml.getElementsByTagName("transition");
                for (i = 0; i < lines.length; i++) {
                    ProcessGraphicsJS.drawArray(paper, lines[i]);
                }
            }
        },
        // 画箭头
        drawArray : function(paper, node) {
            var srcId = node.parentNode.getAttribute("value");
            var destId = node.getAttribute("to");
            var srcNode = paper.getById(srcId);
            var destNode = paper.getById(destId);
            // 计算线的起点和终点坐标
            var srcLeft = null;
            var srcTop = null;
            var destleft = null;
            var destTop = null;
            // 如果左侧一致 则说明从下面画线，否则为侧面画线
            if (srcNode.attr("left") == destNode.attr("left")) {
                srcLeft = srcNode.attr("left") + srcNode.attr("width") / 2;
                srcTop = srcNode.attr("top") + srcNode.attr("height") * 1;
                destleft = destNode.attr("left") + destNode.attr("width") / 2;
                destTop = destNode.attr("top");
            } else if (srcNode.attr("left") < destNode.attr("left")) {// 从左往右画
                srcLeft = srcNode.attr("left") + srcNode.attr("width") * 1;
                srcTop = srcNode.attr("top") + srcNode.attr("height") / 2;
                destleft = destNode.attr("left");
                destTop = destNode.attr("top") + destNode.attr("height") / 2;
            } else {// 从右往左画
                srcLeft = srcNode.attr("left");
                srcTop = srcNode.attr("top") + srcNode.attr("height") / 2;
                destleft = destNode.attr("left") + destNode.attr("width") * 1;
                destTop = destNode.attr("top") + destNode.attr("height") / 2;
            }
            ;
            var param = "M" + srcLeft + "," + srcTop + "L" + destleft + "," + destTop;
            paper.path(param);

            // 画箭头部分
            var angle = Raphael.angle(srcLeft, srcTop, destleft, destTop);// API方法获取角度，原直线倾斜度
            var angle_one = Raphael.rad(angle - 15);// API方法转变为弧度
            var angle_two = Raphael.rad(angle + 15);// API方法转变为弧度
            // arrow points求出箭头的双向点
            var angle_x1 = destleft + Math.cos(angle_one) * length;
            var angle_y1 = destTop + Math.sin(angle_one) * length;
            var angle_x2 = destleft + Math.cos(angle_two) * length;
            var angle_y2 = destTop + Math.sin(angle_two) * length;
            param = "M" + angle_x1 + "," + angle_y1 + "L" + destleft + "," + destTop + "L" + angle_x2 + "," + angle_y2;
            paper.path(param);
        },
        findXmlNode : function(node) {
            if (node == "process")
                return xml.documentElement;// 返回根节点
            if (node == null || node.flowtype == null)
                return null;
            var nodes = xml.getElementsByTagName(node.flowtype);
            var sameNodes = [];

            for (var i = 0; i < nodes.length; i++) {
                // 判断是连线还是节点，是节点的话用节点坐标比较，是连线的话，用连线对应的文本节点坐标比较
                if (node.flowtype == "transition") {
                    var font = Ext.DomQuery.select("span[title=" + node.id + "]")[0];
                    for (i = 0; i < nodes.length; i++) {
                        if (nodes[i].getAttribute("g") == font.style.pixelLeft + "," + font.style.pixelTop)
                            sameNodes.push(nodes[i]);
                    }
                } else {
                    for (i = 0; i < nodes.length; i++) {
                        if (nodes[i].getAttribute("g") == node.style.pixelLeft + "," + node.style.pixelTop + ","
                                + node.style.pixelWidth + "," + node.style.pixelHeight)
                            sameNodes.push(nodes[i]);
                    }
                }
            }
            if (sameNodes.length == 1) {
                return sameNodes[0];
            } else {
                for (var i = 0; i < sameNodes.length; i++) {
                    if (sameNodes[i].getAttribute("name") == node.title) {
                        return sameNodes[i];
                    }
                }
            }
            return null;
        },
        // 画节点
        drawNode : function(paper, child) {
            if (child) {
                var showNode = true;// 是否创建节点
                var imgName = null;
                switch (child.tagName) {
                case "start":
                    imgName = "base/img-batchWriteOff/icon/start.gif";
                    break;
                case "task":
                    imgName = "base/img-batchWriteOff/icon/task.gif";
                    break;
                case "end":
                    imgName = "base/img-batchWriteOff/icon/end.gif";
                    break;
                default:
                    showNode = false;
                }
                if (showNode) {
                    var left = parseInt(child.getAttribute("left"));
                    var top = parseInt(child.getAttribute("top")) - height;
                    // 画节点框
                    paper = ProcessGraphicsJS.getPaper();
                    var rect = paper.rect(left, top, child.getAttribute("width"), child.getAttribute("height"), 10);
                    rect.id = child.getAttribute("value");
                    rect.attr("flowType", child.tagName);
                    rect.attr("title", child.getAttribute("name"));
                    rect.attr("fill", "#EEEEEE");
                    rect.attr("left", left);
                    rect.attr("top", top);
                    rect.attr("width", child.getAttribute("width"));
                    rect.attr("height", child.getAttribute("height"));
                    rect.attr("isDtl", child.getAttribute("isDtl"));
                    if (child.getAttribute("isDtl") == "Y") {
                        rect.attr("dtlUrl", child.getAttribute("dtlUrl"));
                    }
                    // 加载鼠标右击事件
                    rect.mousedown(function(e) {
                        if (e.button != "2") {
                            return false;
                        }
                        ;
                        var regionId = $("#city").val();
                        var billDay = $("#billDate").val();
                        var runMode = $("#mode").val();
                        var taskId = this.id;
                        var state="0";
                        if (this.attr("flowType") == "end") {
                            state = "1";// END
                        } else {
                            if (this.attr("fill") == "yellow" && runMode != "AUTO") {
                                state = "1";// 人工干预模式下，稽核完成后置状态 等待人工前台修改为 Y
                            } else if (this.attr("fill") == "red") {
                                state = "2";// 异常结束或者确认失败
                            }
                        }
                        var paramInfo = "regionId=" + regionId + "&billDay=" + billDay + "&runMode=" + runMode + "&taskId="
                                + taskId + "&billingCycleId=" + billingCycleId + "&state=" + state;
                        window['Wade_isStopResizeHeight'] = true;
                        popupPage("module.batchWriteOff.OperateArea", null, paramInfo,
                                "操作区域", 800, 200, "updateAfter");
                    });
                    // 加载双击事件
                    rect.dblclick(function() {
                        if ("Y" == this.attr("isDtl")) {
                            var url = "?service=page/" + rect.attr("dtlUrl");
                            var param = {};
                            param.regionId = $("#city").val();
                            param.billDay = $("#billDate").val();
                            param.runMode = $("#mode").val();
                            param.taskId = this.id;
                            param.billingCycleId = billingCycleId;
                            showModelessDialog(url, param, "status:false;dialogWidth:1000px;dialogHeight:550px");
                        }
                    });
                    // 画图像
                    if (imgName) {
                        paper.image(imgName, left + 3, top + 4, 20, 20);
                    }
                    // 写标签 字体略小
                    var attrStr = {font: "12px Microsoft YaHei", opacity: 0.5};
                    paper.text(left + 60, top + 30, child.getAttribute("name")).attr(attrStr);
                };
            }
        },
        // 展现全省数据
        showAll : function() {
            var regionId = $("#city").val();
            var billDay = $("#billDate").val();
            var sheight = screen.height-200;
            var swidth = screen.width-10;
            var winoption ="status:false;dialogWidth:"+swidth+"px;dialogHeight:"+sheight+"px";
            showModelessDialog("?service=page/module.batchWriteOff.AllRegionDisplay", regionId + "&" + billDay,
                    winoption);
        },
        // 重置流程图
        resetPic : function() {
            // 取消定时器
            if (timer != null) {
                window.clearInterval(timer);
            }
            ;
            $("#commitBtn").attr("disabled", false);
            $("#picDiv").css("display", "none");
            paper = null;
            disabledArea("paramterSet", false);
            if (timer != null) {
                window.clearInterval(timer);
            }
        },
        // 退出窗体
        quitDiv : function(id) {
            $.closeExternalPopupDiv(id);
        },
        // 监控节点状态更改颜色
        monNode : function() {
            var flag = false;
            var region_id = $("#city").val();
            var bill_day = $("#billDate").val();
            var run_mode = $("#mode").val();
            var param = "regionId=" + region_id;
            param += "&billDay=" + bill_day;
            param += "&runMode=" + run_mode;
            param += "&billingCycleId=" + billingCycleId;
            ajaxSubmit(null, "monNode", param, null, function(ret) {
                if (ret.get("retValue") == "N") {
                    alert(ret.get("retMsg"));
                    return;
                }
                var jsonStr = ret.get("jsonStr");
                var json = eval('(' + jsonStr + ')');
                for ( var i in json) {
                    var node = paper.getById(i);
                    if (node) {
                        var state = json[i];
                        if (state == "C") {// 执行中
                            node.attr("fill", "green");
                        } else if (state == "F") {// 正常完成
                            node.attr("fill", "lightblue");
                        } else if (state == "X" || state == "N") {// 异常结束或者确实失败
                            node.attr("fill", "red");
                        } else if (state == "H") {// 人工干预模式下，稽核完成后置状态 等待人工前台修改为 Y
                            node.attr("fill", "yellow");
                        } else if (state == "Y") {// 确认完成 自动模式下稽核完成后，置Y状态
                            node.attr("fill", "gray");
                        }
                        if (i == "<%=end%>" && state == "Y") {
                            flag = true;
                        }
                    }
                }
                if (flag) {
                    window.clearInterval(timer);
                }
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
        afterAction:function(){
            var test = $("#updateAfter").val();
            var param = test.split("#");
            if (param && param.length == 3 && param[1] == "OK"){
            	var rect = paper.getById(param[0]);
            	MessageBox.success("操作成功","地市："+param[2]+" 启动成功",function(){});
                if (rect.attr("flowType") == "start") {
                    rect.attr("fill", "green");
                }
            }
        }
    };
})();