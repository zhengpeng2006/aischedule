var TriggerInfoJSObj = (function()
{
    return {
        // 查询
        qryOnclick : function(obj)
        {
            $.ajax.submit('qryForm', 'loadWTriggerInfo', null, 'triggerTab', null, null);
        },
        sortStr : function(index,obj, type) {
            $.sortTable(obj, type, function(tr1, tr2) {
                var childIndex = "td:nth-child("+ index + ")";
                var tr1Data = $(tr1).children(childIndex).html();
                var tr2Data = $(tr2).children(childIndex).html();
                var result = 0;
                if (tr1Data < tr2Data){
                    result = -1;
                }else if (tr1Data == tr2Data){
                    result = 0;
                }else{
                    result = 1;
                }
                return result;
            }, "desc");
        },
        sortMath : function(index,obj, type) {
            $.sortTable(obj, type, function(tr1, tr2) {
                var childIndex = "td:nth-child("+ index + ")";
                var tr1Data = $(tr1).children(childIndex).html();
                var tr2Data = $(tr2).children(childIndex).html();
                return parseFloat(tr2Data)-parseFloat(tr1Data);
            }, "desc");
        },
        exportOnclick :  function(obj)
        {
        	var ip = $("#ip").val();
			var infoName = $("#infoName").val();
			var hostName = $("#hostName").val();
			var beginDate = $("#beginDate").val();
			var endDate = $("#endDate").val();
			var url = window.location.href;
			var index = url.indexOf("?", 0);
			var dest = url.substr(0,index)+"export?opertion=triggerInfoExport&ip="+ip
					+"&infoName="+infoName
					+"&hostName="+hostName
					+"&beginDate="+beginDate
					+"&endDate="+endDate;
			window.location.href=dest;
        }
    };
})();