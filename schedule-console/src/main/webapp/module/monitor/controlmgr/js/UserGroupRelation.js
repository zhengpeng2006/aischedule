var UserGroupRelJSObj = {
    // 全局组标识
    USER_GROUP_ID : 0,
    // 行单击事件， 查询用户组用户信息
    userGroupRowClick : function()
    {
        UserGroupRelJSObj.USER_GROUP_ID = $.table.get("userGroupTabTable").getSelected("userGroupId").text();
        $.ajax.submit(null, 'qryUserListByGroupId', "userGroupId=" + UserGroupRelJSObj.USER_GROUP_ID, 'userTab', null, null);
    },
    // 设置关联
    btn_config : function()
    {
        var selRec = $.table.get("userGroupTabTable").getSelected()
        if(null == selRec){
            MessageBox.alert("提示信息", "请选择用户组");
            return;
        }

        $.ajax.submit(null, 'qryRelUserListByGroupId', "userGroupId=" + UserGroupRelJSObj.USER_GROUP_ID, 'relUserTab', function(result)
        {
            popupDiv('relUserPopDiv', 550, "用户组与用户关系设置");
        }, null);

    },
    // 删除关联信息
    btn_delConfig : function()
    {
        var chkRows = $.table.get("userTabTable").getCheckedRowDatas();
        if(null != chkRows && chkRows.length < 1){
            MessageBox.alert("提示信息", "请选择要删除的用户");
            return;
        }
        MessageBox.confirm("提示框", "是否删除用户组信息", function(btn)
        {
            if("ok" == btn){
                var paramObj = {};
                paramObj.userGroupId = UserGroupRelJSObj.USER_GROUP_ID;
                paramObj.userList = new Array();
                for(i = 0; i < chkRows.length; i++){
                    var obj = new Object();
                    obj.userId = chkRows.get(i).get('userId');
                    paramObj.userList.push(obj);
                }

                $.ajax.submit(null, 'deleteUserGroupRel', "paramJson=" + JSON.stringify(paramObj), null, function(result)
                {
                    MessageBox.success("提示信息", "删除成功", function()
                    {
                        $.closeExternalPopupDiv("relUserPopDiv");
                        UserGroupRelJSObj.userGroupRowClick();
                    });
                }, null);
            }
        }, null);

    },
    // 保存用户关联问题
    btn_relUserSave : function(obj)
    {
        // 获取checked的记录
        var chkRows = $.table.get("relUserTabTable").getCheckedRowDatas();
        if(null != chkRows && chkRows.length < 1){
            MessageBox.alert("提示信息", "请选择要关联的用户");
            return;
        }

        var paramObj = {};
        paramObj.userGroupId = UserGroupRelJSObj.USER_GROUP_ID;
        paramObj.userList = new Array();
        for(i = 0; i < chkRows.length; i++){
            var obj = new Object();
            obj.userId = chkRows.get(i).get('userId');
            paramObj.userList.push(obj);
        }
        $.ajax.submit(null, 'userGroupRelSave', "paramJson=" + JSON.stringify(paramObj), null, function(result)
        {
            $.closeExternalPopupDiv("relUserPopDiv");
            MessageBox.success("提示信息", "保存成功");
            UserGroupRelJSObj.userGroupRowClick();
        }, null);

    }
}