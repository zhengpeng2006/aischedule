package com.asiainfo.deploy.test;

import java.util.ArrayList;
import java.util.List;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.deploy.common.bo.BODeployNodeVersionBean;
import com.asiainfo.deploy.dao.interfaces.INodeRefVersionDAO;

public class NodeVersionTest {
	private static INodeRefVersionDAO dao = (INodeRefVersionDAO)ServiceFactory.getService(INodeRefVersionDAO.class);
	
	public static void main(String[] args) throws Exception {
		List<Long> list = new ArrayList<Long>();
		list.add(1L);
		list.add(2L);
		list.add(74L);
		BODeployNodeVersionBean[] beans = dao.qryNodeVersionsByNodes(list);
		System.out.println(beans.length);
	}
}
