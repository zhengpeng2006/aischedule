package com.asiainfo.deploy.dao.impl;

import com.asiainfo.deploy.common.bo.BODeployAppParamsBean;
import com.asiainfo.deploy.common.bo.BODeployAppParamsEngine;
import com.asiainfo.deploy.dao.interfaces.IAppParamDAO;

/**
 * 应用的实例化参数的数据库接口
 * @author 孙德东(24204)
 */
public class AppParamDAOImpl implements IAppParamDAO{

	@Override
	public BODeployAppParamsBean[] getInstancedPramsByAppId(long applicationId) throws Exception {
		BODeployAppParamsBean condition = new BODeployAppParamsBean();
		condition.setApplicationId(applicationId);
		
		return BODeployAppParamsEngine.getBeans(condition);
	}

	@Override
	public void batchSave(BODeployAppParamsBean[] beans) throws Exception {
		
		for (BODeployAppParamsBean bean : beans) {
            if(bean.isNew() && bean.getApplicationParamId() <= 0) {
				bean.setApplicationParamId(getBeanId());
			}
		}
		
		BODeployAppParamsEngine.saveBatch(beans);
	}

	@Override
	public long getBeanId() throws Exception
    {
        return BODeployAppParamsEngine.getNewId().longValue();
    }
	
	@Override
	public void save(BODeployAppParamsBean bean) throws Exception {
        if(bean.isNew() && bean.getApplicationParamId() <= 0) {
			bean.setApplicationParamId(getBeanId());
		}
		
		BODeployAppParamsEngine.save(bean);
	}

    @Override
    public BODeployAppParamsBean getAppParam(long applicationParamId) throws Exception
    {
        return BODeployAppParamsEngine.getBean(applicationParamId);
    }

    @Override
    public void deleteAppParam(BODeployAppParamsBean bean) throws Exception
    {
        if(bean != null) {
            bean.delete();
            BODeployAppParamsEngine.save(bean);
        }
    }

    
}
