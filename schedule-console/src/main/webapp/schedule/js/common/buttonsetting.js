/*
 * 按钮组
 * @date 2016-09-21
 */
define(["component"], function(cmp) {
	
	var ClassName = "pageui-button";
		
	/*
	 * 组件的命名空间
	 */
	UI.Button = $.inherit(UI.Component, {
		
		/*
		 * 默认配置项
		 */
		defConf: {
		
		},
		
		/*
		 * 类名
		 */
		className: 'UI.Button',
		
		/*
		 * 组件模板
		 */
		tpl: [
		     "<div id='{id}' class='", ClassName, " {cls}'>",
		          "<a class='pageui-button-setting'>",
		              "<i class='pageui-button-icon'></i>",
		          "</a>",
		     "</div>"
		].join(""),
		
		/*
		 * 初始化方法,组件被实例化之后就会执行
		 */
		init: function() {

			var conf = this.conf;
			
			var $core = this._core = this.dom.find("a.pageui-button-setting");
			
			this._data = conf.data;
			
			this._width = conf.width;
			
			this.onClick(conf.onClick);
			
			this._hidePanel = function() {};
			
			this.initEvent();
			
		},
		
		/*
		 * 初始化事件
		 */
		initEvent: function() {
			
		   var _this = this,
		       $core = this._core;
			
            $core.on("click", function() {
				
				_this._panel = $("<div class='pageui-button-panel Non-Global' style='width: " + (_this._width || ($core.outerWidth() + 30)) + "px'></div>").html(_this.createPanel());
				
				UI.showCombo(_this);
				
			});
           
            UI.document.on("click", "ul.pageui-button-list li", function() {
            	
             	var $this = $(this),
           	        url = $this.attr("url");
           	
             	_this._onClick($this);
       		
       	    });
           
		},
		
		createPanel: function() {
			
			 var arr = [];
				
			this._data.each(function(i, item) {
				
				arr.push(["<li url = '", item.url , "' value = '", item.value ,"' >", item.name, "</li>"].join(""));
				
			});
			
			return "<ul class='pageui-button-list'>" + arr.join("") + "</ul>";
			
		},
		
        onClick: function(fn) {
			
			this._onClick = $.isFunction(fn) ? fn : function() {};
			
			return this;
			
		}
		
	});
	
	/*
	 * 注册按钮组组件
	 */
	UI._register({
		cls: ClassName,
		namespace: "Button"
	});
	
	return UI.Button;
	
});
