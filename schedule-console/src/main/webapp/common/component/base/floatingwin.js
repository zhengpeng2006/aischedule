/*
 * 浮窗
 * $auth AnNing
 * $date 2016-01-07
 */
define(["component"], function() {
	
	/*
	 * 浮窗,从页面右侧弹出
	 */
	UI.FloatingWin = $.inherit(UI.Component, {
		
		/*
		 * 默认配置项
		 */
		defConf: {
			title: "",
			width: 600,
			autoDestroy: true,
			afterShow: function() {},	//弹出后执行的回调函数
			afterHide: function() {},	//关闭后执行的回调函数
			/*
			 * 浮窗底部的按钮组,和弹窗是一样样的
			 * {
			 *     label: 按钮上的文字,
			 *     name: 随便个什么都行,但是唯一,
			 *     handler: 回调函数,这里特别要说明的是,函数中的this对象指向实例本身
			 * }
			 */
			btns: []
		},
		
		/*
		 * 类名
		 */
		className: 'UI.FloatingWin',
		
		/*
		 * 模板
		 */
		tpl: [
		    "<div class='pageui-floatingWin {cls}'>",
		    	"<div class='pageui-floatingWin-core'>",
		    		"<div class='pageui-floatingWin-content'></div>",
		    	"</div>",
		    	"<div class='pageui-floatingWin-shadow'></div>",
		    "</div>"
		].join(""),
		
		/*
		 * 初始化方法
		 */
		init: function() {
			
			var conf = this.conf,
				$dom = this.dom;
			
			this._AD = conf.autoDestroy;
			
			this._core = $dom.find("div.pageui-floatingWin-core");
			
			this._content = $dom.find("div.pageui-floatingWin-content");
			
			this._shadow = $dom.find("div.pageui-floatingWin-shadow");
			
			this.afterShow(conf.afterShow);
			
			this.afterHide(conf.afterHide);
			
			this.width(conf.width);
			
			this.initEvent();
			
		},
		
		/*
		 * 初始化事件
		 */
		initEvent: function() {
			
			var _this = this;
			
			/*
			 * 点击蒙层关闭浮窗事件
			 */
			this._shadow.on("click", function() {
				
				_this.pop(false);
				
			});
			
		},
		
		/*
		 * 弹出或隐藏
		 */
		pop: function(type) {
			
			var _this = this,
				$dom = this.dom,
				$core = this._core;
			
			if (!$dom) return;
			
			if (type) {
				
				if (!$dom.parent().size()) $dom.appendTo(UI.body);
				
				UI._FW_ = this;
				
				$core.css({right: -this.dom.width(), opacity: 0});
				
				$core.stop().animate({right: 0, opacity: 1}, "fast", function() {
					
					_this._afterShow.call(_this);
					
				});
				
				this._shadow.fadeIn("linear");
				
			} else {
				
				$dom.stop().fadeOut("fast", function() {
					
					if (_this._AD) _this.destroy();
					
					_this._afterHide.call(_this);
					
				});
				
			}
			
			return this;
			
		},
		
		/*
		 * 重设afterShow回调函数
		 */
		afterShow: function(fn) {
			
			this._afterShow = fn;
			
		},
		
		/*
		 * 重设afterHide回调函数
		 */
		afterHide: function(fn) {
			
			this._afterHide = fn;
			
		},
		
		/*
		 * 重设浮窗的宽度
		 */
		width: function(w) {
			
			var _w = parseInt(w),
				maxW = Math.round(UI.window.width() * .618);
			
			var width = isNaN(_w) ? 600 : (_w < 600 ? 600 : (_w > maxW ? maxW : _w));
			
			this._core.width(width);
			
		},
		
		/*
		 * 模仿$.fn.append
		 */
		append: function(param) {
			
			this._content.append(param);
			
			return this;
			
		},
		
		/*
		 * 模仿$.fn.prepend
		 */
		prepend: function(param) {
			
			this._content.prepend(param);
			
			return this;
			
		},
		
		/*
		 * 模仿$.fn.html
		 */
		html: function(text) {
			
			this._content.html(text);
			
			return this;
			
		}
		
	});
	
	return UI.FloatingWin;
	
});
