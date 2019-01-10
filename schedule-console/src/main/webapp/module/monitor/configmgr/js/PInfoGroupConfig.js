//任务分组管理页面JS
var PInfoGroupJS = (function(){
	return {
		//查询
		queryOnclick : function (obj)
	    {
	           $.ajax.submit('queryForm','auto_getPInfoGroupByCodeAndName_queryOnclick',null,'goupInfos',null,null);
	    },
	    //清空查询条件
	    reset : function ()
	    {
	    	document.getElementById("name").value = "";
	    	document.getElementById("code").value = "";
	    },
	    //删除
	    deleteOnclick : function (obj)
	    {
	    	   var table = $.table.get("goupInfosTable");
	    	   var dom = table.getSelected();
	    	   if (dom)
	    	   {
	    	    	MessageBox.confirm("提示框", "确定删除？", function(btn)
	            	{
	            		if("ok" == btn)
	            		{
	    	   				var groupId = dom.children().eq(1).html();
	    	          		$.ajax.submit(null,'auto_delete_deleteOnclick',"groupId="+groupId,null,function(result){
	    	          			 var delFlag = result.get("DEL_FLAG");
	    	          			 if ("F" == delFlag)
	    	          			 {
	    	          			 	MessageBox.success("提示信息", "该分组下有任务关联，无法删除", function()
				                    {
				                    	
				                    });
	    	          			 }
	    	          			 else
	    	          			 {
	    	          			 	$("#formQuery").trigger("click");
	    	          			 }
	    	          		});
	    	   			}
	    	   		},{ok : "是",
	                cancel : "否"})
	    	   }
	    	   else
	    	   {
					MessageBox.success("提示信息","请选择要删除的记录",function(){});    	   
	    	   }
	    	   
	    },
	    //弹出新增窗口
	    add : function ()
	    {
	    	resetArea('pGroupInfo',true);
	        popupDiv('infoFormDiv', 550, "新增分组");
	    },
	    //保存数据
	    infoSave : function ()
	    {
	    	if (this.checkData())
	    	{
	    		$.ajax.submit('pGroupInfo','auto_saveOrUpdate_saveOnclick',null,null,function(){
	    		    $.closeExternalPopupDiv("infoFormDiv");
	    		    $("#formQuery").trigger("click");
	    		    MessageBox.success("提示信息", "保存成功");
	    		},null);
	    	}
	           
	    },
	    //数据校验
	    checkData : function ()
	    {
	    	var name = $("#nameInput").val();
	    	var code = $("#codeInput").val();
	    	var select = $("#selectInput").val();
	    	if (this.isNotBlank(name) && this.isNotBlank(code) && this.isNotBlank(select))
	    	{
	    		return true;
	    	}
	    	else
	    	{
	    		MessageBox.alert("名称,编码或层未填写");
	    		return false;
	    	}
	    },
	    //判空
	    isNotBlank : function (obj)
	    {
	    	if (obj)
	    	{
	    		obj = obj.replace(/(^\s*)|(\s*$)/g, "");
	    		if (obj.length > 0)
	    		{
	    			return true;
	    		}
	    	}
	    	return false;
	    },
	    //弹出修改窗口
	    updateOnclick : function (obj)
	    {
	    	   var table = $.table.get("goupInfosTable");
	    	   var dom = table.getSelected();
	    	   if (dom)
	    	   {
	 	   				var groupId = dom.children().eq(1).html();
	 	          		$.ajax.submit(null,'auto_getMonPInfoGroupById_updateOnclick',"groupId="+groupId,"pGroupInfo",null,null);
	 	          		popupDiv('infoFormDiv', 550, "修改分组配置");
	    	   }
	    	   else
	    	   {
					MessageBox.success("提示信息","请选择要修改的记录",function(){});    	   
	    	   }
	    },
	    //退出弹框
	    quitDiv : function ()
	    {
		    $.closeExternalPopupDiv("infoFormDiv");
	    }

	};
})();