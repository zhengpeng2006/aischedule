var paramObj = {};

$(function() {
    paramObj.serverId = $.params.get('serverId');

    // 初始化信息
    DeployAppParamInfoJs.init(paramObj);

});
/**
 * 分组信息管理
 * 
 */
var DeployAppParamInfoJs = (function() {

    var serverId = null;

    return {
        // 初始化
        init : function(param) {
            serverId = param.serverId;
            disabledArea("deployAppParamDetail", true);
            $.ajax.submit(null, 'qryParamInfo', "serverId=" + serverId, "deployAppParamTab", function(result) {
            }, null);
        },
        freshdeployAppParamTab : function() {
            $.ajax.submit(null, 'qryParamInfo', "serverId=" + serverId, "deployAppParamTab", function(result) {
            }, null);
        },
        editClick : function() {
            var paramType = null;
            var selRec = $.table.get("deployAppParamTabTable").getSelected();
            if (null == selRec) {
                MessageBox.alert("提示信息", "请选择一条记录");
                return;
            }
            paramType = $.table.get('deployAppParamTabTable').getRowData().get('paramType');
            if("T"!=paramType){
                $("#key").attr("disabled",false);
            }
            $("#value").attr("disabled",false);
            document.getElementById("btnGroupDiv1").style.display = "none";
            document.getElementById("btnGroupDiv2").style.display = "block";
        },
        showDetail : function() {
            var selRecId = null;
            var selRec = $.table.get("deployAppParamTabTable").getSelected();
            if (selRec != null) {
                selRecId = $.table.get('deployAppParamTabTable').getRowData().get('applicationParamId');
                $.ajax.submit(null, 'qryParamDetail', "applicationParamId=" + selRecId, "deployAppParamDetail",
                        function(result) {
                            disabledArea("deployAppParamDetail", true);
                        }, null);
            }
            disabledArea("deployAppParamDetail", true);
            document.getElementById("btnGroupDiv1").style.display = "block";
            document.getElementById("btnGroupDiv2").style.display = "none";

        },
        saveClick : function() {
            var noTipInfo="已有该键";
            var templateTipInfo="模板参数，键不能修改";
            var invalidStr="参数信息中含有非法字符，请检查后重新填写！";
            var ret1 = $.validate.verifyAll("deployAppParamDetail");
            if (ret1 == false) {
                return false;
            }
            $.ajax.submit('deployAppParamDetail', 'saveOrUpdate', "serverId=" + serverId + "&applicationParamId="
                    + $("#applicationParamId").val(), null, function(result) {
                var saveFlag = result.get("SAVE_FLAG");
                if (saveFlag == "N") {
                    MessageBox.success("提示信息", noTipInfo, function() {
                    });
                }
                if(saveFlag=="T"){
                    MessageBox.success("提示信息", templateTipInfo, function() {
                    });
                }
                if(saveFlag=="Y"){
                    DeployAppParamInfoJs.freshdeployAppParamTab();
                    document.getElementById("btnGroupDiv1").style.display = "block";
                    document.getElementById("btnGroupDiv2").style.display = "none";
                    disabledArea("deployAppParamDetail", true);
                }
                if (saveFlag == "I") {
                	MessageBox.alert("提示信息", invalidStr);
                }
            }, null);
        },
        cancelClick : function() {
            document.getElementById("btnGroupDiv1").style.display = "block";
            document.getElementById("btnGroupDiv2").style.display = "none";
            DeployAppParamInfoJs.showDetail();
        },
        newClick : function(){
            resetArea("deployAppParamDetail", true);
            $("#key").attr("disabled",false);
            $("#value").attr("disabled",false);
            $("#btnGroupDiv1").css("display","none");
            $("#btnGroupDiv2").css("display","block");
            $("#paramType").val("C");
        },
        delClick:function(){
            var selRec = $.table.get("deployAppParamTabTable").getSelected();
            if (null == selRec) {
                MessageBox.alert("提示信息", "请选择要删除的记录");
                return;
            }
            selRecId = $.table.get('deployAppParamTabTable').getRowData().get('applicationParamId');
            MessageBox.confirm("提示框", "是否删除参数信息", function(btn) {
                if ("ok" == btn) {
                    $.ajax.submit(null, 'delParam', "applicationParamId=" + selRecId, null, function(result) {
                        disabledArea("deployAppParamDetail", true);
                        $.ajax.submit(null, 'qryParamInfo', "serverId=" + serverId, "deployAppParamTab", function(result) {
                        }, null);
                        $("#editBtn").attr("disabled", true);
                        $("#delBtn").attr("disabled", true);
                    }, null);
                }
            }, {
                ok : "是",
                cancel : "否"
            });
        }
    };
})();