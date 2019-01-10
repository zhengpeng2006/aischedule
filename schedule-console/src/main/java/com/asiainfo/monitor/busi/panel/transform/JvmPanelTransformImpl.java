package com.asiainfo.monitor.busi.panel.transform;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.monitor.tools.common.interfaces.ITaskRtn;
import com.asiainfo.monitor.tools.exe.task.model.TaskRtnModel;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class JvmPanelTransformImpl extends DefaultPanelTransform implements IPanelTransform {

	private static transient Log log=LogFactory.getLog(JvmPanelTransformImpl.class);
	
	public JvmPanelTransformImpl() {
		super();
		// TODO 自动生成构造函数存根
	}

	public String transform(TaskRtnModel obj) throws Exception {
		if (obj==null)
			return null;
		List rtnList=obj.getRtns();
		String result="";
		if (rtnList!=null && rtnList.size()>0){
			ITaskRtn rtn=(ITaskRtn)rtnList.get(0);
			if (rtn!=null){
				String msg=rtn.getMsg();
				StringBuilder sb=new StringBuilder("");
				if (StringUtils.isNotBlank(getValueFromInfo(msg,1))){
					// 总内存(M)
					sb.append("<node idcode=\""+obj.getIdCode()+"\" name=\"").append(AIMonLocaleFactory.getResource("MOS0000293")).append("\" value=\""+getValueFromInfo(msg,1)+"\" time=\""+rtn.getTime()+"\"/>");
				}
				if (StringUtils.isNotBlank(getValueFromInfo(msg,2))){
					// 使用内存(M)
					sb.append("<node idcode=\""+obj.getIdCode()+"\" name=\"").append(AIMonLocaleFactory.getResource("MOS0000294")).append("\" value=\""+getValueFromInfo(msg,2)+"\" time=\""+rtn.getTime()+"\"/>");
				}
				result=sb.toString();
			}
		}
		if (log.isDebugEnabled()){
			log.debug("JVM transformed:"+result);
		}
		return result;
	}

}
