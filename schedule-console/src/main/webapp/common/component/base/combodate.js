/*
 * 时间框
 */
define(["combo", "calendar", "number"], function() {
	
	var ClassName = "pageui-combodate",
        days = ["Su", "Mo", "Tu", "We", "Th", "Fr", "Sa", "Su"],
        months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];

	/*
	 * 补零
	 */
	function fz(num) {
		
		return (+num < 10 ? "0" : "") + num;
		
	}
	
	/*
	 * 组件的命名空间
	 */
	UI.ComboDate = $.inherit(UI.Combo, {
		
		/*
		 * 默认配置项
		 */
		defConf: {
			format: 'yyyy-mm-dd'
		},
		
		/*
		 * 类名
		 */
		className: 'UI.ComboDate',
		
		/*
		 * 初始化方法,组件被实例化之后就会执行
		 */
		init: function() {
			
			var conf = this.conf;
			
			this.constructor.sup.init.apply(this);
			
			this._panelWidth = 280;
			
			this._Clock = conf.clock;
			
			this._ClearBtn = conf.clearBtn;
			
			this.dom.addClass(ClassName);
			
			this.setFormat(conf.format);
			
			this.setMin(conf.min);
			
			this.setMax(conf.max);
			
			this.setValue(conf.value);
			
		},
		
		/*
		 * 重写createPanel
		 */
		createPanel: function() {
			
			var time;
			
			if (this._Clock) {
				
				var value = this.getValue(),
					hour, minute, second;
				
				if (value.length) {
					
					var arr = value.split(" ").last().split(":");
					
					var hour = arr[0],
						minute = arr[1],
						second = arr[2];
					
				} else {
					
					var date = new Date();
					
					var hour = date.getHours(),
						minute = date.getMinutes(),
						second = date.getSeconds();
					
				}
				
				var date = new Date();
				
				time = [
					"<div class='pageui-combodate-time'>",
						"<input class='pageui-number' value='", fz(hour), "' data-config='cls: \"pageui-combodate-hour\", width: 26, height: 26, min: 0, max: 24, suffix: \"时\"' />",
						"<input class='pageui-number' value='", fz(minute), "' data-config='cls: \"pageui-combodate-minute\", width: 26, height: 26, min: 0, max: 60, suffix: \"分\"' />",
						"<input class='pageui-number' value='", fz(second), "' data-config='cls: \"pageui-combodate-second\", width: 26, height: 26, min: 0, max: 60, suffix: \"秒\"' />",
						this._ClearBtn ? "<a class='pageui-btn pageui-combodate-clear'>清除</a>" : "",
						"<a class='pageui-btn pageui-combodate-confirm'>确定</a>",
					"</div>"
				].join("");
				
			}
			
			return [
			    "<div class='pageui-calendar' data-config=\"val: '", this._value.val(), "',format: '", this._format, "',min: '", this._min, "',max: '", this._max, "'\"></div>",
			    time || [
			        "<div class='pageui-combodate-btns'>",
			        	"<a class='pageui-btn pageui-combodate-clear'>清除</a>",
			        "</div>"
			    ].join("")
			].join("");
			
		},
		
		/*
		 * 
		 */
		setFormat: function(format) {
			
			this.format = format;
			
			return this;
			
		},
		
		/*
		 * 设置值
		 */
		setValue: function(v) {
			
			if (v) {
			     
				if (this._max) {
					
					if (v.valueOf() > this._max.valueOf()) {
					     
						v = this._max;
						
					}
					
				}
				
				if (this._min) {
					
					if (v.valueOf() < this._min.valueOf()) {
					     
						v = this._min;
						
					}
					
				}
				
				v = formatDate(new Date(v.valueOf()), this.format);
				
				this._text.text(v).removeClass("gray");
				
				this._value.val(v);
				
			}
			
			return this;
			
		},
		
		/*
		 * 设置最大值
		 */
		setMax: function(max) {
			
			this._max = max;
			
			return this;
					
		},
		
		/*
		 * 设置最小值
		 */
		setMin: function(min) {
			
			this._min = min;
			
			return this;
			
		},
		
	});
	
	/*
	 * 日期格式转换
	 */
    var formatDate = UI.ComboDate.formatDate = function(date, format) {
		
		if (typeof format === 'string')
			format = UI.Calendar.parseFormat(format);
		
		var val = {
			d: date.getDate(),
			DD: days[date.getDay()],
			m: date.getUTCMonth() + 1,
			MM: months[date.getUTCMonth()],
			yy: date.getUTCFullYear().toString().substring(2),
			yyyy: date.getUTCFullYear()
		};
		
		val.dd = (val.d < 10 ? '0' : '') + val.d;
		
		val.mm = (val.m < 10 ? '0' : '') + val.m;
		
		var date = [],
			seps = $.extend([], format.separators);
		
		for (var i = 0, cnt = format.parts.length; i <= cnt; i++) {
			
			if (seps.length)
				date.push(seps.shift());
			
			date.push(val[format.parts[i]]);
			
		}
		
		return date.join('');
		
	};
	
	/*
	 * 选中一个日期
	 */
	UI.document.on("click", ".pageui-calendar-panel a", function() {
		
		var $this = $(this);
		
		if (UI._topCombo && UI._topCombo._panel.find($this).size() && !UI._topCombo._Clock) {
			
			var value = formatDate($this.closest(".pageui-calendar").getPageUI().value, UI._topCombo.format);
			
			UI._topCombo._text.text(value).removeClass("gray");
			
			UI._topCombo._value.val(value);
			
			UI.hideCombo();
			
		}
		
	});
	
	/*
	 * 确定按钮
	 */
	UI.document.on("click", "a.pageui-combodate-confirm", function() {
		
		var $this = $(this);
		
		if (UI._topCombo && UI._topCombo._panel.find($this).size()) {
			
			var time = [
			    fz($this.siblings(".pageui-combodate-hour").getPageUI().getValue()),
			    fz($this.siblings(".pageui-combodate-minute").getPageUI().getValue()),
			    fz($this.siblings(".pageui-combodate-second").getPageUI().getValue()),
			].join(":");
			
			var value = formatDate($this.parent().siblings(".pageui-calendar").getPageUI().value, UI._topCombo.format) + " " + time;
			
			UI._topCombo._text.text(value).removeClass("gray");
			
			UI._topCombo._value.val(value);
			
			UI.hideCombo();
			
		}
		
	});
	
	/*
	 * 清除按钮
	 */
	UI.document.on("click", "a.pageui-combodate-clear", function() {
		
		var $this = $(this);
		
		if (UI._topCombo && UI._topCombo._panel.find($this).size()) {
			
			UI._topCombo.clear();
			
		}
			
	});
	
	/*
	 * 组件解析器
	 */
	UI.ComboDate.parser = function($elem, dataConfig) {
		
		
		
	};
	
	/*
	 * 注册时间框组件
	 */
	UI._register({
		cls: ClassName,
		namespace: "ComboDate"
	});
	
	return UI.ComboDate;
	
});