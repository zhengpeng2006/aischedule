package com.asiainfo.monitor.acquire.service.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.privilege.UserInfoInterface;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.acquire.AcquireConst;
import com.asiainfo.monitor.acquire.service.interfaces.IProcessStatusSV;
import com.asiainfo.monitor.analyse.IDataAnalyse;
import com.asiainfo.monitor.analyse.impl.DataAnalyseImpl;
import com.asiainfo.monitor.busi.dao.interfaces.IAIMonCmdDAO;
import com.asiainfo.monitor.common.CommonSvUtil;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonCmdValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonServerValue;
import com.asiainfo.schedule.cache.TaskViewCache;
import com.asiainfo.schedule.core.utils.Constants.KeyCodes;
import com.asiainfo.socket.client.ClientUtils;
import com.asiainfo.socket.service.ClientHelper;

public class ProcessStatusSVImpl implements IProcessStatusSV {

    private static transient Log LOG = LogFactory.getLog(ProcessStatusSVImpl.class);
    
    /** 任务状态：正在运行 */
    private static final String STATE_RUN = "start";


    @Override
    public JSONObject handle() throws Exception {
        //获取所有的数据
        Container container = loadDatas();

        //根据主机IP执行脚本
        Map<String, Map<String, List<String>>> processesInfo = getProcessInfo(container);

        //根据分组进行展示
        JSONArray jsonArrAll = new JSONArray();
        
      //获取所有任务信息
        TaskViewCache cache = TaskViewCache.getInstance();
        Map<String,List<Map<String,String>>> taskViews = cache.getData();

        Map<Long, List<Data>> originalGroup = container.getOriginalGroup();
        Set<Map.Entry<Long, List<Data>>> groups = originalGroup.entrySet();
        for (Map.Entry<Long, List<Data>> group : groups) {
            long groupId = group.getKey();
            List<Data> appsUnderGroup = group.getValue();

            //返回分组信息
            JSONObject groupObj = new JSONObject();
            JSONArray jsonArrGroup = new JSONArray();
            groupObj.put("group", appsUnderGroup.get(0).groupName); //可确保不会空指针
            jsonArrGroup.add(groupObj);

            //获取该分组下面的主机列表
            Set<Long> hostIdSet = container.hostIdListUnderGroup(groupId);
            for (Long hostId : hostIdSet) {
                JSONArray jsonArr = new JSONArray();

                List<Data> hostIdDatasList = container.getIdGroup().get(hostId);
                String ip = hostIdDatasList.get(0).hostIp;//某个hostId只会归属一个ip
                String hostName = hostIdDatasList.get(0).hostName;
                String hostIp = hostIdDatasList.get(0).hostIp;

                Map<String, List<String>> process = processesInfo.get(ip);
                if (process != null) {
                    jsonArr = acqWarningInfo(hostName, hostId, hostIp, process, container,taskViews);
                    jsonArrGroup.add(jsonArr);
                } else {
                    JSONObject obj = new JSONObject();
                    obj.put("title", hostName + "(" + hostIp + "):" + "该主机未连通");
                    jsonArr.add(obj);
                    jsonArrGroup.add(jsonArr);
                }
            }
            jsonArrAll.add(jsonArrGroup);
        }

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("hostServer", jsonArrAll);
        jsonObj.put("curVersion", cache.getCurVersion());
        return jsonObj;
    }

    public JSONArray acqWarningInfo(String hostName, Long hostId, String hostIp, Map<String, List<String>> processInfoMap, Container container,Map<String,List<Map<String,String>>> taskViews) throws Exception {
        JSONArray jsonArr = new JSONArray();
        JSONObject obj = new JSONObject();
        obj.put("title", hostName + "(" + hostIp + "):");
        JSONArray jsonArray = new JSONArray();

        // 获取当前主机部署的应用进程信息
        List<Data> appList = container.getIdGroup().get(hostId);
        //查找应用进程时，匹配的字符串
        String procParamStr = "appframe.server.name=";
        if (null == processInfoMap) {
            return null;
        }
        
        Map map = new HashMap<String, List>();
        Map mapNoStart = new HashMap<String, IBOAIMonServerValue>();
        Iterator it = processInfoMap.keySet().iterator();
        while (it.hasNext()) {
            List<String> procInfoList = processInfoMap.get(it.next());
            // 获取最后一个元素，该元素记录进程启动Command
            String cmdInfo = procInfoList.get(procInfoList.size() - 4);
            for (Data appVal : appList) {
                // 如果启动Command包含appCode，则认为是该进程的信息
                String parttenStr = procParamStr + appVal.serverCode;
                if (cmdInfo.contains(parttenStr)) {
                    List list = new ArrayList<List>();
                    String serverId = Long.toString(appVal.serverId);
                    if (map.containsKey(serverId)) {
                        List list1 = (List) map.get(serverId);
                        list1.add(procInfoList);
                        map.put(serverId, list1);
                    } else {
                        list.add(procInfoList);
                        map.put(serverId, list);
                    }
                    break;
                } else {
                    String serverId = Long.toString(appVal.serverId);
                    if (!mapNoStart.containsKey(serverId)) {
                        mapNoStart.put(serverId, appVal);
                    }
                }
            }
        }
        Object s[] = map.keySet().toArray();
        for (int i = 0; i < map.size(); i++) {
            String key = (String) s[i];
            if (mapNoStart.containsKey(key)) {
                mapNoStart.remove(key);
            }

            Data d = container.getServerById(Long.valueOf(key));
            List list = (List) map.get(s[i]);
            JSONObject jsonObj = new JSONObject();
            if (list.size() > 1) {
                jsonObj.put("state", "进程异常");
                jsonObj.put("desc", "重复的进程");
            } else {
                jsonObj.put("state", "进程正常");
                jsonObj.put("desc", "正常的进程");
            }
            jsonObj.put("hostId", String.valueOf(hostId));
            jsonObj.put("hostName", hostName);
            jsonObj.put("serverCode", d.serverCode);
            jsonObj.put("serverName", d.serverName);
            //添加任务统计信息
            List<Map<String,String>> taskInfos = taskViews.get(d.serverCode);
            jsonObj = addTaskCountInfo(jsonObj, taskInfos);
            jsonArray.add(jsonObj);
        }
        Object ss[] = mapNoStart.keySet().toArray();
        for (int i = 0; i < mapNoStart.size(); i++) {
            Data appVal = (Data) mapNoStart.get(ss[i]);
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("hostId", String.valueOf(hostId));
            jsonObj.put("hostName", hostName);
            jsonObj.put("serverCode", appVal.serverCode);
            jsonObj.put("serverName", appVal.serverName);
            jsonObj.put("state", "进程异常");
            jsonObj.put("desc", "未启动的进程");
           //添加任务统计信息
            List<Map<String,String>> taskInfos = taskViews.get(appVal.serverCode);
            jsonObj = addTaskCountInfo(jsonObj, taskInfos);
            jsonArray.add(jsonObj);
        }
        jsonArr.add(obj);
        jsonArr.add(jsonArray);
        return jsonArr;
    }

    /**
     * 获取进程信息
     *
     * @param container
     * @return 
     * @throws Exception
     */
    public Map<String, Map<String, List<String>>> getProcessInfo(Container container) throws Exception {
        IAIMonCmdDAO dao = (IAIMonCmdDAO) ServiceFactory.getService(IAIMonCmdDAO.class);
        IBOAIMonCmdValue cmd = dao.queryById(15); //process的预置脚本
        //获取后台用户名
        cmd.setCmdExpr(cmd.getCmdExpr().replace("grep -v $0", "grep -v $0 | grep -E '" + loadUsers() + "'"));

        Map<String, Map<String, List<String>>> rtnMap = new HashMap<String, Map<String, List<String>>>();
        
        List<Map<String, String>> requests = new ArrayList<Map<String, String>>();
        Set<String> monitors = container.getMonitorsIp();
        for (String ip : monitors) {

            if (!ClientUtils.ping(ip)) {
                continue;
            }
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("TYPE_ID", AcquireConst.EXE_TYPE_SHELL);
            paramMap.put("PARAM", null);
            paramMap.put("SHELL", cmd.getCmdExpr());
            //监控节点的home路径--一个IP只会有一个监控节点,这里去监控节点的home路径
            paramMap.put("HOME_PATH", container.getMonitorHomePathByIp(ip)); //必定至少有一条记录
            paramMap.put("IP", ip);

            requests.add(paramMap);
        }
        
        Map<String, String> rtns = ClientHelper.batchAsyncExecShell(requests);
        Set<Map.Entry<String, String>> entries = rtns.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            if (StringUtils.isEmpty(entry.getValue())) {
                continue;
            }
            
             // 数据分析
            IDataAnalyse dataAnalyse = new DataAnalyseImpl();
            Map<String, List<String>> processInfoMap = (Map<String, List<String>>) dataAnalyse.procInfoAnalyse(cmd.getCmdName(), entry.getValue());
            rtnMap.put(entry.getKey(), processInfoMap);
        }
        return rtnMap;
    }
    
    private String loadUsers() throws Exception {
        String sql = "select t.user_name from AI_MON_USERS t where t.state = 'U'";
        StringBuilder sb = new StringBuilder();
        Connection conn = null;
        ResultSet resultset = null;
        try {
            conn = ServiceManager.getSession().getConnection();
            resultset = ServiceManager.getDataStore().retrieve(conn, sql, new HashMap<String, String>());
            while (resultset.next()) {
               sb.append(resultset.getString("user_name")).append("|");
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (resultset != null) {
                resultset.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
		String users = sb.toString();
		if (users.endsWith("|")) {
			users = users.substring(0, users.length() - 1);
		}

        return users;
    }

    /**
     * 加载所有的应用编码以及相应的主机、分组
     *
     * @return
     * @throws Exception
     */
    private Container loadDatas() throws Exception {
        StringBuilder sql = new StringBuilder(100);
        Map<String, String> parameter = new HashMap<String, String>();
        sql.append("select s.server_id,s.server_code,s.server_name, n.is_monitor_node,h.host_id,h.host_name,h.host_ip,g.group_id,g.group_name,d.client_home_path")
                .append(" from ai_mon_server s, ai_mon_node n, ai_mon_physic_host h, ai_mon_group g, ai_mon_group_host_rel ghr,")
                .append(" ai_deploy_node_strategy_rela nsr,ai_deploy_strategy d")
                .append(" where s.state = 'U' and n.state = 'U' and h.state = 'U' and g.state = 'U'")
                .append(" and s.node_id = n.node_id and n.host_id = h.host_id and h.host_id = ghr.host_id and ghr.group_id = g.group_id")
                .append(" and n.node_id = nsr.node_id and nsr.deploy_strategy_id = d.deploy_strategy_id");
        UserInfoInterface userInfo=CommonSvUtil.getUserInfo();
        String priv="";
        //当用户信息不为空的时候，获取分组信息
        if(userInfo!=null) {
            priv=String.valueOf(userInfo.getID())+"_"+userInfo.getCode();
        }
        //根据不同的用户添加查询条件
        if(StringUtils.isNotBlank(priv)) {
            if(!(priv.split("_")[0]).equals("-110")) {
                sql.append(" and g.priv like '%"+priv+"%'");
            }
        }
        Container container = new Container();
        Connection conn = null;
        ResultSet resultset = null;
        try {
            conn = ServiceManager.getSession().getConnection();
            resultset = ServiceManager.getDataStore().retrieve(conn, sql.toString(), parameter);
            while (resultset.next()) {
                Data d = new Data();
                d.serverId = resultset.getLong("server_id");
                d.serverCode = resultset.getString("server_code");
                d.serverName = resultset.getString("server_name");
                d.isMonitorNode = StringUtils.equalsIgnoreCase("Y", resultset.getString("is_monitor_node")) ? true : false;
                d.hostId = resultset.getLong("host_id");
                d.hostName = resultset.getString("host_name");
                d.hostIp = resultset.getString("host_ip");
                d.groupId = resultset.getLong("group_id");
                d.groupName = resultset.getString("group_name");
				//d.userName = resultset.getString("user_name");
                //d.userPasswd = resultset.getString("user_pwd");
                d.homePath = resultset.getString("client_home_path");

                container.add(d);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (resultset != null) {
                resultset.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return container;
    }
    
    /**
     * 给原数据添加任务统计信息
     * @param jsonObj
     * @param taskInfos
     * @return
     */
    private JSONObject addTaskCountInfo(JSONObject jsonObj,List<Map<String,String>> taskInfos){
        int runCnt = 0;//记录正在跑的
        int stopCut = 0;//记录已经停止了
        int totolCnt = 0;//记录总数
        if (taskInfos!= null){
            for (Map<String,String> map : taskInfos) {
				if (STATE_RUN.equalsIgnoreCase(map.get(KeyCodes.TaskRunSts.name()))){
					runCnt++;
				}else{
					stopCut++;
				}
			}
            totolCnt = taskInfos.size();
        }
        jsonObj.put("runCnt", runCnt);
        jsonObj.put("stopCut",stopCut);
        jsonObj.put("totolCnt",totolCnt);
        return jsonObj;
    }

    /**
     * 数据结构
     *
     * @author 孙德东(24204)
     */
    private class Data {

        public long groupId;
        public String groupName;
        public long hostId;
        public String hostName;
        public String hostIp;
        public long serverId;
        public String serverCode;
        public String serverName;
        public boolean isMonitorNode;
		//public String userName;
        //public String userPasswd;
        public String homePath;
    }

    /**
     * 数据容器(适当的划分分组）
     *
     * @author 孙德东(24204)
     */
    private class Container {

        //对应用根据hostId分组

        private Map<Long, List<Data>> devidedByHostIdGroup = new HashMap<Long, List<Data>>();
        //对应用根据hostIp分组
        private Map<String, List<Data>> devidedByHostIpGroup = new HashMap<String, List<Data>>();
        //原始的分组
        private Map<Long, List<Data>> originalGroup = new HashMap<Long, List<Data>>();
        //未分组的数据
        private Map<Long, Data> serversMap = new HashMap<Long, Data>();
        //监控分组
        private Map<String, List<Data>> monitorGroup = new HashMap<String, List<Data>>();

        /**
         * 保存一个应用
         *
         * @param d
         */
        public void add(Data d) {
            addToIdGroup(d);
            addToIpGroup(d);
            addToOriginalGroup(d);
            addToMonitorGroup(d);
            addToServers(d);
        }

        //先根据Id分组
        private void addToIdGroup(Data d) {
            List<Data> list = devidedByHostIdGroup.get(d.hostId);
            if (list == null) {
                list = new ArrayList<Data>();
                devidedByHostIdGroup.put(d.hostId, list);
            }
            list.add(d);
        }

        //在根据Ip分组
        private void addToIpGroup(Data d) {
            List<Data> list = devidedByHostIpGroup.get(d.hostIp);
            if (list == null) {
                list = new ArrayList<Data>();
                devidedByHostIpGroup.put(d.hostIp, list);
            }
            list.add(d);
        }

        //按照原始概念分组
        private void addToOriginalGroup(Data d) {
            List<Data> list = originalGroup.get(d.groupId);
            if (list == null) {
                list = new ArrayList<Data>();
                originalGroup.put(d.groupId, list);
            }
            list.add(d);
        }

        //是否加入到监控列表
        private void addToMonitorGroup(Data d) {
            if (!d.isMonitorNode) {
                return;
            }

            List<Data> list = monitorGroup.get(d.hostIp);
            if (list == null) {
                list = new ArrayList<Data>();
                monitorGroup.put(d.hostIp, list);
            }
            list.add(d);
        }

		//获取存在监控节点的主机列表
        private void addToServers(Data d) {
            serversMap.put(d.serverId, d);
        }

        public Map<Long, List<Data>> getIdGroup() {
            return this.devidedByHostIdGroup;
        }

        public Map<String, List<Data>> getIpGroup() {
            return this.devidedByHostIpGroup;
        }

        public Map<Long, List<Data>> getOriginalGroup() {
            return this.originalGroup;
        }

        /**
         * 获取排重后的监控IP(存在监控节点的IP)
         *
         * @return
         */
        public Set<String> getMonitorsIp() {
            return this.monitorGroup.keySet();
        }

        /**
         * 获取home路径
         *
         * @return
         */
        public String getMonitorHomePathByIp(String ip) {
            List<Data> list = this.monitorGroup.get(ip);
            if (list == null || list.isEmpty()) {
                return StringUtils.EMPTY;
            }

            return list.get(0).homePath;
        }

        /**
         * 获取某个组下面的hostid列表
         *
         * @param groupId
         * @return
         */
        public Set<Long> hostIdListUnderGroup(long groupId) {
            List<Data> datas = originalGroup.get(groupId);

            Set<Long> rtn = new HashSet<Long>();
            for (Data d : datas) {
                rtn.add(d.hostId);
            }

            return rtn;
        }

        public Data getServerById(long serverId) {
            return serversMap.get(serverId);
        }
    }
}
