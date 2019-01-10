define(["core", "grid", "form"], function () {

    var $grid,
        $form,
        $searchBtn,
        requestParam = {},
        type,
        array;

    /*
     * 初始化文档流
     */
    function initDom($page) {

        $page.addClass("process-info").append([
            "<div class='page-main-title'>应用启停</div>",
            "<div class='page-main-tab'>",
            "<a type='1' class='active'>业务纬度</a>",
            "<a type='2'>主机纬度</a>",
            "</div>",
            "<div class='page-main-searcher'></div>",
            "<div class='page-main-panel'>",
            "<div class='page-main-panel-bar'>",
            "<a name='stop'>终止</a>",
            "<p>|</p>",
            "<a name='start'>启动</a>",
            "</div>",
            "</div>",
        ].join(""));

        createForm($page);

        $searchBtn = $("<div class='searcher-btn process-info-searcher'><a class='pageui-btn page-query'>查询</a></div>").appendTo($page.find("div.page-main-searcher"));

        $grid = new UI.Grid({
            pager: {
                url: Request.get("publish/qryProcess"),
                getParams: function () {

                    array = [];

                    return requestParam;

                },
                totalFilter: function (data) {

                    return data.data.total;

                }
            },
            loadFilter: function (data) {

                if (data.data) {

                    return data.data.processTableItems;

                }

            },
            columns: [{
                checkbox: true
            }, {
                field: "serverCode",
                title: "应用编码"
            }, {
                field: "hostName",
                title: "主机名"
            }, {
                field: "hostGroup",
                title: "主机集群"
            }, {
                field: "taskCode",
                title: "任务编码"
            }, {
                field: "processState",
                title: "进程状态"
            }, {
                field: "operation",
                title: "操作",
                formatter: function (val, row) {

                    return [
                        "<a class='pageui-grid-icon icon-view' name='view' title='详情'></a>",
                    ].join("");

                }
            }],
            checkOnRow: true,
            onCheck: function (row, flag, index) {

                flag ? array.push(row.serverCode) : array.splice(array.indexOf(row.serverCode), 1);

            }
        }).appendTo($page.find("div.page-main-panel"));

    }

    function createForm($page) {

        if ($form) $form.destroy();

        $form = new UI.Form({
            cls: "common-pageui-form",
            columns: [].concat(type === '1' ? [
                {
                    type: "ComboBox",
                    name: "taskGroup",
                    prefix: "任务分组:",
                    width: 150,
                    searcher: true,
                    require: true,
                    valueField: "groupCode",
                    textField: "groupName",
                    url: Request.get("config/task/qryTaskGroup"),
                    loadFilter: function (data) {

                        return data.data.groupInfosItems;

                    },
                    onChange: function (data) {

                        $form.getItem("taskCode").clear();

                        if (data) {

                            $.ajax({
                                url: Request.get("manage/showTaskCodes"),
                                data: {
                                    groupCode: data.groupCode
                                },
                                success: function (data) {

                                    $form.getItem("taskCode").loadData(data.data);

                                }

                            });

                        }
                    }
                }, {
                    type: "ComboBox",
                    name: "taskCode",
                    prefix: "任务名称:",
                    textField: "value",
                    valueField: "key",
                    require: true,
                    searcher: true,
                    width: 150
                }
            ] : [{
                type: "ComboBox",
                name: "hostGroup",
                prefix: "主机分组:",
                clearBtn: true,
                searcher: true,
                width: 150,
                valueField: "groupId",
                textField: "groupName",
                url: Request.get("config/host/qryHostGroup"),
                loadFilter: function (data) {

                    return data.data.groupTabItems;

                },
                getUrlParam: function () {

                    return {
                        level: 1,
                        treeNodeId: -1
                    };

                },
                onChange: function (data) {

                    $form.getItem("hostId").clear();

                    if (data) {

                        $.ajax({
                            url: Request.get("config/host/qryHost"),
                            data: {
                                level: 2,
                                treeNodeId: data.groupId
                            },
                            success: function (data) {

                                $form.getItem("hostId").loadData(data.data.hostTabItems);

                            }

                        });

                    }

                }
            }, {
                type: "ComboBox",
                name: "hostId",
                prefix: "主机名称:",
                clearBtn: true,
                searcher: true,
                width: 150,
                valueField: "hostId",
                textField: "hostName",
                onChange: function(data) {

                    $form.getItem("nodeId").clear();

                    if (data) {

                        $.ajax({
                            url: Request.get("config/host/qryNode"),
                            data: {
                                level: 3,
                                treeNodeId: data.hostId
                            },
                            success: function(data) {

                                $form.getItem("nodeId").loadData(data.data.nodeTabItems);

                            }

                        });

                    }

                }
            }, {
                type: "ComboBox",
                name: "nodeId",
                prefix: "节点名称:",
                width: 150,
                searcher: true,
                clearBtn: true,
                valueField: "nodeId",
                textField: "nodeName"
            }, {
                type: "TextInput",
                name: "taskCode",
                prefix: "应用编码:",
                width: 150
            }])
        }).prependTo($page.find("div.page-main-searcher"));

    }

    /*
     * 初始化事件
     */
    function initEvent($page) {

        /*
         * 切换tab
         */
        $page.on("click", "div.page-main-tab>a", function () {

            $(this).addClass("active").siblings().removeClass("active");

            type = $(this).attr("type");

            array = [];

            createForm($page);

            $grid.loadData([]);

        });

        /*
         * 查询
         */
        $searchBtn.on("click", "a", function () {

            var data = $form.getData(),
                qryData = [];

            if (!data) return;

            if (type === '2') {

                if (!data.hostGroup && !data.hostId && !data.taskCode && !data.nodeId) {

                    UI.alert("请至少填写一个查询条件!");

                    return;

                }

            }

            qryData.push(data);

            qryData.push({});

            requestParam = (type === '1') ? {
                BusiQueryForm: JSON.stringify(qryData),
                formName: 'BusiQueryForm'
            } : {
                hostQueryForm: JSON.stringify(qryData),
                formName: 'hostQueryForm'
            };

            $grid.onRequest(true);

        });

        /*
         * 启动，终止
         */
        $page.on("click", "div.page-main-panel-bar>a", function () {

            if (!array.length) {

                UI.alert("请选择一个应用!");

                return;

            }

            $.ajax({
                url: Request.get("publish/operateProcess"),
                data: {
                    appCodes: array.join(","),
                    operationId: $(this).attr("name") === 'start' ? 1 : 2
                },
                success: function (data) {

                    UI.alert([
                        "操作成功数为:", data.data.successNum, "</br>",
                        "操作失败数为:", data.data.failedNum, "</br>",
                        "失败详情:", data.data.failedCode
                    ].join(""));

                    array = [];

                    $grid.onRequest(true);

                }
            });

        });

        /*
         * 详情
         */
        $grid.on("click", "a.pageui-grid-icon", function () {

            var $this = $(this),
                row = $grid.getRowData($this.closest("tr").index());

            if ($this.attr("name") === 'view') {

                require(["service/publish/process-setting"], function (obj) {

                    obj.create({
                        serverCode: row.serverCode
                    });

                });

            }

        });

    }

    /*
     * 创建页面对象
     */
    function create($page) {

        type = "1";

        array = [];

        initDom($page);

        initEvent($page);

        return $page;

    }

    return {
        create: create
    };

});