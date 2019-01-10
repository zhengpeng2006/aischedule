package com.asiainfo.monitor.busi.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.ai.appframe2.complex.tab.split.function.IFunction;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

/**
 * 按年月日分表函数
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: AI(NanJing)</p>
 * @author Yang Hua
 * @version 5.5
 */
public class IYYYYMMFunctionImpl implements IFunction {

	public IYYYYMMFunctionImpl() {
	}

	/**
	 *
	 * @param value Object
	 * @throws Exception
	 * @return String
	 */
	public String convert(Object value) throws Exception {
		String rtn = null;
		if (value instanceof Date) {
			DateFormat objDateFormat = new SimpleDateFormat("yyyyMM");
			rtn = objDateFormat.format(value);
		}else if (value instanceof String){
			if (StringUtils.trim(value.toString()).length()==6){
				// 日期类型值应该为6位数
				rtn=(String)value;
			}
			else {
				DateFormat from = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date d = from.parse((String)value);
				DateFormat to = new SimpleDateFormat("yyyyMM");
				rtn = to.format(d);
			}
		}
		else {
			// "类型必须为是日期类型"
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000233"));
		}
		return rtn;
	}
}
