var paramObj = {};
$(function()
{
	//取得传过来的任务ID
    paramObj.infoId = $.params.get('infoId');
    paramObj.showSelect = $.params.get('showSelect');


    // 初始化分组信息
    PExecConfigJS.init(paramObj);

});

//监控配置闭包对象
var PExecConfigJS = (function(){
	//取得传过来的任务ID
	var infoId = null;
	var showSelect = false;
	return {
		//初始化
		init : function(param)
		{
			infoId = param.infoId;
			showSelect = param.showSelect;
			if (showSelect)
			{
				$("#configbuttons").css("display", "");
			}
			disabledArea("dataInput", true);
			PExecConfigJS.queryOnclick();
		},
		//查询
		queryOnclick : function (obj)
	    {
	           $.ajax.submit('queryForm','auto_getExecByNameAndExpr_queryOnclick',null,'execInfos',null,null);
	    },
	    //删除
	    deleteOnclick : function ()
	    {
	    	var table = $.table.get("execInfosTable");
	    	   var dom = table.getSelected();
	    	   if (dom)
	    	   {
	    	    	MessageBox.confirm("提示框", "确定删除？", function(btn)
	            	{
	            		if("ok" == btn)
	            		{
	    	   				var execId = dom.children().eq(1).html();
	            			$.ajax.submit(null,'auto_delete_deleteOnclick',"execId="+execId,null,function(result){
	            				var delFlag = result.get("DEL_FLAG");
	    	          			 if ("F" == delFlag)
	    	          			 {
	    	          			 	MessageBox.error("操作错误", "该数据有任务关联，无法删除");
	    	          			 }
	    	          			 else
	    	          			 {
	    	          			 	$("#queryButton").trigger("click");
	    	          			 }
	            			});
	            		}
	            	},{ok : "是",
	                    cancel : "否"});
	    	   }
	    	   else
	    	   {
					MessageBox.error("操作错误","请选择要删除的记录");    	   
	    	   }
	    },
	    //新增弹框
	    addInfo : function (operation)
	    {
	    	if ("add" == operation)
	    	{
		    	resetArea('dataInput',true);
	    	}
			disabledArea("dataInput", false);
			$("#editDiv").css("display", "none");
			$("#saveDiv").css("display", "");
	    },
	  //修改弹框
	    updateInfo : function(dom)
	    {
	    	   if (dom)
	    	   {
 	   				var execId = dom.children().eq(1).html();
 	          		$.ajax.submit(null,'auto_getBeanById_updateOnclick',"execId="+execId,"dataInput",function(){
 	          			disabledArea("dataInput", true);
 	          			$("#editDiv").css("display", "");
						$("#saveDiv").css("display", "none");
 	          		});
	    	   }
	    },
	  //弹框保存
	    infoSave : function()
	    {
	    	var expr = $("#exprInput").val();
	    	if (this.isBlank(expr))
    		{
	    		MessageBox.alert("提示信息","表达式不能为空",function(){});
	    		return;
    		}
	    	
	    	var ret1 = $.validate.verifyAll("dataInput");
			if (ret1 == false) {
				return false;
			};
			
    		$.ajax.submit('dataInput','auto_saveOrUpdate_saveOnclick',null,null,function(result){
    		    if (result.get("flag") == "F")
				{
					MessageBox.error("操作失败","保存失败，失败详情："+result.get("msg"),function(){});
				}else if(result.get("flag") == "I"){
					MessageBox.alert("提示信息","配置信息中含有非法字符，请检查后重新填写！");
				}
				else
				{
	    		    $("#queryButton").trigger("click");
	    		    PExecConfigJS.cancel();
				}
    		},null);
	    },
	    //退出弹框
	    quitDiv : function()
	    {
		    $.closeExternalPopupDiv("dataInput");
	    },
	    //给任务选择配置
	    dataSelect : function()
	    {
	    	   if (infoId != null)
    		   {
	    		   var table = $.table.get("execInfosTable");
		    	   var dom = table.getSelected();
		    	   if (dom)
		    	   {
		    	    	
    	   				var typeId = dom.children().eq(1).html();
    			        $.ajax.submit(null,'auto_saveOrUpdate_dataSaveOnclick',"infoId="+infoId+"&typeId="+typeId,null,function(){
    			        	//关闭弹窗
    			        	PExecConfigJS.quitPage();
    			        });
		    	   }
		    	   else
		    	   {
						MessageBox.error("提示信息","请选择监控配置信息");    	   
		    	   }
    		   }
	    	   
	    },
	    //关闭弹窗
	    quitPage : function()
	    {
	    	 try
	    	   {
	    		   setPopupReturnValue(null, null, true);
	    	   }
	    	   catch (e)
	    	   {
	    		   return;
	    	   }
	    },
	    //显示表达式详情
	    showDetail : function(obj)
	    {
	    	var tr = obj.parent().parent();
				var execId = tr.children().eq(1).html();
	           $.ajax.submit(null,'auto_getBeanById_updateOnclick',"execId="+execId+"&detail=detail","erprDetail",null,null);
		        popupDiv('erprDetail', 550, "表达式详情");
	    },
	    //取消回退至编辑按钮区
		cancel : function() {
			var table = $.table.get("execInfosTable");
			var dom = table.getSelected();
			if (dom) {
				var execId = dom.children().eq(1).html();
				$.ajax.submit(null,'auto_getBeanById_updateOnclick',"execId="+execId,"dataInput",
						function() {
							disabledArea("dataInput", true);
							$("#editDiv").css("display", "");
							$("#saveDiv").css("display", "none");
						});
			} else {
				disabledArea("dataInput", true);
				$("#editDiv").css("display", "");
				$("#saveDiv").css("display", "none");
			}
		},
		//判空
	    isBlank : function (obj)
	    {
	    	if (obj)
	    	{
	    		obj = obj.replace(/(^\s*)|(\s*$)/g, "");
	    		if (obj.length > 0)
	    		{
	    			return false;
	    		}
	    	}
	    	return true;
	    }
	};
})();