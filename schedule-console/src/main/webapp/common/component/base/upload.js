/*
 * 上传控件
 */
define(["combo", "webuploader"], function() {
	
	var ClassName = "pageui-upload",
		DefaultHeight = 30;
	
	var fileTpl = [
		"<li data-id='{id}'>",
			"<i class='icon-waiting'></i>",
			"<p title='{name}'>{name}</p>",
			"<span class='pageui-upload-icons'></span>",
		"</li>"
	].join("");
	
	/*
	 * 组件的命名空间
	 */
	UI.Upload = $.inherit(UI.Combo, {
		
		/*
		 * 默认配置项
		 */
		defConf: {},
		
		/*
		 * 类名
		 */
		className: 'UI.Upload',
		
		/*
		 * 组件模板
		 */
		tpl: [
		    "<div id='{id}' class='", ClassName, " {cls}'>",
		    	"<b class='pageui-input-prefix'>{prefix}</b>",
		    	"<span class='pageui-input-core Non-Global'>",
		    		"<p class='pageui-input-text'></p>",
		    		"<i class='pageui-textinput-icon pageui-combo-arrow'></i>",
		    		"<ul class='pageui-upload-list'></ul>",
		    	"</span>",
		    	"<a class='pageui-btn pageui-upload-btn'>上传</a>",
		    	"<b class='pageui-input-suffix'>{suffix}</b>",
		    "</div>"
		].join(""),
		
		/*
		 * 初始化方法,组件被实例化之后就会执行
		 */
		init: function() {

			var conf = this.conf;
			
			var $core = this._core = this.dom.find("span.pageui-input-core");
			
			this._text = $core.find("p");
			
			this._icon = $core.find("i");
			
			this._btn = this.dom.find("a");
			
			this.require(conf.require);
			
			this.setErrorPrompt(conf.errorPrompt);
			
			this.validator(conf.validator);
			
			this.setPrompt(conf.prompt);
			
	        this.width(conf.width);
			
			this.height(conf.height);
			
			this.multiple(conf.multiple);
			
			this._name = conf.name;
			
			this.setServer(conf.server, true);
			
			this.setParam(conf.param, true);
			
			this.setFormat(conf.format, true);
			
			this.successFn(conf.successFn);
			
			this.errorFn(conf.errorFn);
			
			this.beforeSubmit(conf.beforeSubmit);
			
			this.uploadAccept(conf.uploadAccept);
			
			this.initUploader();
			
			this.initEvent();
			
		},
		
		/*
		 * 初始化事件
		 */
		initEvent: function() {
			
			var _this = this;
			
			/*
			 * 上传
			 */
			this._btn.on('click', function() {
				
				if (_this._list.children().size()) {
					
					_this._BS();
					
					_this.submit();
					
				}
			    
			});
			
			this._core.on('click', function() {
				
				_this._clearError();
				
			});
			
		},
		
		/*
		 * 
		 */
		submit: function() {
			
			this._uploader.retry();
			
			return this;
			
		},
		
		/*
		 * 初始化upload实例
		 */
		initUploader: function() {
			
			var _this = this;
			
			var uploader = this._uploader = require("webuploader").create({
				 pick: {
		             id: this._core,
		             multiple: this._multiple,
		             name: this._name
		         },
		         accept: {
		             extensions: this._format,
		         },
	             server: this._server,
	             formData: this._param
			});
			
			var $list = this._list = this._core.find("ul");
			
			if (!this._multiple) {
				
				uploader.on("beforeFileQueued", function(){
					
			    	var queueFile = uploader.getFiles('inited');
			    	
			    	if (queueFile.length) {
			    		
			    		uploader.removeFile(queueFile[0], true); 
			    		
			    	}
					
				});
				
			}
			
			/*
			 * 文件加入队列
			 */
			uploader.on("fileQueued", function(file) {
				
				_this._multiple ? $list.append(fileTpl.format(file)) : $list.html(fileTpl.format(file));
				
				_this._reHeight();
				
			});
			
			/*
			 * 文件上传过程中创建进度条实时显示
			 */
			uploader.on("uploadProgress", function(file, percentage) {
			   
			    $list.find("li[data-id='" + file.id + "']").find(">i").attr({"class": "icon-loading"});
		     	 
		    });
		   
		    /*
		     * 成功
		     */
			uploader.on("uploadSuccess", function(file) {
			   
			    $list.find("li[data-id='" + file.id + "']").find(">i").attr({"class": "icon-success"});
			    
			    _this._SF();
			   
		    });
			
			/*
			 * 判断后台返回是否正确
			 */
			uploader.on('uploadAccept', function(block,ret){

				if (_this._UA(ret) == false) {
					
					return false;
					
				};
				
		    });

		    /*
		     * 失败
		     */
			uploader.on("uploadError", function(file) {
				
			    var $obj = $list.find("li[data-id='" + file.id + "']");
				   
			    $obj.find(">i").attr({"class": "icon-warning"});
			    
			    $obj.find("span").html([
					"<i class='icon-reload'></i>",              
					"<i class='icon-remove'></i>"
			    ].join(""));
			    
			    $obj.on("click", "i", function() {
			    	
			    	var $this = $(this);
			    	
			    	/*
			    	 * 删除
			    	 */
			    	if ($this.hasClass("icon-remove")) {
			    		
			    		uploader.removeFile(file, true);
			    		
			    		$obj.remove();
						
						_this._reHeight();
			    		
			    	}
			    	
			    	/*
			    	 * 重新上传
			    	 */
			    	if ($this.hasClass("icon-reload")) {
			    		
			    		uploader.retry(file);
			    		
			    	}
			    	
			    });
			    
			    _this._EF();
			    
		    });
			
		},
		
		/*
		 * 设置输入框高度
		 */
		height: function(height) {
			
			var h = $.isNumeric(height) ? height : DefaultHeight,
				p = (h - 18) / 2;
			
			this.dom.css({lineHeight: h + "px"});
			
			this._btn.css({height: h, lineHeight: h + "px"});
			
			this._core.height(h - 2);
			
			this._text.css({paddingTop: p, paddingBottom: p});

			return this;
			
		},
		
		/*
		 * 撑开高度
		 */
		_reHeight: function() {
			
			this.dom.css({height: this._core.outerHeight() + this._list.outerHeight()});
			
		},
		
		/*
		 * 设置提示信息
		 */
		setPrompt: function(text) {
			
			this._prompt = text || "";
			
			this._text
			  .text(this._prompt)
			  .addClass("gray");
				
			return this;
			
		},
		
		/*
		 * 设置服务请求地址
		 */
		setServer: function(server, flag) {
			
            this._server = server;
			
            !flag && (this._uploader.options.server = this._server);
            
			return this;
			
		},
		
		/*
		 * 设置服务参数
		 */
		setParam: function(param, flag) {
			
			this._param = param;
			
			!flag && (this._uploader.options.formData = this._param);
				
			return this;
			
		},
		
		/*
		 * 接受哪些类型的文件,比如'gif,jpg,jpeg,bmp,png'
		 */
		setFormat: function(format, flag) {
			
			this._format = format;
			
			!flag && (this._uploader.options.accept.extensions = this._format);
			
			return this;
			
		},
		
		beforeSubmit: function(fn) {
			
            this._BS = $.isFunction(fn) ? fn : function() {};
			
			return this;
			
		},
		
		/*
		 * 提交成功后的回调函数
		 */
		successFn: function(fn) {
			
			this._SF = $.isFunction(fn) ? fn : function() {};
			
			return this;
			
		},
		
		/*
		 * 提交失败后的回调函数
		 */
		errorFn: function(fn) {
			
			this._EF = $.isFunction(fn) ? fn : function() {};
			
			return this;
			
		},
		
		/*
		 * 当某个文件上传到服务端响应后，会派送此事件来询问服务端响应是否有效
		 */
		uploadAccept: function (fn) {
			
            this._UA = $.isFunction(fn) ? fn : function() {};
			
			return this;
			
		},
		
		/*
		 * 获取值
		 */
		getValue: function() {
			
			return this._list.find("li");
			
		}
		
	});
	
	/*
	 * 组件解析器
	 */
	UI.Upload.parser = function($elem, dataConfig) {
		
		
		
	};
	
	/*
	 * 注册上传控件
	 */
	UI._register({
		cls: ClassName,
		namespace: "Upload"
	});
	
	return UI.Upload;
	
});