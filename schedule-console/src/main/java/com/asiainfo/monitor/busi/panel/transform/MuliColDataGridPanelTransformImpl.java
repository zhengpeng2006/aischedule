package com.asiainfo.monitor.busi.panel.transform;


import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.monitor.tools.common.interfaces.ITaskRtn;
import com.asiainfo.monitor.tools.exe.task.model.TaskRtnModel;
import com.asiainfo.monitor.tools.util.TypeConst;

/**
 * 画出多列的类似于Grid的结构,适用于一个面板任务配置多个命令(命令参数用|分隔，比如573|576，假如表名后缀为am_payment_573_201108)
 * 第1个Row是抬头
 * <grid>
 * <row><col0>地区</col0><col1>业务变更</col1><col2>订购增值策划</col2><col3>普通GSM开户</col3><col4>时间</col4></row>
 * <row><col0>杭州</col0><col1>210</col1><col2>26</col2><col3>65</col3><col4>2011-04-19</col4></row>
 * <row><col0>湖州</col0><col1>189</col1><col2>21</col2><col3>43</col3><col4>2011-04-19</col4></row>
 * <row><col0>绍兴</col0><col1>225</col1><col2>31</col2><col3>72</col3><col4>2011-04-19</col4></row>
 * <row><col0>温州</col0><col1>177</col1><col2>16</col2><col3>21</col3><col4>2011-04-19</col4></row>
 * </grid>
 * 
 * 代表作：营业业务量
 * @author guocx
 *
 */
public class MuliColDataGridPanelTransformImpl extends DefaultPanelTransform {

	private static transient Log log = LogFactory.getLog(MuliColDataGridPanelTransformImpl.class);
	
	@Override
	public String transform(TaskRtnModel obj) throws Exception {
		if (obj == null)
			return null;
		List rtnList=obj.getRtns();
		StringBuilder sb=new StringBuilder("");
		if (rtnList!=null && rtnList.size()>0){
			ITaskRtn rtn;
			List collist=new LinkedList();
			//列出所有的列title
			for (int i=0;i<rtnList.size();i++) {
				rtn=(ITaskRtn)rtnList.get(i);
				if (rtn!=null){
					String msg=rtn.getMsg();
					String[] colStr = StringUtils.split(msg,TypeConst._SPLIT_CHAR);
					for (int j=0;j<colStr.length;j++) {
						String itemCol=getNameFromInfo(colStr[j]);
						if (!collist.contains(itemCol))
							collist.add(itemCol);
					}
				}
			}
			//画出表格所有列的title xml
			sb.append("<row>");
			for (int i=0;i<collist.size();i++){
				sb.append("<col"+i+">").append(collist.get(i)).append("</col"+i+">");				
			}
			sb.append("</row>");
			
			//画出每行数据内容 xml
			for (int i=0;i<rtnList.size();i++) {
				rtn=(ITaskRtn)rtnList.get(i);
				if (rtn!=null){
					String msg=rtn.getMsg();
					if (StringUtils.isNotBlank(getValueFromInfo(msg,1))){
						String[] colStr = StringUtils.split(msg,TypeConst._SPLIT_CHAR);
						sb.append("<row>");
						for (int j=0;j<colStr.length;j++) {
							if (StringUtils.isNotBlank(colStr[j])) {
								String colname=getNameFromInfo(colStr[j]);
								int count=collist.indexOf(colname);
								//如果当前此列的名称和列表中该列名的位置一致，则画出该列
								if (count>=0){
									String curVal=getValueFromInfo(colStr[j]);
									if (StringUtils.isBlank(curVal)){
										sb.append("<col"+count+">").append("0").append("</col"+count+">");
									}else{
										sb.append("<col"+count+">").append(curVal).append("</col"+count+">");
									}
								}
									
							}
						}
						if (colStr.length<collist.size()){
							int location=colStr.length-1;
							if (!StringUtils.isBlank(colStr[location])){
								location++;								
							}
							for (;location<collist.size();location++){
								sb.append("<col"+location+">").append("0").append("</col"+location+">");
							}
						}
						sb.append("</row>");
					}
				}
			}
		}
		if (log.isDebugEnabled()){
			log.debug("transformed:"+sb.toString());
		}
		return sb.toString();
	}

	public static void main(String[] args){
	}
}
