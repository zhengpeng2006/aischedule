/**
 * 闭包使用，但是使用闭包，到底好不好啊
 */
var UserGroupJSObj = (function()
{
    var ACTION_FLAG = "ADD";
    return {
        // 清空用户详细信息
        clearUserDetail : function()
        {
            resetArea("userGroupDetail", true);
        },

        // 点击新增按钮
        buttonAddOnClick : function()
        {
            this.ACTION_FLAG = "ADD";
            // 清空用户表单信息
            this.clearUserDetail();
            this.openUserInfoDiv("新增用户组")
        },
        openUserInfoDiv : function(title)
        {
            popupDiv(this.createPopupDiv("userGroupDetailForm", $("#userGroupDetailForm").html()), 550, title);
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
            var selRec = $.table.get("userGroupTabTable").getSelected()
            if(null == selRec){
                MessageBox.alert("提示信息", "请选择一条记录");
                return;
            }
            this.ACTION_FLAG = "EDIT";
            var id = $.table.get('userGroupTabTable').getRowData().get('userGroupId');
            $.ajax.submit(null, 'auto_getBeanById_btn_editOnclick', "userGroupId=" + id, 'userGroupDetail', function(result)
            {
                UserGroupJSObj.openUserInfoDiv("编辑用户组");
            }, null);
        },
        // 保存
        btn_saveOnclick : function(obj)
        {
            var ret1 = $.validate.verifyAll("userGroupDetail");
            if(ret1 == false){
                return false;
            }

            $.ajax.submit('userGroupDetail', 'auto_saveOrUpdate_btn_saveOnclick', null, 'userGroupTab', function(result)
            {
                var tipInfo = "修改用户组信息成功！";
                if(UserGroupJSObj.ACTION_FLAG == "ADD"){
                    tipInfo = "新增用户组成功"
                }

                MessageBox.success("提示信息", tipInfo, function()
                {
                    $.closeExternalPopupDiv("userGroupDetailForm");
                    UserGroupJSObj.btn_qryOnclick();
                });
                UserGroupJSObj.ACTION_FLAG = "";
            }, null);
        },
        // 刪除操作
        btn_delOnclick : function(obj)
        {
            var selRec = $.table.get("userGroupTabTable").getSelected()
            if(null == selRec){
                MessageBox.alert("提示信息", "请选择要刪除的记录！");
                return;
            }

            MessageBox.confirm("提示框", "是否删除用户组信息", function(btn)
            {
                if("ok" == btn){
                    var id = $.table.get('userGroupTabTable').getRowData().get('userGroupId');
                    $.ajax.submit(null, 'auto_delete_btn_DelOnclick', "userGroupId=" + id, 'userGroupTab', function(result)
                    {
                        MessageBox.success("提示信息", "删除用户组成功", function()
                        {
                            $.closeExternalPopupDiv("userGroupDetailForm");
                            UserGroupJSObj.btn_qryOnclick();
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
            $.ajax.submit('qryform', 'loadUserGropuData', null, 'userGroupTab', null, function(e, i)
            {
                $.showErrorMessage('Operation failed', i);
            });
        }

    }
})();
