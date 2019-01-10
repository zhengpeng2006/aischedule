/*
 * 带有纵向滚动条的容器
 * @date 2016-06-10
 * @auth AnNing
 */
define(["component"], function(cmp) {
	
	var ClassName = "pageui-scrollbox";
	
	/*
	 * 组件的命名空间
	 */
	UI.ScrollBox = $.inherit(UI.Component, {
		
		/*
		 * 默认配置项
		 */
		defConf: {
			width: "100%",
			height: "100%",
			content: ""
		},
		
		/*
		 * 类名
		 */
		className: 'UI.ScrollBox',
		
		/*
		 * 组件模板
		 */
		tpl: [
		    "<div id='{id}' class='", ClassName, " {cls}'>",
		    	"<div class='pageui-scrollbox-core'></div>",
		    	"<div class='pageui-scrollbox-zoneY'>",
		    		"<a class='pageui-scrollbox-barY'></a>",
		    	"</div>",
		    	"<div class='pageui-scrollbox-zoneX'>",
		    		"<a class='pageui-scrollbox-barX'></a>",
		    	"</div>",
		    "</div>"
		].join(""),
		
		/*
		 * 初始化方法,组件被实例化之后就会执行
		 */
		init: function() {
			
			var conf = this.conf;
		
			this._core = this.dom.find("div.pageui-scrollbox-core");

			this._zoneX = this.dom.find("div.pageui-scrollbox-zoneX");

			this._barX = this.dom.find("a.pageui-scrollbox-barX");

			this._zoneY = this.dom.find("div.pageui-scrollbox-zoneY");

			this._barY = this.dom.find("a.pageui-scrollbox-barY");

			this.width(conf.width);
			
			this.height(conf.height);
			
			this.html(conf.content);
			
		},
		
		/*
		 * 设置容器宽度
		 */
		width: function(width) {
			
			this.dom.width($.isNumeric(width) ?  width : "auto");
			
			this.resize();
		
		},
		
		/*
		 * 设置容器高度
		 */
		height: function(height) {
			
			this.dom.height($.isNumeric(height) ?  height : "100%");
			
			this.resize();
		
		},
		
		/*
		 * 渲染后,计算滚动条的状态
		 */
		delay: function() {
			
			this.resize();
			
		},
		
		/*
		 * 模拟html方法
		 */
		html: function() {
			
			$.fn.html.apply(this._core, arguments);
			
			this.resize();
			
			return this;
			
		},
		
		/*
		 * 模拟append方法
		 */
		append: function() {
			
			$.fn.append.apply(this._core, arguments);
			
			this.resize();
			
			return this;
			
		},
		
		/*
		 * 模拟prepend方法
		 */
		prepend: function() {
			
			$.fn.prepend.apply(this._core, arguments);
			
			this.resize();
			
			return this;
			
		},
		
		/*
		 * 模拟清空方法
		 */
		empty: function() {
			
			this._core.empty();
			
			this._core.css({"margin-right": 0});
			
			this._zoneY.hide();
			
			return this;
			
		},
		
		/*
		 * 重写show方法
		 */
		show: function() {
			
			this.dom.show();
			
			this.resize();
			
			return this;
			
		},
		
		/*
		 * 计算滚动条
		 */
		resize: function() {
			
			if (this.dom.is(":visible")) {
				
				var $core = this._core,
					w = $core.outerWidth(),
					h = $core.outerHeight(),
					sw = $core[0].scrollWidth,
					sh = $core[0].scrollHeight,
					sx = sw - w,
					sy = sh - h;
				
				if (sx > 0) {
					
					$core.css({bottom: 5});
					
					if (sy > 0) this._zoneX.css({right: 11});
					
					this._zoneX.show();
					
					this._barX.css({left: (this._zoneX.width() - this._barX.width()) * $core.scrollLeft() / sx});
					
				} else {
					
					$core.css({bottom: 0});
					
					this._zoneX.hide().css({right: 0});
					
				}
				
				if (sy > 0) {
					
					$core.css({right: 11});

					if (sx > 0) this._zoneY.css({bottom: 5});
					
					this._zoneY.show();
					
					this._barY.css({top: (this._zoneY.height() - this._barY.height()) * $core.scrollTop() / sy});
					
				} else {
					
					$core.css({right: 0});

					this._zoneY.hide().css({bottom: 0});
					
				}
				
			}
			
		}
		
	});
	
	/*
	 * 组件解析器
	 */
	UI.ScrollBox.parser = function($elem) {
		
		return {
			content: $elem.html()
		};
		
	};
	
	/*
	 * 注册容器组件
	 */
	UI._register({
		cls: ClassName,
		namespace: "ScrollBox"
	});
	
	/*
	 * 滚动事件
	 */
	UI.document.on("mousewheel DOMMouseScroll", function(event) {
	
		var target = event.srcElement || event.target;
		
		if (UI._topCombo && !UI._topCombo._panel.find(target).size()) {
			
			UI.hideCombo();
			
		}
		
		var $scroll = $(target).parents(".pageui-scrollbox").eq(0);
		
		if (!$scroll.size()) return;
		
		var $core = $scroll.find("div.pageui-scrollbox-core"),
			$bar = $scroll.find("a.pageui-scrollbox-barY");
		
		var h = $core.height(),
			sh = $core[0].scrollHeight,
			s = sh - h,
			sz = $bar.parent().height() - $bar.height();
		
		if (s <= 0) return;
		
		event.preventDefault ? event.preventDefault() : event.returnValue = false;
		
		var st;
		
		if (event.originalEvent.wheelDelta) {
			
			st = -event.originalEvent.wheelDelta / 1.2 + $core.scrollTop();
			
		} else if (event.originalEvent.detail) {
			
			st = event.originalEvent.detail * 100 + $core.scrollTop();
			
		}
		
		var bt = (st > s ? sz : sz * (st / s));
		
		if (bt < 0) bt = 0;
		
		$core.scrollTop(st);
		
		$bar.css({top: bt});
		
		return false;
		
	});
	
	/*
	 * 滚动条拖拽事件
	 */
	UI.document.on("mousedown", "a.pageui-scrollbox-barY", function(event) {
		
		UI.selectstart(true);
		
		var $bar = $(this).addClass("active"),
			$zone = $bar.parent(),
			$core = $zone.siblings(".pageui-scrollbox-core");
		
		var s = $core[0].scrollHeight - $core.height(),
			dy = event.pageY,
			st = parseInt($bar.css("top")),
			sz = $zone.height() - $bar.height();
		
		UI.html.on("mousemove.scrollDrag", function(event) {
			
			var step = event.pageY - dy,
				_bt = st + step;
			
			if (_bt < 0) {
				
				$bar.css({top: 0});
				
				$core.scrollTop(0);
				
			} else if (_bt > sz) {
				
				$bar.css({top: sz});
				
				$core.scrollTop(s);
				
			} else {
				
				$bar.css({top: _bt});
				
				$core.scrollTop((_bt / sz) * s);
				
			}
			
		});
		
		UI.html.on("mouseup.scrollDrag", function(event) {
			
			event.stopPropagation();
			
			$bar.removeClass("active");
			
			UI.html.off(".scrollDrag");
			
			UI.selectstart(false);
			
		});
		
	});
	
	/*
	 * 滚动条拖拽事件
	 */
	UI.document.on("mousedown", "a.pageui-scrollbox-barX", function(event) {
		
		UI.selectstart(true);
		
		var $bar = $(this).addClass("active"),
			$zone = $bar.parent(),
			$core = $zone.siblings(".pageui-scrollbox-core");
		
		var s = $core[0].scrollWidth - $core.width(),
			dx = event.pageX,
			sl = parseInt($bar.css("left")),
			sz = $zone.width() - $bar.width();
		
		UI.html.on("mousemove.scrollDrag", function(event) {
			
			var step = event.pageX - dx,
				_bl = sl + step;
			
			if (_bl < 0) {
				
				$bar.css({left: 0});
				
				$core.scrollLeft(0);
				
			} else if (_bl > sz) {
				
				$bar.css({left: sz});
				
				$core.scrollLeft(s);
				
			} else {
				
				$bar.css({left: _bl});
				
				$core.scrollLeft((_bl / sz) * s);
				
			}
			
		});
		
		UI.html.on("mouseup.scrollDrag", function(event) {
			
			event.stopPropagation();
			
			$bar.removeClass("active");
			
			UI.html.off(".scrollDrag");
			
			UI.selectstart(false);
			
		});
		
	});
	
	return UI.ScrollBox;
	
});