var paramObj = {};
$(function() {
    paramObj.serverId = $.params.get('serverId');
    // 初始化应用标识
    NodeTreeInfoJs.init(paramObj);

});

/**
 * 闭包对象,选择节点页面js
 */
var NodeTreeInfoJs = (function() {
    var LEVEL_ONE = "1";// 一级节点
    var LEVEL_TWO = "2";// 二级节点
    var LEVEL_THREE = "3";// 三级节点
    var LEVEL_FOUR = "4";// 四级节点
    var CUR_SEL_ID = null;// 当前选中节点标识
    var CUR_SEL_LEVEL = null;// 当前选中节点层级
    return {
        init : function(param) {
            serverId = param.serverId;
        },
        // 树初始化后执行
        treeInitAfterAction : function() {
            $.tree.get("sysOrgTree").expandByPath("-1");
        },
        // 点击树节点text
        treeTextClick : function(node) {
            if (null != node) {
                CUR_SEL_LEVEL = node.level;
                CUR_SEL_ID = node.id;
            }
            if (CUR_SEL_LEVEL == LEVEL_ONE) {
                $("#renameSer").css("display", "none");
            } else if (CUR_SEL_LEVEL == LEVEL_TWO) {
                $("#renameSer").css("display", "none");
            } else if (CUR_SEL_LEVEL == LEVEL_THREE) {
                $("#renameSer").css("display", "none");
            } else if (CUR_SEL_LEVEL == LEVEL_FOUR) {
                $("#renameSer").css("display", "");
            }
        },
        save : function() {
            var copySuc = "复制应用成功";
            var nullServerCode = "应用编码不能为空";
            var isExist = "该应用编码已经存在，请修改应用编码";
            $.ajax.submit('serverDetail', 'copyServer', "serverId=" + serverId + "&treeNodeId=" + CUR_SEL_ID, null,
                    function(result) {
                        var saveFlag = result.get("SAVE_FLAG");
                        if (saveFlag == "C") {
                            MessageBox.success("提示信息", copySuc, function() {
                            });
                        }
                        if (saveFlag == "N") {
                            MessageBox.alert("提示信息", nullServerCode, function() {
                            });
                        }
                        if (saveFlag == "E") {
                            MessageBox.error("提示信息", isExist, function() {
                            });
                        }
                    }, null);
        }
    };
})();