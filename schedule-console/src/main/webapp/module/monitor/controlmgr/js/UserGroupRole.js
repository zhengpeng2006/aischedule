var UserGroupRoleJSObj = {
    // 全局组标识
    USER_GROUP_ID : 0,
    // 行单击事件， 查询用户组用户信息
    userGroupRowClick : function()
    {
        UserGroupRoleJSObj.USER_GROUP_ID = $.table.get("userGroupTabTable").getSelected("userGroupId").text();
        $.ajax.submit(null, 'qryUserRoleListByGroupId', "userGroupId=" + UserGroupRoleJSObj.USER_GROUP_ID, 'userRoleTab', null, null);
    },
    // 设置关联
    btn_config : function()
    {
        var selRec = $.table.get("userGroupTabTable").getSelected()
        if(null == selRec){
            MessageBox.alert("提示信息", "请选择用户组");
            return;
        }

        $.ajax.submit(null, 'qryRelUserRoleListByGroupId', "userGroupId=" + UserGroupRoleJSObj.USER_GROUP_ID, 'relUserRoleTab', function(result)
        {
            popupDiv('relUserRolePopDiv', 550, "用户组与角色设置");
        }, null);

    },
    // 删除关联信息
    btn_delConfig : function()
    {
        var chkRows = $.table.get("userRoleTabTable").getCheckedRowDatas();
        if(null != chkRows && chkRows.length < 1){
            MessageBox.alert("提示信息", "请选择要删除的角色");
            return;
        }

        MessageBox.confirm("提示框", "是否删除角色信息信息", function(btn)
        {
            if("ok" == btn){
                var paramObj = {};
                paramObj.userGroupId = UserGroupRoleJSObj.USER_GROUP_ID;
                paramObj.userRoleList = new Array();
                for(i = 0; i < chkRows.length; i++){
                    var obj = new Object();
                    obj.userRoleId = chkRows.get(i).get('userRoleId');
                    paramObj.userRoleList.push(obj);
                }
                $.ajax.submit(null, 'deleteUserGroupRoleRel', "paramJson=" + JSON.stringify(paramObj), null, function(result)
                {
                    MessageBox.success("提示信息", "删除成功", function()
                    {
                        $.closeExternalPopupDiv("relUserRolePopDiv");
                        UserGroupRoleJSObj.userGroupRowClick();
                    });

                }, null);
            }
        }, {
            ok : "是",
            cancel : "否"
        });

    },
    // 保存用户关联问题
    btn_relUserSave : function(obj)
    {
        // 获取checked的记录
        var chkRows = $.table.get("relUserRoleTabTable").getCheckedRowDatas();
        if(null != chkRows && chkRows.length < 1){
            MessageBox.alert("提示信息", "请选择要关联的用户");
            return;
        }

        var paramObj = {};
        paramObj.userGroupId = UserGroupRoleJSObj.USER_GROUP_ID;
        paramObj.userRoleList = new Array();
        for(i = 0; i < chkRows.length; i++){
            var obj = new Object();
            obj.userRoleId = chkRows.get(i).get('userRoleId');
            paramObj.userRoleList.push(obj);
        }
        $.ajax.submit(null, 'userGroupRoleRelSave', "paramJson=" + JSON.stringify(paramObj), null, function(result)
        {
            $.closeExternalPopupDiv("relUserRolePopDiv");
            MessageBox.success("提示信息", "保存成功");
            UserGroupRoleJSObj.userGroupRowClick();
        }, null);

    }
}