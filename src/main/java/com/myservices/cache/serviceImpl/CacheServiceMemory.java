package com.myservices.cache.serviceImpl;

import java.util.concurrent.ConcurrentHashMap;

import com.myservices.cache.Service;

/**
 * 
 * CacheServiceMemory class cache object with keys
 * 
 * @author Devaiah Doni
 *
 */
public class CacheServiceMemory implements Service {

	// use this for concurrency support, otherwise you can use simple HashMap
	private ConcurrentHashMap<String, Object> cachedMap;

	// this is numner of entries in cache, but this can be extended to
	// memory also
	private int capacity = 10; // default , check factory for overriding this

	public CacheServiceMemory() {
		cachedMap = new ConcurrentHashMap<String, Object>();
	}

	public CacheServiceMemory(ConcurrentHashMap<String, Object> cachedMap) {
		this.cachedMap = cachedMap;
	}

	public Object get(String key) {
		return cachedMap.get(key);
	}

	public void put(String key, Object value) throws Exception {
		if (cachedMap.containsKey(key)) {
			System.out.println("Object exist in Cache with key: " + key);
		} else {
			if (cachedMap.size() < capacity) {
				cachedMap.put(key, value);
				System.out.println("Cached key: " + key);
			} else {
				String erorMessage = "Capacity Exceeded, max allowed :" + capacity + " ";
				// throw exception
				System.out.println(erorMessage);
				throw new Exception(erorMessage);
			}

		}

	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

}
