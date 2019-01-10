package com.asiainfo.monitor.tools.util.transform;


public class VMChartTransformImpl extends DefaultChartTransformImpl {
	public VMChartTransformImpl() {
	}

	protected String getName(String infoName, String infoValue) {
		/*
		StringBuffer sb = new StringBuffer();
		sb.append(infoName + "_");

		if (isContentItem("W", infoValue, 1)) {
			// 等待数量
			sb.append(AIMonLocaleFactory.getResource("MOS0000262"));
		} else if (isContentItem("X", infoValue, 1)) {
			// 错误数量
			sb.append(AIMonLocaleFactory.getResource("MOS0000263"));
		}

		return sb.toString();
		*/
		return infoName;
	}

	protected String getValue(String infoName, String infoValue, String xvalue) {
		StringBuffer sb = new StringBuffer();
		sb.append("<node name=\"").append(infoName).append("\" time=\"")
				.append(xvalue).append("\" value=\"");
		sb.append(this.getValueFromInfo(infoValue, this.getShowValuePos()));
		sb.append("\" />");
		return sb.toString();
	}
}
