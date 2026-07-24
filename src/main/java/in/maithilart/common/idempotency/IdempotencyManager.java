package in.maithilart.common.idempotency;

import java.time.Duration;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import in.maithilart.common.cache.MaithilCacheManager;
import in.maithilart.common.constants.MaithilConstants.IdempotencyStatus;

@Component
public class IdempotencyManager {

	private static final Logger log = LoggerFactory.getLogger(IdempotencyManager.class);

	private static final Duration DEFAULT_TTL = Duration.ofHours(24);

	private final MaithilCacheManager cacheManager;

	public IdempotencyManager(MaithilCacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	public IdempotencyRecord get(UUID userId, UUID idempotencyKey) {

		try {

			return cacheManager.get(buildKey(userId, idempotencyKey), IdempotencyRecord.class);

		} catch (Exception ex) {

			log.warn("Unable to fetch idempotency record", ex);

			// Fail Open
			return null;
		}
	}

	public void put(UUID userId, UUID idempotencyKey, IdempotencyRecord record) {
		log.info("Saving idempotency key : {}", idempotencyKey);
		put(userId, idempotencyKey, record, DEFAULT_TTL);
	}

	public void put(UUID userId, UUID idempotencyKey, IdempotencyRecord record, Duration ttl) {

		try {

			cacheManager.put(buildKey(userId, idempotencyKey), record, ttl);

		} catch (Exception ex) {

			log.warn("Unable to save idempotency record", ex);

			// Fail Open
		}
	}

	public void delete(UUID userId, UUID idempotencyKey) {

		try {

			cacheManager.delete(buildKey(userId, idempotencyKey));

		} catch (Exception ex) {

			log.warn("Unable to delete idempotency record", ex);

			// Fail Open
		}
	}

	public boolean exists(UUID userId, UUID idempotencyKey) {

		try {

			return cacheManager.exists(buildKey(userId, idempotencyKey));

		} catch (Exception ex) {

			log.warn("Unable to check idempotency record", ex);

			// Fail Open
			return false;
		}
	}

	private String buildKey(UUID userId, UUID idempotencyKey) {

		return "idem:" + userId + ":" + idempotencyKey;
	}

	

	boolean acquireLock(UUID userId, UUID idempotencyKey, IdempotencyRecord record, Duration ttl) {
		return cacheManager.putIfAbsent(buildKey(userId, idempotencyKey), record, ttl);
	}
}
