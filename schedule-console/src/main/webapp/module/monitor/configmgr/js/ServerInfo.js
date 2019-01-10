var paramObj = {};
$(function()
{
    paramObj.serverId = $.params.get('serverId');
    paramObj.flag = $.params.get("flag");
    paramObj.treeNodeId =$.params.get("treeNodeId");

    // 初始化分组信息
    ServerInfoJs.init(paramObj);

});
/**
 * 分组信息管理
 * 
 */
var ServerInfoJs = (function()
{

    var serverId = null;
    var flag = null;
    var treeNodeId=null;

    return {
        // 初始化
        init : function(param)
        { 
        	serverId=param.serverId;
            flag = param.flag;
            treeNodeId=param.treeNodeId;

            // 编辑
            if(flag == "EDIT"){
                $.ajax.submit(null, 'qryServerInfo', "serverId=" + serverId, "serverDetail", function(result)
                {
                }, null);
            }
            // 新增
            else if(flag == "NEW"){
                // 清空form内容
                resetArea("serverDetail", true);
            }
        },
        // 保存
        save : function()
        {   
            var editTipInfo = "应用信息编辑成功！";
            var newTipInfo = "新增应用成功！";
            var codeTipInfo="该应用编码已经存在，请重新填写！";
            var nameTipInfo="该应用名称已经存在，请重新填写！";
            var bothTipInfo="该应用编码和名称都已经存在，请重新填写！";
            var invalidStr="应用信息中含有非法字符，请检查后重新填写！";
            var ret1 = $.validate.verifyAll("serverDetail");
            if(ret1 == false){
                return false;
            }
            $.ajax.submit('serverDetail', 'saveOrUpdate', "treeNodeId=" +treeNodeId, null, function(result)
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
        }
    }
})();