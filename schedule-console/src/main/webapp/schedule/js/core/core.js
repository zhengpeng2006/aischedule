/*
 * 全局变量
 * 放在CORE中,控制变量污染
 * @auth AnNing
 */
define(["request", "prototype", "dialog", "pager"], function(request) {
	
	UI.Pager.formatParam = function(param) {
		
		return {
			offset: (param.pageNum - 1) * param.linage,
			linage: param.linage
		};
		
	};
	
	Request.type = 1;			//0为本地服务;1为远程服务

	Request.mockPref = "data/";	//请求管理~本地服务的前缀路径
	
	Request.srvPref = window.location.origin + window.location.pathname.substr(0, window.location.pathname.substr(1).indexOf("/")+1) + "/?";	//请求管理~远程服务的前缀路径
	
	var CORE = window.CORE = {},
		$document = CORE.document = $(document),
		$menu = CORE.menu = $("#page-menu"),
		$main = CORE.main = $("#page-main");
	
	var pageMap = {
		
		/* 基础配置 */
		"10_3": "service/config/task/task",            //调度任务配置
		"11_3": "service/config/host/host",            //主机配置
		"12_3": "service/config/host/node",            //主机节点配置
		"13_3": "service/config/host/app",             //主机应用配置
		"14_3": "service/config/strategy/strategy",    //部署策略配置
		"15_3": "service/config/monitor/monitor",      //监控任务配置
		"16_3": "service/config/user/user",            //用户配置
		/* 基础配置 */
		
		/* 监控 */
		"20_3": "service/monitor/host/host",
		"21_3": "service/monitor/bus/bus",
		"22_3": "service/monitor/warning/warning",
		"23_3": "service/monitor/log/log",
		"24_3": "service/monitor/status/status",
		/* 监控 */
		
		/* 调度管理 */
        "40_3": "service/manage/state",      //任务执行状态监控
        "41_3": "service/manage/report",             //任务当天执行报表
        "42_3": "service/manage/log",       //任务执行日志监控
        /* 调度管理 */
        
        /* 部署发布 */
        "30_3": "service/publish/process",            //应用启停
        "31_3": "service/publish/deploy"              //应用发布
        /* 部署发布 */
		
	};
	
	/*
	 * 重写ajax
	 */
	$.oldAjax = $.ajax;
	
	$.ajax = function(config) {
		
		$.oldAjax($.extend({}, config, {
			type: "POST",
			success: function(data) {
				
           	    if (typeof data === "string") data = $.parseJSON(data);
           	    
           	    if (data.context.x_resultcode === '-1') {
           	    	
           	         UI.alert(data.context.x_resultinfo);
           	    	
           	    } else {

           	    	if (data.data) {

    					if (data.data.retcode) {
    						
    						switch (data.data.retcode) {
    						
    						     case "0":
    						    	 
    						    	 config.success();
    						    	 
    						         break;
    						         
    						     case "1": 
    						    	 
    						    	 UI.alert(data.data.resultMsg);
    						    	 
    						    	 break;
    						    	 
    						     case "2": 
    						    	 
    						    	 UI.alert("系统异常");
    						    	 
    						    	 break;
    						   
                                 case "4": 
    						    	 
                                	 window.location.href = "login.html";
    						    	 
    						    	 break;
    						}
    						
    					} else {
    						
    						config.success(data);
    						
    					}
    					
    				} 
           	    	
           	    }
				
			},
			error: function() {
				
				config.error();
				
			}
		}));
		
	};
	
	/*
	 * 后台要求这么搞,随便调个服务用来检测是否没有cookie
	 */
//	$.ajax({
//        url: window.location.origin + window.location.pathname.substr(0, window.location.pathname.substr(1).indexOf("/")+1) + "/?service=ajax&page=module.monitor.configmgr.HostInfo&listener=qryAllHostInfo",
//        success: function(data) {
//        
//        }
//    });
	
	/*
	 * 初始化页面
	 */
	CORE.init = function() {
		
		$.ajax({
			url: Request.get("index/loadTreeData", 0),
			success: function(data) {
				
				var d = data.data["-1"].childNodes,
					arr = [],
					item, _arr;
				
				for (k in d) {
					
					item = d[k];
					
					_arr = [];
					
					for (j in item.childNodes) {
						
						_arr.push(["<dd data-id='", j, "'>", item.childNodes[j].text, "</dd>"].join(""));
						
					}
					
					arr.push([
					   	"<li data-id='", k, "'>",
					   		"<span>", item.text, "</span>",
					   		"<dl>", _arr.join(""), "</dl>",
					   	"</li>"
					].join(""));
					
				}
				
				$menu.append(arr.join(""));
				
				item = null; 
				
				arr = null;
				
			}
		});
		
	};
	

	var $this = $('span').first().toggleClass("active"), 
		$dl = $('dl').first(), 
		$dd = $dl.children().first();

	$dl.stop()[$this.hasClass("active") ? "slideDown" : "slideUp"]("fast");

	$dd.addClass("active");

	require([ "service/config/task/task" ], function(obj) {

		var $div = $("<div></div>").appendTo($main);

		obj.create($div);

	});

	
	/*
	 * 展开收起
	 */
	$menu.on("click", "span", function() {
		
		var $this = $(this).toggleClass("active"),
			$dl = $this.siblings("dl");
		
		$dl.stop()[$this.hasClass("active") ? "slideDown" : "slideUp"]("fast");
		
	});
	
	/*
	 * 点击一个模块
	 */
	$menu.on("click", "dd", function() {
		
		var $this = $(this);
		
		if ($this.hasClass("active")) return;
		
		$main.empty();
		
		var $active = $menu.find("dd.active").removeClass("active");

		$this.addClass("active");
		
		require([pageMap[$this.attr("data-id")]], function(obj) {
			
			var $div = $("<div></div>").appendTo($main);
			
			obj.create($div);
			
		});
		
	});
	
	/*
	 * panel内的tab控件
	 */
	$document.on("click", ".page-main-panel-tab-header span", function() {
		
		var $this = $(this).addClass("active");
		
		$this.siblings().removeClass("active");
		
		$this.parent().siblings(".page-main-panel-tab-content")
			.find(".page-main-panel-tab-item[name='" + $this.attr("name") + "']")
				.addClass("active")
			.siblings()
				.removeClass("active");
		
	});
	
	/*
	 * 回车查询
	 */
	$document.on("keyup", function(event) {
	   
	    if (event.keyCode == 13) {
	        
	        if ($("body>div.pageui-dialog").size()) return;
	        
	        var $query = $main.find("a.page-query");
	        
	        $query.size() && $query.trigger("click");
	        
	    }
	    
	});
	
	/*
	 * 获取任务类型
	 */
	CORE.getTaskType = [
  	      {
	    	  "text": "complex",
	    	  "value": "complex"
	      }, {
	    	  "text": "single",
	    	  "value": "single"
	      }, {
	    	  "text": "tf",
	    	  "value": "tf"
	      }, {
	    	  "text": "reload",
	    	  "value": "reload"
	      }
	];
	
	/*
	 * 获取所有的主机
	 */
	CORE.getAllHost = function() {
		
		var hostMap = {};
		
		$.ajax({
			url: Request.get("config/task/qryAllHostInfo"),
			async: false,
			success: function(data) {
	        	
			    if ( data.data && data.data.hostList) {
			    	
			    	var hostList = data.data.hostList;
			    	
			    	for (var i = 0; i < hostList.length; i++) {
			    		
			    		hostMap[hostList[i].VALUE] = hostList[i].TEXT;
			    		
			    	}
			    	
			    }
				
			}
		});
		
		return hostMap;
		
	};
	
	/*
	 * 将时间空间化为数字进行比较
	 */
	CORE.timeFormat = function(time) {
		
		return +time.split(" ").join("").split("-").join("").split(":").join("");
		
	};
	
	return CORE;
	
});