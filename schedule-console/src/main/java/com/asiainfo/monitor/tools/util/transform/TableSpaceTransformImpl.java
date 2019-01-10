package com.asiainfo.monitor.tools.util.transform;

public class TableSpaceTransformImpl extends DefaultChartTransformImpl {

	public TableSpaceTransformImpl() {
		super();
		// TODO 自动生成构造函数存根
	}

	protected String getName(String infoName, String infoValue) {
		String label=this.getValueFromInfo(infoValue, 0);
		return label;
	}

	protected String getValue(String infoName, String infoValue, String time) {
		StringBuffer sb = new StringBuffer();
		sb.append("<node name=\"").append(getName(infoName,infoValue)).append("\"");
		sb.append(" time=\"").append(time).append("\"");
		sb.append(" value=\"").append(this.getValueFromInfo(infoValue, 4)).append("\"");
		sb.append(" />");
		System.out.println(sb.toString());
		return sb.toString();
	}
}
