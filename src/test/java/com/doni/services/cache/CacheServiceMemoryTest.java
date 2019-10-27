package com.doni.services.cache;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.myservices.cache.Service;
import com.myservices.cache.service.factory.CacheFactory;
import com.myservices.cache.serviceImpl.CacheType;

public class CacheServiceMemoryTest {

	Service cacheService = null;

	@Before
	public void init() {
		cacheService = CacheFactory.getCache(CacheType.MEMORY);
	}

	@Test
	public void testPutAndGet() throws Exception {
		cacheService.put("Test1Key1", "TestObject");
		Object testObject = cacheService.get("Test1Key1");
		assertNotNull(testObject);
	}

	@Test
	public void tetGetNegative() throws Exception {
		Object testObject = cacheService.get("thiskeynotavailable");
		assertNull(testObject);
	}

	@Test(expected = Exception.class)
	public void testGetCapacity() throws Exception {
		cacheService.put("Key1", "TestObject");
		cacheService.put("Key2", "TestObject2");
		cacheService.put("Key3", "TestObject3");
		cacheService.put("Key4", "TestObject4");
		cacheService.put("Key5", "TestObject5"); // this will throw capacity exception
	}
	@After
	public void clenup()
	{
		// @TODO: clear cache 
	}
}
