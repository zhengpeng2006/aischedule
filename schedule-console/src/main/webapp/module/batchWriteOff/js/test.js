var paramObj = {};
$(function(){
	try{
		//window.dialogArguments 取父页面传过来的参数（json对象）
		var param = window.dialogArguments;
		alert(param.id);
	}catch(e){
		alert(e.message);
	}
});