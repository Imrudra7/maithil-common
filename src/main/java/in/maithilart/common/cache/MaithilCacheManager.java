package in.maithilart.common.cache;

import java.time.Duration;

import com.fasterxml.jackson.core.type.TypeReference;

public interface MaithilCacheManager{

	<T> void put(String key, T value);

	<T> void put(String key, T value, Duration ttl);

	<T> T get(String key, Class<T> type);
	
	<T> T get(String key, TypeReference<T> typeReference);

	void delete(String key);

	boolean exists(String key);

	void expire(String key, Duration ttl);
	
	long increment(String key);

	long increment(String key, long delta);

	<T> boolean putIfAbsent(String key, T value, Duration ttl);
}
