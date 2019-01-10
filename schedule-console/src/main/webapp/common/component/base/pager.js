/*
 * Pager
 * @date 2016-06-25
 * @auth AnNing
 */
define(["component"], function(cmp) {
	
	/*
	 * 分页
	 */
	UI.Pager = $.inherit(UI.Component, {
		
		/*
		 * 默认配置项
		 */
		defConf: {
			url: "",				//请求地址
			type: "recent",			//分页的类型
			success: null,			//成功后的回调函数
			getParams: null,		//请求前会通过这个函数或许请求所需的参数
			paramStringify: false,	//是否将请求入参使用JSON.stringify方法转换
			totalFilter: null,		//用来过滤total这个字段
			emptyTip: "",			//空结果时候展示的提示
			linage: 10				//每页多少行
		},
		
		/*
		 * 类名
		 */
		className: "UI.Pager",
		
		/*
		 * 模板
		 */
		tpl: "<div id='{id}' class='pageui-pager {cls}'></div>",
		
		/*
		 * 初始化
		 */
		init: function() {
			
			var conf = this.conf;
			
			this._PS = conf.paramStringify;
			
			this.emptyTip(conf.emptyTip);
			
			this.url(conf.url);
			
			this.method(conf.method);
			
			this.linage(conf.linage);
			
			this.success(conf.success);
			
			this.getParams(conf.getParams);
			
			this.totalFilter(conf.totalFilter);
			
			this.type(conf.type);
			
			this.initEvent();
			
		},
		
		/*
		 * 初始化事件
		 */
		initEvent: function() {
			
			var _this = this;
			
			this.dom.on("click", "a", function() {
				
				_this._PN = $(this).attr("name");
				
				_this.onRequest();
				
			});
			
		},
		
		/*
		 * 空数据提示信息
		 * param可以是一段字符串也可以是一个方法,方法需要返回一段字符串
		 */
		emptyTip: function(param) {
			
			this._ET = param;
			
			return this;
			
		},
		
		/*
		 * 设置请求的url
		 */
		url: function(url) {
			
			this._URL = url;
			
			return this;
			
		},
		
		/*
		 * 设置每页多少行
		 */
		linage: function(num) {
			
			this._L = (isNaN(num) || num <= 0) ? 10 : Math.ceil(num); 
			
			return this;
			
		},
		
		/*
		 * 设置一个函数,请求成功后的毁掉函数
		 */
		success: function(fn) {
			
			this._S = $.isFunction(fn) ? fn : function() {};
			
			return this;
			
		},
		
		/*
		 * 设置一个函数,这个函数会在请求时添加额外的参数
		 */
		getParams: function(fn) {
			
			this._GP = $.isFunction(fn) ? fn : function() {};
			
			return this;
			
		},
		
		/*
		 * 设置分页类型
		 */
		type: function(type) {
			
			this._R = UI.Pager.Render[type] || UI.Pager.Render["recent"];
			
			return this;
			
		},
		
		method: function(type) {
			
			this._M = (type == "post" ? "POST" : "GET");
			
			return this;
			
		},
		
		/*
		 * 分页嘛,就必须要有total这个属性了,这个函数是为了有些请求返回的对象total不是这个字段
		 */
		totalFilter: function(fn) {
			
			this._TF = $.isFunction(fn) ? fn : function(data) {
				
				return data.total;
				
			};
			
			return this;
			
		},
		
		/*
		 * 请求
		 */
		onRequest: function(p1, p2) {
			
			var flag = Boolean(p1),
				obj = {};
			
			if (p2 && $.isPlainObject(p2)) {
				
				obj = p2;
				
				flag = Boolean(p1);
				
			} else if ($.isPlainObject(p1)) {
				
				obj = p1;
				
				flag = false;
				
			}
			
			var _this = this,
				pageNum = this._PN = flag ? 1 : this._PN;
				linage = this._L,
				param = {
					pageNum: pageNum,
					linage: linage
				};
				
			var data = $.extend(UI.Pager.formatParam({pageNum: pageNum, linage: linage}), this._GP(param) || {}, obj);
				
			$.ajax({
				url: this._URL,
				type: this._M,
				data: this._PS ? JSON.stringify(data) : data,
				success: function(data) {
					
					_this._S(data);
					
					_this._render(+pageNum, +linage, _this._TF(data));
					
				}
			});
			
			return this;
			
		},
		
		/*
		 * 跳转到第N页
		 */
		jumpTo: function(num) {
			
			this._PN = (isNaN(num) && num < 1) ? 1 : Math.floor(num);
			
			this.onRequest();
			
			return this;
			
		},
		
		/*
		 * 渲染分页
		 */
		_render: function(pageNum, linage, total) {
			
			if (total) {
				
				this.dom.html(this._R(pageNum, linage, total));
				
			} else {
				
				this.dom.html("<div class='pageui-pager-empty'>" + ($.isFunction(this._ET) ? this._ET() : this._ET) + "</div>");
				
			}
			
		}
	
	});
	
	UI.Pager.formatParam = function(param) {
		
		return param;
		
	};
	
	/*
	 * 通过当前页,每页行数,总行数渲染分页
	 */
	UI.Pager.Render = {
		recent: function(pageNum, linage, total) {
			
			var pageCount = Math.ceil(total / linage),
				arr = [];
			
			if (pageCount > 5) {
				
				arr.push("<a name='" + pageNum + "' class='active'>" + pageNum + "</a>");
				
				if (pageNum > 1) {
					
					arr.unshift([
					    "<a name='", (pageNum - 1), "'>", (pageNum - 1), "</a>"
					].join(""));
					
					if (pageNum > 2) {
						
						arr.unshift([
						    "<a name='1'>1</a>",
						    "<span>...</span>"
						].join(""));
						
					}
					
				}
				
				if (pageNum < pageCount) {
					
					arr.push([
					    "<a name='", (pageNum + 1), "'>", (pageNum + 1), "</a>"
					].join(""));
					
					if (pageNum < (pageCount - 1)) {
						
						arr.push([
							"<span>...</span>",
							"<a name='", pageCount, "'>", pageCount, "</a>"
						].join(""));
						
					}
					
				}
				
			} else {
				
				for (var i = 0; i < pageCount;) {
					
					arr.push(["<a name='", (++i), "'", (i == pageNum ? " class='active'" : ""), ">", i, "</a>"].join(""));
					
				}
				
			}
			
			return arr.join("");
			
		}
	};
	
	/*
	 * 自定义分页
	 */
	UI.Pager.setRender = function(name, fn) {
		
		UI.Pager.Render[name] = fn;
		
	};
	
	return UI.Pager;
	
});