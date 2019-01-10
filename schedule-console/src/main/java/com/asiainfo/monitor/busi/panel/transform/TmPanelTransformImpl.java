package com.asiainfo.monitor.busi.panel.transform;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.monitor.tools.common.interfaces.ITaskRtn;
import com.asiainfo.monitor.tools.exe.task.model.TaskRtnModel;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class TmPanelTransformImpl extends DefaultPanelTransform implements IPanelTransform {

	private static transient Log log=LogFactory.getLog(TmPanelTransformImpl.class);
	
	public TmPanelTransformImpl() {
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
					// 开始事务
					sb.append("<node idcode=\""+obj.getIdCode()+"\" name=\"").append(AIMonLocaleFactory.getResource("MOS0000295")).append("\" value=\""+getValueFromInfo(msg,1)+"\" time=\""+rtn.getTime()+"\"/>");
				}
				if (StringUtils.isNotBlank(getValueFromInfo(msg,2))){
					// 提交事务
					sb.append("<node idcode=\""+obj.getIdCode()+"\" name=\"").append(AIMonLocaleFactory.getResource("MOS0000296")).append("\" value=\""+getValueFromInfo(msg,2)+"\" time=\""+rtn.getTime()+"\"/>");
				}
				if (StringUtils.isNotBlank(getValueFromInfo(msg,3))){
					// 回滚事务
					sb.append("<node idcode=\""+obj.getIdCode()+"\" name=\"").append(AIMonLocaleFactory.getResource("MOS0000297")).append("\" value=\""+getValueFromInfo(msg,3)+"\" time=\""+rtn.getTime()+"\"/>");
				}
				if (StringUtils.isNotBlank(getValueFromInfo(msg,4))){
					// 挂起事务
					sb.append("<node idcode=\""+obj.getIdCode()+"\" name=\"").append(AIMonLocaleFactory.getResource("MOS0000298")).append("\" value=\""+getValueFromInfo(msg,4)+"\" time=\""+rtn.getTime()+"\"/>");
				}
				if (StringUtils.isNotBlank(getValueFromInfo(msg,5))){
					// 恢复事务
					sb.append("<node idcode=\""+obj.getIdCode()+"\" name=\"").append(AIMonLocaleFactory.getResource("MOS0000299")).append("\" value=\""+getValueFromInfo(msg,5)+"\" time=\""+rtn.getTime()+"\"/>");
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
