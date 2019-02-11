package com.zenika.zencontact.persistence.cache;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

public class MemCacheService {

    private static MemCacheService INSTANCE =
        new MemCacheService();

    public static MemCacheService getInstance() {
        return INSTANCE;
    }

    public MemcacheService memCache = MemcacheServiceFactory.getMemcacheService();

    public static final String CONTACTS_CACHES_KEY = "CONTACTS_CACHE";
}