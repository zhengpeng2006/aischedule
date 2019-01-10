/**
 * 闭包对象,基础配置页面js
 */
var BaseConfigPortalJs = (function() {
    var LEVEL_ONE = "1";// 一级节点
    var LEVEL_TWO = "2";// 二级节点
    var LEVEL_THREE = "3";// 三级节点
    var LEVEL_FOUR = "4";// 四级节点

    var CUR_SEL_ID = null;// 当前选中节点标识
    var CUR_SEL_LEVEL = null;// 当前选中节点层级

    return {

        // 树初始化后执行
        treeInitAfterAction : function() {
            $.tree.get("sysOrgTree").expandByPath("-1");
            
        },
        // 点击树节点text
        treeTextClick : function(node) {
            if (null != node) {
                CUR_SEL_LEVEL = node.level;
                CUR_SEL_ID = node.id;
            }
            $("#editBtn").attr("disabled",true);
            $("#delBtn").attr("disabled",true);
            // 刷新区域
            var refreshTarget = null;

            // 展示集群分组
            if (CUR_SEL_LEVEL == LEVEL_ONE) {
                $("#groupTabDiv").css("display", "");

                // 隐藏其他节点节点信息
                $("#hostTabDiv").css("display", "none");
                $("#nodeTabDiv").css("display", "none");
                $("#serverTabDiv").css("display", "none");
                $("#allInfo").text("分组列表信息");
                refreshTarget = "groupTab";

            }
            // 点击二级节点展示分组下主机
            else if (CUR_SEL_LEVEL == LEVEL_TWO) {
                $("#hostTabDiv").css("display", "");

                // 隐藏其他节点节点信息
                $("#groupTabDiv").css("display", "none");
                $("#nodeTabDiv").css("display", "none");
                $("#serverTabDiv").css("display", "none");
                $("#allInfo").text("主机列表信息");
                refreshTarget = "hostTab";
                $("#user_manager").attr("disabled",true);
                $("#con_manager").attr("disabled",true);
                $("#gh_rel_manager").attr("disabled",true);
            }
            // 点击三级节点展示主机下节点
            else if (CUR_SEL_LEVEL == LEVEL_THREE) {
                $("#nodeTabDiv").css("display", "");

                // 隐藏其他节点节点信息
                $("#groupTabDiv").css("display", "none");
                $("#hostTabDiv").css("display", "none");
                $("#serverTabDiv").css("display", "none");
                $("#allInfo").text("节点列表信息");
                refreshTarget = "nodeTab";
            }
            // 点击四级节点展示节点下应用
            else if (CUR_SEL_LEVEL == LEVEL_FOUR) {
                $("#serverTabDiv").css("display", "");

                // 隐藏其他节点节点信息
                $("#groupTabDiv").css("display", "none");
                $("#hostTabDiv").css("display", "none");
                $("#nodeTabDiv").css("display", "none");
                $("#allInfo").text("应用列表信息");
                refreshTarget = "serverTab";
            }
            $("#btnGroupDiv").css("display", "");

            // 展示分组信息
            $.ajax.submit(null, 'loadTableData', "level=" + CUR_SEL_LEVEL + "&treeNodeId=" + CUR_SEL_ID, refreshTarget,
                    function(result) {

                    }, null);
        },
        // 新增按钮
        newClick : function() {
            var actionFlag = "NEW";

            // 分组
            if (CUR_SEL_LEVEL == LEVEL_ONE) {
                window['Wade_isStopResizeHeight'] = true;
                popupPage("module.monitor.configmgr.GroupInfo", null, "flag=" + actionFlag, "新增分组", 600, 300,
                        "updateAfter");

            }
            // 主机
            else if (CUR_SEL_LEVEL == LEVEL_TWO) {
                window['Wade_isStopResizeHeight'] = true;
                popupPage("module.monitor.configmgr.HostInfo", null,
                        "flag=" + actionFlag + "&treeNodeId=" + CUR_SEL_ID, "新增主机", 600, 300, "updateAfter");
            }
            // 节点
            else if (CUR_SEL_LEVEL == LEVEL_THREE) {
                window['Wade_isStopResizeHeight'] = true;
                popupPage("module.monitor.configmgr.NodeInfo", null,
                        "flag=" + actionFlag + "&treeNodeId=" + CUR_SEL_ID, "新增节点", 600, 300, "updateAfter");
            }

            else if (CUR_SEL_LEVEL == LEVEL_FOUR) {
                window['Wade_isStopResizeHeight'] = true;
                popupPage("module.monitor.configmgr.ServerInfo", null, "flag=" + actionFlag + "&treeNodeId="
                        + CUR_SEL_ID, "新增服务", 600, 300, "updateAfter");
            }
        },
        editClick : function() {
            var actionFlag = "EDIT";

            // 分组
            if (CUR_SEL_LEVEL == LEVEL_ONE) {
                var selRecId = null;
                var selRec = $.table.get("groupTabTable").getSelected();
                if (null == selRec) {
                    MessageBox.alert("提示信息", "请选择一条记录");
                    return;
                }

                selRecId = $.table.get('groupTabTable').getRowData().get('groupId');
                window['Wade_isStopResizeHeight'] = true;
                popupPage("module.monitor.configmgr.GroupInfo", null, "flag=" + actionFlag + "&groupId=" + selRecId,
                        "编辑分组", 600, 300, "updateAfter");

            }
            // 主机
            else if (CUR_SEL_LEVEL == LEVEL_TWO) {
                var selRecId = null;
                var selRec = $.table.get("hostTabTable").getSelected();
                if (null == selRec) {
                    MessageBox.alert("提示信息", "请选择一条记录");
                    return;
                }

                selRecId = $.table.get('hostTabTable').getRowData().get('hostId');
                window['Wade_isStopResizeHeight'] = true;
                popupPage("module.monitor.configmgr.HostInfo", null, "flag=" + actionFlag + "&hostId=" + selRecId+ "&treeNodeId=" + CUR_SEL_ID,
                        "编辑主机", 600, 300, "updateAfter");
            }
            // 节点
            else if (CUR_SEL_LEVEL == LEVEL_THREE) {
                var selRecId = null;
                var selRec = $.table.get("nodeTabTable").getSelected();
                if (null == selRec) {
                    MessageBox.alert("提示信息", "请选择一条记录");
                    return;
                }

                selRecId = $.table.get('nodeTabTable').getRowData().get('nodeId');
                window['Wade_isStopResizeHeight'] = true;
                popupPage("module.monitor.configmgr.NodeInfo", null, "flag=" + actionFlag + "&nodeId=" + selRecId+ "&treeNodeId=" + CUR_SEL_ID,
                        "编辑节点", 600, 300, "updateAfter");
            }

            else if (CUR_SEL_LEVEL == LEVEL_FOUR) {
                var selRecId = null;
                var selRec = $.table.get("serverTabTable").getSelected();
                if (null == selRec) {
                    MessageBox.alert("提示信息", "请选择一条记录");
                    return;
                }

                selRecId = $.table.get('serverTabTable').getRowData().get('serverId');
                window['Wade_isStopResizeHeight'] = true;
                popupPage("module.monitor.configmgr.ServerInfo", null, "flag=" + actionFlag + "&serverId=" + selRecId
                        + "&treeNodeId=" + CUR_SEL_ID, "编辑服务", 600, 300, "updateAfter");
            }
        },
        // 点击删除按钮
        delClick : function() {
            var selRecId = null;
            var chkTipInfo = "不能删除，请重新校验！";
            var succTipInfo = "删除成功！";
            // 分组
            if (CUR_SEL_LEVEL == LEVEL_ONE) {
                var selRec = $.table.get("groupTabTable").getSelected();
                if (null == selRec) {
                    MessageBox.alert("提示信息", "请选择要删除的记录");
                    return;
                }

                selRecId = $.table.get('groupTabTable').getRowData().get('groupId');
                chkTipInfo = "请先删除该分组下所有主机节点！";

            }
            // 主机
            else if (CUR_SEL_LEVEL == LEVEL_TWO) {
                var selRec = $.table.get("hostTabTable").getSelected();
                if (null == selRec) {
                    MessageBox.alert("提示信息", "请选择要删除的记录");
                    return;
                }

                selRecId = $.table.get('hostTabTable').getRowData().get('hostId');
                chkTipInfo = "请先删除该主机下所有节点！";

            }
            // 节点
            else if (CUR_SEL_LEVEL == LEVEL_THREE) {
                var selRec = $.table.get("nodeTabTable").getSelected();
                if (null == selRec) {
                    MessageBox.alert("提示信息", "请选择要删除的记录");
                    return;
                }

                selRecId = $.table.get('nodeTabTable').getRowData().get('nodeId');
                chkTipInfo = "请先删除该节点下所有应用！";
            }

            else if (CUR_SEL_LEVEL == LEVEL_FOUR) {
                var selRec = $.table.get("serverTabTable").getSelected();
                if (null == selRec) {
                    MessageBox.alert("提示信息", "请选择要删除的记录");
                    return;
                }

                selRecId = $.table.get('serverTabTable').getRowData().get('serverId');
                chkTipInfo = "";

            }
            MessageBox.confirm("提示框", "是否删除信息", function(btn) {
                if ("ok" == btn) {
                    $.ajax.submit(null, 'delTableRec', "level=" + CUR_SEL_LEVEL + "&recId=" + selRecId + "&treeNodeId="
                            + CUR_SEL_ID, null, function(result) {
                        // 这里要刷新当前页面，通过点击树操作来实现

                        var delFlag = result.get("DEL_FLAG");

                        // 标识分组下校验还有主机，则提示不能删除
                        if (delFlag == "N") {
                            MessageBox.success("提示信息", chkTipInfo, function() {
                            });
                        }
                        // 提示删除成功消息
                        else {
                            BaseConfigPortalJs.treeTextClick();
                            $.ajax.submit(null, 'loadTreeData', null, 'treeOutPart', function(result) {
                            }, null);
                        }

                    }, null);
                }
            }, {
                ok : "是",
                cancel : "否"
            });

        },
        afterAction : function() {
            var test = $("#updateAfter").val();
            if("noOperate"!=test){
                $.ajax.submit(null, '', null, 'treeOutPart', function(result) {
                    $.tree.get("sysOrgTree").expandByPath("-1");
                }, null);
                BaseConfigPortalJs.treeTextClick();
            }
        },
        conClick : function() {
            if (CUR_SEL_LEVEL == LEVEL_TWO) {
                var selRecId = null;
                var selRec = $.table.get("hostTabTable").getSelected();
                if (null == selRec) {
                    MessageBox.alert("提示信息", "请选择一条记录");
                    return;
                }

                selRecId = $.table.get('hostTabTable').getRowData().get('hostId');
                window['Wade_isStopResizeHeight'] = true;
                popupPage("module.monitor.configmgr.ConModeInfo", null, "hostId=" + selRecId, "连接方式管理", 600, 400,
                        "updateAfter");
            }

        },
        userClick : function() {
            if (CUR_SEL_LEVEL == LEVEL_TWO) {
                var selRecId = null;
                var selRec = $.table.get("hostTabTable").getSelected();
                if (null == selRec) {
                    MessageBox.alert("提示信息", "请选择一条记录");
                    return;
                }

                selRecId = $.table.get('hostTabTable').getRowData().get('hostId');
                window['Wade_isStopResizeHeight'] = true;
                popupPage("module.monitor.configmgr.HostUserInfo", null, "hostId=" + selRecId, "用户管理", 600, 400,
                        "updateAfter");
            }
        },
        nodeUserClick:function(){
            if (CUR_SEL_LEVEL == LEVEL_THREE) {
                var selRecId = null;
                var selRec = $.table.get("nodeTabTable").getSelected();
                if (null == selRec) {
                    MessageBox.alert("提示信息", "请选择一条记录");
                    return;
                }

                selRecId = $.table.get('nodeTabTable').getRowData().get('nodeId');
                window['Wade_isStopResizeHeight'] = true;
                popupPage("module.monitor.configmgr.NodeUserInfo", null, "nodeId=" + selRecId, "用户管理", 600, 400,
                        "updateAfter");
            }
        },
        slaveClick : function() {
            if (CUR_SEL_LEVEL == LEVEL_TWO) {
                var selRecId = null;
                var selRec = $.table.get("hostTabTable").getSelected();
                if (null == selRec) {
                    MessageBox.alert("提示信息", "请选择一条记录");
                    return;
                }

                selRecId = $.table.get('hostTabTable').getRowData().get('hostId');
                window['Wade_isStopResizeHeight'] = true;
                popupPage("module.monitor.configmgr.MasterSlaveInfo", null, "hostId=" + selRecId, "备机信息", 600, 400,
                        null);
            }
        },
        qryClick : function(obj) {
            window['Wade_isStopResizeHeight'] = true;
            popupPage("module.monitor.configmgr.HostAllInfo", null, "hostId=" + obj.attr("hostId"), "主机详细信息", 600, 700,
                    null);
        },
        appParamClick : function(){
            if (CUR_SEL_LEVEL == LEVEL_FOUR) {
                var selRecId = null;
                var selRec = $.table.get("serverTabTable").getSelected();
                if (null == selRec) {
                    MessageBox.alert("提示信息", "请选择一条记录");
                    return;
                }

                selRecId = $.table.get('serverTabTable').getRowData().get('serverId');
                window['Wade_isStopResizeHeight'] = true;
                popupPage("module.monitor.configmgr.DeployAppParamInfo", null, "serverId=" + selRecId, "部署参数管理", 600, 400,
                        "updateAfter");
            }
        },
        //复制应用
        copyServer:function(){
            var selRecId = null;
            var selRec = $.table.get("serverTabTable").getSelected();
            if (null == selRec) {
                MessageBox.alert("提示信息", "请选择一条记录");
                return;
            }
            selRecId = $.table.get('serverTabTable').getRowData().get('serverId');
            window['Wade_isStopResizeHeight'] = true;
            popupPage("module.monitor.configmgr.NodeTreeInfo", null, "serverId=" + selRecId, "复制应用", 600, 400,
                    "updateAfter");
        },
        disDeployBtn : function(){
            $("#app_manager").attr("disabled",false);
            $("#copyServer").attr("disabled",false);
            $("#editBtn").attr("disabled",false);
            $("#delBtn").attr("disabled",false);
        },
        disGroupBtn : function(){
            $("#editBtn").attr("disabled",false);
            $("#delBtn").attr("disabled",false);
        },
        disHostBtn : function(){
            $("#editBtn").attr("disabled",false);
            $("#delBtn").attr("disabled",false);
            $("#user_manager").attr("disabled",false);
            $("#con_manager").attr("disabled",false);
            $("#gh_rel_manager").attr("disabled",false);
        },
        disNodeBtn : function(){
            $("#editBtn").attr("disabled",false);
            $("#delBtn").attr("disabled",false);
        },
        exportHostInfo:function(){
            var url = window.location.href;
            var index = url.indexOf("?", 0);
            var dest = url.substr(0,index)+"export?opertion=hostExport";
            window.location.href=dest;
        }
    };

})();