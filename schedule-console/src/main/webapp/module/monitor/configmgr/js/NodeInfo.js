var paramObj = {};
$(function() {
    paramObj.nodeId = $.params.get('nodeId');
    paramObj.flag = $.params.get("flag");
    paramObj.treeNodeId = $.params.get("treeNodeId");

    // 初始化分组信息
    NodeInfoJs.init(paramObj);

});
/**
 * 分组信息管理
 * 
 */
var NodeInfoJs = (function() {

    var nodeId = null;
    var flag = null;
    var treeNodeId = null;

    return {
        // 初始化
        init : function(param) {
            nodeId = param.nodeId;
            flag = param.flag;
            treeNodeId = param.treeNodeId;

            // 编辑
            if (flag == "EDIT") {
                $.ajax.submit(null, 'qryNodeInfo', "nodeId=" + nodeId, "nodeDetail", function(result) {
                }, null);
            }
            // 新增
            else if (flag == "NEW") {
                // 清空form内容
                resetArea("nodeDetail", true);
            }
        },
        // 保存
        save : function() {
            var codeTipInfo = "该节点编码已经存在，请重新填写！";
            var nameTipInfo = "该节点名称已经存在，请重新填写！";
            var bothTipInfo = "该节点编码和名称都已经存在，请重新填写！";
            var monitorInfo="该主机下已有监控节点";
            var invalidStr="节点信息中含有非法字符，请检查后重新填写！";
            var ret1 = $.validate.verifyAll("nodeDetail");
            if (ret1 == false) {
                return false;
            }

            $.ajax.submit('nodeDetail', 'saveOrUpdate', "treeNodeId=" + treeNodeId, null, function(result) {
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
                if(saveFlag=="M"){
                    MessageBox.success("提示信息", monitorInfo, function() {
                    });
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