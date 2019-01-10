var paramObj = {};
$(function()
{
	//取得传过来的任务ID
    paramObj.infoId = $.params.get('infoId');
    paramObj.showSelect = $.params.get('showSelect');


    // 初始化分组信息
    PTimeConfigJS.init(paramObj);

});

//时间监控配置页面JS
var PTimeConfigJS = (function (){
	//取得传过来的任务ID
	var infoId = null;
	var showSelect = false;
	return{
		
		//初始化
		init : function(param)
		{
			infoId = param.infoId;
			showSelect = param.showSelect;
			if (showSelect)
			{
				$("#configbuttons").css("display", "");
			}
			$("#queryButton").trigger("click");
			disabledArea("timeInput", true);
		},
		//查询
		queryOnclick : function (obj)
	    {
	           $.ajax.submit('queryForm','auto_getTimeInfoByExpr_queryOnclick',null,'timeInfos',null,null);
	    },
	    //删除
	    deleteOnclick : function ()
	    {
	    	var table = $.table.get("timeInfosTable");
	    	   var dom = table.getSelected();
	    	   if (dom)
	    	   {
	    	    	MessageBox.confirm("提示框", "确定删除？", function(btn)
	            	{
	            		if("ok" == btn)
	            		{
	    	   				var timeId = dom.children().eq(1).html();
	            			$.ajax.submit(null,'auto_delete_deleteOnclick',"timeId="+timeId,null,function(result){
	            				var delFlag = result.get("DEL_FLAG");
	    	          			 if ("F" == delFlag)
	    	          			 {
	    	          			 	MessageBox.error("操作错误", "错误详情："+result.get("msg"),function(){});
	    	          			 }
	    	          			 else
	    	          			 {
	    	          		    	resetArea('timeInput',true);
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
	    	if("add" == operation)
	    	{
		    	resetArea('timeInput',true);
	    	}
	    	disabledArea("timeInput", false);
			$("#editDiv").css("display", "none");
			$("#saveDiv").css("display", "");
	    },
	    //修改弹框
	    updateInfo : function(obj)
	    {
	    	if (obj)
    		{
	   				var timeId = obj.children().eq(1).html();
 	          		$.ajax.submit(null,'auto_getBeanById_updateOnclick',"timeId="+timeId,"timeInput",function(){
 	          			disabledArea("timeInput", true);
 	          			$("#editDiv").css("display", "");
						$("#saveDiv").css("display", "none");
 	          		});
    		}
	    },
	    //弹框保存
	    infoSave : function()
	    {
	    	var ret1 = $.validate.verifyAll("timeInput");
			if (ret1 == false) {
				return false;
			};
			
    		$.ajax.submit('timeInput','auto_saveOrUpdate_saveOnclick',null,null,function(result){
    			if (result.get("flag") == "F")
				{
					MessageBox.error("操作失败","保存失败，失败详情："+result.get("msg"),function(){});
				}else if(result.get("flag") == "I"){
					MessageBox.alert("提示信息","时间配置信息中含有非法字符，请检查后重新填写！");
				}
				else
				{
					$("#queryButton").trigger("click");
					PTimeConfigJS.cancel();
				}
    		},null);
	    },
	    //给任务选择时间配置
	    dataSelect : function()
	    {
	    	   if (infoId != null)
    		   {
	    		   var table = $.table.get("timeInfosTable");
		    	   var dom = table.getSelected();
		    	   if (dom)
		    	   {
		    	    	
    	   				var timeId = dom.children().eq(1).html();
    			        $.ajax.submit(null,'auto_saveOrUpdate_dataSelectOnclick',"infoId="+infoId+"&timeId="+timeId,null,function(){
    			        	//关闭弹窗
    			        	PTimeConfigJS.quitPage();
    						MessageBox.success("提示信息","任务时间配置成功");    	   
    			        });
		    	   }
		    	   else
		    	   {
						MessageBox.error("提示信息","请选择时间配置信息");    	   
		    	   }
    		   }
	    	   
	    },
	    //关闭弹窗
	    quitPage : function()
	    {
	    	 try
	    	   {
	    		   setPopupReturnValue("time", null, true);
	    	   }
	    	   catch (e)
	    	   {
	    		   return;
	    	   }
	    },
	    //取消回退至编辑按钮区
		cancel : function() {
			var table = $.table.get("timeInfosTable");
			var dom = table.getSelected();
			if (dom) {
				var timeId = dom.children().eq(1).html();
				$.ajax.submit(null, 'auto_getBeanById_updateOnclick',
						"timeId=" + timeId, "timeInput",
						function() {
							disabledArea("timeInput", true);
							$("#editDiv").css("display", "");
							$("#saveDiv").css("display", "none");
						});
			} else {
				disabledArea("timeInput", true);
				$("#editDiv").css("display", "");
				$("#saveDiv").css("display", "none");
			}
		}
	   
	};
	
})();