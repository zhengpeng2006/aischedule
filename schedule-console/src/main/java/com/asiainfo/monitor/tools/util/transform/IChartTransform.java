package com.asiainfo.monitor.tools.util.transform;

import java.util.List;

public interface IChartTransform {

	public void setShowNamePos(int showNamePos);
	
	public void setShowValuePos(int showValuePos);
	
	public List createChartData(ITransformData[] tdatas) throws Exception;
	
}
