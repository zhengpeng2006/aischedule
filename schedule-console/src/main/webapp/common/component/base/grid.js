/*
 * 表格
 * @date 2016-06-22
 * @auth AnNing
 */
define(["component", "pager"], function(cmp) {
	
	var ClassName = "pageui-grid";
	
	var Align = {
		left: 'left',
		center: 'center',
		right: 'right'
	};
	
	var Sort = {
		asc: "asc",
		desc: ""
	};
	
	/*
	 * 组件的命名空间
	 */
	UI.Grid = $.inherit(UI.Component, {
		
		/*
		 * 默认配置项
		 */
		defConf: {
			//showIndex: false,		//是否显示序号
			//showCheckbox: false,	//是否显示多选框
			fillSpace: true,		//是否用空白行进行渲染
			linage: 10				//每页可展现的记录数  
		},
		
		/*
		 * 类名
		 */
		className: 'UI.Grid',
		
		/*
		 * 组件模板
		 */
		tpl: [
		    "<div id='{id}' class='", ClassName, " {cls}'>",
		    	"<table class='pageui-grid-body' cellpadding='0' cellspacing='0'>",
			    	"<thead>",
			    		"<tr></tr>",
			    	"</thead>",
			    	"<tbody></tbody>",
		    	"</table>",
		    "</div>"
		].join(""),
		
		/*
		 * 初始化方法,组件被实例化之后就会执行
		 */
		init: function() {
			
			var conf = this.conf;
			
			this._thead = this.dom.find("thead>tr");
			
			this._tbody = this.dom.find("tbody");
			
			this.align(conf.align);
			
			this.linage(conf.linage);
			
			this.fillSpace(conf.fillSpace);
			
			this.loadFilter(conf.loadFilter);
			
			this.orderFilter(conf.orderFilter);
			
			this.setColumns(conf.columns);
			
			this.rowSelectedStyle(conf.rowSelectedStyle);
			
			this.checkOnRow(conf.checkOnRow);
			
			this.onRowClick(conf.onRowClick);
			
			this.onCheck(conf.onCheck);
			
			this.pager(conf.pager);
			
			this.setBottomBtns(conf.bottomBtns);
			
			this.initEvent();
			
			conf.data && this.loadData(conf.data);
			
		},
		
		/*
		 * 初始化事件
		 */
		initEvent: function() {
			
			var _this = this,
				$thead = this._thead,
				$tbody = this._tbody;
			
			this.dom
				/*
				 * 点击行
				 */
				.on("click", "tbody tr:not(.pageui-grid-empty)", function(event) {
					
					var $this = $(this),
						index = $this.index(),
						rowData = _this.getRowData(index);
					
					_this._RSS && $this.addClass("active").siblings().removeClass("active");
					
					_this._checkOnRow && $this.find('td.pageui-grid-checkbox i.pageui-checkbox').trigger('click');
                    
                    _this._rowClick(rowData, index);
					
				})
				/*
				 * checkbox
				 */
				.on("click", "tbody td.pageui-grid-checkbox i.pageui-checkbox", function() {
					
					var $this = $(this).toggleClass("active"),
						index = $this.closest("tr").index();
					
					var as = $tbody.find("td.pageui-grid-checkbox i.active").size(),
						is = $tbody.find("td.pageui-grid-checkbox").size(),
						rowData = _this.getRowData(index);
					
					$thead.find("i.pageui-checkbox")[(as == is) ? "addClass" : "removeClass"]("active");
					
					_this._check(rowData, $this.hasClass("active"), index);
					
					return false;
					
				});
			
			$thead
				/*
				 * 全选
				 */
				.on("click", "i.pageui-checkbox", function() {
					
					var flag = $(this).toggleClass("active").hasClass("active");
					
					$tbody.find("tr:not(.pageui-grid-empty) td.pageui-grid-checkbox i")[flag ? "addClass" : "removeClass"]("active");
					
					_this._D.each(function(i, item) {
						
						_this._check(item, flag, i);
						
					});
					
				})
				/*
				 * 排序字段的鼠标移入
				 */
				.on("mouseenter", "th.pageui-grid-sort", function() {
					
					var $this = $(this).addClass("hover");
					
					$tbody.find("tr:not(.pageui-grid-empty) td:nth-child(" + ($this.index() + 1) + ")").addClass("hover");
					
				})
				/*
				 * 排序字段的鼠标移出
				 */
				.on("mouseleave", "th.pageui-grid-sort", function() {
					
					var $this = $(this).removeClass("hover");
					
					$tbody.find("td:nth-child(" + ($this.index() + 1) + ")").removeClass("hover");
					
				})
				/*
				 * 重新排序
				 */
				.on("click", "th.pageui-grid-sort", function() {
					
					var $this = $(this).toggleClass("asc");
					
					if (_this.onRequest) {
						
						_this.onRequest();
						
					} else {
						
						_this.sort($this.attr("data-field"), ($this.hasClass("asc") ? "asc" : "desc"));
						
					}
					
				});
			
		},
		
		/*
		 * 是否用空白行进行填充
		 */
		fillSpace: function(b) {
			
			this._FS = Boolean(b);
			
			return this;
			
		},
		
		/*
		 * 分页
		 */
		pager: function(config) {
			
			if (config) {
				
				var _this = this;
				
				this._P = new UI.Pager($.extend(config, {
					linage: this._L,
					success: function(data) {
						
						_this.loadData(data);
						
					}
				})).appendTo(this.dom);
				
				this.onRequest = function(flag) {
					
					var param = {},
						$sort = _this._thead.find("th.pageui-grid-sort");
					
					if ($sort.size()) {
						
						var arr = [];
						
						$sort.each(function() {
							
							var $this = $(this);
							
							arr.push({
								name: $this.attr("data-field"),
								order: $this.hasClass("asc") ? "asc" : "desc"
							});
							
						});
						
						if (_this._OF) param = _this._OF(arr) || {};
						
					}
					
					_this._P.onRequest(flag, param);
					
				};
				
				config.immediately && this.onRequest(true);
				
			}
				
			return this._P;
				
		},
		
		/*
		 * 每页多少行
		 */
		linage: function(n) {
			
			this._L = n;
			
			return this;
			
		},
		
		/*
		 * 统一对齐的方式.默认left
		 */
		align: function(type) {
			
			this._A = Align[type] || "left";
			
			return this;
			
		},
		
		/*
		 * 
		 */
		loadFilter: function(fn) {
			
			this._LF = $.isFunction(fn) ? fn : function(data) {
				
				return data;
				
			};
			
			return this;
			
		},
		
		/*
		 * 必须配置这个函数,远程排序才能正确运行
		 */
		orderFilter: function(fn) {
			
			this._OF = $.isFunction(fn) ? fn : function() {};
			
			return this;
			
		},
		
		/*
		 * 选择之后带有样式
		 */
		rowSelectedStyle: function(b) {
            
            this._RSS = Boolean(b);
            
            return this;
            
        },
		
		/*
		 * 是否点击整行选中checkbox
		 */
		checkOnRow: function(b) {
			
			this._checkOnRow = Boolean(b);
			
			return this;
			
		},
		
		/*
		 * 元数据
		 */
		setColumns: function(data) {
			
			var d = this._DS = [];
			
			var _this = this,
				arr = [],
				eArr = [];
			
			data.each(function(i, item) {
				
				if (item.checkbox) {
					
					if (_this._CB) return;
					
					var thTpl = "<th style='width: 30px; text-align: center;'><i class='pageui-checkbox'></i></th>";
					
					_this._S ?
						arr.splice(1, 0, thTpl)
						: arr.unshift(thTpl);
					
					_this._CB = item;
					
				} else if (item.sequence) {
					
					if (_this._S) return;
					
					arr.unshift("<th style='width: 30px; text-align: center;'>#</th>");
					
					_this._S = item;
					
				} else {
					
					arr.push([
					    "<th", (item.width ? " width='" + item.width + "'" : ""), (item.sortable ? [" class='pageui-grid-sort ", (Sort[item.order] || ""), "' data-field='", item.field, "'"].join("") : ""), " style='text-align:", (item.align || _this._A), "'>",
						    "<div>",
						    	item.title,
					    	"</div>",
					    "</th>"	
					].join(""));
					
					d.push(item);
					
				}
				
			});
			
			this._thead.html(arr.join(""));
			
			for (var i = 0, l = arr.length; i < l; i++) {
				
				eArr.push("<td><div>&nbsp;</div></td>");
				
			}
			
			this._EC = "<tr class='pageui-grid-empty'>" + eArr.join("") + "</tr>";
			
			return this;
			
		},
		
		/*
		 * 加载表格数据
		 */
		loadData: function(d) {
			
			var _this = this,
				data = this._D = this._LF(d),
				trArr = [];
			
			data && data.each(function(i, item) {
				
				var colArr = ["<tr>"];
				
				/*
				 * 是否序号
				 */
				_this._S && colArr.push([
				    "<td style='width: 30px; text-align: center;'>",
				    	_this._S.formatter ? _this._S.formatter(item, i) : (i + 1),
				    "</td>"
				].join(""));
				
				/*
				 * 是否多选
				 */
				_this._CB && colArr.push([
				    "<td class='pageui-grid-checkbox' style='width: 30px; text-align: center;'>",
				    	"<i class='pageui-checkbox", (_this._CB.formatter && _this._CB.formatter(item, i) ? " active" : ""), "'></i>",
				    "</td>"
				].join(""));
				
				_this._DS.each(function(_i, _item) {
					
					colArr.push([
						"<td style='text-align:", _item.align || _this._A, "'>",
							"<div", (_item.styler ? (" style='" + _item.styler(item[_item.field], item, i) + "'") : ""), ">",
								_item.formatter ? _item.formatter(item[_item.field], item, i) : item[_item.field],
							"</div>",
						"</td>"
					].join(""));
					
				});
				
				trArr.push(colArr.join("") + "</tr>");
				
			});
			
			/*
			 * 空白行
			 */
			if (this._FS && data) for (var i = data.length; i < this._L; i++) {
				
				trArr.push(this._EC);
				
			}
			
			this._tbody.html(trArr.join(""));
			
			if (_this._CB && !this._tbody.find("td.pageui-grid-checkbox i:not(.active)").size())
				this._thead.find("i.pageui-checkbox").addClass("active");
			
			return this;
		
		},
		
		/*
		 * 以现有的数据重新渲染表格
		 * 可由外部改变该组件绑定的数据实例后操作
		 */
		reload: function() {
		    
		    this.loadData(this._D);
		    
		},
		
		/*
		 * 单击表格一行时执行的回调函数
		 */
		onRowClick: function(fn) {
			
			this._rowClick = $.isFunction(fn) ? fn : function() {};
			
			return this;
			
		},
		
		/*
		 * checkbox选中取消时执行的回调函数
		 */
		onCheck: function(fn) {
			
	        this._check = $.isFunction(fn) ? fn : function() {};
			
			return this;
			
		},
		
		/*
		 * 根据index获取行数据
		 */
        getRowData: function(index) {
        	
        	return this._D[index];
            
        },
        
        /*
         * 返回表格数据
         */
        getData: function() {
        	
        	return this._D;
        	
        },
        
        /*
         * 获取选中记录
         */
        getSelected: function() {
            
            var _this = this,
                result = [];
            
            this._tbody.find("tr.active").each(function() {
                
                result.push(_this._D[$(this).index()]);
                
            });
            
            return result;
            
        },
        
        /*
         * 获取checkbox选中的记录
         */
        getChecked: function() {
        	
        	var _this = this,
        		result = [];
        	
        	this._tbody.find("td.pageui-grid-checkbox i.active").each(function() {
        		
        		result.push(_this._D[$(this).closest("tr").index()]);
        		
        	});
        	
        	return result;
        	
        },
        
        /*
         * 设置checkbox选中的记录
         */
        check: function(indexs) {
        	
        	var _this = this,
        	    array = $.isArray(indexs) ? indexs : [indexs];
        	
        	array.each(function(i, item) {
        		
        		var $obj = $(_this._tbody.find("tr")[item]);
        		
        		$obj.find('td.pageui-grid-checkbox i.pageui-checkbox').trigger('click');
        		
        	});
        	
        },
        
        /*
         * 排序
         */
        sort: function(name, order) {
        	
        	this._D.sort(function(a, b) {
        		
        		return order == "desc" ? a[name] > b[name] : a[name] < b[name];
        		
        	});
        	
        	this.loadData(this._D);
        	
        },
        
        /*
         * 设置底部按钮
         */
        setBottomBtns: function(btns) {
        	
        	if ($.isArray(btns)) {
        		
        		var $btns = $("<div class='pageui-grid-bBtns'></div>").appendTo(this.dom);
        		
        		btns.each(function(i, item) {
        			
        			$("<a class='pageui-btn'>" + item.title + "</a>")
        				.appendTo($btns)
        				.on("click", item.handler);
        			
        		});
        		
        	} else {
        		
        		this.dom.find(">div.pageui-grid-bBtns").empty().remove();
        		
        	}
        	
        }
        
	});
	
	/*
	 * 组件解析器
	 */
	UI.Grid.parser = function($elem, dataConfig) {
		
		var conf = {};
		
        if (!dataConfig.columns) {
			
			var arr = [];
			
			$elem.children().each(function() {
				
				var config = eval("({" + ($(this).attr("data-config") || "") + "})");
				
				/*
				 * title和field这两个属性必须要有
				 */
                if (config.title && config.field) {
                	
    				arr.push(config);
    				
                }
                
			});
			
			conf.columns = arr;
			
		}
		
		return conf;
		
	};
	
	/*
	 * 注册表格 组件
	 */
	UI._register({
		cls: ClassName,
		namespace: "Grid"
	});
	
	return UI.Grid;
	
});
