package in.maithilart.common.observability.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import in.maithilart.common.constants.MaithilConstants.MetricsConstants;
import io.micrometer.core.instrument.MeterRegistry;

@ConditionalOnClass(MeterRegistry.class)
@Configuration
public class MetricsConfiguration {

	@Bean
	MeterRegistryCustomizer<MeterRegistry> metricsCommonTags(

			@Value("${spring.application.name:unknown}") String serviceName,

			@Value("${maithil.observability.application:maithil-art}") String application,

			@Value("${maithil.observability.environment:dev}") String environment

	) {

		return registry -> registry.config().commonTags(

				MetricsConstants.APPLICATION, application,

				MetricsConstants.SERVICE, serviceName,

				MetricsConstants.ENVIRONMENT, environment

		);
	}
}
