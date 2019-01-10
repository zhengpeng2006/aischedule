package com.asiainfo.monitor.busi.exe.task.model;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.monitor.tools.common.interfaces.IWorker;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.model.BaseContainer;
import com.asiainfo.monitor.tools.model.CmdType;
import com.asiainfo.monitor.tools.model.interfaces.ITaskCmdContainer;

/**
 * 执行任务进程的容器
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: AI(NanJing)</p>
 * @author wu songkai
 * @version 3.0
 */
public class TaskExecContainer extends BaseContainer implements ITaskCmdContainer, IWorker
{

    private static Log log = LogFactory.getLog(TaskExecContainer.class);

    // 标识码
    private String code;

    // 具体命令
    private CmdType cmdType;

    // 表达式
    private String expr;

    // 上级
    private long taskId;

    // 顺序
    private int sort;

    private String paramRule;

    private String runType;

    public TaskExecContainer()
    {
        super();
    }

    public String getType()
    {
        return _TASK_EXEC;
    }

    public CmdType getCmdType()
    {
        return cmdType;
    }

    public void setCmdType(CmdType cmdType)
    {
        this.cmdType = cmdType;
    }

    public String getExpr()
    {
        return expr;
    }

    public void setExpr(String expr)
    {
        this.expr = expr;
    }

    public long getParentId()
    {
        return this.taskId;
    }

    public void setParentId(long taskId)
    {
        this.taskId = taskId;
    }

    public String getCode()
    {
        return this.code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getParamRule()
    {
        return paramRule;
    }

    public void setParamRule(String paramRule)
    {
        this.paramRule = paramRule;
    }

    public int getSort()
    {
        return sort;
    }

    public void setSort(int sort)
    {
        this.sort = sort;
    }

    public String getRunTimeType()
    {
        return this.runType;
    }

    public void setRunTimeType(String runType)
    {
        this.runType = runType;
    }

    // 执行命令
    public void action() throws Exception
    {
        try {
            // 调用前事件
            fireContainerEvent(BaseContainer._DO_BEFORE_EVENT, null);

            // 执行命令
            this.cmdType.action();

            // 调用后事件
            fireContainerEvent(BaseContainer._DO_AFTER_EVENT, null);
        }
        catch(Exception e) {
            // "执行任务["+this.getName()+"]时发生异常"
            log.error(AIMonLocaleFactory.getResource("MOS0000177", this.getName()), e);
            throw new Exception(AIMonLocaleFactory.getResource("MOS0000177", this.getName()), e);
        }
        finally {
            if(this.signal != null)
                this.signal.countDown();
        }
    }

}
