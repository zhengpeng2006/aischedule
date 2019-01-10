package com.asiainfo.index.base.dao.interfaces;

import java.util.List;
import java.util.Map;

public interface IBsStaticDataDAO {
	
	Map<String,List<String[]>> getAllEnmuValue() throws Exception;
	
}
