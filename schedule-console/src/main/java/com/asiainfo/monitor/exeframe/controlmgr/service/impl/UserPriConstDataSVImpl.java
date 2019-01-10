package com.asiainfo.monitor.exeframe.controlmgr.service.impl;

import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.ai.appframe2.service.ServiceFactory;
import com.ailk.common.data.IDataBus;
import com.ailk.common.data.IDataContext;
import com.ailk.common.data.impl.DataBus;
import com.ailk.common.data.impl.DataContext;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonDomainBean;
import com.asiainfo.monitor.exeframe.controlmgr.common.UserPrivConst;
import com.asiainfo.monitor.exeframe.controlmgr.dao.interfaces.IUserPriConstDataDAO;
import com.asiainfo.monitor.exeframe.controlmgr.service.interfaces.IUserPriConstDataSV;

public class UserPriConstDataSVImpl implements IUserPriConstDataSV {

    public IDataBus getUserPrivConstByCode(String constCode) throws Exception
    {
        IDataContext context = new DataContext();
        JSONArray jsonArray = new JSONArray();

        Map<String, String> optMap = null;

        // 是否允许
        if (constCode.equals(UserPrivConst.CONST_ALLOW_NO)) {
            optMap = UserPrivConst.constAllowNoMap;
        }
        // 锁定状态
        else if (constCode.equals(UserPrivConst.CONST_LOCK_STATE)) {
            optMap = UserPrivConst.constLockStateMap;
        }

        Iterator<String> keyIt = optMap.keySet().iterator();
        while (keyIt.hasNext()) {
            String key = keyIt.next();
            String val = optMap.get(key);

            JSONObject obj = new JSONObject();
            obj.put("TEXT", key);
            obj.put("VALUE", val);
            jsonArray.add(obj);
        }


        return new DataBus(context, jsonArray);
    }

    public BOAIMonDomainBean[] getDomainInfo() throws Exception
    {
        IUserPriConstDataDAO dao = (IUserPriConstDataDAO) ServiceFactory.getService(IUserPriConstDataDAO.class);
        return dao.getDomainInfo();
    }

    public void loadTreeTableData() throws Exception
    {
        IUserPriConstDataDAO dao = (IUserPriConstDataDAO) ServiceFactory.getService(IUserPriConstDataDAO.class);
        dao.loadTreeTableData();
    }

}