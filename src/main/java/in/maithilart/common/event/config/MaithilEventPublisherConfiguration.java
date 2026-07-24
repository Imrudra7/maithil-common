package in.maithilart.common.event.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import in.maithilart.common.db.DatabaseExecutor;
import in.maithilart.common.event.poller.MaithilEventPoller;
import in.maithilart.common.event.pollerimpl.DatabaseEventPoller;
import in.maithilart.common.event.publisher.MaithilEventPublisher;
import in.maithilart.common.event.publisherimpl.DatabaseCommunicatorPublisher;
import in.maithilart.common.event.publisherimpl.KafkaEventPublisher;
import in.maithilart.common.event.publisherimpl.SpringApplicationEventPublisher;
import in.maithilart.common.event.util.Messenger;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@Configuration
@ConditionalOnProperty(name = "maithil.event.enabled", havingValue = "true")
public class MaithilEventPublisherConfiguration {

	@Bean
	@ConditionalOnProperty(name = "maithil.event.publisher", havingValue = "communicator")
	MaithilEventPublisher dbPublisher(DatabaseExecutor databaseExecutor, Messenger messenger) {

		return new DatabaseCommunicatorPublisher(databaseExecutor, messenger);
	}

	@Bean
	@ConditionalOnProperty(name = "maithil.event.publisher", havingValue = "spring")
	MaithilEventPublisher springPublisher(ApplicationEventPublisher publisher) {

		return new SpringApplicationEventPublisher(publisher);
	}

	@Bean
	@ConditionalOnProperty(name = "maithil.event.publisher", havingValue = "kafka")
	MaithilEventPublisher kafkaPublisher() {

		return new KafkaEventPublisher();
	}

	@Bean
	@ConditionalOnProperty(name = "maithil.event.poller.enabled", havingValue = "true")
	MaithilEventPoller databasePoller(DatabaseExecutor databaseExecutor) {
		return new DatabaseEventPoller(databaseExecutor);
	}

}
