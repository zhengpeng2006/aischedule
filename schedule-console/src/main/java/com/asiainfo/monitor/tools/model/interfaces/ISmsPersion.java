package com.asiainfo.monitor.tools.model.interfaces;

public interface ISmsPersion {

	public String getPerson_Id();

	public void setPerson_Id(String personId);

	public String getPerson_Name();

	public void setPerson_Name(String personName);

	public String getPhone_Num();

	public void setPhone_Num(String phoneNum);

	public String getRegion_Id();

	public void setRegion_Id(String regionId);
	
	public int getWarn_Level();

	public void setWarn_Level(int warnLevel);
}
