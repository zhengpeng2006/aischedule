package com.asiainfo.batchwriteoff.table;
/**
 * <p> @Description </p>
 * <p> @company Asiainfo-linkage (NJ) </p>
 * <p> @author 闫瑞国 </p>
 * <p> @Email yanrg@asiainfo-linkage.com </p>
 * <p> @createDate Oct 29, 2012 9:06:24 PM </p>
 * <p> @modifyDate </p>
 * <p> @version 1.0 </p>
 */
public class Sequence {

	private String sequenceName;
	private long minValue;
	private long maxValue;
	private long incrementBy;
	private String cycleFlag ;
	private long cacheSize;
	
	
	
	public String getSequenceName() {
		return sequenceName;
	}
	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}
	public long getMinValue() {
		return minValue;
	}
	public void setMinValue(long minValue) {
		this.minValue = minValue;
	}
	public long getMaxValue() {
		return maxValue;
	}
	public void setMaxValue(long maxValue) {
		this.maxValue = maxValue;
	}
	public long getIncrementBy() {
		return incrementBy;
	}
	public void setIncrementBy(long incrementBy) {
		this.incrementBy = incrementBy;
	}
	public String getCycleFlag() {
		return cycleFlag;
	}
	public void setCycleFlag(String cycleFlag) {
		this.cycleFlag = cycleFlag;
	}
	public long getCacheSize() {
		return cacheSize;
	}
	public void setCacheSize(long cacheSize) {
		this.cacheSize = cacheSize;
	}
	
}
