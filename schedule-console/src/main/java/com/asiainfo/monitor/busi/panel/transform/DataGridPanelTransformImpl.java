package com.asiainfo.monitor.busi.panel.transform;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.monitor.tools.common.interfaces.ITaskRtn;
import com.asiainfo.monitor.tools.exe.task.model.TaskRtnModel;
import com.asiainfo.monitor.tools.util.TypeConst;

public class DataGridPanelTransformImpl extends DefaultPanelTransform {
	
	private static transient Log log = LogFactory.getLog(DataGridPanelTransformImpl.class);
	
	@Override
	public String transform(TaskRtnModel obj) throws Exception {
		if (obj == null)
			return null;
		List rtnList=obj.getRtns();
		StringBuilder sb=new StringBuilder("");
		if (rtnList!=null && rtnList.size()>0){
			ITaskRtn rtn;
			for (int i=0;i<rtnList.size();i++) {
				rtn=(ITaskRtn)rtnList.get(i);
				if (rtn!=null){
					String msg=rtn.getMsg();
					if (StringUtils.isNotBlank(getValueFromInfo(msg,1))){
						String[] colStr = StringUtils.split(msg,TypeConst._SPLIT_CHAR);
						if (i == 0) {
							sb.append("<row>");
							for (int j=0;j<colStr.length;j++) {
								if (StringUtils.isNotBlank(colStr[j])) {
									sb.append("<col"+j+">").append(getNameFromInfo(colStr[j])).append("</col"+j+">");
								}
							}
							sb.append("</row>");
						}
						sb.append("<row>");
						for (int j=0;j<colStr.length;j++) {
							if (StringUtils.isNotBlank(colStr[j])) {
								sb.append("<col"+j+">").append(getValueFromInfo(colStr[j])).append("</col"+j+">");
							}
						}
						sb.append("</row>");
					}
				}
			}
		}
		if (log.isDebugEnabled()){
			log.debug("transformed:"+sb.toString());
		}
		return sb.toString();
	}
	
}
