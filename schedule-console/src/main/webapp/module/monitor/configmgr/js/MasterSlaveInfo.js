var paramObj = {};

$(function() {
    paramObj.hostId = $.params.get('hostId');

    // 初始化信息
    MasterSlaveInfoJs.init(paramObj);

});
/**
 * 分组信息管理
 * 
 */
var MasterSlaveInfoJs = (function() {

    var hostId = null;

    return {
        // 初始化
        init : function(param) {
            hostId = param.hostId;

            $.ajax.submit(null, 'qryMasterSlaveInfo', "hostId=" + hostId, "slaveTab", function(result) {
            }, null);
        },
        freshSlaveTab : function(){
            $.ajax.submit(null, 'qryMasterSlaveInfo', "hostId=" + hostId, "slaveTab", function(result) {
            }, null);
        },   
        showDetail: function(){
            var selRecId = null;
            var selRec = $.table.get("userTabTable").getSelected();
            if (null == selRec) {
                MessageBox.alert("提示信息", "请选择一条记录");
                return;
            }
            selRecId = $.table.get('userTabTable').getRowData().get('hostUserId');
            $.ajax.submit(null, 'qryHostUserInfoDetail', "hostUserId=" + selRecId, "userDetail", function(result) {
            }, null);
        },
        newClick : function() {
            resetArea("slaveDetail", true);
            $("#hostIdDetail").attr("disabled",false);
            $("#btnGroupDiv1").css("display","none");
            $("#btnGroupDiv2").css("display","block");
            
        },
        delClick : function() {
            var selRecId = null;
            var selRec = $.table.get("slaveTabTable").getSelected();
            if (null == selRec) {
                MessageBox.alert("提示信息", "请选择一条记录");
                return;
            }
            selRecId = $.table.get('slaveTabTable').getRowData().get('hostId');
            MessageBox.confirm("提示框", "是否删除用户信息", function(btn) {
                if ("ok" == btn) {
                    $.ajax.submit(null, 'delSlave', "masterHostId=" + hostId+"&slaveHostId=" + selRecId, null,
                            function(result) {
                                    MasterSlaveInfoJs.freshSlaveTab();
                                    $("#delBtn").attr("disabled", true);
                            }, null);
                }
            }, {
                ok : "是",
                cancel : "否"
            });

        },
        saveClick : function(){
           
            var existTipInfo = "该主机下已有一台备机，不可再次添加！（一台主机只能有一台备机）";
            var repeatTipInfo = "主机不能作为自身备机！";
            var ret1 = $.validate.verifyAll("slaveDetail");
            if(ret1 == false){
                return false;
            }

            $.ajax.submit('slaveDetail', 'saveOrUpdate', "masterHostId=" + hostId, null, function(result)
            {   
                var saveFlag = result.get("SAVE_FLAG");
                
                if (saveFlag == "E") {
                    MessageBox.success("提示信息", existTipInfo, function() {          
                    });
                }else{
                    if(saveFlag == "R"){
                        MessageBox.success("提示信息", repeatTipInfo, function() {
                            // 重新查询table信息
                            MasterSlaveInfoJs.freshSlaveTab();
                        });
                    }else {
                        MasterSlaveInfoJs.freshSlaveTab();
                        $("#hostIdDetail").attr("disabled",true);
                        $("#btnGroupDiv1").css("display","block");
                        $("#btnGroupDiv2").css("display","none");
                    }
                }
                // 提示成功消息
                
            }, null);

        },
        cancelClick :function(){
            $("#hostIdDetail").attr("disabled",true);
            $("#btnGroupDiv1").css("display","block");
            $("#btnGroupDiv2").css("display","none");
            resetArea("slaveDetail", true);
        },
        displayPort:function(type){
                var port=type.split("_")[1];
                var conId=type.split("_")[2];
                if(type.indexOf("SSH")>=0){
                    $("#conPort").val(port);
                    $("#conId").val(conId);
                }
                if(type.indexOf("Telnet")>=0){
                    $("#conPort").val(port);
                    $("#conId").val(conId);
                }
                if(type.indexOf("Ftp")>=0){
                    $("#conPort").val(port);
                    $("#conId").val(conId);
                }           
        },
        onClick :function(){
            $("#delBtn").attr("disabled",false);
        }
    }
})();