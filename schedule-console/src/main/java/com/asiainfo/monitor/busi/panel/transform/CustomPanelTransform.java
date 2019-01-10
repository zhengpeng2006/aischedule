package com.asiainfo.monitor.busi.panel.transform;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.monitor.tools.common.interfaces.ITaskRtn;
import com.asiainfo.monitor.tools.exe.task.model.TaskRtnModel;

public class CustomPanelTransform extends DefaultPanelTransform {

	private static transient Log log=LogFactory.getLog(CustomPanelTransform.class);
	
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
//					if (StringUtils.isNotBlank(getValueFromInfo(msg,1))){
//						if (obj.getType().equalsIgnoreCase(ICmdType.SHELL))
//							result.append("<node idcode=\""+rtn.getCode()+"\" name=\""+getValueFromInfo(msg,0)+"["+rtn.getCode()+"]"+"\" value=\""+getValueFromInfo(msg,1)+"\" time=\""+rtn.getTime()+"\"/>");
//						else
//							result.append("<node idcode=\""+rtn.getCode()+"\" name=\""+getValueFromInfo(msg,0)+"\" value=\""+getValueFromInfo(msg,1)+"\" time=\""+rtn.getTime()+"\"/>");
//					}
					String[] tmpstr = StringUtils.split(msg, '|');
					for (int j = 1; j < tmpstr.length; j++) {
						String tmp = tmpstr[j];
						result.append("<node value=\""+getValueFromInfo(tmp)+"\" time=\""+getNameFromInfo(tmp)+"\"/>");
					}
					
				}
			}			
		}
		if (log.isDebugEnabled()){
			log.debug("transformed:"+result);
		}
		return result.toString();
	}

	
	public static void main(String[] args) {
		CustomPanelTransform ct = new CustomPanelTransform();
		StringBuffer result=new StringBuffer();
		String msg = "地区:嘉兴|订购增值策划:7|业务变更:21";
		String[] tmpstr = StringUtils.split(msg, '|');
		for (int j = 1; j < tmpstr.length; j++) {
			String tmp = tmpstr[j];
			result.append("<node value=\""+ct.getValueFromInfo(tmp)+"\" time=\""+ct.getNameFromInfo(tmp)+"\"/>");
		}
		
		System.out.println(result.toString());
	}
	
}
