$(function(){
	//window.dialogArguments 取父页面传过来的参数（json对象）
	var param = window.dialogArguments;
	AllRegionDisplayJS.init(param);
});


var AllRegionDisplayJS = (function() {
	var BUSI_INTERVAL = null;// 业务定时器
	var BUSI_INTERVAL_TIME = 20000;// 业务定时器间隔刷新时间
	return {
		init : function(param){
			AllRegionDisplayJS.qryWriteOffInfo(param);
			// 启动定时器
			clearInterval(BUSI_INTERVAL);
			BUSI_INTERVAL = setInterval(function() {
				AllRegionDisplayJS.qryWriteOffInfo(param);
			}, BUSI_INTERVAL_TIME);
		},
		// 全省地市展示查询
		qryWriteOffInfo : function(param){
			var strs = new Array();
			strs = param.split("&");
			param="regionId="+strs[0]+"&billDay="+strs[1];
			$.ajax.submit(null, "qryWriteOffInfo", param, "writeOffInfo", function(){
			    AllRegionDisplayJS.showStateColor();
			});
		},
		//根据节点状态改变颜色
		showStateColor:function(){
		    $("tbody").find("td").each(function(index, elem) {
		        var content=elem.innerHTML;
		        if(content.indexOf('置出账标识完成')!=-1){
		          //当td内容为置出账标识完成的时候，不做操作
		        }
		        else if (content.indexOf('完成')!=-1) {
                    $(this).css("background", "gray");
                    $(this).css("color","white");
                }
                else if(content.indexOf('手动确认')!=-1){
                    $(this).css("background", "yellow");
                }
                else if(content.indexOf('执行中')!=-1){
                    $(this).css("background", "lightblue");
                }
                else if(content.indexOf('出错')!=-1){
                    $(this).css("background", "red");
                    $(this).css("color","white");
                }
            });
		}
	};
}
)();
