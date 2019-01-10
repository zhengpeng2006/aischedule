package com.asiainfo.monitor.tools.util.transform;

import org.apache.commons.lang.StringUtils;

import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class WKPaymentChartTransformImpl extends DefaultChartTransformImpl {
	
	public WKPaymentChartTransformImpl() {
	}

	/**
	 * 缴费工单
	 */
	protected String getName(String infoName, String infoValue) {
		// NAME:郑州缴费工单|WK_PAYMENT:4^F^346
		StringBuffer sb = new StringBuffer();
		sb.append(infoName + "_");
		String value=this.getValueFromInfo(infoValue, this.getShowValuePos());
		String flag1=StringUtils.split(value,"^")[0];
		String flag2=StringUtils.split(value,"^")[1];
		if (flag1.equals("1")) {
			// 缴费
			sb.append(AIMonLocaleFactory.getResource("MOS0000264"));
		} else if (flag1.equals("2")) {
			// 销帐反销
			sb.append(AIMonLocaleFactory.getResource("MOS0000265"));
		} else if (flag1.equals("3")) {
			// 虚拟划拨
			sb.append(AIMonLocaleFactory.getResource("MOS0000266"));
		} else if (flag1.equals("4")) {
			// 实时停开机
			sb.append(AIMonLocaleFactory.getResource("MOS0000267"));
		} else if (flag1.equals("5")) {
			// 神州行有效期计算
			sb.append(AIMonLocaleFactory.getResource("MOS0000268"));
		}

		sb.append("_");

		if (flag2.equals("F")) {
			// 错误
			sb.append(AIMonLocaleFactory.getResource("MOS0000269"));
		} else if (flag2.equals("1")) {
			// 处理中
			sb.append(AIMonLocaleFactory.getResource("MOS0000261"));
		} else if (flag2.equals("0")) {
			// 虚拟划拨
			sb.append(AIMonLocaleFactory.getResource("MOS0000270"));
		}

		return sb.toString();
	}

	protected String getValue(String infoName, String infoValue, String xvalue) {
		StringBuffer sb = new StringBuffer();
		sb.append("<node name=\"").append(this.getName(infoName, infoValue)).append("\" time=\"")
				.append(xvalue).append("\" value=\"");
		sb.append(this.getValueFromInfo(infoValue, this.getShowValuePos()));
		sb.append("\" />");
		return sb.toString();
	}
}
