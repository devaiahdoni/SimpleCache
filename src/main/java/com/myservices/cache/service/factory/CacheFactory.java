package com.myservices.cache.service.factory;

import com.myservices.cache.Service;
import com.myservices.cache.serviceImpl.CacheServiceFile;
import com.myservices.cache.serviceImpl.CacheServiceMemory;
import com.myservices.cache.serviceImpl.CacheType;

public class CacheFactory {



	private CacheFactory() {

	}

	public static Service getCache(CacheType cacheType) {
		switch (cacheType) {
		case MEMORY:
			CacheServiceMemory cacheService = new CacheServiceMemory();
			// set capacity here getting from properties file
			cacheService.setCapacity(4);
			return cacheService;
		case FILE:
			CacheServiceFile cacheService1 = new CacheServiceFile();
			cacheService1.setCapacity(2);
			//set cache location  getting from properties file
			cacheService1.setCacheLocation("cache");
			return cacheService1;
		case DB:
			System.out.println("DB Cache NOT IMPLEMENTED");
			break;
		}
		return null;
	}

}
