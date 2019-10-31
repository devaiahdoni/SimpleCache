# SimpleCache

[CacheServiceMemory](https://github.com/devaiahdoni/SimpleCache/blob/master/src/main/java/com/myservices/cache/serviceImpl/CacheServiceMemory.java)

key as String, value can be anything.

[CacheServiceFile](https://github.com/devaiahdoni/SimpleCache/blob/master/src/main/java/com/myservices/cache/serviceImpl/CacheServiceFile.java)

```
public void put(String key, Object value) 
Copies file in cache location.
ex: original path :/test/test.txt 
cache location :cache/test/test.txt

public Object get(String key)
gets File object from cached file location
```

get method returns file content as String

Referred
[FileCache](https://www.cacheonix.org/articles/How_to_Cache_a_File_in_Java.htm)
