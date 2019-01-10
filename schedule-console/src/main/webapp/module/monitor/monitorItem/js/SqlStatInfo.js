/**
 * 闭包对象,基础配置页面js
 */
var SqlStatInfoJs = (function() {
    var LEVEL_ONE = "1";// 一级节点
    var LEVEL_TWO = "2";// 二级节点
    var LEVEL_THREE = "3";// 三级节点
    var LEVEL_FOUR = "4";// 四级节点

    var CUR_SEL_ID = null;// 当前选中节点标识
    var CUR_SEL_LEVEL = null;// 当前选中节点层级

    return {

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

            // 刷新区域
            var refreshTarget = null;

            // 展示集群分组
            if (CUR_SEL_LEVEL == LEVEL_ONE) {
              
            }
            // 点击二级节点展示分组下主机
            else if (CUR_SEL_LEVEL == LEVEL_TWO) {
              
            }
            // 点击三级节点展示主机下节点
            else if (CUR_SEL_LEVEL == LEVEL_THREE) {
               
            }
            // 点击四级节点展示节点下应用
            else if (CUR_SEL_LEVEL == LEVEL_FOUR) {
              
            }     
       
        }
    };

})();