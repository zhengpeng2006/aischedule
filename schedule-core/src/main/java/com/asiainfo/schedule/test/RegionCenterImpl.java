package com.asiainfo.schedule.test;

import com.ai.appframe2.complex.center.CenterInfo;
import com.ai.appframe2.complex.center.interfaces.ICenter;

public class RegionCenterImpl implements ICenter{

	@Override
	public CenterInfo getCenterByValue(String value) throws Exception {
		return new CenterInfo("2",value);
	}

}
