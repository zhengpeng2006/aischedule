/*
 * 管理请求
 */
(function() {
	
	var dataArray = [
	    {},				//这个池里存放本地数据的url地址
	    {}				//这个池里存放远程数据的url地址
	];
	
	var Request = window.Request = {
		type: 0,
		mockPref: "",
		srvPref: "",
		set: function(urlId, mockSrc, srvSrc) {
			
			dataArray[0][urlId] = this.mockPref + mockSrc;
			
            dataArray[1][urlId] = this.srvPref + srvSrc;     
			
		},
		get: function(urlId, flag) {
			
			var type = isNaN(flag) ? this.type : (flag == 0 ? 0 : 1);
			
			return dataArray[type][urlId];
			
		},
		getDataArrays: function() {
			
			return dataArray[this.type];
			
		}
	};
	
	if (typeof define === "function" && define.amd) {

		define("request", [], function() {
			
			return Request;
		
		});
		
	}
	
})();