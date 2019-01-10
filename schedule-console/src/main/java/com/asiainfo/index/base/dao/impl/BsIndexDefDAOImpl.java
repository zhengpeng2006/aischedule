package com.asiainfo.index.base.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.index.base.bo.BOBsIndexDefBean;
import com.asiainfo.index.base.bo.BOBsIndexDefEngine;
import com.asiainfo.index.base.dao.interfaces.IBsIndexDefDAO;
import com.asiainfo.index.base.ivalues.IBOBsIndexDefValue;
import com.asiainfo.index.service.interfaces.IBsIndexSV;

public class BsIndexDefDAOImpl implements IBsIndexDefDAO{
	@Override
	public BOBsIndexDefBean getBeanById(int id) throws Exception {
		return BOBsIndexDefEngine.getBean(id);
	}
	
	/**
     * 获取所有指标维度的对应显示名
     * @return  Map<Integer,String> key为指标编号，value为显示名
     * @throws Exception
     */
    @Override
    public Map<Integer, String> getAllIndexName() throws Exception
    {
        IBOBsIndexDefValue[] values= BOBsIndexDefEngine.getBeans(null,null);
        Map<Integer,String> map=null;
        if(values!=null&&values.length>0) {
            map=new HashMap<Integer, String>();
            for(IBOBsIndexDefValue value:values) {
                map.put(value.getIndexId(), value.getIndexName()+"&"+value.getIndexCode());
            }
        }
        return map;
    }
    public static void main(String[] args) throws Exception
    {
        IBsIndexSV bsIndexSV=(IBsIndexSV)ServiceFactory.getService(IBsIndexSV.class);
        bsIndexSV.getAllIndexName();
    }
}
