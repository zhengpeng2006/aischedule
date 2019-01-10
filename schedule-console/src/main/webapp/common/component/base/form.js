/*
 * 表单组件
 * @date 2016-7-1
 */
define(["component"], function(cmp) {
	
	var ClassName = "pageui-form",
		DefaultItemWidth = 200,
		DefaultTitleWidth = 80;
	
	var Align = {
		left: "left",
		right: "right"
	};
	
	/*
	 * form组件允许被创建的组件
	 */
	var FormItem = {
		textinput: {
			path: "textinput",
			namespace: "TextInput"
		},
		password: {
			path: "textinput",
			namespace: "TextInput"
		},
		number: {
			path: "number",
			namespace: "Number"
		},
		combobox: {
			path: "combobox",
			namespace: "ComboBox"
		},
		combodate: {
			path: "combodate",
			namespace: "ComboDate"
		},
		file: {
			path: "file",
			namespace: "File"
		},
		upload: {
			path: "upload",
			namespace: "Upload"
		},
		textarea: {
			path: "textarea",
			namespace: "TextArea"
		}
	};
	
	/*
	 * 组件的命名空间
	 */
	UI.Form = $.inherit(UI.Component, {
		
		/*
		 * 默认配置项
		 */
		defConf: {
			itemWidth: DefaultItemWidth,
			titleWidth: DefaultTitleWidth,
			titleAlign: "left"
		},
		
		/*
		 * 类名
		 */
		className: 'UI.Form',
		
		/*
		 * 组件模板
		 */
		tpl: [
		    "<div id='{id}' class='", ClassName, " {cls}'><form></form></div>"
		].join(""),
		
		/*
		 * 初始化方法,组件被实例化之后就会执行
		 */
		init: function() {
			
			var conf = this.conf;
			
			this._form = this.dom.find('form');
			
			this.itemWidth(conf.itemWidth);
			
			this.titleWidth(conf.titleWidth);
			
			this.titleAlign(conf.titleAlign);
			
			this.loadFilter(conf.loadFilter);
			
			this.loadData(conf.data);
			
			this.setColumns(conf.columns);
			
		},
		
		/*
		 * 设置公共宽度
		 */
		itemWidth: function(width) {
			
			var w = this._itemWidth = $.isNumeric(width) ?  (width - 2) : DefaultItemWidth;
			
			this.find("div.pageui-form-item>div").each(function() {
				
				$(this).data("_pageui").width(w);
				
			});
			
			return this;
			
		},
		
		/*
		 * 设置title宽度
		 */
		titleWidth: function(width) {
			
			this._titleWidth = $.isNumeric(width) ?  width : DefaultTitleWidth;
			
			this.find("b.pageui-input-prefix").css({width: this._titleWidth});
			
			return this;
			
		},
		
		/*
		 * 设置title宽度
		 */
		titleAlign: function(type) {
			
			this._titleAlign = Align[type] || "left";
			
			this.find("b.pageui-input-prefix").css({textAlign: this._titleAlign});
			
			return this;
			
		},
		
		/*
		 * 渲染元数据
		 */
		setColumns: function(ds) {
			
			this._form.empty();
			
			var _this = this,
			    dataSource = this._DS = ds || [],
			    data = this._D,
			    IA = this._instanceArr = [],
			    IM = this._instanceMap = {};
			
			data && dataSource.each(function(i, item) {
				
				item.value = item.valueFormatter ? item.valueFormatter(data[item.name], data) : data[item.name];
				
			});
			
			dataSource.each(function(i, item) {
				
				if (item.type) {
					
					var obj = FormItem[item.type.toLocaleLowerCase()];
					
					if (obj) {
						
						var $div = $("<div class='pageui-form-item" + (item.require ? " pageui-require" : "") + "'></div>").appendTo(_this._form);
						
						item.width = item.width || _this._itemWidth;
					    
						require([obj.path], function() {
							  
							var $i = IM[item.name] = IA[IA.length] = (new UI[obj.namespace](item)).appendTo($div);
	
							$div.append(item.postscript);
							
							$i.dom.find("b.pageui-input-prefix").css({width: _this._titleWidth, textAlign: _this._titleAlign});
							
						});
						
					}
					  
				}
				
			});
			
			return this;
				
		},
		
		/*
		 * 数据转换
		 */
		loadFilter: function(fn) {
			
			this._LF = $.isFunction(fn) ? fn : function(data) {
				
				return data;
				
			};
			
			return this;
			
		},
		
		/*
		 * 获取form数据
		 */
		getData: function() {
			
			if (this.validate()) return;
			
			var _this = this,
			    data = {};
			
			_this._DS.each(function(i, item) {
				
				var name = item.name;
				
				if (name) {
					
					var value = _this._instanceMap[name].getValue();
					
					data[name] = (item.formatter ? item.formatter(value) : value);

				}
				
			});
			
			return data;
			
		},
		
		/*
		 *  刷新form数据
		 */
		loadData: function(data) {
			
			var _this = this,
			    data = this._D = this._LF(data),
			    dataSource = this._DS;
			
			dataSource && dataSource.each(function(i, item) {
				
				var name = item.name,
					value = item.valueFormatter ? item.valueFormatter(data[name], data) : data[name];
				
				value && _this._instanceMap[name].setValue(value);
				
			});
			
			return this;
				
		},
		
		/*
		 * 获取指定单元的数据值
		 */
		getValue: function(name) {
			
			return this._instanceMap[name] && this._instanceMap[name].getValue();
			
		},
		
		/*
		 * 设置指定单元的数据值
		 */
		setValue: function(name,value) {
			
			this._instanceMap[name] && this._instanceMap[name].setValue(value);
			
			return this;
			
		},
		
		/*
		 * 获取指定单元的显示值
		 */
		getText: function(name) {
			
			return this._instanceMap[name] && this._instanceMap[name].getText();
			
		},
		
		/*
		 * 设置指定单元的显示值
		 */
		setText: function(name, value) {
			
			this._instanceMap[name] && this._instanceMap[name].setText(value);
			
			return this;
			
		},
		
		/*
		 * 获取form中的组件实例
		 */
		getItem: function(name) {
			
			return this._instanceMap[name];
			
		},
		
		/*
		 * 清空字段
		 * name 设置的值表示清空指定字段值，不设置表示清空全部字段值
		 */
		clear: function(name) {
			
			var _this = this;
			
			if (name) {
				
				if ($.isArray(name)) {
					
					name.each(function(i,item) {
						
						_this._instanceMap[item] && _this._instanceMap[item].clear();
						
					});
					
					
				} else {
					
					_this._instanceMap[name] && _this._instanceMap[name].clear();

				}
				
			} else {
				
				_this._DS.each(function(i,item) {
					
					_this._instanceMap[item.name] && _this._instanceMap[item.name].clear();
					
				});
				
			}
			
			return this;
			
		},
		
		/*
		 * 禁用指定字段
		 */
		disable: function(name, flag) {
			
			var _this = this;

			if (name) {
				
               if ($.isArray(name)) {
					
					name.each(function(i,item) {
						
						_this._instanceMap[item] && _this._instanceMap[item].readonly(flag);

					});
					
				} else {
					
					_this._instanceMap[name] && _this._instanceMap[name].readonly(flag);
					
				}
				
			} else {
				
	            _this._DS.each(function(i,item) {
					
					_this._instanceMap[item.name] && _this._instanceMap[item.name].readonly(flag);

				});
				
			}
			
			return this;
			
		},
		
		/*
		 * 校验
		 */
		validate: function() {
			
			var flag;
			
			this._instanceArr.each(function(i, item) {
				
				if (item.validate && item.validate()) flag = true;
				
			});
			
			return flag;
			
		},
		
		/*
		 * form方式提交
		 */
		ajaxSubmit: function(option) {
			
			var _this = this;
			
	        _this._form.attr({method: 'post', enctype: 'multipart/form-data'});
	        
			require(["ajaxform"], function() {
				  
				_this._form.ajaxSubmit(option);
				
			});
			
		}
		
	});
	
	/*
	 * 组件解析器
	 */
	UI.Form.parser = function($elem, dataConfig) {
		
		var conf = {};
		
        if (!dataConfig.columns) {
			
			var arr = [];
			
			$elem.find(">span").each(function() {
				
				var _this = $(this),
					obj = {},
				    config = _this.attr("data-config") ? eval("({" + ($this.attr("data-config") || "") + "})") : "";

                if (config) {
                	
    				arr.push(obj);
    				
                }
                
			});
			
			conf.columns = arr;
			
		}
		
		return conf;
	
	};
	
	/*
	 * 注册表单组件
	 */
	UI._register({
		cls: ClassName,
		namespace: "Form"
	});
	
	return UI.Form;
	
});