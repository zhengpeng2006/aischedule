package com.asiainfo.monitor.busi.panel.transform;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.monitor.tools.common.interfaces.ITaskRtn;
import com.asiainfo.monitor.tools.exe.task.model.TaskRtnModel;
import com.asiainfo.monitor.tools.model.interfaces.ICmdType;

public class CommPanelTransform extends DefaultPanelTransform {

	private static transient Log log=LogFactory.getLog(CommPanelTransform.class);
	
	@Override
	public String transform(TaskRtnModel obj) throws Exception {
		if (obj==null)
			return null;
		List rtnList=obj.getRtns();
		StringBuffer result=new StringBuffer();
		if (rtnList!=null && rtnList.size()>0){
			for (int i=0;i<rtnList.size();i++){
				ITaskRtn rtn=(ITaskRtn)rtnList.get(i);
				if (rtn!=null){
					String msg=rtn.getMsg();
					if (StringUtils.isNotBlank(getValueFromInfo(msg,1))){
						if (obj.getType().equalsIgnoreCase(ICmdType.SHELL))
							result.append("<node idcode=\""+rtn.getCode()+"\" name=\""+getValueFromInfo(msg,0)+"["+rtn.getCode()+"]"+"\" value=\""+getValueFromInfo(msg,1)+"\" time=\""+rtn.getTime()+"\"/>");
						else
							result.append("<node idcode=\""+rtn.getCode()+"\" name=\""+getValueFromInfo(msg,0)+"\" value=\""+getValueFromInfo(msg,1)+"\" time=\""+rtn.getTime()+"\"/>");
					}
				}
			}			
		}
		if (log.isDebugEnabled()){
			log.debug("transformed:"+result);
		}
		return result.toString();
	}

}
