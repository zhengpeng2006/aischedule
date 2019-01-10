var paramObj = {};

$(function() {
    paramObj.nodeId = $.params.get('nodeId');

    // 初始化信息
    NodeUserInfoJs.init(paramObj);

});
/**
 * 分组信息管理
 * 
 */
var NodeUserInfoJs = (function() {

    var nodeId = null;

    return {
        // 初始化
        init : function(param) {
            nodeId = param.nodeId;
            disabledArea("nodeUserDetail", true);

            $.ajax.submit(null, 'qryNodeUserInfo', "nodeId=" + nodeId, "nodeUserTab", function(result) {
            }, null);
        },
        freshUserTab : function() {
            $.ajax.submit(null, 'qryNodeUserInfo', "nodeId=" + nodeId, "nodeUserTab", function(result) {
            }, null);
        },
        showDetail : function() {
            var selRecId = null;
            var selRec = $.table.get("nodeUserTabTable").getSelected();
            if (null != selRec) {
                selRecId = $.table.get('nodeUserTabTable').getRowData().get('nodeUserId');
                $.ajax.submit(null, 'qryNodeUserInfoDetail', "nodeUserId=" + selRecId, "nodeUserDetail", function(result) {
                    disabledArea("nodeUserDetail", true);
                    document.getElementById("btnGroupDiv1").style.display = "block";
                    document.getElementById("btnGroupDiv2").style.display = "none";
                    $("#editBtn").attr("disabled", false);
                    $("#delBtn").attr("disabled", false);
                });
            }
        },
        newClick : function() {
            resetArea("nodeUserDetail", true);
            disabledArea("nodeUserDetail", false);
            document.getElementById("btnGroupDiv1").style.display = "none";
            document.getElementById("btnGroupDiv2").style.display = "block";

        },
        editClick : function() {
            var selRecId = null;
            var selRec = $.table.get("nodeUserTabTable").getSelected();
            if (null == selRec) {
                MessageBox.alert("提示信息", "请选择一条记录");
                return;
            }
            disabledArea("nodeUserDetail", false);
            document.getElementById("btnGroupDiv1").style.display = "none";
            document.getElementById("btnGroupDiv2").style.display = "block";
        },
        delClick : function() {
            var selRecId = null;
            var selRec = $.table.get("nodeUserTabTable").getSelected();
            if (null == selRec) {
                MessageBox.alert("提示信息", "请选择一条记录");
                return;
            }
            selRecId = $.table.get('nodeUserTabTable').getRowData().get('nodeUserId');
            MessageBox.confirm("提示框", "是否删除用户信息", function(btn) {
                if ("ok" == btn) {
                    $.ajax.submit(null, 'delNodeUser', "nodeUserId=" + selRecId, null, function(result) {
                        NodeUserInfoJs.freshUserTab();
                        $("#editBtn").attr("disabled", true);
                        $("#delBtn").attr("disabled", true);
                    }, null);
                }
            }, {
                ok : "是",
                cancel : "否"
            });

        },
        saveClick : function() {
            var noTipInfo = "节点下只能有一个用户！";
            var invalidStr="节点用户信息中含有非法字符，请检查后重新填写！";
            var ret1 = $.validate.verifyAll("nodeUserDetail");
            if (ret1 == false) {
                return false;
            }
            $.ajax.submit('nodeUserDetail', 'saveOrUpdate', "nodeId=" + nodeId, null, function(result) {
                var saveFlag = result.get("SAVE_FLAG");
                
                if (saveFlag == "N") {
                    MessageBox.success("提示信息", noTipInfo, function() {

                    });
                }
                if (saveFlag == "A") {
                    NodeUserInfoJs.freshUserTab();
                    $("#editBtn").attr("disabled", true);
                    $("#delBtn").attr("disabled", true);
                    document.getElementById("btnGroupDiv1").style.display = "block";
                    document.getElementById("btnGroupDiv2").style.display = "none";
                  
                    disabledArea("nodeUserDetail", true);
                }
                if (saveFlag == "E") {
                    NodeUserInfoJs.freshUserTab();
                    $("#editBtn").attr("disabled", true);
                    $("#delBtn").attr("disabled", true);
                    document.getElementById("btnGroupDiv1").style.display = "block";
                    document.getElementById("btnGroupDiv2").style.display = "none";
                
                    disabledArea("nodeUserDetail", true);
                }
                // 提示成功消息
                if (saveFlag == "I") {
                	MessageBox.alert("提示信息", invalidStr);
                }
            }, null);
        },
        cancelClick : function() {
            document.getElementById("btnGroupDiv1").style.display = "block";
            document.getElementById("btnGroupDiv2").style.display = "none";
            NodeUserInfoJs.showDetail();
            disabledArea("nodeUserDetail", true);
        },
        displayPort : function(type) {
            var port = type.split("_")[1];
            var conId = type.split("_")[2];
            if (type.indexOf("SSH") >= 0) {
                $("#conPort").val(port);
                $("#conId").val(conId);
            }
            if (type.indexOf("Telnet") >= 0) {
                $("#conPort").val(port);
                $("#conId").val(conId);
            }
            if (type.indexOf("Ftp") >= 0) {
                $("#conPort").val(port);
                $("#conId").val(conId);
            }
        }
    }
})();