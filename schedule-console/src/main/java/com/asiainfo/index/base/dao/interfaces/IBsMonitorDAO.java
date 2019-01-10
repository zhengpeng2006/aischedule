package com.asiainfo.index.base.dao.interfaces;

import java.util.Map;

import com.asiainfo.index.base.bo.BOBsMonitorBean;

public interface IBsMonitorDAO {
	BOBsMonitorBean getBeanById(int id) throws Exception;

	Map<Integer, String> getAllMonitors() throws Exception;
}
