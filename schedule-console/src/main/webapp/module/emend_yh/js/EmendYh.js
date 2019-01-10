$(function(){
	//window.dialogArguments 取父页面传过来的参数（json对象）
	var param = window.dialogArguments;
	EmendYhJS.init(param);
});


var EmendYhJS = (function() {
	var BUSI_INTERVAL = null;// 业务定时器
	var BUSI_INTERVAL_TIME = 20000;// 业务定时器间隔刷新时间
	return {
		init : function(param){
			EmendYhJS.qryEmendYh(param);
			// 启动定时器
			clearInterval(BUSI_INTERVAL);
			BUSI_INTERVAL = setInterval(function() {
				EmendYhJS.qryEmendYh(param);
			}, BUSI_INTERVAL_TIME);
		},
		// 全省地市展示查询
		qryEmendYh : function(param){
			//alert(param);
			$.ajax.submit(null, "qryEmendYh", null, "emendYh", function(){
				EmendYhJS.showStateColor();
			});
		},
		//根据节点状态改变颜色
		showStateColor:function(){
		    $("tbody").find("td").each(function(index, elem) {
		        var content=elem.innerHTML;
		       //正确结束，执行中，异常
		        //正常结束
		        if (content.indexOf('\u6b63\u786e\u7ed3\u675f')!=-1) {
                    $(this).css("background", "gray");
                    $(this).css("color","white");
                }//执行中
                else if(content.indexOf('\u6267\u884c\u4e2d')!=-1){
                    $(this).css("background", "lightblue");
                }//异常
                else if(content.indexOf('\u5f02\u5e38')!=-1){
                    $(this).css("background", "red");
                    $(this).css("color","white");
                }
		        /*if ("F"==content) {
                    $(this).css("background", "gray");
                    $(this).css("color","white");
                }
                else if("C"==content){
                    $(this).css("background", "lightblue");
                }
                else if("X"==content){
                    $(this).css("background", "red");
                    $(this).css("color","white");
                }*/
            });
		}
	};
}
)();
