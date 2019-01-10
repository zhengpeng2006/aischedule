package com.asiainfo.monitor.tools.i18n;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import com.ai.appframe2.complex.cache.CacheFactory;
import com.ai.appframe2.util.locale.AppframeLocaleFactory;
import com.asiainfo.monitor.tools.cache.impl.AIMonI18nResourceCacheImpl;


/**
 * monitor系统的国际化工厂
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2010</p>
 *
 * <p>Company: AI(NanJing)</p>
 *
 * @author wangdy2
 * @version 1.0
 */
public final class AIMonLocaleFactory {
  private AIMonLocaleFactory() {
  }

  /**
   * 获得当前的语言
   * @return String
   */
  public static Locale getCurrentLocale() {
    return AppframeLocaleFactory.getCurrentLocale();
  }

  /**
   *
   * @param key String
   * @param args Object[]
   * @return String
   */
  public static String getResource(String key, Object[] args) {
    String value = getResource(key);

    if (args != null && args.length > 0) {
      for (int i = 0; i < args.length; i++) {
	value = StringUtils.replaceOnce(value, "{" + i + "}", args[i].toString());
      }
    }
    return value;
  }

  /**
   *
   * @param key String
   * @return String
   */
  public static String getResource(String key) {
    String rtn = null;
    try {
      rtn = (String) CacheFactory.get(AIMonI18nResourceCacheImpl.class, key);
    }
    catch (Throwable ex) {
      throw new RuntimeException("key:" + key + ",resource found exception", ex);
    }

    if (rtn == null) {
      throw new RuntimeException("key:" + key + ",resource not found");
    }

    return rtn;
  }

  /**
   *
   * @param key String
   * @param arg1 String
   * @return String
   */
  public static String getResource(String key, String arg1) {
    return getResource(key, new String[] {arg1});
  }

  /**
   *
   * @param key String
   * @param arg1 String
   * @param arg2 String
   * @return String
   */
  public static String getResource(String key, String arg1,String arg2) {
    return getResource(key, new String[] {arg1,arg2});
  }

  /**
   *
   * @param key String
   * @param arg1 String
   * @param arg2 String
   * @param arg3 String
   * @return String
   */
  public static String getResource(String key, String arg1,String arg2,String arg3) {
    return getResource(key, new String[] {arg1,arg2,arg3});
  }

}