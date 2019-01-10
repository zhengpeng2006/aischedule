var $J = jQuery.noConflict();
var timeTicket = null;
$(function()
{

    // 初始化线形图组件
    var dycLineChart = $J("#dycDiv").createLine(EchartTestJs.getIniOption());
    EchartTestJs.lineChart = dycLineChart;

    // var timeTicket = null;
    clearInterval(timeTicket);
    timeTicket = setInterval(function()
    {
        y1 = Math.random() * 100;
        y1 = y1.toFixed(1) - 0;
        x = (new Date()).toLocaleTimeString().replace(/^\D*/, '');

        y2 = Math.random() * 100;
        y2 = y2.toFixed(1) - 0;

        y3 = Math.random() * 100;
        y3 = y3.toFixed(1) - 0;

        // 动态数据接口 addData
        var dataArr = [ {
            x : x,
            y : y1
        }, {
            x : x,
            y : y2
        }, {
            x : x,
            y : y3
        } ];

        // 往线性图标里追加实时数据
        EchartTestJs.lineChart.appendData(dataArr);

    }, 3000);

});

var EchartTestJs = (function()
{
    var lineChart = null;
    return {
        // 获取初始化选项
        getIniOption : function(value)
        {
            // 定义Y轴初始化坐标数据范围，默认min：0，max:100
            var yAxis = {
                min : 0,
                max : value,
                name : '%'
            };
            // 序列初始化选项，就是要显示几条线
            var series = [ {
                name : 'CPU', // 序列显示名称
                lineColor : 'green'
            }, {
                name : 'MEM', // 序列显示名称
                lineColor : 'red'
            }, {
                name : 'FS', // 序列显示名称
                lineColor : 'black'
            } ];

            // 定义动态曲线图初始化选项
            var dycLineOpt = {};

            dycLineOpt.yAxis = yAxis;
            dycLineOpt.series = series;
            // Y轴线初始化方法，有几条线就有几个数组，最后一个元素为初始化X轴坐标数据
            dycLineOpt.iniAxisData = EchartTestJs.iniAxisData(value);

            return dycLineOpt;
        },
        // 初始化Y轴数据
        iniAxisData : function(value)
        {
            // 线1
            var y1 = [];
            var len = 180;
            while(len--){
                y1.push((Math.random() * value + 5).toFixed(1) - 0);
            }

            // 线2
            var y2 = [];
            var len = 180;
            while(len--){
                y2.push((Math.random() * value + 5).toFixed(1) - 0);
            }

            // 线3
            var y3 = [];
            var len = 180;
            while(len--){
                y3.push((Math.random() * value + 5).toFixed(1) - 0);
            }

            // x轴
            var now = new Date();
            var x = [];
            var len = 180;
            while(len--){
                x.unshift(now.toLocaleTimeString().replace(/^\D*/, ''));
                now = new Date(now - 2000);
            }

            var retArr = new Array();
            retArr.push(y1);
            retArr.push(y2);
            retArr.push(y3);

            retArr.push(x);

            return retArr;
        },
        clickRefresh : function()
        {
            EchartTestJs.lineChart.showLoading();
            setTimeout(EchartTestJs.refresh,1000);
        },
        refresh:function(){
        	var max = Math.floor(Math.random()*100);
            EchartTestJs.lineChart = $J("#dycDiv").createLine(EchartTestJs.getIniOption(max));
            EchartTestJs.lineChart.hideLoading();
        }
        
    };
})();