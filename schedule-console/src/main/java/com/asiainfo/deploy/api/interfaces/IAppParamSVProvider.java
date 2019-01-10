package com.asiainfo.deploy.api.interfaces;

import com.asiainfo.deploy.common.bo.BODeployAppParamsBean;
import com.asiainfo.deploy.common.bo.BODeployAppTemplateBean;
import com.asiainfo.deploy.common.ivalues.IBODeployAppTemplateValue;

/**
 * 应用参数Api
 * @author 孙德东(24204)
 */
public interface IAppParamSVProvider
{
    /**
     * 获取应用需要配置的应用相关的参数名
     * @param deployStragetyId
     * @return
     * @throws Exception 
     */
    BODeployAppParamsBean[] getAppParams(long deployStragetyId) throws Exception;

    /**
     * 获取应用已经配置的参数列表
     * @param applicationId 应用Id
     * @return Key:参数名  Value:参数值
     * @throws Exception 
     */
    BODeployAppParamsBean[] getConfigedAppParams(long applicationId) throws Exception;

    /**
     * 保存配置的应用的参数值
     * @param applicationId 应用Id
     * @param paramPairs Key:应用参数名 Value:应用参数值
     * @return
     * @throws Exception 
     */
    void saveAppParams(BODeployAppParamsBean[] beans) throws Exception;

    /**
     * 通过应用参数标识查询部署应用参数
     * 
     * @param request
     * @throws Exception
     */
    BODeployAppParamsBean getAppParam(long applicationParamId) throws Exception;

    boolean isExistKey(long applicationParamId, String key) throws Exception;
    
    /**
     * 复制一份应用参数
     * @param srcAppId
     * @param dstAppId
     * @return
     * @throws Exception
     */
    public boolean copyAppParams(long srcAppId, long dstAppId) throws Exception;
    
    /**
	 * 获取所有的应用模板
	 * @return
	 * @throws Exception 
	 */
	BODeployAppTemplateBean[] getAllAppTemplate() throws Exception;
	
	BODeployAppTemplateBean getAppTemplateById(long templateId) throws Exception;
    
    /**
	 * 删除模板
	 * @param templateId
	 * @throws Exception
	 */
	void deleteAppTemplate(long templateId) throws Exception;
	
	/**
	 * 保存模板
	 * @throws Exception
	 */
	void saveAppTemplate(IBODeployAppTemplateValue value) throws Exception;
	
	/**
	 * 删除参数
	 * @param bean
	 * @throws Exception
	 */
    void delete(BODeployAppParamsBean bean) throws Exception;
}
