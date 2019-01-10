package com.asiainfo.deploy.api.external;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.deploy.api.interfaces.IAppParamSVProvider;
import com.asiainfo.deploy.common.bo.BODeployAppParamsBean;

/**
 * 应用参数对外接口
 * @author 孙德东(24204)
 */
public class AppParamUtils {
	private AppParamUtils(){}
	/**
	 * 获取应用需要配置的应用相关的参数名
	 * @param deployStragetyId
	 * @return
	 * @throws Exception 
	 */
	public static BODeployAppParamsBean[] getAppParams(long deployStragetyId) throws Exception {
		return ((IAppParamSVProvider)ServiceFactory.getService(IAppParamSVProvider.class)).getAppParams(deployStragetyId);
	}

	/**
	 * 获取应用已经配置的参数列表
	 * @param applicationId 应用Id
	 * @return Key:参数名  Value:参数值
	 * @throws Exception 
	 */
	public static BODeployAppParamsBean[] getConfigedAppParams(long applicationId) throws Exception {
		return ((IAppParamSVProvider)ServiceFactory.getService(IAppParamSVProvider.class)).getConfigedAppParams(applicationId);
	}
	
	/**
	 * 保存配置的应用的参数值
	 * @param applicationId 应用Id
	 * @param paramPairs Key:应用参数名 Value:应用参数值
	 * @return
	 * @throws Exception 
	 */
	public static void saveAppParams(BODeployAppParamsBean[] beans) throws Exception {
		((IAppParamSVProvider)ServiceFactory.getService(IAppParamSVProvider.class)).saveAppParams(beans);
	}
	
	/**
	 * 复制一份应用参数
	 * @throws Exception 
	 */
	public static boolean copyAppParams(long srcAppId, long dstAppId) throws Exception {
		return ((IAppParamSVProvider)ServiceFactory.getService(IAppParamSVProvider.class)).copyAppParams(srcAppId,  dstAppId);
	}

    /**
    * 通过应用参数标识查询参数
    * 
    * @param request
    * @throws Exception
    */
    public static BODeployAppParamsBean getAppParam(long applicationParamId) throws Exception
    {
        return ((IAppParamSVProvider) ServiceFactory.getService(IAppParamSVProvider.class)).getAppParam(applicationParamId);
    }

    /**
     * 判断该应用下key是否存在
     * 
     * @param request
     * @throws Exception
     */
    public static boolean isExistKey(long applicationId, String key) throws Exception
    {
        return ((IAppParamSVProvider) ServiceFactory.getService(IAppParamSVProvider.class)).isExistKey(applicationId, key);
    }
    
    /**
     * 删除参数
     * @param bean
     * @throws Exception
     */
    public static void delete(BODeployAppParamsBean bean) throws Exception
    {
        ((IAppParamSVProvider) ServiceFactory.getService(IAppParamSVProvider.class)).delete(bean);
    }
}
