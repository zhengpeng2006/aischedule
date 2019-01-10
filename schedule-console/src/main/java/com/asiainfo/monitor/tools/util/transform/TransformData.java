package com.asiainfo.monitor.tools.util.transform;

/**
 * 转化图形的数据对象
 * @author guocx
 *
 */
public class TransformData implements ITransformData{
	
	private String code;
	
	private String name;
	
	private String value;
	
	private String time;
	
	public TransformData(){		
	}

	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	
}
