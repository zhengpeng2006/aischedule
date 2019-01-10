package com.asiainfo.monitor.exeframe.configmgr.view;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.tapestry.IRequestCycle;

import com.ai.appframe2.complex.util.e.K;
import com.ai.appframe2.service.ServiceFactory;
import com.ai.comframe.utils.TimeUtil;
import com.ailk.appengine.util.DataBusHelper;
import com.ailk.appengine.web.view.AppPage;
import com.ailk.common.data.IDataBus;
import com.asiainfo.common.utils.PagingUtil;
import com.asiainfo.monitor.common.utils.XssUtil;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonUsersValue;
import com.asiainfo.monitor.exeframe.configmgr.service.interfaces.IAIMonUsersSV;
import com.asiainfo.monitor.exeframe.controlmgr.common.UserPrivUtils;
import com.asiainfo.resultConstant.ResultConstants;

/**
 * Created by SMT Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
public abstract class UsersInfo extends AppPage
{
    
    /**
     * 根据查询条件查询用户信息
     * 
     * @param request
     * @throws Exception
     */
    public void loadUsersInfo(IRequestCycle cycle)
        throws Exception
    {
        // 分页使用
        String offset = getContext().getParameter("offset");
        String linage = getContext().getParameter("linage");
        
        IDataBus qryformBus = createData("qryForm");
        JSONObject qryformCondition = (JSONObject)qryformBus.getData();
        
        int start = Integer.parseInt(qryformBus.getContext().getPaginStart() + "");
        int end = Integer.parseInt(qryformBus.getContext().getPaginEnd() + "");
        
        String userCode = (String)qryformCondition.get("userCode");
        
        IAIMonUsersSV sv = (IAIMonUsersSV)ServiceFactory.getService(IAIMonUsersSV.class);
        IBOAIMonUsersValue[] result = sv.qryUsersInfoByUserCode(userCode);
        
        IDataBus bus = null;
        if (result == null)
            bus = DataBusHelper.getEmptyArrayDataBus();
        else
            bus = DataBusHelper.getDataBusByBeans(result, IBOAIMonUsersValue.class);
        
        JSONArray dataArray = bus.getDataArray();
        Iterator iterator = dataArray.iterator();
        while (iterator.hasNext())
        {
            JSONObject obj = (JSONObject)iterator.next();
            if (obj.get("createDate").toString().length() > 8)
            {
                obj.put("createDate", obj.get("createDate").toString().substring(0, 10));
            }
        }
        /*
         * setUsersTabItems(bus.getDataArray());
         */
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("usersTabItems", PagingUtil.convertArray2Page(bus.getDataArray(), offset, linage));
        jsonObject.put("total", bus.getDataArray().size());
        this.setAjax(jsonObject);
        
    }
    
    /**
     * 编辑或新增后刷新
     * 
     * @param request
     * @throws Exception
     */
    public void freshUsersInfo(IRequestCycle cycle)
        throws Exception
    {
        IAIMonUsersSV sv = (IAIMonUsersSV)ServiceFactory.getService(IAIMonUsersSV.class);
        IBOAIMonUsersValue[] result = sv.qryUsersInfoByUserCode(null);
        
        IDataBus bus = null;
        if (result == null)
            bus = DataBusHelper.getEmptyArrayDataBus();
        else
            bus = DataBusHelper.getDataBusByBeans(result, IBOAIMonUsersValue.class);
        
        JSONArray dataArray = bus.getDataArray();
        Iterator iterator = dataArray.iterator();
        while (iterator.hasNext())
        {
            JSONObject obj = (JSONObject)iterator.next();
            if (obj.get("createDate").toString().length() > 8)
            {
                obj.put("createDate", obj.get("createDate").toString().substring(0, 10));
            }
        }
        setUsersTabItems(bus.getDataArray());
    }
    
    /**
     * 根据用户标识查询用户信息
     * 
     * @param request
     * @throws Exception
     */
    public void qryUserInfoDetail(IRequestCycle cycle)
        throws Exception
    {
        String userId = getContext().getParameter("userId");
        IAIMonUsersSV uSv = (IAIMonUsersSV)ServiceFactory.getService(IAIMonUsersSV.class);
        IBOAIMonUsersValue value = uSv.qryUserById(userId);
        
        IDataBus dataBus = null;
        if (value == null)
        {
            dataBus = DataBusHelper.getEmptyArrayDataBus();
        }
        else
        {
            dataBus = DataBusHelper.getDataBusByBeans(value, IBOAIMonUsersValue.class);
        }
        
        /* this.setUsersForm(dataBus.getDataObject()); */
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("usersForm", dataBus.getDataObject());
        this.setAjax(jsonObject);
    }
    
    /**
     * 根据用户标识删除用户信息
     * 
     * @param request
     * @throws Exception
     */
    public void delUser(IRequestCycle cycle)
        throws Exception
    {
        String userId = getContext().getParameter("userId");
        IAIMonUsersSV uSv = (IAIMonUsersSV)ServiceFactory.getService(IAIMonUsersSV.class);
        IBOAIMonUsersValue value = uSv.qryUserById(userId);
        uSv.delete(value);
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(ResultConstants.ResultKey.RESULT_CODE, ResultConstants.ResultCodeValue.SUCCESS);
        this.setAjax(jsonObject);
    }
    
    /**
     * 新增或修改用户信息
     * 
     * @param request
     * @throws Exception
     */
    public void saveOrUpdate(IRequestCycle request)
        throws Exception
    {
        Map<String, String> retMap = new HashMap<String, String>();
        IDataBus dataBus = this.createData("usersForm");
        IBOAIMonUsersValue value = (IBOAIMonUsersValue)DataBusHelper.getBOBean(dataBus);
        IAIMonUsersSV huSv = (IAIMonUsersSV)ServiceFactory.getService(IAIMonUsersSV.class);
        boolean b = huSv.userCodeIsExist(value.getUserCode());
        
        if (value.getUserId() == 0)
        {
            if (b)
            {
                retMap.put("SAVE_FLAG", ResultConstants.ResultCodeValue.FAILED);
                retMap.put("msg", "\u7528\u6237\u7f16\u7801\u5df2\u5b58\u5728");
            }
            else
            {
                // 保存用户信息之前要先进行校验
                boolean bool = XssUtil.checkBeforeSave(value, value.getClass(), null);
                value.setUserPwd(K.j(value.getUserPwd()));// 加密密码
                if (bool)
                {
                    huSv.save(value);
                    retMap.put("SAVE_FLAG", ResultConstants.ResultCodeValue.SUCCESS);
                }
                else
                {
                    retMap.put("SAVE_FLAG", ResultConstants.ResultCodeValue.FAILED);
                    retMap.put("msg", ResultConstants.ResultCodeValue.CHECK_FAILED);
                }
            }
        }
        else
        {
            IBOAIMonUsersValue oldValue = huSv.qryUserById(Long.toString(value.getUserId()));
            
            value.setModifyDate(TimeUtil.getSysTime());
            String userId = Long.toString(value.getUserId());
            IBOAIMonUsersValue userValue = huSv.qryUserById(userId);
            
            if (userValue.getUserCode().equals(value.getUserCode()))
            {
                // 保存用户信息之前要先进行校验
                boolean bool = XssUtil.checkBeforeSave(value, value.getClass(), null);
                // 若是密码改变了，就进行加密
                if (!value.getUserPwd().equals(oldValue.getUserPwd()))
                {
                    value.setUserPwd(K.j(value.getUserPwd()));// 加密密码
                }
                if (bool)
                {
                    huSv.save(value);
                    retMap.put("SAVE_FLAG", ResultConstants.ResultCodeValue.SUCCESS);
                }
                else
                {
                    retMap.put("SAVE_FLAG", ResultConstants.ResultCodeValue.FAILED);
                    retMap.put("msg", ResultConstants.ResultCodeValue.CHECK_FAILED);
                }
            }
            else
            {
                boolean userNameIsExist = huSv.userCodeIsExist(value.getUserCode());
                if (userNameIsExist)
                {
                    retMap.put("SAVE_FLAG", ResultConstants.ResultCodeValue.FAILED);
                    retMap.put("msg", "\u7528\u6237\u7f16\u7801\u5df2\u5b58\u5728");
                }
                else
                {
                    // 保存用户信息之前要先进行校验
                    boolean bool = XssUtil.checkBeforeSave(value, value.getClass(), null);
                    // 若是密码改变了，就进行加密操作
                    if (!value.getUserPwd().equals(oldValue.getUserPwd()))
                    {
                        value.setUserPwd(K.j(value.getUserPwd()));// 加密密码
                    }
                    if (bool)
                    {
                        huSv.save(value);
                        retMap.put("SAVE_FLAG", ResultConstants.ResultCodeValue.SUCCESS);
                    }
                    else
                    {
                        retMap.put("SAVE_FLAG", ResultConstants.ResultCodeValue.FAILED);
                        retMap.put("msg", ResultConstants.ResultCodeValue.CHECK_FAILED);
                    }
                }
            }
        }
        JSONObject jsonObj = new JSONObject();
        jsonObj.put(ResultConstants.ResultKey.RESULT_CODE, retMap.get("SAVE_FLAG"));
        jsonObj.put(ResultConstants.ResultKey.RESULT_MSG, retMap.get("msg"));
        this.setAjax(jsonObj);
    }
    
    /**
     * 前台状态翻译
     * 
     * @param type
     * @param key
     * @return
     * @throws Exception
     * @throws RemoteException
     */
    public String doTranslate(String type, String val)
        throws RemoteException, Exception
    {
        String text = null;
        if ("USER_STATE".equals(type))
        {
            text = UserPrivUtils.doTranslate(type, val);
        }
        return text;
    }
    
    private void initExtend(IRequestCycle cycle)
    {
        
    }
    
    private void initExtend()
    {
        
    }
    
    public abstract void setUsersForm(JSONObject usersForm);
    
    public abstract void setUsersTabItems(JSONArray usersTabItems);
    
    public abstract void setUsersTabItem(JSONObject usersTabItem);
    
    public abstract void setQryForm(JSONObject qryForm);
    
    public abstract void setUsersTabCount(long usersTabCount);
    
    public abstract void setUsersTabRowIndex(int usersTabRowIndex);
    
    @Override
    protected void initPageAttrs()
    {
        bindBoName("usersTab", "com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonUsers");
        
        bindBoName("usersForm", "com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonUsers");
        
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        IDataBus usersForm = createData("usersForm");
        if (usersForm != null && !usersForm.getDataObject().isEmpty())
        {
            setUsersForm(usersForm.getDataObject());
        }
        IDataBus usersTabItem = createData("usersTabItem");
        if (usersTabItem != null && !usersTabItem.getDataObject().isEmpty())
        {
            setUsersTabItem(usersTabItem.getDataObject());
        }
        IDataBus qryForm = createData("qryForm");
        if (qryForm != null && !qryForm.getDataObject().isEmpty())
        {
            setQryForm(qryForm.getDataObject());
        }
        initExtend(cycle);
    }
    
}
