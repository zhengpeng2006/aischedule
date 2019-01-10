package com.asiainfo.monitor.busi.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonDomainSV;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonDomainValue;

public class DomainAction {
	
    private static transient Log log=LogFactory.getLog(DataSrcMonitorAction.class);

    /**
     * 获取所有域列表
     * @return
     * @throws Exception
     */
    public IBOAIMonDomainValue[] getAllDomain()throws Exception{
        try{
            IAIMonDomainSV sv = (IAIMonDomainSV)ServiceFactory.getService(IAIMonDomainSV.class);
            return sv.getDomains(null, null, null, null);
        }catch(Exception e){
            log.error("Call DomainAction's Method getAllDomain has Exception :"+e.getMessage());
            return null;
        }
    }
    
    /**
     * 根据条件查询域列表
     * @param domainCode
     * @param domainType
     * @param domainDesc
     * @param domainState
     * @return
     * @throws Exception
     */
    public List getDomains(String domainCode,String domainType,String domainDesc,String domainState)throws Exception{
        try{
            List rlt = new ArrayList();
            Map tmp = null;
            IAIMonDomainSV sv = (IAIMonDomainSV)ServiceFactory.getService(IAIMonDomainSV.class);
            IBOAIMonDomainValue[] values = sv.getDomains(domainCode, domainType, domainDesc, domainState);
            for(int i=0;i<values.length;i++){
                tmp = new HashMap();
                tmp.put("CHK", "false");
                tmp.put("DOMAIN_CODE", values[i].getDomainCode());
                tmp.put("DOMAIN_ID", String.valueOf(values[i].getDomainId()));
                tmp.put("DOMAIN_TYPE", values[i].getDomainType());
                tmp.put("DOMAIN_DESC", values[i].getDomainDesc());
                tmp.put("DOMAIN_STATE", values[i].getState());
                tmp.put("DOMAIN_REMARK", values[i].getRemarks());
                rlt.add(tmp);
            }
            return rlt;
        }catch(Exception e){
            log.error("Call DomainAction's Method getDomains has Exception :"+e.getMessage());
            return null;
        }       
    }

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception{
        DomainAction da = new DomainAction();
        IBOAIMonDomainValue[] values = da.getAllDomain();
        for(int i=0;i<values.length;i++){
            System.out.println(values[i].getDomainCode());
        }
    }

}
