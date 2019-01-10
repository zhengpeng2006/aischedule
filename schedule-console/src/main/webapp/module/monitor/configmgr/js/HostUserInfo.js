var paramObj = {};

$(function() {
    paramObj.hostId = $.params.get('hostId');

    // 初始化信息
    HostUserInfoJs.init(paramObj);

});
/**
 * 分组信息管理
 * 
 */
var HostUserInfoJs = (function() {

    var hostId = null;

    return {
        // 初始化
        init : function(param) {
            hostId = param.hostId;

            disabledArea("userDetail", true);

            $.ajax.submit(null, 'qryHostUserInfo', "hostId=" + hostId, "userTab", function(result) {
            }, null);
        },
        freshUserTab : function() {
            $.ajax.submit(null, 'qryHostUserInfo', "hostId=" + hostId, "userTab", function(result) {
            }, null);
        },
        showDetail : function() {
            var selRecId = null;
            var selRec = $.table.get("userTabTable").getSelected();
            if (null != selRec) {
                selRecId = $.table.get('userTabTable').getRowData().get('hostUserId');
                $.ajax.submit(null, 'qryHostUserInfoDetail', "hostUserId=" + selRecId, "userDetail", function(result) {
                    disabledArea("userDetail", true);
                    document.getElementById("btnGroupDiv1").style.display = "block";
                    document.getElementById("btnGroupDiv2").style.display = "none";
                    $("#editBtn").attr("disabled", false);
                    $("#delBtn").attr("disabled", false);
                });
            }
        },
        newClick : function() {
            resetArea("userDetail", true);
            disabledArea("userDetail", false);
            document.getElementById("btnGroupDiv1").style.display = "none";
            document.getElementById("btnGroupDiv2").style.display = "block";

        },
        editClick : function() {
            var selRecId = null;
            var selRec = $.table.get("userTabTable").getSelected();
            if (null == selRec) {
                MessageBox.alert("提示信息", "请选择一条记录");
                return;
            }
            disabledArea("userDetail", false);
            document.getElementById("btnGroupDiv1").style.display = "none";
            document.getElementById("btnGroupDiv2").style.display = "block";
        },
        delClick : function() {
            var selRecId = null;
            var selRec = $.table.get("userTabTable").getSelected();
            if (null == selRec) {
                MessageBox.alert("提示信息", "请选择一条记录");
                return;
            }
            selRecId = $.table.get('userTabTable').getRowData().get('hostUserId');
            MessageBox.confirm("提示框", "是否删除用户信息", function(btn) {
                if ("ok" == btn) {
                    $.ajax.submit(null, 'delHostUser', "hostUserId=" + selRecId, null, function(result) {
                        HostUserInfoJs.freshUserTab();
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
            var noTipInfo = "该用户已经存在！";
            var ret1 = $.validate.verifyAll("userDetail");
            if (ret1 == false) {
                return false;
            }
            // 校验两次输入密码是否一致
            if ($("#userPwd").val() != $("#reUserPwd").val()) {
                MessageBox.alert("提示信息", "两次输入的密码不一致，请重新输入");
                $("#userPwd").focus();
                return false;
            }

            $.ajax.submit('userDetail', 'saveOrUpdate', "hostId=" + hostId, null, function(result) {
                var saveFlag = result.get("SAVE_FLAG");
                
                if (saveFlag == "N") {
                    MessageBox.success("提示信息", noTipInfo, function() {

                    });
                }
                if (saveFlag == "A") {
                    HostUserInfoJs.freshUserTab();
                    $("#editBtn").attr("disabled", true);
                    $("#delBtn").attr("disabled", true);
                    document.getElementById("btnGroupDiv1").style.display = "block";
                    document.getElementById("btnGroupDiv2").style.display = "none";
                  
                    disabledArea("userDetail", true);
                }
                if (saveFlag == "E") {
                    HostUserInfoJs.freshUserTab();
                    $("#editBtn").attr("disabled", true);
                    $("#delBtn").attr("disabled", true);
                    document.getElementById("btnGroupDiv1").style.display = "block";
                    document.getElementById("btnGroupDiv2").style.display = "none";
                
                    disabledArea("userDetail", true);
                }
                // 提示成功消息

            }, null);

        },
        cancelClick : function() {

            document.getElementById("btnGroupDiv1").style.display = "block";
            document.getElementById("btnGroupDiv2").style.display = "none";
            HostUserInfoJs.showDetail();
            disabledArea("userDetail", true);
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