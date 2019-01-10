package com.asiainfo.monitor.tools.util.transform;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import com.ai.appframe2.util.StringUtils;




public class DefaultChartTransformImpl implements IChartTransform {
	
	protected int showNamePos;
	
	protected int showValuePos;
	
	public void setShowNamePos(int showNamePos){
		this.showNamePos = showNamePos;
	}
	
	public int getShowNamePos(){
		return showNamePos;
	}
	
	public void setShowValuePos(int showValuePos){
		this.showValuePos = showValuePos;
	}
	
	public int getShowValuePos(){
		return showValuePos;
	}
	
	protected String getName(String infoName, String infoValue) {
		String label=this.getValueFromInfo(infoValue, this.getShowNamePos());
		return label;
	}

	protected String getValue(String infoName, String infoValue,String xvalue) {
		String ret = "<node name=\""+this.getName(infoName,infoValue)+"\" time=\""+xvalue+"\" value=\""+this.getValueFromInfo(infoValue,this.getShowValuePos())+"\"/>";
		return ret;
	}
	
	/**
	 * 指定第deep项的名称值与传入参数item相等
	 * @param item
	 * @param oldString
	 * @return
	 */
	 protected boolean isContentItem(String item,String oldString,int deep){
		  boolean ret = false;
		  String[] tmp = StringUtils.split(oldString,'|');		    
		  if(tmp.length>deep){
		    	String[] tmpValue = StringUtils.split(tmp[deep],':');
		    	if(tmpValue!=null&&tmpValue.length==2){
		    		if(tmpValue[0].equalsIgnoreCase(item)){
	    		     return true;
	    		    }
		    	}
		    }
		  return ret;
	  }
	
	/**
	 * 取指定位数的值,位数为竖线分割的位数
	 * @param fromStr
	 * @param deep
	 * @return
	 */
	protected String getValueFromInfo(String fromStr,int deep){
		String[] tmpStr = StringUtils.split(fromStr, '|');
		if(tmpStr.length>deep){
			String[] tmp = StringUtils.split(tmpStr[deep], ':');
			if(tmp.length==2){
				return tmp[1];
			}
		}
		return "0";
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
		List legendList = new ArrayList();
		List values = new ArrayList();
		List timeList = new ArrayList();  

	        for (int i = 0; i < tdatas.length; i++){
	        	String name=getName(tdatas[i].getName(),tdatas[i].getValue());
	        	String value  =getValue(tdatas[i].getName(),tdatas[i].getValue(),tdatas[i].getTime());
	        	if (!timeList.contains(tdatas[i].getTime()))
	        		timeList.add(tdatas[i].getTime());
	        	if(!legendList.contains(name)){
	        		legendList.add(name);
	        	}
	        	values.add(value);
	        }
	        for(int i=0;i<timeList.size();i++){	        	
	        	StringBuilder result = new StringBuilder("<chart>");
        		for(int j=0;j<legendList.size();j++){
        			String legendName = String.valueOf(legendList.get(j));
        			for(int k=0;k<values.size();k++){
        				String tmpDate  = String.valueOf(timeList.get(i));
        				String tmpValue = String.valueOf(values.get(k));
        				if(tmpValue.indexOf(tmpDate)>-1&&tmpValue.indexOf(legendName)>-1){
        					result.append(tmpValue);
        				}
        			}
        		}
        		result.append("</chart>");
        		ret.add(result.toString());
        	}
        return ret;
	}
}
