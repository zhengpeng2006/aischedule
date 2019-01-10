package com.asiainfo.monitor.busi.exe.task.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.CharEncoding;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.complex.util.e.K;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.exe.task.interfaces.AbstractTaskProcess;
import com.asiainfo.monitor.busi.exe.task.interfaces.IMonTask;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonStaticDataSV;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonStaticDataValue;
import com.asiainfo.monitor.tools.common.KeyName;
import com.asiainfo.monitor.tools.common.interfaces.IKeyName;
import com.asiainfo.monitor.tools.common.interfaces.ITaskRtn;
import com.asiainfo.monitor.tools.exe.task.model.TaskRtn;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.model.BackTimingTaskContext;
import com.asiainfo.monitor.tools.model.BaseContainer;
import com.asiainfo.monitor.tools.model.interfaces.ICmdType;
import com.asiainfo.monitor.tools.model.interfaces.ITaskCmdContainer;
import com.asiainfo.monitor.tools.util.SSHUtil;
import com.asiainfo.monitor.tools.util.TypeConst;

/**
 * 
 * @author guocx
 *
 */
public class MonMergeTaskImpl extends AbstractTaskProcess implements IMonTask
{

    private transient static Log log = LogFactory.getLog(MonMergeTaskImpl.class);

    /**
     * 
     * @param objMonPInfo
     *            MonPInfo
     * @throws Exception
     */
    public void doTask(List in) throws Exception
    {
        BackTimingTaskContext[] tasks = (BackTimingTaskContext[]) in.toArray(new BackTimingTaskContext[0]);
        BackTimingTaskContext task = tasks[0];
        for(int i = 0; i < tasks.length; i++) {
            tasks[i].clear();
        }
        List list1 = new LinkedList();
        List list2 = new LinkedList();
        List params = new LinkedList();

        ITaskCmdContainer container = (ITaskCmdContainer) task.getCmdContainers().get(0);

        if(container.getType().equalsIgnoreCase(ITaskCmdContainer._TASK_EXEC)) {
            String charEncoding = CharEncoding.ISO_8859_1;
            IAIMonStaticDataSV staticDataSV = (IAIMonStaticDataSV) ServiceFactory.getService(IAIMonStaticDataSV.class);
            IBOAIMonStaticDataValue[] datas = staticDataSV.queryByCodeType("SSH_CHARENCODING");
            if(datas != null && datas.length > 0) {
                charEncoding = datas[0].getCodeValue();
            }

            for(int i = 0; i < tasks.length; i++) {
                ITaskCmdContainer itemCmdContainer = (ITaskCmdContainer) tasks[i].getCmdContainers().get(0);

                if(itemCmdContainer.getCmdType().getType().equalsIgnoreCase(ICmdType.SHELL)) {
                    list1.add(tasks[i].getId());
                    list2.add(itemCmdContainer.getExpr());

                    //参数解析
                    if(((BaseContainer) itemCmdContainer).getParameter() != null && ((BaseContainer) itemCmdContainer).getParameter().size() > 0) {
                        String innerPara = "";
                        List parameter = ((BaseContainer) itemCmdContainer).getParameter();
                        StringBuilder sb = new StringBuilder("");
                        for(int count = 0, k = 0; count < parameter.size(); count++) {
                            KeyName itemInnerPara = (KeyName) parameter.get(count);
                            //如果内参是主机ID,则不视为内参
                            if(itemInnerPara.getName().equals(TypeConst.HOST + TypeConst._ID))
                                continue;
                            if(k > 0) {
                                sb.append(" ");
                            }
                            sb.append(itemInnerPara.getKey());
                            k++;
                        }
                        innerPara = sb.toString();
                        if(StringUtils.isBlank(charEncoding))
                            params.add(innerPara);
                        else
                            params.add(new String(innerPara.getBytes(), charEncoding));
                    }
                    else {
                        params.add("");
                    }

                }
                else if(itemCmdContainer.getCmdType().getType().equalsIgnoreCase(ICmdType.COMMAND)) {
                    throw new Exception(AIMonLocaleFactory.getResource("MOS0000153") + itemCmdContainer.getCmdType().getType());
                }
                else if(itemCmdContainer.getCmdType().getType().equalsIgnoreCase(ICmdType.JAVACOMMAND)) {
                    throw new Exception(AIMonLocaleFactory.getResource("MOS0000153") + itemCmdContainer.getCmdType().getType());
                }
                else {
                    throw new Exception(AIMonLocaleFactory.getResource("MOS0000153") + itemCmdContainer.getCmdType().getType());
                }
            }
        }
        else if(container.getType().equalsIgnoreCase(ITaskCmdContainer._TASK_TABLE)) {
            // "执行方式为table的在合并task中没有实现"
            throw new Exception(AIMonLocaleFactory.getResource("MOS0000154"));
        }

        String ip = "", port = "", user = "", password = "";
        if(task.getParameter() != null && task.getParameter().size() > 0) {
            IKeyName ipParam = task.findParameter(TypeConst.IP);
            if(ipParam != null)
                ip = ipParam.getKey();
            IKeyName portParam = task.findParameter(TypeConst.PORT);
            if(portParam != null) {
                port = portParam.getKey();
            }
            IKeyName userParam = task.findParameter(TypeConst.USER);
            if(userParam != null)
                user = userParam.getKey();
            IKeyName passwordParam = task.findParameter(TypeConst.PASSWORD);
            if(passwordParam != null)
                password = K.k(passwordParam.getKey());
        }
        else {
            // "没有为Shell任务["+task.getId()+"]设置登陆信息"
            throw new Exception(AIMonLocaleFactory.getResource("MOS0000155", task.getId()));
        }

        // 批量执行
        String[] returnStr = SSHUtil.ssh4Shell(ip, Integer.parseInt(port), user, password, (String[]) list1.toArray(new String[0]),
                (String[]) params.toArray(new String[0]), (String[]) list2.toArray(new String[0]));
        for(int k = 0; k < returnStr.length; k++) {
            String rtn = returnStr[k];
            BackTimingTaskContext itemTask = tasks[k];
            try {
                ITaskRtn taskRtn = new TaskRtn();
                taskRtn.setMsg(rtn);

                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                String time = dateFormat.format(new Date());
                taskRtn.setTime(time);
                if(StringUtils.isNotBlank(taskRtn.getMsg())) {
                    itemTask.addTaskRtn(taskRtn);

                    if(taskRtn.getMsg().indexOf(TypeConst._NEWLINE_CHAR) > 0) {
                        String[] msgStr = StringUtils.split(taskRtn.getMsg(), TypeConst._NEWLINE_CHAR);
                        taskRtn.setMsg(msgStr[0]);
                        for(int i = 1; i < msgStr.length; i++) {
                            ITaskRtn newTaskRtn = (ITaskRtn) taskRtn.clone();
                            newTaskRtn.setMsg(msgStr[i]);
                            itemTask.addTaskRtn(newTaskRtn);
                        }
                    }
                }
                if(itemTask.getThreshold() != null && StringUtils.isNotBlank(itemTask.getThreshold().getExpr()))
                    interpreter(itemTask);
                this.process(itemTask);
            }
            catch(Throwable ex) {
                // 执行失败,返回数据
                log.error("info_id:" + itemTask.getId() + "," + AIMonLocaleFactory.getResource("MOS0000157") + rtn, ex);
            }
        }

    }

    public static void main(String[] args) throws Exception
    {
    }
}
