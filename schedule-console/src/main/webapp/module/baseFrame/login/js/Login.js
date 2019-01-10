$(function(){
	$(document).keydown(function(event){ 
		if(event.keyCode == 13){ //绑定回车 
			LoginJS.login();
			}
		});
});

var LoginJS = (function(){
	return {
		isBlank : function (obj)
	    {
	    	if (obj)
	    	{
	    		obj = obj.replace(/(^\s*)|(\s*$)/g, "");
	    		if (obj.length > 0)
	    		{
	    			return false;
	    		}
	    	}
	    	return true;
	    },
		login : function()
		{
			var name = $("#custId").val();
			var pwd = $("#password").val();
			if (LoginJS.isBlank(name)){
				MessageBox.alert("提示信息","请输入账号",function(){});
				return;
			}
			if (LoginJS.isBlank(pwd)){
				MessageBox.alert("提示信息","请输入密码",function(){});
				return;
			}
			var b = new Base64();  
	        var pwd = b.encode(pwd);  
			$.beginPageLoading("登录中，请稍候"); 
	           $.ajax.submit(null,'userLogin',"name="+name+"&pwd="+pwd,null,function(data){
					$.endPageLoading();
   				var delFlag = data.get("DEL_FLAG");
	        	   if ("T" == delFlag)
	        	   {
	        		   $.redirect.toPage(null,"module.baseFrame.mainFrame.Mainframe",null,null);
	        	   }
	        	   else if ("F" == delFlag)
        		   {
	        		   MessageBox.error("操作失败","登录失败,原因是："+data.get("msg"));
        		   }
	           });

		},
		dataCancel : function()
		{
			$("#userName").val("");
			$("#userPwd").val("");
		}
	};
})();