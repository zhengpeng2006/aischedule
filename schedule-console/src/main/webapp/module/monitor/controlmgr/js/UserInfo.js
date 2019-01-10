var UserInfoJSObj = (function()
{
    var ACTION_FLAG = "ADD";

    return {
        // 清空用户详细信息
        clearUserDetail : function()
        {
            resetArea("userDetail", true);
        },

        // 点击新增按钮
        buttonAddOnClick : function()
        {
            this.ACTION_FLAG = "ADD";
            // 清空用户表单信息
            this.clearUserDetail();
            this.openUserInfoDiv("新增用户")
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
            // 保存用户信息
            $("#btn_save").bind("click", function()
            {

            });
            return divid;
        },
        // 编辑按钮
        btn_editOnclick : function(obj)
        {
            var selRec = $.table.get("userInfoTabTable").getSelected()
            if(null == selRec){
                MessageBox.alert("提示信息", "请选择一条记录");
                return;
            }
            this.ACTION_FLAG = "EDIT";
            var id = $.table.get('userInfoTabTable').getRowData().get('userId');
            $.ajax.submit(null, 'auto_getBeanById_btn_editOnclick', "userId=" + id, 'userDetail', function(result)
            {
                UserInfoJSObj.openUserInfoDiv("编辑用户");
            }, null);
        },
        // 保存
        btn_saveOnclick : function(obj)
        {
            var ret1 = $.validate.verifyAll("userDetail");
            if(ret1 == false){
                return false;
            }

            // 校验两次输入密码是否一致
            if($("#userPass").val() != $("#reUserPass").val()){
                MessageBox.alert("提示信息", "两次输入的密码不一致，请重新输入");
                $("#userPass").focus();
                return false;
            }

            $.ajax.submit('userDetail', 'auto_saveOrUpdate_btn_saveOnclick', null, 'userInfoTab', function(result)
            {
                var tipInfo = "修改用户信息成功！";
                if(UserInfoJSObj.ACTION_FLAG == "ADD"){
                    tipInfo = "新增用户成功"
                }

                MessageBox.success("提示信息", tipInfo, function()
                {
                    $.closeExternalPopupDiv("userDetailForm");
                    UserInfoJSObj.btn_qryOnclick();
                });
                UserInfoJSObj.ACTION_FLAG = "";
            }, null);
        },
        // 刪除操作
        btn_delOnclick : function(obj)
        {
            var selRec = $.table.get("userInfoTabTable").getSelected()
            if(null == selRec){
                MessageBox.alert("提示信息", "请选择要刪除的记录！");
                return;
            }

            MessageBox.confirm("提示框", "是否删除用户信息", function(btn)
            {
                if("ok" == btn){
                    var id = $.table.get('userInfoTabTable').getRowData().get('userId');
                    $.ajax.submit(null, 'auto_delete_btn_DelOnclick', "userId=" + id, 'userInfoTab', function(result)
                    {
                        MessageBox.success("提示信息", "删除用户成功", function()
                        {
                            $.closeExternalPopupDiv("userDetailForm");
                            UserInfoJSObj.btn_qryOnclick();
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
            $.ajax.submit('qryform', 'loadUserData', null, 'userInfoTab', null, null);
        }
    }
})();