package com.asiainfo.monitor.busi.panel.transform;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.monitor.tools.common.interfaces.ITaskRtn;
import com.asiainfo.monitor.tools.exe.task.model.TaskRtnModel;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class OnlineUserPanelTransformImpl extends DefaultPanelTransform implements IPanelTransform {

	private final static transient Log log=LogFactory.getLog(OnlineUserPanelTransformImpl.class);
	
	public OnlineUserPanelTransformImpl() {
		super();
		// TODO 自动生成构造函数存根
	}

	public String transform(TaskRtnModel obj) throws Exception {
		if (obj==null)
			return null;
		List rtnList=obj.getRtns();
		StringBuffer result=new StringBuffer("");
		
		if (rtnList!=null && rtnList.size()>0){
			for (int i=0;i<rtnList.size();i++){
				ITaskRtn rtn=(ITaskRtn)rtnList.get(i);			
				if (rtn!=null){
					String msg=rtn.getMsg();
					if (StringUtils.isNotBlank(getValueFromInfo(msg,1))){
						// 在线数(位)
						result.append("<node idcode=\""+rtn.getCode()+"\" name=\"").append(AIMonLocaleFactory.getResource("MOS0000307")).append("\" value=\""+getValueFromInfo(msg,1)+"\" time=\""+rtn.getTime()+"\"/>");
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
