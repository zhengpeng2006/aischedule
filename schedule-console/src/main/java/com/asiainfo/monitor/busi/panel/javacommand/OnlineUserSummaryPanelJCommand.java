package com.asiainfo.monitor.busi.panel.javacommand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.ai.appframe2.util.locale.AppframeLocaleFactory;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonStaticDataSV;
import com.asiainfo.monitor.busi.service.interfaces.IAPIShowUserSV;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonStaticDataValue;
import com.asiainfo.monitor.tools.common.KeyName;
import com.asiainfo.monitor.tools.common.interfaces.IJavaCommand;
import com.asiainfo.monitor.tools.util.TypeConst;

public class OnlineUserSummaryPanelJCommand implements IJavaCommand {

	private final static transient Log log=LogFactory.getLog(OnlineUserSummaryPanelJCommand.class);
	
	/**
	 * 实时面板：在线用户统计信息，地区、总人数、终端数
	 */
	public String execute(KeyName[] parameter) throws Exception {
		String result=null;
		if (parameter==null || parameter.length<1)
			return result;
		String appId="";
		for (int i=0;i<parameter.length;i++){
			if (parameter[i].getName().equals(TypeConst.SERVER+TypeConst._ID)){
				appId=parameter[i].getKey();
				break;
			}
		}
		if (StringUtils.isBlank(appId))
			appId=parameter[0].getKey();
		
		if (StringUtils.isNotBlank(appId)){
			IAPIShowUserSV showUserSV=(IAPIShowUserSV)ServiceFactory.getService(IAPIShowUserSV.class);
			List userArray=showUserSV.getOnlineUsers(appId);
//			List userArray=fetchOnlineUserByServer(appId);
			
			IAIMonStaticDataSV staticDataSV=(IAIMonStaticDataSV)ServiceFactory.getService(IAIMonStaticDataSV.class);
			IBOAIMonStaticDataValue[] regionValues=staticDataSV.queryByCodeType("REGION_MAP");
			Map regionDefineMap=new HashMap();
			for (int i=0;i<regionValues.length;i++){
				regionDefineMap.put(regionValues[i].getCodeValue().toUpperCase(), regionValues[i].getCodeName());
			}
			Map regionUsersMap=new HashMap();
			Map regionIpMap=new HashMap();
			
			if (userArray!=null){
				for (int i=0;i<userArray.size();i++){
					Map attrMap = (Map)((Map)userArray.get(i)).get("ATTRS");
				    String realRegionId = ((String)attrMap.get("REGION_ID")).trim();
				    String ip=String.valueOf(((Map)userArray.get(i)).get("IP"));
				    //设置地区、人员总数
				    if (regionUsersMap.containsKey(realRegionId)){
				    	Integer uCount=(Integer)regionUsersMap.get(realRegionId);
				    	uCount=uCount+1;
				    	regionUsersMap.put(realRegionId,uCount);				    	
				    }else{
				    	Integer uCount=new Integer(1);
				    	regionUsersMap.put(realRegionId,uCount);				    	
				    }
				    //设置地区、终端总数
				    if (regionIpMap.containsKey(realRegionId)){				    	
				    	List ipList=(List)regionIpMap.get(realRegionId);
				    	if (ipList==null){
				    		ipList=new ArrayList();
				    		ipList.add(ip);
				    		regionIpMap.put(realRegionId, ipList);
				    	}else{
				    		if (!ipList.contains(ip)){
				    			ipList.add(ip);
				    			regionIpMap.put(realRegionId, ipList);
				    		}
				    	}
				    }else{
				    	List ipList=new ArrayList();
				    	ipList.add(ip);
				    	regionIpMap.put(realRegionId, ipList );
				    }
				}
			}
			List summaryList=new ArrayList();
			for (Iterator it=regionUsersMap.keySet().iterator();it.hasNext();){
				String regionId=String.valueOf(it.next());
				String regionName="";
				if (regionDefineMap.containsKey(regionId.toUpperCase())){
					regionName=String.valueOf(regionDefineMap.get(regionId.toUpperCase()));
				}
				int userSize=((Integer)regionUsersMap.get(regionId)).intValue();
				List ipList=(List)regionIpMap.get(regionId);
				int ipSize=ipList!=null && ipList.size()>0?ipList.size():userSize;
				Map item=new HashMap();
				item.put("REGION_ID", regionId);
				item.put("REGION_NAME", regionName);
				item.put("USERCOUNT", userSize);
				item.put("IPCOUNT", ipSize);
				summaryList.add(item);
			}
			StringBuffer sb=new StringBuffer();
			for (int i=0;i<summaryList.size();i++){
				Map itemMap=(Map)summaryList.get(i);
				if (itemMap!=null){
					if (i>0)
						sb.append(TypeConst._NEWLINE_CHAR);
					sb.append("NAME:");
					sb.append(itemMap.get("REGION_NAME"));
					sb.append(TypeConst._SPLIT_CHAR);
					sb.append("USERCOUNT:");
					sb.append(itemMap.get("USERCOUNT")==null?0:itemMap.get("USERCOUNT"));
					sb.append(TypeConst._SPLIT_CHAR);
					sb.append("IPCOUNT:");
					sb.append(itemMap.get("IPCOUNT")==null?0:itemMap.get("IPCOUNT"));
				}
			}
			result=sb.toString();
		}
		return result;
	}

	public List fetchOnlineUserByServer(String appId) throws Exception {
		List result=new ArrayList();
		try {
			if (appId.equals("762")){
				HashMap[] rtn = new HashMap[14];
				rtn[0] = new HashMap();
				rtn[0].put("SERVER_NAME", "crm-web-g13-srv1");
				rtn[0].put("ID", new Long(102));
				rtn[0].put("IP", "10.70.135.213");
				rtn[0].put("CODE", "linjianghui");
				rtn[0].put("NAME", "林江辉");
				rtn[0].put("ORG_NAME", "浙江移动总公司");
				rtn[0].put("ATTRS", "ATTRS");
				Map attrMap0 = new HashMap();
				attrMap0.put("REGION_ID", "573");
				rtn[0].put("ATTRS", attrMap0);
				result.add(rtn[0]);
				
				rtn[1] = new HashMap();
				rtn[1].put("SERVER_NAME", "crm-web-g13-srv1");
				rtn[1].put("ID", new Long(103));
				rtn[1].put("IP", "10.73.102.220");
				rtn[1].put("CODE", "feijie2");
				rtn[1].put("NAME", "斐捷");
				rtn[1].put("ORG_NAME", "桐乡振兴中路营业厅");
				rtn[1].put("ATTRS", "ATTRS");
				Map attrMap1 = new HashMap();
				attrMap1.put("REGION_ID", "573");
				rtn[1].put("ATTRS", attrMap1);
				result.add(rtn[1]);
				
				rtn[2] = new HashMap();
				rtn[2].put("SERVER_NAME", "crm-web-g13-srv2");
				rtn[2].put("ID", new Long(104));
				rtn[2].put("IP", "10.73.124.100");
				rtn[2].put("CODE", "zhangliy");
				rtn[2].put("NAME", "张丽艳");
				rtn[2].put("ORG_NAME", "嘉兴嘉禾");
				rtn[2].put("ATTRS", "ATTRS");
				Map attrMap2 = new HashMap();
				attrMap2.put("REGION_ID", "573");
				rtn[2].put("ATTRS", attrMap2);
				result.add(rtn[2]);
				
				rtn[3] = new HashMap();
				rtn[3].put("SERVER_NAME", "crm-web-g13-srv3");
				rtn[3].put("ID", new Long(105));
				rtn[3].put("IP", "10.73.108.127");
				rtn[3].put("CODE", "dongqin");
				rtn[3].put("NAME", "董勤");
				rtn[3].put("ORG_NAME", "海盐勤俭路营业厅");
				rtn[3].put("ATTRS", "ATTRS");
				Map attrMap3 = new HashMap();
				attrMap3.put("REGION_ID", "573");
				rtn[3].put("ATTRS", attrMap3);
				result.add(rtn[3]);
				
				rtn[4] = new HashMap();
				rtn[4].put("SERVER_NAME", "crm-web-g13-srv1");
				rtn[4].put("ID", new Long(106));
				rtn[4].put("IP", "10.73.124.51");
				rtn[4].put("CODE", "zhangjinhua");
				rtn[4].put("NAME", "张金华");
				rtn[4].put("ORG_NAME", "嘉兴南湖禾兴南路动感地带旗舰店");
				rtn[4].put("ATTRS", "ATTRS");
				Map attrMap4 = new HashMap();
				attrMap4.put("REGION_ID", "573");
				rtn[4].put("ATTRS", attrMap4);
				result.add(rtn[4]);
				
				rtn[5] = new HashMap();
				rtn[5].put("SERVER_NAME", "crm-web-g13-srv1");
				rtn[5].put("ID", new Long(102));
				rtn[5].put("IP", "10.73.124.32");
				rtn[5].put("CODE", "zhouqin2");
				rtn[5].put("NAME", "周勤");
				rtn[5].put("ORG_NAME", "嘉兴华庭街营业厅");
				rtn[5].put("ATTRS", "ATTRS");
				Map attrMap5 = new HashMap();
				attrMap5.put("REGION_ID", "573");
				rtn[5].put("ATTRS", attrMap5);
				result.add(rtn[5]);
				
				rtn[6] = new HashMap();
				rtn[6].put("SERVER_NAME", "crm-web-g13-srv1");
				rtn[6].put("ID", new Long(112));
				rtn[6].put("IP", "10.73.108.126");
				rtn[6].put("CODE", "yangfang3");
				rtn[6].put("NAME", "杨芳");
				rtn[6].put("ORG_NAME", "海盐勤俭路营业厅");
				rtn[6].put("ATTRS", "ATTRS");
				Map attrMap6 = new HashMap();
				attrMap6.put("REGION_ID", "573");
				rtn[6].put("ATTRS", attrMap6);
				result.add(rtn[6]);
				
				rtn[7] = new HashMap();
				rtn[7].put("SERVER_NAME", "crm-web-g13-srv1");
				rtn[7].put("ID", new Long(107));
				rtn[7].put("IP", "10.73.122.62");
				rtn[7].put("CODE", "zhuguiyuan");
				rtn[7].put("NAME", "朱桂员");
				rtn[7].put("ORG_NAME", "海盐新桥北路营业厅");
				rtn[7].put("ATTRS", "ATTRS");
				Map attrMap7 = new HashMap();
				attrMap7.put("REGION_ID", "573");
				rtn[7].put("ATTRS", attrMap7);
				result.add(rtn[7]);
				
				rtn[8] = new HashMap();
				rtn[8].put("SERVER_NAME", "crm-web-g13-srv1");
				rtn[8].put("ID", new Long(90));
				rtn[8].put("IP", "10.73.112.149");
				rtn[8].put("CODE", "xujiali");
				rtn[8].put("NAME", "徐佳丽");
				rtn[8].put("ORG_NAME", "海宁水月亭路营业厅");
				rtn[8].put("ATTRS", "ATTRS");
				Map attrMap8 = new HashMap();
				attrMap8.put("REGION_ID", "573");
				rtn[8].put("ATTRS", attrMap8);
				result.add(rtn[8]);
				
				rtn[9] = new HashMap();
				rtn[9].put("SERVER_NAME", "crm-web-g13-srv4");
				rtn[9].put("ID", new Long(12));
				rtn[9].put("IP", "10.73.8.114");
				rtn[9].put("CODE", "xiayu3");
				rtn[9].put("NAME", "夏雨");
				rtn[9].put("ORG_NAME", "嘉兴斜西街营业厅");
				rtn[9].put("ATTRS", "ATTRS");
				Map attrMap9 = new HashMap();
				attrMap9.put("REGION_ID", "573");
				rtn[9].put("ATTRS", attrMap9);
				result.add(rtn[9]);
				
				rtn[10] = new HashMap();
				rtn[10].put("SERVER_NAME", "crm-web-g13-srv1");
				rtn[10].put("ID", new Long(98));
				rtn[10].put("IP", "10.73.112.150");
				rtn[10].put("CODE", "zhuliye");
				rtn[10].put("NAME", "朱丽叶");
				rtn[10].put("ORG_NAME", "海宁水月亭路营业厅");
				rtn[10].put("ATTRS", "ATTRS");
				Map attrMap10 = new HashMap();
				attrMap10.put("REGION_ID", "573");
				rtn[10].put("ATTRS", attrMap10);
				result.add(rtn[10]);
				
				rtn[11] = new HashMap();
				rtn[11].put("SERVER_NAME", "crm-web-g13-srv1");
				rtn[11].put("ID", new Long(127));
				rtn[11].put("IP", "10.73.104.120");
				rtn[11].put("CODE", "panjinyue");
				rtn[11].put("NAME", "潘金跃");
				rtn[11].put("ORG_NAME", "平湖当湖路营业厅");
				rtn[11].put("ATTRS", "ATTRS");
				Map attrMap11 = new HashMap();
				attrMap11.put("REGION_ID", "573");
				rtn[11].put("ATTRS", attrMap11);
				result.add(rtn[11]);
				
				rtn[12] = new HashMap();
				rtn[12].put("SERVER_NAME", "crm-web-g13-srv1");
				rtn[12].put("ID", new Long(98));
				rtn[12].put("IP", "10.70.54.211");
				rtn[12].put("CODE", "huangyun1");
				rtn[12].put("NAME", "黄云");
				rtn[12].put("ORG_NAME", "浙江移动总公司");
				rtn[12].put("ATTRS", "ATTRS");
				Map attrMap12 = new HashMap();
				attrMap12.put("REGION_ID", "573");
				rtn[12].put("ATTRS", attrMap12);
				result.add(rtn[12]);
				
				rtn[13] = new HashMap();
				rtn[13].put("SERVER_NAME", "crm-web-g13-srv1");
				rtn[13].put("ID", new Long(127));
				rtn[13].put("IP", "10.73.129.30");
				rtn[13].put("CODE", "jiangdanyan");
				rtn[13].put("NAME", "蒋丹燕");
				rtn[13].put("ORG_NAME", "嘉兴中山西路营业厅");
				rtn[13].put("ATTRS", "ATTRS");
				Map attrMap13 = new HashMap();
				attrMap13.put("REGION_ID", "573");
				rtn[13].put("ATTRS", attrMap13);
				result.add(rtn[13]);
				
			}
			
			if (appId.equals("721")){
				HashMap[] rtn = new HashMap[7];
				rtn[0] = new HashMap();
				rtn[0].put("SERVER_NAME", "crm-web-g1-srv8");
				rtn[0].put("ID", new Long(102));
				rtn[0].put("IP", "10.70.135.213");
				rtn[0].put("CODE", "linjianghui");
				rtn[0].put("NAME", "林江辉");
				rtn[0].put("ORG_NAME", "浙江移动总公司");
				rtn[0].put("ATTRS", "ATTRS");
				Map attrMap0 = new HashMap();
				attrMap0.put("REGION_ID", "571");
				rtn[0].put("ATTRS", attrMap0);
				result.add(rtn[0]);
				
				rtn[1] = new HashMap();
				rtn[1].put("SERVER_NAME", "crm-web-g1-srv8");
				rtn[1].put("ID", new Long(103));
				rtn[1].put("IP", "10.73.102.220");
				rtn[1].put("CODE", "feijie2");
				rtn[1].put("NAME", "斐捷");
				rtn[1].put("ORG_NAME", "桐乡振兴中路营业厅");
				rtn[1].put("ATTRS", "ATTRS");
				Map attrMap1 = new HashMap();
				attrMap1.put("REGION_ID", "571");
				rtn[1].put("ATTRS", attrMap1);
				result.add(rtn[1]);
				
				rtn[2] = new HashMap();
				rtn[2].put("SERVER_NAME", "crm-web-g1-srv8");
				rtn[2].put("ID", new Long(104));
				rtn[2].put("IP", "10.73.124.100");
				rtn[2].put("CODE", "zhangliy");
				rtn[2].put("NAME", "张丽艳");
				rtn[2].put("ORG_NAME", "嘉兴嘉禾");
				rtn[2].put("ATTRS", "ATTRS");
				Map attrMap2 = new HashMap();
				attrMap2.put("REGION_ID", "571");
				rtn[2].put("ATTRS", attrMap2);
				result.add(rtn[2]);
				
				rtn[3] = new HashMap();
				rtn[3].put("SERVER_NAME", "crm-web-g1-srv8");
				rtn[3].put("ID", new Long(105));
				rtn[3].put("IP", "10.73.108.127");
				rtn[3].put("CODE", "dongqin");
				rtn[3].put("NAME", "董勤");
				rtn[3].put("ORG_NAME", "海盐勤俭路营业厅");
				rtn[3].put("ATTRS", "ATTRS");
				Map attrMap3 = new HashMap();
				attrMap3.put("REGION_ID", "571");
				rtn[3].put("ATTRS", attrMap3);
				result.add(rtn[3]);
				
				rtn[4] = new HashMap();
				rtn[4].put("SERVER_NAME", "crm-web-g1-srv8");
				rtn[4].put("ID", new Long(106));
				rtn[4].put("IP", "10.73.124.51");
				rtn[4].put("CODE", "zhangjinhua");
				rtn[4].put("NAME", "张金华");
				rtn[4].put("ORG_NAME", "嘉兴南湖禾兴南路动感地带旗舰店");
				rtn[4].put("ATTRS", "ATTRS");
				Map attrMap4 = new HashMap();
				attrMap4.put("REGION_ID", "571");
				rtn[4].put("ATTRS", attrMap4);
				result.add(rtn[4]);
				
				rtn[5] = new HashMap();
				rtn[5].put("SERVER_NAME", "crm-web-g1-srv8");
				rtn[5].put("ID", new Long(102));
				rtn[5].put("IP", "10.73.124.32");
				rtn[5].put("CODE", "zhouqin2");
				rtn[5].put("NAME", "周勤");
				rtn[5].put("ORG_NAME", "嘉兴华庭街营业厅");
				rtn[5].put("ATTRS", "ATTRS");
				Map attrMap5 = new HashMap();
				attrMap5.put("REGION_ID", "571");
				rtn[5].put("ATTRS", attrMap5);
				result.add(rtn[5]);
				
				rtn[6] = new HashMap();
				rtn[6].put("SERVER_NAME", "crm-web-g1-srv8");
				rtn[6].put("ID", new Long(112));
				rtn[6].put("IP", "10.73.108.126");
				rtn[6].put("CODE", "yangfang3");
				rtn[6].put("NAME", "杨芳");
				rtn[6].put("ORG_NAME", "海盐勤俭路营业厅");
				rtn[6].put("ATTRS", "ATTRS");
				Map attrMap6 = new HashMap();
				attrMap6.put("REGION_ID", "571");
				rtn[6].put("ATTRS", attrMap6);
				result.add(rtn[6]);
				
				
			}
			
		} catch (Exception ex) {
			log.error(AppframeLocaleFactory.getResource("com.ai.appframe2.complex.mbean.standard.session.login_user_exception"),ex);// 获得已登录的用户异常
		}
		return result;
	}
	
	public static void main(String[] args){
		OnlineUserSummaryPanelJCommand command=new OnlineUserSummaryPanelJCommand();
		KeyName[] parameters=new KeyName[1];
		parameters[0]=new KeyName("SERVER_ID","762");
		try{
			System.out.println(command.execute(parameters));
		}catch(Exception e){
			
		}
	}
}
