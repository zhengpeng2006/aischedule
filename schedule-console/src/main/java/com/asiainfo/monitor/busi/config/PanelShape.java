package com.asiainfo.monitor.busi.config;



import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.monitor.busi.cache.interfaces.IPanel;
import com.asiainfo.monitor.busi.model.event.PanelTaskEvent;
import com.asiainfo.monitor.busi.panel.view.DefaultViewWrapperStrategy;
import com.asiainfo.monitor.busi.panel.view.IViewWrapperStrategy;
import com.asiainfo.monitor.tools.common.KeyName;
import com.asiainfo.monitor.tools.common.interfaces.IWorker;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.model.interfaces.IWebPanelTaskContext;
import com.asiainfo.monitor.tools.pool.impl.SimpleObjectPoolImpl;
import com.asiainfo.monitor.tools.util.GlobEnvContext;

public abstract class PanelShape implements IWorker, com.asiainfo.monitor.tools.model.interfaces.IClear, Cloneable, Serializable
{
	
	private static transient Log log=LogFactory.getLog(PanelShape.class);
	//面板值对象
	private IPanel panel=null;

	//面板运行期参数
	private List parameter=null;
	
	private Lock l=new ReentrantLock();
	
	protected CountDownLatch signal=null;
	
	protected String shapeXml;
	
	public PanelShape(IPanel panel){
		this.panel=panel;
	}
	
	public IPanel getPanel() {
		return panel;
	}

	public void setPanel(IPanel panel) {
		this.panel = panel;
	}

	public List getParameter() {
		return parameter;
	}
	
	public void addParameter(KeyName item) throws Exception{
		if (parameter==null){
			if (l.tryLock()){
				try{
					if (parameter==null)
						parameter=new ArrayList();
				}finally{
					l.unlock();
				}
			}else{
				if (log.isDebugEnabled()){
					// 当前有别的线程正在初始化参数
					log.debug(AIMonLocaleFactory.getResource("MOS0000215"));
				}
			}			
		}
		if (parameter!=null)
			parameter.add(item);
	}

	public abstract String getViewType();
	
	public boolean enableConfig(){
		return panel!=null?true:false;
	}
	
	public CountDownLatch getSignal() {
		return signal;
	}

	public void setSignal(CountDownLatch signal) {
		this.signal = signal;
	}

	public void clear(){
		this.shapeXml=null;
	}
	
	public String getShapeXml() {
		return shapeXml;
	}

	public void setShapeXml(String shapeXml) {
		this.shapeXml = shapeXml;
	}
	
	public Object clone(){
        Object o = null;
        try{
        	ByteArrayOutputStream bo=new ByteArrayOutputStream();
    		ObjectOutputStream oo=new ObjectOutputStream(bo);
    		oo.writeObject(this);
    		ByteArrayInputStream bi=new ByteArrayInputStream(bo.toByteArray());
    		ObjectInputStream oi=new ObjectInputStream(bi);
    		o=oi.readObject();    		
        }catch(IOException ioe){
			// "深度复制任务模型发生异常:"
			log.error(AIMonLocaleFactory.getResource("MOS0000254")+ioe.getMessage());
		}
		catch(ClassNotFoundException cnte){
			// "深度复制任务模型发生异常:"
			log.error(AIMonLocaleFactory.getResource("MOS0000254")+cnte.getMessage());
		}
        return o;
    }


	/**
	 * 执行面板任务，返回任务结果
	 * 一个PanelShape对应一个界面Panel
	 * @return
	 * @throws Exception
	 */
	public void action() throws Exception{
		try{
			PanelTaskEvent event=new PanelTaskEvent();
			
			//结果如何处理
			List taskList=(List)event.getEventResult(this);
			if (taskList==null || taskList.size()<1)
				return;
			
			//同面板的多个任务（一个界面面板 Panel可能选择多主机或多应用，比如CPU信息，看多台主机，即多条线展现）,取第一个面板任务的显示装饰类
			//要求该面板的所有任务的显示装饰类必须唯一,即画的图形都是一种
			IWebPanelTaskContext context=(IWebPanelTaskContext)taskList.get(0);
			String viewStrategy=context.getViewWrapperClass();
			if (StringUtils.isBlank(viewStrategy)){
				viewStrategy=DefaultViewWrapperStrategy.class.getName();
			}
			IViewWrapperStrategy viewWrapperSV=(IViewWrapperStrategy)SimpleObjectPoolImpl.getInstance().getObject(viewStrategy);
			if (viewWrapperSV==null)
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000354",viewStrategy));
			StringBuilder sb=new StringBuilder("");
			try{
				sb.append(viewWrapperSV.wrapperHeader(taskList, panel));
				for (int i=0;i<taskList.size();i++){
					context=(IWebPanelTaskContext)taskList.get(i);
					sb.append(viewWrapperSV.wrapperBody(context.getRtnModel(), panel));
				}
				sb.append(viewWrapperSV.wrapperTail(taskList, panel));
			}catch(Exception e){
				// 面板运行期异常:
				log.error(AIMonLocaleFactory.getResource("MOS0000218"),e);
				throw  new Exception(AIMonLocaleFactory.getResource("MOS0000218")+e.getMessage());
			}finally{
				SimpleObjectPoolImpl.getInstance().releaseObject(viewStrategy,viewWrapperSV);
				if (taskList!=null && taskList.size()>0){
					taskList.clear();
					taskList=null;
				}
			}
			if (log.isDebugEnabled()){
				// "面板执行期结果:"
				log.debug(AIMonLocaleFactory.getResource("MOS0000219")+sb.toString());
			}
			shapeXml=sb.toString();
			if (GlobEnvContext.isDebug())
				log.error("-------\n"+"shapeXml-Result:" + shapeXml+"###########");
			return;
		}finally{
			if (this.signal!=null)
				this.signal.countDown();
		}		
	}
}
