package com.asiainfo.monitor.busi.panel.transform;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.monitor.tools.common.interfaces.ITaskRtn;
import com.asiainfo.monitor.tools.exe.task.model.TaskRtnModel;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class DataSourcePanelTransformImpl extends DefaultPanelTransform implements IPanelTransform {

	private static transient Log log=LogFactory.getLog(DataSourcePanelTransformImpl.class);
	
	public DataSourcePanelTransformImpl() {
		super();
		// TODO 自动生成构造函数存根
	}

	public String transform(TaskRtnModel obj) throws Exception {
		if (obj==null)
			return null;
		List rtnList=obj.getRtns();
		String result="";
		if (rtnList!=null && rtnList.size()>0){
			StringBuilder sb=new StringBuilder("");			
			ITaskRtn rtn=(ITaskRtn)rtnList.get(0);
			if (rtn!=null){				
				for (int i=0;i<rtnList.size();i++){					
					rtn=(ITaskRtn)rtnList.get(i);					
					if (rtn!=null){
						String msg=rtn.getMsg();
						String name=getValueFromInfo(msg,0);
						if (StringUtils.isNotBlank(getValueFromInfo(msg,1))){
							// 空闲数
							sb.append("<node idcode=\""+obj.getIdCode()+"\" name=\"").append(AIMonLocaleFactory.getResource("MOS0000291")).append("\" value=\""+getValueFromInfo(msg,1)+"\" time=\""+name+"\"/>");
						}
						if (StringUtils.isNotBlank(getValueFromInfo(msg,2))){
							// 活动数
							sb.append("<node idcode=\""+obj.getIdCode()+"\" name=\"").append(AIMonLocaleFactory.getResource("MOS0000292")).append("\" value=\""+getValueFromInfo(msg,2)+"\" time=\""+name+"\"/>");
						}						
					}
				}
				result=sb.toString();
			}
		}
		if (log.isDebugEnabled()){
			log.debug("transformed:"+result);
		}
		return result;
	}
}
