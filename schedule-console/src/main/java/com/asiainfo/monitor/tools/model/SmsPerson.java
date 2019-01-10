package com.asiainfo.monitor.tools.model;

import java.io.Serializable;

import com.asiainfo.monitor.tools.model.interfaces.ISmsPersion;

public class SmsPerson implements ISmsPersion,Serializable{
	
	private String person_Id;
	
	private String person_Name;
	
	private String phone_Num;
	
	private String region_Id;

	private int warn_Level;
	
	public String getPerson_Id() {
		return person_Id;
	}

	public void setPerson_Id(String personId) {
		person_Id = personId;
	}

	public String getPerson_Name() {
		return person_Name;
	}

	public void setPerson_Name(String personName) {
		person_Name = personName;
	}

	public String getPhone_Num() {
		return phone_Num;
	}

	public void setPhone_Num(String phoneNum) {
		phone_Num = phoneNum;
	}

	public String getRegion_Id() {
		return region_Id;
	}

	public void setRegion_Id(String regionId) {
		region_Id = regionId;
	}

	public int getWarn_Level() {
		return warn_Level;
	}

	public void setWarn_Level(int warnLevel) {
		warn_Level = warnLevel;
	} 
	
}
