package com.myservices.cache;

public interface Service {

    Object get(String key) throws Exception;

    void put(String key, Object value) throws Exception;

}
