package com.myservices.cache.serviceImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.io.FileUtils;

import com.myservices.cache.Service;

/**
 * Loads file name and file content in cache
 * 
 * @author Devaiah Doni
 *
 */
public class CacheServiceFile implements Service {

	// use this for concurrency support, otherwise you can use simple HashMap
	protected ConcurrentHashMap<String, Object> cachedMap;

	protected String cacheLocation = "cache";
	// this is numner of entries in cache, but this can be extended to
	// memory also
	protected int capacity = 5; // default , check factory for overriding this

	public CacheServiceFile() {
		cachedMap = new ConcurrentHashMap<String, Object>();
	}

	public CacheServiceFile(ConcurrentHashMap<String, Object> cachedMap) {
		this.cachedMap = cachedMap;
	}

	public Object get(String key) throws Exception {
//		return cachedMap.get(key);
		return getFileFromCache(key);
	}

	private Object getFileFromCache(String key) {
		Object cacheFileLocation = cachedMap.get(key);
		if (cacheFileLocation != null) {
			return new File(cacheFileLocation.toString());
		}
		// this can throw exception if key not found
		return null;
	}

	public void put(String key, Object value) throws Exception {
		if (cachedMap.containsKey(key)) {
			// check file exits in cache or not

			System.out.println(value + " File exist in Cache with key: " + key);
		} else {
			if (cachedMap.size() < capacity) {
				String cacheFilePath = getCacheFileLocation(value.toString());
				if (cacheFilePath != null) {
					// instead of file content we can added FileCacheVO Object with timestamp etc to
					// check timestamp, if file modified after cached
					cachedMap.put(key, cacheFilePath);
					System.out.println("Cached key: " + key + " with file content: " + cacheFilePath);
				} else {
					throw new FileNotFoundException(value + " File not found");
				}
			} else {
				String erorMessage = "File Cache Capacity Exceeded, max allowed :" + capacity + " ";
				// throw exception
				System.out.println(erorMessage);
				throw new Exception(erorMessage);
			}
		}
	}

	private String getCacheFileLocation(String value) throws IOException {

		if (value != null) {
			// check file exists
			// this loads file from absolute/relative path
			File file = new File(value.toString());
			if (file.exists()) {
				// read file content into String
				File destCacheFile = new File(cacheLocation + "\\" + value);
				destCacheFile.getParentFile().mkdirs();
				FileUtils.copyFile(file, destCacheFile);
				return destCacheFile.getPath();
			}
			// here can be checked if file exists in classpath
			else {
				String errorMessage = value.toString() + "NOT Found , might be deleted after cached";
				throw new FileNotFoundException(errorMessage);
			}
		}
		return null;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public String getCacheLocation() {
		return cacheLocation;
	}

	public void setCacheLocation(String cacheLocation) {
		this.cacheLocation = cacheLocation;
	}

}
