/*
 * 树
 */
define(["component", "scrollbox"], function(cmp) {
	
	var ClassName = "pageui-tree";
	
	var TYPE = {
		"normal": "",
		"multi": "<i class='pageui-checkbox'></i>",
		"radio": "<i class='pageui-radio'></i>"
	};
	
	/*
	 * 节点实例
	 */
	function Node(id, pid, text, data, n) {
		
		this.id = id;
		
		this.pid = pid;
		
		this.text = text;
		
		this.data = data;
		
		this.n = n;
		
		this._p;
		
		this._c;
		
	}
	
	/*
	 * 命名空间
	 */
	UI.Tree = $.inherit(UI.Component, {
		
		/*
		 * 默认配置项
		 */
		defConf: {
			textField: "text",
			idField: "id",
			pIdField: "pid",
			rootId: "-1",
			data: null
		},
	
		/*
		 * 类名
		 */
		className: 'UI.Tree',
		
		/*
		 * 模板
		 */
		tpl: ["<div id='{id}' class='", ClassName, " {cls}'></div>"].join(""),
		
		/*
		 * 初始化
		 */
		init: function() {
			
			var conf = this.conf;
		
			this._allNodes = [];				//保存该树的所有节点
			
			this._immaturity = {};				//没找到父节点的节点,先放这里
			
			this._TF = conf.textField;
			
			this._IF = conf.idField;
			
			this._PF = conf.pIdField;
			
			this.onClick(conf.onClick);
			
			this.type(conf.type);
			
			this._rootNode = new Node(conf.rootId);
			
			this._mainUl = $("<ul class='pageui-tree-main'></ul>");
			
			this._content = new UI.ScrollBox().html(this._mainUl).appendTo(this.dom);
			
			conf.data && this.loadData(conf.data);
			
			this.initEvent();
			
		},
		
		/*
		 * 
		 */
		initEvent: function() {
			
			var _this = this;
			
			this.dom
				/*
				 * 展开收起
				 */
				.on("click", "i.cross", function() {
				
					var $this = $(this),
						flag = $this.toggleClass("active").hasClass("active");
					
					$this.siblings("i")[flag ? "addClass" : "removeClass"]("active");
					
					$this.siblings("ul").stop()[flag ? "slideDown" : "slideUp"]("fast", function() {
						
						$(this).css({height: "auto"});
						
						_this._content.resize();
						
					});
				
				})
				/*
				 * 点击节点
				 */
				.on("click", "li a", function() {
					
					_this._activeNode && _this._activeNode.removeClass("active");
					
					var $this = _this._activeNode = $(this).addClass("active");
					
					_this._click(_this._allNodes[$this.parent().attr("data-n")]);
					
				})
				/*
				 * 单选
				 */
				.on("click", "i.pageui-radio", function() {
					
					_this._AR && _this._AR.removeClass("active");
					
					_this._AR = $(this).addClass("active");
					
				})
				/*
				 * 多选
				 */
				.on("click", "i.pageui-checkbox", function() {
					
					$(this).toggleClass("active");
					
				});
			
		},
		
		/*
		 * 切换状态
		 */
		type: function(type) {
			
			this._type = type;
			
			this.dom.find("span").html(TYPE[type] || "");
			
		},
		
		/*
		 * 点击节点
		 */
		onClick: function(fn) {
			
			this._click = $.isFunction(fn) ? fn : function() {};
			
		},
		
		/*
		 * 加载数据
		 * flag为true时会清空所有数据后再加载data
		 */
		loadData: function(data, flag) {
			
			var AN = this._allNodes,
				IM = this._immaturity,
				TF = this._TF,
				IF = this._IF,
				PF = this._PF;
			
			/*
			 * 现将data中的节点对象化,并推入aNodes集合中
			 */
			var item;
			
			for (var i = 0, il = data.length; i < il; i++) {
				
				item = data[i];
				
				AN.push(new Node(item[IF], item[PF], item[TF], item, AN.length));
				
			}
			
			var node, pNode, children;
			
			/*
			 * 这里相当于格式化节点,查找到该节点的父节点,子节点
			 */
			for (var n = 0, nl = AN.length; n < nl; n++) {
				
				pNode = null;
				
				node = AN[n];
				
				children = IM[node.id];		//查看该节点是否已经有子节点呗格式化过

				if (children) {										//若该节点的子节点先进行了格式化
					
					for (var c = 0, cl = children.length; c < cl; c++) {
						
						children[c]._p = node;
						
					}
					
					node._c = children;
					
					delete children;
					
				}
				
				/*
				 * 开始找父节点
				 */
				if (node.pid == this._rootNode.id) {				//找出父节点
					
					pNode = this._rootNode;
				
				} else {
					
					for (var m = n; m--;) {							//从当前位置往前找
						
						if (AN[m].id == node.pid) {
							
							pNode = AN[m];
							
							break;
						
						} else if (AN[m].pid == node.pid) {
							
							pNode = AN[m]._p;
							
							break;
							
						}
					}
					
					if (!pNode) {									//往后找
						
						for (var k = n + 1; k < nl; k++) {
							
							if (AN[k].id == node.pid) {
								
								pNode = AN[k];
								
								break;
							}
							
						}
						
					}
					
				}
				
				/*
				 * 如果找到了父节点
				 */
				if (pNode) {
					
					node._p = pNode;
					
					pNode._c ? pNode._c.push(node) : (pNode._c = [node]);
					
				} else {
					
					if (IM[node.pid]) {
						
						IM[node.pid].push(node);
						
					} else {
						
						IM[node.pid] = [node];
						
					}
					
				}
				
			}
			
			this._mainUl.html(this._render(this._rootNode));
			
			
		},
		
		/*
		 * 渲染一个节点下的所有子节点
		 */
		_render: function(node) {
			
			var c = node._c;							//获取node的子节点集合
			
			if (!c) return "";							//如果没有子节点,直接返回空字符 
			
			var arr = [],
				span = ["<span>", TYPE[this._type], "</span>"].join(""),
				cNode,
				cc;
			
			for (var i = 0, cl = c.length; i < cl; i++) {
				
				cNode = c[i];							//子节点
				
				cc = cNode._c;							//子节点的子节点集合
				
				arr.push([								//构造节点文档
					"<li class='pageui-tree-node", ((i == cl - 1) ? " last-child" : "") , "' data-n='", cNode.n, "'>",
						"<i class='", (cc ? "cross" : "line"), "'></i>",
						span,
						"<i class='", (cNode.icon || (cc ? "folder" : "file")), "'></i>",
						"<a>", cNode.text, "</a>",
						(cc ? "<ul class='pageui-tree-cluster'>" + this._render(cNode) + "</ul>" : ""),
					"</li>"
				].join(""));
				
			}
			
			return arr.join("");
			
		}
		
	});
	
	return UI.Tree;
	
});