package com.asiainfo.monitor.tools.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.monitor.tools.common.interfaces.ITaskRtn;
import com.asiainfo.monitor.tools.common.interfaces.IWorker;
import com.asiainfo.monitor.tools.exe.task.model.TaskRtn;
import com.asiainfo.monitor.tools.model.interfaces.ICmdType;
import com.asiainfo.monitor.tools.model.interfaces.ITaskCmdContainer;
import com.asiainfo.monitor.tools.model.interfaces.ITaskContext;
import com.asiainfo.monitor.tools.util.TypeConst;

public abstract class CmdType implements ICmdType, IWorker
{
    private static Log log = LogFactory.getLog(CmdType.class);

    private String name;

    private ITaskCmdContainer container;

    public CmdType()
    {
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public ITaskCmdContainer getContainer()
    {
        return container;
    }

    public void setContainer(ITaskCmdContainer container)
    {
        this.container = container;
    }

    abstract public String getType();

    /**
     * 任务执行
     */
    public void action() throws Exception
    {
        ITaskRtn taskRtn = new TaskRtn();

        doTask(taskRtn);

        taskRtn.setParentId(this.getContainer().getId());
        taskRtn.setCode(this.getContainer().getCode());
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String time = dateFormat.format(new Date());
        taskRtn.setTime(time);
        if(StringUtils.isNotBlank(taskRtn.getMsg())) {
            boolean implflag = false;
            if(this.getContainer().getParent() instanceof ITaskContext) {
                taskRtn.setIdCode(((ITaskContext) this.getContainer().getParent()).getId());
                ((ITaskContext) this.getContainer().getParent()).addTaskRtn(taskRtn);
                implflag = true;
            }

            if(taskRtn.getMsg().indexOf(TypeConst._NEWLINE_CHAR) > 0) {
                String[] msgStr = StringUtils.split(taskRtn.getMsg(), TypeConst._NEWLINE_CHAR);
                taskRtn.setMsg(msgStr[0]);
                for(int i = 1; i < msgStr.length; i++) {
                    ITaskRtn newTaskRtn = (ITaskRtn) taskRtn.clone();
                    newTaskRtn.setMsg(msgStr[i]);
                    if(implflag)
                        ((ITaskContext) this.getContainer().getParent()).addTaskRtn(newTaskRtn);
                }
            }
        }
    }

    public abstract void doTask(ITaskRtn taskRtn) throws Exception;
}
