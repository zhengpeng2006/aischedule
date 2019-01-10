$(function()
{
    // 是区域刻度
    disabledArea("privEntityUl", true);
});

var PrivEntityJsObj = {
    SEL_NODE_ID : 0,
    SEL_NODE_LEVEL : 0,
    // 动作标识
    ACTION_FLAG : "ADD",
    // 点击树节点事件
    areaTreeTextAction : function(node)
    {
        if(node.dataid){
            if(node.dataid.split("●").length == 2){
                this.SEL_NODE_LEVEL = 2;
            }
            else{
                this.SEL_NODE_LEVEL = 0;
            }
        }
        var tree = $.tree.get("userPrivEntityTree");

        tree.expand(1);

        this.SEL_NODE_ID = node.id;
        $.ajax.submit(null, 'clickTreeNodeAction', "entityId=" + node.id, 'userPrivEntityDetail', function(result)
        {
            disabledArea("privEntityUl", true);

        }, null);

    },
    treeInitAfterAction : function()
    {
        // $.tree.get("userPrivEntityTree").selectNode(88);
    },
    checkboxClickAction : function()
    {

    },

    // 清空用户详细信息
    clearPrivEntityDetail : function()
    {
        resetArea("userPrivEntityDetail", true);
    },

    // 点击新增按钮
    buttonAddOnClick : function()
    {
        if(this.SEL_NODE_ID == 0){
            MessageBox.alert("提示信息", "请选择上级实体节点");
            return;
        }
        this.ACTION_FLAG = "ADD";
        // 清空用户表单信息
        this.clearPrivEntityDetail();
        disabledArea("privEntityUl", false);
        $("#submitDiv").css("display", "block");
        $("#normalOpDiv").css("display", "none");
    },
    openUserInfoDiv : function(title)
    {
        popupDiv(this.createPopupDiv("userDetailForm", $("#userDetailForm").html()), 550, title);
    },

    /**
     * 创建弹出框DIV 创建完成后返回弹出框的id
     */
    createPopupDiv : function(divid, content)
    {
        var $div = $("#" + divid);
        if(!$div.length){
            $div = $('<div id="' + divid + '" style="display:none;"></div>').html(content).appendTo($("body"));
        }
        $("#btn_cancel").bind("click", function()
        {
            $.closeExternalPopupDiv(divid);
        });
        return divid;
    },
    // 编辑按钮
    btn_editOnclick : function(obj)
    {
        if(this.SEL_NODE_LEVEL == 2){
            MessageBox.alert("提示信息", "系统初始菜单，不允许修改");
            return;
        }
        this.ACTION_FLAG = "EDIT";
        disabledArea("privEntityUl", false);
        $("#submitDiv").css("display", "block");
        $("#normalOpDiv").css("display", "none");
    },
    // 保存
    btn_saveOnclick : function(obj)
    {
        // 校验必填信息
        var ret1 = $.validate.verifyAll("userPrivEntityDetail");
        if(ret1 == false){
            return false;
        }

        // 系统菜单不能添加
        var entityType = $("#entityType").val();
        // 表示新增的是系统属性，则不允许
        if(entityType == 1){
            MessageBox.alert("提示信息", "不能新增系统菜单");
            return;
        }

        // 校验，树节点和名称不能重复
        var entityCodeVal = $("#entityCode").val();
        var entityNameVal = $("#entityName").val();

        // 校验菜单编码和名称不能重复
        var result = ajaxCheck(null, "checkEntityNameCode", "entityCode=" + entityCodeVal + "&entityName=" + entityNameVal, 'code', null);

        if(result.rscode() == "T"){
            MessageBox.alert("提示信息", "菜单名称或代码已经存在，请确认填写！");
            return;
        }

        // 提交保存数据
        $.ajax.submit('userPrivEntityDetail', 'saveOrUpdate', "parentId=" + this.SEL_NODE_ID, null, function(result)
        {
            var selId = result.get("entityId");
            var tipInfo = "修改成功！";
            if(PrivEntityJsObj.ACTION_FLAG == "ADD"){
                tipInfo = "新增权限实体成功"
            }

            MessageBox.success("提示信息", tipInfo, function(flag)
            {
                // 刷新树，选中树节点
                PrivEntityJsObj.recoverDetail();
                disabledArea("privEntityUl", true);

                // 刷新树
                $.tree.get("userPrivEntityTree").refresh();
                // 选中树节点暂不可用
                $.tree.get("userPrivEntityTree").selectNode(selId);
            });

            PrivEntityJsObj.ACTION_FLAG = "";

        }, null);
    },
    // 刪除操作
    btn_delOnclick : function(obj)
    {
        if(this.SEL_NODE_LEVEL == 2){
            MessageBox.alert("提示信息", "系统初始菜单，不允许删除");
            return;
        }

        var nodeId = this.SEL_NODE_ID;
        if(nodeId == 0){
            MessageBox.alert("提示信息", "请选择要刪除的记录！");
            return;
        }

        MessageBox.confirm("提示框", "是否删除权限实体信息", function(btn)
        {
            if("ok" == btn){
                $.ajax.submit(null, 'auto_delete_btn_DelOnclick', "entityId=" + nodeId, null, function(result)
                {
                    MessageBox.success("提示信息", "删除权限实体成功", function()
                    {
                        $.tree.get("userPrivEntityTree").refresh();
                    });
                }, null);
            }
        }, {
            ok : "是",
            cancel : "否"
        });

    },
    // 取消按钮
    btn_cancelOnclick : function(obj)
    {
        if(this.ACTION_FLAG == "ADD"){
            $.ajax.submit(null, 'clickTreeNodeAction', "entityId=" + this.SEL_NODE_ID, 'userPrivEntityDetail', function(result)
            {
                $("#submitDiv").css("display", "none");
                $("#normalOpDiv").css("display", "block");
                disabledArea("privEntityUl", false);
            }, null);
        }
        else{
            $("#submitDiv").css("display", "none");
            $("#normalOpDiv").css("display", "block");
            disabledArea("privEntityUl", true);
        }

        this.ACTION_FLAG = "";

    },
    // 恢复当前节点数据
    recoverDetail : function()
    {
        var tmpNode = {};
        tmpNode.id = this.SEL_NODE_ID;
        this.areaTreeTextAction(tmpNode);
        disabledArea("privEntityUl", false);
    },
    // 查询
    btn_qryOnclick : function(obj)
    {
        $.ajax.submit('qryform', 'auto_getUserInfoByCond_btn_qryOnclick', null, 'userInfoTab', null, null);
    }
}