/*
 * 本来上传控件是想使用webuploader的.但是因为有些地方的上传,需要form整体提交.单独写这个
 */
define(["component", "textinput"], function() {

	var ClassName = "pageui-file",
		DefaultWidth = 170,
		DefaultHeight = 30;
	
	/*
	 * 
	 */
	UI.File = $.inherit(UI.TextInput, {
		
		/*
		 * 默认配置项
		 */
		defConf: {},
	
		/*
		 * 类名
		 */
		className: 'UI.File',
		
		/*
		 * 组件模板
		 */
		tpl: [
		    "<div id='{id}' class='pageui-combo ", ClassName, " {cls}'>",
		    	"<b class='pageui-input-prefix'>{prefix}</b>",
		    	"<span class='pageui-input-core'>",
		    		"<p class='pageui-input-text'></p>",
		    		"<input class='pageui-input-value' type='file' name='{name}' />",
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
			
			this._text = $core.find("p");
			
			this._value = $core.find("input.pageui-input-value");
			
			this.isCombo = true;
			
			this.require(conf.require);
			
			this.setErrorPrompt(conf.errorPrompt);
			
			this.validator(conf.validator);
			
			this.setPrompt(conf.prompt);
			
			this.setIcons(conf.icons);
			
			this.onChange(conf.onChange);
			
			this.width(conf.width);
			
			this.height(conf.height);
			
			this.initEvent();
			
		},
		
		/*
		 * 初始化事件
		 */
		initEvent: function() {
			
			var _this = this;
			
			var $value = this._value.on("change", function() {
				
				_this._text.text($value.val().split("\\").last()).removeClass("gray");
				
				_this._change();
				
			});
			
		},
		
		/*
		 * 重写readonly方法
		 */
		readonly: function(b) {
			
			this._value.prop({disabled: Boolean(b)});
			
			this._core[b ? "addClass" : "removeClass"]("disabled");
			
			return this;
			
		}
		
	});
	
	return UI.File;
	
});