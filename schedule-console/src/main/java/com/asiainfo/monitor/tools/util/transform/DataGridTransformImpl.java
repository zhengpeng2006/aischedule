package com.asiainfo.monitor.tools.util.transform;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.util.TypeConst;

public class DataGridTransformImpl extends DefaultChartTransformImpl {
	
	public String getGridValue(ITransformData[] datas) {
		StringBuilder sb=new StringBuilder("");
		if (datas!=null  && datas.length>0){
			sb.append("<grid>");
			for (int i=0;i<datas.length;i++) {
				if (StringUtils.isNotBlank(datas[i].getValue())) {
					if (i == 0) {
						sb.append("<row>");
						String[] colStr = StringUtils.split(datas[i].getValue(),TypeConst._SPLIT_CHAR);
						int j=0;
						for (;j<colStr.length;j++) {
							if (StringUtils.isNotBlank(colStr[j])) {
								sb.append("<col"+j+">").append(getNameFromInfo(colStr[j])).append("</col"+j+">");
							}
						}
						if (StringUtils.isNotBlank(datas[i].getTime())) {
							sb.append("<col"+j+">").append(AIMonLocaleFactory.getResource("MOS0000309")).append("</col"+j+">");
						}
						sb.append("</row>");
					}
					sb.append("<row>");
					String[] colStr = StringUtils.split(datas[i].getValue(),'|');
					int j=0;
					for (;j<colStr.length;j++) {
						if (StringUtils.isNotBlank(colStr[j])) {
							sb.append("<col"+j+">").append(getValueFromInfo(colStr[j])).append("</col"+j+">");
						}
					}
					if (StringUtils.isNotBlank(datas[i].getTime())) {
						sb.append("<col"+j+">").append(datas[i].getTime()).append("</col"+j+">");
					}
					sb.append("</row>");
				}
			}
			sb.append("</grid>");
        }
	    return sb.toString();
	}
	
	public String getStatisValue(ITransformData[] datas) {
		StringBuilder sb=new StringBuilder("");
		if (datas!=null && datas.length>0){
			sb.append("<chart>");
			Map hm = new HashMap();
			for (int i=0;i<datas.length;i++) {
				if (StringUtils.isNotBlank(datas[i].getValue())) {
					String[] colStr = StringUtils.split(datas[i].getValue(),TypeConst._SPLIT_CHAR);
					String name ="";
					String value = "";
					if (i == 0) {
						for (int j=0;j<colStr.length;j++) {
							name = getNameFromInfo(colStr[j]);
							value = getValueFromInfo(colStr[j]);
							if (isCanParseInt(value)) {
								if (!hm.containsKey(name)){
									hm.put(name, value);
								} 
							}
						}
						continue;
					}
					for (int j=0;j<colStr.length;j++) {
						name = getNameFromInfo(colStr[j]);
						value = getValueFromInfo(colStr[j]);
						if (isCanParseInt(value)) {
							if (hm.containsKey(name)){
								hm.put(name, Integer.parseInt(hm.get(name).toString())+Integer.parseInt(value));
							}
						}
					}
				}
			}
			if (!hm.isEmpty()) {
				Iterator it = hm.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry entry = (Map.Entry) it.next();
					sb.append("<node name=\"").append(entry.getKey()).append("\" value=\"").append(entry.getValue()).append("\"");
					sb.append(" time=\"").append(datas[0].getTime()).append("\"").append(" />");
				}
			}
			sb.append("</chart>");
        }
	    return sb.toString();
	}
	
	protected String getValueFromInfo(String infoValue) {
		String[] tmpStr = StringUtils.split(infoValue, TypeConst._INTERPRET_CHAR);
		if(tmpStr.length==2){
			return tmpStr[1];
		}
		return "";
	}
	
	protected String getNameFromInfo(String infoValue) {
		String[] tmpStr = StringUtils.split(infoValue, TypeConst._INTERPRET_CHAR);
		if(tmpStr.length==2){
			return tmpStr[0];
		}
		return "";
	}
	
	protected boolean isCanParseInt(String str) {
		if (str == null || str.length() < 1) {
			return false;
		}
		return str.matches("\\d+");
	}
	
	/**
	 * 创建线图根住图数据
	 * @param recordValues
	 * @param objITransform
	 * @return
	 * @throws Exception
	 */
	public List createChartData(ITransformData[] tdatas) throws RemoteException,Exception {
		List ret = new ArrayList();
		StringBuilder result = new StringBuilder("<chart>");
    	result.append(getGridValue(tdatas));
		result.append("</chart>");
		ret.add(result.toString());
        return ret;
	}
	
	public static void main(String args[]) throws Exception {
		DataGridTransformImpl df = new DataGridTransformImpl();
		ITransformData[] datas=new TransformData[4];
		datas[0]=new TransformData();
		datas[0].setValue("地区:杭州|开户量:210|销户量:26|改套餐量:65");
		datas[0].setTime("2011-04-19");
		
		datas[1]=new TransformData();
		datas[1].setValue("地区:湖州|开户量:189|销户量:21|改套餐量:43");
		datas[1].setTime("2011-04-19");
		
		datas[2]=new TransformData();
		datas[2].setValue("地区:绍兴|开户量:225|销户量:31|改套餐量:72");
		datas[2].setTime("2011-04-19");
		
		datas[3]=new TransformData();
		datas[3].setValue("地区:温州|开户量:177|销户量:16|改套餐量:21");
		datas[3].setTime("2011-04-19");
		
		System.out.println(df.getGridValue(datas));
		System.out.println(df.getStatisValue(datas));
	}
}
