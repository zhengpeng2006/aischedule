package com.asiainfo.monitor.exeframe.configmgr.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.ai.appframe2.complex.cache.CacheFactory;
import com.ai.appframe2.service.ServiceFactory;
import com.ailk.common.data.IDataBus;
import com.ailk.common.data.IDataContext;
import com.ailk.common.data.impl.DataBus;
import com.ailk.common.data.impl.DataContext;
import com.ailk.common.data.impl.TreeBean;
import com.asiainfo.monitor.busi.cache.impl.AIMonPInfoGroupCacheImpl;
import com.asiainfo.monitor.busi.cache.impl.MonPInfoGroup;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPInfoValue;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPInfoSV;

/**
 * @author lj
 * 任务配置相关几个页面的数据转换
 *
 */
public class ConfigPageUtil
{
    /**
     * 将map转换为下拉框
     * @param map 实际值为key 描述为value的map
     * @return
     * @throws Exception
     */
    public static IDataBus getSelectList(Map<String, String> map)
    {
        if (null == map)
        {
            return null;
        }
        IDataContext context = new DataContext();
        JSONArray jsonArray = new JSONArray();
        for(Map.Entry<String, String> entry : map.entrySet()) {
            JSONObject obj = new JSONObject();
            String key = entry.getValue();
            String val = entry.getKey();
            obj.put("TEXT", key);
            obj.put("VALUE", val);
            jsonArray.add(obj);
        }
        return new DataBus(context, jsonArray);
    }
    
    /**
     * 获取系统结构菜单树数据
     * 
     * @return
     * @throws Exception
     */
    public static TreeBean[] getTreeItems() throws Exception
    {
        // 进程任务服务
        IAIMonPInfoSV infoSv = (IAIMonPInfoSV) ServiceFactory.getService(IAIMonPInfoSV.class);

        // 获取所有任务分组信息
        Map<String, MonPInfoGroup> groupMap = CacheFactory.getAll(AIMonPInfoGroupCacheImpl.class);

        // 主机节点Map
//        Map<String, List<ServerNode>> hostNodeMap = commonService.fetchHostNodeMap();

        // 树节点list
        List<TreeBean> treeList = new ArrayList<TreeBean>();

        // 根节点初始化
        TreeBean rootNode = new TreeBean();
        rootNode.setCode("root");
        rootNode.setId("-1");
        rootNode.setLabel("任务分组");
        rootNode.setLevel("1");
        rootNode.setParentId("0");
        rootNode.setValue("root");
        treeList.add(rootNode);

        // 循环遍历生成树节点数据
        MonPInfoGroup[] grpArr = groupMap.values().toArray(new MonPInfoGroup[0]);
        TreeBean treeNode2 = null;
        for(int i = 0; i < grpArr.length; i++) {
            String level = "2";
            treeNode2 = new TreeBean();
            treeNode2.setId(grpArr[i].getId() + "_" + level);
            treeNode2.setLabel(grpArr[i].getName());
            treeNode2.setLevel(level);
            treeNode2.setParentId(rootNode.getId());
            treeList.add(treeNode2);

            // 获得二级任务信息节点
            IBOAIMonPInfoValue[] infos = infoSv.getMonPInfoValueByParams(Long.parseLong(grpArr[i].getId()),-1,-1,-1);
            TreeBean treeNode3 = null;
            for(IBOAIMonPInfoValue info : infos) {
                level = "3";
                treeNode3 = new TreeBean();
                treeNode3.setId(info.getInfoId() + "_" + level);
                treeNode3.setLabel(info.getName());
                treeNode3.setLevel(level);
                treeNode3.setParentId(treeNode2.getId());
                treeList.add(treeNode3);

                // 获得3级节点，主机节点
//                List<ServerNode> list = hostNodeMap.get(hostBean.getHostId());
//                TreeBean treeNode4 = null;
//                if(null != list) {
//                    for(ServerNode node : list) {
//                        level = "4";
//                        treeNode4 = new TreeBean();
//                        treeNode4.setId(node.getNode_Id() + "_" + level);
//                        treeNode4.setLabel(node.getNode_Name());
//                        treeNode4.setLevel(level);
//                        treeNode4.setParentId(treeNode3.getId());
//                        treeList.add(treeNode4);
//                    }
//                }
            }
        }

        return (TreeBean[]) treeList.toArray(new TreeBean[0]);
    }
    
    
    /**
     * 将map转换为下拉框(按key排序）
     * @param map 实际值为key 描述为value的map
     * @return
     * @throws Exception
     */
    public static IDataBus getSelectListSort(Map<String, String> map)
    {
        if (null == map)
        {
            return null;
        }
        IDataContext context = new DataContext();
        JSONArray jsonArray = new JSONArray();
        for(Map.Entry<String, String> entry : map.entrySet()) {
            JSONObject obj = new JSONObject();
            String key = entry.getValue();
            String val = entry.getKey();
            obj.put("TEXT", key);
            obj.put("VALUE", val);
            jsonArray.add(obj);
        }
        Collections.sort(jsonArray,new Comparator<Object>() {
			@Override
			public int compare(Object o1, Object o2) {
				JSONObject json1 = (JSONObject)o1;
				JSONObject json2 = (JSONObject)o2;
				String number = "^\\d+$";
				if (json1.getString("VALUE").matches(number)){
					return (int)(Long.parseLong(json1.getString("VALUE")) - Long.parseLong(json2.getString("VALUE")));
				}else{
					return json1.getString("VALUE").compareTo(json1.getString("VALUE"));
				}
			}
		});
        return new DataBus(context, jsonArray);
    }
}
