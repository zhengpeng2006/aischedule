package com.asiainfo.monitor.tools.util.transform;

import org.apache.commons.lang.StringUtils;

import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class StopOpenChartTransformImpl extends DefaultChartTransformImpl {
	
	public StopOpenChartTransformImpl() {
	}

	/**
	 * 营业停开机
	 */
	protected String getName(String infoName, String infoValue) {
		StringBuffer sb = new StringBuffer();
		sb.append(infoName + "_");
		//NAME:**营业停开机|I_S_STOPOPEN:1^143
		String value=this.getValueFromInfo(infoValue, this.getShowValuePos());
		String flag=StringUtils.split(value,"^")[0];
		
		if (flag.equals("2")) {
			// 处理失败
			sb.append(AIMonLocaleFactory.getResource("MOS0000257"));
		} else if (flag.equals("1")) {
			// 处理中
			sb.append(AIMonLocaleFactory.getResource("MOS0000261"));
		} else if (flag.equals("0")) {
			// 积压
			sb.append(AIMonLocaleFactory.getResource("MOS0000259"));
		}
		return sb.toString();
	}

	protected String getValue(String infoName, String infoValue, String xvalue) {
		StringBuffer sb = new StringBuffer();
		sb.append("<node name=\"").append(this.getName(infoName, infoValue)).append("\" time=\"")
				.append(xvalue).append("\" value=\"");
		sb.append(this.getValueFromInfo(infoValue, 2));
		sb.append("\" />");
		return sb.toString();
	}
}
