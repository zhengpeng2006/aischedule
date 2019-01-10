/**
 * 
 */
package com.asiainfo.batchwriteoff.util;

import com.ai.appframe2.complex.cache.CacheFactory;
import com.ai.appframe2.complex.center.CenterFactory;
import com.ai.appframe2.service.ServiceFactory;
import com.ai.common.cache.BsParaDetailCacheImpl;
import com.ai.common.i18n.CrmLocaleFactory;
import com.ai.common.ivalues.IBOBsParaDetailValue;
import com.ai.common.util.CenterUtil;
import com.ai.common.util.ExceptionUtil;
import com.asiainfo.batchwriteoff.common.BatchWriteOffConsts;
import com.asiainfo.batchwriteoff.ivalues.IQBOBsBatchWfFlowRelValue;
import com.asiainfo.batchwriteoff.service.interfaces.IBWFCommonBillingSV;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.*;
//import com.asiainfo.crm.ams.exe.task.AmBalanceStatTask;

/**   
 * @Copyright Copyright (c) 2012 Asiainfo-Linkage
 * 
 * @ClassName com.asiainfo.crm.ams.balance.batchwriteoff.util.GraphicsUtil
 * @Description 图形化生成工具
 *
 * @version v1.0.0
 * @author yandong2
 * @date 2012-9-21 上午09:21:55
 * <br>
 * Modification History:<br>
 * Date         Author          Version            Description<br>
 * ---------------------------------------------------------*<br>
 * 2012-9-21     yandong2           v1.0.0               淇敼鍘熷洜<br>
 */
public class GraphicsUtil {
	
	  private final static transient Log log= LogFactory.getLog(GraphicsUtil.class);
	
	  private final  static  int defaultLeft=20;
	  
	  private final  static  int defaultTop=180;
	  
	  private final  static  int defaultWidth=110;
	  
	  private final  static  int defaultHeight=50;
	
	  
	  /**
	   * 创建流程
	   * @param graphics 所有结点集合
	   * @param totalStep 总步骤
	   * @return
	   * @throws Exception
	   */
	  public static String  createProcess(ArrayList<Graphics> graphics,int totalStep)throws Exception{
		  if (graphics==null||graphics.size()<=0) {
			throw new Exception("param init fail...");
		  }
		  Document document= DocumentHelper.createDocument();
		  Element process=document.addElement("process");
		  process.addAttribute("name", CrmLocaleFactory.getResource("AMS2300331"));//批销
		  //开始结点x
		  Element start=process.addElement("start");
		  start.addAttribute("name", "START");
		  start.addAttribute("value", getCfgInfo(BatchWriteOffConsts.Common.PARA_CODE_START));
		  start.addAttribute("left", String.valueOf(defaultLeft));
		  start.addAttribute("top", String.valueOf(defaultTop-80));
		  start.addAttribute("width", String.valueOf(defaultWidth));
		  start.addAttribute("height", String.valueOf(defaultHeight));
		  start.addAttribute("isDtl", BatchWriteOffConsts.Common.FLOW_NODE_IS_DTL_N);
		  Element transition=start.addElement("transition");
		  transition.addAttribute("to", graphics.get(0).getValue());
		  transition.addAttribute("left", String.valueOf(defaultLeft));
		  transition.addAttribute("top", String.valueOf(defaultTop));
		  //先取出end节点的value 以后要用 add by 李靖 20150203
		  String endValue = getCfgInfo(BatchWriteOffConsts.Common.PARA_CODE_END);
		  deal(document,process,graphics,totalStep,endValue);
		  //结束结点
		  Element end=process.addElement("end");
		  end.addAttribute("name", "END");
		  end.addAttribute("value", endValue);
		  end.addAttribute("top", String.valueOf(Integer.parseInt(getElement(document, "task","transition", endValue).attributeValue("top"))+40));
		  end.addAttribute("left", String.valueOf(Integer.parseInt(getElement(document, "task","transition", endValue).attributeValue("left"))));
		  end.addAttribute("width", String.valueOf(defaultWidth));
		  end.addAttribute("height", String.valueOf(defaultHeight));
		  end.addAttribute("isDtl", BatchWriteOffConsts.Common.FLOW_NODE_IS_DTL_N);
		  return document.asXML();
	  }
	  
	/**  
	 * 生成任务结点XML  
	 * @Function deal
	 * @Description 
	 *
	 * @param document
	 * @param process
	 * @param graphics
	 * @param totalStep
	 * @param endValue
	 * @version v1.0.0
	 * @author yandong2
	 * @date 2012-9-24 下午02:52:56
	 * 
	 * <strong>Modification History:</strong><br>
	 * Date         Author          Version            Description<br>
	 * ---------------------------------------------------------<br>
	 * 2012-9-24     yandong2           v1.0.0               修改原因<br>
	 * 2015-2-3   lijing           v1.0.1                添加结束节点值
	 */
	private static void deal(Document document, Element process,ArrayList<Graphics> graphics,int totalStep,String endValue)throws Exception {
		 if (graphics==null||graphics.size()<=0) {
				throw new Exception("param init fail...");
		 }
		 boolean flag=false;
		 HashMap<Integer,Graphics> existGraphics=new HashMap<Integer, Graphics>();
		 int avgStep=Integer.parseInt(getCfgInfo(BatchWriteOffConsts.Common.PARA_CODE_LENGTH));
		 if(log.isDebugEnabled()){
			 log.debug("every rows node counts ==========="+avgStep);
		 }
		 int row=0;
		 for (int i = 0,j=graphics.size(); i < j; i++) {
			Graphics temp=graphics.get(i);
			int pTaskId=Integer.parseInt(temp.getValue());
			int levelId=temp.getLevelId();
			int reverseLevelId=totalStep-levelId;
			if(log.isDebugEnabled()){
				 log.debug(temp.getName()+"========================="+levelId);
			}
			if(i==0){
				existGraphics.put(pTaskId, temp);
			}
			if (existGraphics.containsKey(pTaskId)) {
				  Element childtask=process.addElement("task");
				  childtask.addAttribute("name", temp.getName());
				  childtask.addAttribute("value", temp.getValue());
				  childtask.addAttribute("isDtl", temp.getIsDtl());
				  if(StringUtils.equals(BatchWriteOffConsts.Common.FLOW_NODE_IS_DTL_Y, temp.getIsDtl())){
						  childtask.addAttribute("dtlUrl", temp.getUrl());
				  }
				  if(i ==0){
					  childtask.addAttribute("left", String.valueOf(defaultLeft));
					  childtask.addAttribute("top", String.valueOf(defaultTop)); 
				  }else{
					  if (reverseLevelId>avgStep&&reverseLevelId%avgStep!=0) {//从第一行之后开始算起
						  if((reverseLevelId-1)%avgStep==0){
							  childtask.addAttribute("left",String.valueOf(Integer.parseInt(getElement(document, "task","transition", temp.getValue()).attributeValue("left"))));
						  }else{
							  if(row%2!=0){//奇数行
								  childtask.addAttribute("left",String.valueOf(Integer.parseInt(getElement(document, "task","transition", temp.getValue()).attributeValue("left"))-75));
							  }else if(row%2==0){//偶数行
								  childtask.addAttribute("left",String.valueOf(Integer.parseInt(getElement(document, "task","transition", temp.getValue()).attributeValue("left"))+75));
							  }
						  }
					  } else if (reverseLevelId>avgStep&&reverseLevelId%avgStep==0) {//从第一行之后开始算起
						  if(row%2!=0){//奇数行
							  childtask.addAttribute("left",String.valueOf(Integer.parseInt(getElement(document, "task","transition", temp.getValue()).attributeValue("left"))-75));
						  }else{//偶数行
							  childtask.addAttribute("left",String.valueOf(Integer.parseInt(getElement(document, "task","transition", temp.getValue()).attributeValue("left"))+75));
						  }
					  }else{
						  childtask.addAttribute("left", String.valueOf(Integer.parseInt(getElement(document, "task","transition", temp.getValue()).attributeValue("left"))+75));
					  }
					  childtask.addAttribute("top", String.valueOf(Integer.parseInt(getElement(document, "task","transition", temp.getValue()).attributeValue("top")))); 
				  }
				  childtask.addAttribute("width", String.valueOf(defaultWidth));
				  childtask.addAttribute("height", String.valueOf(defaultHeight));
				  if(temp.getChildren()!=null&&temp.getChildren().size()>0){
					  for (int k = 0; k < temp.getChildren().size()&&k<=3; k++) {
						     Graphics cGraphics=temp.getChildren().get(k);
						     int cTaskId=Integer.parseInt(cGraphics.getValue());
						     if (reverseLevelId%avgStep==0) {//换行的地方，建议不要在此有并行分支。
						    	 Element transition=childtask.addElement("transition");
						    	 transition.addAttribute("left", String.valueOf(Integer.parseInt(childtask.attributeValue("left"))));
						    	 transition.addAttribute("top", String.valueOf(defaultTop+180*(row+1)));
						    	 transition.addAttribute("to", cGraphics.getValue());
						    	 row++;
							}else  if (reverseLevelId>avgStep){
								Element transition=childtask.addElement("transition");
								 if(row%2!=0){//奇数行
									 transition.addAttribute("left", String.valueOf(Integer.parseInt(childtask.attributeValue("left"))-125));
								 }else{//偶数行
									 transition.addAttribute("left", String.valueOf(Integer.parseInt(childtask.attributeValue("left"))+125));
								 }
								 if(temp.getChildren().size()==1){
								    	transition.addAttribute("top", String.valueOf(Integer.parseInt(childtask.attributeValue("top"))+60*k));
								 }else if(temp.getChildren().size()==2){
								    	transition.addAttribute("top", String.valueOf(Integer.parseInt(childtask.attributeValue("top"))+60*(k==0?k-1:k)));
								 }else{
								    	transition.addAttribute("top", String.valueOf(Integer.parseInt(childtask.attributeValue("top"))+60*(k-1)));
								 }
							    transition.addAttribute("to", cGraphics.getValue());
							    flag=true;
							}else{//第一行的处理
							  	Element transition=childtask.addElement("transition");
							    transition.addAttribute("left", String.valueOf(Integer.parseInt(childtask.attributeValue("left"))+125));
							    if(temp.getChildren().size()==1){
							    	transition.addAttribute("top", String.valueOf(defaultTop+60*k));
							    }else if(temp.getChildren().size()==2){
							    	transition.addAttribute("top", String.valueOf(defaultTop+60*(k==0?k-1:k)));
							    }else{
							    	transition.addAttribute("top", String.valueOf(defaultTop+60*(k-1)));
							    }
							    transition.addAttribute("to", cGraphics.getValue());
							}
						    existGraphics.put(cTaskId, cGraphics);
					  }
				  }else{
					  Element transition=childtask.addElement("transition");
					  if (flag) {
						  transition.addAttribute("top", String.valueOf(Integer.parseInt(childtask.attributeValue("top"))+50));
						  transition.addAttribute("left", String.valueOf(Integer.parseInt(childtask.attributeValue("left"))));
					  }else{
						  transition.addAttribute("top", String.valueOf(defaultTop));
						  transition.addAttribute("left", String.valueOf(Integer.parseInt(childtask.attributeValue("left"))+125));
					  }
					  transition.addAttribute("to", endValue);
				  }
			}
		 }
	}
    /**
     * 计算深度
     * @Function calcSteps
     * @Description 
     *
     * @param list
     * @param root
     * @return
     * @throws Exception
     *
     * @version v1.0.0
     * @author yandong2
     * @date 2012-9-25 上午11:02:23
     * 
     * <strong>Modification History:</strong><br>
     * Date         Author          Version            Description<br>
     * ---------------------------------------------------------<br>
     * 2012-9-25     yandong2           v1.0.0               修改原因<br>
     */
    public static int calcSteps(ArrayList<Graphics> list,Graphics root)throws Exception{
	  int steps=1;
	  int temp=0;
	  if(root.getChildren()!=null&&root.getChildren().size()>0){
		  for (int i = 0; i < root.getChildren().size(); i++) {
			  int t=calcSteps(root.getChildren().get(i).getChildren(),root.getChildren().get(i));
			  if (t>temp) {
				  temp=t;
			  }
			}
	  }
	  steps+=temp;
	  return steps;
    }
	 
      /**
       * 获取XML中元素
       * @Function getElement
       * @Description 
       *
       * @param document
       * @param parentProperty
       * @param property
       * @param name
       * @return
       * @throws Exception
       *
       * @version v1.0.0
       * @author yandong2
       * @date 2012-9-25 上午11:02:34
       * 
       * <strong>Modification History:</strong><br>
       * Date         Author          Version            Description<br>
       * ---------------------------------------------------------<br>
       * 2012-9-25     yandong2           v1.0.0               修改原因<br>
       */
	 public static Element getElement(Document document,String parentProperty,String property,String name)throws Exception{
		   Iterator<Element> nodes=document.nodeIterator();
		   List<Element> list=new ArrayList<Element>();
		   if(nodes!=null){
			   Element processElement=nodes.next();
			    for (Iterator iterator = processElement.nodeIterator(); iterator
						.hasNext();) {
			    	Element temp = (Element) iterator.next();
					if (temp.getName().equals(parentProperty)) {
						 for (Iterator t = temp.nodeIterator(); t.hasNext();) {
							 Element temp2 = (Element) t.next();
							 if(temp2.getName().equals(property)&&temp2.attribute("to").getText().equals(name)){
								 list.add(temp2);
							 }
						 }
						
					}
				}
		   }
		   if (list.size()==0) {
			  throw new Exception("coding  mistakes...");
		   }
		   if (list.size()==1) {
			   return list.get(0);
		   }
		   Element element= DocumentHelper.createElement(property);
		   int top=0;
		   int left=0;
		   for(int i=0;i<list.size();i++){
			   top+=Integer.parseInt(list.get(i).attributeValue("top"));
			   left+=Integer.parseInt(list.get(i).attributeValue("left"));
		   }
		   element.addAttribute("top",String.valueOf(top/list.size()));
		   element.addAttribute("left",String.valueOf(left/list.size()));
		   return element;
	  }
	  
	  

	/**   
	 * @Function main
	 * @Description 
	 *
	 * @param args
	 *
	 * @version v1.0.0 
	 * @author yandong2
	 * @date 2012-9-21 上午09:21:55
	 * 
	 * <strong>Modification History:</strong><br>
	 * Date         Author          Version            Description<br>
	 * ---------------------------------------------------------<br>
	 * 2012-9-21     yandong2           v1.0.0               修改原因<br>
	 */
	public static void main(String[] args)throws Exception {
		CenterFactory.setCenterInfoByTypeAndValue(CenterUtil.REGION_ID, "240");
		ArrayList<Graphics> list=getAllNodes("240", 1l);
		int totalStep=calcSteps(list, getRootNode(list))+1;
		ArrayList<Graphics> result=sort(list,totalStep);
		for (int i = 0; i < result.size(); i++) {
			System.out.println(result.get(i).getValue()+result.get(i).getName());
		}
		System.out.println(createProcess(result,totalStep));
	}
	
	/**
	 * 根据对象之间的关系模拟二叉树关系,层级遍历
	 * @Function sort
	 * @Description 
	 *
	 * @param list
	 * @param totalStep
	 * @return
	 * @throws Exception
	 *
	 * @version v1.0.0
	 * @author yandong2
	 * @date 2012-9-24 下午04:35:17
	 * 
	 * <strong>Modification History:</strong><br>
	 * Date         Author          Version            Description<br>
	 * ---------------------------------------------------------<br>
	 * 2012-9-24     yandong2           v1.0.0               修改原因<br>
	 */
	public static ArrayList<Graphics> sort(ArrayList<Graphics> list,int totalStep)throws Exception{
		if (list==null||list.size()<=0) {
				throw new Exception("param init fail...");
		}
		
		HashMap<Integer,ArrayList<Graphics>> tempMap=new HashMap<Integer, ArrayList<Graphics>>();
		for (int i = 0,j=list.size(); i < j; i++) {
			Graphics temp=list.get(i);
			int levelId=calcSteps(list, temp);
			temp.setLevelId(levelId);
			if(isUsed(list, temp)){
				if (tempMap.containsKey(Integer.valueOf(levelId))) {
					ArrayList<Graphics> tempArrayList=tempMap.get(Integer.valueOf(levelId));
					tempArrayList.add(temp);
				}else{
					ArrayList<Graphics> tempArrayList=new ArrayList<Graphics>();
					tempArrayList.add(temp);
					tempMap.put(Integer.valueOf(levelId), tempArrayList);
				}
			}
		}
		if(log.isDebugEnabled()){
			for (Iterator iterator = tempMap.entrySet().iterator(); iterator.hasNext();) {
				Map.Entry<Integer, ArrayList<Graphics>> o = (Map.Entry<Integer, ArrayList<Graphics>>) iterator.next();
				log.debug(o.getKey()+":"+o.getValue().size());
			}
		}
		ArrayList<Graphics> result=new ArrayList<Graphics>();
		for (int i = totalStep; i >=0; i--) {
			if (tempMap.containsKey(Integer.valueOf(i))) {
				ArrayList<Graphics> tempArrayList=tempMap.get(Integer.valueOf(i));
				result.addAll(tempArrayList);
			}
		}
		if(log.isDebugEnabled()){
			for (int i = 0; i < result.size(); i++) {
				log.debug(result.get(i).getValue()+":"+result.get(i).getName()+":"+result.get(i).getLevelId());
			}
		}
		return result;
	}
	
	/**
	 * 校验当前结点是否被其他结点所用
	 * @Function isUsed
	 * @Description 
	 *
	 * @param list
	 * @param speficGraphics
	 * @return
	 * @throws Exception
	 *
	 * @version v1.0.0
	 * @author yandong2
	 * @date 2012-9-25 上午10:55:52
	 * 
	 * <strong>Modification History:</strong><br>
	 * Date         Author          Version            Description<br>
	 * ---------------------------------------------------------<br>
	 * 2012-9-25     yandong2           v1.0.0               修改原因<br>
	 */
	public static boolean isUsed(ArrayList<Graphics> list,Graphics speficGraphics)throws Exception{
		if(list!=null&&list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				Graphics temp=list.get(i);
				if(temp.getParents()!=null&&temp.getParents().size()>0){
					for (int j = 0; j < temp.getParents().size(); j++) {
						Graphics tmpGraphics=temp.getParents().get(j);
						if (tmpGraphics!=null&&tmpGraphics.getValue().equals(speficGraphics.getValue())) {
							return true;
						}
					}
					
				}
				if(temp.getChildren()!=null&&temp.getChildren().size()>0){
					for (int j = 0; j < temp.getChildren().size(); j++) {
						Graphics tmpGraphics=temp.getChildren().get(j);
						if (tmpGraphics!=null&&tmpGraphics.getValue().equals(speficGraphics.getValue())) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * 获取根节点
	 * @Function getRootNode
	 * @Description 
	 *
	 * @param list
	 * @return
	 * @throws Exception
	 *
	 * @version v1.0.0
	 * @author yandong2
	 * @date 2012-10-20 下午02:52:39
	 * 
	 * <strong>Modification History:</strong><br>
	 * Date         Author          Version            Description<br>
	 * ---------------------------------------------------------<br>
	 * 2012-10-20     yandong2           v1.0.0               修改原因<br>
	 */
	private static Graphics getRootNode(ArrayList<Graphics> list)throws Exception{
		if(list!=null&&list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				Graphics temp=list.get(i);
				if((temp.getParents()==null||temp.getParents().size()<=0)&&temp.getChildren()!=null&&temp.getChildren().size()>0){
					return temp;
				}
			}
		}
		return null;
	}
	
	
	/**
	 * 获取所有批销流程结点
	 * @Function getAllNodes
	 * @Description 
	 *
	 * @param regionId
	 * @param billDay
	 * @return
	 * @throws Exception
	 *
	 * @version v1.0.0
	 * @author yandong2
	 * @date 2012-10-20 上午10:45:32
	 * 
	 * <strong>Modification History:</strong><br>
	 * Date         Author          Version            Description<br>
	 * ---------------------------------------------------------<br>
	 * 2012-10-20     yandong2           v1.0.0               修改原因<br>
	 */
	public static ArrayList<Graphics> getAllNodes(String regionId,long billDay)throws Exception{
		ArrayList<Graphics> result=new ArrayList<Graphics>();
		int start=Integer.parseInt(getCfgInfo(BatchWriteOffConsts.Common.PARA_CODE_START));
		int end=Integer.parseInt(getCfgInfo(BatchWriteOffConsts.Common.PARA_CODE_END));
		IBWFCommonBillingSV commonSV=(IBWFCommonBillingSV) ServiceFactory.getService(IBWFCommonBillingSV.class);
		IQBOBsBatchWfFlowRelValue[] allFlows=commonSV.getBatchWfFlowRels(regionId, billDay);
		if (allFlows!=null&&allFlows.length>0) {
			Map<String, Graphics> retMap=new HashMap<String, Graphics>();
			Map<String, ArrayList<String>> parentflowIds=new HashMap<String, ArrayList<String>>();
			Map<String, ArrayList<String>> childflowIds=new HashMap<String, ArrayList<String>>();
            for (IQBOBsBatchWfFlowRelValue allFlow : allFlows) {
                //去掉开始、结束结点
                if (allFlow.getWfFlowId() == start || allFlow.getWfFlowId() == end) {
                    continue;
                }
                String flowId = String.valueOf(allFlow.getWfFlowId());
                if (!retMap.containsKey(flowId)) {
                    Graphics graphics = new Graphics();
                    graphics.setValue(flowId);
                    graphics.setName(allFlow.getWfFlowName());
                    if (StringUtils.equals(BatchWriteOffConsts.Common.FLOW_NODE_IS_DTL_Y, allFlow.getIsDtl())) {
                        graphics.setIsDtl(BatchWriteOffConsts.Common.FLOW_NODE_IS_DTL_Y);
                        graphics.setUrl(allFlow.getDtlUrl());
                    }
                    retMap.put(flowId, graphics);
                }
                //遍历出结点的父节点
                String preFlowId = String.valueOf(allFlow.getPreWfFlowId());
                if (parentflowIds.containsKey(flowId)) {
                    ArrayList<String> parentNodes = parentflowIds.get(flowId);
                    parentNodes.add(preFlowId);
                } else {
                    if (allFlow.getPreWfFlowId() != start) {
                        ArrayList<String> parentNodes = new ArrayList<String>();
                        parentNodes.add(preFlowId);
                        parentflowIds.put(flowId, parentNodes);
                    }
                }
                if (allFlow.getPreWfFlowId() != start) {
                    //遍历出 结点的子节点
                    if (childflowIds.containsKey(preFlowId)) {
                        ArrayList<String> childNodes = childflowIds.get(preFlowId);
                        childNodes.add(flowId);
                    } else {
                        if (allFlow.getWfFlowId() != end) {
                            ArrayList<String> childNodes = new ArrayList<String>();
                            childNodes.add(flowId);
                            childflowIds.put(preFlowId, childNodes);
                        }
                    }
                }

            }
            for (Map.Entry<String, ArrayList<String>> entry : parentflowIds.entrySet()) {
                String key = entry.getKey();
                Graphics o = retMap.get(key);
                ArrayList<String> preList = entry.getValue();
                if (preList != null && preList.size() > 0) {
                    for (String aPreList : preList) {
                        Graphics temp = retMap.get(aPreList);
                        o.addParent(temp);
                    }
                }
            }

            for (Map.Entry<String, ArrayList<String>> entry : childflowIds.entrySet()) {
                String key = entry.getKey();
                Graphics o = retMap.get(key);
                ArrayList<String> childList = entry.getValue();
                if (childList != null && childList.size() > 0) {
                    for (String aChildList : childList) {
                        Graphics temp = retMap.get(aChildList);
                        o.addChild(temp);
                    }
                }
            }
            for (Map.Entry<String, Graphics> entry : retMap.entrySet()) {
                result.add(entry.getValue());
            }
		}
		return result;
	}
	
	/**
	 * 创建批销流程，动态生成XML报文
	 * @Function createBatchWriteOffFlow
	 * @Description 
	 *
	 * @param regionId
	 * @param billDay
	 * @param runMode
	 * @return
	 * @throws Exception
	 *
	 * @version v1.0.0
	 * @author yandong2
	 * @date 2012-10-20 下午03:04:23
	 * 
	 * <strong>Modification History:</strong><br>
	 * Date         Author          Version            Description<br>
	 * ---------------------------------------------------------<br>
	 * 2012-10-20     yandong2           v1.0.0               修改原因<br>
	 */
	public static String createBatchWriteOffFlow(String regionId,long billDay,String runMode)throws Exception{
		ArrayList<Graphics> list=getAllNodes(regionId, billDay);
		if (list==null||list.size()<=0) {
			return "";
		}
		int totalStep=calcSteps(list, getRootNode(list))+1;
		ArrayList<Graphics> result=sort(list,totalStep);
		return createProcess(result,totalStep).replaceAll("\"", "'").replaceAll("\\n", "").replaceAll("\\t", "").trim();
	}
	
	
	/**
	 * 获取配置结点信息
	 * @param paraCode
	 * @return
	 * @throws Exception
	 */
	public static String getCfgInfo(String paraCode)throws Exception{
		String nodeVal="";
		String cacheKey = BatchWriteOffConsts.Common.ALL_PROVINCE_CODE+"_"+paraCode
						+"_"+ BatchWriteOffConsts.Common.PARA_TYPE_BATCH_WIRTE_OFF_CFG;
		IBOBsParaDetailValue detailValue=(IBOBsParaDetailValue) CacheFactory.get(BsParaDetailCacheImpl.class, cacheKey);
		if(detailValue==null){
			ExceptionUtil.throwBusinessException("AMS2300330");//批销结点配置不全，请检查！
		}
		nodeVal=detailValue.getPara1();
		return nodeVal;
	}
	
//	/**
//	 * 根据地市插入月账单沉淀任务
//	 * @param regionId 地市
//	 * @return
//	 * @throws Exception
//	 */
//	public static long insertBillGatherTask(String regionId)throws Exception{
//		//需要上发帐处
//	    Timestamp curr_date = AmsUtil.getDBSysdate();
//		ITaskSV taskSv = (ITaskSV)ServiceFactory.getService(ITaskSV.class) ;
//		IBOCfgTaskValue task = new BOCfgTaskBean() ;
//		task.setTaskName("月末账单沉淀_"+regionId) ;
//		task.setStaffId(0) ;
//		task.setCfgTaskTypeCode("AMS_TASK_"+regionId) ;
//		task.setBusinessClass(AmBalanceStatTask.class.getName()) ;
//		task.setCreateDate(curr_date) ;
//		task.setStateDate(curr_date) ;
//		task.setState("U") ;
//		//立即执行
//		task.setTaskMethod(TaskFrameWork.TASK_METHOD_IMMEDIATE) ;
//		IBOCfgTaskParamValueValue[] params = new IBOCfgTaskParamValueValue[1] ;
//		params[0] = new BOCfgTaskParamValueBean() ;
//		params[0].setParamId(1001);
//		params[0].setParamValue(regionId);
//		return taskSv.insertTask(task, params) ;
//	}
}
