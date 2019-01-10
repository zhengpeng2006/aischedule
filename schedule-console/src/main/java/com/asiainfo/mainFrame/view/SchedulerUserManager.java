package com.asiainfo.mainFrame.view;

import com.ai.appframe2.privilege.UserInfoInterface;
import com.ai.frame.loginmgr.AbstractUserManagerImpl;
import com.ailk.appengine.privilege.user.AECenterUserInfoImpl;

/**
 * 调度平台用户管理
 * @author 孙德东(24204)
 */
public class SchedulerUserManager extends AbstractUserManagerImpl{

	@Override
	public UserInfoInterface getBlankUserInfo() {
		return new AECenterUserInfoImpl();
	}

}
