package com.asiainfo.monitor.tools.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.monitor.tools.common.KeyName;
import com.asiainfo.monitor.tools.common.interfaces.IEnable;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.model.interfaces.IContainerListener;
import com.asiainfo.monitor.tools.model.interfaces.IContext;

public abstract class BaseContainer extends AbstractEnable implements Serializable, IContext, IEnable
{

    private static Log log = LogFactory.getLog(BaseContainer.class);

    protected String id;
    // 容器寄主1:主机
    protected String hostId;

    // 容器寄主2:应用
    protected String serverId;

    // 容器名称
    protected String name;

    // 容器内参数
    private List parameter = null;

    protected Object attach;
    // 容器监听
    protected ArrayList listeners = new ArrayList();

    private BaseContainer parent;

    protected Lock l = new ReentrantLock();

    protected PropertyChangeSupport support = new PropertyChangeSupport(this);

    public static final String _DO_BEFORE_EVENT = "DO_BEFORE";

    public static final String _DO_AFTER_EVENT = "DO_AFTER";

    protected CountDownLatch signal = null;

    public BaseContainer()
    {
        super();
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getHostId()
    {
        return hostId;
    }

    public void setHostId(String hostId)
    {
        this.hostId = hostId;
        support.firePropertyChange("hostId", null, hostId);
    }

    public String getServerId()
    {
        return serverId;
    }

    public void setServerId(String serverId)
    {
        this.serverId = serverId;
        support.firePropertyChange("serverId", null, serverId);
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public BaseContainer getParent()
    {
        return parent;
    }

    public void setParent(BaseContainer parent)
    {
        this.parent = parent;
    }

    public Object getAttach()
    {
        return attach;
    }

    public void setAttach(Object attach)
    {
        this.attach = attach;
    }

    public List getParameter()
    {
        return parameter;
    }

    public CountDownLatch getSignal()
    {
        return signal;
    }

    public void setSignal(CountDownLatch signal)
    {
        this.signal = signal;
    }

    /**
     * 验证参数是否存在
     * 
     * @param item
     * @return:返回存在参数的位置
     */
    protected int validateParameter(KeyName item)
    {
        int location = -1;
        if(parameter != null && parameter.size() > 0) {
            for(int i = 0; i < parameter.size(); i++) {
                if(((KeyName) parameter.get(i)).getName().equals(item.getName())) {
                    parameter.remove(i);
                    location = i;
                    break;
                }
            }
        }
        return location;
    }

    public KeyName findParameter(String name)
    {
        if(parameter == null)
            return null;
        KeyName result = null;
        for(int i = 0; i < parameter.size(); i++) {
            if(((KeyName) parameter.get(i)).getName().equals(name)) {
                result = (KeyName) parameter.get(i);
                break;
            }
        }
        return result;
    }

    /**
     * 设置参数
     * 
     * @param item
     * @throws Exception
     */
    public void addParameter(KeyName item) throws Exception
    {
        if(parameter == null) {
            if(l.tryLock()) {
                try {
                    if(parameter == null)
                        parameter = new LinkedList();
                }
                finally {
                    l.unlock();
                }
            }
            else {
                if(log.isDebugEnabled()) {
                    // 当前有别的线程正在初始化参数
                    log.debug(AIMonLocaleFactory.getResource("MOS0000251"));
                }
            }
        }
        int location = validateParameter(item);
        if(location >= 0) {
            if(log.isDebugEnabled()) {
                // 面板任务参数已经存在并且也已删除
                log.debug(AIMonLocaleFactory.getResource("MOS0000252"));
            }
        }
        else {
            location = 0;
        }
        parameter.add(location, item);
        support.firePropertyChange("parameter", null, item);
    }

    /**
     * 批量放置参数
     * 
     * @param params
     * @throws Exception
     */
    public void addParameter(List params) throws Exception
    {
        if(parameter == null) {
            if(l.tryLock()) {
                try {
                    if(parameter == null)
                        parameter = new LinkedList();
                }
                finally {
                    l.unlock();
                }
            }
            else {
                if(log.isDebugEnabled()) {
                    // 当前有别的线程正在初始化参数
                    log.debug(AIMonLocaleFactory.getResource("MOS0000251"));
                }
            }
        }
        if(params != null)
            parameter.addAll(params);
    }

    /**
     * 增加容器监听
     * 
     * @param listener
     */
    public void addContainerListener(IContainerListener listener)
    {
        synchronized(listeners) {
            listeners.add(listener);
        }

    }

    /**
     * 移除容器监听
     * 
     * @param listener
     */
    public void removeContainerListener(IContainerListener listener)
    {
        synchronized(listeners) {
            listeners.remove(listener);
        }
    }

    /**
     * 触发容器监听事件
     * 
     * @param type
     * @param data
     */
    public void fireContainerEvent(String type, Object data) throws Exception
    {
        if(listeners.size() < 1)
            return;
        ContainerEvent event = new ContainerEvent(this, type, data);
        IContainerListener list[] = new IContainerListener[0];
        synchronized(listeners) {
            list = (IContainerListener[]) listeners.toArray(list);
        }
        for(int i = 0; i < list.length; i++)
            ((IContainerListener) list[i]).containerEvent(event);

    }

    /**
     * 增加属性监听
     * 
     * @param listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        support.addPropertyChangeListener(listener);
    }

    /**
     * 移除属性监听
     * 
     * @param listener
     */
    public void removePropertyChangeListener(PropertyChangeListener listener)
    {
        support.removePropertyChangeListener(listener);
    }

    // 深度复制
    private Object deepClone() throws IOException, OptionalDataException, ClassNotFoundException
    {
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream(bo);
        oo.writeObject(this);
        ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
        ObjectInputStream oi = new ObjectInputStream(bi);
        return oi.readObject();
    }

    public Object clone()
    {
        Object temp = null;
        try {
            temp = this.deepClone();

        }
        catch(IOException ioe) {
            // "深度复制任务模型发生异常:"
            log.error(AIMonLocaleFactory.getResource("MOS0000254") + ioe.getMessage());
        }
        catch(ClassNotFoundException cnte) {
            // "深度复制任务模型发生异常:"
            log.error(AIMonLocaleFactory.getResource("MOS0000254") + cnte.getMessage());
        }
        return temp;
    }
}
