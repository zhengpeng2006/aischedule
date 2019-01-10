package com.asiainfo.index.base.dao.interfaces;

import java.util.Map;

import com.asiainfo.index.base.bo.BOBsIndexDefBean;

public interface IBsIndexDefDAO {
	BOBsIndexDefBean getBeanById(int id) throws Exception;
	
	/**
     * 获取所有指标维度的对应显示名
     * @return  Map<Integer,String> key为指标编号，value为显示名
     * @throws Exception
     */
    Map<Integer, String> getAllIndexName() throws Exception;
}
