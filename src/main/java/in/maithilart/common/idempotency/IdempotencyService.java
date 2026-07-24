package in.maithilart.common.idempotency;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import in.maithilart.common.constants.MaithilConstants;
import in.maithilart.common.constants.MaithilConstants.IdempotencyStatus;
import in.maithilart.common.exception.MaithilException;

@Service
public class IdempotencyService {
	private static final Logger log = LoggerFactory.getLogger(IdempotencyService.class);
	private final IdempotencyManager manager;

	public IdempotencyService(IdempotencyManager manager) {
		this.manager = manager;

	}

	public Object execute(long ttlMinutes, IdempotencyKey key, String hash, IdempotentAction action) throws Throwable {

		log.debug("Checking idempotency for key : {}", key.getIdempotencyKey());

		IdempotencyRecord record = manager.get(key.getUserId(), key.getIdempotencyKey());

		if(record != null){
		    return handleExistingRecord(record, hash);
		}

		IdempotencyRecord processing = new IdempotencyRecord();
		processing.setStatus(IdempotencyStatus.IN_PROGRESS);
		processing.setRequestHash(hash);
		processing.setCreatedAt(Instant.now());

		boolean acquired = manager.acquireLock(key.getUserId(), key.getIdempotencyKey(), processing, Duration.ofMinutes(ttlMinutes));

		if (!acquired) {

			// Ho sakta hai dusra thread abhi COMPLETE kar chuka ho.
			record = manager.get(key.getUserId(), key.getIdempotencyKey());

			if(record != null){
			    return handleExistingRecord(record, hash);
			}

			throw new MaithilException(MaithilConstants.CONFLICT, "Request is already being processed.");
		}

		try {

			log.debug("Executing original method");

			Object response = action.execute();

			IdempotencyRecord completed = new IdempotencyRecord();

			completed.setCreatedAt(processing.getCreatedAt());
			completed.setStatus(IdempotencyStatus.COMPLETED);
			completed.setRequestHash(hash);

			if (response instanceof ResponseEntity<?> entity) {

				completed.setStatusCode(entity.getStatusCode().value());
				completed.setResponse(entity.getBody());

			} else {

				completed.setStatusCode(HttpStatus.OK.value());
				completed.setResponse(response);
			}

			manager.put(key.getUserId(), key.getIdempotencyKey(), completed, Duration.ofMinutes(ttlMinutes));

			return response;

		} catch (Throwable ex) {

			// Business fail hua to IN_PROGRESS hata do.
			manager.delete(key.getUserId(), key.getIdempotencyKey());

			throw ex;
		}
	}
	
	private Object handleExistingRecord(IdempotencyRecord record, String hash) {

	    if (!Objects.equals(record.getRequestHash(), hash)) {
	        throw new MaithilException(
	                MaithilConstants.CONFLICT,
	                "Idempotency key already used with different request");
	    }

	    if (record.getStatus() == IdempotencyStatus.COMPLETED) {
	        return record.getResponse();
	    }

	    throw new MaithilException(
	            MaithilConstants.CONFLICT,
	            "Request is already being processed.");
	}
}
