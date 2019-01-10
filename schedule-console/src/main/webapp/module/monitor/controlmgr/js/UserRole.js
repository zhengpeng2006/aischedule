var UserRoleJSObj = {
    ACTION_FLAG : "ADD",
    // 清空用户详细信息
    clearUserDetail : function()
    {
        resetArea("userRoleDetail", true);
    },

    // 点击新增按钮
    buttonAddOnClick : function()
    {
        this.ACTION_FLAG = "ADD";
        // 清空用户表单信息
        this.clearUserDetail();
        this.openUserInfoDiv("新增角色")
    },
    openUserInfoDiv : function(title)
    {
        popupDiv(this.createPopupDiv("userRoleForm", $("#userRoleForm").html()), 550, title);
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
        // 保存用户信息
        $("#btn_save").bind("click", function()
        {

        });
        return divid;
    },
    // 编辑按钮
    btn_editOnclick : function(obj)
    {
        var selRec = $.table.get("userRoleTabTable").getSelected()
        if(null == selRec){
            MessageBox.alert("提示信息", "请选择一条记录");
            return;
        }
        this.ACTION_FLAG = "EDIT";
        var id = $.table.get('userRoleTabTable').getRowData().get('userRoleId');
        $.ajax.submit(null, 'auto_getBeanById_btn_editOnclick', "userRoleId=" + id, 'userRoleDetail', function(result)
        {
            UserRoleJSObj.openUserInfoDiv("编辑角色");
        }, null);
    },
    // 保存
    btn_saveOnclick : function(obj)
    {
        var ret1 = $.validate.verifyAll("userRoleDetail");
        if(ret1 == false){
            return false;
        }

        $.ajax.submit('userRoleDetail', 'auto_saveOrUpdate_btn_saveOnclick', null, 'userRoleTab', function(result)
        {
            var tipInfo = "修改角色信息成功！";
            if(UserRoleJSObj.ACTION_FLAG == "ADD"){
                tipInfo = "新增角色成功"
            }

            MessageBox.success("提示信息", tipInfo, function()
            {
                $.closeExternalPopupDiv("userRoleForm");
                UserRoleJSObj.btn_qryOnclick();
            });
            UserRoleJSObj.ACTION_FLAG = "";
        }, null);
    },
    // 刪除操作
    btn_delOnclick : function(obj)
    {
        var selRec = $.table.get("userRoleTabTable").getSelected()
        if(null == selRec){
            MessageBox.alert("提示信息", "请选择要刪除的记录！");
            return;
        }

        MessageBox.confirm("提示框", "是否删除角色信息?", function(btn)
        {
            if("ok" == btn){
                var id = $.table.get('userRoleTabTable').getRowData().get('userRoleId');
                $.ajax.submit(null, 'auto_delete_btn_DelOnclick', "userRoleId=" + id, 'userRoleTab', function(result)
                {
                    MessageBox.success("提示信息", "删除角色成功", function()
                    {
                        $.closeExternalPopupDiv("userRoleForm");
                        UserRoleJSObj.btn_qryOnclick();
                    });
                }, null);
            }
        }, {
            ok : "是",
            cancel : "否"
        });

    },
    // 查询
    btn_qryOnclick : function(obj)
    {
        $.ajax.submit('qryform', 'loadUserRoleData', null, 'userRoleTab', function()
        {
            $.fixTable("userRoleTabTable", {
                fixHead : true,
                fixFoot : true,
                leftCols : 0,
                rightCols : 0
            });
        }, null);
    }

}
