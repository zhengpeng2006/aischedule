var paramObj = {};
$(function() {
    // window.dialogArguments 取父页面传过来的参数（json字符串）
    // eval方法为将json字符串转化为json对象。需要在参数外加上（）
    // var param = eval("(" + window.dialogArguments + ")");
    var paramObj = window.dialogArguments;
    DetailJs.init(paramObj);
});
var DetailJs = (function() {
    var regionId = null;
    var runMode = null;
    var billDay = null;
    var taskId = null;
    var billingCycleId = null;
    return {
        init : function(param) {
            regionId = param.regionId;
            runMode = param.runMode;
            billDay = param.billDay;
            taskId = param.taskId;
            billingCycleId = param.billingCycleId;
            $.ajax.submit(null, 'loadDetailData',"regionId="+regionId+"&runMode="+runMode+"&billDay="+billDay+"&taskId="+taskId+"&billingCycleId="+billingCycleId , 'processList',
                    function(result) {
                    }, null);
        }
    };

})();
