$(function(){
});

var TaskExportJS = (function(){
	return {
		init : function(){
			 $.beginPageLoading(); 
			ajaxSubmit(null,"showTree",null,null,function(result){
				$.endPageLoading();
				$("#detailDiv").html(result.get("msg"));
				$("#detailDiv").css("display","");
			});
		},
		//导出数据
		exportData : function(){
			var url = window.location.href;
			 var index = url.indexOf("?", 0);
			 var dest = url.substr(0,index)+"export?opertion=taskExport";
			 window.location.href=dest;
		}
	};
})();