package com.asiainfo.socket.queue;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

/**
 * 有界/同步的Map
 * 
 * @author 孙德东(24204)
 */
public class BoundedConcurrentMap<K,V> {
	private Map<K, V> container = new ConcurrentHashMap<K, V>();
	private Semaphore semaphore = null;
	
	/**
	 * size
	 * @param size
	 */
	public BoundedConcurrentMap(int size) {
		semaphore = new Semaphore(size);
	}
	
	public void put(K key, V value) throws InterruptedException {
		semaphore.acquire();
		container.put(key, value);
	}
	
	/*public V get(K key) {
		return container.get(key);
	}*/
	
	public V remove(K key) {
		V rtn = container.remove(key);
		semaphore.release();
		return rtn;
	}
	
	public int size() {
		return container.size();
	}
        
         public V get(K key) {
            return container.get(key);
        }
}
