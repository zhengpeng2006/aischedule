var UserRoleEntityRelJSObj = {
    // 全局组标识
    USER_ROLE_ID : 0,
    // 行单击事件， 查询用户组用户信息
    userRoleRowClick : function()
    {
        UserRoleEntityRelJSObj.USER_ROLE_ID = $.table.get("userRoleTabTable").getSelected("userRoleId").text();
        $.ajax.submit(null, 'qryUserEntityListByRoleId', "userRoleId=" + UserRoleEntityRelJSObj.USER_ROLE_ID, 'userPrivEntityTab', null, null);
    },
    // 设置关联
    btn_config : function()
    {
        var selRec = $.table.get("userRoleTabTable").getSelected()
        if(null == selRec){
            MessageBox.alert("提示信息", "请选择角色");
            return;
        }
        popupDiv('relUserRoleEntityPopDiv', 550, "角色与权限实体设置");
        $.tree.get("userPrivEntityTree").refresh("userRoleId=" + UserRoleEntityRelJSObj.USER_ROLE_ID);

        /*
         * $.ajax.submit(null, 'loadEntityTreeData', "userRoleId=" + UserRoleEntityRelJSObj.USER_ROLE_ID, 'treePrivEntity', function(result) {
         * $.tree.get("userPrivEntityTree").expandAll(); popupDiv('relUserRoleEntityPopDiv', 550, "角色与权限实体设置"); }, null);
         */

    },

    // 删除关联信息
    btn_delConfig : function()
    {
        var chkRows = $.table.get("userPrivEntityTreeTabTable").getCheckedRowDatas();
        if(null != chkRows && chkRows.length < 1){
            MessageBox.alert("提示信息", "请选择要删除的角色");
            return;
        }

        MessageBox.confirm("提示框", "是否删除角色信息", function(btn)
        {
            if("ok" == btn){
                var paramObj = {};
                paramObj.userRoleId = UserRoleEntityRelJSObj.USER_ROLE_ID;
                paramObj.userEntityList = new Array();
                for(i = 0; i < chkRows.length; i++){
                    var obj = new Object();
                    obj.entityId = chkRows.get(i).get('entityId');
                    paramObj.userEntityList.push(obj);
                }
                $.ajax.submit(null, 'deleteUserRoleEntityRel', "paramJson=" + JSON.stringify(paramObj), null, function(result)
                {
                    $.closeExternalPopupDiv("relUserRoleEntityPopDiv");
                    MessageBox.success("提示信息", "删除成功");
                    UserRoleEntityRelJSObj.userRoleRowClick();
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
        var chkIds = $.tree.get("userPrivEntityTree").getCheckedIds();
        if(null != chkIds && chkIds.length < 1){
            MessageBox.alert("提示信息", "请选择要关联的实体");
            return;
        }

        var paramObj = {};
        paramObj.userRoleId = UserRoleEntityRelJSObj.USER_ROLE_ID;
        paramObj.userIdList = new Array();
        for(i = 0; i < chkIds.length; i++){
            var obj = new Object();
            obj.entityId = chkIds[i];
            paramObj.userIdList.push(obj);
        }
        $.ajax.submit(null, 'userRoleEntityRelSave', "paramJson=" + JSON.stringify(paramObj), null, function(result)
        {
            $.closeExternalPopupDiv("relUserRoleEntityPopDiv");
            MessageBox.success("提示信息", "保存成功");
            UserRoleEntityRelJSObj.userRoleRowClick();
        }, null);
    },
    // 选中checkbox
    checkTreeBoxAction : function()
    {
        return true;

    },
    // 点击树节点信息
    treeNodeClickAction : function(nodedata)
    {

        return true;

    }

}
