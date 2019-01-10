var paramObj = {};
$(function() {
	// 取得传过来的任务ID
	paramObj.cfg_tf_dtl_id = $.params.get('cfgTfDtlId');
	paramObj.taskCode = $.params.get("taskCode");
	// 初始化分组信息
	ColRelJS.init(paramObj);

});

/**
 * 调度任务参数配置JS闭包对象
 */
var ColRelJS = (function() {
	// 传过来的task主键
	var cfg_tf_dtl_id = null;
	var taskCode = null;
	return {
		// 初始化
		init : function(param) {
			cfg_tf_dtl_id = param.cfg_tf_dtl_id;
			taskCode = param.taskCode;
			ColRelJS.qryCols();
		},
		// 判空字符串和空格
		isNullOrEmpty : function(str) {
			if (str) {
				if ($.trim(str).length > 0) {
					return false;
				}
			}
			return true;
		},
		//查询映射关系
		qryCols : function(){
			if (null != cfg_tf_dtl_id && taskCode != null)
			{
				$.ajax.submit(null,"qryCols","cfgTfDtlId="+cfg_tf_dtl_id+"&taskCode="+taskCode,"colRels",null);
			}
			
		},
		//添加一条记录
		addCol : function()
		{
			var ret1 = $.validate.verifyAll("colInput");
			if (ret1 == false) {
				return false;
			}
			$.ajax.submit("colInput","addCol","cfgTfDtlId="+cfg_tf_dtl_id+"&taskCode="+taskCode,null,function(result){
				if (result && result.get("flag") == "F")
				{
					MessageBox.error("操作失败","添加失败，原因是："+result.get("msg"));
				}else if(result.get("flag")=="I"){
					MessageBox.alert("提示信息","映射信息中含有非法字符，请检查后重新填写！");
				}else
				{
					ColRelJS.qryCols();
				}
			});
		},
		//删除记录
		deleteCol : function()
		{
			var table = $.table.get("colRelsTable");
			var rows = table.getCheckedRowDatas();
			if (rows && rows.length > 0)
			{
				//记录映射id
				var ids = [];
				for(var i = 0;i< rows.length;i++)
				{
					ids.push(rows.get(i).get("mappingId"));
				}
				$.ajax.submit(null,"deleteCols","cfgTfDtlId="+cfg_tf_dtl_id+"&taskCode="+taskCode+"&ids="+ids,null,function(result){
					if (result && result.get("flag") == "F")
					{
						MessageBox.error("操作失败","删除失败，原因是："+result.get("msg"));
					}
					else
					{
						ColRelJS.qryCols();
					}
				});
			}
			else
			{
				MessageBox.alert("提示信息","请选择一个映射关系记录");
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
	    }
		
	};
})();