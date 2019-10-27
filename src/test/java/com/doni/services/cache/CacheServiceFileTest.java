package com.doni.services.cache;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.myservices.cache.Service;
import com.myservices.cache.service.factory.CacheFactory;
import com.myservices.cache.serviceImpl.CacheType;

public class CacheServiceFileTest {

	Service cacheService = null;

	@Before
	public void init() {
		cacheService = CacheFactory.getCache(CacheType.FILE);
	}

	@Test
	public void testPutAndGet() throws Exception {

		cacheService.put("TestFile1Key1", "src/test/resources/testfile1.txt");
		Object testObject = cacheService.get("TestFile1Key1");
		Assert.assertNotNull(testObject);
	}

	@Test
	public void testPutAndGetNegative() throws Exception {
		Object testObject = cacheService.get("thisfilenotavailableincache");
		Assert.assertNull(testObject);
	}

	@Test(expected = Exception.class)
	public void testGetCapacity() throws Exception {
		cacheService.put("FileKey1", "src/test/resources/testfile1.txt");
		cacheService.put("FileKey2", "testfile2.txt");
		cacheService.put("FileKey3", "src/test/resources/testfile3.txt");// this will throw capacity exception
	}

	@After
	public void clenup() {
		// @TODO: clear cache
	}
}
