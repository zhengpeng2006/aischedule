package com.asiainfo.monitor.busi.cache.interfaces;

public interface IPanel {

	public String getExec_Method();

	public String getLayer();

	public String getPanel_Desc();

	public String getThreshold_Id();

	public String getObj_Id();

	public String getObj_Type();

	public String getTime_Id();

	public void setTime_Id(String time_Id);

	public String getPanel_Id();

	public String getTemp_Type();

	public String getPanel_Name();

	public String getView_strategy();

	public void setView_strategy(String viewStrategy);

	public String getView_transform();

	public void setView_transform(String viewTransform);

	public String getView_Type();

	public String getState();

	public String getRemarks();

	public String getContrail();
}
