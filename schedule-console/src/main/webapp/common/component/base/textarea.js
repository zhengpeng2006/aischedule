/*
 * 文本域
 * @date 2016-07-05
 */
define(["component", "textinput"], function(cmp) {
	
	var ClassName = "pageui-textarea",
		DefaultWidth = 170,
		DefaultHeight = 60;
	
	/*
	 * 组件的命名空间
	 */
	UI.TextArea = $.inherit(UI.TextInput, {
		
		/*
		 * 默认配置项
		 */
		defConf: {
			width: DefaultWidth,	//pageui-input-core默认宽度
			height: DefaultHeight,	//pageui-input-core默认高度
			name: "",				//供form表单使用
			prompt: "",				//提示信息
			value: "",				//默认值
			prefix: "",				//前缀文字
			suffix: "",				//后缀文字
			readonly: false			//是否为只读
		},
		
		/*
		 * 类名
		 */
		className: 'UI.TextArea',
		
		/*
		 * 组件模板
		 */
		tpl: [
		    "<div id='{id}' class='", ClassName, " {cls}'>",
		    	"<b class='pageui-input-prefix'>{prefix}</b>",
		    	"<span class='pageui-input-core'>",
		    		"<textarea class='pageui-textarea-text' name='{name}'></textarea>",
		    		"<div class='pageui-textarea-prompt'></div>",
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
			
            this._text = this._value = $core.find("textarea.pageui-textarea-text");
			
			this._tip = $core.find("div.pageui-textarea-prompt");
			
			this.name = name;
			
			this.require(conf.require);
			
			this.setErrorPrompt(conf.errorPrompt);
			
			this.validator(conf.validator);
			
			this.clearBtn(conf.clearBtn);
			
			this.setPrompt(conf.prompt);
			
			this.onChange(conf.onChange);
			
			this.onFocus(conf.onFocus);
			
			this.onBlur(conf.onBlur);

			this.width(conf.width);
			
			this.height(conf.height);
			
			this.setValue(conf.value);
			
			this.readonly(conf.readonly);
			
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
				
				if ($core.hasClass("disabled")) {
					
					$text.trigger("blur");
					
					return;
					
				}
				
				_this._focus();
				
				$core.addClass("focused");
				
				if (!$text.val().trim().length) {
					
					_this._tip.text("");
					
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
					
					_this._tip.text(_this._prompt);
					
				}
				
				_this._blur();
				
			});
			
			/*
			 * 值改变
			 */
			$text.on("input propertychange", function() {
				
				_this._change($text.val().trim());
				
			});
			
		},
		
		/*
		 * 设置提示信息
		 */
		setPrompt: function(text) {
			
			this._prompt = text;
			
			if (!this.getValue().length) {
				
				this._tip.text(this._prompt);
				
			}
			
		},
		
		/*
		 * 设置宽度
		 */
		width: function(width) {
			
			var w = $.isNumeric(width) ?  (width - 2) : DefaultWidth;
			
			this._core.width(w);
			
			this._text.width(w - 10);
			
		},
		
		/*
		 * 设置高
		 */
		height: function(height) {
			
			var h = $.isNumeric(height) ?  (height - 2) : DefaultHeight;
			
			this._core.height(h);
			
			this._text.height(h - 10);
			
		},
		
		/*
		 * 获取text
		 */
		getText: function() {
			
			return this._text.val().trim();
			
		},
		
		/*
		 * 获取值
		 */
		getValue: function() {
			
			return this._text.val().trim();
			
		},
		
		/*
		 * 设置值
		 */
		setValue: function(v) {
			
			var val = v ? v.trim() : "";
			
			this._tip.text(val.length ? "" : this._prompt);
			
			this._text.val(val);
			
		}

	});
	
	/*
	 * 组件解析器
	 */
	UI.TextArea.parser = function($elem) {
		
		var config = {},
			name = $elem.attr("name"),
			value = $elem.attr("value") || $elem[$elem.is("textarea") ? "val" : "text"]();

		if (name) config.name = name;
		
		if (value) config.value = value;
		
		return config;
	
	};
	
	/*
	 * 注册文本域组件
	 */
	UI._register({
		cls: ClassName,
		namespace: "TextArea"
	});
	
	return UI.TextArea;
	
});
