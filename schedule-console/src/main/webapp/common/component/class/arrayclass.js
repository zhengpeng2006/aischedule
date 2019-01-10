(function() {
	
	/*
	 * 判断一个对象是否是数组
	 */
	function isArray(obj) {   
		
		return Object.prototype.toString.call(obj) === "[object Array]";    
	
	}
	
	/*
	 * 删除集合中的一个对象
	 */
	function removeArrItem(arr, item) {
		
		for (var i = 0, l = arr.length; i < l; i++) {
			
			if (item === arr[i]) {
				
				arr.splice(i, 1);
				
				return;
				
			};
			
		}
		
	}
	
	/*
	 * config = {
	 *     data: [],
	 *     idField: "",
	 *     onChange: function() {}
	 * }
	 */
	var ArrayClass = window.ArrayClass = function(config) {
		
		return config.idField ? new ComplexArr(config) : new Arr(config);
		
	};
	
	/*
	 * 普通的集合,对象与对象之间没有任何关系.
	 */
	function Arr(config) {
		
		this.onChange(config.onChange || function() {});
		
		this.setData(config.data);
		
	}
	
	/*
	 * 拓展原型
	 */
	Arr.prototype = {
			
		/*
		 * 监听数据改变
		 */
		onChange: function(fn) {
			
			if (fn) {
				
				this._change = (typeof fn == "function" ? fn : function() {});
				
			} else {
				
				this._change(this.data || []);
				
			}
			
		},
		
		/*
		 * 塞入数据,清空之前的数据
		 */
		setData: function(data) {
			
			this.data = (isArray(data) ? data : []);
			
			this.onChange();
			
		},
		
		/*
		 * 插入一条(或n条)记录
		 * index为插入指定索引位置,如果不传就是追加在最后
		 */
		addData: function(data, index) {
			
			var i = index || this.data.length;
			
			this.data.splice.apply(this.data, [i, 0].concat(data));
			
			this.onChange();
			
		},
		
		/*
		 * 将data更新到指定索引位置上
		 */
		updateData: function(index, data) {
			
			if ((index >= this.data.length) || (index < 0)) return;
			
			this.data.splice(index, 1, data);
			
			this.onChange();
			
		},
		
		/*
		 * 删除一个元素
		 */
		removeData: function(item) {
			
			var data = this.data;
			
			for (var i = 0, l = data.length; i < l; i++) {
				
				if (item === data[i]) {
					
					this.removeItemByIndex(i);
					
					return;
					
				};
				
			}
			
		},
		
		/*
		 * 通过索引删除一个元素
		 */
		removeDataByIndex: function(index) {
			
			this.data.splice(index, 1);
			
			this.onChange();
			
		},
		
		/*
		 * 清空
		 */
		clear: function() {
			
			this.data = [];
			
			this.onChange();
			
			return this;
			
		}
		
	};
	
	/*
	 * 集合中的每一个对象都有唯一的标识,idField为标识的字段
	 */
	function ComplexArr(config) {
		
		this.idField = config.idField;
		
		this.onChange(config.onChange || function() {});
		
		this.setData(config.data);
		
	}
	
	/*
	 * 拓展原型
	 */
	ComplexArr.prototype = {
			
		/*
		 * 监听数据改变
		 */
		onChange: function(fn) {
			
			if (fn) {
				
				this._change = fn;
				
			} else {
				
				this._change(this.data || []);
				
			}
			
		},
		
		/*
		 * 初始化数据
		 */
		setData: function(d) {
			
			var idField = this.idField;
			
			var data = this.data = [],				//展示所有数据的集合,change方法被触发后,这个变量会被当做形参传入
				dataMap = this.dataMap = {},		//data的map形式.
				statusArr = this.statusArr = [],	//保存所有展示数据以及原始数据状态的集合["original", "modified", "added", "removed"]
				statusMap = this.statusMap = {};	//statusArr的map形式.
			
			/*
			 * 遍历data,并将data赋值给this.original,对象实例化后方便追溯
			 */
			this.original = (isArray(d) ? d : []).each(function(i, item) {
				
				var id = item[idField];
				
				dataMap[id] = $.extend(true, {}, item);
				
				data.push(dataMap[id]);
				
				statusMap[id] = {id: id, status: "original"};
				
				statusArr.push(statusMap[id]);
				
			});
			
			this.onChange();
			
		},
		
		/*
		 * 添加数据(如果已经存在,即视为修改)
		 * data可以是一个bean,也可以是一个bean的集合
		 */
		addData: function(d) {
			
			var _this = this,
				idField = this.idField,
				data = this.data,
				dataMap = this.dataMap,
				statusMap = this.statusMap,
				statusArr = this.statusArr;
			
			/*
			 * 遍历data
			 */
			([].concat(d)).each(function(i, item) {
				
				var id = item[idField];
				
				/*
				 * 如果可以在状态集合中追溯到这条数据的记录,说明这行数据已经存在了
				 */
				if (statusMap[id]) {
					
					var _item = statusMap[id];
					
					/*
					 * 除了handling状态以外的三种状态(["original", "removed", "modified"])全都改变为"modified"
					 * 只有原始数据能有这三种状态
					 */
					switch(_item.status) {
					
						case "original":
						case "removed":		//如果是已经删掉的原始数据又被重新添加进来,视为修改操作
							
							_item.status = "modified";
					
					}
					
				} else {					//插入一条状态的记录
					
					var _item = statusMap[id] = {
						id: id,
						status: "added"
					};
					
					statusArr.push(_item);
					
				}
				
				if (dataMap[id]) {	//数据覆盖
					
					$.extend(true, dataMap[id], item);
					
				} else {			//新增
					
					data[data.length] = dataMap[id] = item;
					
				}
				
			});

			this.onChange();
			
		},
		
		/*
		 * 更新一个对象
		 */
		updateData: function(data) {
			
			this.addData(data);
			
		},
		
		/*
		 * 删除数据
		 * 允许通过id删除或直接塞入对象删除
		 */
		removeData: function(param, flag) {
			
			var id = param[this.idField] || param;
			
			if (this.statusMap[id]) {
				
				var item = this.statusMap[id];
				
				if (item.status != "added") {
					
					item.status = "removed";
					
				} else {
					
					removeArrItem(this.statusArr, item);
					
					delete this.statusMap[id];
					
				}

				removeArrItem(this.data, this.dataMap[id]);
				
				delete this.dataMap[id];
					
				!flag && this.onChange();
				
			} else if (console.log) {
				
				console.log("ArrayClass.removeData方法入参出错");
				
			}
			
		},
		
		/*
		 * 获取修改数据,新增数据,以及被删除的原始数据
		 */
		getData: function() {
			
			var modifyArr = [],
				addedArr = [],
				removeArr = [],
				dataMap = this.dataMap;
			
			this.statusArr.each(function(i, item) {
				
				switch (item.status) {
				
					case "modified":
						
						modifyArr.push(dataMap[item.id]);
						
						break;
						
					case "added":
						
						addedArr.push(dataMap[item.id]);
						
						break;
						
					case "removed":
						
						removeArr.push(item.id);
				
				}
				
			});

			return {
				modified: modifyArr,
				added: addedArr,
				removed: removeArr
			};
			
		},
		
		/*
		 * 清空保存的所有数据
		 */
		clear: function() {
			
			this.data = [];
			
			this.dataMap = {};
			
			this.statusArr = [];
			
			this.statusMap = {};
			
			this.onChange();
			
			return this;
			
		}
		
	};
	
	/*
	 * 兼容amd模式
	 */
	if (typeof define === "function" && define.amd) {
		
		define("ArrayClass", [], function() {
			
			return ArrayClass;
		
		});
		
	}
	
})();