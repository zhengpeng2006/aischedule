var paramObj = {};
$(function() {
    paramObj.groupId = $.params.get('groupId');
    paramObj.flag = $.params.get("flag");

    // 初始化分组信息
    GroupInfoJs.init(paramObj);

});
/**
 * 分组信息管理
 * 
 */
var GroupInfoJs = (function() {

    var groupId = null;
    var flag = null;

    return {
        // 初始化
        init : function(param) {
            groupId = param.groupId;
            flag = param.flag;

            // 编辑
            if (flag == "EDIT") {
                $.ajax.submit(null, 'qryGroupInfo', "groupId=" + groupId, "groupDetail", function(result) {
                }, null);
            }
            // 新增
            else if (flag == "NEW") {
                // 清空form内容
                resetArea("groupDetail", true);
            }
        },
        // 保存
        save : function() {
            var codeTipInfo = "该分组编码已经存在，请重新填写！";
            var nameTipInfo = "该分组名称已经存在，请重新填写！";
            var bothTipInfo = "该分组编码和名称都已经存在，请重新填写！";
            var invalidStr="分组信息中含有非法字符，请检查后重新填写！";

            var ret1 = $.validate.verifyAll("groupDetail");
            if (ret1 == false) {
                return false;
            }

            $.ajax.submit('groupDetail', 'saveOrUpdate', null, null, function(result) {

                var saveFlag = result.get("SAVE_FLAG");
                if (saveFlag == "C") {
                    MessageBox.success("提示信息", codeTipInfo, function() {

                    });
                }
                if (saveFlag == "N") {
                    MessageBox.success("提示信息", nameTipInfo, function() {
                    });
                }
                if (saveFlag == "B") {
                    MessageBox.success("提示信息", bothTipInfo, function() {
                    });
                }
                if (saveFlag == "A") {
                    setPopupReturnValue(null, null, close);
                }
                if (saveFlag == "E") {
                    setPopupReturnValue(null, null, close);
                }
                if (saveFlag == "I") {
                	MessageBox.alert("提示信息", invalidStr);
                }
            }, null);
        },
        // 取消关闭
        close : function() {
            setPopupReturnValue("noOperate",null,true);
        }
    }
})();