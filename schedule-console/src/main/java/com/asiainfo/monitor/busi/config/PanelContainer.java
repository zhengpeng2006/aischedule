package com.asiainfo.monitor.busi.config;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.asiainfo.monitor.tools.common.TaskWorker;

public class PanelContainer {

	
	private List shapes=new LinkedList();
	
	//执行方式,asyn:true异步执行
	private boolean asyn=false;
	
	public List getShapes() {
		return shapes;
	}

	public void setShapes(List shapes) {
		this.shapes = shapes;
	}

	public void addShape(PanelShape shape){
		this.shapes.add(shape);
	}
	
	public boolean isAsyn() {
		return asyn;
	}

	public void setAsyn(boolean asyn) {
		this.asyn = asyn;
	}
	
	/**
	 * 执行面板任务
	 * @return
	 * @throws Exception
	 */
	public List work() throws Exception{
		List result=null;
		if (shapes.size()>0){
			ThreadPoolExecutor executor=null;
			CountDownLatch signal=null;
			try{
				if (isAsyn()){					
					signal=new CountDownLatch(shapes.size());
					int cpuCount=Runtime.getRuntime().availableProcessors();
					int maxCount=cpuCount*5;
					//如果面板个数小于cpu的个数的3倍，则统一将默认线程数设置为cpu个数,否则设置为cpu个数
					int coreCount=shapes.size()<(cpuCount*3)?cpuCount:cpuCount*3;
					if (coreCount>shapes.size())
						coreCount=shapes.size();
					executor=new ThreadPoolExecutor(coreCount, maxCount, 5, TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>(cpuCount*10),new ThreadPoolExecutor.CallerRunsPolicy());
				}
				
				result=new ArrayList(shapes.size());
				for (int i=0;i<shapes.size();i++){
					PanelShape itemShape=(PanelShape)shapes.get(i);
					if (itemShape!=null){
						if (isAsyn()){
							itemShape.setSignal(signal);
							executor.execute(new TaskWorker(itemShape));							
						}else{
							itemShape.action();
							result.add(itemShape.getShapeXml());
							itemShape.clear();
						}						
					}
				}
				if (isAsyn()){
					signal.await();
			        for (int i=0;i<shapes.size();i++){
						PanelShape itemShape=(PanelShape)shapes.get(i);
						if (itemShape!=null){
							result.add(itemShape.getShapeXml());
							itemShape.clear();
						}
					}
				}
			}catch(Exception e){
				throw new Exception(e);
			}finally{
				if (signal!=null)
					signal=null;
				if (executor!=null){
					executor.shutdownNow();
					executor=null;
				}
			}
		}
		return result;
	}
	
}
