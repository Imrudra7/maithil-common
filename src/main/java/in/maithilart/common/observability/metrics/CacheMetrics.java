package in.maithilart.common.observability.metrics;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import org.springframework.stereotype.Component;

import in.maithilart.common.constants.MaithilConstants.CacheMetricNames;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

@Component
public class CacheMetrics {

	private final MeterRegistry registry;

	private final Counter hitCounter;
	private final Counter missCounter;
	private final Counter putCounter;
	private final Counter deleteCounter;
	private final Counter errorCounter;

	public CacheMetrics(MeterRegistry registry) {

		this.registry = registry;

		this.hitCounter = Counter.builder(CacheMetricNames.HIT).description("Total cache hits").register(registry);

		this.missCounter = Counter.builder(CacheMetricNames.MISS).description("Total cache misses").register(registry);

		this.putCounter = Counter.builder(CacheMetricNames.PUT).description("Total cache puts").register(registry);

		this.deleteCounter = Counter.builder(CacheMetricNames.DELETE).description("Total cache deletes")
				.register(registry);

		this.errorCounter = Counter.builder(CacheMetricNames.ERROR).description("Total cache errors")
				.register(registry);
	}

	public void recordHit() {
		hitCounter.increment();
	}

	public void recordMiss() {
		missCounter.increment();
	}

	public void recordPut() {
		putCounter.increment();
	}

	public void recordDelete() {
		deleteCounter.increment();
	}

	public void recordError() {
		errorCounter.increment();
	}

	public <T> T recordOperation(String operation, Supplier<T> supplier) {

		Timer.Sample sample = Timer.start(registry);

		try {

			return supplier.get();

		} finally {

			sample.stop(Timer.builder(CacheMetricNames.OPERATION).tag("operation", operation).register(registry));

		}
	}

	public void recordOperation(String operation, Runnable runnable) {

		Timer.Sample sample = Timer.start(registry);

		try {

			runnable.run();

		} finally {

			sample.stop(Timer.builder(CacheMetricNames.OPERATION).tag("operation", operation).register(registry));

		}
	}
}