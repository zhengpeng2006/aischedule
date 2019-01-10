package com.asiainfo.monitor.interapi.api.trace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.asiainfo.monitor.tools.util.TypeConst;

public class SvrTrace extends DefaultTrace implements ITrace {
	
	private String className = null;

	private String methodName = null;

	private String appIp = null;

	private String appServerName = null;

	private String center = null;


	private List parameter = new ArrayList();

	private String picture;
	

	private List child = new ArrayList();

	
	public SvrTrace() {
	}

	
	public String getAppIp() {
		return appIp;
	}

	public String getAppServerName() {
		return appServerName;
	}

	public String getCenter() {
		return center;
	}

	public String getClassName() {
		return className;
	}
	
	public void setAppIp(String appIp) {
		this.appIp = appIp;
	}

	public void setAppServerName(String appServerName) {
		this.appServerName = appServerName;
	}

	public void setCenter(String center) {
		this.center = center;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}


	public String getPicture(){
		return this.picture;
	}

	
	public void setPicture(String picture) {
		this.picture = picture;
	}

	public void addParameter(Object in){
		this.parameter.add(in);
	}
	/**
	 *
	 * @param objITrace ITrace
	 */
	public void addChild(ITrace objITrace) {
		child.add(objITrace);
	}

	public boolean isNode(){
		return child!=null && child.size()>0;
	}
	
	public void buildTree(List list){
		StringBuilder sb=new StringBuilder("");
		if (parameter!=null && parameter.size()>0){
			
			for (int i=0;i<parameter.size();i++){
				sb.append(((SimpleParam)parameter.get(i)).getPicture());
			}			
		}
		this.setPicture(sb.toString());
		
		Map svrMap=new HashMap();
		list.add(svrMap);
		svrMap.put(TypeConst.TREE_ID,this.getId());
		String classId=className.substring(className.lastIndexOf(".")+1);
		String time=this.getCreateTime();
		String hhmmss=time.substring(time.lastIndexOf(" ")+1);
		svrMap.put(TypeConst.TREE_NAME,"Svr["+classId+"]["+hhmmss+"]");
		svrMap.put(TypeConst.TREE_TYPE, this.getType());
		svrMap.put(TypeConst.TREE_ICON,"svrIcon");
		svrMap.put(TypeConst.TREE_STATE,"0");
		svrMap.put(TypeConst.TREE_LEVEL,"0");
		svrMap.put(TypeConst.TREE_PARENT_ID,"-1");
		svrMap.put("TRACE_OBJECT",this);
		
		
		if (isNode()){
			List childList=new ArrayList(child.size());
			svrMap.put(TypeConst.TREE_CHILDREN,childList);
			for (int i=0;i<child.size();i++){
				((ITrace)child.get(i)).buildTree(childList);				
			}
		}
		
	}
}
