package com.asiainfo.resultConstant;

/**
 * 结果码
 *
 * @author dingpk 75499
 * @date 2016年9月19日 上午10:52:58
 */
public interface ResultConstants
{
    
    interface ResultCodeValue
    {
        // 正确返回码
        String SUCCESS = "0";
        
        // 错误
        String FAILED = "1";
        
        // 异常捕获
        String ERROR = "2";
        
        // 主机分组编码已存在
        String HOST_GROUPCODE_EXISTS = "\u5206\u7ec4\u7f16\u7801\u5df2\u5b58\u5728";
        
        // 主机分组名称已存在
        String HOST_GROUPNAME_EXISTS = "\u5206\u7ec4\u540d\u79f0\u5df2\u5b58\u5728";
        
        // 主机分组名称和分组编码均已存在
        String HOST_GROUPNAME_CODE_ALLEXISTS =
            "\u5206\u7ec4\u540d\u79f0\u548c\u5206\u7ec4\u7f16\u7801\u5747\u5df2\u5b58\u5728";
        
        // 校验失败
        String CHECK_FAILED = "\u6821\u9a8c\u5931\u8d25";
        
        // 不能删除
        String DEL_FAILED = "\u4e0d\u80fd\u5220\u9664";
    }
    
    interface ResultKey
    {
        // 结果返回code
        String RESULT_CODE = "retcode";
        
        // 结果返回msg
        String RESULT_MSG = "resultMsg";
        
    }
    
}
