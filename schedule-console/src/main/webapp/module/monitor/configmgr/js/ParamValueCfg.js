var paramObj = {};
$(function()
{
	//取得传过来的任务ID
    paramObj.infoId = $.params.get('infoId');
    paramObj.showSelect = $.params.get('showSelect');


    // 初始化分组信息
    ParamValueCfgJS.init(paramObj);

});
//参数配置JS
var ParamValueCfgJS = (function(){
	var infoId = null;
	var showSelect = false;
	var registeType = 10;//任务默认是10
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
			if (infoId != null)
			{
				ParamValueCfgJS.queryData(infoId);
			}
			disabledArea("paramInput", true);
		},
		//查询
		queryData : function (infoParamId)
		{
			$.ajax.submit(null,'auto_getParamValuesByType_queryButtonOnclick',"infoId="+infoParamId+"&registeType="+registeType,"paramValues",null,null);

		},
		//删除
	    deleteOnclick : function () 
	    {
	    	var table = $.table.get("paramValuesTable");
	    	   var dom = table.getSelected();
	    	   if (dom)
	    	   {
	    	    	MessageBox.confirm("提示框", "确定删除？", function(btn)
	            	{
	            		if("ok" == btn)
	            		{
	    	   				var vId = dom.children().eq(1).html();
	            			$.ajax.submit(null,'auto_deleteParamValues_deleteOnclick',"vId="+vId,null,function(result){
	            				
	            				 if (result.get("flag") == "F")
	         					{
	         						MessageBox.error("操作失败","删除失败，失败详情："+result.get("msg"),function(){});
	         					}
	         					else
	         					{
	         						ParamValueCfgJS.queryData(infoId);
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
		    	resetArea("paramInput",true);
	    	}
	    	disabledArea("paramInput", false);
			$("#editDiv").css("display", "none");
			$("#saveDiv").css("display", "");
	    },
	    //修改弹框
	    updateInfo : function(obj)
	    {
	    	   if (obj)
	    	   {
	 	   				var vId = obj.children().eq(1).html();
	 	          		$.ajax.submit(null,'auto_getParamValuesById_updateOnclick',"vId="+vId,"paramInput",function(){
	 	          			disabledArea("paramInput", true);
	 	          			$("#editDiv").css("display", "");
							$("#saveDiv").css("display", "none");
	 	          		});
	    	   }
	    },
	    //弹框保存
	    infoSave : function()
	    {
	    	var ret1 = $.validate.verifyAll("paramInput");
			if (ret1 == false) {
				return false;
			};
			
    		$.ajax.submit('paramInput','auto_saveOrUpdate_paramSaveOnclick',"infoId="+infoId+"&registeType="+registeType,null,function(result){
    		    if (result.get("flag") == "F")
				{
					MessageBox.error("操作失败","保存失败，失败详情："+result.get("msg"),function(){});
				}else if(result.get("flag") == "I"){
					MessageBox.alert("提示信息","参数信息中含有非法字符，请检查后重新填写！");
				}
				else
				{
	    		    ParamValueCfgJS.queryData(infoId);
	    		    ParamValueCfgJS.cancel();
				}
    		    
    		});
	    	
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
	    //退出弹框
	    quitDiv : function()
	    {
		    $.closeExternalPopupDiv("paramInput");
	    },
	    //关闭弹窗
	    quitPage : function()
	    {
	    	MessageBox.confirm("提示框", "确定 配置完毕？", function(btn)
	            	{
	            		if("ok" == btn)
	            		{
	            			try
	         	    	   {
	         	    		   setPopupReturnValue(null, null, true);
	         	    	   }
	         	    	   catch (e)
	         	    	   {
	         	    		   return;
	         	    	   }
	            		}
	            	},{ok : "是",
	                    cancel : "否"});
	    },
	    //取消回退至编辑按钮区
	    cancel : function() {
			var table = $.table.get("paramValuesTable");
			var dom = table.getSelected();
			if (dom) {
				var vId = dom.children().eq(1).html();
	          		$.ajax.submit(null,'auto_getParamValuesById_updateOnclick',"vId="+vId,"paramInput",
						function() {
							disabledArea("paramInput", true);
							$("#editDiv").css("display", "");
							$("#saveDiv").css("display", "none");
						});
			} else {
				disabledArea("paramInput", true);
				$("#editDiv").css("display", "");
				$("#saveDiv").css("display", "none");
			}
		}
	};
})();