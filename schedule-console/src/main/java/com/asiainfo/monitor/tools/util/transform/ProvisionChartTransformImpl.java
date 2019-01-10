package com.asiainfo.monitor.tools.util.transform;

import org.apache.commons.lang.StringUtils;

import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class ProvisionChartTransformImpl extends DefaultChartTransformImpl {
	public ProvisionChartTransformImpl() {
	}

	
	protected String getName(String infoName, String infoValue) {

		StringBuffer sb = new StringBuffer();
		sb.append(infoName + "_");
		// NAME:开通工单(HLR)|PS_PROVISION:2^6
		String value=this.getValueFromInfo(infoValue, this.getShowValuePos());
		String flag=StringUtils.split(value,"^")[0];
		
		if (flag.equals("2")) { // 积压
			sb.append(AIMonLocaleFactory.getResource("MOS0000259"));
		} else if (flag.equals("1")) { // 未知
			sb.append(AIMonLocaleFactory.getResource("MOS0000260"));
		}
		return sb.toString();
	}

	protected String getValue(String infoName, String infoValue, String xvalue) {
		StringBuffer sb = new StringBuffer();
		sb.append("<node name=\"").append(this.getName(infoName, infoValue)).append("\" time=\"")
				.append(xvalue).append("\" value=\"");
		sb.append(this.getValueFromInfo(infoValue, this.getShowValuePos()));
		sb.append("\"/>");
		return sb.toString();
	}
}
