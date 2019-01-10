package com.asiainfo.schedule.core.data;

import java.io.StringWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException.NoNodeException;
import org.apache.zookeeper.KeeperException.NodeExistsException;
import org.apache.zookeeper.Transaction;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooKeeper.States;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.appframe2.complex.util.e.K;
import com.asiainfo.schedule.core.Version;
import com.asiainfo.schedule.core.client.tf.config.CfgTf;
import com.asiainfo.schedule.core.client.tf.config.CfgTfDtl;
import com.asiainfo.schedule.core.client.tf.config.CfgTfMapping;
import com.asiainfo.schedule.core.po.BackupInfoBean;
import com.asiainfo.schedule.core.po.CfgTfBean;
import com.asiainfo.schedule.core.po.CfgTfDtlBean;
import com.asiainfo.schedule.core.po.FaultBean;
import com.asiainfo.schedule.core.po.ServerBean;
import com.asiainfo.schedule.core.po.TaskBean;
import com.asiainfo.schedule.core.po.TaskGroupBean;
import com.asiainfo.schedule.core.po.TaskItemBackupInfo;
import com.asiainfo.schedule.core.po.TaskItemJobBean;
import com.asiainfo.schedule.core.po.TaskJobBean;
import com.asiainfo.schedule.core.po.TaskView;
import com.asiainfo.schedule.core.utils.CommonUtils;
import com.asiainfo.schedule.core.utils.Constants.JobSts;
import com.asiainfo.schedule.core.utils.Constants.RunSts;
import com.asiainfo.schedule.core.utils.Constants.ServerType;
import com.asiainfo.schedule.core.utils.Constants.TaskType;
import com.asiainfo.schedule.core.utils.DateUtils;
import com.asiainfo.schedule.core.utils.JsonUtils;
import com.asiainfo.schedule.core.utils.SchedulerLogger;

/**
 * zookeeper数据管理实现
 * 
 * @author LiuQQ
 * @company Asiainfo
 * @date 2014年8月27日
 * @modify
 */
public class ZKDataManagerFactory extends DataManagerFactory
{
    private static transient final Logger logger = LoggerFactory.getLogger(ZKDataManagerFactory.class);
    
    private ZooKeeper zk;
    
    private List<ACL> acl;
    
    private String pathManagerRegistry;
    
    private String pathExecutorRegistry;
    
    private String pathTaskConfig;
    
    private String pathTaskGroup;
    
    private String pathServerChannel;
    
    private String pathFault;
    
    private String pathStaticData;
    
    private String pathDeploy;
    
    private String backupConfig;
    
    private String backupRunningTaskItem;
    
    public enum Keys
    {
        rootPath, connectString, userName, password, sessionTimeout
    }
    
    @Override
    protected void initial()
        throws Exception
    {
        super.initial();
        this.createZKSession();
        this.checkVersion();
    }
    
    public synchronized void createZKSession()
        throws Exception
    {
        if (this.checkZookeeperState() == true)
        {
            return;
        }
        // 密码解密（rc2）
        String password = K.k_s(this.getScheduleConfig().getZkPassword());
        String authString = this.getScheduleConfig().getZkUserName() + ":" + password;
        zk =
            new ZooKeeper(this.getScheduleConfig().getZkConnectString(),
                this.getScheduleConfig().getZkSessionTimeOut(), new ZKSessionWatcher());
        zk.addAuthInfo("digest", authString.getBytes());
        acl = new ArrayList<ACL>();
        acl.add(new ACL(ZooDefs.Perms.ALL, new Id("digest", DigestAuthenticationProvider.generateDigest(authString))));
        acl.add(new ACL(ZooDefs.Perms.READ, Ids.ANYONE_ID_UNSAFE));
        int count = 0;
        while (this.checkZookeeperState() == false)
        {
            count++;
            if (count % 50 == 0)
            {
                logger.warn("zookeeper状态未就绪,等待...");
            }
            
            if (count > 500)
            {
                throw new Exception("请检查zookeeper服务器是否正常启动或者网络正常！" + this.getScheduleConfig().getZkConnectString());
            }
            Thread.sleep(60);
        }
    }
    
    /**
     * added by sundd:2016-01-23 对NoNodeException进行处理，不存在认为获取到的数据为null
     * 并不是所有情况都可以使用该方法替换，对于getChilren后遍历的情况需要特殊处理，不放入结果list中
     * 
     * @param path
     * @return
     * @throws Exception
     */
    private byte[] getDataWithNoNodeExceptionHandled(String path, boolean watch, Stat stat)
        throws Exception
    {
        try
        {
            return this.getZooKeeper().getData(path, watch, stat);
        }
        catch (NoNodeException e)
        {
            return null;
        }
    }
    
    /**
     * added by sundd:2016-01-23 对NoNodeException进行处理，不存在认为获取到的数据为空列表
     * 并不是所有情况都可以使用该方法替换，对于getChilren后遍历的情况需要特殊处理，不放入结果list中
     * 
     * @param path
     * @return
     * @throws Exception
     */
    private List<String> getChildrenWithNoNodeExceptionHandled(String path, boolean watch)
        throws Exception
    {
        try
        {
            return this.getZooKeeper().getChildren(path, watch);
        }
        catch (NoNodeException e)
        {
            return new ArrayList<String>();
        }
    }
    
    private void checkVersion()
        throws Exception
    {
        if (zk.exists(this.getRootPath(), false) == null)
        {
            ZKTools.createPath(zk, this.getRootPath(), CreateMode.PERSISTENT, acl);
            checkParent(this.getRootPath());
            zk.setData(this.getRootPath(), Version.getVersion().getBytes(), -1);
        }
        else
        {
            checkParent(this.getRootPath());
            // 不需要处理nonodeException
            byte[] value = zk.getData(this.getRootPath(), false, null);
            if (value == null)
            {
                zk.setData(this.getRootPath(), Version.getVersion().getBytes(), -1);
            }
            else
            {
                String dataVersion = new String(value);
                if (Version.isCompatible(dataVersion) == false)
                {
                    throw new Exception("任务调度中心版本:" + Version.getVersion() + ",与zookeeper已存在的版本不兼容 :" + dataVersion);
                }
                logger.info("初始化成功,任务调度中心当前版本为:" + Version.getVersion() + ",zookeeper数据版本为:" + dataVersion);
            }
        }
        // init data
        pathManagerRegistry = this.getRootPath() + "/serverRegistry/manager";
        pathExecutorRegistry = this.getRootPath() + "/serverRegistry/executor";
        pathTaskConfig = this.getRootPath() + "/task";
        pathTaskGroup = this.getRootPath() + "/group";
        pathServerChannel = this.getRootPath() + "/serverChannel";
        pathFault = this.getRootPath() + "/fault";
        pathStaticData = this.getRootPath() + "/staticData";
        pathDeploy = this.getRootPath() + "/deploy";
        
        backupConfig = this.getRootPath() + "/backupConfig";
        backupRunningTaskItem = this.getRootPath() + "/backupRunningTaskItem";
        
        if (zk.exists(pathManagerRegistry, false) == null)
        {
            ZKTools.createPath(zk, pathManagerRegistry, CreateMode.PERSISTENT, acl);
        }
        if (zk.exists(pathExecutorRegistry, false) == null)
        {
            ZKTools.createPath(zk, pathExecutorRegistry, CreateMode.PERSISTENT, acl);
        }
        if (zk.exists(pathServerChannel, false) == null)
        {
            ZKTools.createPath(zk, pathServerChannel, CreateMode.PERSISTENT, acl);
        }
        if (zk.exists(pathFault, false) == null)
        {
            ZKTools.createPath(zk, pathFault, CreateMode.PERSISTENT, acl);
        }
        if (zk.exists(pathTaskConfig, false) == null)
        {
            ZKTools.createPath(zk, pathTaskConfig, CreateMode.PERSISTENT, acl);
        }
        if (zk.exists(pathTaskGroup, false) == null)
        {
            ZKTools.createPath(zk, pathTaskGroup, CreateMode.PERSISTENT, acl);
        }
        
        if (zk.exists(pathStaticData, false) == null)
        {
            ZKTools.createPath(zk, pathStaticData, CreateMode.PERSISTENT, acl);
        }
        
        if (zk.exists(pathDeploy, false) == null)
        {
            ZKTools.createPath(zk, pathDeploy, CreateMode.PERSISTENT, acl);
        }
        
        if (zk.exists(backupConfig, false) == null)
        {
            ZKTools.createPath(zk, backupConfig, CreateMode.PERSISTENT, acl);
        }
        
        if (zk.exists(backupRunningTaskItem, false) == null)
        {
            ZKTools.createPath(zk, backupRunningTaskItem, CreateMode.PERSISTENT, acl);
        }
        
    }
    
    protected String getRootPath()
    {
        return this.getScheduleConfig().getZkRootPath();
    }
    
    boolean checkZookeeperState()
    {
        
        return zk != null && zk.getState().isAlive() && (zk.getState() == States.CONNECTED);
    }
    
    @Override
    public List<String> getAllServerIdByType(ServerType type)
        throws Exception
    {
        String path = this.getPath(type.name());
        if (this.getZooKeeper().exists(path, false) == null)
        {
            return new ArrayList<String>();
        }
        List<String> uuidList = this.getChildrenWithNoNodeExceptionHandled(path, false);
        return uuidList;
    }
    
    @Override
    public List<ServerBean> getAllServerRegistry(ServerType type)
        throws Exception
    {
        List<ServerBean> ret = new ArrayList<ServerBean>();
        for (String serverId : this.getAllServerIdByType(type))
        {
            ret.add(this.getServerRegistry(serverId, type));
        }
        
        return ret;
    }
    
    @Override
    public ServerBean getServerRegistry(String serverId, ServerType type)
        throws Exception
    {
        String zkPath = this.getPath(type.name()) + "/" + serverId;
        ServerBean result = null;
        if (this.getZooKeeper().exists(zkPath, false) != null)
        {
            // modified by sundd
            byte[] value = this.getDataWithNoNodeExceptionHandled(zkPath, false, null);
            if (value != null)
            {
                
                result = JsonUtils.fromJson(new String(value), ServerBean.class);
                
            }
        }
        return result;
    }
    
    public String getPath(String type)
    {
        if (ServerType.manager.name().equals(type))
        {
            return this.pathManagerRegistry;
        }
        else
        {
            return this.pathExecutorRegistry;
        }
    }
    
    protected void checkParent(String path)
        throws Exception
    {
        String[] list = path.split("/");
        String zkPath = "";
        for (int i = 0; i < list.length - 1; i++)
        {
            String str = list[i];
            if (str.equals("") == false)
            {
                zkPath = zkPath + "/" + str;
                if (zk.exists(zkPath, false) != null)
                {
                    byte[] value = getDataWithNoNodeExceptionHandled(zkPath, false, null);
                    if (value != null)
                    {
                        String tmpVersion = new String(value);
                        if (tmpVersion.indexOf("ai-schedule-") >= 0)
                        {
                            throw new Exception(
                                "\""
                                    + zkPath
                                    + "\"  is already a schedule instance's root directory, its any subdirectory cannot as the root directory of others");
                        }
                    }
                }
            }
        }
    }
    
    protected List<ACL> getAcl()
    {
        return acl;
    }
    
    public ZooKeeper getZooKeeper()
        throws Exception
    {
        if (this.checkZookeeperState() == false)
        {
            throw new Exception("Zookeeper[" + this.getScheduleConfig().getZkConnectString() + "] connect error");
        }
        return this.zk;
    }
    
    @Override
    public void serverUnRegist(String serverId, ServerType type)
        throws Exception
    {
        String path = this.getPath(type.name()) + "/" + serverId;
        
        if (this.getZooKeeper().exists(path, false) != null)
        {
            this.getZooKeeper().delete(path, -1);
        }
    }
    
    @Override
    public String serverRegist(String serverId, ServerType type)
        throws Exception
    {
        Timestamp cur = new Timestamp(System.currentTimeMillis());
        String ip = CommonUtils.getIP();
        String hostName = CommonUtils.getHostName();
        String pid = CommonUtils.getPID();
        String path = this.getPath(type.name());
        try
        {
            if (this.getZooKeeper().exists(path, false) == null)
            {
                this.getZooKeeper().create(path, null, acl, CreateMode.PERSISTENT);
            }
        }
        catch (NodeExistsException ex)
        {
            ex.printStackTrace();
        }
        
        if (serverId != null)
        {
            if (this.getZooKeeper().exists(path + "/" + serverId, false) != null)
            {
                throw new Exception("注册失败，当前服务进程标识已经注册：" + serverId);
            }
            path = this.getZooKeeper().create(path + "/" + serverId, null, acl, CreateMode.EPHEMERAL);
        }
        else
        {
            String uuid = ip + "$" + hostName + "$" + pid + "$";
            path = this.getZooKeeper().create(path + "/" + uuid, null, acl, CreateMode.EPHEMERAL_SEQUENTIAL);
            serverId = path.substring(path.lastIndexOf("/") + 1);
        }
        
        ServerBean server = new ServerBean();
        server.setServerId(serverId);
        server.setServerType(type.name());
        server.setHeartbeatTime(cur);
        server.setHostName(hostName);
        server.setIp(ip);
        server.setPid(pid);
        server.setRegistTime(cur);
        this.getZooKeeper().setData(path, JsonUtils.toJson(server).getBytes(), -1);
        return serverId;
    }
    
    @Override
    public void heartbeat(ServerBean server)
        throws Exception
    {
        
        if (server == null || CommonUtils.isBlank(server.getServerId()) || CommonUtils.isBlank(server.getServerType()))
        {
            throw new NullPointerException();
        }
        
        this.getZooKeeper().setData(this.getPath(server.getServerType()) + "/" + server.getServerId(),
            JsonUtils.toJson(server).getBytes(),
            -1);
        
    }
    
    @Override
    public List<String> getAllTaskCodes()
        throws Exception
    {
        String path = pathTaskConfig;
        if (this.getZooKeeper().exists(path, false) == null)
        {
            return new ArrayList<String>();
        }
        List<String> codes = this.getChildrenWithNoNodeExceptionHandled(path, false);
        return codes;
    }
    
    @Override
    public List<TaskBean> getAllValidTasks()
        throws Exception
    {
        
        List<String> l = this.getAllTaskCodes();
        if (l == null || l.size() == 0)
        {
            return null;
        }
        List<TaskBean> result = new ArrayList<TaskBean>(l.size());
        for (String s : l)
        {
            TaskBean bean = this.getTaskInfoByTaskCode(s);
            if (bean != null && bean.getState().equals("U"))
            {
                result.add(bean);
            }
        }
        return result;
    }
    
    @Override
    public TaskBean getTaskInfoByTaskCode(String taskCode)
        throws Exception
    {
        String path = pathTaskConfig + "/" + taskCode;
        if (this.getZooKeeper().exists(path, false) == null)
        {
            return null;
        }
        TaskBean result = null;
        byte[] value = this.getDataWithNoNodeExceptionHandled(path, false, null);
        if (value != null)
        {
            result = JsonUtils.fromJson(new String(value), TaskBean.class);
            
        }
        return result;
    }
    
    @Override
    public String getCurScheduleServer(String taskCode)
        throws Exception
    {
        String path = pathTaskConfig + "/" + taskCode + "/c_manager";
        if (this.getZooKeeper().exists(path, false) == null)
        {
            return null;
        }
        byte[] bs = this.getDataWithNoNodeExceptionHandled(path, false, null);
        return (bs == null ? null : new String(bs));
    }
    
    @Override
    public void setReqScheduleServer(String taskCode, String serverId)
        throws Exception
    {
        byte[] value = null;
        if (serverId != null)
        {
            value = serverId.getBytes();
        }
        String path = pathTaskConfig + "/" + taskCode + "/r_manager";
        if (this.getZooKeeper().exists(path, false) == null)
        {
            this.getZooKeeper().create(path, value, acl, CreateMode.PERSISTENT);
            
        }
        else
        {
            this.getZooKeeper().setData(path, value, -1);
        }
        
    }
    
    @Override
    public void setCurScheduleServer(String taskCode, String serverId)
        throws Exception
    {
        byte[] value = null;
        if (serverId != null)
        {
            value = serverId.getBytes();
        }
        String path = pathTaskConfig + "/" + taskCode + "/c_manager";
        if (this.getZooKeeper().exists(path, false) == null)
        {
            this.getZooKeeper().create(path, value, acl, CreateMode.PERSISTENT);
            
        }
        else
        {
            this.getZooKeeper().setData(path, value, -1);
        }
        
    }
    
    @Override
    public String getReqScheduleServer(String taskCode)
        throws Exception
    {
        String path = pathTaskConfig + "/" + taskCode + "/r_manager";
        if (this.getZooKeeper().exists(path, false) == null)
        {
            return null;
        }
        byte[] bs = this.getDataWithNoNodeExceptionHandled(path, false, null);
        return (bs == null ? null : new String(bs));
    }
    
    @Override
    public List<String> getAllManageTasksByCurServer(String serverId)
        throws Exception
    {
        List<String> result = new ArrayList<String>();
        for (String taskCode : this.getAllTaskCodes())
        {
            if (serverId.equals(this.getCurScheduleServer(taskCode)))
            {
                result.add(taskCode);
            }
        }
        return result;
    }
    
    @Override
    public void createTaskGroup(TaskGroupBean group)
        throws Exception
    {
        if (group == null || group.getGroupCode() == null || "".equals(group.getGroupCode()))
        {
            throw new NullPointerException();
        }
        String path = this.pathTaskGroup + "/" + group.getGroupCode();
        if (CommonUtils.isBlank(group.getCreateTime()))
        {
            group.setCreateTime(DateUtils.formatYYYYMMddHHmmss(DateUtils.getCurrentDate()));
        }
        if (this.getZooKeeper().exists(path, false) == null)
        {
            this.getZooKeeper().create(path, JsonUtils.toJson(group).getBytes(), acl, CreateMode.PERSISTENT);
        }
        else
        {
            throw new Exception("任务组已经存在，不能重复创建。" + group.getGroupCode());
        }
    }
    
    @Override
    public void deleteTaskGroup(String groupCode)
        throws Exception
    {
        String path = this.pathTaskGroup + "/" + groupCode;
        if (this.getZooKeeper().exists(path, false) != null)
        {
            this.getZooKeeper().delete(path, -1);
        }
    }
    
    @Override
    public void createTask(TaskBean schedTask)
        throws Exception
    {
        
        checkTask(schedTask);
        
        schedTask.setVersion(1);
        schedTask.setState("E");
        
        String path = pathTaskConfig + "/" + schedTask.getTaskCode();
        if (this.getZooKeeper().exists(path, false) == null)
        {
            try
            {
                // 创建任务，当任务拆分项传入为空时，默认生成拆分项（0 代表所有）
                if (schedTask.getItems() == null || schedTask.getItems().length == 0)
                {
                    schedTask.setItems(new String[] {"0"});
                }
                this.getZooKeeper().create(path, JsonUtils.toJson(schedTask).getBytes(), acl, CreateMode.PERSISTENT);
                // 初始状态设置为停止
                this.getZooKeeper().create(path + "/sts", RunSts.stop.name().getBytes(), acl, CreateMode.PERSISTENT);
                
                this.getZooKeeper().create(path + "/item", null, acl, CreateMode.PERSISTENT);
                
                String[] items = schedTask.getItems();
                if (schedTask.isSplitRegion() == false)
                {
                    for (String item : items)
                    {
                        this.getZooKeeper().create(path + "/item/" + item, null, acl, CreateMode.PERSISTENT);
                    }
                }
                else
                {
                    // 按地市拆分,taskitem的按固定规则：items不为空=regionCode^item；items为空则=regionCode
                    String[] regionCodes = this.getScheduleConfig().getRegionCodes();
                    for (String region : regionCodes)
                    {
                        if (items.length == 1 && items[0].equals("0"))
                        {
                            this.getZooKeeper().create(path + "/item/" + region, null, acl, CreateMode.PERSISTENT);
                        }
                        else
                        {
                            for (String item : items)
                            {
                                this.getZooKeeper().create(path + "/item/" + (region + "^" + item),
                                    null,
                                    acl,
                                    CreateMode.PERSISTENT);
                            }
                        }
                    }
                }
                
            }
            catch (Exception ex)
            {
                logger.error("创建任务出现异常，回滚已生成数据！", ex);
                this.deleteTree(path);
                throw ex;
            }
            
        }
        else
        {
            throw new Exception("任务已经存在，不能重复创建。" + schedTask.getTaskCode());
        }
        
    }
    
    @Override
    public void updateTask(TaskBean schedTask)
        throws Exception
    {
        
        checkTask(schedTask);
        
		if (!"U".equals(schedTask.getState()) && !"H".equals(schedTask.getState())) {
			schedTask.setState("E");
		}
        
        String path = pathTaskConfig + "/" + schedTask.getTaskCode();
        if (this.getZooKeeper().exists(path, false) != null)
        {
            // 获取老数据
            TaskBean old = this.getTaskInfoByTaskCode(schedTask.getTaskCode());
            if (old == null)
            {
                throw new Exception("任务信息不存在，无法修改！" + schedTask.getTaskCode());
            }
            
            if (schedTask.getItems() == null || schedTask.getItems().length == 0)
            {
                schedTask.setItems(new String[] {"0"});
            }
            schedTask.setVersion(old.getVersion() + 1);
            String[] newItems = schedTask.getItems();
            String[] oldItems = old.getItems();
            Arrays.sort(newItems);
            Arrays.sort(oldItems);
            if (old.isSplitRegion() != schedTask.isSplitRegion() || !Arrays.equals(newItems, oldItems))
            {
                logger.warn("警告：任务拆分方式存在修改，将重新生成任务拆分项，原有任务分配的进程服务将清除！" + schedTask.getTaskCode());
                this.deleteTree(path + "/item");
                
                this.getZooKeeper().create(path + "/item", null, acl, CreateMode.PERSISTENT);
                
                if (schedTask.isSplitRegion() == false)
                {
                    for (String item : newItems)
                    {
                        this.getZooKeeper().create(path + "/item/" + item, null, acl, CreateMode.PERSISTENT);
                    }
                }
                else
                {
                    // 按地市拆分,taskitem的按固定规则：items不为空=regionCode^item；items为空则=regionCode
                    String[] regionCodes = this.getScheduleConfig().getRegionCodes();
                    for (String region : regionCodes)
                    {
                        if (newItems.length == 1 && newItems[0].equals("0"))
                        {
                            this.getZooKeeper().create(path + "/item/" + region, null, acl, CreateMode.PERSISTENT);
                        }
                        else
                        {
                            for (String item : newItems)
                            {
                                this.getZooKeeper().create(path + "/item/" + (region + "^" + item),
                                    null,
                                    acl,
                                    CreateMode.PERSISTENT);
                            }
                        }
                    }
                }
            }
            
        }
        else
        {
            throw new Exception("任务不存在，无法修改。" + schedTask.getTaskCode());
        }
        
        this.getZooKeeper().setData(path, JsonUtils.toJson(schedTask).getBytes(), -1);
        
    }
    
    private void checkTask(TaskBean schedTask)
        throws Exception
    {
        if (schedTask == null)
        {
            throw new NullPointerException();
        }
        
        if (CommonUtils.isBlank(schedTask.getTaskCode()))
        {
            throw new Exception("任务编码不能为空！");
        }
        if (CommonUtils.isBlank(schedTask.getTaskGroupCode()))
        {
            throw new Exception("任务组编码不能为空！");
        }
        
        if (CommonUtils.isBlank(schedTask.getTaskType()))
        {
            throw new Exception("任务类型不能为空！");
        }
        
        if (schedTask.getItems() != null && schedTask.getItems().length > 0)
        {
            for (String s : schedTask.getItems())
            {
                if (s.indexOf("^") > -1)
                {
                    throw new Exception("任务拆分项中不能包含'^'关键字符！");
                }
            }
        }
        
    }
    
    /**
     * 包含本身自己的目录
     * 
     * @param path
     * @throws Exception
     */
    public void deleteTree(String path)
        throws Exception
    {
        ZKTools.deleteTree(this.getZooKeeper(), path);
    }
    
    public String[] getTree(String path)
        throws Exception
    {
        return ZKTools.getTree(this.getZooKeeper(), path);
    }
    
    public void printTree(String path, StringWriter sw, String lineSplitChar)
        throws Exception
    {
        ZKTools.printTree(this.getZooKeeper(), path, sw, lineSplitChar);
    }
    
    @Override
    public TaskJobBean createTaskJob(String taskCode, HashMap<String, String> param, String creator)
        throws Exception
    {
        String path = pathTaskConfig + "/" + taskCode;
        TaskBean task = this.getTaskInfoByTaskCode(taskCode);
        if (task == null)
        {
            throw new Exception("不存在的任务编码：" + path);
        }
        if (!"U".equals(task.getState()))
        {
            throw new Exception("任务状态失效，无法创建！" + taskCode);
        }
        String pathJob = path + "/jobId";
        if (this.getZooKeeper().exists(pathJob, false) == null)
        {
            this.getZooKeeper().create(pathJob, null, acl, CreateMode.PERSISTENT);
        }
        else
        {
            List<String> c = this.getChildrenWithNoNodeExceptionHandled(pathJob, false);
            if (c != null && c.size() > 0)
            {
                throw new Exception("任务已经存在运行的任务流水号，不能重复创建！");
            }
        }
        List<String> items = this.getAllTaskItem(taskCode);
        if (items == null || items.size() == 0)
        {
            logger.error("任务【" + taskCode + "】不存在任务项，无法创建任务流水！！");
            return null;
        }
        HashMap<String, String> itemServers = new HashMap<String, String>();
        for (String it : items)
        {
            String server = this.getTaskItemServerId(taskCode, it);
            if (!CommonUtils.isBlank(server))
            {
                itemServers.put(it, server);
            }
        }
        
        if (itemServers.size() == 0)
        {
            logger.error("任务【" + taskCode + "】所有任务项未分派任务处理进程，无法创建任务流水！！");
            return null;
        }
        
        String jobId = genJobId(taskCode);
        TaskJobBean job = new TaskJobBean();
        job.setCreateTime(DateUtils.formatYYYYMMddHHmmss(DateUtils.getCurrentDate()));
        job.setCreator(creator);
        job.setJobId(jobId);
        job.setTaskCode(taskCode);
        job.setPriority(task.getPriority());
        
        Map<String, String> configParam = this.getTaskParam(taskCode);
        if (configParam == null)
        {
            configParam = new HashMap<String, String>();
        }
        if (param != null)
        {
            configParam.putAll(param);
        }
        job.setParam(configParam);
        
        job.setSts(JobSts.C.name());
        
        // 先创建任务流水
        this.getZooKeeper().create(pathJob + "/" + jobId, JsonUtils.toJson(job).getBytes(), acl, CreateMode.PERSISTENT);
        try
        {
            Transaction t = this.getZooKeeper().transaction();
            
            for (String item : itemServers.keySet())
            {
                String itemJobId = jobId + "$" + item;
                String serverId = itemServers.get(item);
                
                TaskItemJobBean itemJobBean = new TaskItemJobBean();
                itemJobBean.setItemJobId(itemJobId);
                itemJobBean.setServerId(serverId);
                itemJobBean.setTaskCode(taskCode);
                itemJobBean.setTaskItemId(item);
                itemJobBean.setTaskJobId(jobId);
                itemJobBean.setPriority(task.getPriority());
                itemJobBean.setTaskVersion(task.getVersion());
                itemJobBean.setTaskItemParam(job.getParam());
                itemJobBean.setSts(RunSts.start.name());
                byte[] bb = JsonUtils.toJson(itemJobBean).getBytes();
                
                // 任务流水下创建拆分项流水
                t.create(pathJob + "/" + jobId + "/" + itemJobId, bb, acl, CreateMode.PERSISTENT);
                
                // 把任务分派到server
                String psc = this.pathServerChannel + "/" + serverId;
                
                if (this.getZooKeeper().exists(psc, false) == null)
                {
                    try
                    {
                        this.getZooKeeper().create(psc, null, acl, CreateMode.PERSISTENT);
                    }
                    catch (NodeExistsException ex)
                    {
                        // 忽略
                    }
                }
                
                t.create(psc + "/" + itemJobId, bb, acl, CreateMode.PERSISTENT);
            }
            // 更改任务状态为开始start
            t.setData(pathTaskConfig + "/" + taskCode + "/sts", RunSts.start.name().getBytes(), -1);
            
            job.setSts(JobSts.R.name());
            t.setData(pathJob + "/" + jobId, JsonUtils.toJson(job).getBytes(), -1);
            
            t.commit();
        }
        catch (Exception ex)
        {
            // 分派任务出现异常，回滚创建的任务流水
            this.getZooKeeper().delete(pathJob + "/" + jobId, -1);
            throw ex;
        }
        return job;
    }
    
    private String genJobId(String taskCode)
    {
        // 同一个任务不存在并发执行的情况
        return taskCode + "$" + System.currentTimeMillis();
    }
    
    @Override
    public String startTaskItemNow(String taskCode, String taskItemId, HashMap<String, String> param, String opId)
        throws Exception
    {
        
        if (this.getZooKeeper().exists(pathTaskConfig + "/" + taskCode + "/item/" + taskItemId, false) == null)
        {
            throw new Exception("不存在的任务项:" + taskCode + "," + taskItemId);
        }
        TaskBean task = this.getTaskInfoByTaskCode(taskCode);
        if (task == null)
        {
            throw new Exception("不存在的任务编码：" + taskCode);
        }
        String serverId = this.getTaskItemServerId(taskCode, taskItemId);
        if (CommonUtils.isBlank(serverId))
        {
            throw new Exception("任务项未指派执行进程，无法执行！！" + taskCode + "," + taskItemId);
        }
        TaskJobBean jobBean = null;
        String jobId = this.getTaskJobId(taskCode);
        boolean isCreateJob = false;
        String pathJob = pathTaskConfig + "/" + taskCode + "/jobId";
        
        if (this.getZooKeeper().exists(pathJob, false) == null)
        {
            try
            {
                this.getZooKeeper().create(pathJob, null, acl, CreateMode.PERSISTENT);
            }
            catch (NodeExistsException ex)
            {
                // 忽略
            }
        }
        
        Map<String, String> configParam = this.getTaskParam(taskCode);
        if (configParam == null)
        {
            configParam = new HashMap<String, String>();
        }
        if (param != null)
        {
            configParam.putAll(param);
        }
        
        if (CommonUtils.isBlank(jobId))
        {
            jobId = genJobId(taskCode);
            jobBean = new TaskJobBean();
            jobBean.setCreateTime(DateUtils.formatYYYYMMddHHmmss(DateUtils.getCurrentDate()));
            jobBean.setCreator(opId);
            jobBean.setJobId(jobId);
            jobBean.setTaskCode(taskCode);
            jobBean.setPriority(task.getPriority());
            jobBean.setParam(configParam);
            
            jobBean.setSts(JobSts.C.name());
            // 先创建任务流水
            this.getZooKeeper().create(pathJob + "/" + jobId,
                JsonUtils.toJson(jobBean).getBytes(),
                acl,
                CreateMode.PERSISTENT);
            isCreateJob = true;
        }
        else
        {
            jobBean = this.getTaskJobByJobId(taskCode, jobId);
            if (TaskType.valueOf(task.getTaskType()).isComplex())
            {
                List<TaskItemJobBean> itemJobs = this.getAllTaskItemJobBean(taskCode, jobId);
                for (TaskItemJobBean ij : itemJobs)
                {
                    if (ij.getTaskItemId().equals(taskItemId))
                    {
                        throw new Exception("任务项正在处理中，作业流水：" + jobId + "，TF/Complex类型任务项不能重复启动！");
                    }
                }
            }
            
        }
        String itemJobId = jobId + "$" + taskItemId + "$" + System.currentTimeMillis();
        try
        {
            Transaction t = this.getZooKeeper().transaction();
            
            TaskItemJobBean itemJobBean = new TaskItemJobBean();
            itemJobBean.setItemJobId(itemJobId);
            itemJobBean.setServerId(serverId);
            itemJobBean.setTaskCode(taskCode);
            itemJobBean.setTaskItemId(taskItemId);
            itemJobBean.setTaskJobId(jobId);
            itemJobBean.setPriority(task.getPriority());
            itemJobBean.setTaskVersion(task.getVersion());
            itemJobBean.setTaskItemParam(configParam);
            itemJobBean.setSts(RunSts.start.name());
            byte[] bb = JsonUtils.toJson(itemJobBean).getBytes();
            
            // 任务流水下创建拆分项流水
            t.create(pathJob + "/" + jobId + "/" + itemJobId, bb, acl, CreateMode.PERSISTENT);
            
            // 把任务分派到server
            String psc = this.pathServerChannel + "/" + serverId;
            
            if (this.getZooKeeper().exists(psc, false) == null)
            {
                try
                {
                    this.getZooKeeper().create(psc, null, acl, CreateMode.PERSISTENT);
                }
                catch (NodeExistsException ex)
                {
                    // 忽略
                }
            }
            
            t.create(psc + "/" + itemJobId, bb, acl, CreateMode.PERSISTENT);
            
            // 更改任务状态为开始start
            t.setData(pathTaskConfig + "/" + taskCode + "/sts", RunSts.start.name().getBytes(), -1);
            
            jobBean.setSts(JobSts.R.name());
            t.setData(pathJob + "/" + jobId, JsonUtils.toJson(jobBean).getBytes(), -1);
            
            t.commit();
        }
        catch (Exception ex)
        {
            // 分派任务出现异常，回滚创建的任务流水
            if (isCreateJob)
            {
                this.getZooKeeper().delete(pathJob + "/" + jobId, -1);
            }
            throw ex;
        }
        if (isCreateJob)
        {
            SchedulerLogger.createTaskLog(taskCode,
                task.getTaskName(),
                task.getVersion() + "",
                jobId,
                jobBean.getCreateTime(),
                "C");
            SchedulerLogger.addTaskLogDtl(taskCode, task.getVersion() + "", jobId, "outside", "", opId, "任务拆分项启动", "");
        }
        SchedulerLogger.addTaskLogDtl(taskCode,
            task == null ? "-1" : task.getVersion() + "",
            jobId,
            "outside",
            taskItemId,
            opId,
            "任务拆分项创建:" + itemJobId,
            "");
        return jobId;
    }
    
    @Override
    public void stopTaskItemJob(TaskItemJobBean itemJobBean)
        throws Exception
    {
        
        if (itemJobBean == null)
        {
            throw new Exception("传入参数为空！！");
        }
        itemJobBean.setSts(RunSts.stop.name());
        Transaction t = this.getZooKeeper().transaction();
        byte[] bb = JsonUtils.toJson(itemJobBean).getBytes();
        t.setData(pathTaskConfig + "/" + itemJobBean.getTaskCode() + "/jobId/" + itemJobBean.getTaskJobId() + "/"
            + itemJobBean.getItemJobId(), bb, -1);
        t.setData(this.pathServerChannel + "/" + itemJobBean.getServerId() + "/" + itemJobBean.getItemJobId(), bb, -1);
        t.commit();
        
    }
    
    @Override
    public List<String> getAllTaskItem(String taskCode)
        throws Exception
    {
        String path = pathTaskConfig + "/" + taskCode + "/item";
        if (this.getZooKeeper().exists(path, false) == null)
        {
            return null;
        }
        
        // 静态数据，不需要NoNodeException
        List<String> items = this.getChildrenWithNoNodeExceptionHandled(path, false);
        Collections.sort(items);
        return items;
    }
    
    @Override
    public void deleteTaskItemJobFromServer(String serverId, String itemJobId)
        throws Exception
    {
        String path = this.pathServerChannel + "/" + serverId + "/" + itemJobId;
        if (this.getZooKeeper().exists(path, false) != null)
        {
            this.getZooKeeper().delete(path, -1);
        }
    }
    
    @Override
    public void deleteTaskItemJobFromTask(String taskCode, String taskJobId, String itemJobId)
        throws Exception
    {
        String path = this.pathTaskConfig + "/" + taskCode + "/jobId/" + taskJobId + "/" + itemJobId;
        if (this.getZooKeeper().exists(path, false) != null)
        {
            this.getZooKeeper().delete(path, -1);
        }
    }
    
    @Override
    public String getTaskRunSts(String taskCode)
        throws Exception
    {
        String path = pathTaskConfig + "/" + taskCode + "/sts";
        if (this.getZooKeeper().exists(path, false) == null)
        {
            return null;
        }
        byte[] sts = this.getDataWithNoNodeExceptionHandled(path, false, null);
        return sts == null ? null : new String(sts);
    }
    
    @Override
    public void setTaskRunSts(String taskCode, String sts)
        throws Exception
    {
        
        String path = pathTaskConfig + "/" + taskCode + "/sts";
        if (this.getZooKeeper().exists(path, false) == null)
        {
            this.getZooKeeper().create(path, sts.getBytes(), acl, CreateMode.PERSISTENT);
        }
        else
        {
            this.getZooKeeper().setData(path, sts.getBytes(), -1);
        }
        
    }
    
    @Override
    public String getTaskJobId(String taskCode)
        throws Exception
    {
        String path = pathTaskConfig + "/" + taskCode + "/jobId";
        if (this.getZooKeeper().exists(path, false) == null)
        {
            return "";
        }
        else
        {
            List<String> list = this.getChildrenWithNoNodeExceptionHandled(path, false);
            if (list == null || list.size() == 0)
            {
                return "";
            }
            return list.get(0);
        }
        
    }
    
    @Override
    public TaskJobBean getTaskJobByJobId(String taskCode, String jobId)
        throws Exception
    {
        String path = pathTaskConfig + "/" + taskCode + "/jobId/" + jobId;
        if (this.getZooKeeper().exists(path, false) == null)
        {
            return null;
        }
        else
        {
            byte[] values = this.getDataWithNoNodeExceptionHandled(path, false, null);
            if (values == null)
            {
                return null;
            }
            else
            {
                return JsonUtils.fromJson(new String(values), TaskJobBean.class);
            }
        }
    }
    
    @Override
    public boolean deleteTaskJob(String taskCode, String jobId)
        throws Exception
    {
        String path = pathTaskConfig + "/" + taskCode + "/jobId/" + jobId;
        if (this.getZooKeeper().exists(path, false) != null)
        {
            this.getZooKeeper().delete(path, -1);
        }
        return true;
    }
    
    @Override
    public List<String> getAllTaskItemJobId(String taskCode, String jobId)
        throws Exception
    {
        if (CommonUtils.isBlank(taskCode) || CommonUtils.isBlank(jobId))
        {
            return null;
        }
        List<String> itemJobIds = null;
        String pathItem = pathTaskConfig + "/" + taskCode + "/jobId/" + jobId;
        if (this.getZooKeeper().exists(pathItem, false) != null)
        {
            
            itemJobIds = this.getChildrenWithNoNodeExceptionHandled(pathItem, false);
        }
        
        return itemJobIds;
    }
    
    @Override
    public List<TaskItemJobBean> getAllTaskItemJobBean(String taskCode, String jobId)
        throws Exception
    {
        List<TaskItemJobBean> result = new ArrayList<TaskItemJobBean>();
        List<String> list = getAllTaskItemJobId(taskCode, jobId);
        if (list != null && list.size() > 0)
        {
            for (String itemJobId : list)
            {
                // modified by sundd:此处不能使用getDataWithNoNodeExceptionHandled，需要特殊处理
                try
                {
                    byte[] b =
                        this.getZooKeeper().getData(pathTaskConfig + "/" + taskCode + "/jobId/" + jobId + "/"
                            + itemJobId,
                            false,
                            null);
                    if (b == null)
                    {
                        TaskItemJobBean j = new TaskItemJobBean();
                        j.setItemJobId(itemJobId);
                        result.add(j);
                    }
                    else
                    {
                        result.add(JsonUtils.fromJson(new String(b), TaskItemJobBean.class));
                    }
                }
                catch (NoNodeException e)
                {
                    // do nothing不加入result
                }
            }
        }
        return result;
    }
    
    @Override
    public List<String> getAllServersByTaskCode(String taskCode)
        throws Exception
    {
        List<String> items = this.getAllTaskItem(taskCode);
        if (items == null || items.size() == 0)
        {
            return null;
        }
        String pathItem = pathTaskConfig + "/" + taskCode + "/item";
        List<String> servers = new ArrayList<String>(items.size());
        for (String item : items)
        {
            if (this.getZooKeeper().exists(pathItem + "/" + item + "/assign_server", false) == null)
            {
                continue;
            }
            
            byte[] b = this.getDataWithNoNodeExceptionHandled(pathItem + "/" + item + "/assign_server", false, null);
            if (b != null)
            {
                String server = new String(b);
                if (!CommonUtils.isBlank(server) && !servers.contains(server))
                {
                    servers.add(server);
                }
            }
        }
        return servers;
    }
    
    @Override
    public String getTaskItemServerId(String taskCode, String itemCode)
        throws Exception
    {
        String path = this.pathTaskConfig + "/" + taskCode + "/item/" + itemCode + "/assign_server";
        if (this.getZooKeeper().exists(path, false) != null)
        {
            byte[] b = this.getDataWithNoNodeExceptionHandled(path, false, null);
            if (b != null)
            {
                return new String(b);
            }
        }
        return "";
    }
    
    @Override
    public TaskGroupBean[] getTaskGroup()
        throws Exception
    {
        
        if (this.getZooKeeper().exists(pathTaskGroup, false) == null)
        {
            return null;
        }
        List<String> groups = this.getChildrenWithNoNodeExceptionHandled(pathTaskGroup, false);
        if (groups == null || groups.size() == 0)
        {
            return null;
        }
        TaskGroupBean[] beans = new TaskGroupBean[groups.size()];
        int i = 0;
        for (String g : groups)
        {
            byte[] b = this.getDataWithNoNodeExceptionHandled(pathTaskGroup + "/" + g, false, null);
            if (b == null)
            {
                beans[i] = new TaskGroupBean();
                beans[i].setGroupCode(g);
                beans[i].setGroupName(g);
            }
            else
            {
                beans[i] = JsonUtils.fromJson(new String(b), TaskGroupBean.class);
            }
            i++;
        }
        return beans;
    }
    
    @Override
    public TaskBean[] getTasksByGroupCode(String groupCode)
        throws Exception
    {
        if (CommonUtils.isBlank(groupCode))
        {
            return null;
        }
        List<String> tasks = this.getAllTaskCodes();
        if (tasks == null || tasks.size() == 0)
        {
            return null;
        }
        List<TaskBean> list = new ArrayList<TaskBean>();
        for (String taskCode : tasks)
        {
            TaskBean bean = this.getTaskInfoByTaskCode(taskCode);
            if (bean != null && groupCode.equals(bean.getTaskGroupCode()))
            {
                list.add(bean);
            }
        }
        return list.toArray(new TaskBean[0]);
    }
    
    @Override
    public List<TaskItemJobBean> getAllTaskJobsByServerId(String serverId)
        throws Exception
    {
        String path = pathServerChannel + "/" + serverId;
        List<TaskItemJobBean> result = new ArrayList<TaskItemJobBean>();
        if (this.getZooKeeper().exists(path, false) != null)
        {
            List<String> itemJobIds = this.getChildrenWithNoNodeExceptionHandled(path, false);
            for (String id : itemJobIds)
            {
                byte[] b = this.getDataWithNoNodeExceptionHandled(path + "/" + id, false, null);
                if (b != null)
                {
                    result.add(JsonUtils.fromJson(new String(b), TaskItemJobBean.class));
                }
            }
        }
        return result;
    }
    
    @Override
    public void setTaskServerRunSts(String serverId, RunSts sts)
        throws Exception
    {
        if (this.getZooKeeper().exists(pathServerChannel + "/" + serverId, false) != null)
        {
            this.getZooKeeper().setData(pathServerChannel + "/" + serverId, sts.name().getBytes(), -1);
        }
    }
    
    @Override
    public String getTaskServerRunSts(String serverId)
        throws Exception
    {
        if (this.getZooKeeper().exists(pathServerChannel + "/" + serverId, false) != null)
        {
            byte[] stsb = this.getDataWithNoNodeExceptionHandled(pathServerChannel + "/" + serverId, false, null);
            if (stsb != null)
            {
                return new String(stsb);
            }
        }
        return null;
    }
    
    @Override
    public void assignServer2TaskItem(String taskCode, String item, String server)
        throws Exception
    {
        String path = this.pathTaskConfig + "/" + taskCode + "/item/" + item;
        if (this.getZooKeeper().exists(path, false) == null)
        {
            throw new Exception("不存在的任务项：" + path);
        }
        if (this.getZooKeeper().exists(path + "/assign_server", false) == null)
        {
            this.getZooKeeper().create(path + "/assign_server",
                server == null ? null : server.getBytes(),
                acl,
                CreateMode.PERSISTENT);
        }
        else
        {
            this.getZooKeeper().setData(path + "/assign_server", server == null ? null : server.getBytes(), -1);
        }
        
    }
    
    @Override
    public List<String> getTaskItemsByServerId(String taskCode, String serverId)
        throws Exception
    {
        List<String> taskItems = this.getAllTaskItem(taskCode);
        if (taskItems == null || taskItems.size() == 0)
        {
            return null;
        }
        List<String> result = new ArrayList<String>();
        for (String item : taskItems)
        {
            String tmp = this.getTaskItemServerId(taskCode, item);
            if (serverId.equals(tmp))
            {
                result.add(item);
            }
        }
        
        return result;
    }
    
    @Override
    public void deleteTask(String taskCode)
        throws Exception
    {
        String jobId = this.getTaskJobId(taskCode);
        if (!CommonUtils.isBlank(jobId))
        {
            throw new Exception("任务正在运行，存在正在执行的作业流水，请先停止任务再删除！" + jobId);
        }
        ZKTools.deleteTree(this.getZooKeeper(), this.pathTaskConfig + "/" + taskCode);
    }
    
    @Override
    public void applyTaskItem(String task, String item, String processorId)
        throws Exception
    {
        String path = pathTaskConfig + "/" + task + "/item/" + item;
        if (this.getZooKeeper().exists(path, false) == null)
        {
            throw new Exception("task item is not exists!" + task + ",item:" + item);
        }
        
        if (this.getZooKeeper().exists(path + "/c_processor", false) == null)
        {
            this.getZooKeeper().create(path + "/c_processor", processorId.getBytes(), acl, CreateMode.PERSISTENT);
        }
        else
        {
            this.getZooKeeper().setData(path + "/c_processor", processorId.getBytes(), -1);
        }
    }
    
    @Override
    public String getTaskItemCurProcessorId(String task, String item)
        throws Exception
    {
        String path = pathTaskConfig + "/" + task + "/item/" + item + "/c_processor";
        if (this.getZooKeeper().exists(path, false) == null)
        {
            return null;
        }
        byte[] b = this.getDataWithNoNodeExceptionHandled(path, false, null);
        if (b != null)
        {
            return new String(b);
        }
        return null;
    }
    
    @Override
    public void releaseTaskItem(String task, String item, String pid)
        throws Exception
    {
        String path = pathTaskConfig + "/" + task + "/item/" + item + "/c_processor";
        if (this.getZooKeeper().exists(path, false) == null)
        {
            return;
        }
        byte[] bb = this.getDataWithNoNodeExceptionHandled(path, false, null);
        if (bb == null)
        {
            return;
        }
        String s = new String(bb);
        if (s.equals(pid))
        {
            this.getZooKeeper().setData(path, null, -1);
        }
        else
        {
            logger.warn("任务项已被其他进程[" + s + "]占有，无法释放。" + pid);
        }
        
    }
    
    @Override
    public void finishTaskItemJob(String serverId, String taskCode, String taskItemId, String taskJobId,
        String itemJobId)
        throws Exception
    {
        Transaction t = this.getZooKeeper().transaction();
        try
        {
            String path = this.pathServerChannel + "/" + serverId + "/" + itemJobId;
            if (this.getZooKeeper().exists(path, false) != null)
            {
                t.delete(path, -1);
            }
            
            path = this.pathTaskConfig + "/" + taskCode + "/jobId/" + taskJobId + "/" + itemJobId;
            if (this.getZooKeeper().exists(path, false) != null)
            {
                t.delete(path, -1);
            }
            
            path = pathTaskConfig + "/" + taskCode + "/item/" + taskItemId + "/c_processor";
            if (this.getZooKeeper().exists(path, false) == null)
            {
                return;
            }
            byte[] bb = this.getDataWithNoNodeExceptionHandled(path, false, null);
            if (bb == null)
            {
                return;
            }
            String s = new String(bb);
            if (s.equals(serverId))
            {
                t.setData(path, null, -1);
            }
            else
            {
                logger.warn("任务项已被其他进程[" + s + "]占有，无法释放。" + serverId);
            }
        }
        finally
        {
            t.commit();
        }
    }
    
    @Override
    public Map<String, String> getTaskParam(String taskCode)
        throws Exception
    {
        Map<String, String> param = new HashMap<String, String>();
        String path = this.pathTaskConfig + "/" + taskCode + "/param";
        if (this.getZooKeeper().exists(path, false) == null)
        {
            return param;
        }
        List<String> pl = this.getChildrenWithNoNodeExceptionHandled(path, false);
        if (pl == null || pl.size() == 0)
        {
            return param;
        }
        
        for (String pCode : pl)
        {
            byte[] values = this.getDataWithNoNodeExceptionHandled(path + "/" + pCode, false, null);
            param.put(pCode, values == null ? null : new String(values));
        }
        return param;
    }
    
    @Override
    public String getFaultProcessFlag()
        throws Exception
    {
        String path = this.pathStaticData + "/fault_process_flag";
        if (this.getZooKeeper().exists(path, false) != null)
        {
            byte[] val = this.getDataWithNoNodeExceptionHandled(path, false, null);
            if (val != null)
            {
                return new String(val);
            }
        }
        return null;
    }
    
    @Override
    public void setFaultProcessFlag(String value)
        throws Exception
    {
        
        String path = this.pathStaticData + "/fault_process_flag";
        if (this.getZooKeeper().exists(path, false) == null)
        {
            this.getZooKeeper().create(path, value == null ? null : value.getBytes(), acl, CreateMode.PERSISTENT);
        }
        else
        {
            this.getZooKeeper().setData(path, value == null ? null : value.getBytes(), -1);
        }
        
    }
    
    @Override
    public List<TaskBean> getTaskBeanFuzzy(String groupCode, String qryTaskCode, String qryTaskName, String taskType,
        String state)
        throws Exception
    {
        List<TaskBean> result = new ArrayList<TaskBean>();
        List<String> taskCodes = this.getAllTaskCodes();
        if (taskCodes == null || taskCodes.size() == 0)
        {
            return result;
        }
        
        for (String code : taskCodes)
        {
            if (!CommonUtils.isBlank(qryTaskCode) && code.indexOf(qryTaskCode) < 0)
            {
                continue;
            }
            TaskBean bean = this.getTaskInfoByTaskCode(code);
            if (bean == null)
            {
                continue;
            }
            
            if (!CommonUtils.isBlank(state) && !state.equals(bean.getState()))
            {
                continue;
            }
            
            if (!CommonUtils.isBlank(taskType) && !taskType.equals(bean.getTaskType()))
            {
                continue;
            }
            
            if (!CommonUtils.isBlank(groupCode) && !groupCode.equals(bean.getTaskGroupCode()))
            {
                continue;
            }
            if (!CommonUtils.isBlank(qryTaskName) && !CommonUtils.isBlank(bean.getTaskName())
                && bean.getTaskName().indexOf(qryTaskName) < 0)
            {
                continue;
            }
            result.add(bean);
        }
        return result;
    }
    
    @Override
    public TaskView[] getTaskView(String groupCode, String qryTaskCode, String qryTaskName)
        throws Exception
    {
        List<String> taskCodes = this.getAllTaskCodes();
        if (taskCodes == null || taskCodes.size() == 0)
        {
            return null;
        }
        TaskGroupBean[] groups = this.getTaskGroup();
        List<TaskView> result = new ArrayList<TaskView>();
        
        List<TaskBean> beans = getTaskBeanFuzzy(groupCode, qryTaskCode, qryTaskName, null, null);
        if (beans == null || beans.size() == 0)
        {
            return null;
        }
        for (TaskBean bean : beans)
        {
            TaskView tv = new TaskView();
            tv.setGroupCode(bean.getTaskGroupCode());
            for (TaskGroupBean g : groups)
            {
                if (g.getGroupCode().equals(bean.getTaskGroupCode()))
                {
                    tv.setGroupName(g.getGroupName());
                }
            }
            tv.setSplitRegion(bean.isSplitRegion());
            tv.setState(bean.getState());
            tv.setTaskName(bean.getTaskName());
            tv.setTaskCode(bean.getTaskCode());
            tv.setTaskType(bean.getTaskType());
            tv.setVersion(bean.getVersion());
            
            tv.setAssignType(bean.getAssignType());
            tv.setCreateTime(bean.getCreateTime());
            tv.setDepends(bean.getDepends());
            tv.setEndTime(bean.getEndTime());
            tv.setExecuteNum(bean.getExecuteNum());
            tv.setFaultProcessMethod(bean.getFaultProcessMethod());
            tv.setIdleSleepTime(bean.getIdleSleepTime());
            tv.setItems(bean.getItems());
            tv.setProcessClass(bean.getProcessClass());
            tv.setScanIntervalTime(bean.getScanIntervalTime());
            tv.setStartTime(bean.getStartTime());
            tv.setTaskDesc(bean.getTaskDesc());
            tv.setThreadNum(bean.getThreadNum());
            
            List<String> items = this.getAllTaskItem(bean.getTaskCode());
            Map<String, String> itemAppCodes = new HashMap<String, String>();
            if (items != null && items.size() > 0)
            {
                for (String item : items)
                {
                    String appCode = this.getTaskItemServerId(bean.getTaskCode(), item);
                    itemAppCodes.put(item, appCode);
                }
            }
            tv.setItemAppCodes(itemAppCodes);
            // 获取任务运行状态
            String sts = this.getTaskRunSts(bean.getTaskCode());
            if (CommonUtils.isBlank(sts))
            {
                sts = RunSts.stop.name();
            }
            tv.setTaskRunSts(sts);
            result.add(tv);
        }
        return result.toArray(new TaskView[0]);
    }
    
    @Override
    public TaskView[] getTaskViewByAppCode(List<String> appCodes)
        throws Exception
    {
        if (appCodes == null || appCodes.size() == 0)
        {
            return null;
        }
        
        List<String> taskCodes = this.getAllTaskCodes();
        if (taskCodes == null || taskCodes.size() == 0)
        {
            return null;
        }
        TaskGroupBean[] groups = this.getTaskGroup();
        List<TaskView> result = new ArrayList<TaskView>();
        for (String code : taskCodes)
        {
            List<String> items = this.getAllTaskItem(code);
            if (items != null && items.size() > 0)
            {
                Map<String, String> itemAppCodes = new HashMap<String, String>();
                for (String item : items)
                {
                    String appCode = this.getTaskItemServerId(code, item);
                    if (appCodes.contains(appCode))
                    {
                        itemAppCodes.put(item, appCode);
                    }
                }
                
                if (!itemAppCodes.isEmpty())
                {
                    TaskView tv = new TaskView();
                    TaskBean bean = this.getTaskInfoByTaskCode(code);
                    tv.setGroupCode(bean.getTaskGroupCode());
                    for (TaskGroupBean g : groups)
                    {
                        if (g.getGroupCode().equals(bean.getTaskGroupCode()))
                        {
                            tv.setGroupName(g.getGroupName());
                        }
                    }
                    tv.setSplitRegion(bean.isSplitRegion());
                    tv.setState(bean.getState());
                    tv.setTaskName(bean.getTaskName());
                    tv.setTaskCode(bean.getTaskCode());
                    tv.setTaskType(bean.getTaskType());
                    tv.setVersion(bean.getVersion());
                    tv.setItemAppCodes(itemAppCodes);
                    
                    tv.setAssignType(bean.getAssignType());
                    tv.setCreateTime(bean.getCreateTime());
                    tv.setDepends(bean.getDepends());
                    tv.setEndTime(bean.getEndTime());
                    tv.setExecuteNum(bean.getExecuteNum());
                    tv.setFaultProcessMethod(bean.getFaultProcessMethod());
                    tv.setIdleSleepTime(bean.getIdleSleepTime());
                    tv.setItems(bean.getItems());
                    tv.setProcessClass(bean.getProcessClass());
                    tv.setScanIntervalTime(bean.getScanIntervalTime());
                    tv.setStartTime(bean.getStartTime());
                    tv.setTaskDesc(bean.getTaskDesc());
                    tv.setThreadNum(bean.getThreadNum());
                    // 获取任务运行状态
                    String sts = this.getTaskRunSts(bean.getTaskCode());
                    if (CommonUtils.isBlank(sts))
                    {
                        sts = "stop";
                    }
                    tv.setTaskRunSts(sts);
                    result.add(tv);
                }
            }
        }
        return result.toArray(new TaskView[0]);
    }
    
    @Override
    public CfgTf getCfgTfByCode(String taskCode)
        throws Exception
    {
        String path = this.pathTaskConfig + "/" + taskCode + "/cfgTf";
        CfgTfBean bean = this.getCfgTfBean(taskCode);
        if (bean == null)
        {
            return null;
        }
        CfgTf result = new CfgTf();
        result.setCfgTfCode(bean.getCfgTfCode());
        result.setErrorSql(bean.getErrorSql());
        result.setFinishSql(bean.getFinishSql());
        result.setPkColumns(bean.getPkColumns());
        result.setProcessingSql(bean.getProcessingSql());
        result.setQuerySql(bean.getQuerySql());
        result.setRemarks(bean.getRemarks());
        result.setSrcDbAcctCode(bean.getSrcDbAcctCode());
        result.setSrcTableName(bean.getSrcTableName());
        result.setCountSql(bean.getCountSql());
        
        List<String> dtls = this.getChildrenWithNoNodeExceptionHandled(path, false);
        if (dtls == null || dtls.size() == 0)
        {
            return result;
        }
        List<CfgTfDtl> dtlList = new ArrayList<CfgTfDtl>(dtls.size());
        for (String dtl : dtls)
        {
            byte[] b = this.getDataWithNoNodeExceptionHandled(path + "/" + dtl, false, null);
            if (b == null)
            {
                logger.error("任务【" + bean.getCfgTfCode() + "】tf详情数据配置为空,忽略此配置！" + dtl);
                continue;
            }
            CfgTfDtlBean dtlBean = JsonUtils.fromJson(new String(b), CfgTfDtlBean.class);
            if (dtlBean == null)
            {
                logger.error("任务【" + bean.getCfgTfCode() + "】tf详情数据配置有误，忽略此配置！" + dtl);
                continue;
            }
            CfgTfDtl tfDtl = new CfgTfDtl();
            tfDtl.setCfgTfCode(dtlBean.getCfgTfCode());
            tfDtl.setCfgTfDtlId(dtlBean.getCfgTfDtlId());
            tfDtl.setDbAcctCode(dtlBean.getDbAcctCode());
            tfDtl.setFinishSql(dtlBean.getFinishSql());
            tfDtl.setRemarks(dtlBean.getRemarks());
            tfDtl.setTableName(dtlBean.getTableName());
            tfDtl.setTfType(dtlBean.getTfType());
            
            List<String> mapping = this.getChildrenWithNoNodeExceptionHandled(path + "/" + dtl, false);
            if (mapping != null && mapping.size() > 0)
            {
                List<CfgTfMapping> mappingList = new ArrayList<CfgTfMapping>(mapping.size());
                for (String m : mapping)
                {
                    byte[] bm = this.getDataWithNoNodeExceptionHandled(path + "/" + dtl + "/" + m, false, null);
                    if (bm == null)
                    {
                        logger.error("任务【" + bean.getCfgTfCode() + "】tf映射数据配置为空，忽略此配置！" + dtl + "/" + m);
                        continue;
                    }
                    CfgTfMapping tfMapping = JsonUtils.fromJson(new String(bm), CfgTfMapping.class);
                    if (tfMapping == null)
                    {
                        logger.error("任务【" + bean.getCfgTfCode() + "】tf映射数据配置有误，忽略此配置！" + dtl + "/" + m);
                        continue;
                    }
                    mappingList.add(tfMapping);
                }
                tfDtl.setObjCfgTfMapping(mappingList.toArray(new CfgTfMapping[0]));
            }
            dtlList.add(tfDtl);
        }
        
        result.setObjCfgTfDtl(dtlList.toArray(new CfgTfDtl[0]));
        return result;
    }
    
    @Override
    public void createOrUpdCfgTf(CfgTfBean tfBean)
        throws Exception
    {
        
        if (tfBean == null || CommonUtils.isBlank(tfBean.getCfgTfCode()))
        {
            throw new Exception("传入的参数为空或tf编码为空！");
        }
        String path = this.pathTaskConfig + "/" + tfBean.getCfgTfCode() + "/cfgTf";
        if (this.getZooKeeper().exists(path, false) == null)
        {
            this.getZooKeeper().create(path, JsonUtils.toJson(tfBean).getBytes(), acl, CreateMode.PERSISTENT);
        }
        else
        {
            this.getZooKeeper().setData(path, JsonUtils.toJson(tfBean).getBytes(), -1);
        }
    }
    
    @Override
    public void deleteCfgTf(String taskCode)
        throws Exception
    {
        String path = this.pathTaskConfig + "/" + taskCode + "/cfgTf";
        if (this.getZooKeeper().exists(path, false) != null)
        {
            this.deleteTree(path);
        }
        
    }
    
    @Override
    public void createCfgTfDtl(CfgTfDtlBean[] tfDtls)
        throws Exception
    {
        if (tfDtls == null || tfDtls.length == 0)
        {
            return;
        }
        for (CfgTfDtlBean dtl : tfDtls)
        {
            if (dtl == null || CommonUtils.isBlank(dtl.getCfgTfCode()))
            {
                throw new Exception("CfgTfCode为空，无法创建！");
            }
            String path = this.pathTaskConfig + "/" + dtl.getCfgTfCode() + "/cfgTf";
            if (this.getZooKeeper().exists(path, false) == null)
            {
                throw new Exception("不存在的tf配置数据，无法创建！" + path);
            }
            String dtlPath = this.getZooKeeper().create(path + "/dtl^", null, acl, CreateMode.PERSISTENT_SEQUENTIAL);
            String dtlId = dtlPath.substring(dtlPath.lastIndexOf("/") + 1);
            dtl.setCfgTfDtlId(dtlId);
            this.getZooKeeper().setData(path + "/" + dtlId, JsonUtils.toJson(dtl).getBytes(), -1);
        }
    }
    
    @Override
    public void createCfgTfMapping(String cfgTfCode, CfgTfMapping[] mappings)
        throws Exception
    {
        if (CommonUtils.isBlank(cfgTfCode) || mappings == null || mappings.length == 0)
        {
            throw new Exception("传入参数有误，不创建！" + cfgTfCode + "/" + mappings);
        }
        String path = this.pathTaskConfig + "/" + cfgTfCode + "/cfgTf";
        if (this.getZooKeeper().exists(path, false) == null)
        {
            throw new Exception("不存在的tf配置！" + path);
        }
        for (CfgTfMapping m : mappings)
        {
            if (m == null || CommonUtils.isBlank(m.getCfgTfDtlId()))
            {
                throw new Exception("创建tfmapping数据时，tfDtlId为空！" + m);
            }
            
            if (this.getZooKeeper().exists(path + "/" + m.getCfgTfDtlId(), false) == null)
            {
                throw new Exception("不存在的tfdtl配置数据！" + path);
            }
            String mappingPath =
                this.getZooKeeper().create(path + "/" + m.getCfgTfDtlId() + "/mapping^",
                    null,
                    acl,
                    CreateMode.PERSISTENT_SEQUENTIAL);
            String mappingId = mappingPath.substring(mappingPath.lastIndexOf("/") + 1);
            m.setMappingId(mappingId);
            this.getZooKeeper().setData(path + "/" + m.getCfgTfDtlId() + "/" + mappingId,
                JsonUtils.toJson(m).getBytes(),
                -1);
        }
    }
    
    @Override
    public void createTaskParam(String taskCode, String paramKey, String paramValue)
        throws Exception
    {
        String path = this.pathTaskConfig + "/" + taskCode + "/param";
        if (this.getZooKeeper().exists(path, false) == null)
        {
            this.getZooKeeper().create(path, null, acl, CreateMode.PERSISTENT);
        }
        this.getZooKeeper().create(path + "/" + paramKey,
            paramValue == null ? null : paramValue.getBytes(),
            acl,
            CreateMode.PERSISTENT);
    }
    
    @Override
    public void createTaskParamDesc(String taskCode, Map<String, String> paramDescMap)
        throws Exception
    {
        if (paramDescMap == null || paramDescMap.size() == 0)
        {
            return;
        }
        String path = this.pathTaskConfig + "/" + taskCode;
        if (this.getZooKeeper().exists(path, false) == null)
        {
            throw new Exception("当前任务不存在:" + taskCode);
        }
        if (this.getZooKeeper().exists(path + "/paramDesc", false) == null)
        {
            try
            {
                this.getZooKeeper().create(path + "/paramDesc", null, acl, CreateMode.PERSISTENT);
            }
            catch (NodeExistsException ex)
            {
            }
        }
        Transaction t = this.getZooKeeper().transaction();
        for (String key : paramDescMap.keySet())
        {
            String descPath = path + "/paramDesc/" + key;
            String value = paramDescMap.get(key);
            if (this.getZooKeeper().exists(descPath, false) == null)
            {
                t.create(descPath, value == null ? null : value.getBytes(), acl, CreateMode.PERSISTENT);
            }
            else
            {
                t.setData(descPath, value == null ? null : value.getBytes(), -1);
            }
        }
        t.commit();
        
    }
    
    @Override
    public Map<String, String> getTaskParamDesc(String taskCode)
        throws Exception
    {
        String path = this.pathTaskConfig + "/" + taskCode;
        if (this.getZooKeeper().exists(path, false) == null)
        {
            throw new Exception("当前任务不存在:" + taskCode);
        }
        if (this.getZooKeeper().exists(path + "/paramDesc", false) == null)
        {
            return null;
        }
        
        List<String> list = this.getChildrenWithNoNodeExceptionHandled(path + "/paramDesc", false);
        Map<String, String> result = new HashMap<String, String>();
        if (list != null && list.size() > 0)
        {
            for (String key : list)
            {
                byte[] v = this.getDataWithNoNodeExceptionHandled(path + "/paramDesc/" + key, false, null);
                if (v != null)
                {
                    result.put(key, new String(v));
                }
                else
                {
                    result.put(key, null);
                }
            }
        }
        
        return result;
    }
    
    @Override
    public void deleteTaskParam(String taskCode, String paramKey)
        throws Exception
    {
        
        String path = this.pathTaskConfig + "/" + taskCode + "/param/" + paramKey;
        if (this.getZooKeeper().exists(path, false) != null)
        {
            this.deleteTree(path);
        }
    }
    
    @Override
    public void deleteCfgTfDtl(String taskCode, String tfDtlId)
        throws Exception
    {
        String path = this.pathTaskConfig + "/" + taskCode + "/cfgTf/" + tfDtlId;
        if (this.getZooKeeper().exists(path, false) != null)
        {
            this.deleteTree(path);
        }
    }
    
    @Override
    public void deleteCfgTfMapping(String taskCode, String tfDtlId, List<String> mappingIds)
        throws Exception
    {
        String path = this.pathTaskConfig + "/" + taskCode + "/cfgTf/" + tfDtlId;
        if (this.getZooKeeper().exists(path, false) != null)
        {
            for (String mappingId : mappingIds)
            {
                if (this.getZooKeeper().exists(path + "/" + mappingId, false) != null)
                {
                    this.deleteTree(path + "/" + mappingId);
                }
            }
        }
    }
    
    @Override
    public CfgTfBean getCfgTfBean(String taskCode)
        throws Exception
    {
        String path = this.pathTaskConfig + "/" + taskCode + "/cfgTf";
        CfgTfBean bean = null;
        if (this.getZooKeeper().exists(path, false) == null)
        {
            return null;
        }
        else
        {
            byte[] bs = this.getDataWithNoNodeExceptionHandled(path, false, null);
            if (bs == null)
            {
                return null;
            }
            else
            {
                bean = JsonUtils.fromJson(new String(bs), CfgTfBean.class);
            }
        }
        
        return bean;
    }
    
    @Override
    public CfgTfDtlBean[] getCfgTfDtlBeans(String taskCode)
        throws Exception
    {
        String path = this.pathTaskConfig + "/" + taskCode + "/cfgTf";
        if (this.getZooKeeper().exists(path, false) == null)
        {
            return null;
        }
        List<String> dtls = this.getChildrenWithNoNodeExceptionHandled(path, false);
        if (dtls == null || dtls.size() == 0)
        {
            return null;
        }
        List<CfgTfDtlBean> dtlList = new ArrayList<CfgTfDtlBean>(dtls.size());
        for (String dtl : dtls)
        {
            byte[] b = this.getDataWithNoNodeExceptionHandled(path + "/" + dtl, false, null);
            if (b == null)
            {
                logger.error("任务【" + taskCode + "】tf详情数据配置为空,忽略此配置！" + dtl);
                continue;
            }
            CfgTfDtlBean dtlBean = JsonUtils.fromJson(new String(b), CfgTfDtlBean.class);
            if (dtlBean == null)
            {
                logger.error("任务【" + taskCode + "】tf详情数据配置有误，忽略此配置！" + dtl);
                continue;
            }
            dtlList.add(dtlBean);
        }
        
        return dtlList.toArray(new CfgTfDtlBean[0]);
    }
    
    @Override
    public CfgTfMapping[] getCfgTfMappings(String taskCode, String tfDtlId)
        throws Exception
    {
        String path = this.pathTaskConfig + "/" + taskCode + "/cfgTf/" + tfDtlId;
        if (this.getZooKeeper().exists(path, false) == null)
        {
            return null;
        }
        List<String> mappings = this.getChildrenWithNoNodeExceptionHandled(path, false);
        if (mappings == null || mappings.size() == 0)
        {
            return null;
        }
        List<CfgTfMapping> mappingList = new ArrayList<CfgTfMapping>(mappings.size());
        for (String mappingId : mappings)
        {
            byte[] b = this.getDataWithNoNodeExceptionHandled(path + "/" + mappingId, false, null);
            if (b == null)
            {
                continue;
            }
            CfgTfMapping bean = JsonUtils.fromJson(new String(b), CfgTfMapping.class);
            if (bean == null)
            {
                continue;
            }
            mappingList.add(bean);
        }
        
        return mappingList.toArray(new CfgTfMapping[0]);
    }
    
    @Override
    public void taskEffect(String taskCode)
        throws Exception
    {
        TaskBean task = this.getTaskInfoByTaskCode(taskCode);
        if (task == null)
        {
            throw new Exception("任务配置不存在！" + taskCode);
        }
        task.setState("U");
        this.updateTask(task);
    }
    
    @Override
    public String getDeployNodeState(String nodeId)
        throws Exception
    {
        
        if (this.getZooKeeper().exists(pathDeploy + "/" + nodeId, false) == null)
        {
            return null;
        }
        else
        {
            byte[] v = this.getDataWithNoNodeExceptionHandled(pathDeploy + "/" + nodeId, false, null);
            return (v == null ? null : new String(v));
        }
    }
    
    @Override
    public void setDeployNodeState(String nodeId, String stateInfo)
        throws Exception
    {
        if (CommonUtils.isBlank(stateInfo))
        {
            return;
        }
        if (this.getZooKeeper().exists(pathDeploy + "/" + nodeId, false) == null)
        {
            this.getZooKeeper().create(pathDeploy + "/" + nodeId, stateInfo.getBytes(), acl, CreateMode.EPHEMERAL);
        }
        else
        {
            this.getZooKeeper().setData(pathDeploy + "/" + nodeId, stateInfo.getBytes(), -1);
        }
    }
    
    @Override
    public String getStaticData(String code)
        throws Exception
    {
        String path = this.pathStaticData + "/" + code;
        if (this.getZooKeeper().exists(path, false) != null)
        {
            byte[] val = this.getDataWithNoNodeExceptionHandled(path, false, null);
            if (val != null)
            {
                return new String(val);
            }
        }
        return null;
    }
    
    @Override
    public void setStaticData(String code, String value)
        throws Exception
    {
        String path = this.pathStaticData + "/" + code;
        if (this.getZooKeeper().exists(path, false) == null)
        {
            this.getZooKeeper().create(path, value == null ? null : value.getBytes(), acl, CreateMode.PERSISTENT);
        }
        else
        {
            this.getZooKeeper().setData(path, value == null ? null : value.getBytes(), -1);
        }
    }
    
    @Override
    public TaskItemJobBean transformTaskItemJob(TaskItemJobBean job, String serverId)
        throws Exception
    {
        if (job == null || CommonUtils.isBlank(serverId))
        {
            throw new Exception("参数为空，无法创建新的任务项流水");
        }
        String newJobId = job.getItemJobId() + "$R";
        TaskItemJobBean newJob = new TaskItemJobBean();
        newJob.setItemJobId(newJobId);
        newJob.setPriority(job.getPriority());
        newJob.setServerId(serverId);
        newJob.setTaskCode(job.getTaskCode());
        newJob.setTaskItemId(job.getTaskItemId());
        newJob.setTaskJobId(job.getTaskJobId());
        newJob.setTaskVersion(job.getTaskVersion());
        newJob.setTaskItemParam(job.getTaskItemParam());
        newJob.setSts(RunSts.start.name());
        Transaction t = this.getZooKeeper().transaction();
        
        byte[] bb = JsonUtils.toJson(newJob).getBytes();
        // 任务流水下创建拆分项流水
        t.create(pathTaskConfig + "/" + newJob.getTaskCode() + "/jobId/" + newJob.getTaskJobId() + "/" + newJobId,
            bb,
            acl,
            CreateMode.PERSISTENT);
        
        String psc = this.pathServerChannel + "/" + serverId;
        if (this.getZooKeeper().exists(psc, false) == null)
        {
            try
            {
                this.getZooKeeper().create(psc, null, acl, CreateMode.PERSISTENT);
            }
            catch (NodeExistsException ex)
            {
                // 忽略
            }
        }
        
        // 把任务分派到server
        t.create(psc + "/" + newJobId, bb, acl, CreateMode.PERSISTENT);
        t.commit();
        return newJob;
    }
    
    @Override
    public List<String> getAllServerChannles()
        throws Exception
    {
        
        return this.getChildrenWithNoNodeExceptionHandled(pathServerChannel, false);
    }
    
    @Override
    public FaultBean getFaultProcessBean(String serverId)
        throws Exception
    {
        if (this.getZooKeeper().exists(pathFault + "/" + serverId, false) == null)
        {
            return null;
        }
        else
        {
            byte[] bb = this.getDataWithNoNodeExceptionHandled(pathFault + "/" + serverId, false, null);
            if (bb == null)
            {
                this.getZooKeeper().delete(pathFault + "/" + serverId, -1);
                return null;
            }
            else
            {
                return JsonUtils.fromJson(new String(bb), FaultBean.class);
            }
        }
    }
    
    @Override
    public void setFaultProcessBean(FaultBean faultBean)
        throws Exception
    {
        if (faultBean == null || CommonUtils.isBlank(faultBean.getFaultServerId()))
        {
            throw new Exception("参数传入有误！！");
        }
        String serverId = faultBean.getFaultServerId();
        if (this.getZooKeeper().exists(pathFault + "/" + serverId, false) == null)
        {
            this.getZooKeeper().create(pathFault + "/" + serverId,
                JsonUtils.toJson(faultBean).getBytes(),
                acl,
                CreateMode.PERSISTENT);
        }
        else
        {
            this.getZooKeeper().setData(pathFault + "/" + serverId, JsonUtils.toJson(faultBean).getBytes(), -1);
        }
    }
    
    @Override
    public void deleteFaultProcessBean(String serverId)
        throws Exception
    {
        if (this.getZooKeeper().exists(pathFault + "/" + serverId, false) != null)
        {
            this.getZooKeeper().delete(pathFault + "/" + serverId, -1);
        }
    }
    
    @Override
    public FaultBean[] getAllFault()
        throws Exception
    {
        if (this.getZooKeeper().exists(pathFault, false) != null)
        {
            List<String> faults = this.getChildrenWithNoNodeExceptionHandled(pathFault, false);
            if (faults != null && faults.size() == 0)
            {
                return null;
            }
            List<FaultBean> list = new ArrayList<FaultBean>(faults.size());
            for (String f : faults)
            {
                list.add(this.getFault(f));
            }
            
            return list.toArray(new FaultBean[0]);
        }
        return null;
    }
    
    public FaultBean getFault(String serverId)
        throws Exception
    {
        String path = this.pathFault + "/" + serverId;
        if (this.getZooKeeper().exists(path, false) != null)
        {
            byte[] b = this.getDataWithNoNodeExceptionHandled(path, false, null);
            if (b == null)
            {
                FaultBean t = new FaultBean();
                t.setFaultServerId(serverId);
                return t;
            }
            return JsonUtils.fromJson(new String(b), FaultBean.class);
        }
        return null;
    }
    
    @Override
    public void faultRecovery(String faultServerId)
        throws Exception
    {
        Transaction t = this.getZooKeeper().transaction();
        
        // 移除故障列表中的处理进程,如果故障服务已经切换到备份应用，把备份应用状态更新为stop
        if (this.getZooKeeper().exists(pathFault + "/" + faultServerId, false) != null)
        {
            FaultBean fault = this.getFault(faultServerId);
            if (fault != null && !CommonUtils.isBlank(fault.getBakServerId()))
            {
                String bakServerId = fault.getBakServerId();
                if (this.getZooKeeper().exists(pathServerChannel + "/" + bakServerId, false) != null)
                {
                    t.setData(pathServerChannel + "/" + bakServerId, RunSts.stop.name().getBytes(), -1);
                }
                
                Map<String, List<String>> taskItems = this.getAllTaskItemsByServerId(bakServerId);
                if (taskItems != null && taskItems.size() > 0)
                {
                    for (String task : taskItems.keySet())
                    {
                        for (String item : taskItems.get(task))
                        {
                            t.setData(this.pathTaskConfig + "/" + task + "/item/" + item + "/assign_server",
                                faultServerId.getBytes(),
                                -1);
                        }
                    }
                }
            }
            t.delete(pathFault + "/" + faultServerId, -1);
            t.setData(pathServerChannel + "/" + faultServerId, RunSts.start.name().getBytes(), -1);
        }
        t.commit();
    }
    
    @Override
    public Map<String, List<String>> getAllTaskItemsByServerId(String serverId)
        throws Exception
    {
        Map<String, List<String>> result = new HashMap<String, List<String>>();
        List<String> taskCodes = this.getAllTaskCodes();
        if (taskCodes == null || taskCodes.size() == 0)
        {
            return result;
        }
        for (String code : taskCodes)
        {
            List<String> l = this.getAllTaskItem(code);
            if (l != null && l.size() > 0)
            {
                List<String> items = new ArrayList<String>();
                for (String item : l)
                {
                    String sid = this.getTaskItemServerId(code, item);
                    if (serverId.equals(sid))
                    {
                        items.add(item);
                    }
                }
                if (items.size() > 0)
                {
                    result.put(code, items);
                }
            }
        }
        return result;
    }
    
    /**
     * 根据进程名称获取后备信息
     * 
     * @param serverName
     * @return
     */
    @Override
    public String getBackupProcessor(String serverName)
        throws Exception
    {
        String backupProcessorName = "";
        String path = this.backupConfig + "/" + serverName;
        if (this.getZooKeeper().exists(path, false) != null)
        {
            backupProcessorName = new String(this.getZooKeeper().getData(path, false, null));
        }
        return backupProcessorName;
    }
    
    @Override
    public void createBackupConfig(BackupInfoBean backupInfo)
        throws Exception
    {
        if (backupInfo == null || CommonUtils.isBlank(backupInfo.getServerCode())
            || CommonUtils.isBlank(backupInfo.getBackupServerCode()))
        {
            throw new Exception("参数传入有误！！");
        }
        
        String path = backupConfig + "/" + backupInfo.getServerCode();
        if (this.getZooKeeper().exists(path, false) == null)
        {
            try
            {
                
                this.getZooKeeper().create(path,
                    backupInfo.getBackupServerCode().getBytes(),
                    acl,
                    CreateMode.PERSISTENT);
                
            }
            catch (Exception ex)
            {
                logger.error("创建备用进程失败！", ex);
                throw ex;
            }
            
        }
        else
        {
            throw new Exception("备用进程已经存在，只能创建一个。" + backupInfo.getServerCode());
        }
        
    }
    
    @Override
    public void deleteBackupConfig(BackupInfoBean backupInfo)
        throws Exception
    {
        String path = backupConfig + "/" + backupInfo.getServerCode();
        if (this.getZooKeeper().exists(path, false) != null)
        {
            this.deleteTree(path);
        }
        
    }
    
    @Override
    public List<String> getMasterProcessor(String backupServerCode)
        throws Exception
    {
        List<String> serverCodes = this.getAllBackupProcessor();
        if (serverCodes == null || serverCodes.size() == 0)
        {
            return null;
        }
        
        List<String> masterServerCodes = new ArrayList<String>(serverCodes.size());
        for (String s : serverCodes)
        {
            String backupServerCodeExist = this.getBackupProcessor(s);
            if (StringUtils.equals(backupServerCode, backupServerCodeExist))
            {
                masterServerCodes.add(s);
            }
        }
        return masterServerCodes;
    }
    
    @Override
    public List<String> getAllBackupProcessor()
        throws Exception
    {
        String path = backupConfig;
        if (this.getZooKeeper().exists(path, false) == null)
        {
            return new ArrayList<String>();
        }
        List<String> codes = this.getChildrenWithNoNodeExceptionHandled(path, false);
        return codes;
    }
    
    @Override
    public void saveBackupRunningTaskItem(String serverName, List<TaskItemBackupInfo> backupInfoList)
        throws Exception
    {
        String path = backupRunningTaskItem + "/" + serverName;
        if (this.getZooKeeper().exists(path, false) == null)
        {
            try
            {
                this.getZooKeeper().create(path, null, acl, CreateMode.PERSISTENT);
            }
            catch (Exception ex)
            {
                logger.error("saveBackupRunningTaskItem failed!", ex);
                throw ex;
            }
            
        }
        this.getZooKeeper().setData(path, JsonUtils.toJson(backupInfoList).getBytes(), -1);
        
    }
    
    @Override
    public void deleteBackupRunningTaskItem(String serverName)
        throws Exception
    {
        String path = backupRunningTaskItem + "/" + serverName;
        if (this.getZooKeeper().exists(path, false) != null)
        {
            this.deleteTree(path);
        }
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<TaskItemBackupInfo> qryBackupRunningTaskItem(String serverName)
        throws Exception
    {
        String path = backupRunningTaskItem + "/" + serverName;
        if (this.getZooKeeper().exists(path, false) != null)
        {
            byte[] value = this.getDataWithNoNodeExceptionHandled(path, false, null);
            if (value != null)
            {
                JSONArray array = JSONArray.fromObject(new String(value));
                return JSONArray.toList(array, TaskItemBackupInfo.class);
                
            }
        }
        
        return null;
    }
    
    @Override
    public List<String> qryHandledBackupRunningServer()
        throws Exception
    {
        if (this.getZooKeeper().exists(backupRunningTaskItem, false) == null)
        {
            return new ArrayList<String>();
        }
        return this.getChildrenWithNoNodeExceptionHandled(backupRunningTaskItem, false);
    }

	@Override
	public void taskHangOn(String taskCode) throws Exception {
		TaskBean task = this.getTaskInfoByTaskCode(taskCode);
        if (task == null)
        {
            throw new Exception("任务配置不存在！" + taskCode);
        }
        task.setState("H");
        this.updateTask(task);
	}
    
}
