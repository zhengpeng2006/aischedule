package com.asiainfo.schedule.core.client;

import com.asiainfo.schedule.core.client.interfaces.ISingleTaskDeal;
import com.asiainfo.schedule.core.po.TaskBean;
import com.asiainfo.schedule.core.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;

/**
 * Created by Genesis on 16/4/26.
 */
public class VmTaskWrapper<T> extends TaskProcessor {

    private static final transient Logger logger = LoggerFactory.getLogger(VmTaskWrapper.class);

    ISingleTaskDeal taskDeal;

    public VmTaskWrapper(TaskBean taskBean, String taskItem, String jobId, String itemJobId, Map<String, String> _param,
                         String serverId) throws Exception{
        super(taskBean, taskItem, jobId, itemJobId, _param, serverId);
        if (CommonUtils.isBlank(taskBean.getProcessClass())) {
            throw new Exception("业务实现类不能为空，必须实现接口ISingleTaskDeal！");
        }
        Object obj = Class.forName(taskBean.getProcessClass()).newInstance();
        if (!(obj instanceof ISingleTaskDeal)) {
            throw new Exception("任务类型为single，业务实现类必须实现接口ISingleTaskDeal！" + taskBean.getProcessClass());
        }
        taskDeal = (ISingleTaskDeal) obj;
    }

    @Override
    protected void excute() throws Exception {

    }

    @Override
    public String getDealDescription() {
        return null;
    }
}
