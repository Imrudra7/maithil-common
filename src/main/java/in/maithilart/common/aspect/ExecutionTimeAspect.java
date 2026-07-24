package in.maithilart.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import in.maithilart.common.annotation.TrackExecutionTime;

@Order(2)
@Component
@Aspect
public class ExecutionTimeAspect {

    private static final Logger log =
            LoggerFactory.getLogger(ExecutionTimeAspect.class);

    @Around("@annotation(trackExecutionTime)")
    public Object measureExecutionTime(
            ProceedingJoinPoint joinPoint,
            TrackExecutionTime trackExecutionTime) throws Throwable {

        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - start;

        long threshold = trackExecutionTime.warnIfGreaterThan();

        if (executionTime > threshold) {

            log.warn(
                    "[AOP][SLOW_METHOD] {} took {} ms (threshold={} ms)",
                    joinPoint.getSignature().toShortString(),
                    executionTime,
                    threshold
            );

        } else {

            log.info(
                    "[AOP] {} executed in {} ms",
                    joinPoint.getSignature().toShortString(),
                    executionTime
            );

        }

        return result;
    }
}