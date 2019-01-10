$(function()
{
    Highcharts.setOptions({
        global : {
            useUTC : false
        }
    });

    // 初始显示线形图信息
    jQuery('#container').highcharts(ChartInition1);
});

// 页面初始显示的线形图
var ChartInition1 = {
    chart : {
        type : 'line',
        animation : true, // don't animate in old IE
        marginRight : 10,
    },
    series : [ {
        name : '内存使用情况',
        data : null
    } ],
    title : {
        text : '内存使用情况'
    },
    xAxis : {
        type : 'datetime',
        title : {
            text : "刷新时间"
        },

        tickPixelInterval : 150,
    },
    yAxis : {
        title : {
            text : '内存占用百分比（%）'
        },
        max : 100,
        alternateGridColor : '#FDFFD5',
        min : 0,
    },
    tooltip : {
        formatter : function()
        {
            return '<b>' + this.series.name + '</b><br/>' + Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>'
                    + Highcharts.numberFormat(this.y, 2) + "%";
        }
    },
    legend : {
        enabled : false
    },
    exporting : {
        enabled : false
    }

};

// 真正要用的线性图
var ChartInition = {
    chart : {
        type : 'spline',
        animation : false, // don't animate in old IE
        marginRight : 10,
        events : {
            load : function()
            {
                MemoryInfoJSObj.SERIES = this.series[0];
                MemoryInfoJSObj.INTERVAL_ID = setInterval(MemoryInfoJSObj.refreshPlotPoint, 1000);
            }
        }
    },
    series : [ {
        name : '内存使用情况',
        data : (function()
        {
            // generate an array of random data
            var data = [], time = (new Date()).getTime(), i;
            for(i = -20; i <= 0; i++){
                data.push({
                    x : time + i * 1000,
                    y : 0
                });
            }
            return data;
        })()
    } ],
    title : {
        text : '内存使用情况'
    },
    xAxis : {
        type : 'datetime',
        tickPixelInterval : 150,
    },
    yAxis : {
        title : {
            text : '内存占用百分比（%）'
        },
        max : 100,
        alternateGridColor : '#FDFFD5',
        min : 0,
        plotLines : [ {
            value : 0,
            width : 1,
            color : '#808080'
        } ]
    },
    tooltip : {
        formatter : function()
        {
            return '<b>' + this.series.name + '</b><br/>' + Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>'
                    + Highcharts.numberFormat(this.y, 2) + "%";
        }
    },
    legend : {
        enabled : true
    },
    exporting : {
        enabled : false
    }

};

var MemoryInfoJSObj = {
    SEL_NODE_ID : null,
    SEL_NODE_LEVEL : -1,
    SEL_NODE_LABEL : null,
    INTERVAL_ID : null,
    SERIES : null,
    LOOP_TOTAL : 0,// 总执行次数
    LOOP_CNT : 0,// 默认定时执行次数

    // 树节点单击事件
    treeNodeClick : function(nodeItem)
    {
        // 获取选中节点的层级和标识
        this.SEL_NODE_ID = nodeItem.id;
        this.SEL_NODE_LEVEL = nodeItem.level;
        this.SEL_NODE_LABEL = nodeItem.text;
    },
    treeInitAfterAction : function()
    {
        // $.tree.get("sysOrgTree").expand(-1);
    },
    // 点击运行按钮
    runBtnClick : function()
    {
        // 如果不是应用节点，则提示，返回
        if(this.SEL_NODE_LEVEL != 4){
            MessageBox.alert("提示信息", "请选择应用节点");
            return false;
        }
        MessageBox.confirm("提示框", "确认要启动实时状态刷新？", function(btn)
        {
            if("ok" == btn){
                // 获取界面刷新元素
                jQuery("#timeSpan").val(60);
                jQuery("#timeInterval").val(1);
                jQuery("#showPointCnt").val(30);
                popupDiv('refreshInfoPopDiv', 550, "刷新条件设置");
            }
        }, {
            ok : "是",
            cancel : "否"
        });

    },
    // 点击确认运行，并获取运行参数
    runClickCommit : function()
    {
        $.closeExternalPopupDiv("refreshInfoPopDiv");

        // 获取界面刷新元素
        var timeSpanVal = jQuery("#timeSpan").val();
        var timeVal = jQuery("#timeInterval").val();
        var pointCnt = jQuery("#showPointCnt").val();

        if(timeSpanVal == ""){
            MessageBox.alert("提示信息", "运行时间段不能为空！");
            return;
        }
        if(timeVal == ""){
            MessageBox.alert("提示信息", "刷新间隔不能为空！");
            return;
        }
        if(pointCnt == ""){
            MessageBox.alert("提示信息", "上限数量不能为空！");
            return;
        }
        if(timeSpanVal == "0"){
            timeSpanVal = 100000;
        }
        if(pointCnt == "0" || pointCnt > 50){
            pointCnt = 50;
        }

        this.LOOP_TOTAL = (timeSpanVal / timeVal);

        // 灰化启动按钮
        disabledArea("runbtndiv", true);
        disabledArea("stopbtndiv", false);

        var seriesIni = [ {
            name : MemoryInfoJSObj.SEL_NODE_LABEL + '内存情况',
            data : (function()
            {
                var data = [], time = (new Date()).getTime(), i;
                for(i = -pointCnt; i <= 0; i++){
                    data.push({
                        x : time + i * 1000,
                        y : 0
                    });
                }
                return data;
            })()
        } ];
        ChartInition.chart.events = {
            load : function()
            {
                MemoryInfoJSObj.SERIES = this.series[0];
                MemoryInfoJSObj.INTERVAL_ID = setInterval(MemoryInfoJSObj.refreshPlotPoint, timeVal * 1000);
            }
        };
        ChartInition.series = seriesIni;

        // 初始化内存图表线形图
        jQuery('#container').highcharts(ChartInition);
    },
    // 取消运行
    runClickCancel : function()
    {
        $.closeExternalPopupDiv("refreshInfoPopDiv");
    },
    pauseBtnClick : function()
    {
        // 清楚定时器
        clearInterval(MemoryInfoJSObj.INTERVAL_ID)
        disabledArea("runbtndiv", false);
        disabledArea("stopbtndiv", true);
    },
    // 调用后台，获取x，y轴
    refreshPlotPoint : function()
    {
        var series = MemoryInfoJSObj.SERIES;
        if(MemoryInfoJSObj.LOOP_CNT <= MemoryInfoJSObj.LOOP_TOTAL){
            // 后台获取参数值
            $.ajax.submit(null, 'qryServerMemInfo', "appServerId=" + MemoryInfoJSObj.SEL_NODE_ID, null, function(result)
            {
                x = result.get("X");
                y = result.get("Y");
                series.addPoint([ x, y ], true, true);
                MemoryInfoJSObj.LOOP_CNT++;
            }, null);
        }
        else{
            MemoryInfoJSObj.pauseBtnClick();
            MemoryInfoJSObj.reset();
        }
    },
    // 计数重置
    reset : function()
    {
        MemoryInfoJSObj.LOOP_TOTAL = 0;
        MemoryInfoJSObj.LOOP_CNT = 0;
    }

}
