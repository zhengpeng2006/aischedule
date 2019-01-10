package com.asiainfo.monitor.interapi;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.common.AIConfigManager;
import com.ai.appframe2.common.ServiceManager;

public class MonitorLocaleFactory {

	private static transient Log log = LogFactory.getLog(MonitorLocaleFactory.class);

	private static final String RESOURCE_BUNDLE_NAME = "i18n.monitorframe_resource";

	private static boolean IS_FIXED_LOCALE = true;

	private static Locale DEFAULT_LOCALE = Locale.CHINA;

	private static final HashMap LOCALE_RESOURCE_MAPPING = new HashMap();

	static {
		//默认locale
		try {
			String strLocale = AIConfigManager.getConfigItem("LOCALE");
			if (!StringUtils.isBlank(strLocale)) {
				DEFAULT_LOCALE = new Locale(strLocale);
			}
		} catch (Throwable ex) {
			DEFAULT_LOCALE = Locale.CHINA;
			log.error("Parsing parameter LOCALE error,use LOCALE=Locale.CHINA",ex);
		} finally {
			log.error("Default locale is " + DEFAULT_LOCALE.toString());
		}

		//固定国际化
		try {
			String strLocaleFixed = AIConfigManager.getConfigItem("LOCALE_FIXED");
			if (!StringUtils.isBlank(strLocaleFixed)&& strLocaleFixed.trim().equalsIgnoreCase("false")) {
				IS_FIXED_LOCALE = false;
			} else {
				IS_FIXED_LOCALE = true;
			}

		} catch (Exception ex) {
			IS_FIXED_LOCALE = true;
			log.error("Parsing parameter LOCALE_FIXED error,use LOCALE_FIXED=true",ex);
		} finally {
			if (IS_FIXED_LOCALE) {
				log.error("LOCALE is fixed");
			} else {
				log.error("LOCALE is not fixed");
			}
		}
	}

	/**
	 *
	 * @return String
	 */
	public static String getStrLocale() {
		return DEFAULT_LOCALE.toString();
	}

	/**
	 *
	 * @param key String
	 * @return String
	 */
	public static String getResource(String key) {
		return getResource(RESOURCE_BUNDLE_NAME, key);
	}

	/**
	 *
	 * @param res String
	 * @param key String
	 * @return String
	 */
	public static String getResource(String res, String key) {
		Locale locale = null;

		if (IS_FIXED_LOCALE) {
			locale = DEFAULT_LOCALE;
		} else {
			locale = ServiceManager.getLocale();
			if (locale == null) {
				locale = DEFAULT_LOCALE;
			}
		}

		Long hashCode = new Long(locale.hashCode() * res.hashCode());

		HashMap resource = (HashMap) LOCALE_RESOURCE_MAPPING.get(hashCode);
		if (resource == null) {
			synchronized (LOCALE_RESOURCE_MAPPING) {
				if (!LOCALE_RESOURCE_MAPPING.containsKey(hashCode)) {
					String fileName = StringUtils.replace(res, ".", "/") + "_"+ locale.toString() + ".properties";
					InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
					if (is == null) {
						throw new RuntimeException("File " + fileName+ " is not exist!!");
					}

					try {
						Properties p = new Properties();
						p.load(is);
						is.close();

						HashMap tmp = new HashMap();
						Set set = p.keySet();
						for (Iterator iter = set.iterator(); iter.hasNext();) {
							String item = (String) iter.next();
							tmp.put(item, p.getProperty(item));
						}

						p.clear();
						p = null;//help gc

						LOCALE_RESOURCE_MAPPING.put(hashCode, tmp);
					} catch (IOException ex) {
						throw new RuntimeException(ex);
					}
				}
				resource = (HashMap) LOCALE_RESOURCE_MAPPING.get(hashCode);
			}
		}

		String rtn = (String) resource.get(key);
		if (rtn == null) {
			rtn = key;
			String fileName = RESOURCE_BUNDLE_NAME + "_" + locale.toString();
			log.error("Not found resource " + key + " from " + fileName);
		}
		return rtn;
	}

	/**
	 *
	 * @param key String
	 * @param params Object[]
	 * @return String
	 */
	public static String getResource(String key, Object[] params) {
		String value = getResource(key);
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				if (params[i] == null) {
					value = StringUtils.replaceOnce(value, "{" + i + "}","{null}");
				} else {
					value = StringUtils.replaceOnce(value, "{" + i + "}",params[i].toString());
				}
			}
		}
		return value;
	}

	/**
	 *
	 * @param res String
	 * @param key String
	 * @param params Object[]
	 * @return String
	 */
	public static String getResource(String res, String key, Object[] params) {
		String value = getResource(res, key);
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				value = StringUtils.replaceOnce(value, "{" + i + "}", params[i].toString());
			}
		}
		return value;
	}

	public static void main(String[] args) throws Exception {
//		System.out.println(MonitorLocaleFactory.getResource("dbgrid.vm.msgtotal", new String[] { "dd", "d2", "ee" }));
	}
}
