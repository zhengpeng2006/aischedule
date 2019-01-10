/*
 * 组件基类
 * @date 2016-06-09
 * @auth AnNing
 */
define(["prototype"], function() {
	
	$.fn.getPageUI = function() {
		
		return this.data("_pageui");
		
	};
	
	var UI = window.UI = function() {
		
		var arr = [];
		
		$.apply(null, arguments).each(function() {
			
			arr.push($(this).data("_pageui"));
			
		});
		
		return new UI.Instance(arr);
		
	};
	
	UI.Instance = function(arr) {
		
		this.data = arr;
		
		for (var i = 0, len = arr.length; i < len; i++) {
			
			this[i] = arr[i];
			
		}
		
	};
	
	UI.Instance.prototype.method = function(functionName) {
		
		var params = Array.prototype.slice.call(arguments, 1);
		
		this.data.each(function(i, item) {
			
			item[functionName] && item[functionName].apply(item, params);
			
		});
		
		return this;
		
	};
	
	/*
	 * 比较常用的标签,保存在UI对象下
	 */
	var $window = UI.window = $(window),
		$document = UI.document = $(document),
		$html = UI.html = $("html"),
		$body = UI.body = $("body");
	
	/*
	 * 反射池
	 */
	var reflexWords = {
		append: ["last", "nextAll"],
		prepend: ["first", "prevAll"],
		after: ["next", "nextUntil"],
		before: ["prev", "prevUntil"]
	};
	
	/*
	 * 页面是否可以托选
	 */
	UI.selectstart = function(flag) {
		
		flag ? 
		  $body.attr({"onselectstart": "return false"}).addClass("body-dragged")
		    : $body.removeAttr("onselectstart").removeClass("body-dragged");
		
	};
	
	/*
	 * 弹开的窗口对象保存在这里
	 */
	UI._topCombo = null;
	
	/*
	 * 弹出
	 */
	UI.showCombo = function($combo) {
		
		UI.hideCombo();
		
		$combo._core.addClass("focused");
		
		UI.body.append($combo._panel);
		
		UI._topCombo = $combo;
		
		topComboPosition();
		
	};
	
	/*
	 * 收起
	 */
	UI.hideCombo = function() {
		
		if (UI._topCombo) {
			
			UI._topCombo._panel.remove();
			
			UI._topCombo._panel = null;
			
			UI._topCombo._core.removeClass("focused");
			
			UI._topCombo._hidePanel();
			
			UI._topCombo = null;
			
		}
		
	};
	
	/*
	 * 计算combo的位置
	 */
	function topComboPosition() {
		
		if (UI._topCombo) {
			
			var offset = UI._topCombo._core.offset(),
				coh = UI._topCombo._core.outerHeight(),
				top = offset.top + coh + 5,
				ph = UI._topCombo._panel.outerHeight(),
				wh = $(window).height(),
				cssObj = {left: offset.left};
			
			if ((ph < offset.top) && ((top + ph) > wh)) {
				
				cssObj.bottom = wh - offset.top + 5;
				
			} else {
				
				cssObj.top = top;
				
			}
			
			UI._topCombo._panel.css(cssObj);
			
			//setTimeout(arguments.callee, 200);
			
		}
		
	}
	
	/*
	 * 自定义点击隐藏
	 */
	UI._topPop = null;
	
	/*
	 * 展开
	 */
	UI.showPop = function($j) {
		
		UI._topPop = $j.show();
		
	};
	
	/*
	 * 展开
	 */
	UI.hidePop = function() {
		
		if (UI._topPop) {
			
			UI._topPop.hide();
			
			UI._topPop = null;
			
		}
		
	};
	
	/*
	 * 公共事件
	 * 这里处理了点击任意地方收起下拉框的事件
	 */
	$document.on("mouseup", function(event) {
		
		var $target = $(event.srcElement || event.target);
		
		if ($target.hasClass("Non-Global") || $target.parents(".Non-Global").size()) return;
		
		UI.hideCombo();
		
		UI.hidePop();
		
	});
	
	/*
	 * 已经注册的组件类名
	 */
	var Clses = UI.Clses = [];
	
	/*
	 * 注册组件,该方法供子孙组件使用
	 * obj = {
	 * 	   cls: "pageui-textinput",		//组件dom的类名
	 *     namespace: "TextInput"
	 * }
	 */
	UI._register = function(obj) {
		
		Clses.push(obj.cls);
		
		reflexWords[obj.cls] = obj.namespace;
		
		UI[obj.namespace].create = function(elem) {

			var $elem = $(elem),
				dataConfig = eval("({" + ($elem.attr("data-config") || "") + "})"),
				config = UI[obj.namespace].parser ? $.extend(dataConfig, UI[obj.namespace].parser($elem, dataConfig)) : dataConfig;
			
			config.id = elem.id || config.id;
			
			new UI[obj.namespace](config).beforeTo($elem);
			
			$elem.remove();
			
		};
		
		$(function() {
			
			UI.body.find("." + obj.cls).each(function() {
				
				if (!this.pageui) {
					
					UI[obj.namespace].create(this);
					
				}
				
			});
			
		});
		
	};
	
	/*
	 * 通过组件的style方法可调整的属性
	 */
	var styleObj = {
		"position": 1,
		"float": 1,
		"width": 1,
		"height": 1,
		"top": 1,
		"right": 1,
		"bottom": 1,
		"left": 1,
		"margin": 1,
		"marginTop": 1,
		"marginRight": 1,
		"marginBottom": 1,
		"marginLeft": 1,
		"clear": 1,
		"display": 1,
		"z-index": 1
	};
	
	/*
	 * 根据ClassName获取类
	 * @className 类名
	 */
	$.getDefinitionByName = function(className) {
	
		var ps = className.split('.');
	
		var i = 0;
	
		var obj = window[ps[i]];
	
		while (i != ps.length - 1) {
	
			obj = obj[ps[++i]];
	
		};
	
		return obj;
	
	};
	
	/*
	 * 扩展jquery的继承
	 * @param sub 子类
	 * @param sup 父类
	 * @overrides 扩展的方法
	 * @使用方法
	 * UI.xx = $.inherit(UI.Component, {});
	 */
	$.inherit = function(sup, overrides) {
	
		var sub = function() {
	
			sup.apply(this, arguments);
	
		};
	
		var F = function() {};
	
		F.prototype = sup.prototype;
	
		sub.prototype = new F();
	
		sub.sup = new sup({__sup__: true});
	
		sub.prototype.constructor = sub;
	
		if (overrides) {
	
			for (var m in overrides) {
	
				sub.prototype[m] = overrides[m];
	
			};
	
		};
	
		return sub;
	
	};
	
	/*
	 * 重写$.fn[append, prepend, after, before]方法
	 */
	$.each(["append", "prepend"], function(i, fn) {
		
		var oldFn = $.fn["old" + fn] = $.fn[fn];
		
		$.fn[fn] = function(elem) {
			
			var $this = $(this),
				reflex = reflexWords[fn],
				$t = $this.children()[reflex[0]]();
			
			/*
			 * 插入组件
			 */
			if (elem instanceof UI.Component) {
	
				oldFn.call(this, elem.create());
		
			} else {
		
				oldFn.apply(this, arguments);
		
			}
			
			var $new = $t.size() ? $t[reflex[1]]() : $this.children();
			
			if (!$new.size()) return this;
			
			if (Clses.length) {
				
				var cls, $targets;
				
				for (var i = 0, il = Clses.length; i < il; i++) {
					
					cls = Clses[i];
					
					$targets = $new.filter("." + cls).add($new.find("." + cls));
					
					for (var j = 0, jl = $targets.size(); j < jl; j++) {
						
						(!$targets[j].pageui) && UI[reflexWords[cls]].create($targets[j]);
						
					}
					
				}
				
				$targets = null;
				
//				Clses.each(function(i, item) {
//					
//					$new.filter("." + item).add($new.find("." + item)).each(function() {
//						
//						(!this.pageui) && UI[reflexWords[item]].create(this);
//						
//					});
//					
//				});
				
			}
			
			/*
			 * 执行组件的afterRender方法
			 */
			if ($document.find($this).size()) {

				var $targets = $new.filter(".ui-nr").add($new.find(".ui-nr"));
				
				for (var n = 0, nl = $targets.size(); n < nl; n++) {
					
					$targets.eq(n)
						.removeClass("ui-nr")
					.data("_pageui").__AR__();
					
				}
				
				$targets = null;
				
//				$new.filter(".ui-nr").add($new.find(".ui-nr")).each(function() {
//					
//					var $this = $(this);
//					
//					$this.removeClass("ui-nr");
//					
//					$this.data("_pageui").__AR__();
//					
//				});
				
			}
			
			return this;
			
		};
		
	});
	
	/*
	 * 重写["after", "before"]方法
	 */
	$.each(["after", "before"], function(i, fn) {
		
		var oldFn = $.fn["old" + fn] = $.fn[fn];
		
		$.fn[fn] = function(elem) {
			
			var $this = $(this),
				reflex = reflexWords[fn],
				$target = $this[reflex[0]]();
			
			/*
			 * 插入组件
			 */
			if (elem instanceof UI.Component) {
	
				oldFn.call(this, elem.create());
		
			} else {
		
				oldFn.apply(this, arguments);
		
			}
			
			var $new = $this[reflex[1]]($target);
			
			if (!$new.size()) return this;
			
			if (Clses.length) {
				
				var cls, $targets;
				
				for (var i = 0, il = Clses.length; i < il; i++) {
					
					cls = Clses[i];
					
					$targets = $new.filter("." + cls).add($new.find("." + cls));
					
					for (var j = 0, jl = $targets.size(); j < jl; j++) {
						
						(!$targets[j].pageui) && UI[reflexWords[cls]].create($targets[j]);
						
					}
					
				}
				
				$targets = null;
				
//				Clses.each(function(i, item) {
//					
//					$new.filter("." + item).add($new.find("." + item)).each(function() {
//						
//						(!this.pageui) && UI[reflexWords[item]].create(this);
//						
//					});
//					
//				});
				
			}
			
			/*
			 * 执行组件的afterRender方法
			 */
			if ($document.find($this).size()) {

				var $targets = $new.filter(".ui-nr").add($new.find(".ui-nr"));
				
				for (var n = 0, nl = $targets.size(); n < nl; n++) {
					
					$targets.eq(n)
						.removeClass("ui-nr")
					.data("_pageui").__AR__();
					
				}
				
				$targets = null;
				
//				$new.filter(".ui-nr").add($new.find(".ui-nr")).each(function() {
//					
//					var $this = $(this);
//					
//					$this.removeClass("ui-nr");
//					
//					$this.data("_pageui").__AR__();
//					
//				});
					
			}
			
			return this;
			
		};
		
	});
	
	/*
	 * 重写html方法
	 */
	$.fn.oldHtml = $.fn.html;
	
	$.fn.html = function() {
		
		var $this = $(this);
		
		if (arguments.length) {
			
			$.fn.oldHtml.apply(this, arguments);
			
			if (!$this.children().size()) return this;
			
			if (Clses.length) {
				
				var cls, $targets;
				
				for (var i = 0, il = Clses.length; i < il; i++) {
					
					cls = Clses[i];
					
					$targets = $this.find("." + cls);
					
					for (var j = 0, jl = $targets.size(); j < jl; j++) {
						
						(!$targets[j].pageui) && UI[reflexWords[cls]].create($targets[j]);
						
					}
					
				}
				
				$targets = null;
				
//				Clses.each(function(i, item) {
//					
//					$this.find("." + item).each(function() {
//						
//						(!this.pageui) && UI[reflexWords[item]].create(this);
//						
//					});
//					
//				});
				
			}
			
			/*
			 * 执行组件的afterRender方法
			 */
			if ($document.find($this).size()) {

				var $targets = $this.find(".ui-nr");
				
				for (var n = 0, nl = $targets.size(); n < nl; n++) {
					
					$targets.eq(n)
						.removeClass("ui-nr")
					.data("_pageui").__AR__();
					
				}
				
				$targets = null;
				
//				$this.find(".ui-nr").each(function() {
//					
//					var $this = $(this);
//					
//					$this.removeClass("ui-nr");
//					
//					$this.data("_pageui").__AR__();
//					
//				});
				
			}
			
			return this;
			
		} else {
			
			return $this.oldHtml();
			
		}
		
	};
	
	/*
	 * renderTo: $(parent)
	 */
	UI.Component = function(conf) {
		
		if (conf && conf.__sup__) return;
		
		this.initConf(conf);//配置项处理
		
		if (this.tpl) {
			
			var pageui = $.GUID();
			
			/*
			 * 生成组件的dom,并把组件实例绑定在dom上
			 */
			this.dom = this.tpl.format2Object(this.conf).data("_pageui", this).addClass("ui-nr");

			/*
			 * 为组件的dom节点分配随机id
			 */
			this.dom[0].pageui = pageui;
			
			/*
			 * 初始化组件
			 */
			this.init();
			
			/*
			 * 设置组件是否可用,这里的是否可用只是在组件上加了一个disable的类名,具体实现需要各个组件自己实现
			 */
			this.disabled(this.conf.disabled);
			
			/*
			 * 设置组件的物理样式
			 */
			this.style(this.conf.style);
			
			/*
			 * 渲染后执行的回调函数
			 */
			this.afterRender(this.conf.afterRender);
			
			var parent = this.conf.renderTo;
			
			parent && $(parent).append(this);
			
		} 
	
	};
	
	UI.Component.prototype = {
		
		/*
		 * 默认配置项
		 */
		defConf: {},
		
		/*
		 * 子类必须重设此项
		 */
		className: "UI.Component",
		
		/*
		 * 初始化方法
		 */
		init: function(){
		
			this.initEvent();
		
		},
		
		/*
		 * 返回dom,$.fn.append,$.fn.prepend,$.fn.before,$.fn.after使用
		 */
		create: function() {
		
			return this.dom;
		
		},
		
		/*
		 * 注册事件,此方法由子类重写
		 */
		initEvent: function() {},
		
		/*
		 * init conf
		 */
		initConf: function(conf) {
		
			this.conf = conf || {};
			
			var definition = $.getDefinitionByName(this.className);
		
			var supO = definition && definition.sup;
		
			while (supO) {
		
				this.conf = $.extend(true, {}, supO.defConf, this.defConf, this.conf);
		
				supO = supO.className ? $.getDefinitionByName(supO.className) : undefined;
		
			}
		
		},
		
		/*
		 * 添加类名
		 */
		addClass: function(cls) {
			
			$.fn.addClass.apply(this.dom, arguments);
			
			return this;
			
		},
		
		/*
		 * 删除类名
		 */
		removeClass: function(cls) {
			
			$.fn.removeClass.apply(this.dom, arguments);
			
			return this;
			
		},
		
		/*
		 * 该组件是否已被渲染
		 */
		isRendered: false,
		
		/*
		 * 组件被添加到dom树后的回调
		 */
		afterRender: function(fn) {
			
			this._AFCallback = $.isFunction(fn) ? fn : function() {};
			
		},
		
		/*
		 * 如果某一个组件需要固有的afterRender方法,重写这个方法
		 */
		delay: function() {},
		
		/*
		 * 该方法会在组件被渲染到文档流中的时候执行
		 * 切勿修改,也不推荐重写
		 */
		__AR__: function() {
			
			this.delay();
			
			this._AFCallback();
			
		},
		
		/*
		 * 是否禁用
		 */
		disabled: function(b) {
			
			this.dom[b ? "addClass" : "removeClass"]("disabled");
		
			return this;
		
		},
		
		/*
		 * 获取组件id
		 */
		getId: function() {
		
			return this.dom[0].id;
		
		},
		
		/*
		 * 销毁
		 */
		destroy: function() {
		
			if (this.dom) {
				
				this.dom.remove();
				
			};
			
			return this;
		
		},
		
		/*
		 * 物理属性
		 * width, height, top, right, bottom, left
		 */
		style: function(config) {
			
			var obj = {};
			
			for (var key in config) {
				
				if (styleObj[key]) obj[key] = config[key]; 
				
			};
			
			this.dom.css(obj);
			
			return this;
			
		},
		
		/*
		 * 显示
		 */
		show: function() {
	
			this.dom && this.dom.show();
			
			return this;
	
		},
		
		/*
		 * 隐藏
		 */
		hide: function() {
	
			this.dom && this.dom.hide();
			
			return this;
	
		},
		
		/*
		 * 查找组件内元素
		 */
		find: function(selector) {
			
			return this.dom.find(selector);
			
		},
		
		/*
		 * 绑定事件
		 */
		on: function() {
			
			$.fn.on.apply(this.dom, arguments);
			
			return this;
			
		},
		
		/*
		 * 模拟$.fn.appendTo
		 */
		appendTo: function($jDom) {
			
			$jDom.eq(0).append(this);
			
			return this;
			
		},
		
		/*
		 * 模拟$.fn.prependTo
		 */
		prependTo: function($jDom) {
			
			$jDom.eq(0).prepend(this);
			
			return this;
			
		},
		
		/*
		 * 模拟$.fn.after
		 */
		afterTo: function($jDom) {
			
			$jDom.eq(0).after(this);
			
			return this;
			
		},
		
		/*
		 * 模拟$.fn.before
		 */
		beforeTo: function($jDom) {
			
			$jDom.eq(0).before(this);
			
			return this;
			
		}
	
	};
	
	return UI.Component;
	
});