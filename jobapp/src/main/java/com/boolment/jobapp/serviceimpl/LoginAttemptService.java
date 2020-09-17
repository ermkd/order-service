package com.boolment.jobapp.serviceimpl;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

@Service
public class LoginAttemptService {

	private static final int MAXIMUM_NUMBER_OF_ATTEMPTS = 5;
	private static final int ATTEMPTS_INCREMENTS = 1;
	private static final int CACHE_PERSIST_TIME = 5;
	private static final int CACHE_MAX_SIZE = 100;
	private LoadingCache<String, Integer> loginAttemptCache;

	public LoginAttemptService() {
		super();
		loginAttemptCache = CacheBuilder.newBuilder().expireAfterWrite(CACHE_PERSIST_TIME, TimeUnit.MINUTES)
				.maximumSize(CACHE_MAX_SIZE).build(new CacheLoader<String, Integer>() {

					public Integer load(String key) {

						return 0;
					}
				});
	}

	public void evictUserFromLoginCache(String username) {
		loginAttemptCache.invalidate(username);

	}

	public void addUserLoginAttemptCache(String username) {
		int attempt = 0;
		try {
			attempt = ATTEMPTS_INCREMENTS + loginAttemptCache.get(username);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		loginAttemptCache.put(username, attempt);

	}

	public boolean hasExceededMaxAttempts(String username) {
		try {
			return loginAttemptCache.get(username) >= MAXIMUM_NUMBER_OF_ATTEMPTS;
		} catch (ExecutionException e) {

			e.printStackTrace();

		}
		return false;
	}

}
