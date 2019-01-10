package com.asiainfo.monitor.interapi.api.trace;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.asiainfo.monitor.tools.util.TypeConst;

public abstract class DefaultTrace implements ITrace {

	//标识
	private String id;
	//名称
	private String name;
	//类型
	private String type;
	//线程标识
	private String threadId;
	//创建时间
	private String createTime;
	//用时
	private String useTime;	
	//用户工号
	private String code;
	//成功
	private String success;
	
	private String icon;
	
	public DefaultTrace() {
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getId() {
		return id;
	}


	public String getCreateTime() {
		return createTime;
	}

	public String getUseTime() {
		return useTime;
	}

	public void setUseTime(String useTime) {
		this.useTime = useTime;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public abstract void addChild(ITrace objITrace);
	
	public void buildTree(List list){
		Map map=new HashMap();
		map.put(TypeConst.TREE_ID,this.getId());
		String hhmmss=createTime.substring(createTime.lastIndexOf(" ")+1);
		map.put(TypeConst.TREE_NAME,this.getName()+"["+hhmmss+"]");
		map.put(TypeConst.TREE_TYPE, this.getType());
		map.put(TypeConst.TREE_ICON,this.getIcon());
		map.put(TypeConst.TREE_STATE,"0");
		map.put(TypeConst.TREE_LEVEL,"0");
		map.put(TypeConst.TREE_PARENT_ID,"-1");
		map.put("TRACE_OBJECT",this);
		list.add(map);
	}

	public String getThreadId() {
		return this.threadId;
	}

	public abstract boolean isNode();

	public void setCreateTime(String createTime) {
		this.createTime=createTime;
	}

	public void setId(String id) {
		this.id=id;
	}

	public void setThreadId(String threadId) {
		this.threadId=threadId;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}
}
