var paramObj = {};
$(function() {
    paramObj.hostId = $.params.get('hostId');

    // 初始化主机标识
    HostAllInfoJs.init(paramObj);

});
/**
 * 主机信息管理
 * 
 */
var HostAllInfoJs = (function() {

    var hostId = null;

    return {
        // 初始化
        init : function(param) {
            hostId = param.hostId;
            
            disabledArea("hostAllDetail",true);

            $.ajax.submit(null, 'qryAllInfo', "hostId=" + hostId, "hostAllDetail", function(result) {
            }, null);
            $.ajax.submit(null, 'qryHostConInfo', "hostId=" + hostId, "hostConTab", function(result) {
            }, null);
            $.ajax.submit(null, 'qryMasterSlaveInfo', "hostId=" + hostId, "hostSlaveTab", function(result) {
            }, null);

        }
    };
})();