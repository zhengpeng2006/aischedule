package com.asiainfo.monitor.interapi.api.trace;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.asiainfo.monitor.tools.util.TypeConst;


/**
 * 简单参数对象
 * @author Guocx
 *
 */
public class SimpleParam implements Serializable {

	private String name;
	
	private String type = null;

	private String value = null;

	private String index = "";

	//描述
	private String picture;
	
	public SimpleParam(){
		
	}
	
	public SimpleParam(String type, String value) {
		this.type = type;
		this.value = value;
	}

	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	public void buildTree(List list){
		Map paraMap=new HashMap();
		list.add(paraMap);
		paraMap.put(TypeConst.TREE_ID,this.getIndex());
		paraMap.put(TypeConst.TREE_NAME,"SimpleParam");
		paraMap.put(TypeConst.TREE_ICON,"simpleIcon");
		paraMap.put(TypeConst.TREE_STATE,"0");
		paraMap.put(TypeConst.TREE_LEVEL,"0");
		paraMap.put(TypeConst.TREE_PARENT_ID,"-1");
		paraMap.put("TRACE_OBJECT",this);
	}
}
