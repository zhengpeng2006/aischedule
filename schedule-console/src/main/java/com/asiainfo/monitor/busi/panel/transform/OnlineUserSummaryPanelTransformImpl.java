package com.asiainfo.monitor.busi.panel.transform;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.monitor.tools.common.interfaces.ITaskRtn;
import com.asiainfo.monitor.tools.exe.task.model.TaskRtnModel;

public class OnlineUserSummaryPanelTransformImpl extends DefaultPanelTransform implements IPanelTransform {

	private final static transient Log log=LogFactory.getLog(OnlineUserSummaryPanelTransformImpl.class);
	
	@Override
	public String transform(TaskRtnModel obj) throws Exception {
		if (obj==null)
			return null;
		List rtnList=obj.getRtns();
		StringBuffer result=new StringBuffer("");
		
		if (rtnList!=null && rtnList.size()>0){
			
			Map summaryMap=new HashMap();
			
			for (int i=0;i<rtnList.size();i++){
				ITaskRtn rtn=(ITaskRtn)rtnList.get(i);
				if (rtn!=null){
					String msg=rtn.getMsg();
					String regionName=getValueFromInfo(msg,0);
					if (summaryMap.containsKey(regionName)){
						Summary summary=(Summary)summaryMap.get(regionName);
						summary.col1=summary.col1+Integer.parseInt(getValueFromInfo(msg,1));
						summary.col2=summary.col2+Integer.parseInt(getValueFromInfo(msg,2));						
					}else{
						Summary summary=new Summary();
						summary.col1=Integer.parseInt(getValueFromInfo(msg,1));
						summary.col2=Integer.parseInt(getValueFromInfo(msg,2));
						summaryMap.put(regionName, summary);
					}					
				}
			}
			result.append("<row><col0>地区</col0><col1>在线用户数</col1><col2>终端数</col2></row>");
			//开始画表格
			Set entries = summaryMap.entrySet();
			if (entries!=null) {
				Iterator iterator = entries.iterator();
				
				while (iterator.hasNext()) {
					result.append("<row>");
					Map.Entry entry = (Map.Entry)iterator.next();
					Object key = entry.getKey();
					result.append("<col0>");
					result.append(key);
					result.append("</col0>");
					Summary value = new Summary();
					value =	(Summary)entry.getValue();
					result.append("<col1>");
					result.append(value.col1);
					result.append("</col1>");
					result.append("<col2>");
					result.append(value.col2);
					result.append("</col2>");
					result.append("</row>");
				}
			}
		}
		if (log.isDebugEnabled()){
			log.debug("transformed:"+result);
		}
		return result.toString();
	}
	
	public class Summary{
		public int col1=0;
		public int col2=0;
	}
}
