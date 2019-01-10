$(function(){
	BaseFrameJS.showInfo();
});


var BaseFrameJS = (function(){
	
	return {
		showInfo : function()
		{
			$("#infoMsg").html("test1<br/>test2<br/>test3<br/>test4<br/>test5");
			$("#infoMsg").attr("display","");

		}
	};
})();