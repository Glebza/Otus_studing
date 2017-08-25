package com.otus.study.hw11.cache.cachemodule;

public interface CacheWrapper<K, T> {
    void put(K key, T value);
    T get(K key);
}
