package com.fon.footballfantasy.config;

import static java.util.Arrays.*;

import java.time.Duration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;

@Configuration
@EnableCaching
public class CacheConfig {

	@Bean
	public CacheManager cacheManager() {
		SimpleCacheManager cm = new SimpleCacheManager();
		cm.setCaches(asList(
				new CaffeineCache("gameweeks", Caffeine.newBuilder().maximumSize(100).recordStats().expireAfterWrite(Duration.ofHours(1)).build())));
		return cm;
	}

}
