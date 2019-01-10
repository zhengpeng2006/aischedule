var PInfoConfigJS = (function(){
	var LEVEL_ONE = "1";// 一级节点
    var LEVEL_TWO = "2";// 二级节点
    var LEVEL_THREE = "3";// 三级节点
    var LEVEL_FOUR = "4";// 四级节点

    var CUR_SEL_ID = null;// 当前选中节点标识
    var CUR_SEL_LEVEL = null;// 当前选中节点层级
    
	return {
		// 树初始化后执行
        treeInitAfterAction : function() {
            $.tree.get("infoTree").expandByPath("-1");
        },
     // 点击树节点text
        treeTextClick : function(node) {
            if (null != node) {
                CUR_SEL_LEVEL = node.level;
                CUR_SEL_ID = node.id;
            }

            // 刷新区域
            var refreshTarget = null;

            // 展示任务分组
            if (CUR_SEL_LEVEL == LEVEL_ONE) {
            	$("#PinfoGroupDiv").css("display", "");

                // 隐藏其他节点节点信息
            	$("#PInfoDiv").css("display", "none");
            	$("#infoDetailDiv").css("display", "none");
                refreshTarget = "groupInfos";

            }
            // 展示分组下任务信息
            else if (CUR_SEL_LEVEL == LEVEL_TWO) {
            	$("#PInfoDiv").css("display", "");

                // 隐藏其他节点节点信息
            	$("#PinfoGroupDiv").css("display", "none");
            	$("#infoDetailDiv").css("display", "none");

                refreshTarget = "infos";
            }
            // 展示分组详细信息
            else if (CUR_SEL_LEVEL == LEVEL_THREE) {
            	$("#infoDetailDiv").css("display", "");

                // 隐藏其他节点节点信息
            	$("#PinfoGroupDiv").css("display", "none");
            	$("#PInfoDiv").css("display", "none");

                refreshTarget = "infoDetail";
                $.ajax.submit(null, 'loadTableData', "level=" + CUR_SEL_LEVEL + "&treeNodeId=" + CUR_SEL_ID, "paramInfos",
                        function(result) {

                        }, null);
            }

            // 展示分组信息
            $.ajax.submit(null, 'loadTableData', "level=" + CUR_SEL_LEVEL + "&treeNodeId=" + CUR_SEL_ID, refreshTarget,
                    function(result) {

                    }, null);
        },
        //弹出分组信息新增窗口
	    groupAdd : function ()
	    {
	    	resetArea('groupInput',true);
	        popupDiv('groupInputDiv', 550, "新增分组信息");
	    },
	    //删除分组信息
	    groupDelete : function (obj)
	    {
	    	   var table = $.table.get("groupInfosTable");
	    	   var dom = table.getSelected();
	    	   if (dom)
	    	   {
	    	    	MessageBox.confirm("提示框", "确定删除？", function(btn)
	            	{
	            		if("ok" == btn)
	            		{
	    	   				var groupId = dom.children().eq(1).html();

	    	          		$.ajax.submit(null,'auto_delete_groupDeleteOnclick',"groupId="+groupId,"groupInfos",function(result){
	    	          			 var delFlag = result.get("DEL_FLAG");
	    	          			 if ("F" == delFlag)
	    	          			 {
	    	          			 	MessageBox.error("操作失败", "删除失败，失败原因："+result.get("msg"), function()
				                    {
	    	          			 		
				                    });
	    	          			 }
	    	          			 else
    	          				 {
		    	          			 PInfoConfigJS.refreshTree();
    	          				 }
	    	          		});
	    	   			}
	    	   		},{ok : "是",
	                cancel : "否"});
	    	   }
	    	   else
	    	   {
					MessageBox.alert("提示信息","请选择要删除的记录",function(){});    	   
	    	   }
	    },
	    //修改分组信息弹窗
	    groupUpdate : function (obj)
	    {
	    	   var table = $.table.get("groupInfosTable");
	    	   var dom = table.getSelected();
	    	   if (dom)
	    	   {
	 	   				var groupId = dom.children().eq(1).html();
	 	          		$.ajax.submit(null,'auto_getMonPInfoGroupById_groupUpdateOnclick',"groupId="+groupId,"groupInput",null,null);
	 	          		popupDiv('groupInputDiv', 550, "修改分组配置");
	    	   }
	    	   else
	    	   {
					MessageBox.alert("提示信息","请选择要修改的记录",function(){});    	   

	    	   }
	    },
	  //保存分组数据
	    groupInfoSave : function ()
	    {
	    	var ret1 = $.validate.verifyAll("groupInput");
            if(ret1 == false){
                return false;
            }
    		$.ajax.submit('groupInput','auto_saveOrUpdate_groupDataSaveOnclick',null,null,function(result){
    			if (result.get("DEL_FLAG") == "F")
				{
    				MessageBox.error("操作失败","保存失败，详情："+result.get("msg"),function(){});
				}else if(result.get("DEL_FLAG") == "I"){
					MessageBox.alert("提示信息","分组信息中含有非法字符，请检查后重新填写！");
				}else{
    				$.closeExternalPopupDiv("groupInputDiv");
    				PInfoConfigJS.refreshTree();
    			}
    		    
    		},null);
	           
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
	    //退出分组信息弹窗
	    groupQuitDiv : function ()
	    {
		    $.closeExternalPopupDiv("groupInputDiv");
	    },
	    //弹出任务信息新增窗口
	    infoAdd : function ()
	    {
	    	resetArea('infoDataInput',true);
	        popupDiv('infoInputDiv', 550, "新增任务信息");
	    },
	    //修改任务信息弹窗
	    infoUpdate : function (obj)
	    {
	    	   var table = $.table.get("infosTable");
	    	   var dom = table.getSelected();
	    	   if (dom)
	    	   {
	 	   				var infoId = dom.children().eq(1).html();
	 	          		$.ajax.submit(null,'auto_getMonPInfoValue_infoUpdateOnclick',"infoId="+infoId,"infoDataInput",null,null);
	 	          		popupDiv('infoInputDiv', 550, "修改任务信息");
	    	   }
	    	   else
	    	   {
					MessageBox.alert("操作错误","请选择要修改的记录",function(){});    	   
	    	   }
	    },
	    
	    //删除任务信息
	    infoDelete : function ()
	    {
	           var table = $.table.get("infosTable");
	    	   var dom = table.getSelected();
	    	   if (dom)
	    	   {
	    	    	MessageBox.confirm("提示框", "确定删除？", function(btn)
	            	{
	            		if("ok" == btn)
	            		{
	    	   				var infoId = dom.children().eq(1).html();

	    	          		$.ajax.submit(null,'auto_delete_infoDeleteOnclick',"infoId="+infoId,"groupInfos",function(result){
	    	    				var delFlag = result.get("DEL_FLAG");
	    	          			 if ("F" == delFlag)
	    	          			 {
	    	          			 	MessageBox.error("操作失败", "删除失败，失败原因："+result.get("msg"), function()
				                    {
	    	          			 		
				                    });
	    	          			 }
		    	          		else
	   	          				 {
			    	          			 PInfoConfigJS.refreshTree();
	   	          				 }
	    	          		});
	    	   			}
	    	   		},{ok : "是",
	                cancel : "否"});
	    	   }
	    	   else
	    	   {
					MessageBox.alert("操作错误","请选择要删除的记录",function(){});    	   
	    	   }
	    },
	  //保存任务数据
	    InfoDataSave : function ()
	    {
	    	var ret1 = $.validate.verifyAll("infoDataInput");
            if(ret1 == false){
                return false;
            }
    		$.ajax.submit('infoDataInput','auto_saveOrUpdate_infoDataSaveOnclick',"treeNodeId="+CUR_SEL_ID,null,function(result){
    			if (result.get("DEL_FLAG") == "F"){
    				MessageBox.error("操作失败","保存失败，详情："+result.get("msg"),function(){});
				}else if(result.get("DEL_FLAG") == "I"){
					MessageBox.error("提示信息","任务数据信息中有非法字符，请检查后重新填写！");
				}else{
    				$.closeExternalPopupDiv("infoInputDiv");
             		PInfoConfigJS.refreshTree();
    			}
    		},null);
	           
	    },
	    //退出任务信息弹窗
	    infoQuitDiv : function ()
	    {
		    $.closeExternalPopupDiv("infoInputDiv");
	    },
	    //各种配置配置
	    config : function(operation)
	    {
	    	var table = $.table.get("infosTable");
	    	   var dom = table.getSelected();
	    	   if (dom)
	    	   {
	 	   			var infoId = dom.children().eq(1).html();
	 	   			if ("time" == operation)
	 	   			{
		 	   			window['Wade_isStopResizeHeight'] = true;
		 	   			popupPage("module.monitor.configmgr.PTimeConfig", null, "infoId="+infoId+"&showSelect=true",
		 	   				"监控时间配置", 800, 470, null);
	 	   			}
	 	   			else if ("param" == operation)
 	   				{
		 	   			window['Wade_isStopResizeHeight'] = true;
	 	   				popupPage("module.monitor.configmgr.ParamValueCfg", null, "infoId="+infoId+"&showSelect=true",
	 	                    "任务参数配置", 800, 400, null);
 	   				}
	 	   			else if("threshold" == operation)
 	   				{
		 	   			window['Wade_isStopResizeHeight'] = true;
	 	   				popupPage("module.monitor.controlmgr.PThresholdConfig", null, "infoId="+infoId+"&showSelect=true",
	 	                    "监控阀值配置", 900, 600, null);
 	   				}
	 	   			else if("exec" == operation)
 	   				{
		 	   			window['Wade_isStopResizeHeight'] = true;
	 	   				popupPage("module.monitor.configmgr.PExecConfig", null, "infoId="+infoId+"&showSelect=true",
	 	                    "监控配置", 900, 530, null);
 	   				}
	 	            
	    	   }
	    	   else
	    	   {
					MessageBox.error("操作错误","请选择要配置的任务");    	   
	    	   }
	    
	    },
	    //刷新树
	    refreshTree : function(){
	    	$.ajax.submit(null, '', null, 'treeOutPart', function(result) {
	    		PInfoConfigJS.treeInitAfterAction();
		    	PInfoConfigJS.treeTextClick();
            }, null);
	    } 

	};
})();