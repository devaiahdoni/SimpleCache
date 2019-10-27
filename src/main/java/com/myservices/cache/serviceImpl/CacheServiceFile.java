package com.myservices.cache.serviceImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;

import com.myservices.cache.Service;

/**
 * Loads file name and file content in cache ,
 * 
 * @author Devaiah Doni
 *
 */
public class CacheServiceFile extends CacheServiceMemory implements Service {

	public CacheServiceFile() {
		super();
	}

	public CacheServiceFile(ConcurrentHashMap<String, Object> cachedMap) {
		super(cachedMap);
	}

	public Object get(String key) throws Exception {
		return super.get(key);
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
			// this loads file from absolute/relative path
			File file = new File(object.toString());
			if (file.exists()) {
				// read file content into String
				return new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
			}
			// here can be checked if file exists in classpath
			else {
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
				String fileContent = getFileConent(value);
				if (fileContent != null) {
					// instead of file content we can added FileCacheVO Object with timestamp etc to
					// check timestamp, if file modified after cached
					cachedMap.put(key, fileContent);
				} else {
					throw new FileNotFoundException(value + " File not found");
				}
				System.out.println("Cached key: " + key + " with file content: " + value);
			} else {
				String erorMessage = "File Capacity Exceeded, max allowed :" + capacity + " ";
				// throw exception
				System.out.println(erorMessage);
				throw new Exception(erorMessage);
			}
		}
	}
}
