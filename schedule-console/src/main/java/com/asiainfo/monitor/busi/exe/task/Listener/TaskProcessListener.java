package com.asiainfo.monitor.busi.exe.task.Listener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPInfoSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerAtomicSV;
import com.asiainfo.monitor.common.CommonConst;
import com.asiainfo.monitor.common.CommonSvUtil;
import com.asiainfo.monitor.common.combin.CmbHostBean;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonServerValue;
import com.asiainfo.monitor.tools.common.KeyName;
import com.asiainfo.monitor.tools.common.interfaces.IKeyName;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.model.BaseContainer;
import com.asiainfo.monitor.tools.model.CmdType;
import com.asiainfo.monitor.tools.model.ContainerEvent;
import com.asiainfo.monitor.tools.model.DefaultTaskContext;
import com.asiainfo.monitor.tools.model.interfaces.IBackTimingTaskContext;
import com.asiainfo.monitor.tools.model.interfaces.IContainerListener;
import com.asiainfo.monitor.tools.model.interfaces.ITaskCmdContainer;
import com.asiainfo.monitor.tools.util.TypeConst;

/**
 * 任务处理监听器
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2014
 * </p>
 * <p>
 * Company: AI(NanJing)
 * </p>
 * 
 * @author wu songkai
 * @version 3.0
 */
public class TaskProcessListener implements IContainerListener, PropertyChangeListener, Serializable
{

    private static transient Log log = LogFactory.getLog(TaskProcessListener.class);

    /**
     * 容器监听器方法
     */
    public void containerEvent(ContainerEvent event) throws Exception
    {
        // 事件执行前方法
        if(event.getType().equals(BaseContainer._DO_BEFORE_EVENT)) {
            try {
                if(event.getContainer() instanceof ITaskCmdContainer) {

                    CmdType cmdType = ((ITaskCmdContainer) event.getContainer()).getCmdType();

                    if(StringUtils.isBlank(cmdType.getName())) {
                        cmdType.setName("tmpsh_" + (event.getContainer().getParent()).getId());
                    }

                    cmdType.setContainer((ITaskCmdContainer) event.getContainer());

                    List parameter = ((BaseContainer) event.getContainer()).getParameter();

                    if(parameter != null && parameter.size() > 0) {
                        for(int i = 0; i < parameter.size(); i++) {
                            KeyName itemPara = (KeyName) parameter.get(i);
                            // 以$打头的Key认为变量，从根容器去查找
                            if(itemPara.getKey().startsWith("$")) {
                                String paraname = itemPara.getName().toUpperCase();
                                KeyName globParam = event.getContainer().getParent().findParameter(paraname);
                                if(globParam != null)
                                    event.getContainer().addParameter(globParam);
                            }
                        }
                    }
                }
                else if(event.getContainer() instanceof DefaultTaskContext) {
                    // 将返回结果设置为空
                    ((DefaultTaskContext) event.getContainer()).clear();
                    if(event.getContainer() instanceof IBackTimingTaskContext) {
                        if(((IBackTimingTaskContext) event.getContainer()).getExecMethod().equals(TypeConst._TASKMETHOD_I)
                                || ((IBackTimingTaskContext) event.getContainer()).getExecMethod().equals(TypeConst._TASKMETHOD_F)) {
                            // 该任务被执行过则抛出异常不再执行
                            if(!((IBackTimingTaskContext) event.getContainer()).getState().equals("U")) {
                                throw new Exception(AIMonLocaleFactory.getResource("MOS0000320",
                                        ((IBackTimingTaskContext) event.getContainer()).getId()));
                            }
                        }
                    }
                }
            }
            catch(Exception e) {
                log.error("Call TaskProcessListener's method containerEvent has Exception :" + e.getMessage());
            }
        }
        else if(event.getType().equals(BaseContainer._DO_AFTER_EVENT)) {
            if(event.getContainer() instanceof IBackTimingTaskContext) {
                // 当任务为立即执行或固定时间执行一次时，该任务执行完毕后将状态设置成F不再执行
                if(((IBackTimingTaskContext) event.getContainer()).getExecMethod().equals(TypeConst._TASKMETHOD_I)
                        || ((IBackTimingTaskContext) event.getContainer()).getExecMethod().equals(TypeConst._TASKMETHOD_F)) {
                    try {
                        ((IBackTimingTaskContext) event.getContainer()).setState("F");
                        IAIMonPInfoSV infoSV = (IAIMonPInfoSV) ServiceFactory.getService(IAIMonPInfoSV.class);
                        infoSV.updateState(Long.parseLong(((IBackTimingTaskContext) event.getContainer()).getId()));
                    }
                    catch(Exception e) {
                        throw new Exception(AIMonLocaleFactory.getResource("MOS0000319", ((IBackTimingTaskContext) event.getContainer()).getId(),
                                e.getMessage()));
                    }
                }
            }
        }
    }

    /**
     * 属性改变事件
     */
    public void propertyChange(PropertyChangeEvent event)
    {
        if(event.getSource() instanceof BaseContainer) {
            try {
                processContainerPropertyChange((BaseContainer) event.getSource(), event.getPropertyName(), event.getOldValue(), event.getNewValue());
            }
            catch(Exception e) {
                log.error("Exception handling Container property change", e);
            }
        }
    }

    /**
     * 属性改变,Context的参数，外参
     * 
     * @param container:主容器Context
     * @param propertyName
     * @param oldValue
     * @param newValue
     * @throws Exception
     */
    protected void processContainerPropertyChange(BaseContainer container, String propertyName, Object oldValue, Object newValue) throws Exception
    {
        if("parameter".equals(propertyName) && newValue != null) {
            if(newValue instanceof IKeyName) {
                // IContainerListener listener=this;
                container.removePropertyChangeListener(this);

                if(((IKeyName) newValue).getName().equals(TypeConst.HOST + TypeConst._ID)) {
                    //IAIMonPhysicHostAtomicSV phostSV = (IAIMonPhysicHostAtomicSV) ServiceFactory.getService(IAIMonPhysicHostAtomicSV.class);
                    //IBOAIMonPhysicHostValue phost = phostSV.getPhysicHostById(((IKeyName) newValue).getKey());
                    //获取主机相关信息
                    CmbHostBean hostBean = CommonSvUtil.qryCmbHostInfo(((IKeyName) newValue).getKey());
                    // Map<String, String> monUserInfo = hostBean.getMonUserInfo();
                    //根据主机标识查询监控用户
                    Map<String, String> monUserInfo = CommonSvUtil.qryMonitorNodeUserInfoByHostId(((IKeyName) newValue).getKey());

                    if(hostBean != null && null != monUserInfo) {
                        container.addParameter(new KeyName(TypeConst.HOST + TypeConst._NAME, hostBean.getHostName()));
                        container.addParameter(new KeyName(TypeConst.IP, hostBean.getHostIp()));
                        container.addParameter(new KeyName(TypeConst.USER, monUserInfo.get(CommonConst.USER_NAME)));
                        container.addParameter(new KeyName(TypeConst.PASSWORD, monUserInfo.get(CommonConst.USER_PASSWD)));
                        container.addParameter(new KeyName(TypeConst.PORT, hostBean.getConPort(CommonConst.CON_TYPE_SSH)));
                    }
                }
                else if(((IKeyName) newValue).getName().equals(TypeConst.SERVER + TypeConst._ID)) {

                    IAIMonServerAtomicSV serverSV = (IAIMonServerAtomicSV) ServiceFactory.getService(IAIMonServerAtomicSV.class);
                    IBOAIMonServerValue server = serverSV.getServerBeanById(((IKeyName) newValue).getKey());

                    if(server != null) {
                        container.addParameter(new KeyName(TypeConst.SERVER + TypeConst._CODE, server.getServerCode()));
                        container.addParameter(new KeyName(TypeConst.SERVER + TypeConst._NAME, server.getServerName()));

                        CmbHostBean hostBean = CommonSvUtil.qryCmbHostInfoByServerId(String.valueOf(server.getServerId()));
                        if(hostBean != null) {
                            //Map<String, String> userMap = hostBean.getMonUserInfo();
                            Map<String, String> userMap = new HashMap<String, String>();
                            userMap = CommonSvUtil.qryMonitorNodeUserInfoByServerId(String.valueOf(server.getServerId()));
                            container.addParameter(new KeyName(TypeConst.HOST + TypeConst._NAME, hostBean.getHostName()));
                            container.addParameter(new KeyName(TypeConst.IP, hostBean.getHostIp()));
                            container.addParameter(new KeyName(TypeConst.USER, userMap.get(CommonConst.USER_NAME)));
                            container.addParameter(new KeyName(TypeConst.PASSWORD, userMap.get(CommonConst.USER_PASSWD)));
                            container.addParameter(new KeyName(TypeConst.PORT, hostBean.getConPort(CommonConst.CON_TYPE_SSH)));
                        }
                    }
                }
                container.addPropertyChangeListener(this);
            }

        }
        else if("hostId".equals(propertyName) && newValue != null) {
            if(container instanceof ITaskCmdContainer)
                return;
            if(StringUtils.isNotBlank(String.valueOf(newValue))) {
                container.addParameter(new KeyName(TypeConst.HOST + TypeConst._ID, String.valueOf(newValue)));
            }
        }
        else if("serverId".equals(propertyName) && newValue != null) {
            if(container instanceof ITaskCmdContainer)
                return;
            if(StringUtils.isNotBlank(String.valueOf(newValue))) {
                container.addParameter(new KeyName(TypeConst.SERVER + TypeConst._ID, String.valueOf(newValue)));
            }
        }
    }
}
