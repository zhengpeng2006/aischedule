package com.asiainfo.monitor.tools.util.transform;


public class HostChartTransformImpl extends DefaultChartTransformImpl {

	protected String getName(String infoName, String infoValue) {
	    /*
		StringBuffer sb = new StringBuffer();
	    sb.append(infoName+"_");
	    
	    if(isContentItem("MEM_USED_PERCENT", infoValue,1)){
	    	// 内存使用百分比
	    	sb.append(AIMonLocaleFactory.getResource("MOS0000255"));
	    }else if(isContentItem("CPU_IDLE_PERCENT", infoValue,1)){
	    	// CPU使用百分比
	    	sb.append(AIMonLocaleFactory.getResource("MOS0000256"));
	    }
	    return sb.toString();
	    */
		return infoName;
	  }

	protected String getValue(String infoName, String infoValue,String xvalue) {
	    StringBuffer sb = new StringBuffer();
	    sb.append("<node name=\"").append(infoName).append("\" time=\"").append(xvalue).append("\" value=\"");
	    if(isContentItem("MEM_USED_PERCENT", infoValue,1)){
	    	sb.append(this.getValueFromInfo(infoValue,this.getShowValuePos()));
	    	sb.append("\"");
	    }else if(isContentItem("CPU_USED_PERCENT", infoValue,1)){
	    	sb.append(this.getValueFromInfo(infoValue,this.getShowValuePos()));
	    	sb.append("\"");
	    }
	    sb.append("/>");
	    return sb.toString();
	  }
	  
	 
}
