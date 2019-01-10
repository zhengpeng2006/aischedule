package com.asiainfo.monitor.busi.exe.task.javacommand;


import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ai.appframe2.complex.mbean.standard.datasource.DataSourceRuntime;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAPITaskSV;
import com.asiainfo.monitor.tools.common.KeyName;
import com.asiainfo.monitor.tools.common.interfaces.IJavaCommand;
import com.asiainfo.monitor.tools.util.TypeConst;

/**
 * 后台定时任务\面板任务,数据源
 * @author Guocx
 *
 */
public class DataSourceJCommand implements IJavaCommand {

	public DataSourceJCommand() {
		super();
		// TODO 自动生成构造函数存根
	}

	public String execute(KeyName[] parameter) throws Exception {
		String result="";
		if (parameter==null || parameter.length<1)
			return result;
		String appId="";
		for (int i=0;i<parameter.length;i++){
			if (parameter[i].getName().equals(TypeConst.SERVER+TypeConst._ID)){
				appId=parameter[i].getKey();
				break;
			}
		}
		if (StringUtils.isBlank(appId))
			appId=parameter[0].getKey();
		
		if (StringUtils.isNotBlank(appId)){
			IAPITaskSV apiTaskSV=(IAPITaskSV)ServiceFactory.getService(IAPITaskSV.class);
			List info=apiTaskSV.getDataSourceRuntime(appId);
			if (info!=null && info.size()>0){
				StringBuilder sb=new StringBuilder("");
				for (int i=0;i<info.size();i++){
					DataSourceRuntime itemDsRun=(DataSourceRuntime)info.get(i);
					if (itemDsRun!=null){
						if (i>0)
							sb.append(TypeConst._NEWLINE_CHAR);
						sb.append("NAME:");
						sb.append(itemDsRun.getDataSource());
						sb.append(TypeConst._SPLIT_CHAR);
						sb.append("NUMIDLE:");
						sb.append(itemDsRun.getNumIdle());
						sb.append(TypeConst._SPLIT_CHAR);
						sb.append("NUMACTIVE:");
						sb.append(itemDsRun.getNumActive());
					}
				}
				result=sb.toString();
			}
		}
				
		return result;
	}

}
