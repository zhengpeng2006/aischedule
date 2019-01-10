package com.asiainfo.monitor.tools.common.interfaces;

import com.asiainfo.monitor.tools.common.KeyName;

/**
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: AI(NanJing)</p>
 *
 * @author Yang Hua
 * @version 2.0
 */
public interface IJavaCommand {

	public final static String STEP="STEP";
  /**
   *
   * @param in String
   * @return String
   * @throws Exception
   */
  public String execute(KeyName[] parameter) throws Exception;
}
