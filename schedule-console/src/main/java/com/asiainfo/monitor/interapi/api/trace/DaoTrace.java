package com.asiainfo.monitor.interapi.api.trace;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.asiainfo.monitor.tools.util.TypeConst;

public class DaoTrace extends DefaultTrace implements ITrace {
	
	private String className = null;

	private String methodName = null;

	private String center = null;

	private List child = new ArrayList();

	public DaoTrace() {
		super();
	}
	
	public String getCenter() {
		return center;
	}

	public String getClassName() {
		return className;
	}
	
	public String getMethodName() {
		return methodName;
	}

	public void setCenter(String center) {
		this.center = center;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public boolean isNode(){
		return child!=null && child.size()>0;
	}
	/**
	 *
	 * @param objITrace ITrace
	 */
	public void addChild(ITrace objITrace) {
		child.add(objITrace);
	}

	public void buildTree(List list){
		Map daoMap=new HashMap();
		list.add(daoMap);
		daoMap.put(TypeConst.TREE_ID,this.getId());
		String classId=className.substring(className.lastIndexOf(".")+1);
		String time=this.getCreateTime();
		String hhmmss=time.substring(time.lastIndexOf(" ")+1);
		daoMap.put(TypeConst.TREE_NAME,"Dao["+classId+"]["+hhmmss+"]");
		daoMap.put(TypeConst.TREE_TYPE, this.getType());
		daoMap.put(TypeConst.TREE_ICON,"daoIcon");
		daoMap.put(TypeConst.TREE_STATE,"0");
		daoMap.put(TypeConst.TREE_LEVEL,"0");
		daoMap.put(TypeConst.TREE_PARENT_ID,"-1");
		daoMap.put("TRACE_OBJECT",this);
		
		if (isNode()){
			List childList=new ArrayList(child.size());
			daoMap.put(TypeConst.TREE_CHILDREN,childList);
			for (int i=0;i<child.size();i++){
				((ITrace)child.get(i)).buildTree(childList);				
			}
		}
	}
}
