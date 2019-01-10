package com.asiainfo.deploy.dao.interfaces;

import com.asiainfo.deploy.common.bo.BODeployAppParamsBean;

/**
 * 应用的实例化参数的数据库接口
 * @author 孙德东(24204)
 */
public interface IAppParamDAO {
	/**
	 * 查询应用实例化后的参数
	 * @param applicationId
	 * @return
	 * @throws Exception 
	 */
	BODeployAppParamsBean[] getInstancedPramsByAppId(long applicationId) throws Exception;
	void batchSave(BODeployAppParamsBean[] beans) throws Exception;
	void save(BODeployAppParamsBean bean) throws Exception;

    BODeployAppParamsBean getAppParam(long applicationParamId) throws Exception;

    void deleteAppParam(BODeployAppParamsBean bean) throws Exception;
    long getBeanId() throws Exception;
}
