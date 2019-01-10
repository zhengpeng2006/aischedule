/*
 * 文本框
 * @date 2016-06-10
 * @auth AnNing
 */
define(["component"], function(cmp) {
	
	var ClassName = "pageui-textinput",
		DefaultWidth = 170,
		DefaultHeight = 30;
	
	/*
	 * 组件的命名空间
	 */
	UI.TextInput = $.inherit(UI.Component, {
		
		/*
		 * 默认配置项
		 */
		defConf: {
			width: DefaultWidth,	//pageui-input-core默认宽度
			height: DefaultHeight,	//pageui-input-core默认高度
			name: "",				//供form表单使用
			prompt: "",				//提示信息
			errorPrompt: "",		//错误提示
			require: false,			//是否必填,该选项主要在form组件中使用
			validator: null,		//校验规则
			icons: [],				//可往输入框两边塞的图片
			value: "",				//默认值
			prefix: "",				//前缀文字
			suffix: "",				//后缀文字
			clearBtn: false,		//是否有清除按钮
			readonly: false,		//是否为只读
			type: "text"       		//是否是密码框
		},
		
		/*
		 * 类名
		 */
		className: "UI.TextInput",
		
		/*
		 * 组件模板
		 */
		tpl: [
		    "<div id='{id}' class='", ClassName, " {cls}'>",
		    	"<b class='pageui-input-prefix'>{prefix}</b>",
		    	"<span class='pageui-input-core'>",
		    		"<input class='pageui-input-text' />",
		    		"<input class='pageui-input-value' name='{name}' />",
		    	"</span>",
		    	"<b class='pageui-input-suffix'>{suffix}</b>",
		    "</div>"
		].join(""),
		
		/*
		 * 初始化方法,组件被实例化之后就会执行
		 */
		init: function() {
			
			var conf = this.conf;
			
			var $core = this._core = this.dom.find("span.pageui-input-core");
			
			this.name = conf.name;
			
			this._text = $core.find("input.pageui-input-text");
			
			this._value = $core.find("input.pageui-input-value");
			
			this.require(conf.require);
			
			this.setErrorPrompt(conf.errorPrompt);
			
			this.validator(conf.validator);
			
			this.clearBtn(conf.clearBtn);
			
			this.setPrompt(conf.prompt);
			
			this.setIcons(conf.icons);
			
			this.onChange(conf.onChange);
			
			this.onFocus(conf.onFocus);
			
			this.onBlur(conf.onBlur);

			this.width(conf.width);
			
			this.height(conf.height);
			
			this.setValue(conf.value);
			
			this.readonly(conf.readonly);
			
			this.setType(conf.type);
			
			this.initEvent();
			
		},
		
		/*
		 * 初始化事件
		 */
		initEvent: function() {
			
			var _this = this,
				$core = this._core,
				$text = this._text;
			
			/*
			 * 入焦
			 */
			$text.on("focus", function() {
				
				_this._clearError();
				
				if ($core.hasClass("disabled")) {
					
					$text.trigger("blur");
					
					return;
					
				}
				
				_this._focus();
				
				$core.addClass("focused");
				
				if ($text.hasClass("gray")) {
					
					$text.removeClass("gray").val("");
					
				} else if (_this._clearBtn) {
					
					_this._popClearBtn(true);
					
				}
				
			});
			
			/*
			 * 出焦
			 */
			$text.on("blur", function() {
				
				if ($core.hasClass("disabled")) {
					
					return;
					
				}
				
				$core.removeClass("focused");
				
				if (!$text.val().trim().length) {
					
					$text
					  .val(_this._prompt)
					  .addClass("gray");
					
				} else if (_this._clearBtn) {
					
					setTimeout(function() {//这样写是防止清空按钮点不到
						
						_this._popClearBtn(false);
						
					}, 150);
					
				}
				
				_this._blur();
				
			});
			
			/*
			 * 值改变
			 */
			$text.on("input propertychange", function() {
				
				var v = $text.val().trim();
				
				if (_this._clearBtn) {
					
					if (!v.length) {
						
						_this._popClearBtn(false);
						
					} else if (!_this._value.val().length) {
						
						_this._popClearBtn(true);
						
					}
					
				}
				
				_this._value.val(v);
				
				_this._change(v);
				
			});
			
			/*
			 * 清空
			 */
			this.dom.on("click", "a.pageui-textinput-clear", function() {
				
				_this._popClearBtn(false);
				
				_this._value.val("");
				
				_this._text
				  .val("")
				  .focus();
				
				_this._change("");
				
			});
			
		},
		
		/*
		 * 设置输入框宽度
		 */
		width: function(width) {
			
			var w = ($.isNumeric(width) ?  width : DefaultWidth) - 2;
			
			this._core.width(w);
			
			this._text.width(w - (this._core.find("i.pageui-textinput-icon").size() * 24) - (this.isCombo ? 24 : 0) - 10);
			
			return this;
			
		},
		
		/*
		 * 设置输入框高度
		 */
		height: function(height) {
			
			var h = $.isNumeric(height) ? height : DefaultHeight,
				p = (h - 18) / 2;
			
			this.dom.css({height: h, lineHeight: h + "px"});
			
			this._core.height(h - 2);
			
			this._text.css({paddingTop: p, paddingBottom: p});

			return this;
			
		},
		
		/*
		 * 是否只读
		 */
		readonly: function(b) {
			
			this._core[b ? "addClass" : "removeClass"]("disabled");
		
			return this;
		
		},
		
		/*
		 * 清空按钮.
		 */
		clearBtn: function(b) {
			
			this._clearBtn = Boolean(b);
		
			return this;
			
		},
		
		/*
		 * 设置提示信息
		 */
		setPrompt: function(text) {
			
			this._prompt = text || "";
			
			if (!this.getValue().length) {
				
				this._text[this._text.is("input") ? "val" : "text"](this._prompt)
				  .addClass("gray");
				
			}
		
			return this;
			
		},
		
		/*
		 * 设置改变事件
		 */
		onChange: function(fn) {
			
			this._change = $.isFunction(fn) ? fn : function() {};
		
			return this;
			
		},
		
		/*
		 * 设置入焦事件
		 */
		onFocus: function(fn) {
			
			this._focus = $.isFunction(fn) ? fn : function() {};
		
			return this;
			
		},
		
		/*
		 * 设置出焦事件
		 */
		onBlur: function(fn) {
			
			this._blur = $.isFunction(fn) ? fn : function() {};
		
			return this;
			
		},
		
		/*
		 * 获取text
		 */
		getText: function() {
			
			return this._value.val().trim();
			
		},
		
		/*
		 * 获取值
		 */
		getValue: function() {
			
			return this._value.val().trim();
			
		},
		
		/*
		 * 设置值
		 */
		setValue: function(v) {
			
			var val = v ? v.trim() : "";
			
			if (val.length) {
				
				this._text[this._text.is("input") ? "val" : "text"](val)
					.removeClass("gray");
				
			} else {
				
				this._text[this._text.is("input") ? "val" : "text"](this._prompt)
					.addClass("gray");
				
			}
			
			this._value.val(val);
			
			return this;
			
		},
		
		/*
		 * 设置按钮
		 */
		setIcons: function(icons) {
			
			var $text = this._text;
			
			$text.siblings(":not(input)").remove();
			
			this._icons = icons.each(function(i, item) {
				
				$text[item.align == "left" ? "before" : "after"]("<i class='pageui-textinput-icon " + (item.cls || "") + "'></i>");
				
			});
			
			this.width(this.dom.outerWidth());
			
			return this;
			
		},
		
		/*
		 * 是否为必填项
		 */
		require: function(b) {
			
			this._require = Boolean(b);
			
			return this;
			
		},
		
		/*
		 * 错误提示
		 */
		setErrorPrompt: function(text) {
		
			this._errorPrompt = text;
			
			return this;
			
		},
		
		/*
		 * 给组件提供校验方法
		 * 如果fn的返回值为string,则使用该string作为错误提示
		 */
		validator: function(fn) {
			
			this._validator = $.isFunction(fn) ? fn : function() {};
			
			return this;
			
		},
		
		/*
		 * 校验
		 */
		validate: function() {
			
			var flag;
			
			var value = this.getValue();
			
			if (this._require && !value.length) {
				
				this.error("必填项");
				
				flag = true;
				
			} else {
				
				flag = this._validator(value, this.getText());
				
				if (flag) {
					
					this.error(flag);
					
				}
				
			}
			
			return Boolean(flag);
			
		},
		
		/*
		 * 清空
		 */
		clear: function() {
			
			this._text[this._text.is("input") ? "val" : "text"](this._prompt)
				.addClass("gray");
			
			this._value.val("");
			
			return this;
			
		},
		
		/*
		 * 错误
		 */
		error: function(param) {
			
			var text = (typeof param == "string" ? param : this._errorPrompt);
			
			this._core.addClass("pageui-error")
				.find("div.pageui-input-error").remove()
			.end().append("<div class='pageui-input-error'>" + text + "</div>");
			
			return this;
			
		},
		
		/*
		 * 清空错误信息
		 */
		_clearError: function() {
			
			this._core.removeClass("pageui-error").find("div.pageui-input-error").remove();
			
		},
		
		/*
		 * 显示隐藏clearBtn
		 */
		_popClearBtn: function(b) {
			
			var w = this._text.width(),
				$clear = this.dom.find("a.pageui-textinput-clear"),
				flag = $clear.size();
			
			if (b && !flag) {
				
				this._text.width(w - 20);
				
				this._text.after("<a class='pageui-textinput-clear'></a>");
				
			} else if (flag) {
				
				this._text.width(w + 20);
				
				$clear.remove();
				
			}
			
		},
		
		/*
		 * 是否是密码框
		 */
		setType: function(type) {
			
			this._text.attr({type: (type.toLocaleLowerCase() == "password") ? "password": "text"});
			
			return this;
			
		}
		
	});
	
	/*
	 * 组件解析器
	 */
	UI.TextInput.parser = function($elem) {
		
		var config = {},
			name = $elem.attr("name"),
			value = $elem.attr("value");

		if (name) config.name = name;
		
		if (value) config.value = value;
		
		return config;
		
	};
	
	/*
	 * 注册文本框组件
	 */
	UI._register({
		cls: ClassName,
		namespace: "TextInput"
	});
	
	return UI.TextInput;
	
});
