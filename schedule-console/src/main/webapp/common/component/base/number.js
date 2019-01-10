/*
 * 文本框
 * @date 2016-06-10
 * @auth AnNing
 */
define(["textinput"], function(cmp) {
	
	var ClassName = "pageui-number";
	
	/*
	 * 组件的命名空间
	 */
	UI.Number = $.inherit(UI.TextInput, {
		
		/*
		 * 默认配置项
		 */
		defConf: {
			max: Infinity,		//最大值无穷大
			min: -Infinity,		//最小值无穷小
			step: 1,			//上下调动的幅度
			precision: 0		//精度默认整数
		},
	
		/*
		 * 类名
		 */
		className: 'UI.Number',
		
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
			
			this.constructor.sup.init.apply(this);
			
			this.setMax(conf.max);
			
			this.setMin(conf.min);
			
			this.setPrecision(conf.precision);
			
			this.setValue(conf.value);
			
			this.setStep(conf.step);
			
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
				
				var v = $text.val().trim();
				
				if (v.length) {
					
					_this.setValue(v);
					
					if (_this._clearBtn) {
						
						setTimeout(function() {//这样写是防止清空按钮点不到
							
							_this._popClearBtn(false);
							
						}, 150);
						
					}
					
				} else {
					
					$text
					  .val(_this._prompt)
					  .addClass("gray");
					
				}
				
				_this._blur();
				
			});
			
			/*
			 * 值改变
			 */
			$text.on("input propertychange", function() {
				
				var v = $text.val().trim();
				
				if (v != "-" && isNaN(v)) {
					
					v = _this._NUM;
					
					$text.val(v);
					
				} else {
					
					_this._NUM = v;
					
				}
				
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
			 * 控制上下浮动
			 */
			$text.on("keyup", function(event) {
				
				var kc = event.keyCode;
				
				if (kc == 38 || kc == 40) {
					
					var val = +$text.val();
					
					if (!isNaN(val)) {
						
						var v = (kc == 38 ? val + _this._step : val - _this._step);

						$text.val(v);
						
						_this._value.val(v);
						
						_this._change(v);
						
					}
					
				}
				
				return false;
				
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
				
			});
			
		},
		
		/*
		 * 设置上下调动的幅度
		 */
		setStep: function(num) {
			
			var n = +num;
			
			this._step = (isNaN(n) || n <= 0) ? 1 : n;
			
		},
		
		/*
		 * 设值最大值
		 */
		setMax: function(num) {
			
			if (isNaN(num) || num == Infinity) {
				
				this._max = Infinity;
				
			} else {
				
				if (this._min > num) {
					
					this._max = this._min;
					
					this._min = num;
					
				} else {
					
					this._max = num;
					
				}
				
			}
			
			this.setValue(this._NUM);
			
		},
		
		/*
		 * 设值最大值
		 */
		setMin: function(num) {
			
			if (isNaN(num) || num == -Infinity) {
				
				this._min = -Infinity;
				
			} else {
				
				if (this._max < num) {
					
					this._min = this._max;
					
					this._max = num;
					
				} else {
					
					this._min = num;
					
				}
				
			}
			
			this.setValue(this._NUM);
			
		},
		
		/*
		 * 设值精度
		 */
		setPrecision: function(num) {
			
			var n = parseInt(num);
			
			this._P = (isNaN(n) || n < 0) ? 0 : n;
			
		},
		
		/*
		 * 设值方法,这里重写是因为只能输入数字
		 */
		setValue: function(num) {
			
			var flag = $.isNumeric(num),
			    val = (flag ? this._numFilter(+num) : this._prompt);
			
			this._text.val(val)[flag ? "removeClass" : "addClass"]("gray");
			
			this._NUM = $.isNumeric(val) ? val : "";
			
			this._value.val(this._NUM);
			
		},
		
		/*
		 * 校验数字用的=.=
		 */
		_numFilter: function(num) {
			
			if (num > this._max) {
				
				num = this._max;
				
			} else if (num < this._min) {
				
				num = this._min;
				
			}
			
			return num.toFixed(this._P);
			
		}
		
	});
	
	/*
	 * 组件解析器
	 */
	UI.Number.parser = function($elem) {
		
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
		namespace: "Number"
	});
	
	return UI.Number;
	
});