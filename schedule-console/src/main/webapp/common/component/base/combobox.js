/*
 * 下拉框
 */
define(["combo"], function() {
	
	var ClassName = "pageui-combobox",
		TextField = "text",
		ValueField = "value";
	
	/*
	 * 组件的命名空间
	 */
	UI.ComboBox = $.inherit(UI.Combo, {
		
		/*
		 * 默认配置项
		 */
		defConf: {
			multipleLink: ",",		//多选的话,value用这个字符来链接
			textField: TextField,
			valueField: ValueField,
			value: null
		},
		
		/*
		 * 类名
		 */
		className: 'UI.ComboBox',
		
		/*
		 * 初始化方法,组件被实例化之后就会执行
		 */
		init: function() {
			
			var conf = this.conf;
			
			this.constructor.sup.init.apply(this);
			
			this.dom.addClass(ClassName);
			
			this.multipleLink(conf.multipleLink);
			
			if (conf.value !== null) {

				var vals = conf.value.toString().split(this._ML);
				
				this._value.val(this._multiple ? vals.join(this._ML) : vals[0]);
				
			}
			
			this.formatter(conf.formatter);

			this.textField(conf.textField);
			
			this.valueField(conf.valueField);
			
			this.searcher(conf.searcher);
			
			this.loadFilter(conf.loadFilter);
			
			this.getUrlParam(conf.getUrlParam);
			
			if (conf.url) {
				
				this.method(conf.method);
				
				this.loadDataByUrl(conf.url);
				
			} else {
				
				conf.data && this.loadData(conf.data);
				
			}
			
		},
		
		/*
		 * 初始化事件
		 */
		initEvent: function() {
			
			this.constructor.sup.initEvent.apply(this);
			
		},
		
		/*
		 * 多选value以这个值进行链接
		 */
		multipleLink: function(text) {
			
			this._ML = text || ",";
			
			return this;
			
		},
		
		/*
		 * 重置textField
		 */
		textField: function(text) {

			this._textField = text || TextField;
			
			return this;
			
		},
		
		/*
		 * 重置valueField
		 */
		valueField: function(text) {

			this._valueField = text || ValueField;
			
			return this;
			
		},
		
		/*
		 * 数据转换
		 */
		loadFilter: function(fn) {
			
			this._loadFilter = $.isFunction(fn) ? fn : function(data) {
				
				return data;
				
			};
			
			return this;
			
		},
		
		/*
		 * 是否允许联想搜索
		 */
		searcher: function(b) {
			
			this._searcher = Boolean(b);
			
			return this;
			
		},
		
		/*
		 * 重写createPanel方法
		 */
		createPanel: function() {
			
			return $([
		    	this._searcher ? [
		    	    "<div class='pageui-combobox-searcher'>",
		    	    	"<div>",
		    	    		"<input />",
		    	    	"</div>",
		    	    	"<a class='pageui-combobox-searcher-clear'></a>",
		    	    	"<a class='pageui-combobox-searcher-handler'></a>",
		    	    "</div>"
		    	].join("") : "",
			    "<div class='pageui-combobox-panel'>",
			    	"<div class='pageui-scrollbox'>",
			    		this._data ? this._getOptions("") : "",
			    	"</div>",
			    "</div>"
			].join(""));
			
		},
		
		/*
		 * 加载下拉数据
		 */
		loadData: function(data) {
			
			var _this = this,
				vf = this._valueField,
				d = this._loadFilter(data);
			
			this._dataMap = {};
			
			/*
			 * 用value作为键存放在_dataMap中方便查找
			 */
			this._data = ($.isArray(d) ? d : []).each(function(i, item) {
				
				_this._dataMap[item[vf]] = item;
				
			});
			
			this._dirtyCheck();
			
			return this;
			
		},
		
		/*
		 * 
		 */
		getUrlParam: function(fn) {
			
			this._getParam = $.isFunction(fn) ? fn : function() {
				
				return {};
				
			};
			
			return this;
			
		},
		
        method: function(type) {
			
			this._M = (type == "post" ? "POST" : "GET");
			
		},
		
		/*
		 * 通过url加载选项,不推荐使用,这个接口是给很lowB的开发人员直接用静态html生成组件的方式提供的
		 * 因为对应的需求,所以该请求中所带的其他参数,请使用get的方式追加在url之后
		 */
		loadDataByUrl: function(url) {
			
			var _this = this,
				_url = this._url = (url || this._url);
			
			$.ajax({
				url: _url,
				type: this._M,
				data: this._getParam(),
				success: function(data) {
					
					_this.loadData(data);
					
				}
			});
			
			return this;
			
		},
		
		/*
		 * 过滤下拉选项时执行的函数
		 */
		formatter: function(fn) {
			
			var _this = this;
			
			this._formatter = $.isFunction(fn) ? fn : function(option, keyWord) {
				
				return option[_this._textField].toLowerCase().indexOf(keyWord) >= 0;
				
			};
			
			return this;
			
		},
		
		/*
		 * 设置值
		 */
		setValue: function(val) {
			
			this._value.val(val);
			
			this._dirtyCheck();
			
			return this;
			
		},
		
		/*
		 * 获取下拉列表
		 */
		_getOptions: function(keyWord) {
			
			var arr = [],
				formatter = this._formatter,
				tf = this._textField,
				vf = this._valueField,
				val = [].concat(this.getValue());
			
			this._data.each(function(i, item) {
				
				if (formatter(item, keyWord))
					arr.push(["<li", (val.indexOf(item[vf].toString()) >= 0 ? " class='active'" : ""), " title='", item[tf], "'", " data-value='", item[vf], "'>", item[tf], "</li>"].join(""));
				
			});
			
			return "<ul class='pageui-combobox-list'>" + arr.join("") + "</ul>";
			
		},
		
		/*
		 * 脏检查
		 */
		_dirtyCheck: function() {
			
			if (!this._data) return;
			
			var _this = this,
				tf = this._textField,
				val = this.getValue(),
				textArr = [],
				valueArr = [];
			
			/*
			 * 检查_value中是否已经有值,有值的话,进行回显
			 */
			val.length && [].concat(val).each(function(i, item) {
				
				var bean = _this._dataMap[item];
				
				if (bean) {
					
					valueArr.push(item);
					
					textArr.push(["<b title='", bean[tf], "' data-value='", item, "'>", bean[tf], (_this._CB ? "<i class='pageui-combo-clear'></i>" : ""), "</b>"].join(""));
					
				}
				
			});
			
			if (textArr.length) {
				
				this._text.html(textArr.join("")).removeClass("gray");
				
				this._value.val(valueArr.join(this._ML));
				
			} else {
				
				this._text.text(this._prompt).addClass("gray");
				
				this._value.val("");
				
			}
			
		}
		
	});
	
	/*
	 * 组件解析器
	 */
	UI.ComboBox.parser = function($elem, dataConfig) {
		
		var config = {},
			name = $elem.attr("name"),
			value = $elem.attr("value");

		if (name) config.name = name;
		
		if (value) config.value = value;
		
		if (!dataConfig.data && $elem.is("select")) {
			
			var arr = [],
				tf = dataConfig.textField || TextField,
				vf = dataConfig.valueField || ValueField;
			
			$elem.find("option").each(function() {
				
				var $this = $(this),
					obj = {};
				
				obj[tf] = $this.text();

				obj[vf] = $this.attr("value");
				
				if ($this.attr("checked")) config.value = obj[vf];
				
				arr.push(obj);
				
			});
			
			config.data = arr;
			
		}
		
		return config;
		
	};
	
	/*
	 * 注册下拉框组件
	 */
	UI._register({
		cls: ClassName,
		namespace: "ComboBox"
	});
	
	/*
	 * 下拉框公共事件
	 */
	UI.document.on("click", "ul.pageui-combobox-list li", function() {
		
		var $this = $(this),
			flag = !UI._topCombo._multiple;
		
		if (flag) {

			$this.addClass("active").siblings().removeClass("active");
			
		} else {
			
			$this.toggleClass("active");
			
		}
		
		var $actives = $this.parent().find(".active");
		
		if ($actives.size()) {
			
			var textArr = [],
				valueArr = [],
				changeArr = [];
			
			$actives.each(function() {
				
				var $item = $(this),
					value = $item.attr("data-value");
				
				textArr.push(["<b title='", $item.text() , "' data-value='", value, "'>", $item.text(), (UI._topCombo._CB ? "<i class='pageui-combo-clear'></i>" : ""), "</b>"].join(""));
				
				valueArr.push(value);
				
				changeArr.push(UI._topCombo._dataMap[value]);
				
			});
			
			UI._topCombo._text.html(textArr.join("")).removeClass("gray");
			
			UI._topCombo._value.val(valueArr.join(UI._topCombo._ML));
			
			UI._topCombo._change(UI._topCombo._multiple ? changeArr : changeArr[0]);
			
			flag && UI.hideCombo();
			
		} else {
			
			UI._topCombo._text.text(UI._topCombo._prompt).addClass("gray");
			
			UI._topCombo._value.val("");
			
		}
		
	});
	
	/*
	 * 取消选项
	 */
	UI.document.on("click", ".pageui-combo .focused .pageui-combo-clear", function() {
		
		var $this = $(this).parent(),
			value = $this.attr("data-value"),
			valueArr = [].concat(UI._topCombo.getValue()).removeItem(value),
			changeArr = [];
		
		UI._topCombo._panel.find("li.active[data-value='" + value + "']").removeClass("active");
		
		$this.remove();
		
		UI._topCombo._value.val(valueArr.join(UI._topCombo._ML));
		
		valueArr.each(function(i, item) {
			
			changeArr.push(UI._topCombo._dataMap[item]);
			
		});
		
		UI._topCombo._change(UI._topCombo._multiple ? changeArr : changeArr[0]);
		
		if (!valueArr.length) {
			
			UI._topCombo._text.text(UI._topCombo._prompt).addClass("gray");
			
		}
		
	});
	
	/*
	 * 查询过滤下拉选项
	 */
	UI.document.on("click", ".pageui-combobox-searcher a", function() {
		
		var $this = $(this),
			$p = $(this).parent();
		
		if ($this.hasClass("pageui-combobox-searcher-clear")) {
			
			$p.find("input").val("");
			
			$this.hide();
			
		}
		
		UI._topCombo._panel.find(".pageui-scrollbox").data("_pageui").html(UI._topCombo._getOptions($p.find("input").val().toLowerCase().trim()));
		
	});
	
	/*
	 * 联想值改变
	 */
	UI.document.on("input propertychange", ".pageui-combobox-searcher input", function() {
		
		var $this = $(this);
		
		$this.parent().next()[$this.val().trim().length ? "show" : "hide"]();
		
	});
    
    /*
     * 回车查询
     */
    UI.document.on("keyup", ".pageui-combobox-searcher input", function(event) {
        
        if (event.keyCode == 13) {
            
            event.stopPropagation();
            
            $(this).parent().siblings(".pageui-combobox-searcher-handler").trigger("click");
            
        }
        
    });
	
	return UI.ComboBox;
	
});