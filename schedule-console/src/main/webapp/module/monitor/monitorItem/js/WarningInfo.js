/**
 * 闭包对象,基础配置页面js
 */
$(function() {
    WarningInfoJs.init();
});
// group是在一个div中，主机信息和进程信息是在table里
var WarningInfoJs = (function() {
	
	//默认版本号
	var CUR_VERSION = -1;

    return {
        // 初始化
        init : function() {
            WarningInfoJs.getData();
        },
        refreshInfo : function() {
            WarningInfoJs.getData();
        },
        getData : function() {
            $.beginPageLoading();
            $.ajax.submit(null, 'loadTableData', null, null, function(result) {
                var hostServer = result.get("hostServer");
                CUR_VERSION = result.get("curVersion");
                var htmlStr = "";
                // 分组的个数
                for (var i = 0; i < hostServer.length; i++) {
                    var groupId = "group" + i;
                    var group = hostServer.get(i);
                    htmlStr += "<div id='" + groupId + "' class='groupDiv'>" + WarningInfoJs.createGroup(group)
                            + "</div>";
                }
                var div = $("#grpHostDiv");
                div.html(htmlStr);
                $.endPageLoading();
            }, null);
        },
        createGroup : function(groupArr) {
            var group = "";
            var groupHtml = "";
            if (groupArr.length > 0) {
                group = groupArr.get(0).get("group");
                groupHtml += "<div class='group'>" + group + ":" + "</div><br/>";
            }
            for (var i = 1; i < groupArr.length; i++) {
                var host = groupArr.get(i);
                groupHtml += WarningInfoJs.createHost(host);
            }
            return groupHtml;
        },
        createHost : function(hostArr) {
            var host = "";
            var hostHtml = "";
            hostHtml += "<table class='tab'>";
            if (hostArr.length > 0) {
                host = hostArr.get(0).get("title");
                hostHtml += "<tr height='15px'><td>";
                hostHtml += "<span class='host'>" + host + "</span>" + "</td>" + "<td><span class='host'>" + "</span>"
                        + "</td>" + "<td><span class='host'>" + "</span>" + "</td>" + "<td><span class='host'>"
                        + "</span>" + "</td>" + "</tr>";
            }
            for (var i = 1; i < hostArr.length; i++) {
                var server = hostArr.get(i);
                hostHtml += WarningInfoJs.createServer(server);
            }
            hostHtml += "</table>";
            return hostHtml;
        },
        createServer : function(serverArr) {
            var serverHtml = "";
            serverHtml += "<tr>";
            var j = 0;
            for (var i = 0; i < serverArr.length; i++) {
                var server = serverArr.get(i);
                var serverCode = server.get("serverCode");
                var desc = server.get("desc");
                var tableStr = "<table class='serverTab'><tr><td colspan='3' style='text-align:center'>" + serverCode
                        + "</td></tr>"
                        +"<tr><td>任务数："+server.get("totolCnt")+"&nbsp;&nbsp;&nbsp;</td>"
                        +"<td>执行中："+server.get("runCnt")+"&nbsp;&nbsp;&nbsp;</td>"
                        +"<td>未启动："+server.get("stopCut")+"</td></tr>"
                        		+"</table>";
                if ("未启动的进程" == desc) {
                    j++;
                    serverHtml += "<td>";
                    serverHtml += "<span class='noStartSpan' id='span"+serverCode+j+"' ondblclick=\"WarningInfoJs.dbClick('"+serverCode+"')\">" + tableStr + "</span>";
                    if (j % 4 == 0) {
                        serverHtml += "</td></tr>";
                    } else {
                        if (j == serverArr.length) {
                            serverHtml += "</td></tr>";
                        } else {
                            serverHtml += "</td>";
                        }
                    }
                }
                if ("重复的进程" == desc) {
                    j++;
                    serverHtml += "<td>";
                    serverHtml += "<span class='repeatSpan' id='span"+serverCode+j+"' ondblclick=\"WarningInfoJs.dbClick('"+serverCode+"')\">" + tableStr + "</span>";
                    if (j % 4 == 0) {
                        serverHtml += "</td></tr>";
                    } else {
                        if (j == serverArr.length) {
                            serverHtml += "</td></tr>";
                        } else {
                            serverHtml += "</td>";
                        }
                    }
                }
                if ("正常的进程" == desc) {
                    j++;
                    serverHtml += "<td>";
                    serverHtml += "<span class='normalSpan' id='span"+serverCode+j+"' ondblclick=\"WarningInfoJs.dbClick('"+serverCode+"')\">" + tableStr + "</span>";
                    if (j % 4 == 0) {
                        serverHtml += "</td></tr>";
                    } else {
                        if (j == serverArr.length) {
                            serverHtml += "</td></tr>";
                        } else {
                            serverHtml += "</td>";
                        }
                    }
                }
            }
            return serverHtml;
        },
       // 判空字符串和空格
		isNullOrEmpty : function(str) {
			if (str) {
				if ($.trim(str).length > 0) {
					return false;
				}
			}
			return true;
		},
        dbClick:function(serverCode){
        	//为空则不去查
        	if (WarningInfoJs.isNullOrEmpty(serverCode)){
        		return;
        	};
        	//校验页面版本和后台版本的一致性 不一致则提示
            var flag = ajaxCheck(null, "checkVersion", "version=" + CUR_VERSION, 'flag', null);
            if ("F" == flag.rscode()){
            	MessageBox.alert("提示框", "任务状态有更新，页面将自动刷新", function() {});
                WarningInfoJs.refreshInfo();
            }else{
            	WarningInfoJs.showTaskViews(serverCode);
            }
        },
        showTaskViews : function(serverCode){
        	$.ajax.submit(null, 'loadTaskInfo', "serverCode=" + serverCode,
                    "taskTab", function(result) {
                    }, null);
            popupDiv('taskTab',700,'任务详情');
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