var paramObj = {};

$(function() {
    paramObj.hostId = $.params.get('hostId');

    // 初始化信息
    ConModeInfoJs.init(paramObj);

});
/**
 * 分组信息管理
 * 
 */
var ConModeInfoJs = (function() {

    var hostId = null;

    return {
        // 初始化
        init : function(param) {
            hostId = param.hostId;
            disabledArea("conDetail", true);

            $.ajax.submit(null, 'qryConModeInfo', "hostId=" + hostId, "conTab", function(result) {
            }, null);
        },
        freshConTab : function() {
            $.ajax.submit(null, 'qryConModeInfo', "hostId=" + hostId, "conTab", function(result) {
            }, null);
        },
        newClick : function() {
            disabledArea("conDetail", false);
            document.getElementById("btnGroupDiv1").style.display = "none";
            document.getElementById("btnGroupDiv2").style.display = "block";
            resetArea("conDetail", true);
        },
        editClick : function() {
            var selRecId = null;
            var selRec = $.table.get("conTabTable").getSelected();
            if (null == selRec) {
                MessageBox.alert("提示信息", "请选择一条记录");
                return;
            }
            selRecId = $.table.get('conTabTable').getRowData().get('conId');
            $.ajax.submit(null, 'qryConModeInfoDetail', "conId=" + selRecId, "conDetail", function(result) {
            }, null);
        },
        delClick : function() {
            var selRecId = null;
            var selRec = $.table.get("conTabTable").getSelected();
            if (null == selRec) {
                MessageBox.alert("提示信息", "请选择一条记录");
                return;
            }
            selRecId = $.table.get('conTabTable').getRowData().get('conId');
            MessageBox.confirm("提示框", "是否删除连接方式信息", function(btn) {
                if ("ok" == btn) {
                    $.ajax.submit(null, 'delConMode', "conId=" + selRecId + "&hostId=" + hostId, null,
                            function(result) {
                                ConModeInfoJs.freshConTab();
                                $("#delBtn").attr("disabled", true);
                            }, null);
                }
            }, {
                ok : "是",
                cancel : "否"
            });

        },
        saveClick : function() {

            var chkTipInfo = "主机中已有此连接类型，不可重复添加！";
            var ret1 = $.validate.verifyAll("conDetail");
            if (ret1 == false) {
                return false;
            }
            $.ajax.submit('conDetail', 'saveOrUpdate', "hostId=" + hostId, null, function(result) {
                var saveFlag = result.get("SAVE_FLAG");

                if (saveFlag == "N") {
                    MessageBox.success("提示信息", chkTipInfo, function() {
                    });
                }
                // 提示成功消息
                else {
                    ConModeInfoJs.freshConTab();
                    document.getElementById("btnGroupDiv1").style.display = "block";
                    document.getElementById("btnGroupDiv2").style.display = "none";
                    disabledArea("conDetail", true);
                }
            }, null);

        },
        cancelClick : function() {
            disabledArea("conDetail", true);
            resetArea("conDetail", true);
            document.getElementById("btnGroupDiv1").style.display = "block";
            document.getElementById("btnGroupDiv2").style.display = "none";
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
            if (type.indexOf("SFTP") >= 0) {
                $("#conPort").val(port);
                $("#conId").val(conId);
            }
        },
        onClick : function() {
            document.getElementById("btnGroupDiv1").style.display = "block";
            document.getElementById("btnGroupDiv2").style.display = "none";
            disabledArea("conDetail", true);
            $("#delBtn").attr("disabled", false);
        }
    }
})();