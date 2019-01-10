/*
 * 工作流程地图
 * 为广西南宁csf系统定制
 */
define(["component", "number", "form"], function() {
	
	var ClassName = "pageui-workflow",
		DefaultWidth = 800,
		MinWidth = 400,
		DefaultHeight = 600,
		MinHeight = 300,
		DefaultXAxis = 10,
		DefaultYAxis = 10,
		cellD = 120,
		cellR = cellD / 2;
	
	var TEXT = {
		begin: "开始",
		node: "节点",
		branch: "判断",
		end: "结束"
	};
	
	var NodeTpl = [
        "<div class='pageui-workflow-{nodeType}' data-color='{color}' style='border-color: #{color}'>{nodeTitle}</div>",
        "<a class='pageui-workflow-joint pageui-top' data-dir='top'></a>",
	    "<a class='pageui-workflow-joint pageui-right' data-dir='right'></a>",
	    "<a class='pageui-workflow-joint pageui-bottom' data-dir='bottom'></a>",
	    "<a class='pageui-workflow-joint pageui-left' data-dir='left'></a>"
	].join("");
	
	UI.WorkFlow = $.inherit(UI.Component, {
		
		/*
		 * 默认配置项
		 */
		defConf: {
			width: DefaultWidth,
			height: DefaultHeight,
			xAxis: DefaultXAxis,
			yAxis: DefaultYAxis,
			editor: []
		},
		
		/*
		 * 类名
		 */
		className: "UI.WorkFlow",
		
		/*
		 * 组件模板
		 */
		tpl: [
		    "<div id='{id}' class='", ClassName, " {cls}'>",
		    	"<div class='pageui-workflow-core'>",
		    		"<div class='pageui-workflow-canvas'></div>",
		    	"</div>",
		    	"<div class='pageui-workflow-tool'>",
		    		"<h1>画布尺寸</h1>",
		    		"<div class='pageui-workflow-tool-item' style='line-height: 26px;'>",
		    			"<input class='pageui-number' value='{xAxis}' data-config='width: 30, height: 26, min: 5' />",
		    			"<span>×</span>",
		    			"<input class='pageui-number' value='{yAxis}' data-config='width: 30, height: 26, min: 5' />",
		    			"<a class='pageui-workflow-repaint' style='margin-left: 10px;'>渲染</a>",
		    		"</div>",
		    		"<h1>节点工具</h1>",
		    		"<div class='pageui-workflow-tool-item' data-type='node'>",
		    			"<i data-type='begin'>",
		    				"<b class='pageui-workflow-tpl-begin'></b>",
		    				"<span>开始节点</span>",
		    			"</i>",
		    			"<i data-type='node'>",
	    					"<b class='pageui-workflow-tpl-node'></b>",
		    				"<span>普通节点</span>",
		    			"</i>",
		    			"<i data-type='branch'>",
	    					"<b class='pageui-workflow-tpl-branch'></b>",
		    				"<span>判断节点</span>",
		    			"</i>",
		    			"<i data-type='end'>",
	    					"<b class='pageui-workflow-tpl-end'></b>",
		    				"<span>结束节点</span>",
		    			"</i>",
		    		"</div>",
		    		"<h1>线段工具</h1>",
		    		"<div class='pageui-workflow-tool-item' data-type='line'>",
		    			"<i data-type='line'>",
    						"<b class='pageui-workflow-tpl-line'></b>",
		    				"<span>连线</span>",
		    			"</i>",,
		    			"<i data-type='unline'>",
							"<b class='pageui-workflow-tpl-unline'></b>",
		    				"<span>删除连线</span>",
		    			"</i>",
		    		"</div>",
		    		"<h1>文字工具</h1>",
		    		"<div class='pageui-workflow-tool-item' data-type='text'>",
		    			"<i data-type='text'>",
    						"<b class='pageui-workflow-tpl-text'></b>",
		    				"<span>文字</span>",
		    			"</i>",
		    			"<i data-type='untext'>",
							"<b class='pageui-workflow-tpl-untext'></b>",
		    				"<span>删除文字</span>",
		    			"</i>",
		    		"</div>",
		    	"</div>",
		    "</div>"
		].join(""),
		
		/*
		 * 初始化方法,组件被实例化之后就会执行
		 */
		init: function() {
			
			var conf = this.conf;

			this._core = this.dom.find("div.pageui-workflow-core");
			
			this._canvas = this.dom.find("div.pageui-workflow-canvas");
			
			this._tool = this.dom.find("div.pageui-workflow-tool");

			this.width(conf.width);

			this.height(conf.height);
			
			this.createReseau(conf.xAxis, conf.yAxis);
			
			this._activeTool = null;
			
			this._editor = $([
			    "<div class='pageui-workflow-editor'>",
			    	"<div class='pageui-workflow-colors'>",
			    		"<b>边框颜色:</b>",
			    		"<a style='background: #999999' name='999999'></a>",
			    		"<a style='background: #e8ed15' name='e8ed15'></a>",
			    		"<a style='background: #f19b2a' name='f19b2a'></a>",
			    		"<a style='background: #d34132' name='d34132'></a>",
			    		"<a style='background: #b13caf' name='b13caf'></a>",
			    		"<a style='background: #564267' name='564267'></a>",
			    		"<a style='background: #377790' name='377790'></a>",
			    		"<a style='background: #189a80' name='189a80'></a>",
			    		"<a style='background: #638a17' name='638a17'></a>",
			    	"</div>",
			    	"<div class='pageui-workflow-editor-btns'>",
			    		"<a name='confirm'>确定</a>",
			    		"<a name='remove'>删除</a>",
			    	"</div>",
			    "</div>"
			].join("")).appendTo(this._core);
			
			this._editorForm = new UI.Form({
				itemWidth: 150,
				titleWidth: 60,
				columns: [{
					type: "TextInput",
					name: "nodeTitle",
					prefix: "节点标题:"
				}, {
					type: "ComboBox",
					name: "nodeType",
					prefix: "节点类型:",
					data: [{
						text: "普通节点",
						value: "node"
					}, {
						text: "判断节点",
						value: "branch"
					}]
				}].concat(conf.editor)
			}).prependTo(this._editor);
			
			this.initEvent();
			
		},
		
		/*
		 * 初始化事件
		 */
		initEvent: function() {
			
			var _this = this,
				$canvas = this._canvas,
				$editor = this._editor,
				$editorForm = this._editorForm,
				$editorTarget;
			
			this._tool
				/*
				 * 重绘画布
				 */
				.on("click", "a.pageui-workflow-repaint", function() {
					
					var $number = $(this).siblings("div.pageui-number"),
						x = $number.eq(0).getPageUI().getValue(),
						y = $number.eq(1).getPageUI().getValue(),
						size = _this.getSize();
					
					if (size.x > x) {
						
						x = size.x;
						
						$number.eq(0).getPageUI().setValue(x);
						
					}
					
					if (size.y > y) {
						
						y = size.y;
						
						$number.eq(1).getPageUI().setValue(y);
						
					}
					
					_this.createReseau(+x, +y);
					
				})
				/*
				 * 工具
				 */
				.on("click", "i", function() {
				
					var $this = $(this);
					
					/*
					 * 已经有一个工具被激活
					 */
					if (_this._activeTool) {
						
						_this._activeTool.removeClass("active");
						
						switch (_this._activeTool.attr("data-type")) {
					
							/*
							 * 连线工具
							 */
							case "line":
								
								$canvas.find(".pageui-workflow-joint").hide();
								
								break;
								
							/*
							 * 删除连线
							 */
							case "unline": 
								
								$canvas.removeClass("pageui-workflow-removelines");
								
								break;
							
							/*
							 * 节点工具
							 */
							case "begin":
							case "node":
							case "branch":
							case "end":
								
								$canvas.off(".node");
								
								break;
	
							/*
							 * 文字工具
							 */
							case "text":
								
								$canvas
									.removeClass("cursor-text")
									.off("click.text")
								.find("div.pageui-workflow-text").each(function() {
									
									var $this = $(this),
										$textarea = $this.find("textarea").hide(),
										text = $textarea.val().trim();
									
									if (text.length) {
										
										$this.find("span").html(text.replace(/\n/img, "<br />")).show();
										
									} else {
										
										$this.empty().remove();
										
									}
									
								});
								
								break;
								
							/*
							 * 删除文字
							 */
							case "untext":
								
								$canvas.find("div.pageui-workflow-text>a").remove();
								
								break;
								
						}
						
						/*
						 * 如果选的就是当前节点.取消当前的工具就好了.
						 */
						if (_this._activeTool[0] == this) {
							
							_this._activeTool = null;
							
							return;
							
						}
						
					}
					
					/*
					 * 开始激活改工具
					 */
					_this._activeTool = $this.addClass("active");
					
					var type = $this.attr("data-type");
					
					switch (type) {
					
						/*
						 * 连线工具
						 */
						case "line":
	
							$canvas.find("a.pageui-workflow-joint").show();
							
							break;
							
						/*
						 * 删除连线
						 */
						case "unline": 
							
							$canvas.addClass("pageui-workflow-removelines");
							
							break;
						
						/*
						 * 节点工具
						 */
						case "begin":
						case "node":
						case "branch":
						case "end":
							
							$canvas
								.on("mouseenter.node", "div.pageui-workflow-cell:not(.pageui-workflow-holder)", function() {
									
									$(this)
										.addClass("mouse-enter")
										.html(NodeTpl.format({
											color: "999999",
											nodeType: type,
											nodeTitle: TEXT[type]
										}));
									
								})
								.on("mouseleave.node", "div.pageui-workflow-cell:not(.pageui-workflow-holder)", function() {
									
									$(this)
										.removeClass("mouse-enter")
										.empty();
									
								})
								.on("click.node", "div.pageui-workflow-cell:not(.pageui-workflow-holder)", function() {
									
									$(this)
										.addClass("pageui-workflow-holder")
										.removeClass("mouse-enter")
									.find(">div")
										.data({
											"begins": [],
											"ends": [],
											"data": {
												nodeType: type,
												nodeTitle: TEXT[type]
											}
										});
									
									$this.trigger("click");
									
								});
							
							break;
						
						/*
						 * 文字工具
						 */
						case "text":
							
							$canvas
								.addClass("cursor-text")
								.on("click.text", function(event) {
									
									if ($(event.target || event.srcElement).is("textarea")) return;
									
									var offset = $canvas.offset();
									
									$([
									    "<div class='pageui-workflow-text' style='top: ", event.pageY - offset.top, "px; left: ", event.pageX - offset.left, "px'>",
									    	"<textarea></textarea>",
									    	"<span></span>",
									    "</div>"
									].join(""))
										.appendTo($canvas)
									.find("textarea")
										.focus();
									
								})
							.find("div.pageui-workflow-text").each(function() {
								
								$(this)
									.find("textarea").show()
									.siblings().hide();
								
							});
							
							break;
							
						/*
						 * 删除文字
						 */
						case "untext":
							
							$canvas.find("div.pageui-workflow-text").append("<a class='pageui-workflow-remove'></a>");
							
							break;
					
					}
					
				});
			
			/*
			 * 拖拽文字
			 */
			$canvas.on("mousedown", "div.pageui-workflow-text", function(event) {
				
				if (_this._activeTool) return;
				
				var $this = $(this),
					top = parseInt($this.css("top")),
					left = parseInt($this.css("left")),
					dx = event.pageX,
					dy = event.pageY;
				
				UI.selectstart(true);

				$canvas
					.on("mousemove.drag", function(event) {
					
						$this.css({
							top: top + event.pageY - dy,
							left: left + event.pageX - dx,
						});
						
					})
					.on("mouseup.drag mouseleave.drag", function() {
						
						$canvas.off(".drag");
						
						UI.selectstart(false);
						
					});
				
			});
			
			/*
			 * 删除文字或连线
			 */
			$canvas.on("click", "a.pageui-workflow-remove", function() {
				
				var $target = $(this).parent();
				
				/*
				 * 删除文字
				 */
				if ($target.hasClass("pageui-workflow-text")) {
					
					$target.empty().remove();
				
				/*
				 * 删除线段
				 */
				} else {

					$target.data("begin").data("begins").removeItem($target[0]);
					
					$target.data("end").data("ends").removeItem($target[0]);
					
					$target.empty().remove();
					
				}
				
			});
			
			/*
			 * 编辑节点
			 */
			$canvas.on("click", "div.pageui-workflow-branch, div.pageui-workflow-node", function(event) {
				
				if (_this._activeTool) return;
				
				event.stopPropagation();
				
				$editorTarget = $(this);
				 
				var position = $editorTarget.parent().attr("data-position").split("-");
				
				$editor.css({
					top: position[1] * cellD + cellR,
					left: position[0] * cellD + cellR,
					display: "block"
				});
				
				$editor.find("a[name='" + $editorTarget.attr("data-color") + "']").trigger("click");

				$editorForm.loadData($editorTarget.data("data"));
				
			});
			
			/*
			 * 拖拽节点
			 */
			$canvas.on("mousedown", "div.pageui-workflow-holder>div", function() {
				
				var $node = $(this),
					data = $node.data("data");
				
				UI.selectstart(true);
				
				$canvas
					.on("mouseenter.drag", "div.pageui-workflow-cell:not(.pageui-workflow-holder)", function() {
						
						$(this)
							.addClass("mouse-enter")
							.html($node.clone());
						
					})
					.on("mouseleave.drag", "div.pageui-workflow-cell:not(.pageui-workflow-holder)", function() {
						
						$(this)
							.removeClass("mouse-enter")
							.empty();
						
					})
					.on("mouseup.drag", "div.pageui-workflow-cell:not(.pageui-workflow-holder)", function() {
						
						var $this = $(this);
						
						$this
							.removeClass("mouse-enter")
							.addClass("pageui-workflow-holder")
							.html(NodeTpl.format(data))
						.find(">div")
							.data({
								"begins": [],
								"ends": [],
								"data": data
							});
						
						$node.data("begins").each(function(i, item) {
							
							var $line = $(item),
								info = $line.attr("data-info").split("|");
							
							renderLine($this.find("a.pageui-" + info[0].split(",")[1]), $line.data("end").siblings("a.pageui-" + info[1].split(",")[1]), $canvas);
							
						});
						
						$node.data("ends").each(function(i, item) {
							
							var $line = $(item),
								info = $line.attr("data-info").split("|");
							
							renderLine($line.data("begin").siblings("a.pageui-" + info[0].split(",")[1]), $this.find("a.pageui-" + info[1].split(",")[1]), $canvas);
							
						});
						
						removeNode($node);
						
					})
					.on("mouseup.drag mouseleave.drag", function() {
						
						$canvas.off(".drag");
						
						UI.selectstart(false);
						
					});
				
			});
			
			/*
			 * 关闭编辑面板
			 */
			$canvas.on("click", function() {
				
				_this.hideEditor();
				
			});
			
			/*
			 * 设置节点边框颜色
			 */
			$editor.on("click", "div.pageui-workflow-colors a", function() {
				
				$(this).addClass("active").siblings().removeClass("active");
				
			});
			
			/*
			 * 编辑节点底部的按钮
			 */
			$editor.on("click", "div.pageui-workflow-editor-btns a", function() {

				switch ($(this).attr("name")) {
				
					case "confirm":
						
						var param = $.extend({color: $editor.find("div.pageui-workflow-colors a.active").attr("name")}, $editorForm.getData());
						
						$editorTarget
							.data("data", param)
							.removeClass("pageui-workflow-node pageui-workflow-branch")
							.addClass("pageui-workflow-" + param.nodeType)
							.text(param.nodeTitle)
							.attr({
								"title": param.nodeTitle,
								"data-color": param.color
							})
							.css({"border-color": "#" + param.color});
						
						break;
						
					case "remove": 
						
						removeNode($editorTarget);
						
						break;
				
				}
				
				_this.hideEditor();
				
			});
			
			/*
			 * 连线
			 */
			var $beginJoint;
			
			$canvas.on("click", "a.pageui-workflow-joint", function() {
				
				var $this = $(this);
				
				if ($beginJoint && !$this.parent().find($beginJoint).size()) {
					
					renderLine($beginJoint, $this, $canvas);
					
					$beginJoint = null;
					
				} else {
					
					$beginJoint = $this;
					
				}
				
			});
			
			/*
			 * 鼠标移入移除
			 */
			$canvas
				.on("mouseenter", "div.pageui-workflow-holder>div", function() {
					
					if (_this._activeTool) return;
					
					$canvas.addClass("opacity-50");
					
					var $this = $(this).addClass("opacity-100");
					
					$this.data("begins").each(function(i, item) {
							
						$(item)
							.addClass("pageui-workflow-highlight-b")
						.data("end")
							.addClass("opacity-100");
						
					});
					
					$this.data("ends").each(function(i, item) {
							
						$(item)
							.addClass("pageui-workflow-highlight-e")
						.data("begin")
							.addClass("opacity-100");
						
					});
					
				})
				.on("mouseleave", "div.pageui-workflow-holder>div", function() {
					
					if (_this._activeTool) return;
					
					$canvas
						.removeClass("opacity-50")
					.find("div.opacity-100")
						.removeClass("opacity-100");
					
					var $this = $(this);
					
					$this.data("begins").each(function(i, item) {
							
						$(item).removeClass("pageui-workflow-highlight-b");
						
					});
					
					$this.data("ends").each(function(i, item) {
							
						$(item).removeClass("pageui-workflow-highlight-e");
						
					});
					
				});
			
		},
		
		/*
		 * 隐藏编辑窗口
		 */
		hideEditor: function() {
			
			this._editor.hide();
			
			this._editorTarget = null;
			
			this._editorForm.clear();
			
		},
		
		/*
		 * 获取数据
		 */
		getData: function() {
			
			var nodes = [],
				lines = [],
				texts = [];
			
			/*
			 * 查找所有节点
			 */
			this._canvas.find("div.pageui-workflow-holder").each(function() {
				
				var $this = $(this),
					position = $this.attr("data-position").split("-");
				
				nodes.push($.extend($this.find(">div").data("data"), {
					x: position[0],
					y: position[1]
				}));
				
			});
			
			/*
			 * 查找所有的线
			 */
			this._canvas.find("div.pageui-workflow-lines").each(function() {
				
				var $this = $(this),
					info = $this.attr("data-info").split("|"),
					beginInfo = info[0].split(","),
					beginPosition = beginInfo[0].split("-"),
					endInfo = info[1].split(","),
					endPosition = endInfo[0].split("-");
				
				lines.push({
					begin: {
						dir: beginInfo[1],
						x: beginPosition[0],
						y: beginPosition[1]
					},
					end: {
						dir: endInfo[1],
						x: endPosition[0],
						y: endPosition[1]
					}
				});
				
			});
			
			/*
			 * 查找所有的文字
			 */
			this._canvas.find("div.pageui-workflow-text").each(function() {
				
				var $this = $(this);
				
				texts.push({
					text: $this.find("textarea").val(),
					x: parseInt($this.css("left")),
					y: parseInt($this.css("top"))
				});
				
			});

			return {
				nodes: nodes,
				lines: lines,
				texts: texts
			};
			
		},
		
		/*
		 * 加载数据
		 */
		loadData: function(data) {
			
			var $canvas = this._canvas;
			
			/*
			 * 解析节点部分
			 */
			data.nodes.each(function(i, item) {
				
				$canvas.find(["div.pageui-workflow-cell[data-position='", item.x, "-", item.y, "']"].join(""))
					.html(NodeTpl.format(item))
					.addClass("pageui-workflow-holder")
				.find(">div")
					.data({
						"begins": [],
						"ends": [],
						"data": item
					});
				
			});
			
			/*
			 * 解析线
			 */
			data.lines.each(function(i, item) {
				
				var $begin = $canvas.find(["div.pageui-workflow-holder[data-position='", item.begin.x, "-", item.begin.y, "']>a.pageui-", item.begin.dir].join("")),
					$end = $canvas.find(["div.pageui-workflow-holder[data-position='", item.end.x, "-", item.end.y, "']>a.pageui-", item.end.dir].join(""));
				
				renderLine($begin, $end, $canvas);
				
			});
			
			/*
			 * 解析所有的文字
			 */
			data.texts.each(function(i, item) {
				
				$canvas.append([
				    "<div class='pageui-workflow-text' style='top: ", item.y, "px; left: ", item.x, "px;'>",
				    	"<textarea style='display: none;'>", item.text, "</textarea>",
				    	"<span>", item.text.replace(/\n/img, "<br />"), "</span>",
				    "</div>"
				].join(""));
				
			});
			
		},
		
		/*
		 * 设置组件的宽度
		 */
		width: function(width) {
			
			this.dom.width(isNaN(width) ? DefaultWidth : (width < 0 ? MinWidth : width));
			
		},
		
		/*
		 * 设置组件的高度度
		 */
		height: function(height) {
			
			this.dom.height(isNaN(height) ? DefaultHeigth : (height < 0 ? MinHeigth : height));
			
		},
		
		/*
		 * 生成网格
		 */
		createReseau: function(x, y) {
			
			var data = this.getData(),
				arr = [];
			
			for (var i = 0; i < y; i++) {
				
				for (var j = 0; j < x; j++) {
					
					arr.push([
					    "<div class='pageui-workflow-cell' data-position='", j, "-", i, "' style='top: ", i * cellD, "px; left:  ", j * cellD, "px'></div>"
					].join(""));
					
				}
				
			}
			
			this._canvas
				.css({width: x * cellD, height: y * cellD})
				.html(arr.join(""));
			
			this.loadData(data);
			
		},
		
		/*
		 * 获取实际使用了的最大网格尺寸
		 */
		getSize: function() {
			
			var x = [],
				y = [];
			
			this._canvas.find("div.pageui-workflow-holder").each(function() {
				
				var p = $(this).attr("data-position").split("-");
				
				x.push(p[0]);
				
				y.push(p[1]);
				
			});
			
			return {
				x: +Math.max.apply(x, x) + 1,
				y: +Math.max.apply(y, y) + 1
			};
			
		}
		
	});
	
	UI.WorkFlow.draw = function($target, data) {
		
		var $canvas = $("<div class='pageui-workflow-canvas' style='overflow: hidden; width: 100%; height: 100%;'></div>");
		
		$target.empty().append($canvas);
		
		/*
		 * 解析节点部分
		 */
		data.nodes.each(function(i, item) {
			
			$([
			    "<div class='pageui-workflow-cell' data-position='", item.x, "-", item.y, "' style='top: ", item.y * cellD, "px; left: ", item.x * cellD,"px;'>",
			    	NodeTpl.format(item),
			    "</div>"
			].join(""))
				.appendTo($canvas)
			.find(">div")
				.data({
					"begins": [],
					"ends": [],
					"data": item
				});
			
		});
		
		/*
		 * 解析线
		 */
		data.lines.each(function(i, item) {
			
			var $begin = $canvas.find(["div.pageui-workflow-cell[data-position='", item.begin.x, "-", item.begin.y, "']>a.pageui-", item.begin.dir].join("")),
				$end = $canvas.find(["div.pageui-workflow-cell[data-position='", item.end.x, "-", item.end.y, "']>a.pageui-", item.end.dir].join(""));
			
			renderLine($begin, $end, $canvas);
			
		});
		
		/*
		 * 解析所有的文字
		 */
		data.texts.each(function(i, item) {
			
			$canvas.append([
			    "<div class='pageui-workflow-text' style='top: ", item.y, "px; left: ", item.x, "px;'>",
			    	"<span>", item.text.replace(/\n/img, "<br />"), "</span>",
			    "</div>"
			].join(""));
			
		});
		
		/*
		 * 事件
		 */
		$canvas
			.on("mousedown", function(event) {
				
				if ((event.target || event.srcElemtn) == $canvas[0]) {
					
					var dx = event.pageX,
						dy = event.pageY,
						st = $canvas.scrollTop(),
						sl = $canvas.scrollLeft();
					
					UI.selectstart(true);
					
					$canvas
						.on("mousemove.drag", function(event) {
							
							var mx = event.pageX,
								my = event.pageY;
							
							$canvas.scrollTop(st + dy - my);
							
							$canvas.scrollLeft(sl + dx - mx);
							
						})
						.on("mouseup.drag mouseleave.drag", function() {
							
							$canvas.off(".drag");
							
							UI.selectstart(false);
							
						});
					
				}
				
			})
			/*
			 * 鼠标移入
			 */
			.on("mouseenter", "div.pageui-workflow-cell>div", function() {
				
				$canvas.addClass("opacity-50");
				
				var $this = $(this).addClass("opacity-100");
				
				$this.data("begins").each(function(i, item) {
						
					$(item)
						.addClass("pageui-workflow-highlight-b")
					.data("end")
						.addClass("opacity-100");
					
				});
				
				$this.data("ends").each(function(i, item) {
						
					$(item)
						.addClass("pageui-workflow-highlight-e")
					.data("begin")
						.addClass("opacity-100");
					
				});
				
			})
			/*
			 * 鼠标移出
			 */
			.on("mouseleave", "div.pageui-workflow-cell>div", function() {
				
				$canvas
					.removeClass("opacity-50")
				.find("div.opacity-100")
					.removeClass("opacity-100");
				
				var $this = $(this);
				
				$this.data("begins").each(function(i, item) {
						
					$(item).removeClass("pageui-workflow-highlight-b");
					
				});
				
				$this.data("ends").each(function(i, item) {
						
					$(item).removeClass("pageui-workflow-highlight-e");
					
				});
				
			});
		
		return $target;
		
	};
	
	/*
	 * 删除节点
	 */
	function removeNode($node) {
		
		$node.data("begins").each(function(i, item) {
			
			var $item = $(item);
			
			$(item).data("end").data("ends").removeItem(item);
			
			$item.remove();
				
		});
		
		$node.data("ends").each(function(i, item) {
			
			var $item = $(item);
			
			$(item).data("begin").data("begins").removeItem(item);
			
			$item.remove();
				
		});
		
		$node.parent().empty().removeClass("pageui-workflow-holder");
		
	}
	
	/*
	 * 绘制延伸节点,并返回延伸后的点
	 */
	function renderTrestle($joint, $lines) {
		
		var np = $joint.parent().position(),
			npt = np.top,
			npl = np.left,
			p;
		
		var top, left;
		
		switch ($joint.attr("data-dir")) {
		
			case "top":
				
				p = {
					top: npt,
					left: npl + cellR,
					dir: "top"
				};
				
				top = p.top + 20;
				
				$lines.append(["<div class='pageui-workflow-vline' style='height: 20px; top: ", p.top, "px; left: ", p.left, "px'></div>"].join(""));
				
				break;
			
			case "right":
				
				p = {
					top: npt + cellR,
					left: npl + cellD,
					dir: "right"
				};
				
				left = p.left - 20;
				
				$lines.append(["<div class='pageui-workflow-hline' style='width: 20px; top: ", p.top, "px; left: ", left, "px'></div>"].join(""));
				
				break;
			
			case "bottom":
				
				p = {
					top: npt + cellD,
					left: npl + cellR,
					dir: "bottom"
				};
				
				top = p.top - 20;
				
				$lines.append(["<div class='pageui-workflow-vline' style='height: 20px; top: ", top, "px; left: ", p.left, "px'></div>"].join(""));
				
				break;
			
			case "left":
				
				p = {
					top: npt + cellR,
					left: npl,
					dir: "left"
				};
				
				left = p.left + 20;
				
				$lines.append(["<div class='pageui-workflow-hline' style='width: 20px; top: ", p.top, "px; left: ", p.left, "px'></div>"].join(""));
				
				break;
		
		}
		
		$lines.append(["<a class='pageui-workflow-remove' style='top: ", (top || p.top), "px; left: ", (left || p.left), "px;'></a>"].join(""));
		
		return p;
		
	}
	
	/*
	 * 绘制两点之间的线
	 * 千万不要尝试修改这个方法中的任意一行代码
	 */
	function renderLine($begin, $end, $canvas) {
		
		/*
		 * 计算开始节点的位置
		 */
		var $beginNode = $begin.siblings("div"),
			$endNode = $end.siblings("div"),
			$lines = $("<div class='pageui-workflow-lines'></div>").appendTo($canvas),
			begin = renderTrestle($begin, $lines),
			end = renderTrestle($end, $lines),
			bDir = begin.dir,
			eDir = end.dir,
			bl = begin.left,
			el = end.left,
			bt = begin.top,
			et = end.top,
			w = Math.abs(bl - el),
			h = Math.abs(bt - et);
		
		$lines
			.attr({"data-info": [
			    $begin.parent().attr("data-position"), ",", bDir, 
			    "|",
			    $end.parent().attr("data-position"), ",", eDir
			].join("")})
			.data({
				"begin": $beginNode,
				"end": $endNode
			});
		
		$beginNode.data("begins").push($lines[0]);
		
		$endNode.data("ends").push($lines[0]);
			
		/*
		 * 上下
		 */
		if ((bDir == "top" && eDir == "bottom") || (bDir == "bottom" && eDir == "top")) {
			
			var top, bottom;
			
			if (bDir == "top") {			//过滤出谁是朝上的谁是朝下的
				
				top = begin;
				
				bottom = end;
				
			} else {

				top = end;
				
				bottom = begin;
				
			}
			
			if (top.top >= bottom.top) {	//两者面对面
				
				var _h = Math.ceil(h / 2 / cellD) * cellD;
				
				$lines.append([
				    "<div class='pageui-workflow-vline' style='height: ", _h, "px; top: ", bottom.top, "px; left: ", bottom.left, "px'></div>",
				    "<div class='pageui-workflow-hline' style='width: ", w, "px; top: ", (bottom.top + _h), "px; left: ", ((bl > el) ? el : bl), "px'></div>",
				    "<div class='pageui-workflow-vline' style='height: ", (h - _h), "px; top: ", (bottom.top + _h), "px; left: ", top.left, "px'></div>"
				].join(""));
				
			} else {						//两者背靠背
				
				var _w = Math.floor(w / 2 / cellD) * cellD + cellR,
					flag = top.left > bottom.left,
					left = flag ? (top.left - _w) : top.left + _w;
				
				$lines.append([
				    "<div class='pageui-workflow-hline' style='width: ", _w, "px; top: ", top.top, "px; left: ", (flag ? top.left - _w : top.left), "px'></div>",
				    "<div class='pageui-workflow-vline' style='height: ", h, "px; top: ", top.top, "px; left: ", left, "px'></div>",
				    "<div class='pageui-workflow-hline' style='width: ", Math.abs(w - _w), "px; top: ", bottom.top, "px; left: ", ((top.left >= bottom.left) ? bottom.left : left), "px'></div>"
				].join(""));
				
			}
		
		/*
		 * 左右
		 */
		} else if ((bDir == "left" && eDir == "right") || (bDir == "right" && eDir == "left")) {
			
			var left, right;
			
			if (bDir == "left") {			//过滤出谁是朝左的谁是朝右的
				
				left = begin;
				
				right = end;
				
			} else {

				left = end;
				
				right = begin;
				
			}
			
			if (left.left >= right.left) {	//两者面对面
				
				var _w = Math.ceil(w / 2 / cellD) * cellD;
				
				$lines.append([
				    "<div class='pageui-workflow-hline' style='width: ", _w, "px; top: ", right.top, "px; left: ", right.left, "px;'></div>",
				    "<div class='pageui-workflow-vline' style='height: ", h, "px; top: ", ((left.top > right.top) ? right.top : left.top), "px; left: ", (right.left + _w), "px;'></div>",
				    "<div class='pageui-workflow-hline' style='width: ", w - _w, "px; top: ", left.top, "px; left: ", (right.left + _w), "px;'></div>",
				].join(""));
				
			} else {						//两者背靠背
				
				var _h = Math.floor(h / 2 / cellD) * cellD + cellR,
					flag = left.top > right.top,
					top = flag ? (left.top - _h) : left.top + _h;
				
				$lines.append([
				    "<div class='pageui-workflow-vline' style='height: ", _h, "px; top: ", (flag ? left.top - _h : left.top), "px; left: ", left.left, "px;'></div>",
				    "<div class='pageui-workflow-hline' style='width: ", w, "px; top: ", top, "px; left: ", left.left, "px'></div>",
				    "<div class='pageui-workflow-vline' style='height: ", Math.abs(h - _h), "px; top: ", ((left.top >= right.top) ? right.top : top), "px; left: ", right.left, "px;'></div>"
				].join(""));
				
			}
		
		/*
		 * 朝向相同
		 */
		} else if (bDir == eDir) {
			
			switch (bDir) {

				case "top":
				case "bottom":
					
					var flag = bt > et, top, left;
					
					if (bl == el) {
						
						$lines.append([
						    "<div class='pageui-workflow-hline' style='width: ", cellR, "px; top: ", bt, "px; left: ", bl, "px;'></div>",
						    "<div class='pageui-workflow-vline' style='height: ", h, "px; top: ", (flag ? et : bt), "px; left: ", (bl + 60), "px;'></div>",
						    "<div class='pageui-workflow-hline' style='width: ", cellR, "px; top: ", et, "px; left: ", el, "px;'></div>"
						].join(""));
						
					} else {
						
						if (bDir == "top") {
							
							top = flag ? et : bt;
							
							left = flag ? bl : el;
							
						} else {
							
							top = flag ? bt : et;
							
							left = flag ? el : bl;
							
						}
						
						$lines.append([
						    "<div class='pageui-workflow-hline' style='width: ", w, "px; top: ", top, "px; left: ", ((bl > el) ? el : bl), "px;'></div>",
						    "<div class='pageui-workflow-vline' style='height: ", h, "px; top: ", (flag ? et : bt), "px; left: ", left, "px;'></div>"
						].join(""));
						
					}
					
					break;

				case "left":
				case "right":
					
					var flag = bl > el, top, left;
					
					if (bt == et) {
						
						$lines.append([
						    "<div class='pageui-workflow-vline' style='height: ", cellR, "px; top: ", bt, "px; left: ", bl, "px;'></div>",
						    "<div class='pageui-workflow-hline' style='width: ", w, "px; top: ", (bt + 60), "px; left: ", (flag ? el : bl), "px;'></div>",
						    "<div class='pageui-workflow-vline' style='height: ", cellR, "px; top: ", et, "px; left: ", el, "px;'></div>"
						].join(""));
						
					} else {
						
						if (bDir == "left") {
							
							top = flag ? bt : et;
							
							left = flag ? el : bl;
							
						} else {
							
							top = flag ? et : bt;
							
							left = flag ? bl : el;
							
						}
						
						$lines.append([
						    "<div class='pageui-workflow-vline' style='height: ", h, "px; top: ", ((bt > et) ? et : bt), "px; left: ", left, "px;'></div>",
						    "<div class='pageui-workflow-hline' style='width: ", w, "px; top: ", top, "px; left: ", (flag ? el : bl), "px;'></div>"
						].join(""));
						
					}
					
					break;
					
			}
		
		/*
		 * 一个上下方向的,一个左右方向的
		 * !!算法未优化,放在以后做!!
		 */
		} else {
			
			var top, right, bottom, left;
			
			/*
			 * 计算出两个拐点的方向
			 */
			switch (bDir) {
			
				case "top":
					
					top = begin;
					
					eDir == "right" ? right = end : left = end;
					
					break;

				case "right":
					
					right = begin;
					
					eDir == "top" ? top = end : bottom = end;
					
					break;
					
				case "bottom":
					
					bottom = begin;
					
					eDir == "right" ? right = end : left = end;
					
					break;
					
				case "left":
					
					left = begin;
					
					eDir == "top" ? top = end : bottom = end;
					
					break;
					
			}
			
			if (top && left) {
				
				/*
				 * 两个向量有交点
				 */
				if (left.top < top.top && left.left > top.left) {
					
					$lines.append([
					    "<div class='pageui-workflow-vline' style='height: ", h, "px; top: ", left.top, "px; left: ", top.left, "px'></div>",
					    "<div class='pageui-workflow-hline' style='width: ", w, "px; top: ", left.top, "px; left: ", top.left, "px'></div>"
					].join(""));
					
				} else {
					
					$lines.append([
		                "<div class='pageui-workflow-hline' style='width: ", w, "px; top: ", top.top, "px; left: ", ((top.left > left.left) ? left.left : top.left), "px'></div>",
		                "<div class='pageui-workflow-vline' style='height: ", h, "px; top: ", ((top.top > left.top) ? left.top : top.top), "px; left: ", left.left, "px'></div>"
	               	].join(""));
					
				}
				
			} else if (top && right) {
				
				/*
				 * 两个向量有交点
				 */
				if (top.left > right.left && top.top > right.top) {
					
					$lines.append([
					    "<div class='pageui-workflow-vline' style='height: ", h, "px; top: ", right.top, "px; left: ", top.left, "px'></div>",
					    "<div class='pageui-workflow-hline' style='width: ", w, "px; top: ", right.top, "px; left: ", right.left, "px'></div>"
					].join(""));
					
				} else {
					
					$lines.append([
		                "<div class='pageui-workflow-hline' style='width: ", w, "px; top: ", top.top, "px; left: ", ((top.left > right.left) ? right.left : top.left), "px'></div>",
		                "<div class='pageui-workflow-vline' style='height: ", h, "px; top: ", ((top.top > right.top) ? right.top : top.top), "px; left: ", right.left, "px'></div>"
	               	].join(""));
					
				}
				
			} else if (bottom && left) {
				
				/*
				 * 两个向量有交点
				 */
				if (bottom.top < left.top && bottom.left < left.left) {
					
					$lines.append([
					    "<div class='pageui-workflow-vline' style='height: ", h, "px; top: ", bottom.top, "px; left: ", bottom.left, "px'></div>",
					    "<div class='pageui-workflow-hline' style='width: ", w, "px; top: ", left.top, "px; left: ", bottom.left, "px'></div>"
					].join(""));
					
				} else {
					
					$lines.append([
					    "<div class='pageui-workflow-hline' style='width: ", w, "px; top: ", bottom.top, "px; left: ", ((bottom.left > left.left) ? left.left : bottom.left), "px'></div>",
					    "<div class='pageui-workflow-vline' style='height: ", h, "px; top: ", ((bottom.top > left.top) ? left.top : bottom.top), "px; left: ", left.left, "px'></div>"
					].join(""));
					
				}
				
			} else if (bottom && right) {
				
				/*
				 * 两个向量有交点
				 */
				if (bottom.top < right.top && bottom.left > right.left) {
					
					$lines.append([
					    "<div class='pageui-workflow-vline' style='height: ", h, "px; top: ", bottom.top, "px; left: ", bottom.left, "px'></div>",
					    "<div class='pageui-workflow-hline' style='width: ", w, "px; top: ", right.top, "px; left: ", right.left, "px'></div>"
					].join(""));
					
				} else {

					$lines.append([
		                "<div class='pageui-workflow-hline' style='width: ", w, "px; top: ", bottom.top, "px; left: ", ((bottom.left > right.left) ? right.left : bottom.left), "px'></div>",
		                "<div class='pageui-workflow-vline' style='height: ", h, "px; top: ", ((bottom.top > right.top) ? right.top : bottom.top), "px; left: ", right.left, "px'></div>"
	               	].join(""));
					
				}
				
			}
			
		}
		
		return $lines;
		
	}
	
	return UI.WorkFlow;
	
});