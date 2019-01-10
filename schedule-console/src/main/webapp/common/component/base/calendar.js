/*
 * 日历
 * @date 2016-06-28
 */
define(["component"], function(cmp) {
	
	var ClassName = "pageui-calendar";
	
	var defaultFormat = "yyyy-mm-dd",
		days = ["Su", "Mo", "Tu", "We", "Th", "Fr", "Sa", "Su"],
        months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
        viewMode = 0; // 0：表示日历面板；1：表示月份面板；2：表示年份面板，用于处理 < 和 > 事件

	/*
	 * 组件的命名空间
	 */
	UI.Calendar = $.inherit(UI.Component, {
		
		/*
		 * 默认配置项
		 */
		defConf: {
			format: defaultFormat
		},
		
		/*
		 * 类名
		 */
		className: "UI.Calendar",
		
		/*
		 * 组件模板
		 */
		tpl: [
		    "<div id='{id}' class='", ClassName, " {cls}'></div>"
		].join(""),
		
		/*
		 * 初始化方法,组件被实例化之后就会执行
		 */
		init: function() {
			
			var conf = this.conf;
			
			this.value = conf.value;
			
			this._viewMode = 0; // {0: 日历面板, 1: 月份面板, 2: 年份面板}
			
			this.setFormat(conf.format);
			
			this.setMax(conf.max);
			
			this.setMin(conf.min);
						
			this.dom.html(this.createPanel());
			
			this.initEvent();
			
		},
		
		/*
		 * 初始化事件
		 */
		initEvent: function() {
			
			var _this = this,
				$dom = this.dom;
			
			$dom
				/*
				 * 头部'<' || '>'
				 */
				.on("click", ".pageui-calendar-header a", function() {
				
					var $this = $(this);
					
					if ($this.hasClass("disabled")) return;
					
					var dir = $this.hasClass("prev") ? -1 : 1;
					
					switch (_this._viewMode) {
					
						case 0:
							
							_this.value = moveMonth(_this.value, dir);
							
							$dom.html(_this.createPanel());
							
							break;
					
						case 1:
							
							var $yyyy = $dom.find("h1 span.year"),
								yyyy = +$yyyy.text() + dir;
							
							$dom.find("div.pageui-calendar-constant")
								.siblings("div.pageui-calendar-panel")
									.remove();
							
							$yyyy.text(yyyy);
							
							$dom.append(_this._createMonthPanel(yyyy));
							
							break;
					
						case 2:
							
							var $yyyy = $dom.find("h1 span.year"),
								yyyy = +$yyyy.text() + dir;
							
							$dom.find("div.pageui-calendar-constant")
								.siblings("div.pageui-calendar-panel")
									.remove();
							
							$yyyy.text(yyyy);
							
							$dom.append(_this._createYearPanel());
							
							break;
					
					}
					
				})
				/*
				 * 切换月份或者年份面板
				 */
				.on("click", ".pageui-calendar-header span", function() {
					
					var $this = $(this);
					
					$this.addClass('active').siblings().removeClass('active');
					
					$dom.find("div.pageui-calendar-constant")
						.hide()
					.siblings("div.pageui-calendar-panel")
						.remove();
					
					if ($this.hasClass('month')) {
						
						$dom.append(_this._createMonthPanel($dom.find("h1 span.year").text(), _this.value.getMonth()));
						
						_this._viewMode = 1;
						
					} else {
						
						$dom.append(_this._createYearPanel());
						
						_this._viewMode = 2;
						
					}
					
				})
				/*
				 * 选中年份或者月份
				 */
				.on("click", ".pageui-calendar-panel dd", function() {
					
					var $this = $(this),
						date = new Date(_this.value);
					
					switch (_this._viewMode) {
					
						case 1:
							
							date.setMonth($this.index());
							
							date.setUTCFullYear($dom.find("h1 span.year").text());
							
							break;
							
						case 2:
							
							date.setUTCFullYear($this.text());
							
							break;
					
					}
					
					if (date.getDate() < _this.value.getDate()) {
						
						_this.value = new Date(date.getUTCFullYear(), date.getMonth(), 0);
						
					} else {
						
						_this.value = date;
						
					}
					
					$dom.html(_this.createPanel());
					
					_this._viewMode = 0;
					
				})
				/*
				 * 选中一个日期
				 */
				.on("click", ".pageui-calendar-panel a", function() {
					
					var $this = $(this);
					
					$this.addClass("active").siblings("a.active").removeClass("active");
					
					_this.value.setDate($this.text());
					
				});
			
			
		},
		
		/*
		 * 设置最大值
		 */
		setMax: function(max) {
			
			if (!max) return;
			
			var maxDay = this._maxDay = (max == "current") ? new Date() : parseDate(max, this.format);
			
			if (this._minDay && (maxDay.valueOf() < this._minDay.valueOf())) {
				
				this._maxDay = this._minDay;
				
				this.setMin(maxDay);
				
			}
			
			this._maxY = this._maxDay.getUTCFullYear();
			
			this._maxM = +this._maxDay.getMonth() + 1;
			
			this._maxD = this._maxDay.getDate();
			
			return this;
			
		},
		
		/*
		 * 设置最小值
		 */
		setMin: function(min) {
			
			if (!min) return;
			
			var minDay = this._minDay = (min == "current") ? currentDay : parseDate(min, this.format);
			
			if (this._maxDay && (minDay.valueOf() > this._maxDay.valueOf())) {
				
				this._minDay = this._maxDay;
				
				this.setMax(minDay);
				
			}
			
			this._minY = this._minDay.getUTCFullYear();
			
			this._minM = +this._minDay.getMonth() + 1;
			
			this._minD = this._minDay.getDate();
			
			return this;
			
		},
		
		/*
		 * 设置format
		 */
		setFormat: function(format) {
			
			this.format = format || defaultFormat;
			
			return this;
			
		},
		
		/*
		 * 创建日期面板
		 * 这里声明一下: 如果手贱,将max的值设置为小于min的值,会在函数内部,将两者交换
		 */
		createPanel: function() {
			
			var format = this.format,
				maxDay = this._maxDay,
				minDay = this._minDay,
				val = this.value,
				date = new Date();
			
			/*
			 * 需要回显的值
			 */
			if (val) {
				
				date = parseDate(val, format);
				
				if (maxDay && (date.valueOf() > maxDay.valueOf())) {
					
					date = maxDay;
					
				} else if (minDay && (date.valueOf() < minDay.valueOf())) {
					
					date = minDay;
					
				}
				
			} 
			
			this.value = date;
			
			var maxY = this._maxY,	//最大年份
				maxM = this._maxM,	//最大月份
				maxD = this._maxD,	//最大日期
				minY = this._minY,	//最小年份
				minM = this._minM,	//最小年份
				minD = this._minD;	//最小日期
			
			var yyyy = date.getUTCFullYear(),
				mm = +date.getMonth() + 1,
				dd = date.getDate();
			
			/*
			 * 开始构建文档流
			 */
			var dom = [],
				yMax = false,
				yMin = false,
				flagMax = false,
				flagMin = false;
			
			if (maxY && yyyy >= maxY) {
				
				yyyy = maxY;
				
				yMax = true;
				
				if (mm >= maxM) {
					
					mm = maxM;
					
					flagMax = true;
					
				}
				
			}
			
			if (minY && yyyy <= minY) {
				
				yyyy = minY;
				
				yMin = true;
				
				if (mm <= minM) {
					
					mm = minM;
					
					flagMin = true;
					
				}
				
			}
			
			dom.push([
			    "<h1 class='pageui-calendar-header'>",
					"<a class='prev", (flagMin ? " disabled" : ""), "'></a>",
					"<a class='next", (flagMax ? " disabled" : ""), "'></a>",
					"<div>",
						"<span class='month'>", months[mm - 1], "</span>",
						"<span class='year'>", yyyy, "</span>",
					"</div>",
	            "</h1>"
		    ].join(""));
			
			/*
			 * 输出日期
			 */
			var dateArr = [],
				lastMonthlastDay = new Date(yyyy, +mm - 1, 0),
				lastDay = +lastMonthlastDay.getDay(),
				lastDate = +lastMonthlastDay.getDate(),
				thisMonthlastDay = new Date(yyyy, mm, 0),
				thisDay = +thisMonthlastDay.getDay(),
				thisDate = +thisMonthlastDay.getDate();
			
			if (lastDay < 6) {
				
				for (var i = lastDay + 1; i--;) {					//先输出上个月最后几天
					
					dateArr.push("<b>" + (lastDate - i) + "</b>");
					
				}
				
			}
			
			if (flagMin) {											//如果当前构造的面板是最小值限定的面板
				
				var d = dd < minD ? minD : dd;
				
				for (var i = 1; i < minD; i++) {					//最小天以前的日期全都不可用
					
					dateArr.push("<b>" + i + "</b>");
					
				}
				
				for (; i < d; i++) {								//前面做过max与min的验证.在这里先将回显日期之前的日期输出
					
					dateArr.push("<a>" + i + "</a>");
					
				}
				
				/*
				 * 输出回显的日期;实际上这是dd等于i
				 */
				dateArr.push(["<a", ( dd > minD ? " class='active'" : ""), ">", d, "</a>"].join(""));
				
				if (flagMax) {										//如果最大值也在这个面板上
					
					for (; i < maxD;) {								//可选日期输出到最大天数
						
						dateArr.push("<a>" + (++i) + "</a>");
						
					}
					
					for (; i < thisDate;) {							//不可选
						
						dateArr.push("<b>" + (++i) + "</b>");
						
					}
					
				} else {
					
					for (; i < thisDate;) {
						
						dateArr.push("<a>" + (++i) + "</a>");
						
					}
					
				}
				
			} else {												//不是最小值的面板
				
				var d = flagMax && dd > maxD ? maxD : dd;
				
				for (var i = 1; i < d; i++) {
					
					dateArr.push("<a>" + i + "</a>");
					
				}
				
				dateArr.push("<a class='active'>" + d + "</a>");
				
				if (flagMax) {										//如果最大值也在这个面板上
					
					for (; i < maxD;) {								//可选日期输出到最大天数
						
						dateArr.push("<a>" + (++i) + "</a>");
						
					}
					
					for (; i < thisDate;) {							//不可选
						
						dateArr.push("<b>" + (++i) + "</b>");
						
					}
					
				} else {
					
					for (; i < thisDate;) {
						
						dateArr.push("<a>" + (++i) + "</a>");
						
					}
					
				}
				
			}
			
			for (var i = 1, l = 7 -thisDay; i < l; i++) {			//打印最后几天
				
				dateArr.push("<b>" + i + "</b>");
				
			}
			
			var arr = [],
			    i = 0;
			
		    while (i < 7) {
		    	
		    	arr.push([
                     "<span>", days[i++], "</span>",
                ].join(""));
		    	
            }
		    
			dom.push([
			    "<div class='pageui-calendar-panel pageui-calendar-constant'>",
				    "<p>", arr.join("") , "</p>",
				    "<div>" , dateArr.join("") , "</div>",
			    "</div>"
			].join(""));

			return dom.join("");
			
		},
		
		/*
		 * 创建月份面板
		 */
		_createMonthPanel: function(yyyy, mm) {
			
			var $a = this.dom.find("h1 a").removeClass("diasbled"),
				flagMax = false,
				flagMin = false,
				i = 0,
			    dom = [];
			
			$a.removeClass("disabled");
			
			if (this._maxY && yyyy >= this._maxY) {
				
				flagMax = true;
				
				this.dom.find("h1 a").last().addClass("disabled");
				
			}
			
			if (this._minY && yyyy <= this._minY) {
					
				flagMin = true;
				
				this.dom.find("h1 a").first().addClass("disabled");
				
			}
			
			var p1Tag = flagMin ? "dt" : "dd",
				p2Len = flagMax ? this._maxM : 12;
			
			for (; i < this._minM; i++) {
				
				dom.push(["<", p1Tag, ">", months[i], "</", p1Tag, ">"].join(""));
				
			}
			
			for (; i < p2Len; i++) {
				
				dom.push(["<dd", (mm === i ? " class='active'" : ""), ">", months[i], "</dd>"].join(""));
				
			}
			
			for (; i < 12; i++) {
				
				dom.push(["<dt>", months[i], "</dt>"].join(""));
				
			}
            
			return [
			    "<div class='pageui-calendar-panel'>",
			    	"<dl>", dom.join(""), "</dl>",
			    "</div>"
			].join("");
			
		},
		
		/*
		 * 创建年面板
		 */
		_createYearPanel: function() {
			
			var $a = this.dom.find("h1 a").removeClass("disabled"),
				yyyy = +this.dom.find("h1 span.year").text(),
				maxY = this._maxY,
				minY = this._minY,
				dom = ["<dd class='active'>" + yyyy + "</dd>"];
			
			var i = 0,
				year;
			
			while (i < 7) {
				
				var year = yyyy - (++i);
				
				dom.unshift(year < minY ? "<dt>" + year + "</dt>" : "<dd>" + year + "</dd>");
				
			}
			
			i = 0;
			
			while (i < 7) {
				
				var year = yyyy + (++i);
				
				dom.push(year > maxY ? "<dt>" + year + "</dt>" : "<dd>" + year + "</dd>");
				
				
			}
			
			if (yyyy == minY) $a.first().addClass("disabled");
			
			if (maxY == yyyy) $a.last().addClass("disabled");
            
			return [
			    "<div class='pageui-calendar-panel'>",
			    	"<dl>", dom.join(""), "</dl>",
			    "</div>"
			].join("");
		
		}
		
	});
	
	/*
	 * 
	 */
	var UTCDate = UI.Calendar.UTCDate = function() {
		
		return new Date(Date.UTC.apply(Date, arguments));
		
	};
	
	/*
	 * 
	 */
	var moveMonth = UI.Calendar.moveMonth = function(date, dir) {
		
		if (!dir) return date;
		
		var new_date = new Date(date.getUTCFullYear(), date.getMonth() + dir, date.getDate());
		
		if (new_date.getDate() < date.getDate()) {
			
			new_date = new Date(new_date.getUTCFullYear(), new_date.getMonth(), 0);
			
		}
		
		return new_date;
		
	};
	
	/*
	 * 切换年
	 */
	var moveYear = UI.Calendar.moveYear = function(date, dir) {
		
		return moveMonth(date, dir * 12);
		
	};
	
	/*
	 * 
	 */
	var parseFormat = UI.Calendar.parseFormat = function(format) {
    	
    	var validParts = /dd?|DD?|mm?|MM?|yy(?:yy)?/g,
            separators = format.replace(validParts, '\0').split('\0'),
			parts = format.match(validParts);
    	
		return (separators && separators.length && parts && parts.length) ? {separators: separators, parts: parts} : {};
		
	};
	
	/*
	 * 
	 */
	var parseDate = UI.Calendar.parseDate = function(date, format) {
	    
		if (date instanceof Date) return date;
    	
	    var nonpunctuation = /[^ -\/:-@\[\u3400-\u9fff-`{-~\t\n\r]+/g;
		
		if (typeof format === 'string') {
			
			format = parseFormat(format);
			
		}
		
		if (/^[\-+]\d+[dmwy]([\s,]+[\-+]\d+[dmwy])*$/.test(date)) {
			
			var part_re = /([\-+]\d+)([dmwy])/,
				parts = date.match(/([\-+]\d+)([dmwy])/g),
				part, dir;
			
			date = new Date();
			
			for (var i=0; i<parts.length; i++) {
				
				part = part_re.exec(parts[i]);
				
				dir = parseInt(part[1]);
				
				switch (part[2]) {
				
					case 'd':
						
						date.setUTCDate(date.getUTCDate() + dir);
						
						break;
						
					case 'm':
						
						date = moveMonth(date, dir);
						
						break;
						
					case 'w':
						
						date.setUTCDate(date.getUTCDate() + dir * 7);
						
						break;
						
					case 'y':
						
						date = moveYear.call(date, dir);
						
						break;
						
				}
				
			}
			
			return UTCDate(date.getUTCFullYear(), date.getMonth(), date.getUTCDate(), 0, 0, 0);
			
		}
		
		var parts = date && date.toString().match(nonpunctuation) || [],
			date = new Date(),
			parsed = {},
			val, 
			filtered, 
			part,
			setters_order = ['yyyy', 'yy', 'M', 'MM', 'm', 'mm', 'd', 'dd'],
			setters_map = {
				yyyy: function(d,v) {
					return d.setUTCFullYear(v);
				},
				yy: function(d,v) {
					return d.setUTCFullYear(2000+v);
				},
				m: function(d,v) {
					if (isNaN(d))
						return d;
					v -= 1;
					while (v<0) v += 12;
					v %= 12;
					d.setUTCMonth(v);
					while (d.getMonth() != v)
						d.setUTCDate(d.getUTCDate() - 1);
					return d;
				},
				d: function(d,v) {
					return d.setUTCDate(v);
				}
			};
		
		setters_map['M'] = setters_map['MM'] = setters_map['mm'] = setters_map['m'];
		
		setters_map['dd'] = setters_map['d'];
		
		date = UTCDate(date.getFullYear(), date.getMonth(), date.getDate(), 0, 0, 0);
		
		var fparts = format.parts.slice();
		
		if (parts.length != fparts.length) {
			
			fparts = $(fparts).filter(function(i,p) {
				
				return $.inArray(p, setters_order) !== -1;
				
			}).toArray();
			
		}

		if (parts.length == fparts.length) {
			
			for (var i=0, cnt = fparts.length; i < cnt; i++) {
				
				val = parseInt(parts[i], 10);
				
				part = fparts[i];
				
				parsed[part] = val;
				
			}
			
			for (var i=0, _date, s; i<setters_order.length; i++) {
				
				s = setters_order[i];
				
				if (s in parsed && !isNaN(parsed[s])) {
					
					_date = new Date(date);
					
					setters_map[s](_date, parsed[s]);
					
					if (!isNaN(_date)) date = _date;
					
				}
				
			}
			
		}
		
		return date;
			
	};
	
	/*
	 * 注册日历 组件
	 */
	UI._register({
		cls: ClassName,
		namespace: "Calendar"
	});
	
	return UI.Calendar;
	
});
