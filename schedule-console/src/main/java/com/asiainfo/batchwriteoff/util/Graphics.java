/**
 * 
 */
package com.asiainfo.batchwriteoff.util;

import java.io.Serializable;
import java.util.ArrayList;

/**   
 * @Copyright Copyright (c) 2012 Asiainfo-Linkage
 * 
 * @ClassName com.asiainfo.crm.ams.balance.batchwriteoff.util.Graphics
 * @Description 图形对象数据定义
 *
 * @version v1.0.0
 * @author yandong2
 * @date 2012-9-14 下午07:49:44
 * <br>
 * Modification History:<br>
 * Date         Author          Version            Description<br>
 * ---------------------------------------------------------*<br>
 * 2012-9-14     yandong2           v1.0.0               淇敼鍘熷洜<br>
 */
public class Graphics implements Serializable {
	
	private int left;//距左边距离
	
	private int top;//距上边距离
	
	private int width;//宽度
	
	private int height;//高度
	
	private String name; //结点名称
	
	private int levelId;//层级
	
	private String isDtl="N";//默认为没有明细信息
	
	private String url;//明细页面url
	
	private String parentName;//父节点名称
	
	private String value;//结点ID
	
	private ArrayList<Graphics> children=null;//当前结点后面的结点集合
	
	private ArrayList<Graphics> parents=null;//当前结点前面的结点集合


	/**
	 * @return the parentName
	 */
	public String getParentName() {
		return parentName;
	}

	/**
	 * @param parentName the parentName to set
	 */
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	/**
	 * @return the top
	 */
	public int getTop() {
		return top;
	}

	/**
	 * @param top the top to set
	 */
	public void setTop(int top) {
		this.top = top;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	 
	/**
	 * 
	 */
	public Graphics() {
		 
	}

	/**
	 * @return the left
	 */
	public int getLeft() {
		return left;
	}

	/**
	 * @param left the left to set
	 */
	public void setLeft(int left) {
		this.left = left;
	}

	 
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the children
	 */
	public ArrayList<Graphics> getChildren() {
		return children;
	}

	/**
	 * @param child the child to set
	 */
	public void addChild(Graphics child) {
		if(this.children==null){
			this.children=new ArrayList<Graphics>();
		}
		this.children.add(child);
	}

	/**
	 * @return the parents
	 */
	public ArrayList<Graphics> getParents() {
		return parents;
	}

	/**
	 * @param parent the parent to set
	 */
	public void addParent(Graphics parent) {
		if(this.parents==null){
			this.parents=new ArrayList<Graphics>();
		}
		this.parents.add(parent);
	}

	/**
	 * @return the levelId
	 */
	public int getLevelId() {
		return levelId;
	}

	/**
	 * @param levelId the levelId to set
	 */
	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}
    
	
	public String getIsDtl() {
		return isDtl;
	}

	public void setIsDtl(String isDtl) {
		this.isDtl = isDtl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	

}
