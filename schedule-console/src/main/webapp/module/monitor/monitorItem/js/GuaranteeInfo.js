$(function(){
    GuaranteeInfoJs.init();
});

var GuaranteeInfoJs=(function(){
    return{
        init:function(){
           /* $.ajax.submit("qryCondition", "qryInfoInit", null, "batchTab", function(result){
                GuaranteeInfoJs.showStateColor();
            });*/
        },
        qryInfo:function(){
            $.ajax.submit("qryCondition", "qryInfo", null, "batchTab", function(result){
                var flag = result.get("FLAG");
                // 标识分组下校验还有主机，则提示不能删除
                if (flag == "D") {
                    MessageBox.alert("提示信息", "开始时间大于结束时间，请重新选择时间", function() {
                    });
                }else if(flag=="C"){
                    MessageBox.alert("提示信息", "查询时间跨度超过一个月，请重新选择时间", function() {
                    });
                }else if(flag=="N"){
                    MessageBox.alert("提示信息", "开始时间和结束时间不能同时为空", function() {
                    });
                }
                GuaranteeInfoJs.showStateColor();
            });
        },
        showTime:function(obj){
            var nextValue = $(obj).next().text();
            $(obj).attr("title",nextValue);
        },
        //根据节点状态改变颜色
        showStateColor:function(){
            $("tbody").find("td").each(function(index, elem) {
                var content=elem.innerHTML;
                if (content.indexOf('结束')!=-1) {
                    $(this).css("background", "gray");
                    $(this).css("color","white");
                }
                else if(content.indexOf('执行中')!=-1){
                    $(this).css("background", "green");
                    $(this).css("color","white");
                }
                else if(content.indexOf('异常')!=-1){
                    $(this).css("background", "red");
                    $(this).css("color","white");
                }
            });
        }
    };
})();