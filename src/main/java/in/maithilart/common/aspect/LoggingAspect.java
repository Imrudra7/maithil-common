package in.maithilart.common.aspect;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import in.maithilart.common.annotation.Loggable;

@Order(1)
@Component
@Aspect
public class LoggingAspect {

	private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Around("@annotation(in.maithilart.common.aspect.Loggable)")
	public Object logMethodExecution(ProceedingJoinPoint joinPoint, Loggable loggable) throws Throwable {

		String methodName = joinPoint.getSignature().toShortString();

		Object[] args = joinPoint.getArgs();
		if (loggable.input()) {
			log.info("[MAITHIL-ART][ENTER] {} Args={}", methodName, Arrays.toString(args));
		}

		try {

			Object result = joinPoint.proceed();
			if (loggable.output()) {
				log.info("[MAITHIL-ART][EXIT] {} Result={}", methodName, objectMapper.writeValueAsString(result));
			}
			return result;

		} catch (Exception ex) {
			if (loggable.logExceptions()) {
				log.error("[MAITHIL-ART][ERROR] {} Exception={}", methodName, ex.getMessage());
			}
			throw ex;
		}
	}
}