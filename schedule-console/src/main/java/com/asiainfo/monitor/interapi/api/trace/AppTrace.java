package com.asiainfo.monitor.interapi.api.trace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.asiainfo.monitor.tools.util.TypeConst;


public class AppTrace extends DefaultTrace implements ITrace {
	
	private List child = new ArrayList();
	
	private String finishTime;
	
	private String msg;
	
	public AppTrace() {
		super();
	}
	
	public String getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}

	/**
	 *
	 * @param objITrace ITrace
	 */
	public void addChild(ITrace objITrace) {
		child.add(objITrace);
	}

	
	public List getChild() {
		return child;
	}

	public boolean isNode(){
		return child!=null && child.size()>0;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void buildTree(List list){
		Map rootMap=new HashMap();
		list.add(rootMap);
		rootMap.put(TypeConst.TREE_ID,this.getId());
		rootMap.put(TypeConst.TREE_NAME,"App["+this.getCreateTime()+"]");
		rootMap.put(TypeConst.TREE_TYPE, this.getType());
		rootMap.put(TypeConst.TREE_STATE,"0");
		rootMap.put(TypeConst.TREE_LEVEL,"0");
		rootMap.put(TypeConst.TREE_ICON,"appIcon");
		rootMap.put(TypeConst.TREE_PARENT_ID,"-1");
		rootMap.put(TypeConst.TREE_STATE, msg);
		rootMap.put("TRACE_OBJECT",this);
		
		if (isNode()){
			List childList=new ArrayList(child.size());
			rootMap.put(TypeConst.TREE_CHILDREN,childList);
			for (int i=0;i<child.size();i++){
				((ITrace)child.get(i)).buildTree(childList);				
			}
		}
	}
}
