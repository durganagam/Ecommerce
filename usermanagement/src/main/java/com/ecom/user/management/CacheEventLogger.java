package com.ecom.user.management;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;

public class CacheEventLogger implements CacheEventListener<Object, Object> {

	@Override
	public void onEvent(CacheEvent<? extends Object, ? extends Object> cacheEvent) {
		System.out.println(cacheEvent.getKey().toString() + cacheEvent.getOldValue() + cacheEvent.getNewValue());
	}

}
