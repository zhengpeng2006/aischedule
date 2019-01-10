package com.asiainfo.monitor.busi.exe.task.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.cache.AIMonPInfoGroupCheckListener;
import com.asiainfo.monitor.busi.cache.MonCacheManager;
import com.asiainfo.monitor.busi.cache.impl.AIMonPInfoGroupCacheImpl;
import com.asiainfo.monitor.busi.cache.impl.MonPInfoGroup;
import com.asiainfo.monitor.busi.exe.task.dao.interfaces.IAIMonPInfoGroupDAO;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPInfoGroupValue;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPInfoValue;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPInfoGroupSV;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPInfoSV;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class AIMonPInfoGroupSVImpl implements IAIMonPInfoGroupSV
{

    /**
     * 修改或保存所属区域再分组信息
     * 
     * @param value
     * @throws Exception
     */
    public void saveOrUpdate(IBOAIMonPInfoGroupValue value) throws Exception
    {
        boolean modify = value.isModified();
        IAIMonPInfoGroupDAO dao = (IAIMonPInfoGroupDAO) ServiceFactory.getService(IAIMonPInfoGroupDAO.class);
        dao.saveOrUpdate(value);
        if(modify) {
            MonPInfoGroup item = (MonPInfoGroup) MonCacheManager.get(AIMonPInfoGroupCacheImpl.class, value.getGroupId() + "");
            if(item != null)
                item.setEnable(false);
        }
        else {
            MonPInfoGroup item = this.wrapperMonPInfoGroupByBean(value);
            MonCacheManager.put(AIMonPInfoGroupCacheImpl.class, item.getId(), item);
        }
    }

    /**
     * 根据代码读取任务分组信息并封装
     * @param code
     * @return
     * @throws Exception
     */
    public MonPInfoGroup getMonPInfoGroupByCodeFromDb(String code) throws Exception
    {
        if(StringUtils.isBlank(code))
            return null;
        IAIMonPInfoGroupDAO dao = (IAIMonPInfoGroupDAO) ServiceFactory.getService(IAIMonPInfoGroupDAO.class);
        IBOAIMonPInfoGroupValue[] values = dao.getMonPInfoGroupByCode(code);
        if(values == null) {
            // 没找到代码为"+code+"的任务分组信息
            throw new Exception(AIMonLocaleFactory.getResource("MOS0000248", code));
        }

        //如果查询出数据，则更新缓存
        if(values.length > 0) {
            MonPInfoGroup result = this.wrapperMonPInfoGroupByBean(values[0]);
            return result;
        }

        return null;
    }

    /**
     * 将任务分组简单封装
     * @param value
     * @return
     */
    public MonPInfoGroup wrapperMonPInfoGroupByBean(IBOAIMonPInfoGroupValue value) throws Exception
    {
        if(value == null || StringUtils.isBlank(value.getState()) || value.getState().equals("E"))
            return null;
        MonPInfoGroup result = new MonPInfoGroup();
        result.setId(value.getGroupId() + "");
        result.setCode(value.getGroupCode());
        result.setName(value.getGroupName());
        result.setDesc(value.getGroupDesc());
        result.setParentId(value.getParentId() + "");
        result.setSortId(value.getSortId() + "");
        result.setStyle(value.getGroupStyle());
        result.setLayer(value.getLayer());
        result.setRemark(value.getRemark());
        result.setCacheListener(new AIMonPInfoGroupCheckListener());
        return result;
    }

    /**
     * 删除所属区域再分组信息
     * 
     * @param areaId
     * @throws Exception
     */
    public void delete(long groupId) throws Exception
    {
        IAIMonPInfoGroupDAO dao = (IAIMonPInfoGroupDAO) ServiceFactory.getService(IAIMonPInfoGroupDAO.class);
        //查询此分组下是否有关联任务
        IAIMonPInfoSV infoSv = (IAIMonPInfoSV) ServiceFactory.getService(IAIMonPInfoSV.class);
        IBOAIMonPInfoValue[] values = infoSv.getMonPInfoValueByParams(groupId, -1, -1, -1);
        if(values != null && values.length > 0)//有则不让删除,返回错误信息
        {
            throw new Exception("\u8be5\u5206\u7ec4\u4e0b\u6709\u4efb\u52a1\u5173\u8054\uff0c\u65e0\u6cd5\u5220\u9664");//跑出指定内容异常
        }
        dao.delete(groupId);
        MonPInfoGroup item = (MonPInfoGroup) MonCacheManager.get(AIMonPInfoGroupCacheImpl.class, groupId + "");
        if(item != null)
            item.setEnable(false);
    }

    /**
     * 根据代码读取归属区域再分组信息
     * 
     * @param code
     * @return
     * @throws Exception
     */
    public List getMonPInfoGroupByCode(String code) throws Exception
    {
        List result = null;
        Map groupMap = MonCacheManager.getAll(AIMonPInfoGroupCacheImpl.class);
        if(groupMap != null && groupMap.size() > 0) {
            Iterator it = groupMap.entrySet().iterator();
            result = new ArrayList();
            while(it.hasNext()) {
                Entry entry = (Entry) it.next();
                if(((MonPInfoGroup) entry.getValue()).getCode().equals(code)) {
                    result.add((MonPInfoGroup) entry.getValue());
                }
            }
        }
        return result;
    }

    public List getAllMonPInfoGroup() throws Exception
    {
        List result = null;
        Map groupMap = MonCacheManager.getAll(AIMonPInfoGroupCacheImpl.class);
        if(groupMap != null && groupMap.size() > 0) {
            Iterator it = groupMap.entrySet().iterator();
            result = new ArrayList();
            while(it.hasNext()) {
                Entry entry = (Entry) it.next();
                result.add((MonPInfoGroup) entry.getValue());
            }
        }
        return result;
    }

    /**
     * 根据归属区域标识，读取下属再分组信息
     * @param busiAreaId
     * @return
     * @throws Exception
     */
    public List getMonPInfoGroupByParentId(long parentId) throws Exception
    {
        List result = null;
        Map groupMap = MonCacheManager.getAll(AIMonPInfoGroupCacheImpl.class);
        if(groupMap != null && groupMap.size() > 0) {
            Iterator it = groupMap.entrySet().iterator();
            result = new ArrayList();
            while(it.hasNext()) {
                Entry entry = (Entry) it.next();
                if(Long.parseLong(((MonPInfoGroup) entry.getValue()).getParentId()) == parentId) {
                    result.add((MonPInfoGroup) entry.getValue());
                }
            }
        }
        return result;
    }

    public int getPInfoGroupCount(String groupCode, String groupName, String layer) throws Exception
    {
        IAIMonPInfoGroupDAO dao = (IAIMonPInfoGroupDAO) ServiceFactory.getService(IAIMonPInfoGroupDAO.class);
        return dao.getPInfoGroupCount(groupCode, groupName, layer);
    }

    /**
     * 获取所有分组信息
     * @return
     * @throws Exception
     */
    public IBOAIMonPInfoGroupValue[] getAllMonPInfoGroupBean() throws Exception
    {
        IAIMonPInfoGroupDAO dao = (IAIMonPInfoGroupDAO) ServiceFactory.getService(IAIMonPInfoGroupDAO.class);
        String condition = IBOAIMonPInfoGroupValue.S_State + " ='U' ";
        return dao.query(condition, null);
    }

    /**
     * 批量保存或修改所属区域再分组信息
     * 
     * @param values
     * @throws Exception
     */
    public void saveOrUpdate(IBOAIMonPInfoGroupValue[] values) throws Exception
    {
        boolean modify = values[0].isModified();

        IAIMonPInfoGroupDAO dao = (IAIMonPInfoGroupDAO) ServiceFactory.getService(IAIMonPInfoGroupDAO.class);
        dao.saveOrUpdate(values);
        for(int i = 0; i < values.length; i++) {
            if(modify) {
                MonPInfoGroup item = (MonPInfoGroup) MonCacheManager.get(AIMonPInfoGroupCacheImpl.class, values[i].getGroupId() + "");
                if(item != null)
                    item.setEnable(false);
            }
            else {
                MonPInfoGroup item = this.wrapperMonPInfoGroupByBean(values[i]);
                MonCacheManager.put(AIMonPInfoGroupCacheImpl.class, item.getId(), item);
            }
        }
    }

    public IBOAIMonPInfoGroupValue[] getPInfoGroupByCodeAndName(String groupCode, String groupName, String layer, Integer startNum, Integer endNum)
            throws Exception
    {
        IAIMonPInfoGroupDAO dao = (IAIMonPInfoGroupDAO) ServiceFactory.getService(IAIMonPInfoGroupDAO.class);
        return dao.getPInfoGroupByCodeAndName(groupCode, groupName, layer, startNum, endNum);
    }

    /**
     * 根据标识读取归属区域代码再分组信息
     * 
     * @param groupId
     * @return
     * @throws Exception
     */
    public IBOAIMonPInfoGroupValue getMonPInfoGroupById(long groupId) throws Exception
    {
        IAIMonPInfoGroupDAO dao = (IAIMonPInfoGroupDAO) ServiceFactory.getService(IAIMonPInfoGroupDAO.class);
        return dao.getMonPInfoGroupById(groupId);
    }

}