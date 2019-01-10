package com.asiainfo.monitor.tools.pool.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.pool.BaseKeyedPoolableObjectFactory;
import org.apache.commons.pool.KeyedPoolableObjectFactory;
import org.apache.commons.pool.impl.GenericKeyedObjectPool;
import org.apache.commons.pool.impl.GenericKeyedObjectPoolFactory;
import org.apache.commons.pool.impl.GenericObjectPool;

public class SimpleObjectPoolImpl {

	private static transient Log log = LogFactory.getLog(SimpleObjectPoolImpl.class);

	private static SimpleObjectPoolImpl _instance=null; 
	
	private int maxActive=100;
	private int maxIdle=5;
	private long maxWaitWhenBlock =2000;
	
	private GenericKeyedObjectPoolFactory _poolfactory = null  ;          
	private GenericKeyedObjectPool _pool = null ;          
	private KeyedPoolableObjectFactory _clientfactory = null ;  
	
	
	public static SimpleObjectPoolImpl getInstance(){
		if (_instance == null) {
			synchronized (SimpleObjectPoolImpl.class) {
				if (_instance == null) {
					_instance = new SimpleObjectPoolImpl();
				}
			}
		}
		return _instance;
	}
	
	
	private SimpleObjectPoolImpl() {
		this._clientfactory = new DefaultKeyedPoolableObjectFactory();
		_poolfactory = new GenericKeyedObjectPoolFactory( _clientfactory , maxActive ,GenericObjectPool.WHEN_EXHAUSTED_BLOCK ,1*60*1000 ,maxIdle );
		_pool = (GenericKeyedObjectPool) _poolfactory.createPool() ; 
		_pool.setMaxWait(maxWaitWhenBlock);
	}

	public void close() {
		try {
			_pool.close() ;
		} catch (Exception e) {
			log.error("close client pool error",e);
		}
	}
	
	public int getAvailableNum() {
		return _pool.getNumIdle();
	}  
	
	public int getActiveNum() {
		return _pool.getNumActive();
	}
	
	public Object getObject(String key) throws Exception{
		return _pool.borrowObject(key);
	}
	
	public void releaseObject(String key,Object obj ) throws Exception {
		_pool.returnObject(key,obj);
	}  
	
	public class DefaultKeyedPoolableObjectFactory extends BaseKeyedPoolableObjectFactory implements KeyedPoolableObjectFactory {
		@Override
		public void destroyObject(Object key, Object obj) throws Exception {
		}
		
		@Override
		public boolean validateObject(Object key, Object obj) {
//			return SimpleObjectPool.validateClient((CassandraClient)obj);
			return true;
		}

		@Override
		public Object makeObject(Object key) throws Exception {
			
			try {
				if (log.isDebugEnabled())
					log.debug("create Object.......");
				return Class.forName(key.toString()).newInstance();
			} catch (Exception e) {
				log.error("create Object["+key+"] error:", e);
				throw e;
			}
		}
	}
	
	public int getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(int maxActive) {

		this.maxActive = maxActive;
		_pool.setMaxActive(maxActive);
	}

	public int getMaxIdle() {
		return maxIdle;
	}
	
	public void setMaxIdle(int maxIdle) {
		
		this.maxIdle = maxIdle;                  
		_pool.setMaxIdle(maxIdle);          
	}
	
	public long getMaxWaitWhenBlock() {
		return maxWaitWhenBlock;
	}
	
	public void setMaxWaitWhenBlock(long maxWaitWhenBlock) {
		this.maxWaitWhenBlock = maxWaitWhenBlock;                  
		_pool.setMaxWait(maxWaitWhenBlock);
	}
	
	public static void main(String[] args){
		/*
		String key="com.asiainfo.appframe.ext.monitor.panel.process.impl.DefaultTaskRtnTransform";
		try{
			for (int i=0;i<8;i++){
				ITaskRtnTransform a=(ITaskRtnTransform)SimpleObjectPool.getInstance().getObject(key);
				SimpleObjectPool.getInstance().releaseObject(key,a);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		*/
	}
	
}
