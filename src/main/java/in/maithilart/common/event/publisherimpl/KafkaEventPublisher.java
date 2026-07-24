package in.maithilart.common.event.publisherimpl;

//import org.springframework.stereotype.Component;

import in.maithilart.common.dto.MaithilEventMessage;
import in.maithilart.common.event.publisher.MaithilEventPublisher;

public class KafkaEventPublisher implements MaithilEventPublisher {

	public void publish(MaithilEventMessage eventMessage) {

		// kafkaTemplate.send(...)
		  System.out.println(
	                "Kafka Publisher Invoked : "
	                        + eventMessage
	        );
	}
}