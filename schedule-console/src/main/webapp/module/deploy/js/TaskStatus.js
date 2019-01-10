$(function() {
    TaskStatusJS.init();
});

var TaskStatusJS = (function() {
    return {
        init : function() {
        },
        //查询任务执行信息
        qryTaskInfos : function() {
            $.beginPageLoading(); 
            $.ajax.submit("qryCondition", "qryTaskInfos", null, "taskInfosTab", function(result){
                $.endPageLoading();
            });
        },
        sort : function(index,obj, type) {
            var childIndex = "td:nth-child(" +index+")";
            $.sortTable(obj, type, function(tr1, tr2) {
                var tr1Data = $(tr1).children(childIndex).html();
                var tr2Data = $(tr2).children(childIndex).html();
                if (!isNaN(tr1Data) && !isNaN(tr2Data)){
                    return parseInt(tr1Data) - parseInt(tr2Data);
                }else{
                    var result = 0;
                    if (tr1Data < tr2Data){
                        result = -1;
                    }else if (tr1Data == tr2Data){
                        result = 0;
                    }else{
                        result = 1;
                    }
                    return result;
                }
            }, "desc");
        }
    };
})();