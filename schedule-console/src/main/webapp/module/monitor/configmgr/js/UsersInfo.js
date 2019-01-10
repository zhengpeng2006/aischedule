$(function() {
    // 初始化信息
    UsersInfoJS.init();

});
var UsersInfoJS= (function()
{
    return {
        // 初始化
        init : function() {
            disabledArea("usersForm", true);
        },
        // 查询
        qryOnclick : function(obj)
        {
            $.ajax.submit('qryForm', 'loadUsersInfo', null, 'usersTab', null, null);
        },
        showDetail : function() {
            var selRecId = null;
            var selRec = $.table.get("usersTabTable").getSelected();
            if (null != selRec) {
                selRecId = $.table.get('usersTabTable').getRowData().get('userId');
                $.ajax.submit(null, 'qryUserInfoDetail', "userId=" + selRecId, "usersForm", function(result) {
                    disabledArea("usersForm", true);
                    $("#btnGroupDiv1").css("display","block");
                    $("#btnGroupDiv2").css("display","none");
                    $("#editBtn").attr("disabled", false);
                    $("#delBtn").attr("disabled", false);
                });
            }
        },
        newClick : function() {
            resetArea("usersForm", true);
            disabledArea("usersForm", false);
            $("#btnGroupDiv1").css("display","none");
            $("#btnGroupDiv2").css("display","block");
        },
        editClick : function() {
            var selRecId = null;
            var selRec = $.table.get("usersTabTable").getSelected();
            if (null == selRec) {
                MessageBox.alert("提示信息", "请选择一条记录");
                return;
            }
            disabledArea("usersForm", false);
            $("#btnGroupDiv1").css("display","none");
            $("#btnGroupDiv2").css("display","block");
        },
        delClick : function() {
            var selRecId = null;
            var selRec = $.table.get("usersTabTable").getSelected();
            if (null == selRec) {
                MessageBox.alert("提示信息", "请选择一条记录");
                return;
            }
            selRecId = $.table.get('usersTabTable').getRowData().get('userId');
            MessageBox.confirm("提示框", "是否删除用户信息", function(btn) {
                if ("ok" == btn) {
                    $.ajax.submit(null, 'delUser', "userId=" + selRecId, null, function(result) {
                        $.ajax.submit('qryForm', 'freshUsersInfo', null, 'usersTab', null, null);
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
            var noTipInfo = "该用户编码已经存在！";
            var invalidStr="用户信息中含有非法字符，请检查后重新填写！";
            var ret1 = $.validate.verifyAll("usersForm");
            if (ret1 == false) {
                return false;
            }
            // 校验两次输入密码是否一致
            if ($("#userPwd").val() != $("#reUserPwd").val()) {
                MessageBox.alert("提示信息", "两次输入的密码不一致，请重新输入");
                $("#userPwd").focus();
                return false;
            }

            $.ajax.submit('usersForm', 'saveOrUpdate',null, null, function(result) {
                var saveFlag = result.get("SAVE_FLAG");
                
                if (saveFlag == "N") {
                    MessageBox.success("提示信息", noTipInfo, function() {

                    });
                }
                if (saveFlag == "A") {
                    MessageBox.success("提示信息", "新增用户信息成功", function() {
                        resetArea("qryForm", true);
                        $.ajax.submit('qryForm', 'freshUsersInfo', null, 'usersTab', null, null);
                    });
                    $("#editBtn").attr("disabled", true);
                    $("#delBtn").attr("disabled", true);
                    $("#btnGroupDiv1").css("display","block");
                    $("#btnGroupDiv2").css("display","none");
                  
                    disabledArea("usersForm", true);
                }
                if (saveFlag == "E") {
                    MessageBox.success("提示信息", "编辑用户信息成功", function() {
                        resetArea("qryForm", true);
                        $.ajax.submit('qryForm', 'freshUsersInfo', null, 'usersTab', null, null);
                    });
                    $("#editBtn").attr("disabled", true);
                    $("#delBtn").attr("disabled", true);
                    $("#btnGroupDiv1").css("display","block");
                    $("#btnGroupDiv2").css("display","none");
                
                    disabledArea("usersForm", true);
                }
                if (saveFlag == "I") {
                	MessageBox.alert("提示信息", invalidStr, function() {
                    });
                }
            }, null);
        },
        cancelClick : function() {
            $("#btnGroupDiv1").css("display","block");
            $("#btnGroupDiv2").css("display","none");
            UsersInfoJS.showDetail();
            disabledArea("usersForm", true);
        }
    };
})();