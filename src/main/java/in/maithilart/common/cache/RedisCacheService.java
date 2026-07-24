package in.maithilart.common.cache;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import in.maithilart.common.constants.MaithilConstants.CacheOperationNames;
import in.maithilart.common.observability.metrics.CacheMetrics;

@Service
public class RedisCacheService implements MaithilCacheManager {

	private static final Logger log = LoggerFactory.getLogger(RedisCacheService.class);

	private final RedisTemplate<String, Object> redisTemplate;
	private final ObjectMapper objectMapper;
	private final AtomicBoolean redisDownLogged = new AtomicBoolean(false);
	private final CacheMetrics cacheMetrics;

	public RedisCacheService(RedisTemplate<String, Object> redisTemplate, ObjectMapper objectMapper,
			CacheMetrics cacheMetrics) {
		this.redisTemplate = redisTemplate;
		this.objectMapper = objectMapper;
		this.cacheMetrics = cacheMetrics;
		log.info("Redis Connection Factory : {}", redisTemplate.getConnectionFactory());

		log.info("DB Index : {}", redisTemplate.getConnectionFactory().getConnection());
		var connection = redisTemplate.getConnectionFactory().getConnection();

		log.info("Redis INFO:\n{}", connection.info("server"));
	}

	@Override
	public <T> T get(String key, Class<T> type) {

		return cacheMetrics.recordOperation(CacheOperationNames.GET, () -> {

			try {

				Object value = redisTemplate.opsForValue().get(key);

				if (value == null) {

					cacheMetrics.recordMiss();
					return null;
				}

				cacheMetrics.recordHit();

				return objectMapper.convertValue(value, type);

			} catch (Exception ex) {

				cacheMetrics.recordError();

				log.warn("Unable to fetch cache for key : {}", key, ex);

				return null;
			}

		});

	}

	@Override
	public <T> T get(String key, TypeReference<T> typeReference) {
		return cacheMetrics.recordOperation(CacheOperationNames.GET, () -> {
			try {

				Object value = redisTemplate.opsForValue().get(key);

				if (value == null) {
				    cacheMetrics.recordMiss();
				    return null;
				}

				cacheMetrics.recordHit();

				return objectMapper.convertValue(value, typeReference);

			} catch (Exception ex) {
				cacheMetrics.recordError();
				log.warn("Unable to fetch cache for key : {}", key, ex);

				return null;
			}
		});
	}

	@Override
	public <T> void put(String key, T value) {

		cacheMetrics.recordOperation(CacheOperationNames.PUT, () -> {

			try {

				redisTemplate.opsForValue().set(key, value);

				cacheMetrics.recordPut();

			} catch (Exception ex) {

				cacheMetrics.recordError();

				log.warn("Unable to cache key : {}", key, ex);

				// Fail-open
			}
		});
	}

	@Override
	public <T> void put(String key, T value, Duration ttl) {

		cacheMetrics.recordOperation(CacheOperationNames.PUT, () -> {

			try {

				log.info("Redis PUT : {}", key);

				redisTemplate.opsForValue().set(key, value, ttl);

				cacheMetrics.recordPut();

				log.info("Exists after PUT : {}", redisTemplate.hasKey(key));

			} catch (Exception ex) {

				cacheMetrics.recordError();

				log.warn("Unable to cache key : {}", key, ex);
			}
		});
	}

	@Override
	public void delete(String key) {

		cacheMetrics.recordOperation(CacheOperationNames.DELETE, () -> {

			try {

				redisTemplate.delete(key);

				cacheMetrics.recordDelete();

			} catch (Exception ex) {

				cacheMetrics.recordError();

				log.warn("Unable to delete cache key : {}", key, ex);
			}
		});
	}

	@Override
	public boolean exists(String key) {

		return cacheMetrics.recordOperation(CacheOperationNames.EXISTS, () -> {

			try {

				Boolean exists = redisTemplate.hasKey(key);

				if (Boolean.TRUE.equals(exists)) {
					cacheMetrics.recordHit();
				} else {
					cacheMetrics.recordMiss();
				}

				return Boolean.TRUE.equals(exists);

			} catch (Exception ex) {

				cacheMetrics.recordError();

				log.warn("Unable to check cache key : {}", key, ex);

				return false; // Fail-open preserved
			}
		});
	}

	@Override
	public void expire(String key, Duration ttl) {

	    cacheMetrics.recordOperation(CacheOperationNames.EXPIRE, () -> {

	        try {

	            redisTemplate.expire(key, ttl);

	        } catch (Exception ex) {

	            cacheMetrics.recordError();

	            log.warn("Unable to set TTL for key : {}", key, ex);
	        }

	    });
	}

	public void clear() {

	    cacheMetrics.recordOperation(CacheOperationNames.CLEAR, () -> {

	        try {

	            redisTemplate.getConnectionFactory()
	                    .getConnection()
	                    .serverCommands()
	                    .flushDb();

	        } catch (Exception ex) {

	            cacheMetrics.recordError();

	            log.warn("Unable to clear Redis DB", ex);
	        }

	    });
	}

	@Override
	public long increment(String key) {

	    return cacheMetrics.recordOperation(CacheOperationNames.INCREMENT, () -> {

	        try {

	            Long value = redisTemplate.opsForValue().increment(key);

	            return value == null ? 0L : value;

	        } catch (Exception ex) {

	            cacheMetrics.recordError();

	            log.warn("Unable to increment key {}", key, ex);

	            // Fail Open
	            if (redisDownLogged.compareAndSet(false, true)) {
	                log.error("Redis is DOWN. Rate limiting disabled.");
	            }

	            return 1L;
	        }
	    });
	}

	@Override
	public long increment(String key, long delta) {

	    return cacheMetrics.recordOperation(CacheOperationNames.INCREMENT, () -> {

	        try {

	            Long value = redisTemplate.opsForValue().increment(key, delta);

	            return value == null ? 0L : value;

	        } catch (Exception ex) {

	            cacheMetrics.recordError();

	            log.warn("Unable to increment key {}", key, ex);

	            // Fail Open
	            if (redisDownLogged.compareAndSet(false, true)) {
	                log.error("Redis is DOWN. Rate limiting disabled.");
	            }

	            return delta;
	        }
	    });
	}
	@Override
	public <T> boolean putIfAbsent(String key, T value, Duration ttl) {

	    return cacheMetrics.recordOperation(CacheOperationNames.SETNX, () -> {

	        log.info("Redis putIfAbsent : {}", key);

	        try {

	            Boolean success = redisTemplate.opsForValue()
	                    .setIfAbsent(key, value, ttl);

	            if (Boolean.TRUE.equals(success)) {
	                cacheMetrics.recordPut();
	            } else {
	                cacheMetrics.recordMiss();
	            }

	            return Boolean.TRUE.equals(success);

	        } catch (Exception ex) {

	            cacheMetrics.recordError();

	            log.warn("Unable to perform SETNX for key : {}", key, ex);

	            // Fail Open
	            return true;
	        }

	    });

	}

}