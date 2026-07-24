package in.maithilart.common.idempotency;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import in.maithilart.common.annotation.Idempotent;
import in.maithilart.common.constants.MaithilConstants;
import in.maithilart.common.util.HashGenerator;
import in.maithilart.common.util.RequestPayloadResolver;
import jakarta.annotation.PostConstruct;

@Aspect
@Component
public class IdempotencyAspect {
	private final Logger log = LoggerFactory.getLogger(IdempotencyAspect.class);

	private final IdempotencyKeyResolver keyResolver;
	private final IdempotencyService idempotencyService;
	private final RequestPayloadResolver requestPayloadResolver;
	private final HashGenerator hashGenerator;

	public IdempotencyAspect(IdempotencyKeyResolver keyResolver, IdempotencyService idempotencyService,
			RequestPayloadResolver requestPayloadResolver, HashGenerator hashGenerator) {
		this.keyResolver = keyResolver;
		this.idempotencyService = idempotencyService;
		this.requestPayloadResolver = requestPayloadResolver;
		this.hashGenerator = hashGenerator;
	}
	@PostConstruct
	public void init() {
	    log.info("IdempotencyAspect Loaded");
	}
	@Around("@annotation(idempotent)")
	public Object handleIdempotency(ProceedingJoinPoint joinPoint, Idempotent idempotent) throws Throwable {

		IdempotencyKey key = keyResolver.resolve();
		Object body = requestPayloadResolver.resolve(joinPoint);
		String hash = hashGenerator.generate(body, MaithilConstants.HASH_ALGO);
		return idempotencyService.execute(idempotent.ttlMinutes(), key,hash, joinPoint::proceed);
	}
}