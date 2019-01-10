package com.asiainfo.monitor.exeframe.configmgr.service.impl;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.ai.appframe2.service.ServiceFactory;
import com.ai.appframe2.util.criteria.Criteria;
import com.ailk.common.data.IDataBus;
import com.ailk.common.data.IDataContext;
import com.ailk.common.data.impl.DataBus;
import com.ailk.common.data.impl.DataContext;
import com.asiainfo.monitor.busi.cache.AIMonConModeCheckListener;
import com.asiainfo.monitor.busi.cache.impl.ConMode;
import com.asiainfo.monitor.busi.cache.interfaces.ICheckCache;
import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonConModeBean;
import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonConModeEngine;
import com.asiainfo.monitor.exeframe.configmgr.abo.dao.interfaces.IAIMonConModeDAO;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonConModeValue;
import com.asiainfo.monitor.exeframe.configmgr.service.interfaces.IAIMonConModeSV;

public class AIMonConModeSVImpl implements IAIMonConModeSV
{

    @Override
    public IBOAIMonConModeValue[] qryByConIdList(List<String> conIdList) throws Exception
    {
        Criteria sql = new Criteria();
        if(conIdList.size() > 0)
            sql.addIn(IBOAIMonConModeValue.S_ConId, conIdList);

        return BOAIMonConModeEngine.getBeans(sql);
    }

    @Override
    public IBOAIMonConModeValue qryConModeInfoByConId(String conId) throws Exception
    {
        IAIMonConModeDAO cmDAO = (IAIMonConModeDAO) ServiceFactory.getService(IAIMonConModeDAO.class);
        return cmDAO.qryConModeInfoByConId(conId);
    }

    @Override
    public IDataBus getSelectList() throws Exception
    {
        Criteria sql = null;
        BOAIMonConModeBean[] result = BOAIMonConModeEngine.getBeans(sql);

        IDataContext context = new DataContext();
        JSONArray jsonArray = new JSONArray();
        for(int i = 0; i < result.length; i++) {
            JSONObject obj = new JSONObject();
            String key = result[i].getConType();
            String valType = result[i].getConType();
            String valPort = Long.toString(result[i].getConPort());
            String valConId = Long.toString(result[i].getConId());
            obj.put("TEXT", key);
            obj.put("VALUE", valType + "_" + valPort + "_" + valConId);
            jsonArray.add(obj);
        }
        return new DataBus(context, jsonArray);
    }

    @Override
    public long saveOrUpdate(IBOAIMonConModeValue value) throws Exception
    {
        IAIMonConModeDAO cmDAO = (IAIMonConModeDAO) ServiceFactory.getService(IAIMonConModeDAO.class);
        return cmDAO.saveOrUpdate(value);
    }

    @Override
    public IBOAIMonConModeValue[] getAllConMode() throws Exception
    {
        IAIMonConModeDAO cmDAO = (IAIMonConModeDAO) ServiceFactory.getService(IAIMonConModeDAO.class);
        return cmDAO.getAllConMode();
    }

    @Override
    public ConMode wrapperConModeConfigByBean(IBOAIMonConModeValue value) throws Exception
    {
        if(value == null || StringUtils.isBlank(value.getState()) || value.getState().equals("E"))
            return null;
        ConMode result = new ConMode();
        result.setCon_Id(value.getConId() + "");
        result.setCon_Port(value.getConPort() + "");
        result.setCon_Type(value.getConType());
        result.setCreate_Date(value.getCreateDate() + "");
        result.setRemark(value.getRemark());
        result.setState(value.getState());

        result.setCacheListener(new AIMonConModeCheckListener());
        return result;
    }

    @Override
    public ICheckCache getConModeByIdFromDb(String id) throws Exception
    {
        if(StringUtils.isBlank(id))
            return null;
        IAIMonConModeDAO conDAO = (IAIMonConModeDAO) ServiceFactory.getService(IAIMonConModeDAO.class);
        IBOAIMonConModeValue value = conDAO.qryConInfoById(id);
        ConMode result = this.wrapperConModeConfigByBean(value);
        return result;
    }

}
