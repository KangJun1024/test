package com.kangjun.algorithm.mytest;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *  自定义Redis内存淘汰策略 LRU:
 *   当内存不足以容纳新写入数据时，在键空间中，移除最近最少使用的 key
 */
public class RedisLRU<K,V> extends LinkedHashMap<K,V> {
    //缓存大小
    private final int CACHE_SIZE;

    /**
     *  传递进来最多能缓存的数据
     */
    public RedisLRU(int cacheSize){
        super((int)Math.ceil(cacheSize / 0.75) + 1,0.75f,true);
        CACHE_SIZE = cacheSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > CACHE_SIZE;
    }
}
