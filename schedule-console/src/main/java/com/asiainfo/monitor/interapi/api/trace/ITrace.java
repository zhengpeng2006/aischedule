package com.asiainfo.monitor.interapi.api.trace;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * trace接口
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: AI(NanJing)</p>
 * @author Yang Hua
 * @version 5.5
 */
public interface ITrace extends Serializable {
	
	public final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

	public final static String FILTERGROUP="Trace";
	
	public void setThreadId(String threadId);

	public String getThreadId();

	public void setId(String id);
	
	public String getName();
	
	public void setName(String name);
	
	public void setCreateTime(String createTime);

	public String getIcon();

	public void setIcon(String icon);

	public String getType();

	public void setType(String type);
	
	public void addChild(ITrace objITrace);
	
	public boolean isNode();
	
	public void buildTree(List list);
}
