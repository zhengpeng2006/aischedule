package com.asiainfo.monitor.busi.config;

import java.util.List;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.cache.MonCacheManager;
import com.asiainfo.monitor.busi.cache.impl.AIMonGroupCacheImpl;
import com.asiainfo.monitor.busi.cache.interfaces.IGroup;
import com.asiainfo.monitor.busi.common.impl.TopuXmlStrategy;
import com.asiainfo.monitor.busi.common.interfaces.IObjectStrategy;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonDomainSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonGroupSV;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonDomainRelatValue;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.util.TypeConst;

public class TopuXmlFactory {

	/**
	 * 返回全局拓扑
	 * @return
	 * @throws Exception
	 */
	public static String getTopTopuXml() throws Exception{
		StringBuilder topuXml=new StringBuilder("<Graph>");		
		try{
			IObjectStrategy strategy=new TopuXmlStrategy();
			IAIMonGroupSV groupSV=(IAIMonGroupSV)ServiceFactory.getService(IAIMonGroupSV.class);
			List groupList=groupSV.getAllGroup();
			if (groupList==null || groupList.size()<1)
				return topuXml.toString();
			topuXml
					.append("<Node name=\"")
					.append(AIMonLocaleFactory.getResource("MOS0000104"))
					.append("\" id=\"-1\" desc=\"")
					.append(AIMonLocaleFactory.getResource("MOS0000104"))
					.append("\" nodeColor=\"")
					.append(TypeConst._NODECOLOR)
					.append("\" nodeSize=\"24\" nodeClass=\"root\" nodeIcon=\"root\"/>");
			
			for (int i=0;i<groupList.size();i++){
				IGroup group=(IGroup)groupList.get(i);
				topuXml.append(strategy.visitGroup(group));				
			}
		}catch(Exception e){
			// 缓存全局拓扑异常
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000221") + ":"+e.getMessage());
		}finally{
			topuXml.append("</Graph>");
			
		}
		return topuXml.toString();
	}
	
	/**
	 * 根据域组合拓扑图
	 * @param domains
	 * @return
	 * @throws Exception
	 */
	public static String getTopuXmlByDomain(String[] domains) throws Exception{
		IAIMonDomainSV domainSV=(IAIMonDomainSV)ServiceFactory.getService(IAIMonDomainSV.class);
		IObjectStrategy strategy=new TopuXmlStrategy();
		StringBuilder domainTopuXml=new StringBuilder("<Graph>");
		domainTopuXml
					.append("<Node name=\"")
					.append(AIMonLocaleFactory.getResource("MOS0000104"))
					.append("\" id=\"-1\" desc=\"")
					.append(AIMonLocaleFactory.getResource("MOS0000104"))
					.append("\" nodeColor=\"")
					.append(TypeConst._NODECOLOR)
					.append("\" nodeSize=\"32\" nodeClass=\"root\" nodeIcon=\"center\"/>");
		for (int i=0;i<domains.length;i++){
			IBOAIMonDomainRelatValue[] relatValues=domainSV.getDomainRelatByTypeAndId("1",domains[i]);
			if (relatValues!=null && relatValues.length>0){
				for (int j=0;j<relatValues.length;j++){
					IGroup group=(IGroup)MonCacheManager.get(AIMonGroupCacheImpl.class,relatValues[j].getRelatdomainId()+"");
					if (group!=null){
						domainTopuXml.append(strategy.visitGroup(group));						
					}
				}
			}
		}
		domainTopuXml.append("</Graph>");
		return domainTopuXml.toString();
	}
	
}
