package com.myservices.cache.serviceImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;

import com.myservices.cache.Service;

/**
 * Loads file name and file path into memory cache get method will load the file
 * and gets file content in String
 * 
 * 
 * 
 * @author Devaiah Doni
 *
 */
public class CacheServiceFile implements Service {

	// no of files to keep in cache
	private int capacity = 5;

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	// use this for concurrency support, otherwise you can use simple HashMap
	private ConcurrentHashMap<String, Object> cachedMap;

	public CacheServiceFile() {
		cachedMap = new ConcurrentHashMap<String, Object>();
	}

	public CacheServiceFile(ConcurrentHashMap<String, Object> cachedMap) {
		this.cachedMap = cachedMap;
	}

	public Object get(String key) throws Exception {
		System.out.println(key + " getting file content :" + cachedMap.get(key));
		return getFileConent(cachedMap.get(key));
	}

	/**
	 * gets file content into String
	 * 
	 * @param object
	 * @return
	 * @throws IOException
	 */
	private String getFileConent(Object object) throws IOException {
		if (object != null) {
			// check file exists
			File file = new File(object.toString());
			if (file.exists()) {
				// read file content into String
				return new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
			} else {
				String errorMessage = object.toString() + "NOT Found , might be deleted after cached";
				throw new FileNotFoundException(errorMessage);
			}
		}

		return null;
	}

	public void put(String key, Object value) throws Exception {
		if (cachedMap.containsKey(key)) {
			System.out.println(value + " File exist in Cache with key: " + key);
		} else {
			if (cachedMap.size() < capacity) {
				if (isFileExists(value)) {
					cachedMap.put(key, value);
				} else {
					throw new FileNotFoundException(value + " File not found");
				}
				System.out.println("Cached key: " + key + " with file : " + value);
			} else {
				String erorMessage = "File Capacity Exceeded, max allowed :" + capacity + " ";
				// throw exception
				System.out.println(erorMessage);
				throw new Exception(erorMessage);
			}

		}

	}

	private boolean isFileExists(Object object) {
		boolean fileExist = false;
		if (object != null) {
			fileExist = new File(object.toString()).exists();
		}
		return fileExist;
	}

}
