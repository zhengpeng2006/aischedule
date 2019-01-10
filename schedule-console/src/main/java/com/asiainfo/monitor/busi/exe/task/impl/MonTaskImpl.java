package com.asiainfo.monitor.busi.exe.task.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.monitor.busi.exe.task.interfaces.AbstractTaskProcess;
import com.asiainfo.monitor.busi.exe.task.interfaces.IMonTask;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.model.BackTimingTaskContext;

/**
 * 
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: AI(NanJing)
 * </p>
 * 
 * @author Yang Hua
 * @version 3.0
 */
public class MonTaskImpl extends AbstractTaskProcess implements IMonTask
{
    private transient static Log log = LogFactory.getLog(MonTaskImpl.class);

    /**
     * 
     * @param objMonPInfo MonPInfo
     * @throws Exception
     */
    public void doTask(List list) throws Exception
    {
        BackTimingTaskContext task = (BackTimingTaskContext) list.get(0);
        try {
            // 首先清除返回结果对象
            task.clear();

            this.execute(task);
        }
        catch(Throwable ex) {
            // 执行失败
            log.error("info_id:" + task.getId() + "," + AIMonLocaleFactory.getResource("MOS0000157"), ex);
            throw new Exception(ex);
        }
    }

    public static void main(String[] args) throws Exception
    {
    }
}
