package com.asiainfo.monitor.tools.model;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.asiainfo.monitor.tools.common.interfaces.IChildNode;
import com.asiainfo.monitor.tools.common.interfaces.IEnable;

public class TaskFilterModel extends AbstractEnable implements IChildNode,IEnable,Serializable{

	private long filter_Id;
	
	private String name;

	private String expr1;
	
	private String expr2;
	
	private String expr3;
	
	private String expr4;
	
	private String expr5;

	private long taskId;
	
	public long getFilter_Id() {
		return filter_Id;
	}

	public void setFilter_Id(long filterId) {
		filter_Id = filterId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExpr1() {
		return expr1;
	}

	public void setExpr1(String expr1) {
		this.expr1 = expr1;
	}

	public String getExpr2() {
		return expr2;
	}

	public void setExpr2(String expr2) {
		this.expr2 = expr2;
	}

	public String getExpr3() {
		return expr3;
	}

	public void setExpr3(String expr3) {
		this.expr3 = expr3;
	}

	public String getExpr4() {
		return expr4;
	}

	public void setExpr4(String expr4) {
		this.expr4 = expr4;
	}

	public String getExpr5() {
		return expr5;
	}

	public void setExpr5(String expr5) {
		this.expr5 = expr5;
	}
	
	public String getExpr() {
		StringBuffer exprSb=new StringBuffer(expr1);
		if (StringUtils.isNotBlank(expr2)){
			exprSb.append("\n");
			exprSb.append(expr2);
		}
		if (StringUtils.isNotBlank(expr3)){
			exprSb.append("\n");
			exprSb.append(expr3);
		}
		if (StringUtils.isNotBlank(expr4)){
			exprSb.append("\n");
			exprSb.append(expr4);
		}
		if (StringUtils.isNotBlank(expr5)){
			exprSb.append("\n");
			exprSb.append(expr5);
		}
		return exprSb.toString();
	}
	
	public long getParentId(){
		return this.taskId;
	}
	
	public void setParentId(long taskId){
		this.taskId=taskId;
	}
	
	public long getSelfId(){
		return this.filter_Id;
	}
	
	public void setSelfId(long selfId){
		this.filter_Id=selfId;
	}
}
