/*
 * 弹出窗
 * @date 2015-09-08
 * @auth AnNing
 */
define(["component", "scrollbox"], function(cmp) {
	
	var btnTpl = "<a class='{cls}'>{text}</a>";
	
	/*
	 * 组件的命名空间
	 */
	UI.Dialog = $.inherit(UI.Component, {
		
		/*
		 * 默认配置项
		 */
		defConf: {
			width: 600,					//默认宽
			height: 400,				//默认高
			title: "",					//标题
			autoDestroy: true,			//是否在关闭的时候就销毁
			draggable: true,			//是否可以拖拽
			afterShow: function() {},	//弹出后执行的回调函数
			afterHide: function() {},	//关闭后执行的回调函数
			/*
			 * 弹窗底部的按钮组
			 * {
			 *     text: 按钮上的文字,
			 *     cls: "",
			 *     handler: 回调函数,这里特别要说明的是,函数中的this对象指向实例本身
			 * }
			 */
			btns: []
		},
		
		/*
		 * 类名
		 */
		className: 'UI.Dialog',
		
		/*
		 * 模板
		 */
		tpl: [
			"<div id='{id}' class='pageui-dialog {cls}'>",
				"<div class='pageui-dialog-win'>",
					"<h1 class='pageui-dialog-header'>",
						"<p class='{titleCls}'>",
							"<span>{title}</span>",
						"</p>",
						"<a class='pageui-dialog-close'></a>",
					"</h1>",
					"<div class='pageui-dialog-core'></div>",
				"</div>",
			"</div>"
		].join(""),
		
		/*
		 * 初始化
		 */
		init: function() {
			
			var conf = this.conf;
			
			var $win = this._win = this.dom.find("div.pageui-dialog-win"),
				$core = this._core = $win.find("div.pageui-dialog-core");
			
			this._header = $win.find("h1");
			
			this._content = new UI.ScrollBox().appendTo($core);
			
			this._AD = conf.autoDestroy;
			
			this.draggable(conf.draggable);
			
			this.afterShow(conf.afterShow);
			
			this.afterHide(conf.afterHide);
			
			this.resize(conf.width, conf.height, true);
			
			this.setBtns(conf.btns);
			
			this.initEvent();
			
		},
		
		/*
		 * 注册事件
		 */
		initEvent: function() {
			
			var _this = this,
				$win = this._win,
				$core = this._core;
			
			/*
			 * 关闭dialog
			 */
			this._header.on("click", "a", function() {
				
				_this.pop(0);
				
			});
			
			/*
			 * 按钮组事件
			 */
			$win.on("click", "div.pageui-dialog-btns a", function() {
				
				var fn = $(this).data("_fn");
				
				fn && fn.call(_this);
					
			});
			
			/*
			 * 拖拽
			 */
			$win.on("mousedown", "h1.pageui-dialog-header", function(event) {
				
				_this._dragged = true;
				
				/*
				 * 这里做一下双重验证,防止有人恶意篡改
				 */
				if ($(this).hasClass("draggable") && _this._DA) {
					
					setTimeout(function() {
						
						if (!_this._dragged) return;
						
						var dx = event.pageX,
						dy = event.pageY,
						mt = parseInt($win.css("margin-top")),
						ml = parseInt($win.css("margin-left"));
					
						$core.hide();
						
						$win.css({opacity: .75});
						
						UI.document.on("mousemove.drag", function(event) {
							
							var mx = event.pageX,
								my = event.pageY;
							
							$win.css({margin: [my - dy + mt, "px", " 0 0 ", mx - dx + ml, "px"].join("")});
							
						});
						
						UI.document.on("mouseup.drag", function() {
							
							$core.show();
							
							$win.css({opacity: 1});
							
							UI.document.off(".drag");
							
						});
						
					}, 200);
					
				}
				
			});
			
			$win.on("mouseup", "h1.pageui-dialog-header", function(event) {
				
				_this._dragged = false;
				
			});
			
		},
		
		/*
		 * 弹出或隐藏
		 */
		pop: function(type) {
			
			var $dom = this.dom;
			
			if (!$dom) return this;
			
			if (type) {
				
				$dom.parent().size() ? $dom.show() : $dom.appendTo(UI.body);
				
				this.resize();
				
				this._afterShow.call(this);
				
			} else {
				
				this._AD ? this.destroy() : $dom.hide();
				
				this._afterHide.call(this);
				
			}
			
			return this;
			
		},
		
		/*
		 * 重新设定窗口大小
		 * w: width,
		 * h: height,
		 * flag: 是否重新居中
		 * 前两个参数传入的如果为undefined,就取当前值
		 */
		resize: function(w, h, flag) {
			
			var $win = this._win,
				height = h || $win.height();
			
			var style = {
				width: w || $win.width(),
				height: height
			};
			
			if (flag) style.margin = [-style.height / 2, "px", " 0 0 ", -style.width / 2, "px"].join("");
			
			$win.css(style);
			
			this._content.height(height - this._header.outerHeight() - (this._btns ? this._btns.outerHeight() : 0) - 10);
			
			return this;
			
		},
		
		/*
		 * 重设afterShow回调函数
		 * 注意,这里的show方法是故意这么做的,专门重写基类的show方法
		 */
		afterShow: function(fn) {
			
			this._afterShow = fn;
			
			return this;
			
		},
		
		/*
		 * 重设afterHide回调函数
		 * 注意,这里的hide方法是故意这么做的,专门重写基类的hide方法
		 */
		afterHide: function(fn) {
			
			this._afterHide = fn;
			
			return this;
			
		},
		
		/*
		 * 重设底部按钮组
		 */
		setBtns: function(btns) {
			
			if (btns && btns.length) {
				
				var $btns = this._btns = $("<div class='pageui-dialog-btns'></div>").appendTo(this._core);
				
				btns.each(function(i, item) {
					
					btnTpl.format2Object(item)
					  .appendTo($btns)
					  .data("_fn", item.handler);
					
				});
			
			} else {
				
				this._btns && this._btns.empty().remove();
				
				this._btns = null;
				
			}
			
			this.dom.is(":visible") && this.resize();
			
			return this;
			
		},
		
		/*
		 * 设置是否可以拖拽
		 */
		draggable: function(flag) {
			
			this._header[flag ? "addClass" : "removeClass"]("draggable");
			
			this._DA = flag;
			
			return this;
			
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
			
		},
		
		/*
		 * 模仿$.fn.empty
		 */
		empty: function() {
			
			this._content.empty();
			
			return this;
			
		},
		
		/*
		 * 设置标题
		 */
		setTitle: function(text, cls) {
			
			this._header.find("span").html(text);
			
			cls && this._header.find("p").attr({"class": cls});
			
			return this;
			
		}
		
	});
	
	/*
	 * alert
	 */
	UI.alert = function(p1, p2) {
		
		var title = "提示",
			text = "",
			fn = function() {};
		
		if (typeof p1 == "string" || typeof p1 == "number") {
			
			text = p1;
			
			if (p2 && typeof p2 == "function") fn = p2;
			
		} else {
			
			if (p1.title && typeof p1.title == "string") title = p1.title;
			
			if (p1.text && typeof p1.text == "string") text = p1.text;

			if (p1.fn && typeof p1.fn == "function") fn = p1.fn;
			
		}
		
		new UI.Dialog({
			title: title,
			width: 500,
			height: 240,
			btns: [{
				text: "确 定",
				handler: function() {
					
					fn();
					
					this.pop(0);
					
				}
			}]
		})
		  .append([
			"<table cellpadding='0' cellspacing='0' width='100%' height='100%'>",
				"<tbody>",
					"<tr>",
						"<td align='center'>", text, "</td>",
					"</tr>",
				"</tbody>",
			"</table>",
		  ].join(""))
		  .pop(true);
			
	};
	
	/*
	 * confirm
	 */
	UI.confirm = function(param) {
		
		var title = param.title || "提示",
			text = param.text || "",
			confirm = param.confirm || function() {},
			cancel = param.cancel || function() {},
			btns = param.btns || [];
		
		btns.unshift({
			text: "确 定",
			handler: function() {
				
				confirm();
				
			}
		});
		
		btns.push({
			text: "取 消",
			handler: function() {
				
				cancel();
				
			}
		});
			
		var $d = new UI.Dialog({
			title: title,
			width: 500,
			height: 240,
			btns: btns
		})
		  .append([
			"<table cellpadding='0' cellspacing='0' width='100%' height='100%'>",
				"<tbody>",
					"<tr>",
						"<td align='center'>", text, "</td>",
					"</tr>",
				"</tbody>",
			"</table>",
		  ].join(""))
		  .pop(true)
		  .on("click", "div.pageui-dialog-btns a", function() {
			  
			  $d.pop(false);
			  
		  });
			
	};
	
	return UI.Dialog;
	
});