package com.asiainfo.monitor.busi.exe.task.interfaces;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.monitor.busi.exe.task.impl.TaskPostionStategyFactory;
import com.asiainfo.monitor.busi.exe.task.interpreter.DefaultInterpreterWrapper;
import com.asiainfo.monitor.busi.exe.task.interpreter.InterpreterWrapper;
import com.asiainfo.monitor.tools.common.interfaces.IKeyName;
import com.asiainfo.monitor.tools.common.interfaces.ITaskRtn;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.model.interfaces.IBackTimingTaskContext;
import com.asiainfo.monitor.tools.model.interfaces.ITaskContext;

public abstract class AbstractTaskProcess implements IExecute
{

    private transient static Log log = LogFactory.getLog(AbstractTaskProcess.class);

    public AbstractTaskProcess()
    {
        super();
        // TODO 自动生成构造函数存根
    }

    /**
     * 任务执行
     */
    public void execute(ITaskContext task) throws Exception
    {
        try {
            task.action();
        }
        catch(Exception e) {
            throw new Exception(AIMonLocaleFactory.getResource("MOS0000161", task.getName()) + e.getMessage());
        }

        //来自后台定时任务告警阀值处理
        String express = ((IBackTimingTaskContext) task).getThreshold().getExpr();
        if(((IBackTimingTaskContext) task).getThreshold() != null && StringUtils.isNotBlank(express)) {
            // 处理告警阀值
            this.interpreter(task);
        }

        // 任务结果处理，如告警信息，写监控记录表等。
        this.process(task);
    }

    /**
     * 任务结果后继脚本处理
     * 
     * @param task
     * @param taskResult
     * @return
     * @throws Exception
     */
    public void interpreter(ITaskContext task) throws Exception
    {
        try {
            // "没有返回值,无需预警处理"
            if(task.getRtnModel() == null || task.getRtnModel().getRtns() == null || task.getRtnModel().getRtns().size() < 1)
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000162"));

            //获取阀值脚本
            String thresholdExpr = ((IBackTimingTaskContext) task).getThreshold().getExpr();

            //对返回结果进行阀值告警
            List rtnList = task.getRtnModel().getRtns();

            if(rtnList != null) {
                for(Object object : rtnList) {
                    ITaskRtn taskRtn = (ITaskRtn) object;
                    IKeyName[] paramArr = task.getParameter() == null ? null : (IKeyName[]) task.getParameter().toArray(new IKeyName[0]);

                    InterpreterWrapper interpreter = new DefaultInterpreterWrapper(task.getId(), task.getCode(), task.getName(), paramArr,
                            thresholdExpr, taskRtn);

                    //解析监控阀值脚本
                    interpreter.execute();

                    interpreter = null;
                }
            }
        }
        catch(Exception e) {
            // 任务运行异常
            log.error(AIMonLocaleFactory.getResource("MOS0000164") + ":taskName:" + task.getName() + ";error:" + e.getMessage());
        }
        return;
    }

    /**
     * 任务后继处理
     * 
     * @param task
     * @param rtn
     * @return
     * @throws Exception
     */
    public void process(ITaskContext task) throws Exception
    {
        if(task.getRtnModel() == null || task.getRtnModel().getRtns() == null || task.getRtnModel().getRtns().size() < 1) {
            // 任务[{0}]执行没有返回值
            log.error(AIMonLocaleFactory.getResource("MOS0000158", task.getId()));
            return;
        }
        try {
            TaskPostionStategyFactory.process(task);
        }
        catch(Exception e) {
            // "任务后置处理异常"
            log.error(AIMonLocaleFactory.getResource("MOS0000159"), e);
        }
    }

}
