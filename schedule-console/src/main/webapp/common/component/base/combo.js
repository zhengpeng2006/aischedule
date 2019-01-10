/*
 * 下拉框基类
 * @date 2016-06-12
 * @auth AnNing
 */
define(["textinput", "scrollbox"], function(textinput) {
	
	var ClassName = "pageui-combo";
	
	UI.Combo = $.inherit(UI.TextInput, {
		
		/*
		 * 默认配置项
		 */
		defConf: {
			multiple: false
		},
		
		/*
		 * 类名
		 */
		className: 'UI.Combo',
		
		/*
		 * 组件模板
		 */
		tpl: [
		    "<div id='{id}' class='", ClassName, " {cls}'>",
		    	"<b class='pageui-input-prefix'>{prefix}</b>",
		    	"<span class='pageui-input-core Non-Global'>",
		    		"<p class='pageui-input-text'></p>",
		    		"<input class='pageui-input-value' name='{name}' />",
		    		//"<i class='pageui-textinput-icon pageui-combo-arrow'></i>",
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
			
			this._text = $core.find("p.pageui-input-text");
			
			this._value = $core.find("input.pageui-input-value");
			
			this._btn = $core.find("a");
			
			this.isCombo = true;
			
			this.require(conf.require);
			
			this.setErrorPrompt(conf.errorPrompt);
			
			this.validator(conf.validator);
			
			this.clearBtn(conf.clearBtn);
			
			this.setPrompt(conf.prompt);
			
			this.multiple(conf.multiple);
			
			this.onChange(conf.onChange);
			
			this.onShowPanel(conf.onShowPanel);
			
			this.onHidePanel(conf.onHidePanel);

			this.width(conf.width);
			
			this.height(conf.height);
			
			this.readonly(conf.readonly);
			
			this.initEvent();
			
		},
		
		/*
		 * 初始化事件
		 */
		initEvent: function() {
			
			var _this = this,
				$core = this._core;
			
			/*
			 * 展开下拉框
			 */
			$core.on("click", function() {
				
				_this._clearError();
				
				if ($core.hasClass("focused") || $core.hasClass("disabled")) return;
				
				_this._panel = $("<div class='pageui-combo-panel Non-Global' style='width: " + (_this._panelWidth || ($core.outerWidth() - 2)) + "px'></div>").html(_this.createPanel());
				
				UI.showCombo(_this);
				
				_this._showPanel();
				
			});
			
		},
		
		/*
		 * 展开面板
		 */
		showPanel: function() {
			
			this._core.trigger("click");
			
			return this;
			
		},
		
		/*
		 * 展开面板
		 */
		hidePanel: function() {
			
			UI.hideCombo();
			
			return this;
			
		},
		
		/*
		 * 展开下拉框时执行的回调函数
		 */
		onShowPanel: function(fn) {
			
			this._showPanel = $.isFunction(fn) ? fn : function() {};
			
			return this;
			
		},
		
		/*
		 * 隐藏下拉框时执行的回调函数
		 */
		onHidePanel: function(fn) {
			
			this._hidePanel = $.isFunction(fn) ? fn : function() {};
			
			return this;
			 
		},
		
		/*
		 * 创建下拉框中的内容
		 */
		createPanel: function() {
			
			return "";
			
		},
		
		/*
		 * 设置是否可以多选
		 */
		multiple: function(b) {
			
			this._multiple = Boolean(b);
			
			return this;
			
		},
		
		/*
		 * 清空按钮.
		 */
		clearBtn: function(b) {
			
			this._CB = Boolean(b);
			
			return this;
			
		},
		
		/*
		 * 获取text
		 */
		getText: function() {
			
			return this._text.text().trim();
			
		},
		
		/*
		 * 获取值
		 */
		getValue: function() {
			
			var val = this._value.val().trim();
			
			return this._multiple ? (val.length ? val.split(this._ML) : []) : val;
			
		}
		
	});
	
	return UI.Combo;
	
});