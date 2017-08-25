package com.otus.study.hw11.cache.cachemodule;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

public class EhCacheWrapper<K, T> implements CacheWrapper<K, T> {
    private final String cacheName;
    private final CacheManager cacheManager;
    public EhCacheWrapper(final String cacheName, final CacheManager cacheManager) {
        this.cacheName = cacheName;
        this.cacheManager = cacheManager;
    }

    public void put(final K key, final T value) {
        getCache().put(new Element(key, value));
    }

    @Override
    public T get(K key) {
        Element element = getCache().get(key);
        if (element != null) {
            return (T) element.getValue();
        }
        return null;
    }


//    public V get(final K key, CacheEntryAdapter<V> adapter) {
//        Element element = getCache().get(key);
//        if (element != null) {
//            return (V) element.getValue();
//        }
//        return null;
//    }

    public Ehcache getCache() {
        return cacheManager.getEhcache(cacheName);
    }
}
