package com.fon.footballfantasy.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;

import com.fon.footballfantasy.domain.Gameweek;
import com.fon.footballfantasy.repository.BaseRepositoryTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class GameweekServiceTest extends BaseRepositoryTest {

	@Autowired
	GameweekService gameweekService;

	@Autowired
	private CacheManager cacheManager;

	@Test
	void testFindByOrderNumber_CaffeineCached() {
		log.info("Test caffeine cached find gameweek by order number");
		Gameweek gameweek1 = Gameweek.builder().orderNumber(1).matches(new ArrayList<>()).playerGameweekPerformances(new ArrayList<>()).build();
		Gameweek gameweek2 = Gameweek.builder().orderNumber(2).matches(new ArrayList<>()).playerGameweekPerformances(new ArrayList<>()).build();
		gameweekService.save(gameweek1);
		gameweekService.save(gameweek2);

		for (int i = 0; i < 3; i++) {
			Gameweek gw1 = gameweekService.findByOrderNumber(gameweek1.getOrderNumber());
			Gameweek gw2 = gameweekService.findByOrderNumber(gameweek2.getOrderNumber());

			assertEquals(gameweek1.getOrderNumber(), gw1.getOrderNumber());
			assertEquals(gameweek2.getOrderNumber(), gw2.getOrderNumber());
		}

		Cache cache = cacheManager.getCache("gameweeks");
		assertEquals(((CaffeineCache) cache).getNativeCache().estimatedSize(), 2);

		// stats
		assertEquals(((CaffeineCache) cache).getNativeCache().stats().requestCount(), 6);
		assertEquals(((CaffeineCache) cache).getNativeCache().stats().missCount(), 2);
		assertEquals(((CaffeineCache) cache).getNativeCache().stats().hitCount(), 4);
		
		((CaffeineCache) cache).getNativeCache().asMap().values().forEach(v -> System.out.println(v));

	}

}
