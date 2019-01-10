$(function(){
});


var EmendInfoJS = (function() {
	return {
        showAll : function() {
            //var regionId = $("#city").val();
            /*var billDay = $("#billDay").val();
            alert(billDay);*/
            var sheight = screen.height-200;
            var swidth = screen.width-10;
            var winoption ="status:false;dialogWidth:"+swidth+"px;dialogHeight:"+sheight+"px";
            showModelessDialog("?service=page/module.emend_yh.EmendYh",null,
                    winoption);
        }
	};
}
)();
