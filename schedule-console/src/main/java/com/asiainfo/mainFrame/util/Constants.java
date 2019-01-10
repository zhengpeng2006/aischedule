package com.asiainfo.mainFrame.util;

public class Constants {
    
    public final static String SESSION_USER_KEY = "user.key";
    public final static String SESSION_USER_ID = "user.id";
    public final static String SESSION_USER_SN = "user.name";
    public final static long SESSION_MAX_TIME = 60 * 60 * 1000;
    
    
    
    public final static String LOGIN = "login";
    public final static int DOMAIN_ID = 61 ; 
    public final static int ORG_ID=0;
    public final static String FUNC_TYPE = "1";
    public final static String PARENT_MENU_KEY = "parent.menu.id";
    
    /**
     * 系统参数KEY
     */
    public final static String PARAM_KEY_SYS_NAME="sys_name";//系统名称
    public final static String PARAM_KEY_COPYRIGHT="copyright";//版权
    public final static String PARAM_KEY_SYS_VERSION="sys_version";//版本号
    public final static String PARAM_KEY_PROVINCE_CODE="province_code";//省代码
    
    /**
     * JAVA系统参数
     */
    public final static String JAVA_HOME="java.home";
    public final static String JAVA_VENDOR="java.vendor";
    public final static String JAVA_VERSION="java.version";
    public final static String JAVA_RUNTIME_NAME="java.runtime.name";
    public final static String JAVA_RUNTIME_VERSION="java.runtime.version";
    public final static String JAVA_LIBRARY_PATH="java.library.path";
    
    public final static String JAVA_VM_INFO="java.vm.info";
    public final static String JAVA_VM_NAME="java.vm.name";
    public final static String JAVA_VM_VENDOR="java.vm.vendor";
    public final static String JAVA_VM_VERSION="java.vm.version";
    
    /**
     * 用户信息参数KEY
     */
    public final static String USER_HOME="user.home";
    public final static String USER_DIR="user.dir";
    public final static String USER_NAME="user.name";
    public final static String USER_TIMEZONE="user.timezone";
    public final static String USER_LANGUAGE="user.language";
    
    public final static String CONTEXT_SERVER_INFO="ServerInfo";
    public final static String SERVLET_VERSION="ServletVersion";
    public final static String OS_ARCH="os.arch";
    public final static String OS_NAME="os.name";
    public final static String OS_VERSION="os.version";
    
    
    /**
     * 系统共享信息KEY
     */
    public final static String COMMON_KEY_REGION = "common.zj.region";//浙江省各个地市信息
    public final static String COMMON_KEY_CENTER = "common.zj.center";//账务中心信息
    /**
     * 以MAP存储浙江省各个地市信息
     */
    public final static String COMMON_KEY_REGION_MAP = "common.zj.region.map";
    public final static String COMMON_KEY_CENTER_MAP = "common.zj.center.map";
    
    /**
     * 加载ADM_CONFIG信息
     */
    public final static String COMMOM_ADM_CONFIG = "common.admconfig";
    
    /**
     * 枚举值信息
     */
    public final static String COMMON_KEY_ENUMERRATION = "common.enumeration";
    
    /**
     * 错误编码信息
     */
    public final static String COMMON_KEY_ERROR_CODE = "common.errCode";
    
    
    /**
     * 数据源KEY常量
     */
    public final static String COMMON_KEY_DATASOURCE_KEY = "common.key.DataSourceKEY";
    
    
    /**
     * 日志级别
     */
    public final static int SYS_LOG_LEVER_FATAL=1;//致命
    public final static int SYS_LOG_LEVER_ERROR=2;//错误
    public final static int SYS_LOG_LEVER_INFO=3; //一般
    
    /**
     * 日志类型
     */
    public final static String SYS_LOG_Type_OP = "OPERATE"; //操作
    public final static String SYS_LOG_Type_ERROR="ERROR";//错误

    
    /**
     *  runStatus状态：
     *  [-2]任务失败,已经发送告警短信;
     *  [-1]任务失败,等待发送告警短信;
     *  [-1]任务失败,等待发送告警短信;
     *  [1]正在执行;
     *  [2]执行成功;
     *  [3]任务运行成功,并且已经发送短信通知;
     *  [4]确认结束;
     *  [5]审核通过;
     *  [6]审核未通过;
     *  [7]已忽略;'
     */
    public final static int FLOW_TASK_SCHEDULE_FAILANDMEG=-2;
    public final static int FLOW_TASK_SCHEDULE_FAILANDREADY=-1;
    public final static int FLOW_TASK_SCHEDULE_RUNNING=1;
    public final static int FLOW_TASK_SCHEDULE_SUCCESS=2;
    public final static int FLOW_TASK_SCHEDULE_SUCCESSANDMEG=3;
    public final static int FLOW_TASK_SCHEDULE_CONFIRM_OVER=4;
    public final static int FLOW_TASK_SCHEDULE_CHECK_YES=5;
    public final static int FLOW_TASK_SCHEDULE_CHECK_NO=6;
    public final static int FLOW_TASK_SCHEDULE_CHECK_IGNOR=7;
    
    /**
     * FTP默认端口
     */
    public final static int FTP_DEFAULT_PORT = 21;
    
    /**
     * 根据地市切换数据源的配置文件路径
     */
    public final static String ADM_CONFIG = "ADMConfig.properties";
    
    /**
     * 权限访问WebService配置KEY(ADMConfig.properties)
     */
    public final static String AXIS2_URL="axis2.url";
    
    /**
     * 进程类型
     */
    public final static Integer PROCESS_TYPE_SCRIPT = 0;//脚本类型
    /**
     * -正则表达式-
     */
    public final static String PATTERN_PHONE="^(13[0-9]|15[0|3|6|7|8|9]|18[7|8|9])[0-9]{8}$";//手机号码
    public final static String PATTERN_INTEGER="^([1-9])[0-9]*$";//整数
    public final static String PATTERN_ZJ_REGION_CODE="^0|57[0-9]|580$";//浙江地市代码
    public final static String PATTERN_EMAIL= "^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]+){1,2})$";
    public final static String PATTERN_IP_PORT = "^(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5]):\\d{2,5}$";
    public final static String PATTERN_DOMAIN_PORT = "^zj(\\w)+\\.yw\\.zj\\.chinamobile\\.com+:\\d{2,5}$";
    public final static String PATTERN_IP_PORT_SID = "^(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5]):\\d{2,5}:[a-zA-Z0-9$_]+$";
    public final static String PATTERN_DOMAIN_PORT_SID = "^zj(\\w)+\\.yw\\.zj\\.chinamobile\\.com+:\\d{2,5}:[a-zA-Z0-9$_]+$";
    

	/** webservice 返回值：操作失败 */
    public static final String RETURN_CODE_FAIL = "1";
	
	/** webservice 返回值：操作成功 */
    public static final String RETURN_CODE_SUCCESS= "2";
}
