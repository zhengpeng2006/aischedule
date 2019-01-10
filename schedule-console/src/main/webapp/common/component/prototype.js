/*
 * 原型扩展类，主要是扩展jquery/JS原生方法
 * @date 2016-06-09
 * @auth AnNing
 */
define(["jquery"], function($) {
	
	/*
	 * 拓展String原型
	 */
	$.extend(String.prototype, {
		
		/*
		 * 格式化
		 */
		format: function(params) {
			
			params = Array.prototype.slice.call(arguments, 0);
			
			params.splice(0, 0, this);
			
			return $.format.apply(null, params);
			
		},
		
		/*
		 * 格式化之后生成jQuery对象
		 */
		format2Object: function(params) {
			
			return $(this.format.apply(this, arguments));
			
		},
		
		/*
		 * 清空两边空格
		 */
		trim: function() {
			
			return this.replace(/(^\s*)|(\s*$)/g, "");
		
		}
		
	});
	
	/*
	 * 拓展Array原型
	 */
	$.extend(Array.prototype, {
		
		/*
		 * 返回集合最后一个对象
		 */
		last: function() {
			
			return this[this.length - 1];
			
		},
		
		/*
		 * 避免了大量的将集合中的对象字符串化
		 */
		format: function(tpl) {
			
			var dom = [];
			
			for (var i = 0, l = this.length; i < l; i++) {
				
				dom.push(tpl.format(this[i]));
				
			}
			
			return dom.join("");
			
		},
		
		/*
		 * 通过索引删除集合中的一个元素
		 */
		removeItemByIndex: function(index) {
			
			this.splice(index, 1);
			
			return this;
			
		},
		
		/*
		 * 删除集合中的一个元素
		 */
		removeItem: function(item) {
			
			var index = this.indexOf(item);
			
			if (index > -1) {
				
				this.splice(index, 1);
				
			}
			
			return this;
			
		},
		
		/*
		 * 迭代
		 */
		each: function(fn, reverse) {
			
			if (fn && typeof fn == "function") {
				
				if (reverse) {
					
					for (var i = this.length; i--;) {
						
						if (fn.call(this, i, this[i])) break;
						
					}
					
				} else {
					
					for (var i = 0, l = this.length; i < l; i++) {
						
						if (fn.call(this, i, this[i])) break;
						
					}
					
				}
				
			}
			
			return this;
			
		},
		
		/*
		 * 交换集合中两个对象的位置
		 */
		swap: function(i, j) {
			
			var temp = this[i]; 
			
			this[i] = this[j]; 
			
			this[j] = temp; 
		
		},
		
		/*
		 * 谢尔排序
		 */
		shellSort: function() { 

			for (var step = this.length >> 1; step > 0; step >>= 1) { 

				for (var i = 0; i < step; i++) { 

					for (var j = i + step; j < this.length; j += step) { 

						var k = j, value = this[j]; 

						while (k >= step && this[k - step] > value) { 

							this[k] = this[k - step]; 

							k -= step; 

						} 

						this[k] = value; 

					} 

				} 

			}
			
			return this;

		},
		
		/*
		 * 快速排序(递归)
		 */
		quickSort: function(s, e) { 

			if (s == null) s = 0; 

			if (e == null) e = this.length - 1; 

			if (s >= e) return; 

			this.swap((s + e) >> 1, e); 

			var index = s - 1; 

			for (var i = s; i <= e; i++) { 

				if (this[i] <= this[e]) this.swap(i, ++index); 

			} 

			this.quickSort(s, index - 1); 

			this.quickSort(index + 1, e); 
			
			return this;

		},
		
		/*
		 * 归并排序
		 */
		mergeSort: function(s, e, b) { 

			if (s == null) s = 0; 

			if (e == null) e = this.length - 1; 

			if (b == null) b = new Array(this.length); 

			if (s >= e) return; 

			var m = (s + e) >> 1; 

			this.mergeSort(s, m, b); 

			this.mergeSort(m + 1, e, b); 

			for (var i = s, j = s, k = m + 1; i <= e; i++) { 

				b[i] = this[(k > e || j <= m && this[j] < this[k]) ? j++ : k++]; 

			} 

			for (var i = s; i <= e; ++i) this[i] = b[i];
			
			return this;

		},
		
		/*
		 * 堆排序
		 */
		heapSort: function() { 

			for (var i = 1; i < this.length; i++) { 

				for (var j = i, k = (j - 1) >> 1; k >= 0; j = k, k = (k - 1) >> 1) { 

					if (this[k] >= this[j]) break; 

						this.swap(j, k); 

					} 

				} 

				for (var i = this.length - 1; i > 0; i--) { 

					this.swap(0, i); 

					for (var j = 0, k = (j + 1) << 1; k <= i; j = k, k = (k + 1) << 1) { 

					if (k == i || this[k] < this[k - 1]) --k; 

					if (this[k] <= this[j]) break; 

					this.swap(j, k); 

				} 

			}
			
			return this;

		}
		
	});
	
	/*
	 * 垫片Array原型下的indexOf方法
	 */
	if (!Array.prototype.indexOf) Array.prototype.indexOf = function(item) {
		
		for (var i = 0, l = this.length; i < l; i++) {
			
			if (item === this[i]) return i;
			
		}
		
		if (i == l) return -1;
		
	};
	
	/*
	 * 拓展jquery的原型方法
	 */
	$.fn.afterTo = function($target) {
		
		$target.after(this);
		
		return this;
		
	};

	$.fn.beforeTo = function($target) {
		
		$target.before(this);
		
		return this;
		
	};
	
	/*
	 * 生成guid
	 */
	$.GUID = function() {

		return "xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx".replace(/[xy]/g, function(c) {
			
	        var r = Math.random() * 16 | 0,
	        	v = c == "x" ? r : (r&0x3|0x8);
	        
	        return v.toString(16);
	        
	    }).toUpperCase();
		
	};
	
	/*
	 * 格式化模板
	 * @auth
	 * @param tpl 模板字符串
	 * <p>
	 * "哇{0}, 哦{1}", ["啊", "一"] 转换成 "哇啊, 哦一"
	 * </p>
	 */
	$.format = function(tpl, params) {
		
		var args = Array.prototype.slice.call(arguments, 1);
		
		return tpl.replace(/\{([^\}]+)\}/img, function($0, $1) {
			
			//复杂对象里的属性
			if ($1.indexOf('.') != -1 && typeof(params) != 'string') {
				
				var objStrs = $1.split('.');
				
				var value = '';
				
				var obj = null;
				
				for (var i = 0, l = objStrs.length; i < l; i++) {
					
					var item = objStrs[i];
					
					try {
						
						obj = obj ? obj[item] : params[item];
						
						value = obj.toString();
						
					} catch (e) {
						
						value = '';
						
					};
					
				};
				
				return value;
			
			} else if (params.hasOwnProperty($1) && typeof(params) != 'string') {
				
				return params[$1];
				
			} else {
				
				return args[$1 * 1] || '';
			
			};
		
		});
	
	};
	
	/*
	 * 获取location.href中的参数
	 */
	$.getParam = function(key) {
		
		var map = {};
		
		window.location.search.substr(1).split("&").each(function(i, item) {
			
			var arr = item.split("=");
			
			map[arr[0]] = arr[1];
			
		});
		
		return (typeof key == "string") ? map[key] : map;
		
	};
	
	return $;
	
});
		
