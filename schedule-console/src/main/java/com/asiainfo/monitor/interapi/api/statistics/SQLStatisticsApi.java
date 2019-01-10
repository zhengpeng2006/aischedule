package com.asiainfo.monitor.interapi.api.statistics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.DataContainerInterface;
import com.asiainfo.monitor.interapi.config.RuleItemProp;
import com.asiainfo.monitor.tools.common.interfaces.INodeInfo;

/**
 * SQL统计API
 * @author Wangdy2
 *
 */

public class SQLStatisticsApi {
    private static transient Log log = LogFactory.getLog(SQLStatisticsApi.class);
	
	private static String logSplitChar=" - ";
	private static String fieldSplitChar="\\|";
	private static String innerSplitChar="\\:";
	private static String methodSplitChar="。";
	
	public SQLStatisticsApi() {
		super();
		// TODO 自动生成构造函数存根
	}

	/**
	 * 获取指定的Service日志
	 * @param file:日志文件
	 * @return DataContainerInterface[]
	 * @throws IOException
	 * @throws Exception
	 */
	public DataContainerInterface[] readLogFile (File file) throws IOException,Exception{
		List results = null;
		if (file.exists()&&file.isFile()){
			String line = null;
			BufferedReader br = null;
			DataContainerInterface dc = null;
			String logDate = null;
			try{
				br = new BufferedReader(new FileReader(file));
				results = new ArrayList();
				while ((line = br.readLine()) != null){
					dc = new DataContainer();
					String[] lineargs = line.split(logSplitChar);
					logDate = lineargs[0].trim();
					logDate = logDate.substring(logDate.indexOf("[") + 1, logDate.indexOf(","));
					java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date date = formatter.parse(logDate);
					dc.set("LogDate", new Long(date.getTime()));
					line = lineargs[1].trim();
					String[] args = line.split(fieldSplitChar);
					for (int i = 0; i < args.length; i++){
						String[] inner = args[i].split(innerSplitChar);
						if (inner.length > 1){
							dc.set(inner[0].trim(), (Object)inner[1].trim());
						}else{
							dc.set(inner[0].trim(), null);
						}
					}
					results.add(dc);
				}
				br.close();
			} catch (IOException e){
				log.error(e.getMessage(), e);
				throw new Exception(e);
			} finally {
				if (br != null) {
					br.close();
				}
			}
		}
		return (DataContainerInterface[])results.toArray(new DataContainerInterface[0]);
	}
	
	/**
	 * 根据统计规则分析日志信息，返回过滤结果
	 * @param DataContainerInterface[]:日志信息
	 * @param INodeInfo:统计规则
	 * @return DataContainerInterface[]
	 * @throws Exception
	 */
	public DataContainerInterface[] getLogInfoByRule (DataContainerInterface[] dc, INodeInfo itemProp) throws Exception{
		List results = null;
		if (dc != null && ((RuleItemProp)itemProp).getValue() != null){
			results = new ArrayList();
			String dcValue = null;  
			String ruleKey = ((RuleItemProp)itemProp).getKey().toString();
			String ruleValue = ((RuleItemProp)itemProp).getValue().toString();
			for (int i = 0; i < dc.length; i++) {
				dcValue = dc[i].get(ruleKey).toString();
				if (dcValue.indexOf(ruleValue) != -1){
					results.add(dc[i]);
				} 
			}
		}
		return (DataContainerInterface[])results.toArray(new DataContainerInterface[0]);
	}
	
	/**
	 * 根据统计规则统计日志信息，返回统计结果
	 * @param DataContainerInterface[]:日志信息
	 * @param INodeInfo:统计规则
	 * @return DataContainerInterface[]
	 * @throws Exception
	 */
	public DataContainerInterface[] getStatisticsInfo (DataContainerInterface[] dc, INodeInfo itemProp) throws Exception{
		List results = null;
		if (dc != null){
			results = new ArrayList();
			List list = new ArrayList();         //用于统计最大，最小耗时
			DataContainerInterface dataCon = null;
			int totalCounts = 0;                 //调用次数
			int successCounts = 0;               //成功次数
			int failureCounts = 0;               //失败次数
			int timeConsuming = 0;               //EndTime - StartTime
			
			String ruleKey = ((RuleItemProp)itemProp).getKey().toString();
			int ruleValue = Integer.parseInt(((RuleItemProp)itemProp).getValue().toString());
			String sql = dc[0].getAsString("Statement");  //sql
			long initDate = dc[0].getAsLong(ruleKey);
			long logDate = 0;
			int diffTime = 0;
			
			for (int i = 0; i < dc.length; i++) {
				logDate = dc[i].getAsLong(ruleKey);
				diffTime = (int) (logDate - initDate) / 1000;
				if (diffTime > ruleValue) {
					dataCon = new DataContainer();
					String[] strs = new String[list.size()] ;
					list.toArray(strs);
					Arrays.sort(strs);
					Date date = new Date(initDate);
					java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					dataCon.set("Rule_Time", formatter.format(date));
					dataCon.set("SQL", sql);
					dataCon.set("Use_Count", new Integer(totalCounts));
					dataCon.set("Max_Use_Time", strs[strs.length - 1]);
					dataCon.set("Max_Use_Time", strs[0]);
					dataCon.set("Succ_Count", new Integer(successCounts));
					dataCon.set("Fail_Count", new Integer(failureCounts));
					results.add(dataCon);

					initDate = logDate;
					totalCounts = 0;
					successCounts = 0;
					failureCounts = 0;
					list = new ArrayList();
				}
			    if (dc[i].getAsInt("ExecFlag") == 0) {
					failureCounts++;
				} else if (dc[i].getAsInt("ExecFlag") == 1) {
					successCounts++;
				}
				totalCounts++;
				timeConsuming = (int) (dc[i].getAsLong("EndTime") - dc[i].getAsLong("StartTime")) / 1000;
				list.add("" + timeConsuming);
			}
		}
		
		/*
		DataContainer ddd = new DataContainer();
		for (int i = 0; i < results.size(); i++){
			ddd = (DataContainer)results.get(i);
			System.out.println("............................");
			System.out.println("时间="+ddd.getAsString("时间"));
			System.out.println("sql语句="+ddd.getAsString("sql语句"));
			System.out.println("调用次数="+ddd.getAsString("调用次数"));
			System.out.println("最大耗时="+ddd.getAsString("最大耗时"));
			System.out.println("最小耗时="+ddd.getAsString("最小耗时"));
			System.out.println("成功次数="+ddd.getAsString("成功次数"));
			System.out.println("失败次数="+ddd.getAsString("失败次数"));
		}*/
		return (DataContainerInterface[])results.toArray(new DataContainerInterface[0]);
	}
	
	public static void main(String args[]){
		SQLStatisticsApi ss = new SQLStatisticsApi();
		File file = new File("D:/forLog/sql/ai_sql.log");
		DataContainerInterface[] dc = new DataContainerInterface[6];
		try{
			INodeInfo rl = new RuleItemProp("Statement","select M.USERID,M.FONTCOLOR,M.THEMEID,M.BACKGROUNDCOLOR,M.WALLPAPERID,M.WALLPAPERPOSITION,M.TRANSPARENCY,M.ID ,M.ROWID as MROWID___  from QO_STYLES M where M.USERID = ?");
			dc = ss.getLogInfoByRule(ss.readLogFile(file),rl);
			INodeInfo rl2 = new RuleItemProp("LogDate","300");
			ss.getStatisticsInfo(dc, rl2);
		}catch (Exception e){
			e.printStackTrace();
		} 
	}
	
}