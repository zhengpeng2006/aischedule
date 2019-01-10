var paramObj = {};
$(function()
{
    paramObj.hostId = $.params.get('hostId');
    paramObj.flag = $.params.get("flag");
    paramObj.treeNodeId =$.params.get("treeNodeId");

    // 初始化分组信息
    HostInfoJs.init(paramObj);

});
/**
 * 分组信息管理
 * 
 */
var HostInfoJs = (function()
{

    var hostId = null;
    var flag = null;
    var treeNodeId=null;

    return {
        // 初始化
        init : function(param)
        {
            hostId = param.hostId;
            flag = param.flag;
            treeNodeId=param.treeNodeId;

            // 编辑
            if(flag == "EDIT"){
                $.ajax.submit(null, 'qryHostInfo', "hostId=" + hostId, "hostDetail", function(result)
                {
                }, null);
            }
            // 新增
            else if(flag == "NEW"){
                // 清空form内容
                resetArea("hostDetail", true);
            }
        },
        // 保存
        save : function()
        {  
            var codeTipInfo="该主机编码已经存在，请重新填写！";
            var nameTipInfo="该主机名称已经存在，请重新填写！";
            var bothTipInfo="该主机编码和名称都已经存在，请重新填写！";
            var invalidStr="主机信息中含有非法字符，请检查后重新填写！";
            var ret1 = $.validate.verifyAll("hostDetail");
            if(ret1 == false){
                return false;
            }
            // 校验两次输入密码是否一致
            if($("#hostPwd").val() != $("#reHostPwd").val()){
                MessageBox.alert("提示信息", "两次输入的密码不一致，请重新输入");
                $("#hostPwd").focus();
                return false;
            }
            $.ajax.submit('hostDetail', 'saveOrUpdate', "treeNodeId=" +treeNodeId, null, function(result)
            {
                
                var saveFlag = result.get("SAVE_FLAG");
                if (saveFlag == "C") {
                    MessageBox.success("提示信息", codeTipInfo, function() {          
                    });
                }
                if(saveFlag == "N"){
                    MessageBox.success("提示信息", nameTipInfo, function() {                           
                    });
                }
                if(saveFlag == "B"){
                    MessageBox.success("提示信息", bothTipInfo, function() {                           
                    });
                }
                if(saveFlag == "A"){
                    setPopupReturnValue(null, null, close);
                }
                if(saveFlag == "E"){
                    setPopupReturnValue(null, null, close);
                }
                if (saveFlag == "I") {
                	MessageBox.alert("提示信息", invalidStr);
                }
            }, null);

        },
        // 取消关闭
        close : function()
        {
            setPopupReturnValue("noOperate",null,true);
        },
        popupUser :function()
        {
        	popupPage("", null, null, "用户弹出框", 600, 600, "updateAfter");
        },
        popupCon :function()
        {
        	popupPage("module.monitor.configmgr.ConModeInfo", null, "treeNodeId=" +treeNodeId, "连接方式弹出框", 600, 600, "updateAfter");
        }
    }
})();