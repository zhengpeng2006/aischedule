package com.asiainfo.monitor.interapi.api.trace;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.asiainfo.monitor.tools.util.TypeConst;



public class JdbcTrace extends DefaultTrace implements ITrace {
	
	private String username = null;

	private String sql = null;

	private String parameter;

	private String picture;
	
	public JdbcTrace() {
		super();
	}

	public void addChild(ITrace objITrace) {		
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getParameter() {
		return parameter;
	}

	public boolean isNode(){
		return false;
	}
	
	
	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	/**
	 * 构建Jdbc的树结构
	 * @param list
	 */
	public void buildTree(List list){
		
		Map jdbcMap=new HashMap();
		list.add(jdbcMap);
		jdbcMap.put(TypeConst.TREE_ID,this.getId());
		String time=this.getCreateTime();
		String hhmmss=time.substring(time.lastIndexOf(" ")+1);
		jdbcMap.put(TypeConst.TREE_NAME,"Jdbc["+hhmmss+"]");
		jdbcMap.put(TypeConst.TREE_TYPE, "Jdbc");
		jdbcMap.put(TypeConst.TREE_ICON,"JdbcIcon");
		jdbcMap.put(TypeConst.TREE_STATE,"0");
		jdbcMap.put(TypeConst.TREE_LEVEL,"0");
		jdbcMap.put(TypeConst.TREE_PARENT_ID,"-1");
		jdbcMap.put("TRACE_OBJECT",this);
		
	}
}
