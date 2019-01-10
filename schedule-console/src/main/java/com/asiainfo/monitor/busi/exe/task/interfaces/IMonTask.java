package com.asiainfo.monitor.busi.exe.task.interfaces;


import java.util.List;

/**
 * 任务接口
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: AI(NanJing)</p>
 * @author Yang Hua
 * @version 3.0
 */
public interface IMonTask {

  /**
   *
   * @param objMonPInfo MonPInfo
   * @throws Exception
   */
  public void doTask(List list) throws Exception;

}
