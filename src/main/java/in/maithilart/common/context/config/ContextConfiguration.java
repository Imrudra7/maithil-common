package in.maithilart.common.context.config;

import org.springframework.context.annotation.Configuration;
import in.maithilart.common.context.provider.CommunicatorSecretProvider;
import in.maithilart.common.context.provider.CurrentUserProvider;
import in.maithilart.common.context.provider.EventMetadataProvider;
import in.maithilart.common.context.provider.MetadataProvider;
import in.maithilart.common.context.provider.MicroserviceNameProvider;
import in.maithilart.common.context.provider.RequestContextProvider;

import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

@Configuration
public class ContextConfiguration {

	@Bean
	@ConditionalOnMissingBean(CurrentUserProvider.class)
	CurrentUserProvider currentUserProvider() {

		return () -> null;
	}

	@Bean
	@ConditionalOnMissingBean(MetadataProvider.class)
	MetadataProvider metadataProvider() {

		return HashMap::new;
	}

	@Bean
	@ConditionalOnMissingBean(MicroserviceNameProvider.class)
	MicroserviceNameProvider microserviceNameProvider() {

		return () -> "MAITHIL-MICROS";
	}

	@Bean
	@ConditionalOnMissingBean(CommunicatorSecretProvider.class)
	CommunicatorSecretProvider communicatorSecretProvider() {

		return () -> "MAITHIL-COMM-SECRET";
	}
	
	@Bean
	@ConditionalOnMissingBean(RequestContextProvider.class)
	RequestContextProvider requestContextProvider() {

		return null;
	}

	@Bean
	@ConditionalOnMissingBean(EventMetadataProvider.class)
	EventMetadataProvider eventMetadataProvider() {

		return new EventMetadataProvider() {

			@Override
			public Map<String, Object> getEventMetadata() {
				return new HashMap<>();
			}

			@Override
			public void clear() {
				// no-op
			}
		};
	}
}
